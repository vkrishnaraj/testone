package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class WorldTracerReqQOHForm extends ValidatorForm {

	private static final int NUM_TELETYPES = 4;
	private String matchingAhl = null;
	private String fromStation = null;
	private String fromAirline = null;
	private String bagTag = null;
	private String furtherInfo = null;
	private List<String> teletypes = new ArrayList<String>();
	private String paxName = null;
	
	
	public WorldTracerReqQOHForm() {
		super();
		for(int i = 0; i < NUM_TELETYPES; i ++) {
			teletypes.add("");
		}
	}
	
	
	public String getMatchingAhl() {
		return matchingAhl;
	}
	public void setMatchingAhl(String matchingAhl) {
		this.matchingAhl = matchingAhl;
	}
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public String getFromAirline() {
		return fromAirline;
	}
	public void setFromAirline(String fromAirline) {
		this.fromAirline = fromAirline;
	}
	public String getFurtherInfo() {
		return furtherInfo;
	}
	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}
	public String getPaxName() {
		return paxName;
	}
	public void setPaxName(String paxName) {
		this.paxName = paxName;
	}
	public String getBagTag() {
		return bagTag;
	}
	public void setBagTag(String bagTag) {
		this.bagTag = bagTag;
	}
	public List<String> getTeletypes() {
		return teletypes;
	}
	public void setTeletypes(List<String> teletypes) {
		this.teletypes = teletypes;
	}
	public String getTeletype(int index) {
		 if (index >= teletypes.size()) {
		        teletypes.add(index, "");
		    }
		    return teletypes.get(index);
	}
	public void setTeletype(int index, String foo) {
	    if (index < teletypes.size( )) {
	        teletypes.set(index, foo);
	    }
	    else {
	        teletypes.add(index, foo);
	    }
	}
}
