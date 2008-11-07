/**
 * 
 */
package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;

/**
 * @author byron
 *
 */
public class DelivercompanyBMO {

	private static Logger logger = Logger.getLogger(DelivercompanyBMO.class);
	
	/**
	 * Retrieve an delivery company based on its id
	 * 
	 * @param deliverCompany_ID
	 * @return delivery company; null if not found or exception
	 */
	public static DeliverCompany getDeliveryCompany(String deliverCompany_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(Expression.eq("delivercompany_ID", new Integer(deliverCompany_ID)));
			return (DeliverCompany) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static Deliver_ServiceLevel getServiceLevel(int serviceLevel) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Deliver_ServiceLevel.class).add(Expression.eq("servicelevel_ID", new Integer(serviceLevel)));
			return (Deliver_ServiceLevel) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

}
