package com.bagnet.nettracer.tracing.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCBag;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCContents;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCItinerary;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPassenger;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPhone;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.ClaimStatus;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCBag;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCContents;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCItinerary;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCPhone;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Bag;
import common.Logger;

public class OnlineClaimsDao {

	public static final String STATUS_SUBMITTED = "SUBMITTED";
	public static final String STATUS_REVIEW = "REVIEW";
	public static final String STATUS_ONRECORD = "ONRECORD";
	private static final String EXCEPTION_LOADING_CLAIM = "Exception loading claim...";
	Logger logger = Logger.getLogger(OnlineClaimsDao.class);
	
	
	/*
	 * Returns the count of items in the "Submitted" state.
	 * This queries the entire system.
	 */
	public int getSubmittedCount() {
		return getSubmittedCount(0,0);
	}
	
	public int getSubmittedCount(int lzId, int stationId){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(o) from com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim o where status = :status";
			if(lzId > 0 && stationId > 0){
			  sql += " and (o.incident.stationassigned.station_ID = :station or o.incident.stationassigned.lz_ID = :lz)";
			}
			Query q = sess.createQuery(sql);
			if(lzId > 0 && stationId > 0){
				q.setParameter("station", stationId);
				q.setParameter("lz", lzId);
			}
			q.setParameter("status", STATUS_SUBMITTED);
			Long r = (Long) q.uniqueResult();
			return r.intValue();

		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return 0;
	}

	
	public int getReviewCount(int lzId, int stationId) {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("select count(o) from com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim o" +
					" where status = :status" +
					" and (o.incident.stationassigned.station_ID = :station or o.incident.stationassigned.lz_ID = :lz)");
			q.setParameter("status", STATUS_REVIEW);
			q.setParameter("station", stationId);
			q.setParameter("lz", lzId);
			Long r = (Long) q.uniqueResult();
			return r.intValue();

		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return 0;
	}
	
