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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Session;

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
import com.bagnet.nettracer.wt.svc.WorldTracerService;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class RetrieveWTActionFiles {

	private static final String WTAF_HIGH_PRI_THREADS = "wtaf.high.pri.thread.count";

	private static final String WTAF_LOW_PRI_THREADS = "wtaf.low.pri.thread.count";

	private static Logger logger = Logger.getLogger(RetrieveWTActionFiles.class);

	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;
	
	private String company;
	int retrieve;

	private WT_ActionFileBmo wafBmo;
	private WorldTracerService wtService;

	private String instanceLabel;

	public void setWtService(WorldTracerService wtService) {
		this.wtService = wtService;
	}

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

	public void manageActionFiles() {
		Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
		if (csv == null || csv.getWt_enabled() != 1) {
			return;
		}
		
		int highConsumerCount = Integer.parseInt(PropertyBMO.getValue(WTAF_HIGH_PRI_THREADS));
		int lowConsumerCount = Integer.parseInt(PropertyBMO.getValue(WTAF_LOW_PRI_THREADS));
		
		ArrayBlockingQueue<RetrieveAfDTO> highQ = new ArrayBlockingQueue<RetrieveAfDTO>(highConsumerCount * 3);
		ArrayBlockingQueue<RetrieveAfDTO> lowQ = new ArrayBlockingQueue<RetrieveAfDTO>(lowConsumerCount * 3);
		
		//create the high priority producer
		RetrieveAfProducer p1 = new RetrieveAfProducer(new ActionFileType[] {ActionFileType.AA, ActionFileType.FW, ActionFileType.WM}, company, highQ);
		new Thread(p1).start();

		logger.info("started up the the high priority action file retrieval producer/consumer threads");
		
		RetrieveAfProducer p2 = new RetrieveAfProducer(new ActionFileType[] {ActionFileType.AP, ActionFileType.CM, ActionFileType.EM, ActionFileType.LM, ActionFileType.PR, ActionFileType.SP}, company, lowQ);
		new Thread(p2).start();
		
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		for(int i = 0; i < highConsumerCount; i++) {
			RetrieveAfConsumer c = new RetrieveAfConsumer(wafBmo, wtService, highQ);
			new Thread(c).start();
		}
		for(int i = 0; i < lowConsumerCount; i++) {
			RetrieveAfConsumer c = new RetrieveAfConsumer(wafBmo, wtService, lowQ);
			new Thread(c).start();
		}

	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}

}
