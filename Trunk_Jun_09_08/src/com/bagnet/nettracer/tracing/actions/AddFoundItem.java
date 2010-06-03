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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import com.bagnet.nettracer.tracing.db.LostAndFound_Photo;
import com.bagnet.nettracer.tracing.forms.LostFoundIncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AddFoundItem extends Action {
	public static final int ADD_NEW_RECORD = 1;
	public static final int UPDATE_RECORD = 3;
	private static Logger logger = Logger.getLogger(AddFoundItem.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);
		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));

		request.setAttribute("found", "1");
		ActionMessages errors = new ActionMessages();
		LostFoundIncidentForm Lform = (LostFoundIncidentForm) form;
		
		List oStatusList = TracerUtils.getStatusList(TracingConstants.TABLE_LOST_FOUND, user.getCurrentlocale());
		request.setAttribute("oStatusList", oStatusList);
		

		if (request.getParameter("historical_report") != null
				&& request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.FOUND_HISTORICAL));
		} else if (request.getParameter("viewhistoryreport") != null
				&& request.getParameter("viewhistoryreport").length() > 0) {
			String reportfile = getReportFile(Lform, user, request);

			if (reportfile == null || reportfile.equals("")) {
				ActionMessage error = new ActionMessage("message.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				request.setAttribute("reportfile", reportfile);
			}

			return mapping.findForward(TracingConstants.FOUND_HISTORICAL);
		} else if (request.getParameter("uploadPhoto") != null) {
			request.setAttribute("upload", "1");
			
			// Save the file in the local directory.
			Hashtable files = Lform.getMultipartRequestHandler().getFileElements();
			FormFile theFile = (FormFile) files.get("theFile1");
			if (theFile != null && theFile.getFileSize() > 0) {
				String st = Long.toString((new Date()).getTime());
				String lead = "";
				if (Lform.getFile_ref_number() != null && Lform.getFile_ref_number().length() > 0)
					lead = Lform.getFile_ref_number();
				
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
					return (mapping.findForward(TracingConstants.LOST_FOUND));
				} else {
					// add the image to the DB.
					LostAndFound_Photo photo = new LostAndFound_Photo();
					photo.setPicpath(picpath);
					photo.setThumbpath(thumbpath);
					photo.setFileName(fileName);
					Lform.getPhotoList().add(photo);
				}
			} else {
				// upload file errors.
				ActionMessage error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			return (mapping.findForward(TracingConstants.LOST_FOUND));
		}

		//key to determine whether the action is add new, close, or update
		int saveActionType = ADD_NEW_RECORD;
		String myFoundItemRefId = Lform.getFile_ref_number();
		if (!(myFoundItemRefId == null || myFoundItemRefId.equals(""))) {
			saveActionType = UPDATE_RECORD;
		}

		
		if (request.getParameter("save") != null) {
			BagService bs = new BagService();

			if (bs.insertLostAndFound(Lform, user)) {
				request.setAttribute("file_ref_number", Lform.getFile_ref_number());
				//logger.error(">>>>>>>>>saveActionType (1-addnew; 3-update) : " + saveActionType);
				if (saveActionType == UPDATE_RECORD) {
					return (mapping.findForward(TracingConstants.UPDATE_LOST_FOUND_SUCCESS));
				} else {
					return (mapping.findForward(TracingConstants.LOST_FOUND_SUCCESS));
				}
				//return (mapping.findForward(TracingConstants.LOST_FOUND_SUCCESS));
			}
		} else {
			
			// remove photo
			String index = "0";
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if (parameter.indexOf("[") != -1) {
					index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));

					if (parameter.indexOf("deletePhoto") != -1) {
						List photoList = Lform.getPhotoList();
						if (photoList != null)
							photoList.remove(Integer.parseInt(index));
						return (mapping.findForward(TracingConstants.LOST_FOUND));
					}
				}
			}
			
			
			
			String file_ref_number = request.getParameter("file_ref_number");
			if (file_ref_number != null && file_ref_number.length() > 0) {
				//open an existing lost item report.
				BagService bs = new BagService();
				if (!bs.findLostFoundByID(file_ref_number, Lform, user, true, request)) {
					ActionMessage error = new ActionMessage("error.no.foundreport");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.SEARCH_LOST_FOUND));
				}
			} else {
				//Create a new on-hand entry
				TracerUtils.populateFoundItem(Lform, user, request);
			}
		}


		return mapping.findForward(TracingConstants.LOST_FOUND);
	}

	private String getReportFile(LostFoundIncidentForm theform, Agent user, HttpServletRequest request) {

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		int type = Integer.parseInt(request.getParameter("outputtype"));
		request.setAttribute("outputtype", "" + type);
		String reportfile = createReport(type, sc, (String) request.getParameter("file_ref_number"),
				user, request);
		return reportfile;

	}

	private static String createReport(int type, ServletContext sc, String file_ref_num, Agent user,
			HttpServletRequest request) {

		LostFoundIncidentForm form = new LostFoundIncidentForm();
		BagService bs = new BagService();
		try {
			bs.populateLostLostFoundForm(file_ref_num, form, user, true);
		} catch (Exception e) {
		}

		try {
			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user
							.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("file_ref_number", format("" + form.getFile_ref_number()));
			parameters.put("dispCreateTime", format("" + form.getDispCreateTime()));
			parameters.put("username", format("" + form.getFiling_agent().getUsername()));
			parameters.put("company_station", format(""
					+ form.getCreate_station().getCompany().getCompanyCode_ID())
					+ " " + format("" + form.getCreate_station().getStationcode()));
			//parameters.put("status", format("" + form.getReport_status().getDescription()));
			parameters.put("status", format("" + 
					TracerUtils.getText(form.getReport_status().getKey(), user)
			));
			if (form.getDisposal_status() != null) {
				parameters.put("disposal_status", format("" + TracerUtils.getText(form.getDisposal_status().getKey(), user )));
			}
			parameters.put("dispCloseDateTime", format("" + form.getDispCloseDateTime()));
			if (form.getClosing_agent() != null) parameters.put("closing_agent_username", format(""
					+ form.getClosing_agent().getUsername()));
			parameters.put("dispCloseDateTime", format("" + form.getDispCloseDateTime()));

			parameters.put("customer_lastname", format("" + form.getCustomer_lastname()));
			parameters.put("first_name", format("" + form.getCustomer_firstname()));
			parameters.put("customer_mname", format("" + form.getCustomer_mname()));
			parameters.put("customer_address1", format("" + form.getCustomer_address1()));
			parameters.put("customer_address2", format("" + form.getCustomer_address2()));

			parameters.put("city", format("" + form.getCustomer_city()));
			parameters.put("state", format("" + form.getState()));
			parameters.put("province", format("" + form.getCustomer_province()));
			parameters.put("zip", format("" + form.getCustomer_zip()));
			parameters.put("country", format("" + form.getCountry()));
			parameters.put("phone", format("" + form.getCustomer_tel()));
			parameters.put("email", format("" + form.getCustomer_email()));
			parameters.put("location", format("" + form.getLocation()));
			parameters.put("description", format("" + form.getItem_description()));
			parameters.put("remark", format("" + form.getRemark()));

			parameters.put("finding_agent_name", format("" + form.getFinding_agent_name()));
			parameters.put("dispDateFoundLost", format("" + form.getDispDateFoundLost()));

			List t = new ArrayList();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "found_history", sc.getRealPath("/"), type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String format(String input) {
		if (input == null || input.equals("null")) return "";
		else return input;
	}

}