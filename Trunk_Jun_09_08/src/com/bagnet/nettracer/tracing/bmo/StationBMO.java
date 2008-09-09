package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Station;

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
	
	public static Station getStationByCode(String stationCode) {
		return getStationByCode(stationCode, null);
	}
	
	public static Station getStationByCode(String stationCode, String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class).add(
					Expression.eq("stationcode", stationCode));
			if (companyCode != null) {
				cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			}
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

	public static List<String> getWorldTracerStations(String company) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("select distinct wt_stationcode from Station where company.companyCode_ID = :company and active = true and length(wt_stationcode) = 3");
			q.setString("company", company);
			return (List<String>) q.list();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.warn("error closing hibernate session", e);
				}
			}
		}
	}
	
}
