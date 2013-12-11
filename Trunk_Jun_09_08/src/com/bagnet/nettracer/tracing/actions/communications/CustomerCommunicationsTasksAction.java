package com.bagnet.nettracer.tracing.actions.communications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class CustomerCommunicationsTasksAction extends CheckedAction {
	
	private Logger logger = Logger.getLogger(CustomerCommunicationsTasksAction.class);
	
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		if (request.getParameter("gettask") != null) {
			// do some task gettin'
		}

		if (request.getParameter("taskId") != null) {
			String taskIdParam = request.getParameter("taskId");
			if (!userCanModifyTask(taskIdParam, user)) {
				request.getSession().setAttribute("taskMessage", new ActionMessage("message.cust.comm.task.cannot.modify", new Object[] { taskIdParam, user.getUsername() }));
				response.sendRedirect("customerCommunicationsApp.do");
				return null;
			}
			
			String taskStatusParam = request.getParameter("taskStatus");
			if (!validStatusSubmitted(taskStatusParam)) {
				logger.warn("Invalid status submitted for incident activity task: " + taskStatusParam);
				request.getSession().setAttribute("taskMessage", new ActionMessage("message.cust.comm.task.update.failed", new Object[] { taskIdParam }));
				response.sendRedirect("customerCommunicationsApp.do");
				return null;
			}
			
			long taskId = getIntValueFromParam(taskIdParam);
			IncidentActivityTask iat = incidentActivityService.loadTask(taskId);
			if (iat != null && iat.getIncidentActivity() != null) {
				Status status = new Status(getIntValueFromParam(taskStatusParam));
				iat.setStatus(status);
//				iat.getIncidentActivity().setStatus(status);
				incidentActivityService.updateTask(iat);
				
				// if approved, generate a print task too
			}
		}
		
		
				
		String remark = request.getParameter("remark");
		
		
		
		return mapping.findForward(TracingConstants.CUSTOMER_COMMUNICATIONS_PENDING);
	}
	
	private boolean userCanModifyTask(String taskIdParam, Agent user) {
		long taskId = getIntValueFromParam(taskIdParam);
		if (taskId <= 0) {
			logger.error("Could not find a task for the given task Id: " + taskIdParam);
			return false;
		}
		
		if (!taskBelongsToUser(taskId, user)) {
			logger.error("Task with id: " + taskId + " does not belong to: " + user.getUsername());
			return false;
		}
		return true;
	}
	
	private boolean taskBelongsToUser(long taskId, Agent user) {
		if (taskId <= 0) return false;
		IncidentActivityTask iat = incidentActivityService.loadTask(taskId);
		return iat != null && iat.getAssigned_agent() != null && iat.getAssigned_agent().getAgent_ID() == user.getAgent_ID();
	}
	
	private boolean validStatusSubmitted(String taskStatusParam) {
		try {
			int statusId = getIntValueFromParam(taskStatusParam);
			return statusId == TracingConstants.STATUS_CUSTOMER_COMM_APPROVED || statusId == TracingConstants.STATUS_CUSTOMER_COMM_DENIED;
		} catch (NumberFormatException nfe) {
			logger.error("Received invalid value for status: " + taskStatusParam, nfe);
		}
		return false;
	}
	
	private int getIntValueFromParam(String param) {
		int value = 0;
		try {
			value = Integer.valueOf(param).intValue();
		} catch (NumberFormatException nfe) {
			logger.error("Received invalid int value for param: " + param, nfe);
		}
		return value;
	}
}
