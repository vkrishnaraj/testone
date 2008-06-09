/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_Item_Photo"
 */
public class Audit_Item_Photo implements Serializable {
	private int audit_photo_id;
	private int Photo_ID;
	private String thumbpath;
	private String picpath;
	private Audit_Item audit_item;
	

	/**
	 * @return Returns the audit_photo_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_item_photo_0"
	 * 
	 * 
	 *  
	 */
	public int getAudit_photo_id() {
		return audit_photo_id;
	}

	/**
	 * @param audit_photo_id
	 *          The audit_photo_id to set.
	 */
	public void setAudit_photo_id(int audit_photo_id) {
		this.audit_photo_id = audit_photo_id;
	}
	
	

	/**
	 * @return Returns the audit_item.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Item"
	 *                        column="audit_item_id"
	 */
	public Audit_Item getAudit_item() {
		return audit_item;
	}
	/**
	 * @param audit_item The audit_item to set.
	 */
	public void setAudit_item(Audit_Item audit_item) {
		this.audit_item = audit_item;
	}
	
	/**
	 * @return Returns the photo_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getPhoto_ID() {
		return Photo_ID;
	}

	/**
	 * @param photo_ID
	 *          The photo_ID to set.
	 */
	public void setPhoto_ID(int photo_ID) {
		Photo_ID = photo_ID;
	}

	/**
	 * @return Returns the picpath.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getPicpath() {
		return picpath;
	}

	/**
	 * @param picpath
	 *          The picpath to set.
	 */
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	/**
	 * @return Returns the thumbpath.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getThumbpath() {
		return thumbpath;
	}

	/**
	 * @param thumbpath
	 *          The thumbpath to set.
	 */
	public void setThumbpath(String thumbpath) {
		this.thumbpath = thumbpath;
	}
}