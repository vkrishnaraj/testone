package com.bagnet.nettracer.tracing.history;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident;

public class IncidentHistoryObject extends HistoryObject {

	private Incident incident;
	private boolean hasTraceResults;
	
	public IncidentHistoryObject() {
		super();
	}
	
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public boolean isHasTraceResults() {
		return hasTraceResults;
	}

	public void setHasTraceResults(boolean hasTraceResults) {
		this.hasTraceResults = hasTraceResults;
	}
	
	@Override
	public String getUniqueId() {
		return date.getTime() + ":" + getType() + ":" + incident.getIncident_ID();
	}

	@Override
	public int getType() {
		return TracingConstants.HISTORY_OBJ_TYPE_INCIDENT;
	}

}
