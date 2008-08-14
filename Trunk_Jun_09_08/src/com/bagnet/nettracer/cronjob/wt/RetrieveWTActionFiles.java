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
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.BetaWtConnector;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerService;
import com.bagnet.nettracer.wt.WorldTracerUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class RetrieveWTActionFiles {

	private static Logger logger = Logger.getLogger(RetrieveWTActionFiles.class);

	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;

	private static String email_host;
	private static String email_from;
	private static int email_port;
	private static String email_to;


	private String company;
	int retrieve;

	private WTQueueBmo wqBmo;
	private WT_ActionFileBmo wafBmo;
	private WorldTracerService wtService;

	public void setWtService(WorldTracerService wtService) {
		this.wtService = wtService;
	}

	public RetrieveWTActionFiles(String companyCode) {
		try {

			company = companyCode;

			Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
			email_host = csv.getEmail_host();
			email_from = csv.getEmail_from();
			email_port = csv.getEmail_port();
			email_to = csv.getEmail_to();
		} catch (Exception e) {
			logger.fatal("unable to start move to lz thread: " + e);
		}

	}

	public String getcompany() {
		return company;
	}

	public void manageActionFiles() {
		deleteActionFiles();
		//retrieveActionFiles();
	}

	public void deleteActionFiles() {
		List<WT_Queue> tasks = wqBmo.getPendingTasksByCompanyType(company, WT_Queue.ERASE_AF_TYPE);

		for (WT_Queue task : tasks) {
			Worldtracer_Actionfiles waf;
			try {
				waf = new Worldtracer_Actionfiles(task.getType_id());
				waf.setAction_file_text(wafBmo.findTextForAf(waf));
				if (waf.getAction_file_text() == null) {
					//logger.warn("tried to delete action file " + task.getType_id() + "but not found in db");
					task.setQueue_status(-2);
					wqBmo.updateQueue(task);
					continue;
				}
				wtService.eraseActionFile(waf);
				task.setQueue_status(-1);
				wqBmo.updateQueue(task);
			} catch (Exception e) {
				logger.error("unable to delete action file for queue task: " + task.getWt_queue_id());
				task.setQueue_status(task.getQueue_status() + 1);
				continue;
			}
		}
	}

	public void retrieveActionFiles() {
		try {
			Company_Specific_Variable csv = AdminUtils.getCompVariable(company);

			if (csv == null || csv.getWt_enabled() != 1) {
				return;
			}

			List<String> stationList = StationBMO.getWorldTracerStations(company);
			for(String station : stationList) {
				logger.info("Downloading data for station: " + station);
				for (int i = 1; i < 8; i++) {

					parsewt_actionfiles(ActionFileType.AA, i, company, station);
					parsewt_actionfiles(ActionFileType.AP, i, company, station);
					parsewt_actionfiles(ActionFileType.CM, i, company, station);
					parsewt_actionfiles(ActionFileType.EM, i, company, station);
					parsewt_actionfiles(ActionFileType.FW, i, company, station);
					parsewt_actionfiles(ActionFileType.LM, i, company, station);
					parsewt_actionfiles(ActionFileType.PR, i, company, station);
					parsewt_actionfiles(ActionFileType.SP, i, company, station);
					parsewt_actionfiles(ActionFileType.WM, i, company, station);
				}
			}

		} catch (Exception e) {
			logger.fatal("****cron thread error: " + e);
		}

	}

	public void parsewt_actionfiles(ActionFileType actionFileType, int day, String airline, String station) {

		try {
			logger.debug("******** retrieving: " + actionFileType + ", day: " + day + "*************");
			
			List<Worldtracer_Actionfiles> actionFiles = wtService.getActionFiles(airline, station, actionFileType, day);

			//String result = WorldTracerUtils.getActionFiles(client, airline, station, actionFileType, day);

			wafBmo.deleteActionFiles(airline, station, actionFileType, day);
			
			for(Worldtracer_Actionfiles waf : actionFiles) {
				wafBmo.saveActionFile(waf);
			}

		} catch (Exception e) {
			logger.error("error retriving wt action file data", e);

			try {
				HtmlEmail he = new HtmlEmail();
				he.setHostName(email_host);
				he.setSmtpPort(email_port);
				he.setFrom(email_from);

				ArrayList<Address> al = new ArrayList<Address>();
				al.add(new InternetAddress(email_to));
				he.setTo(al);
				String msg = "please restart NT cronjob thread for environment indicated by sending email address.";
				msg += "\n\nError with retrieve wt action file in RetrieveWTActionFiles thread: \n" + e.toString();

				he.setHtmlMsg(msg);

				he.send();
			} catch (Exception maile) {
				logger.fatal("unable to send mail due to smtp error." + maile);
				// return new ActionMessage("error.unable_to_send_mail");
			}

		}

	}



	public void setWqBmo(WTQueueBmo wqBmo) {
		this.wqBmo = wqBmo;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}

}
