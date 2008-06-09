package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.action.ActionForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the claim form that is used for accessing claim
 * functionality
 */
public final class ClaimsToBeProcessedForm extends ActionForm {

	private String inc_num;
	private String s_time;
	private String e_time;

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
	 * @return Returns the inc_num.
	 */
	public String getInc_num() {
		return inc_num;
	}

	/**
	 * @param inc_num
	 *          The inc_num to set.
	 */
	public void setInc_num(String inc_num) {
		this.inc_num = inc_num;
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