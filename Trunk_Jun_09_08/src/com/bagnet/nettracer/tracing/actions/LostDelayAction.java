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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.ProactiveNotificationBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.LostAndFound_Photo;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.dr.DisputeUtils;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MBRActionUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

public class LostDelayAction extends CheckedAction {
	private static Logger logger = Logger.getLogger(LostDelayAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		ActionMessages errors = new ActionMessages();

		if(session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		IncidentForm theform = (IncidentForm) form;
		
		if(request.getParameter("email_customer") != null)
			theform.setEmail_customer(1);
		else
			theform.setEmail_customer(0);
		
		Agent user = (Agent) session.getAttribute("user");

		if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		if(request.getParameter("historical_report") != null && request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.LD_HISTORICAL));
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

			return mapping.findForward(TracingConstants.LD_HISTORICAL);
		}

		if (!(request.getParameter("changeassignedstation") != null
				&& request.getParameter("changeassignedstation").equals("1") && ((IncidentForm) form)
				.getStationassigned_ID() > 0)
				&& !(request.getParameter("getstation") != null && request.getParameter("getstation").equals("1"))) {
			if (!manageToken(request)) {
				return (mapping.findForward(TracingConstants.INVALID_TOKEN));
			}
		}

		BagService bs = new BagService();
		WorldTracerQueueUtils wq = new WorldTracerQueueUtils();


