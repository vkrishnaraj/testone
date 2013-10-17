package com.bagnet.nettracer.tracing.actions.communications;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.dto.TemplateOptionDTO;
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
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerCommunicationsAction extends CheckedAction {

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
		
		// handles ajax call for the list of available templates
		if (request.getParameter("templateList") != null) {
			try {
				int ordinal = Integer.valueOf(request.getParameter("templateList"));
				writeTemplateOptionsList(TemplateType.fromOrdinal(ordinal), response);
			} catch (NumberFormatException nfe) {
				logger.error("Invalid value found for templateList", nfe);
			}
			return null;
		}

		// handles the case in which the user wants to preview the document
		if (request.getParameter("preview_document") != null) {
			DocumentTemplateResult result = documentService.previewFile(user, request.getParameter("preview_document"), response);
			if (!result.isSuccess()) {
				return mapping.findForward(TracingConstants.FILE_NOT_FOUND);
			}
			return null;
		}
		
		// handles the case in which the user wants to preview the document
		if (request.getParameter("view_sample_printout") != null) {
			session.setAttribute("goto_customer_communications", true);
			String incidentId = (String) request.getParameter("view_sample_printout");
			DocumentTemplateResult result = viewSamplePrintout(incidentId, user, response, messages);
			if (!result.isSuccess()) {
				return mapping.findForward(TracingConstants.FILE_NOT_FOUND);
			}
			return null;
		}

		boolean success = true;
		CustomerCommunicationsForm ccf = (CustomerCommunicationsForm) form;
		
		if (request.getParameter("templateId") != null && request.getParameter("incident") != null) {
			success = populateCustomerCommunicationsForm(request.getParameter("incident"), request.getParameter("templateId"), ccf, user, messages);			
		} else if (request.getParameter("command") != null && TracingConstants.COMMAND_EDIT.equals(request.getParameter("command"))) {
			try {
				long id = Long.valueOf(request.getParameter("communicationsId"));
				IncidentActivity ia = incidentActivityService.load(id);
				if (ia != null) {
					DomainUtils.toForm(ia, ccf);
					success = true;
				}
			} catch (NumberFormatException nfe) {
				logger.error("Failed to load customer communication with id: " + request.getParameter("communicationsId"), nfe);
				success = false;
			}
		} else if (request.getParameter("command") != null && TracingConstants.COMMAND_DELETE.equals(request.getParameter("command"))) {
			try {
				long id = Long.valueOf(request.getParameter("communicationsId"));
				success = incidentActivityService.delete(id);
			} catch (NumberFormatException nfe) {
				logger.error("Failed to delete customer communication with id: " + request.getParameter("communicationsId"), nfe);
				success = false;
			}
			response.sendRedirect("searchIncident.do?incident="+request.getParameter("incident"));
			return null;
		} else if (TracingConstants.COMMAND_CREATE.equals(ccf.getCommand())) {
			success = saveCustomerCommunications(ccf, user, messages);
		} else if (TracingConstants.COMMAND_UPDATE.equals(ccf.getCommand())) {
			success = updateCustomerCommunications(ccf, user, messages);
		}
		
		if (ccf.isPreview()) {
			DocumentTemplateResult result = generateTemplatePreview(ccf, user);
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
	
	private void writeTemplateOptionsList(TemplateType type, HttpServletResponse response) {
		List<TemplateOptionDTO> options = new ArrayList<TemplateOptionDTO>();
		try {
			options = templateService.getTemplateOptionsByType(type);
			
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("text/x-json;charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(mapper.writeValueAsString(options));
			out.flush();
		} catch (Exception e) {
			logger.error("Error caught trying to return the templateList", e);
		}
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
		messages.add(ActionMessages.GLOBAL_MESSAGE, this.getActionMessage(TracingConstants.COMMAND_CREATE, success, ccf.getDocumentTitle()));
		return success;
	}

	private boolean updateCustomerCommunications(CustomerCommunicationsForm ccf, Agent user, ActionMessages messages) {
		IncidentActivity incidentActivity = DomainUtils.fromForm(ccf, user);
		incidentActivity.setAgent(user);
		
		boolean success = incidentActivityService.update(incidentActivity);
		messages.add(ActionMessages.GLOBAL_MESSAGE, this.getActionMessage(TracingConstants.COMMAND_UPDATE, success, ccf.getDocumentTitle()));
		return success;
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
			if (!result.isSuccess()) {
				return result;
			}
			
			result = documentService.generatePdf(user, document);
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
	
	private DocumentTemplateResult generateTemplatePreview(CustomerCommunicationsForm ccf, Agent user) {
		ccf.setPreview(false);
		DocumentTemplateResult result = new DocumentTemplateResult();
		Document document = documentService.load(ccf.getDocumentId());
		if (document == null) {
			result.setMessageKey("document.generated.failure");
			return result;
		}
		
		try {
			result = documentService.generatePdf(user, document);
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
	
	private DocumentTemplateResult viewSamplePrintout(String incidentId, Agent user, HttpServletResponse response, ActionMessages messages) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		try {
			long id = Long.valueOf(incidentId);
			IncidentActivity ia = incidentActivityService.load(id);
			
			if (ia == null) {
				logger.error("Failed to load incident activity with id: " + incidentId);
			} else if (ia.getDocument() == null) {
				logger.error("No document found for incident activity with id: " + incidentId);
			}

			if (ia != null && ia.getDocument() != null) {
				result = documentService.generatePdf(user, ia.getDocument());
				if (!result.isSuccess()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
				}
				
				result = documentService.previewFile(user, (String) result.getPayload(), response);
				if (!result.isSuccess()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
				}
				return result;
			}
		} catch (NumberFormatException nfe) {
			logger.error("Could not load incident activity with id: " + incidentId, nfe);
		} catch (Exception e) {
			logger.error("Failed to generate preview for incident activity: " + incidentId, e);
		}
		result.setSuccess(false);
		result.setPayload(TracingConstants.FILE_NOT_FOUND);
		return result;
	}
	
	private ActionMessage getActionMessage(String action, boolean success, String name) {
		return new ActionMessage(success ? "incident.activity." + action + ".success" : "incident.activity." + action + ".failed", new Object[] { name });
	}
}