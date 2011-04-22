//package fs;
//
//import java.math.BigInteger;
//import java.util.Date;
//import java.util.List;
//
//import org.hibernate.Hibernate;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.junit.Test;
//
//import aero.nettracer.fs.model.Claim;
//import aero.nettracer.fs.model.Incident;
//
//import com.bagnet.nettracer.hibernate.FraudWrapper;
//
//public class ProcessForMatches {
//
//
//	
//	@Test
//	public void process() {
//		Session sess = FraudWrapper.getFraudSession().openSession();
//
//		// GET LIST OF EVERYTHING TO PROCESS
//		String sql = "select c.id cid, i.id iid from incident i left outer join claim c on i.claim_id = c.id where i.id is not null or c.id is not null";
//		SQLQuery query = sess.createSQLQuery(sql);
//		
//		query.addScalar("cid", Hibernate.LONG);
//		query.addScalar("iid", Hibernate.LONG);
//
//		List<Object[]> toProcess = query.list();
//		System.out.println("Total size: " + toProcess.size());
//		sess.close();
//		sess = null;
//		Long claimId = null;
//		Long incidentId = null;
////		System.out.println("Beginning...");
//		int count = 0;
//		Date start = new Date();
//		for (Object[] strs : toProcess) {
////			System.out.println("Iterating: " + count);
//			
//			claimId = (Long) strs[0];
//			incidentId = (Long) strs[1];
//
//			if (sess == null) {
//				sess = FraudWrapper.getFraudSession().openSession();
//			}
//			Claim c = null;
//			Incident i = null;
//			if (claimId != null && claimId.longValue() > 0) {
//				c = (Claim) sess.load(Claim.class, claimId.longValue());
//			} else if (incidentId != null && incidentId.longValue() > 0) {
//				i = (Incident) sess.load(Incident.class, incidentId.longValue());
//			}
//			
//			
//			trace(sess, i, c);
//			
//
//			if (++count % 250 == 0) {
//				sess.close();
//				sess = null; 
//				Date now = new Date();
//				System.out.println("Count: " + count + " / " + toProcess.size() + "  Elapsed time: " + (double)(now.getTime() - start.getTime())/1000F/60F);
//			}
//		}
//
//	}
//
//	private void trace(Session sess, Incident i, Claim c) {
//		if (i != null) {
////			System.out.println("Incident: " + i.getAirlineIncidentId());
//			
//			
//		} if (c != null) {
////			System.out.println("Claim: " + c.getIncident().getAirlineIncidentId());
//		} 
//	  
//  }
//}
