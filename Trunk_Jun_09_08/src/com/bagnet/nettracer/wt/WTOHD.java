package com.bagnet.nettracer.wt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.core.WSCoreUtil;

public class WTOHD {

	private static final Logger logger = Logger.getLogger(WTOHD.class);

	/**
	 * starttype = true means came from hibernate starttype = false means came
	 * from main method so don't call any hibernate code
	 * 
	 * @param wtdata
	 *            ohd html
	 * @param starttype
	 * @return
	 * @throws WorldTracerException
	 */
	public static OHD parseWTOHD(String wtdata, WTStatus wtstatus, Agent agent)
			throws WorldTracerException {
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
			if (wt_id == null) {
				wt_id = ohdMap.get("File_Reference_Number");
			}
			if (wt_id == null) {
				throw new WorldTracerException(
						"unable to import OHD.  no wt_id was parsed");
			}
			logger.debug("parsed worldtracer id, " + wt_id
					+ " from wt response.");

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
			String thes = wt_id.substring(0, 3);
			String thec = wt_id.substring(3, 5);
			Company c = null;
			Station s = TracerUtils.getStationByWtCode(thes, thec);
			if (s == null) {
				// no station, create the station into the database, if no
				// company, go ahead and create the company as well
				c = AdminUtils.getCompany(thec);
				if (c == null) {
					// create company first
					c = new Company();
					c.setCompanyCode_ID(limitStringLength(thec.toUpperCase(), 3));
					c.setCompanydesc(limitStringLength(thec.toUpperCase(), 255));

					Company_Specific_Variable var = new Company_Specific_Variable();
					var.setCompanyCode_ID(c.getCompanyCode_ID());

					c.setVariable(var);

					HibernateUtils.saveCompany(c, null, false);
				}

				if (c == null) {
					throw new WorldTracerException(
							String
									.format(
											"Could not Import WorldTracer OHD %s.  Could not create company %s",
											wt_id, thec));
				} else {
					// create station for this company
					s = TracerUtils.getStationByCode(thes.toUpperCase(), c
							.getCompanyCode_ID());
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
						throw new WorldTracerException(
								String
										.format(
												"Could not Import WorldTracer ohd %s.  Could not create station %s",
												wt_id, thes));
					}
				}
			}

			ohd.setFoundAtStation(s);
			ohd.setHoldingStation(s);

			// agent
			
			if (agent == null) {
				Agent ntuser = WorldTracerUtils.getWTAgent(ohd.getFoundAtStation().getStation_ID(), thec);
				if (ntuser == null) {
					throw new WorldTracerException(
							"Unable to import OHD, not default worldtracer agent found");
				}
				agent = ntuser;
			}
			ohd.setAgent(agent);
			

