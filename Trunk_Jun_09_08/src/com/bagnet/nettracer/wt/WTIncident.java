package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditCompanyUtils;
import com.bagnet.nettracer.ws.core.WSCoreUtil;

public class WTIncident {
	private String error;
	private static Logger logger = Logger.getLogger(WTIncident.class);
	
	/**
	 * insert incident into WT
	 * 
	 * @param client
	 * @param companycode
	 * @return
	 */
	public String insertIncident(HttpClient client, String companycode, String filenum) {
		String fieldSep = ".";
		String _t = "";
		String entrySep = "/";

		String responseBody = null;
		
		// retrieve ohd
		IncidentBMO obmo = new IncidentBMO();
		Incident incident = obmo.findIncidentByID(filenum);
		if (incident == null) {
			setError("invalid incident filenum");
			return null;
		}
		
		// generate post string _s
		StringBuffer sb = new StringBuffer();

		sb.append("STNARL" + _t);
		sb.append(incident.getStationassigned().getStationcode());
		//System.out.println(incident.getStationassigned().getStationcode());
		//sb.append("CBS");
		sb.append(incident.getStationassigned().getCompany().getCompanyCode_ID());
		//System.out.println(incident.getStationassigned().getCompany().getCompanyCode_ID());
		sb.append(fieldSep);
		
		ArrayList<String> allFields = new ArrayList<String>();

		// passengers last name and initials
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			int c = 0;
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> initials = new ArrayList<String>();
			for (Passenger p : (Set<Passenger>) incident.getPassengers()) {
				if(p.getLastname() != null && p.getLastname().trim().length() > 0) {
					names.add(p.getLastname().trim());
					if(p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
						initials.add(p.getFirstname().substring(0, 1) + p.getLastname().substring(0, 1));
					}
					else {
						initials.add(p.getLastname().substring(0, 1));
					}
					c++;
				}
				if(c >= 3) break;
			}
			if (names.size() > 0) {
				allFields.add("NM" + StringUtils.join(names, entrySep));
				allFields.add("IT" + StringUtils.join(initials, entrySep));
			}
		}

