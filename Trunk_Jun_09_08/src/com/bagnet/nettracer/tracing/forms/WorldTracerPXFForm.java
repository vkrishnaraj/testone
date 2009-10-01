package com.bagnet.nettracer.tracing.forms;


import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class WorldTracerPXFForm extends ValidatorForm {

	private static final int NUM_TELETYPES = 5;
	private String furtherInfo = null;
	private List<String> teletypes = new ArrayList<String>();
	private String paxName = null;
	
	private String matchingAhl = null;
	
	private String station_1 = null;
	private String airline_1 = null;
	private String area_1 = null;
	
	private String station_2 = null;
	private String airline_2 = null;
	private String area_2 = null;
	
	private String station_3 = null;
	private String airline_3 = null;
	private String area_3 = null;
	
	private String station_4 = null;
	private String airline_4 = null;
	private String area_4 = null;
	
	private String station_5 = null;
	private String airline_5 = null;
	private String area_5 = null;
	
	private String pxfOption = "ACTION_MESSAGE_ADDRESS";
	
	private String allStationsArea = null;
	
	private String regionArea = null;
	private String regionCode = null;
	
	public String getMatchingAhl() {
		return matchingAhl;
	}
	public void setMatchingAhl(String matchingAhl) {
		this.matchingAhl = matchingAhl;
	}
	
	public String getStation_1() {
		return station_1;
	}


	public void setStation_1(String station_1) {
		this.station_1 = station_1;
	}


	public String getAirline_1() {
		return airline_1;
	}


	public void setAirline_1(String airline_1) {
		this.airline_1 = airline_1;
	}


	public String getArea_1() {
		return area_1;
	}


	public void setArea_1(String area_1) {
		this.area_1 = area_1;
	}


	public String getStation_2() {
		return station_2;
	}


	public void setStation_2(String station_2) {
		this.station_2 = station_2;
	}


	public String getAirline_2() {
		return airline_2;
	}


	public void setAirline_2(String airline_2) {
		this.airline_2 = airline_2;
	}


	public String getArea_2() {
		return area_2;
	}


	public void setArea_2(String area_2) {
		this.area_2 = area_2;
	}


	public String getStation_3() {
		return station_3;
	}


	public void setStation_3(String station_3) {
		this.station_3 = station_3;
	}


	public String getAirline_3() {
		return airline_3;
	}


	public void setAirline_3(String airline_3) {
		this.airline_3 = airline_3;
	}


	public String getArea_3() {
		return area_3;
	}


	public void setArea_3(String area_3) {
		this.area_3 = area_3;
	}


	public String getStation_4() {
		return station_4;
	}


	public void setStation_4(String station_4) {
		this.station_4 = station_4;
	}


	public String getAirline_4() {
		return airline_4;
	}


	public void setAirline_4(String airline_4) {
		this.airline_4 = airline_4;
	}


	public String getArea_4() {
		return area_4;
	}


	public void setArea_4(String area_4) {
		this.area_4 = area_4;
	}


	public String getStation_5() {
		return station_5;
	}


	public void setStation_5(String station_5) {
		this.station_5 = station_5;
	}


	public String getAirline_5() {
		return airline_5;
	}


	public void setAirline_5(String airline_5) {
		this.airline_5 = airline_5;
	}


	public String getArea_5() {
		return area_5;
	}


	public void setArea_5(String area_5) {
		this.area_5 = area_5;
	}
	
	public String getRegionCode() {
		return regionCode;
	}


	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}


	public String getAllStationsArea() {
		return allStationsArea;
	}


	public void setAllStationsArea(String allStationsArea) {
		this.allStationsArea = allStationsArea;
	}


	public String getRegionArea() {
		return regionArea;
	}


	public void setRegionArea(String regionArea) {
		this.regionArea = regionArea;
	}
	

	public String getPxfOption() {
		return pxfOption;
	}


	public void setPxfOption(String pxfOption) {
		this.pxfOption = pxfOption;
	}


	public WorldTracerPXFForm() {
		super();
		for(int i = 0; i < NUM_TELETYPES; i ++) {
			teletypes.add("");
		}
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
