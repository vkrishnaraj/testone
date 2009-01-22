package com.bagnet.nettracer.tracing.bmo.claims;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;


public class ClaimSettlementBMO {
	
	private static Logger logger = Logger.getLogger(ClaimSettlementBMO.class);
	
	public static ClaimSettlement getClaimSettlement(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.claims.ClaimSettlement claim where claim.incident.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("Unable to find claim settlement object: " + incident_ID);
				return null;
			}
			ClaimSettlement obj = (ClaimSettlement) list.get(0);

			return obj;
		} catch (Exception e) {
			logger.error("Unable to retrieve claim settlement object: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}
	
	public static ClaimSettlement saveClaimSettlement(ClaimSettlement claim, Session sess) {
		boolean sessionNull = (sess == null);
		Transaction t = null;
		
		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			t = sess.beginTransaction();
			sess.saveOrUpdate(claim);
			t.commit();
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			throw new RuntimeException("Unable to save claim settlement object.");
		} finally {
			if (sessionNull) {
				sess.close();
			}
		}
		return claim;
	}
}
