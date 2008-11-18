package com.bagnet.nettracer.cronjob.tracing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.db.TraceOHD;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class PassiveTrace implements Runnable {

	public Logger logger = null;
	
	private String incidentId;
	private String companyCode;
	private int daysBack;
	private int daysForward;
	private double minimumScore;
	private RuleSet ruleSet;
	private TracingOhdCache ohdCache;
	private PassiveTraceErrorHandler errorHandler;
	protected static String SECONDARY_COLOR = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_COLOR_PERCENT);
	protected static String TERTIARY_COLOR = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_COLOR_PERCENT);
	protected static String SECONDARY_TYPE = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_TYPE_PERCENT);
	protected static String TERTIARY_TYPE = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_TYPE_PERCENT);
	protected static ConcurrentHashMap<String, String> AirlineConversionMap = new ConcurrentHashMap<String, String>();
	public static final String PROPERTY_TRACING_DAYS_BACKWARD = "tracing.backward";
	public static final String PROPERTY_TRACING_DAYS_FORWARD = "tracing.forward";
	public static final String PROPERTY_TRACING_DAY_BREAK = "tracing.oldnewbreak";

	private static final int INCIDENT_INDEX = 0;

	private static final int TRACED_INDEX = 1;

	private static final int OHD_ID_INDEX = 0;

	private static final int OHD_DATE_INDEX = 1;
	
	private PTMode mode;

	private TracingIncidentCache incidentCache;

	private String lastTracedDate;
	
	public enum PTMode {NEW, OLD};


	public static void main(String[] args) {
		PassiveTraceErrorHandler errorHandler = null;
		
		String companyCode = null;
		int threads = 1;
		int seconds = 60;
		int daysBack = 2;
		int daysForward = 45;
		double minimumScore = 0;

		String instanceName = "Test";
		startPassiveTracing("US", instanceName, PTMode.OLD);
	}

	public static void startPassiveTracing(String companyCode, String instanceName, PTMode mode) {
		String loggerName = mode.name() + "PassiveTracing";
		Logger logger = Logger.getLogger(loggerName);
		int threadLimit = 10;
		int seconds = 60;
		double minimumScore = 50;
		int daysForward = Integer.parseInt(PropertyBMO.getValue(PROPERTY_TRACING_DAYS_FORWARD));
		int daysBack = Integer.parseInt(PropertyBMO.getValue(PROPERTY_TRACING_DAYS_BACKWARD));
		
		Company_Specific_Variable var = AdminUtils.getCompVariable(companyCode);
		
		if (var.getTotal_threads() > 0) {
			threadLimit = var.getTotal_threads();
		}
		
		logger.info("Total threads: " + threadLimit);

		if (var.getSeconds_wait() > 0) {
			seconds = var.getSeconds_wait();
		}

		if (var.getMin_match_percent() > 0) {
			minimumScore = var.getMin_match_percent();
		}

		String emailHost = var.getEmail_host();
		String emailFrom = var.getEmail_from();
		String emailTo = var.getEmail_to();
		int port = var.getEmail_port();

		PassiveTraceErrorHandler errorHandler = new PassiveTraceErrorHandler(
				emailHost, emailFrom, emailTo, port, instanceName);
		
		List<Object[]> incidentList = new ArrayList<Object[]>();
		boolean initialRun = true;
		RuleSet ruleSet = new RuleSet();
		TracingOhdCache ohdCache = null;
		TracingIncidentCache incidentCache = null;
		
		ArrayList<Date> hibernateErrorDates = new ArrayList<Date>();
		
		while (true) {
			try {
				Date tmpDate = null;

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
							+ " i.status.status_ID = :status and i.createdate > :dateCutoff and i.createtime > :timeCutoff ORDER BY i.lastupdated desc";
					break;
				case OLD:
					c = new GregorianCalendar();
					c.setTime(TracerDateTime.getGMTDate());
					c.add(GregorianCalendar.HOUR, -hourBreak);
					
					cutoff = c.getTime();
					hsql = "SELECT i.incident_ID, i.ohd_lasttraced FROM Incident i WHERE i.itemtype.itemType_ID = :itemType AND "
							+ " i.status.status_ID = :status and i.createdate < :dateCutoff and i.createtime < :timeCutoff ORDER BY i.lastupdated desc";
					break;
				}

				Query query = sess.createQuery(hsql);
				query.setInteger("status", TracingConstants.MBR_STATUS_OPEN);
				query.setInteger("itemType", TracingConstants.LOST_DELAY);
				query.setDate("dateCutoff", cutoff);
				query.setTime("timeCutoff", cutoff);
				incidentList = query.list();
				
				
				if (ohdCache == null || ohdCache.getReCacheDate().before(new GregorianCalendar())) {
					ohdCache = new TracingOhdCache(loggerName);
				}
				
				if (incidentCache == null || incidentCache.getReCacheDate().before(new GregorianCalendar())) {
					incidentCache = new TracingIncidentCache(loggerName);
				}
				
				
				
				logger.info("Starting tracing session... Total Incidents: "
						+ incidentList.size());
				
				ExecutorService pool = Executors.newFixedThreadPool(threadLimit);
				
				for (int i = 0; i<incidentList.size(); ++i) {
					String incidentId = (String) incidentList.get(i)[INCIDENT_INDEX];
					PassiveTrace pt = new PassiveTrace(companyCode, daysBack,
						daysForward, incidentId, minimumScore, ruleSet,
						errorHandler, ohdCache, incidentCache, logger, (String) incidentList.get(i)[TRACED_INDEX]);
					pool.execute(pt);
					
					if (i % 250 == 0 && i > 0) {
						logger.info(i + " incidents processed");
					}
				}
					
				pool.shutdown();

				while (pool.isTerminated() == false) {
					Thread.sleep(10*1000);
				}
				logger.info("All incidents processed...");
				
				// Reset Caches
				incidentCache.reset();
				
				String sql = "SELECT * FROM OHD WHERE "
					+ "(STATUS_ID = :status1 or STATUS_ID = :status2)";

				SQLQuery validOhdQuery = sess.createSQLQuery(sql);
				
				validOhdQuery.setInteger("status1", TracingConstants.OHD_STATUS_OPEN);
				validOhdQuery.setInteger("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
	
				validOhdQuery.addScalar("OHD_ID", Hibernate.STRING);
	
				List<String> validOhdList = validOhdQuery.list();
				sess.close();
				
				ohdCache.reset(validOhdList);
				
				if (incidentList.size() == 0) {
					Thread.sleep(30*1000);
				}

			} catch (HibernateException e) {

				errorHandler.sendEmail("Passive tracer encountered a FATAL HIBERNATE error while running.", e, true, true);
				
				logger.fatal("Passive tracer encountered a FATAL HIBERNATE error while running.", e);
				e.printStackTrace();
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

	public PassiveTrace(String companyCode, int daysBack, int daysForward,
			String incidentId, double minimumScore, RuleSet ruleSet,
			PassiveTraceErrorHandler errorHandler, TracingOhdCache ohdCache, TracingIncidentCache incidentCache, Logger logger, String lastTracedDate) {
		this.companyCode = companyCode;
		this.incidentId = incidentId;
		this.daysBack = daysBack;
		this.daysForward = daysForward;
		this.minimumScore = minimumScore;
		this.ruleSet = ruleSet;
		this.errorHandler = errorHandler;
		this.ohdCache = ohdCache;
		this.incidentCache = incidentCache;
		this.logger = logger;
		this.lastTracedDate = lastTracedDate;
		
	}

	public void run() {
		String message = null;
		try {
			logger.debug("TraceIncident ID: " + incidentId);
			
			// Get the hibernate session we're going to use for this thread.
			Session sess = HibernateWrapper.getSession().openSession();
			String lastTraced = null;

			TraceIncident incident = incidentCache.loadIncident(incidentId, lastTracedDate, sess);
			
			if (incident == null) {
				String LOCAL_ERROR_MESSAGE = "No incident found by ID - usually indicative of DB error: " + incidentId; 
				logger.error(LOCAL_ERROR_MESSAGE);
				errorHandler.sendEmail(message + "<br /><br />", false, true);
				
				try {
					Thread.sleep(300*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			} else {

				// We only want to trace against ohds that have not been traced or
				// have been updated in recent history.
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
						+ "(STATUS_ID = :status1 or STATUS_ID = :status2) AND "
						+ dateRange + " AND " + lastUpdatedStr
						+ " ORDER BY LASTUPDATED ASC";
	
				SQLQuery query = sess.createSQLQuery(sql);
				query.setInteger("status1", TracingConstants.OHD_STATUS_OPEN);
				query.setInteger("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
				query.setTimestamp("beginDateRange", beginDateRange);
				query.setTimestamp("endDateRange", endDateRange);
	
				if (incident.getOhd_lasttraced() != null) {
					Date ohdLastTraced = DateUtils.convertToDate(incident
							.getOhd_lasttraced(),
							TracingConstants.DB_DATETIMEFORMAT, null);
					query.setTimestamp("lastUpdated", ohdLastTraced);
				}
	
				query.addScalar("OHD_ID", Hibernate.STRING);
				query.addScalar("lastupdated", Hibernate.TIMESTAMP);
	
				List<Object[]> ohdList = query.list();
				logger.debug("Total OHDs found: " + ohdList.size());
	
				for (Object[] ohdItem : ohdList) {
					String ohd_ID = (String) ohdItem[OHD_ID_INDEX];
					Date lastUpdated = (Date) ohdItem[OHD_DATE_INDEX];
					
					message = "Exception thrown - TraceIncident: "
							+ incident.getIncident_ID() + " OHD:" + ohd_ID;
					//OHD ohd = (OHD) sess.load(OHD.class, ohd_ID);
					TraceOHD ohd = ohdCache.loadOhd(ohd_ID, lastUpdated, sess);
					//OHD ohd = OhdBMO.getOHDByID(ohd_ID, sess);

					Score score = Trace.trace(incident, ohd, null);
	
					logger.debug("  OHD: " + ohd_ID + "  Score: "
							+ score.getOverallScore());
	
					GregorianCalendar now = new GregorianCalendar();
					now.setTime(ohd.getLastupdated());
					now.add(Calendar.SECOND, 1);
	
					lastTraced = DateUtils.formatDate(now.getTime(),
							TracingConstants.DB_DATETIMEFORMAT, null, null);
	
					// If worth scoring, add to DB.
					if (score.getOverallScore() > this.minimumScore || score.isGtsv()) {
						
						// Does the match already exist in our system?
						double percMatch = formatPercent(score.getOverallScore());
						
						String matchExistsSQL = "SELECT COUNT(*) as COUNT FROM MATCH_HISTORY WHERE MBR_NUMBER= :incident_ID AND OHD_ID = :ohd_ID AND MATCH_PERCENT = :matchPercent";
		
						SQLQuery matchExistsQuery = sess.createSQLQuery(matchExistsSQL);
						matchExistsQuery.setString("incident_ID", incidentId);
						matchExistsQuery.setString("ohd_ID", ohd_ID);
						matchExistsQuery.setDouble("matchPercent", percMatch);
						matchExistsQuery.addScalar("COUNT", Hibernate.INTEGER);
						List<Integer> queryResults = matchExistsQuery.list();
						int queryCount = queryResults.get(0).intValue();
						
						// If we have not saved this particular match before, then do so now.
						if (queryCount == 0) {
							logger.info("  New Match detected - TraceIncident: "
									+ incident.getIncident_ID() + " OHD: "
									+ ohd.getOHD_ID() + " Score: "
									+ score.getOverallScore());
	
							
							Match match = new Match();
							match.setBagnumber(score.getBagNumber());
							match.setMatch_type(TracingConstants.PASSIVE_MATCH_TYPE);
							match.setMatch_percent(formatPercent(score.getOverallScore()));
							match.setStatus(StatusBMO
									.getStatus(TracingConstants.MATCH_STATUS_OPEN));
							match.setMatch_made_on(TracerDateTime.getGMTDate());
							Incident tmpIncident = new Incident();
							tmpIncident.setIncident_ID(incident.getIncident_ID());
							match.setMbr(tmpIncident);
							OHD tmpOhd = new OHD();
							tmpOhd.setOHD_ID(ohd.getOHD_ID());
							match.setOhd(tmpOhd);
							match.setClaimchecknum(score.getClaimCheckNumber());
		
							HashSet<Match_Detail> details = new HashSet<Match_Detail>();
		
							for (MatchResult result : score.getMatchResults()) {
								if (result.isUsedInScoring()) {
									Match_Detail detail = new Match_Detail();
									detail.setMatch(match);
									detail.setMbr_info(result.getIncidentContents());
									detail.setOhd_info(result.getOhdContents());
									detail.setPercentage(formatPercent(result.getPercentMatch()));
									detail.setItem(result.getMatchElement()
											.getConstant());
									details.add(detail);
								}
							}
		
							match.setDetails(details);
							HibernateUtils.save(match);
							sess.evict(match);
						}
					}
					sess.evict(ohd);
				}
	
				if (lastTraced != null) {
					incident.setOhd_lasttraced(lastTraced);
					HibernateUtils.save(incident, sess);
				}
				sess.close();
			}
		} catch (HibernateException e) {
			logger.error(message, e);
			errorHandler.sendEmail(message + "<br /><br />", e, false, true);
			e.printStackTrace();
			
			try {
				Thread.sleep(300*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(message, e);
			errorHandler.sendEmail(message + "<br /><br />", e, false, false);
			e.printStackTrace();
			
			try {
				Thread.sleep(300*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		logger.debug("TraceIncident complete.");
		
	}
	
	private static double formatPercent(double percent) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		format.setMaximumFractionDigits(4);
		return new Double(format.format(percent)).doubleValue();
	}
	
	public static OHD getOhd(String ohd_ID) {
		// OHD
		// PASSENGERS
		// ADDRESSES
		// ITINERARIES
		return null;
	}
	
	public static TraceIncident getIncident(String incident_ID) {
		// OHD
		// PASSENGERS
		// ADDRESSES
		// ITINERARIES
		return null;
	}


}
