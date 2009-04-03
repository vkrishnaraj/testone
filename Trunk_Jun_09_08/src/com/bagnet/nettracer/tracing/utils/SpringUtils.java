package com.bagnet.nettracer.tracing.utils;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.integrations.reports.CustomReportBMO;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.svc.ActionFileManager;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class SpringUtils {

	private static AbstractApplicationContext ctx = null;
	
	public final static String RESERVATION_INTEGRATION = "reservationIntegration";
	public final static String SCANNER_DATA_SOURCE = "scannerDataSource";
	public final static String CUSTOM_REPORT_BMO = "customReportBmo";
	public final static String WORLDTRACER_SERVICE = "wtService";
	public final static String EVENT_HANDLER = "eventHandler";

	private static final String WORLDTRACER_CONNECTOR = "wtConnector";

	private static final String WORLDTRACER_TX_BMO = "wtTx-bmo";

	private static final String ACTIONFILE_MANAGER = "actionFileManager";
	
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
	
	public static WorldTracerService getWorldTracerService() {
		return (WorldTracerService) getBean(WORLDTRACER_SERVICE);
	}
	
	public static WorldTracerConnector getWorldTracerConnector() {
		return (WorldTracerConnector) getBean(WORLDTRACER_CONNECTOR);
	}
	
	public static ClientEventHandler getClientEventHandler() {
		return (ClientEventHandler) getBean(EVENT_HANDLER);
	}

	public static WtTransactionBmo getWtTxBmo() {
		return (WtTransactionBmo) getBean(WORLDTRACER_TX_BMO);
	}

	public static ActionFileManager getActionFileManager() {
		// TODO Auto-generated method stub
		return (ActionFileManager) getBean(ACTIONFILE_MANAGER);
	}
}