		//the company specific codes..
		
		
		List codes = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.LOST_DELAY, true, user);
		//add to the loss codes

		request.setAttribute("losscodes", codes);

		request.setAttribute("LOST_DELAY_RECEIPT", Integer.toString(ReportingConstants.LOST_RECEIPT_RPT));
		request.setAttribute("lostdelay", "1");

		if(request.getParameter("express") != null)
			request.setAttribute("express", "1");

		if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG, user))
			theform.setReadonly(1);
		if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, user))
			theform.setAllow_remark_update(1);

		if(user.getStation().getStation_ID() != theform.getStationassigned_ID()) {
			request.setAttribute("cantmatch", "1");
		}

		/** ****************** handle requests ******************** */

		if (request.getParameter("prepopSearch") != null) {
			
			List prepopIncList = IncidentBMO.queryLDIncidentsForTagTrace(theform.getBagTagNumber());
			List prepopOhdList = OhdBMO.queryOhdsForTagTrace(theform.getBagTagNumber());
			
			if (prepopIncList != null) {
				request.setAttribute("prepopIncList", prepopIncList);
			}
			if (prepopOhdList != null) {
				request.setAttribute("prepopOhdList", prepopOhdList);
			}
			
			if (prepopIncList == null && prepopOhdList == null) {
				ActionMessage error = new ActionMessage("prepop.search.nodata");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
			request.setAttribute("prepopulate", new Integer("1"));
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}

		ActionMessage error = null;

		// ajax call to change assigned agent dropdown
		if(MBRActionUtils.actionChangeAssignedStation(theform, request)) {
			return (mapping.findForward(TracingConstants.AJAX_AGENTASSIGNED));
		}
		// AJAX CALL
		if(request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {
			List faultstationlist = null;
			List faultCompanyList = null;
			if (theform.getFaultcompany_id() != null && !theform.getFaultcompany_id().equals("")) {
				// If the user has limited permission, 
				if (UserPermissions.hasLimitedSavePermission(user, theform.getIncident_ID())) {
					faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
					faultCompanyList = new ArrayList();
					faultCompanyList.add(user.getStation().getCompany());
				} else {
					faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
					faultCompanyList = (List) request.getSession().getAttribute("companylistByName");
				}
				request.setAttribute("faultstationlist", faultstationlist);
				request.setAttribute("faultCompanyList", faultCompanyList);
			} 

				if (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals("")) {
					theform.setFaultcompany_id(user.getCompanycode_ID());
					theform.setFaultstation_id(0);
				}

			return (mapping.findForward(TracingConstants.AJAX_FAULTSTATION));
		}

		IncidentUtils.manageIncidentEmailing(user.getStation().getCompany(), request);

		List agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
		request.setAttribute("agentassignedlist", agentassignedlist);
		String form_incident_id = null;
		Dispute myDispute = null;
		if(theform.getIncident_ID() != null) {
			form_incident_id = theform.getIncident_ID();
			request.setAttribute("incident", form_incident_id);
			myDispute = DisputeUtils.getDisputeByIncidentId(form_incident_id);
		}
		
		boolean disputeProcess = false;
		if (myDispute != null) {
			disputeProcess = true;
		} 

		ServletContext sc = getServlet().getServletContext();
		String realpath = sc.getRealPath("/");
		
		if(request.getAttribute("faultCompanyList") == null || request.getAttribute("faultstationlist") == null) {
			if(UserPermissions.hasLimitedSavePermissionByType(user, TracingConstants.LOST_DELAY)) {
				request.setAttribute("faultstationlist", UserPermissions.getLimitedSaveStations(user, TracingConstants.LOST_DELAY));
				ArrayList faultCompanyList = new ArrayList();
				faultCompanyList.add(user.getStation().getCompany());
				request.setAttribute("faultCompanyList", faultCompanyList);
			}
			else {

				if (TracerProperties.isTrue(TracerProperties.SET_DEFAULT_AIRLINE) && (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals(""))) {
					theform.setFaultcompany_id(user.getCompanycode_ID());
				}
				
				request.setAttribute("faultstationlist", TracerUtils.getStationList(theform.getFaultcompany_id()));
				request.setAttribute("faultCompanyList", (List) request.getSession().getAttribute("companylistByName"));
				
			}
		}
		// / confirm matching and unmatching
		error = MBRActionUtils.actionMatching(theform, request, user, realpath);

		if(error != null) {
			if(error.getKey().length() > 0) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}

		if(MBRActionUtils.actionUnMatching(theform, request, user, realpath)) {
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}

		// if got here and one of the claimchecks has tempOHD entered, then
		// alert user
		error = MBRActionUtils.checkOHDEntered(theform, request, user, realpath);
		if(error != null) {
			if(error.getKey().length() > 0) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}

		// close report
		if(MBRActionUtils.actionClose(theform, request, user, errors)) {
			saveMessages(request, errors);

				int currentStatus = -1;
				boolean canSave = UserPermissions.hasIncidentSavePermission(user, form_incident_id);
				if(!canSave) {
					return mapping.findForward(TracingConstants.LD_CLOSE_READ_ONLY);
				}
				if(request.getAttribute("currentstatus") != null) {
					currentStatus = Integer.parseInt((String)request.getAttribute("currentstatus"));
				}

				if(currentStatus == TracingConstants.MBR_STATUS_CLOSED) {
					
					//TODO: if locked and does not have lock permission then they get read only
					
					//if it is closed user can only edit it if they have the permission to edit closed files
					if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_LOSS_CODES, user)) {
						//check to see if there is a dispute on file for this incident
						//if so, display Dispute Resolution Tab, and no Dispute Fault button
						request.setAttribute("disputeProcess", disputeProcess);
						return mapping.findForward(TracingConstants.LD_CLOSE);
					}
					return mapping.findForward(TracingConstants.LD_CLOSE_READ_ONLY);
				}
				//not closed
				else {
					request.setAttribute("disputeProcess", disputeProcess);
					return (mapping.findForward(TracingConstants.LD_CLOSE));
				}
		}

		if (MBRActionUtils.actionDelete(theform, request)) {
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}
		if(MBRActionUtils.actionAdd(theform, request, user)) {
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}


		String loc = null;
		if((loc = MBRActionUtils.actionAddAssoc(theform, request, user)) != null) {
			return (mapping.findForward(loc));
		}
		
		//image file save feature
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
			request.setAttribute("upload", Integer.toString(itemindex));
			return (mapping.findForward(TracingConstants.LD_MAIN));
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
					error = new ActionMessage("error.uploadfile");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.LD_MAIN));
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
				error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}	
		
			return (mapping.findForward(TracingConstants.LD_MAIN));
		}
		//end of new image file save feature code - G

		//TODO: handle dispute fault
		if (request.getParameter("disputeFault") != null) {
			handleDisputeFault(mapping, theform, request, response);
		}

		// save incident
		if(request.getParameter("save") != null || request.getParameter("close") != null
				|| request.getParameter("doclose") != null || request.getParameter("doclosewt") != null
				|| request.getParameter("savetemp") != null || request.getParameter("savetracing") != null
				|| request.getParameter("savetowt") != null || request.getParameter("amendWT") != null ) {

			Incident iDTO = new Incident();
			
			//key to determine whether the action is add new, close, or update
			int saveActionType = ADD_NEW_RECORD;

			// if save for tracing, change status to open,
			if(request.getParameter("savetracing") != null)
				theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_OPEN);

			// if save for temp, change status to temp
			if(request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))
				theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_TEMP);

			// if just save, then don't do anything.

			if(request.getParameter("savetowt") != null && theform.getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				error = new ActionMessage("error.wt_save_closed");
			}
			else {
				//TODO: update the key saveActionType: if no incident id, then it is addnew
				String myIncidentId = theform.getIncident_ID();
				if (!(myIncidentId == null || myIncidentId.equals(""))) {
					if( request.getParameter("close") != null && request.getParameter("doclose") != null) {
						saveActionType = CLOSE_RECORD;
					} else {
						saveActionType = UPDATE_RECORD;
					}
				}
				
				if( request.getParameter("close") != null && request.getParameter("doclose") != null) {
					error = bs.insertIncident(iDTO, theform, TracingConstants.LOST_DELAY, realpath, user, false);
				}
				else {
					error = bs.insertIncident(iDTO, theform, TracingConstants.LOST_DELAY, realpath, user, true);
				}
				if(error == null) {
					if (theform.getOtherSystemInformation() != null && theform.getOtherSystemInformation().trim().length() >0) {
						// Assumes this is new and that we are saving OSI for first time.
						OtherSystemInformation osi = new OtherSystemInformation();
						osi.setIncident(iDTO);
						osi.setInfo(theform.getOtherSystemInformation());
						HibernateUtils.save(osi);
					} 
					if (theform.getPcn_id() != null && theform.getPcn_id().length() > 0) {
						long pcn_id = Long.parseLong(theform.getPcn_id());
						ProactiveNotificationBMO.setIncidentId(pcn_id, iDTO);
					}
					WtqIncidentAction wtq = null;
					if(request.getParameter("savetowt") != null && (iDTO.getWt_id() == null || iDTO.getWt_id().trim().length() == 0) ) {
						wtq = new WtqCreateAhl();
					}
					else if (request.getParameter("amendWT") != null) {
						wtq = new WtqAmendAhl();
					}
					else if (
							(request.getParameter("doclosewt") != null || request.getParameter("close") != null || request.getParameter("doclose") != null)
							
							&& (iDTO.getWtFile() != null && iDTO.getWtFile().getWt_status() != WTStatus.CLOSED)) {
						wtq = new WtqCloseAhl();
					}
					if(wtq != null) {
						wtq.setAgent(user);
						wtq.setCreatedate(TracerDateTime.getGMTDate());
						wtq.setIncident(iDTO);
						WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					}
					else if ( user.getStation().getCompany().getVariable().isAuto_wt_amend() && iDTO.getWtFile() != null && iDTO.getWtFile().getWt_status() != WTStatus.CLOSED ) {
						wtq = new WtqAmendAhl();
						wtq.setAgent(user);
						wtq.setCreatedate(TracerDateTime.getGMTDate());
						wtq.setIncident(iDTO);
						WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					}
				}
			}

			if(error == null) {
				request.setAttribute("lostdelay", "1");
				request.setAttribute("Incident_ID", iDTO.getIncident_ID());
				
				//logger.error(">>>>>>>>>saveActionType (1-addnew; 2-close; 3-update) : " + saveActionType);
				
				if (saveActionType == UPDATE_RECORD) {
					return (mapping.findForward(TracingConstants.UPDATE_FILE_SUCCESS));
				} else if (saveActionType == CLOSE_RECORD) {
					return (mapping.findForward(TracingConstants.CLOSE_FILE_SUCCESS));
				} else {
					return (mapping.findForward(TracingConstants.INSERT_SUCCESS));
				}
			}
			else if(error.getKey().equals("error.unable_to_close_incident")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				//request attribute would have been set to closed initially, but we need to unset it, so the close
				//button will show up on the close form
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
				request.setAttribute("disputeProcess", disputeProcess);
				return (mapping.findForward(TracingConstants.LD_CLOSE));
			}
			else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.LD_MAIN));
			}
		}

		/**
		 * *********** prepopulation for new list *********** or retrieve to
		 * modify incident on html rewrite link
		 */
		String incident = request.getParameter("incident_ID");
		if(incident != null && incident.length() > 0) {
			Incident inc;
			if((inc = bs.findIncidentByID(incident, theform, user, TracingConstants.LOST_DELAY)) == null) {

				error = new ActionMessage("error.noincident");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return (mapping.findForward(TracingConstants.SEARCH_INCIDENT));
			}
			
			session.setAttribute("incidentObj", inc);
			
			//wt  suspend or reinstate
			if(request.getParameter("wtq_reinstate") != null && request.getParameter("wtq_reinstate").trim().length() > 0) {
				WtqReinstateAhl wtq = new WtqReinstateAhl();
				wtq.setAgent(user);
				wtq.setIncident(inc);
				WorldTracerQueueUtils.createOrReplaceQueue(wtq);
			}
			if(request.getParameter("wtq_suspend") != null && request.getParameter("wtq_suspend").trim().length() > 0) {
				WtqSuspendAhl wtq = new WtqSuspendAhl();
				wtq.setAgent(user);
				wtq.setIncident(inc);
				WorldTracerQueueUtils.createOrReplaceQueue(wtq);
			}
			// need to find out if there are any pending world tracer actions
			// for this incident
			boolean result = true;
			if(request.getParameter("wtq_pending_cancel") != null && request.getParameter("wtq_pending_cancel").trim().length() > 0) {
				long wtq_id = Long.parseLong(request.getParameter("wtq_pending_cancel"));
				try {
					result = WorldTracerQueueUtils.cancelPendingAction(wtq_id);
					if(result) {
						request.setAttribute("incident", incident);
						return (mapping.findForward(TracingConstants.LD_MAIN));
					}
				} catch(Exception ex) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.wt_cancel_pending"));	
				}
			}
			WtqIncidentAction pendingAction = WorldTracerQueueUtils.findPendingIncidentAction(incident);
			if(pendingAction != null) {
				if(pendingAction instanceof WtqCreateAhl) {
					request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CREATE);
				}
				else if(pendingAction instanceof WtqAmendAhl) {
					request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_AMEND);
				}
				else if(pendingAction instanceof WtqSuspendAhl) {
					request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_SUSPEND);
				}
				else if(pendingAction instanceof WtqReinstateAhl) {
					request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_REINSTATE);
				}
				else if(pendingAction instanceof WtqCloseAhl) {
					request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CLOSE);
				}
				request.setAttribute("wtq_pending_id", pendingAction.getWt_queue_id());
			}
			
			request.setAttribute("incident", incident);

		} else {
			error = null;
			ArrayList alerrors = new ArrayList();
			// prepopulate new incident fields
			TracerUtils.populateIncident(theform, request, TracingConstants.LOST_DELAY);
			//next two lines solve the problem with Occurred BSO Airline dropdown not default to right value
			IncidentForm thenewform = (IncidentForm)session.getAttribute("incidentForm");
			thenewform.setFaultcompany_id(user.getCompanycode_ID());
			
			request.setAttribute("newform", "1");

			// Attempt to prepopulate the fields from the reservation integration.
			if (MBRActionUtils.prePopulate(request,theform,alerrors,TracingConstants.LOST_DELAY)) {
				if (alerrors.size() > 0) {
					for (int i=0;i<alerrors.size();i++) {
						error = new ActionMessage((String)alerrors.get(i));
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
					saveMessages(request, errors);
					request.setAttribute("prepopulate", new Integer("1"));
				}
				return (mapping.findForward(TracingConstants.LD_MAIN));
			}
			
			if (SpringUtils.getReservationIntegration().isPopulateIncidentFormOn() && request.getParameter("skip_prepopulate") == null && request.getParameter("wt_af_id") == null && request.getParameter("express") == null) {
				request.setAttribute("prepopulate",new Integer("1"));
			}
		}
		return (mapping.findForward(TracingConstants.LD_MAIN));
	}

	private String getReportFile(IncidentForm theform, Agent user, HttpServletRequest request) {

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		HashMap selections = new HashMap();

		if(request.getParameter("all") != null) {
			request.setAttribute("all", "1");
			selections.put("passenger", "true");
			selections.put("passenger_itinerary", "true");
			selections.put("baggage_itinerary", "true");
			selections.put("baggage_check", "true");
			selections.put("claim_check", "true");
			selections.put("baggage_info", "true");
			selections.put("remarks", "true");
			selections.put("messages", "true");
			selections.put("tasks", "true");
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

			if(request.getParameter("passenger_itinerary") != null) {
				request.setAttribute("passenger_itinerary", "1");
				selections.put("passenger_itinerary", "true");
			}
			else
				selections.put("passenger_itinerary", null);

			if(request.getParameter("baggage_itinerary") != null) {
				request.setAttribute("baggage_itinerary", "1");
				selections.put("baggage_itinerary", "true");
			}
			else
				selections.put("baggage_itinerary", null);

			if(request.getParameter("baggage_check") != null) {
				request.setAttribute("baggage_check", "1");
				selections.put("baggage_check", "true");
			}
			else
				selections.put("baggage_check", null);

			if(request.getParameter("claim_check") != null) {
				request.setAttribute("claim_check", "1");
				selections.put("claim_check", "true");
			}
			else
				selections.put("claim_check", null);

			if(request.getParameter("baggage_info") != null) {
				request.setAttribute("baggage_info", "1");
				selections.put("baggage_info", "true");
			}
			else
				selections.put("baggage_info", null);

			if(request.getParameter("remarks") != null) {
				request.setAttribute("remarks", "1");
				selections.put("remarks", "true");
			}
			else
				selections.put("remarks", null);

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
		String reportfile = createReport(selections, type, sc, (String) request.getParameter("incident_ID"), user,
				request);
		return reportfile;

	}

	private static String createReport(HashMap selections, int type, ServletContext sc, String incident_ID, Agent user,
			HttpServletRequest request) {

		IncidentForm form = new IncidentForm();
		BagService bs = new BagService();
		try {
			bs.findIncidentByID(incident_ID, form, user, TracingConstants.MISSING_ARTICLES);
		}
		catch (Exception e) {
		}

		try {
			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			HashMap report_info = new HashMap();
			report_info.put("incident_ID", form.getIncident_ID());
			report_info.put("dispcreatetime", form.getDispcreatetime());
			report_info.put("agentinit", form.getAgentinit());
			report_info.put("stationcreatedcode", form.getStationcreated().getStationcode());
			report_info.put("dispclosedate", form.getDispclosedate());
			report_info.put("status", form.getStatus().getTextDescription(user));
			report_info.put("stationassigned", StationBMO.getStation("" + form.getStationassigned_ID())
					.getStationcode());
			report_info.put("nonrevenue", form.getNonrevenue() == 0 ? "no" : "yes");

			if(form.getReportmethod() == 0)
				report_info.put("reportmethod", "In Person");
			else if(form.getReportmethod() == 1)
				report_info.put("reportmethod", "BSO Phone");
			else if(form.getReportmethod() == 2)
				report_info.put("reportmethod", "Call Center");
			else if(form.getReportmethod() == 3)
				report_info.put("reportmethod", "Internet");
			else if(form.getReportmethod() == 4)
				report_info.put("reportmethod", "Kiosk");

			report_info.put("recordlocator", form.getRecordlocator());
			report_info.put("ticketnumber", form.getTicketnumber());
			parameters.put("report_info", report_info);

			if(selections.get("passenger") != null) {
				List passengers = form.getPassengerlist();
				if(passengers != null && passengers.size() > 0) {
					parameters.put("passengerReport", ReportBMO.getCompiledReport("ma_passenger", sc.getRealPath("/")));
					parameters.put("addressReport", ReportBMO.getCompiledReport("ma_address", sc.getRealPath("/")));
					parameters.put("passenger", new JRBeanCollectionDataSource(passengers));
				}
				else {
					parameters.put("passenger", null);
				}
			}
			else {
				parameters.put("passenger", null);
			}

			if(selections.get("passenger_itinerary") != null) {
				List pass_itineraries = form.getPassItineraryList();
				if(pass_itineraries != null && pass_itineraries.size() > 0) {
					parameters
							.put("passitineraryReport", ReportBMO.getCompiledReport("itinerary", sc.getRealPath("/")));
					parameters.put("passitinerary", new JRBeanCollectionDataSource(pass_itineraries));
				}
				else
					parameters.put("passitinerary", null);
			}
			else {
				parameters.put("passenger_itinerary", null);
			}

			if(selections.get("baggage_itinerary") != null) {

				List bag_itineraries = form.getBagItineraryList();
				if(bag_itineraries != null && bag_itineraries.size() > 0) {
					parameters.put("bagitineraryReport", ReportBMO.getCompiledReport("itinerary", sc.getRealPath("/")));
					parameters.put("bagitinerary", new JRBeanCollectionDataSource(bag_itineraries));
				}
				else
					parameters.put("bagitinerary", null);
			}
			else {
				parameters.put("bagitinerary", null);
			}

			if(selections.get("baggage_check") != null) {

				HashMap bag_check_info = new HashMap();
				bag_check_info.put("numpassengers", "" + form.getNumpassengers());
				bag_check_info.put("numbagchecked", "" + form.getNumbagchecked());
				bag_check_info.put("numbagreceived", "" + form.getNumbagreceived());

				if(form.getCheckedlocation().equalsIgnoreCase("0"))
					bag_check_info.put("checkedlocation", "");
				else if(form.getCheckedlocation().equalsIgnoreCase("1"))
					bag_check_info.put("checkedlocation", "Curb-side");
				else if(form.getCheckedlocation().equalsIgnoreCase("2"))
					bag_check_info.put("checkedlocation", "Ticket Counter");
				else if(form.getCheckedlocation().equalsIgnoreCase("3"))
					bag_check_info.put("checkedlocation", "Gate");
				else if(form.getCheckedlocation().equalsIgnoreCase("4"))
					bag_check_info.put("checkedlocation", "Remote");
				else if(form.getCheckedlocation().equalsIgnoreCase("5"))
					bag_check_info.put("checkedlocation", "Plane-side");
				else if(form.getCheckedlocation().equalsIgnoreCase("6"))
					bag_check_info.put("checkedlocation", "Unchecked");
				else if(form.getCheckedlocation().equalsIgnoreCase("7"))
					bag_check_info.put("checkedlocation", "Kiosk");

				if(form.getCourtesyreport() == 0)
					bag_check_info.put("courtesyreport", "no");
				else if(form.getCourtesyreport() == 1)
					bag_check_info.put("courtesyreport", "yes");
				else
					bag_check_info.put("courtesyreport", "");

				if(form.getTsachecked() == 0)
					bag_check_info.put("tsachecked", "no");
				else if(form.getTsachecked() == 1)
					bag_check_info.put("tsachecked", "yes");
				else
					bag_check_info.put("tsachecked", "");

				if(form.getCustomcleared() == 0)
					bag_check_info.put("customcleared", "no");
				else if(form.getCustomcleared() == 1)
					bag_check_info.put("customcleared", "yes");
				else
					bag_check_info.put("customcleared", "");

				parameters.put("bag_check_info", bag_check_info);
			}
			else {
				parameters.put("bag_check_info", null);
			}

			if(selections.get("claim_check") != null) {

				List claimchecks = form.getClaimchecklist();
				if(claimchecks != null && claimchecks.size() > 0) {
					parameters.put("claimcheckReport", ReportBMO
							.getCompiledReport("ld_claimcheck", sc.getRealPath("/")));
					parameters.put("claimchecks", new JRBeanCollectionDataSource(claimchecks));
				}
				else {
					parameters.put("claimchecks", null);
				}
			}
			else {
				parameters.put("claimchecks", null);
			}

			if(selections.get("baggage_info") != null) {

				List bag_items = form.getItemlist();
				if(bag_items != null && bag_items.size() > 0) {
					parameters.put("baginfoReport", ReportBMO.getCompiledReport("ma_bag_info", sc.getRealPath("/")));
					parameters.put("inventoryReport", ReportBMO
							.getCompiledReport("item_inventory", sc.getRealPath("/")));
					parameters.put("baginfo", new JRBeanCollectionDataSource(bag_items));
				}
				else {
					parameters.put("baginfo", null);
				}
			}
			else {
				parameters.put("baginfo", null);
			}

			if(selections.get("remarks") != null) {

				List remarks = form.getRemarklist();
				if(remarks != null && remarks.size() > 0) {
					parameters.put("remarkReport", ReportBMO.getCompiledReport("remarks", sc.getRealPath("/")));
					parameters.put("remarks", new JRBeanCollectionDataSource(remarks));
				}
				else {
					parameters.put("remarks", null);
				}
			}
			else {
				parameters.put("remarks", null);
			}

			if(selections.get("request") != null) {
				List requestList = OHDUtils.getIncidentRequests(form.getIncident_ID());
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
				List matches = MatchUtils.getMatchWithMBR(form.getIncident_ID(), true);
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

			if(selections.get("messages") != null) {

				List messages = MessageUtils.getReportMessages(incident_ID, 1);
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

				List tasks = TaskUtils.getFileTasks(incident_ID, 1);
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

			List t = new ArrayList();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "ld_history", sc.getRealPath("/"), type);
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
	
	private ActionForward handleDisputeFault(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.error("handleDisputeFault here...");
		return (mapping.findForward(TracingConstants.UPDATE_FILE_SUCCESS));
	}
}