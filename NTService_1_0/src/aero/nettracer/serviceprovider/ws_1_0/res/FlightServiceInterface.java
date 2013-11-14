package aero.nettracer.serviceprovider.ws_1_0.res;

import java.util.Calendar;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;

/**
 * Interface for FlightService integration to receive from reservation systems general flight 
 * itinerary information that is not tied to any particular PNR.
 * 
 * @author Loupas
 *
 */
public interface FlightServiceInterface {
	/**
	 * Retrieves flight itinerary segments for the the given arrival station and date
	 * 
	 * @param user 
	 * @param station - arrival station
	 * @param date - arrival date
	 * @return
	 * @throws UnexpectedException
	 */
	public FlightDataResponse getFlightData(User user, String station, Calendar date) throws UnexpectedException;
}
