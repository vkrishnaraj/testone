package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for active tracing form.
 */
public class ViewMassOnHandsForm extends ValidatorForm {

	private String ohd_num;
	private String s_time; //start time
	private String e_time; //end time

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
}