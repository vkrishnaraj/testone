/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.wt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;
import com.bagnet.nettracer.wt.connector.WorldTracerConnectionException;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class RetrieveWTActionFiles implements ApplicationContextAware {

	private static final String WTAF_THREAD_COUNT = "wtaf.ng.thread.count";

	private static Logger logger = Logger.getLogger(RetrieveWTActionFiles.class);
	
	private WT_ActionFileBmo wafBmo;

	public WT_ActionFileBmo getWafBmo() {
		return wafBmo;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}
	
	private String company;
	int retrieve;

	private String instanceLabel;

	private ApplicationContext applicationContext;

	public RetrieveWTActionFiles(String companyCode, String instanceLabel) {
		try {

			company = companyCode;
			this.instanceLabel = instanceLabel;
		} catch (Exception e) {
			logger.fatal("unable to initialize retrieve action file bean: " + e);
		}

	}

	public String getcompany() {
		return company;
	}

	public void manageActionFiles() throws Exception {
		Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
		if (csv == null || csv.getWt_enabled() != 1) {
			return;
		}
		
		
		
		List<String> stationList = StationBMO.getWorldTracerStations(company);
		
		int threadCount = Integer.parseInt(PropertyBMO.getValue(WTAF_THREAD_COUNT));
		
		if(stationList.size() < 1 || threadCount < 1) {
			logger.info("no wt stations, so no action files pulled");
			return;
		}
		
		CountDownLatch doneSignal = new CountDownLatch(threadCount);
		
		ArrayBlockingQueue<String> stationQ = new ArrayBlockingQueue<String>(stationList.size());
		stationQ.addAll(stationList);
		
		//create the high priority producer

		for(int i = 0; i < threadCount; i++) {
			ActionFileWorker afw = new ActionFileWorker(stationQ, doneSignal, company, wafBmo);
			WorldTracerConnector conn = (WorldTracerConnector) applicationContext.getBean("wtConn");
			afw.setConn(conn);
			new Thread(afw).start();
		}

		//wait for all threads to finish
		doneSignal.await();
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.applicationContext = ctx;
		
	}


}
