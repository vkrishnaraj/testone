package com.bagnet.nettracer.cronjob.tracing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.cronjob.tracing.dto.ConsumerDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.cronjob.tracing.dto.Score;
import com.bagnet.nettracer.cronjob.tracing.dto.SettingsDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.UpdateDTO;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.db.TraceOHD;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class TracingConsumer implements Runnable {

	private BlockingQueue<ConsumerDTO> queue = null;
	private SettingsDTO settings = null;
	private Logger logger = null;
	private Session sess = null;
  private Session dirtySess = null;
	private BlockingQueue<UpdateDTO> updateQueue = null;


	public TracingConsumer(BlockingQueue<ConsumerDTO> queue,
			SettingsDTO settings, BlockingQueue<UpdateDTO> updateQueue) {
		this.queue = queue;
		this.settings = settings;
		this.logger = settings.getLogger();
		this.updateQueue  = updateQueue;
	}

	public void run() {
		String message = "";
		while (true) {
			try {
				
				if (sess == null) {
					sess = HibernateWrapper.getSession().openSession();
				}
				
				if (dirtySess == null) {
					dirtySess = HibernateWrapper.getDirtySession().openSession();
				}
				
				ConsumerDTO dto = queue.take();
				
				message = "Exception thrown - TracingConsumer: "
					+ dto.getIncident().getIncident_ID() + " OHD:" + dto.getOhdId();
				
				
				TraceOHD ohd = settings.getOhdCache().loadOhd(dto.getOhdId(),
						dto.getLastUpdated(), sess);
				
				TraceIncident incident = dto.getIncident();
				Score score = Trace.trace(incident, ohd, settings.getRuleSet());
				logger.debug("  OHD: " + dto.getOhdId() + "  Score: "
						+ score.getOverallScore());

				if (score.getOverallScore() > settings.getMinimumScore()
						|| score.isGtsv()) {

					// Does the match already exist in our system?
					double percMatch = formatPercent(score.getOverallScore());

					String matchExistsSQL = "SELECT COUNT(*) as COUNT FROM MATCH_HISTORY WHERE MBR_NUMBER= :incident_ID AND OHD_ID = :ohd_ID AND MATCH_PERCENT = :matchPercent";

					SQLQuery matchExistsQuery = dirtySess.createSQLQuery(matchExistsSQL);
					matchExistsQuery.setString("incident_ID", incident
							.getIncident_ID());
					matchExistsQuery.setString("ohd_ID", ohd.getOHD_ID());
					matchExistsQuery.setDouble("matchPercent", percMatch);
					matchExistsQuery.addScalar("COUNT", Hibernate.INTEGER);
					List<Integer> queryResults = matchExistsQuery.list();
					int queryCount = queryResults.get(0).intValue();

					// If we have not saved this particular match before, then do so
					// now.
					if (queryCount == 0) {
						logger.info("  New Match detected - TraceIncident: "
								+ incident.getIncident_ID() + " OHD: "
								+ ohd.getOHD_ID() + " Score: "
								+ score.getOverallScore());

						Match match = new Match();
						match.setBagnumber(score.getBagNumber());
						match.setMatch_type(TracingConstants.PASSIVE_MATCH_TYPE);
						match.setMatch_percent(formatPercent(score
								.getOverallScore()));
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
								detail.setPercentage(formatPercent(result
										.getPercentMatch()));
								detail.setItem(result.getMatchElement()
										.getConstant());
								details.add(detail);
							}
						}

						match.setDetails(details);
						HibernateUtils.save(match, null);
					}
				}

				if (dto.isLastIncident() == true) {				
					Date lt = ohd.getLastupdated();
					lt.setTime(lt.getTime() + 1000);
					incident.setOhd_lasttraced(lt);
					
					updateQueue.put(new UpdateDTO(incident.getIncident_ID(),lt));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (HibernateException e) {
				if (sess != null) {
					sess.close();
					sess = null;	
				}
				
				if (dirtySess != null) {
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
				if (sess != null) {
					sess.close();
					sess = null;	
				}
				
				if (dirtySess != null) {
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

	private static double formatPercent(double percent) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		format.setMaximumFractionDigits(4);
		return new Double(format.format(percent)).doubleValue();
	}

}
