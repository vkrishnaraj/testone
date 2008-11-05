package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @hibernate.class table="webservice_session"
 * 
 * @author matt
 *
 */
public class Webservice_Session implements Serializable{
	private int id;
	private String username;
	private String companycode_id;
	private String session_id;
	private Date date_active;
	
	/**
	 * @return the id
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="webservice_0"
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the username
	 * 
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the companycode_id
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCompanycode_id() {
		return companycode_id;
	}
	/**
	 * @param companycode_id the companycode_id to set
	 */
	public void setCompanycode_id(String companycode_id) {
		this.companycode_id = companycode_id;
	}
	/**
	 * @return the session_id
	 * 
	 * @hibernate.property type="string"
	 */
	public String getSession_id() {
		return session_id;
	}
	/**
	 * @param session_id the session_id to set
	 */
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	/**
	 * @return the date_active
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getDate_active() {
		return date_active;
	}
	/**
	 * @param date_active the date_active to set
	 */
	public void setDate_active(Date date_active) {
		this.date_active = date_active;
	}
	
	
}
