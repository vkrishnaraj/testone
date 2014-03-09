package com.bagnet.nettracer.tracing.utils.taskmanager.inbound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;

public class AutoAssignImpl implements AutoAssign{
	
	public static int INBOUND = 1;
	public static int ACAA = 2;
	public static int DAMAGED = 3;

	/* 
	 * For this implementation, the auto assign will first determine which agent have the ability to be assigned tasks base on the following:
	 * 		*For an agent to be assign a task of a particular type, the agent must be enabled for that type.
	 * 		*If the load for all agents is set to zero, then the agent can be assigned tasks base on the previous rule.
	 * 			If there is a non-zero load for any agent, then for any agent with a zero load the respective agent cannot be assign any tasks
	 * 
	 * The task will be assigned for each task type independently with zero regard to overall load across types.
	 * The tasks will be assigned by type in the follow order: ACAA, DAMAGED, INBOUND
	 */
	@Override
	public void autoAssignedTasks(List<UnassignedInboundAgentElement> matrix,List<UnassignedIncidentElement> incidentList) {
		updateAssignable(matrix);
		assign(getAgentList(ACAA, matrix), getIncidentList(ACAA, incidentList));
		assign(getAgentList(DAMAGED, matrix), getIncidentList(DAMAGED, incidentList));
		assign(getAgentList(INBOUND, matrix), getIncidentList(INBOUND, incidentList));
	}
	
	/**
	 * For an agent to be assign a task of a particular type, the agent must be enabled for that type.
	 * If the load for all agents is set to zero, then the agent can be assigned tasks base on the previous rule.
	 * 			If there is a non-zero load for any agent, then for any agent with a zero load the respective agent cannot be assign any tasks
	 * 
	 * @param matrix
	 */
	private void updateAssignable(List<UnassignedInboundAgentElement> matrix){
		double maxLoad = 0.0;
		if(matrix != null){
			for(UnassignedInboundAgentElement agent:matrix){
				maxLoad = Math.max(maxLoad, agent.getLoad());
			}
			for(UnassignedInboundAgentElement agent:matrix){
				if(maxLoad == 0.0 || agent.getLoad() > 0.0){
					agent.setEnableAssignments(true);
				} else {
					agent.setEnableAssignments(false);
				}
			}
		}
	}
	
	/**
	 * The assign method assumes that the incidentList is for a particular task type and that the agent matrix consists of only those agents that are
	 * able to be assign tasks of said type.
	 * 
	 * The assignment algorithm will assign each agent a task for the first round of assignments.  After the first round, the agents will be assign tasks
	 * with respect their assignment load.
	 * 
	 * @param matrix
	 * @param incidentList
	 */
	private void assign(List<UnassignedInboundAgentElement> matrix,List<UnassignedIncidentElement> incidentList){
		if(matrix == null || matrix.size() == 0 || incidentList == null || incidentList.size() == 0){
			return;
		}
		
		double maxLoad = 0;
		for(UnassignedInboundAgentElement agent:matrix){
			agent.setCurrentAssign(0.0);
			if(agent.getLoad() > maxLoad){
				maxLoad = agent.getLoad();
			}
		}
		
		boolean firstRound = true;
		int i = 0;
		for(UnassignedIncidentElement incident:incidentList){
			boolean unassigned = true;
			
			/*
			 * The primary exit condition for this loop is the boolean unassigned, however,
			 * will bail if cycled through entire agent list 
			 */
			for(int j = 0; j <= matrix.size() && unassigned; j++){
				UnassignedInboundAgentElement agent = matrix.get(i);
				
				if(firstRound){
					/* assign each agent a task on the first round */
					unassigned = false;
					assignIncident(agent.getAgent(), incident);
				} else {
					/* assign tasks respected to the agent load */
					agent.setCurrentAssign(agent.getCurrentAssign() + agent.getLoad());
					if(agent.getCurrentAssign() >= maxLoad){
						agent.setCurrentAssign(agent.getCurrentAssign() - maxLoad);
						unassigned = false;
						assignIncident(agent.getAgent(), incident);
					}
				}

				/* get next agent index */
				if(i+1 >= matrix.size()){
					i = 0;
					firstRound = false;
				} else {
					i++;
				}
			}
		}
	}
	
	/**
	 * Return task list for the given task type
	 * 
	 * @param type
	 * @param incidentList
	 * @return
	 */
	private List<UnassignedIncidentElement> getIncidentList(int type, List<UnassignedIncidentElement> incidentList){
		List<UnassignedIncidentElement> ret = new ArrayList<UnassignedIncidentElement>();
		for(UnassignedIncidentElement incident:incidentList){
			InboundQueueTask task = incident.getPriorityTask();
			if(type == INBOUND && task.isInbound()){
				ret.add(incident);
			}
			if(type == ACAA && task.isAcaa()){
				ret.add(incident);
			}
			if(type == DAMAGED && task.isDamaged()){
				ret.add(incident);
			}
		}
		return ret;
	}
	
	/**
	 * Return list of agents that have the ability to be assign the given task type
	 * 
	 * Sort by lowest load
	 * 
	 * @param type
	 * @param matrix
	 * @return
	 */
	private List<UnassignedInboundAgentElement> getAgentList(int type, List<UnassignedInboundAgentElement> matrix){
		List<UnassignedInboundAgentElement> ret = new ArrayList<UnassignedInboundAgentElement>();
		for(UnassignedInboundAgentElement agent:matrix){
			if(agent.isEnableAssignments()){
				if(type == INBOUND && agent.isInbound()){
					ret.add(agent);
				}
				if(type == ACAA && agent.isAcaa()){
					ret.add(agent);
				}
				if(type == DAMAGED && agent.isDamaged()){
					ret.add(agent);
				}
			}
		}
		Collections.sort(ret, new AgentSort());
		return ret;
	}
	
	/**
	 * Assign incident and all assoicated tasks
	 * 
	 * @param agent
	 * @param incident
	 */
	private void assignIncident(Agent agent, UnassignedIncidentElement incident){
		incident.getIncident().setAgentassigned(agent);
		if(incident.getTasks() != null){
			for(InboundQueueTask task:incident.getTasks()){
				task.setAssigned_agent(agent);
			}
		}
	}
	
	private class AgentSort implements Comparator<UnassignedInboundAgentElement>{
		@Override
		public int compare(UnassignedInboundAgentElement o1, UnassignedInboundAgentElement o2) {
			return o1.getLoad() > o2.getLoad()?1:-1;
		}
	}
}
