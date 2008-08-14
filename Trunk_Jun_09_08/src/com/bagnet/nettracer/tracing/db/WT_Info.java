package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;



/**
 * @author Administrator
 * 
 * @hibernate.class table="wt_info"
 *  
 */
public class WT_Info implements Serializable {
	private int id;
	private String requestContext;
	private String responseContext;
	private Date wt_info_date;
	/**
	 * @hibernate.id generator-class="native" type="integer" column="ID"
	 *  * @hibernate.generator-param name="sequence" value="ID"
	 * @return Returns the ID.
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
	 * @return the requestContext
	 * 
	 * @hibernate.property type="string"
	 */
	public String getRequestContext() {
		return requestContext;
	}
	/**
	 * @param requestContext the requestContext to set
	 */
	public void setRequestContext(String requestContext) {
		this.requestContext = requestContext;
	}
	/**
	 * @return the responseContext
	 * 
	 * @hibernate.property type="string"
	 */
	public String getResponseContext() {
		return responseContext;
	}
	/**
	 * @param responseContext the responseContext to set
	 */
	public void setResponseContext(String responseContext) {
		this.responseContext = responseContext;
	}
	/**
	 * @return the wt_info_date
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getWt_info_date() {
		return wt_info_date;
	}
	/**
	 * @param wt_info_date the wt_info_date to set
	 */
	public void setWt_info_date(Date wt_info_date) {
		this.wt_info_date = wt_info_date;
	}
}
