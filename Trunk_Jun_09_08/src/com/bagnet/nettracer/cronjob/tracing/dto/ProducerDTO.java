package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.Date;

public class ProducerDTO {
	private String incidentId;
	private Date ohdLastTraced;

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public Date getOhdLastTraced() {
		return ohdLastTraced;
	}

	public void setOhdLastTraced(Date ohdLastTraced) {
		this.ohdLastTraced = ohdLastTraced;
	}
}
