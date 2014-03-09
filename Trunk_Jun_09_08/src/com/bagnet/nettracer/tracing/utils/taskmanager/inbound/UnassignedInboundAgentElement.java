package com.bagnet.nettracer.tracing.utils.taskmanager.inbound;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;

public class UnassignedInboundAgentElement implements Cloneable {
	private Agent agent;
	private double load;
	
	private boolean inbound;
	private boolean acaa;
	private boolean damaged;
	
	private boolean enableAssignments;
	
	private int maxAssign;
	private double currentAssign;
	
	private List<InboundQueueTask> taskList;
	
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public double getLoad() {
		return load;
	}
	public void setLoad(double load) {
		this.load = load;
	}
	public boolean isInbound() {
		return inbound;
	}
	public void setInbound(boolean inbound) {
		this.inbound = inbound;
	}
	public boolean isAcaa() {
		return acaa;
	}
	public void setAcaa(boolean acaa) {
		this.acaa = acaa;
	}
	public boolean isDamaged() {
		return damaged;
	}
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	public int getMaxAssign() {
		return maxAssign;
	}
	public void setMaxAssign(int maxAssign) {
		this.maxAssign = maxAssign;
	}
	public double getCurrentAssign() {
		return currentAssign;
	}
	public void setCurrentAssign(double currentAssign) {
		this.currentAssign = currentAssign;
	}
	public List<InboundQueueTask> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<InboundQueueTask> taskList) {
		this.taskList = taskList;
	}
	
	public int getRemainingSlots(){
		if(taskList != null){
			return maxAssign - taskList.size(); 
		} else {
			return maxAssign;
		}
	}
	
	@Override
	public UnassignedInboundAgentElement clone(){
		try {
			UnassignedInboundAgentElement ret = new UnassignedInboundAgentElement();
			BeanUtils.copyProperties(this, ret);
			return ret;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	public boolean isEnableAssignments() {
		return enableAssignments;
	}
	public void setEnableAssignments(boolean enableAssignments) {
		this.enableAssignments = enableAssignments;
	}
}
