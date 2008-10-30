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

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
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
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class PassiveTrace implements Runnable {

	public static Logger logger = Logger.getLogger(PassiveTrace.class);
	
	private String incidentId;
	private String companyCode;
	private int daysBack;
	private int daysForward;
	private double minimumScore;
	private RuleSet ruleSet;
	private PassiveTraceErrorHandler errorHandler;

	public static void main(String[] args) {
		PassiveTraceErrorHandler errorHandler = null;
		
		String companyCode = null;
		int threads = 1;
		int seconds = 60;
		int daysBack = 2;
		int daysForward = 15;
		double minimumScore = 0;
		String instanceName = "Not Defined";

		if (args.length == 2) {
			companyCode = args[0];
			instanceName = args[1];
			startPassiveTracing(companyCode, instanceName);
		} else {
			System.out.println("Startup arguments required:");
			System.out.println(" arg[0] = CompanyCode (i.e. 'DA'), arg[1] = InstanceName");
		}
	}

	public static void startPassiveTracing(String companyCode, String instanceName) {

		int threadLimit = 10;
		int seconds = 60;
		double minimumScore = 50;
		int daysForward = Integer.parseInt(PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_DAYS_FORWARD));
		int daysBack = Integer.parseInt(PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_DAYS_BACKWARD));
		
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
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		List<String> incidentList = new ArrayList<String>();
		boolean initialRun = true;
		RuleSet ruleSet = new RuleSet();
		int count = 0;
		ArrayList<Date> hibernateErrorDates = new ArrayList<Date>();
		
		while (true) {
			try {
				Date tmpDate = null;
				// If the incidentList is empty, sleep for a little while.
				if (incidentList.size() == 0) {
					if (threadList.size() == 0) {
	
						// Sleep before next run.
						if (initialRun == true) {
							initialRun = false;
						} else {
							try {
								tmpDate = new Date();
								logger.info("Sleeping for " + seconds + "...");
								Thread.sleep(1000 * seconds);
							} catch (InterruptedException e) {
								// Ignore exception
								logger.debug("ignoring thread interrupted exception", e);
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
	
						logger.info("Starting tracing session... Total Incidents: "
								+ incidentList.size());
					}
	
				} else {
					// Get the next incident in the list and kick off a trace.
					if (threadList.size() < threadLimit) {
						String incidentId = incidentList.remove(0);
						PassiveTrace pt = new PassiveTrace(companyCode, daysBack,
								daysForward, incidentId, minimumScore, ruleSet,
								errorHandler);
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
			}catch (HibernateException e) {

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
			PassiveTraceErrorHandler errorHandler) {
		this.companyCode = companyCode;
		this.incidentId = incidentId;
		this.daysBack = daysBack;
		this.daysForward = daysForward;
		this.minimumScore = minimumScore;
		this.ruleSet = ruleSet;
		this.errorHandler = errorHandler;
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
	
				List<String> ohdList = query.list();
				logger.debug("Total OHDs found: " + ohdList.size());
	
				for (String ohd_ID : ohdList) {
					message = "Exception thrown - Incident: "
							+ incident.getIncident_ID() + " OHD:" + ohd_ID;
					
					OHD ohd = OhdBMO.getOHDByID(ohd_ID, sess);
					sess.setReadOnly(ohd, true);
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
							logger.info("  New Match detected - Incident: "
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
							match.setMbr(incident);
							match.setOhd(ohd);
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
	
	public static Incident getIncident(String incident_ID) {
		// OHD
		// PASSENGERS
		// ADDRESSES
		// ITINERARIES
		return null;
	}


}
