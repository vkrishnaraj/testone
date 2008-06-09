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
 * @hibernate.class table="audit_lost_found_photo"
 */
public class Audit_LostAndFound_Photo implements Serializable {

	private int id;
	private int Photo_ID;
	private String thumbpath;
	private String picpath;
	private String file_ref_number;
	private Audit_LostAndFoundIncident lostandfoundincident;
	
	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_lost_found_photo_0"
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
	 * @return Returns the Audit_LostAndFoundIncident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident"
	 *                        column="audit_lost_found_id"
	 */
	public Audit_LostAndFoundIncident getLostandfoundincident() {
		return lostandfoundincident;
	}

	/**
	 * @param lostandfoundincident the lostandfoundincident to set
	 */
	public void setLostandfoundincident(
			Audit_LostAndFoundIncident lostandfoundincident) {
		this.lostandfoundincident = lostandfoundincident;
	}

	
	/**
	 * @return Returns the file_ref_number.
	 * 
	 * @hibernate.property type="string"
	 *  
	 */
	public String getFile_ref_number() {
		return file_ref_number;
	}

	/**
	 * @param file_ref_number the file_ref_number to set
	 */
	public void setFile_ref_number(String file_ref_number) {
		this.file_ref_number = file_ref_number;
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
		Audit_LostAndFound_Photo aoi = (Audit_LostAndFound_Photo) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getPicpath(), this.getPicpath())
				|| AuditOHDUtils.notEqualObjects(aoi.getThumbpath(), this.getThumbpath())) {
			ret = false;
		}
		return ret;
	}


}