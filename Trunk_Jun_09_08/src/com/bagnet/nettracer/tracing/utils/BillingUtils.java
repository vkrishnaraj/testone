package com.bagnet.nettracer.tracing.utils;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Billing;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class BillingUtils {

	public static Billing reportExists(String report_num) {
		Billing ret = null;

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria criteria = sess.createCriteria(Billing.class);
			criteria.add(Expression.eq("incident.incident_ID", report_num));
			List results = criteria.list();
			if (results.size() > 0) ret = (Billing) results.get(0);
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
		return ret;
	}

}