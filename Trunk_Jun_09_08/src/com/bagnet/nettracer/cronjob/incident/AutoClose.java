package com.bagnet.nettracer.cronjob.incident;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

public class AutoClose {
	
	private static Logger logger = Logger.getLogger(AutoClose.class);
	private String companyCode = null;
	
	public static final String DEFAULT_REMARK = "This Incident was auto-closed due to the amount of time in open status.";
	
	public AutoClose(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * Cron process to auto close old incidents
	 */
	@SuppressWarnings("rawtypes")
	public void autoCloseIncidents() {
		
		Company comp = CompanyBMO.getCompany(companyCode);
		logger.debug("COMPANY LOADED: " + comp.getCompanyCode_ID());
		
		if (comp != null && comp.getVariable() != null) {
			Company_Specific_Variable vars = comp.getVariable();
			logger.debug("VARIABLES LOADED: " + vars.getAuto_close_days_back()
					+ ", " + vars.getAuto_close_ld_code()
					+ ", " + vars.getAuto_close_ld_station()
					+ ", " + vars.getAuto_close_dam_code()
					+ ", " + vars.getAuto_close_dam_station()
					+ ", " + vars.getAuto_close_pil_code()
					+ ", " + vars.getAuto_close_pil_station()
					);
			
			if (vars.getAuto_close_days_back() > 0) {

				logger.debug("RUNNING AUTO-CLOSE: " + vars.getAuto_close_days_back());
				Session session = null;
				try {
					session = HibernateWrapper.getSession().openSession();
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, (-1 * vars.getAuto_close_days_back()));
					String date = DateUtils.formatDate(cal.getTime(),TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null);
					String sql = "select Incident_ID from incident where createdate < '" + date + "' and status_ID != " + TracingConstants.MBR_STATUS_CLOSED;
					
					logger.debug("QUERY PREPARED: " + sql);
					
					SQLQuery query = session.createSQLQuery(sql);

					query.addScalar("Incident_ID", StandardBasicTypes.STRING);
					
					List results = query.list();
					if (results == null) {
						return;
					}
					Station ldStat = StationBMO.getStation(vars.getAuto_close_ld_station());
					Station damStat = StationBMO.getStation(vars.getAuto_close_dam_station());
					Station pilStat = StationBMO.getStation(vars.getAuto_close_pil_station());
					String autoCloseAgent = "ntadmin";                                                                     // PROPERTY???
			 		Agent agent = AdminUtils.getAgentBasedOnUsername(autoCloseAgent, companyCode);
					int successfulCloses = 0;
					
					for (int i = 0; i < results.size(); i++) {
						String inc_id = (String) results.get(i);
						Incident inc = IncidentBMO.getIncidentByID(inc_id, session);
						inc.setStatus(StatusBMO.getStatus(TracingConstants.MBR_STATUS_CLOSED));
						inc.setClosedate(TracerDateTime.getGMTDate());
						if (inc.getItemtype_ID() == TracingConstants.LOST_DELAY) {
							inc.setLoss_code(vars.getAuto_close_ld_code());
							inc.setFaultstation(ldStat);
						} else if (inc.getItemtype_ID() == TracingConstants.DAMAGED_BAG) {
							inc.setLoss_code(vars.getAuto_close_dam_code());
							inc.setFaultstation(damStat);
						} else if (inc.getItemtype_ID() == TracingConstants.MISSING_ARTICLES) {
							inc.setLoss_code(vars.getAuto_close_pil_code());
							inc.setFaultstation(pilStat);
						} else {
							continue;
						}

				 		Remark r = new Remark();

						r.setAgent(agent);
						r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
						String remarkText = PropertyBMO.getValue(PropertyBMO.AUTO_CLOSE_REMARK);
						r.setRemarktext(remarkText != null ? remarkText : DEFAULT_REMARK);                
						r.setIncident(inc);
						r.setRemarktype(TracingConstants.REMARK_CLOSING);
						Set<Remark> remarks = inc.getRemarks();
						
						int remarkLoc = PropertyBMO.getValueAsInt(PropertyBMO.AUTO_CLOSE_REMARK_LOC);
						if (remarkLoc == 1) {
							r.setRemarktype(TracingConstants.REMARK_REGULAR);
						} else if (remarkLoc == 2) {
							Remark r2 = new Remark();
							r2.setAgent(agent);
							r2.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
							r2.setRemarktext(remarkText != null ? remarkText : DEFAULT_REMARK);                
							r2.setIncident(inc);
							r2.setRemarktype(TracingConstants.REMARK_REGULAR);
							remarks.add(r2);
						}
						remarks.add(r);
						
						IncidentBMO iBMO = new IncidentBMO();
						int success = 0;
						try{
							success = iBMO.saveAndAuditIncident(false, inc, agent, session);
						} catch (StaleStateException sse){
							//ignore
						}
						if (success != 1) {
							logger.error("INCIDENT " + inc.getIncident_ID() + " DID NOT SUCCESSFULLY AUTO-CLOSE!");
							continue;
						}
						
						if (inc.getWtFile() != null && inc.getWtFile().getWt_status() != WTStatus.CLOSED) {
							WtqIncidentAction wtq = new WtqCloseAhl();
							wtq.setAgent(agent);
							wtq.setCreatedate(TracerDateTime.getGMTDate());
							wtq.setIncident(inc);
							WorldTracerQueueUtils.createOrReplaceQueue(wtq);
						}

						successfulCloses++;
					}

					logger.info("CLOSED " + successfulCloses + " INCIDENTS WITH AUTO-CLOSE!");
					
				} catch (Exception e) {
					logger.error("AUTO-CLOSE FAILURE: " + e);
				} finally {
					if (session != null) session.close();
				}
			}
		}
	}
	
