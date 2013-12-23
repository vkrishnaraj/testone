/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.bagnet.clients.us.SharesIntegrationWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.dr.DisputeUtils;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.history.IncidentHistoryObject;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ClientUtils;
import com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.HistoryUtils;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MBRActionUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

/**
 * 
 * @author Matt
 */
public class MissingAction extends CheckedAction {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session and user validity
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));

		/** ****************** handle requests ******************** */

		IncidentForm theform = (IncidentForm) form;

		MBRActionUtils.createIssuanceLists(request, theform.getStationassigned(), TracingConstants.MISSING_ARTICLES, theform.getIssuanceItemIncidents());
		
		if (request.getParameter("email_customer") != null)
			theform.setEmail_customer(1);
		else
			theform.setEmail_customer(0);
		
		ActionMessages errors = new ActionMessages();

		boolean checkLLC = false;
		if(request.getAttribute("currentstatus") != null) {
			checkLLC = Integer.parseInt((String)request.getAttribute("currentstatus")) == TracingConstants.MBR_STATUS_CLOSED;
		}
		
		//the company specific codes..
		List codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.MISSING_ARTICLES, true, user, checkLLC);
		//add to the loss codes
		request.setAttribute("losscodes", codes);

		List list = new ArrayList();
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_DISABLED_BAG, user) 
				&& session.getAttribute("assistDeviceList") == null) {
			list=new ArrayList(MBRActionUtils.getAssistDeviceTypes());

			if(list!=null)
				session.setAttribute("assistDeviceList", list);
		}
		BagService bs = new BagService();

		request.setAttribute("missing", "1");

		if (request.getParameter("express") != null)
			request.setAttribute("express", "1");

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES, user))
			theform.setReadonly(1);

		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, user))
			theform.setAllow_remark_update(1);
		
		if (request.getParameter("historical_report") != null && request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.MA_HISTORICAL));
		} else if (request.getParameter("viewhistoryreport") != null && request.getParameter("viewhistoryreport").length() > 0) {
			String reportfile = getReportFile(theform, user, request);

			if (reportfile == null || reportfile.equals("")) {
				if(request.getParameter("outputtype").equalsIgnoreCase("5")) {	//Teletype
					ActionMessage error = new ActionMessage("");
					String teletypeAddress = request.getParameter("teletypeAddress");
					if(teletypeAddress == null || teletypeAddress.equals("")) {
						error = new ActionMessage("message.no.teletype.address.provided");
					} else {
						error = new ActionMessage("message.send.teletype.info");
					}
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			} else {
				request.setAttribute("reportfile", reportfile);
			}

			return mapping.findForward(TracingConstants.MA_HISTORICAL);
		}
		
		if (!(request.getParameter("changeassignedstation") != null
				&& request.getParameter("changeassignedstation").equals("1") && ((IncidentForm) form)
				.getStationassigned_ID() > 0)
				&& !(request.getParameter("getstation") != null && request.getParameter("getstation").equals("1"))) {
			if (!manageToken(request)) {
				return (mapping.findForward(TracingConstants.INVALID_TOKEN));
			}
		}

		// ajax call to change assigned agent dropdown
		if (MBRActionUtils.actionChangeAssignedStation(theform, request)) {
			return (mapping.findForward(TracingConstants.AJAX_AGENTASSIGNED));
		}
		
		IncidentUtils.manageIncidentEmailing(user.getStation().getCompany(), request);
		
		List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
		request.setAttribute("agentassignedlist", agentassignedlist);
		
		//new code for dispute resolution
		String form_incident_id = null;
		boolean disputeProcess = false;

		if(theform.getIncident_ID() != null) {
			form_incident_id = theform.getIncident_ID();
			request.setAttribute("incident", form_incident_id);
			
			IncidentBMO.fillFormWithExistingData(form_incident_id, theform);
		}
		if(!(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES))){
			
			Dispute myDispute = null;
			
			if(theform.getIncident_ID() != null) {
				myDispute = DisputeUtils.getDisputeByIncidentId(form_incident_id);
			}
			
			if (myDispute != null) {
				disputeProcess = true;
			} 
			
			if (theform.getIncident_ID() != null) request.setAttribute("incident",theform.getIncident_ID());
	
			if (request.getParameter("lock_fault") != null){
				DisputeResolutionUtils.lockIncident(theform.getIncident_ID(),theform);
				theform.setLocked(true);
				request.removeAttribute("lock_fault");
			}
			if (request.getParameter("unlock_fault") != null){
				DisputeResolutionUtils.unlockIncident(theform.getIncident_ID());
				theform.setLocked(false);
				request.removeAttribute("unlock_fault");
			}
			if (request.getParameter("lock_faultcode") != null){
				DisputeResolutionUtils.lockIncidentCode(theform.getIncident_ID(),theform.getLoss_code());
				theform.setCodeLocked(true);
				request.removeAttribute("lock_fault");
			}
			if (request.getParameter("unlock_faultcode") != null){
				DisputeResolutionUtils.unlockIncidentCode(theform.getIncident_ID());
				theform.setCodeLocked(false);
				request.removeAttribute("unlock_fault");
			}
			if (request.getParameter("lock_faultstation") != null){
				DisputeResolutionUtils.lockIncidentStation(theform.getIncident_ID(),theform.getFaultstation().getStation_ID());
				theform.setStationLocked(true);
				request.removeAttribute("lock_faultstation");
			}
			if (request.getParameter("unlock_faultstation") != null){
				DisputeResolutionUtils.unlockIncidentStation(theform.getIncident_ID());
				theform.setStationLocked(false);
				request.removeAttribute("unlock_fault");
			}
		}
		
		if(MBRActionUtils.actionClose(theform, request, user, errors)) {
			saveMessages(request, errors);
			
			//		 AJAX CALL
			if (request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {
				return (mapping.findForward(TracingConstants.AJAX_FAULTSTATION));
			} else {
				int currentStatus = -1;
				boolean canSave = UserPermissions.hasIncidentSavePermission(user, theform.getIncident_ID());
				if(!canSave) {
					return mapping.findForward(TracingConstants.MISSING_CLOSE_READ_ONLY);
				}
				if(request.getAttribute("currentstatus") != null) {
					currentStatus = Integer.parseInt((String)request.getAttribute("currentstatus"));
				}
				System.out.println("CHECK THIS OUT!!! Current Status: "+currentStatus);
				if(currentStatus == TracingConstants.MBR_STATUS_CLOSED) {
					
					//if it is closed user can only edit it if they have the permission to edit closed files
					if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_MISSING_LOSS_CODES, user)) {
						//check to see if there is a dispute on file for this incident
						//if so, display Dispute Resolution Tab, and no Dispute Fault button
						request.setAttribute("disputeProcess", disputeProcess);
						return mapping.findForward(TracingConstants.MISSING_CLOSE);
					}
					return mapping.findForward(TracingConstants.MISSING_CLOSE_READ_ONLY);
				}
				//not closed
				else {
					request.setAttribute("disputeProcess", disputeProcess);
					return (mapping.findForward(TracingConstants.MISSING_CLOSE));
				}
			}
			
		}
		if(request.getAttribute("faultCompanyList") == null || request.getAttribute("faultstationlist") == null) {
			if(UserPermissions.hasLimitedSavePermissionByType(user, TracingConstants.MISSING_ARTICLES)) {
				request.setAttribute("faultstationlist", UserPermissions.getLimitedSaveStations(user, TracingConstants.MISSING_ARTICLES));
				ArrayList faultCompanyList = new ArrayList();
				faultCompanyList.add(user.getStation().getCompany());
				request.setAttribute("faultCompanyList", faultCompanyList);
			}
			else if (UserPermissions.hasLimitedFaultAirlinesByType(user, TracingConstants.MISSING_ARTICLES)) {
				request.setAttribute("faultstationlist", TracerUtils.getStationList(theform.getFaultcompany_id()));
				ArrayList faultCompanyList = new ArrayList();
				faultCompanyList.add(user.getStation().getCompany());
				request.setAttribute("faultCompanyList", faultCompanyList);
			}
			else {
				request.setAttribute("faultstationlist", TracerUtils.getStationList(theform.getFaultcompany_id()));
				request.setAttribute("faultCompanyList", (List) request.getSession().getAttribute("companylistByName"));
			}
		}
		
		if(MBRActionUtils.passengerPickedUp(theform, request,errors)) {
			saveMessages(request, errors);
			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}
		if (MBRActionUtils.actionDelete(theform, request)) {
			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}
		if (MBRActionUtils.actionAdd(theform, request, user,TracingConstants.MISSING_ARTICLES)) {
			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}
		if(MBRActionUtils.actionIssueItem(theform, request, user)) {
			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}
		if(MBRActionUtils.actionReturnItem(theform, request, user)) {
			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}

		String loc = null;
		if ((loc = MBRActionUtils.actionAddAssoc(theform, request, user)) != null) {
			return (mapping.findForward(loc));
		}
		
		//to handle the image file upload
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
			theform.getItem(itemindex, -1).getPhotolist().remove(photoindex);
			request.setAttribute("upload", Integer.toString(itemindex));
			request.setAttribute("markDirty", 1);
			return (mapping.findForward(TracingConstants.DAMAGED_MAIN));
		}

		if (fileindex >= 0) {
			// add photo
			request.setAttribute("upload", Integer.toString(fileindex));

			
			
			// Save the file in the local directory.
			Hashtable files = theform.getMultipartRequestHandler().getFileElements();
			FormFile theFile = (FormFile) files.get("imagefile" + fileindex);
			request.setAttribute("markDirty", 1);
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
					return (mapping.findForward(TracingConstants.MISSING_MAIN));
				} else {
					// add the image to the DB.
					Item_Photo photo = new Item_Photo();
					photo.setPicpath(picpath);
					photo.setThumbpath(thumbpath);
					photo.setItem(theform.getItem(fileindex, -1));
					photo.setFileName(fileName);
					ArrayList al = (ArrayList) theform.getItem(fileindex, -1).getPhotolist();
					al.add(photo);
				}
			} else {
				// upload file errors.
				ActionMessage error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
	

			return (mapping.findForward(TracingConstants.MISSING_MAIN));
		}
		
		
		ServletContext sc = getServlet().getServletContext();
		String realpath = sc.getRealPath("/");


		// save incident
		if (request.getParameter("save") != null || request.getParameter("doclose") != null 
				|| request.getParameter("saveadditions") != null || request.getParameter("saveCustCommId") != null) {
			Incident iDTO = new Incident();
			
			//key to determine whether the action is add new, close, or update
			int saveActionType = ADD_NEW_RECORD;
			
			if (theform.getIncident_ID() == null || theform.getIncident_ID().length() == 0)
				theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_OPEN);

			// update the key saveActionType: if no incident id, then it is addnew
			String myIncidentId = theform.getIncident_ID();
			if (!(myIncidentId == null || myIncidentId.equals(""))) {
				if( request.getParameter("close") != null && request.getParameter("close").equals("1")) {
					saveActionType = CLOSE_RECORD;
				} else {
					saveActionType = UPDATE_RECORD;
				}
			}
			
			ActionMessage error = null; 

			if (request.getParameter("close") != null && request.getParameter("close").equals("1")) {
				error = bs.insertIncident(iDTO, theform, TracingConstants.MISSING_ARTICLES, sc, request);
			} else if(request.getParameter("saveadditions") != null) {
				error=bs.saveItems(iDTO,theform, TracingConstants.MISSING_ARTICLES, realpath, user, false);
			} else {
				error = bs.insertIncident(iDTO, theform, TracingConstants.MISSING_ARTICLES, sc, request, true);
			}

			if (error == null) {
				
				if (theform.getOtherSystemInformation() != null && theform.getOtherSystemInformation().trim().length() >0) {
					// Assumes this is new and that we are saving OSI for first time.
					OtherSystemInformation osi = new OtherSystemInformation();
					osi.setIncident(iDTO);
					osi.setInfo(theform.getOtherSystemInformation());
					HibernateUtils.save(osi);
					
				} 
				boolean isNew = theform.getIncident_ID() == null || theform.getIncident_ID().trim().length() == 0;
				if (isNew && PropertyBMO.isTrue("ntfs.submit.missing")) {
					ConnectionUtil.createAndSubmitForTracing(iDTO, user, request, UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, user));
//					File file = ConnectionUtil.createAndSubmitForTracing(iDTO, user, request, UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, user));
//					if (file != null) {
//						session.setAttribute("file", file);
//					}
				}
				
				theform.setRemarkEnteredWhenNotifiedOfRequirements(false);
				theform.setNotifiedOfRequirements(false);
				request.setAttribute("missingarticles", "1");
				request.setAttribute("Incident_ID", iDTO.getIncident_ID());
				
				IncidentHistoryObject IHO=new IncidentHistoryObject();
				IHO.setIncident(iDTO);
				IHO.setObjectID(iDTO.getIncident_ID());
				IHO.setLinkURL("searchIncident.do?incident=");
				IHO.setObjectType(TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
				
				//return (mapping.findForward(TracingConstants.INSERT_SUCCESS));
				if (saveActionType == UPDATE_RECORD) {
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_UPDATE+" "+TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);
					return (mapping.findForward(TracingConstants.UPDATE_FILE_SUCCESS));
				} else if (saveActionType == CLOSE_RECORD) {
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_CLOSE+" "+TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);
					return (mapping.findForward(TracingConstants.CLOSE_FILE_SUCCESS));
				} else {
					IHO.setStatusDesc(TracingConstants.HIST_DESCRIPTION_CREATE+" "+TracingConstants.HIST_DESCRIPTION_MISSING+" "+TracingConstants.HIST_DESCRIPTION_INCIDENT);
					HistoryUtils.AddToHistoryContainer(session, IHO, null);
					return (mapping.findForward(TracingConstants.INSERT_SUCCESS));
				}
				
			} else if (error.getKey().equals("error.unable_to_close_incident")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				request.setAttribute("currentstatus", iDTO.getStatus().getStatus_ID());
				saveMessages(request, errors);
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
				theform.setLastupdated(IncidentBMO.getIncidentLastUpdateTimestamp(iDTO.getIncident_ID()));//reseting lastUpdated, otherwise agent will be unable to close incident
				return (mapping.findForward(TracingConstants.MISSING_CLOSE));
			}	else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.MISSING_MAIN));
			}
		}
		/**
		 * *********** prepopulation for new list *********** or retrieve to modify
		 * incident on html rewrite link
		 */
		String incident = request.getParameter("incident_ID");
		Incident inc = null;
		if (incident != null && incident.length() > 0) {
			if ((inc = bs.findIncidentByID(incident, theform, user, TracingConstants.MISSING_ARTICLES)) == null) {
				ActionMessage error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			}
			request.setAttribute("incident", incident);
			session.setAttribute("incidentObj", inc);
			List<ActionMessage> lockErrors = SpringUtils.getLockFile().getLockActionMessages(inc.getIncident_ID(), user);
			if(lockErrors != null){
				for(ActionMessage lockError:lockErrors){
					errors.add(ActionMessages.GLOBAL_MESSAGE, lockError);
					saveMessages(request, errors);
				}
			}

		} else {
			if(request.getParameter("doprepopulate")==null){
				theform.setRecordlocator("");
			}
			// prepopulate
			TracerUtils.populateIncident(theform, request, TracingConstants.MISSING_ARTICLES);
			IncidentForm thenewform = (IncidentForm)session.getAttribute("incidentForm");
			thenewform.setFaultcompany_id(user.getCompanycode_ID());
			request.setAttribute("newform", "1");

			ActionMessage error = null;
			ArrayList alerrors = new ArrayList();

			if(request.getParameter("pnrpopulate") == null && user.getStation().getCompany().getVariable().getPnr_last_x_days()!=0 && theform.getRecordlocator()!=null && theform.getRecordlocator().length()>0){
				List<Incident> pnrList = MBRActionUtils.prePopulateCheck(theform.getRecordlocator(),user.getStation().getCompany().getVariable().getPnr_last_x_days());
				if(pnrList!=null && pnrList.size()>0){
					List<Incident> ilist=new ArrayList();
					for(Incident o:pnrList){
						Incident i=o;
						ilist.add(i);
					}
					request.setAttribute("pnrlist", ilist);
					session.setAttribute("pnrtrue",  theform.getRecordlocator());
					theform.setRecordlocator(theform.getRecordlocator());
				}
			}
			boolean pnrcheck=(user.getStation().getCompany().getVariable().getPnr_last_x_days()!=0 && (request.getParameter("pnrpopulate") != null || (request.getParameter("pnrpopulate") == null && request.getAttribute("pnrlist")==null))) || (user.getStation().getCompany().getVariable().getPnr_last_x_days()==0 && request.getParameter("doprepopulate1") != null);
			// Attempt to prepopulate the fields from the reservation integration.
			if(!(theform.getRecordlocator()!=null  && theform.getRecordlocator().length()>0) && session.getAttribute("pnrtrue")!=null && session.getAttribute("pnrtrue").toString().length()>0){
				theform.setRecordlocator(session.getAttribute("pnrtrue").toString());
			}
			if (pnrcheck && MBRActionUtils.prePopulate(request,theform,alerrors,TracingConstants.MISSING_ARTICLES)) {
				if (alerrors.size() > 0) {
					for (int i=0;i<alerrors.size();i++) {
						error = new ActionMessage((String)alerrors.get(i));
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
					saveMessages(request, errors);
					request.setAttribute("prepopulate",new Integer("1"));
				} else {
					MBRActionUtils.createIssuanceLists(request, theform.getStationassigned(), TracingConstants.MISSING_ARTICLES, theform.getIssuanceItemIncidents());
					request.setAttribute("pnrlist", null);
					session.setAttribute("pnrtrue", null);
					request.setAttribute("markDirty", 1);
				}
				return (mapping.findForward(TracingConstants.MISSING_MAIN));
			}
			
			if (SpringUtils.getReservationIntegration().isPopulateIncidentFormOn() && request.getParameter("skip_prepopulate") == null  && request.getParameter("express") == null) {
				request.setAttribute("prepopulate",new Integer("1"));
				return (mapping.findForward(TracingConstants.MISSING_MAIN));
			}
			
		}

		MBRActionUtils.createIssuanceLists(request, theform.getStationassigned(), TracingConstants.MISSING_ARTICLES, theform.getIssuanceItemIncidents());
		
		return (mapping.findForward(TracingConstants.MISSING_MAIN));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getReportFile(IncidentForm theform, Agent user, HttpServletRequest request) {

		ServletContext sc = getServlet().getServletContext();

		HashMap selections = new HashMap();

		if (request.getParameter("all") != null) {
			request.setAttribute("all", "1");
			selections.put("passenger", "true");
			selections.put("passenger_itinerary", "true");
			selections.put("baggage_itinerary", "true");
			selections.put("baggage_check", "true");
			selections.put("baggage_info", "true");
			selections.put("missing_articles", "true");
			selections.put("remarks", "true");
			selections.put("messages", "true");
			selections.put("tasks", "true");
			selections.put("interimexpense", "true");
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

			if (request.getParameter("baggage_info") != null) {
				request.setAttribute("baggage_info", "1");
				selections.put("baggage_info", "true");
			} else
				selections.put("baggage_info", null);

			if (request.getParameter("missing_articles") != null) {
				request.setAttribute("missing_articles", "1");
				selections.put("missing_articles", "true");
			} else
				selections.put("missing_articles", null);

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
			
			if (request.getParameter("interimexpense") != null) {
				request.setAttribute("interimexpense", "1");
				selections.put("interimexpense", "true");
			} else
				selections.put("interimexpense", null);						
		}

		int type = Integer.parseInt(request.getParameter("outputtype"));
		request.setAttribute("outputtype", "" + type);
		String reportfile = createReport(selections, type, sc, (String) request.getParameter("incident_ID"), user, request);
		return reportfile;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
			
			//history report: set special parameter to signal long report with no page break for HTML
			parameters.put("history_report_long", "Yes");

			HashMap report_info = new HashMap();
			report_info.put("incident_ID", form.getIncident_ID());
			report_info.put("dispcreatetime", form.getDispcreatetime());
			report_info.put("agentinit", form.getAgentinit());
			report_info.put("stationcreatedcode", form.getStationcreated().getStationcode());
			report_info.put("dispclosedate", form.getDispclosedate());
			report_info.put("status", TracerUtils.getText(form.getStatus().getKey(), user));
			report_info.put("stationassigned", StationBMO.getStation("" + form.getStationassigned_ID()).getStationcode());
			report_info.put("nonrevenue", form.getNonrevenue() == 0 ? "no" : "yes");

//			if (form.getReportmethod() == 0)
//				report_info.put("reportmethod", "In Person");
//			else if (form.getReportmethod() == 1)
//				report_info.put("reportmethod", "BSO Phone");
//			else if (form.getReportmethod() == 2)
//				report_info.put("reportmethod", "Call Center");
//			else if (form.getReportmethod() == 3)
//				report_info.put("reportmethod", "Internet");
//			else if (form.getReportmethod() == 4)
//				report_info.put("reportmethod", "Kiosk");

			report_info.put("reportmethod", ((ClientUtils) SpringUtils.getBean("clientUtils")).getReportMethodName(form.getReportmethod()));
			
			report_info.put("recordlocator", form.getRecordlocator());
			report_info.put("ticketnumber", form.getTicketnumber());
			
			//newly added fields
			report_info.put("associatedFile", form.getAssoc_ID());
			report_info.put("lossCode", form.getLoss_code());
			int faultStationId = form.getFaultstation_id();
			report_info.put("faultStation", TracerUtils.getStationcode(faultStationId));
			report_info.put("closingRemarks", form.getClosingRemark(2).getRemarktext());
			
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

			if (selections.get("baggage_info") != null) {

				List bag_items = form.getItemlist();
				if (bag_items != null && bag_items.size() > 0) {
					parameters.put("baginfoReport", ReportBMO.getCompiledReport("ma_bag_info", sc.getRealPath("/")));
					parameters.put("inventoryReport", ReportBMO.getCompiledReport("item_inventory", sc.getRealPath("/")));
					parameters.put("baginfo", new JRBeanCollectionDataSource(bag_items));
				} else {
					parameters.put("baginfo", null);
				}
			} else {
				parameters.put("baginfo", null);
			}

			if (selections.get("missing_articles") != null) {
				List articles = form.getArticlelist();
				if (articles != null && articles.size() > 0) {

					//Initialize the locale for all articles
					for (Iterator i = articles.iterator(); i.hasNext();) {
						Articles a = (Articles) i.next();

						a.setLocale(user.getDefaultlocale());
					}

					parameters.put("articlesReport", ReportBMO.getCompiledReport("ma_articles", sc.getRealPath("/")));
					parameters.put("articles", new JRBeanCollectionDataSource(articles));
				} else {
					parameters.put("articles", null);
				}
			} else {
				parameters.put("articles", null);
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

			if (selections.get("interimexpense") != null) {

				List<ExpensePayout> interimexpense = form.getExpenselist();

				if (interimexpense != null && interimexpense.size() > 0) {
		            double checktotal   = 0;
		            double vouchertotal = 0;
		            int    mileagetotal = 0;
					for(ExpensePayout payout : interimexpense) {
			              if(payout.getStatus().getStatus_ID() != TracingConstants.EXPENSEPAYOUT_STATUS_DENIED) {
			                  checktotal   += payout.getCheckamt();
			                  if (payout.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PAID ) 
			                    vouchertotal += payout.getVoucheramt();
			                  mileagetotal += payout.getMileageamt();
			                  }
						
					}
					parameters.put("checktotal",  Double.toString(checktotal));
					parameters.put("vouchertotal", Double.toString(vouchertotal));
					parameters.put("mileagetotal", Integer.toString(mileagetotal));
					parameters.put("interimexpenseReport", ReportBMO.getCompiledReport("interimexpense", sc.getRealPath("/")));
					parameters.put("interimexpense", new JRBeanCollectionDataSource(interimexpense));
				} else {
					parameters.put("interimexpense", null);
				}
			} else {
				parameters.put("interimexpense", null);
			}
			
			if (type == 5) {	//Teletype so no regular reports
				String myTeletypeHistoricalReport = buildTeletypeStyleHistoricalReport(parameters);
				SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
				String teletypeAddress = request.getParameter("teletypeAddress");
				iw.sendTelexBySlice(myTeletypeHistoricalReport, teletypeAddress, form.getIncident_ID());
				
				return null;
			} else {
				List t = new ArrayList();
				t.add("");
				ReportBMO bmo = new ReportBMO(request);
				return bmo.getReportFile(t, parameters, "ma_history", sc.getRealPath("/"), type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String buildTeletypeStyleHistoricalReport(Map parameters) {
		StringBuilder historicalReport = new StringBuilder();
		historicalReport.append(newline);
		
		ResourceBundle resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		
		Map reportInfo = (HashMap) parameters.get("report_info");
		if (reportInfo != null) {
			historicalReport.append("-- " + resourceBundle.getString("header.incident_info") + " --");
			historicalReport.append(newline);
			
			//general section
			historicalReport
				.append(resourceBundle.getString("colname.incident_num") + ": ")
				.append(reportInfo.get("incident_ID") + newline);
			historicalReport
				.append(resourceBundle.getString("colname.incident_create_date") + ": ")
				.append(reportInfo.get("dispcreatetime") + newline);
			historicalReport
				.append(resourceBundle.getString("colname.agentusername") + ": ")
				.append(reportInfo.get("agentinit") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.stationcreated_nobr") + ": ")
				.append(reportInfo.get("stationcreatedcode") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.file_close_date") + ": ")
				.append(reportInfo.get("dispclosedate") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.status") + ": ")
				.append(reportInfo.get("status") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.stationassigned_nobr") + ": ")
				.append(reportInfo.get("stationassigned") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.non_revenue") + ": ")
				.append(reportInfo.get("nonrevenue") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.report_method") + ": ")
				.append(reportInfo.get("reportmethod") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.recordlocator") + ": ")
				.append(reportInfo.get("recordlocator") + newline);		
			
			historicalReport
				.append(resourceBundle.getString("colname.ticket") + ": ")
				.append(reportInfo.get("ticketnumber") + newline);	
			
			//newly added fields
			historicalReport
				.append(resourceBundle.getString("colname.associated.file") + ": ")
				.append(reportInfo.get("associatedFile") + newline);	

			historicalReport
				.append(resourceBundle.getString("colname.loss.code") + ": ")
				.append(reportInfo.get("lossCode") + newline);			
			
			historicalReport
				.append(resourceBundle.getString("colname.fault.station") + ": ")
				.append(reportInfo.get("faultStation") + newline);
			
			historicalReport
				.append(resourceBundle.getString("colname.closing.remarks") + ": ")
				.append(reportInfo.get("closingRemarks") + newline);			
			
			
			//ma_passenger section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.passenger_info") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource passengerDS = (JRBeanCollectionDataSource) parameters.get("passenger");
			if (passengerDS != null) {
				List<Passenger> myPassengers = (List<Passenger>) passengerDS.getData();
				if (myPassengers != null & myPassengers.size() >= 1) {
					for (Passenger pax : myPassengers) {
						historicalReport
							.append(resourceBundle.getString("colname.pass_name"))
							.append(newline)
							.append(resourceBundle.getString("colname.airline_membership") + ": ")
							.append(pax.getAirlinememcompany() + newline)
							.append(indent + resourceBundle.getString("colname.membership_status") + ": ")
							.append(pax.getAirlinememstatus() + newline)
							.append(indent + resourceBundle.getString("colname.membership_number") + ": ")
							.append(pax.getAirlinememnumber() + newline)
							.append(indent + resourceBundle.getString("colname.last_name") + ": ")
							.append(pax.getLastname() + newline)
							.append(indent + resourceBundle.getString("colname.first_name") + ": ")
							.append(pax.getFirstname() + newline)
							.append(indent + resourceBundle.getString("middle") + ": ")
							.append(pax.getMiddlename() + newline)
							.append(indent + resourceBundle.getString("colname.salutation") + ": ")
							.append(pax.getDispsalutation() + newline)
							.append(resourceBundle.getString("colname.job_title") + ": ")
							.append(pax.getJobtitle() + newline)
							.append(resourceBundle.getString("colname.dlstate") + ": ")
							.append(pax.getDispdlstate() + newline)
							.append(resourceBundle.getString("colname.drivers") + ": ")
							.append(pax.getDriverslicense() + newline)
							.append(resourceBundle.getString("colname.common_num") + ": ")
							.append(pax.getCommonnum() + newline)
							.append(resourceBundle.getString("colname.country_of_issue") + ": ")
							.append(pax.getDispcountryofissue() + newline);
						
						//pax addresses
						historicalReport.append(newline);
						JRBeanCollectionDataSource addressDS = (JRBeanCollectionDataSource) pax.getAddressesForReport();
						if (addressDS != null) {
							List<Address> myAddresses = (List<Address>) addressDS.getData();
							if (myAddresses != null & myAddresses.size() >= 1) {
								for (Address address : myAddresses) {
									historicalReport
										.append(resourceBundle.getString("colname.street_addr1") + ": ")
										.append(address.getAddress1() + newline)
										.append(resourceBundle.getString("colname.street_addr2") + ": ")
										.append(address.getAddress2() + newline)
										.append(resourceBundle.getString("colname.city") + ": ")
										.append(address.getCity() + newline)
										.append(resourceBundle.getString("colname.state") + ": ")
										.append(address.getState() + newline)
										.append(resourceBundle.getString("colname.province") + ": ")
										.append(address.getProvince() + newline)
										.append(resourceBundle.getString("colname.country") + ": ")
										.append(address.getCountry() + newline)
										.append(resourceBundle.getString("colname.zip") + ": ")
										.append(address.getZip() + newline)
										.append(resourceBundle.getString("colname.business_ph") + ": ")
										.append(address.getWorkphone() + newline)
										.append(resourceBundle.getString("colname.home_ph") + ": ")
										.append(address.getHomephone() + newline)
										.append(resourceBundle.getString("colname.alt_ph") + ": ")
										.append(address.getAltphone() + newline)
										.append(resourceBundle.getString("colname.mobile_ph") + ": ")
										.append(address.getMobile() + newline)
										.append(resourceBundle.getString("colname.pager_ph") + ": ")
										.append(address.getPager() + newline)
										.append(resourceBundle.getString("colname.hotel") + ": ")
										.append(address.getHotel() + newline)
										.append(resourceBundle.getString("colname.email") + ": ")
										.append(address.getEmail() + newline)
										.append(resourceBundle.getString("colname.valid_bdate") + ": ")
										.append(address.getDispvalid_bdate() + newline)
										.append(resourceBundle.getString("colname.valid_edate") + ": ")
										.append(address.getDispvalid_edate() + newline);
								}
							}
						}
					}
				}
			}
			
			//pax itinerary section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.itinerary") + " --");
			historicalReport.append(newline);
			
			JRBeanCollectionDataSource paxItineraryDS = (JRBeanCollectionDataSource) parameters.get("passitinerary");
			if (paxItineraryDS != null) {
				List<Itinerary> myItineraries = (List<Itinerary>) paxItineraryDS.getData();
				if (myItineraries != null & myItineraries.size() >= 1) {
					for (Itinerary paxItinerary : myItineraries) {
						historicalReport
							.append(resourceBundle.getString("colname.fromto") + ": ")
							.append(paxItinerary.getLegfrom() + " / " + paxItinerary.getLegto() + newline)
							.append(indent + resourceBundle.getString("colname.flightnum") + ": ")
							.append(paxItinerary.getFlightnum() + newline)
							.append(indent + resourceBundle.getString("colname.departdate") + ": ")
							.append(paxItinerary.getDisdepartdate() + newline)
							.append(indent + resourceBundle.getString("colname.arrdate") + ": ")
							.append(paxItinerary.getDisarrivedate() + newline)
							.append(indent + resourceBundle.getString("colname.schdeptime") + ": ")
							.append(paxItinerary.getDisschdeparttime() + newline)									
							.append(indent + resourceBundle.getString("colname.actdeptime") + ": ")
							.append(paxItinerary.getDisactdeparttime() + newline)					
							.append(indent + resourceBundle.getString("colname.scharrtime") + ": ")
							.append(paxItinerary.getDisscharrivetime() + newline)
							.append(indent + resourceBundle.getString("colname.actarrtime") + ": ")
							.append(paxItinerary.getDisactarrivetime() + newline)
							.append(newline);
					}
				}
			}
			
			//baggage itinerary section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.bag_itinerary") + " --");
			historicalReport.append(newline);
			
			JRBeanCollectionDataSource bagItineraryDS = (JRBeanCollectionDataSource) parameters.get("bagitinerary");
			if (bagItineraryDS != null) {
				List<Itinerary> myBagItineraries = (List<Itinerary>) bagItineraryDS.getData();
				if (myBagItineraries != null & myBagItineraries.size() >= 1) {
					for (Itinerary bagItinerary : myBagItineraries) {
						historicalReport
							.append(resourceBundle.getString("colname.fromto") + ": ")
							.append(bagItinerary.getLegfrom() + "/" + bagItinerary.getLegto() + newline)
							.append(indent + resourceBundle.getString("colname.flightnum") + ": ")
							.append(bagItinerary.getFlightnum() + newline)
							.append(indent + resourceBundle.getString("colname.departdate") + ": ")
							.append(bagItinerary.getDisdepartdate() + newline)
							.append(indent + resourceBundle.getString("colname.arrdate") + ": ")
							.append(bagItinerary.getDisarrivedate() + newline)
							.append(indent + resourceBundle.getString("colname.schdeptime") + ": ")
							.append(bagItinerary.getDisschdeparttime() + newline)									
							.append(indent + resourceBundle.getString("colname.actdeptime") + ": ")
							.append(bagItinerary.getDisactdeparttime() + newline)					
							.append(indent + resourceBundle.getString("colname.scharrtime") + ": ")
							.append(bagItinerary.getDisscharrivetime() + newline)
							.append(indent + resourceBundle.getString("colname.actarrtime") + ": ")
							.append(bagItinerary.getDisactarrivetime() + newline)
							.append(newline);
					}
				}
			}

			
			//checked baggage section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.checked_bag_info") + " --");
			historicalReport.append(newline);
			
			Map bag_check_info = (HashMap) parameters.get("bag_check_info");
			
			historicalReport
				.append(resourceBundle.getString("colname.num_pass") + ": ")
				.append(bag_check_info.get("numpassengers") + newline)
				.append(resourceBundle.getString("colname.num_bag_checked") + ": ")
				.append(bag_check_info.get("numbagchecked") + newline)
				.append(resourceBundle.getString("colname.bags_rec") + ": ")
				.append(bag_check_info.get("numbagreceived") + newline)
				.append(resourceBundle.getString("colname.bag_loc") + ": ")
				.append(bag_check_info.get("checkedlocation") + newline)
				.append(resourceBundle.getString("colname.courtesy_report") + ": ")
				.append(bag_check_info.get("courtesyreport") + newline)
				.append(resourceBundle.getString("colname.tsa") + ": ")
				.append(bag_check_info.get("tsachecked") + newline)	
				.append(resourceBundle.getString("colname.custom") + ": ")
				.append(bag_check_info.get("customcleared") + newline);
			
			
			//baggage information section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.bag_info") + " --");
			historicalReport.append(newline);
			
			JRBeanCollectionDataSource bagInfoDS = (JRBeanCollectionDataSource) parameters.get("baginfo");
			if (bagInfoDS != null) {
				List<Item> bags = (List<Item>) bagInfoDS.getData();
				if (bags != null & bags.size() >= 1) {
					for (Item bag : bags) {
						historicalReport
							.append(resourceBundle.getString("colname.claimnum") + ": ")
							.append(bag.getClaimchecknum() + newline)
							.append(resourceBundle.getString("colname.last_name_onbag") + ": ")
							.append(bag.getLnameonbag() + newline)
							.append(resourceBundle.getString("colname.first_name_onbag") + ": ")
							.append(bag.getFnameonbag() + newline)
							.append(resourceBundle.getString("colname.mid_initial_onbag") + ": ")
							.append(bag.getMnameonbag() + newline)
							.append(resourceBundle.getString("colname.color") + ": ")
							.append(bag.getColor() + newline)
							.append(resourceBundle.getString("colname.bagtype") + ": ")
							.append(bag.getBagtype() + newline)
							.append(resourceBundle.getString("colname.manufacturer") + ": ")
							.append(bag.getManufacturer() + newline)
							.append(resourceBundle.getString("colname.bag_status") + ": ")
							.append(resourceBundle.getString(bag.getDispstatus()) + newline)
							.append(resourceBundle.getString("colname.x_desc") + ": ")
							.append(resourceBundle.getString(bag.getXdescelement1Key()) + newline)
							.append(resourceBundle.getString(bag.getXdescelement2Key()) + newline)
							.append(resourceBundle.getString(bag.getXdescelement3Key()) + newline);
						
						//bag content section
						historicalReport.append(newline);
						historicalReport.append("-- " + resourceBundle.getString("colname.key_contents") + " --");
						historicalReport.append(newline);
						List<Item_Inventory> contents = (List<Item_Inventory>) bag.getInventorylist();
						if (contents != null & contents.size() >= 1) {
							//historicalReport.append("# of contents:" + contents.size() + newline);
							for (Item_Inventory content : contents) {
								historicalReport
									.append(resourceBundle.getString("colname.category") + ": ")
									.append(content.getCategory() + newline)
									.append(resourceBundle.getString("colname.description") + ": ")
									.append(content.getDescription() + newline);
							}
						}
					}
				}
			}
			
			//missing articles section 
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.ma") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource articlessDS = (JRBeanCollectionDataSource) parameters.get("articles");
			if (articlessDS != null) {
				List<Articles> articles = (List<Articles>) articlessDS.getData();
				if (articles != null & articles.size() >= 1) {
					for (Articles article : articles) {
						historicalReport
							.append(resourceBundle.getString("colname.article") + ": ")
							.append(article.getArticle() + newline)
							.append(resourceBundle.getString("colname.purchase_date") + ": ")
							.append(article.getDispurchasedate() + newline)
							.append(resourceBundle.getString("colname.cost") + ": ")
							.append(article.getDiscost() + newline)
							.append(resourceBundle.getString("colname.currency") + ": ")
							.append(article.getCurrency() + newline)
							.append(resourceBundle.getString("colname.desc") + ": ")
							.append(article.getDescription() + newline);
						historicalReport.append(newline);
					}
				}
			}			
			
			//remarks section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.remarks") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource remarksDS = (JRBeanCollectionDataSource) parameters.get("remarks");
			if (remarksDS != null) {
				List<Remark> remarks = (List<Remark>) remarksDS.getData();
				if (remarks != null & remarks.size() >= 1) {
					for (Remark remark : remarks) {
						historicalReport
							.append(resourceBundle.getString("colname.date_time") + ": ")
							.append(remark.getDispcreatetime() + newline)
							.append(resourceBundle.getString("colname.station") + ": ")
							.append(remark.getAgentStation() + newline)
							.append(resourceBundle.getString("colname.agent") + ": ")
							.append(remark.getAgentUsername() + newline)
							.append(remark.getRemarktext() + newline);
						historicalReport.append(newline);
					}
				}
			}

			//messages section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.messages") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource messagesDS = (JRBeanCollectionDataSource) parameters.get("messages");
			if (messagesDS != null) {
				List<Message> messages = (List<Message>) messagesDS.getData();
				if (messages != null & messages.size() >= 1) {
					for (Message message : messages) {
						historicalReport
						.append(resourceBundle.getString("header.subject") + ": ")
						.append(message.getSubject() + newline)
						.append(resourceBundle.getString("colname.agent") + ": ")
						.append(message.getCreatedBy() + newline)
						.append(resourceBundle.getString("colname.date") + ": ")
						.append(message.getDisp_send_date() + newline)
						.append(resourceBundle.getString("header.station") + ": ")
						.append(message.getStationString() + newline)
						.append(resourceBundle.getString("header.to") + ": ")
						.append(message.getMessageTo() + newline)
						.append(resourceBundle.getString("header.message") + ": ")
						.append(message.getMessage() + newline);
					}
				} else {
					historicalReport.append("N/A" + newline);
				}
			} else {
				historicalReport.append("N/A" + newline);
			}
			
			//tasks section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.tasks") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource tasksDS = (JRBeanCollectionDataSource) parameters.get("tasks");
			if (tasksDS != null) {
				List<Task> tasks = (List<Task>) tasksDS.getData();
				if (tasks != null & tasks.size() >= 1) {
					for (Task task : tasks) {
						historicalReport
							.append(resourceBundle.getString("header.tsk_desc") + ": ")
							.append(task.getDescription() + newline)
							.append(resourceBundle.getString("header.tsk_created_by") + ": ")
							.append(task.getCreatedBy() + newline)
							.append(resourceBundle.getString("header.tsk_status") + ": ")
							.append(task.getStatusString() + newline)
							.append(resourceBundle.getString("header.tsk_priority") + ": ")
							.append(task.getPriorityString() + newline)
							.append(resourceBundle.getString("header.tsk_station") + ": ")
							.append(task.getStationString() + newline)
							.append(resourceBundle.getString("header.tsk_due_date") + ": ")
							.append(task.getDispduedate() + newline)
							.append(resourceBundle.getString("header.tsk_due_time") + ": ")
							.append(task.getDispduetime() + newline)
							.append(resourceBundle.getString("header.tsk_reminder_date") + ": ")
							.append(task.getDispreminderdate() + newline)
							.append(resourceBundle.getString("header.tsk_reminder_time") + ": ")
							.append(task.getDispremindertime() + newline)
							.append(resourceBundle.getString("header.tsk_remarks") + ": ")
							.append(task.getRemarks() + newline);
					}
				} else {
					historicalReport.append("N/A" + newline);
				}				
			} else {
				historicalReport.append("N/A" + newline);
			}
			//interimexpense section
			historicalReport.append(newline);
			historicalReport.append("-- " + resourceBundle.getString("header.interimexpense") + " --");
			historicalReport.append(newline);
			JRBeanCollectionDataSource expenseDS = (JRBeanCollectionDataSource) parameters.get("interimexpense");
			if (expenseDS != null) {
				List<ExpensePayout> expenses = (List<ExpensePayout>) expenseDS.getData();
				if (expenses != null & expenses.size() >= 1) {
					for (ExpensePayout payout : expenses) {
						historicalReport
						.append(resourceBundle.getString("colname.createdate") + ": ")
						.append(payout.getDiscreatedate() + newline)
						.append(resourceBundle.getString("colname.paycode") + ": ")
						.append(payout.getPaycode() + newline)
						.append(resourceBundle.getString("colname.draft") + ": ")
						.append(payout.getDraft() + newline)
						.append(resourceBundle.getString("colname.draftreqdate") + ": ")
						.append(payout.getDisdraftreqdate() + newline)
						.append(resourceBundle.getString("colname.draftpaiddate") + ": ")
						.append(payout.getDisdraftpaiddate() + newline)
						.append(resourceBundle.getString("colname.checkamt") + ": ")
						.append(payout.getDischeckamt() + payout.getCurrency_ID() + newline)
						.append(resourceBundle.getString("colname.voucheramt") + ": ")
						.append(payout.getDisvoucheramt() + newline)
						.append(resourceBundle.getString("colname.mileageamt") + ": ")
						.append(payout.getMileageamt() + newline)
						.append(resourceBundle.getString("header.status") + ": ")
						.append(payout.getResourceValue() + newline)
						.append(resourceBundle.getString("header.approval_deny_date") + ": ")
						.append(payout.getDispapproval_date() + newline);	
						historicalReport.append(newline);
					}
				}
			}			
		}
		
		//logger.error(historicalReport.toString());
		
		String phraseToReplace = ": null" + newline;
		String newPhrase = ": " + newline;
		String result = org.apache.commons.lang.StringUtils.replace(historicalReport.toString(), phraseToReplace, newPhrase);
		//Strip out asterisks
		//result = org.apache.commons.lang.StringUtils.replace(result, "*", " ");
		
		//logger.info(">>>>capacity : " + historicalReport.capacity());
		//logger.info(result);
		
		return result;
	
	}

}