package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.Station;

public class RequestOhdBMO {

	private static final Logger logger = Logger.getLogger(RequestOhdBMO.class);

	public static OHDRequest getOpenRequest(Station station, String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("from OHDRequest where ohd.OHD_ID = :ohd_id and requestForStation = :station and status.status_ID = :status");
			q.setString("ohd_id", ohd_id);
			q.setParameter("station", station);
			q.setParameter("status", TracingConstants.OHD_STATUS_OPEN);
			List<OHDRequest> result = q.list();
			if(result == null || result.size() < 1) {
				return null;
			}
			return result.get(0);
		} catch (Exception e) {
			logger.error("Unable to determine if OHDRequest exists", e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.warn("Unable to close session after (attempting?) retrieving OHDRequest", e);
				}
			}
		}
	}

}
