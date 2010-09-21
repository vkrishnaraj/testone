package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.ItemTypeBMO;
import com.bagnet.nettracer.tracing.bmo.ManufacturerBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.ItemType;

/**
 * @author invisible
 * 
 * This class represents the form that is used for searching incident reports.
 */
public final class SearchDisputeForm extends ValidatorForm {

	private String incident_ID = "";
	private int status_ID;
	private String s_createtime = "";
	private String e_createtime = "";
	
	private int[] status_IDs;
	
	private int stationcreated_ID;
	
	private String flightnum = ""; // also for custom query
	
	private ItemType itemtype;
	
	
	
	
	public ItemType getItemtype() {
		return itemtype;
	}

	public void setItemtype(ItemType itemtype) {
		this.itemtype = itemtype;
	}

	public String getTypedesc() {
		return itemtype.getDescription();
	}
	
	public String getFlightnum() {
		return flightnum;
	}

	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	/**
	 * @return Returns the stationcreated_ID.
	 */
	public int getStationcreated_ID() {
		return stationcreated_ID;
	}

	/**
	 * @param stationcreated_ID
	 *          The stationcreated_ID to set.
	 */
	public void setStationcreated_ID(int stationcreated_ID) {
		this.stationcreated_ID = stationcreated_ID;
	}

	public String getIncident_ID() {
		return incident_ID;
	}

	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	public int getStatus_ID() {
		return status_ID;
	}

	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}

	public String getS_createtime() {
		return s_createtime;
	}

	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}

	public String getE_createtime() {
		return e_createtime;
	}

	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	public int[] getStatus_IDs() {
		return status_IDs;
	}

	public void setStatus_IDs(int[] status_IDs) {
		this.status_IDs = status_IDs;
	}
	
	

}
