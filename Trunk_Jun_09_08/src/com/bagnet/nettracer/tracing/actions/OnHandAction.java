package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for
 * adding/viewing/modifying on-hand reports.
 * 
 * @author Ankur Gupta
 */
public class OnHandAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
													// validity
		boolean rohd = false;
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if(user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_OH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();
		BagService bs = new BagService();
		OnHandForm theform = (OnHandForm) form;

		// Status pertaining to the on hand file
		List oStatusList = OHDUtils.getOhdStatusList(user.getCurrentlocale());
		request.setAttribute("oStatusList", oStatusList);
		request.setAttribute("onhand", "1");

		if(request.getParameter("historical_report") != null && request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.OHD_HISTORICAL));
		}
		else if(request.getParameter("viewhistoryreport") != null
				&& request.getParameter("viewhistoryreport").length() > 0) {
			String reportfile = getReportFile(theform, user, request);

			if(reportfile == null || reportfile.equals("")) {
				ActionMessage error = new ActionMessage("message.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			else {
				request.setAttribute("reportfile", reportfile);
			}

			return mapping.findForward(TracingConstants.OHD_HISTORICAL);
		}
		// add new remark box -- set new remark with current gmt time
		else if(request.getParameter("addremark") != null) {
			Remark r = theform.getRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("remark", "1");
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add new itinerary box
		else if(request.getParameter("additinerary") != null) {
			theform.getItinerary(theform.getItinerarylist().size());
			request.setAttribute("itinerary", "1");
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add a new baggage item
		else if(request.getParameter("additem") != null) {
			theform.getItem(theform.getItemlist().size());
			request.setAttribute("item", "1");
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add a new passenger
		else if(request.getParameter("addPassenger") != null) {
			request.setAttribute("passenger", "1");
			theform.getPassenger(theform.getPassengerList().size());
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// upload a photo
		else if(request.getParameter("uploadPhoto") != null) {
			request.setAttribute("upload", "1");

			// Save the file in the local directory.
			Hashtable files = theform.getMultipartRequestHandler().getFileElements();
			FormFile theFile = (FormFile) files.get("theFile1");
			if(theFile != null && theFile.getFileSize() > 0) {
				String st = Long.toString((new Date()).getTime());
				String lead = "";
				if(theform.getOhd_id() != null && theform.getOhd_id().length() > 0)
					lead = theform.getOhd_id();

				// now make subfolder with year and month
				Calendar cal = new GregorianCalendar();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);

				// compute the folder name
				String folder = user.getStation().getCompany().getCompanyCode_ID() + "/" + year + "/" + month + "/"
						+ day + "/";
				;

				// paths to be stored in the db
				String fileName = theFile.getFileName();
				String picpath = folder + lead + "_" + st + "_" + fileName;
				String thumbpath = folder + lead + "_" + st + "_thumb_" + fileName;

				boolean uploadresult = ImageUtils.doUpload(theFile, user, folder, picpath, thumbpath);
				if(!uploadresult) {
					ActionMessage error = new ActionMessage("error.uploadfile");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.OHD_MAIN));
				}
				else {
					// add the image to the DB.
					OHD_Photo photo = new OHD_Photo();
					photo.setPicpath(picpath);
					photo.setThumbpath(thumbpath);
					theform.getPhotoList().add(photo);
				}
			}
			else {
				// upload file errors.
				ActionMessage error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}
		// save temporary.
		else if((request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))
				|| request.getParameter("savetracing") != null || request.getParameter("savetowt") != null
				|| request.getParameter("closetowt") != null) {
			OHD oDTO = null;
			if(theform.getOhd_id() != null)
				oDTO = bs.findOHDByID(theform.getOhd_id());
			if(oDTO == null) {
				oDTO = new OHD();
				oDTO.setAgent(user);
			}
			Status s = new Status();

			// Do a temporary saving..Matching is not initiated.
			if(request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))
				s.setStatus_ID(TracingConstants.OHD_STATUS_TEMP);
			else {

				// Can only change the status to OPEN if the file status is null
				if(theform.getStatus() == null)
					s.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
				else
					s.setStatus_ID(theform.getStatus().getStatus_ID());
			}
			oDTO.setStatus(s);
			ArrayList list = new ArrayList();
			// Mass on hand entry by adding multiple bag tag numbers.
			if(request.getParameter("mass") != null) {
				list.add("mass");
			}
			if(request.getParameter("savetowt") != null && oDTO.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_OPEN) {
				rohd = false;
			}
			else if((request.getParameter("savetracing") != null || request.getParameter("savetowt") != null
					|| request.getParameter("closetowt") != null)) {
				rohd = bs.insertOnHand(oDTO, theform, list, user);	
				if(request.getParameter("savetowt") != null) {
					WtqOhdAction wtq = new WtqCreateOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
				else if (request.getParameter("closetowt") != null) {
					WtqOhdAction wtq = new WtqCloseOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
			}
			if(rohd) {

				request.setAttribute("ohd_ID", oDTO.getOHD_ID());
				if(request.getParameter("mass") != null) {
					request.setAttribute("ohdidlist", list);
					return mapping.findForward(TracingConstants.MASS_ON_HAND_SUCCESS);
				}
				else
					return (mapping.findForward(TracingConstants.INSERT_ON_HAND_SUCCESS));
			}
			else {
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}
		}
		else {
			boolean foundAddress = false;
			boolean deletePassenger = false;
			boolean deleteAddress = false;
			boolean deleteBagItin = false;
			boolean deleteItem = false;
			boolean deletePhoto = false;
			boolean deleteRemark = false;

			// This technique is employed to get the []['nd] reference
			String index = "0";
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if(parameter.indexOf("[") != -1) {
					index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));

					// Add a new address within a passenger
					if(parameter.indexOf("addAddress") != -1) {
						foundAddress = true;
						break;
					}// delete passenger is clicked
					else if(parameter.indexOf("deletePassenger") != -1) {
						deletePassenger = true;
						break;
					}// delete address is clicked
					else if(parameter.indexOf("deleteAddress") != -1) {
						deleteAddress = true;
						break;
					}// delete bag is clicked
					else if(parameter.indexOf("deleteBag") != -1) {
						deleteBagItin = true;
						break;
					}// delete bag item is clicked.
					else if(parameter.indexOf("deleteItem") != -1) {
						deleteItem = true;
						break;
					}// delete photo is clicked
					else if(parameter.indexOf("deletePhoto") != -1) {
						deletePhoto = true;
						break;
					}// delete remark is clicked.
					else if(parameter.indexOf("deleteRemark") != -1) {
						deleteRemark = true;
						break;
					}
				}
			}

			// add address
			if(foundAddress) {
				OHD_Address address = new OHD_Address();
				OHD_Passenger passenger = theform.getPassenger(Integer.parseInt(index));
				passenger.addAddress(address);
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete passenger
			else if(deletePassenger) {
				request.setAttribute("passenger", "1");
				List passengerList = theform.getPassengerList();
				if(passengerList != null) {
					passengerList.remove(Integer.parseInt(index));
				}
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete address
			else if(deleteAddress) {
				request.setAttribute("passenger", "1");
				theform.removeAddress(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete bag
			else if(deleteBagItin) {
				request.setAttribute("itinerary", "1");
				List itnList = theform.getItinerarylist();
				if(itnList != null)
					itnList.remove(Integer.parseInt(index));
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete bag-item
			else if(deleteItem) {
				request.setAttribute("item", "1");
				List itemList = theform.getItemlist();
				if(itemList != null)
					itemList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete photo
			else if(deletePhoto) {
				request.setAttribute("upload", "1");
				List photoList = theform.getPhotoList();
				if(photoList != null)
					photoList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete remark
			else if(deleteRemark) {
				request.setAttribute("remark", "1");
				List remarkList = theform.getRemarklist();
				if(remarkList != null)
					remarkList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}
			else {
				String onhand_id = request.getParameter("ohd_ID");
				OnHandForm addform = (OnHandForm) form;
				if(onhand_id != null && onhand_id.length() > 0) {
					// if OHD_ID exists; load exisiting on-hand
					addform.setAgent(user);
					if(!bs.findOnHand(onhand_id, addform, user)) {
						ActionMessage error = new ActionMessage("error.no.onhandreport");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
					}
				}
				else {
					// Create a new on-hand entry
					TracerUtils.populateOnHand(addform, request);

				}

				if(request.getParameter("express") != null) {
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				}
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG, user)) {
					if(user.getStation().getCompany().getCompanyCode_ID().equals(addform.getHolding_company())
							&& user.getStation().getStationcode().equals(addform.getHolding_station())) {
						addform.setReadonly(0);
						return mapping.findForward(TracingConstants.OHD_MAIN);
					}
					else {
						addform.setReadonly(1);
					}
				}
				else {
					addform.setReadonly(1);
				}

				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_OH, user))
					theform.setAllow_remark_update(1);

				return mapping.findForward(TracingConstants.OHD_MAIN);

			}
		}
	}

	private String getReportFile(OnHandForm theform, Agent user, HttpServletRequest request) {

		// need the historical report for this report.
		String ohd_id = theform.getOhd_id();

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		HashMap selections = new HashMap();

		if(request.getParameter("all") != null) {
			request.setAttribute("all", "1");
			selections.put("passenger", "true");
			selections.put("itinerary", "true");
			selections.put("inventory", "true");
			selections.put("remarks", "true");
			selections.put("photos", "true");
			selections.put("messages", "true");
			selections.put("tasks", "true");
			selections.put("forward", "true");
			selections.put("request", "true");
			selections.put("matches", "true");

		}
		else {
			request.setAttribute("all", "0");
			if(request.getParameter("passenger") != null) {
				request.setAttribute("passenger", "1");
				selections.put("passenger", "true");
			}
			else {
				selections.put("passenger", null);
			}

			if(request.getParameter("itinerary") != null) {
				request.setAttribute("itinerary", "1");
				selections.put("itinerary", "true");
			}
			else
				selections.put("itinerary", null);

			if(request.getParameter("inventory") != null) {
				request.setAttribute("inventory", "1");
				selections.put("inventory", "true");
			}
			else
				selections.put("inventory", null);

			if(request.getParameter("remarks") != null) {
				request.setAttribute("remarks", "1");
				selections.put("remarks", "true");
			}
			else
				selections.put("remarks", null);

			if(request.getParameter("photos") != null) {
				request.setAttribute("photos", "1");
				selections.put("photos", "true");
			}
			else
				selections.put("photos", null);

			if(request.getParameter("messages") != null) {
				request.setAttribute("messages", "1");
				selections.put("messages", "true");
			}
			else
				selections.put("messages", null);

			if(request.getParameter("tasks") != null) {
				request.setAttribute("tasks", "1");
				selections.put("tasks", "true");
			}
			else
				selections.put("tasks", null);

			if(request.getParameter("forward") != null) {
				request.setAttribute("forward", "1");
				selections.put("forward", "true");
			}
			else
				selections.put("forward", null);

			if(request.getParameter("request") != null) {
				request.setAttribute("request", "1");
				selections.put("request", "true");
			}
			else
				selections.put("request", null);

			if(request.getParameter("matches") != null) {
				request.setAttribute("matches", "1");
				selections.put("matches", "true");
			}
			else
				selections.put("matches", null);
		}
		int type = Integer.parseInt(request.getParameter("outputtype"));

		request.setAttribute("outputtype", "" + type);

		String reportfile = createReport(type, sc, theform.getOhd_id(), user, selections, request);

		return reportfile;
	}

	private static String createReport(int type, ServletContext sc, String ohd_id, Agent user, HashMap selections,
			HttpServletRequest request) {

		OnHandForm form = new OnHandForm();
		BagService bs = new BagService();
		try {
			bs.findOnHand(ohd_id, form, user);
		}
		catch (Exception e) {
		}

		try {
			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("airline", format("" + form.getAgent().getCompanycode_ID()));
			parameters.put("createdate", format("" + form.getDispFoundTime()));
			parameters.put("file_reference", format("" + form.getOhd_id()));
			parameters.put("holding_company", format("" + form.getHolding_company()));
			parameters.put("found_at_station", format("" + form.getFound_station()));
			parameters.put("status", format("" + form.getStatus().getDescription()));
			if(form.getXDesc1() > 0) {
				parameters.put("ede1", format("" + TracerUtils.getXdescelement(form.getXDesc1()).getDescription()));
			}
			else {
				parameters.put("ede1", "");
			}
			if(form.getXDesc2() > 0) {
				parameters.put("ede2", format("" + TracerUtils.getXdescelement(form.getXDesc2()).getDescription()));
			}
			else {
				parameters.put("ede2", "");
			}
			if(form.getXDesc3() > 0) {
				parameters.put("ede3", format("" + TracerUtils.getXdescelement(form.getXDesc3()).getDescription()));
			}
			else {
				parameters.put("ede3", "");
			}
			parameters.put("bag_arrive_date", format("" + form.getDispBagArriveDate()));
			parameters.put("found_at_company", format("" + form.getFound_company()));
			parameters.put("airline_membership", format("" + form.getCompanycode_ID()));
			parameters.put("membership_num", format("" + form.getMembershipnum()));
			parameters.put("membershipstatus", format("" + form.getMembershipstatus()));
			parameters.put("airline_membership", format("" + form.getMembershipnum()));
			parameters.put("color", format("" + form.getBagColor()));
			if(form.getManufacturer_ID() > 0) {
				parameters.put("manufacturer", format(""
						+ TracerUtils.getManufacturer(form.getManufacturer_ID()).getDescription()));
			}
			else {
				parameters.put("manufacturer", "");
			}
			parameters.put("record_locator", format("" + form.getPnr()));
			parameters.put("membership_num", format("" + form.getMembershipnum()));
			parameters.put("type", format("" + form.getBagType()));
			parameters.put("storage_location", format("" + form.getStorage_location()));
			parameters.put("date_closed", format("" + form.getDispCloseDate()));
			parameters.put("agent", format("" + form.getAgent_initials()));
			parameters.put("bag_tag_number", format("" + form.getBagTagNumber()));
			parameters.put("holding_station", format("" + form.getHolding_station()));

			if(selections.get("passenger") != null) {
				List passengers = form.getPassengerList();
				if(passengers != null && passengers.size() > 0) {
					parameters
							.put("passengerReport", ReportBMO.getCompiledReport("ohd_passenger", sc.getRealPath("/")));
					parameters.put("addressReport", ReportBMO.getCompiledReport("ohd_address", sc.getRealPath("/")));
					parameters.put("passenger", new JRBeanCollectionDataSource(passengers));
				}
				else
					parameters.put("passenger", null);
			}
			else
				parameters.put("passenger", null);

			if(selections.get("itinerary") != null) {
				List itineraries = form.getItinerarylist();
				if(itineraries != null && itineraries.size() > 0) {
					parameters
							.put("itineraryReport", ReportBMO.getCompiledReport("ohd_itinerary", sc.getRealPath("/")));
					parameters.put("itinerary", new JRBeanCollectionDataSource(itineraries));
				}
				else
					parameters.put("itinerary", null);
			}
			else
				parameters.put("itinerary", null);

			if(selections.get("inventory") != null) {
				List inventories = form.getItemlist();
				if(inventories != null && inventories.size() > 0) {
					parameters
							.put("inventoryReport", ReportBMO.getCompiledReport("ohd_inventory", sc.getRealPath("/")));
					parameters.put("inventory", new JRBeanCollectionDataSource(inventories));
				}
				else {
					parameters.put("inventory", null);
				}
			}
			else
				parameters.put("inventory", null);

			if(selections.get("remarks") != null) {
				List remarks = form.getRemarklist();
				if(remarks != null && remarks.size() > 0) {
					parameters.put("remarkReport", ReportBMO.getCompiledReport("remarks", sc.getRealPath("/")));
					parameters.put("remarks", new JRBeanCollectionDataSource(remarks));
				}
				else
					parameters.put("remarks", null);

			}
			else
				parameters.put("remarks", null);

			if(selections.get("photos") != null) {
				List photos = form.getPhotoList();
				if(photos != null && photos.size() > 0) {
					for(Iterator i = photos.iterator(); i.hasNext();) {
						OHD_Photo photo = (OHD_Photo) i.next();
						photo.setThumbpath(TracerProperties.get("image_store")
								+ photo.getThumbpath());
					}
					parameters.put("photoReport", ReportBMO.getCompiledReport("ohd_photos", sc.getRealPath("/")));
					parameters.put("photos", new JRBeanCollectionDataSource(photos));
				}
				else
					parameters.put("photos", null);
			}
			else
				parameters.put("photos", null);

			if(selections.get("messages") != null) {
				List messages = MessageUtils.getReportMessages(ohd_id, 0);
				if(messages != null && messages.size() > 0) {
					for(Iterator i = messages.iterator(); i.hasNext();) {
						Message msg = (Message) i.next();
						msg.set_DATEFORMAT(user.getDateformat().getFormat());
						msg.set_TIMEFORMAT(user.getTimeformat().getFormat());
						msg.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					parameters.put("messageReport", ReportBMO.getCompiledReport("messages", sc.getRealPath("/")));
					parameters.put("messages", new JRBeanCollectionDataSource(messages));
				}
				else {
					parameters.put("messages", null);
				}
			}
			else {
				parameters.put("messages", null);
			}

			if(selections.get("tasks") != null) {
				List tasks = TaskUtils.getFileTasks(ohd_id, 0);
				if(tasks != null && tasks.size() > 0) {
					for(Iterator i = tasks.iterator(); i.hasNext();) {
						Task task = (Task) i.next();
						task.set_DATEFORMAT(user.getDateformat().getFormat());
						task.set_TIMEFORMAT(user.getTimeformat().getFormat());
						task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					parameters.put("taskReport", ReportBMO.getCompiledReport("tasks", sc.getRealPath("/")));
					parameters.put("tasks", new JRBeanCollectionDataSource(tasks));
				}
				else {
					parameters.put("tasks", null);
				}
			}
			else {
				parameters.put("tasks", null);
			}

			if(selections.get("forward") != null) {
				List forwardLog = OHDUtils.getForwardMessages(form.getOhd_id());
				if(forwardLog != null && forwardLog.size() > 0) {
					for(Iterator i = forwardLog.iterator(); i.hasNext();) {
						OHD_Log log = (OHD_Log) i.next();
						log.set_DATEFORMAT(user.getDateformat().getFormat());
						log.set_TIMEFORMAT(user.getTimeformat().getFormat());
						log.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));

						Set e = log.getItinerary();

						if(e != null && e.size() > 0) {
							for(Iterator iter = e.iterator(); iter.hasNext();) {
								OHD_Log_Itinerary itinerary = (OHD_Log_Itinerary) iter.next();
								itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
								itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
							}
						}
					}
					parameters.put("forwardItineraryReport", ReportBMO.getCompiledReport("forward_itinerary", sc
							.getRealPath("/")));
					parameters.put("forwardReport", ReportBMO.getCompiledReport("ohd_forward", sc.getRealPath("/")));
					parameters.put("forward", new JRBeanCollectionDataSource(forwardLog));
				}
				else {
					parameters.put("forward", null);
				}
			}
			else
				parameters.put("forward", null);

			if(selections.get("request") != null) {
				List requestList = OHDUtils.getOHDRequests(form.getOhd_id());
				if(requestList != null && requestList.size() > 0) {
					for(Iterator iter = requestList.iterator(); iter.hasNext();) {
						OHDRequest req = (OHDRequest) iter.next();
						req.set_DATEFORMAT(user.getDateformat().getFormat());
						req.set_TIMEFORMAT(user.getTimeformat().getFormat());
						req.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					// request reports
					parameters.put("requestReport", ReportBMO.getCompiledReport("ohd_request", sc.getRealPath("/")));
					parameters.put("request", new JRBeanCollectionDataSource(requestList));
				}
				else {
					parameters.put("request", null);
				}
			}
			else {
				parameters.put("request", null);
			}

			if(selections.get("matches") != null) {
				List matches = MatchUtils.getMatchWithOHD(form.getOhd_id());
				if(matches != null && matches.size() > 0) {
					parameters.put("match_detail_report", ReportBMO.getCompiledReport("match_detail_report", sc
							.getRealPath("/")));
					parameters.put("matchReport", ReportBMO.getCompiledReport("match_report", sc.getRealPath("/")));
					parameters.put("matches", new JRBeanCollectionDataSource(matches));
				}
				else {
					parameters.put("matches", null);
				}
			}
			else
				parameters.put("matches", null);

			List t = new ArrayList();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "onhand_history", sc.getRealPath("/"), type);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String format(String input) {
		if(input == null || input.equals("null"))
			return "";
		else
			return input;
	}
}