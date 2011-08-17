package com.bagnet.nettracer.tracing.bmo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class PropertyBMO {

	private static Logger logger = Logger.getLogger(PropertyBMO.class);

	private static ConcurrentHashMap<String, Object> propCache = new ConcurrentHashMap<String, Object>();

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
	public static final String PROPERTY_WT_AF_EXPIRE = "wt.af.expire";
	public static final String DISABLE_INC_REPORT = "disable.inc.report";
	public static final String PROPERTY_LIMIT_OHD_STATIONS = "losscode.ohd.limitstations";
	public static final String PROPERTY_LIMIT_OHD_ADDSTATIONS = "losscode.ohd.addstations";
	public static final String PROPERTY_DEFAULT_OHD_SEARCH_STATUS = "default.ohd.search.status";
	public static final String PROPERTY_PCN_ENABLED = "pcn.enabled";
	public static final String PROPERTY_TELEX_PRINTER = "telex.printer";

	public static final String PROPERTY_WT_CAPTCHA = "wt.captcha";
	public static final String RESERVATION_HOURS_FORWARD = "reservation.hours.forward";
	public static final String RESERVATION_HOURS_BACK = "reservation.hours.backward";
	public static final String PROPERTY_NT_RES_PASSWORD = "nt.res.password";
	public static final String PROPERTY_NT_RES_USERNAME = "nt.res.username";
	public static final String PROPERTY_NT_RES_OSI_ON = "nt.res.osi.on";

	public static final String PROPERTY_NT_BOOKING_ENDPOINT = "booking.nt.endpoint";

	public static final String HIGH_PRIORITY_PAX_COMMUNICATION_HOURS = "high.priority.pax.com.hours";

	public static final String PROPERTY_NT_CENTRAL_ENDPOINT = "nt.central.endpoint";
	public static final String PROPERTY_NT_CENTRAL_USERNAME = "nt.central.username";
	public static final String PROPERTY_NT_CENTRAL_PASSWORD = "nt.central.password";
	
	public static final String PROPERTY_NT_COMPANY_WEIGHT_UNIT_DEFAULT = "nt.company.weight.unit.default";

	public static final String PROPERTY_NT_COMPANY_TIME_RANGE_WITHIN_LAST = "nt.company.time.range.within.last";

	public static final String SEND_FORWARD_NOTIFICATIONS = "send.forward.notifications";

	public static final String CRM_INTEGRATION_ENDPOINT = "crm.integration.endpoint";
	public static final String US_CRM_COUNTRY_MAP = "us.crm.country.map";

	public static final String CUSTOM_DELAY_RECEIPT_FILES = "custom.delay.receipt.files";
	public static final String CUSTOM_DAMAGE_RECEIPT_FILES = "custom.damage.receipt.files";
	public static final String CUSTOM_MISSING_RECEIPT_FILES = "custom.missing.receipt.files";
	
	public static final String PROPERTY_BDO_CANCEL_EMAIL_ALERT = "bdo.cancle.email.alert";
	public static final String PROPERTY_BAG_ITIN_NOT_REQUIRED = "bag.itin.not.required";

	public static final String PROPERTY_LIMIT_DAMAGED_FAULT_AIRLINE = "limit.damaged.fault.airline";	
	public static final String PROPERTY_LIMIT_LD_FAULT_AIRLINE = "limit.ld.fault.airline";	
	public static final String PROPERTY_LIMIT_MISSING_FAULT_AIRLINE = "limit.missing.fault.airline";
	public static final String PROPERTY_LIMIT_OHD_FAULT_AIRLINE = "limit.ohd.fault.airline";
	public static final String DISPLAY_NON_REVENUE_CODES = "display.non.revenue.codes";
	
	public static final String PROPERTY_OIA_AGENT = "oia.agent";
	public static final String PROPERTY_OIA_HOURS_BEFORE_EXPIRE = "oia.hours.before.expire";
	
	public static final String PROPERTY_DEFAULT_DAM_CODE = "default.dam.code";
	public static final String PROPERTY_DEFAULT_PIL_CODE = "default.pil.code";

	public static final String CENTRAL_FRAUD_CHECK_ENABLED = "fraud.check.enabled";
	public static final String CENTRAL_FRAUD_CHECK_TIMEOUT = "fraud.check.timeout";

	public static final String CENTRAL_FRAUD_DAM_MISSING_TIMEOUT = "fraud.check.timeout.dam.mis";
	
	public static final String CENTRAL_FRAUD_SERVER_LOCATION = "fraud.server.location";
	public static final String CENTRAL_FRAUD_SERVICE_NAME = "fraud.server.name";
	public static final String CENTRAL_FRAUD_PERMISSION_SERVICE_NAME = "fraud.permissions.server.name";
	public static final String CENTRAL_FRAUD_TIMEOUT = "fraud.timeout";
	
	public static final String LF_AUTO_CLOSE_DAYS = "lf.auto.close";
	public static final String LF_AUTO_SALVAGE_DAYS = "lf.auto.salvage";
	
	public static final String FS_RETENTION_YEARS = "fs.retention.years";
	
	public static boolean updateProperty(String key, String value){
		boolean success = true;
		if(key != null && value != null){
			//We need to explicitly define which properties the application can change
			if(key.equals(FS_RETENTION_YEARS)){
				String sql = "update properties set valueStr = :value where keyStr = :key";
				Session sess = HibernateWrapper.getSession().openSession();
				
				try {
					Transaction t = sess.getTransaction();
					t.begin();
					Query q = sess.createSQLQuery(sql);
					
					q.setString("key", key);
					q.setString("value", value);
					q.executeUpdate();
					t.commit();
					ArrayList<String> list = new ArrayList<String>();
					list.add(value);
					propCache.put(key, list);
				} catch (Exception e) {
					logger.error("Error updating property " + key, e);
					success = false;
				} finally {
					sess.close();
				}
			}
		}
		return success;
	}
	
	
	/**
	 * Retrieves the value of the property from the database.
	 * 
	 * @param keyVal Key
	 * @return String property value.
	 * @throws HibernateException
	 */
	private static List<String> getValues(String keyVal) throws HibernateException {
		if(propCache.containsKey(keyVal)) {
			return (List<String>) propCache.get(keyVal);
		}
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery("select property.valueStr from com.bagnet.nettracer.tracing.db.Property property where keyStr= :key");
			q.setString("key", keyVal);
			List<String> list = q.list();
			propCache.put(keyVal, list);
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
			return ("1").equals(getValue(property));
		} catch (Exception e) {
			logger.warn("error checking boolean property " + property + ", returning false", e);
			return false;
		}
	}
	
	public static void resetCache() {
		propCache.clear();
	}

	public static int getValueAsInt(String keyVal) {
		try {
			return Integer.parseInt(getValue(keyVal));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static Object getObject(String key) {
		return propCache.get(key);
	}
	
	public static void setObject(String key, Object o) {
		propCache.put(key, o);
	}
	
}
