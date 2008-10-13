/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

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
import java.util.StringTokenizer;
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

import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MBRActionUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DamagedAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");
		
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		ActionMessages errors = new ActionMessages();

		//the company specific codes..
		List codes = LossCodeBMO.getLocaleCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.DAMAGED_BAG, user
				.getCurrentlocale(), true, user);
		//add to the loss codes
		request.setAttribute("losscodes", codes);

		BagService bs = new BagService();
		IncidentForm theform = (IncidentForm) form;
		request.setAttribute("damaged", "1");

		if (request.getParameter("express") != null)
			request.setAttribute("express", "1");

		// readonly
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG, user))
			theform.setReadonly(1);

		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, user))
			theform.setAllow_remark_update(1);

		/** ****************** handle requests ******************** */

		if (request.getParameter("historical_report") != null && request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.DAMAGED_HISTORICAL));
		} else if (request.getParameter("viewhistoryreport") != null && request.getParameter("viewhistoryreport").length() > 0) {
			String reportfile = getReportFile(theform, user, request);

			if (reportfile == null || reportfile.equals("")) {
				ActionMessage error = new ActionMessage("message.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				request.setAttribute("reportfile", reportfile);
			}

			return mapping.findForward(TracingConstants.DAMAGED_HISTORICAL);
		}

		// ajax call to change assigned agent dropdown
		if (MBRActionUtils.actionChangeAssignedStation(theform, request)) {
			return (mapping.findForward(TracingConstants.AJAX_AGENTASSIGNED));
		}
		
		IncidentUtils.manageIncidentEmailing(user.getStation().getCompany(), request);

		List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
		request.setAttribute("agentassignedlist", agentassignedlist);
		if (theform.getIncident_ID() != null) request.setAttribute("incident",theform.getIncident_ID());
		
		if (MBRActionUtils.actionClose(theform, request, user, errors)) {
			saveMessages(request, errors);
				//		 AJAX CALL
			if (request.getParameter("close") != null && request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {
				return (mapping.findForward(TracingConstants.AJAX_FAULTSTATION));
			} else {
				return (mapping.findForward(TracingConstants.DAMAGED_CLOSE));
			}
			
		}

		if (MBRActionUtils.actionAdd(theform, request, user)) {
			return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
		}

		if (MBRActionUtils.actionDelete(theform, request)) {
			return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
		}

		String loc = null;
		if ((loc = MBRActionUtils.actionAddAssoc(theform, request, user)) != null) {
			return (mapping.findForward(loc));
		}

		int itemindex = -1, photoindex = -1;
		int fileindex = -1;
		StringTokenizer stt = null;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			try {
				if (parameter.indexOf("removePhoto") > -1) {
					stt = new StringTokenizer(parameter, "_");
					if (stt.hasMoreElements())
						stt.nextToken();
					if (stt.hasMoreElements())
						itemindex = Integer.parseInt(stt.nextToken());
					if (stt.hasMoreElements())
						photoindex = Integer.parseInt(stt.nextToken());
				}
			} catch (Exception removephotoe) {
				// tempering with data, should never happen
			}
			if (parameter.indexOf("uploadPhoto") > -1) {
				fileindex = Integer.parseInt(parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]")));
			}
		}

		if (itemindex >= 0 && photoindex >= 0) {
			// don't remove photo for now.
			Item_Photo thephoto = (Item_Photo) theform.getItem(itemindex, -1).getPhotolist().get(photoindex);
			theform.getItem(itemindex, -1).getPhotolist().remove(photoindex);
			//File pfile = new File(TracingConstants.IMAGE_STORE +
			// thephoto.getPicpath());
			//if (pfile.exists()) pfile.delete();
			//pfile = new File(TracingConstants.IMAGE_STORE +
			// thephoto.getThumbpath());
			//if (pfile.exists()) pfile.delete();
			request.setAttribute("upload", Integer.toString(itemindex));
			return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
		}

		if (fileindex >= 0) {
			// add photo
			request.setAttribute("upload", Integer.toString(fileindex));

			
			
			// Save the file in the local directory.
			Hashtable files = theform.getMultipartRequestHandler().getFileElements();
			FormFile theFile = (FormFile) files.get("imagefile" + fileindex);
			if (theFile != null && theFile.getFileSize() > 0) {
				String st = Long.toString((new Date()).getTime());
				String lead = "";
				if (theform.getIncident_ID() != null && theform.getIncident_ID().length() > 0)
					lead = theform.getIncident_ID();
				
				// now make subfolder with year and month
				Calendar cal = new GregorianCalendar();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				//compute the folder name
				String folder = user.getStation().getCompany().getCompanyCode_ID() + "/" + year + "/" + month + "/" + day + "/";;
				
				//paths to be stored in the db
				String fileName = theFile.getFileName();
				String picpath = folder + lead + "_" + st + "_" + fileName;
				String thumbpath = folder + lead + "_" + st + "_thumb_" + fileName;
				
				boolean uploadresult = ImageUtils.doUpload(theFile, user, folder, picpath, thumbpath);
				if (!uploadresult) {
					ActionMessage error = new ActionMessage("error.uploadfile");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
				} else {
					// add the image to the DB.
					Item_Photo photo = new Item_Photo();
					photo.setPicpath(picpath);
					photo.setThumbpath(thumbpath);
					photo.setItem(theform.getItem(fileindex, -1));
					ArrayList al = (ArrayList) theform.getItem(fileindex, -1).getPhotolist();
					al.add(photo);
				}
			} else {
				// upload file errors.
				ActionMessage error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
	

			return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
		}
		
		ServletContext sc = getServlet().getServletContext();
		String realpath = sc.getRealPath("/");
		
		// save incident
		if (request.getParameter("save") != null || request.getParameter("doclose") != null) {
			Incident iDTO = new Incident();
			if (theform.getIncident_ID() == null || theform.getIncident_ID().length() == 0)
				theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_OPEN);

			

			if (request.getParameter("email_customer") != null)
				theform.setEmail_customer(1);
			else
				theform.setEmail_customer(0);

			ActionMessage error = null;
			if (request.getParameter("close") != null && request.getParameter("close").equals("1")) {
				error = bs.insertIncident(iDTO, theform, TracingConstants.DAMAGED_BAG, realpath, user);
			} else {
				error = bs.insertIncident(iDTO, theform, TracingConstants.DAMAGED_BAG, realpath, user, true);
			}
			
			if (error == null) {
				request.setAttribute("damaged", "1");
				request.setAttribute("Incident_ID", iDTO.getIncident_ID());
				return (mapping.findForward(TracingConstants.INSERT_SUCCESS));
			} else if (error.getKey().equals("error.unable_to_close_incident")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.DAMAGED_CLOSE));
			}	else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
			}
		}
		/**
		 * *********** prepopulation for new list *********** or retrieve to modify
		 * incident on html rewrite link
		 */
		String incident = request.getParameter("incident_ID");
		if (incident != null && incident.length() > 0) {
			if (bs.findIncidentByID(incident, theform, user, TracingConstants.DAMAGED_BAG) == null) {
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			}
			request.setAttribute("incident", incident);
			

		} else {
			// prepopulate
			TracerUtils.populateIncident(theform, request, TracingConstants.DAMAGED_BAG);
			request.setAttribute("newform", "1");
			
			ActionMessage error = null;
			ArrayList alerrors = new ArrayList();
			if (MBRActionUtils.prePopulate(request,theform,alerrors,TracingConstants.DAMAGED_BAG)) {
				if (alerrors.size() > 0) {
					for (int i=0;i<alerrors.size();i++) {
						error = new ActionMessage((String)alerrors.get(i));
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
					saveMessages(request, errors);
					request.setAttribute("prepopulate",new Integer("1"));
				}
				return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
			}
			
			if (SpringUtils.getReservationIntegration().isPopulateIncidentFormOn() && request.getParameter("skip_prepopulate") == null && request.getParameter("express") == null) {
				request.setAttribute("prepopulate",new Integer("1"));
				return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
			}
		
		}

		return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
	}

	private String getReportFile(IncidentForm theform, Agent user, HttpServletRequest request) {

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		HashMap selections = new HashMap();

		if (request.getParameter("all") != null) {
			request.setAttribute("all", "1");
			selections.put("passenger", "true");
			selections.put("passenger_itinerary", "true");
			selections.put("baggage_itinerary", "true");
			selections.put("baggage_check", "true");
			selections.put("damaged_info", "true");
			selections.put("remarks", "true");
			selections.put("messages", "true");
			selections.put("tasks", "true");

		} else {
			request.setAttribute("all", "0");
			if (request.getParameter("passenger") != null) {
				request.setAttribute("passenger", "1");
				selections.put("passenger", "true");
			} else {
				selections.put("passenger", null);
			}

			if (request.getParameter("passenger_itinerary") != null) {
				request.setAttribute("passenger_itinerary", "1");
				selections.put("passenger_itinerary", "true");
			} else
				selections.put("passenger_itinerary", null);

			if (request.getParameter("baggage_itinerary") != null) {
				request.setAttribute("baggage_itinerary", "1");
				selections.put("baggage_itinerary", "true");
			} else
				selections.put("baggage_itinerary", null);

			if (request.getParameter("baggage_check") != null) {
				request.setAttribute("baggage_check", "1");
				selections.put("baggage_check", "true");
			} else
				selections.put("baggage_check", null);

			if (request.getParameter("damaged_info") != null) {
				request.setAttribute("damaged_info", "1");
				selections.put("damaged_info", "true");
			} else
				selections.put("damaged_info", null);

			if (request.getParameter("remarks") != null) {
				request.setAttribute("remarks", "1");
				selections.put("remarks", "true");
			} else
				selections.put("remarks", null);

			if (request.getParameter("messages") != null) {
				request.setAttribute("messages", "1");
				selections.put("messages", "true");
			} else
				selections.put("messages", null);

			if (request.getParameter("tasks") != null) {
				request.setAttribute("tasks", "1");
				selections.put("tasks", "true");
			} else
				selections.put("tasks", null);
		}

		int type = Integer.parseInt(request.getParameter("outputtype"));
		request.setAttribute("outputtype", "" + type);
		String reportfile = createReport(selections, type, sc, (String) request.getParameter("incident_ID"), user, request);
		return reportfile;

	}

	private static String createReport(HashMap selections, int type, ServletContext sc, String incident_ID, Agent user, HttpServletRequest request) {

		IncidentForm form = new IncidentForm();
		BagService bs = new BagService();
		try {
			bs.findIncidentByID(incident_ID, form, user, TracingConstants.MISSING_ARTICLES);
		} catch (Exception e) {
		}

		try {
			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user
					.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			HashMap report_info = new HashMap();
			report_info.put("incident_ID", form.getIncident_ID());
			report_info.put("dispcreatetime", form.getDispcreatetime());
			report_info.put("agentinit", form.getAgentinit());
			report_info.put("stationcreatedcode", form.getStationcreated().getStationcode());
			report_info.put("dispclosedate", form.getDispclosedate());
			report_info.put("status", form.getStatus().getDescription());
			report_info.put("stationassigned", StationBMO.getStation("" + form.getStationassigned_ID()).getStationcode());
			report_info.put("nonrevenue", form.getNonrevenue() == 0 ? "no" : "yes");

			if (form.getReportmethod() == 0)
				report_info.put("reportmethod", "In Person");
			else if (form.getReportmethod() == 1)
				report_info.put("reportmethod", "BSO Phone");
			else if (form.getReportmethod() == 2)
				report_info.put("reportmethod", "Call Center");
			else if (form.getReportmethod() == 3)
				report_info.put("reportmethod", "Internet");
			else if (form.getReportmethod() == 4)
				report_info.put("reportmethod", "Kiosk");

			report_info.put("recordlocator", form.getRecordlocator());
			report_info.put("ticketnumber", form.getTicketnumber());
			parameters.put("report_info", report_info);

			if (selections.get("passenger") != null) {
				List passengers = form.getPassengerlist();
				if (passengers != null && passengers.size() > 0) {
					parameters.put("passengerReport", ReportBMO.getCompiledReport("ma_passenger", sc.getRealPath("/")));
					parameters.put("addressReport", ReportBMO.getCompiledReport("ma_address", sc.getRealPath("/")));
					parameters.put("passenger", new JRBeanCollectionDataSource(passengers));
				} else {
					parameters.put("passenger", null);
				}
			} else {
				parameters.put("passenger", null);
			}

			if (selections.get("passenger_itinerary") != null) {
				List pass_itineraries = form.getPassItineraryList();
				if (pass_itineraries != null && pass_itineraries.size() > 0) {
					parameters.put("passitineraryReport", ReportBMO.getCompiledReport("itinerary", sc.getRealPath("/")));
					parameters.put("passitinerary", new JRBeanCollectionDataSource(pass_itineraries));
				} else
					parameters.put("passitinerary", null);
			} else {
				parameters.put("passenger_itinerary", null);
			}

			if (selections.get("baggage_itinerary") != null) {

				List bag_itineraries = form.getBagItineraryList();
				if (bag_itineraries != null && bag_itineraries.size() > 0) {
					parameters.put("bagitineraryReport", ReportBMO.getCompiledReport("itinerary", sc.getRealPath("/")));
					parameters.put("bagitinerary", new JRBeanCollectionDataSource(bag_itineraries));
				} else
					parameters.put("bagitinerary", null);
			} else {
				parameters.put("bagitinerary", null);
			}

			if (selections.get("baggage_check") != null) {

				HashMap bag_check_info = new HashMap();
				bag_check_info.put("numpassengers", "" + form.getNumpassengers());
				bag_check_info.put("numbagchecked", "" + form.getNumbagchecked());
				bag_check_info.put("numbagreceived", "" + form.getNumbagreceived());

				if (form.getCheckedlocation().equalsIgnoreCase("0"))
					bag_check_info.put("checkedlocation", "");
				else if (form.getCheckedlocation().equalsIgnoreCase("1"))
					bag_check_info.put("checkedlocation", "Curb-side");
				else if (form.getCheckedlocation().equalsIgnoreCase("2"))
					bag_check_info.put("checkedlocation", "Ticket Counter");
				else if (form.getCheckedlocation().equalsIgnoreCase("3"))
					bag_check_info.put("checkedlocation", "Gate");
				else if (form.getCheckedlocation().equalsIgnoreCase("4"))
					bag_check_info.put("checkedlocation", "Remote");
				else if (form.getCheckedlocation().equalsIgnoreCase("5"))
					bag_check_info.put("checkedlocation", "Plane-side");
				else if (form.getCheckedlocation().equalsIgnoreCase("6"))
					bag_check_info.put("checkedlocation", "Unchecked");
				else if (form.getCheckedlocation().equalsIgnoreCase("7"))
					bag_check_info.put("checkedlocation", "Kiosk");


				if (form.getCourtesyreport() == 0)
					bag_check_info.put("courtesyreport", "no");
				else if (form.getCourtesyreport() == 1)
					bag_check_info.put("courtesyreport", "yes");
				else
					bag_check_info.put("courtesyreport", "");

				if (form.getTsachecked() == 0)
					bag_check_info.put("tsachecked", "no");
				else if (form.getTsachecked() == 1)
					bag_check_info.put("tsachecked", "yes");
				else
					bag_check_info.put("tsachecked", "");

				if (form.getCustomcleared() == 0)
					bag_check_info.put("customcleared", "no");
				else if (form.getCustomcleared() == 1)
					bag_check_info.put("customcleared", "yes");
				else
					bag_check_info.put("customcleared", "");

				parameters.put("bag_check_info", bag_check_info);
			} else {
				parameters.put("bag_check_info", null);
			}

			if (selections.get("damaged_info") != null) {

				List bag_items = form.getItemlist();
				if (bag_items != null && bag_items.size() > 0) {

					//Initialize the locale for all articles
					for (Iterator i = bag_items.iterator(); i.hasNext();) {
						Item item = (Item) i.next();

						item.setLocale(user.getDefaultlocale());
					}

					parameters.put("damaged_infoReport", ReportBMO.getCompiledReport("damaged_bag_info", sc.getRealPath("/")));
					parameters.put("inventoryReport", ReportBMO.getCompiledReport("item_inventory", sc.getRealPath("/")));
					parameters.put("photoReport", ReportBMO.getCompiledReport("damaged_photos", sc.getRealPath("/")));
					parameters.put("damaged_info", new JRBeanCollectionDataSource(bag_items));
				} else {
					parameters.put("damaged_info", null);
				}
			} else {
				parameters.put("damaged_info", null);
			}

			if (selections.get("remarks") != null) {

				List remarks = form.getRemarklist();
				if (remarks != null && remarks.size() > 0) {
					parameters.put("remarkReport", ReportBMO.getCompiledReport("remarks", sc.getRealPath("/")));
					parameters.put("remarks", new JRBeanCollectionDataSource(remarks));
				} else {
					parameters.put("remarks", null);
				}
			} else {
				parameters.put("remarks", null);
			}

			if (selections.get("messages") != null) {

				List messages = MessageUtils.getReportMessages(incident_ID, 1);
				if (messages != null && messages.size() > 0) {
					for (Iterator i = messages.iterator(); i.hasNext();) {
						Message msg = (Message) i.next();
						msg.set_DATEFORMAT(user.getDateformat().getFormat());
						msg.set_TIMEFORMAT(user.getTimeformat().getFormat());
						msg.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					}
					parameters.put("messageReport", ReportBMO.getCompiledReport("messages", sc.getRealPath("/")));
					parameters.put("messages", new JRBeanCollectionDataSource(messages));
				} else {
					parameters.put("messages", null);
				}
			} else {
				parameters.put("messages", null);
			}

			if (selections.get("tasks") != null) {

				List tasks = TaskUtils.getFileTasks(incident_ID, 1);
				if (tasks != null && tasks.size() > 0) {
					for (Iterator i = tasks.iterator(); i.hasNext();) {
						Task task = (Task) i.next();
						task.set_DATEFORMAT(user.getDateformat().getFormat());
						task.set_TIMEFORMAT(user.getTimeformat().getFormat());
						task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					}
					parameters.put("taskReport", ReportBMO.getCompiledReport("tasks", sc.getRealPath("/")));
					parameters.put("tasks", new JRBeanCollectionDataSource(tasks));
				} else {
					parameters.put("tasks", null);
				}
			} else {
				parameters.put("tasks", null);
			}

			List t = new ArrayList();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "damaged_history", sc.getRealPath("/"), type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String format(String input) {
		if (input == null || input.equals("null"))
			return "";
		else
			return input;
	}

}