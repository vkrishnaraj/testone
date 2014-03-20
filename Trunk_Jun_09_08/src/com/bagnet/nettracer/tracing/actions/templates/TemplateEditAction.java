package com.bagnet.nettracer.tracing.actions.templates;

import java.util.Date;
import java.util.TimeZone;

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
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.exceptions.InvalidDocumentTypeException;
import com.bagnet.nettracer.tracing.factory.TemplateAdapterFactory;
import com.bagnet.nettracer.tracing.forms.templates.TemplateEditForm;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.TemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class TemplateEditAction extends CheckedAction {

	private Logger logger = Logger.getLogger(TemplateEditAction.class);
	
	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
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
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DOCUMENT_TEMPLATES_MANAGE, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if (session.getAttribute("templateVars") == null) {
			session.setAttribute("templateVars", templateService.getTemplateVars());
		}
		
		TemplateEditForm dtf = (TemplateEditForm) form;
		if (request.getParameter("preview_document") != null) {
			handlePreviewDocument(response, messages, user, dtf, (String) request.getParameter(TracingConstants.OUTPUT_TYPE));
			return null;
		}
		
		dtf.setTypesList(TemplateType.getDependencyTemplateTypes());
		setUserDataOnForm(dtf, user);

		Template template = null;
		boolean success = false;
		boolean redirect = false;

		// coming here from another page (ie: search the search page)
		if (request.getParameter("template_id") != null || request.getParameter("create") != null) {
			if (request.getParameter("template_id") != null) {
				try {
					template = templateService.load(Long.valueOf(request.getParameter("template_id")));
				} catch (NumberFormatException nfe) {
					logger.error("Invalid id: " + request.getParameter("template_id") + " provided.", nfe);
				}
			}
			DomainUtils.toForm(template, dtf);
		} else if (TracingConstants.COMMAND_CREATE.equals(dtf.getCommand())) {
			success = saveTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_UPDATE.equals(dtf.getCommand())) {
			success = updateTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_DELETE.equals(dtf.getCommand())) {
			success = deleteTemplate(dtf, messages);
			redirect = success;
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		if (redirect) {
			return mapping.findForward(TracingConstants.SEARCH_TEMPLATE);
		} else {
			return mapping.findForward(TracingConstants.EDIT_TEMPLATE);
		}
	}

	private void handlePreviewDocument(HttpServletResponse response, ActionMessages messages, Agent user, TemplateEditForm dtf, String outputTypeParam) {
		int outputType = -1;
		try {
			outputType = Integer.parseInt(outputTypeParam);

			DocumentTemplateResult result = generateTemplatePreview(dtf, user, outputType);
			if (!result.isSuccess()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey(), result.getPayload() != null ? result.getPayload() : null));
			} else {
				byte[] toOutput = documentService.getByteArrayForDocument((Document) result.getPayload(), user.getCompanycode_ID(), PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP), outputType);
				outputToResponse(response, toOutput, outputType);
			}
			
		} catch (NumberFormatException nfe) {
			logger.error("Invalid value for output", nfe);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.generated.failure"));
		}
	}
	
	private boolean saveTemplate(TemplateEditForm dtf, ActionMessages messages) {
		boolean success = false;
		if (templateService.containsValidVariables(dtf.getData())) {
			Template template = getTemplateAndSetType(dtf);
			long id = templateService.save(template);
			dtf.setId(id);
			success = id != 0;
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, dtf.getName()));
			if (!template.isValid()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid"));
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", templateService.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}
	
	private boolean updateTemplate(TemplateEditForm dtf, ActionMessages messages) {
		boolean success = false;
		if (templateService.containsValidVariables(dtf.getData())) {
			Template template = getTemplateAndSetType(dtf);
			dtf.setLastUpdated(DateUtils.convertToGMTDate(new Date()));
			success = templateService.update(template);
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_UPDATE, success, dtf.getName()));
			if (!template.isValid()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid"));
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", templateService.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}

	private boolean deleteTemplate(TemplateEditForm dtf, ActionMessages messages) {
		String name = dtf.getName();
		boolean success = templateService.delete(dtf.getId());
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_DELETE, success, name));
		return success;
	}
	
	private DocumentTemplateResult generateTemplatePreview(TemplateEditForm dtf, Agent user, int outputType) {
		dtf.setPreview(false);
		Template template = templateService.load(dtf.getId());
		// 1. validate the template to be used
		DocumentTemplateResult result = templateService.validateTemplate(template);
		if (!result.isSuccess()) {
			return result;
		}
		
		Document document = new Document(template);
		try {
			// 2. create the document using a dummy template adapter
			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(DomainUtils.getDummyAdapterDTO(user));
			result = documentService.mergeDocumentToPrint(document, adapter);
			if (!result.isSuccess()) {
				return result;
			}
			
			// 3. create the pdf file	
			if (outputType == TracingConstants.REPORT_OUTPUT_PDF) {
				result = documentService.generatePdf(user, document, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP));
			}
			result.setPayload(document);
			
		} catch (InvalidDocumentTypeException e) {
			logger.error("Failed to create adapter for template type", e);
			return new DocumentTemplateResult(false, "document.template.type.error");
		} catch (InsufficientInformationException e) {
			logger.error("Insufficient information to create the template adapter.", e);
			return new DocumentTemplateResult(false, "document.template.missing.information.error");
		}
		
		return result;
	}
	
	private void setUserDataOnForm(TemplateEditForm dtf, Agent user) {
		dtf.set_DATEFORMAT(user.getDateformat().getFormat());
		dtf.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dtf.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
	}

	private ActionMessage getActionMessage(String action, boolean success, String name) {
		return new ActionMessage(success ? "document.template." + action + ".success" : "document.template." + action + ".failed", new Object[] { name });
	}
	
	private Template getTemplateAndSetType(TemplateEditForm tef) {
		Template template = DomainUtils.fromForm(tef);
		template.setTypes(templateService.determineRequiredTemplateTypes(template));
		return template;
	}
	
}