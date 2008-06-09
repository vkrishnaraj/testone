/*
 * Created on Aug 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="Audit_file_control"
 */
public class Audit_ControlLog implements Serializable {

	private int id;
	private int control_id;
	private String start_date;
	private String end_date;
	private Station controlling_station;
	
	private Audit_OHD audit_ohd;
	private OHD ohd;

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Audit_file_control_0"
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
	public Audit_OHD getAudit_ohd() {
		return audit_ohd;
	}
	/**
	 * @param audit_ohd The audit_ohd to set.
	 */
	public void setAudit_ohd(Audit_OHD audit_ohd) {
		this.audit_ohd = audit_ohd;
	}
	
	/**
	 * @return Returns the ohd.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="file_ref_number"
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
	 * @hibernate.property type="integer"
	 * @return Returns the control_id.
	 */
	public int getControl_id() {
		return control_id;
	}

	/**
	 * @param control_id
	 *          The control_id to set.
	 */
	public void setControl_id(int control_id) {
		this.control_id = control_id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the end_date.
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date
	 *          The end_date to set.
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	/**
	 * @hibernate.property type="string"
	 * @return Returns the start_date.
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *          The start_date to set.
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	/**
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="controlling_station_id"
	 * @return Returns the controlling_station.
	 */
	public Station getControlling_station() {
		return controlling_station;
	}

	/**
	 * @param controlling_station
	 *          The controlling_station to set.
	 */
	public void setControlling_station(Station controlling_station) {
		this.controlling_station = controlling_station;
	}
}