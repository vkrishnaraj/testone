package com.bagnet.nettracer.tracing.actions.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.DocumentTemplateDTO;
import com.bagnet.nettracer.tracing.dto.DocumentTemplateSearchDTO;
import com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateSearchForm;
import com.bagnet.nettracer.tracing.service.DocumentTemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
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
		
		if (TracingConstants.COMMAND_CREATE.equals(request.getParameter(TracingConstants.COMMAND_CREATE))) {
			response.sendRedirect("documentTemplate.do?create=1");
			return null;
		}

		DocumentTemplateSearchForm dtsf = (DocumentTemplateSearchForm) form;
		List<DocumentTemplateDTO> results = new ArrayList<DocumentTemplateDTO>();
		DocumentTemplateSearchDTO dto = DocumentTemplateUtils.fromForm(dtsf);
		dto.set_DATEFORMAT(user.getDateformat().getFormat());
		dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		getSortCriteria(dto, request);
		
		int currpage = 0;
		int rowcount = 0;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);

		if (TracingConstants.COMMAND_SEARCH.equals(dtsf.getCommand())) {
			if (dtsf.getNextpage() != null && dtsf.getNextpage().equals("1")) {
				currpage++;
			}
	
			if (dtsf.getPrevpage() != null && dtsf.getPrevpage().equals("1")) {
				currpage--;
			}
	
			rowcount = service.getDocumentTemplateCount(dto);
			
			dto.setCurrentPage(currpage);
			dto.setRowsPerPage(rowsperpage);
	
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);		
			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			
			boolean end = currpage + 1 == totalpages && totalpages > 1;
			if (end)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int j = 0; j < totalpages; j++) {
					al.add(Integer.toString(j));
				}
				request.setAttribute("pages", al);
			}
			
			results = service.listDocumentTemplates(dto);
			
			if (!end && results.size() == 1) {
				long templateId = results.iterator().next().getId();
				response.sendRedirect("documentTemplate.do?template_id=" + templateId);
				return null;
			}
			
			request.setAttribute("results", results);
		} else {
			DocumentTemplateUtils.resetSearchForm(dtsf);
		}
		
		if (session.getAttribute("documentTemplateStatusList") == null) {
			session.setAttribute("documentTemplateStatusList", TracerUtils.getStatusList(TracingConstants.TABLE_DOCUMENT_TEMPLATE_STATUS, user.getDefaultlocale()));
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		dtsf.set_DATEFORMAT(user.getDateformat().getFormat());
		return mapping.findForward(TracingConstants.SEARCH_DOCUMENT_TEMPLATE);
	}
	
	private void getSortCriteria(DocumentTemplateSearchDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder(TracingConstants.TABLE_ID_TEMPLATES);
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "id");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}

}
