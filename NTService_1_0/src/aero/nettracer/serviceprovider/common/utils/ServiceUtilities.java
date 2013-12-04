package aero.nettracer.serviceprovider.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.dao.UserDao;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.ReservationSystemType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.ws_1_0.res.FlightServiceInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.res.VoucherServiceInterface;

public class ServiceUtilities {

	private static Logger logger = Logger.getLogger(ServiceUtilities.class);
	private static Logger log = Logger.getLogger("authentication");
	
	public static User getAndAuthorizeUser(String username, String password,
			PermissionType permissionType) throws UserNotAuthorizedException {
		log.info("Authorizing User: "+username+" for permission type: "+permissionType.toString());
		Session sess = HibernateWrapper.getSession().openSession();
		User user = null;
		try {
			user = UserDao.getByUsername(sess, username);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sess.close();
		}
		if (!authorize(user, permissionType)){
			log.info("User: "+username+" for permission type: "+permissionType.toString()+" Authorization successful!");
			throw new UserNotAuthorizedException();
		} else {
			log.info("User: "+username+" for permission type: "+permissionType.toString()+" Authorization failed!");
		}
		return user;
	}

	public static boolean authorize(User user, PermissionType permissionType) {
		if (user != null) {
			if (user.getProfile().getPermissions().containsKey(permissionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns Reservation implementation based on the reservation system specified by the user profile
	 * TODO - consider using Spring injection (jiri NT-1292)
	 * 
	 * @param user
	 * @return
	 * @throws ConfigurationException
	 */
	public static ReservationInterface getReservationSystem(User user)
			throws ConfigurationException {
		Map<ParameterType, String> parameters = user.getProfile()
				.getParameters();
		if (parameters.containsKey(ParameterType.RESERVATION_SYSTEM_TYPE)) {
			String value = parameters
					.get(ParameterType.RESERVATION_SYSTEM_TYPE);
			if (value.equals(ReservationSystemType.SABRE.name())) {
				// TODO: Change to singleton? Use spring?
				return new Reservation();
			} else if (value.equals(ReservationSystemType.SPIRIT.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.spirit.Reservation();
			} else if (value.equals(ReservationSystemType.WEBJET.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.webjet.Reservation();
			} else if (value.equals(ReservationSystemType.RADIXX.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.radixx.Reservation();
			} else if (value.equals(ReservationSystemType.NAVITAIRE.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.navitaire.Reservation();
			} else if (value.equals(ReservationSystemType.CEBS.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.cebs.Reservation();
			}
		}

		throw new ConfigurationException();
	}
	
	/**
	 * Returns FlightService implementation based on the reservation system specified by the user profile
	 * TODO - consider using Spring injection (jiri NT-1292)
	 * 
	 * @param user
	 * @return
	 * @throws ConfigurationException
	 */
	public static FlightServiceInterface getFlightService(User user) throws ConfigurationException {
		Map<ParameterType, String> parameters = user.getProfile()
				.getParameters();
		if (parameters.containsKey(ParameterType.RESERVATION_SYSTEM_TYPE)) {
			String value = parameters
					.get(ParameterType.RESERVATION_SYSTEM_TYPE);
			if (value.equals(ReservationSystemType.CEBS.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.cebs.FlightService();
			}
		}
		throw new ConfigurationException();
	}
	
	/**
	 * Returns VoucherService implementation based on the voucher/reservation system specified by the user profile
	 * 
	 * Currently SWA is the only implementation, they handle vouchers through their res system.  For any future voucher
	 * integrations that is not part of a reservation system, consider creating and mapping the implementation class to the
	 * aero.nettracer.serviceprovider.ws_1_0.voucher package.  Alternately, consider abstracting the res package.
	 * 
	 * TODO - consider using Spring injection (jiri NT-1292)
	 * 
	 * @param user
	 * @return
	 * @throws ConfigurationException
	 */
	public static VoucherServiceInterface getVoucherService(User user) throws ConfigurationException {
		Map<ParameterType, String> parameters = user.getProfile()
				.getParameters();
		if (parameters.containsKey(ParameterType.VOUCHER_SYSTEM_TYPE)) {
			String value = parameters
					.get(ParameterType.VOUCHER_SYSTEM_TYPE);
			if (value.equals(ReservationSystemType.CEBS.name())) {
				// TODO: Change to singleton? Use spring?
				return new aero.nettracer.serviceprovider.ws_1_0.res.cebs.VoucherService();
			}
		}
		throw new ConfigurationException();
	}

	public static ArrayList<String> splitOnWordBreak(String srcWord,
			int maxLength) {
		ArrayList<String> list = new ArrayList<String>();
		maxLength += 1;
		int divide = 0;
		int endIndex = 0;
		for (int i = 0; i < srcWord.length();) {
			endIndex = java.lang.Math.min(i + maxLength, srcWord.length());
			divide = getIndexToDivide(srcWord.trim().substring(i, endIndex),
					" ", maxLength - 1);
			list.add(srcWord.trim().substring(i, i + divide).trim());
			i += divide;
		}

		return list;
	}

	private static int getIndexToDivide(String srcStr, String delimiter,
			int maxLength) {
		if (srcStr.length() < maxLength) {
			return srcStr.length();
		}
		int index = srcStr.lastIndexOf(delimiter);
		if (index > 0) {
			return index;
		} else {
			return srcStr.length() - 1;
		}
	}


	public static Date getGMTDate() {
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat(ServiceConstants.DB_DATETIMEFORMAT);
			df.setTimeZone(TimeZone.getTimeZone("GMT"));

			Date newdate = convertToDate(df.format(now), ServiceConstants.DB_DATETIMEFORMAT,
					null);

			return newdate;
		} catch (Exception e) {
			return new Date();
		}

	}

	public static Date convertToDate(String str, String instyle, String inloc) {
		return convertToDate(str, instyle, inloc, null);
	}
	
	public static Date convertToDate(String str, String instyle, String inloc, TimeZone startTZ) {
		try {
			Locale locale = null;
			if (inloc == null) locale = Locale.US;
			else locale = new Locale(inloc);
			if (instyle == null || instyle.equals("")) instyle = ServiceConstants.DB_DATEFORMAT;

			if (str == null || str.length() <= 0) return null;

			SimpleDateFormat df = new SimpleDateFormat(instyle, locale);
			if(startTZ != null) {
				df.setTimeZone(startTZ);
			}
			Date mydate = df.parse(str);
			Calendar c = Calendar.getInstance();
			c.setTime(mydate);
			if (c.get(Calendar.YEAR) < 1900) return null;

			return mydate;
		} catch (Exception e) {
			logger.warn("user entered bad date format: " + instyle);
		}
		return null;
	}

}
