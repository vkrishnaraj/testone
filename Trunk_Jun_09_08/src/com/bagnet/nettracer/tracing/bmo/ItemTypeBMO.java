package com.bagnet.nettracer.tracing.bmo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.ItemType;

public class ItemTypeBMO {

	public static ItemType getItemType(int itemType_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ItemType.class).add(
					Expression.eq("itemType_ID", new Integer(itemType_ID)));
			if (cri.list().size() > 0)
				return (ItemType) cri.list().get(0);
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
