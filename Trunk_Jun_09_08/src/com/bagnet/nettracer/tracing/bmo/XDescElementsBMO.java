package com.bagnet.nettracer.tracing.bmo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.XDescElement;

public class XDescElementsBMO {

	public static String getXdescelementcode(int id) {
		Session sess = null;
		try {
			if (id == 0)
				return "X";
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(XDescElement.class).add(
					Expression.eq("XDesc_ID", new Integer(id)));
			XDescElement xe = (XDescElement) cri.list().get(0);
			return xe.getCode();
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

	public static int getXdescelementid(String code) {
		Session sess = null;
		try {
			if (code == null || code.length() == 0)
				return 7;
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(XDescElement.class).add(
					Expression.eq("code", code));
			return ((XDescElement)cri.list().get(0)).getXDesc_ID();
		} catch (Exception e) {
			e.printStackTrace();
			return 7;
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
