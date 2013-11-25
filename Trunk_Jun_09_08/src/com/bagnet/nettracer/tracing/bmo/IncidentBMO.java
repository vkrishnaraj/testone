/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.type.StandardBasicTypes;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.IncidentControl;
import com.bagnet.nettracer.tracing.db.Incident_Assoc;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Incident_Range;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_BDO;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.TraceIncident;
import com.bagnet.nettracer.tracing.db.audit.Audit_Claim;
import com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.PrecoderResult;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Administrator
 * 
 *         create date - Jul 15, 2004
 */
public class IncidentBMO {
	private static Logger logger = Logger.getLogger(IncidentBMO.class);

	public int insertIncident(boolean checkStaleState, Incident iDTO, String assoc_ID, Agent mod_agent) throws HibernateException, StaleStateException {
		return insertIncident(checkStaleState, iDTO, assoc_ID, mod_agent, false);
	}



	public int insertIncident(boolean checkStaleState, Incident iDTO, String assoc_ID, Agent mod_agent, Session sess) throws HibernateException, StaleStateException {
		return insertIncidentWithSession(checkStaleState, iDTO, assoc_ID, mod_agent, false, sess);
	}
	
	public int insertIncidentWithSession(boolean checkStaleState, Incident iDTO, String assoc_ID, Agent mod_agent, boolean checkClosedStatus, Session sess) throws HibernateException, StaleStateException {
		boolean oldStatusKept = false;
		Transaction t = null;
		try {
		
			Incident oldinc = null;
			String incident_id = iDTO.getIncident_ID();
			boolean isnew = false;
			PrecoderResult precoderResult = null;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				iDTO.setIncident_ID(getIncidentID(iDTO.getStationcreated()));
				precoderResult = SpringUtils.getPrecoder().getFaultStationAndLossCode(iDTO);
				if(precoderResult != null){
					iDTO.setLoss_code(precoderResult.getLossCode());
					iDTO.setFaultstation(precoderResult.getFaultStation());
				}
				
				long versionId = IncidentChecklistBMO.getActiveChecklistVersion(null).getVersion_id();
				iDTO.setChecklist_version(versionId);
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null){
					isnew = true;
				} else {
					//check to see if incident has been updated recently
					if(checkStaleState && iDTO.getLastupdated().before(oldinc.getLastupdated())){
						throw new StaleStateException();
					}
				}
			}
			
		
			t = sess.beginTransaction();
		
