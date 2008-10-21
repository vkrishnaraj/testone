package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.datafeed.FlightInfo;

public class FlightInfoBmo extends HibernateDaoSupport {


	@Transactional
	public void deleteAllFlights() {
		Session sess = getSession(false);
		
		Query q = sess.createQuery("delete from FlightInfo");
		q.executeUpdate();
	}

	@Transactional
	public void save(FlightInfo fi) {
		Session sess = getSession(false);
		sess.save(fi);
		
	}



}
