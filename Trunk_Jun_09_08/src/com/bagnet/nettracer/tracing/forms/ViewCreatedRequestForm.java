package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing all created requests.
 */
public final class ViewCreatedRequestForm extends ValidatorForm {

	private String status; //request status filter
	private String s_time; //start time
	private String e_time; //end time
	private String ohd_num; //on-hand id
	private String incident_ID; //matching incident id if any.

	/**
	 * @return Returns the e_time.
	 */
	public String getE_time() {
		return e_time;
	}

	/**
	 * @param e_time
	 *          The e_time to set.
	 */
	public void setE_time(String e_time) {
		this.e_time = e_time;
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
	 * @return Returns the ohd_num.
	 */
	public String getOhd_num() {
		return ohd_num;
	}

	/**
	 * @param ohd_num
	 *          The ohd_num to set.
	 */
	public void setOhd_num(String ohd_num) {
		this.ohd_num = ohd_num;
	}

	/**
	 * @return Returns the s_time.
	 */
	public String getS_time() {
		return s_time;
	}

	/**
	 * @param s_time
	 *          The s_time to set.
	 */
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}