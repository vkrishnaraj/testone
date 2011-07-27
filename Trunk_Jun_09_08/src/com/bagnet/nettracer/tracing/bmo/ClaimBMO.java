/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.audit.Audit_Claim;
import com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class ClaimBMO {
	private static Logger logger = Logger.getLogger(ClaimBMO.class);

	public boolean insertClaim(Claim cDTO, Audit_Claim acDTO, String incident_ID) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			t = sess.beginTransaction();
			
			if(cDTO.getIncident() == null) {
				Incident inc = (Incident) sess.get(Incident.class, incident_ID);
				cDTO.setNtIncident(inc);
				inc.getClaims().add(cDTO);
				sess.update(inc);
			}
			sess.saveOrUpdate(cDTO);
			
			t.commit();

			//check if audit is enabled for this company....
			if (acDTO != null
					&& acDTO.getModify_agent().getStation().getCompany().getVariable().getAudit_claims() == 1) {
				if (acDTO != null) {
					acDTO.setClaim_ID(cDTO.getId());
					t = sess.beginTransaction();
					sess.save(acDTO);
					t.commit();
				}
			}

		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);
			e.printStackTrace();
			if (t != null) t.rollback();
			return false;
		} finally {
			sess.close();
		}
		return true;
	}

	/**
	 * find a claim using a one off session
	 * @param claim_ID
	 * @return
	 * @throws HibernateException
	 */
	public Claim findClaimByID(long claim_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			return (Claim) sess.get(Claim.class, claim_ID);
		} catch (Exception e) {
			logger.error("unexpected error retreiving claim: " + e);
			return null;
		} finally {
			if(sess != null) sess.close();
		}
	}
		
	/**
	 * finds a claim using a passed in session, when used as part of a larger transaction
	 * 
	 * @param claim_ID
	 * @param sess
	 * @return
	 */
	public Claim findClaimByID(int claim_ID, Session sess) {
		if(sess == null) return null;
		
		try {
			return (Claim) sess.get(Claim.class, claim_ID);
		} catch (Exception e) {
			logger.error("unexpected error retrieving claim: " + e);
			return null;
		}
	}

	public boolean saveExpense(ExpensePayout ep, Audit_ExpensePayout a_ep) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			t = sess.beginTransaction();
			sess.saveOrUpdate(ep);
			
			// audit
			sess.saveOrUpdate(a_ep);
			
			t.commit();
			
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);
			e.printStackTrace();
			if (t != null) t.rollback();
			return false;
		} finally {
			sess.close();
		}
		return true;
	}

}