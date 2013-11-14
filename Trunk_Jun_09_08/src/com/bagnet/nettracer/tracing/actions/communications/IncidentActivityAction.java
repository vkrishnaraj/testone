package com.bagnet.nettracer.tracing.actions.communications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class IncidentActivityAction extends CheckedAction {
	
	private Logger logger = Logger.getLogger(IncidentActivityAction.class);
	
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		ActionMessages messages = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		// ajax call to return the list of available incident activities
		if (request.getParameter("activityList") != null) {
			incidentActivityService.writeOptionsList(incidentActivityService.getActivityOptions(), response);
			return null;
		}
		
		boolean success = false;
		String command = request.getParameter("command");
		String incidentId = request.getParameter("incident");
		String activity = request.getParameter("activity");
		if (TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION.equals(activity)) {
			response.sendRedirect("customerCommunications.do?incident="+incidentId+"&templateId="+request.getParameter("templateId"));
			return null;
		} else if (TracingConstants.COMMAND_CREATE.equalsIgnoreCase(command)) {
			success = createActivity(incidentId, activity, user, messages);
		} else if (TracingConstants.COMMAND_DELETE.equalsIgnoreCase(command)
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_DELETE, user)) {
			success = deleteActivity(request.getParameter("activity"), incidentId, messages);
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		response.sendRedirect("searchIncident.do?incident=" + incidentId + "#activities");
		return null;
	}
	
	private boolean createActivity(String incidentId, String activityId, Agent user, ActionMessages messages) {
		boolean success = false;
		
		Activity activity = getActivityFromParam(activityId, messages);
		if (activity == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.find.activity"));
			return success;
		}
		
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		if (incident == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.find.incident", new Object[] { incidentId }));
			return success;
		}
		
		IncidentActivity ia = DomainUtils.createIncidentActivity(incident, activity, user);
		success = incidentActivityService.save(ia) != 0;
		if (!success) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.create", new Object[] { activity.getDescription() }));
		}
		return success;
	}
	
	private boolean deleteActivity(String activityId, String incidentId, ActionMessages messages) {
		boolean success = false;
		try {
			long iaId = Long.parseLong(activityId);
			if (incidentActivityService.activityBelongsToIncident(iaId, incidentId)) {
				incidentActivityService.delete(iaId);
			}
		} catch (NumberFormatException nfe) {
			logger.error("Could not delete activity with id: " + activityId, nfe);
		}
		return success;
	}
	
	private Activity getActivityFromParam(String activityIdParam, ActionMessages messages) {
		Activity activity = null;
		try {
			activity = incidentActivityService.getActivity(activityIdParam);
		} catch (NumberFormatException nfe) {
			logger.error("Invalid activity id: " + activityIdParam, nfe);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalid.activity.id"));
		}
		return activity;
	}
	
}
