package com.bagnet.nettracer.tracing.dao.lf;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

public class LFDeliveryDAO {
	
	private static final String EXCEPTION_MESSAGE = "Exception in LFLostDAO";
	private static Logger logger = Logger.getLogger(LFLostDAO.class);
	
	public static LFDelivery loadDelivery(long id) {
		Session session = null;
		LFDelivery delivery = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			delivery = (LFDelivery) session.get(LFDelivery.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return delivery;
	}
	
	public static boolean saveDelivery(LFDelivery delivery) {
		boolean success = false;
		if (delivery == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(delivery.getId() > 0) {
				session.merge(delivery);
			} else {
				session.save(delivery);
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
	
	public static LFDelivery getDeliveryByLostId(LFLost lost) {
		LFDelivery delivery = new LFDelivery();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(LFDelivery.class);
			criteria.add(Restrictions.eq("lost", lost));
			List results = criteria.list();
			if (results != null && !results.isEmpty()) {
				delivery = (LFDelivery) results.get(0);
			}
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return delivery;
	}

}
