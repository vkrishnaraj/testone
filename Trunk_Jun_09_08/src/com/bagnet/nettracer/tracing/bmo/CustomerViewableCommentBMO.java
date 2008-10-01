package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;

public class CustomerViewableCommentBMO {

	private static Logger logger = Logger.getLogger(CustomerViewableCommentBMO.class);
	
	public static CustomerViewableComment getComment(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(CustomerViewableComment.class).add(
					Expression.eq("incident.incident_ID", incident_ID));
			if (cri.list().size() > 0) {
				return (CustomerViewableComment) cri.list().get(0);
			} else {
				logger.debug("Unable to find customer comment for: " + incident_ID);
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve customer comment: " + e);
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
}
