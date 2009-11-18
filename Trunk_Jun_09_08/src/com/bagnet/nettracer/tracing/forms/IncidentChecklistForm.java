package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;

public class IncidentChecklistForm extends ValidatorForm {
	private String incident_id;
	
	private List<String> selectedOptions = new ArrayList<String>();
	
	private String action;

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	private String undoReference;
	

	public String getUndoReference() {
		return undoReference;
	}
	public void setUndoReference(String undoReference) {
		this.undoReference = undoReference;
	}
	public String getIncident_id() {
		return incident_id;
	}

	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}

	public List<String> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
	
	
//	public List<String> getSelectedOptions(int index, int value) {
//		return selectedOptions;
//	}
//
//	public void setSelectedOptions(int index, int value) {
//		this.selectedOptions = selectedOptions;
//	}	
	
}
