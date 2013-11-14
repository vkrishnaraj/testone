package aero.nettracer.serviceprovider.ws_1_0.response;

import aero.nettracer.serviceprovider.ws_1_0.common.Itinerary;

/**
 * Response document for FlightService
 * 
 * @author Loupas
 *
 */
public class FlightDataResponse extends GenericResponse {
	private Itinerary[] flights;

	public Itinerary[] getFlights() {
		return flights;
	}

	public void setFlights(Itinerary[] flights) {
		this.flights = flights;
	}
}
