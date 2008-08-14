package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Status;

public class StatusBMO {

	private static Logger logger = Logger.getLogger(StatusBMO.class);
	
	/**
	 * 
	 */
	public static Status getStatus(int status_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Status status where status_ID= :status_ID");
			q.setInteger("status_ID", status_ID);
			List list = q.list();
			if (list == null || list.size() == 0) {
				logger.debug("unable to find status");
				return null;
			}
			return (Status) list.get(0);
		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static Status getStatus(int status_ID, String locale) {
		Session sess = null;
		if (locale == null)
			locale = TracingConstants.DEFAULT_LOCALE;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Status.class).add(
					Expression.eq("status_ID", new Integer(status_ID))).add(
					Expression.eq("locale", locale));
			return (Status) cri.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


}
