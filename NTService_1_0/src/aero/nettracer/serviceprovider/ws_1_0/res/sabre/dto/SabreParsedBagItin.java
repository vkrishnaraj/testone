package aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto;

import java.util.Calendar;

public class SabreParsedBagItin {
	private String departureCity;
	private String arrivalCity;
	private String airline;
	private String flight;
	private Calendar checkedTime;

	public Calendar getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Calendar checkedTime) {
		this.checkedTime = checkedTime;
	}

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

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String key() {
		return airline + flight + departureCity + arrivalCity;
	}

}
