/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Priority;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.forms.TaskForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for managing
 * all the tasks setup by the agent. Tasks can be independent or associated with
 * various report types. Tasks can be created, viewed, and modified. Also,
 * allows search capability to tasks.
 * 
 * @author Ankur Gupta
 */
public class ManageTask extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		// check session and user validity
		TracerUtils.checkSession(session);
		if (session.getAttribute("user") == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		Agent user = (Agent) session.getAttribute("user");

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OTHER_TASKS, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
				

		//	 menu highlite
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_OTHER_TASKS);

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		//Obtain all the relevant station usernames
		List stationAgents = AdminUtils.getAgentsByStation("" + agent_station.getStation_ID(),
				"username", null, 0, 0);

		request.setAttribute("stationAgents", stationAgents);

		String sort = request.getParameter("sort");

		if (sort != null && sort.length() > 0) request.setAttribute("sort", sort);

		ActionMessages errors = new ActionMessages();
		TaskForm dForm = (TaskForm) form;

		String file_ref_number = request.getParameter("file_ref_number");
		String assigned_to = dForm.getAssigned_to_id();
		String file_type = request.getParameter("file_type");

		if (request.getParameter("file") != null) {
			file_ref_number = request.getParameter("file");
			dForm.setFile_ref_number(file_ref_number);
		}

		if (request.getParameter("type") != null) {
			file_type = request.getParameter("type");
			request.setAttribute("file_type",file_type);
		}

		List statusList = TaskUtils.getTaskStatusList(user.getCurrentlocale());
		request.setAttribute("task_status_list", statusList);

		//Task is ready to be saved.
		if (request.getParameter("done") != null) {
			ArrayList al = new ArrayList();
			al.add(new LabelValueBean("On-hand", "0"));
			al.add(new LabelValueBean("Report", "1"));

			Task task = null;
			if (request.getParameter("task_id") != null && (!request.getParameter("task_id").equals("0"))) {
				task = TaskUtils.getTask(request.getParameter("task_id"));
				task.setTask_id(Integer.parseInt(request.getParameter("task_id")));
			} else {
				task = new Task();
				task.setAssigningAgent(user);
				task.setStation(user.getStation());
			}

			String assignedtoagent_id = request.getParameter("assigned_to_id");

			if (assignedtoagent_id == null || assignedtoagent_id.equals("")) {
				task.setAssignedTo(null);
			} else {
				Agent agent = AdminUtils.getAgent(assignedtoagent_id);
				task.setAssignedTo(agent);
			}

			task.set_DATEFORMAT(user.getDateformat().getFormat());
			task.set_TIMEFORMAT(user.getTimeformat().getFormat());
			task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));

			Date duedate = null, reminderdate = null;

			String dispduedate = request.getParameter("dispduedate");
			String dispduetime = request.getParameter("dispduetime");

			String dispreminderdate = request.getParameter("dispreminderdate");
			String dispremindertime = request.getParameter("dispremindertime");

			if (dispduedate != null && !dispduedate.equals("")) {
				if (dispduetime != null && !dispduetime.equals("")) {
					duedate = DateUtils.convertToGMTDate(dispduedate + " " + dispduetime, user
							.getDateformat().getFormat()
							+ " " + user.getTimeformat().getFormat());
				} else {
					duedate = DateUtils.convertToGMTDate(dispduedate, user.getDateformat().getFormat());
				}
				task.setDue_date_time(duedate);
			}

			if (dispreminderdate != null && !dispreminderdate.equals("")) {
				if (dispremindertime != null && !dispremindertime.equals("")) {
					reminderdate = DateUtils.convertToGMTDate(dispreminderdate + " " + dispremindertime, user
							.getDateformat().getFormat()
							+ " " + user.getTimeformat().getFormat());
				} else {
					reminderdate = DateUtils.convertToGMTDate(dispreminderdate, user.getDateformat()
							.getFormat());
				}
				task.setReminder_date_time(reminderdate);
			}

			task.setDescription(request.getParameter("description"));
			task.setFile_ref_number(request.getParameter("file_ref_number"));
			task.setFile_type(Integer.parseInt(request.getParameter("file_type")));
			Priority p = new Priority();
			p.setPriority_ID(Integer.parseInt(request.getParameter("priority.priority_ID")));
			task.setPriority(p);

			Status s = new Status();
			s.setStatus_ID(Integer.parseInt(request.getParameter("status.status_ID")));
			task.setStatus(s);

			task.setRemarks(request.getParameter("remarks"));

			if (task.getFile_ref_number() != null && !task.getFile_ref_number().equals("")
					&& task.getFile_type() == -1) {
				//error should select a file type
				ActionMessage error = new ActionMessage("error.missing.filetype");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);

				request.setAttribute("task", task);
				request.setAttribute("typelist", al);

				return mapping.findForward(TracingConstants.VIEW_TASK);

			}
			if (task.getFile_ref_number().equals("")) task.setFile_type(-1);

			//check if the file is on hand or incident
			if (Integer.parseInt(request.getParameter("file_type")) == 0) {
				//check if the file is on hand number.
				OHD ohd = OHDUtils.getOHD(task.getFile_ref_number());
				if (ohd == null) {
					ActionMessage error = new ActionMessage("error.incorrect.filetype");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);

					request.setAttribute("task", task);
					request.setAttribute("typelist", al);

					return mapping.findForward(TracingConstants.VIEW_TASK);
				}
			}

			//check if the file is on hand or incident
			if (Integer.parseInt(request.getParameter("file_type")) == 1) {
				//check if the file is on hand number.
				IncidentBMO bmo = new IncidentBMO();
				Incident incident = bmo.findIncidentByID(task.getFile_ref_number());
				if (incident == null) {
					ActionMessage error = new ActionMessage("error.incorrect.filetype");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);

					request.setAttribute("task", task);
					request.setAttribute("typelist", al);

					return mapping.findForward(TracingConstants.VIEW_TASK);
				}
			}

			//check if the reminder date is greater than due-date
			if (reminderdate != null && duedate != null && reminderdate.compareTo(duedate) > 0) {
				ActionMessage error = new ActionMessage("error.greater.reminder");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);

				request.setAttribute("task", task);
				request.setAttribute("typelist", al);

				return mapping.findForward(TracingConstants.VIEW_TASK);

			}

			if (task.getTask_id() == 0) HibernateUtils.saveNew(task);
			else HibernateUtils.save(task);

			//reset the file_ref_number and assigned_to
			dForm.setAssigned_to_id("");
			dForm.setFile_ref_number("");

			file_ref_number = "";
			assigned_to = "";
			file_type = "";

		}

		if (request.getParameter("delete1") != null && request.getParameter("task_ids") != null
				&& request.getParameter("task_ids").length() > 0) {

			String tasks = (String) request.getParameter("task_ids");
			StringTokenizer st = new StringTokenizer(tasks, ",");
			while (st.hasMoreTokens()) {
				Task task = TaskUtils.getTask(st.nextToken());
				Status s = new Status();
				s.setStatus_ID(TracingConstants.TASK_STATUS_DELETED);
				task.setStatus(s);
				HibernateUtils.save(task);
			}

			//HibernateUtils.delete(message);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("task.delete.success"));
			saveMessages(request, errors);
		}

		//Create a new task.
		if (request.getParameter("create") != null) {
			ArrayList al = new ArrayList();
			al.add(new LabelValueBean("On-hand", "0"));
			al.add(new LabelValueBean("Report", "1"));

			Task task = new Task();
			task.setAssigningAgent(user);
			task.setStation(user.getStation());

			task.setPriority(TaskUtils.getPriority(user.getCurrentlocale(), "Medium"));
			task.setStatus(TaskUtils.getStatusByDesc(user.getCurrentlocale(), "Not Started"));

			if (request.getParameter("file_ref_number") != null
					&& !request.getParameter("file_ref_number").equals("")) {
				task.setFile_ref_number(request.getParameter("file_ref_number"));
				if (request.getParameter("file_type") != null) task.setFile_type(Integer.parseInt(request
						.getParameter("file_type")));
			} else {
				task.setFile_type(-1);
			}
			request.setAttribute("task", task);
			request.setAttribute("typelist", al);

			return mapping.findForward(TracingConstants.VIEW_TASK);
		}

		//View/Modify existing task
		if (request.getParameter("edit_task_id") != null) {
			Task task = TaskUtils.getTask(request.getParameter("edit_task_id"));
			task.set_DATEFORMAT(user.getDateformat().getFormat());
			task.set_TIMEFORMAT(user.getTimeformat().getFormat());
			task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
					.getTimezone()));

			ArrayList al = new ArrayList();
			al.add(new LabelValueBean("On-hand", "0"));
			al.add(new LabelValueBean("Report", "1"));

			request.setAttribute("task", task);
			request.setAttribute("typelist", al);
			return mapping.findForward(TracingConstants.VIEW_TASK);
		}

		int task_status_id = Task.ALL_TASKS;
		if (request.getParameter("task_status") != null) task_status_id = Integer.parseInt(request
				.getParameter("task_status"));
		else if (mapping.getParameter() != null && mapping.getParameter().equals("active")) {
			task_status_id = Task.ACTIVE_TASKS;
			dForm.setTask_status(Integer.toString(task_status_id));
		}

		int taskListCount = ((Long) TaskUtils.getTasks(
				agent_station.getStation_ID(), assigned_to, file_ref_number, task_status_id,
				request.getParameter("s_time"), request.getParameter("e_time"), user, 0, 0, sort,
				file_type, true).get(0)).intValue();

		if (taskListCount > 0) {
			/** ************ pagination ************* */
			int rowcount = -1;
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			int totalpages = 0;

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			if (request.getParameter("nextpage") != null && request.getParameter("nextpage").equals("1")) currpage++;
			if (request.getParameter("prevpage") != null && request.getParameter("prevpage").equals("1")) currpage--;

			request.setAttribute("currpage", Integer.toString(currpage));

			// get row count
			rowcount = taskListCount;

			// find out total pages
			totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}

			//request list
			List taskList = TaskUtils.getTasks(agent_station.getStation_ID(), assigned_to,
					file_ref_number, task_status_id, request.getParameter("s_time"), request
							.getParameter("e_time"), user, rowsperpage, currpage, sort, file_type, false);

			if (taskList != null) {
				for (Iterator i = taskList.iterator(); i.hasNext();) {
					Task task = (Task) i.next();
					task.set_DATEFORMAT(user.getDateformat().getFormat());
					task.set_TIMEFORMAT(user.getTimeformat().getFormat());
					task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
							user.getDefaulttimezone()).getTimezone()));
				}

				if (currpage + 1 == totalpages) request.setAttribute("end", "1");
				if (totalpages > 1) {
					ArrayList al = new ArrayList();
					for (int i = 0; i < totalpages; i++) {
						al.add(Integer.toString(i));
					}
					request.setAttribute("pages", al);
				}
			}
			/** ************ end of pagination ************* */
			request.setAttribute("taskList", taskList);
		} else {
			int rowsperpage = request.getParameter("rowsperpage") != null ? Integer.parseInt(request
					.getParameter("rowsperpage")) : TracingConstants.ROWS_PER_PAGE;
			if (rowsperpage < 1) rowsperpage = TracingConstants.ROWS_PER_PAGE;
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			int currpage = request.getParameter("currpage") != null ? Integer.parseInt(request
					.getParameter("currpage")) : 0;
			request.setAttribute("currpage", Integer.toString(currpage));
		}
		return mapping.findForward(TracingConstants.VIEW_TASK_LIST);
	}
}