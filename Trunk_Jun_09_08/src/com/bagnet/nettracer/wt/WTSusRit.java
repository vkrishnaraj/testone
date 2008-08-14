package com.bagnet.nettracer.wt;
import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
public class WTSusRit {
	private String error;
	private static Logger logger = Logger.getLogger(WTSusRit.class);
	
  public String insertCAHLSustoWT(HttpClient client, String companycode, String filenum){
	    String _n = ".";
		String _t = "";
		String _h = "/";

		String responseBody = null;
		
		// retrieve incident
		IncidentBMO obmo = new IncidentBMO();
		Incident incident = obmo.findIncidentByID(filenum);
		if (incident == null) {
			setError("invalid incident filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();
		
		// bags 1-10
		if (incident.getItemlist() != null && incident.getItemlist().size() > 0) {
			String temp,temp2;
			int c = 0;
			String xdesc1, xdesc2, xdesc3;
			for (c = 0;c<incident.getItemlist().size();c++) {
				// bag claimcheck
				if (incident.getClaimchecks() != null && incident.getClaimchecks().size() > c) {
					Incident_Claimcheck ic = (Incident_Claimcheck) incident.getClaimcheck_list().get(c);
					
					if (ic.getClaimchecknum().length() > 0) {
						String bt = ic.getClaimchecknum();
						if (bt.length() > 6) {
							bt = bt.substring(bt.length()-6);
						}
						sb.append("TN" + _t + incident.getStationassigned().getCompany().getCompanyCode_ID().toUpperCase() + bt.toUpperCase() + _n);
					}
				}
			
				Item item = (Item) incident.getItemlist().get(c);
				// color and type
				xdesc1 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_1());
				xdesc2 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_2());
				xdesc3 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_3());
				// if missing color, type, xdesc, then return null
				if (item.getColor() == null || item.getColor().length() == 0 || item.getBagtype() == null || item.getBagtype().length() == 0 || xdesc1 == null || xdesc2 == null || xdesc3 == null) {
					error = "incident needs to have valid color and bag type entered";
					return null;
				}
				
				// color, type, descelements
				sb.append("CT" + _t + item.getColor());
				sb.append(item.getBagtype());
				sb.append(xdesc1);
				sb.append(xdesc2);
				sb.append(xdesc3);
				sb.append(_n);				
			}
		} else {
			error = "incident needs to have valid bag information";
			return null;
		}

				
		sb.append(_n);

		// agent
		sb.append("AG" + _t + incident.getAgent().getUsername() + "/" + incident.getAgent().getCompanycode_ID() + _n);
		
		// replace string
		String wtstring = sb.toString();
		wtstring = wtstring.replace("\n", "");
		wtstring = wtstring.replace("\r", "");
		wtstring = wtstring.toUpperCase();

		
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagResponseSUS.exe?A1=" + "AHL" + "&FR=" + filenum + "&amendSUS=" + wtstring;
		getstring = getstring.replace(" ", "+");
		
