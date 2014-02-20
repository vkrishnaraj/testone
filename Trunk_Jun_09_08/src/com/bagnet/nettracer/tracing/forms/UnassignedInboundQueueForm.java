package com.bagnet.nettracer.tracing.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.taskmanager.UnassignedInboundAgentElement;
import com.bagnet.nettracer.tracing.utils.taskmanager.UnassignedIncidentElement;

public class UnassignedInboundQueueForm extends ValidatorForm {
	private static final long serialVersionUID = -2791233648024154545L;
	
	private List<UnassignedInboundAgentElement> agentMatrix;
	
	/**
	 * When sorting with display tag, the check box values are not being submitted.
	 * When page is submitted, store agentMatrix so that check box values can we set in the action when using sort.
	 */
	private List<UnassignedInboundAgentElement> previousAgentMatrix;
	
	
	private List<Agent> agentList;
	
	private List<UnassignedIncidentElement> incidentList;
	
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
	
	public UnassignedInboundAgentElement getAgentMatrix(int i){
		if(agentMatrix != null && agentMatrix.size() > i){
			return agentMatrix.get(i);
		} else {
			return null;
		}
	}
	
	public List<UnassignedIncidentElement> getIncidentList() {
		return incidentList;
	}
	public void setIncidentList(List<UnassignedIncidentElement> incidentList) {
		this.incidentList = incidentList;
	}
	
	public UnassignedIncidentElement getIncidentList(int i){
		if(incidentList != null && incidentList.size() > i){
			return incidentList.get(i);
		} else {
			return null;
		}
	}
	public List<UnassignedInboundAgentElement> getPreviousAgentMatrix() {
		return previousAgentMatrix;
	}
	public void setPreviousAgentMatrix(List<UnassignedInboundAgentElement> previousAgentMatrix) {
		this.previousAgentMatrix = previousAgentMatrix;
	}
}
