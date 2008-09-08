package com.bagnet.nettracer.tracing.utils;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bagnet.nettracer.integrations.reports.CustomReportBMO;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;

public class SpringUtils {

	private static AbstractApplicationContext ctx = null;
	
	public final static String RESERVATION_INTEGRATION = "reservationIntegration";
	public final static String SCANNER_DATA_SOURCE = "scannerDataSource";
	public final static String CUSTOM_REPORT_BMO = "customReportBmo";
	
	static {
		try {
			ctx = new ClassPathXmlApplicationContext("/spring-beans.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getBean(String beanName) {
		if (ctx == null) {
			return null;
		}
		return ctx.getBean(beanName);
	}
	
	public static ReservationIntegration getReservationIntegration() {
		return (ReservationIntegration) getBean(RESERVATION_INTEGRATION);
	}
	
	public static CustomReportBMO getCustomReportBMO() {
		return (CustomReportBMO) getBean(CUSTOM_REPORT_BMO);
	}
	
}
