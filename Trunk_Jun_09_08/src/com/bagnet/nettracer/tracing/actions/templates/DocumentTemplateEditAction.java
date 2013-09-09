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
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateForm;
import com.bagnet.nettracer.tracing.service.DocumentTemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DocumentTemplateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class DocumentTemplateEditAction extends CheckedAction {

	private static Logger logger = Logger.getLogger(DocumentTemplateEditAction.class);
	
	private DocumentTemplateService service = (DocumentTemplateService) SpringUtils.getBean("documentTemplateService");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");

		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (session.getAttribute("documentTemplateVars") == null) {
			session.setAttribute("documentTemplateVars", service.getDocumentTemplateVars());
		}
		
		DocumentTemplate template = null;
		DocumentTemplateForm dtf = (DocumentTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		boolean success = false;
		boolean redirect = false;

		// coming here from another page (ie: search the search page)
		if (request.getParameter("template_id") != null || request.getParameter("create") != null) {
			if (request.getParameter("template_id") != null) {
				try {
					template = service.load(Long.valueOf(request.getParameter("template_id")));
				} catch (NumberFormatException nfe) {
					logger.error("Invalid id: " + request.getParameter("template_id") + " provided.", nfe);
				}
			}
			DocumentTemplateUtils.toForm(template, dtf);
		} else if (TracingConstants.COMMAND_CREATE.equals(dtf.getCommand())) {
			success = saveTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_UPDATE.equals(dtf.getCommand())) {
			success = updateTemplate(dtf, messages);
		} else if (TracingConstants.COMMAND_DELETE.equals(dtf.getCommand())) {
			success = deleteTemplate(dtf, messages);
			
//			MJS: not sure if I need this yet since I'm redirecting on delete
//			DocumentTemplateUtils.toForm(null, dtf);
			redirect = success;
		}
		
		if (!messages.isEmpty()) {
			saveMessages(request, messages);
			request.setAttribute("success", success);
		}
		
		dtf.set_DATEFORMAT(user.getDateformat().getFormat());
		dtf.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dtf.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		
		if (redirect) {
			return mapping.findForward(TracingConstants.SEARCH_DOCUMENT_TEMPLATE);
		} else {
			return mapping.findForward(TracingConstants.EDIT_DOCUMENT_TEMPLATE);
		}
	}
	
	public boolean saveTemplate(DocumentTemplateForm dtf, ActionMessages messages) {
		boolean success = false;
		if (service.containsValidVariables(dtf.getData())) {
			long id = service.save(DocumentTemplateUtils.fromForm(dtf));
			dtf.setId(id);
			success = id != 0;
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_CREATE, success, dtf.getName()));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", service.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}
	
	public boolean updateTemplate(DocumentTemplateForm dtf, ActionMessages messages) {
		boolean success = false;
		if (service.containsValidVariables(dtf.getData())) {
			dtf.setLastUpdated(DateUtils.convertToGMTDate(new Date()));
			DocumentTemplate template = DocumentTemplateUtils.fromForm(dtf);
			success = service.update(template);
			messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_UPDATE, success, dtf.getName()));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.template.invalid.variable", service.getFirstInvalidVar(dtf.getData())));
		}
		return success;
	}

	public boolean deleteTemplate(DocumentTemplateForm dtf, ActionMessages messages) {
		String name = dtf.getName();
		boolean success = service.delete(dtf.getId());
		messages.add(ActionMessages.GLOBAL_MESSAGE, getActionMessage(TracingConstants.COMMAND_DELETE, success, name));
		return success;
	}

	private ActionMessage getActionMessage(String action, boolean success, String name) {
		return new ActionMessage(success ? "document.template." + action + ".success" : "document.template.update.failed", new Object[] { name });
	}

	public DocumentTemplateService getService() {
		return service;
	}

	public void setService(DocumentTemplateService service) {
		this.service = service;
	}
	
}
