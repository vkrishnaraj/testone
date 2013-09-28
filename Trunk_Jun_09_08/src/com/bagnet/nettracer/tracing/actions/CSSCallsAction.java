package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.dto.CSSStationsDTO;
import com.bagnet.nettracer.tracing.forms.GeneralTaskForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.CSSCallsUtil;

public class CSSCallsAction extends Action{
	
	private static final Logger logger = Logger.getLogger(CSSCallsAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		HttpSession session = request.getSession();
		ActionMessages errors = new ActionMessages();
		GeneralTaskForm theForm = (GeneralTaskForm)form;
		
		Agent user = (Agent) session.getAttribute("user");
		
		if(session.getAttribute("sessionTaskManagerOriginalStation") != null){
			Station originalStation = (Station)session.getAttribute("sessionTaskManagerOriginalStation");
			if(originalStation != null){
				if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, user)) {
					session.setAttribute("cbroStationID", (new Integer(originalStation.getStation_ID())).toString());
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT, user)) {
						user.setStation(originalStation);
					}
				}
			}
		}
		
		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1)
				+ ".do", user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(!UserPermissions
						.hasPermission(
								TracingConstants.SYSTEM_COMPONENT_MANAGE_TASKS,
								user)){
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		
		MorningDutiesTask task = (MorningDutiesTask) session.getAttribute("sessionTaskContainer");
		int day = CSSCallsUtil.ALLDAYS;
		
		if(request.getParameter("tasklist") != null){
			//check first to see if agent has a task currently opened
			MorningDutiesTask hasTask = (MorningDutiesTask)CSSCallsUtil.hasAssignedTask(user);
			if(hasTask != null){
				request.setAttribute("errorMsg", "generaltask.task.in.progress");
				session.setAttribute("sessionTaskContainer", hasTask);
				session.setAttribute("sessionTaskStartTime", new Date());
				request.setAttribute("Incident_ID", hasTask.getIncident().getIncident_ID());
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
			}
			
			if (!"NO".equals(request.getParameter("tasklist"))) {
				boolean hasStations = setSelectedStations(session, theForm);
				if (!hasStations) {
					ActionMessage error = new ActionMessage("error.must.select.station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return getStations(mapping, request, session, user, theForm);
				}
			}
			
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			int currpage = 1;

			currpage = theForm.getCurrpage() != null ? Integer.parseInt(theForm.getCurrpage()) : 0;
			if (theForm.getNextpage() != null && theForm.getNextpage().equals("1"))
				currpage++;
			if (theForm.getPrevpage() != null && theForm.getPrevpage().equals("1"))
				currpage--;
			
			String stationCSVString = (String) session.getAttribute("sessionCSSStations");
			
			List<Incident> incidentList = CSSCallsUtil.getPaginatedList(stationCSVString, rowsperpage, currpage);
			if(incidentList != null && incidentList.size() > 0){
				request.setAttribute("resultlist", incidentList);
			}
			
			long rowcount = CSSCallsUtil.getPaginatedCount(stationCSVString);
			
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			if (currpage + 1 == totalpages)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList al = new ArrayList();
				for (int j = 0; j < totalpages; j++) {
					al.add(Integer.toString(j));
				}
				request.setAttribute("pages", al);
			}
			
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			request.setAttribute("currpage", Integer.toString(currpage));
			return (mapping.findForward(TracingConstants.VIEW_CSS_CALL_TASKS));
		}

		if(request.getParameter("complete") != null){
			String remark = (String) request.getParameter("taskRemark");
			CSSCallsUtil.finishTask(user, task, remark);
			request.setAttribute("gettaskbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("abort") != null){
			String remark = (String) request.getParameter("taskRemark");
			CSSCallsUtil.abortTask(user, task, remark);
			request.setAttribute("gettaskbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("defer") != null){
			String startTime = (String) request.getParameter("taskStart");
			String expireTime = (String) request.getParameter("taskExpire");
			String startDate = (String) request.getParameter("taskStartDate");
			String expireDate = (String) request.getParameter("taskExpireDate");
			String remark = (String) request.getParameter("taskRemark");
			CSSCallsUtil.deferTask(user, task, startDate, startTime, expireDate, expireTime, remark);
			request.setAttribute("gettaskbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("gettask") != null){
			//First check to see if agent has a task already
			MorningDutiesTask hasTask = (MorningDutiesTask)CSSCallsUtil.hasAssignedTask(user);
			if(hasTask != null){
				session.setAttribute("sessionTaskContainer", hasTask);
				session.setAttribute("sessionTaskStartTime", new Date());
				response.sendRedirect("css_calls.do?loadIncident=" + hasTask.getIncident().getIncident_ID());
			}
			
			MorningDutiesTask gtask = null;
			if(request.getParameter("incident") != null){
				//agent is explicitly loading a task from the task list
				gtask = CSSCallsUtil.getTaskByIncidentId(user, request.getParameter("incident"), day);
			} else {
				//agent clicked getTask
				String stationCSVString = (String) session.getAttribute("sessionCSSStations");
				if (stationCSVString == null) {
					ActionMessage error = new ActionMessage("error.must.select.station");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return getStations(mapping, request, session, user, theForm);
				}
				int attempts = 0;
				do{
					gtask = (MorningDutiesTask)CSSCallsUtil.getTask(user, stationCSVString);
					System.out.println("getting task attempt: " + (attempts + 1));
				}while(gtask == null && attempts++ < 2);
			}
			if(gtask != null){
				if(gtask.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_OPEN){
					gtask = (MorningDutiesTask) CSSCallsUtil.startTask(user, gtask);
				}else{
					gtask = null;
				}
			}
			if (gtask == null){
				//redirect error task not available
				request.setAttribute("errorMsg", "generaltask.taskunavailable");
				request.setAttribute("taskmanagerbutton", 1);
				session.removeAttribute("sessionTaskContainer");
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
			}
			session.setAttribute("sessionTaskContainer", gtask);
			session.setAttribute("sessionTaskStartTime", new Date());
			response.sendRedirect("css_calls.do?loadIncident=" + gtask.getIncident().getIncident_ID());
			return null;
		}
		
		if(request.getParameter("loadIncident") != null){
			logger.debug("loading the incident");
			String incident_ID = request.getParameter("loadIncident");
			MorningDutiesTask t = (MorningDutiesTask)session.getAttribute("sessionTaskContainer");
			if(t != null && t.getIncident() != null && t.getIncident().getStationassigned() != null){
				Station station = t.getIncident().getStationassigned();
				if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_VIEW, user)) {
					session.setAttribute("cbroStationID", (new Integer(station.getStation_ID())).toString());
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CBRO_MGMT, user)) {
						session.setAttribute("sessionTaskManagerOriginalStation", user.getStation());
						user.setStation(station);
					}
				}
			}
			response.sendRedirect("searchIncident.do?incident=" + incident_ID);
			return null;
		}
		
		return getStations(mapping, request, session, user, theForm);
	}
	
	private static ActionForward getStations(ActionMapping mapping, HttpServletRequest request, HttpSession session, Agent user, GeneralTaskForm theForm) {
		MorningDutiesTask hasTask = (MorningDutiesTask)CSSCallsUtil.hasAssignedTask(user);
		if(hasTask != null){
			request.setAttribute("errorMsg", "generaltask.task.in.progress");
			session.setAttribute("sessionTaskContainer", hasTask);
			session.setAttribute("sessionTaskStartTime", new Date());
			request.setAttribute("Incident_ID", hasTask.getIncident().getIncident_ID());
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		
		List<CSSStationsDTO> stationList = CSSCallsUtil.getStationList(user);
		if(stationList != null && stationList.size() > 0){
			setCheckboxes(session, stationList);
			theForm.setStationList(stationList);
		}
		
		return (mapping.findForward(TracingConstants.VIEW_CSS_CALL_STATIONS));
	}
	
	private static void setCheckboxes(HttpSession session, List<CSSStationsDTO> stationList) {
		String selectedStations = (String) session.getAttribute("sessionCSSStations");
		if (selectedStations != null) {
			String[] stations = selectedStations.split(",");
			for (int i = 0; i < stations.length; i++) {
				String station = stations[i];
				if (station != null && station.trim().length() > 0) {
					for (CSSStationsDTO dto : stationList) {
						dto.setStation1Checked(dto.isStation1Checked() || station.equals(dto.getStation1Desc()));
						dto.setStation2Checked(dto.isStation2Checked() || station.equals(dto.getStation2Desc()));
						dto.setStation3Checked(dto.isStation3Checked() || station.equals(dto.getStation3Desc()));
					}
				}
			}
		}
	}
	
	private static boolean setSelectedStations(HttpSession session, GeneralTaskForm theForm) {
		session.removeAttribute("sessionCSSStations");
		String css_stations = "";
		boolean hasStations = false;
		if (theForm.getStationList() != null) {
			for (CSSStationsDTO dto : theForm.getStationList()) {
				if (dto.isStation1Checked()) {
					css_stations += dto.getStation1Desc() + ",";
					hasStations = true;
				}
				if (dto.isStation2Checked()) {
					css_stations += dto.getStation2Desc() + ",";
					hasStations = true;
				}
				if (dto.isStation3Checked()) {
					css_stations += dto.getStation3Desc() + ",";
					hasStations = true;
				}
			}
		}
		if (hasStations) {
			session.setAttribute("sessionCSSStations", css_stations);
		}
		return hasStations;
	}
}
