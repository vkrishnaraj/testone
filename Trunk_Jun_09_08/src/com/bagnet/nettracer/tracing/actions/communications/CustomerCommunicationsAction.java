package com.bagnet.nettracer.tracing.actions.communications;

import java.util.Date;

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
import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
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
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class CustomerCommunicationsAction extends CheckedAction {

	private final String REQUEST_TEMPLATE_LIST = "templateList";
	private final String REQUEST_PREVIEW_DOCUMENT = "preview_document";
	private final String REQUEST_VIEW_SAMPLE_PRINTOUT = "view_sample_printout";
	
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
		
		if (isAjaxRequest(request)) {
			return handleAjaxRequest(user, request, response, mapping);
		}

		boolean success = true;
		CustomerCommunicationsForm ccf = (CustomerCommunicationsForm) form;
		
		if (request.getParameter("taskId") != null) {
			String taskIdParam = (String) request.getParameter("taskId");
			try {
				IncidentActivityTask task = incidentActivityService.loadTask(Long.valueOf(taskIdParam));
				if (task != null) {
					ccf.setTaskId(task.getTask_id());
					ccf.setTaskStatus(task.getStatus().getStatus_ID());
				}
			} catch (NumberFormatException nfe) {
				logger.error("Invalid task id: " + taskIdParam, nfe);
			}
		}
		
		if (request.getParameter("templateId") != null && request.getParameter("incident") != null) {
			success = populateCustomerCommunicationsForm(request.getParameter("incident"), request.getParameter("templateId"), ccf, user, messages);			
		} else if (request.getParameter("command") != null && TracingConstants.COMMAND_EDIT.equals(request.getParameter("command"))
				&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, user)) {
			try {
				long id = Long.valueOf(request.getParameter("communicationsId"));
				IncidentActivity ia = incidentActivityService.load(id);
				if (ia != null) {
					DomainUtils.toForm(ia, ccf, user);
					if (request.getParameter("approvalTask") != null && TracingConstants.TRUE.equalsIgnoreCase((String) request.getParameter("approvalTask"))) {
						IncidentActivityTask iat = incidentActivityService.loadTaskForIncidentActivity(ia, new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING));
						if (iat != null) {
							ccf.setTaskId(iat.getTask_id());
							ccf.setTaskStatus(iat.getStatus().getStatus_ID());
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
		}
		
		if (ccf.isPreview()) {
			DocumentTemplateResult result = generatePreview(ccf, user);
			if (result.isSuccess()) {
				request.setAttribute("previewLink", result.getPayload());
			}
			success = result.isSuccess();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		return mapping.findForward(TracingConstants.EDIT_COMMUNICATIONS);
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter(REQUEST_TEMPLATE_LIST) != null 
				|| request.getParameter(REQUEST_PREVIEW_DOCUMENT) != null 
				|| request.getParameter(REQUEST_VIEW_SAMPLE_PRINTOUT) != null;
	}
	
	private ActionForward handleAjaxRequest(Agent user, HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) {
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
			String directoryKey = request.getParameter("receipt") != null ? PropertyBMO.DOCUMENT_LOCATION_RECEIPTS : PropertyBMO.DOCUMENT_LOCATION_TEMP;
			DocumentTemplateResult result = documentService.previewFile(user, request.getParameter("preview_document"), PropertyBMO.getValue(directoryKey), response);
			if (!result.isSuccess()) {
				return mapping.findForward(TracingConstants.FILE_NOT_FOUND);
			}
			return null;
		}
		
		// handles the case in which the user wants to preview the document
		if (request.getParameter(REQUEST_VIEW_SAMPLE_PRINTOUT) != null) {
			String incidentId = (String) request.getParameter("view_sample_printout");
			DocumentTemplateResult result = viewSamplePrintout(incidentId, user, response, new ActionMessages());
			if (!result.isSuccess()) {
				return mapping.findForward(TracingConstants.FILE_NOT_FOUND);
			}
			return null;
		}
		
		return null;
	}
	
	private boolean populateCustomerCommunicationsForm(String incidentId, String templateId, CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		boolean success = false;
		Incident incident = new IncidentBMO().findIncidentByID(incidentId);
		if (incident == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.to.load.incident", new Object[] { incidentId }));
		} else {
			try {
				long tId = Long.valueOf(templateId);
				DocumentTemplateResult result = generateDocument(tId, incident, user);
				if (!result.isSuccess()) {
					if (result.getPayload() != null) {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey(), new Object[] { result.getPayload() }));
					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
					}
				} else {
					Document document = (Document) result.getPayload();
					DomainUtils.toForm(document, ccf);
				}
				ccf.setTemplateId(tId);
				success = true;
			} catch (NumberFormatException nfe) {
				logger.error("Unable to load template with id: " + templateId, nfe);
				success = false;
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("unable.to.load.template", new Object[] { templateId }));
			}
		
		}
		
		ccf.setCommand(TracingConstants.COMMAND_CREATE);
		ccf.setCustCommId(incident.getCustCommId());
		ccf.setIncidentId(incidentId);
		return success;
	}
	
	private boolean saveCustomerCommunications(CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		IncidentActivity incidentActivity = DomainUtils.fromForm(ccf, user);
		incidentActivity.setAgent(user);
		incidentActivity.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		
		long incidentActivityId = incidentActivityService.save(incidentActivity);
		ccf.setId(incidentActivity.getId());
		ccf.setDocumentId(incidentActivity.getDocument().getId());
		
		boolean success = incidentActivityId != 0;
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, ccf.getDocumentTitle()));
		if (success && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user)) {
			if (!createTask(incidentActivity)) {
				logger.error("Failed to create a task for IncidentActivity with id: " + incidentActivityId);
			}
		}
		return success;
	}

	private boolean updateCustomerCommunications(CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		IncidentActivity incidentActivity = DomainUtils.fromForm(ccf, user);
		incidentActivity.setAgent(user);
		
		boolean success = incidentActivityService.update(incidentActivity);
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_UPDATE, success, ccf.getDocumentTitle()));
		if (success && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_APPROVAL, user)) {
			if (ccf.getTaskId() > 0 && !incidentActivityService.closeTask(ccf.getTaskId())) {
				logger.error("Failed to delete the rejected task for IncidentActivity with id: " + incidentActivity.getId());
			}
			
			if (!createTask(incidentActivity)) {
				logger.error("Failed to create a task for IncidentActivity with id: " + incidentActivity.getId());
			}
		}
		
		DomainUtils.toForm(incidentActivityService.load(incidentActivity.getId()), ccf, user);
		return success;
	}
	
	private boolean createTask(IncidentActivity incidentActivity) {
		if (!incidentActivityService.hasIncidentActivityTask(incidentActivity)) {
			IncidentActivityTask iat = DomainUtils.createIncidentActivityTask(incidentActivity, new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING));
			boolean success = incidentActivityService.saveTask(iat) != 0;
			incidentActivity.getTasks().add(iat);
			return success;
		}
		return true;
	}
	
	private DocumentTemplateResult generateDocument(long templateId, Incident incident, Agent user) {
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

			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(dto);
			result = documentService.merge(document, adapter);
			if (result.isSuccess()) {
				result.setPayload(document);
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
	
	private DocumentTemplateResult generatePreview(CustomerCommunicationsForm ccf, Agent user) {
		ccf.setPreview(false);
		DocumentTemplateResult result = new DocumentTemplateResult();
		Document document = documentService.load(ccf.getDocumentId());
		if (document == null) {
			result.setMessageKey("document.generated.failure");
			return result;
		}
		
		try {
			result = documentService.generatePdf(user, document, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP));
			if (result.isSuccess()) {
				document.setFileName((String) result.getPayload());
				result.setSuccess(documentService.update(document));
			}
		} catch (InsufficientInformationException e) {
			logger.error("Insufficient information to create incident activity preview.", e);
			return new DocumentTemplateResult(false, "document.template.missing.information.error");
		}
		
		return result;
	}
	
	private DocumentTemplateResult viewSamplePrintout(String incidentActivityId, Agent user, HttpServletResponse response, ActionMessages messages) {
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
				result = documentService.generatePdf(user, ia.getDocument(), tempDir);
				if (!result.isSuccess()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
				}
				
				result = documentService.previewFile(user, (String) result.getPayload(), tempDir, response);
				if (!result.isSuccess()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
				}
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
