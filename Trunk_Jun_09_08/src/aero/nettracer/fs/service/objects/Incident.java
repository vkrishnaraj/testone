package aero.nettracer.fs.service.objects;

import java.util.Date;

public class Incident {
	private String airline;
	private String airlineIncidentId;
	private Date incidentCreated;
	private int incidentType;
	private int numberOfBdosCreated;
	private int numberDaysOpen;
	private Date incidentOpened;
	private Date incidentClosed;
	private String incidentDescription;
	
	private Person[] claimants;
	private Segment[] segments;

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirlineIncidentId() {
		return airlineIncidentId;
	}

	public void setAirlineIncidentId(String airlineIncidentId) {
		this.airlineIncidentId = airlineIncidentId;
	}

	public Date getIncidentCreated() {
		return incidentCreated;
	}

	public void setIncidentCreated(Date incidentCreated) {
		this.incidentCreated = incidentCreated;
	}

	public int getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(int incidentType) {
		this.incidentType = incidentType;
	}

	public int getNumberOfBdosCreated() {
		return numberOfBdosCreated;
	}

	public void setNumberOfBdosCreated(int numberOfBdosCreated) {
		this.numberOfBdosCreated = numberOfBdosCreated;
	}

	public int getNumberDaysOpen() {
		return numberDaysOpen;
	}

	public void setNumberDaysOpen(int numberDaysOpen) {
		this.numberDaysOpen = numberDaysOpen;
	}

	public Date getIncidentOpened() {
		return incidentOpened;
	}

	public void setIncidentOpened(Date incidentOpened) {
		this.incidentOpened = incidentOpened;
	}

	public Date getIncidentClosed() {
		return incidentClosed;
	}

	public void setIncidentClosed(Date incidentClosed) {
		this.incidentClosed = incidentClosed;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public Person[] getClaimants() {
		return claimants;
	}

	public void setClaimants(Person[] claimants) {
		this.claimants = claimants;
	}

	public Segment[] getSegments() {
		return segments;
	}

	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}

}
