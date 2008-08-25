package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Byron Smith
 * 
 * This class represents bdo administration form
 */

public final class MaintainDeliveryCompanyForm extends ValidatorForm {
	// May not be necessary
	private int station_ID;

	private int delivercompany_ID;
	private int serviceLevel_ID;
	private String name;
	private String address;
	private String phone;
	private String companySearchName;
	private String description;
	private String deliveryCompanyName;
	private String integration_type;
	
	// May not be necessary
	public void setStation_ID(int id) {
		this.station_ID = id;
	}

	// May not be necessary
	public int getStation_ID() {
		return station_ID;
	}
	
	public void setServiceLevel_ID(int id) {
		this.serviceLevel_ID = id;
	}
	
	public int getServiceLevel_ID() {
		return serviceLevel_ID;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setDelivercompany_ID(int id) {
		this.delivercompany_ID = id;
	}
	
	public int getDelivercompany_ID() {
		return delivercompany_ID;
	}
	
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}
	
	public void setDeliveryCompanyName(String dcn) {
		this.deliveryCompanyName = dcn;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setCompanySearchName(String searchName) {
		this.companySearchName = searchName;
	}
	
	public String getCompanySearchName() {
		return companySearchName;
	}

	/**
	 * @return the integration_type
	 */
	public String getIntegration_type() {
		return integration_type;
	}

	/**
	 * @param integration_type the integration_type to set
	 */
	public void setIntegration_type(String integration_type) {
		this.integration_type = integration_type;
	}
}