package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;

public class OtherSystemInformationBMO {
	
	private static Logger logger = Logger.getLogger(OtherSystemInformationBMO.class);
	
	public static OtherSystemInformation getOsi(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(OtherSystemInformation.class).add(
					Expression.eq("incident.incident_ID", incident_ID));
			if (cri.list().size() > 0) {
				return (OtherSystemInformation) cri.list().get(0);
			} else {
				logger.debug("unable to find incident: " + incident_ID);
				return null;
			}
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

}
