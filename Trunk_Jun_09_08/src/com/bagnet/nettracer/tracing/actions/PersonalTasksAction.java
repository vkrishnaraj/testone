package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;
import com.bagnet.nettracer.tracing.forms.PersonalTasksForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.InboundTasksUtils;

public class PersonalTasksAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		// check session
		TracerUtils.checkSession(session);

		ActionMessages errors = new ActionMessages();
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
				.findForward(TracingConstants.NO_PERMISSION));


		PersonalTasksForm theForm = (PersonalTasksForm) form;
		request.setAttribute("personalTasksForm", theForm);
		session.setAttribute("personalTasksForm", theForm);
		InboundTasksDTO dto = theForm.getDto();
		
		if (request.getParameter("getNext") != null){
			InboundQueueTask task = InboundTasksUtils.getNextAssignedTask(user);
			if(task == null || task.getInboundqueue() == null || task.getInboundqueue().getIncident() == null){
				ActionMessage error = new ActionMessage("personaltask.error.notaskavailable");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				theForm.setTaskList(getPaginatedList(request, theForm, user, dto));
				return mapping.findForward(TracingConstants.PERSONAL_TASKS);
			}
			response.sendRedirect("searchIncident.do?incident=" + task.getInboundqueue().getIncident().getIncident_ID());
		}
		
		if (request.getParameter("closeTaskId") != null){
			Long taskId = new Long(request.getParameter("closeTaskId"));
			InboundQueueTask task = InboundTasksUtils.getTask(taskId);
			InboundTasksUtils.closeTask(task, user);
			theForm.setTaskList(getPaginatedList(request, theForm, user, dto));
			return mapping.findForward(TracingConstants.PERSONAL_TASKS);
		}
		
		if (request.getParameter("reset") != null){
			//first time loading page, create new dto with default values
			dto = initDTO(dto,user);
			theForm.setDto(dto);
			request.setAttribute("reset", null);
		}
		
		
		theForm.setTaskList(getPaginatedList(request, theForm, user, dto));
		if(theForm.getTaskList() != null && theForm.getTaskList().size() > 0){
			theForm.setHasNextTask(true);
		} else {
			theForm.setHasNextTask(false);
		}
		return mapping.findForward(TracingConstants.PERSONAL_TASKS);
	}
	
	/**
	 * Returns a paginated list of bagdrop elements based on the given search and sort criteria specified by BagDropDTO and DisplayTag
	 * 
	 * @param request
	 * @param theForm
	 * @param agent
	 * @param dto
	 * @return
	 */
	private List<InboundQueueTask> getPaginatedList(HttpServletRequest request, PersonalTasksForm theForm, Agent agent, InboundTasksDTO dto){
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, request.getSession());
		int currpage = theForm.getCurrpage() != null ? Integer.parseInt(theForm.getCurrpage()) : 0;
		if (theForm.getNextpage() != null && theForm.getNextpage().equals("1"))
			currpage++;
		if (theForm.getPrevpage() != null && theForm.getPrevpage().equals("1"))
			currpage--;
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("currpage", Integer.toString(currpage));
		
		
		//zero out limits and sort for count
		dto.setMaxResults(0);
		dto.setStartIndex(0);
		dto.setSort(null);
		long rowcount = InboundTasksUtils.getTasksCount(dto);
		
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);

		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		if (currpage + 1 == totalpages)
			request.setAttribute("end", "1");
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}
		
		//set limits and sort for list
		getSortCriteria(dto, request);
		dto.setMaxResults(rowsperpage);
		dto.setStartIndex(currpage*rowsperpage);
		return InboundTasksUtils.getTasks(dto, agent);
	}
	
	/**
	 * Decodes Sort Criteria for DisplayTag
	 * 
	 * @param dto
	 * @param request
	 */
	private void getSortCriteria(InboundTasksDTO dto, HttpServletRequest request) {
		ParamEncoder encoder = new ParamEncoder("task");
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		dto.setSort(sort != null ? sort : "id");
		
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		dto.setDir(dir != null ? dir : TracingConstants.SORT_ASCENDING);		
	}
	
	/**
	 * Returns new instance of BagDropDTO with default values
	 * 
	 * @param dto
	 * @param user
	 * @return
	 */
	private InboundTasksDTO initDTO(InboundTasksDTO dto, Agent user){
			dto = new InboundTasksDTO();
			
			Status status = new Status();
			status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
			dto.setStatus(status);
			dto.setAssigned_agent(user);
			
			return dto;
	}
}
