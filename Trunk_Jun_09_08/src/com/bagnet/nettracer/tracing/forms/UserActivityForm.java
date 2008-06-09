package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for active tracing form.
 */
public class UserActivityForm extends ValidatorForm {

	private String s_time;
	private String e_time;
	private String agent;
	private String activity_status;

	/**
	 * @return Returns the activity_status.
	 */
	public String getActivity_status() {
		return activity_status;
	}

	/**
	 * @param activity_status
	 *          The activity_status to set.
	 */
	public void setActivity_status(String activity_status) {
		this.activity_status = activity_status;
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
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