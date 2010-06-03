package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.UserGroup;

public class UsergroupBMO {

	private static Logger logger = Logger.getLogger(UsergroupBMO.class);
	
	/**
	 * 
	 */
	public static UserGroup getUsergroup(int usergroup_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			return (UserGroup) sess.load(UserGroup.class, usergroup_ID);
			
		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
}
