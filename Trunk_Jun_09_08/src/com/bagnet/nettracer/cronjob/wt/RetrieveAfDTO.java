package com.bagnet.nettracer.cronjob.wt;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

public class RetrieveAfDTO {

	private ActionFileType type;
	private int day;
	private String companyCode;
	private String stationCode;

	public RetrieveAfDTO(ActionFileType type, int day, String companyCode, String stationCode) {
		this.type = type;
		this.day = day;
		this.companyCode = companyCode;
		this.stationCode = stationCode;
	}

	public ActionFileType getType() {
		return type;
	}

	public void setType(ActionFileType type) {
		this.type = type;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	
}
