package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

@Entity
@Table(name = "report")
@Proxy(lazy = false)
public class Report implements Serializable {

	private int id;
	private int number;
	private String resource_key;
	private String locale = TracingConstants.DEFAULT_LOCALE;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	@Basic
	@Column(nullable = false)
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *          the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the resource_key
	 */
	@Basic
	@Column(nullable=false, length = 36)
	public String getResource_key() {
		return resource_key;
	}

	/**
	 * @param resource_key
	 *          the resource_key to set
	 */
	public void setResource_key(String resource_key) {
		this.resource_key = resource_key;
	}
	

	@Transient
	public String getResourceValue() {
		ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(locale));
		return myResources.getString(getResource_key());
	}
	
	
	
	public void setLocale(String locale) {
		this.locale = locale;
	}

}
