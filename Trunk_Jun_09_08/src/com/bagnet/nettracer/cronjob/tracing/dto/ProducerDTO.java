package com.bagnet.nettracer.cronjob.tracing.dto;

public class ProducerDTO {
	private String incidentId;
	private String ohdLastTraced;

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getOhdLastTraced() {
		return ohdLastTraced;
	}

	public void setOhdLastTraced(String ohdLastTraced) {
		this.ohdLastTraced = ohdLastTraced;
	}
}
