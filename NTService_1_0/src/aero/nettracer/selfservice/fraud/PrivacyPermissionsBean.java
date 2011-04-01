package aero.nettracer.selfservice.fraud;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

@Stateless
public class PrivacyPermissionsBean implements PrivacyPermissionsRemote, PrivacyPermissionsHome{
	
	public static  List<PrivacyPermissions> getPrivacyPermissions(){
		return null;
	}
	

	public PrivacyPermissions getPrivacyPermissions(String companycode, PrivacyPermissions.AccessLevelType level){
		if(companycode == null || level == null) return null;
		Session sess = HibernateWrapper.getSession().openSession();
		
		String sql = "from aero.nettracer.serviceprovider.common.db.PrivacyPermissions p where companycode = :companycode and level = :level";
		
		Query q = sess.createQuery(sql.toString());
		q.setParameter("companycode", companycode);
		q.setParameter("level", level.toString());
		
		
		
//		PrivacyPermissions ca = new PrivacyPermissions();
//		PrivacyPermissionsKey key = new PrivacyPermissionsKey(companycode, level);
//		ca.setKey(key);

		try{
//			List list = sess.createCriteria(PrivacyPermissions.class).add(Example.create(ca)).list();
			List list = q.list();
			if(list != null && list.size() > 0){
				return (PrivacyPermissions)list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sess.close();
		}
		return null;
	}
	
	public void setPrivacyPermissions(PrivacyPermissions ca){
		Transaction t = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(ca);
			t.commit();
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
