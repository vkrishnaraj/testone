package aero.nettracer.selfservice.fraud;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;


import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;





@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome{

	public String echoTest(String s){
		return "echo: " + s;
	}
	
	public boolean insertClaim(FsClaim claim){

		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(claim);
			t.commit();
			return true;
		} catch (Exception e) {
//			logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
//					logger.error("Error Saving: ", ex);
				}
			}
			return false;
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
	
	public static FsClaim resetId(FsClaim claim){
		claim.setAirlineClaimId((new Long(claim.getId())).toString());
		claim.setId(0);
		System.out.println(claim.toString());
		return claim;
	}
	
}
