package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing incoming requests.
 * There is no data-entry currently, therefore, left blank intentionally.
 */
public final class ViewIncomingRequestForm extends ValidatorForm {

	private String ohd_num;
	private String bag_tag;
	private String expedite;
	
	private String teletypeAddress;
	

	public String getTeletypeAddress() {
		return teletypeAddress;
	}

	public void setTeletypeAddress(String teletypeAddress) {
		this.teletypeAddress = teletypeAddress;
	}

	/**
	 * @return Returns the bag_tag.
	 */
	public String getBag_tag() {
		return bag_tag;
	}

	/**
	 * @param bag_tag
	 *          The bag_tag to set.
	 */
	public void setBag_tag(String bag_tag) {
		this.bag_tag = bag_tag;
	}

	/**
	 * @return Returns the expedite.
	 */
	public String getExpedite() {
		return expedite;
	}

	/**
	 * @param expedite
	 *          The expedite to set.
	 */
	public void setExpedite(String expedite) {
		this.expedite = expedite;
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