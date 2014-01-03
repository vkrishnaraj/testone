package com.bagnet.nettracer.tracing.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;
import com.bagnet.nettracer.tracing.forms.UnassignedInboundQueueForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.InboundTasksUtils;

public class UnassignedInboundQueueAction extends Action {
	
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

		
		UnassignedInboundQueueForm theForm = (UnassignedInboundQueueForm) form;
		request.setAttribute("unassignedInboundQueueForm", theForm);
		session.setAttribute("unassignedInboundQueueForm", theForm);
		request.setAttribute("agentList", InboundTasksUtils.getInboundAgentList());
		
		ParamEncoder encoder = new ParamEncoder("task");
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		InboundTasksDTO dto = initDTO();
		
		if (request.getParameter("autoassign") != null){
			List<InboundQueueTask> tasks = InboundTasksUtils.autoAssignedTasks(theForm.getAgentMatrix(), theForm.getTaskList());
			theForm.setTaskList(InboundTasksUtils.sortTaskList(tasks, sort, dir));
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if (request.getParameter("update") != null){
			//sort by agent (unassigned first), then type
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if (request.getParameter("commit") != null){
			List<InboundQueueTask> tasks = theForm.getTaskList();
			if(tasks != null){
				for(InboundQueueTask task:tasks){
					InboundTasksUtils.saveTask(task, user);
				}
			}
			List<InboundQueueTask> updatedTasks = InboundTasksUtils.getTasks(dto, user);
			theForm.setTaskList(InboundTasksUtils.sortTaskList(updatedTasks, sort, dir));
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if (request.getParameter("reset") != null){
			List<InboundQueueTask> tasks = theForm.getTaskList();
			if(tasks != null){
				for(InboundQueueTask task:tasks){
					task.setAssigned_agent(null);
					task.getIncident().setAgentassigned(null);
				}
			}
			InboundTasksUtils.sortTaskList(tasks, sort, dir);
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		//fetch all unassigned items
		List<InboundQueueTask> tasks = InboundTasksUtils.getTasks(dto, user);
		theForm.setTaskList(InboundTasksUtils.sortTaskList(tasks, sort, dir));
		
		theForm.setAgentList(InboundTasksUtils.getInboundAgentList());
		theForm.setAgentMatrix(InboundTasksUtils.getInboundAgentMatrixList());
		return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
	}
	
	private InboundTasksDTO initDTO(){
		InboundTasksDTO dto = new InboundTasksDTO();
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		dto.setStatus(status);
		
		dto.setAssigned_agent(null);
		dto.setSearchUnassignedTasks(true);
		return dto;
	}
	
}


