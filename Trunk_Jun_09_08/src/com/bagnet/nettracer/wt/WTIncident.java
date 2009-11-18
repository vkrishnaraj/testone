package com.bagnet.nettracer.wt;

import java.text.SimpleDateFormat;
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

import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.WSCoreUtil;

public class WTIncident {
	private static final Logger logger = Logger.getLogger(WTIncident.class);
	private static final int MAX_NAME_LENGTH = 20;
	private static final int MAX_ADDRESS_LINE = 50;
	private static final int MAX_PHONE_LENGTH = 50;
	

	/**
	 * create new incident based on WorldTracer AHL
	 * @param wtdata ahl html
	 * @param wtStatus
	 * @param agent
	 * @return
	 * @throws WorldTracerException
	 */
	public static Incident parseWTIncident(String wtdata, WTStatus wtStatus, Agent agent) throws WorldTracerException {
		try {
			// first figure out if this incident is new or already existing through
			// WT_column
			if (agent == null) {
				throw new WorldTracerException("Unable to import incident, not default worldtracer agent found");
			}
			Incident incident = null;
			Map<String, String> incidentMap = getParamsFromHtml(wtdata);

			String wt_id;
			HashSet<Address> hash = new HashSet<Address>();
			ArrayList<Passenger> list = new ArrayList<Passenger>();
			// get wt_id
			if(wtdata == null) {
				throw new WorldTracerException("unable to retrieve worldtraer id, wt content is bad");
			}
			wt_id = incidentMap.get("Record_Number");
			if (wt_id == null) {
				wt_id = incidentMap.get("File_Reference_Number");
			}
			if(wt_id == null){
				throw new WorldTracerException("unable to retrieve worldtraer id, wt content is bad");
			}
			logger.debug("parsed worldtracer id, " + wt_id + " from wt response.");

			incident = WorldTracerUtils.findIncidentByWTID(wt_id);
			
			if (incident == null) 
				incident = new Incident();
			incident.setWtFile(new WorldTracerFile(wt_id, wtStatus));
			Status ntStatus = new Status();
			if (wtStatus.equals(WTStatus.ACTIVE))
				ntStatus.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
			else
				ntStatus.setStatus_ID(TracingConstants.MBR_STATUS_CLOSED);
			
			incident.setStatus(ntStatus);
			
			// create station and assigned station are the wt_id 0,3 characters
			String thes = wt_id.substring(0,3);
			String thec = wt_id.substring(3,5);
			Company c = null;
			Station s = TracerUtils.getStationByWtCode(thes, thec);
			if (s == null) {
				// no station, create the station into the database, if no company, go ahead and create the company as well
				c = AdminUtils.getCompany(thec);
				if (c == null) {
					c = CompanyBMO.createCompany(thec, null);
				} 
				
				if (c == null) {
					throw new WorldTracerException(String.format("Could not Import WorldTracer file %s.  Could not create company %s", wt_id, thec));
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
						throw new WorldTracerException(String.format("Could not Import WorldTracer file %s.  Could not create station %s", wt_id, thes));
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
			if (agent == null) {
				throw new WorldTracerException("Unable to import incident, not default worldtracer agent found");
			}
			incident.setAgent(agent);
			
			// remarks
			Remark  rm = new Remark();			
			LinkedHashSet<Remark> remark_set = new LinkedHashSet<Remark>();		
			if (wt_id != null) {
				Worldtracer_Actionfiles Action_file = null;
				Action_file = WorldTracerUtils.findActionFileByIncidentID((wt_id));
				if(Action_file != null){
					String remarktext = Action_file.getAction_file_text();
					rm.setAgent(agent);
					rm.setRemarktype(1);
					rm.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
					rm.setIncident(incident);
					rm.setRemarktext(remarktext);
					remark_set.add(rm);
					incident.setRemarks(remark_set);
				}
				
			}
			// date
			String datetimestr = incidentMap.get("CreatedDate");
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
						throw new WorldTracerException("unable to import WT file.  Unable to parse create date");
					}
					incident.setCreatedate(thedate);
					incident.setCreatetime(thedate);
				} else {
					throw new WorldTracerException("unable to import WT file.  Unable to parse create date");
				}
			}
			// passenger
			String lname = null, salu;
			Passenger pa = new Passenger();
			Address addr = new Address();
			String[] lnames = null;
			if(incidentMap.get("Passenger_Identification.Names") != null){
				lnames = incidentMap.get("Passenger_Identification.Names").split("//");
				if(lnames.length > 0) {
					pa.setLastname(limitStringLength(lnames[0].split(" ")[0], MAX_NAME_LENGTH));
				}
			}
			String[] initials = null;
			if(incidentMap.get("Passenger_Identification.Initials") != null){
				initials = incidentMap.get("Passenger_Identification.Initials").split("//");
				if(initials.length > 0) {
					pa.setMiddlename(limitStringLength(initials[0], MAX_NAME_LENGTH));
				}
			}
			pa.setSalutation(0);
			salu = incidentMap.get("Passenger_Identification.Title");
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
			String street1=null, street2=null, state=null, zip = null, country=null, hphone=null, wphone=null, mphone1=null, mphone2=null;
			if(incidentMap.get("Permanent_Contact_Information.Address") != null){
				String[] streets = incidentMap.get("Permanent_Contact_Information.Address").split("//");
				if(streets.length > 0) street1 = streets[0];
				if(streets.length > 1){
					street2 = streets[1];
				}
				
			}
			if(incidentMap.get("Delivery_Address.State") != null){
				state = incidentMap.get("Delivery_Address.State");
			}
			if(incidentMap.get("Delivery_Address.Zip_Code") != null){
				zip = incidentMap.get("Delivery_Address.Zip_Code");
			}
			if(incidentMap.get("Delivery_Address.Country_Code") != null){
				country = incidentMap.get("Delivery_Address.Country_Code");
				if (country != null && country.length() > 2) 
					country = country.substring(0, 2);
			}
			if(incidentMap.get("Permanent_Contact_Information.Home/Business_Phone_Number") != null){
				String[] hphones = incidentMap.get("Permanent_Contact_Information.Home/Business_Phone_Number").split("//");
				if(hphones.length > 0) hphone = hphones[0];
				if(hphones.length > 1)
					wphone = hphones[1];
			}
			if(incidentMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number") != null){
				String[] mphones = incidentMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number").split("//");
				if(mphones.length > 0) mphone1 = mphones[0];
				if(mphones.length > 1){
					mphone2 = mphones[1];
				}
			}
			String fax1=null, fax2=null, email=null;
			if(incidentMap.get("Electronic_Contact_Information.Fax") != null){
				String[] faxes = incidentMap.get("Electronic_Contact_Information.Fax").split("//");
				if(faxes.length > 0) fax1 = faxes[0];
				if(faxes.length > 1){
					fax2 = faxes[1];
				}
			}
			if(incidentMap.get("Electronic_Contact_Information.Email_Address") != null){
				String[] emails = incidentMap.get("Electronic_Contact_Information.Email_Address").split("//");
				if (emails.length > 0) email = emails[0];
			}
			
			addr.setAddress1(limitStringLength(street1, MAX_ADDRESS_LINE));
			addr.setState_ID(state);
			addr.setZip(limitStringLength(zip, 9));
			addr.setHomephone(limitStringLength(hphone, MAX_PHONE_LENGTH));
			addr.setWorkphone(limitStringLength(wphone, MAX_PHONE_LENGTH));
			addr.setMobile(limitStringLength(mphone1, MAX_PHONE_LENGTH));
			addr.setAltphone(limitStringLength(fax1, MAX_PHONE_LENGTH));
			addr.setEmail(limitStringLength(email, 100));
			addr.setCountrycode_ID(country);
			addr.setPassenger(pa);
			hash.add(addr);
			pa.setAddresses(hash);
			pa.setIncident(incident);

			list.add(pa);

			// passenger 2
			pa = new Passenger();
			if(lnames != null && lnames.length > 1){
				lname = lnames[1].split(" ")[0];
				// has pass 2
				hash = new HashSet<Address>();
				addr = new Address();
				addr.setAddress1(limitStringLength(street2, MAX_ADDRESS_LINE));
				addr.setMobile(limitStringLength(mphone2, MAX_PHONE_LENGTH));
				addr.setAltphone(limitStringLength(fax2, MAX_PHONE_LENGTH));
				addr.setPassenger(pa);
				hash.add(addr);
				pa.setAddresses(hash);
				pa.setLastname(limitStringLength(lname, MAX_NAME_LENGTH));
				if(initials != null && initials.length > 1){
					pa.setMiddlename(limitStringLength(initials[1], MAX_NAME_LENGTH));
				}
				pa.setIncident(incident);
				list.add(pa);
			}
			// passenger 3
			pa = new Passenger();
			if(lnames != null && lnames.length > 2){
				// has pass 3
				lname = lnames[2].split(" ")[0];
				hash = new HashSet<Address>();
				addr = new Address();
				addr.setPassenger(pa);
				hash.add(addr);
				pa.setAddresses(hash);
				pa.setLastname(limitStringLength(lname, MAX_NAME_LENGTH));
				if(initials != null && initials.length > 2){
					pa.setMiddlename(limitStringLength(initials[2], MAX_NAME_LENGTH));
				}
				pa.setIncident(incident);
				list.add(pa);
			}
			
			incident.setPassengers(new LinkedHashSet<Passenger>(list));
			
			// bags
			Item item = null;
			Item_Inventory ii = null;
			ArrayList<Item> itemlist = new ArrayList<Item>();
			LinkedHashSet<Item_Inventory> ii_set = null;
			
			String bagtag,color,type=null;
			int xdesc1 = 7,xdesc2 = 7,xdesc3 = 7;
			String cstr = null, ccat = null, cdesc = null;
			int ccat_id = 0;
			int numbags = 0;
			for(Map.Entry<String, String> entry : incidentMap.entrySet()){
				String key = entry.getKey();
				Pattern p = Pattern.compile("Bag_([0123456789]+)", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(key);
				if(m.find()){
					int tempNum = Integer.parseInt(m.group(1));
					if(tempNum > numbags){
						numbags = tempNum;
					}
				}
			}
			if (numbags > 0) {
				incident.setNumbagchecked(numbags);
			}
			LinkedHashSet<Incident_Claimcheck> hashbagTag = new LinkedHashSet<Incident_Claimcheck>();
			for (int i=1; i<=numbags; i++) {
				// create a new bag
				item = new Item();
				bagtag = incidentMap.get("Bag_"+ i +"_details.Tag_#");
				if(bagtag != null && bagtag.length() > 1){
					bagtag = bagtag.replace(" ", "");
					Incident_Claimcheck ic = new Incident_Claimcheck();
					ic.setClaimchecknum(limitStringLength(bagtag, 13));
					ic.setIncident(incident);
					hashbagTag.add(ic);
				}
				color = incidentMap.get("Bag_"+ i +"_details.Color_Type");
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
				item.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
				String brand = incidentMap.get("Bag_"+ i +"_details.Brand_Information");
				if(brand != null){
					item.setManufacturer_other(limitStringLength(brand, 100));
				}
				// content
				ii_set = new LinkedHashSet<Item_Inventory>();
				
				String[] cstrs = null;
				if(incidentMap.get("Bag_"+ i +"_details.Bag_Contents") != null){
					cstrs = incidentMap.get("Bag_"+ i +"_details.Bag_Contents").split("//");
					cdesc = null;
					ccat_id = 0;
					for(int j = 0; j < cstrs.length; j++){
						// new content, content 1
						ii = new Item_Inventory();
						cstr = cstrs[j];
						// first get category
						if (cstr != null) {
							ccat = cstr.substring(0, cstr.indexOf(" "));
							cdesc = cstr.substring(cstr.indexOf(" ") + 1);
						}
						if (ccat != null) {
							ccat_id = WorldTracerUtils.getContentCategory(ccat);
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
				//item.setClaimchecknum(bagtag);
				item.setColor(color);
				item.setBagtype(type);
				item.setXdescelement_ID_1(xdesc1);
				item.setXdescelement_ID_2(xdesc2);
				item.setXdescelement_ID_3(xdesc3);
				item.setInventory(ii_set);
				item.setBagnumber(i-1);
				item.setIncident(incident);
				itemlist.add(item);
			}
			incident.setItemlist(itemlist);
			incident.setClaimchecks(hashbagTag);
			
			// routing and itinerary
			String flightcomp="", flightnum="", tempfdate = "";
			Date flightdate=null;
			String rt = incidentMap.get("FLIGHTS_INFORMATION.Route");
			if(rt != null && rt.length() > 1){
				rt = rt.replace(" ", "");
			}
			String allFlight = incidentMap.get("FLIGHTS_INFORMATION.Flight");
			String[] flights = null;
			if(allFlight != null && allFlight.length() > 1){
				allFlight = allFlight.replace(" ", "");
				flights = allFlight.split("//");
			}
			
			/**
			 * 
			 * multiple flight
			 */
			StringTokenizer st = null;
			ArrayList<String> fc_arr = new ArrayList<String>();
			ArrayList<String> fn_arr = new ArrayList<String>();
			ArrayList<Date> fd_arr = new ArrayList<Date>();
			if(flights != null){
				for(int j = 0; j < flights.length; j++){
					String flight = flights[j];
					String[] fli = flight.split("/");
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = new GregorianCalendar();
						gc.setTime(incident.getCreatedate());
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = DateUtils.convertToDate(tempfdate, "ddMMMyyyy", null);
					}
					fc_arr.add(flightcomp);
					fn_arr.add(flightnum);
					fd_arr.add(flightdate);
				}
			}
			//start  bag routing flight
			String bagAllFlights = incidentMap.get("Bag_Routing.Flight");
			String[] bagFlights = null;
			if(bagAllFlights != null && bagAllFlights.length() > 1){
				bagAllFlights = bagAllFlights.replace(" ", "");
				bagFlights = bagAllFlights.split("//");
			}
			//multi bag flight
			ArrayList<String> bagFlightCom_arr = new ArrayList<String>();
			ArrayList<String> bagFlightNum_arr = new ArrayList<String>();
			ArrayList<Date> bagFlightDate_arr = new ArrayList<Date>();
			if(bagFlights != null){
				for(int j = 0; j < bagFlights.length; j++){
					String flight = bagFlights[j];
					String[] fli = flight.split("/");
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = new GregorianCalendar();
						gc.setTime(incident.getCreatedate());
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = DateUtils.convertToDate(tempfdate, "ddMMMyyyy", null);
					}
					bagFlightCom_arr.add(flightcomp);
					bagFlightNum_arr.add(flightnum);
					bagFlightDate_arr.add(flightdate);
				}
			}
			//end bag flight
				
			st = new StringTokenizer(rt,"/");
			Itinerary iti = null;
			Itinerary itiBag = null;
			LinkedHashSet<Itinerary> iti_set = new LinkedHashSet<Itinerary>();
			int i = 0;
			String from,to,nextfrom=null;
			while (st.hasMoreTokens()) {
				from=null; to=null;
				//iti for passenger flight
				iti = new Itinerary();
				iti.setItinerarytype(0);
				//itiBag for bag flight
				itiBag = new Itinerary();
				itiBag.setItinerarytype(1);
				
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;	// the from for next routing
					}
					//set passenger flight
					iti.setLegfrom(limitStringLength(from, 3));
					iti.setLegto(to);
					iti.setLegfrom_type(i+1);
					iti.setLegto_type(i+2);
					if (fn_arr.size() > i) iti.setFlightnum((String)fn_arr.get(i));
					if (fc_arr.size() > i) iti.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) iti.setDepartdate((Date)fd_arr.get(i));
					iti.setIncident(incident);
					//set bag flight
					itiBag.setLegfrom(limitStringLength(from, 3));
					itiBag.setLegto(limitStringLength(to, 3));
					itiBag.setLegfrom_type(i+1);
					itiBag.setLegto_type(i+2);
					if (bagFlightCom_arr.size() > i) itiBag.setAirline(limitStringLength((String)bagFlightCom_arr.get(i), 3));
					if (bagFlightNum_arr.size() > i) itiBag.setFlightnum(limitStringLength((String)bagFlightNum_arr.get(i), 4));
					if (bagFlightDate_arr.size() > i) itiBag.setDepartdate((Date)bagFlightDate_arr.get(i));
					itiBag.setIncident(incident);
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					//set passenger flight
					iti.setLegfrom(limitStringLength(from, 3));
					iti.setLegto(limitStringLength(to, 3));
					iti.setLegfrom_type(i+1);
					iti.setLegto_type(i+2);
					if (fn_arr.size() > i) iti.setFlightnum(limitStringLength((String)fn_arr.get(i), 4));
					if (fc_arr.size() > i) iti.setAirline(limitStringLength((String)fc_arr.get(i), 3));
					if (fd_arr.size() > i) iti.setDepartdate((Date)fd_arr.get(i));
					iti.setIncident(incident);
					//set bag flight
					itiBag.setLegfrom(from);
					itiBag.setLegto(to);
					itiBag.setLegfrom_type(i+1);
					itiBag.setLegto_type(i+2);
					if (bagFlightCom_arr.size() > i) itiBag.setAirline(limitStringLength((String)bagFlightCom_arr.get(i), 3));
					if (bagFlightNum_arr.size() > i) itiBag.setFlightnum(limitStringLength((String)bagFlightNum_arr.get(i), 4));
					if (bagFlightDate_arr.size() > i) itiBag.setDepartdate((Date)bagFlightDate_arr.get(i));
					itiBag.setIncident(incident);
				}
				iti_set.add(iti);
				iti_set.add(itiBag);
				i++;
			}
			incident.setItinerary(iti_set);
			
			if (incident.getIncident_ID() != null && incident.getIncident_ID().length() > 0) {
				//update
				logger.info("update incident: " + incident.getIncident_ID());
			} else {
				logger.info("insert new incident");
			}

			// insert or update incident into database
			//IncidentBMO ibmo = new IncidentBMO();
			//ibmo.insertIncidentForWT(incident);
			
			return incident;
		} catch (WorldTracerException e) {
			throw e;
		}
		catch (Exception e) {
			throw new WorldTracerException("unknown error importing WT File", e);
		}
	}
	private static String limitStringLength(String inputString, int maxLength) {
		if(inputString == null) return null;
		
		if(inputString.length() > maxLength) return inputString.substring(0, maxLength);
		
		return inputString;
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
				//String value = m.group(2).replace("<br/>", "//").replace("<BR/>", "//");
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
