package com.bagnet.nettracer.tracing.actions.communications;

import java.io.IOException;

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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
//import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao; Not related to NT-740
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
//import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile; Not related to NT-740
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
//import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException; Not related to NT-740
//import com.bagnet.nettracer.tracing.service.DocumentService; Not related to NT-740
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class CustomerCommunicationsTasksAction extends CheckedAction {
	
	private Logger logger = Logger.getLogger(CustomerCommunicationsTasksAction.class);
//	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN); Not related to NT-740
	
	private final Status STATUS_PENDING_PRINT = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_PRINT);
	private final Status STATUS_PENDING_WP = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_WP);
	private final Status STATUS_PENDING = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING);
	private final Status STATUS_FRAUD_REVIEW = new Status(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW);
	private final Status STATUS_SUPERVISOR_REVIEW = new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW);
	private final Status STATUS_APPROVED = new Status(TracingConstants.STATUS_CUSTOMER_COMM_APPROVED);
	private final Status STATUS_DENIED = new Status(TracingConstants.STATUS_CUSTOMER_COMM_DENIED);
	
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		ActionMessages messages = new ActionMessages();
		boolean success = false;
		
		Agent user = (Agent) session.getAttribute("user");
		String returnForward=TracingConstants.CUSTOMER_COMMUNICATIONS_PENDING;
		if (user == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user) 
				|| UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REVIEW, user)
				|| UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SUPERVISOR_REVIEW, user))) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		if (request.getParameter("gettask") != null) {
			Status s=null;
			if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user)){
				s=STATUS_PENDING;
			}
			
			if(request.getParameter("fraudReview")!=null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REVIEW, user)){
				s=STATUS_FRAUD_REVIEW;
			}

			if(request.getParameter("supervisorReview")!=null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SUPERVISOR_REVIEW, user)){
				s=STATUS_SUPERVISOR_REVIEW;
			}
			
			IncidentActivityTask task = incidentActivityService.getAssignedTask(user,s);
			if (task != null) {
				
				return displayTask(task, response);
				
			} else if (request.getParameter("communicationsId") != null) {
				
				String communicationsIdParam = request.getParameter("communicationsId");
				task = incidentActivityService.startTask((long) getIntValueFromParam(communicationsIdParam), user,s);
				if (task != null) {
					return displayTask(task, response);
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.cust.comm.task.unable.to.assign", new Object[] { communicationsIdParam, user.getUsername() }));
				}
				
			} else {
				int attempts = 0;
				do{
					task = incidentActivityService.getTask(user,s);
					System.out.println("getting task attempt: " + (attempts + 1));
				} while(task == null && attempts++ < 2);
				
				if (task != null) {
					return displayTask(task, response);
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.cust.comm.task.none.available"));
				}
			}
		} else if (request.getParameter("taskId") != null) {
			String taskIdParam = request.getParameter("taskId");
			String taskStatusParam = request.getParameter("taskStatus");
			
			int statusId=getIntValueFromParam(taskStatusParam);
			if(statusId == TracingConstants.FINANCE_STATUS_FRAUD_REJECTED || statusId == TracingConstants.FINANCE_STATUS_FRAUD_APPROVED ){
				returnForward=TracingConstants.FRAUD_REVIEW;
			} else if(statusId == TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED || statusId == TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED){
				returnForward=TracingConstants.SUPERVISOR_REVIEW;
			}
			
			if (!userCanModifyTask(taskIdParam, user)) {
				request.getSession().setAttribute("taskMessage", new ActionMessage("message.cust.comm.task.cannot.modify", new Object[] { taskIdParam, user.getUsername() }));
				if(returnForward.equals(TracingConstants.FRAUD_REVIEW)){
					response.sendRedirect("fraudReview.do");
					return null;
				} else if (returnForward.equals(TracingConstants.SUPERVISOR_REVIEW)){
					response.sendRedirect("supervisorReview.do");
					return null;
				}
				response.sendRedirect("customerCommunicationsApp.do");
				return null;
			}
			
			if (!validStatusSubmitted(taskStatusParam)) {
				logger.warn("Invalid status submitted for incident activity task: " + taskStatusParam);
				request.getSession().setAttribute("taskMessage", new ActionMessage("message.cust.comm.task.update.failed", new Object[] { taskIdParam }));
				if(returnForward.equals(TracingConstants.FRAUD_REVIEW)){
					response.sendRedirect("fraudReview.do");
					return null;
				} else if (returnForward.equals(TracingConstants.SUPERVISOR_REVIEW)){
					response.sendRedirect("supervisorReview.do");
					return null;
				}
				response.sendRedirect("customerCommunicationsApp.do");
				return null;
			}
			
			long incidentActivityTaskId = getIntValueFromParam(taskIdParam);
			success = handleTask(incidentActivityTaskId, 
					(statusId == TracingConstants.STATUS_CUSTOMER_COMM_APPROVED 
						|| statusId == TracingConstants.FINANCE_STATUS_FRAUD_APPROVED
						|| statusId == TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED));
			if (success) {
				success = handleRemark(incidentActivityTaskId, (String) request.getParameter("remark"), user);
			}
		}

		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		if(returnForward.equals(TracingConstants.FRAUD_REVIEW)){
			response.sendRedirect("fraudReview.do");
			return null;
		} else if (returnForward.equals(TracingConstants.SUPERVISOR_REVIEW)){
			response.sendRedirect("supervisorReview.do");
			return null;
		}
		response.sendRedirect("customerCommunicationsApp.do");
		return null;
	}
	
	private boolean handleTask(long incidentActivityTaskId, boolean approved) {
		incidentActivityService.closeTask(incidentActivityTaskId);
		IncidentActivityTask iat = incidentActivityService.loadTask(incidentActivityTaskId);
		if (iat != null) {
			iat.getIncidentActivity().setApprovalAgent(iat.getAssigned_agent());
			return approved ? handleApproveTask(iat) : handleRejectTask(iat);
		}
		logger.error("Failed to load task with id: " + incidentActivityTaskId);
		return false;
	}
	
	private boolean handleApproveTask(IncidentActivityTask iat) {
		IncidentActivity ia=iat.getIncidentActivity();
		if (!incidentActivityService.createTask(ia, STATUS_APPROVED, iat.getAssigned_agent(), false)) {
			logger.error("Failed to create an approval task for IncidentActivity: " + iat.getTask_id());
		}

		if(iat.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			return incidentActivityService.createTask(ia, new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW));
		} else if (iat.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
			return incidentActivityService.createTask(ia, new Status(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT));
		}
		
		switch(ia.getCustCommId()) {
			case TracingConstants.CUST_COMM_POSTAL_MAIL:
				return incidentActivityService.createTask(ia, STATUS_PENDING_PRINT);
			case TracingConstants.CUST_COMM_WEB_PORTAL:
				/* Not related to NT-740
				try {
					OCFile file=documentService.publishDocument(ia);
					if(file!=null){
						OnlineClaimsDao dao=new OnlineClaimsDao();
						dao.saveFile(file);
					}
				} catch (InsufficientInformationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
				return incidentActivityService.createTask(ia, STATUS_PENDING_WP);
			default:
				logger.error("Invalid value found for customer communication method id: " + ia.getCustCommId());
				return false;
		}
	}
	
	private boolean handleRejectTask(IncidentActivityTask iat) {
		IncidentActivity ia=iat.getIncidentActivity();
		if(iat.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			return incidentActivityService.createTask(ia, new Status(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED), ia.getAgent());
		} else if(iat.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
			return incidentActivityService.createTask(ia, new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED), ia.getAgent());
		}
		return incidentActivityService.createTask(ia, STATUS_DENIED, ia.getAgent());
	}
	
	private boolean handleRemark(long incidentActivityTaskId, String remark, Agent madeBy) {
		if (remark == null || remark.isEmpty()) return true;
		
		incidentActivityService.closeTask(incidentActivityTaskId);
		IncidentActivityTask iat = incidentActivityService.loadTask(incidentActivityTaskId);
		if (iat != null) {
			return incidentActivityService.createIncidentActivityRemark(remark, iat.getIncidentActivity(), madeBy);
		}
		return false;
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
		if (taskStatusParam == null || taskStatusParam.isEmpty()) return false;
		try {
			int statusId = getIntValueFromParam(taskStatusParam);
			return statusId == TracingConstants.STATUS_CUSTOMER_COMM_APPROVED || statusId == TracingConstants.STATUS_CUSTOMER_COMM_DENIED
					|| statusId == TracingConstants.FINANCE_STATUS_FRAUD_REJECTED || statusId == TracingConstants.FINANCE_STATUS_FRAUD_APPROVED 
					|| statusId == TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED || statusId == TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED;
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
	
	private ActionForward displayTask(IncidentActivityTask iat, HttpServletResponse response) throws IOException {
		String addon="";
		if(iat.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			addon="&isFraud=1";
		}
		response.sendRedirect("customerCommunications.do?command=" + TracingConstants.COMMAND_EDIT + "&communicationsId=" + iat.getIncidentActivity().getId() + "&approvalTask=true"+addon);
		return null;		
	}
	
}
