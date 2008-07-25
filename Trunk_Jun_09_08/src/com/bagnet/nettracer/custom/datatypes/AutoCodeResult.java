package com.bagnet.nettracer.custom.datatypes;

import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;

public class AutoCodeResult {
	private Incident incident;
	private Company_specific_irregularity_code lossCode;
	private Station faultStation;
	private Exception exception;
	
	public AutoCodeResult() {
		
	}
	
	public AutoCodeResult(Incident incident, Company_specific_irregularity_code lossCode, Station faultStation, Exception e) {
		setIncident(incident);
		setLossCode(lossCode);
		setFaultStation(faultStation);
		setException(e);
	}
	
	
	public AutoCodeResult(Incident incident, int lossCode, int faultStation, Exception e) {
		setIncident(incident);
		setLossCode(lossCode);
		setFaultStation(faultStation);
		setException(e);
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident; 
	}
	
	public Incident getIncident() {
		return incident;
	}
	
	public void setLossCode(Company_specific_irregularity_code lossCode) {
		this.lossCode = lossCode;
	}
	
	public void setLossCode(int lossCode) {
		// TODO Implement this.
	}
	
	public Company_specific_irregularity_code getLossCode() {
		return lossCode;
	}
	
	public void setFaultStation(Station station) {
		this.faultStation = station;
	}
	
	public void setFaultStation(int station_ID) {
		// TODO Implement this.
	}
	
	public Station getFaultStation() {
		return faultStation;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
	
}
