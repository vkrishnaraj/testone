package aero.nettracer.serviceprovider.common.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.db.User;


public class UserDao {
	
	// TODO: Test case: no results
	// TODO: Test case: one result
	public static User getByUsername(Session sess, String username) {
		Query q = sess.getNamedQuery(User.LOAD_BY_PERMISSION);
		q.setParameter("username", username);
		return (User) q.uniqueResult();
	}
	
	
}
