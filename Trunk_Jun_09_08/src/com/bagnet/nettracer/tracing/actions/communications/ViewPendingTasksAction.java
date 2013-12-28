package com.bagnet.nettracer.tracing.actions.communications;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import com.bagnet.nettracer.tracing.forms.communications.TaskSearchForm;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class ViewPendingTasksAction extends CheckedAction {

	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_TASKS_VIEW_PENDING, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		TaskSearchForm tsf = (TaskSearchForm) form;

		List<IncidentActivityTaskDTO> results = new ArrayList<IncidentActivityTaskDTO>();
		IncidentActivityTaskSearchDTO dto = DomainUtils.fromForm(tsf);
		dto.setAgentLocale(new Locale(user.getCurrentlocale()));
		setSearchSpecificInfoOnDto(tsf, dto, user);
		getSortCriteria(dto, request);
		
		int currpage = 0;
		int rowcount = 0;
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);

		if (TracingConstants.COMMAND_SEARCH.equals(tsf.getCommand())) {
			
			currpage = tsf.getCurrpage() != null ? Integer.parseInt(tsf.getCurrpage()) : 0;
			if (tsf.getNextpage() != null && tsf.getNextpage().equals("1")) {
				currpage++;
			}
	
			if (tsf.getPrevpage() != null && tsf.getPrevpage().equals("1")) {
				currpage--;
			}
	
			rowcount = incidentActivityService.getIncidentActivitiesNotInWorkCount(dto);
			
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
			
			results = incidentActivityService.listIncidentActivitiesNotInWork(dto);
			request.setAttribute("results", results);
		} else {
			DomainUtils.resetTaskForm(tsf);
		}
		
		if (session.getAttribute("incidentActivityStatusList") == null) {
			session.setAttribute("incidentActivityStatusList", TracerUtils.getStatusList(TracingConstants.TABLE_COMMUNICATIONS_STATUS, user.getDefaultlocale(), "description"));
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		tsf.set_DATEFORMAT(user.getDateformat().getFormat());
		return mapping.findForward(TracingConstants.TASKS_VIEW_PENDING);
	}

	private void setSearchSpecificInfoOnDto(TaskSearchForm tsf, IncidentActivityTaskSearchDTO dto, Agent user) {
		setUserInfoOnDto(user, dto);
		dto.setActive(true);
		if (tsf.getAgentName() != null && !tsf.getAgentName().isEmpty()) {
			dto.setAgent(AdminUtils.getAgentBasedOnUsername(tsf.getAgentName(), user.getCompanycode_ID()));
		}
		if (tsf.getStatus() > 0) {
			dto.setStatus(new Status(tsf.getStatus()));
		}
		dto.setPassengerLastName(tsf.getPassengerLastName());
		dto.setPassengerFirstName(tsf.getPassengerFirstName());
	}

	private void setUserInfoOnDto(Agent user, IncidentActivityTaskSearchDTO dto) {
		dto.set_DATEFORMAT(user.getDateformat().getFormat());
		dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
	}

	private void getSortCriteria(IncidentActivityTaskSearchDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder(TracingConstants.TABLE_ID_TASKS_NOT_IN_WORK);
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "openDate");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}

}
