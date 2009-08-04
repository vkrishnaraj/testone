package aero.nettracer.serviceprovider.common.utils;

import java.util.Map;

import org.hibernate.classic.Session;

import aero.nettracer.serviceprovider.common.dao.UserDao;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.ReservationSystemType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

public class ServiceUtilities {
	
	public static User getAndAuthorizeUser(String username, String password, PermissionType permissionType) throws UserNotAuthorizedException {
		Session sess = HibernateWrapper.getSession().openSession();
		User user = null;
		try {
			user = UserDao.getByUsername(sess, username);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sess.close();
		}
		if (!authorize(user, permissionType)) throw new UserNotAuthorizedException();
		return user;
	}
	
	private static boolean authorize(User user, PermissionType permissionType) {
		if (user != null) {
			if (user.getProfile().getPermissions().containsKey(permissionType)) {
				return true;
			}
		}
		return false;
	}

	public static ReservationInterface getReservationSystem(User user) throws ConfigurationException {
	  Map<ParameterType, String> parameters = user.getProfile().getParameters();
	  if (parameters.containsKey(ParameterType.RESERVATION_SYSTEM_TYPE)) {
	  	String value = parameters.get(ParameterType.RESERVATION_SYSTEM_TYPE);
	  	if (value.equals(ReservationSystemType.SABRE.name())) {
	  		// TODO: Change to singleton?  Use spring?
	  		return new Reservation();
	  	}
	  }
	  
	  throw new ConfigurationException();
  }
}
