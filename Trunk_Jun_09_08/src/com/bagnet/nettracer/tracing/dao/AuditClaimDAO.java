package com.bagnet.nettracer.tracing.dao;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
//import org.hibernate.classic.Session;
import org.hibernate.Session;

import aero.nettracer.fs.model.AuditFsClaim;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class AuditClaimDAO {
	
	private static Logger logger = Logger.getLogger(AuditClaimDAO.class);
	private static final String EXCEPTION_MESSAGE = "Exception in AuditClaimDAO";

	public static boolean saveAuditClaim(AuditFsClaim auditClaim) {
		boolean success = false;
		if (auditClaim == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			session.save(auditClaim);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
}