			// save incident
			if (iDTO.getIncident_ID() != null) {
				
				IncidentControl myIncidentControl;
				normalizePhoneNumbers(iDTO);
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
				if (isnew) {
					sess.save(iDTO);
				
					myIncidentControl = new IncidentControl();
					myIncidentControl.setAssignedDate(iDTO.getLastupdated());
					myIncidentControl.setIncident(iDTO);
					sess.save(myIncidentControl);

				
				} else {
					if (iDTO.getChecklist_version() == 0) {
						iDTO.setChecklist_version(oldinc.getChecklist_version());
					}
					if (checkClosedStatus) {
						Status oldStatus = oldinc.getStatus();
						iDTO.setStatus(oldStatus);
						iDTO.setClosedate(oldinc.getClosedate());
						oldStatusKept = true;
					}
		
					if (oldinc.getWtFile() != null && iDTO.getWtFile() == null) {
						iDTO.setWtFile(oldinc.getWtFile());
					}
					
					//to persist language setting
					if (oldinc.getLanguage() != null && iDTO.getLanguage() == null) {
						iDTO.setLanguage(oldinc.getLanguage());
					}
		
					
					if (oldinc.getStationassigned().getStation_ID() != iDTO.getStationassigned().getStation_ID()) {
						//get the id of the IncidentControl obj
						myIncidentControl = oldinc.getIncidentControl();
						
						if (myIncidentControl == null) {
							myIncidentControl = new IncidentControl();
							myIncidentControl.setAssignedDate(iDTO.getLastupdated());
							myIncidentControl.setIncident(iDTO);
							sess.save(myIncidentControl);
						} else {
							myIncidentControl.setAssignedDate(TracerDateTime.getGMTDate());
							//sess.saveOrUpdate(myIncidentControl);
							sess.update(myIncidentControl);	
						}
					}

					
					// delete first then insert
					sess.delete(oldinc);

					iDTO = clearIncidentIds(iDTO);
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return 0;
			}
		
			// change the photo names from temppath_ to incident_ID _
		
			if (isnew) {
				// update airtran
				if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()) {
					String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(),
							TracingConstants.DB_DATETIMEFORMAT, null, iDTO.get_TIMEZONE());
		
					SpringUtils.getReservationIntegration().writeCommentToPNR(
							"Baggage Claim (" + iDTO.getIncident_ID() + ") created on " + formateddatetime,
							iDTO.getRecordlocator());
				}
				if(precoderResult != null){
					com.bagnet.nettracer.tracing.bmo.PrecoderBMO.insert(iDTO.getIncident_ID(), precoderResult);
				}
			}
			
			// check to see if we closed the report, if we did, then close all
			// matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				MatchUtils.closeMatches(iDTO.getIncident_ID(), null, true);
			}
		
			// association report
			if (assoc_ID == null || assoc_ID.length() == 0)
				assoc_ID = iDTO.getIncident_ID();
			Incident_Assoc ia = new Incident_Assoc();
			ia.setAssoc_ID(assoc_ID);
			ia.setIncident_ID(iDTO.getIncident_ID());
			ia.setItemtype_ID(iDTO.getItemtype().getItemType_ID());
		
			@SuppressWarnings("rawtypes")
			List list = sess.createCriteria(Incident_Assoc.class).add(Example.create(ia)).list();
		
			if (list == null || list.size() == 0) {
				t = sess.beginTransaction();
				sess.save(ia);
				t.commit();
			}
		
			// check if audit is enabled for this company....
			if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation()
					.getCompany().getVariable().getAudit_lost_delayed() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_damaged() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, mod_agent);
		
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}
		
		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);
		
			if (t != null)
				t.rollback();
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e.toString());
		
			if (t != null)
				t.rollback();
			return 0;
		} finally {
		
			sess.close();
		}
		if (oldStatusKept) {
			return 2;
		}
		return 1;
	}
	
	public int insertItemContents(boolean checkStaleState, List<Item> ilist, Agent mod_agent, Incident iDTO) 
			throws HibernateException, StaleStateException {
		Transaction t = null;
		Session sess = null;
		List<Item_Inventory> newcontents=new ArrayList<Item_Inventory>();
		for(Item i:ilist){
			for(Object o:i.getInventorylist()){
				Item_Inventory iv=(Item_Inventory)o;
				if(iv.getInventory_ID()==0){
					newcontents.add(iv);
				}
				
			}
		}
		try {
			sess=HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			
			// save contents
			for(Item_Inventory iv:newcontents){
				sess.save(iv);
			}

			t.commit();
			sess.flush();
			
			// check if audit is enabled for this company....
			if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation()
					.getCompany().getVariable().getAudit_lost_delayed() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_damaged() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, mod_agent);
		
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}
		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);

			if (t != null)
				t.rollback();
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {
			if(sess!=null){
				sess.close();
			}
		}
		return 1;
	}
	
	public int insertIncident(boolean checkStaleState, Incident iDTO, String assoc_ID, Agent mod_agent, boolean checkClosedStatus)
			throws HibernateException, StaleStateException {
		boolean oldStatusKept = false;
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			Incident oldinc = null;
			String incident_id = iDTO.getIncident_ID();
			boolean isnew = false;
			PrecoderResult precoderResult = null;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				iDTO.setIncident_ID(getIncidentID(iDTO.getStationcreated()));
				precoderResult = SpringUtils.getPrecoder().getFaultStationAndLossCode(iDTO);
				if(precoderResult != null){
					iDTO.setLoss_code(precoderResult.getLossCode());
					iDTO.setFaultstation(precoderResult.getFaultStation());
				}
				
				long versionId = IncidentChecklistBMO.getActiveChecklistVersion(null).getVersion_id();
				iDTO.setChecklist_version(versionId);
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null){
					isnew = true;
				} else {
					//check to see if incident has been updated recently
					if(checkStaleState && iDTO.getLastupdated().before(oldinc.getLastupdated())){
						throw new StaleStateException();
					}
				}
			}		

			t = sess.beginTransaction();

			// save incident
			if (iDTO.getIncident_ID() != null) {
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
		
				IncidentControl myIncidentControl;

				normalizePhoneNumbers(iDTO);
				if (isnew) {
					sess.save(iDTO);
					
					myIncidentControl = new IncidentControl();
					myIncidentControl.setAssignedDate(iDTO.getLastupdated());
					myIncidentControl.setIncident(iDTO);
					sess.save(myIncidentControl);
				}

				else {
					if (iDTO.getChecklist_version() == 0) {
						iDTO.setChecklist_version(oldinc.getChecklist_version());
					}
					if (checkClosedStatus) {
						Status oldStatus = oldinc.getStatus();
						iDTO.setStatus(oldStatus);
						iDTO.setClosedate(oldinc.getClosedate());
						oldStatusKept = true;
					}
					
					if (oldinc.getTracingStarted() == null && iDTO.getTracingStatus() != TracingConstants.INCIDENT_TRACING_STATUS_DEFAULT) {
						iDTO.setTracingStarted(TracerDateTime.getGMTDate());
						iDTO.setTracingAgent(mod_agent);
					} else {
						iDTO.setTracingStarted(oldinc.getTracingStarted());
						iDTO.setTracingAgent(oldinc.getTracingAgent());
					}
					
					if (oldinc.getTracingStatus() != iDTO.getTracingStatus() && iDTO.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_FINAL) {
						iDTO.setTracingComplete(TracerDateTime.getGMTDate());
					} else {
						iDTO.setTracingComplete(oldinc.getTracingComplete());
					}

					if (oldinc.getWtFile() != null && iDTO.getWtFile() == null) {
						iDTO.setWtFile(oldinc.getWtFile());
					}
					
					if (oldinc.getOc_claim_id() != 0 && iDTO.getOc_claim_id() == 0) {
						iDTO.setOc_claim_id(oldinc.getOc_claim_id());
					}

					//to persist language setting
					if (oldinc.getLanguage() != null && iDTO.getLanguage() == null) {
						iDTO.setLanguage(oldinc.getLanguage());
					}
					
					if (oldinc.getPrintedreceipt() != null && iDTO.getPrintedreceipt() == null){
						iDTO.setPrintedreceipt(oldinc.getPrintedreceipt());
					}
					
					// add remarks for issuance item changes
					if (iDTO != null && iDTO.getIssuanceItemIncidents() != null) {
						IssuanceItemBMO.addIssuanceItemRemarks(iDTO, mod_agent);
					}

					// The purpose is to allow a user to identify specific incidents that were assigned to them in a given date range.
					if (oldinc.getStationassigned().getStation_ID() != iDTO.getStationassigned().getStation_ID()) {
						//get the id of the IncidentControl obj
						myIncidentControl = oldinc.getIncidentControl();
						
						if (myIncidentControl == null) {
							myIncidentControl = new IncidentControl();
							myIncidentControl.setAssignedDate(iDTO.getLastupdated());
							myIncidentControl.setIncident(iDTO);
							sess.save(myIncidentControl);
						} else {
							myIncidentControl.setAssignedDate(TracerDateTime.getGMTDate());
							//sess.saveOrUpdate(myIncidentControl);
							sess.update(myIncidentControl);	
						}
					}
					
					// delete first then insert
					sess.delete(oldinc);
					iDTO = clearIncidentIds(iDTO);
					sess.merge(iDTO);
				}
				t.commit();
			} else {
				return 0;
			}
			
			// adjust numbers and statuses of issuance items, being done here so that incident ID is always available
			if (iDTO != null && iDTO.getIssuanceItemIncidents() != null) {
				for (IssuanceItemIncident iItem : iDTO.getIssuanceItemIncidents()) {
					if (iItem.isUpdated()) {
						IssuanceItemBMO.adjustIssuanceItem(iItem);
					}
				}	
			}

			// change the photo names from temppath_ to incident_ID _

			if (isnew) {
				// update airtran
				if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()) {
					String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(),
							TracingConstants.DB_DATETIMEFORMAT, null, iDTO.get_TIMEZONE());

					SpringUtils.getReservationIntegration().writeCommentToPNR(
							"Baggage Claim (" + iDTO.getIncident_ID() + ") created on " + formateddatetime,
							iDTO.getRecordlocator());
				}
				if(precoderResult != null){
					com.bagnet.nettracer.tracing.bmo.PrecoderBMO.insert(iDTO.getIncident_ID(), precoderResult);
				}

			}

			// check to see if we closed the report, if we did, then close all
			// matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				MatchUtils.closeMatches(iDTO.getIncident_ID(), null, true);
			}

			// association report
			if (assoc_ID == null || assoc_ID.length() == 0)
				assoc_ID = iDTO.getIncident_ID();
			Incident_Assoc ia = new Incident_Assoc();
			ia.setAssoc_ID(assoc_ID);
			ia.setIncident_ID(iDTO.getIncident_ID());
			ia.setItemtype_ID(iDTO.getItemtype().getItemType_ID());

			@SuppressWarnings("rawtypes")
			List list = sess.createCriteria(Incident_Assoc.class).add(Example.create(ia)).list();

			if (list == null || list.size() == 0) {
				t = sess.beginTransaction();
				sess.save(ia);
				t.commit();
			}

			// check if audit is enabled for this company....
			if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation()
					.getCompany().getVariable().getAudit_lost_delayed() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_damaged() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent()
							.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, mod_agent);

				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);

			if (t != null)
				t.rollback();
			return -1;
		} catch (StaleStateException sse){
			logger.error("unable to insert into database because incident is stale: " + sse);

			if (t != null)
				t.rollback();
			throw sse;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {

			sess.close();
		}
		if (oldStatusKept) {
			return 2;
		}
		return 1;
	}

	public boolean updateIncidentNoAudit(boolean checkStaleState, boolean updatedLastUpdateTimestamp, Incident iDTO) throws HibernateException, StaleStateException {
		if(checkStaleState && IncidentBMO.isStale(iDTO)){
			throw new StaleStateException();
		}
		
		if(updatedLastUpdateTimestamp){
			iDTO.setLastupdated(TracerDateTime.getGMTDate());
		}
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			t = sess.beginTransaction();

			normalizePhoneNumbers(iDTO);
			sess.saveOrUpdate(iDTO);
			t.commit();

		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);
			e.printStackTrace();
			if (t != null)
				t.rollback();
			return false;
		} finally {

			sess.close();
		}
		return true;
	}

	public int updateRemarksOnly(String incident_id, Set<Remark> remarks, Agent mod_agent) throws HibernateException {
		return updateRemarksAndLossCodeOnly(incident_id, remarks, mod_agent, -99);
	}

	public int updateRemarksAndLossCodeOnly(String incident_id, Set<Remark> remarks, Agent mod_agent, int losscode) throws HibernateException {
		//do not check for stale states on remark only saves
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			Incident oldinc = null;
			if (incident_id == null || incident_id.length() <= 0) {
				return -1;
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null)
					return -1;
			}

			t = sess.beginTransaction();
			// save incident
			if (oldinc.getIncident_ID() != null) {
				oldinc.setLastupdated(TracerDateTime.getGMTDate());

				if (remarks != null) {
					oldinc.setRemarks(remarks);
					for (Iterator<Remark> i = oldinc.getRemarks().iterator(); i.hasNext();) {
						Remark rm = (Remark) i.next();
						rm.setIncident(oldinc);
					}
				} else {
					return -1;
				}
				
				if (losscode > 0) {
					oldinc.setLoss_code(losscode);
				}
				
				sess.saveOrUpdate(oldinc);
				t.commit();
			} else {
				return 0;
			}

			// check if audit is enabled for this company....
			if ((oldinc.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && oldinc.getAgent().getStation()
					.getCompany().getVariable().getAudit_lost_delayed() == 1)
					|| (oldinc.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && oldinc.getAgent()
							.getStation().getCompany().getVariable().getAudit_damaged() == 1)
					|| (oldinc.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && oldinc.getAgent()
							.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(oldinc, mod_agent);

				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);

			if (t != null)
				t.rollback();
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {

			sess.close();
		}
		return 1;
	}
	
	
	public int saveAndAuditIncident(boolean checkStaleState, Incident incident, Agent mod_agent, Session sess) throws HibernateException, StaleStateException {
		if(checkStaleState && IncidentBMO.isStale(incident)){
			throw new StaleStateException();
		}
		
		boolean sessionNull = (sess == null);
		
		Transaction t = null;
		
		if (sessionNull) {
			sess = HibernateWrapper.getSession().openSession();
		}
		
		try {
			incident.setLastupdated(TracerDateTime.getGMTDate());
			
			Audit_Incident audit_dto = null;
			if ((incident.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && incident.getAgent().getStation()
					.getCompany().getVariable().getAudit_lost_delayed() == 1)
					|| (incident.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && incident.getAgent()
							.getStation().getCompany().getVariable().getAudit_damaged() == 1)
					|| (incident.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && incident.getAgent()
							.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
				audit_dto = AuditIncidentUtils.getAuditIncident(incident, mod_agent);
			}

			t = sess.beginTransaction();
			sess.update(incident);
			if (audit_dto != null) {
				sess.save(audit_dto);
			}
			t.commit();

		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);

			if (t != null)
				t.rollback();
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {
			if (sess != null && sessionNull) {
				sess.close();
			}
		}
		return 1;
	}

	/**
	 * This is to be used solely by the tag number trace feature that may occur
	 * when an agent selects to search by bag tag number from the Lost/Delay
	 * prepopulation screen.
	 * 
	 * @param tagNumber
	 * @return
	 */
	public static List<Incident> queryLDIncidentsForTagTrace(String tagNumber) {

		if (tagNumber == null || tagNumber.trim().length() == 0) {
			return null;
		}

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer queryString = new StringBuffer();
			queryString.append("select incident from com.bagnet.nettracer.tracing.db.Incident incident ");
			queryString.append(" left outer join incident.claimchecks claimcheck ");
			queryString.append(" left outer join incident.itemlist item ");
			queryString.append(" where ");
			queryString.append(" incident.status.status_ID <> :closed");
			queryString.append(" and incident.itemtype.itemType_ID = :lostdelay ");

			String fullTag = null;
			String basicTag = null;

			try {
				fullTag = LookupAirlineCodes.getFullBagTag(tagNumber);
			} catch (BagtagException e) {
				// Ignore
			}
		
			try {
				basicTag = LookupAirlineCodes.getTwoCharacterBagTag(tagNumber);
			} catch (BagtagException e) {
				// Ignore
			}
			

			if (fullTag != null && basicTag != null) {
				queryString.append(" and ((item.claimchecknum like :fullTag");
				queryString.append(" or claimcheck.claimchecknum like :fullTag) ");
				queryString.append(" or (item.claimchecknum like :basicTag");
				queryString.append(" or claimcheck.claimchecknum like :basicTag)) ");
			} else if (fullTag != null) {
				queryString.append(" and (item.claimchecknum like :fullTag");
				queryString.append(" or claimcheck.claimchecknum like :fullTag) ");
			} else if (basicTag != null) {
				queryString.append(" and (item.claimchecknum like :basicTag");
				queryString.append(" or claimcheck.claimchecknum like :basicTag) ");
			}

			Query q = sess.createQuery(queryString.toString());

			q.setInteger("closed", TracingConstants.MBR_STATUS_CLOSED);
			q.setInteger("lostdelay", TracingConstants.LOST_DELAY);

			if (basicTag != null) {
				q.setParameter("basicTag", basicTag);
			}
			if (fullTag != null) {
				q.setParameter("fullTag", fullTag);
			}

			@SuppressWarnings("unchecked")
			List<Incident> list = (List<Incident>) q.list();

			if (list.size() == 0) {
				// No matching incidents found
				return null;
			}

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				sess.close();
			} catch (Exception e) {
				logger.error("unable to close connection: " + e);
			}
		}

	}

	public static Incident getIncidentByID(String incident_ID, Session sess) {
		if (incident_ID == null || incident_ID.length() == 0) return null;
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			return (Incident) sess.load(Incident.class, incident_ID);
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	public Incident findIncidentByID(String incident_ID) {
		return getIncidentByID(incident_ID, null);
	}

	public TraceIncident findTraceIncidentByID(String incident_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			return (TraceIncident) sess.load(TraceIncident.class, incident_ID);
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	/**
	 * find passenger read only information
	 * 
	 * @param incident_ID
	 * @param name
	 * @return @throws HibernateException
	 */
	public Incident findIncidentForPVO(String incident_ID, String name) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();

		try {
			if (name == null || name.length() == 0)
				return null;

			Query q = sess
					.createQuery("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident join incident.passengers passenger"
							+ " where incident.incident_ID= :incident_ID and (passenger.lastname like :name or passenger.firstname like :name)");
			q.setString("incident_ID", incident_ID);
			q.setString("name", name);
			@SuppressWarnings("rawtypes")
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident: " + incident_ID);
				return null;
			}
			Incident iDTO = (Incident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * find incidents matching freq flyer number
	 * 
	 * @param freq_flyer
	 * @return @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<Incident> findIncidentForPVOFreqFlyer(String freq_flyer) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();

		try {

			Query q = sess
					.createQuery("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident join incident.passengers passenger"
							+ " where passenger.membership.membershipnum = :freqFlyer");
			q.setString("freqFlyer", freq_flyer);
			List<Incident> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident with freq flyer num: " + freq_flyer);
				return null;
			}

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * find incidents matching record locator
	 * 
	 * @param record_locator
	 * @return @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<Incident> findIncidentForPVORecordLocator(String record_locator) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();

		try {

			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.DATE, -30);
			Date incCutoff = c.getTime();
			Query q = sess
					.createQuery("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident"
							+ " where incident.recordlocator = :record and incident.createdate > :cutoff");
			q.setString("record", record_locator);
			q.setDate("cutoff", incCutoff);
			List<Incident> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident with record locator: " + record_locator);
				return null;
			}

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * find incidents matching phone numbers
	 * 
	 * @param phone_number
	 * @return @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<Incident> findIncidentForPVOPhoneNumber(String phone_number) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();

		try {

			Query q = sess
					.createQuery("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident"
							+ " join incident.passengers passenger join passenger.addresses address"
							+ " where address.homephone_norm like :searchnum or "
							+		 "address.workphone_norm like :searchnum or "
							+		 "address.mobile_norm like :searchnum or "
							+		 "address.pager_norm like :searchnum or "
							+		 "address.altphone_norm like :searchnum "
							+ " order by incident.createdate desc");
			q.setMaxResults(5);
			q.setString("searchnum", phone_number);
			List<Incident> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident with phone number: " + phone_number);
				return null;
			}

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * find address objects for active incidents
	 * 
	 * @return @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findActiveIncidentsPhones() throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();

		try {
			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.DATE, -30);
			Date incCutoff = c.getTime();
			SQLQuery q = sess
					.createSQLQuery("select a.homephone_norm h, a.workphone_norm w, a.mobile_norm m, a.pager_norm pg, a.altphone_norm ap" +
							" from incident i, address a, passenger p where i.status_id = 12 and i.createdate > :cutoff and " +
							"p.incident_ID = i.Incident_ID and a.passenger_ID = p.Passenger_ID");
			q.setDate("cutoff", incCutoff);
			q.addScalar("h", StandardBasicTypes.STRING);
			q.addScalar("w", StandardBasicTypes.STRING);
			q.addScalar("m", StandardBasicTypes.STRING);
			q.addScalar("pg", StandardBasicTypes.STRING);
			q.addScalar("ap", StandardBasicTypes.STRING);
			List<Object[]> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to retrieve active numbers!");
				return null;
			}

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * @param hours
	 *            the number of hours old the incident must be to move to wt
	 * @param companyCode
	 *            company whose incidents are to be moved to wt
	 * @return list of Incidents that need to be moved to world tracer
	 */
	@SuppressWarnings("unchecked")
	public List<Incident> findMoveToWtInc(int hours, String companyCode) {
		if (hours <= 0) {
			return null;
		}
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0 - hours));
		Date incCutoff = c.getTime();

		String queryString = "select inc from com.bagnet.nettracer.tracing.db.Incident inc where "
				+ " inc.wtFile is null "
				+ // no worldtracer file already
				// " and inc.incident_ID not in (select q.incident.incident_ID from WtqIncidentAction q where q.status = :qStatus)"
				// +
				" and ((inc.createdate < :incCutoff) or (inc.createdate = :incCutoff and inc.createtime < :incTimeCutoff))  "
				+ // old enough
				" and inc.status.status_ID = :status "
				+ // only open
				" and inc.stationassigned.company.companyCode_ID = :companyCode "
				+ " and inc.itemtype.itemType_ID = :itemType " + " order by inc.createdate asc ";

		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Query q = sess.createQuery(queryString);
			q.setDate("incCutoff", incCutoff);
			q.setTime("incTimeCutoff", incCutoff);
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			q.setParameter("companyCode", companyCode);
			// q.setParameter("qStatus", WtqStatus.PENDING);
			q.setParameter("itemType", TracingConstants.LOST_DELAY);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to get move to WT Incident list: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List findIncident(SearchIncident_DTO siDTO, Agent user, int rowsperpage, int currpage, boolean iscount)
			throws HibernateException {
		return findIncident(siDTO, user, rowsperpage, currpage, iscount, false);
	}

	@SuppressWarnings("rawtypes")
	public List findIncident(SearchIncident_DTO siDTO, Agent user, int rowsperpage, int currpage, boolean iscount,
			boolean dirtyRead) throws HibernateException {
		return findIncident(siDTO, user, rowsperpage, currpage, iscount, false, null);
	}

	@SuppressWarnings("rawtypes")
	public List findIncident(SearchIncident_DTO siDTO, Agent user, int rowsperpage, int currpage, boolean iscount,
			boolean dirtyRead, String sort) throws HibernateException {
		Session sess = null;

		if (dirtyRead) {
			sess = HibernateWrapper.getDirtySession().openSession();
		} else {
			sess = HibernateWrapper.getSession().openSession();
		}
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			if (iscount)
				s.append("select count(incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident ");
			else
				s.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident ");

			boolean tagPresent = false;
			if (!siDTO.getClaimchecknum().isEmpty() || siDTO.getExpediteTagNum() != null && !siDTO.getExpediteTagNum().isEmpty() 
					||  SortParam.OHD_POSITION.getParamString().equalsIgnoreCase(sort) || SortParam.OHD_POSITIONREV.getParamString().equalsIgnoreCase(sort)) {
				s.append(" left outer join incident.itemlist item ");
				tagPresent = true;
			}
			
			if (siDTO.getClaimchecknum().length() > 0) {
				s.append(" left outer join incident.claimchecks claimcheck ");
			}
			if (siDTO.getAirline().length() > 0 || siDTO.getFlightnum().length() > 0)
				s.append(" join incident.itinerary itinerary ");
			
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0 || siDTO.getCompanycode_ID().length() > 0
					|| siDTO.getMembershipnum().length() > 0)
				s.append(" join incident.passengers passenger ");
			
			//add the join clause here for station assignment datetime
			if (siDTO.getS_station_assignment_time().length() > 0 
					|| siDTO.getAssigned2StationWithin24hrs() == 1) {
				s.append(" join incident.incidentControl incidentcontrol");
			}

			s.append(" where 1=1 ");

			if (siDTO.getIncident_ID().length() > 0) {
				s.append(" and (incident.incident_ID like :incident_ID ");

				if (siDTO.getWt_id()!=null && siDTO.getWt_id().length() > 0) {

					if (siDTO.isWtConditionOr()) {
						s.append(" or incident.wtFile.wt_id like :wt_id )");
					} else {
						s.append(" and incident.wtFile.wt_id like :wt_id )");
					}
				} else {
					s.append(") ");
				}
			} else if (siDTO.getWt_id()!=null && siDTO.getWt_id().length() > 0) {
				s.append(" and incident.wtFile.wt_id like :wt_id ");
			}
			
			if (siDTO.getExpediteTagNum() != null && !siDTO.getExpediteTagNum().isEmpty()) {
				s.append("and item.expediteTagNum like :expediteTagNum ");
			}

			if (siDTO.getTicketnumber().length() > 0)
				s.append("and incident.ticketnumber like :ticketnumber ");

			if (siDTO.getItemType_ID() > 0) {
				s.append(" and incident.itemtype.itemType_ID = :itemType_ID ");
			}

			if (siDTO.getRecordlocator() != null && siDTO.getRecordlocator().trim().length() > 0) {
				s.append(" and incident.recordlocator like :recordlocator");
			}

			if (siDTO.getAgent().length() > 0)
				s.append(" and incident.agent.username like :agent ");

			if (siDTO.getStatus_ID() > 0) {
				s.append(" and incident.status.status_ID = :status_ID");
			} else if (siDTO.getStatus_IDs() != null && siDTO.getStatus_IDs().length > 0) {
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0; i < siDTO.getStatus_IDs().length; i++) {
					if (i > 0)
						sb.append(",");
					sb.append(":status_ID" + i);
				}
				sb.append(")");
				s.append(" and incident.status.status_ID in " + sb.toString());
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)

			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(siDTO.getS_createtime(), siDTO.getE_createtime(), tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

				} else {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
				}
			}
			
			// regarding station assignment date
			Date sAssignedDate = null, eAssignedDate = null;
			Date sAssignedDate1 = null, eAssignedDate1 = null; // add one for time zone
			ArrayList assignedDateList = IncidentUtils.calculateDateDiff(siDTO.getS_station_assignment_time(), 
											siDTO.getE_station_assignment_time(), tz, user);
			if (assignedDateList == null) {
				return null;
			}
			sAssignedDate = (Date) assignedDateList.get(0);
			sAssignedDate1 = (Date) assignedDateList.get(1);
			eAssignedDate = (Date) assignedDateList.get(2);
			eAssignedDate1 = (Date) assignedDateList.get(3);
			
			if (sAssignedDate != null) {
				if (eAssignedDate != null && sAssignedDate != eAssignedDate) {
					s.append(" and (incidentcontrol.assignedDate= :startassigneddate )"
							+ " or (incidentcontrol.assignedDate= :endassigneddate1 )"
							+ " or (incidentcontrol.assignedDate > :startassigneddate and incidentcontrol.assignedDate <= :endassigneddate)");

				} else {
					s.append(" and (incidentcontrol.assignedDate= :startassigneddate )"
							+ " or (incidentcontrol.assignedDate= :startassigneddate1)");
				}
			} else if (siDTO.getAssigned2StationWithin24hrs() == 1) {
				s.append(" and incidentcontrol.assignedDate >= :startassigneddate ");		
			}
			

			if (siDTO.getCompanycreated_ID().length() > 0)
				s.append(" and incident.stationcreated.company.companyCode_ID = :companycreated_ID");
			if (siDTO.getStationcreated_ID() > 0)
				s.append(" and incident.stationcreated.station_ID = :stationcreated_ID");
			if (siDTO.getStationassigned_ID() > 0)
				s.append(" and incident.stationassigned.station_ID = :stationassigned_ID");
			if (siDTO.getAgentassigned() != null && siDTO.getAgentassigned().trim().length() > 0)
				s.append(" and incident.agentassigned.username like :agentassigned");

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (passenger.membership.membershipnum like :membershipnum");
				s.append(" and passenger.membership.companycode_ID like :companyCode_ID)");
			}
			
			intelligentSearchProcessing(siDTO, s, tagPresent);
			

			if (siDTO.getAirline().length() > 0) {
				s.append(" and itinerary.airline like :airline");
			}
			if (siDTO.getFlightnum().length() > 0) {
				s.append(" and itinerary.flightnum like :flightnum");
			}

			if (siDTO.getNoAssignedAgent() == 1) {
				s.append(" and incident.agentassigned is null");
			}

			//order by
			if (!iscount) {
				s.append(" order by ");
				if(siDTO.isDjReport()){
					s.append("incident.itemtype.itemType_ID asc, ");
				}
				if (SortParam.OHD_POSITION.getParamString().equalsIgnoreCase(sort)) {
					s.append("item.posId desc");
				} else if (SortParam.OHD_POSITIONREV.getParamString().equalsIgnoreCase(sort)) {
					s.append("item.posId asc");
				} else if (siDTO.getRecordlocator() != null && siDTO.getRecordlocator().trim().length() > 0) {
					s.append("incident.createdate desc, incident.createtime desc");
				} else {
					s.append("incident.incident_ID");
				}
			}

			q = sess.createQuery(s.toString());
			q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (siDTO.getIncident_ID().length() > 0)
				q.setString("incident_ID", siDTO.getIncident_ID());
			
			if (siDTO.getWt_id()!=null && siDTO.getWt_id().length() > 0)
				q.setString("wt_id", siDTO.getWt_id());

			if (siDTO.getItemType_ID() > 0) {
				q.setInteger("itemType_ID", siDTO.getItemType_ID());
			}
			if (siDTO.getRecordlocator() != null && siDTO.getRecordlocator().trim().length() > 0) {
				q.setString("recordlocator", siDTO.getRecordlocator());
			}
			if (siDTO.getStatus_ID() > 0) {
				q.setInteger("status_ID", siDTO.getStatus_ID());
			} else if (siDTO.getStatus_IDs() != null && siDTO.getStatus_IDs().length > 0) {

				for (int i = 0; i < siDTO.getStatus_IDs().length; i++) {
					q.setInteger("status_ID" + i, siDTO.getStatus_IDs()[i]);
				}

			}

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);

				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}
			
			if (siDTO.getExpediteTagNum() != null && !siDTO.getExpediteTagNum().isEmpty()) {
				q.setString("expediteTagNum", siDTO.getExpediteTagNum());
			}
			
			if (siDTO.getClaimchecknum()!=null && siDTO.getClaimchecknum().length()>0) {
				String tag=siDTO.getClaimchecknum();
				/*
		    	 * Checking for UTB tag - When saving UTB as a Incident_Claimcheck, it only needs to check against the claimCheckNum field	
		    	 */
				if(tag!=null && !tag.contains("%")){
					boolean utb=false;
			    	if(tag.length()>3 &&tag.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
		    			q.setString("keyUTB", tag.substring(0, 3));
		    			q.setString("keyUTBList", tag.substring(3));
		    			utb=true;
			    	}
	    			if (tag.length() == 8 && !utb) {
	    				String key = tag.substring(0, 2);
		    			q.setString("key8" + key, key);
		    			q.setString("key8" + key + "List", tag.substring(2));
		    		}
	    			if (tag.length() == 9 && !utb) {
	
	    				String key = tag.substring(0, 3);
		    			q.setString("key9" + key, key);
		    			q.setString("key9" + key + "List", tag.substring(3));
		    		}
	    			if (tag.length() == 10 && !utb) {
	    				String key = tag.substring(0, 4);
		    			q.setString("key10" + key + "One", key.substring(0, 1));
		    			q.setString("key10" + key + "Two", key.substring(1,4));
		    			q.setString("key10" + key + "List", tag.substring(4));
		    		}
	    			
	    			
				} else {
					if (tag != null && tag.length() > 0) {
						q.setString("claimchecknum", siDTO.getClaimchecknum().trim());
					}
					if(siDTO.getClaimchecknum2() != null && siDTO.getClaimchecknum2().length() > 0) {
						q.setString("claimchecknum2", siDTO.getClaimchecknum2().trim());
					}
				}
    		}
			
			// station assignment date related
			if (sAssignedDate != null) {
				if (eAssignedDate != null && sAssignedDate != eAssignedDate) {
					q.setDate("startassigneddate", sAssignedDate);
					q.setDate("endassigneddate", eAssignedDate);
					q.setDate("endassigneddate1", eAssignedDate1);

				} else {
					q.setDate("startassigneddate", sAssignedDate);
					q.setDate("startassigneddate1", sAssignedDate1);
				}
			} else if (siDTO.getAssigned2StationWithin24hrs() == 1) {
				// figure out the date we need here
				//Date myLast24HoursFromNowDate = TracerDateTime.getGMTDate();
				//myLast24HoursFromNowDate.setTime(myLast24HoursFromNowDate.getTime() - Timer.ONE_HOUR * 24);  
				//q.setDate("startassigneddate", myLast24HoursFromNowDate);
				
				int numberOfHoursBack =24;
				try {
					numberOfHoursBack = PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_NT_COMPANY_TIME_RANGE_WITHIN_LAST);
				} catch (Exception e) {
					logger.error("unable to retrieve value from properties table.");
				}
				Date myHoursBackFromNowDate = TracerDateTime.getGMTDate();
				myHoursBackFromNowDate.setTime(myHoursBackFromNowDate.getTime() - 60*60*1000*numberOfHoursBack); 
				q.setTimestamp("startassigneddate", myHoursBackFromNowDate);
			}
			
			if (siDTO.getAssigned2StationWithin24hrs() == 1) {
				
			}
		

			if (siDTO.getCompanycreated_ID().length() > 0)
				q.setString("companycreated_ID", siDTO.getCompanycreated_ID());
			if (siDTO.getStationcreated_ID() > 0)
				q.setInteger("stationcreated_ID", siDTO.getStationcreated_ID());
			if (siDTO.getStationassigned_ID() > 0)
				q.setInteger("stationassigned_ID", siDTO.getStationassigned_ID());
			if (siDTO.getAgentassigned() != null && siDTO.getAgentassigned().trim().length() > 0)
				q.setString("agentassigned", siDTO.getAgentassigned());

			if (siDTO.getTicketnumber() != null & !siDTO.getTicketnumber().equals(""))
				q.setString("ticketnumber", siDTO.getTicketnumber());

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {

				String a = siDTO.getFirstname().length() == 0 ? "%" : siDTO.getFirstname();
				String b = siDTO.getMiddlename().length() == 0 ? "%" : siDTO.getMiddlename();
				String c = siDTO.getLastname().length() == 0 ? "%" : siDTO.getLastname();

				q.setString("firstname", a);
				q.setString("middlename", b);
				q.setString("lastname", c);
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getAirline().length() > 0) {
				q.setString("airline", siDTO.getAirline().toUpperCase());
			}
			if (siDTO.getFlightnum().length() > 0) {
				q.setString("flightnum", siDTO.getFlightnum().toUpperCase());

			}

			if (siDTO.getAgent().length() > 0)
				q.setString("agent", siDTO.getAgent());

			List results = q.list();
			return results;
		} catch (Exception e) {
			logger.error("unable to retrieve incident in findIncident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	private void intelligentSearchProcessing(SearchIncident_DTO siDTO, StringBuffer s, boolean tagPresent){ //,HashMap<String, ArrayList<String>> eightDig,HashMap<String, ArrayList<String>> nineDig, HashMap<String, ArrayList<String>> tenDig) {
		
		String tag=siDTO.getClaimchecknum();
    	if (tag != null && tag.length() > 0) {
    		if(!tag.contains("%")){
    		String itemSelect = "";
	    		/*
		    	 * Checking for UTB tag - When saving UTB as a Incident_Claimcheck, it only needs to check against the claimCheckNum field	
		    	 */
    			if(tag!=null && tag.length()>3 && tag.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
    				itemSelect = " and ((claimcheck.claimchecknum_ticketingcode = :keyUTB and claimcheck.claimchecknum_bagnumber = :keyUTBList))";
    			}else if (tag != null && tag.length() == 8) {
    				String key = tag.substring(0, 2);
    				itemSelect = " and ((item.claimchecknum_carriercode = :key8" + key + " and item.claimchecknum_bagnumber = :key8" + key + "List) or (claimcheck.claimchecknum_carriercode = :key8" + key + " and claimcheck.claimchecknum_bagnumber = :key8" + key + "List))";
    			} else if (tag != null && tag.length() == 9) {
    				String key = tag.substring(0, 3);
    				itemSelect = " and ((item.claimchecknum_ticketingcode = :key9" + key + " and item.claimchecknum_bagnumber = :key9" + key + "List) or (claimcheck.claimchecknum_ticketingcode = :key9" + key + " and claimcheck.claimchecknum_bagnumber = :key9" + key + "List))";
    			} else if (tag != null && tag.length() == 10) {
    				String key = tag.substring(0, 4);
    				itemSelect = " and (((item.claimchecknum_leading = :key10" + key + "One or item.claimchecknum_leading is null) "
        					+ "and (item.claimchecknum_ticketingcode = :key10" + key + "Two and item.claimchecknum_bagnumber = :key10" + key + "List)) or "
        					+ "((claimcheck.claimchecknum_leading = :key10" + key + "One or claimcheck.claimchecknum_leading is null) "
        					+ "and (claimcheck.claimchecknum_ticketingcode = :key10" + key + "Two and claimcheck.claimchecknum_bagnumber = :key10" + key + "List))) ";
    			}
    		s.append(itemSelect );
    		} else {

    			int searchType = siDTO.getIntelligentTagSearchType();
    			if (siDTO.isIntelligentTagSearch() && tagPresent && searchType == 0) {
    				Pattern pattern = Pattern.compile(LookupAirlineCodes.PATTERN_10_DIGIT_BAG_TAG);
    				if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
    					siDTO.setIntelligentTagSearchType(10);
    				} else {
    					pattern = Pattern.compile(LookupAirlineCodes.PATTERN_9_DIGIT_BAG_TAG);
    					if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
    						siDTO.setIntelligentTagSearchType(9);
    					} else {
    						pattern = Pattern.compile(LookupAirlineCodes.PATTERN_8_CHAR_BAG_TAG);
    						if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
    							siDTO.setIntelligentTagSearchType(8);
    						}
    					}
    				}
    			}
    			
    			if (siDTO.isIntelligentTagSearch() && searchType > 0) {
    				String nineDigitWildcardTag = null;
    				String genericTag = null;
    				
    				String claimcheck = siDTO.getClaimchecknum().trim();

    				if (claimcheck.indexOf("%") == -1) {
    					if (searchType == 10) {
    						nineDigitWildcardTag = "%" + claimcheck.substring(1);
    						try {
    							genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
    						} catch (BagtagException e) {
    							// Ignore
    							logger.error("Unable to convert bag tag: " + claimcheck);
    							genericTag = claimcheck;
    						}
    					} else if (searchType == 9) {
    						nineDigitWildcardTag = "%" + claimcheck;
    						try {
    							genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
    						} catch (BagtagException e) {
    							// Ignore
    							logger.error("Unable to convert bag tag: " + claimcheck);
    							genericTag = claimcheck;
    						}
    					} else if (searchType == 8) {
    						genericTag = claimcheck;
    						try {
    							nineDigitWildcardTag = "%" + (LookupAirlineCodes.getFullBagTag(claimcheck)).substring(1);
    						} catch (BagtagException e) {
    							// Ignore
    							logger.error("Unable to convert bag tag: " + claimcheck);
    							nineDigitWildcardTag = claimcheck;
    						}
    					}

    					siDTO.setClaimchecknum(nineDigitWildcardTag);
    					siDTO.setClaimchecknum2(genericTag);
    				}
    				s.append(" and (item.claimchecknum like :claimchecknum or item.claimchecknum like :claimchecknum2");
    				s.append(" or claimcheck.claimchecknum like :claimchecknum or claimcheck.claimchecknum like :claimchecknum2)");

    			} else if (siDTO.getClaimchecknum().length() > 0) {
    				s.append(" and (item.claimchecknum like :claimchecknum");
    				s.append(" or claimcheck.claimchecknum like :claimchecknum)");
    			}
    		}
		}
		
	}
	
	private void intelligentSearchProcessing(SearchIncidentForm siDTO, StringBuffer s, boolean tagPresent) {

		int searchType = siDTO.getIntelligentTagSearchType();
		if (siDTO.isIntelligentTagSearch() && tagPresent && searchType == 0) {
			Pattern pattern = Pattern.compile(LookupAirlineCodes.PATTERN_10_DIGIT_BAG_TAG);
			if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
				siDTO.setIntelligentTagSearchType(10);
			} else {
				pattern = Pattern.compile(LookupAirlineCodes.PATTERN_9_DIGIT_BAG_TAG);
				if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
					siDTO.setIntelligentTagSearchType(9);
				} else {
					pattern = Pattern.compile(LookupAirlineCodes.PATTERN_8_CHAR_BAG_TAG);
					if (pattern.matcher(siDTO.getClaimchecknum()).find()) {
						siDTO.setIntelligentTagSearchType(8);
					}
				}
			}
		}
		
		
			
		if (siDTO.isIntelligentTagSearch() && searchType > 0) {
			String nineDigitWildcardTag = null;
			String genericTag = null;
			
			String claimcheck = siDTO.getClaimchecknum().trim();

			if (claimcheck.indexOf("%") == -1) {
				if (searchType == 10) {
					nineDigitWildcardTag = "%" + claimcheck.substring(1);
					try {
						genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						genericTag = claimcheck;
					}
				} else if (searchType == 9) {
					nineDigitWildcardTag = "%" + claimcheck;
					try {
						genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						genericTag = claimcheck;
					}
				} else if (searchType == 8) {
					genericTag = claimcheck;
					try {
						nineDigitWildcardTag = "%" + (LookupAirlineCodes.getFullBagTag(claimcheck)).substring(1);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						nineDigitWildcardTag = claimcheck;
					}
				}

				siDTO.setClaimchecknum(nineDigitWildcardTag);
				siDTO.setClaimchecknum2(genericTag);
			}
			s.append(" and (item.claimchecknum like :claimchecknum or item.claimchecknum like :claimchecknum2");
			s.append(" or claimcheck.claimchecknum like :claimchecknum or claimcheck.claimchecknum like :claimchecknum2)");

		} else if (siDTO.getClaimchecknum().length() > 0) {
			s.append(" and (item.claimchecknum like :claimchecknum");
			s.append(" or claimcheck.claimchecknum like :claimchecknum)");
		}
	}

	public Incident findIncidentByWtId(String wt_id) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident where incident.wtFile.wt_id= :wt_id");
			q.setParameter("wt_id", wt_id);
			@SuppressWarnings("unchecked")
			List<Incident> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident with wt_id: " + wt_id);
				return null;
			}
			Incident iDTO = (Incident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	/**
	 * automatically generate 10 character incident number 3 alpha/7 numeric
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getIncidentID(Station stationcreated) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		// get highest num from Incident_Range table
		try {

			stationcreated = findStationByID(stationcreated.getStation_ID());

			if (stationcreated == null) {
				logger.error("invalid station");
				return null;
			}
			String stationcode = stationcreated.getStationcode();
			String companycode = stationcreated.getCompany().getCompanyCode_ID();

			// insert into the incident_range with this companycode
			Transaction t = sess.beginTransaction();
			Incident_Range ir = new Incident_Range();
			ir.setCompanycode_ID(companycode);
			sess.save(ir);
			t.commit();
			long newnum = ir.getCurrent_num();
			if (newnum == 0) {
				logger.error("unable to create a new incident number");
				return null;
			}

			// get the last record with this companycode_ID;
			Query q = null;
			List<Incident> list = null;
			
			StringBuffer s = new StringBuffer();
			s.append(stationcode);
			s.append(companycode);
			String num = Long.toString(newnum);
			// padd new number to # digits that total length will equal to
			// tracingconstants.incident_len
			for (int i = 0; i < TracingConstants.INCIDENT_LEN - companycode.length() - stationcode.length()
					- num.length(); i++) {
				s.append("0");
			}
			s.append(num);
			String incident_num = s.toString().toUpperCase();

			// making sure there isn't another incident id in the table already
			// this would have never happen in production
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident "
					+ "where incident.incident_ID = :incident_ID");

			q.setString("incident_ID", incident_num);

			list = q.list();
			if (list != null && list.size() > 0) {
				return getIncidentID(stationcreated);
			} else {
				return incident_num;
			}

		} catch (Exception e) {
			logger.error("unable to create a new incident number: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public Station findStationByID(int station_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			Query q = sess.createQuery("select station from com.bagnet.nettracer.tracing.db.Station station "
					+ "where station.station_ID= :station_ID");
			q.setParameter("station_ID", new Integer(station_ID));

			@SuppressWarnings("unchecked")
			List<Station> list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find station: " + station_ID);
				return null;
			}
			Station station = (Station) list.get(0);

			return station;
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getAssocReports(String incident_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident_Assoc a "
					+ "where a.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List<Incident_Assoc> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find association: " + incident_ID);
				return null;
			}
			Incident_Assoc ia = (Incident_Assoc) list.get(0);
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident_Assoc a "
					+ "where a.assoc_ID= :assoc_ID");
			q.setParameter("assoc_ID", ia.getAssoc_ID());
			list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find association: " + ia.getAssoc_ID());
				return null;
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public List customQuery(SearchIncidentForm siDTO, Agent user, int rowsperpage, int currpage, boolean iscount,
			String searchType) throws HibernateException {
		return customQuery(siDTO, user, rowsperpage, currpage, iscount, searchType, false);
	}

	/**
	 * custom query script
	 * 
	 * @param siDTO
	 * @param user
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return @throws HibernateException
	 */
	@SuppressWarnings("rawtypes")
	public List customQuery(SearchIncidentForm siDTO, Agent user, int rowsperpage, int currpage, boolean iscount,
			String searchType, boolean dirtyRead) throws HibernateException {
		boolean tagPresent = false;
		Session sess = null;

		if (dirtyRead) {
			sess = HibernateWrapper.getDirtySession().openSession();
		} else {
			sess = HibernateWrapper.getSession().openSession();
		}

		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			if (iscount)
				s.append("select count(incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident ");
			else
				s.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident ");

			if (siDTO.getClaimchecknum().length() > 0 || siDTO.getColor().length() > 0
					|| siDTO.getBagtype().length() > 0 || siDTO.getXdescelement_ID1() > 0
					|| siDTO.getXdescelement_ID2() > 0 || siDTO.getXdescelement_ID3() > 0
					|| siDTO.getManufacturer_ID() > 0 || siDTO.getCategory_ID() > 0
					|| siDTO.getDescription().length() > 0)
				s.append(" join incident.itemlist item ");
			if (siDTO.getCategory_ID() > 0 || siDTO.getDescription().length() > 0)
				s.append(" join item.inventory inventory ");
			if (siDTO.getFlightnum().length() > 0 || siDTO.getAirline().length() > 0)
				s.append(" join incident.itinerary itinerary ");
			if (siDTO.getClaimchecknum().length() >  0) {
				s.append(" left outer join incident.claimchecks claimcheck ");
				tagPresent = true;
			}
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0 || siDTO.getCompanycode_ID().length() > 0
					|| siDTO.getMembershipnum().length() > 0 || siDTO.getAddress1().length() > 0
					|| siDTO.getAddress2().length() > 0 || siDTO.getCity().length() > 0
					|| (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0)
					|| (siDTO.getProvince() != null && siDTO.getProvince().length() > 0) || siDTO.getZip().length() > 0
					|| siDTO.getPhone().length() > 0 || siDTO.getCountrycode_ID().length() > 0
					|| siDTO.getEmail().length() > 0) {
				s.append(" join incident.passengers passenger ");
				s.append(" join passenger.addresses address");
			}

			if (searchType.equals("1")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.LOST_DELAY);
			} else if (searchType.equals("2")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.DAMAGED_BAG);
			} else if (searchType.equals("3")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.MISSING_ARTICLES);
			} else {
				// Includes searchType 4.
				s.append(" where 1=1 ");
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)

			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(siDTO.getS_createtime(), siDTO.getE_createtime(), tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

				} else {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
				}
			}

			// record locator
			if (siDTO.getRecordlocator().length() > 0) {
				s.append(" and incident.recordlocator like :recordlocator");
			}

			//WT ID
			if (siDTO.getWt_id()!=null && siDTO.getWt_id().length() > 0) {
				s.append(" and incident.wtFile.wt_id like :wt_id ");
			}
			
			// passenger
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (passenger.membership.membershipnum like :membershipnum");
				s.append(" and passenger.membership.companycode_ID like :companyCode_ID)");
			}

			intelligentSearchProcessing(siDTO, s, tagPresent);
			
			// addresses

			if (siDTO.getAddress1().length() > 0)
				s.append(" and (address.address1 like :addr1)");
			if (siDTO.getAddress2().length() > 0)
				s.append(" and (address.address2 like :addr2)");
			if (siDTO.getCity().length() > 0)
				s.append(" and address.city like :city");
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0)
				s.append(" and address.state_ID like :state_ID");
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0)
				s.append(" and address.province like :province");
			if (siDTO.getCountrycode_ID().length() > 0) {
				s.append(" and address.countrycode_ID like :countrycode_ID");
			}
			if (siDTO.getZip().length() > 0)
				s.append(" and address.zip like :zip");
			if (siDTO.getPhone().length() > 0)
				s.append(" and (address.homephone like :phone or address.workphone like :phone "
						+ " or address.mobile like :phone or address.pager like :phone or address.altphone like :phone"
						+ " or address.homephone_norm like :phone or address.workphone_norm like :phone or"
						+ " address.mobile_norm like :phone or address.pager_norm like :phone or"
						+ " address.altphone_norm like :phone)");
			if (siDTO.getEmail().length() > 0)
				s.append(" and address.email like :email");

			if (siDTO.getColor().length() > 0)
				s.append(" and item.color like :color");
			if (siDTO.getBagtype().length() > 0)
				s.append(" and item.bagtype like :bagtype");
			if (siDTO.getXdescelement_ID1() > 0)
				s
						.append(" and (item.xdescelement_ID_1 = :xdesc1 or item.xdescelement_ID_2 = :xdesc1 or item.xdescelement_ID_3 = :xdesc1)");
			if (siDTO.getXdescelement_ID2() > 0)
				s
						.append(" and (item.xdescelement_ID_1 = :xdesc2 or item.xdescelement_ID_2 = :xdesc2 or item.xdescelement_ID_3 = :xdesc2)");
			if (siDTO.getXdescelement_ID3() > 0)
				s
						.append(" and (item.xdescelement_ID_1 = :xdesc3 or item.xdescelement_ID_2 = :xdesc3 or item.xdescelement_ID_3 = :xdesc3)");
			if (siDTO.getManufacturer_ID() > 0)
				s.append(" and item.manufacturer_ID = :manu_ID");
			if (siDTO.getManufacturer_other().length() > 0)
				s.append(" and item.manufacturer_other like :manu_other");

			if (siDTO.getCategory_ID() > 0)
				s.append(" and inventory.categorytype_ID = :category_ID ");

			if (siDTO.getDescription().length() > 0) {

				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ', ',').split(
						"\\,");
				int tempx = 0;
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						if (tempx == 0)
							s.append(" and (");
						else
							s.append(" or ");
						s.append(" inventory.description like :description" + x + " ");
						if (x == words.length - 1)
							s.append(") ");
						tempx++;
					}
				}
			}

			if (siDTO.getFlightnum().length() > 0)
				s.append(" and itinerary.flightnum like :flightnum");

			if (siDTO.getAirline().length() > 0)
				s.append(" and itinerary.airline like :airline");

			if (siDTO.getStatus_ID() > 0)
				s.append(" and incident.status.status_ID = :status_ID");
			
			if(siDTO.getStationcreated_ID()>0){
				s.append(" and incident.stationcreated.station_ID = :createstation_ID");
			}

			if(siDTO.getStationassigned_ID()>0){
				s.append(" and incident.stationassigned.station_ID = :assignstation_ID");
			}

			if (!iscount)
				s.append(" order by incident.incident_ID");

			q = sess.createQuery(s.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);

				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}

			if (siDTO.getWt_id()!=null && siDTO.getWt_id().length() > 0) {
				q.setString("wt_id", siDTO.getWt_id());
			}
			
			if (siDTO.getStatus_ID() > 0)
				q.setInteger("status_ID", siDTO.getStatus_ID());

			if (siDTO.getRecordlocator().length() > 0) {
				q.setString("recordlocator", siDTO.getRecordlocator());
			}

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {

				String a = siDTO.getFirstname().length() == 0 ? "%" : siDTO.getFirstname();
				String b = siDTO.getMiddlename().length() == 0 ? "%" : siDTO.getMiddlename();
				String c = siDTO.getLastname().length() == 0 ? "%" : siDTO.getLastname();

				q.setString("firstname", a);
				q.setString("middlename", b);
				q.setString("lastname", c);
			}
			if (siDTO.getAddress1().length() > 0)
				q.setString("addr1", siDTO.getAddress1().toUpperCase());
			if (siDTO.getAddress2().length() > 0)
				q.setString("addr2", siDTO.getAddress2().toUpperCase());
			if (siDTO.getCity().length() > 0)
				q.setString("city", siDTO.getCity().toUpperCase());
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0)
				q.setString("state_ID", siDTO.getState_ID().toUpperCase());
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0)
				q.setString("province", siDTO.getProvince().toUpperCase());
			if (siDTO.getCountrycode_ID().length() > 0)
				q.setString("countrycode_ID", siDTO.getCountrycode_ID().toUpperCase());
			if (siDTO.getZip().length() > 0)
				q.setString("zip", siDTO.getZip().toUpperCase());
			if (siDTO.getPhone().length() > 0)
				q.setString("phone", siDTO.getPhone().toUpperCase());
			if (siDTO.getEmail().length() > 0)
				q.setString("email", siDTO.getEmail().toUpperCase());

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getClaimchecknum().length() > 0)
				q.setString("claimchecknum", siDTO.getClaimchecknum().trim());
			if (siDTO.getClaimchecknum2() != null && siDTO.getClaimchecknum2().length() > 0)
				q.setString("claimchecknum2", siDTO.getClaimchecknum2().trim());

			if (siDTO.getColor().length() > 0)
				q.setString("color", siDTO.getColor().toUpperCase());
			if (siDTO.getBagtype().length() > 0)
				q.setString("bagtype", siDTO.getBagtype().toUpperCase());
			if (siDTO.getXdescelement_ID1() > 0)
				q.setInteger("xdesc1", siDTO.getXdescelement_ID1());
			if (siDTO.getXdescelement_ID2() > 0)
				q.setInteger("xdesc2", siDTO.getXdescelement_ID2());
			if (siDTO.getXdescelement_ID3() > 0)
				q.setInteger("xdesc3", siDTO.getXdescelement_ID3());
			if (siDTO.getManufacturer_ID() > 0)
				q.setInteger("manu_ID", siDTO.getManufacturer_ID());
			if (siDTO.getManufacturer_other().length() > 0)
				q.setString("manu_other", siDTO.getManufacturer_other().toUpperCase().trim());

			if (siDTO.getCategory_ID() > 0)
				q.setInteger("category_ID", siDTO.getCategory_ID());
			if (siDTO.getDescription().length() > 0) {
				// String[] words =
				// StringUtils.removePronouns(siDTO.getDescription()).split("\\s");
				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ', ',').split(
						"\\,");
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						q.setString("description" + x, "%" + (words[x]).trim() + "%");
					}
				}
			}

			if (siDTO.getFlightnum().length() > 0)
				q.setString("flightnum", siDTO.getFlightnum().toUpperCase());
			if (siDTO.getAirline().length() > 0)
				q.setString("airline", siDTO.getAirline().toUpperCase());
			if(siDTO.getStationcreated_ID()>0){
				q.setInteger("createstation_ID", siDTO.getStationcreated_ID());
			}

			if(siDTO.getStationassigned_ID()>0){
				q.setInteger("assignstation_ID", siDTO.getStationassigned_ID());
			}
			List results = q.list();
			return results;
		} catch (Exception e) {
			logger.error("unable to retrieve incident in findIncident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public List findBDOList(String incident_ID) {
		Session sess = null;
		try {
			String query = "select bdo from com.bagnet.nettracer.tracing.db.BDO bdo "
					+ "where bdo.incident.incident_ID = :incident_ID  order by bdo.BDO_ID";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setString("incident_ID", incident_ID);
			@SuppressWarnings("unchecked")
			List<BDO> list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find bdo from incident_ID: " + incident_ID);
				return null;
			}
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve bdo: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	/**
	 * clear ids for reinsert after delete
	 * 
	 * @param inc
	 * @return
	 */
	public Incident clearIncidentIds(Incident inc) {
		if (inc.getPassengers() != null && inc.getPassengers().size() > 0) {
			for (Passenger o : inc.getPassengers()) {
				o.setPassenger_ID(0);
				if (o.getMembership() != null) {
					o.getMembership().setMembership_ID(0);
				}

				if (o.getAddresses() != null && o.getAddresses().size() > 0) {
					for (@SuppressWarnings("unchecked")
					Iterator<Address> j = o.getAddresses().iterator(); j.hasNext();) {
						Address o2 = j.next();
						o2.setAddress_ID(0);
					}
				}
			}
		}

		if (inc.getItinerary() != null && inc.getItinerary().size() > 0) {
			for (Itinerary o : inc.getItinerary()) {
				o.setItinerary_ID(0);
			}
		}

		if (inc.getClaimchecks() != null && inc.getClaimchecks().size() > 0) {
			for (Incident_Claimcheck cc : inc.getClaimchecks()) {
				cc.setClaimcheck_ID(0);
			}
		}

		if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
			for (Item item : inc.getItemlist()) {
				item.setItem_ID(0);

				if (item.getInventory() != null && item.getInventory().size() > 0) {
					for (Iterator<Item_Inventory> j = item.getInventory().iterator(); j.hasNext();) {
						Item_Inventory o2 = (Item_Inventory) j.next();
						o2.setInventory_ID(0);
					}
				}

				if (item.getPhotoes() != null && item.getPhotoes().size() > 0) {
					for (Iterator<Item_Photo> j = item.getPhotoes().iterator(); j.hasNext();) {
						Item_Photo o2 = (Item_Photo) j.next();
						o2.setPhoto_ID(0);
					}
				}
				
				if (item.getItem_bdo() != null && item.getItem_bdo().size() > 0) {
					for (Iterator<Item_BDO> j = item.getItem_bdo().iterator(); j.hasNext();) {
						Item_BDO o2 = (Item_BDO) j.next();
						o2.setId(0);
					}
				}
				
				
			}
		}

		if (inc.getArticles() != null && inc.getArticles().size() > 0) {
			for (Articles a : inc.getArticles()) {
				a.setArticles_ID(0);
			}
		}

		if (inc.getRemarks() != null && inc.getRemarks().size() > 0) {
			for (Remark remark : inc.getRemarks()) {
				remark.setRemark_ID(0);
			}
		}

		if (inc.getExpenses() != null) {
			for (ExpensePayout ep : inc.getExpenses()) {
				ep.setExpensepayout_ID(0);
			}
		}
		
		return inc;
	}
	
	public Date incrementPrintedReceipt(String incident_id) throws HibernateException {
		Incident inc = this.findIncidentByID(incident_id);
		if (inc.getPrintedreceipt() == null) {
			inc.setPrintedreceipt(TracerDateTime.getGMTDate());
			try{
				this.updateIncidentNoAudit(false,false,inc);
			} catch (StaleStateException sse){
				//loupas - should never reach this
				return null;//we failed to successfully save the receipt date, returning null
			}
		}
		return inc.getPrintedreceipt();
	}

	@SuppressWarnings("unchecked")
	public static List<String> queryForFaultCode(ItemType iType, int faultStation, int lossCode) {

		Session sess = null;
		List<String> list = null;

		try {
			sess = HibernateWrapper.getDirtySession().openSession();

			SQLQuery query = sess.createSQLQuery("SELECT Incident_ID FROM INCIDENT WHERE itemtype_ID = :itemType "
					+ "AND faultstation_id = :faultStation AND loss_code = :lossCode AND status_ID = :statusId");

			query.addScalar("Incident_ID", StandardBasicTypes.STRING);
			query.setInteger("itemType", iType.getItemType_ID());
			query.setInteger("faultStation", faultStation);
			query.setInteger("lossCode", lossCode);
			query.setInteger("statusId", TracingConstants.MBR_STATUS_CLOSED);

			list = (List<String>) query.list();
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Incident> findWtEarlyMove(int incEarlyHours, List<String> earlyMoveStations, String companyCode) {
		if (incEarlyHours <= 0) {
			return null;
		}

		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0 - incEarlyHours));
		Date incCutoff = c.getTime();

		String queryString = "select inc from com.bagnet.nettracer.tracing.db.Incident inc where "
				+ " inc.wtFile is null "
				+ // no worldtracer file already
				" and inc.incident_ID not in (select q.incident.incident_ID from WtqIncidentAction q where q.status = :qStatus)"
				+ " and inc.createdate <= :incCutoff "
				+ // old enough
				" and inc.status.status_ID = :status "
				+ // only open
				" and inc.stationassigned.company.companyCode_ID = :companyCode "
				+ " and inc.itemtype.itemType_ID = :itemType "
				+ " and inc.stationassigned.stationcode in (:earlyStationList) " + " order by inc.createdate asc ";

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(queryString);
			q.setParameter("incCutoff", incCutoff);
			q.setParameter("status", TracingConstants.MBR_STATUS_OPEN);
			q.setParameter("companyCode", companyCode);
			q.setParameter("qStatus", WtqStatus.PENDING);
			q.setParameter("itemType", TracingConstants.LOST_DELAY);
			q.setParameterList("earlyStationList", earlyMoveStations, StandardBasicTypes.STRING);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to get move to WT Incident list: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}

	public ActionMessage saveExpense(ExpensePayout ep, String incident_ID, Agent agent) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = sess.beginTransaction();
		try {
			Incident inc = (Incident) sess.get(Incident.class, incident_ID);
			if (inc == null) {
				return new ActionMessage("invalid.claim.info");
			}

			inc.setLastupdated(TracerDateTime.getGMTDate());
			ep.setIncident(inc);
			inc.getExpenses().add(ep);
//			if (inc.getClaims()==null || inc.getClaims().size()<=0) {
//				Claim c = new Claim();
//				
//				c.setNtIncident(inc);
//				
//				c.setAmountClaimedCurrency(inc.getAgent().getDefaultcurrency());
//				// mjs: begin NTFS modification
////				c.setCountryofissue(AdminUtils.getCompany(inc.getAgent().getCompanycode_ID()).getCountrycode_ID());
//				String countryOfIssue = AdminUtils.getCompany(inc.getAgent().getCompanycode_ID()).getCountrycode_ID();
////				for (Person claimant: c.getClaimants()) {
////					claimant.setPassportIssuer(countryOfIssue);
////				}
//				// mjs: end NTFS modification
//				Status st = new Status();
//				st.setStatus_ID(TracingConstants.CLAIM_STATUS_INPROCESS);
//				c.setStatus(st);
//				
//				LinkedHashSet<Claim> claims = new LinkedHashSet<Claim>();
//				claims.add(c);
//				inc.setClaims(claims);
//				sess.save(c);
//			}
			sess.save(ep);
			sess.update(inc);
			tx.commit();
			if (SpringUtils.getReservationIntegration().isWriteCommentToPnrOn()
					&& SpringUtils.getReservationIntegration().isWriteExpensesToPnrOn()) {
				String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(),
						TracingConstants.DB_DATETIMEFORMAT, null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
								agent.getDefaulttimezone()).getTimezone()));
				if (ep.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
					// under limit, auto approve
					SpringUtils.getReservationIntegration().writeCommentToPNR(
							TracingConstants.CMT_CREATE_INTERIM_UNDERLIMIT + formateddatetime,
							ep.getIncident().getRecordlocator());
				} else {
					// over limit, pending
					SpringUtils.getReservationIntegration().writeCommentToPNR(
							TracingConstants.CMT_CREATE_INTERIM + formateddatetime,
							ep.getIncident().getRecordlocator());
				}
			}
			Transaction tx2 = sess.beginTransaction();
			try {
				auditClaim(inc, TracerUtils.getText("claim.created.with.expense", agent),
						agent, sess);
				tx2.commit();
			} catch (Exception e) {
				tx2.rollback();
				logger.error("unable to audit claim entry");
			}

		} catch (Exception e) {
			logger.error("unable to save new expense to a claim", e);
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception e1) {
					logger.debug("error rolling back transaction in saveExpense", e1);
				}
			}
			return new ActionMessage("database.error");
		} finally {
			if (sess != null)
				sess.close();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void auditClaim(Incident inc, String reasonForAudit, Agent user, Session sess) throws Exception {
		Audit_Claim ac = new Audit_Claim();
		ac.setExpenses(new HashSet());



		ac.setModify_time(TracerDateTime.getGMTDate());
		ac.setModify_agent(user);
		ac.setModify_reason(reasonForAudit);
		
		ac.setIncident(inc);

		Status st = new Status();
		st.setStatus_ID(inc.getStatus().getStatus_ID());
		ac.setStatus(st);
		
		Date modifyTime = TracerDateTime.getGMTDate();
		
		for (ExpensePayout ep : inc.getExpenses()) {
			Audit_ExpensePayout aep = new Audit_ExpensePayout();
			BeanUtils.copyProperties(aep, ep);
			
			aep.setAgent(user);
			aep.setModify_time(modifyTime);
			aep.setModify_reason(reasonForAudit);
			String temp = "";
			SimpleDateFormat sdf = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			for (Comment comment : ep.getComments()) {
				temp += String.format("\r\n%s %s %s", comment.getAgent().getUsername(), sdf.format(comment
						.getCreateDate()), comment.getContent());
			}
			aep.setAuditComments(temp);
			ac.getExpenses().add(aep);
			aep.setAudit_claim(ac);
			sess.save(aep);
		}
		sess.save(ac);
//		}
	}
	
	@SuppressWarnings("unchecked")
	private Incident normalizePhoneNumbers(Incident iDTO) {
		if (iDTO.getPassengers() != null) {
			for (Passenger pass : iDTO.getPassengers()) {
				if (pass.getAddresses() != null) {
					for (Address addr : (Set<Address>) pass.getAddresses()) {
						addr.setHomephone_norm(TracerUtils.normalizePhoneNumber(addr.getHomephone()));
						addr.setWorkphone_norm(TracerUtils.normalizePhoneNumber(addr.getWorkphone()));
						addr.setMobile_norm(TracerUtils.normalizePhoneNumber(addr.getMobile()));
						addr.setPager_norm(TracerUtils.normalizePhoneNumber(addr.getPager()));
						addr.setAltphone_norm(TracerUtils.normalizePhoneNumber(addr.getAltphone()));
					}
				}
			}
		}
		return iDTO;
	}
	
	public static Date getIncidentLastUpdateTimestamp(String incidentId){
		String sql = "select lastupdated from incident where incident_id = :incidentId";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(sql);
			q.setParameter("incidentId", incidentId);
			return (Date)q.uniqueResult();
		} catch (Exception e) {
			logger.error("unable to get last update timestamp for incident: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}
	
	public static boolean isStale(Incident inc){
		if(inc != null && inc.getIncident_ID() != null && inc.getLastupdated() != null){
			Date lastUpdated = IncidentBMO.getIncidentLastUpdateTimestamp(inc.getIncident_ID());
			if (lastUpdated != null){
				return inc.getLastupdated().before(lastUpdated);
			}
		}
		return false;
	}

	/**
	 * Get Incidents by PNR
	 *
	 * Method uses to get and return a list of incidents that match pnr 
	 * and were made within the last X amount of days (determined by Company_Specific_Variable pnrLastXDays)
	 *
	 * @param  pnr 	String of pnr to compare against Incidents
	 * @param  lastXDays	Integer of day count that the incident has to be made within the last days of to be included.
	 * @return List of incidents with similar pnr and made in the last X Days.
	 * -Sean Fine
	 */
	public static List<Incident> getIncidentsByPNR(String pnr, int lastXDays){
		String sql = "from com.bagnet.nettracer.tracing.db.Incident where createdate > :lastXDate and recordlocator = :pnr";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			Date lastXDate=new Date();
			lastXDate=DateUtils.addDays(lastXDate, -lastXDays);
			q.setParameter("lastXDate", lastXDate);
			q.setParameter("pnr", pnr);
			
			@SuppressWarnings("unchecked")
			List<Incident> ilist= (List<Incident>) q.list();
			return ilist;
		} catch (Exception e) {
			logger.error("Error in checking existing PNRs for Incidents: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}
	
	
	
	/**
	 * Inserts a single remark for an incident.
	 * 
	 * Since the existing IncidentBMO.updateRemarksOnly takes a set of remarks and overwrites the exisitng set,
	 * this method will load the existing incident remark set and append the new remark before saving.
	 * 
	 * @param remarkText
	 * @param incidentId
	 * @param agent
	 * @param remarkType
	 * @return boolean
	 */
	public boolean insertRemark(String remarkText, String incidentId, Agent agent, int remarkType){
		Incident incident = findIncidentByID(incidentId);
		if(incident == null){
			return false;
		}
		
		if(agent == null){
			//TODO - we use ogadmin for remarks for other crons such as moveToLZ, maybe we should consider a configuration system or auto agent
			agent = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		}

		Remark r = new Remark();
		r.setAgent(agent);
		r.setIncident(incident);
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setRemarktype(remarkType);
		r.setRemarktext(remarkText);
		incident.getRemarks().add(r);

		if(updateRemarksOnly(incident.getIncident_ID(), incident.getRemarks(), agent) > 0){
			//remarks were updated successfully
			return true;
		} else {
			return true;
		}
	}
	
	/**
	 * Get Remarks by Incident
	 *
	 * Method used to get and return a list of remarks that belong to an Incident
	 *
	 * @param  incident_id 	String of incident_id to compare against remark incident_ids
	 * @return List of Remarks with incident_id.
	 * -Sean Fine
	 */
	public static List<Remark> getRemarksByIncidentId(String incident_id){
		String sql = "from com.bagnet.nettracer.tracing.db.Remark where incident.id = :incidentId";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("incidentId", incident_id);
			@SuppressWarnings("unchecked")
			List<Remark> ilist= (List<Remark>) q.list();
			return ilist;
		} catch (Exception e) {
			logger.error("Error in checking Remarks for Incidents: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}


	/**
	 * Method to populate the form with existing data from the database for the
	 * purposes of checking if remarks are required.
	 * Currently used for getting existing Item data and the size of the remarks
	 * 
	 * @param form_incident_id
	 *            - id of incident to fill the existing information
	 * @param theform
	 *            - incidentForm to be populated
	 */
	public static void fillFormWithExistingData(String form_incident_id, IncidentForm theform) {

			Incident inc=IncidentBMO.getIncidentByID(form_incident_id, null);
			fillFormWithExistingData(inc,theform);
	}
	
	/**
	 * Method to populate the form with existing data from the database for the
	 * purposes of checking if remarks are required.
	 * Currently used for getting existing Item data and the size of the remarks
	 * 
	 * @param inc
	 *            - incident to fill the existing information
	 * @param theform
	 *            - incidentForm to be populated
	 */
	public static void fillFormWithExistingData(Incident inc, IncidentForm theform) {
		if(inc!=null){
			if(inc.getItemlist()!=null){
				theform.setExistItemlist(inc.getItemlist());
			} else {
				theform.setExistItemlist(new ArrayList<Item>());
			}
			
			if(inc.getRemarks()!=null){
				theform.setExistRemarkSize(inc.getRemarks().size());
			} else {
				/* Assumed Remark size to be 1*/
				theform.setExistRemarkSize(1);
			}

			if(inc.getStatus()!=null){
				theform.setExistIncStatus(inc.getStatus());
			}
		} else {
			theform.setExistItemlist(new ArrayList<Item>());
			/* Assumed Remark size to be 1*/
			theform.setExistRemarkSize(1);
			
		}
		
	}
}