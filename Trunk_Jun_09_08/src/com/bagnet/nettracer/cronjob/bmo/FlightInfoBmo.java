package com.bagnet.nettracer.cronjob.bmo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.datafeed.FlightInfo;

public class FlightInfoBmo {


	public void deleteAllFlights() {
		Session sess = HibernateWrapper.getSession().openSession();
		
		Query q = sess.createQuery("delete from FlightInfo");
		q.executeUpdate();
	}

	public void save(FlightInfo fi) {
		Session sess = HibernateWrapper.getSession().openSession();
		sess.save(fi);
		
	}



}
