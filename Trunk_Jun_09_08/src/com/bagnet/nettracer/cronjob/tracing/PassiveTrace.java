package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class PassiveTrace implements Runnable {

	// TODO: NEED TO ADD ERROR HANDLING TO EMAIL IF PT CRASHES OR FAILS TO HIT
	public static Logger logger = Logger.getLogger(PassiveTrace.class);
	private String incidentId;
	private String companyCode;
	private int daysBack;
	private int daysForward;
	private double minimumScore;
	private RuleSet ruleSet;

	public static void main(String[] args) {

		String companyCode = "NK";
		int threads = 1;
		int seconds = 60;
		int daysBack = 2;
		int daysForward = 15;
		double minimumScore = 0;

		if (args.length == 1) {
			companyCode = args[0];
		}

		Company_Specific_Variable var = AdminUtils.getCompVariable(companyCode);

		if (var.getTotal_threads() > 0) {
			threads = var.getTotal_threads();
		}


		if (var.getSeconds_wait() > 0) {
			seconds = var.getSeconds_wait();
		}
		
		if (var.getMin_match_percent() > 0) {
			minimumScore = var.getMin_match_percent();
		}

		startPassiveTracing(companyCode, threads, seconds, daysBack,
				daysForward, minimumScore);
	}

	public static void startPassiveTracing(String companyCode, int threadLimit,
			int seconds, int daysBack, int daysForward, double minimumScore) {

		ArrayList<Thread> threadList = new ArrayList<Thread>();
		List<String> incidentList = new ArrayList<String>();
		boolean initialRun = true;
		RuleSet ruleSet = new RuleSet();
		int count = 0;

		while (true) {
			Date tmpDate = null;
			// If the incidentList is empty, sleep for a little while.
			if (incidentList.size() == 0) {

				// Sleep before next run.
				if (initialRun == true) {
					initialRun = false;
				} else {
					try {
						tmpDate = new Date();

						Thread.sleep(1000 * seconds);
					} catch (InterruptedException e) {
						// Ignore exception
						e.printStackTrace();
					}
				}

				
				Session sess = HibernateWrapper.getSession().openSession();
				String sql = "SELECT * FROM INCIDENT WHERE ITEMTYPE_ID = :itemType AND "
						+ "STATUS_ID = :status ORDER BY lastupdated desc";

				SQLQuery query = sess.createSQLQuery(sql);
				query.setInteger("status", TracingConstants.MBR_STATUS_OPEN);
				query.setInteger("itemType", TracingConstants.LOST_DELAY);
				query.addScalar("INCIDENT_ID", Hibernate.STRING);
				incidentList = query.list();
				sess.close();
				
				logger.info("Starting tracing session... Total Incidents: " + incidentList.size());


			} else {
				// Get the next incident in the list and kick off a trace.
				if (threadList.size() < threadLimit) {
					String incidentId = incidentList.remove(0);
					PassiveTrace pt = new PassiveTrace(companyCode, daysBack,
							daysForward, incidentId, minimumScore, ruleSet);
					Thread ptThread = new Thread(pt);
					threadList.add(ptThread);
					ptThread.start();
					++count;
					
					if (count % 250 == 0) {
						logger.info(count + " incidents processed");
					}
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// Ignore exception
						e.printStackTrace();
					}

				}
			}

			// Clean up unused threads from end backward so I don't miss any
			// threads.
			for (int i = threadList.size() - 1; i >= 0; i--) {
				Thread thread = threadList.get(i);
				if (thread == null || (thread != null && !thread.isAlive())) {
					threadList.remove(i);
				}
			}
		}
	}

	public PassiveTrace(String companyCode, int daysBack, int daysForward,
			String incidentId, double minimumScore, RuleSet ruleSet) {
		this.companyCode = companyCode;
		this.incidentId = incidentId;
		this.daysBack = daysBack;
		this.daysForward = daysForward;
		this.minimumScore = minimumScore;
		this.ruleSet = ruleSet;
	}

	public void run() {
		String message = null;
		try {
			logger.debug("Incident ID: " + incidentId);
	
			// Get the hibernate session we're going to use for this thread.
			Session sess = HibernateWrapper.getSession().openSession();
			String lastTraced = null;
			
			// Get the Inc
			Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
	
			// We only want to trace against ohds that have not been traced or have
			// been updated in recent history.
			String lastUpdatedStr = null;
			if (incident.getOhd_lasttraced() == null) {
				lastUpdatedStr = " LASTUPDATED IS NOT NULL ";
			} else {
				lastUpdatedStr = " LASTUPDATED >= :lastUpdated";
			}
	
			// Find the earliest date to search from - the earlier of the create
			// date and the first itinerary departure date.
			Date searchFromDate = incident.getCreatedate();
	
			Set<Itinerary> itinSet = ((Set<Itinerary>) incident.getItinerary());
			if (itinSet != null && itinSet.size() > 0) {
				Object[] itinArray = (itinSet.toArray());
				Itinerary itin = (Itinerary) itinArray[0];
				if (itin.getDepartdate() != null
						&& itin.getDepartdate().compareTo(searchFromDate) < 0) {
					searchFromDate = itin.getDepartdate();
				}
	
			}
			
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(searchFromDate);
			cal.add(Calendar.DAY_OF_YEAR, -this.daysBack);
			Date beginDateRange = cal.getTime();
			cal.setTime(searchFromDate);
			cal.add(Calendar.DAY_OF_YEAR, this.daysForward);
			Date endDateRange = cal.getTime();
	
			String dateRange = " FOUNDDATE >= :beginDateRange AND FOUNDDATE <= :endDateRange ";
			
			String sql = "SELECT * FROM OHD WHERE "
					+ "(STATUS_ID = :status1 or STATUS_ID = :status2) AND " + dateRange + " AND "
					+ lastUpdatedStr + " ORDER BY LASTUPDATED ASC";
			
			
			
			SQLQuery query = sess.createSQLQuery(sql);
			query.setInteger("status1", TracingConstants.OHD_STATUS_OPEN);
			query.setInteger("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
			query.setTimestamp("beginDateRange", beginDateRange);
			query.setTimestamp("endDateRange", endDateRange);
	
			if (incident.getOhd_lasttraced() != null) {		
				Date ohdLastTraced = DateUtils.convertToDate(incident
						.getOhd_lasttraced(), TracingConstants.DB_DATETIMEFORMAT, null);
				query.setTimestamp("lastUpdated", ohdLastTraced);
			}
			
			query.addScalar("OHD_ID", Hibernate.STRING);
	
			List<String> ohdList = query.list();
			
			for (String ohd_ID : ohdList) {
				message = "Exception thrown - Incident: " + incident.getIncident_ID() + " OHD:" + ohd_ID; 
				OHD ohd = OhdBMO.getOHDByID(ohd_ID, sess);
				sess.setReadOnly(ohd, true);
				Score score = Trace.trace(incident, ohd, null);
	
				logger.debug("  OHD: " + ohd_ID + "  Score: " + score.getOverallScore());
	
				GregorianCalendar now = new GregorianCalendar();
				now.setTime(ohd.getLastupdated());
				now.add(Calendar.SECOND, 1);
				
	
				lastTraced = DateUtils.formatDate(now.getTime(), TracingConstants.DB_DATETIMEFORMAT, null, null);
				
				// If worth scoring, add to DB.
				if (score.getOverallScore() > this.minimumScore) {
					
					// Check to see if this is already in DB (Inc, OHD, Percent) - if so, skip.
					// TODO: Not sure we really need this, so I'm skipping for now.
					
					logger.info("  Match detected - Incident: " + incident.getIncident_ID() + " OHD: " + ohd.getOHD_ID() + " Score: " + score.getOverallScore());
					// Add to DB
					Match match = new Match();
					match.setBagnumber(score.getBagNumber());
					match.setMatch_type(TracingConstants.PASSIVE_MATCH_TYPE);
					match.setMatch_percent(score.getOverallScore());
					match.setStatus(StatusBMO.getStatus(TracingConstants.MATCH_STATUS_OPEN));
					match.setMatch_made_on(DateUtils.convertToGMTDate(new Date()));
					match.setMbr(incident);
					match.setOhd(ohd);
					
					HashSet<Match_Detail> details = new HashSet<Match_Detail>();
					
					
					// TODO: Determine what to do with:
					// 'categories' (1 or 2)
					// 'claimchecknum' (Should we really show?)
					
					for (MatchResult result: score.getMatchResults()) {
						if (result.isUsedInScoring()) {
							Match_Detail detail = new Match_Detail();
							detail.setMatch(match);
							detail.setMbr_info(result.getIncidentContents());
							detail.setOhd_info(result.getOhdContents());
							detail.setPercentage(result.getPercentMatch());
							detail.setItem(result.getMatchElement().getConstant());
							details.add(detail);
						}
					}
					
					match.setDetails(details);
					HibernateUtils.save(match);
					sess.evict(match);
				}
				sess.evict(ohd);
			}
			
			if (lastTraced != null) {
				incident.setOhd_lasttraced(lastTraced);
				HibernateUtils.save(incident, sess);
			}
			sess.close();
		} catch (Exception e) {
			logger.error(message);
			e.printStackTrace();
		}

	}
}
