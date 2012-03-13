package com.bagnet.nettracer.tracing.actions.lfc;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;

public class SalvageItemContainer {

	private LFSalvage salvage;
	private LFFound found;
	private Agent agent;
	private boolean addItem;
	
	public SalvageItemContainer(LFSalvage salvage, LFFound found, Agent agent, boolean addItem) {
		this.salvage = salvage;
		this.found = found;
		this.agent = agent;
		this.addItem = addItem;
	}
	
	public LFSalvage getSalvage() {
		return salvage;
	}
	
	public void setSalvage(LFSalvage salvage) {
		this.salvage = salvage;
	}
	
	public LFFound getFound() {
		return found;
	}
	
	public void setFound(LFFound found) {
		this.found = found;
	}
	
	public Agent getAgent() {
		return agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public boolean isAddItem() {
		return addItem;
	}
	
	public void setAddItem(boolean addItem) {
		this.addItem = addItem;
	}
	
}