	public OnlineClaim changeClaimStatusToReview(long claimId) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			OnlineClaim claim = (OnlineClaim) sess.load(OnlineClaim.class, claimId);
			claim.setStatus(STATUS_REVIEW);
			t = sess.beginTransaction();
			sess.save(claim);
			t.commit();
			return claim;
		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
			if (t != null) {
				t.rollback();
			}
		} finally {
			sess.close();
		}		
		return null;
	}
	
	public boolean saveMessage(OCMessage message) {
		List<OCMessage> messagelist=new ArrayList<OCMessage>();
		messagelist.add(message);
		return saveMessages(messagelist);

	}

	public static boolean saveMessages(List<OCMessage> messages) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			for(OCMessage message:messages){
				sess.merge(message);
				sess.flush();
				sess.clear();
			}
			t.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			sess.close();
		}
		return false;
	}

	public boolean saveFile(OCFile file) {
		List<OCFile> filelist=new ArrayList<OCFile>();
		filelist.add(file);
		return saveFiles(filelist);
	}
	
	public static boolean saveFiles(List<OCFile> files) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			for(OCFile file:files){
				sess.merge(file);
				sess.flush();
				sess.clear();
			}
			t.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		} finally {
			sess.close();
		}
		return false;
	}
	
	public OnlineClaim changeClaimStatusToOnRecord(long claimId) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			OnlineClaim claim = (OnlineClaim) sess.load(OnlineClaim.class, claimId);
			claim.setStatus(STATUS_ONRECORD);
			t = sess.beginTransaction();
			sess.save(claim);
			t.commit();
			return claim;
		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
			if (t != null) {
				t.rollback();
			}
		} finally {
			sess.close();
		}
		return null;

	}


	public int getClaimsListCount(String status, Date startDate, Date endDate, int rowsPerPage, int currentPage, int lzId, int stationId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			
			StringBuilder query = getListingQueryString(status, lzId, stationId, true, startDate, endDate);
			
			Query q = sess.createQuery(query.toString());
			
			setListQueryParameters(status, lzId, stationId, q, startDate, endDate);
			return ((Long) (q.list().get(0))).intValue();
			

		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return 0;
	}

	
	public List<OnlineClaim> getClaimsList(String status,  Date startDate, Date endDate, int rowsPerPage, int currentPage, int lzId, int stationId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			
			StringBuilder query = getListingQueryString(status, lzId, stationId, false, startDate, endDate);
			
			Query q = sess.createQuery(query.toString());
			
			setListQueryParameters(status, lzId, stationId, q, startDate, endDate);
			
			if (rowsPerPage > 0) {
				int startnum = currentPage * rowsPerPage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsPerPage);
			}
			@SuppressWarnings("unchecked")
			List<OnlineClaim> l = q.list();
			return l;

		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return null;
	}


	private void setListQueryParameters(String status, int lzId, int stationId, Query q, Date startDate, Date endDate) {
	  if (status != null)
	  	q.setParameter("status", status);
	  if (stationId > 0 && lzId > 0) {
	  	q.setParameter("station", stationId);
	  	q.setParameter("lz", lzId);
	  }
	  if (startDate != null & endDate != null) {
		  q.setParameter("startDate", startDate);
		  q.setParameter("endDate", endDate);
	  }

  }


	private StringBuilder getListingQueryString(String status, int lzId, int stationId, boolean isCount, Date startDate, Date endDate) {
	  StringBuilder query = null;			
	  if (!isCount) {
	  	query = new StringBuilder("select o from com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim o where ");
	  } else {
	  	query = new StringBuilder("select count(o) from com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim o where ");
	  }
	  
	  if (status != null)
	  	query.append(" status = :status");
	  if (stationId > 0 && lzId > 0) {
	  	query.append(" and (o.incident.stationassigned.station_ID = :station or o.incident.stationassigned.lz_ID = :lz)");
	  }
	  if (startDate != null & endDate != null) {
		  query.append(" and o.submitDate >= :startDate and o.submitDate <= :endDate ");
	  }
	  
	  if (!isCount) {
	  	query.append(" order by o.submitDate asc");
	  }
	  return query;
  }


	public OnlineClaim getOnlineClaim(String incidentId) {
		Session sess = null;
		OnlineClaim claim = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			Criteria crit = sess.createCriteria(OnlineClaim.class);
			crit.add(Restrictions.eq("incident.incident_ID", incidentId));
			claim = (OnlineClaim) crit.uniqueResult();

		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return claim;
	}

	public OnlineClaim getOnlineClaim(long id) {
		Session sess = null;
		OnlineClaim claim = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			claim = (OnlineClaim) sess.load(OnlineClaim.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_LOADING_CLAIM, e);
		} finally {
			sess.close();
		}
		return claim;
	}
	
	public OnlineClaim saveOnlineClaimWsUseOnly(OnlineClaim claim, String incidentId, Agent agent) throws AuthorizationException {
		return saveOnlineClaimWsUseOnly(claim, incidentId, agent, null);
	}

	public OnlineClaim saveOnlineClaimWsUseOnly(OnlineClaim claim, String incidentId, Agent agent, Session sess) throws AuthorizationException {
		boolean isNew = true;
		boolean contactUpdateOnly = false;
		OnlineClaim existingDbClaim = null;
		boolean sessProvided = true;

		if (claim != null && claim.getClaimId() != 0) {
			isNew = false;
		}

		Transaction t = null;
		try {
			if (sess == null) {
				sessProvided = false;
				sess = HibernateWrapper.getSession().openSession();
			}
			t = sess.beginTransaction();
			if (!isNew) {
				existingDbClaim = (OnlineClaim) sess.load(OnlineClaim.class, claim.getClaimId());
				
				String currentStatus = existingDbClaim.getStatus();

				if (existingDbClaim.getClaimId() != claim.getClaimId() || !existingDbClaim.getIncident().getIncident_ID().equals(incidentId)) {
					throw new AuthorizationException();
				} else {
					for (OCPhone i : existingDbClaim.getPhone()) {
						sess.delete(i);
					}
					
					for (OCPassenger i : existingDbClaim.getPassenger()) {
						sess.delete(i);
					}
					
					if (existingDbClaim.getMailingAddress() != null) {
						sess.delete(existingDbClaim.getMailingAddress());
					}

					if (existingDbClaim.getPermanentAddress() != null) {
						sess.delete(existingDbClaim.getPermanentAddress());
					}
					
					if (!existingDbClaim.getStatus().equalsIgnoreCase("NEW")) {
				
						contactUpdateOnly = true;
						contactOnlyUpdateMapping(claim, existingDbClaim);
					} else {
						for (OCBag i : existingDbClaim.getBag()) {
							for (OCContents j : i.getContents()) {
								sess.delete(j);
							}
							sess.delete(i);
						}
						
						for (OCItinerary i : existingDbClaim.getItinerary()) {
							sess.delete(i);
						}

						Map<Long,IncidentActivity> iafilemap=new HashMap<Long,IncidentActivity>();
						for (OCFile i : existingDbClaim.getFile()) {
							if(i.getIncAct()!=null){
								iafilemap.put(i.getId(), i.getIncAct());
								i.getIncAct().getFiles().remove(i);
							}
							sess.delete(i);
						}

						Map<Long,IncidentActivity> iamessagemap=new HashMap<Long,IncidentActivity>();
						for (OCMessage i : existingDbClaim.getMessages()) {
							if(i.getIncAct()!=null){
								iamessagemap.put(i.getId(), i.getIncAct());
								i.getIncAct().getMessages().remove(i);
							}
							sess.delete(i);
						}
						
						IncidentActivity ia=null;
						for(OCMessage i:claim.getMessages()){
							ia=iamessagemap.get(i.getId());
							if(ia!=null){
								i.setIncAct(ia);
							}
							i.setId(0);
						}

						for(OCFile i:claim.getFile()){
							ia=iafilemap.get(i.getId());
							if(ia!=null){
								i.setIncAct(ia);
							}
							i.setId(0);
						}
						
						BeanUtils.copyProperties(claim, existingDbClaim);
						
						existingDbClaim.setStatus(currentStatus);
	
						if (checkSubmitCondition(existingDbClaim)) {
							existingDbClaim.setStatus(STATUS_REVIEW);
							existingDbClaim.setSubmitDate(TracerDateTime.getGMTDate());
						}
					}
				}
			}
			
			boolean saveIncident = false;
			if (!isNew && !contactUpdateOnly) {
				mapSubObjToParentObjects(existingDbClaim);
				
				createIncidentActivity(existingDbClaim);
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
			} else if (!isNew && contactUpdateOnly) {
				mapSubObjToParentObjects(existingDbClaim);
				// Updating the existing claim, not replacing it.
				createIncidentActivity(existingDbClaim);
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
			} else {
				mapSubObjToParentObjects(claim);
				claim.setStatus("NEW");
				createIncidentActivity(claim);
				sess.save(claim);
				saveIncident = true;
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
			}
			
//			sess.clear();
			t.commit();
			if (saveIncident) {
				Incident i = claim.getIncident();
				OnlineClaimsDao dao = new OnlineClaimsDao();
				claim = dao.getOnlineClaim(incidentId);
				long claimId = claim.getClaimId();
				i.setOc_claim_id(claimId);
				IncidentBMO iBMO = new IncidentBMO();
				agent = AdminUtils.getAgent(PropertyBMO.getValue(PropertyBMO.PROPERTY_OIA_AGENT));
				try{
					iBMO.saveAndAuditIncident(false, i, agent, sess);
				} catch (StaleStateException sse){
					sse.printStackTrace();
					//loupas - should never reach here
				}
			}
			return claim;
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			logger.error(EXCEPTION_LOADING_CLAIM, e);
			e.printStackTrace();
		} finally {
			if (sess != null && !sessProvided)
				sess.close();
		}
		return null;
	}
	
	private boolean checkSubmitCondition(OnlineClaim claim) {
		if (claim.getSubmitDate() == null) {
			for (OCPassenger pass : claim.getPassenger()) {
				if (pass == null || pass.getAccept() == null || !pass.getAccept().equalsIgnoreCase("ACCEPT")) {
					return false;
				}
			}
		}
		return true;
	}

	private void mapSubObjToParentObjects(OnlineClaim claim) {
		if (claim.getBag() != null) {
			for (OCBag i : claim.getBag()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
				for (OCContents j : i.getContents()) {
					if (j.getBag() == null)
						j.setBag(i);
				}
			}
		}

		if (claim.getFile() != null) {
			for (OCFile i : claim.getFile()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
			}
		}

		if (claim.getItinerary() != null) {
			for (OCItinerary i : claim.getItinerary()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
			}
		}

		if (claim.getPhone() != null) {
			for (OCPhone i : claim.getPhone()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
			}
		}

		if (claim.getPassenger() != null) {
			for (OCPassenger i : claim.getPassenger()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
			}
		}

		if (claim.getMessages() != null) {
			for (OCMessage i : claim.getMessages()) {
				if (i.getClaim() == null)
					i.setClaim(claim);
			}
		}
	}

	private void contactOnlyUpdateMapping(OnlineClaim origin, OnlineClaim dest) {
		dest.setPassenger(origin.getPassenger());
		dest.setPermanentAddress(origin.getPermanentAddress());
		dest.setMailingAddress(origin.getMailingAddress());
		dest.setOccupation(origin.getOccupation());
		dest.setBusinessName(origin.getBusinessName());
		dest.setEmailAddress(origin.getEmailAddress());
		dest.setPhone(origin.getPhone());
		dest.setSocialSecurity(origin.getSocialSecurity());
		dest.setFrequentFlierNumber(origin.getFrequentFlierNumber());
	}

	private com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim generateAuditClaim(OnlineClaim claim, Agent agent_modified) {
		com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim retVal = new com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim();
						
		if (retVal.getBag() != null) {
			for (AOCBag i : retVal.getBag()) {
				i.setClaim(retVal);
				for (AOCContents j : i.getContents()) {
					j.setBag(i);
				}
			}
		}

		if (retVal.getFile() != null) {
			for (AOCFile i : retVal.getFile()) {
				i.setClaim(retVal);
			}
		}

		if (retVal.getItinerary() != null) {
			for (AOCItinerary i : retVal.getItinerary()) {
				i.setClaim(retVal);
			}
		}

		if (retVal.getPhone() != null) {
			for (AOCPhone i : retVal.getPhone()) {
				i.setClaim(retVal);
			}
		}

		retVal.setAgent_modified(agent_modified);
		retVal.setTime_modified(TracerDateTime.getGMTDate());
		return retVal;
	}

	public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim convertClaimDbToWs(OnlineClaim claim) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		//claim.setClaimStatus(claim.getIncident().getClaimStatus()); PLACEHOLDER TO FILL IN CLAIM STATUS INFO FROM INCIDENT
		com.bagnet.nettracer.ws.onlineclaims.xsd.Claim retVal = mapper.map(claim, com.bagnet.nettracer.ws.onlineclaims.xsd.Claim.class);
		return retVal;
	}

	public OnlineClaim convertClaimWsToDb(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim claim, String incidentId) {
		logBefore(claim);
		int timeDiff = getTimeDiff(claim);
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		OnlineClaim retVal = new OnlineClaim();
		retVal.setFile(new LinkedHashSet<OCFile>());
		retVal.setItinerary(new LinkedHashSet<OCItinerary>());
		retVal.setPassenger(new LinkedHashSet<OCPassenger>());
		retVal.setPhone(new LinkedHashSet<OCPhone>());
		retVal.setMessages(new LinkedHashSet<OCMessage>());
		mapper.map(claim, retVal);
		
		Set<OCBag> bagsRet = new LinkedHashSet<OCBag>();
		for (Bag bag : claim.getBagArray()) {
			OCBag bagRet = new OCBag();
			bagRet.setContents(new LinkedHashSet<OCContents>());
			mapper.map(bag, bagRet);
			bagsRet.add(bagRet);
		}
		retVal.setBag(bagsRet);
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Incident i = IncidentBMO.getIncidentByID(incidentId, sess);
			retVal.setIncident(i);
			retVal = fixTime(retVal, timeDiff);
			logAfter(retVal);
			return retVal;
		} finally {
			sess.close();
		}
	}
	
	private OnlineClaim fixTime(OnlineClaim retVal, int timeDiff) {
        if(null != retVal.getFiledPrevoiusDate()){
        	Calendar temp = Calendar.getInstance();
        	temp.setTime(retVal.getFiledPrevoiusDate());
        	temp.add(Calendar.HOUR_OF_DAY, timeDiff);
        	retVal.setFiledPrevoiusDate(temp.getTime());
        }
        
		Set<OCItinerary> itineraries = retVal.getItinerary();
		if(null != itineraries && itineraries.size() >0){
			for (OCItinerary itin : itineraries) {
				if (null != itin && null != itin.getDate()) {
			        Calendar temp = Calendar.getInstance();
			        temp.setTime(itin.getDate());
			        temp.add(Calendar.HOUR_OF_DAY, timeDiff);
					itin.setDate(temp.getTime());
				}
			}
		}
		retVal.setItinerary(itineraries);
		
		Set<OCBag> bags=retVal.getBag();
		if(null != bags && bags.size() >0){
			for (OCBag bag : bags) {
				if(null != bag && null != bag.getPurchaseDate()){
		        	Calendar temp = Calendar.getInstance();
		        	temp.setTime(bag.getPurchaseDate());
			        temp.add(Calendar.HOUR_OF_DAY, timeDiff);
					bag.setPurchaseDate(temp.getTime());
				}
			}
		}
		retVal.setBag(bags);
		return retVal;
	}
	
	private boolean createIncidentActivity(OnlineClaim claim){
		Agent activityagent = AdminUtils.getAgent(PropertyBMO.getValue(PropertyBMO.PROPERTY_OIA_AGENT));
		boolean hasNewInfo=false;
		if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, activityagent)){
			try{
				IncidentActivityService ias=(IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
			
				Activity a=ias.getActivity(TracingConstants.INBOUND_CORRESPONDANCE);
				IncidentActivity ia=DomainUtils.createIncidentActivity(claim.getIncident(), a, activityagent);
				if(claim.getMessages()!=null){
					for(OCMessage message:claim.getMessages()){
						if(message.getStatusId()==TracingConstants.OC_STATUS_NEW){
							hasNewInfo=true;
							message.setIncAct(ia);
							message.setStatusId(TracingConstants.OC_STATUS_PUBLISHED);
						}
					}
				}
				
				if(claim.getFile()!=null){
					for(OCFile file:claim.getFile()){
						if(file.getStatusId()==TracingConstants.OC_STATUS_NEW){
							hasNewInfo=true;
							file.setIncAct(ia);
							file.setStatusId(TracingConstants.OC_STATUS_NODOWNLOAD);
						}
					}
				}
				if(hasNewInfo){
					ias.save(ia);
				
					System.out.println("CREATE INBOUND CORRESPONDANCE TASK");
					//Create Task for Inbound Correspondance for NT-
				}
			} catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return true;
	}
	
	private int getTimeDiff(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim claim) {
		if (claim != null) {
			if (claim.getItineraryArray() != null) {
				for (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary itin : claim.getItineraryArray()) {
					if (itin.getDate() != null) {
						return figureTimeDifference(itin.getDate());
					}
				}
			}
			if (claim.getFiledPrevoiusDate() != null) {
				return figureTimeDifference(claim.getFiledPrevoiusDate());
			}
			if (claim.getBagArray() != null) {
				for (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag bag : claim.getBagArray()) {
					if (bag.getPurchaseDate() != null) {
						return figureTimeDifference(bag.getPurchaseDate());
					}
				}
			}
		}
		
		return 0;
	}
	
	private int figureTimeDifference(Calendar wsTime) {
		int wsHourOfDay = wsTime.get(Calendar.HOUR_OF_DAY);  
		int wsDayOfMonth = wsTime.get(Calendar.DAY_OF_MONTH);  
		
		Calendar server = Calendar.getInstance();
		server.setTime(wsTime.getTime());
		
		int localHourOfDay = server.get(Calendar.HOUR_OF_DAY);  
		int localDayOfMonth = server.get(Calendar.DAY_OF_MONTH);  
	 
		// Difference between Web Service Provided and Server  
		int hourDifference = wsHourOfDay - localHourOfDay;  
		int dayDifference = wsDayOfMonth - localDayOfMonth;  
		if (dayDifference != 0) {  
			hourDifference = hourDifference + 24;  
		}  
		logger.fatal("HOUR DIFFERENCE = " + hourDifference);  
		return hourDifference;
	}
	
	private void logBefore(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim claim) {
		if (claim.getFiledPrevoiusDate() != null) {
			logger.fatal("BEFORE CONVERSION P: " + claim.getFiledPrevoiusDate().toString());
		}
		if (claim.getItineraryArray() != null) {
			for (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary itin : claim.getItineraryArray()) {
				if (itin != null && itin.getDate() != null) {
					logger.fatal("BEFORE CONVERSION I: " + itin.getDate().toString());
				}
			}
		}
		if (claim.getBagArray() != null) {
			for (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag bag : claim.getBagArray()) {
				if (bag != null && bag.getPurchaseDate() != null) {
					logger.fatal("BEFORE CONVERSION B: " + bag.getPurchaseDate().toString());
				}
			}
		}
	}
	
	private void logAfter(OnlineClaim retVal) {
		if (retVal.getFiledPrevoiusDate() != null) {
			logger.fatal("AFTER CONVERSION P: " + retVal.getFiledPrevoiusDate().toString());
		}
		if (retVal.getItinerary() != null) {
			for (OCItinerary itin : retVal.getItinerary()) {
				if (itin != null && itin.getDate() != null) {
					logger.fatal("AFTER CONVERSION I: " + itin.getDate().toString());
				}
			}
		}
		if (retVal.getBag() != null) {
			for (OCBag bag : retVal.getBag()) {
				if (bag != null && bag.getPurchaseDate() != null) {
					logger.fatal("AFTER CONVERSION B: " + bag.getPurchaseDate().toString());
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void completeMapping(OnlineClaim source, OnlineClaim destination) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		mapper.map(source, destination);
	}
	
	public OnlineClaim createOnlineClaim(String incidentId, Session sess, String fName, String name){
		OnlineClaim c=new OnlineClaim();
		Incident i = IncidentBMO.getIncidentByID(incidentId, sess);
		c.setIncident(i);
		c.setStatus(ClaimStatus.NEW.toString());
		Set<OCPassenger> paxes = new HashSet<OCPassenger>();
		OCPassenger pass = new OCPassenger();
		pass.setFirstName(fName);
		pass.setLastName(name);
		paxes.add(pass);
		c.setPassenger(paxes);
		return c;
	}

	public static boolean acknowledgeMessages(IncidentActivity ia){
		boolean success=false;
		if(ia!=null){
			List<OCMessage> messagelist=new ArrayList<OCMessage>();
			for(OCMessage message:ia.getMessages()){
				message.setDateReviewed(new Date());
				messagelist.add(message);
			}
			List<OCFile> filelist=new ArrayList<OCFile>();
			for(OCFile file:ia.getFiles()){
				file.setDateViewed(new Date());
				filelist.add(file);
			}
			success=saveMessages(messagelist) && saveFiles(filelist);
		}
		return success;
	}
}
