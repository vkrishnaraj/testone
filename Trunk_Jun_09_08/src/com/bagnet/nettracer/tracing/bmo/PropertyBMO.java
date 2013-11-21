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

import edu.emory.mathcs.backport.java.util.Arrays;

public class PropertyBMO {

	private static Logger logger = Logger.getLogger(PropertyBMO.class);

	private static ConcurrentHashMap<String, Object> propCache = new ConcurrentHashMap<String, Object>();

	// PROPERTIES USED TO LIMIT STATIONS TO CODE TO
	public static final String PROPERTY_LIMIT_MISSING_STATIONS = "losscode.missing.limitstations";
	public static final String PROPERTY_LIMIT_LD_STATIONS = "losscode.ld.limitstations";
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
	public static final String PROPERTY_DEFAULT_REPORT_SEVEN = "default.report.seven";

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

	public static final String PROPERTY_BAG_LEVEL_LOSS_CODES = "bag.level.loss.codes";
	public static final String PROPERTY_BAG_LEVEL_LOSS_CODES_ITIN_CHECK = "bag.level.loss.codes.itin.check";
	
	public static final String PROPERTY_NT_COMPANY_WEIGHT_UNIT_DEFAULT = "nt.company.weight.unit.default";

	public static final String PROPERTY_NT_COMPANY_TIME_RANGE_WITHIN_LAST = "nt.company.time.range.within.last";

	public static final String SEND_FORWARD_NOTIFICATIONS = "send.forward.notifications";

	public static final String CRM_INTEGRATION_ENDPOINT = "crm.integration.endpoint";
	public static final String US_CRM_COUNTRY_MAP = "us.crm.country.map";
	
	public static final String CONVERT_BAGTAG = "convert.bagtag";

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
	public static final String PROPERTY_BDO_RECEIPT_MOBILE_FIRST = "bdo_receipt_mobile_first";
	public static final String PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE = "allow_open_incident_dispute";
	public static final String PROPERTY_INCIDENT_CLOSE_STATION = "incident.close.station";

	public static final String PROPERTY_OIA_AGENT = "oia.agent";
	public static final String PROPERTY_OIA_HOURS_BEFORE_EXPIRE = "oia.hours.before.expire";
	public static final String PROPERTY_INCIDENT_CUSTOMCLEARED_SELECT = "incident.customcleared.pleaseselect";
	public static final String PROPERTY_INCIDENT_DISPUTE_SELECT = "incident.dispute.pleaseselect";
	public static final String PROPERTY_INCIDENT_DISPUTE_GETNEXT = "incident.dispute.getnext";
	public static final String PROPERTY_INCIDENT_FAULT_LOCK = "incident.dispute.faultlock";
	
	public static final String PROPERTY_TO_BE_INVENTORIED = "to.be.inventoried";
	
	public static final String PROPERTY_DEFAULT_DAM_CODE = "default.dam.code";
	public static final String PROPERTY_DEFAULT_PIL_CODE = "default.pil.code";

	public static final String CENTRAL_FRAUD_CHECK_ENABLED = "fraud.check.enabled";
	public static final String CENTRAL_FRAUD_CHECK_TIMEOUT = "fraud.check.timeout";

	public static final String CENTRAL_FRAUD_DAM_MISSING_TIMEOUT = "fraud.check.timeout.dam.mis";
	
	public static final String CENTRAL_FRAUD_SERVER_LOCATION = "fraud.server.location";
	public static final String CENTRAL_FRAUD_SERVICE_NAME = "fraud.server.name";
	public static final String CENTRAL_FRAUD_PERMISSION_SERVICE_NAME = "fraud.permissions.server.name";
	public static final String CENTRAL_FRAUD_TIMEOUT = "fraud.timeout";
	
	public static final String LF_EJB_SERVER_LOCATION = "lf.server.location";
	public static final String LF_EJB_SERVER_NAME = "lf.server.name";
	public static final String LF_AUTO_CLOSE_DAYS = "lf.auto.close";
	public static final String LF_AUTO_SALVAGE_DAYS = "lf.auto.salvage";
	public static final String LF_USER = "lf.user";
	public static final String LF_TRACE_CUTOFF = "lf.tracing.cutoff";
	public static final String LF_TRACE_FILTER_WEIGHT = "lf.tracing.filter.weight";
	
	public static final String FS_RETENTION_YEARS = "fs.retention.years";
	
	public static final String PROPERTY_WT_USE_STATE_NAME = "wt.state.name";
	public static final String PROPERTY_WT_USE_COUNTRY_NAME = "wt.country.name";
	
