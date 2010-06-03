package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Matt Zhu
 * 
 * This class represent bdo form
 */
public final class SearchBDOForm extends ValidatorForm {

	private String incident_ID;
	private String ohd_ID;
	private String bdo_ID;
	private String lastname;
	private String firstname;
	private String middlename;

	private String s_createtime;
	private String e_createtime;

	private String companycreated_ID;
	private int stationcreated_ID;
	private String agent;

	private int delivercompany_ID;
	private int servicelevel_ID;
	private String deliverydate;

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
	 * @return Returns the companycreated_ID.
	 */
	public String getCompanycreated_ID() {
		return companycreated_ID;
	}

	/**
	 * @param companycreated_ID
	 *          The companycreated_ID to set.
	 */
	public void setCompanycreated_ID(String companycreated_ID) {
		this.companycreated_ID = companycreated_ID;
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
	 * @return Returns the firstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *          The firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	 * @return Returns the lastname.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return Returns the middlename.
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
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

	/**
	 * @return Returns the stationcreated_ID.
	 */
	public int getStationcreated_ID() {
		return stationcreated_ID;
	}

	/**
	 * @param stationcreated_ID
	 *          The stationcreated_ID to set.
	 */
	public void setStationcreated_ID(int stationcreated_ID) {
		this.stationcreated_ID = stationcreated_ID;
	}

	/**
	 * @return Returns the delivercompany_ID.
	 */
	public int getDelivercompany_ID() {
		return delivercompany_ID;
	}

	/**
	 * @param delivercompany_ID
	 *          The delivercompany_ID to set.
	 */
	public void setDelivercompany_ID(int delivercompany_ID) {
		this.delivercompany_ID = delivercompany_ID;
	}

	/**
	 * @return Returns the deliverydate.
	 */
	public String getDeliverydate() {
		return deliverydate;
	}

	public String getBdo_ID() {
			return bdo_ID;
		
	}

	public void setBdo_ID(String bdo_ID) {
			this.bdo_ID = bdo_ID;
	}

	/**
	 * @param deliverydate
	 *          The deliverydate to set.
	 */
	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}

	/**
	 * @return Returns the servicelevel_ID.
	 */
	public int getServicelevel_ID() {
		return servicelevel_ID;
	}

	/**
	 * @param servicelevel_ID
	 *          The servicelevel_ID to set.
	 */
	public void setServicelevel_ID(int servicelevel_ID) {
		this.servicelevel_ID = servicelevel_ID;
	}
}