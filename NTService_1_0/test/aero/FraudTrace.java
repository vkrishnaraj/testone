package aero;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class FraudTrace {
	@Test
	public void run() {
		Date start = new Date();
//		Producer.matchClaim(3879);
		Date end = new Date();
		System.out.println("run1 " + (end.getTime() - start.getTime()));
//		start = new Date();
//		Producer.matchClaim(14537);
//		end = new Date();
//		System.out.println("run1 " + (end.getTime() - start.getTime()));
	}
	
	//3870
	
//	@Test
	public void letsSeeWhatWeGet(){
		String sql = "select id from fsclaim where id > 3500";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("id", Hibernate.LONG);
		List<Long> result = pq.list();
		sess.close();
		Date start = new Date();
		Date tick = new Date();
		int i = 0;
		for (Long strs : result) {
			i++;
			System.out.println("claim: " + strs);
			if(i%20 == 0){
				tick = new Date();
				double percentDone = i/result.size();
				long tpc = (long) ((tick.getTime() - start.getTime())/i);
				long timeLeft = tpc * (result.size() - i);
				System.out.println("" + (timeLeft/60000) + "   " + i + "/" + result.size());
				System.out.println(TraceWrapper.getIncidentCacheSize() + "   " + TraceWrapper.getClaimCacheSize());
			}
			
			Long c1 = (Long) strs;
			if(c1 != null){
//				Producer.matchClaim(c1);
			}
		}
		
	}
}
