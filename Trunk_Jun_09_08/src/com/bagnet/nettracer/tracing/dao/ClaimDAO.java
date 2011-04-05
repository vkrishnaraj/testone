package com.bagnet.nettracer.tracing.dao;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Claim;

public class ClaimDAO {
	
	private static final String EXCEPTION_MESSAGE = "Exception in ClaimDAO";
	
	private static Logger logger = Logger.getLogger(ClaimDAO.class);
	
	public static Claim loadClaim(long id) {
		Session session = null;
		Claim claim = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			claim = (Claim) session.get(Claim.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return claim;
	}
	
	public static boolean saveClaim(Claim claim) {
		boolean success = false;
		if (claim == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(claim.getId() > 0) {
				session.merge(claim);
			} else {
				session.save(claim);
			}
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