		// passenger title
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			Passenger p = (Passenger) incident.getPassengers().iterator().next();
			if (p.getSalutationdesc() != null && p.getSalutationdesc().trim().length() > 0) {
				allFields.add("PT" + p.getSalutationdesc().trim());
			}
			if (p.getAirlinememstatus() != null && p.getAirlinememstatus().length() > 0) {
				allFields.add("PS" + p.getAirlinememstatus());
			}
			if (p.getAirlinememnumber() != null && p.getAirlinememnumber().length() > 0) {
				allFields.add("FL" + p.getAirlinememnumber());
			}
			Address or = p.getAddress(0);
			if (or.getAddress1().length() > 0 || or.getCity().length() > 0 || or.getState().length() > 0 || or.getZip().length() > 0) {
				allFields.add("PA" + (or.getAddress1() + " " + or.getCity() + " " + or.getState() + " " + or.getZip()).trim());
			}
			if (or.getEmail() != null && or.getEmail().length() > 0) {
				String temp = or.getEmail();
				temp = temp.replaceAll("@", "/A/").replaceAll("\\.", "/D/").replaceAll("_", "/U/").replaceAll("\\+", "/P/").replaceAll("~", "/T/");
				allFields.add("EA" + temp.trim());
			}
			if (or.getCountrycode_ID().length() > 0) {
				allFields.add("CO" + or.getCountrycode_ID().trim());
			}
		}

		// homephone
		if (incident.getPassengers() != null && incident.getPassengers().size() > 0) {
			int c = 0;
			for (Passenger p : (Set<Passenger>) incident.getPassengers()) {
				Address or = p.getAddress(0);
				if(or != null && or.getHomephone() != null && or.getHomephone().trim().length() > 0) {
					allFields.add("PN" + or.getHomephone().trim());
					c++;
				}
				if( c >= 2) {
					break;
				}
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
					allFields.add("TP" + _t + or.getWorkphone());
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
					allFields.add("CP" + _t + or.getMobile());
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
					allFields.add("FX" + _t + or.getAltphone());
					c++;
				}
				if (c >= 2) break;
			}
		}
		
		// local delivery method
		
		// number of passengers
		allFields.add("NP" + _t + incident.getNumpassengers());
		

		// flight date
		boolean hasflight = false;
		if (incident.getItinerary() != null && incident.getItinerary().size() > 0) {
			String temp;
			int pc = 0;
			int bc = 0;
			
			ArrayList<String> pFlights = new ArrayList<String>();
			ArrayList<String> bFlights = new ArrayList<String>();
			
			for (Iterator i = incident.getItinerary().iterator(); i.hasNext();) {
				Itinerary op = (Itinerary) i.next();
				
					temp = op.getAirline() + op.getFlightnum() + "/";
					if (DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null) != null) temp += DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null);
					if (temp.trim().length() > 1) {
						if(op.getItinerarytype() == TracingConstants.PASSENGER_ROUTING && pc < 4) {
							pc++;
							pFlights.add(temp.trim().toUpperCase());
						}
						else if (op.getItinerarytype() == TracingConstants.BAGGAGE_ROUTING && bc < 4) {
							bc++;
							bFlights.add(temp.trim().toUpperCase());
						}
					}

				if (pc >= 4 && bc >= 4) break;
			}
			if( pc > 0 && bc > 0 ) {
				allFields.add("FD" + StringUtils.join(pFlights, entrySep));
				allFields.add("BR" + StringUtils.join(bFlights, entrySep));
			}
			else {
				error = "Please enter a valid flight/date itinerary for both passenger and bags";
				return null;
			}
		}
		
		// baggage routing
		boolean hasrouting = false;
		if (incident.getItinerary() != null && incident.getItinerary().size() > 0) {
			String temp,temp2;
			int c = 0;
			ArrayList<String> routes = new ArrayList<String>();
			
			for (Iterator i = incident.getItinerary().iterator(); i.hasNext();) {
				Itinerary op = (Itinerary) i.next();
				if (op.getItinerarytype() == TracingConstants.BAGGAGE_ROUTING) {
					temp = op.getLegfrom();
					temp2 = op.getLegto();
					if (temp.trim().length() > 0 || temp2.trim().length() > 0) {
						if (c == 0 && temp.trim().length() > 0) {
							routes.add(temp.toUpperCase());
						}
						if (temp2.trim().length() > 0) {
							routes.add(temp2.toUpperCase());
						}
						c ++;
						hasrouting = true;
					}
				}
				if (c >= 14) break;
			}
			if (c > 0) {
				allFields.add("RT" + StringUtils.join(routes, entrySep));
			}
		}
		if (!hasrouting) {
			error = "Please enter valid bag routings stations";
			return null;
		}

		// the BR is different than the RT and is not required see flights above
		/*
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
							sb.append("BR" + _t + temp.toUpperCase());
							c++;
							sb.append(entrySep + temp2.toUpperCase());
						}
					} else {
						if (temp2.trim().length() > 0) sb.append(entrySep + temp2.toUpperCase());
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
		sb.append(fieldSep);
		*/

		
		// bags 1-10
		if (incident.getItemlist() != null && incident.getItemlist().size() > 0) {
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
						allFields.add("TN" + incident.getStationassigned().getCompany().getCompanyCode_ID().toUpperCase() + bt.toUpperCase());
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
				allFields.add("CT" + item.getColor() + item.getBagtype() + xdesc1 + xdesc2 + xdesc3);

				
				// manu
				if (item.getManufacturer().length() > 0 ) 
					allFields.add("BI" + _t + item.getManufacturer().toUpperCase());
				
				// lockcode ??
				
				// content
				if (item.getInventorylist() != null && item.getInventorylist().size() > 0) {
					StringBuffer sbcn = new StringBuffer();
					for (int cc = 0;cc<item.getInventorylist().size();cc++) {
						Item_Inventory iinv = (Item_Inventory) item.getInventorylist().get(cc);
						if (cc == 0) {
							sbcn.append(iinv.getCategory() + "/" + iinv.getDescription());
						} else {
							sbcn.append(fieldSep + "-    " + iinv.getCategory() + "/" + iinv.getDescription());
						}

					}

					allFields.add(String.format("CC%02d%s", c+1, sbcn.toString()));

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
		//sb.append("HC" + _t + "Y" + fieldSep);
		allFields.add("HCY");
		
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
			allFields.add("FS" + incident.getFaultstationcode().toUpperCase());
			//sb.append("FS" + _t + incident.getFaultstationcode().toUpperCase() + fieldSep);
		}
		
		// reason for loss
		if (incident.getLoss_code() > 0) {
			allFields.add("RL" + incident.getLoss_code());
			//sb.append("RL" + _t + incident.getLoss_code() + fieldSep);
		}
		
		// ticket number
		if (incident.getTicketnumber().length() > 0) {
			allFields.add("TK" + incident.getTicketnumber());
//			sb.append("TK" + _t + incident.getTicketnumber() + fieldSep);
			//we need a PB field, but I don't know what that is yet (pooled ticket number identifier)
			allFields.add("PB"+ incident.getTicketnumber());
		}
		else {
			allFields.add("PB0000");
		}
		
		
		
		
		//sb.append(fieldSep);

		// agent
		allFields.add("AG" + incident.getAgent().getUsername() + "/" + incident.getAgent().getCompanycode_ID());
//		sb.append("AG" + _t + incident.getAgent().getUsername() + "/" + incident.getAgent().getCompanycode_ID() + fieldSep);
		
		sb.append(StringUtils.join(allFields, fieldSep));
		// replace string
		String wtstring = sb.toString();
		wtstring = wtstring.replace("\n", "");
		wtstring = wtstring.replace("\r", "");
		wtstring = wtstring.replace("STN", "");
		wtstring = wtstring.replace("ARL", "");
		wtstring = wtstring.toUpperCase();
		String encodedstring = wtstring.substring(5);
		String tempstring = encodedstring;
		try {
			tempstring = URLEncoder.encode(tempstring,"UTF-8");
		} catch (Exception e) {}
		
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagAHL.exe?A1=" + companycode.toLowerCase() + "&A2=WM&STNARL="+wtstring.substring(0,5) + "&AHL=" + encodedstring;
		getstring = getstring.replaceAll("\\s+", "%20");
		
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

			String wt_id = null;
			Pattern p = Pattern.compile("AHL\\s+(\\w{3}\\w{2}\\d{5})");
			Matcher m = p.matcher(responseBody);
			if (m.find()) {
				wt_id = m.group(1);
			}
			//String wt_id = StringUtils.ParseWTString2(responseBody, "<td>WM DAH ", "     /-ACCEPTED");
			
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
    public String closeIncident(HttpClient client, String companycode,Incident idto){
		String responseBody = null;	
		String wt_http = WorldTracerUtils.getWt_url(companycode);
		String wt_url = "http://" + wt_http + "/";
		String getstring = wt_url + "cgi-bin/bagCAH.exe";
		getstring = getstring.replace(" ", "+");
		String snm = new String();

		
		// passengers last name
		if (idto.getPassengers() != null && idto.getPassengers().size() > 0) {
			String temp = "";
			OHD_Passenger op = null;
			int c = 0;
			for (Iterator i = idto.getPassengers().iterator(); i.hasNext();) {
			
				op = (OHD_Passenger) i.next();
				if(op != null)
				temp = op.getLastname();
				if (c == 0) { 
					if (temp != null && temp.trim().length() > 0) snm =  temp;
				} else if(snm != null && snm.trim().length() <= 0){
					if (snm != null && temp.trim().length() > 0) snm =  temp;
				}
				if (temp != null && temp.trim().length() > 0) c++;
				if (c >= 3) break;
			}
		}
		
		PostMethod method = new PostMethod(getstring);		
		method.setDoAuthentication(true);
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");
        method.addParameter("T1",idto.getWt_id());
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
	public ArrayList<String> parseAllWTIncident(String wtdata) {
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
	public Incident parseWTIncident(String wtdata,boolean starttype, String wtstatus) {
		try {
			// first figure out if this incident is new or already existing through
			// WT_column
			Incident incident = null;

			String wt_id;
			int loc;
			HashSet hash = new HashSet();
			ArrayList list = new ArrayList();
			
			// get wt_id
			wt_id = StringUtils.ParseWTString2(wtdata, "WM DAH ", "\r");
			if (wt_id == null) {
				setError("unable to retrieve worldtracer id, wt content is bad");
				return null;
			}
			System.out.println(wt_id);

			if (starttype)
				incident = WorldTracerUtils.findIncidentByWTID(wt_id);
			
			if (incident == null) incident = new Incident();
			incident.setWt_id(wt_id);
			Status status = new Status();
			if (wtstatus.equals(WorldTracerUtils.status_active))
				status.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
			else
				status.setStatus_ID(TracingConstants.MBR_STATUS_CLOSED);
			
			incident.setStatus(status);
			
			// create station and assigned station are the wt_id 0,3 characters
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
						HibernateUtils.save(s);
					}
					
					if (s == null) {
						setError("unable to create station in nettracer, please contact admin");
						logger.error(error);
						return null;
					}
				}
				
				
			}
	
			incident.setStationcreated(s);
			incident.setStationassigned(s);
			

			// itemtype
			ItemType itype = new ItemType();
			itype.setItemType_ID(TracingConstants.LOST_DELAY);
			incident.setItemtype(itype);
			
			// agent
			Agent ntuser = WorldTracerUtils.getWTAgent(incident.getStationcreated().getStation_ID(),thec);
			if (ntuser == null) {
				setError("unable to find designated wt_user in: " + thes + " for the company overall in nt database, please designate an user for wt first");
				logger.error(error);
				return null;
			}
			incident.setAgent(ntuser);
			
			// remarks
			Remark  rm = new Remark();			
			HashSet<Remark> remark_set = new HashSet<Remark>();		
			if (wt_id != null) {
				Worldtracer_Actionfiles Action_file = null;
				Action_file = WorldTracerUtils.findActionFileByIncidentID((wt_id));
				String remarktext = Action_file.getAction_file_text();
				rm.setAgent(ntuser);
				rm.setRemarktype(1);
				rm.setIncident(incident);
				rm.setRemarktext(remarktext);
				remark_set.add(rm);
				incident.setRemarks(remark_set);
			}
			// date
			String datetimestr = StringUtils.ParseWTString2(wtdata, "BAG CREATED ", "/ HDQ CONTROL");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAGS CREATED ", "/ HDQ CONTROL");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAG CREATED ", "/ " + thes + " CONTROL");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAGS CREATED ", "/ " + thes + " CONTROL");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAG CREATED ", "\n");
			if (datetimestr == null) datetimestr = StringUtils.ParseWTString2(wtdata, "BAGS CREATED ", "\n");
			
			if (datetimestr != null) {
				String datestr = datetimestr.substring(0,7);
				String timestr = datetimestr.substring(8,12);
				Date thedate = DateUtils.convertToDate(datestr + "/" + timestr, "ddMMMyy/HHmm", null);
				incident.setCreatedate(thedate);
				incident.setCreatetime(thedate);
			} else {
				error = "invalid create date";
				return null;
			}
			
			
			// passenger
			String lname, mi, salu;
			Passenger pa = new Passenger();
			Address addr = new Address();

			lname = StringUtils.ParseWTString2(wtdata, "NM01 ", ".IT01");
			mi = StringUtils.ParseWTString2(wtdata, ".IT01 ", ".PR");
			pa.setLastname(lname);
			pa.setMiddlename(mi);
			pa.setSalutation(0);
			salu = StringUtils.ParseWTString2(wtdata, "PT   ", "\n");
			if (salu != null) {
				if (salu.equals("DR"))
					pa.setSalutation(1);
				else if (salu.equals("MR"))
					pa.setSalutation(2);
				else if (salu.equals("MS"))
					pa.setSalutation(3);
				else if (salu.equals("MISS"))
					pa.setSalutation(4);
				else if (salu.equals("MRS")) pa.setSalutation(5);
			}
			// address
			String street, state=null, zip = null, country, hphone, mphone;
			street = StringUtils.ParseWTString2(wtdata, "PA01 ", "\n");
			country = StringUtils.ParseWTString2(wtdata, "CO   ", "\n");
			if (country != null && country.length() > 2) country = country.substring(0, 2);
			if (street != null && street.length() > 10) {
				zip = street.substring(street.length() - 5);
				if (StringUtils.removeNonNumeric(zip).length() == 5) {
					street = street.substring(0,street.length() - 5).trim();
					if (country != null && country.equals("US")) {
						if (street != null && street.length() > 5) {
							state = street.substring(street.length() - 2);
							street = street.substring(0,street.length() - 2);
						}
					}
				} else {
					zip = null;
				}
			}
			hphone = StringUtils.ParseWTString2(wtdata, "PN01 ", "\n");
			mphone = StringUtils.ParseWTString2(wtdata, "CP01 ", "\n");
			
			addr.setAddress1(street);
			addr.setState_ID(state);
			addr.setZip(zip);
			addr.setHomephone(hphone);
			addr.setMobile(mphone);
			addr.setCountrycode_ID(country);
			addr.setPassenger(pa);
			
			hash.add(addr);
			pa.setAddresses(hash);
			pa.setIncident(incident);

			list.add(pa);

			// passenger 2
			pa = new Passenger();
			lname = StringUtils.ParseWTString2(wtdata, "NM02 ", "\n");
			if (lname != null) {
				// has pass 2
				hash = new HashSet();
				addr = new Address();
				addr.setPassenger(pa);
				hash.add(addr);
				pa.setAddresses(hash);
				pa.setLastname(lname);
				pa.setIncident(incident);
				list.add(pa);
			}
			// passenger 3
			pa = new Passenger();
			lname = StringUtils.ParseWTString2(wtdata, "NM03 ", "\n");
			if (lname != null) {
				// has pass 3
				hash = new HashSet();
				addr = new Address();
				addr.setPassenger(pa);
				hash.add(addr);
				pa.setAddresses(hash);
				pa.setLastname(lname);
				pa.setIncident(incident);
				list.add(pa);
			}
			
			incident.setPassengers(new HashSet(list));
			
			// bags
			Item item = null;
			Item_Inventory ii = null;
			ArrayList<Item> itemlist = new ArrayList<Item>();
			HashSet<Item_Inventory> ii_set = null;
			
			String bagtag,color,type=null;
			int xdesc1 = 7,xdesc2 = 7,xdesc3 = 7;
			String cstr = null, ccat = null, cdesc = null;
			int ccat_id = 0;
			
			String numbags = StringUtils.ParseWTString2(wtdata, "/DRY/  ", " BAG");
			if (numbags != null) {
				incident.setNumbagchecked(Integer.parseInt(numbags));
			}
			// two variables we are not using in NT
			// String bagdestination = StringUtils.ParseWTString2(wtdata, "DB   ", ".BL");
			// String baglastseen = StringUtils.ParseWTString2(wtdata, ".BL ", "\n");
			boolean hasmorebags = false;
			String itemstring = null;
			String contentstring = null;
			for (int i=1;i<=10;i++) {
				String starts = "0" + i;
				String ends = "0" + (i+1);
				if (i == 9) ends = "10";
				if (i == 10) starts = "10";
				
				// if no more bags, break the loop
				if (wtdata.indexOf("/BAG" + starts + "/") < 0) break;
				
				itemstring = StringUtils.ParseWTString2(wtdata, "/BAG" + starts + "/", "/BAG" + ends + "/");
				if (itemstring == null) {
					itemstring = StringUtils.ParseWTString2(wtdata, "/BAG" + starts + "/", "/RTI/");
				}
				if (itemstring == null) break;
				
				// create a new bag
				item = new Item();
			
				bagtag = StringUtils.ParseWTString2(itemstring, "TN" + starts + " ", ".CT" + starts);
				color = StringUtils.ParseWTString2(itemstring, ".CT" + starts + " ", "\n");
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
				item.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
				item.setManufacturer_other(StringUtils.ParseWTString2(itemstring, "BI01 ", "\n"));
				
				// content
				ii_set = new HashSet<Item_Inventory>();
				
				// new content, content 1
				ii = new Item_Inventory();
	
				cstr = StringUtils.ParseWTString2(itemstring, "CC" + starts + " ", "\n");
				ccat = StringUtils.ParseWTString2(itemstring, "CC" + starts + " ", " /");
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
					ii.setCategorytype_ID(ccat_id);
					ii.setDescription(cdesc);
					ii.setItem(item);
					ii_set.add(ii);
				}
	
				boolean keepgoing = true;
				if (ccat_id == 0 && (cdesc == null || cdesc.length()== 0)) keepgoing = false;
				////// content2 - x
				while (keepgoing) {
					ii = new Item_Inventory();
					contentstring = StringUtils.ParseWTString2(itemstring, cdesc, null);
					if (contentstring == null || contentstring.length() == 0) keepgoing = false;
					
					if (keepgoing) {
						cstr = StringUtils.ParseWTString2(contentstring, "-    ", "\n");
						if (cstr == null) cstr = StringUtils.ParseWTString2(contentstring, "-    ", null);
						if (cstr == null) break;
						ccat = StringUtils.ParseWTString2(contentstring, "-    ", " /");
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
							
							ii.setCategorytype_ID(ccat_id);
							ii.setDescription(cdesc);
							ii.setItem(item);
							ii_set.add(ii);
						}
					}
				}
			
				item.setClaimchecknum(bagtag);
				item.setColor(color);
				item.setBagtype(type);
				item.setXdescelement_ID_1(xdesc1);
				item.setXdescelement_ID_2(xdesc2);
				item.setXdescelement_ID_3(xdesc3);
				item.setInventory(ii_set);
				item.setIncident(incident);
				itemlist.add(item);
			}
			incident.setItemlist(itemlist);
			
			
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
							gc.setTime(incident.getCreatedate());
							tempfdate += gc.get(gc.YEAR);
							flightdate = DateUtils.convertToDate(tempfdate, "ddMMMyyyy", null);
						}
					}
					fd_arr.add(flightdate);
				}
			}
				
			st = new StringTokenizer(rt,"/");
			Itinerary iti = null;
			HashSet<Itinerary> iti_set = new HashSet<Itinerary>();
			int i = 0;
			String from,to,nextfrom=null;
			while (st.hasMoreTokens()) {
				from=null;to=null;
				iti = new Itinerary();
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;	// the from for next routing
					}
					iti.setLegfrom(from);
					iti.setLegto(to);
					if (fn_arr.size() > i) iti.setFlightnum((String)fn_arr.get(i));
					if (fc_arr.size() > i) iti.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) iti.setDepartdate((Date)fd_arr.get(i));
					iti.setIncident(incident);
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					iti.setLegfrom(from);
					iti.setLegto(to);
					if (fn_arr.size() > i) iti.setFlightnum((String)fn_arr.get(i));
					if (fc_arr.size() > i) iti.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) iti.setDepartdate((Date)fd_arr.get(i));
					iti.setIncident(incident);;
				}
				iti_set.add(iti);
				i++;
			}
			incident.setItinerary(iti_set);
			
			if (incident == null) {
				error = "unable to create incident object";
				return null;
			}
			
			if (incident.getIncident_ID() != null && incident.getIncident_ID().length() > 0) {
				//update
				System.out.println("update incident: " + incident.getIncident_ID());
			} else {
				System.out.println("insert new incident");
			}
			
			
			// insert or update incident into database
			IncidentBMO ibmo = new IncidentBMO();
			ibmo.insertIncidentForWT(incident);
			
			return incident;
		} catch (Exception e) {
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
	 * @param error
	 *          the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
