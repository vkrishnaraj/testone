package com.bagnet.nettracer.tracing.utils;

import java.io.IOException;
import java.util.Properties;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class TracerProperties {

	private static Properties properties = new Properties();
	
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
	public static final String SCANNER_CLASS_PATH = "scanner.class.path";
	public static final String RYNNS_ENDPOINT = "rynns.endpoint";
	public static final String SUPPRESSION_PRINTING_NONHTML = "suppress.print.nonhtml";
	
	
	
	static {
		try {
			properties.load(HibernateWrapper.class.getResourceAsStream("/tracer.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static String get(String property) {
		return properties.getProperty(property);
	}
	
	public static boolean isTrue(String property) {
		try {
			return properties.getProperty(property).equals("1");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
