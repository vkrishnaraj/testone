package com.bagnet.nettracer.tracing.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.utils.taskmanager.UnassignedInboundAgentElement;

public class UnassignedInboundQueueForm extends ValidatorForm {
	private static final long serialVersionUID = -2791233648024154545L;
	
	private String test;
	
	private List<UnassignedInboundAgentElement> agentMatrix;
	private List<Agent> agentList;
	private List<InboundQueueTask> taskList;
	public List<UnassignedInboundAgentElement> getAgentMatrix() {
		return agentMatrix;
	}
	public void setAgentMatrix(List<UnassignedInboundAgentElement> agentMatrix) {
		this.agentMatrix = agentMatrix;
	}
	public List<Agent> getAgentList() {
		return agentList;
	}
	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
	}
	public List<InboundQueueTask> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<InboundQueueTask> taskList) {
		this.taskList = taskList;
	}

	
	public void reset(ActionMapping mapping, 
			HttpServletRequest request) {
		if(agentMatrix != null){
			for(UnassignedInboundAgentElement agent:agentMatrix){
				agent.setAcaa(false);
				agent.setDamaged(false);
				agent.setInbound(false);
			}
		}
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		System.out.println(test);
		
		this.test = test;
	}
	
	public UnassignedInboundAgentElement getAgentMatrix(int i){
		if(agentMatrix != null && agentMatrix.size() > i){
			return agentMatrix.get(i);
		} else {
			return null;
		}
	}
	
	public InboundQueueTask getTaskList(int i){
		if(taskList != null && taskList.size() > i){
			return taskList.get(i);
		} else {
			return null;
		}
	}
}
