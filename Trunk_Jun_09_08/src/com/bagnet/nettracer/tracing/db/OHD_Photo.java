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
 * @hibernate.class table="ohd_photo"
 */
public class OHD_Photo implements Serializable {
	private int Photo_ID;
	private String thumbpath;
	private String picpath;

	private OHD ohd;

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="true"
	 */
	public OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}
	
	/**
	 * @return Returns the photo_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Photo_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_photo_0"
	 * 
	 *  
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