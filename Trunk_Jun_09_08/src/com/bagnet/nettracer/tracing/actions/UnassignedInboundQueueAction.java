package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;
import com.bagnet.nettracer.tracing.forms.UnassignedInboundQueueForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.taskmanager.InboundTasksUtils;
import com.bagnet.nettracer.tracing.utils.taskmanager.UnassignedInboundAgentElement;
import com.bagnet.nettracer.tracing.utils.taskmanager.UnassignedIncidentElement;

public class UnassignedInboundQueueAction extends Action {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
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
		
		ParamEncoder encoder = new ParamEncoder("incident");
		String sort = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		String dir = request.getParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		InboundTasksDTO dto = initDTO();	
		
		if(request.getParameter("assignIncident") != null && request.getParameter("assignAgent") != null){			
			updateAssignment(theForm, request);
			return null;/** Async ajax call, do not return forward **/
		}
		
		if (request.getParameter("autoassign") != null){
			List<UnassignedIncidentElement> incidentList = theForm.getIncidentList();
			List<InboundQueueTask> taskList = new ArrayList<InboundQueueTask>();
			if(incidentList != null){
				for(UnassignedIncidentElement incident:incidentList){
					if(incident.getTasks() != null){
						taskList.addAll(incident.getTasks());
					}
				}
			}
			InboundTasksUtils.autoAssignedTasks(theForm.getAgentMatrix(), taskList);
			theForm.setIncidentList(InboundTasksUtils.sortTaskList(incidentList, sort, dir));
			/** save agent matrix for use by display tag sorting **/
			theForm.setPreviousAgentMatrix(cloneAgentMatrix(theForm.getAgentMatrix()));
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if (request.getParameter("commit") != null){
			List<UnassignedIncidentElement> incidentList = theForm.getIncidentList();
			
			if(incidentList != null && updateAssignments(theForm, user, request, errors)){
				for(UnassignedIncidentElement incident:incidentList){
					if(incident.getTasks() != null){
						for(InboundQueueTask task:incident.getTasks()){
							Station assignedStation = null;
							if(task != null && task.getAssigned_agent() != null){
								assignedStation = task.getAssigned_agent().getStation();
							}
							InboundTasksUtils.saveTask(task, user, assignedStation);
						}
					}
				}
			}
			List<UnassignedIncidentElement> updatedTasks = InboundTasksUtils.getUnassignedIncidents(dto, user);
			theForm.setIncidentList(InboundTasksUtils.sortTaskList(updatedTasks, sort, dir));
			/** save agent matrix for use by display tag sorting **/
			theForm.setPreviousAgentMatrix(cloneAgentMatrix(theForm.getAgentMatrix()));
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if (request.getParameter("reset") != null){
			List<UnassignedIncidentElement> incidentList = theForm.getIncidentList();
			if(incidentList != null){
				for(UnassignedIncidentElement incident:incidentList){
					incident.getIncident().setAgentassigned(null);
					if(incident.getTasks() != null){
						for(InboundQueueTask task: incident.getTasks()){
							task.setAssigned_agent(null);
						}
					}
				}
			}
			InboundTasksUtils.sortTaskList(incidentList, sort, dir);
			/** save agent matrix for use by display tag sorting **/
			theForm.setPreviousAgentMatrix(cloneAgentMatrix(theForm.getAgentMatrix()));
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		if(request.getParameter("loadList") != null){
			List<UnassignedIncidentElement> incidentList = InboundTasksUtils.getUnassignedIncidents(dto, user); 
			theForm.setIncidentList(InboundTasksUtils.sortTaskList(incidentList, sort, dir));
			theForm.setAgentList(InboundTasksUtils.getInboundAgentList());
			theForm.setAgentMatrix(InboundTasksUtils.getInboundAgentMatrixList());
			return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
		}
		
		/** If no parameters are set, sort list and return **/
		theForm.setIncidentList(InboundTasksUtils.sortTaskList(theForm.getIncidentList(), sort, dir));
		if(theForm.getPreviousAgentMatrix() != null){
			/** use previous agent matrix to persist checkbox values with sorting **/
			theForm.setAgentMatrix(cloneAgentMatrix(theForm.getPreviousAgentMatrix()));
		}
		return mapping.findForward(TracingConstants.UNASSIGNED_INBOUND_QUEUE);
	}
	
	private Agent getAgentByID(int id, List<Agent>agents){
		if(-1 < id && agents != null){
			for(Agent agent:agents){
				if(agent.getAgent_ID() == id){
					return agent;
				}
			}
		}
		
		return null;
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
	
	/**
	 * Updates a single agent assignment, primarily used for ajax updates
	 * 
	 * @param theForm
	 * @param request
	 */
	private void updateAssignment(UnassignedInboundQueueForm theForm, HttpServletRequest request){
		Agent agent = getAgentByID(NumberUtils.toInt(request.getParameter("assignAgent")), theForm.getAgentList());
		int incidentId = NumberUtils.toInt(request.getParameter("assignIncident"));
		
		Incident incident = null;
		if(theForm.getIncidentList(incidentId) != null){
			incident = theForm.getIncidentList(incidentId).getIncident();
		}

		if(agent != null && incident != null){
			incident.setAgentassigned(agent);
			if(theForm.getIncidentList(incidentId).getTasks() != null){
				for(InboundQueueTask task:theForm.getIncidentList(incidentId).getTasks()){
					task.setAssigned_agent(agent);
				}
			}
		}
	}
	
	/**
	 * Updates all agent assignments, primarily used for form submits
	 * 
	 * @param theForm
	 * @param user
	 * @param request
	 * @param errors
	 * @return
	 */
	private boolean updateAssignments(UnassignedInboundQueueForm theForm, Agent user, HttpServletRequest request, ActionMessages errors){
		String[] agentAssignedIDs = request.getParameterValues("incident.incident.agentassigned.agent_ID");
		if(agentAssignedIDs == null || agentAssignedIDs.length < 1 || theForm.getIncidentList() == null || theForm.getIncidentList().size() != agentAssignedIDs.length){

			/* This is primarily for debugging purposes for getting dropdowns to work with display table, 
			 * we should never legitimately end up in this state.  If we do, then it is a bug that we need to fix */

			log.error("Failed to reassign agent. Agents Assigned IDs = {}", (agentAssignedIDs == null) ? null : agentAssignedIDs.length);
			ActionMessage error = new ActionMessage("error.no.agent");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return false;
		} else {
			for(int i = 0; i < theForm.getIncidentList().size(); i++){
				Agent agent = getAgentByID(NumberUtils.toInt(agentAssignedIDs[i]), theForm.getAgentList());
				theForm.getIncidentList(i).getIncident().setAgentassigned(agent);
				if(theForm.getIncidentList(i).getTasks() != null){
					for(InboundQueueTask task:theForm.getIncidentList(i).getTasks()){
						task.setAssigned_agent(agent);
					}
				}
			}
			return true;
		}
	}
	
	private List<UnassignedInboundAgentElement> cloneAgentMatrix(List<UnassignedInboundAgentElement> matrix){
		if(matrix != null){
			List<UnassignedInboundAgentElement> ret = new ArrayList<UnassignedInboundAgentElement>();
			for(UnassignedInboundAgentElement e:matrix){
				ret.add(e.clone());
			}
			return ret;
		} else {
			return null;
		}
	}
	
}


