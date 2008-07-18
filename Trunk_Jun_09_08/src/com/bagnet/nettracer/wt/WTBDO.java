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
	/*
	public String insertBDO(HttpClient client, String companycode, String filenum) {
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
		sb.append(bdo.getStation().getStationcode());
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
					System.out.println(temp);
				} else {
					if (temp.trim().length() > 0) sb.append(_h + temp);
					System.out.println(temp);
				}
				if (temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		sb.append(_n);

		// middle initial
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
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

		// passenger title
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				temp = op.getSalutationdesc();
				//System.out.println(temp.trim().length());
				if (temp != null && temp.trim().length() > 0) {
					sb.append("PT" + _t + temp + _n);
					
					
				}
				// membership status
				if (op.getAirlinememstatus() != null && op.getAirlinememstatus().length() > 0) {
					sb.append("PS" + _t + op.getAirlinememstatus() + _n);
				}
				
				// membership num
				if (op.getAirlinememnumber() != null && op.getAirlinememnumber().length() > 0) {
					sb.append("FL" + _t + op.getAirlinememnumber() + _n);
				}
				
				break;
			}

		}
		
		// address, email, and country
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				Address or = op.getAddress(0);
				if (or.getAddress1().length() > 0 || or.getCity().length() > 0 || or.getState().length() > 0 || or.getZip().length() > 0) {
					sb.append("PA" + _t + (or.getAddress1() + " " + or.getCity() + " " + or.getState() + " " + or.getZip()).trim() + _n);

				}
				
				if (or.getEmail().length() > 0) {
					sb.append("EA" + _t + or.getEmail() + _n);
				}

				if (or.getCountrycode_ID().length() > 0) {
					sb.append("CO" + _t + or.getCountrycode_ID() + _n);
				}
				break;
			}
		}


		// homephone
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				Address or = op.getAddress(0);
				if (or.getHomephone().length() > 0) {
					sb.append("PN" + _t + or.getHomephone() + _n);
					c++;
				}
				if (c >= 2) break;
			}
		}
		
		// temp phone
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				Address or = op.getAddress(0);
				if (or.getWorkphone().length() > 0) {
					sb.append("TP" + _t + or.getWorkphone() + _n);
					c++;
				}
				if (c >= 2) break;
			}
		}
		
		// cell phone
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				Address or = op.getAddress(0);
				if (or.getMobile().length() > 0) {
					sb.append("CP" + _t + or.getMobile() + _n);
					c++;
				}
				if (c >= 2) break;
			}
		}
		
		
		// fax
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			String temp;
			int c = 0;
			for (Iterator i = incident.getPassengers().iterator(); i.hasNext();) {
				Passenger op = (Passenger) i.next();
				Address or = op.getAddress(0);
				if (or.getAltphone().length() > 0) {
					sb.append("FX" + _t + or.getAltphone() + _n);
					c++;
				}
				if (c >= 2) break;
			}
		}
		
		// local delivery method
		
		// number of passengers
		sb.append("NP" + _t + incident.getNumpassengers() + _n);
		

		// flight date
		boolean hasflight = false;
		if (incident.getItinerary() != null && incident.getItinerary().size() > 0) {
			String temp;
			int c = 0;
			
			for (Iterator i = incident.getItinerary().iterator(); i.hasNext();) {
				Itinerary op = (Itinerary) i.next();
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
		// baggage routing
		boolean hasrouting = false;
		if (incident.getItinerary() != null && incident.getItinerary().size() > 0) {
			String temp,temp2;
			int c = 0;
			for (Iterator i = incident.getItinerary().iterator(); i.hasNext();) {
				Itinerary op = (Itinerary) i.next();
				if (op.getItinerarytype() == TracingConstants.BAGGAGE_ROUTING) {
					temp = op.getLegfrom();
					temp2 = op.getLegto();
					if (c == 0) {
						if (temp.trim().length() > 0) {
							sb.append("BR" + _t + temp.toUpperCase());
							c++;
							sb.append(_h + temp2.toUpperCase());
						}
					} else {
						if (temp2.trim().length() > 0) sb.append(_h + temp2.toUpperCase());
					}
					if (temp.trim().length() > 0) {hasrouting=true;c++;}
					if (c >= 4) break;
				}
			}
		}
		if (!hasrouting) {
			error = "Please enter valid bag routings stations";
			return null;
		} else {
			sb.append(_n);
		}

		hasrouting = false;
		if (incident.getItinerary() != null && incident.getItinerary().size() > 0) {
			String temp,temp2;
			int c = 0;
			for (Iterator i = incident.getItinerary().iterator(); i.hasNext();) {
				Itinerary op = (Itinerary) i.next();
				if (op.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
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
					if (c >= 14) break;
				}
			}
		}
		if (!hasrouting) {
			error = "Please enter valid passenger routings stations";
			return null;
		}
		sb.append(_n);

		
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
				xdesc1 = TracerUtils.getXdescelementcode(item.getXdescelement_ID_1());
				xdesc2 = TracerUtils.getXdescelementcode(item.getXdescelement_ID_2());
				xdesc3 = TracerUtils.getXdescelementcode(item.getXdescelement_ID_3());
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
				
				// manu
				if (item.getManufacturer().length() > 0 ) 
					sb.append("BI" + _t + item.getManufacturer().toUpperCase() + _n);
				
				// lockcode ??
				
				// content
				if (item.getInventorylist() != null && item.getInventorylist().size() > 0) {
					StringBuffer sbcn = new StringBuffer();
					for (int cc = 0;cc<item.getInventorylist().size();cc++) {
						Item_Inventory iinv = (Item_Inventory) item.getInventorylist().get(cc);
						if (cc == 0) {
							sbcn.append(iinv.getCategory() + "/" + iinv.getDescription());
						} else {
							sbcn.append("-    " + iinv.getCategory() + "/" + iinv.getDescription());
						}

					}
					sb.append("CN" + _t + sbcn.toString() + _n);
				}
				
				// date bag received

				// date bag delivered

			}
		} else {
			error = "incident needs to have valid bag information";
			return null;
		}

		// bag last seen ?? (BL)
	
		// bag weight (BW)
		
		// bag ticket (BX)
	
		// cost and payment (claim, CS1 - 5)
	
		// destination on bag (DB)
	
		// date questionaire (DQ)
			
		//hc and si supplemental information
		sb.append("HC" + _t + "Y" + _n);
		
		// insurance (IN)
		// keys collected (KK)
		// toiletry kit (KT)
		// language (LA)
		// match record (MR) we are not going to fill this for sure
		// missing weight (NW)
		// Pooled Ticket Number Identifier (PB)
		// partner code (PC)
		
		// fault station
		if (incident.getFaultstationcode().length() > 0) {
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
		
		
		sb.append(_n);

		// agent
		sb.append("AG" + _t + incident.getAgent().getUsername() + "/" + incident.getAgent().getCompanycode_ID() + _n);
		
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
			wt_id = "ATLDA00000017";
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
	*/
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
