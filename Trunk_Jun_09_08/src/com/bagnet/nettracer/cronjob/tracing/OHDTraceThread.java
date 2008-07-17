/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.tracing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.match.IncidentElements;
import com.bagnet.nettracer.match.Matcher;
import com.bagnet.nettracer.match.OHDElements;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class OHDTraceThread extends Thread {
	private static Logger logger = Logger.getLogger(OHDTraceThread.class);
	private String inc_lupd; // incident table lastupdated column
	private String ohd_lupd; // ohd table lastupdated column

	double percent = 0;

	private IncidentElements ie = null;
	private OHDElements oe = null;

	private String incident_ID;
	private Connection mbr_conn;
	private Connection ohd_conn;

	private int current_starting_row = 0; // current record number

	private final int NUM_ROWS_TO_GET = 100; // number of ohds to get at once

	private int isactive_tracing = 0; // 0 is passive tracing, 1 is active tracing

	private MainMBRThread mbr_thread;

	private boolean is_match_made = false;

	private ConnectionPool cp;
	
	private String email_host;
	private String email_from;
	private int email_port;
	private String email_to;
	
	private int MIN_PERCENT = 0;

	public OHDTraceThread() {
		super();
	}

	/**
	 * active tracing constructor
	 * 
	 * @param scope
	 * @param sdate
	 * @param edate
	 */
	public OHDTraceThread(MainMBRThread m, ConnectionPool cp) {
		super();
		mbr_thread = m;
		this.cp = cp;
	}

	public void run() {
		ResultSet ohd_rs = null;
		ResultSet rs = null;
		String sql = null;

		Item item = null;

		try {
			Statement stmt = ohd_conn.createStatement();
			Statement stmt2 = ohd_conn.createStatement();
			Statement mbr_stmt = mbr_conn.createStatement();

			int scope = mbr_thread.getScope();
			Date sdate = mbr_thread.getSdate();
			Date edate = mbr_thread.getEdate();
			Agent user = mbr_thread.getUser();
			
			String laststartingohd = "";
			
			while (true) {
				// start tracing
				if (mbr_thread.is_debug) logger.info(".........................................................................");

				/** ** active search criterias **** */
				StringBuffer sb = new StringBuffer(256);
				String extrafrom = "";
				if (sdate != null && edate != null) sb.append(" and founddate between '"
						+ DateUtils.formatDate(sdate, TracingConstants.DB_DATEFORMAT, null, null) + "' and '"
						+ DateUtils.formatDate(edate, TracingConstants.DB_DATEFORMAT, null, null) + "' ");
				if (sdate != null && edate == null) sb.append(" and founddate = '"
						+ DateUtils.formatDate(sdate, TracingConstants.DB_DATEFORMAT, null, null) + "' ");
				if (scope == 1) {
					// company wide
					extrafrom = ",station ";
					sb.append(" and holding_station_ID = station.station_ID and station.companycode_ID = '"
							+ user.getStation().getCompany().getCompanyCode_ID() + "' ");
				}
				if (scope == 2) {
					// station wide
					sb.append(" and holding_station_ID = " + user.getStation().getStation_ID() + " ");
				}

				// make sure don't trace bags created 2 day earlier than the itinerary
				// or the report create date
				Date reportdate = null;
				if (ie.getItineraries() != null && ie.getItineraries().size() > 0) {
					Itinerary it = (Itinerary) ie.getItineraries().get(0);
					reportdate = it.getDepartdate();
				}
				if (reportdate == null) reportdate = ie.getCreatedate();
				String datestring = DateUtils.formatDate(reportdate, TracingConstants.DB_DATEFORMAT, null,
						null);
				
				if (mbr_thread.dbtype == MainMBRThread.MSSQL) {
					String tttt = "lastupdated >= '" + getOhd_lupd() + "'";
					if (getOhd_lupd() == null) tttt = "lastupdated is not NULL";
					sql = "SELECT * FROM "
						+ " (SELECT TOP " + NUM_ROWS_TO_GET + " * FROM "
						+ " (SELECT TOP " + (current_starting_row + NUM_ROWS_TO_GET) + " OHD_ID,record_locator,claimnum,lastupdated,membership_ID, "
						+ "manufacturer_ID,color,type,xdescelement_ID_1,xdescelement_ID_2,xdescelement_ID_3"
						+ " from ohd" + extrafrom + " where " + tttt
						+ " and DATEDIFF(day, founddate, '" + datestring + "') <= 5 " + sb.toString()
						+ " and (status_ID = " + TracingConstants.OHD_STATUS_OPEN + " or status_ID = "
						+ TracingConstants.OHD_STATUS_IN_TRANSIT + ") "
						+ " order by lastupdated, OHD_ID ) AS foo "
					  + " ORDER BY lastupdated DESC, OHD_ID DESC) bar "
						+ " ORDER BY lastupdated, OHD_ID ";
				} else {
					// get the next ohd to trace
					String tttt;
					if (getOhd_lupd() == null) {
						tttt = "lastupdated is not NULL";
					}
					else {
						tttt = "lastupdated >= '" + getOhd_lupd() + "'";
					}
					sql = "select OHD_ID,record_locator,claimnum,lastupdated,membership_ID, "
							+ "manufacturer_ID,color,type,xdescelement_ID_1,xdescelement_ID_2,xdescelement_ID_3"
							+ " from ohd" + extrafrom + " where " + tttt
							+ " and DATEDIFF('" + datestring + "',founddate) <= 5 " + sb.toString()
							+ " and (status_ID = " + TracingConstants.OHD_STATUS_OPEN + " or status_ID = "
							+ TracingConstants.OHD_STATUS_IN_TRANSIT + ") "
							+ " order by lastupdated, OHD_ID limit " + current_starting_row + ", "
							+ NUM_ROWS_TO_GET;
				}

				//logger.info("GET OHD: " + sql);
				ohd_rs = stmt.executeQuery(sql);
				
				
				boolean hasresult = false;
				int docount = 0;
				while (ohd_rs.next()) {
					// for mssql, there is no limit function, so we have to stop when the last 100 recordset are retrieved to prevent infiniteloop
					if (mbr_thread.dbtype == MainMBRThread.MSSQL) {
						if (docount == 0 && ohd_rs.getString("OHD_ID").equals(laststartingohd)) break;
						else if (docount == 0) laststartingohd = ohd_rs.getString("OHD_ID");
						docount++;
					}
					
					hasresult = true;

					// fill ohd details
					oe = new OHDElements();

					/** ******** claim check and record locator ******* */
					oe.setClaimchecknum(ohd_rs.getString("claimnum") == null ? "" : ohd_rs
							.getString("claimnum"));
					oe.setRecordlocator(ohd_rs.getString("record_locator") == null ? "" : ohd_rs
							.getString("record_locator"));

					/** ********** bag info **************** */
					oe.setManufacturer_ID(ohd_rs.getInt("manufacturer_ID"));
					oe.setColor(ohd_rs.getString("color") == null ? "" : ohd_rs.getString("color"));
					oe.setBagtype(ohd_rs.getString("type") == null ? "" : ohd_rs.getString("type"));
					oe.setXdesc1(ohd_rs.getInt("xdescelement_ID_1"));
					oe.setXdesc2(ohd_rs.getInt("xdescelement_ID_2"));
					oe.setXdesc3(ohd_rs.getInt("xdescelement_ID_3"));

					/** ****** membership ********** */
					sql = "select member.membershipnum,member.companycode_ID from airlinemembership member where membership_ID = "
							+ ohd_rs.getInt("membership_ID");
					rs = stmt2.executeQuery(sql);
					if (rs.next()) {
						oe.setMembershipnum(rs.getString("membershipnum") == null ? "" : rs
								.getString("membershipnum"));
						oe.setMembership_company(rs.getString("companycode_ID") == null ? "" : rs
								.getString("companycode_ID"));
					}
					rs.close();

					/** *** passenger info ***** */
					sql = "select distinct firstname,middlename,lastname,passenger_ID from ohd_passenger "
							+ " where OHD_ID = '" + ohd_rs.getString("OHD_ID") + "'";
					rs = stmt2.executeQuery(sql);
					OHD_Passenger ohd_p = null;
					ResultSet rs2 = null;
					Statement stmt3 = ohd_conn.createStatement();
					while (rs.next()) {
						ohd_p = new OHD_Passenger();
						ohd_p.setLastname(rs.getString("lastname") == null ? "" : rs.getString("lastname"));
						ohd_p.setMiddlename(rs.getString("middlename") == null ? "" : rs
								.getString("middlename"));
						ohd_p.setFirstname(rs.getString("firstname") == null ? "" : rs.getString("firstname"));
						oe.getPassengers().add(ohd_p);

						// address information
						sql = "select distinct address1,address2,city,state_ID,zip,homephone,workphone,mobile,"
								+ "pager,altphone,email from ohd_address where passenger_ID = "
								+ rs.getInt("passenger_ID");

						rs2 = stmt3.executeQuery(sql);
						OHD_Address address;
						while (rs2.next()) {
							address = new OHD_Address();
							address.setAddress1(rs2.getString("address1") == null ? "" : rs2
									.getString("address1"));
							address.setAddress2(rs2.getString("address2") == null ? "" : rs2
									.getString("address2"));
							address.setCity(rs2.getString("city") == null ? "" : rs2.getString("city"));
							address.setState_ID(rs2.getString("state_ID") == null ? "" : rs2
									.getString("state_ID"));
							address.setZip(rs2.getString("zip") == null ? "" : rs2.getString("zip"));
							address.setEmail(rs2.getString("email") == null ? "" : rs2.getString("email"));
							oe.getAddresses().add(address);

							oe.getPhonelist().add(
									rs2.getString("homephone") == null ? "" : rs2.getString("homephone"));
							oe.getPhonelist().add(
									rs2.getString("workphone") == null ? "" : rs2.getString("workphone"));
							oe.getPhonelist().add(rs2.getString("mobile") == null ? "" : rs2.getString("mobile"));
							oe.getPhonelist().add(rs2.getString("pager") == null ? "" : rs2.getString("pager"));
							oe.getPhonelist().add(
									rs2.getString("altphone") == null ? "" : rs2.getString("altphone"));
						}
						rs2.close();

					}
					rs.close();

					/** ********** itinerary ********** */
					sql = "select distinct legfrom,legto,departdate,arrivedate,flightnum,"
							+ "schdeparttime,scharrivetime,actdeparttime,actarrivetime "
							+ "from ohd_itinerary where OHD_ID = '" + ohd_rs.getString("OHD_ID") + "'";

					rs = stmt2.executeQuery(sql);
					OHD_Itinerary iti;
					while (rs.next()) {
						iti = new OHD_Itinerary();
						iti.setLegfrom(rs.getString("legfrom") == null ? "" : rs.getString("legfrom"));
						iti.setLegto(rs.getString("legto") == null ? "" : rs.getString("legto"));
						iti.setDepartdate(rs.getDate("departdate"));
						iti.setArrivedate(rs.getDate("arrivedate"));
						iti.setFlightnum(rs.getString("flightnum") == null ? "" : rs.getString("flightnum"));
						iti.setSchdeparttime(rs.getTime("schdeparttime"));
						iti.setScharrivetime(rs.getTime("scharrivetime"));
						iti.setActdeparttime(rs.getTime("actdeparttime"));
						iti.setActarrivetime(rs.getTime("actarrivetime"));
						oe.getItineraries().add(iti);
					}
					rs.close();

					/** *********** contents ************** */
					sql = "select distinct b.categorytype,a.description "
							+ "from ohd_inventory a,ohd_categorytype b " + "where ohd_id = '"
							+ ohd_rs.getString("OHD_ID") + "' and "
							+ "a.OHD_categorytype_ID = b.OHD_categorytype_ID";
					rs = stmt2.executeQuery(sql);
					while (rs.next()) {
						oe.getContents().add(
								(rs.getString("categorytype") == null ? "" : rs.getString("categorytype")) + " "
										+ (rs.getString("description") == null ? "" : rs.getString("description")));
					}
					rs.close();

					Matcher matcher = new Matcher(this, ohd_rs.getString("OHD_ID"));

					/** ***** do main matching ******* */
					if (mbr_thread.is_debug) logger.info("MATCHING: " + getIncident_ID() + " with " + ohd_rs.getString("OHD_ID"));
					boolean matched = matcher.doMatching(ie, oe, MIN_PERCENT);
					

					// update incident table with the traced ohd lastupdated row
					// add 1 to ohd_rs.getString("lastupdated") so after everything is
					// done, mbr won't trace the same ohd again
					String l = ohd_rs.getString("lastupdated");
					
					if (mbr_thread.dbtype == MainMBRThread.MSSQL) {
						sql = "update incident set lastupdated = '" + inc_lupd + "',ohd_lasttraced = "
								+ "DATEADD(second,1,'" + l + "') where incident_ID = '" + getIncident_ID() + "'";
					} else {
						sql = "update incident set lastupdated = '" + inc_lupd + "',ohd_lasttraced = "
						+ "ADDTIME('" + l + "', '00:00:01') where incident_ID = '" + getIncident_ID() + "'";
					}
					if (mbr_thread.is_debug) logger.info("Updating MBR " + getIncident_ID() + " with lasttraced ohd " + ohd_rs.getString("OHD_ID") + " at " + l);
					mbr_stmt.executeUpdate(sql);
					
				} // end ohd_rs while

				current_starting_row += NUM_ROWS_TO_GET;
				
				if (!hasresult) {
					// no more records
					break;
				}
			}

			// close everything and wait for thread to die
			stmt.close();
			stmt2.close();
			mbr_stmt.close();
			cp.returnConnection(getOhd_conn());
			cp.returnConnection(getMbr_conn());

		} catch (Exception e) {
			logger.fatal("Error running OHD Tracing Thread: " + e);
			e.printStackTrace();
			
			try {
				HtmlEmail he = new HtmlEmail();
				he.setHostName(email_host);
				he.setSmtpPort(email_port);
				he.setFrom(email_from);
	
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(email_to));
				he.setTo(al);
				String msg = "Error with tracing in OHDTraceThread: " + e.toString();
				msg += "\n\nThis may not be a fatal error.  Please check to ensure that Passive Tracing is still functioning and notify NetTracer Technical Support via bugtracker or support@nettracer.aero.";
				he.setHtmlMsg(msg);
			
				he.send();
			} catch (Exception maile) {
				logger.fatal("unable to send mail due to smtp error." + maile);
				//return new ActionMessage("error.unable_to_send_mail");
			}
			
			// thread will die automatically
		}
	}


	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}

	public synchronized void insertMatchHistory(String ohd_id, int cat, double percent, ArrayList al,
			String claimchecknum, int bagnumber) throws Exception {
		Statement stmt = mbr_conn.createStatement();
		ResultSet rs = null;

		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		format.setMaximumFractionDigits(4);
		String perstr = format.format(percent);

		// match has been made, for active tracing, send message
		mbr_thread.setIs_match_made(true);

		// first check to see if this same match was in there already
		String sql = "select match_id from match_history where mbr_number = '" + getIncident_ID()
				+ "' and " + "OHD_ID = '" + ohd_id + "' and match_percent = " + perstr;
		rs = stmt.executeQuery(sql);
		if (rs.next()) {
			rs.close();
			return;
		}

		String nowtime = "now()";
		if (mbr_thread.dbtype == MainMBRThread.MSSQL) nowtime = "getdate()";
		
		// if no same match, insert
		sql = "insert into match_history "
				+ "(mbr_number,match_type,OHD_ID,claimchecknum,bagnumber,category,match_percent,match_made_on,status_id) values ('"
				+ getIncident_ID() + "'," + isactive_tracing + ",'" + ohd_id + "','" + claimchecknum + "',"
				+ bagnumber + "," + cat + "," + perstr + "," + nowtime + "," + TracingConstants.MATCH_STATUS_OPEN
				+ ")";
		stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		rs = stmt.getGeneratedKeys();
		long id = 0;
		if (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();

		if (id > 0) {
			Match_Detail md = null;

			for (int i = 0; i < al.size(); i++) {
				md = (Match_Detail) al.get(i);
				sql = "insert into match_detail (match_ID,item,mbr_info,ohd_info,percentage) values (" + id
						+ ",'" + md.getItem() + "','" + md.getMbr_info() + "','" + md.getOhd_info() + "',"
						+ md.getPercentage() + ")";
				stmt.executeUpdate(sql);
			}
		}

	}

	/**
	 * @return Returns the inc_lupd.
	 */
	public String getInc_lupd() {
		return inc_lupd;
	}

	/**
	 * @param inc_lupd
	 *          The inc_lupd to set.
	 */
	public void setInc_lupd(String inc_lupd) {
		this.inc_lupd = inc_lupd;
	}

	/**
	 * @return Returns the ohd_lupd.
	 */
	public String getOhd_lupd() {
		return ohd_lupd;
	}

	/**
	 * @param ohd_lupd
	 *          The ohd_lupd to set.
	 */
	public void setOhd_lupd(String ohd_lupd) {
		this.ohd_lupd = ohd_lupd;
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
	 * @return Returns the mbr_conn.
	 */
	public Connection getMbr_conn() {
		return mbr_conn;
	}

	/**
	 * @param mbr_conn
	 *          The mbr_conn to set.
	 */
	public void setMbr_conn(Connection mbr_conn) {
		this.mbr_conn = mbr_conn;
	}

	/**
	 * @return Returns the ohd_conn.
	 */
	public Connection getOhd_conn() {
		return ohd_conn;
	}

	/**
	 * @param ohd_conn
	 *          The ohd_conn to set.
	 */
	public void setOhd_conn(Connection ohd_conn) {
		this.ohd_conn = ohd_conn;
	}

	/**
	 * @return Returns the ie.
	 */
	public IncidentElements getIe() {
		return ie;
	}

	/**
	 * @param ie
	 *          The ie to set.
	 */
	public void setIe(IncidentElements ie) {
		this.ie = ie;
	}

	/**
	 * @return Returns the isactive_tracing.
	 */
	public int isIsactive_tracing() {
		return isactive_tracing;
	}

	/**
	 * @param isactive_tracing
	 *          The isactive_tracing to set.
	 */
	public void setIsactive_tracing(int isactive_tracing) {
		this.isactive_tracing = isactive_tracing;
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
	
	public String getEmail_from() {
		return email_from;
	}
	public void setEmail_from(String email_from) {
		this.email_from = email_from;
	}
	public String getEmail_host() {
		return email_host;
	}
	public void setEmail_host(String email_host) {
		this.email_host = email_host;
	}
	public int getEmail_port() {
		return email_port;
	}
	public void setEmail_port(int email_port) {
		this.email_port = email_port;
	}
	public String getEmail_to() {
		return email_to;
	}
	public void setEmail_to(String email_to) {
		this.email_to = email_to;
	}
	
	/**
	 * @return Returns the mIN_PERCENT.
	 */
	public int getMIN_PERCENT() {
		return MIN_PERCENT;
	}
	/**
	 * @param min_percent The mIN_PERCENT to set.
	 */
	public void setMIN_PERCENT(int min_percent) {
		MIN_PERCENT = min_percent;
	}
}