package com.bagnet.nettracer.tracing.forms.audit;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class AuditCompanyForm extends ValidatorForm {

	private String companycode = "";
	private String s_createtime = "";
	private String e_createtime = "";
	private String agent; // create agent username

	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 *          The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
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
	 * @return Returns the e_createtime.
	 */
	public String getE_createtime() {
		return e_createtime;
	}

	/**
	 * @param e_createtime
	 *          The e_createtime to set.
	 */
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	/**
	 * @return Returns the s_createtime.
	 */
	public String getS_createtime() {
		return s_createtime;
	}

	/**
	 * @param s_createtime
	 *          The s_createtime to set.
	 */
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}
}