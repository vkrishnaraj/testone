package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.WSCoreUtil;
import com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm;

public class WTOHD {
	private String error;
	private static Logger logger = Logger.getLogger(WTOHD.class);

	/**
	 * insert ohd into WT
	 * 
	 * @param client
	 * @param companycode
	 * @return
	 */
	
/*	
	public String insertOHD(HttpClient client, String companycode, String filenum) {
		String _n = ".";
		String _t = "";
		String _h = "/";

		String responseBody = null;
		
		// retrieve ohd
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(filenum);
		if (ohd == null) {
			setError("invalid ohd filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();

		sb.append("STNARL" + _t);
		//sb.append(ohd.getHoldingStation().getStationcode());
		sb.append(ohd.getHoldingStation().getWt_stationcode());
		//sb.append("CBS");
		sb.append(ohd.getFoundAtStation().getCompany().getCompanyCode_ID());
		sb.append(_n);

		// passengers last name
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
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
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				temp = op.getMiddlename();
				if (c == 0) {
					if (temp.trim().length() > 0) sb.append("IT" + _t + temp);
				} else {
					if (temp.trim().length() > 0) sb.append(_h + temp);
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		sb.append(_n);
		if (ohd.getMembership() != null && ohd.getMembership().getMembershipnum().length() > 0) {
			sb.append("FL" + _t + ohd.getMembership().getMembershipnum() + _n);
		}

		// cellphone
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getMobile().length() > 0) {
					sb.append("CP" + _t + or.getMobile() + _n);
					c++;
				}
				if (c >= 2) break;
			}
		}
		// fax
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getAltphone().length() > 0) {
					sb.append("FX" + _t + or.getAltphone() + _n);
					c++;
				}

				if (c >= 2) break;
			}
		}

		// email
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getEmail().length() > 0) {
					sb.append("EA" + _t + or.getEmail() + _n);
					c++;
				}
				if (c >= 1) break;
			}
		}

		// flight date
		boolean hasflight = false;
		if (ohd.getItinerary() != null && ohd.getItinerary().size() > 0) {
			String temp;
			int c = 0;
			
			for (Iterator i = ohd.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary op = (OHD_Itinerary) i.next();
				temp = op.getAirline() + op.getFlightnum() + "/";
				if (DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null) != null) temp += DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null);
				if (c == 0) {
					if (temp.trim().length() > 1) sb.append("FD" + _t + temp.toUpperCase());
				} else {
					if (temp.trim().length() > 1) sb.append(_h + temp.toUpperCase());
				}
				if (temp.trim().length() > 1) {hasflight=true;c++;}
				if (c >= 4) break;
			}
		}
		sb.append(_n);
		if (!hasflight) {
			error = "Please enter a valid flight/date itinerary";
			return null;
		}
		// routing
		boolean hasrouting = false;
		if (ohd.getItinerary() != null && ohd.getItinerary().size() > 0) {
			String temp,temp2;
			int c = 0;
			for (Iterator i = ohd.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary op = (OHD_Itinerary) i.next();
				temp = op.getLegfrom();
				temp2 = op.getLegto();
				if (c == 0) {
					if (temp.trim().length() > 0) {
						sb.append("RT" + _t + temp.toUpperCase());
						c++;
						sb.append(_h + temp2.toUpperCase());
					}
				} else {
					if (temp2.trim().length() > 0) sb.append(_h + temp2.toUpperCase());
				}
				if (temp.trim().length() > 0) {hasrouting=true;c++;}
				if (c >= 5) break;
			}
		}
		if (!hasrouting) {
			error = "Please enter valid bag routings stations";
			return null;
		}
		sb.append(_n);

		// bag claimcheck
		
		if (ohd.getClaimnum().length() > 0) {
			String bt = ohd.getClaimnum();
			if (bt.length() > 6) {
				bt = bt.substring(bt.length()-6);
			}
			sb.append("TN" + _t + ohd.getFoundAtStation().getCompany().getCompanyCode_ID().toUpperCase() + bt.toUpperCase() + _n);
		} 

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
		
		// storage location
		if (ohd.getStorage_location().length() > 0 ) 
			sb.append("SL" + _t + ohd.getStorage_location().toUpperCase() + _n);
		
		// address on bag
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getAddress1().length() > 0) {
					sb.append("AB" + _t + or.getAddress1() + " " + or.getAddress2() + " " + or.getCity() + " " + or.getState_ID() + " " + or.getZip() + _n);
					c++;
				}

				if (c >= 2) break;
			}
		}
		
		// phone on bag
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getHomephone().length() > 0) {
					sb.append("BP" + _t + or.getHomephone() + _n);
					c++;
				}

				if (c >= 2) break;
			}
		}
		

		// content
		if (ohd.getItems() != null && ohd.getItems().size() > 0) {
			String temp;
			int c = 0;
			StringBuffer sbcn = new StringBuffer();
			for (Iterator i = ohd.getItems().iterator(); i.hasNext();) {
				OHD_Inventory op = (OHD_Inventory) i.next();
				if (c == 0) {
					sbcn.append(op.getCategory() + "/" + op.getDescription());
				} else {
					sbcn.append("-    " + op.getCategory() + "/" + op.getDescription());
				}
				c++;
			}
			sb.append("CN" + _t + sbcn.toString() + _n);
		}
		
		// hc and si supplemental information
		sb.append("HC" + _t + "Y" + _n);
		
		
		
		sb.append(_n);
		
		// remarks
		
		if (ohd.getRemarks() != null && ohd.getRemarks().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getRemarks().iterator(); i.hasNext();) {
				Remark op = (Remark) i.next();
				if (op.getRemarktext().length() > 0) {
					sb.append("FF" + _t + op.getRemarktext() + _n);
					c++;
				}

				if (c >= 99) break;
			}
		}
		
		
		// agent
		sb.append("AG" + _t + ohd.getAgent().getUsername() + "/" + ohd.getAgent().getCompanycode_ID() + _n);
				
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
		//String getstring = WorldTracerUtils.wt_url + "cgi-bin/bagOHD.exe";
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String cgiexe = "cgi-bin/bagOHD.exe?A1=" + companycode.toLowerCase() + "&A2=WM&STNARL="+wtstring.substring(0,5) + "&OHD=" + tempstring;
		String getstring = wt_url + cgiexe;
		getstring = getstring.replace(" ", "+");
		GetMethod method = null;
		try {
			
			method = new GetMethod(getstring);


			method.setDoAuthentication(true);
	
			// Provide custom retry handler is necessary
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

			String requestInfo = WorldTracerUtils.getWtRequest(wt_url, cgiexe);
			// Execute the method.

			int statusCode = client.executeMethod(method);


			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			WorldTracerUtils.insertWTInfo(requestInfo,responseBody);
	
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
			String wt_id = StringUtils.ParseWTString2(responseBody, "<td>WM DOH ", "     /-ACCEPTED");
			if (wt_id != null && wt_id.length() >= 10) {
				// insert wt_id into ohd
				responseBody = wt_id;
				ohd.setWt_id(wt_id);
				HibernateUtils.save(ohd);
			} else {
				error = responseBody;
				responseBody = null;
			}
				
		}
		return responseBody;
	}*/
	
