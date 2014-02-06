package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Manufacturer;


public class ManufacturerBMO {
	
	public static String getManufacturerString(int manufacturer_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Manufacturer.class).add(
					Restrictions.eq("manufacturer_ID", new Integer(manufacturer_ID)));
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
	
	public static Manufacturer getManufacturerByDesc(String desc) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Manufacturer.class).add(
					Restrictions.eq("description", desc).ignoreCase());
			@SuppressWarnings("unchecked")
			List<Manufacturer> list=cri.list();
			if (list!=null && list.size() > 0)
				return list.get(0);
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