			// date
			String datetimestr = ohdMap.get("CreatedDate");
			if (datetimestr != null) {
				// calculate created year
				Calendar today = Calendar.getInstance();
				int thisYear = 0;
				int month = 0;
				String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
						"JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
				String createMonth = datetimestr.substring(2, 5);
				for (int i = 0; i < months.length; i++) {
					if (months[i].equalsIgnoreCase(createMonth)) {
						month = i + 1;
						break;
					}
				}
				if (month <= (today.get(Calendar.MONTH) + 1)) {
					thisYear = today.get(Calendar.YEAR);
				} else {
					thisYear = today.get(Calendar.YEAR) - 1;
				}
				datetimestr = datetimestr + thisYear + "/0000";

				if (datetimestr != null) {
					Date thedate = DateUtils.convertToDate(datetimestr,
							"ddMMMyyyy/HHmm", null);
					if (thedate == null) {
						throw new WorldTracerException(
								"unable to import WT OHD.  Unable to parse create date");
					}
					ohd.setFounddate(thedate);
					ohd.setFoundtime(thedate);
				} else {
					throw new WorldTracerException(
							"unable to import WT OHD.  Unable to parse create date");
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
			if (ohdMap.get("Passenger_Identification.Names") != null) {
				lnames = ohdMap.get("Passenger_Identification.Names").split(
						"//");
				if(lnames.length > 0) {
					pa.setLastname(limitStringLength(lnames[0].split(" ")[0], 20));
				}
			}
			String[] initials = null;
			if (ohdMap.get("Passenger_Identification.Initials") != null) {
				initials = ohdMap.get("Passenger_Identification.Initials")
						.split("//");
				if(initials.length > 0)
					pa.setMiddlename(limitStringLength(initials[0], 20));
			}
			String paaddr1 = null, paaddr2 = null, fax = null, hphone = null, wphone = null, mphone = null;
			if (ohdMap.get("Permanent_Contact_Information.Address") != null) {
				String[] streets = ohdMap.get(
						"Permanent_Contact_Information.Address").split("//");
				if (streets.length > 0) {
					paaddr1 = streets[0];
				}
				if (streets.length > 1) {
					paaddr2 = streets[1];
				}
			}
			if (ohdMap.get("Common_information.Address_on_bag") != null) {
				String[] streets = ohdMap.get(
						"Common_information.Address_on_bag").split("//");
				if (streets.length > 0) {
					paaddr1 = streets[0];
				}
				if (streets.length > 1) {
					paaddr2 = streets[1];
				}
			}
			if (ohdMap
					.get("Permanent_Contact_Information.Home/Business_Phone_Number") != null) {
				String[] hphones = ohdMap
						.get(
								"Permanent_Contact_Information.Home/Business_Phone_Number")
						.split("//");
				if (hphones.length > 0) {
					hphone = hphones[0];
				}
				if (hphones.length > 1)
					wphone = hphones[1];
			} else {
				if (ohdMap.get("Common_information.Phone") != null) {
					String[] hphones = ohdMap.get("Common_information.Phone")
							.split("//");
					if (hphones.length > 0) {
						hphone = hphones[0];
					}
					if (hphones.length > 1)
						wphone = hphones[1];
				}
			}
			if (ohdMap
					.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number") != null) {
				String[] mphones = ohdMap
						.get(
								"Permanent_Contact_Information.Cell/Mobile_Phone_Number")
						.split("//");
				if (mphones.length > 0) {
					mphone = mphones[0];
				}
			}
			if (ohdMap.get("Electronic_Contact_Information.Fax") != null) {
				String[] faxes = ohdMap.get(
						"Electronic_Contact_Information.Fax").split("//");
				if(faxes.length > 0)
					fax = faxes[0];
			}

			addr.setAddress1(limitStringLength(paaddr1, 50));
			addr.setAddress2(limitStringLength(paaddr2, 50));
			addr.setHomephone(limitStringLength(hphone, 50));
			addr.setWorkphone(limitStringLength(wphone, 50));
			addr.setMobile(limitStringLength(mphone, 50));
			addr.setAltphone(limitStringLength(fax, 50));
			addr.setOhd_passenger(pa);

			addrhash.add(addr);
			pa.setAddresses(addrhash);
			pa.setOhd(ohd);
			pahash.add(pa);

			// passenger 2
			if (lnames != null && lnames.length > 1) {
				pa = new OHD_Passenger();
				lname = lnames[1].split(" ")[0];
				// has pass 2
				addrhash = new LinkedHashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(limitStringLength(lname, 20));
				if (initials != null && initials.length > 1) {
					pa.setMiddlename(limitStringLength(initials[1], 20));
				}
				pa.setOhd(ohd);
				pahash.add(pa);
			}
			// passenger 3
			if (lnames != null && lnames.length > 2) {
				pa = new OHD_Passenger();
				lname = lnames[2].split(" ")[0];
				// has pass 3
				addrhash = new LinkedHashSet<OHD_Address>();
				addr = new OHD_Address();
				addr.setOhd_passenger(pa);
				addrhash.add(addr);
				pa.setAddresses(addrhash);
				pa.setLastname(limitStringLength(lname, 20));
				if (initials != null && initials.length > 2) {
					pa.setMiddlename(limitStringLength(initials[2], 20));
				}
				pa.setOhd(ohd);
				pahash.add(pa);
			}

			ohd.setPassengers(pahash);

			// bags
			OHD_Inventory ii = null;
			LinkedHashSet<OHD_Inventory> ii_set = null;

			String bagtag, color, type = null;
			int xdesc1 = 7, xdesc2 = 7, xdesc3 = 7;
			String cstr = null, ccat = null, cdesc = null;
			int ccat_id = 0;

			bagtag = ohdMap.get("Bag_1_details.Tag_#");
			if (bagtag != null)
				bagtag = bagtag.replace(" ", "");
			color = ohdMap.get("Bag_1_details.Color_Type");
			if (color != null) {
				color = color.replace(" ", "");
				if (color.length() == 7) {
					type = color.substring(2, 4);
					xdesc1 = WorldTracerUtils.getXdescID(color.substring(4, 5));
					xdesc2 = WorldTracerUtils.getXdescID(color.substring(5, 6));
					xdesc3 = WorldTracerUtils.getXdescID(color.substring(6));
					color = color.substring(0, 2);
				} else if (color.length() == 8) {
					type = color.substring(3, 5);
					xdesc1 = WorldTracerUtils.getXdescID(color.substring(5, 6));
					xdesc2 = WorldTracerUtils.getXdescID(color.substring(6, 7));
					xdesc3 = WorldTracerUtils.getXdescID(color.substring(7));
					color = color.substring(0, 3);
				}
			}
			// manufacturer
			ohd.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
			ohd.setManufacturer_other(limitStringLength(ohdMap
					.get("Bag_1_details.Brand_Information"), 100));

			// content
			ii_set = new LinkedHashSet<OHD_Inventory>();
			String[] cstrs = null;
			if (ohdMap.get("Bag_1_details.Bag_Contents") != null) {
				cstrs = ohdMap.get("Bag_1_details.Bag_Contents").split("//");
				cdesc = null;
				ccat_id = 0;
				for (int j = 0; j < cstrs.length; j++) {
					// new content, content 1
					ii = new OHD_Inventory();
					cstr = cstrs[j];
					// first get category
					if (cstr != null) {
						ccat = cstr.substring(0, cstr.indexOf(" "));
						cdesc = cstr.substring(cstr.indexOf(" ") + 1);
					}
					if (ccat != null) {
						ccat_id = WorldTracerUtils.getContentCategory(ccat);
					}
					// if wt category is not found, then keep the category in
					// the string
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

			ohd.setClaimnum(limitStringLength(bagtag, 13));
			ohd.setColor(limitStringLength(color, 2));
			ohd.setType(limitStringLength(type, 2));
			ohd.setXdescelement_ID_1(xdesc1);
			ohd.setXdescelement_ID_2(xdesc2);
			ohd.setXdescelement_ID_3(xdesc3);
			ohd.setItems(ii_set);

			// routing and itinerary
			String flightcomp = "", flightnum = "", tempfdate = "";
			Date flightdate = null;
			String rt = ohdMap.get("Flights_and_Routings.Route");
			if (rt != null && rt.length() > 1) {
				rt = rt.replace(" ", "");
			}
			String allFlight = ohdMap.get("Flights_and_Routings.Flight");
			String[] flights = null;
			if (allFlight != null && allFlight.length() > 1) {
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

			if (flights != null) {
				for (int j = 0; j < flights.length; j++) {
					String flight = flights[j];
					String[] fli = flight.split("/");
					
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = new GregorianCalendar();
						gc.setTime(ohd.getFounddate());
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = DateUtils.convertToDate(tempfdate,
								"ddMMMyyyy", null);
					}
					fc_arr.add(flightcomp);
					fn_arr.add(flightnum);
					fd_arr.add(flightdate);
				}
			}

			st = new StringTokenizer(rt, "/");
			OHD_Itinerary oi = null;
			LinkedHashSet<OHD_Itinerary> oi_set = new LinkedHashSet<OHD_Itinerary>();
			int i = 0;
			String from, to, nextfrom = null;
			while (st.hasMoreTokens()) {
				from = null;
				to = null;
				oi = new OHD_Itinerary();
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to; // the from for next routing
					}
					oi.setLegfrom(limitStringLength(from, 3));
					oi.setLegto(limitStringLength(to, 3));
					if (fn_arr.size() > i)
						oi.setFlightnum(limitStringLength((String) fn_arr.get(i), 4));
					if (fc_arr.size() > i)
						oi.setAirline(limitStringLength((String) fc_arr.get(i), 3));
					if (fd_arr.size() > i)
						oi.setDepartdate((Date) fd_arr.get(i));
					oi.setOhd(ohd);
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					oi.setLegfrom(limitStringLength(from, 3));
					oi.setLegto(limitStringLength(to, 3));
					if (fn_arr.size() > i)
						oi.setFlightnum(limitStringLength((String) fn_arr.get(i), 4));
					if (fc_arr.size() > i)
						oi.setAirline(limitStringLength((String) fc_arr.get(i), 2));
					if (fd_arr.size() > i)
						oi.setDepartdate((Date) fd_arr.get(i));
					oi.setOhd(ohd);
				}
				oi_set.add(oi);
				i++;
			}
			ohd.setItinerary(oi_set);

			// remarks
			Remark rm = null;
			boolean hasmorerem = true;
			String rmtxt;
			LinkedHashSet<Remark> remark_set = new LinkedHashSet<Remark>();

			i = 1;
			if (wt_id != null) {
				Worldtracer_Actionfiles Action_file = null;
				Action_file = WorldTracerUtils.findActionFileByOhdID(wt_id);
				if (Action_file != null) {
					String remarktext = Action_file.getAction_file_text();
					rm = new Remark();
					rm.setAgent(agent);
					rm.setRemarktype(1);
					rm.setCreatetime(DateUtils.formatDate(new Date(),
							TracingConstants.DB_DATETIMEFORMAT, null, null));
					rm.setOhd(ohd);
					rm.setRemarktext(remarktext);
					remark_set.add(rm);
				}
			}
			if (agent != null) {
				while (hasmorerem) {
					rm = new Remark();
					// Free Form Text max 9 items
					rmtxt = ohdMap.get("Free_Form_Text." + i);
					if (rmtxt != null) {
						rm.setAgent(agent);
						rm.setRemarktext(rmtxt);
						rm.setRemarktype(1);
						rm
								.setCreatetime(DateUtils.formatDate(new Date(),
										TracingConstants.DB_DATETIMEFORMAT,
										null, null));
						rm.setOhd(ohd);
						remark_set.add(rm);
					} else
						break;
					i++;

				}
			}

			ohd.setRemarks(remark_set);
			ohd.setStorage_location(ohdMap.get("Common_information.Storage_Location"));

			if (ohd.getOHD_ID() != null && ohd.getOHD_ID().length() > 0) {
				// update
				logger.info("update ohd: " + ohd.getOHD_ID());
			} else {
				logger.info("insert new ohd");
			}

			// insert or update incident into database
			// OhdBMO ibmo = new OhdBMO();
			// ibmo.insertOHDForWT(ohd);

			return ohd;
		} catch (Exception e) {
			throw new WorldTracerException("unexpected error importing WT OHD",
					e);
		}
	}