		GetMethod method = null;
		try {
			
			method = new GetMethod(getstring);


			method.setDoAuthentication(true);
	
			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		
			// Execute the method.

			int statusCode = client.executeMethod(method);


			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			
	
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
			error = "fatal protocal violation" + e.toString();
			return null;
		} catch (Exception e) {
			System.err.println("Fatal error: " + e.getMessage());
			e.printStackTrace();
			error = "fatal error" + e.toString();
			return null;
		} finally {
			// Release the connection.
			if (method != null) method.releaseConnection();
			
			// get the worldtracer id
			String wt_id = StringUtils.ParseWTString2(responseBody, "<td>WM DAH ", "     /-ACCEPTED");
			if (wt_id != null && wt_id.length() >= 10) {
				// return wt_id (insert wt_id into ohd here)
				responseBody = "got worldtracer id: " + wt_id;
				incident.setWt_id(wt_id);
				HibernateUtils.save(incident);
			} else {
				error = responseBody;
				responseBody = null;
			}
				
		}
		return responseBody; 
  }
  
  public String insertCOhdSustoWT(HttpClient client, String companycode, String filenum){
	    String _n = ".";
		String _t = "";
		String _h = "/";

		String responseBody = null;
		
		// retrieve incident
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(filenum);
		if (ohd == null) {
			setError("invalid ohd filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();
		String xdesc1, xdesc2, xdesc3;
		xdesc1 = XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_1());
		xdesc2 = XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_2());
		xdesc3 = XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_3());
		// if missing color, type, xdesc, then return null
		if (ohd.getColor() == null || ohd.getColor().length() == 0 || ohd.getType() == null || ohd.getType().length() == 0 || xdesc1 == null || xdesc2 == null || xdesc3 == null) {
			error = "ohd needs to have valid color and bag type entered";
			return null;
		}

		// color, type, descelements
		sb.append("CT" + _t + ohd.getColor());
		sb.append(ohd.getType());
		sb.append(xdesc1);
		sb.append(xdesc2);
		sb.append(xdesc3);
		sb.append(_n);
		
		// manu
		if (ohd.getManufacturer().length() > 0 ) 
			sb.append("BI" + _t + ohd.getManufacturer().toUpperCase() + _n);

				
		sb.append(_n);

		// agent
		sb.append("AG" + _t + ohd.getAgent().getUsername() + "/" + ohd.getAgent().getCompanycode_ID() + _n);
		
		// replace string
		String wtstring = sb.toString();
		wtstring = wtstring.replace("\n", "");
		wtstring = wtstring.replace("\r", "");
		wtstring = wtstring.toUpperCase();

		
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagResponseSUS.exe?A1=" + "AHL" + "&FR=" + filenum + "&amendSUS=" + wtstring;
		getstring = getstring.replace(" ", "+");
		
		GetMethod method = null;
		try {
			
			method = new GetMethod(getstring);


			method.setDoAuthentication(true);
	
			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		
			// Execute the method.

			int statusCode = client.executeMethod(method);


			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			
	
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
			error = "fatal protocal violation" + e.toString();
			return null;
		} catch (Exception e) {
			System.err.println("Fatal error: " + e.getMessage());
			e.printStackTrace();
			error = "fatal error" + e.toString();
			return null;
		} finally {
			// Release the connection.
			if (method != null) method.releaseConnection();
			
			// get the worldtracer id
			String wt_id = StringUtils.ParseWTString2(responseBody, "<td>WM DAH ", "     /-ACCEPTED");
			if (wt_id != null && wt_id.length() >= 10) {
				// return wt_id (insert wt_id into ohd here)
				responseBody = "got worldtracer id: " + wt_id;
				ohd.setWt_id(wt_id);
				HibernateUtils.save(ohd);
			} else {
				error = responseBody;
				responseBody = null;
			}
				
		}
		return responseBody; 
}
  public String insertPAHLSustoWT(HttpClient client, String companycode, String filenum, String filetype, Item partresultlist){
	    String _n = ".";
		String _t = "";
		String _h = "/";

		String responseBody = null;
		
		// retrieve incident
		IncidentBMO obmo = new IncidentBMO();
		Incident incident = obmo.findIncidentByID(filenum);
		if (incident == null) {
			setError("invalid incident filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();
		
		// bags
		if (incident.getItemlist() != null && incident.getItemlist().size() > 0){
			String temp,temp2;
			int c = 0;
			String xdesc1, xdesc2, xdesc3;
			for (c = 0;c<incident.getItemlist().size();c++) {
				// bag claimcheck
				if (incident.getClaimchecks() != null && incident.getClaimchecks().size() > c) {
					Incident_Claimcheck ic = (Incident_Claimcheck) incident.getClaimcheck_list().get(c);
					
					if (ic.getClaimchecknum().length() > 0) {
						String bt = ic.getClaimchecknum();
						if (bt.length() > 6) {
							bt = bt.substring(bt.length()-6);
						}
						sb.append("TN" + _t + incident.getStationassigned().getCompany().getCompanyCode_ID().toUpperCase() + bt.toUpperCase() + _n);
					}
				}
			
				Item item = (Item) incident.getItemlist().get(c);
				// color and type
				xdesc1 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_1());
				xdesc2 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_2());
				xdesc3 = XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_3());
				// if missing color, type, xdesc, then return null
				if (item.getColor() == null || item.getColor().length() == 0 || item.getBagtype() == null || item.getBagtype().length() == 0 || xdesc1 == null || xdesc2 == null || xdesc3 == null) {
					error = "incident needs to have valid color and bag type entered";
					return null;
				}
				
				// color, type, descelements
				sb.append("CT" + _t + item.getColor());
				sb.append(item.getBagtype());
				sb.append(xdesc1);
				sb.append(xdesc2);
				sb.append(xdesc3);
				sb.append(_n);				
			}
		} else {
			error = "incident needs to have valid bag information";
			return null;
		}

				
		sb.append(_n);

		// agent
		sb.append("AG" + _t + incident.getAgent().getUsername() + "/" + incident.getAgent().getCompanycode_ID() + _n);
		
		// replace string
		String wtstring = sb.toString();
		wtstring = wtstring.replace("\n", "");
		wtstring = wtstring.replace("\r", "");
		wtstring = wtstring.toUpperCase();

		
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagResponseSUS.exe?A1=" + "AHL" + "&FR=" + filenum + "&amendSUS=" + wtstring;
		getstring = getstring.replace(" ", "+");
		
		GetMethod method = null;
		try {
			
			method = new GetMethod(getstring);


			method.setDoAuthentication(true);
	
			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		
			// Execute the method.

			int statusCode = client.executeMethod(method);


			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			
	
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
			error = "fatal protocal violation" + e.toString();
			return null;
		} catch (Exception e) {
			System.err.println("Fatal error: " + e.getMessage());
			e.printStackTrace();
			error = "fatal error" + e.toString();
			return null;
		} finally {
			// Release the connection.
			if (method != null) method.releaseConnection();
			
			// get the worldtracer id
			String wt_id = StringUtils.ParseWTString2(responseBody, "<td>WM DAH ", "     /-ACCEPTED");
			if (wt_id != null && wt_id.length() >= 10) {
				// return wt_id (insert wt_id into ohd here)
				responseBody = "got worldtracer id: " + wt_id;
				incident.setWt_id(wt_id);
				HibernateUtils.save(incident);
			} else {
				error = responseBody;
				responseBody = null;
			}
				
		}
		return responseBody; 
}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
