package com.bagnet.nettracer.tracing.actions.communications;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.dao.impl.IncidentActivityDAOImpl;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.exceptions.InvalidDocumentTypeException;
import com.bagnet.nettracer.tracing.factory.TemplateAdapterFactory;
import com.bagnet.nettracer.tracing.forms.communications.CustomerCommunicationsForm;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.service.TemplateService;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.EmailUtils;
import com.bagnet.nettracer.tracing.utils.ExpenseUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.inbound.InboundTasksUtils;

public class CustomerCommunicationsAction extends CheckedAction {

	private final String REQUEST_TEMPLATE_LIST = "templateList";
	private final String REQUEST_PREVIEW_DOCUMENT = "preview_document";
	private final String REQUEST_VIEW_SAMPLE_PRINTOUT = "view_sample_printout";
	private final Status STATUS_PENDING = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING);
	private final Status STATUS_FRAUD_REVIEW = new Status(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW);
	private final Status STATUS_SUPERVISOR_REVIEW = new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW);
	private final Status STATUS_AWAITING_DISBURSEMENT = new Status(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT);
	private final Status STATUS_PENDING_PRINT = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_PRINT);
	private final Status STATUS_PENDING_WP = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_WP);
	
	private Logger logger = Logger.getLogger(CustomerCommunicationsAction.class);
	
	
	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	private TemplateService templateService = (TemplateService) SpringUtils.getBean(TracingConstants.TEMPLATE_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		ActionMessages messages = new ActionMessages();
		Agent user = (Agent) session.getAttribute("user");

		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, user)) {
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		}
		
		CustomerCommunicationsForm ccf = (CustomerCommunicationsForm) form;
		if (isAjaxRequest(request)) {
			return handleAjaxRequest(ccf, user, request, response, mapping);
		}

		boolean success = true;
		
		if (request.getParameter("taskId") != null) {
			String taskIdParam = (String) request.getParameter("taskId");
			try {
				IncidentActivityTask task = incidentActivityService.loadTask(Long.valueOf(taskIdParam));
				if (task != null) {
					int statusId=task.getStatus().getStatus_ID();
					ccf.setTaskId(task.getTask_id());
					ccf.setTaskStatus(statusId);
					if(statusId==TracingConstants.STATUS_CUSTOMER_COMM_PENDING
							 || statusId==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW
							 || statusId==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
						ccf.setPendingReview(true);
					}
				}
			} catch (NumberFormatException nfe) {
				logger.error("Invalid task id: " + taskIdParam, nfe);
			}
		}
		
		if (request.getParameter("templateId") != null && request.getParameter("incident") != null) {
			success = populateCustomerCommunicationsForm(request.getParameter("incident"), request.getParameter("templateId"), ccf, user, messages, request.getParameter("expense"));			
		} else if (request.getParameter("command") != null && TracingConstants.COMMAND_EDIT.equals(request.getParameter("command"))
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			try {
				long id = Long.valueOf(request.getParameter("communicationsId"));
				IncidentActivity ia = incidentActivityService.load(id);
				if (ia != null) {
					DomainUtils.toForm(ia, ccf, user);
					if (request.getParameter("approvalTask") != null && TracingConstants.TRUE.equalsIgnoreCase((String) request.getParameter("approvalTask"))) {
						Status s=null;
						if(ia.getActivity()!=null && ia.getActivity().getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY)){
							if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REVIEW, user) && request.getParameter("isFraud") != null){
								s=new Status(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW);
							} else if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SUPERVISOR_REVIEW, user)){

								s=new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW);
							}
						} else {
							s=new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING);
						}
						IncidentActivityTask iat = incidentActivityService.loadTaskForIncidentActivity(ia, s);
						if (iat != null) {
							int statusId=iat.getStatus().getStatus_ID();
							ccf.setTaskId(iat.getTask_id());
							ccf.setTaskStatus(statusId);

							if(statusId==TracingConstants.STATUS_CUSTOMER_COMM_PENDING
									 || statusId==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW
									 || statusId==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
								ccf.setPendingReview(true);
							}
						}
					}
					success = true;
				}
			} catch (NumberFormatException nfe) {
				logger.error("Failed to load customer communication with id: " + request.getParameter("communicationsId"), nfe);
				success = false;
			}
		} else if (request.getParameter("command") != null && TracingConstants.COMMAND_DELETE.equals(request.getParameter("command"))
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_DELETE, user)) {
			try {
				long id = Long.valueOf(request.getParameter("communicationsId"));
				success = incidentActivityService.delete(id);
			} catch (NumberFormatException nfe) {
				logger.error("Failed to delete customer communication with id: " + request.getParameter("communicationsId"), nfe);
				success = false;
			}
			
			if (StringUtils.equalsIgnoreCase("y", request.getParameter("ajax"))) {
				if (!success) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
				
				return null;
			}
			
			String anchor = "#activities";
			if (!success) {
				session.setAttribute("redirectMessageKey", "customer.communications.delete.failure");
				anchor = "";
			}
			response.sendRedirect("searchIncident.do?incident="+request.getParameter("incident")+anchor);
			return null;
		} else if (TracingConstants.COMMAND_CREATE.equals(ccf.getCommand())) {
			success = saveCustomerCommunications(ccf, user, messages);
		} else if (TracingConstants.COMMAND_UPDATE.equals(ccf.getCommand()) 
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			success = updateCustomerCommunications(ccf, user, messages);
		} else if (TracingConstants.COMMAND_ACKNOWLEDGE.equals(ccf.getCommand()) 
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			success = incidentActivityService.closeTask(ccf.getTaskId());
			DomainUtils.toForm(incidentActivityService.load(ccf.getId()), ccf, user);
			ccf.setTaskStatus(0);
			if(!success){
				logger.error("Failed to close a task for IncidentActivityTask with id: " + ccf.getTaskId());
			}
		} else if (TracingConstants.COMMAND_ACKNOWLEDGE_INBOUND.equals(ccf.getCommand()) 
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {

			long id = Long.valueOf(request.getParameter("communicationsId"));
			IncidentActivity ia = incidentActivityService.load(id);
			
			if(TracingConstants.ACTIVITY_CODE_INBOUND_CURE.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_FAX.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_MAIL.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_PORTAL.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_RECEIVED_DAMAGED_ITEM.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_SECONDARY_CORRESPONDENCE.equals(ia.getActivity().getCode())){
				success=OnlineClaimsDao.acknowledgeMessages(ia);

				if(!success){
					logger.error("Failed to acknowledge messages for IncidentActivity with id: " + id);
				}
			}
			
			if(TracingConstants.ACTIVITY_CODE_INBOUND_CURE.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_FAX.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_MAIL.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_INBOUND_PORTAL.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_RECEIVED_DAMAGED_ITEM.equals(ia.getActivity().getCode()) ||
			   TracingConstants.ACTIVITY_CODE_SECONDARY_CORRESPONDENCE.equals(ia.getActivity().getCode())){

				InboundTasksUtils.closeTaskByIncidentActivityId(id, user);
			}

			response.sendRedirect("searchIncident.do?incident="
					+ request.getParameter("incident") + "#activities");
			return null;
		} else if (TracingConstants.COMMAND_UNPUBLISH.equals(ccf.getCommand()) 
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			long id = Long.valueOf(request.getParameter("communicationsId"));
			success = publishCommunications(id, false);
			response.sendRedirect("searchIncident.do?incident="
					+ request.getParameter("incident") + "#activities");
			return null;
		} else if (TracingConstants.COMMAND_PUBLISH.equals(ccf.getCommand()) 
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			long id = Long.valueOf(request.getParameter("communicationsId"));
			success = publishCommunications(id, true);
			response.sendRedirect("searchIncident.do?incident="
					+ request.getParameter("incident") + "#activities");
			return null;
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		return mapping.findForward(TracingConstants.EDIT_COMMUNICATIONS);
	}
	
	private boolean publishCommunications(long id, boolean publish) {
		boolean success=true;
		try{
			IncidentActivity ia = incidentActivityService.load(id);
			if (ia != null) {
				if (ia.getMessages() != null) {
					for (OCMessage message : ia.getMessages()) {
						message.setPublish(publish);
						if(publish){
							message.setStatusId(TracingConstants.OC_STATUS_PUBLISHED);
						} else {
							message.setStatusId(TracingConstants.OC_STATUS_UNPUBLISHED);
						}
					}
					success = OnlineClaimsDao.saveMessages(ia.getMessages());
				}

				if (ia.getFiles() != null && ia.getFiles().size()>0
						|| (ia.getDocument()!=null && publish)) {
					if(!publish){
						for (OCFile file : ia.getFiles()) {
							file.setPublish(publish);
							file.setStatusId(TracingConstants.OC_STATUS_UNPUBLISHED);
						}
					} else {
						/** An Incident Activity will only have one Document to publish on the NT Core side **/
						OCFile file=documentService.publishDocument(ia);
						if(file!=null){
							ia.getFiles().add(file);
						}
					}
					success = OnlineClaimsDao.saveFiles(ia.getFiles());
				}
				if (!success) {
					logger.error("Failed to publish or unpublish communication for IncidentActivity with id: "+ id);
				} else if (publish){
					ia.setPublishedDate(new Date());
					IncidentActivityDAO dao=new IncidentActivityDAOImpl();
					success=dao.update(ia);
					ServletContext sc = getServlet().getServletContext();
					String realpath = sc.getRealPath("/");
					if(!success || !EmailUtils.sendIncidentActivityEmail(ia, realpath)){
						logger.error("Failed to email customer about communication for IncidentActivity with id: "+ id);
					}
					
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return success;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter(REQUEST_TEMPLATE_LIST) != null 
				|| request.getParameter(REQUEST_PREVIEW_DOCUMENT) != null 
				|| request.getParameter(REQUEST_VIEW_SAMPLE_PRINTOUT) != null;
	}
	
	private ActionForward handleAjaxRequest(CustomerCommunicationsForm ccf, Agent user, HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) {
		// handles ajax call for the list of available templates
		if (request.getParameter(REQUEST_TEMPLATE_LIST) != null) {
			try {
				int ordinal = Integer.valueOf(request.getParameter("templateList"));
				incidentActivityService.writeOptionsList(templateService.getTemplateOptionsByType(TemplateType.fromOrdinal(ordinal)), response);
			} catch (NumberFormatException nfe) {
				logger.error("Invalid value found for templateList", nfe);
			}
			return null;
		}

		// handles the case in which the user wants to preview the document
		if (request.getParameter(REQUEST_PREVIEW_DOCUMENT) != null) {
			try {
				int outputType = Integer.parseInt((String) request.getParameter(TracingConstants.OUTPUT_TYPE));
				long documentId = Long.parseLong((String) request.getParameter("preview_document"));
				Document document = documentService.load(documentId);
				if (document != null && document.getFileName() == null || StringUtils.isBlank(document.getFileName())) {
					if (outputType == TracingConstants.REPORT_OUTPUT_PDF) {
						if (!documentService.generatePdf(user, document, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP)).isSuccess()) {
							logger.error("Failed to generate preview for customer communication");
						}
					}
				}
				byte[] toOutput = documentService.getByteArrayForDocument(document, user.getCompanycode_ID(), PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP), outputType);
				outputToResponse(response, toOutput, outputType);
			} catch (Exception e) {
				logger.error("Failed to generate CustomerCommunications preview", e);
			}
			return null;
		}
		
		// handles the case in which the user wants to preview the document
		if (request.getParameter(REQUEST_VIEW_SAMPLE_PRINTOUT) != null) {
			try {
				String incidentActivityId = (String) request.getParameter("view_sample_printout");
				int outputType = Integer.parseInt((String) request.getParameter(TracingConstants.OUTPUT_TYPE));
				DocumentTemplateResult result = viewSamplePrintout(incidentActivityId, user, response, new ActionMessages(), outputType);
				if (!result.isSuccess()) {
					return mapping.findForward(TracingConstants.FILE_NOT_FOUND);
				}
			} catch (Exception e) {
				logger.error("Failed to display sample printout", e);
			}
			return null;
		}
		
		return null;
	}
	
	private boolean populateCustomerCommunicationsForm(String incidentId, String templateId, CustomerCommunicationsForm ccf, Agent user, ActionMessages messages, String expenseId) {
		boolean success = false;
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		int eId=0;
		if(expenseId!=null && !expenseId.isEmpty()){
			try {
				eId = Integer.valueOf(expenseId);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		
		if (incident == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.to.load.incident", new Object[] { incidentId }));
		} else {
			try {
				long tId = Long.valueOf(templateId);
				DocumentTemplateResult result = generateDocument(tId, incident, user, eId);
				if (!result.isSuccess()) {
					if (result.getPayload() != null) {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey(), new Object[] { result.getPayload() }));
					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
					}
					
					for (String missingInfo: result.getMissingInfoList()) {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.print.missing.info", new Object[] { missingInfo }));
					}
				}
				
				Document document = (Document) result.getPayload();
				DomainUtils.toForm(document, ccf);
				ccf.setTemplateId(tId);
				success = result.isSuccess();
			} catch (NumberFormatException nfe) {
				logger.error("Unable to load template with id: " + templateId, nfe);
				success = false;
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.to.load.template", new Object[] { templateId }));
			}
		
		}
		
		ccf.setCommand(TracingConstants.COMMAND_CREATE);
		ccf.setCustCommId(incident.getCustCommId());
		ccf.setIncidentId(incidentId);
		ccf.setExpenseId(eId);
		return success;
	}
	
	private boolean saveCustomerCommunications(CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		IncidentActivity incidentActivity = DomainUtils.fromForm(ccf, user);
		incidentActivity.setAgent(user);
		incidentActivity.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		boolean custCommApprove=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user);
		boolean fraudReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REVIEW, user);
		boolean supervisorReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SUPERVISOR_REVIEW, user);
		boolean paymentReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL, user);
		boolean isClaimSettlement=incidentActivity.getActivity()!=null && incidentActivity.getActivity().getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY);
		
		if((custCommApprove && !isClaimSettlement) || 
				((fraudReview || supervisorReview || paymentReview) && isClaimSettlement)){
			incidentActivity.setApprovalAgent(user);
		}
		
		boolean success = documentService.hasCustomerCommunicationRequiredInformation(incidentActivity.getDocument());
		if (!success) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, ccf.getDocumentTitle()));
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("customer.communication.missing.required.information", new Object[] { documentService.getMissingRequiredVariable(incidentActivity.getDocument()) }));
			return success;
		}
		
		long incidentActivityId = incidentActivityService.save(incidentActivity);
		ccf.setId(incidentActivity.getId());
		ccf.setDocumentId(incidentActivity.getDocument().getId());
		
		success = incidentActivityId != 0;
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, ccf.getDocumentTitle()));
		if (success) {
			if (!incidentActivityService.hasIncidentActivityTask(incidentActivity)) {
				/** SF:move cust_comm_approval check here to determine what status the task should get
				 * If They don't have the cust comm approval permission, create as normal
				 * if they do have the cust comm approval permission, create as a Print or WP Pending task and create the file, but don't publish it
				 * Added additional logic for fraud/supervisor/awaiting disbursement reviewers
				 */
				Status s=null;
				
				if(!custCommApprove && !fraudReview && !supervisorReview && !paymentReview){
					s=STATUS_PENDING;
					if(isClaimSettlement){
						if(user.getStation().getCompany().getVariable().isFraudReview()){
							s=STATUS_FRAUD_REVIEW;
						} else {
							s=STATUS_SUPERVISOR_REVIEW;
						}
					}
				} else if((supervisorReview || paymentReview) && isClaimSettlement) {
					/**
					 * SF: If the User is a Payment Approver user, the task will
					 * be put into the payment approval queue because the
					 * expense still needs a draft number, draft date, and mail
					 * date
					 **/
					s=STATUS_AWAITING_DISBURSEMENT;
				} else if(fraudReview && isClaimSettlement) {
					s=STATUS_SUPERVISOR_REVIEW;
				} else if(custCommApprove) {
					/**
					 * SF: If the User is a Cust Comm Approver user, but they're
					 * creating a Claim Settlement Letter, then it should apply
					 * to the Payment Approval Process not the Letter Approval
					 * Process
					 **/
					if(isClaimSettlement){
						if(user.getStation().getCompany().getVariable().isFraudReview()){
							s=STATUS_FRAUD_REVIEW;
						} else {
							s=STATUS_SUPERVISOR_REVIEW;
						}
					} else {
						switch(incidentActivity.getCustCommId()) {
							case TracingConstants.CUST_COMM_POSTAL_MAIL:
								s=STATUS_PENDING_PRINT;
								break;
							case TracingConstants.CUST_COMM_WEB_PORTAL:
								s=STATUS_PENDING_WP;
								break;
							default:
								logger.error("Invalid value found for customer communication method id: " + incidentActivity.getCustCommId());
								return false;
						}
					}
				} else {
					s=STATUS_PENDING;
				}
				
				if (!incidentActivityService.createTask(incidentActivity, s)) {
					logger.error("Failed to create a task for IncidentActivity with id: " + incidentActivityId);
				}
			}
		}
		return success;
	}

	private boolean updateCustomerCommunications(CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		IncidentActivity incidentActivity = DomainUtils.fromForm(ccf, user);
		// mjs: we don't want to change the agent if a task is in progress.
		if (ccf.getTaskId() == 0) {
			incidentActivity.setAgent(user);
		} else {
			incidentActivity.setAgent(new Agent(ccf.getAgentId()));
		}
		
		boolean success = incidentActivityService.update(incidentActivity);
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_UPDATE, success, ccf.getDocumentTitle()));
		boolean fraudReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FRAUD_REVIEW, user);
		boolean supervisorReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SUPERVISOR_REVIEW, user);
		boolean paymentReview=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL, user);
		boolean paymentApprover=fraudReview || supervisorReview || paymentReview;
		
		boolean isClaimSettlement=incidentActivity.getActivity()!=null && incidentActivity.getActivity().getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY);
		/**
		 * SF: If the user is a part of the payment approval process (Fraud
		 * Review, Supervisor Review or Payment Approval, then they have to be
		 * the original creator to make a new task
		 **/
		boolean makeNewTask=(!fraudReview && !supervisorReview && !paymentReview) || incidentActivity.getAgent().getAgent_ID()==user.getAgent_ID();
		if (success && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user) && makeNewTask){
			if (ccf.getTaskId() > 0 && !incidentActivityService.closeTask(ccf.getTaskId())) {
				logger.error("Failed to close the task for IncidentActivity with id: " + incidentActivity.getId());
			}
			Status s=null;
			if(!paymentApprover){
				s=STATUS_PENDING;
				if(isClaimSettlement){
					if(user.getStation().getCompany().getVariable().isFraudReview()){
						s=STATUS_FRAUD_REVIEW;
					} else {
						s=STATUS_SUPERVISOR_REVIEW;
					}
				}
			}  else if((supervisorReview || paymentReview) && isClaimSettlement) {
				/**
				 * SF: If the User is a Payment Approver user, the task will
				 * be put into the payment approval queue because the
				 * expense still needs a draft number, draft date, and mail
				 * date
				 **/
				s=STATUS_AWAITING_DISBURSEMENT;
			} else if(fraudReview && isClaimSettlement) {
				s=STATUS_SUPERVISOR_REVIEW;
			} else {
				/**
				 * SF: If it's not a claim settlement letter creating a pending
				 * task for the Customer Communications Approval queue
				 **/
				s=STATUS_PENDING;
			}

			if (!incidentActivityService.hasIncidentActivityTask(incidentActivity) && !incidentActivityService.createTask(incidentActivity, s)) {
				logger.error("Failed to create a task for IncidentActivity with id: " + incidentActivity.getId());
			}
		}
		
		DomainUtils.toForm(incidentActivityService.load(incidentActivity.getId()), ccf, user);
		return success;
	}
	
	private DocumentTemplateResult generateDocument(long templateId, Incident incident, Agent user, int epId) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		Template template;
		
		if ((template = templateService.load(templateId)) == null) {
			result.setMessageKey("unable.to.load.template");
			result.setPayload(templateId);
			return result;
		}
		
		result = templateService.validateTemplate(template);
		if (!result.isSuccess()) {
			return result;
		}
		
		Document document = new Document(template);
		try {
			TemplateAdapterDTO dto = DomainUtils.getTemplateAdapterDTO(user, template);
			dto.setIncident(incident);
			dto.setClaim(ClaimUtils.getFsClaimFromNtIncident(incident));
			if(epId>0){
				ExpensePayout ep=ExpenseUtils.getExpensePayout(String.valueOf(epId));
				if(ep!=null){
					dto.setExpensePayout(ep);
				}
			}
			
			result = TemplateAdapterFactory.hasRequiredInfo(dto);
			List<String> missingInfoList = result.getMissingInfoList();
			
			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(dto);
			
			result = documentService.mergeDocumentToEdit(document, adapter);			
			result.setPayload(document);
			if (!missingInfoList.isEmpty()) {
				result.setSuccess(false);
				result.setMessageKey("document.generated.failure");
				result.setMissingInfoList(missingInfoList);
			}
			
		} catch (InvalidDocumentTypeException e) {
			logger.error("Failed to create adapter for template type", e);
			return new DocumentTemplateResult(false, "document.template.type.error");
		} catch (InsufficientInformationException e) {
			logger.error("Insufficient information to create the template adapter.", e);
			return new DocumentTemplateResult(false, "document.template.missing.information.error");
		}
		
		return result;
	}
	
	private DocumentTemplateResult viewSamplePrintout(String incidentActivityId, Agent user, HttpServletResponse response, ActionMessages messages, int outputType) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		try {
			long id = Long.valueOf(incidentActivityId);
			IncidentActivity ia = incidentActivityService.load(id);
			
			if (ia == null) {
				logger.error("Failed to load incident activity with id: " + incidentActivityId);
			} else if (ia.getDocument() == null) {
				logger.error("No document found for incident activity with id: " + incidentActivityId);
			}

			if (ia != null && ia.getDocument() != null) {
				String tempDir = PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP);
				if (outputType == TracingConstants.REPORT_OUTPUT_PDF) {
					result = documentService.generatePdf(user, ia.getDocument(), tempDir);
					if (!result.isSuccess()) {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
					}
				}
				
				byte[] toOutput = documentService.getByteArrayForDocument(ia.getDocument(), user.getCompanycode_ID(), tempDir, outputType);
				outputToResponse(response, toOutput, outputType);
				
				result.setSuccess(true);
				return result;
			}
		} catch (NumberFormatException nfe) {
			logger.error("Could not load incident activity with id: " + incidentActivityId, nfe);
		} catch (Exception e) {
			logger.error("Failed to generate preview for incident activity: " + incidentActivityId, e);
		}
		result.setSuccess(false);
		result.setPayload(TracingConstants.FILE_NOT_FOUND);
		return result;
	}
	
	private ActionMessage getActionMessage(String action, boolean success, String name) {
		return new ActionMessage(success ? "incident.activity." + action + ".success" : "incident.activity." + action + ".failed", new Object[] { name });
	}
}
