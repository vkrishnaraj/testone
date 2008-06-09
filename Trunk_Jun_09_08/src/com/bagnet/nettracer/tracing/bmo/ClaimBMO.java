/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.audit.Audit_Claim;
import com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class ClaimBMO {
	private static Logger logger = Logger.getLogger(ClaimBMO.class);

	public boolean insertClaim(Claim cDTO, Audit_Claim acDTO) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			t = sess.beginTransaction();
			sess.saveOrUpdate(cDTO);
			t.commit();

			//check if audit is enabled for this company....
			if (acDTO != null
					&& acDTO.getModify_agent().getStation().getCompany().getVariable().getAudit_claims() == 1) {
				if (acDTO != null) {
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

	public Claim findClaimByID(int claim_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			Query q = sess.createQuery("select claim from com.bagnet.nettracer.tracing.db.Claim claim "
					+ "where claim.claim_ID= :claim_ID");
			q.setParameter("claim_ID", new Integer(claim_ID));
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find claim: " + claim_ID);
				return null;
			}
			Claim cDTO = (Claim) list.get(0);

			return cDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve claim: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
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