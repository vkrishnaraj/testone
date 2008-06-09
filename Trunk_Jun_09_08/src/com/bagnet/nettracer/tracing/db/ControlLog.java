/*
 * Created on Aug 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ListIterator;

import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="file_control"
 */
public class ControlLog implements Serializable {

	private int control_id;
	private String start_date;
	private String end_date;
	private Station controlling_station;
	
	private OHD ohd;

	/**
	 * @hibernate.id generator-class="native" type="integer" column="control_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="file_control_0"
	 * 
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
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="file_ref_number" not-null="true"
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
	
	public String toXML() {
		StringBuffer ret = new StringBuffer(1096);
		
		ret.append("<control_log>");
		ret.append("<control_id>" + control_id + "</control_id>");
		ret.append("<file_ref_number>" + ohd.getOHD_ID() + "</file_ref_number>");
		ret.append("<start_date>" + start_date + "</start_date>");
		ret.append("<end_date>" + end_date + "</end_date>");
		ret.append("<controlling_station_id>" + controlling_station + "</controlling_station_id>");
		ret.append("</control_log>");
		
		
		return ret.toString();
	}
	
	public static ControlLog XMLtoObject(ElementNode root) {
		ControlLog obj = new ControlLog();

	
		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;
		
		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("control_id")) {
				obj.setControl_id(NumberUtils.parseInt(child.getTextContents()));
			}else if (child.getType().equals("file_ref_number")) {
				OHD ohd = new OHD();
				ohd.setOHD_ID(child.getTextContents());
				obj.setOhd(ohd);
			}else if (child.getType().equals("start_date")) {
				obj.setStart_date(child.getTextContents());
			}else if (child.getType().equals("end_date")) {
				obj.setEnd_date(child.getTextContents());
			}else if (child.getType().equals("controlling_station_id")) {
				Station s = new Station();
				s.setStation_ID(NumberUtils.parseInt(child.getTextContents()));
				
				obj.setControlling_station(s);
			}
		}
		
		return obj;
	}
}