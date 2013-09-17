package com.bagnet.nettracer.tracing.actions.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.exceptions.InvalidDocumentTypeException;
import com.bagnet.nettracer.tracing.factory.TemplateAdapterFactory;
import com.bagnet.nettracer.tracing.forms.templates.TemplateEditForm;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.TemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TemplateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class TemplateEditAction extends CheckedAction {

	private Logger logger = Logger.getLogger(TemplateEditAction.class);
	
	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
	private TemplateService templateService = (TemplateService) SpringUtils.getBean(TracingConstants.TEMPLATE_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

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
		
		if (request.getParameter("preview_document") != null) {
			previewFile(user, request.getParameter("preview_document"), response);
			return null;
		}
		
		TemplateEditForm dtf = (TemplateEditForm) form;
		setUserDataOnForm(dtf, user);

		ActionMessages messages = new ActionMessages();
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
			TemplateUtils.toForm(template, dtf);
		} else if (TracingConstants.COMMAND_CREATE.equals(dtf.getCommand())) {
			success = saveTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_UPDATE.equals(dtf.getCommand())) {
			success = updateTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_DELETE.equals(dtf.getCommand())) {
			success = deleteTemplate(dtf, messages);
			redirect = success;
		}
		
		if (dtf.isPreview()) {
			DocumentTemplateResult result = generateTemplatePreview(dtf, user);
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
		
		if (redirect) {
			return mapping.findForward(TracingConstants.SEARCH_TEMPLATE);
		} else {
			return mapping.findForward(TracingConstants.EDIT_TEMPLATE);
		}
	}
	
	public boolean saveTemplate(TemplateEditForm dtf, ActionMessages messages) {
		boolean success = false;
		if (templateService.containsValidVariables(dtf.getData())) {
			long id = templateService.save(getTemplateAndSetType(dtf));
			dtf.setId(id);
			success = id != 0;
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, dtf.getName()));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", templateService.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}
	
	public boolean updateTemplate(TemplateEditForm dtf, ActionMessages messages) {
		boolean success = false;
		if (templateService.containsValidVariables(dtf.getData())) {
			dtf.setLastUpdated(DateUtils.convertToGMTDate(new Date()));
			success = templateService.update(getTemplateAndSetType(dtf));
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_UPDATE, success, dtf.getName()));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", templateService.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}

	public boolean deleteTemplate(TemplateEditForm dtf, ActionMessages messages) {
		String name = dtf.getName();
		boolean success = templateService.delete(dtf.getId());
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_DELETE, success, name));
		return success;
	}
	
	public DocumentTemplateResult generateTemplatePreview(TemplateEditForm dtf, Agent user) {
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
			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(TemplateUtils.getDummyAdapterDTO(user));
			result = documentService.merge(document, adapter);
			if (!result.isSuccess()) {
				return result;
			}
			
			// 3. create the pdf file		
			String documentStorePath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + "temp";
			result = documentService.generatePdf(document, documentStorePath);
			
		} catch (InvalidDocumentTypeException e) {
			logger.error("Failed to create adapter for template type: " + template.getType().getDefaultName(), e);
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
		Template template = TemplateUtils.fromForm(tef);
		template.setType(templateService.determineRequiredTemplateType(template));
		return template;
	}
	
	private boolean previewFile(Agent user, String fileName, HttpServletResponse response) {
		boolean success = false;
		
		String documentStorePath = TracerProperties.get(user.getCompanycode_ID(),"document_store") + "temp/";
		File toPreview = new File(documentStorePath + fileName);
		if (!toPreview.exists()) {
			return false;
		}
		
		response.setContentType("application/pdf");
		response.setContentLength((int) toPreview.length());
		
		FileInputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(toPreview);
			out = response.getOutputStream();
			
			int current = -1;
			while ((current = in.read()) > -1) {
				out.write(current);
			}

			success = true;
		} catch (IOException e) {
			logger.error("Failed to read the preview file: " + fileName, e);
		} finally {
			try {
				if (in != null) in.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close the input and output streams in file preview", e);
			}
		}
		
		return success;
	}

}