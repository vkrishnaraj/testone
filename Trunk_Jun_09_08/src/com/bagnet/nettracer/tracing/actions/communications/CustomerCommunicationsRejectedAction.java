package com.bagnet.nettracer.tracing.actions.communications;

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
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.forms.communications.CustomerCommunicationsTaskForm;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class CustomerCommunicationsRejectedAction extends CheckedAction {
	
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		CustomerCommunicationsTaskForm cctf = (CustomerCommunicationsTaskForm) form;
		
		List<IncidentActivityTaskDTO> results = new ArrayList<IncidentActivityTaskDTO>();
		IncidentActivityTaskSearchDTO dto = DomainUtils.fromForm(cctf);
		dto.setStatus(new Status(TracingConstants.STATUS_CUSTOMER_COMM_DENIED));
		setUserInfoOnDto(user, dto);
		getSortCriteria(dto, request);
		
		int currpage = 0;
		int rowcount = incidentActivityService.getIncidentActivityTaskCount(dto);
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);

		if (rowcount > 0) {
			currpage = cctf.getCurrpage() != null ? Integer.parseInt(cctf.getCurrpage()) : 0;
			if (cctf.getNextpage() != null && cctf.getNextpage().equals("1")) {
				currpage++;
			}
	
			if (cctf.getPrevpage() != null && cctf.getPrevpage().equals("1")) {
				currpage--;
			}
	
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
			
			results = incidentActivityService.listIncidentActivityTasks(dto);
			
			request.setAttribute("results", results);
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		cctf.set_DATEFORMAT(user.getDateformat().getFormat());
		
		return mapping.findForward(TracingConstants.CUSTOMER_COMMUNICATIONS_REJECTED);
	}
	
	private void setUserInfoOnDto(Agent user, IncidentActivityTaskSearchDTO dto) {
		dto.setAgent(user);
		dto.set_DATEFORMAT(user.getDateformat().getFormat());
		dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
	}
	
	private void getSortCriteria(IncidentActivityTaskSearchDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder(TracingConstants.TABLE_ID_CUST_COMM_REJECTED);
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "incidentId");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}
	
}