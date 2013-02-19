package com.bagnet.nettracer.tracing.utils;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheManager;
import org.jboss.ha.framework.server.CacheManagerLocator;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.integrations.reports.CustomReportBMO;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.general.ThreadHandler;
import com.bagnet.nettracer.tracing.utils.lock.LockFile;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.svc.ActionFileManager;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class SpringUtils {

	private static AbstractApplicationContext ctx = null;
	
	public final static String RESERVATION_INTEGRATION = "reservationIntegration";
	public final static String SCANNER_DATA_SOURCE = "scannerDataSource";
	public final static String CUSTOM_REPORT_BMO = "customReportBmo";
	public final static String WORLDTRACER_SERVICE = "wtService";
	public final static String EVENT_HANDLER = "eventHandler";
	public final static String PRECODER = "precoder";
	
	public final static String SHARES_INTEGRATION = "sharesIntegration";

	private static final String WORLDTRACER_CONNECTOR = "wtConnector";
	private static final String WORLDTRACER_CONNECTION_POOL = "wtConnectionPool";
	private static final String WORLDTRACER_RULE_MAP = "wtRuleMap";

	private static final String WORLDTRACER_TX_BMO = "wtTx-bmo";

	private static final String ACTIONFILE_MANAGER = "actionFileManager";
	
	private static final String LOCK_BMO = "lockBMO";
	
	private static final String INCIDENT_LOCK = "incidentLock";
	
	static {
		try {
			ctx = new ClassPathXmlApplicationContext("/spring-beans.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
	
	public static void init() {
//		try {
//			CacheManager cacheManager = CacheManagerLocator.getCacheManagerLocator().getCacheManager( null );
//			Cache<Object, Object> myCache = cacheManager.getCache( PropertyBMO.getValue(PropertyBMO.LOCK_CACHE_CLUSTER), true );
//			myCache.start();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
	}
	
	public static ReservationIntegration getReservationIntegration() {
		return (ReservationIntegration) getBean(RESERVATION_INTEGRATION);
	}
	
	public static CustomReportBMO getCustomReportBMO() {
		return (CustomReportBMO) getBean(CUSTOM_REPORT_BMO);
	}
	
	public static Precoder getPrecoder(){
		return (Precoder) getBean(PRECODER);
	}
	
	public static WorldTracerService getWorldTracerService() {
		return (WorldTracerService) getBean(WORLDTRACER_SERVICE);
	}
	
//	public static WorldTracerConnector getWorldTracerConnector() {
//		return (WorldTracerConnector) getBean(WORLDTRACER_CONNECTOR);
//	}
	
	public static ClientEventHandler getClientEventHandler() {
		return (ClientEventHandler) getBean(EVENT_HANDLER);
	}

	public static WtTransactionBmo getWtTxBmo() {
		return (WtTransactionBmo) getBean(WORLDTRACER_TX_BMO);
	}

	public static ActionFileManager getActionFileManager() {
		return (ActionFileManager) getBean(ACTIONFILE_MANAGER);
	}
	
	public static LockBMO getLockBmo(){
		return (LockBMO) getBean(LOCK_BMO);
	}
	
	public static RuleMapper getWtRuleMap() {
		return (RuleMapper) getBean(WORLDTRACER_RULE_MAP);
	}
	
	public static ThreadHandler getSharesIntegrationHandler(){
		return (ThreadHandler) getBean(SHARES_INTEGRATION);
	}
	
	public static LockFile getLockFile(){
		return (LockFile) getBean(INCIDENT_LOCK);
	}
}
