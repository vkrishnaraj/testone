package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;

public class WorldTracerIntegrationWrapper extends HttpServlet  {

	static Agent user;
	String wt_url = WorldTracerUtils.getWt_url(user.getCompanycode_ID());
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		user = (Agent) session.getAttribute("user");
		HttpClient client = WorldTracerUtils.connectWT(user.getStation().getCompany().getVariable().getWt_url() + "/",user.getCompanycode_ID());
		String method = req.getParameter("method");

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		/**
		 * method 1 is to get active stations
		 * method 2 is to get all lost delayed
		 * method 2a is to get one lost/delay from filename and insert into NT 
		 * method 2b is to get all lost/delay and insert into NT
		 * method 3 is to get all ohd
		 * method 3a is to get one ohd from filename and insert into NT
		 * method 3b is to get all ohd and insert into NT
		 */
		if (method != null && method.equals("1")) {
			String airline = req.getParameter("airline");
			String stations = WorldTracerUtils.getActiveStations(client, airline);
			out.print(stations);
		} else if (method != null && method.equals("2")) {
			/********** get all lost/delayed from wt *************/
			String airline = req.getParameter("airline");
			String station = req.getParameter("station");
			String status = req.getParameter("status");
			String daterange=req.getParameter("dt");
			
			if (status == null) status = "A";
			if (daterange == null) daterange = "";
			
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_handled)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>Handled - H<p>");
			} else {
				String rafs = WorldTracerUtils.getAllRAF(client, airline, station,status,daterange);

				rafs = rafs.replace("<input type=\"submit\" value=\"Display\" name=\"B1\" target=\"index\" id=\"text5\">", "");
				out.print(rafs);
			}
			
		} else if (method != null && method.equals("2a")) {
			
			/******** get single l/d and insert into nt database ********/
			String filenum = req.getParameter("filenum");
			String status = req.getParameter("status");
			if (status == null) status = "A";
			
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_handled)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>Handled - H<p>");
			} else {
				
				String result = WorldTracerUtils.getRAF(client, filenum, wt_url);
				WTIncident wi = new WTIncident();
				Incident incident = wi.parseWTIncident(result,true,status);
				if (incident == null) out.println("unable to insert WT incident into NT incident DB, error: " + wi.getError());
			}
			
		} else if (method != null && method.equals("2b")) {
			/****** get all l/d for a station and insert into db **********/
			String airline = req.getParameter("airline");
			String station = req.getParameter("station");
			String status = req.getParameter("status");
			String daterange=req.getParameter("dt");
			
			if (status == null) status = "A";
			if (daterange == null) daterange = "";
			
			String notparsed = "";
			String parsed = "";
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_handled)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>Handled - H<p>");
			} else {
				String result = WorldTracerUtils.getAllRAF(client, airline, station,status, daterange);
				WTIncident wi = new WTIncident();
				ArrayList<String> al = wi.parseAllWTIncident(result);
				if (al == null) out.println(wi.getError());
				else {
					if (al.size() == 0) {
						out.println("no wt data");
					} else {
						for (int i=0;i<al.size();i++) {
							result = WorldTracerUtils.getROF(client, al.get(i),wt_url);
							Incident incident = wi.parseWTIncident(result,true,status);
							if (incident == null) notparsed += al.get(i) + ",";
							else parsed += incident.getIncident_ID() + ",";
						}
						
						if (wi.getError() != null && wi.getError().length() > 0) {
							out.println("some incidents are not parsed, WT INCIDENT IDs: " + notparsed + "<p>");
							out.println("incidents that are parsed successfully, new INCIDENTs: " + parsed + "<p>");
							out.println("error was: " + wi.getError());
						} else {
							out.println("All incidents are parsed successfully: new INCIDENTs: " + parsed + "<p>");
						}
						
					}
				}
			}
		} else if (method != null && method.equals("3")) {
			/********** display all ohd from wt station ***********/
			String airline = req.getParameter("airline");
			String station = req.getParameter("station");
			String status = req.getParameter("status");
			String daterange=req.getParameter("dt");
			
			if (status == null) status = "A";
			if (daterange == null) daterange = "";
			
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_qoh)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>QOH File - Q<p>");
			} else {
				String result = WorldTracerUtils.getAllROF(client, airline, station, status,daterange);
				WTOHD wi = new WTOHD();
				ArrayList<String> al = wi.parseAllWTOHD(result);
				if (al == null) out.println(wi.getError());
				else {
					for (int i=0;i<al.size();i++) {
						out.println(al.get(i) + "<br>");
					}
				}
			}
		} else if (method != null && method.equals("3a")) {
			/******** get single ohd and insert into nt database ********/
			String filenum = req.getParameter("filenum");
			String status = req.getParameter("status");
			if (status == null) status = "A";
			
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_qoh)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>QOH File - Q<p>");
			} else {
				String result = WorldTracerUtils.getROF(client, filenum, wt_url);
				WTOHD wi = new WTOHD();
				OHD ohd = wi.parseWTOHD(result,true,status);
				if (ohd == null) out.println("unable to insert WT OHD into NT OHD DB, error: " + wi.getError());
			}
		} else if (method != null && method.equals("3b")) {
			/****** get all ohds for a station and insert into db **********/
			String airline = req.getParameter("airline");
			String station = req.getParameter("station");
			String status = req.getParameter("status");
			String daterange=req.getParameter("dt");
			
			if (status == null) status = "A";
			if (daterange == null) daterange = "";
			
			String notparsed = "";
			String parsed = "";
			if (!status.equals(WorldTracerUtils.status_active) && !status.equals(WorldTracerUtils.status_closed)
					&& !status.equals(WorldTracerUtils.status_suspended) && !status.equals(WorldTracerUtils.status_extended)
					&& !status.equals(WorldTracerUtils.status_qoh)) {
				out.println("status input wrong, you can only use the following statuses: <p>" +
						"A - Active<br>C - Closed<br>Suspended - S<br>Extended - D<br>QOH File - Q<p>");
			} else {
				String result = WorldTracerUtils.getAllROF(client, airline, station,status,daterange);
				WTOHD wi = new WTOHD();
				ArrayList<String> al = wi.parseAllWTOHD(result);
				if (al == null) out.println(wi.getError());
				else {
					if (al.size() == 0) {
						out.println("no wt data");
					} else {
						for (int i=0;i<al.size();i++) {
							result = WorldTracerUtils.getROF(client, al.get(i), wt_url);
							OHD ohd = wi.parseWTOHD(result,true,status);
							if (ohd == null) notparsed += al.get(i) + ",";
							else parsed += ohd.getOHD_ID() + ",";
						}
						
						if (wi.getError() != null && wi.getError().length() > 0) {
							out.println("some ohds are not parsed, WT OHD IDs: " + notparsed + "<p>");
							out.println("ohds that are parsed successfully, new OHDs: " + parsed + "<p>");
							out.println("error was: " + wi.getError());
						} else {
							out.println("All ohds are parsed successfully: new OHDs: " + parsed + "<p>");
						}
						
					}
				}

			}
		} else if (method != null && method.equals("10")) {
			String filenum = req.getParameter("filenum");
			String company = req.getParameter("airline");
			WTOHD wto = new WTOHD();
			String result = wto.insertOHD(client, company, filenum);
			if (result == null) result = wto.getError();
			out.print(result);
		}
		
		
		

		out.flush();
		out.close();

	}
	
	public static void main(String[] args) {
		WorldTracerUtils wtutil = new WorldTracerUtils();
		HttpClient client = wtutil.connectWT("fl/",user.getCompanycode_ID());
		// String stations = getActiveStations(client,"aa");
		// System.out.println("###\n" + stations + "\n###");

		//String result = WTOHD.insertOHD(url, client, "fl", "ATLFL00033001");
		
		String airline = "NK";
		String station = "ATL";
		
		String filenum = "ATLNK10812";

		//String result = WorldTracerUtils.getRAF(client, filenum);
		//WTIncident wi = new WTIncident();
		//Incident incident = wi.retrieveIncident(result, false);

		// retrieve ohd
		//String result = WorldTracerUtils.getROF(client, filenum);
		//WTOHD wi = new WTOHD();
		//OHD ohd = wi.parseWTOHD(result,true);
		
		// retrieve all raf
		String result = wtutil.getAllRAF(client, airline, station, "A","");
		
	
		System.out.println("###\n" + result + "\n###");
	}
}
