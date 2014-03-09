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
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.inbound.InboundTasksUtils;

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
			long incidentActivityId = createActivity(incidentId, activity, user, messages, myform.getOutMessage());
			success = incidentActivityId > 0;
			if(success){
				IncidentActivity ia = incidentActivityService.load(incidentActivityId);
				success = createOCMessage(user, ia, messages, myform.getOutMessage());
			}
			
		} else if (TracingConstants.COMMAND_CREATE.equalsIgnoreCase(command)) {
			long incidentActivityId = createActivity(incidentId, activity, user, messages, null);
			success = incidentActivityId > 0;
			if(success){
				IncidentActivity ia = incidentActivityService.load(incidentActivityId);
				if(success){
					createInboundTask(user, ia);
				}
			}			
		} else if (TracingConstants.COMMAND_DELETE.equalsIgnoreCase(command)
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_DELETE, user)) {
			
			long activityId = StringUtils.getLong(request.getParameter("activity"));
			success = deleteActivity(activityId, incidentId, messages);
			if(success){
				InboundTasksUtils.closeTaskByIncidentActivityId(activityId, user);
			}
			
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		response.sendRedirect("searchIncident.do?incident=" + incidentId + "#activities");
		return null;
	}
	
	private long createActivity(String incidentId, String activityId, Agent user, ActionMessages messages, String message) {
		long incidentActivityID = -1;
		
		Activity activity = getActivityFromParam(activityId, messages);
		if (activity == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.find.activity"));
			return -1;
		}
		
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		if (incident == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.find.incident", new Object[] { incidentId }));
			return -1;
		}
		
		IncidentActivity ia = DomainUtils.createIncidentActivity(incident, activity, user);
		if(message!=null && incident.getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL){
			ia.setPublishedDate(new Date());
		}
		
		incidentActivityID = incidentActivityService.save(ia);
		if (incidentActivityID <= 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.create", new Object[] { activity.getDescription() }));
		}
		return incidentActivityID;
	}
	
	private boolean createOCMessage(Agent user, IncidentActivity ia, ActionMessages messages, String message){
		boolean success = false;
		if(message!=null && !message.isEmpty() && ia != null && ia.getIncident() != null){
			OnlineClaimsDao dao=new OnlineClaimsDao();
			OnlineClaim c=dao.getOnlineClaim(ia.getIncident().getIncident_ID());
			if (c == null) {
				try {
					String fName="";
					String name="";
					if(ia.getIncident().getPassenger_list()!=null && ia.getIncident().getPassenger_list().get(0)!=null){
						Passenger p=ia.getIncident().getPassenger_list().get(0);
						fName=p.getFirstname();
						name=p.getLastname();
					}
					c = dao.createOnlineClaim(ia.getIncident().getIncident_ID(), null, fName, name);
	
					dao.saveOnlineClaimWsUseOnly(c, ia.getIncident().getIncident_ID(), null, null);
					
				} catch (AuthorizationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			OCMessage newmessage=new OCMessage();
			newmessage.setDateCreated(new Date());
			newmessage.setMessage(message.replace("\r\n", "<br>"));
			newmessage.setUsername(user.getFirstname()+" "+user.getLastname());
			newmessage.setIncAct(ia);
			newmessage.setClaim(c);
			if(ia.getIncident().getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL) {
				newmessage.setPublish(true);
				newmessage.setStatusId(TracingConstants.OC_STATUS_PUBLISHED);
			} else {
				newmessage.setPublish(false);
				newmessage.setStatusId(TracingConstants.OC_STATUS_UNPUBLISHED);
			}
			
			success=dao.saveMessage(newmessage);
			
			if (!success) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.unable.to.create.message", new Object[] { ia.getActivity().getDescription() }));
			} else if(ia.getIncident().getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL
					&& TracingConstants.OUTBOUND_CORRESPONDANCE.equals(ia.getActivity().getCode())) {

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
	
	private void createInboundTask(Agent user, IncidentActivity ia){
		String code = ia.getActivity().getCode();
		if(TracingConstants.ACTIVITY_CODE_INBOUND_CURE.equals(code) ||
		   TracingConstants.ACTIVITY_CODE_INBOUND_FAX.equals(code) ||
		   TracingConstants.ACTIVITY_CODE_INBOUND_MAIL.equals(code) ||
		   TracingConstants.ACTIVITY_CODE_INBOUND_PORTAL.equals(code) ||
		   TracingConstants.ACTIVITY_CODE_SECONDARY_CORRESPONDENCE.equals(code)){
			InboundTasksUtils.createInboundTask(ia.getIncident(), user, ia.getActivity(), ia.getId());
		}
	}

	private boolean deleteActivity(long activityId, String incidentId, ActionMessages messages) {
		boolean success = false;
		if (incidentActivityService.activityBelongsToIncident(activityId, incidentId)) {
			success = incidentActivityService.delete(activityId);
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
