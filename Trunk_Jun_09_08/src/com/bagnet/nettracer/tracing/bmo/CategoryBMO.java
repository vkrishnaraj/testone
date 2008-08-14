package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;

public class CategoryBMO {

	public static OHD_CategoryType getCategory(int code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(
					Expression.eq("OHD_CategoryType_ID", new Integer(code)));
			List retList = cri.list();
			
			if (retList.size() == 1) {
				return (OHD_CategoryType) cri.list().get(0);
			}
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
	
	public static OHD_CategoryType getCategory(String code, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(
					Expression.eq("OHD_CategoryType_ID", new Integer(code)))
					.add(Expression.eq("locale", locale));
			return (OHD_CategoryType) cri.list().get(0);
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
