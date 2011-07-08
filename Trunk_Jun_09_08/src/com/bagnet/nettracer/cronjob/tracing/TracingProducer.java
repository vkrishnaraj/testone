package com.bagnet.nettracer.cronjob.tracing;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.tracing.dto.ConsumerDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.ProducerDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.SettingsDTO;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.db.TraceItinerary;

public class TracingProducer implements Runnable {

	ArrayBlockingQueue<ProducerDTO> queue = null;
	ArrayBlockingQueue<ConsumerDTO> consumerQueue = null;
	SettingsDTO settings = null;

	private static final int OHD_ID_INDEX = 0;

	private static final int OHD_DATE_INDEX = 1;

	private Session sess = null;
	private Session dirtySess = null;

	public TracingProducer(ArrayBlockingQueue<ProducerDTO> queue,
			ArrayBlockingQueue<ConsumerDTO> consumerQueue, SettingsDTO settings) {
		this.queue = queue;
		this.consumerQueue = consumerQueue;
		this.settings = settings;

	}

	public void run() {
		String message = "";
		ProducerDTO dto;
		while (true) {
			try {
				if (sess == null) {
					sess = HibernateWrapper.getSession().openSession();
				}
				if(dirtySess == null) {
					dirtySess = HibernateWrapper.getDirtySession().openSession();
				}
				dto = queue.take();

				TraceIncident incident = settings.getIncidentCache().loadIncident(
						dto.getIncidentId(), dto.getOhdLastTraced(), sess);

				if (incident == null) {
					String LOCAL_ERROR_MESSAGE = "No incident found by ID - usually indicative of DB error: "
							+ dto.getIncidentId();
					settings.getLogger().error(LOCAL_ERROR_MESSAGE);
					settings.getErrorHandler().sendEmail(LOCAL_ERROR_MESSAGE + "<br /><br />", false, true);

					try {
						Thread.sleep(300 * 1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

				} else {
					message = "Exception thrown - TraceIncident: "
						+ incident.getIncident_ID();
					String lastUpdatedStr = null;
					if (incident.getOhd_lasttraced() == null) {
						lastUpdatedStr = " LASTUPDATED IS NOT NULL ";
					} else {
						lastUpdatedStr = " LASTUPDATED >= :lastUpdated";
					}

					// Find the earliest date to search from - the earlier of the create
					// date and the first itinerary departure date.
					Date searchFromDate = incident.getCreatedate();

					Set<TraceItinerary> itinSet = ((Set<TraceItinerary>) incident.getItinerary());
					if (itinSet != null && itinSet.size() > 0) {
						Object[] itinArray = (itinSet.toArray());
						TraceItinerary itin = (TraceItinerary) itinArray[0];
						if (itin.getDepartdate() != null
								&& itin.getDepartdate().compareTo(searchFromDate) < 0) {
							searchFromDate = itin.getDepartdate();
						}
					}

					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(searchFromDate);
					cal.add(Calendar.DAY_OF_YEAR, -settings.getDaysBack());
					Date beginDateRange = cal.getTime();
					cal.setTime(searchFromDate);
					cal.add(Calendar.DAY_OF_YEAR, settings.getDaysForward());
					Date endDateRange = cal.getTime();

					String dateRange = " FOUNDDATE >= :beginDateRange AND FOUNDDATE <= :endDateRange ";

					String sql = "SELECT OHD_ID, lastupdated FROM OHD WHERE "
							+ "(STATUS_ID = :status1 or STATUS_ID = :status2) AND "
							+ dateRange + " AND " + lastUpdatedStr
							+ " ORDER BY LASTUPDATED ASC";

					SQLQuery query = dirtySess.createSQLQuery(sql);
					query.setInteger("status1", TracingConstants.OHD_STATUS_OPEN);
					query.setInteger("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
					query.setTimestamp("beginDateRange", beginDateRange);
					query.setTimestamp("endDateRange", endDateRange);

					if (incident.getOhd_lasttraced() != null) {
						query.setTimestamp("lastUpdated", incident.getOhd_lasttraced());
					}

					query.addScalar("OHD_ID", Hibernate.STRING);
					query.addScalar("lastupdated", Hibernate.TIMESTAMP);

					List<Object[]> ohdList = query.list();
					settings.getLogger().debug("Total OHDs found: " + ohdList.size());

					for (int i=0; i<ohdList.size(); ++ i) {
						Object[] ohdItem = ohdList.get(i);
						String ohdId = (String) ohdItem[OHD_ID_INDEX];
						Date lastUpdated = (Date) ohdItem[OHD_DATE_INDEX];
						ConsumerDTO conDto = new ConsumerDTO(incident, ohdId, lastUpdated);
						if (i == ohdList.size() - 1) {
							conDto.setLastIncident(true);
							settings.getLogger().debug("Consumer queue size: " + consumerQueue.size());
							settings.getLogger().debug("Producer queue size: " + queue.size());
							
						}
						consumerQueue.put(conDto);
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (HibernateException e) {
				
				if(sess != null) {
					sess.close();
					sess = null;
				}
				if(dirtySess != null) {
					dirtySess.close();
					dirtySess = null;
				}
				settings.getLogger().error(message, e);
				settings.getErrorHandler().sendEmail(message + "<br /><br />", e,
						false, true);
				e.printStackTrace();

				try {
					Thread.sleep(300 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				if(sess != null) {
					sess.close();
					sess = null;
				}
				if(dirtySess != null) {
					dirtySess.close();
					dirtySess = null;
				}
				settings.getLogger().error(message, e);
				settings.getErrorHandler().sendEmail(message + "<br /><br />", e,
						false, false);
				e.printStackTrace();

				try {
					Thread.sleep(300 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
