package aero.nettracer.serviceprovider.common.auth;

import org.hibernate.classic.Session;

import aero.nettracer.serviceprovider.common.dao.UserDao;
import aero.nettracer.serviceprovider.common.db.Permission;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class ServiceAuthenticator {
	
	public static User getAndAuthorizeUser(String username, String password, long permission) throws UserNotAuthorizedException {
		Session sess = HibernateWrapper.getSession().openSession();
		User user = null;
		try {
			user = UserDao.getByUsername(sess, username);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sess.close();
		}
		if (!authorize(user, permission)) throw new UserNotAuthorizedException();
		return user;
	}
	
	public static boolean authorize(User user, long permission) {
		if (user != null) {
			for (Permission perm: user.getProfile().getPermissions()) {
				if (perm.getPermission_id() == permission) {
					return true;
				}
			}
		}
		return false;
	}
}
