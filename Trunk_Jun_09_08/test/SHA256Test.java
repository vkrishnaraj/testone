import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    	
    	Transaction tx = null;
    	if (toChange != null) {
    		Object[] row;
    		for (int i = 0; i < toChange.size(); ++i) {
    			row = (Object[]) toChange.get(i);
    			if (row != null) {
    				long id = (Long) row[0];
    				String dl = null;
    				String ss = null;
    				String pn = null;
    				String updSql = "update person set driversLicenseNumber = :dln , socialSecurity = :ss , passportNumber = :ppn where id = :id";
    				
    				// SHA256 the salted SHA1 entry
    				if (row[1] != null) {
    					dl = StringUtils.sha256((String) row[1]);
    				}
    				if (row[2] != null) {
    					ss = StringUtils.sha256((String) row[2]); 
    				}
    				if (row[3] != null) {
    					pn = StringUtils.sha256((String) row[3]);
    				}
    				
    				try {
    					
	    				sess = HibernateWrapper.getSession().openSession();
	    				tx = sess.beginTransaction();
	    				SQLQuery query = sess.createSQLQuery(updSql);
	    				
	    				query.setParameter("id", id);
	    				query.setParameter("dln", dl, StandardBasicTypes.STRING);
	    				query.setParameter("ss", ss, StandardBasicTypes.STRING);
	    				query.setParameter("ppn", pn, StandardBasicTypes.STRING);
	    				
	    				query.executeUpdate();
	    				tx.commit();
	    				System.out.println("Person ID " + id + " has been updated!");
    				} catch (Exception e) {
    					tx.rollback();
    				} finally {
    					if (sess != null) {
    						sess.close();
    					}
    				}
    			}
    		}
    	}
    }
}
