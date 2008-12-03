package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.tracing.dto.ConsumerDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.ProducerDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.SettingsDTO;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class PassiveTrace {

	public Logger logger = null;
	
	
	protected static String SECONDARY_COLOR = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_COLOR_PERCENT);
	protected static String TERTIARY_COLOR = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_COLOR_PERCENT);
	protected static String SECONDARY_TYPE = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_TYPE_PERCENT);
	protected static String TERTIARY_TYPE = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_TYPE_PERCENT);
	protected static ConcurrentHashMap<String, String> AirlineConversionMap = new ConcurrentHashMap<String, String>();
	public static final String PROPERTY_TRACING_DAYS_BACKWARD = "tracing.backward";
	public static final String PROPERTY_TRACING_DAYS_FORWARD = "tracing.forward";
	public static final String PROPERTY_TRACING_DAY_BREAK = "tracing.oldnewbreak";
	private static final String PROPERTY_MINIMUM_RUN_LENGTH = "tracing.minutes.wait";
	private static final int MINIMUM_RUN_LENGTH = Integer.parseInt(PropertyBMO.getValue(PROPERTY_MINIMUM_RUN_LENGTH));

	private static final int INCIDENT_INDEX = 0;
	private static final int TRACED_INDEX = 1;

	
	
	public enum PTMode {NEW, OLD};


	public static void main(String[] args) {	
  	String instanceName = "Test";
		startPassiveTracing("US", instanceName, PTMode.OLD);
	}

	public static void startPassiveTracing(String companyCode, String instanceName, PTMode mode) {
		String loggerName = mode.name() + "PassiveTracing";
		Logger logger = Logger.getLogger(loggerName);
		int threadLimit = 10;
		double minimumScore = 50;
		int daysForward = Integer.parseInt(PropertyBMO.getValue(PROPERTY_TRACING_DAYS_FORWARD));
		int daysBack = Integer.parseInt(PropertyBMO.getValue(PROPERTY_TRACING_DAYS_BACKWARD));
		
		Company_Specific_Variable var = AdminUtils.getCompVariable(companyCode);
		
		if (var.getTotal_threads() > 0) {
			threadLimit = var.getTotal_threads();
		}
		
		logger.info("Total threads: " + threadLimit);

		if (var.getMin_match_percent() > 0) {
			minimumScore = var.getMin_match_percent();
		}

		// Generate Error Handler
		String emailHost = var.getEmail_host();
		String emailFrom = var.getEmail_from();
		String emailTo = var.getEmail_to();
		int port = var.getEmail_port();

		PassiveTraceErrorHandler errorHandler = new PassiveTraceErrorHandler(
				emailHost, emailFrom, emailTo, port, instanceName);
		
		
		List<Object[]> incidentList = new ArrayList<Object[]>();

		
		TracingOhdCache ohdCache = new TracingOhdCache(loggerName);;
		TracingIncidentCache incidentCache =  new TracingIncidentCache(loggerName);
				
		ArrayBlockingQueue<ProducerDTO> producerQueue = new ArrayBlockingQueue<ProducerDTO>(5);
		ArrayBlockingQueue<ConsumerDTO> consumerQueue = new ArrayBlockingQueue<ConsumerDTO>(500);
		
		int NUM_PRODUCER_THREADS = java.lang.Math.max(threadLimit / 4, 1);
		int NUM_CONSUMER_THREADS = java.lang.Math.max(threadLimit / 4 * 3, 1);
		
		
		logger.info("Producer Threads: " + NUM_PRODUCER_THREADS);
		logger.info("Consumer Threads: " + NUM_CONSUMER_THREADS);
		
		SettingsDTO settings = new SettingsDTO(companyCode, daysBack, daysForward, minimumScore, null, errorHandler, ohdCache, incidentCache, logger);
				
		// Create producers and consumers
		for (int i=0; i<NUM_PRODUCER_THREADS; ++i) {
			TracingProducer producer = new TracingProducer(producerQueue, consumerQueue, settings);
			new Thread(producer).start();
		}
		
		for (int i=0; i<NUM_CONSUMER_THREADS; ++i) {
			TracingConsumer consumer = new TracingConsumer(consumerQueue, settings);
			new Thread(consumer).start();
		}

		long elapsedTime = 0;
		long remainingTime = 0;
		
		// Produce List of incidents for consumers to consume.
		while (true) {
			try {
				Calendar startTime = new GregorianCalendar();
				// Reset cache if necessary
				if (settings.getOhdCache().getReCacheDate().before(new GregorianCalendar())) {
					settings.setOhdCache(new TracingOhdCache(settings.getLogger().getName()));
				}
				
				if (settings.getIncidentCache().getReCacheDate().before(new GregorianCalendar())) {
					settings.setIncidentCache(new TracingIncidentCache(settings.getLogger().getName()));
				}
				incidentCache = settings.getIncidentCache();
				ohdCache = settings.getOhdCache();

				settings.setRuleSet(new RuleSet());

				
				Session sess = HibernateWrapper.getSession().openSession();
				Calendar c;
				Date cutoff = null;
				String hsql = "";
				int hourBreak = Integer.parseInt(PropertyBMO.getValue(PROPERTY_TRACING_DAY_BREAK));
				switch (mode) {
				case NEW:
					c = new GregorianCalendar();
					c.setTime(TracerDateTime.getGMTDate());
					c.add(GregorianCalendar.HOUR, -hourBreak);
					cutoff = c.getTime();
					hsql = "SELECT i.incident_ID, i.ohd_lasttraced FROM Incident i WHERE i.itemtype.itemType_ID = :itemType AND "
							+ " i.status.status_ID = :status and (i.createdate > :dateCutoff or (i.createdate = :dateCutoff and i.createtime >= :timeCutoff)) ORDER BY i.lastupdated desc";
					break;
				case OLD:
					c = new GregorianCalendar();
					c.setTime(TracerDateTime.getGMTDate());
					c.add(GregorianCalendar.HOUR, -hourBreak);
					
					cutoff = c.getTime();
					hsql = "SELECT i.incident_ID, i.ohd_lasttraced FROM Incident i WHERE i.itemtype.itemType_ID = :itemType AND "
							+ " i.status.status_ID = :status and (i.createdate < :dateCutoff or (i.createdate = :dateCutoff and i.createtime <= :timeCutoff)) ORDER BY i.lastupdated desc";
					break;
				}

				Query query = sess.createQuery(hsql);
				query.setInteger("status", TracingConstants.MBR_STATUS_OPEN);
				query.setInteger("itemType", TracingConstants.LOST_DELAY);
				query.setDate("dateCutoff", cutoff);
				query.setTime("timeCutoff", cutoff);
				
				incidentList = query.list();
				
				logger.info("Starting tracing session... Total Incidents: "
						+ incidentList.size());
				
				for (int i = 0; i<incidentList.size(); ++i) {
					String incidentId = (String) incidentList.get(i)[INCIDENT_INDEX];
					ProducerDTO prodDto = new ProducerDTO();
					prodDto.setIncidentId(incidentId);
					prodDto.setOhdLastTraced((String) incidentList.get(i)[TRACED_INDEX]);
					producerQueue.put(prodDto);
				}
				
				// Wait for everything to complete.
				while (producerQueue.size() > 0 || consumerQueue.size() > 0) {
					Thread.sleep(10*1000);
				}
				
				logger.info("All incidents processed...");
				
				// RESET INCIDENT CACHE
				incidentCache.reset();
				
				// RESET OHD CACHE
				String sql = "SELECT * FROM OHD WHERE "
					+ "(STATUS_ID = :status1 or STATUS_ID = :status2) order by founddate asc";

				SQLQuery validOhdQuery = sess.createSQLQuery(sql);
				
				validOhdQuery.setInteger("status1", TracingConstants.OHD_STATUS_OPEN);
				validOhdQuery.setInteger("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
	
				validOhdQuery.addScalar("OHD_ID", Hibernate.STRING);
				validOhdQuery.addScalar("founddate", Hibernate.DATE);
	
				List<Object[]> validOhdList = validOhdQuery.list();
				
				sess.close();
				
				ohdCache.reset(validOhdList);
				// TODO: HERE
				//OhdReference reference = new OhdReference();
				// reference.rebuild(validOhdList);
				
				Calendar completeTime = new GregorianCalendar();
				elapsedTime = completeTime.getTimeInMillis() - startTime.getTimeInMillis();
				remainingTime = (MINIMUM_RUN_LENGTH*60*1000) - elapsedTime;

				if (remainingTime > 0) {
					logger.info("Elapsed Tracing Cycle Time: " + elapsedTime/1000  + " seconds.  Sleeping: " + remainingTime / 1000 + " seconds.");
					Thread.sleep(remainingTime);
				} else {
					logger.info("Elapsed Tracing Cycle Time: " + elapsedTime/1000  + " seconds.");	
				}

			} catch (HibernateException e) {
				logger.fatal("Passive tracer encountered a FATAL HIBERNATE error while running.", e);
				e.printStackTrace();
				errorHandler.sendEmail("Passive tracer encountered a FATAL HIBERNATE error while running.", e, true, true);
			
				try {
					Thread.sleep(60*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				logger.fatal("Passive tracer encountered a FATAL error while running...", e);
				errorHandler.sendEmail("Passive tracer encountered a FATAL error while running...", true, false);
				
			}
		}
	}
}
