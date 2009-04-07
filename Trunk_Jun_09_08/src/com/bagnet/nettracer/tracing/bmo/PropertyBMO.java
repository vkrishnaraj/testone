package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class PropertyBMO {
	
	private static Logger logger = Logger.getLogger(PropertyBMO.class);
	
	// PROPERTIES USED TO LIMIT STATIONS TO CODE TO
	public static final String PROPERTY_LIMIT_LD_STATIONS = "losscode.ld.limitstations";
	public static final String PROPERTY_LIMIT_MISSING_STATIONS = "losscode.missing.limitstations";
	public static final String PROPERTY_LIMIT_DAMAGED_STATIONS = "losscode.dam.limitstations";
	public static final String PROPERTY_LIMIT_LD_ADDSTATIONS = "losscode.ld.addstations";
	public static final String PROPERTY_LIMIT_MISSING_ADDSTATIONS = "losscode.missing.addstations";
	public static final String PROPERTY_LIMIT_DAMAGED_ADDSTATIONS = "losscode.dam.addstations";
	public static final String PROPERTY_TRACING_SECONDARY_COLOR_PERCENT = "tracing.secondary.color";
	public static final String PROPERTY_TRACING_TERTIARY_COLOR_PERCENT = "tracing.tertiary.color";
	public static final String PROPERTY_TRACING_SECONDARY_TYPE_PERCENT = "tracing.secondary.type";
	public static final String PROPERTY_TRACING_TERTIARY_TYPE_PERCENT = "tracing.tertiary.type";
	public static final String PROPERTY_BOOKING_ENDPOINT = "booking.endpoint";
	public static final String PROPERTY_DEFAULT_COUNTRY = "default.country";
	public static final String PROPERTY_NUM_CONTENT_FIELDS = "num.content.fields";
	public static final String PROPERTY_WT_FWD_ONLY = "wt.fwd.only";
	public static final String PROPERTY_SET_DEFAULT_MEMBERSHIP = "set.default.membership";
	public static final String WT_PRODUCTION_MODE = "wt.production.mode";
	public static final String PROPERTY_WT_QUEUE_ATTEMPTS = "wt.queue.attempts";

	/**
	 * Retrieves the value of the property from the database.
	 * 
	 * @param keyVal Key
	 * @return String property value.
	 * @throws HibernateException
	 */
	private static List<String> getValues(String keyVal) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery("select property.valueStr from com.bagnet.nettracer.tracing.db.Property property where keyStr= :key");
			q.setString("key", keyVal);
			List<String> list = q.list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return list;
		} catch (Exception e) {
			logger.error("Error retreiving property " + keyVal, e);
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static String getValue(String keyVal) {
		List<String> list = getValues(keyVal);
		
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	public static List<String> getValueList(String keyVal) {
		List<String> list = getValues(keyVal);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	public static boolean isTrue(String property) {
		try {
			return getValue(property).equals("1");
		} catch (Exception e) {
			logger.warn("error checking boolean property " + property + ", returning false", e);
			return false;
		}
	}
}