	public static final String PROPERTY_SHARES_THREAD_COUNT = "shares.thread.count";
	public static final String PROPERTY_SHARES_MAX_THREAD_COUNT = "shares.max.thread.count";
	
	public static final String PROPERTY_DELIVERY_INSTRUCTIONS ="bdo.delivery.instructions";

	public static final String LF_TRACING_WEIGHT_SERIAL = "lf.tracing.weight.serial";
	public static final String LF_TRACING_WEIGHT_NAME = "lf.tracing.weight.name";
	public static final String LF_TRACING_WEIGHT_LAST_NAME = "lf.tracing.weight.last.name";
	public static final String LF_TRACING_WEIGHT_BAG_NAME = "lf.tracing.weight.bag.name";
	public static final String LF_TRACING_WEIGHT_NICK_NAME = "lf.tracing.weight.nick.name";
	public static final String LF_TRACING_WEIGHT_ADDRESS = "lf.tracing.weight.address";
	public static final String LF_TRACING_WEIGHT_PHONE = "lf.tracing.weight.phone";
	public static final String LF_TRACING_WEIGHT_EMAIL = "lf.tracing.weight.email";
	public static final String LF_TRACING_WEIGHT_INITIAL_NAME = "lf.tracing.weight.initial.name";

	public static final String LF_TRACING_WEIGHT_MVA = "lf.tracing.weight.mva";
	public static final String LF_TRACING_WEIGHT_AGREEMENT = "lf.tracing.weight.agreement";
	public static final String LF_TRACING_BRAND = "lf.tracing.weight.brand";
	public static final String LF_TRACING_WEIGHT_SUBCATEGORY = "lf.tracing.weight.subcategory";
	public static final String LF_TRACING_WEIGHT_CATEGORY = "lf.tracing.weight.category";
	public static final String LF_TRACING_WEIGHT_COLOR = "lf.tracing.weight.color";
	public static final String LF_TRACING_WEIGHT_DESCRIPTION = "lf.tracing.weight.description";
	public static final String LF_TRACING_WEIGHT_LONG_DESCRIPTION = "lf.tracing.weight.longdescription";
	public static final String LF_TRACING_WEIGHT_MODEL = "lf.tracing.weight.model";
	public static final String LF_TRACING_WEIGHT_CASE_COLOR = "lf.tracing.weight.casecolor";
	public static final String LF_TRACING_WEIGHT_FLIGHT_NUMBER = "lf.tracing.weight.flightnumber";
	
	public static final String LF_EMAIL_RETURNADDR_INIT = "lf.email.returnaddr.init";
	public static final String LF_EMAIL_RETURNADDR_FIRST = "lf.email.returnaddr.first";
	public static final String LF_EMAIL_RETURNADDR_SECOND = "lf.email.returnaddr.second";
	public static final String LF_EMAIL_RETURNADDR_THIRD = "lf.email.returnaddr.third";
	public static final String LF_EMAIL_RETURNADDR_FORTH = "lf.email.returnaddr.forth";
	public static final String LF_EMAIL_RETURNADDR_FIFTH = "lf.email.returnaddr.fifth";
	public static final String LF_EMAIL_RETURNADDR_CLOSE = "lf.email.returnaddr.close";
	
	public static final String LF_EMAIL_ONLINE_BILLING = "lf.email.online.billing";
	
	public static final String LF_TRACING_USE_CACHE = "lf.tracing.use.cache";
	public static final String LF_TRACING_CACHE_SIZE = "lf.tracing.cache.size";
	public static final String LF_TRACING_CACHE_EXPIRE = "lf.tracing.cache.expire";
	public static final String LF_TRACING_CACHE_EXPIRE_RAND = "lf.tracing.cache.expire.rand";
	public static final String LF_TRACING_CACHE_CLEANUP_INTERVAL = "lf.tracing.cache.cleanup.interval";
	public static final String LF_TRACING_SECONDARY_TRACE = "lf.tracing.secondary.trace";
	public static final String LF_TRACING_ADDRESS = "lf.tracing.address";
	public static final String LF_TRACING_NAME = "lf.tracing.name";
	public static final String LF_TRACING_LAST_NAME = "lf.tracing.last.name";
	public static final String LF_TRACING_NICK_NAME = "lf.tracing.nick.name";
	public static final String LF_TRACING_SERIAL= "lf.tracing.serial";
	public static final String LF_TRACING_PHONE = "lf.tracing.phone";
	public static final String LF_TRACING_EMAIL= "lf.tracing.email";
	
	public static final String LIMITED_CODES_LOSSDELAY = "limited.codes.lossdelay";
	public static final String LIMITED_CODES_DAMAGED = "limited.codes.damaged";
	public static final String LIMITED_CODES_MISSING = "limited.codes.missing";
	public static final String LIMITED_CODES_DISPUTES = "limited.codes.disputes";
	
