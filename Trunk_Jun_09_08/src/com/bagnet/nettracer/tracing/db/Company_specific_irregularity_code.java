/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="company_irregularity_codes"
 * @hibernate.cache usage="read-write"
 */
public class Company_specific_irregularity_code implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6577844701884219201L;
	private int code_id;
	private int loss_code;
	private String description;
	private int report_type;
	private boolean show_to_limited_users;
	private boolean active;
	private boolean controllable;
	private boolean transferStation;
	private boolean departStation;
	private boolean destinationStation;


	private Company company;

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the report_type.
	 */
	public int getReport_type() {
		return report_type;
	}

	/**
	 * @param report_type
	 *          The report_type to set.
	 */
	public void setReport_type(int report_type) {
		this.report_type = report_type;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="code_id"
	 * 
	 * 
	 * @hibernate.generator-param name="sequence"
	 *                            value="company_irregularity_codes_0"
	 * 
	 * 
	 * @return Returns the code_id.
	 */
	public int getCode_id() {
		return code_id;
	}

	/**
	 * @param code_id
	 *          The code_id to set.
	 */
	public void setCode_id(int code_id) {
		this.code_id = code_id;
	}

	/**
	 * @return Returns the company.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the loss_code.
	 */
	public int getLoss_code() {
		return loss_code;
	}

	/**
	 * @param loss_code
	 *          The loss_code to set.
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}

	public String getCombination() {
		return loss_code + " - " + description;
	}

	/**
	 * @return the show_to_limited_users
	 * @hibernate.property type="boolean"
	 */
	public boolean isShow_to_limited_users() {
		return show_to_limited_users;
	}

	/**
	 * @param show_to_limited_users
	 *          the show_to_limited_users to set
	 */
	public void setShow_to_limited_users(boolean show_to_limited_users) {
		this.show_to_limited_users = show_to_limited_users;
	}

	/**
	 * @return whether the loss code is active or not
	 * @hibernate.property type="boolean"
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * @param active : the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return whether the loss code is controllable or not
	 * @hibernate.property type="boolean"
	 */
	public boolean isControllable() {
		return controllable;
	}
	
	/**
	 * @param controllable : the controllable to set
	 */
	public void  setControllable(boolean controllable) {
		this.controllable = controllable;
	}
	
	public String getActiveDisp() {
		return active ? "Yes" : "No";
	}
	
	public String getControllableDisp() {
		return controllable ? "Yes" : "No";
	}
	
	public String getCodeDescription(){
		return getLoss_code()+" - "+getDescription();
	}

	/**
	 * @return whether the loss code is transferStation or not
	 * @hibernate.property type="boolean"
	 */
	public boolean isTransferStation() {
		return transferStation;
	}
	
	/**
	 * @param transferStation : the transferStation to set
	 */
	public void setTransferStation(boolean transferStation) {
		this.transferStation = transferStation;
	}

	/**
	 * @return whether the loss code is departStation or not
	 * @hibernate.property type="boolean"
	 */
	public boolean isDepartStation() {
		return departStation;
	}
	
	/**
	 * @param departStation : the departStation to set
	 */
	public void setDepartStation(boolean departStation) {
		this.departStation = departStation;
	}

	/**
	 * @return whether the loss code is destinationStation or not
	 * @hibernate.property type="boolean"
	 */
	public boolean isDestinationStation() {
		return destinationStation;
	}
	
	/**
	 * @param destinationStation : the destinationStation to set
	 */
	public void setDestinationStation(boolean destinationStation) {
		this.destinationStation = destinationStation;
	}
}