	private static String limitStringLength(String inputString, int maxLength) {
		// TODO Auto-generated method stub
		if (inputString == null)
			return null;

		if (inputString.length() > maxLength)
			return inputString.substring(0, maxLength);

		return inputString;
	}

	/**
	 * retrieve params from html <input...>, return Map
	 * 
	 * @param htmlBody
	 *            html body
	 * @return Map or null
	 */
	public static Map<String, String> getParamsFromHtml(String htmlBody) {
		if (htmlBody == null) {
			return null;
		}
		Pattern input_patt = Pattern.compile(
				"<SPAN>([^<>]+)[^<>\\[\\]]*<\\/SPAN>", Pattern.CASE_INSENSITIVE
						| Pattern.DOTALL);
		Matcher m = input_patt.matcher(htmlBody);
		Map<String, String> paramMap = new HashMap<String, String>();
		if (m.find()) {
			String idDate = m.group(1);
			idDate = idDate.replace("&nbsp;", "");
			int index = idDate.indexOf("(");
			paramMap.put("CreatedDate", idDate.substring(index + 1, index + 6));
		}
		input_patt = Pattern.compile(
				"<table id=\"tab_div\"[^<>]*>(.+?)<\\/table>",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		m = input_patt.matcher(htmlBody);
		String temp = "";
		if (m.find()) {
			htmlBody = m.group(1);
			input_patt = Pattern.compile(
					"<tr><td[^<>]*>(.*?)</td>(.*?)<\\/tr>",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = input_patt.matcher(htmlBody);
			String suffix = "";
			while (m.find()) {
				String title = m.group(1).trim().replace(" ", "_");
				// String value = m.group(2).replace("<br/>",
				// "//").replace("<BR/>", "//").replace("<br>",
				// "//").replace("<BR>", "//");
				String value = m.group(2).replaceAll(
						"<br/>|<BR/>|<br>|<BR>|<br />|<BR />", "//");
				if (value == null || value.equals("")) {
					if (Character.isDigit(title.charAt(0))) {
						suffix = "";
						temp = "";
						continue;
					}
					suffix = title + ".";
				} else {
					Pattern pat = Pattern.compile(
							"<span[^<>]*?>([^<>]*?)</span>",
							Pattern.CASE_INSENSITIVE);
					Matcher mat = pat.matcher(value);
					if (mat.find()) {
						if ((title == null || title.startsWith("<td"))
								&& paramMap.get(temp) != null) {
							String newValue = paramMap.get(temp)
									+ "//"
									+ mat.group(1).replace("&nbsp;", " ")
											.trim();
							paramMap.put(temp, newValue);

						} else {
							temp = suffix + title;
							paramMap.put(temp, mat.group(1).replace("&nbsp;",
									" ").trim());
						}
					}
				}
			}
		}
		return paramMap;
	}
}
