package com.bagnet.nettracer.tracing.actions.communications;

import java.util.Date;

import javax.servlet.ServletContext;
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
import com.bagnet.nettracer.tracing.dao.AuthorizationException;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.EmailUtils;
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
		IncidentForm myform=(IncidentForm) form;
		
		// ajax call to return the list of available incident activities
		if (request.getParameter("activityList") != null) {
			incidentActivityService.writeOptionsList(incidentActivityService.getActivityOptions(), response);
			return null;
		}
		
		boolean success = false;
		String command = request.getParameter("command");
		String incidentId = request.getParameter("incident");
		String expenseId = request.getParameter("expense");
		String activity = request.getParameter("activity");
		if (TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION.equals(activity)) {
			response.sendRedirect("customerCommunications.do?incident="+incidentId+"&templateId="+request.getParameter("templateId"));
			return null;
		} else if(TracingConstants.CREATE_SETTLEMENT_ACTIVITY.equals(activity)){
			response.sendRedirect("customerCommunications.do?incident="+incidentId+"&expense="+expenseId+"&templateId="+request.getParameter("templateId"));
			return null;
		} else if(TracingConstants.OUTBOUND_CORRESPONDANCE.equals(activity)){

			success = createActivity(incidentId, activity, user, messages, myform.getOutMessage());
		} else if (TracingConstants.COMMAND_CREATE.equalsIgnoreCase(command)) {
			success = createActivity(incidentId, activity, user, messages, null);
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
	
	private boolean createActivity(String incidentId, String activityId, Agent user, ActionMessages messages, String message) {
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
		if(message!=null && incident.getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL){
			ia.setPublishedDate(new Date());
		}
		
		success = incidentActivityService.save(ia) != 0;
		if (!success) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.create", new Object[] { activity.getDescription() }));
		}

		if(message!=null && !message.isEmpty() && incident.getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL){
			OnlineClaimsDao dao=new OnlineClaimsDao();
			OnlineClaim c=dao.getOnlineClaim(incidentId);
			if (c == null) {
				try {
					String fName="";
					String name="";
					if(incident.getPassenger_list()!=null && incident.getPassenger_list().get(0)!=null){
						Passenger p=incident.getPassenger_list().get(0);
						fName=p.getFirstname();
						name=p.getLastname();
					}
					c = dao.createOnlineClaim(incidentId, null, fName, name);
	
					dao.saveOnlineClaimWsUseOnly(c, incidentId, null, null);
					
				} catch (AuthorizationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			OCMessage newmessage=new OCMessage();
			newmessage.setDateCreated(new Date());
			newmessage.setMessage(message);
			newmessage.setUsername(user.getFirstname()+" "+user.getLastname());
			newmessage.setIncAct(ia);
			newmessage.setClaim(c);
			newmessage.setPublish(true);
			newmessage.setStatusId(TracingConstants.OC_STATUS_PUBLISHED);
			success=dao.saveMessage(newmessage);
			if (!success) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.create.message", new Object[] { activity.getDescription() }));
			} else {

				ServletContext sc = getServlet().getServletContext();
				String realpath = sc.getRealPath("/");
				try{
					EmailUtils.sendIncidentActivityEmail(ia, realpath);
				} catch(InsufficientInformationException iie){
					iie.printStackTrace();
					logger.error("Failed to email customer about communication for IncidentActivity with id: "+ ia.getId());
				}
			}
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
