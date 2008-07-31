package com.bagnet.nettracer.tracing.bmo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class StationBMO {

	private static Logger logger = Logger.getLogger(StationBMO.class);

	/**
	 * Retrieves the station associated with this station id
	 * 
	 * @param stationId
	 *          the id of the station
	 * @return station or null if not found or exception
	 */
	public static Station getStation(int station_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class).add(
					Expression.eq("station_ID", new Integer(station_ID)));
			if (cri.list().size() > 0)
				return (Station) cri.list().get(0);
			else
				return null;
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

	public static Station getStation(String stationID) {
		return getStation(new Integer(stationID).intValue());
	}
	
}
