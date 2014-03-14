package aero.nettracer.serviceprovider.wt_1_0.common;

import java.text.DateFormat;
import java.util.Calendar;

public class Itinerary {
	private String departureCity;
	private String arrivalCity;
	private String airline;
	private String flightNumber;
	private Calendar flightDate;
	private int legfrom_type;
	private int legto_type;
	private String arnk;

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Calendar getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getLegfrom_type() {
		return legfrom_type;
	}

	public void setLegfrom_type(int legfrom_type) {
		this.legfrom_type = legfrom_type;
	}

	public int getLegto_type() {
		return legto_type;
	}

	public void setLegto_type(int legto_type) {
		this.legto_type = legto_type;
	}
	
	public String getItinInfo(){
		StringBuilder itinString = new StringBuilder();
		if(getAirline()!=null && !getAirline().isEmpty()){
			itinString.append(getAirline() + " ");
		}
		if(getFlightNumber()!=null && !getFlightNumber().isEmpty()){
			itinString.append(getFlightNumber()+" ");
		}
		if(getDepartureCity()!=null && !getDepartureCity().isEmpty()){
			itinString.append(getDepartureCity()+" ");
		}
		if(getArrivalCity()!=null && !getArrivalCity().isEmpty()){
			itinString.append(getArrivalCity()+" ");
		}
		if(getFlightDate() != null && getFlightDate().getTime() != null){
			DateFormat ITIN_DATE_FORMAT = new java.text.SimpleDateFormat("MMM dd, yyyy");
			itinString.append(ITIN_DATE_FORMAT.format(getFlightDate().getTime())+" ");
		}
		
		return itinString.toString();
	}

	public String getArnk() {
		return arnk;
	}

	public void setArnk(String arnk) {
		this.arnk = arnk;
	}
}
