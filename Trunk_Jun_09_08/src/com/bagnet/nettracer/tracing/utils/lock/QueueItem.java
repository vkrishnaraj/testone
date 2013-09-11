package com.bagnet.nettracer.tracing.utils.lock;

import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;

public class QueueItem {
	boolean lockIncidentReturn;
	List<ActionMessage> lockActionMessagesReturn;
	Agent agent;
	Incident incident;
	String incidentId;
	int queueItemType;
	
	
	public boolean isLockIncidentReturn() {
		return lockIncidentReturn;
	}
	public void setLockIncidentReturn(boolean lockIncidentReturn) {
		this.lockIncidentReturn = lockIncidentReturn;
	}
	public List<ActionMessage> getLockActionMessagesReturn() {
		return lockActionMessagesReturn;
	}
	public void setLockActionMessagesReturn(
			List<ActionMessage> lockActionMessagesReturn) {
		this.lockActionMessagesReturn = lockActionMessagesReturn;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	public int getQueueItemType() {
		return queueItemType;
	}
	public void setQueueItemType(int queueItemType) {
		this.queueItemType = queueItemType;
	}
	
	/**
	 * Using the QueueItem Object itself as the lock.  This method notifies the objectWait.
	 */
	public void objectNotification(){
		synchronized(this){
			this.notify();
		}
	}
	
	/**
	 * Using the QueueItem Object itself as the lock.  This method places a lock with a maxWaitTime.
	 */
	public void objectWait(long maxTime){
		synchronized(this){
			try {
				this.wait(maxTime);
			} catch (InterruptedException e) {
				//continue
			}
		}
	}
	
}
