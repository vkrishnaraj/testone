package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.Date;


public class UpdateDTO {
	private String incidentId;
	private Date ohdLastTraced;
	
	public UpdateDTO(String incidentId, Date ohdLastTraced) {
		this.incidentId = incidentId;
		this.ohdLastTraced = ohdLastTraced;
	}

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

