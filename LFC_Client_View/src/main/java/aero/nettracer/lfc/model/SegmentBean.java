package aero.nettracer.lfc.model;

import java.io.Serializable;

public class SegmentBean implements Serializable{

	private static final long serialVersionUID = 6759537869024990873L;
	
	private long id;
	private int departureLocation;
	private int arrivalLocation;
	private String flightNumber;
	private String departureLocationDesc;
	private String arrivalLocationDesc;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDepartureLocation() {
		return departureLocation;
	}
	
	public void setDepartureLocation(int departureLocation) {
		this.departureLocation = departureLocation;
	}
	
	public int getArrivalLocation() {
		return arrivalLocation;
	}
	
	public void setArrivalLocation(int arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepartureLocationDesc() {
		return departureLocationDesc;
	}

	public void setDepartureLocationDesc(String departureLocationDesc) {
		this.departureLocationDesc = departureLocationDesc;
	}

	public String getArrivalLocationDesc() {
		return arrivalLocationDesc;
	}

	public void setArrivalLocationDesc(String arrivalLocationDesc) {
		this.arrivalLocationDesc = arrivalLocationDesc;
	}
	
}
