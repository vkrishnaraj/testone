package com.bagnet.nettracer.wt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.WSCoreUtil;

public class WTOHD {

	private static final Logger logger = Logger.getLogger(WTOHD.class);
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
/*	public String amendAOH(HttpClient client,String companycode,String filenum){
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
	}*/
	
/*    public String closeOHD(HttpClient client, String companycode,OHD odto){
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
    }*/


	/**
	 * starttype = true means came from hibernate
	 * starttype = false means came from main method so don't call any hibernate code
	 * @param wtdata
	 * @param starttype
	 * @return
	 * @throws WorldTracerException 
	 */
	public static OHD parseWTOHD(String wtdata, WTStatus wtstatus) throws WorldTracerException {
		try {
			if (wtdata == null) {
				throw new WorldTracerException("no OHD data to parse");
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
				wt_id = StringUtils.ParseWTString2(wtdata, "WT DOH ", "\r");
			}
			if(wt_id == null) {
				throw new WorldTracerException("unable to import OHD.  no wt_id was parsed");
			}
			System.out.println(wt_id);

			ohd = WorldTracerUtils.findOHDByWTID(wt_id);
			
			if (ohd == null) ohd = new OHD();
			ohd.setWtFile(new WorldTracerFile(wt_id));
			
			// set status to open
			Status status = new Status();
			if (wtstatus.equals(WTStatus.ACTIVE))
				status.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
			else
				status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);	
			
			ohd.setStatus(status);
			// found station and holding station are the wt_id 0,3 characters
			String thes = wt_id.substring(0,3);
			String thec = wt_id.substring(3,5);
			Company c = null;
			Station s = TracerUtils.getStationByWtCode(thes, thec);
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
					throw new WorldTracerException(String.format("Could not Import WorldTracer OHD %s.  Could not create company %s", wt_id, thec));
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
						throw new WorldTracerException(String.format("Could not Import WorldTracer ohd %s.  Could not create station %s", wt_id, thes));
					}
				}
				
				
			}
			
			ohd.setFoundAtStation(s);
			ohd.setHoldingStation(s);
			
			// agent
			Agent ntuser = WorldTracerUtils.getWTAgent(ohd.getFoundAtStation().getStation_ID(),thec);
			if (ntuser == null) {
				throw new WorldTracerException("Unable to import OHD, not default worldtracer agent found");
			}
			ohd.setAgent(ntuser);

			// date
			String datetimestr = StringUtils.ParseWTString2(wtdata, "BAG CREATED ", "\n");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAGS CREATED ", "\n");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "CREATED ", "\n");
				
			if (datetimestr != null) {
				String datestr = datetimestr.substring(0,7);
				String timestr = datetimestr.substring(8,12);
				Date thedate = DateUtils.convertToDate(datestr + "/" + timestr, "ddMMMyy/HHmm", null);
				if(thedate == null) {
					throw new WorldTracerException("unable to import WT OHD.  Unable to parse create date");
				}
				ohd.setFounddate(thedate);
				ohd.setFoundtime(thedate);
			} else {
				throw new WorldTracerException("unable to import WT OHD.  Unable to parse create date");
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
			if(itemstring == null) itemstring = StringUtils.ParseWTString2(wtdata, "/BAG01/", "/OSI/");
			if(itemstring == null) itemstring = StringUtils.ParseWTString2(wtdata, "/BAG01/", "/HIS/");


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
				ccat_id = WSCoreUtil.getContentCategory(ccat);
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
			int itincount = 0;
			while (keepgoing && itincount < 10) {
				itincount ++;
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
						ccat_id = WSCoreUtil.getContentCategory(ccat);
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
			throw new WorldTracerException("unexpected error importing WT OHD", e);
		}
	}
	
	/**
	 * starttype = true means came from hibernate
	 * starttype = false means came from main method so don't call any hibernate code
	 * @param wtdata ohd html
	 * @param starttype
	 * @return
	 * @throws WorldTracerException 
	 */
	public static OHD newParseWTOHD(String wtdata, WTStatus wtstatus) throws WorldTracerException {
		try {
			if (wtdata == null) {
				throw new WorldTracerException("no OHD data to parse");
			}
			
			// first figure out if this ohd is new or already existing through
			// WT_column
			OHD ohd = null;
			Map<String, String> ohdMap = getParamsFromHtml(wtdata);

			String wt_id;
			wt_id = ohdMap.get("Record_Number");
			if(wt_id == null) {
				throw new WorldTracerException("unable to import OHD.  no wt_id was parsed");
			}
			logger.debug("parsed worldtracer id, " + wt_id + " from wt response.");

			ohd = WorldTracerUtils.findOHDByWTID(wt_id);
			
			if (ohd == null) 
				ohd = new OHD();
			ohd.setWtFile(new WorldTracerFile(wt_id));
			
			// set status to open
			Status status = new Status();
			if (wtstatus.equals(WTStatus.ACTIVE))
				status.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
			else
				status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);	
			
			ohd.setStatus(status);
			// found station and holding station are the wt_id 0,3 characters
			String thes = wt_id.substring(0,3);
			String thec = wt_id.substring(3,5);
			Company c = null;
			Station s = TracerUtils.getStationByWtCode(thes, thec);
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
					throw new WorldTracerException(String.format("Could not Import WorldTracer OHD %s.  Could not create company %s", wt_id, thec));
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
						throw new WorldTracerException(String.format("Could not Import WorldTracer ohd %s.  Could not create station %s", wt_id, thes));
					}
				}
			}
			
			ohd.setFoundAtStation(s);
			ohd.setHoldingStation(s);
			
			// agent
			Agent ntuser = WorldTracerUtils.getWTAgent(ohd.getFoundAtStation().getStation_ID(),thec);
			if (ntuser == null) {
				throw new WorldTracerException("Unable to import OHD, not default worldtracer agent found");
			}
			ohd.setAgent(ntuser);

			// date
			String datetimestr = ohdMap.get("CreatedDate");
			if(datetimestr != null){
				//calculate created year
				Calendar today = Calendar.getInstance();
				int thisYear = 0;
				int month = 0;
				String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				String createMonth = datetimestr.substring(2, 5);
				for(int i = 0; i < months.length; i++){
					if(months[i].equalsIgnoreCase(createMonth)){
						month = i + 1;
						break;
					}
				}
				if(month <= (today.get(Calendar.MONTH) + 1)){
					thisYear = today.get(Calendar.YEAR);
				}else{
					thisYear = today.get(Calendar.YEAR) - 1;
				}
				datetimestr = datetimestr + thisYear + "/0000"; 
				
				if (datetimestr != null) {
					Date thedate = DateUtils.convertToDate(datetimestr, "ddMMMyyyy/HHmm", null);
					if(thedate == null) {
						throw new WorldTracerException("unable to import WT OHD.  Unable to parse create date");
					}
					ohd.setFounddate(thedate);
					ohd.setFoundtime(thedate);
				} else {
					throw new WorldTracerException("unable to import WT OHD.  Unable to parse create date");
				}
			}
			/*****
			 * missing: email field, frequent flyer number field
			 */
			LinkedHashSet<OHD_Address> addrhash = new LinkedHashSet<OHD_Address>();
			LinkedHashSet<OHD_Passenger> pahash = new LinkedHashSet<OHD_Passenger>();
			// passenger
			String lname;
			OHD_Passenger pa = new OHD_Passenger();
			OHD_Address addr = new OHD_Address();

			String[] lnames = null;
			if(ohdMap.get("Passenger_Identification.Names") != null){
				lnames = ohdMap.get("Passenger_Identification.Names").split("//");
				pa.setLastname(lnames[0].split(" ")[0]);
			}
			String[] initials = null;
			if(ohdMap.get("Passenger_Identification.Initials") != null){
				initials = ohdMap.get("Passenger_Identification.Initials").split("//");
				pa.setMiddlename(initials[0]);
			}
			String paaddr1=null, paaddr2=null, fax=null, hphone=null, wphone=null, mphone=null;
			if(ohdMap.get("Permanent_Contact_Information.Address") != null){
				String[] streets = ohdMap.get("Permanent_Contact_Information.Address").split("//");
				paaddr1 = streets[0];
				if(streets.length > 1){
					paaddr2 = streets[1];
				}
			}
		    if(ohdMap.get("Common_information.Address_on_bag") != null){
				String[] streets = ohdMap.get("Common_information.Address_on_bag").split("//");
				paaddr1 = streets[0];
				if(streets.length > 1){
					paaddr2 = streets[1];
				}
			}
			if(ohdMap.get("Permanent_Contact_Information.Home/Business_Phone_Number") != null){
				String[] hphones = ohdMap.get("Permanent_Contact_Information.Home/Business_Phone_Number").split("//");
				hphone = hphones[0];
				if(hphones.length > 1)
					wphone = hphones[1];
			}else{
				if(ohdMap.get("Common_information.Phone") != null){
					String[] hphones = ohdMap.get("Common_information.Phone").split("//");
					hphone = hphones[0];
					if(hphones.length > 1)
						wphone = hphones[1];
				}
			}
			if(ohdMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number") != null){
				String[] mphones = ohdMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number").split("//");
				mphone = mphones[0];
			}
			if(ohdMap.get("Electronic_Contact_Information.Fax") != null){
				String[] faxes = ohdMap.get("Electronic_Contact_Information.Fax").split("//");
				fax = faxes[0];
			}

			addr.setAddress1(paaddr1);
			addr.setAddress2(paaddr2);
			addr.setHomephone(hphone);
			addr.setWorkphone(wphone);
			addr.setMobile(mphone);
			addr.setAltphone(fax);
			addr.setOhd_passenger(pa);
			
			addrhash.add(addr);
			pa.setAddresses(addrhash);
			pa.setOhd(ohd);
			pahash.add(pa);

			// passenger 2
			if(lnames != null && lnames.length > 1){
				pa = new OHD_Passenger();
				lname = lnames[1].split(" ")[0];
				// has pass 2
				addrhash = new LinkedHashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(lname);
				if(initials != null && initials.length > 1){
					pa.setMiddlename(initials[1]);
				}
				pa.setOhd(ohd);
				pahash.add(pa);
			}
			// passenger 3
			if(lnames != null && lnames.length > 2){
				pa = new OHD_Passenger();
				lname = lnames[2].split(" ")[0];
				// has pass 3
				addrhash = new LinkedHashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(lname);
				if(initials != null && initials.length > 2){
					pa.setMiddlename(initials[2]);
				}
				pa.setOhd(ohd);
				pahash.add(pa);
			}
			
			ohd.setPassengers(pahash);
			
			// bags
			OHD_Inventory ii = null;
			LinkedHashSet<OHD_Inventory> ii_set = null;
			
			String bagtag,color,type=null;
			int xdesc1 = 7,xdesc2 = 7,xdesc3 = 7;
			String cstr = null, ccat = null, cdesc = null;
			int ccat_id = 0;

			bagtag = ohdMap.get("Bag_1_details.Tag_#");
			if(bagtag != null)
				bagtag = bagtag.replace(" ", "");
			color = ohdMap.get("Bag_1_details.Color_Type");
			if (color != null) {
				color = color.replace(" ", "");
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
			ohd.setManufacturer_other(ohdMap.get("Bag_1_details.Brand_Information"));
				
			// content
			ii_set = new LinkedHashSet<OHD_Inventory>();
			String[] cstrs = null;
			if(ohdMap.get("Bag_1_details.Bag_Contents") != null){
				cstrs = ohdMap.get("Bag_1_details.Bag_Contents").split("//");
				cdesc = null;
				ccat_id = 0;
				for(int j = 0; j < cstrs.length; j++){
					// new content, content 1
					ii = new OHD_Inventory();
					cstr = cstrs[j];
					// first get category
					if (cstr != null) {
						ccat = cstr.substring(0, cstr.indexOf(" "));
						cdesc = cstr.substring(cstr.indexOf(" ") + 1);
					}
					if (ccat != null) {
						ccat_id = WSCoreUtil.getContentCategory(ccat);
					}
					// if wt category is not found, then keep the category in the string
					if (ccat_id == 0) 
						cdesc = cstr;
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
			String rt = ohdMap.get("Flights_and_Routings.Route");
			if(rt != null && rt.length() > 1){
				rt = rt.replace(" ", "");
			}
			String allFlight = ohdMap.get("Flights_and_Routings.Flight");
			String[] flights = null;
			if(allFlight != null && allFlight.length() > 1){
				flights = allFlight.replace(" ", "").split("//");
			}
			
			/**
			 * 
			 * multiple flight
			 */
	
			StringTokenizer st = null;
			ArrayList<String> fn_arr = new ArrayList<String>();
			ArrayList<String> fc_arr = new ArrayList<String>();
			ArrayList<Date> fd_arr = new ArrayList<Date>();
			
			if(flights != null){
				for(int j = 0; j < flights.length; j++){
					String flight = flights[j];
					String[] fli = flight.split("/");
					flightcomp = fli[0];
					flightnum = fli[1];
					tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = new GregorianCalendar();
						gc.setTime(ohd.getFounddate());
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = DateUtils.convertToDate(tempfdate, "ddMMMyyyy", null);
					}
					fc_arr.add(flightcomp);
					fn_arr.add(flightnum);
					fd_arr.add(flightdate);
				}
			}
			
			st = new StringTokenizer(rt,"/");
			OHD_Itinerary oi = null;
			LinkedHashSet<OHD_Itinerary> oi_set = new LinkedHashSet<OHD_Itinerary>();
			int i = 0;
			String from,to,nextfrom=null;
			while (st.hasMoreTokens()) {
				from=null; to=null;
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
			Remark  rm = null;
			boolean hasmorerem = true;
			String rmtxt;
			LinkedHashSet<Remark> remark_set = new LinkedHashSet<Remark>();
			
			i = 1;
			if (wt_id != null) {
				Worldtracer_Actionfiles Action_file = null;
				Action_file = WorldTracerUtils.findActionFileByOhdID(wt_id);
				if(Action_file != null){
					String remarktext = Action_file.getAction_file_text();
					rm = new Remark();
					rm.setAgent(ntuser);
					rm.setRemarktype(1);
					rm.setCreatetime(DateUtils.formatDate(new Date(), TracingConstants.DB_DATETIMEFORMAT, null, null));
					rm.setOhd(ohd);
					rm.setRemarktext(remarktext);
					remark_set.add(rm);
				}
			}
			if (ntuser != null) {
				while (hasmorerem) {
					rm = new Remark();
					//Free Form Text max 9 items
					rmtxt = ohdMap.get("Free_Form_Text." + i);
					if (rmtxt != null) {
						rm.setAgent(ntuser);
						rm.setRemarktext(rmtxt);
						rm.setRemarktype(1);
						rm.setCreatetime(DateUtils.formatDate(new Date(), TracingConstants.DB_DATETIMEFORMAT, null, null));
						rm.setOhd(ohd);
						remark_set.add(rm);
					} else break;
					i++;
					
				}
			}
			
			ohd.setRemarks(remark_set);
			ohd.setStorage_location(ohdMap.get("Common_information.Storage_Location"));
			
			if (ohd.getOHD_ID() != null && ohd.getOHD_ID().length() > 0) {
				//update
				logger.info("update ohd: " + ohd.getOHD_ID());
			} else {
				logger.info("insert new ohd");
			}
			
			// insert or update incident into database
			OhdBMO ibmo = new OhdBMO();
			ibmo.insertOHDForWT(ohd);

			return ohd;
		} catch (Exception e) {
			throw new WorldTracerException("unexpected error importing WT OHD", e);
		}
	}
	/**
	 * retrieve params from html <input...>, return Map
	 * @param htmlBody html body
	 * @return Map or null
	 */
	public static Map<String, String> getParamsFromHtml(String htmlBody){
		if(htmlBody == null){
			return null;
		}
		Pattern input_patt = Pattern.compile("<SPAN>([^<>]+)[^<>\\[\\]]*<\\/SPAN>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = input_patt.matcher(htmlBody);
		Map<String, String> paramMap = new HashMap<String, String>();
		if (m.find()) {
			String idDate = m.group(1);
			idDate = idDate.replace("&nbsp;", "");
			int index = idDate.indexOf("(");
			paramMap.put("CreatedDate", idDate.substring(index + 1, index + 6));
		}
		input_patt = Pattern.compile("<table id=\"tab_div\"[^<>]*>(.+?)<\\/table>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		m = input_patt.matcher(htmlBody);
		String temp = "";
		if(m.find()){
			htmlBody = m.group(1); 
			input_patt = Pattern.compile("<tr><td[^<>]*>(.*?)</td>(.*?)<\\/tr>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = input_patt.matcher(htmlBody);
			String suffix = "";
			while(m.find()){
				String title = m.group(1).trim().replace(" ", "_");
				//String value = m.group(2).replace("<br/>", "//").replace("<BR/>", "//").replace("<br>", "//").replace("<BR>", "//");
				String value = m.group(2).replaceAll("<br/>|<BR/>|<br>|<BR>|<br />|<BR />", "//");
				if(value == null || value.equals("")){
					if(Character.isDigit(title.charAt(0))){
						suffix = "";
						temp = "";
						continue;
					}
					suffix = title + ".";
				}
				else{
					Pattern pat = Pattern.compile("<span[^<>]*?>([^<>]*?)</span>", Pattern.CASE_INSENSITIVE);
					Matcher mat = pat.matcher(value);
					if(mat.find()){
						if((title == null || title.startsWith("<td")) && paramMap.get(temp) != null){
							String newValue = paramMap.get(temp) + "//" + mat.group(1).replace("&nbsp;", " ").trim();
							paramMap.put(temp, newValue);
							
						}else{
							temp = suffix + title;
							paramMap.put(temp, mat.group(1).replace("&nbsp;", " ").trim());
						}
					}
				}
			}
		}
		return paramMap;
	}
}
