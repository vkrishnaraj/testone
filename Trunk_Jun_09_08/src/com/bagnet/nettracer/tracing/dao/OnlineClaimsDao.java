package com.bagnet.nettracer.tracing.dao;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("select count(o) from com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim o where status = :status");
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
			crit.add(Expression.eq("incident.incident_ID", incidentId));
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
//				String currentSubmitDate = existingDbClaim.getSubmitDate();

				if (existingDbClaim.getClaimId() != claim.getClaimId() || !existingDbClaim.getIncident().getIncident_ID().equals(incidentId)) {
					throw new AuthorizationException();
				} else if (existingDbClaim.getAccept() != null && existingDbClaim.getAccept().equalsIgnoreCase("ACCEPT") || !existingDbClaim.getStatus().equalsIgnoreCase("NEW")) {
					contactUpdateOnly = true;
					contactOnlyUpdateMapping(claim, existingDbClaim);
				} else {
					
					for (OCBag i : existingDbClaim.getBag()) {
						for (Object j : i.getContents()) {
							sess.delete(j);
						}
						sess.delete(i);
					}
					for (Object i : existingDbClaim.getPhone()) {
						sess.delete(i);
					}
					for (Object i : existingDbClaim.getItinerary()) {
						sess.delete(i);
					}
					for (Object i : existingDbClaim.getFile()) {
						sess.delete(i);
					}
					
					if (existingDbClaim.getMailingAddress() != null) {
						sess.delete(existingDbClaim.getMailingAddress());
					}

					if (existingDbClaim.getPermanentAddress() != null) {
						sess.delete(existingDbClaim.getPermanentAddress());
					}
					
					
					BeanUtils.copyProperties(claim, existingDbClaim);
					
					existingDbClaim.setStatus(currentStatus);

					if (existingDbClaim.getAccept() != null && existingDbClaim.getAccept().equalsIgnoreCase("ACCEPT") && existingDbClaim.getSubmitDate() == null) {
						existingDbClaim.setStatus(STATUS_SUBMITTED);
						existingDbClaim.setSubmitDate(TracerDateTime.getGMTDate());
					}
				}
			}
			

			if (!isNew && !contactUpdateOnly) {

				mapSubObjToParentObjects(existingDbClaim);
				
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				// TODO: SAVE AC
//				sess.save(ac);
			} else if (!isNew && contactUpdateOnly) {
				mapSubObjToParentObjects(existingDbClaim);
				// Updating the existing claim, not replacing it.
				sess.saveOrUpdate(existingDbClaim);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				// TODO: SAVE AC
				//				sess.save(ac);
			} else {
				mapSubObjToParentObjects(claim);
				claim.setStatus("NEW");
				sess.save(claim);
				long claimId = claim.getClaimId();
				Incident i = claim.getIncident();
				i.setOc_claim_id(claimId);
				sess.saveOrUpdate(i);
				com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim ac = generateAuditClaim(claim, agent);
				// TODO: SAVE AC
				//				sess.save(ac);
			}

			t.commit();
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
//		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
//		com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim retVal = mapper.map(claim, com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class);

		/*
		  
		 
		 */
		com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim retVal = new com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim();
		
//		BeanUtils.copyProperties(claim, retVal);
				
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
