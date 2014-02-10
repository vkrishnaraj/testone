package com.bagnet.nettracer.tracing.actions.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.TemplateDTO;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.forms.templates.TemplateSearchForm;
import com.bagnet.nettracer.tracing.service.TemplateService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class TemplateSearchAction extends CheckedAction {

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
		
		TemplateSearchForm dtsf = (TemplateSearchForm) form;
		if (TracingConstants.COMMAND_CREATE.equals(dtsf.getCommand()) || TracingConstants.COMMAND_CREATE.equals(request.getParameter(TracingConstants.COMMAND_CREATE))) {
			response.sendRedirect("editTemplate.do?create=1");
			return null;
		}

		List<TemplateDTO> results = new ArrayList<TemplateDTO>();
		TemplateSearchDTO dto = DomainUtils.fromForm(dtsf);
		setUserInfoOnDto(user, dto);
		getSortCriteria(dto, request);
		
		int currpage = 0;
		int rowcount = 0;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);

		if (!TracingConstants.COMMAND_CLEAR.equals(dtsf.getCommand())) {
			
			currpage = dtsf.getCurrpage() != null ? Integer.parseInt(dtsf.getCurrpage()) : 0;
			if (dtsf.getNextpage() != null && dtsf.getNextpage().equals("1")) {
				currpage++;
			}
	
			if (dtsf.getPrevpage() != null && dtsf.getPrevpage().equals("1")) {
				currpage--;
			}
	
			rowcount = templateService.getTemplateCount(dto);
			
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
			
			results = templateService.listTemplates(dto);
			
			if (!end && results.size() == 1) {
				long templateId = results.iterator().next().getId();
				response.sendRedirect("editTemplate.do?template_id=" + templateId);
				return null;
			}
			
			request.setAttribute("results", results);
		}
		
		if (session.getAttribute("templateStatusList") == null) {
			session.setAttribute("templateStatusList", TracerUtils.getStatusList(TracingConstants.TABLE_TEMPLATE_STATUS, user.getDefaultlocale()));
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		dtsf.set_DATEFORMAT(user.getDateformat().getFormat());
		return mapping.findForward(TracingConstants.SEARCH_TEMPLATE);
	}

	private void setUserInfoOnDto(Agent user, TemplateSearchDTO dto) {
		dto.set_DATEFORMAT(user.getDateformat().getFormat());
		dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
	}
	
	private void getSortCriteria(TemplateSearchDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder(TracingConstants.TABLE_ID_TEMPLATES);
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "id");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}

}
