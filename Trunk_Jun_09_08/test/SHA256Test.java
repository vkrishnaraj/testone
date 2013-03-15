import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.utils.StringUtils;



public class SHA256Test {

    @org.junit.Test
	public void testSHA1toSHA256() {
    	Session sess = null;
    	List toChange = null;
    	String sql = "select id, driversLicenseNumber, socialSecurity, passportNumber from person where " +
    			"(driversLicenseNumber is not null and length(driversLicenseNumber) = 40) or " +
    			"(socialSecurity is not null and length(socialSecurity) = 40) or " +
    			"(passportNumber is not null and length(passportNumber) = 40)";
    	try {
    		sess = HibernateWrapper.getSession().openSession();
    		SQLQuery query = sess.createSQLQuery(sql);
    		query.addScalar("id", StandardBasicTypes.LONG);
    		query.addScalar("driversLicenseNumber", StandardBasicTypes.STRING);
    		query.addScalar("socialSecurity", StandardBasicTypes.STRING);
    		query.addScalar("passportNumber", StandardBasicTypes.STRING);
    		toChange = query.list();
    	} catch (Exception e) {
    		
    	} finally {
    		if (sess != null) {
    			sess.close();
    		}
    	}
    	
    	if (toChange != null) {
    		Object[] row;
    		for (int i = 0; i < toChange.size(); ++i) {
    			row = (Object[]) toChange.get(i);
    			if (row != null) {
    				long id = (Long) row[0];
    				String dl = (String) row[1];
    				String ss = (String) row[2];
    				String pn = (String) row[3];
    				String updSql = "update person set driversLicenseNumber = :dln , socialSecurity = :ss , passportNumber = :ppn where id = :id";
    				
    				// SHA256 the salted SHA1 entry
    				dl = StringUtils.sha256(dl);
    				ss = StringUtils.sha256(ss); 
    				pn = StringUtils.sha256(pn);
    				
    				sess = HibernateWrapper.getSession().openSession();
    				SQLQuery query = sess.createSQLQuery(updSql);
    				
    				query.setParameter("id", id);
    				query.setParameter("dln", dl);
    				query.setParameter("ss", ss);
    				query.setParameter("ppn", pn);
    				
    				query.executeUpdate();
    				System.out.println("Person ID " + id + " has been updated!");
    			}
    		}
    	}
    }
}
