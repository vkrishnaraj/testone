/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.tracing;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.match.IncidentElements;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class MainMBRThread extends Thread {

	private static ArrayList threads;
	private static Logger logger = Logger.getLogger(MainMBRThread.class);

	private final static int MAX_RECONNECT_ATTEMPTS = 10;
	
	private static int TOTAL_THREADS = 1;
	
	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;
	
	// dateformat
	public final static String DB_DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DB_DATEFORMAT = "yyyy-MM-dd";
	public final static String DB_TIMEFORMAT = "HH:mm:ss";
	
	public final static String DB_MSDATETIMEFORMAT = "MM/dd/yyyy hh:mm:ss a";
	public final static String DB_MSDATEFORMAT = "MM/dd/yyyy";
	public final static String DB_MSTIMEFORMAT = "hh:mm:ss a";
	

	private final static String TIME_FORMAT = "yyyyMMddHHmmss";

	// debug
	public boolean is_debug = false;
	// db
	public int dbtype;
	private static String dbdrivername;
	private static String mbr_dburl;
	private static String mbr_dbuid;
	private static String mbr_dbpwd;

	private static String ohd_dburl;
	private static String ohd_dbuid;
	private static String ohd_dbpwd;

	private static String email_host;
	private static String email_from;
	private static int email_port;
	private static String email_to;
	
	
	private int MIN_PERCENT = 1;
	
	private long current_starting_row;
	private String incident_ID;
	private int scope = 0;
	private Date sdate;
	private Date edate;
	private boolean isactive_tracing = false;
	private Agent user;
	private boolean is_match_made = false;
	private String company;
	
	private String laststartingmbr = "";

	// connection pool
	private ConnectionPool cp;

	// number of seconds to wait before creating new threads if last one was null
	public static long SECONDS_WAIT = 30;

	public MainMBRThread(String path, String company, String incident_ID, int scope, Date sdate,
			Date edate, Agent user, boolean isactive_tracing) {
		try {
			if (isactive_tracing) {
				this.isactive_tracing = isactive_tracing;
				this.incident_ID = incident_ID;
				this.scope = scope;
				this.sdate = sdate;
				this.edate = edate;
				this.user = user;
			}

			this.company = company;

			threads = new ArrayList(TOTAL_THREADS);

			// start from the first record
			current_starting_row = 0;

			Connection conn = null;

			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(path + "tracing.cfg.properties"));
				
				is_debug=(Integer.parseInt(properties.getProperty("debug")) == 1 ? true : false);
				
				String dbt = properties.getProperty("db.type");
				if (dbt.equals("mssql")) dbtype = MSSQL;
				if (dbt.equals("mysql")) dbtype = MYSQL;
				if (dbt.equals("oracle")) dbtype = ORACLE;
				
				dbdrivername = properties.getProperty("db.driver_class");

				/** * for now, we will only use one database ** */
				
				mbr_dburl = properties.getProperty("mbr.db.url");
				mbr_dbuid = properties.getProperty("mbr.db.username");
				mbr_dbpwd = properties.getProperty("mbr.db.password");

				ohd_dburl = properties.getProperty("ohd.db.url");
				ohd_dbuid = properties.getProperty("ohd.db.username");
				ohd_dbpwd = properties.getProperty("ohd.db.password");
				
				
				
			} catch (IOException e) {
				logger.fatal("unable to read the tracing.cfg.properties file." + e);
				return;
			}

			Class.forName(dbdrivername).newInstance();

			// initialize company specific variable from database
			
			int attemptedReconnects = 0;
			
			while (conn == null && attemptedReconnects < MAX_RECONNECT_ATTEMPTS) {
				try {
					conn = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);
				} catch (Exception e) {
					attemptedReconnects +=1;
					if (attemptedReconnects < MAX_RECONNECT_ATTEMPTS) {
						logger.error("Unable to connect to database.  Waiting 30 seconds and retrying...");
						pause(30);
					}	else {
						logger.fatal("Unable to connect to database after " + MAX_RECONNECT_ATTEMPTS + " attempts...");
					}
				}
			}
			
			
			
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from company_specific_variable where companycode_ID = '"
							+ company + "'");
			if (rs.next()) {
				TOTAL_THREADS = rs.getInt("total_threads");
				SECONDS_WAIT = rs.getInt("seconds_wait");
				if (SECONDS_WAIT <= 0) SECONDS_WAIT = 30;
				if (TOTAL_THREADS <= 0) TOTAL_THREADS = 1;
				
				MIN_PERCENT = rs.getInt("MIN_MATCH_PERCENT");
				if (MIN_PERCENT <= 0) MIN_PERCENT = 50;
				
				email_host = rs.getString("email_host");
				email_from = rs.getString("email_from");
				email_port = rs.getInt("email_port");
				email_to = rs.getString("email_to");
			}
			rs.close();
			st.close();
			conn.close();
			
			if (isactive_tracing) TOTAL_THREADS = 1;

			// create connection pool for mbr and ohd
			cp = ConnectionPool.getInstance(mbr_dburl, mbr_dbuid, mbr_dbpwd, dbdrivername,
					TOTAL_THREADS + 1);

		} catch (Exception e) {
			logger.fatal("unable to start passive tracing: " + e);
		}
	}

	public void run() {
		boolean connectionError = false;
		try {

			// active tracing, only run one mbr
			if (isactive_tracing) {

				OHDTraceThread ohd_thread = null;
				ohd_thread = getNewObj();

				if (ohd_thread != null) {

					ohd_thread.start();
					while (true) {
						if (isIs_match_made()) {
							// send match made message and then terminate this thread

							// Put in the send box.
							Message msg = new Message();
							msg.setSend_date(TracerDateTime.getGMTDate());
							msg.setAgent(user);
							msg.setSend_station(user.getStation());
							String body = "Match has been made for MBR " + incident_ID
									+ "<br><br>Click <a href='viewMatches.do?active=1&incident_ID=" + incident_ID
									+ "'>here</a> to see the results.";

							msg.setMessage(body);
							msg.setSubject("Active Tracing match made for MBR " + incident_ID);
							Recipient rec = new Recipient();
							rec.setCompany_code(user.getStation().getCompany().getCompanyCode_ID());
							rec.setStation(user.getStation());

							List newRecpList = new ArrayList();
							newRecpList.add(rec);

							msg.setRecipients(new HashSet(newRecpList));
							HibernateUtils.save(msg);

							//save each repient's copy

							for (Iterator i = newRecpList.iterator(); i.hasNext();) {
								Recipient recpt = (Recipient) i.next();
								MessageCopy copy = new MessageCopy();
								copy.setAgent(user);
								copy.setParent_message(msg);
								copy.setBody(msg.getMessage());
								copy.setSubject(msg.getSubject());
								copy.setReceiving_station(recpt.getStation());
								Status s = new Status();
								s.setStatus_ID(TracingConstants.MESSAGE_STATUS_NEW);
								copy.setStatus(s);
								HibernateUtils.save(copy);
							}

							break;
						} else {
							if (!ohd_thread.isAlive()) break;
						}

						// wait 30 seconds before checking to send
						pause(30);
					}
				}
			} else {
				// passive tracing

				while (true) {
					String lastupdated = null;
					OHDTraceThread ohd_thread = null;

					// check to see if we have enough threads in the arraylist, if not,
					// try
					// to get new thread in there
					if (threads.size() < TOTAL_THREADS) {
						// get the next incident by looking at the lastupdated

						// get a new incident tracing thread
						int attemptedReconnects = 0;
						do {
							try {
								ohd_thread = getNewObj();
								connectionError = false;
							} catch (Exception e) {
								connectionError = true;
								logger.error("Unable to connect to database.  Waiting 30 seconds and retrying...");
								attemptedReconnects +=1;
								if (attemptedReconnects > MAX_RECONNECT_ATTEMPTS) {
									logger.fatal("Unable to connect to database.  Unable to continue!");
									throw e;
								}
								pause(30);
								
							}
						} while (connectionError == true);

						if (ohd_thread != null) {
							// before start, see if there are ie elements, if not, go on to
							// next one
							if (ohd_thread.getIe() != null) {

								// start sub thread
								ohd_thread.start();

								// insert into list
								threads.add(ohd_thread);
							}

						} else {
							// wait 1 hour before inserting a thread again
							logger.info("waiting for new MBR......");
							pause(SECONDS_WAIT);
							current_starting_row = 0; // start from the first mbr again
						}
					}

					// remove the threads that are dead already
					for (int i = 0; i < threads.size(); i++) {
						ohd_thread = (OHDTraceThread) threads.get(i);
						if (ohd_thread != null) {
							if (!ohd_thread.isAlive()) threads.remove(i);
						} else {
							threads.remove(i);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("main thread error: " + e);
			
			try {
				HtmlEmail he = new HtmlEmail();
				he.setHostName(email_host);
				he.setSmtpPort(email_port);
				he.setFrom(email_from);
	
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(email_to));
				he.setTo(al);
				String msg = "Please restart tracing thread for environment indicated by sending email address."; 
			  msg += "\n\nError with tracing in MainMBRThread: \n" + e.toString();
				
				he.setHtmlMsg(msg);
			
				he.send();
			} catch (Exception maile) {
				logger.fatal("unable to send mail due to smtp error." + maile);
				//return new ActionMessage("error.unable_to_send_mail");
			}
		}
	}

	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}

	/******
	 * find a new mbr report to trace
	 * @return
	 * @throws Exception
	 */
	public synchronized OHDTraceThread getNewObj() throws Exception {
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet inc_rs = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		// create connection

		// create tracer obj
		OHDTraceThread ohd_thread = null;
		ohd_thread = new OHDTraceThread(this, cp);

		// assign connection to thread obj
		ohd_thread.setMbr_conn(cp.getConnection());
		ohd_thread.setOhd_conn(cp.getConnection());
		
		if (ohd_thread.getMbr_conn() == null) throw new Exception("Could not connect to database");
		if (ohd_thread.getOhd_conn() == null) throw new Exception("Could not connect to database");
		
		// assign email information
		ohd_thread.setEmail_host(email_host);
		ohd_thread.setEmail_from(email_from);
		ohd_thread.setEmail_to(email_to);
		ohd_thread.setEmail_port(email_port);
		ohd_thread.setMIN_PERCENT(MIN_PERCENT);
		
		stmt = ohd_thread.getMbr_conn().createStatement();
		stmt2 = ohd_thread.getMbr_conn().createStatement();
		String sql = null;

		// get an incident that is not closed from the db and lastupdated is greater
		// than the last one traced

		// use a different sql for active tracing
		if (isactive_tracing) {
			sql = "select incident_ID,createdate,lastupdated,ohd_lasttraced,recordlocator "
					+ " from incident where " + "status_ID <> " + TracingConstants.MBR_STATUS_CLOSED
					+ " and status_ID <> " + TracingConstants.MBR_STATUS_TEMP
					+ " and itemtype_ID = 1 and incident_ID = '" + incident_ID + "'";
			ohd_thread.setIsactive_tracing(1);
		} else {
			if (dbtype == MSSQL) {
				
				sql = "SELECT * FROM "
					+ " (SELECT TOP 1 * FROM "
					+ " (SELECT TOP " + (current_starting_row + 1) + " incident_ID, createdate, lastupdated, ohd_lasttraced, recordlocator "
					+ " FROM incident, station WHERE "
					+ " status_ID = "
					+ TracingConstants.MBR_STATUS_OPEN 
					+ " and itemtype_ID = 1 AND station.station_id = incident.stationassigned_id AND station.companycode_ID = '"
					+ company + "' order by lastupdated,incident_ID ) AS foo "
				  + " ORDER BY lastupdated DESC, incident_ID DESC) bar "
					+ " ORDER BY lastupdated, incident_ID ";

			} else {
				sql = "select incident_ID,createdate,lastupdated,ohd_lasttraced,recordlocator "
						+ " from incident,station where "
						+ "status_ID = "
						+ TracingConstants.MBR_STATUS_OPEN
						+ " and itemtype_ID = 1 and station.station_id = incident.stationassigned_id and station.companycode_ID = '"
						+ company + "' order by lastupdated,incident_ID " + "limit " + current_starting_row
						+ ",1";
			}
			ohd_thread.setIsactive_tracing(0);
		}

		//logger.info("GET MBR: " + sql);

		inc_rs = stmt.executeQuery(sql);
		
		if (inc_rs.next()) {
			
			//for mssql, there is no limit function, so we have to stop when the last 100 recordset are retrieved to prevent infiniteloop
			if (dbtype == MainMBRThread.MSSQL) {
				if (inc_rs.getString("incident_ID").equals(laststartingmbr)) {
					inc_rs.close();
					stmt.close();
					cp.returnConnection(ohd_thread.getMbr_conn());
					cp.returnConnection(ohd_thread.getOhd_conn());

					// restart from the beginning and trace everything again
					current_starting_row = 0;
					laststartingmbr = "";
					return null;
				}
				laststartingmbr = inc_rs.getString("incident_ID");
			}
			
			
			ohd_thread.setIncident_ID(inc_rs.getString("incident_ID"));
			ohd_thread.setInc_lupd(inc_rs.getString("lastupdated"));
			ohd_thread.setOhd_lupd(inc_rs.getString("ohd_lasttraced"));
			
			logger.info("mbr: " + inc_rs.getString("incident_ID"));
			if (is_debug) logger.info("looking at MBR for company " + company + ": " + inc_rs.getString("incident_ID")
					+ " ::: lastupdated: " + inc_rs.getString("lastupdated") + " ::: ohdtraced: " + inc_rs.getString("ohd_lasttraced"));

			/** ************ fill IncidentElements **************** */
			IncidentElements ie = new IncidentElements();

			// fill mbr information
			ie.setRecordlocator(inc_rs.getString("recordlocator") == null ? "" : inc_rs
					.getString("recordlocator"));
			ie.setCreatedate(inc_rs.getDate("createdate"));

			// fill passenger information
			sql = "select distinct firstname,middlename,lastname, passenger.passenger_ID, member.membershipnum, member.companycode_ID "
					+ " from passenger "
					+ "left outer join airlinemembership member on passenger.membership_ID = member.membership_ID "
					+ " where incident_ID = '" + ohd_thread.getIncident_ID() + "'";
			rs = stmt.executeQuery(sql);
			Passenger pass = null;
			AirlineMembership mem = null;
			Company com = null;
			Address address = null;

			Statement stmt3 = ohd_thread.getMbr_conn().createStatement();

			boolean hasclaim_or_bag = false;

			while (rs.next()) {
				// name
				pass = new Passenger();
				pass.setPassenger_ID(rs.getInt("passenger_ID"));
				pass.setFirstname(rs.getString("firstname") == null ? "" : rs.getString("firstname"));
				pass.setMiddlename(rs.getString("middlename") == null ? "" : rs.getString("middlename"));
				pass.setLastname(rs.getString("lastname") == null ? "" : rs.getString("lastname"));
				ie.getPassengers().add(pass);

				// membership
				mem = new AirlineMembership();
				mem.setCompanycode_ID(rs.getString("companycode_ID") == null ? "" : rs
						.getString("companycode_ID"));
				mem.setMembershipnum(rs.getString("membershipnum") == null ? "" : rs
						.getString("membershipnum"));
				ie.getMemberships().add(mem);

				// address
				sql = "select distinct address1,address2,city,state_ID,zip,homephone,workphone,mobile,"
						+ "pager,altphone,email from address where passenger_ID = " + rs.getInt("passenger_ID");

				rs2 = stmt3.executeQuery(sql);

				while (rs2.next()) {
					address = new Address();
					address.setAddress1(rs2.getString("address1") == null ? "" : rs2.getString("address1"));
					address.setAddress2(rs2.getString("address2") == null ? "" : rs2.getString("address2"));
					address.setCity(rs2.getString("city") == null ? "" : rs2.getString("city"));
					address.setState_ID(rs2.getString("state_ID") == null ? "" : rs2.getString("state_ID"));
					address.setZip(rs2.getString("zip") == null ? "" : rs2.getString("zip"));
					address.setEmail(rs2.getString("email") == null ? "" : rs2.getString("email"));
					ie.getAddresses().add(address);

					ie.getPhonelist().add(
							rs2.getString("homephone") == null ? "" : rs2.getString("homephone"));
					ie.getPhonelist().add(
							rs2.getString("workphone") == null ? "" : rs2.getString("workphone"));
					ie.getPhonelist().add(rs2.getString("mobile") == null ? "" : rs2.getString("mobile"));
					ie.getPhonelist().add(rs2.getString("pager") == null ? "" : rs2.getString("pager"));
					ie.getPhonelist().add(rs2.getString("altphone") == null ? "" : rs2.getString("altphone"));

				}

			}

			rs.close();

			// fill claimcheck information
			sql = "select distinct claimcheck_ID,claimchecknum "
					+ "from incident_claimcheck ic where (ic.OHD_ID = '' or ic.OHD_ID is null) and ic.incident_ID = '"
					+ ohd_thread.getIncident_ID() + "'";
			rs = stmt.executeQuery(sql);
			Incident_Claimcheck claimcheck;
			while (rs.next()) {
				claimcheck = new Incident_Claimcheck();
				claimcheck.setClaimcheck_ID(rs.getInt("claimcheck_ID"));
				claimcheck.setClaimchecknum(rs.getString("claimchecknum") == null ? "" : rs
						.getString("claimchecknum"));
				ie.getClaimchecks().add(claimcheck);
				hasclaim_or_bag = true;
			}
			rs.close();

			// fill bag information bag names
			sql = "select distinct Item_ID,bagnumber, manufacturer_ID,color,bagtype,"
					+ "xdescelement_ID_1,xdescelement_ID_2,xdescelement_ID_3,"
					+ "lnameonbag,mnameonbag,fnameonbag "
					+ "from item where (item.OHD_ID = '' or item.OHD_ID is null) and "
					+ "item.incident_ID = '" + ohd_thread.getIncident_ID()
					+ "'";
			rs = stmt.executeQuery(sql);
			Item item;
			Item_Inventory ii;
			while (rs.next()) {
				item = new Item();
				item.setBagnumber(rs.getInt("bagnumber"));
				item.setManufacturer_ID(rs.getInt("manufacturer_ID"));
				item.setColor(rs.getString("color") == null ? "" : rs.getString("color"));
				item.setBagtype(rs.getString("bagtype") == null ? "" : rs.getString("bagtype"));
				item.setXdescelement_ID_1(rs.getInt("xdescelement_ID_1"));
				item.setXdescelement_ID_2(rs.getInt("xdescelement_ID_2"));
				item.setXdescelement_ID_3(rs.getInt("xdescelement_ID_3"));
				item.setLnameonbag(rs.getString("lnameonbag") == null ? "" : rs.getString("lnameonbag"));
				item.setMnameonbag(rs.getString("mnameonbag") == null ? "" : rs.getString("mnameonbag"));
				item.setFnameonbag(rs.getString("fnameonbag") == null ? "" : rs.getString("fnameonbag"));
				
				sql = "select categorytype_ID,description,categorytype from item_inventory,ohd_categorytype where categorytype_ID = OHD_CategoryType_ID and item_ID = " + rs.getInt("Item_ID");
				rs2 = stmt2.executeQuery(sql);
				while (rs2.next()) {
					ii = new Item_Inventory();
					ii.setTempcat(rs2.getString("categorytype"));
					ii.setCategorytype_ID(rs2.getInt("categorytype_ID"));
					ii.setDescription(rs2.getString("description"));
					item.getInventorylist().add(ii);
				}
				rs2.close();
				
				ie.getItems().add(item);
				hasclaim_or_bag = true;
			}
			rs.close();

			// fill itinerary information
			sql = "select distinct legfrom,legto,departdate,arrivedate,flightnum,"
					+ "schdeparttime,scharrivetime,actdeparttime,actarrivetime "
					+ "from itinerary where incident_ID = '" + ohd_thread.getIncident_ID()
					+ "' order by departdate";

			rs = stmt.executeQuery(sql);
			Itinerary iti;
			while (rs.next()) {
				iti = new Itinerary();
				iti.setLegfrom(rs.getString("legfrom") == null ? "" : rs.getString("legfrom"));
				iti.setLegto(rs.getString("legto") == null ? "" : rs.getString("legto"));
				iti.setDepartdate(rs.getDate("departdate"));
				iti.setArrivedate(rs.getDate("arrivedate"));
				iti.setFlightnum(rs.getString("flightnum") == null ? "" : rs.getString("flightnum"));
				iti.setSchdeparttime(rs.getTime("schdeparttime"));
				iti.setScharrivetime(rs.getTime("scharrivetime"));
				iti.setActdeparttime(rs.getTime("actdeparttime"));
				iti.setActarrivetime(rs.getTime("actarrivetime"));
				ie.getItineraries().add(iti);
			}
			rs.close();

			if (hasclaim_or_bag) {
				ohd_thread.setIe(ie);
			} else {
				ohd_thread.setIe(null);
			}
			
			//Date lastupdate = null;
			//if (dbtype == MSSQL) lastupdate = DateUtils.convertToDate(rs.getString("lastupdated"),DB_MSDATETIMEFORMAT,null);
			//else lastupdate = DateUtils.convertToDate(rs.getString("lastupdated"),DB_DATETIMEFORMAT,null);
			current_starting_row++;
			
			inc_rs.close();
			stmt.close();
			
			cp.returnConnection(ohd_thread.getMbr_conn());
			cp.returnConnection(ohd_thread.getOhd_conn());

			
			return ohd_thread;

		} else {
			// no incident to get, don't insert thread
			inc_rs.close();
			stmt.close();
			cp.returnConnection(ohd_thread.getMbr_conn());
			cp.returnConnection(ohd_thread.getOhd_conn());

			// restart from the beginning and trace everything again
			current_starting_row = 0;
			return null;
		}

	}

	/**
	 * @return Returns the is_match_made.
	 */
	public boolean isIs_match_made() {
		return is_match_made;
	}

	/**
	 * @param is_match_made
	 *          The is_match_made to set.
	 */
	public void setIs_match_made(boolean is_match_made) {
		this.is_match_made = is_match_made;
	}

	/**
	 * @return Returns the edate.
	 */
	public Date getEdate() {
		return edate;
	}

	/**
	 * @param edate
	 *          The edate to set.
	 */
	public void setEdate(Date edate) {
		this.edate = edate;
	}

	/**
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @return Returns the isactive_tracing.
	 */
	public boolean isIsactive_tracing() {
		return isactive_tracing;
	}

	/**
	 * @param isactive_tracing
	 *          The isactive_tracing to set.
	 */
	public void setIsactive_tracing(boolean isactive_tracing) {
		this.isactive_tracing = isactive_tracing;
	}

	/**
	 * @return Returns the scope.
	 */
	public int getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *          The scope to set.
	 */
	public void setScope(int scope) {
		this.scope = scope;
	}

	/**
	 * @return Returns the sdate.
	 */
	public Date getSdate() {
		return sdate;
	}

	/**
	 * @param sdate
	 *          The sdate to set.
	 */
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	/**
	 * @return Returns the user.
	 */
	public Agent getUser() {
		return user;
	}

	/**
	 * @param user
	 *          The user to set.
	 */
	public void setUser(Agent user) {
		this.user = user;
	}
}

