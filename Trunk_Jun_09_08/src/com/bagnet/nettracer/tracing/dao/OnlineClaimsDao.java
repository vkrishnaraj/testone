package com.bagnet.nettracer.tracing.dao;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCBag;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCContents;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCItinerary;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPhone;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCBag;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCContents;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCItinerary;
import com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCPhone;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import common.Logger;

public class OnlineClaimsDao {
	
	Logger logger = Logger.getLogger(OnlineClaimsDao.class);
	

	public OnlineClaim getOnlineClaim(String incidentId) {
		Session sess = null;
		OnlineClaim claim = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			Criteria crit = sess.createCriteria(OnlineClaim.class);
			crit.add(Expression.eq("incident.incident_ID", incidentId));
			claim = (OnlineClaim) crit.uniqueResult();
			
		} catch (Exception e) {
			logger.error("Exception loading claim...", e);
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
			logger.error("Exception loading claim...", e);
		} finally {
			sess.close();
		}
		return claim;
	}
	
	public OnlineClaim saveOnlineClaimWsUseOnly(OnlineClaim claim, String incidentId, Agent agent) throws AuthorizationException {
		boolean isNew = true;
		boolean contactUpdateOnly = false;
		OnlineClaim existingDbClaim = null;
		
		if (claim != null && claim.getClaimId() != 0) {
			isNew = false;
		}
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			if (!isNew) {
				existingDbClaim = (OnlineClaim) sess.load(OnlineClaim.class, claim.getClaimId());
				
				if (existingDbClaim.getClaimId() != claim.getClaimId() || !existingDbClaim.getIncident().getIncident_ID().equals(incidentId)) {
					throw new AuthorizationException();
				} else if (existingDbClaim.getAccept() != null && existingDbClaim.getAccept().equalsIgnoreCase("ACCEPT")  || !existingDbClaim.getStatus().equalsIgnoreCase("NEW")) {
					contactUpdateOnly = true;
					contactOnlyUpdateMapping(claim, existingDbClaim);
				} else {
					BeanUtils.copyProperties(claim, existingDbClaim);
					
					if (existingDbClaim.getAccept() != null && existingDbClaim.getAccept().equalsIgnoreCase("ACCEPT") && existingDbClaim.getSubmitDate() != null) {
						existingDbClaim.setStatus("SUBMITTED");
						existingDbClaim.setSubmitDate(TracerDateTime.getGMTDate());
					}
				}
			}
			t = sess.beginTransaction();
			
			if (!isNew && !contactUpdateOnly) {
				
				mapSubObjToParentObjects(existingDbClaim);
				
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				sess.save(ac);
			} else if (!isNew && contactUpdateOnly) {
				mapSubObjToParentObjects(existingDbClaim);
				// Updating the existing claim, not replacing it.
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				sess.save(ac);
			} else {
				mapSubObjToParentObjects(claim);
				sess.save(claim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				sess.save(ac);
			}
			
			t.commit();
			return claim;
		} catch (Exception e) {
			if (t != null) t.rollback();
			logger.error("Exception loading claim...", e);
		} finally {
			if (sess != null) 
				sess.close();
		}
		return null;
	}


	private void mapSubObjToParentObjects(OnlineClaim claim) {
		for (OCBag i: claim.getBag()) {
			if (i.getClaim() == null)
				i.setClaim(claim);
			for (OCContents j: i.getContents()) {
				if (j.getBag() == null)
					j.setBag(i);
			}
		}
		
		for (OCFile i : claim.getFile()) {
			if (i.getClaim() == null)
				i.setClaim(claim);
		}

		for (OCItinerary i : claim.getItinerary()) {
			if (i.getClaim() == null)
				i.setClaim(claim);
		}

		for (OCPhone i : claim.getPhone()) {
			if (i.getClaim() == null)
				i.setClaim(claim);
		}		
		
	}

	private void contactOnlyUpdateMapping(OnlineClaim origin, OnlineClaim dest) {
		dest.setLastName(origin.getLastName());
		dest.setFirstName(origin.getFirstName());
		dest.setMiddleInitial(origin.getMiddleInitial());
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
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim retVal = mapper.map(claim, com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class);
		for (AOCBag i: retVal.getBag()) {
			i.setClaim(retVal);
			for (AOCContents j: i.getContents()) {
				j.setBag(i);
			}
		}
		
		for (AOCFile i : retVal.getFile()) {
			i.setClaim(retVal);
		}

		for (AOCItinerary i : retVal.getItinerary()) {
			i.setClaim(retVal);
		}

		for (AOCPhone i : retVal.getPhone()) {
			i.setClaim(retVal);
		}		
		
		retVal.setAgent_modified(agent_modified);
		retVal.setTime_modified(TracerDateTime.getGMTDate());
		return retVal;
	}
	

	public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim convertClaimDbToWs(OnlineClaim claim) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		com.bagnet.nettracer.ws.onlineclaims.xsd.Claim retVal = mapper.map(claim, com.bagnet.nettracer.ws.onlineclaims.xsd.Claim.class);
		return retVal;
	}
	
	public OnlineClaim convertClaimWsToDb(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim claim, String incidentId) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		OnlineClaim retVal = mapper.map(claim, OnlineClaim.class);
		Incident i = new Incident();
		i.setIncident_ID(incidentId);
		retVal.setIncident(i);
		return retVal;
	}
	

	private void completeMapping(OnlineClaim source, OnlineClaim destination) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		mapper.map(source, destination);
	}
}
