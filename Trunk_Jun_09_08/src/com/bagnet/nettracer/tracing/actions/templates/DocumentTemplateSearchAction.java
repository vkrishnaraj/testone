package com.bagnet.nettracer.tracing.actions.templates;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateSearchForm;
import com.bagnet.nettracer.tracing.service.DocumentTemplateService;
import com.bagnet.nettracer.tracing.service.impl.DocumentTemplateSearchDTO;
import com.bagnet.nettracer.tracing.utils.DocumentTemplateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class DocumentTemplateSearchAction extends CheckedAction {

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
		
		if (TracingConstants.TRUE.equals(request.getParameter(TracingConstants.COMMAND_CREATE))) {
			response.sendRedirect("documentTemplate.do?create=1");
			return null;
		}

		DocumentTemplateSearchForm dtsf = (DocumentTemplateSearchForm) form;
		List<DocumentTemplate> results = new ArrayList<DocumentTemplate>();
		DocumentTemplateSearchDTO dto = DocumentTemplateUtils.fromForm(dtsf);
		
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
		int currpage = 0;

		if (dtsf.getNextpage() != null && dtsf.getNextpage().equals("1"))
			currpage++;
		if (dtsf.getPrevpage() != null && dtsf.getPrevpage().equals("1"))
			currpage--;
		
		int rowcount = service.getDocumentTemplateCount(dto);
		
		if (session.getAttribute("documentTemplateStatusList") == null) {
			session.setAttribute("documentTemplateStatusList", TracerUtils.getStatusList(TracingConstants.TABLE_DOCUMENT_TEMPLATE_STATUS, user.getDefaultlocale()));
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		dtsf.set_DATEFORMAT(user.getDateformat().getFormat());
		return mapping.findForward(TracingConstants.SEARCH_DOCUMENT_TEMPLATE);
	}

}
