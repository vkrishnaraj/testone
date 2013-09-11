package com.bagnet.nettracer.tracing.utils.lock;

import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;

public interface LockFile {
	public boolean lockIncident(Agent agent, Incident inc);
	public List<ActionMessage> getLockActionMessages(String incidentId, Agent agent);
}