	public static final String THREAD_MONITOR_THREAD_TIMEOUT = "thread.monitor.thread.timeout";
	public static final String THREAD_MONITOR_EMAIL_INTERVAL = "thread.monitor.email.interval";
	
	public static final String WT_TAGLESS_AS_OAL = "wt.tagless.as.oal";
	
	public static final String DIRECT_ACCESS_URL = "direct.access.url";
	
	public static final String SCANQUERY_DAYS_BACK = "scanquery.days.back";

	public static final String LOCK_CACHE_CLUSTER = "lock.cache.cluster";
	
	public static final String AUTO_CLOSE_REMARK = "auto.close.remark";
	public static final String AUTO_CLOSE_REMARK_LOC = "auto.close.remark.loc";

	public static final String AUTO_CLOSE_OHD_REMARK = "auto.close.ohd.remark";
	
	public static final String TRACING_STATUS_BLOCK_WT = "tracing.status.block.wt";
	
	public static final String SPOKEN_LANGUAGE_LIST = "spoken.language.list";

	public static final String DUP_CLAIM_CACHE_EXPIRE_TIME = "dup.claim.cache.expire.time";
	
	public static final String HORNETQ_URL = "hornetq.url";
	public static final String HORNETQ_HOST = "hornetq.host";
	public static final String HORNETQ_PORT = "hornetq.port";
	public static final String HORNETQ_DEST = "hornetq.dest";
	public static final String HORNETQ_FACTORY = "hornetq.factory";
	public static final String HORNETQ_USER = "hornetq.user";
	public static final String HORNETQ_PASS = "hornetq.pass";
	public static final String HORNETQ_REMOTE_CREDS = "hornetq.remote.creds";
	public static final String HORNETQ_JMS_CREDS = "hornetq.jms.creds";
	public static final String HORNETQ_USE_JNDI = "hornetq.use.jndi";
	
	public static final String RES_WS_TIMEOUT = "res.ws.timeout";
	
	public static final String FEDEX_ADDRESS_ENDPOINT = "fedex.address.endpoint";
	public static final String FEDEX_RATE_ENDPOINT = "fedex.rate.endpoint";
	public static final String FEDEX_ACCOUNT_NUMBER = "fedex.account.number";
	public static final String FEDEX_METER_NUMBER = "fedex.meter.number";
	public static final String FEDEX_KEY = "fedex.key";
	public static final String FEDEX_PASSWORD = "fedex.password";

	public static final String BDSI_ADDRESS_ENDPOINT = "bsdi.address.endpoint";
	
	public static final String TRANSFIRST_USER_ID="transfirst.user.id";
	public static final String TRANSFIRST_REG_KEY="transfirst.reg.key";
	public static final String TRANSFIRST_ADDRESS_ENDPOINT="transfirst.address.endpoint";

	public static final String POPULATE_STORAGE_LOCATION = "populate.storage.location";

	public static final String SAVE_DATA_POPUP = "save.data.popup";
	
	public static final String CS2_EXPENSE_PAYCODE = "cs2.expense.paycode";
	public static final String CS2_EXPENSE_STATUS = "cs2.expense.status";
	public static final String CS2_EXPENSE_PAYTYPE = "cs2.expense.paytype";
	public static final String CS2_EXPENSE_CURRENCY = "cs2.expense.currency";


	public static final String MAX_PNR_LAST_DAYS = "max.pnr.last.days";
	
	public static final String SWA_SERVICE_ADDRESS_ENDPOINT = "swa.service.address.endpoint";
	public static final String BDO_WS_TIMEOUT = "bso.ws.timeout";
	
	public static final String NT_USER="nt.user";
	public static final String NTFS_USER="ntfs.user";
	
	public static final String DOMESIC_PAYOUT_VALUE="domestic.payout.value";
	
	public static final String FOUND_ITEM_RECEIPT_TEMPLATE = "found.item.receipt.template";
	
	public static final String DOCUMENT_LOCATION_LETTERS = "document.location.letters";
	public static final String DOCUMENT_LOCATION_RECEIPTS = "document.location.receipts";
	public static final String DOCUMENT_LOCATION_TEMP = "document.location.temp";
	
	public static final String RECEIPT_CUSTOM_TYPES = "receipt.custom.types";
	
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

	@SuppressWarnings("unchecked")
	public static List<String> getSplitList(String keyVal) {
		String value = getValue(keyVal);
		List<String> list=null;
		if(value!=null){
			list=Arrays.asList(value.split(","));
		}
		
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
