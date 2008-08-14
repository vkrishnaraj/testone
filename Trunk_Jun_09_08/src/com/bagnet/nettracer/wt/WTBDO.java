package com.bagnet.nettracer.wt;

import java.net.URLEncoder;
import java.util.Iterator;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class WTBDO {
	private String error;
	private static Logger logger = Logger.getLogger(WTBDO.class);
	/**
	 * insert bdo into WT
	 * 
	 * @param client
	 * @param companycode
	 * @return
	 */
	
	public String insertBDO(HttpClient client, String companycode, int filenum) {
		String _n = ".";
		String _t = "";
		String _h = "/";

		String responseBody = null;
		
		// retrieve bdo
		
		BDO bdo = WorldTracerUtils.findBDOByID(filenum) ;
		if (bdo == null) {
			setError("invalid bdo filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();

		sb.append("STNARL" + _t);
		sb.append(bdo.getStation().getWt_stationcode());
		sb.append(bdo.getStation().getCompany().getCompanyCode_ID());
		sb.append(_n);

		// FILE REFERENCE NUMBER
		
		// passengers last name
		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = bdo.getPassengers().iterator(); i.hasNext();) {
				BDO_Passenger op = (BDO_Passenger) i.next();
				temp = op.getLastname();
				if (c == 0) {
					if (temp.trim().length() > 0) sb.append("NM" + _t + temp);
					
				} else {
					if (temp.trim().length() > 0) sb.append(_h + temp);
					
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		sb.append(_n);

		// middle initial
		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = bdo.getPassengers().iterator(); i.hasNext();) {
				BDO_Passenger op = (BDO_Passenger) i.next();
				temp = op.getMiddlename();
				if (c == 0) {
					if (temp.trim().length() > 0) sb.append("IT" + _t + temp);
					System.out.println(temp);
				} else {
					if (temp.trim().length() > 0) sb.append(_h + temp);
					
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		sb.append(_n);

		// address
		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = bdo.getPassengers().iterator(); i.hasNext();) {
				BDO_Passenger op = (BDO_Passenger) i.next();
				temp  = op.getAddress1();
				sb.append("PA01" + _t + _n);
				break;
			}
		}


		// homephone
		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = bdo.getPassengers().iterator(); i.hasNext();) {
				BDO_Passenger op = (BDO_Passenger) i.next();
				temp = op.getHomephone();
				sb.append("PN01" + _t + _n);
			}
		}
		
		// mobile phone
		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = bdo.getPassengers().iterator(); i.hasNext();) {
				BDO_Passenger op = (BDO_Passenger) i.next();
				temp = op.getMobile();
                sb.append("CP01" + _t + _n);
			}
		}

		



		

/*
		// zip
		if (bdo.getb.length() > 0) {
			sb.append("FS" + _t + incident.getFaultstationcode().toUpperCase() + _n);
		}
		
		// reason for loss
		if (incident.getLoss_code() > 0) {
			sb.append("RL" + _t + incident.getLoss_code() + _n);
		}
		
		// ticket number
		if (incident.getTicketnumber().length() > 0) {
			sb.append("TK" + _t + incident.getTicketnumber() + _n);
		}
		
		*/
		

		// agent
		sb.append("AG" + _t + bdo.getAgent().getUsername() + "/" + bdo.getAgent().getCompanycode_ID() + _n);
		
		// replace string
		String wtstring = sb.toString();
		wtstring = wtstring.replace("\n", "");
		wtstring = wtstring.replace("\r", "");
		wtstring = wtstring.replace("STN", "");
		wtstring = wtstring.replace("ARL", "");
		wtstring = wtstring.toUpperCase();
		if (wtstring.substring(wtstring.length()-2).equals("..")) wtstring = wtstring.substring(0,wtstring.length()-2);
		String encodedstring = wtstring.substring(5);
		String tempstring = encodedstring;
		try {
			tempstring = URLEncoder.encode(tempstring,"UTF-8");
		} catch (Exception e) {}
		
		//String getstring = WorldTracerUtils.wt_url + "cgi-bin/bagAHL.exe?A1=" + companycode.toLowerCase() + "&A2=WM&STNARL="+wtstring.substring(0,5) + "&AHL=" + encodedstring;
		//getstring = getstring.replace(" ", "+");
		
		GetMethod method = null;
		try {
			
			//method = new GetMethod(getstring);


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
				
				HibernateUtils.save(bdo);
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

	/**
	 * @param error
	 *          the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
}
