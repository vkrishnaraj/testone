package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for requesting functionality.
 */
public final class RequestOnHandForm extends ValidatorForm {

	private String ohd_ID;
	private String incident_ID;
	private String match_ID;
	private String reason;

	/**
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *          The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return Returns the match_ID.
	 */
	public String getMatch_ID() {
		return match_ID;
	}

	/**
	 * @param match_ID
	 *          The match_ID to set.
	 */
	public void setMatch_ID(String match_ID) {
		this.match_ID = match_ID;
	}

	/**
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @return Returns the ohd_ID.
	 */
	public String getOhd_ID() {
		return ohd_ID;
	}

	/**
	 * @param ohd_ID
	 *          The ohd_ID to set.
	 */
	public void setOhd_ID(String ohd_ID) {
		this.ohd_ID = ohd_ID;
	}
}