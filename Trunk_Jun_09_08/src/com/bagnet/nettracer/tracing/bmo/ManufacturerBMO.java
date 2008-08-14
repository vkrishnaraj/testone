package com.bagnet.nettracer.tracing.bmo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Manufacturer;


public class ManufacturerBMO {
	
	public static String getManufacturerString(int manufacturer_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Manufacturer.class).add(
					Expression.eq("manufacturer_ID", new Integer(manufacturer_ID)));
			if (cri.list().size() > 0)
				return ((Manufacturer) cri.list().get(0)).getDescription();
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
}
