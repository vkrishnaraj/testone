package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing created requests.
 * There is no data-entry currently, therefore, left blank intentionally.
 */
public final class ViewRequestForm extends ValidatorForm {

	private String ohd_num;
	private String incident_num;

	/**
	 * @return Returns the incident_num.
	 */
	public String getIncident_num() {
		return incident_num;
	}

	/**
	 * @param incident_num
	 *          The incident_num to set.
	 */
	public void setIncident_num(String incident_num) {
		this.incident_num = incident_num;
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
}