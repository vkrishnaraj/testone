/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class MBRActionUtils {
		
	public static boolean actionAdd(IncidentForm theform, HttpServletRequest request, Agent user) {
		// when adding or deleting from the page,
		// email_customer checkbox needs to be set to 0 if user unchecked it
		int fileindex = -1;
		StringTokenizer stt = null;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("addinventory") > -1) {
				fileindex = Integer.parseInt(parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]")));
			}
		}

		if (request.getParameter("email_customer") == null)
			theform.setEmail_customer(0);

		// add new remark box
		if (request.getParameter("addremark") != null) {
			//set new remark with current time
			Remark r = theform.getRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("remark", Integer.toString(theform.getRemarklist().size() - 1));
			return true;
		}
		// add passenger
		if (request.getParameter("addPassenger") != null) {
			theform.getPassenger(theform.getPassengerlist().size());
			request.setAttribute("passenger", Integer.toString(theform.getPassengerlist().size() - 1));
			return true;
		}

		// add claimcheck
		if (request.getParameter("addclaimcheck") != null) {
			theform.getClaimcheck(theform.getClaimchecklist().size());
			request.setAttribute("claimcheck", "1");
			return true;
		}
		// add new item box
		if (request.getParameter("additem") != null) {
			Item item = theform.getItem(theform.getItemlist().size(), TracingConstants.LOST_DELAY);
			item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
			item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
			item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
			item.set_DATEFORMAT(user.getDateformat().getFormat());
			item.setCurrency_ID(user.getDefaultcurrency());
			// set item status if it is not being set to open
			item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN, user.getCurrentlocale()));
			request.setAttribute("item", Integer.toString(theform.getItemlist().size() - 1));
			return true;
		}
		if (fileindex >= 0) {
			Item_Inventory ii = new Item_Inventory();
			ii.setItem(theform.getItem(fileindex, -1));
			theform.getItem(fileindex, -1).getInventorylist().add(ii);
			request.setAttribute("inventory", Integer.toString(fileindex));
			return true;
		}

		if (request.getParameter("addarticles") != null) {
			Articles a = theform.getArticle(theform.getArticlelist().size());
			a.setCurrency_ID(user.getDefaultcurrency());
			request.setAttribute("articles", Integer.toString(theform.getArticlelist().size() - 1));
			return true;
		}
		// add new itinerary box
		if (request.getParameter("addpassit") != null) {
			theform.getItinerary(theform.getItinerarylist().size(), TracingConstants.PASSENGER_ROUTING);
			request.setAttribute("passit", "1");
			return true;
		}
		if (request.getParameter("addbagit") != null) {
			theform.getItinerary(theform.getItinerarylist().size(), TracingConstants.BAGGAGE_ROUTING);
			request.setAttribute("bagit", "1");
			return true;
		}

		return false;
	}

	public static boolean actionDelete(IncidentForm theform, HttpServletRequest request) {
		/** ************* delete *************** */
		boolean deleteArticle = false;
		boolean deleteClaimcheck = false;
		boolean deletePass = false;
		boolean deleteItem = false;
		boolean deleteInventory = false;
		boolean deletePhoto = false;
		boolean deleteRemark = false;
		boolean deletePassit = false;
		boolean deleteBagit = false;

		// when adding or deleting from the page,
		// email_customer checkbox needs to be set to 0 if user unchecked it
		if (request.getParameter("email_customer") == null)
			theform.setEmail_customer(0);

		String index = "0";
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
				if (parameter.indexOf("deleteClaimcheck") != -1) {
					deleteClaimcheck = true;
					break;
				} else if (parameter.indexOf("deleteArticle") != -1) {
					deleteArticle = true;
					break;
				} else if (parameter.indexOf("deleteItem") != -1) {
					deleteItem = true;
					break;
				} else if (parameter.indexOf("deletePassenger") != -1) {
					deletePass = true;
					break;
				} else if (parameter.indexOf("deletePhoto") != -1) {
					deletePhoto = true;
					break;
				} else if (parameter.indexOf("deletePassit") != -1) {
					deletePassit = true;
					break;
				} else if (parameter.indexOf("deleteBagit") != -1) {
					deleteBagit = true;
					break;
				} else if (parameter.indexOf("deleteRemark") != -1) {
					deleteRemark = true;
					break;
				}
			} else if (parameter.indexOf("deleteinventory") > -1) {
				deleteInventory = true;
				break;

			}
		}
		if (deleteClaimcheck) {
			request.setAttribute("claimcheck", "1");
			List claimchecklist = theform.getClaimchecklist();
			if (claimchecklist != null)
				claimchecklist.remove(Integer.parseInt(index));
			return true;
		} else if (deleteItem) {
			List itemList = theform.getItemlist();
			if (itemList != null)
				itemList.remove(Integer.parseInt(index));
			request.setAttribute("item", Integer.toString(theform.getItemlist().size() - 1));
			return true;
		} else if (deleteInventory) {
			int itemindex = -1;
			int inventoryindex = -1;
			int fileindex = -1;
			StringTokenizer stt = null;
			Enumeration en = request.getParameterNames();
			while (en.hasMoreElements()) {
				String parameter = (String) en.nextElement();
				try {
					if (parameter.indexOf("deleteinventory") > -1) {
						stt = new StringTokenizer(parameter, "_");
						if (stt.hasMoreElements())
							stt.nextToken();
						if (stt.hasMoreElements())
							itemindex = Integer.parseInt(stt.nextToken());
						if (stt.hasMoreElements())
							inventoryindex = Integer.parseInt(stt.nextToken());
					}
				} catch (Exception removephotoe) {
					// tempering with data, should never happen
				}
			}

			if (itemindex >= 0 && inventoryindex >= 0) {
				Item_Inventory ii = (Item_Inventory) theform.getItem(itemindex, -1).getInventorylist().get(inventoryindex);
				theform.getItem(itemindex, -1).getInventorylist().remove(inventoryindex);
			}

			request.setAttribute("item", Integer.toString(itemindex));
			return true;

		} else if (deletePass) {

			List passList = theform.getPassengerlist();
			if (passList != null)
				passList.remove(Integer.parseInt(index));
			request.setAttribute("passenger", Integer.toString(theform.getPassengerlist().size() - 1));
			return true;
		} else if (deleteArticle) {
			request.setAttribute("articles", "1");
			List articleList = theform.getArticlelist();
			if (articleList != null)
				articleList.remove(Integer.parseInt(index));
			request.setAttribute("articles", Integer.toString(theform.getArticlelist().size() - 1));
			return true;
		} else if (deletePassit) {
			request.setAttribute("passit", "1");
			List itinerarylist = theform.getItinerarylist();
			if (itinerarylist != null)
				itinerarylist.remove(Integer.parseInt(index));
			return true;
		} else if (deleteBagit) {
			request.setAttribute("bagit", "1");
			List itinerarylist = theform.getItinerarylist();
			if (itinerarylist != null)
				itinerarylist.remove(Integer.parseInt(index));
			return true;
			//} else if (deletePhoto) {
			//	request.setAttribute("upload", "1");
			//	List itemlist = theform.getItemlist();
			//	if (photoList != null) photoList.remove(Integer.parseInt(index));
			//	return (mapping.findForward(TracingConstants.LD_MAIN));
		} else if (deleteRemark) {
			List remarkList = theform.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			request.setAttribute("remark", Integer.toString(theform.getRemarklist().size() - 1));
			return true;
		}
		return false;
	}
	
	public static boolean actionClose(IncidentForm theform, HttpServletRequest request, Agent user, ActionMessages errors) throws Exception {

		List faultstationlist = null;
		List faultCompanyList = null;
		if (theform.getFaultcompany_id() != null && !theform.getFaultcompany_id().equals("")) {
			// If the user has limited permission, 
			if (UserPermissions.hasLimitedSavePermission(user, theform.getIncident_ID())) {
				faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
				faultCompanyList = new ArrayList();
				faultCompanyList.add(user.getStation().getCompany());
			} else {
				faultstationlist = TracerUtils.getStationList(user.getCurrentlocale(), theform.getFaultcompany_id());
				faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
			}
			request.setAttribute("faultstationlist", faultstationlist);
			request.setAttribute("faultCompanyList", faultCompanyList);
		} 
		
		// change faultstationlist (ajax call)
		if (request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {

			if (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals("")) {
				theform.setFaultstation_id(0);
				return true;
			}

			return true;
		}

		if (request.getParameter("close") != null && (request.getParameter("doclose") != null || request.getParameter("doclosewt") != null)) {
			ActionMessage error = null;
			if (theform.getFaultstation_id() == 0 || Integer.toString(theform.getFaultstation_id()) == "") {
				error = new ActionMessage("error.choose_faultcompany");
				if (error != null) {
					if (error.getKey().length() > 0) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
				}

				return true;
			}
			if (theform.getLoss_code() == 0) {
				error = new ActionMessage("error.choose_lossreason");
				if (error != null) {
					if (error.getKey().length() > 0) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
				}
				return true;
			}
			theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_CLOSED);

			return false;
		}

		// regular save, don't change status
		if (request.getParameter("close") != null && request.getParameter("save") != null) {
			return false;
		}

		if (theform.getStatus() != null) {
			request.setAttribute("currentstatus", Integer.toString(theform.getStatus().getStatus_ID()));
		}

		String comp = theform.getFaultcompany_id();
		if ((comp == null || comp.length() == 0) && theform.getStationassigned().getCompany() != null) {
			comp = theform.getStationassigned().getCompany().getCompanyCode_ID();
		}
		
		if (faultstationlist == null) {
			faultstationlist = TracerUtils.getStationList(user.getCurrentlocale(), comp);
			request.setAttribute("faultstationlist", faultstationlist);
		}

		// add closing remark

		if (request.getParameter("addcloseremark") != null) {
			//set new remark with current time
			Remark r = theform.getClosingRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			return true;
		}
		// delete closing remark
		boolean deleteremark = false;
		String index = "0";
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
				if (parameter.indexOf("deleteCloseRemark") != -1) {
					deleteremark = true;
					break;
				}
			}
		}
		if (deleteremark) {
			List remarkList = theform.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			return true;
		}

		// close report
		if (request.getParameter("close") != null) {
			if (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals("")) {
				// use current company as fault company and get default_station_code
				// from company_specific_variables
				theform.setFaultcompany_id(theform.getStationassigned().getCompany().getCompanyCode_ID());
			}

			if (theform.getFaultstation_id() <= 0) {
				Company_Specific_Variable csv = AdminUtils.getCompVariable(theform.getFaultcompany_id());
				if (csv.getDefault_station_code() == 0)
					theform.setFaultstation(theform.getStationassigned());
				else {
					Station st = StationBMO.getStation(Integer.toString(csv.getDefault_station_code()));
					theform.setFaultstation(st);
				}
			}

			boolean hasclose = false;
			for (int i = 0; i < theform.getRemarklist().size(); i++) {
				Remark r = theform.getClosingRemark(i);
				if (r.getRemarktype() == TracingConstants.REMARK_CLOSING) {
					hasclose = true;
					break;
				}
			}
			if (!hasclose) {
				// add closing remark if there isn't one
				Remark r = theform.getClosingRemark(theform.getRemarklist().size());
				r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
				r.setAgent(user);
				r.set_DATEFORMAT(user.getDateformat().getFormat());
				r.set_TIMEFORMAT(user.getTimeformat().getFormat());
				r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}

			return true;
		}
		return false;
	}

	public static boolean actionGetStation(IncidentForm theform, HttpServletRequest request, Agent user) throws Exception {
		if (request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {

			if (theform.getFaultcompany_id() == null)
				return true;

			List faultstationlist = TracerUtils.getStationList(user.getCurrentlocale(), theform.getFaultcompany_id());
			request.setAttribute("faultstationlist", faultstationlist);
			return true;
		}
		return false;
	}

	/**
	 * add associate report
	 * 
	 * @param theform
	 * @param request
	 * @param user
	 * @return @throws
	 *         Exception
	 */
	public static String actionAddAssoc(IncidentForm theform, HttpServletRequest request, Agent user) throws Exception {
		String loc = null;
		int type = 0;

		HttpSession session = request.getSession();

		if (request.getParameter("add_assoc_report") != null) {
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.LOST_DELAY) {
				loc = TracingConstants.LD_MAIN;
				type = TracingConstants.LOST_DELAY;
			}
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.MISSING_ARTICLES) {
				loc = TracingConstants.MISSING_MAIN;
				type = TracingConstants.MISSING_ARTICLES;
			}
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.DAMAGED_BAG) {
				loc = TracingConstants.DAMAGED_MAIN;
				type = TracingConstants.DAMAGED_BAG;
			}
			if (type == 0)
				return null;
			// initialize vars
			request.setAttribute("newform", "1");
			theform.setIncident_ID("");
			theform.setWtFile(null);
			theform.set_DATEFORMAT(user.getDateformat().getFormat());
			theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			theform.setCreatedate(TracerDateTime.getGMTDate());
			theform.setCreatetime(TracerDateTime.getGMTDate());
			// set station
			theform.setStationcreated_ID(user.getStation().getStation_ID());
			theform.setStationcreated(user.getStation());
			theform.setStationassigned_ID(user.getStation().getStation_ID());
			//theform.setFaultstation(new Station());
			// set agent
			theform.setAgent(user);
			// set status as temp to start off
			Status status = new Status();
			status.setStatus_ID(TracingConstants.MBR_STATUS_TEMP);
			theform.setStatus(status);
			// set report method
			theform.setReportmethod(user.getStation().getCompany().getVariable().getReport_method());

			// clear pass ids for new insert
			ArrayList al = new ArrayList();
			ArrayList al2 = null;
			if (theform.getPassengerlist() != null) {
				Passenger pa = null;
				Passenger pa2 = null;
				Address ad = null;
				Address ad2 = null;
				for (int i = 0; i < theform.getPassengerlist().size(); i++) {
					pa = (Passenger) theform.getPassengerlist().get(i);
					pa.setPassenger_ID(0);
					
					pa2 = new Passenger();
					BeanUtils.copyProperties(pa2, pa);
					
					if (pa2.getMembership() != null) pa2.getMembership().setMembership_ID(0);
					else {
						pa2.setMembership(new AirlineMembership());
					}
					
					// address
					al2 = new ArrayList();
					for (int j = 0; j < pa.getAddresses().size(); j++) {
						ad = (Address) pa.getAddress(j);
						ad2 = new Address();
						BeanUtils.copyProperties(ad2, ad);
						ad2.setAddress_ID(0);
						ad2.setPassenger(pa2);
						al2.add(ad2);
					}
					pa2.setAddresses(new HashSet(al2));

					al.add(pa2);
				}
			}
			theform.setPassengerlist(al);

			// clear itinerary ids for new insert
			al = new ArrayList();
			if (theform.getItinerarylist() != null) {
				Itinerary iti = null;
				Itinerary iti2 = null;
				for (int i = 0; i < theform.getItinerarylist().size(); i++) {
					iti = (Itinerary) theform.getItinerarylist().get(i);
					iti.setItinerary_ID(0);
					iti2 = new Itinerary();
					BeanUtils.copyProperties(iti2, iti);
					al.add(iti2);
				}
			}
			theform.setItinerarylist(al);

			// clear bag ids
			al = new ArrayList();
			if (theform.getItemlist() != null) {
				Item item = null;
				Item item2 = null;
				Item_Inventory ii = null;
				Item_Inventory ii2 = null;
				for (int i = 0; i < theform.getItemlist().size(); i++) {
					item = (Item) theform.getItemlist().get(i);
					item.setItemtype_ID(type);
					item.setItem_ID(0);
					item.setIncident(null);
					item.set_DATEFORMAT(user.getDateformat().getFormat());
					item.setCurrency_ID(user.getDefaultcurrency());
					item.setOHD_ID("");
					item.setPhotolist(new ArrayList());
					item2 = new Item();
					BeanUtils.copyProperties(item2, item);

					// clear inventory
					al2 = new ArrayList();
					for (int j = 0; j < item.getInventorylist().size(); j++) {
						ii = (Item_Inventory) item.getInventorylist().get(j);
						ii2 = new Item_Inventory();
						BeanUtils.copyProperties(ii2, ii);
						ii2.setInventory_ID(0);
						ii2.setItem(item2);
						al2.add(ii2);
					}
					item2.setInventory(new HashSet(al2));

					al.add(item2);

				}
			}
			theform.setItemlist(al);

			// clear remarks id
			al = new ArrayList();
			if (theform.getRemarklist() != null) {
				Remark remark = null;
				Remark remark2 = null;
				for (int i = 0; i < theform.getRemarklist().size(); i++) {
					remark = (Remark) theform.getRemarklist().get(i);
					remark.setRemark_ID(0);
					remark.set_DATEFORMAT(user.getDateformat().getFormat());
					remark.set_TIMEFORMAT(user.getTimeformat().getFormat());
					remark.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					remark2 = new Remark();
					BeanUtils.copyProperties(remark2, remark);
					al.add(remark2);
				}
			}
			theform.setRemarklist(al);

			// clear claim information
			theform.setClaims(null);

			// create new article
			theform.setArticlelist(new ArrayList());
			if (type == TracingConstants.MISSING_ARTICLES) {
				Articles a = theform.getArticle(0);
				a.set_DATEFORMAT(user.getDateformat().getFormat());
				a.setCurrency_ID(user.getDefaultcurrency());
			}

			// set new claimcheck
			al = new ArrayList();
			al.add(new Incident_Claimcheck());
			theform.setClaimchecklist(al);

		}
		return loc;
	}

	/**
	 * MATCH
	 * 
	 * @param theform
	 * @param request
	 * @param user
	 * @param realpath
	 * @return @throws
	 *         Exception
	 */
	public static ActionMessage actionMatching(IncidentForm theform, HttpServletRequest request, Agent user, String realpath) throws Exception {

		BagService bs = new BagService();
		Incident_Claimcheck ic = null;
		Item item = null;
		OhdBMO oBMO = new OhdBMO();

		OHD ohd_obj = null;

		ActionMessage error = null;

		// match claim check is clicked
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			if (request.getParameter("matchclaim" + i) != null) {
				// unmatch one claim
				ic = (Incident_Claimcheck) theform.getClaimcheck(i);
				String ohd = ic.getTempOHD_ID();
				if (ohd != null)
					ohd = StringUtils.fillzero(TracerUtils.removeSpaces(ohd.toUpperCase()));
				ohd_obj = oBMO.findOHDByID(ohd);
				if (ohd_obj == null) {
					// not a valid on-hand id
					ic.setOHD_ID("");
					error = new ActionMessage("error.match_noonhand");
					//request.setAttribute("claimcheck", "1");
					return error;
				}
				ic.setOHD_ID(ohd); // new uppercased and spaced removed ohd_id

				if ((error = bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, realpath, user)) == null) {
					// update ohd status to be delivered if it is in this station
					if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED, user.getDefaultlocale().toString()));
						oBMO.insertOHD(ohd_obj, theform.getAgent());
					}
				} else {
					return error;
				}
				request.setAttribute("claimcheck", "1");
				return new ActionMessage("");
			}
		}

		// match bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			if (request.getParameter("matchbag" + i) != null) {
				// unmatch one bag
				item = (Item) theform.getItem(i, 0);
				String ohd = item.getTempOHD_ID();
				if (ohd != null)
					ohd = TracerUtils.removeSpaces(ohd.toUpperCase());
				ohd_obj = oBMO.findOHDByID(ohd);
				if (ohd_obj == null) {
					// not a valid on-hand id
					item.setOHD_ID("");
					error = new ActionMessage("error.match_noonhand");
					//request.setAttribute("item", Integer.toString(i));
					return error;
				} else {
					if (ohd_obj.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_OPEN) {
						// ohd is not open
						item.setOHD_ID("");
						error = new ActionMessage("error.match_noonhand");
						//request.setAttribute("item", Integer.toString(i));
						return error;
					}
				}
				item.setOHD_ID(ohd); // new uppercased and spaced removed ohd_id

				if (item.getClaimchecknum() == null || item.getClaimchecknum().length() <= 0) {
					// no claimcheck, most likely manually matched
					// move ohd information over
					if (ohd_obj.getClaimnum() != null) {
						item.setClaimchecknum(ohd_obj.getClaimnum());
						// find claimnum in claimcheck list and match that one as well
						for (int j = 0; j < theform.getClaimchecklist().size(); j++) {
							ic = (Incident_Claimcheck) theform.getClaimchecklist().get(j);
							if (ic.getClaimchecknum() != null && ic.getClaimchecknum().equals(ohd_obj.getClaimnum())) {
								ic.setOHD_ID(item.getOHD_ID());
							}
						}
					}

				}

				// change both item and ohd to tobedelivered because they are in the
				// same station
				if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED, user.getDefaultlocale().toString()));
				} else {
					// change item to matched only
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_MATCHED, user.getDefaultlocale().toString()));
				}

				if ((error = bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, realpath, user)) == null) {
					// update ohd status to be delivered if it is in this station
					if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED, user.getDefaultlocale().toString()));
						oBMO.insertOHD(ohd_obj, theform.getAgent());
					}
				} else {
					return error;
				}

				oBMO.insertOHD(ohd_obj, theform.getAgent());

				request.setAttribute("item", Integer.toString(i));
				return new ActionMessage("");
			}
		}
		return null;
	}

	public static boolean actionUnMatching(IncidentForm theform, HttpServletRequest request, Agent user, String realpath) throws Exception {
		// unmatch claim is clicked
		BagService bs = new BagService();
		Incident_Claimcheck ic = null;
		Item item = null;
		OHD ohd_obj = null;
		OhdBMO oBMO = new OhdBMO();
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			if (request.getParameter("unmatchclaim" + i) != null) {
				// unmatch one claim
				ic = (Incident_Claimcheck) theform.getClaimcheck(i);
				String ohd = ic.getOHD_ID();
				ohd_obj = oBMO.findOHDByID(ohd);
				// find the bag associated with this
				for (int j = 0; j < theform.getItemlist().size(); j++) {
					item = (Item) theform.getItem(j, 0);
					if (item.getOHD_ID() != null && item.getOHD_ID().equals(ohd)) {
						// see if bdo is created first
						BDO bdo = item.getBdo();
						if (bdo != null) {
							// if bdo created, then set bdo to ohd instead
							bdo.setIncident(null);
							item.setBdo(null);
							BDOUtils.insertBDOtoDB(bdo, user);
						}
						
						// clear bag ohd and claimcheck
						item.setOHD_ID("");
						item.setClaimchecknum("");

						// if bag is at the report station, change both status to open
						// again

						if (ohd_obj != null & ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
							item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN, user.getDefaultlocale().toString()));
							if (bdo == null) {
								// if no bdo, then change ohd status, otherwise leave it as is
								ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN, user.getDefaultlocale().toString()));
							}
							oBMO.insertOHD(ohd_obj, theform.getAgent());
						} else {
							// change item to open only
							item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN, user.getDefaultlocale().toString()));
						}

						break;
					}
				}
				//empty out claim ohd
				ic.setOHD_ID("");
				// call unmatch to clear out match history
				MatchUtils.unmatchOHD(ohd);

				bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, realpath, user);
				request.setAttribute("claimcheck", "1");
				return true;
			}
		}

		// unmatch bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			if (request.getParameter("unmatchbag" + i) != null) {
				// unmatch one bag
				item = (Item) theform.getItem(i, 0);
				String ohd = item.getOHD_ID();
				ohd_obj = oBMO.findOHDByID(ohd);
				// find the claimcheck associated with this
				for (int j = 0; j < theform.getClaimchecklist().size(); j++) {
					ic = (Incident_Claimcheck) theform.getClaimcheck(j);
					if (ic.getOHD_ID() != null && ic.getOHD_ID().equals(ohd)) {
						// clear claim ohd
						ic.setOHD_ID("");
						break;
					}
				}
				
				// see if bdo is created first
				BDO bdo = item.getBdo();
				if (bdo != null) {
					// if bdo created, then set bdo to ohd instead
					bdo.setIncident(null);
					item.setBdo(null);
					BDOUtils.insertBDOtoDB(bdo, user);
				}
				
				//empty out bag ohd and claimcheck
				item.setOHD_ID("");
				item.setClaimchecknum("");

				// if bag is at the report station, change both status to open
				// again
				if (ohd_obj != null & ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN, user.getDefaultlocale().toString()));
					if (bdo == null) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN, user.getDefaultlocale().toString()));
					}
					oBMO.insertOHD(ohd_obj, theform.getAgent());
				} else {
					// change item to open only
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN, user.getDefaultlocale().toString()));
				}

				// call unmatch to clear out match history
				MatchUtils.unmatchOHD(ohd);

				bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, realpath, user);

				request.setAttribute("item", Integer.toString(i));
				return true;
			}
		}

		return false;
	}

	
	public static ActionMessage checkOHDEntered(IncidentForm theform, HttpServletRequest request, Agent user, String realpath) throws Exception {

		BagService bs = new BagService();
		Incident_Claimcheck ic = null;
		Item item = null;
		OhdBMO oBMO = new OhdBMO();

		OHD ohd_obj = null;

		ActionMessage error = null;

	
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			ic = (Incident_Claimcheck) theform.getClaimcheck(i);
			String ohd = ic.getTempOHD_ID();
			if (ohd != null && ohd.length() > 0 && (ic.getOHD_ID() == null || ic.getOHD_ID().length() == 0)) {
				error = new ActionMessage("error.match_needtoconfirm");
				return error;
			}
		}

		// match bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			item = (Item) theform.getItem(i, 0);
			String ohd = item.getTempOHD_ID();
			if (ohd != null && ohd.length() > 0 && (ic.getOHD_ID() == null || ic.getOHD_ID().length() == 0)) {
				error = new ActionMessage("error.match_needtoconfirm");
				return error;
			}
		}
		return null;
	}
	
	
	public static boolean actionChangeAssignedStation(IncidentForm theform, HttpServletRequest request) {

		// change up service level
		if (request.getParameter("changeassignedstation") != null && request.getParameter("changeassignedstation").equals("1")) {
			if (theform.getStationassigned_ID() > 0) {
				List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
				// AJAX CALL
				request.setAttribute("agentassignedlist", agentassignedlist);
				return true;
			}
		}
		return false;
	}

	public static boolean prePopulate(HttpServletRequest request,IncidentForm form, ArrayList<String> alerrors, int incidentType) {
		if (request.getParameter("doprepopulate") != null) {
			if (request.getParameter("wt_af_id") == null) {
				alerrors.addAll(SpringUtils.getReservationIntegration().populateIncidentForm(request, form, incidentType));
				return true;
			}
		}
		return false;
	}
}