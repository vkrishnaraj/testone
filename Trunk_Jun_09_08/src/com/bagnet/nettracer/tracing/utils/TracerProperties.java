package com.bagnet.nettracer.tracing.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class TracerProperties {

	private static Properties properties = new Properties();
	
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public static final String APP_TYPE = "app_type";
	public static final String IMAGE_STORE = "image_store";
	public static final String INCIDENT_TAB_BAGGAGE = "inc.tab.baggage";
	public static final String INCIDENT_TAB_INC_INFORMATION = "inc.tab.incinfo";
	public static final String INCIDENT_TAB_INTERIM = "inc.tab.interim";
	public static final String INCIDENT_TAB_ITINERARY = "inc.tab.itinerary";
	public static final String INCIDENT_TAB_OSI = "inc.tab.osi";
	public static final String INCIDENT_TAB_PASSENGER = "inc.tab.passenger";
	public static final String INCIDENT_TAB_REMARKS = "inc.tab.remarks";
	public static final String INCIDENT_TAB_CUSTOMER_COMMENTS = "inc.tab.customercomments";
	public static final String RESERVATION_POPULATE_INCIDENT_ON = "booking.is_on";
	public static final String RESERVATION_CLASS_PATH = "reservation.class.path";
	public static final String RESERVATION_BY_BAGTAG = "reservation.bagtag";
	public static final String RESERVATION_UPDATE_COMMENT_ON = "updatecomment.is_on";
	public static final String RESERVATION_POPULATE_OHD_ON = "populate.onhand";
	public static final String RESERVATION_POPULATION_SEARCH = "populate.incident.bagtagsearch";
	public static final String SCANNER_CLASS_PATH = "scanner.class.path";
	public static final String SUPPRESSION_PRINTING_NONHTML = "suppress.print.nonhtml";
	public static final String EMAIL_REPORT_LD = "inc.receipt.email.ld";
	public static final String EMAIL_REPORT_DAM = "inc.receipt.email.dam";
	public static final String EMAIL_REPORT_PIL = "inc.receipt.email.pil";
	public static final String EMAIL_REPORT_LD_DISABLE_IMAGE = "inc.receipt.email.ld.disableimg";
	public static final String EMAIL_REPORT_DAM_DISABLE_IMAGE = "inc.receipt.email.dam.disableimg";
	public static final String EMAIL_REPORT_PIL_DISABLE_IMAGE = "inc.receipt.email.pil.disableimg";
	public static final String PROPERTY_REPORT_MAX_ROWS = "reports.max.rows";
	public static final String FRENCH_STATIONS = "french.stations";
	public static final String SAVE_ON_CLOSE_PAGE = "display.closepage.save.while.open";
	public static final String RESERVATION_UPDATE_EXPENSES_ON = "updatecomment.expenses.is_on";
	public static final String ALLOW_CACHING = "allow.caching";
	public static final String DEFAULT_CHECKED_LOCATION = "default.checked.location";
	
	public static final String BDO_LABEL_CUSTOMER_INFORMATION="bdo.label.customer.information";
	public static final String BDO_LABEL_NAME="bdo.label.name";
	public static final String BDO_LABEL_ADDRESS="bdo.label.address";
	public static final String BDO_LABEL_PHONE_NUMBER="bdo.label.phone.number";
	public static final String BDO_LABEL_WORK_NUMBER="bdo.label.work.number";
	public static final String BDO_LABEL_HOTEL="bdo.label.hotel";
	public static final String BDO_LABEL_HOTEL_NUMBER="bdo.label.hotel.number";
	public static final String BDO_LABEL_MOBILE_NUMBER="bdo.label.mobile.number";
	public static final String BDO_LABEL_BAG_INFORMATION="bdo.label.bag.information";
	public static final String BDO_LABEL_DELIVERY_INFORMATION="bdo.label.delivery.information";
	public static final String BDO_LABEL_NUMBER_BAGS="bdo.label.number.bags";
	public static final String BDO_LABEL_BAG_NUMBER="bdo.label.bag.number";
	public static final String BDO_LABEL_COLOR="bdo.label.color";
	public static final String BDO_LABEL_TYPE="bdo.label.type";
	public static final String BDO_LABEL_RECEIVED_BY="bdo.label.received.by";
	public static final String BDO_LABEL_DELIVERY_INSTRUCTIONS="bdo.label.delivery.instructions";
	public static final String BDO_LABEL_CHARGES="bdo.label.charges";
	public static final String BDO_LABEL_DELIVERY_COMPANY="bdo.label.delivery.company";
	public static final String BDO_LABEL_SERVICE_LEVEL="bdo.label.service.level";
	public static final String BDO_LABEL_PICKUPDATE_AND_TIME="bdo.label.pickup.date.time";		
	public static final String BDO_LABEL_PICKUPDATE="bdo.label.pickup.date";	
	public static final String BDO_LABEL_REFERENCE_NUMBER="bdo.label.reference.number";
	public static final String BDO_LABEL_DELIVERY_ADDRESS="bdo.label.delivery.address";

	private static final Logger logger = Logger.getLogger(TracerProperties.class);

	public static final String IGNORE_CHECK_DIGIT = "ignoreCheckDigit";
	public static final String SET_DEFAULT_AIRLINE = "set.default.fault.airline";

	static {
		try {
			properties.load(TracerProperties.class
					.getResourceAsStream("/tracer.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String property) {
		
		try {
			lock.readLock().lock();
			return properties.getProperty(property);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public static String get(String airline, String property) {
		
		try {
			lock.readLock().lock();
			if(airline!=null && properties.getProperty(airline.toLowerCase()+"."+property)!=null)
				return properties.getProperty(airline.toLowerCase()+"."+property);
			else
				return properties.getProperty(property);
		} finally {
			lock.readLock().unlock();
		}
	}

	public static boolean isTrue(String property) {
		String value = null;
		try {
			lock.readLock().lock();
			value = properties.getProperty(property);
		}finally {
			lock.readLock().unlock();
		}
		if ( value != null) {
			return value.equals("1");
		} else {
			// If the value is not found a null value will be returned
			// and we need to return false.
			return false;
		}
	}
	
	public static boolean isTrue(String airline, String property) {
		String value = null;
		try {
			lock.readLock().lock();
			if(airline!=null && properties.getProperty(airline.toLowerCase()+"."+property)!=null){
				value =  properties.getProperty(airline.toLowerCase()+"."+property);
			} else {
				value = properties.getProperty(property);
			}

		}finally {
			lock.readLock().unlock();
		}
		if ( value != null) {
			return value.equals("1");
		} else {
			// If the value is not found a null value will be returned
			// and we need to return false.
			return false;
		}
	}
	
	public static int getMaxReportRows(String airline) {
		return Integer.parseInt(TracerProperties.get(airline, PROPERTY_REPORT_MAX_ROWS));
	}
	
	public static int getMaxReportRows() {
		return Integer.parseInt(TracerProperties.get(null, PROPERTY_REPORT_MAX_ROWS));
	}
	
	public static String getInstanceLabel() {
		String instanceRef = System.getProperty("instance.ref");
		if (instanceRef != null) {
			return instanceRef;
		}
		return "";
	}
	
	public static boolean isFrontend() {
		String instanceRef = System.getProperty("instance.ref");
		if (instanceRef != null) {
			return instanceRef.startsWith("I");
		}
		return false;
	}
	
	public static void reloadProperties() {
		try {
		lock.writeLock().lock();
		properties.clear();
		properties.load(TracerProperties.class
				.getResourceAsStream("/tracer.properties"));
		} catch (IOException e) {
			logger.error("unable to reload properties", e);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
