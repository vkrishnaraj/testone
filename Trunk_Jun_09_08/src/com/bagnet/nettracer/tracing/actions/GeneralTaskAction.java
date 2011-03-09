package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask;
import com.bagnet.nettracer.tracing.forms.GeneralTaskForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.MorningDutiesUtil;

public class GeneralTaskAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		System.out.println("GeneralTaskAction");
		
		
		HttpSession session = request.getSession();
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
		int minutes = 0;
		int day = getDay(task);
		
		if(request.getParameter("tasklist") != null){
//			System.out.println("complete");
			//check first to see if agent has a task currently opened
			MorningDutiesTask hasTask = (MorningDutiesTask)MorningDutiesUtil.hasAssignedTask(user);
			if(hasTask != null){
				request.setAttribute("errorMsg", "generaltask.task.in.progress");
				session.setAttribute("day", getDay(hasTask));
				session.setAttribute("sessionTaskContainer", hasTask);
				session.setAttribute("sessionTaskStartTime", new Date());
				request.setAttribute("Incident_ID", hasTask.getIncident().getIncident_ID());
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
//				response.sendRedirect("searchIncident.do?incident=" + hasTask.getIncident().getIncident_ID());
			}
			
			Integer i = new Integer(request.getParameter("tasklist"));
			day = i.intValue();
			
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			int currpage = 1;
			int end = 10;
			int pages = 13;

			request.setAttribute("day", day);
			List<Incident> pauselist = MorningDutiesUtil.getPauseList(user, day);
			if(pauselist != null && pauselist.size() > 0){
				request.setAttribute("pauselist", pauselist);
			}
			currpage = theForm.getCurrpage() != null ? Integer.parseInt(theForm.getCurrpage()) : 0;
			if (theForm.getNextpage() != null && theForm.getNextpage().equals("1"))
				currpage++;
			if (theForm.getPrevpage() != null && theForm.getPrevpage().equals("1"))
				currpage--;
			
			List<Incident> incidentList = MorningDutiesUtil.getPaginatedList(user, day, rowsperpage, currpage);
			if(incidentList != null && incidentList.size() > 0){
				request.setAttribute("resultlist", incidentList);
			}
			
			
			long rowcount = MorningDutiesUtil.getCount(user, day);
			
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
//			request.setAttribute("end", end);
			return (mapping.findForward(TracingConstants.VIEW_MORNING_DUTIES));
		}

		if(request.getParameter("complete") != null){
			
			boolean success = true;
//			System.out.println("complete");
			//validate
			String error = MorningDutiesUtil.validateWork(task);
			if(error != null){
				//we have an error message
				
				request.setAttribute("day", day);
				request.setAttribute("errorMsg", error);
				request.setAttribute("Incident_ID", task.getIncident().getIncident_ID());
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
			}
			
			MorningDutiesUtil.closeTask(user, task);
			MorningDutiesUtil.addActivity(user, task, MorningDutiesUtil.ResolutionType.WORKED, getDuration((Date)session.getAttribute("sessionTaskStartTime")));
			//TODO check success
			//if success, go to success page
			if(success){
				request.setAttribute("gettaskbutton", 1);
				request.setAttribute("day", day);
				session.removeAttribute("sessionTaskContainer");
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
			}
			//else, go to error page
		}
		if(request.getParameter("pause") != null){
//			System.out.println("pause");
			MorningDutiesUtil.pauseTask(user, task);
			MorningDutiesUtil.addActivity(user, task, MorningDutiesUtil.ResolutionType.PAUSED, getDuration((Date)session.getAttribute("sessionTaskStartTime")));
			request.setAttribute("day", day);
			request.setAttribute("taskmanagerbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("defer") != null){
//			System.out.println("defer");
			MorningDutiesUtil.deferTask(user, task, new Integer(request.getParameter("defer_time")));
			MorningDutiesUtil.addActivity(user, task, MorningDutiesUtil.ResolutionType.DEFERED, getDuration((Date)session.getAttribute("sessionTaskStartTime")));
			request.setAttribute("day", day);
			request.setAttribute("gettaskbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("abort") != null){
//			System.out.println("abort");
			MorningDutiesUtil.abortTask(user, task);
			MorningDutiesUtil.addActivity(user, task, MorningDutiesUtil.ResolutionType.ABORTED, getDuration((Date)session.getAttribute("sessionTaskStartTime")));
			request.setAttribute("day", day);
			request.setAttribute("taskmanagerbutton", 1);
			session.removeAttribute("sessionTaskContainer");
			return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
		}
		if(request.getParameter("gettask") != null){
			//First check to see if agent has a task already
			MorningDutiesTask hasTask = (MorningDutiesTask)MorningDutiesUtil.hasAssignedTask(user);
			if(hasTask != null){
				session.setAttribute("sessionTaskContainer", hasTask);
				session.setAttribute("sessionTaskStartTime", new Date());
//				response.sendRedirect("searchIncident.do?incident=" + hasTask.getIncident().getIncident_ID());
				response.sendRedirect("GeneralTask.do?loadIncident=" + hasTask.getIncident().getIncident_ID());
			}
			
			MorningDutiesTask gtask = null;
			Integer i = new Integer(request.getParameter("gettask"));
			day = i.intValue();
			if(request.getParameter("incident") != null){
				//agent is explicitly loading a task from the task list
				gtask = MorningDutiesUtil.getTaskByIncidentId(user, request.getParameter("incident"), day);
				if(gtask != null){
					if(gtask.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_OPEN
							|| (gtask.getAssigned_agent().getAgent_ID() == user.getAgent_ID() 
									&& gtask.getStatus().getStatus_ID() == TracingConstants.TASK_MANAGER_PAUSED)){
						//TODO need to lock task
						if(MorningDutiesUtil.lockTask(gtask)!=null){
							Status s = new Status();
							s.setStatus_ID(TracingConstants.TASK_MANAGER_WORKING);
							TaskManagerBMO.saveTask(gtask);
						}else{
							gtask = null;
						}
					}else{
						gtask = null;
					}
				} else {
					//no task found, create new task
					Incident incident = new Incident();
					incident.setIncident_ID(request.getParameter("incident"));
					gtask = (MorningDutiesTask) MorningDutiesUtil.createTask(user, incident, day);
				}
				
			} else {
				//agent clicked getTask
				int attempts = 0;
				do{
					gtask = (MorningDutiesTask)MorningDutiesUtil.getTask(user, day);
					System.out.println("getting task attempt: " + (attempts + 1));
				}while(gtask == null && attempts++ < 2);
			}
			if (gtask == null){
				//TODO redirect error task not availiable
				
				request.setAttribute("errorMsg", "generaltask.taskunavailable");
				request.setAttribute("taskmanagerbutton", 1);
				session.removeAttribute("sessionTaskContainer");
				return (mapping.findForward(TracingConstants.MORNING_DUTIES_UPDATED));
			}
			session.setAttribute("sessionTaskContainer", gtask);
			session.setAttribute("sessionTaskStartTime", new Date());
//			response.sendRedirect("searchIncident.do?incident=" + gtask.getIncident().getIncident_ID());
			response.sendRedirect("GeneralTask.do?loadIncident=" + gtask.getIncident().getIncident_ID());
			return null;
		}
		
		if(request.getParameter("loadIncident") != null){
			System.out.println("loading the incident");
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
		return null;
	}

	private static long getDuration(Date start) {
		long duration = -1;
		if(start != null){
			Date end = new Date();
			duration = end.getTime() - start.getTime();
		}
		return duration;
	}
	
	private static int getDay(MorningDutiesTask task){
		int day = 0;
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask ){
			day = MorningDutiesUtil.TWODAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask ){
			day = MorningDutiesUtil.THREEDAY;
		}
		if(task instanceof com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask ){
			day = MorningDutiesUtil.FOURDAY;
		}
		return day;
	}
}
