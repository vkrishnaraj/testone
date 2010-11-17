package com.bagnet.nettracer.tracing.db;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.wt.bmo.ActionFileStationBMO;

public class ActionFileCountTest extends HibernateDaoSupport {
	public static AbstractApplicationContext ctx = null;

	
	@Test
	public void testCounts() {
		try {
//			String[] configs = {
//					"com/bagnet/nettracer/cronjob/applicationContext.xml",
//					"com/bagnet/nettracer/cronjob/cronJobs.xml" };
//			ctx = new ClassPathXmlApplicationContext(configs);
//
//			ctx.registerShutdownHook();

			Session sess = HibernateWrapper.getSession().openSession();

			Query q = sess
					.createQuery("from ActionFileStation afs where afs.companyCode = :companyCode and afs.stationCode = :wtStation");
			q.setString("companyCode", "WS");
			q.setString("wtStation", "YYC");
			ArrayList <ActionFileStation> af =  (ArrayList) q.list();

			// ActionFileStationBMO afs = new ActionFileStationBMO();
			// ActionFileStation af = afs.getAfStation("WS", "YYC");
			 System.out.println(af.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
