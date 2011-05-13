package com.bagnet.nettracer.tracing.dao.lf;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

public class LFLostDAO {

	private static final String EXCEPTION_MESSAGE = "Exception in LFLostDAO";
	
	private static Logger logger = Logger.getLogger(LFLostDAO.class);
	
	public static LFLost loadLost(long id) {
		Session session = null;
		LFLost lost = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			lost = (LFLost) session.get(LFLost.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return lost;
	}
	
	public static boolean saveLost(LFLost lost) {
		boolean success = false;
		if (lost == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(lost.getId() > 0) {
				session.merge(lost);
			} else {
				session.save(lost);
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
