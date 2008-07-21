/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class RetrieveWTActionFiles extends Thread {

	private static ArrayList threads;
	private static Logger logger = Logger
			.getLogger(RetrieveWTActionFiles.class);

	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;

	private static String email_host;
	private static String email_from;
	private static int email_port;
	private static String email_to;

	private static int min_to_retrieve = 1440;

	private String company;
	int retrieve;
	HttpClient client = null;

	public RetrieveWTActionFiles(Properties properties) {
		try {

			company = properties.getProperty("company.code");

			Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
			email_host = csv.getEmail_host();
			email_from = csv.getEmail_from();
			email_port = csv.getEmail_port();
			email_to = csv.getEmail_to();
			min_to_retrieve = csv.getRetrieve_actionfile_interval();
		} catch (Exception e) {
			logger.fatal("unable to start move to lz thread: " + e);
		}

	}

	public String getcompany() {
		return company;
	}

	public void run() {
		while (true) {
			try {

				// company = "US";

				this.client = WorldTracerUtils.connectWT(WorldTracerUtils
						.getWt_suffix_airline(company)
						+ "/", company);

				List<Station> stationList = AdminUtils.getStations(null,
						company, 0, 0);
				String station = null;
				for (int x = 0; x < stationList.size(); ++x) {
					station = stationList.get(x).getStationcode();
					logger.info("Downloading data for station: " + station);
					for (int i = 1; i < 8; i++) {
						// for (int i = 1; i < 2; i++) {
						parsewt_actionfiles("FW", Integer.toString(i), company,
								station);
						parsewt_actionfiles("AA", Integer.toString(i), company,
								station);
						parsewt_actionfiles("WM", Integer.toString(i), company,
								station);
						parsewt_actionfiles("SP", Integer.toString(i), company,
								station);
						parsewt_actionfiles("AP", Integer.toString(i), company,
								station);
						parsewt_actionfiles("EM", Integer.toString(i), company,
								station);
						parsewt_actionfiles("CM", Integer.toString(i), company,
								station);
						parsewt_actionfiles("LM", Integer.toString(i), company,
								station);
						parsewt_actionfiles("PR", Integer.toString(i), company,
								station);
					}
				}

				logger
						.info("waiting for 24 hours to retrieve action files again...");

			} catch (Exception e) {
				logger.fatal("****cron thread error: " + e);
			}

			pause(min_to_retrieve * 60);

		}

	}

	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}

	public void parsewt_actionfiles(String wt_type, String day, String airline,
			String station) {
		Session sess = null;

		try {
			System.out.println("******** retrieving: " + wt_type + ", day: "
					+ day + "*************");

			sess = HibernateWrapper.getSession().openSession();
			Connection mbr_conn = sess.connection();
			Statement stmt = mbr_conn.createStatement();
			ResultSet rs = null;

			String result = WorldTracerUtils.getActionFiles(client, airline,
					station, wt_type, day);
			// System.out.println("Results of action file: \n" + result);
			Worldtracer_Actionfiles wa = null;
			String parsed = "";

			String sql = "";

			String ac_start = "<input type=\"hidden\" name=\"menuITEM\" value=\"";
			String ac_end = "\">";
			String ahl_start = " AHL ";
			String ahl_start2 = "\nAHL ";
			String ahl_start3 = " A/";

			String ohd_start = " OHD ";
			String ohd_start2 = "\nOHD ";
			String ohd_start3 = " O/";

			String wt_ahl_id = "";
			String wt_ohd_id = "";
			double percent = 0;
			// sql = "delete from worldtracer_actionfiles where day = '" + day +
			// "' and station = '" + station + "' and airline = '" + airline +
			// "' and action_file_type = '" + wt_type + "'";
			// stmt.executeUpdate(sql);
			// first set all items to be deleted
			stmt
					.executeUpdate("update worldtracer_actionfiles set delete_trigger = 1");

			while ((parsed = StringUtils.ParseWTString2(result, ac_start,
					ac_end)) != null) {
				// System.out.println("Parsed Item: \n" + parsed);
				wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start, 10);
				if (wt_ahl_id == null)
					wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start2,
							10);
				if (wt_ahl_id == null)
					wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start3,
							10);
				if (wt_ahl_id != null) {
					if (wt_ahl_id.length() < 10)
						wt_ahl_id = null;
					else {
						// need to be alphanumeric
						if (!StringUtils.isAlphanumeric(wt_ahl_id))
							wt_ahl_id = null;
					}
				}
				if (wt_ahl_id == null)
					wt_ahl_id = "";

				wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start, 10);
				if (wt_ohd_id == null)
					wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start2,
							10);
				if (wt_ohd_id == null)
					wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start3,
							10);
				if (wt_ohd_id != null) {
					if (wt_ohd_id.length() < 10)
						wt_ohd_id = null;
					else {
						// need to be alphanumeric
						if (!StringUtils.isAlphanumeric(wt_ohd_id))
							wt_ohd_id = null;
					}
				}
				if (wt_ohd_id == null)
					wt_ohd_id = "";
				// check to see if this file already exist, if not, then insert,
				// if already exist, then set trigger to 0 so it doesn't get
				// deleted
				rs = stmt
						.executeQuery("select id from worldtracer_actionfiles where action_file_text = '"
								+ parsed.replace("'", "''") + "'");
				if (rs.next()) {
					// has this action file already, do not update and set
					// delete trigger to 0
					stmt
							.executeUpdate("update worldtracer_actionfiles set delete_trigger = 0 where id = '"
									+ rs.getInt("id") + "'");

				} else {
					if (wt_type != null && wt_type.equalsIgnoreCase("wm")) {
					//judging the action_file_type is or not "WM" 
						if (parsed.indexOf("SCORE") != -1) {
							//judging the action_file_text have the "score" substring
							int i = parsed.lastIndexOf("-");
							try {
								//catch the score and insert it into table worldtracer_actionfiles
								StringBuffer br = new StringBuffer();
								for (int j = 0; j <= 4; j++) {
									if (!parsed.substring(i + j + 2, i + j + 3)
											.equals(" ")) {
										br.append(parsed.substring(i + j + 2, i
												+ j + 3));
										System.out.println(br.toString());
									} else {
										break;
									}
								}
								percent = Double.parseDouble(br.toString());
								sql = "insert into worldtracer_actionfiles (action_file_type,action_file_text,day,station,airline,wt_incident_id,wt_ohd_id,delete_trigger,percent) values "
										+ "('"
										+ wt_type.toUpperCase()
										+ "','"
										+ parsed.replace("'", "''")
										+ "','"
										+ day
										+ "','"
										+ station
										+ "','"
										+ airline
										+ "','"
										+ wt_ahl_id
										+ "','"
										+ wt_ohd_id + "',0,'" + percent + "')";
								stmt.executeUpdate(sql);
								System.out
										.println("File inserted............................");
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							//action_file_text have not the score substring,insert 0 into table worldtracer_actionfiles 
							percent=0;
							sql = "insert into worldtracer_actionfiles (action_file_type,action_file_text,day,station,airline,wt_incident_id,wt_ohd_id,delete_trigger,percent) values "
									+ "('"
									+ wt_type.toUpperCase()
									+ "','"
									+ parsed.replace("'", "''")
									+ "','"
									+ day
									+ "','"
									+ station
									+ "','"
									+ airline
									+ "','"
									+ wt_ahl_id
									+ "','"
									+ wt_ohd_id
									+ "',0,'"
									+ percent + "')";
							stmt.executeUpdate(sql);
							System.out
									.println("File inserted............................");
						}
					} else {
						//action_file_type is not the WM,insert 0 into table worldtracer_actionfiles
						percent=0;
						sql = "insert into worldtracer_actionfiles (action_file_type,action_file_text,day,station,airline,wt_incident_id,wt_ohd_id,delete_trigger,percent) values "
								+ "('"
								+ wt_type.toUpperCase()
								+ "','"
								+ parsed.replace("'", "''")
								+ "','"
								+ day
								+ "','"
								+ station
								+ "','"
								+ airline
								+ "','"
								+ wt_ahl_id
								+ "','"
								+ wt_ohd_id
								+ "',0,'"
								+ 0
								+ "')";
						stmt.executeUpdate(sql);
						System.out
								.println("File inserted............................");
					}
				}

				int loc = result
						.indexOf("<input type=\"hidden\" name=\"menuITEM\" value=\"");
				result = result.substring(loc + 1);

			}
			// now delete all the rows with delete_trigger = 1 and day and type
			// match
			stmt
					.executeUpdate("delete from worldtracer_actionfiles where delete_trigger = 1 and action_file_type = '"
							+ wt_type.toUpperCase()
							+ "' and day = '"
							+ day
							+ "'");
			sess.disconnect();

		} catch (Exception e) {
			System.out.println("error retriving wt data: " + e.toString());
			e.printStackTrace();

			try {
				HtmlEmail he = new HtmlEmail();
				he.setHostName(email_host);
				he.setSmtpPort(email_port);
				he.setFrom(email_from);

				ArrayList al = new ArrayList();
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

}
