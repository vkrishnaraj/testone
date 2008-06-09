/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_ohd_photo"
 */
public class Audit_OHD_Photo implements Serializable {

	private int id;
	private int Photo_ID;
	private String thumbpath;
	private String picpath;
	private String OHD_ID;
	private Audit_OHD ohd;
	
	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_ohd_photo_0"
	 * 
	 * 
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD"
	 *                        column="audit_ohd_id"
	 */
	public Audit_OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(Audit_OHD ohd) {
		this.ohd = ohd;
	}
		
	
	/**
	 * @return Returns the oHD_ID.
	 * 
	 * @hibernate.property type="string"
	 *  
	 */
	public String getOHD_ID() {
		return OHD_ID;
	}

	/**
	 * @param ohd_id
	 *          The oHD_ID to set.
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
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

	public boolean equals(Object obj) {
		Audit_OHD_Photo aoi = (Audit_OHD_Photo) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getPicpath(), this.getPicpath())
				|| AuditOHDUtils.notEqualObjects(aoi.getThumbpath(), this.getThumbpath())) {
			ret = false;
		}
		return ret;
	}
}