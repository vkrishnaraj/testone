/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.List;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="CompanyCode"
 */
public class Company implements Serializable {
	private String CompanyCode_ID;
	private String companydesc;
	private String address1;
	private String address2;
	private String city;
	private String state_ID;
	private String countrycode_ID;
	private String zip;
	private String phone;
	private String email_address;
	private Company_Specific_Variable variable;
	private List irregularity_codes;

	/**
	 * @hibernate.property type="string"
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *          The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *          The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *          The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *          The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the zip.
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *          The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return Returns the email_address.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail_address() {
		return email_address;
	}
	/**
	 * @param email_address The email_address to set.
	 */
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	/**
	 * @return Returns the companyCode_ID.
	 * 
	 * @hibernate.id generator-class="assigned" type="string" length="3"
	 *               column="CompanyCode_ID"
	 */
	public String getCompanyCode_ID() {
		return CompanyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		CompanyCode_ID = companyCode_ID;
	}

	/**
	 * @return Returns the companydesc.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCompanydesc() {
		return companydesc;
	}

	/**
	 * @param companydesc
	 *          The companydesc to set.
	 */
	public void setCompanydesc(String companydesc) {
		this.companydesc = companydesc;
	}


	/**
	 * @hibernate.property type="string"
	 * @return Returns the countrycode_ID.
	 */
	public String getCountrycode_ID() {
		return (countrycode_ID == null || countrycode_ID.length() ==  0 ? TracingConstants.DEFAULT_COUNTRY : countrycode_ID);
	}

	/**
	 * @param countrycode_ID
	 *          The countrycode_ID to set.
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}

	/**
	 * @hibernate.property type="string"
	 * 
	 * @return Returns the state_ID.
	 */
	public String getState_ID() {
		return state_ID;
	}

	/**
	 * @param state_ID
	 *          The state_ID to set.
	 */
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company_Specific_Variable"
	 *                        column="variablecode"
	 * @return Returns the variable.
	 */
	public Company_Specific_Variable getVariable() {
		return variable;
	}

	/**
	 * @param variable
	 *          The variable to set.
	 */
	public void setVariable(Company_Specific_Variable variable) {
		this.variable = variable;
	}

	/**
	 * @hibernate.list cascade="save-update" inverse="true"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"
	 * @hibernate.index column="loss_code"
	 * @hibernate.key column="companycode_ID"
	 * @return Returns the irregularity_codes.
	 */
	public List getIrregularity_codes() {
		return irregularity_codes;
	}

	/**
	 * @param irregularity_codes
	 *          The irregularity_codes to set.
	 */
	public void setIrregularity_codes(List irregularity_codes) {
		this.irregularity_codes = irregularity_codes;
	}

	public boolean equals(Company cmp) {
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(this.getCompanyCode_ID(), cmp.getCompanyCode_ID())) {
			ret = false;
		}
		return ret;
	}
}