	//submit ohd amend to WorldTracer
	public String amendAOH(HttpClient client,String companycode,String filenum){
		String _n = "\n";
		String _t = "";
		String _h = "/";
		String responseBody = null;
		// retrieve ohd
		OhdBMO obmo = new OhdBMO();
		OHD ohd = obmo.findOHDByID(filenum);
		if (ohd == null) {
			setError("invalid ohd filenum");
			return null;
		}
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagAOH.exe";
		getstring = getstring.replace(" ", "+");
		PostMethod method = new PostMethod(getstring);		
		method.setDoAuthentication(true);
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");
		method.addParameter("PAX","PAX");
		method.addParameter("BAG","BAG");
		method.addParameter("RTI","RTI");
		// passengers last name
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				temp = op.getLastname();
				
					if (temp.trim().length() > 0) {
						c++;
						method.addParameter("NM"+"0"+c,_t + temp + _n);	
					}
				if (c >= 3) break;
			}
		}
	

		// middle initial
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				temp = op.getMiddlename();
				
					if (temp.trim().length() > 0) {
						c++;
						method.addParameter("IT"+"0"+c , _t + temp + _n);
					}

				if (c >= 3) break;
			}
		}

		if (ohd.getMembership() != null && ohd.getMembership().getMembershipnum().length() > 0) {
			method.addParameter("FL" , _t + ohd.getMembership().getMembershipnum() + _n);
		}

	
		// cellphone
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
			
					if (or.getMobile().length() > 0) {
						c++;
						method.addParameter("PN"+"0"+c , _t + or.getHomephone()+_n);					
					}
			}
		}
		
		// fax
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				
					if (or.getAltphone().length() > 0) {
						c++;
						method.addParameter("FX"+"0"+c , _t + or.getAltphone()+_n);
						
					}				
					if (c >= 2) break;
			}
		}

		// email
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				if (or.getEmail().length() > 0) {
					method.addParameter("EA" , _t + or.getEmail() + _n);
					c++;
				}
				if (c >= 1) break;
			}
		}

		// flight date
		boolean hasflight = false;
		if (ohd.getItinerary() != null && ohd.getItinerary().size() > 0) {
			String temp;
			int c = 0;
			
			for (Iterator i = ohd.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary op = (OHD_Itinerary) i.next();
				temp = op.getAirline() + op.getFlightnum() + "/";
				if (DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null) != null) temp += DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null);
			
					if (temp.trim().length() > 1) {
						c++;
						method.addParameter("FD"+"0"+c , _t + temp.toUpperCase()+_n);
					}			
				if (c >= 4) break;
			}
		}
	
		if (!hasflight) {
			error = "Please enter a valid flight/date itinerary";
			return null;
		}
		
		// routing
		boolean hasrouting = false;
		if (ohd.getItinerary() != null && ohd.getItinerary().size() > 0) {
			String temp,temp2;
			int c = 0;
			for (Iterator i = ohd.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary op = (OHD_Itinerary) i.next();
				temp = op.getLegfrom();
				temp2 = op.getLegto();
		
					if (temp.trim().length() > 0) {
						c++;
						method.addParameter("RT"+"0"+c , _t + temp.toUpperCase()+_h + temp2.toUpperCase()+_n);

					}
		
				if (temp.trim().length() > 0) {hasrouting=true;}
				if (c >= 5) break;
			}
		}
		if (!hasrouting) {
			error = "Please enter valid bag routings stations";
			return null;
		}


		// bag claimcheck
		
		if (ohd.getClaimnum().length() > 0) {
			String bt = ohd.getClaimnum();
			if (bt.length() > 6) {
				bt = bt.substring(bt.length()-6);
			}
			method.addParameter("TN" , _t + ohd.getFoundAtStation().getCompany().getCompanyCode_ID().toUpperCase() + bt.toUpperCase() + _n);
		} 

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
		method.addParameter("CT" , _t + ohd.getColor()+ohd.getType()+xdesc1+xdesc2+xdesc3+_n);
	
		// manu
		if (ohd.getManufacturer().length() > 0 ) 
			method.addParameter("BI" , _t + ohd.getManufacturer().toUpperCase() + _n);
		
		// storage location
		if (ohd.getStorage_location().length() > 0 ) 
			method.addParameter("SL" , _t + ohd.getStorage_location().toUpperCase() + _n);
		
		// address on bag
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				
				if (or.getAddress1().length() > 0) {
					c++;
					method.addParameter("AB"+"0"+c , _t + or.getAddress1() + " " + or.getAddress2() + " " + or.getCity() + " " + or.getState_ID() + " " + or.getZip()+_n);
				}
			
				if (c >= 2) break;
			}
		}
		
		// phone on bag
		if (ohd.getPassengers() != null && ohd.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				OHD_Address or = op.getAddress(0);
				
				if (or.getHomephone().length() > 0) {
					c++;
					method.addParameter("BP"+"0"+c , _t + or.getHomephone()+_n);
					
				}
				if (c >= 2) break;
			}
		}
		

		// content
		if (ohd.getItems() != null && ohd.getItems().size() > 0) {
			String temp;
			int c = 0;
			StringBuffer sbcn = new StringBuffer();
			for (Iterator i = ohd.getItems().iterator(); i.hasNext();) {
				OHD_Inventory op = (OHD_Inventory) i.next();
				if (c == 0) {
					sbcn.append(op.getCategory() + "/" + op.getDescription());
				} else {
					sbcn.append("-    " + op.getCategory() + "/" + op.getDescription());
				}
				c++;
			}
			method.addParameter("CN" , _t + sbcn.toString() + _n);
		}
		
		// hc and si supplemental information
		method.addParameter("HC" , _t + "Y" + _n);
		
		
		
		
		// agent
		method.addParameter("AG" , _t + ohd.getAgent().getUsername() + "/" + ohd.getAgent().getCompanycode_ID() + _n);
				
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			
			
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}
	
    public String closeOHD(HttpClient client, String companycode,OHD odto){
		String responseBody = null;	
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String cgiexe = "cgi-bin/bagCOH.exe";
		String getstring =  wt_url + cgiexe;
		getstring = getstring.replace(" ", "+");
		String snm = new String();
		String _t = "";
		String _h = "/";
		
		// passengers last name
		if (odto.getPassengers() != null && odto.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = odto.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				temp = op.getLastname();
				if (c == 0) { 
					if (temp.trim().length() > 0) snm =  temp;
				} else if(snm.trim().length() <= 0){
					if (temp.trim().length() > 0) snm =  temp;
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		
		PostMethod method = new PostMethod(getstring);		
		method.setDoAuthentication(true);
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");
        method.addParameter("FR1",odto.getWt_id());
        method.addParameter("NM",snm);
        method.addParameter("AG",odto.getAgent().getUsername() + "/" + odto.getAgent().getCompanycode_ID());

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			String requestInfo = WorldTracerUtils.getWtRequest(method, cgiexe);
			// Execute the method.
			
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			int start = responseBody.indexOf("---- TYPE A ACCESS - CRT ----");
			int end = responseBody.indexOf("---- TYPE B ACCESS - TTY ----");
			if (start > 0 && end > 0) {
				responseBody = responseBody.substring(start + "---- TYPE A ACCESS - CRT ----".length(), end);
			}
			WorldTracerUtils.insertWTInfo(requestInfo,responseBody);
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
    }
    

    public String closeIncident(HttpClient client, String companycode,Incident idto){
		String responseBody = null;	
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagCAH.exe";
		getstring = getstring.replace(" ", "+");
		String snm = new String();

		
		// passengers last name
		if (idto.getPassengers() != null && idto.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = idto.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger op = (OHD_Passenger) i.next();
				temp = op.getLastname();
				if (c == 0) { 
					if (temp.trim().length() > 0) snm =  temp;
				} else if(snm.trim().length() <= 0){
					if (temp.trim().length() > 0) snm =  temp;
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		
		PostMethod method = new PostMethod(getstring);		
		method.setDoAuthentication(true);
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");
        method.addParameter("T1",idto.getWtFile().getWt_id());
        method.addParameter("NM",snm);
        method.addParameter("FS",idto.getStationcode());
        method.addParameter("RL",Integer.toString(idto.getLoss_code()));
        Company_specific_irregularity_code csic = AdminUtils.getLossCode(idto.getLoss_code(), TracingConstants.LOST_DELAY, TracingConstants.DEFAULT_LOCALE, AdminUtils.getCompany(companycode));
        method.addParameter("RC",csic.getDescription());
        method.addParameter("AG",idto.getAgent().getUsername() + "/" + idto.getAgent().getCompanycode_ID());

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			int start = responseBody.indexOf("---- TYPE A ACCESS - CRT ----");
			int end = responseBody.indexOf("---- TYPE B ACCESS - TTY ----");
			if (start > 0 && end > 0) {
				responseBody = responseBody.substring(start + "---- TYPE A ACCESS - CRT ----".length(), end);
			}

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
    }

	/**
	 * return an arraylist of worldtracer ohd ids from a list of ids
	 * @param wtdata
	 * @return
	 */
	public ArrayList<String> parseAllWTOHD(String wtdata) {
		ArrayList<String> wt_ids = new ArrayList<String>();
		
		if (wtdata == null) {
			setError("wt content is bad");
			return null;
		}
		
		String wt_id = null;
		while (true) {
			wt_id = StringUtils.ParseWTString2(wtdata, "<!-- fileref:", "-->");
			if (wt_id == null) break;
			if (wt_id.length()> 0) wt_ids.add(wt_id);
			wtdata = StringUtils.ParseWTString2(wtdata, "<!-- fileref:" + wt_id,null);
			
		}
		return wt_ids;

	}
	/**
	 * starttype = true means came from hibernate
	 * starttype = false means came from main method so don't call any hibernate code
	 * @param wtdata
	 * @param starttype
	 * @return
	 */
	public OHD parseWTOHD(String wtdata,boolean starttype,String wtstatus) {
		try {
			if (wtdata == null) {
				setError("wt content is bad");
				logger.error(error);
				return null;
			}
			
			// first figure out if this incident is new or already existing through
			// WT_column
			OHD ohd = null;

			String wt_id;
			int loc;
			//HashSet hash = new HashSet();
			//ArrayList list = new ArrayList();
			
			// get wt_id
			wt_id = StringUtils.ParseWTString2(wtdata, "WM DOH ", "\r");
			if (wt_id == null) {
				setError("unable to retrieve worldtracer id, wt content is bad");
				logger.error(error);
				return null;
			}
			System.out.println(wt_id);

			if (starttype)
				ohd = WorldTracerUtils.findOHDByWTID(wt_id);
			
			if (ohd == null) ohd = new OHD();
			ohd.setWtFile(new WorldTracerFile(wt_id));
			
			// set status to open
			Status status = new Status();
			if (wtstatus.equals(WorldTracerUtils.status_active))
				status.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
			else
				status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);	
			
			ohd.setStatus(status);
			// found station and holding station are the wt_id 0,3 characters
			String thes = wt_id.substring(0,3);
			String thec = wt_id.substring(3,5);
			Company c = null;
			Station s = TracerUtils.getStationByCode(thes, thec);
			if (s == null) {
				// no station, create the station into the database, if no company, go ahead and create the company as well
				c = AdminUtils.getCompany(thec);
				if (c == null) {
					// create company first
					c = new Company();
					c.setCompanyCode_ID(thec.toUpperCase());
					c.setCompanydesc(thec.toUpperCase());
					
					Company_Specific_Variable var = new Company_Specific_Variable();
					var.setCompanyCode_ID(c.getCompanyCode_ID());
					
					c.setVariable(var);
					
					HibernateUtils.saveCompany(c, null, false);
				} 
				
				if (c == null) {
					setError("unable to create company in nettracer, please contact admin");
					logger.error(error);
					return null;
				} else {
					// create station for this company
					s = TracerUtils.getStationByCode(thes.toUpperCase(), c.getCompanyCode_ID());
					if (s == null) {
						// create station
						s = new Station();
						s.setCompany(c);
						s.setStationcode(thes.toUpperCase());
						s.setStationdesc(thes.toUpperCase());
						s.setActive(true);
						s.setLocale(TracingConstants.DEFAULT_LOCALE);
						HibernateUtils.save(s);
					}
					
					if (s == null) {
						setError("unable to create station in nettracer, please contact admin");
						logger.error(error);
						return null;
					}
				}
				
				
			}
			
			ohd.setFoundAtStation(s);
			ohd.setHoldingStation(s);
			
			// agent
			Agent ntuser = WorldTracerUtils.getWTAgent(ohd.getFoundAtStation().getStation_ID(),thec);
			if (ntuser == null) {
				setError("unable to find designated wt_user in: " + thes + " for the company overall in nt database, please designate an user for wt first");
				logger.error(error);
				return null;
			}
			ohd.setAgent(ntuser);

			// date
			String datetimestr = StringUtils.ParseWTString2(wtdata, "BAG CREATED ", "\n");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAGS CREATED ", "\n");
				
			if (datetimestr != null) {
				String datestr = datetimestr.substring(0,7);
				String timestr = datetimestr.substring(8,12);
				Date thedate = DateUtils.convertToDate(datestr + "/" + timestr, "ddMMMyy/HHmm", null);
				ohd.setFounddate(thedate);
				ohd.setFoundtime(thedate);
			} else {
				error = "invalid create date";
				return null;
			}
			
			/*****
			 * missing: email field, frequent flyer number field
			 */
			HashSet<OHD_Address> addrhash = new HashSet<OHD_Address>();
			HashSet<OHD_Passenger> pahash = new HashSet<OHD_Passenger>();
			// passenger
			String lname, mi, salu;
			OHD_Passenger pa = new OHD_Passenger();
			OHD_Address addr = new OHD_Address();

			lname = StringUtils.ParseWTString2(wtdata, "NM01 ", ".IT01");
			if (lname == null) lname = StringUtils.ParseWTString2(wtdata, "NM01 ", "\n");
			mi = StringUtils.ParseWTString2(wtdata, ".IT01 ", "\n");
			pa.setLastname(lname);
			pa.setMiddlename(mi);
			
			String paaddr1 = StringUtils.ParseWTString2(wtdata, "PA01 ", "\n");
			String paaddr2 = StringUtils.ParseWTString2(wtdata, "PA02 ", "\n");
			String hphone = StringUtils.ParseWTString2(wtdata, "PN01 ", "\n");
			String mphone = StringUtils.ParseWTString2(wtdata, "CP01 ", "\n");
			String fax = StringUtils.ParseWTString2(wtdata, "TP01 ", "\n");

			addr.setAddress1(paaddr1);
			addr.setAddress2(paaddr2);
			addr.setHomephone(hphone);
			addr.setMobile(mphone);
			addr.setAltphone(fax);
			addr.setOhd_passenger(pa);
			
			addrhash.add(addr);
			pa.setAddresses(addrhash);
			pa.setOhd(ohd);
			pahash.add(pa);

			// passenger 2
			pa = new OHD_Passenger();
			lname = StringUtils.ParseWTString2(wtdata, "NM02 ", ".IT02");
			if (lname == null) lname = StringUtils.ParseWTString2(wtdata, "NM02 ", "\n");
			mi = StringUtils.ParseWTString2(wtdata, ".IT02 ", "\n");
			if (lname != null) {
				// has pass 2
				addrhash = new HashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(lname);
				pa.setMiddlename(mi);
				pa.setOhd(ohd);
				pahash.add(pa);

			}
			// passenger 3
			pa = new OHD_Passenger();
			lname = StringUtils.ParseWTString2(wtdata, "NM03 ", ".IT03");
			if (lname == null) lname = StringUtils.ParseWTString2(wtdata, "NM03 ", "\n");
			mi = StringUtils.ParseWTString2(wtdata, ".IT03 ", "\n");
			if (lname != null) {
				// has pass 2
				addrhash = new HashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(lname);
				pa.setMiddlename(mi);
				pa.setOhd(ohd);
				pahash.add(pa);
			}
			
			ohd.setPassengers(pahash);
			
			
			// bags

			OHD_Inventory ii = null;
			HashSet<OHD_Inventory> ii_set = null;
			
			String bagtag,color,type=null;
			int xdesc1 = 7,xdesc2 = 7,xdesc3 = 7;
			String cstr = null, ccat = null, cdesc = null;
			int ccat_id = 0;

			// two variables we are not using in NT
			// String bagdestination = StringUtils.ParseWTString2(wtdata, "DB   ", ".BL");
			// String baglastseen = StringUtils.ParseWTString2(wtdata, ".BL ", "\n");
			String itemstring = null;
			String contentstring = null;

			itemstring = StringUtils.ParseWTString2(wtdata, "/BAG01/", "/RTI/");


			bagtag = StringUtils.ParseWTString2(itemstring, "TN01 ", ".CT01");
			color = StringUtils.ParseWTString2(itemstring, ".CT01 ", "\n");
			if (color != null) {
				if (color.length() == 7) {
					type = color.substring(2,4);
					xdesc1 = WorldTracerUtils.getXdescID(color.substring(4,5));
					xdesc2 = WorldTracerUtils.getXdescID(color.substring(5,6));
					xdesc3 = WorldTracerUtils.getXdescID(color.substring(6));
					color = color.substring(0,2);
				} else if (color.length() == 8) {
					type = color.substring(3,5);
					xdesc1 = WorldTracerUtils.getXdescID(color.substring(5,6));
					xdesc2 = WorldTracerUtils.getXdescID(color.substring(6,7));
					xdesc3 = WorldTracerUtils.getXdescID(color.substring(7));
					color = color.substring(0,3);
				}
			}
			// manufacturer
			ohd.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
			ohd.setManufacturer_other(StringUtils.ParseWTString2(itemstring, "BI01 ", "\n"));
				
			// content
			ii_set = new HashSet<OHD_Inventory>();
				
			// new content, content 1
			ii = new OHD_Inventory();
	
			cstr = StringUtils.ParseWTString2(itemstring, "CC01 ", "\n");
			ccat = StringUtils.ParseWTString2(itemstring, "CC01 ", " /");
			if (ccat == null) ccat = StringUtils.ParseWTString2(itemstring, "CC01 ", "/");
			cdesc = null;
			ccat_id = 0;
			// first get category
			if (cstr != null) {
				cdesc = StringUtils.ParseWTString2(cstr, "/", null);
			}
			if (ccat != null) {
				if (starttype) ccat_id = WSCoreUtil.getContentCategory(ccat);
				else ccat_id = 0;
			}
			// if wt category is not found, then keep the category in the string
			if (ccat_id == 0) cdesc = cstr;
			if (ccat_id != 0 || (cdesc != null && cdesc.length() > 0)) {
				ii.setOHD_categorytype_ID(ccat_id);
				ii.setDescription(cdesc);
				ii.setOhd(ohd);
				ii_set.add(ii);
			}
				
	
			boolean keepgoing = true;
			if (ccat_id == 0 && (cdesc == null || cdesc.length()== 0)) keepgoing = false;
			////// content2 - x
			while (keepgoing) {
				ii = new OHD_Inventory();
				contentstring = StringUtils.ParseWTString2(itemstring, cdesc, null);
				if (contentstring == null || contentstring.length() == 0) keepgoing = false;
				
				if (keepgoing) {
					cstr = StringUtils.ParseWTString2(contentstring, "-    ", "\n");
					if (cstr == null) cstr = StringUtils.ParseWTString2(contentstring, "-    ", null);
					if (cstr == null) break;
					ccat = StringUtils.ParseWTString2(contentstring, "-    ", " /");
					if (ccat == null) ccat = StringUtils.ParseWTString2(contentstring, "-    ", "/");
					cdesc = null;
					ccat_id = 0;
					// first get category
					if (cstr != null) {
						cdesc = StringUtils.ParseWTString2(cstr, "/", "\n");
						if (cdesc == null) cdesc = StringUtils.ParseWTString2(cstr, "/", null);
						if (cdesc == null) break;
					}
					if (ccat != null) {
						if (starttype) ccat_id = WSCoreUtil.getContentCategory(ccat);
						else ccat_id = 0;
					}
					// if wt category is not found, then keep the category in the string
					if (ccat_id == 0) cdesc = cstr;
					if (ccat_id != 0 || (cdesc != null && cdesc.length() > 0)) {
						ii.setOHD_categorytype_ID(ccat_id);
						ii.setDescription(cdesc);
						ii.setOhd(ohd);
						ii_set.add(ii);
					}
				}
			}
			
			ohd.setClaimnum(bagtag);
			ohd.setColor(color);
			ohd.setType(type);
			ohd.setXdescelement_ID_1(xdesc1);
			ohd.setXdescelement_ID_2(xdesc2);
			ohd.setXdescelement_ID_3(xdesc3);
			ohd.setItems(ii_set);

			// routing and itinerary
			String flightcomp="", flightnum="", tempfdate = "";
			Date flightdate=null;
			String rt = StringUtils.ParseWTString2(wtdata, "RT   ", "\n");
			String flight = StringUtils.ParseWTString2(wtdata, "FD   ", "\n"); 
			
			/**
			 * 
			 * multiple flight
			 */
	
			StringTokenizer st = null;
			ArrayList fn_arr = new ArrayList();
			ArrayList fc_arr = new ArrayList();
			ArrayList fd_arr = new ArrayList();
			
			if (flight != null) {
				st = new StringTokenizer(flight,"/");
				while (st.hasMoreTokens()) {
					flightnum = st.nextToken();
					flightcomp = flightnum.substring(0,2);
					if (flightnum.length() > 2) flightnum = flightnum.substring(2);
					else flightnum = "";
					fn_arr.add(flightnum);
					fc_arr.add(flightcomp);
					
					flightdate = null;
					if (st.hasMoreTokens()) {
						tempfdate = st.nextToken();
						if (tempfdate != null) {
							Calendar gc = new GregorianCalendar();
							gc.setTime(ohd.getFounddate());
							tempfdate += gc.get(gc.YEAR);
							flightdate = DateUtils.convertToDate(tempfdate, "ddMMMyyyy", null);
						}
					}
					fd_arr.add(flightdate);
				}
			}
			
			st = new StringTokenizer(rt,"/");
			OHD_Itinerary oi = null;
			HashSet<OHD_Itinerary> oi_set = new HashSet<OHD_Itinerary>();
			int i = 0;
			String from,to,nextfrom=null;
			while (st.hasMoreTokens()) {
				from=null;to=null;
				oi = new OHD_Itinerary();
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;	// the from for next routing
					}
					oi.setLegfrom(from);
					oi.setLegto(to);
					if (fn_arr.size() > i) oi.setFlightnum((String)fn_arr.get(i));
					if (fc_arr.size() > i) oi.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) oi.setDepartdate((Date)fd_arr.get(i));
					oi.setOhd(ohd);
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					oi.setLegfrom(from);
					oi.setLegto(to);
					if (fn_arr.size() > i) oi.setFlightnum((String)fn_arr.get(i));
					if (fc_arr.size() > i) oi.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) oi.setDepartdate((Date)fd_arr.get(i));
					oi.setOhd(ohd);
				}
				oi_set.add(oi);
				i++;
			}
			ohd.setItinerary(oi_set);
			
			// remarks
			Remark  rm = new Remark();
			boolean hasmorerem = true;
			String rmtxt;
			HashSet<Remark> remark_set = new HashSet<Remark>();
			
			i = 1;
			String start;
			
			
			if (wt_id != null) {
				Worldtracer_Actionfiles Action_file = null;
				Action_file = WorldTracerUtils.findActionFileByOhdID(wt_id);
				String remarktext = Action_file.getAction_file_text();
//System.out.println(remarktext);
				rm.setAgent(ntuser);
				rm.setRemarktype(1);
				rm.setOhd(ohd);
				rm.setRemarktext(remarktext);
				remark_set.add(rm);
			}
			if (ntuser != null) {
				while (hasmorerem) {
					if (i < 10) start = "0" + i;
					else start = Integer.toString(i);
					rmtxt = StringUtils.ParseWTString2(wtdata, "FF" + start + " ", "\n");
					if (rmtxt != null) {
						
						rm.setAgent(ntuser);
						rm.setRemarktext(rmtxt);
						rm.setRemarktype(1);
						rm.setOhd(ohd);
						remark_set.add(rm);
					} else break;
					i++;
					
				}
			}
			
			ohd.setRemarks(remark_set);
			
			
			if (ohd == null) {
				error = "unable to create ohd object";
				return null;
			}
			
			if (ohd.getOHD_ID() != null && ohd.getOHD_ID().length() > 0) {
				//update
				System.out.println("update ohd: " + ohd.getOHD_ID());
			} else {
				System.out.println("insert new ohd");
			}
			
			
			// insert or update incident into database
			OhdBMO ibmo = new OhdBMO();
			ibmo.insertOHDForWT(ohd);

			return ohd;
		} catch (Exception e) {
			e.printStackTrace();
			setError(e.toString());
			return null;
		}
	}
	
	

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}


	
	
}