	/**
	 * Cron process to auto close old ohds at LZ
	 */
	@SuppressWarnings("rawtypes")
	public void autoCloseOHDs() {
		
		Company comp = CompanyBMO.getCompany(companyCode);
		logger.debug("COMPANY LOADED: " + comp.getCompanyCode_ID());
		
		if (comp != null && comp.getVariable() != null) {
			Company_Specific_Variable vars = comp.getVariable();
			logger.debug("VARIABLES LOADED: " + vars.getAuto_close_ohd_days_back());
			
			if (vars.getAuto_close_ohd_days_back() > 0) {

				logger.debug("RUNNING AUTO-CLOSE: " + vars.getAuto_close_ohd_days_back());
				Session session = null;
				try {
					session = HibernateWrapper.getSession().openSession();
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, (-1 * vars.getAuto_close_ohd_days_back()));
					String date = DateUtils.formatDate(cal.getTime(),TracingConstants.DB_DATEFORMAT,null,null);
					String sql = "select OHD_ID from ohd where founddate < '" + date + "' and status_ID != " + TracingConstants.OHD_STATUS_CLOSED +" and holding_station_ID="+vars.getOhd_lz(); 
					
					logger.debug("QUERY PREPARED: " + sql);
					
					SQLQuery query = session.createSQLQuery(sql);

					query.addScalar("OHD_ID", StandardBasicTypes.STRING);
					
					List results = query.list();
					if (results == null) {
						return;
					}
					String autoCloseAgent = "ntadmin";                                                                     // PROPERTY???
			 		Agent agent = AdminUtils.getAgentBasedOnUsername(autoCloseAgent, companyCode);
					int successfulCloses = 0;
					
					for (int i = 0; i < results.size(); i++) {
						String ohd_id = (String) results.get(i);
						OHD ohd = OhdBMO.getOHDByID(ohd_id, session);
						ohd.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_CLOSED));
						ohd.setDisposal_status(StatusBMO.getStatus(TracingConstants.LF_STATUS_SALVAGED));
						ohd.setClose_date(TracerDateTime.getGMTDate());

				 		Remark r = new Remark();
				 		
						r.setAgent(agent);
						r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
						String remarkText = PropertyBMO.getValue(PropertyBMO.AUTO_CLOSE_OHD_REMARK);
						r.setRemarktext(remarkText != null ? remarkText.replace("{autoCloseDays}", String.valueOf(vars.getAuto_close_ohd_days_back())) : DEFAULT_REMARK);                
						r.setOhd(ohd);
						r.setRemarktype(TracingConstants.REMARK_REGULAR);
						@SuppressWarnings("unchecked")
						Set<Remark> remarks = ohd.getRemarks();
						
						remarks.add(r);
						
						OhdBMO oBMO = new OhdBMO();
						boolean success = false;
						success = oBMO.simpleSaveAndAuditOhd(ohd, agent, ohd.getFoundAtStation(), session);
						if (!success) {
							logger.error("OHD " + ohd.getOHD_ID() + " DID NOT SUCCESSFULLY AUTO-CLOSE!");
							continue;
						}
						
						if (ohd.getWtFile() != null && ohd.getWtFile().getWt_status() != WTStatus.CLOSED) {
							WtqOhdAction wtq = new WtqCloseOhd();
							wtq.setAgent(agent);
							wtq.setCreatedate(TracerDateTime.getGMTDate());
							wtq.setOhd(ohd);
							WorldTracerQueueUtils.createOrReplaceQueue(wtq);
						}

						successfulCloses++;
					}

					logger.info("CLOSED " + successfulCloses + " OHDS WITH AUTO-CLOSE!");
					
				} catch (Exception e) {
					logger.error("AUTO-CLOSE FAILURE: " + e);
				} finally {
					if (session != null) session.close();
				}
			}
		}
	}

}
