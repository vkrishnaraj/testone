package aero.nettracer.serviceprovider.ws_1_0.response;

import java.util.Calendar;

public class KioskResponse extends GenericResponse {
	private String incidentId;

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	private Calendar createDate;

}
