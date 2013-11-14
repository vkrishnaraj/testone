package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.res.FlightServiceInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;

import com.swacorp.services.btws.v1.GetFlightListRequestDocument;
import com.swacorp.services.btws.v1.GetFlightListRequestDocument.GetFlightListRequest;
import com.swacorp.services.btws.v1.GetFlightListResponseDocument;
import com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList;
import com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList.Flight;

import com.swacorp.services.btws.wsdl.v1.BTWSStub;
import com.swacorp.services.btws.wsdl.v1.GetFlightListError;

/**
 * @author Loupas
 * 
 * Cebs implementation of FlightServiceInterface
 * Currently used for Southwest Bagdrop
 *
 */
public class FlightService implements FlightServiceInterface{

	private static Logger logger = Logger.getLogger(FlightService.class);
	
	/**
	 * Defining stub for mocking/junit
	 */
	private BTWSStub btwsStub = null;
	
	/**
	 * Defining document for mocking/junit
	 */
	private GetFlightListRequestDocument requestDoc = null;
	
	protected BTWSStub getBtwsStub() {
		return btwsStub;
	}

	protected void setBtwsStub(BTWSStub btwsStub) {
		this.btwsStub = btwsStub;
	}
	
	/**
	 * If a requestDoc is previously set, use that instance of GetFlightListRequestDocument (used for mock junit),
	 * otherwise create new request document with the given parameters
	 * 
	 * @param station
	 * @param date
	 * @return
	 */
	public GetFlightListRequestDocument getRequestDoc(String station, Calendar date) {
		if(requestDoc == null){
			GetFlightListRequestDocument doc = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest request = doc.addNewGetFlightListRequest();
			request.setDate(date);
			request.setStation(station);
			return doc;
		} else {
			return requestDoc;
		}
	}

	/**
	 * Set a request document that is used for mock unit testing
	 * 
	 * @param requestDoc
	 */
	public void setRequestDoc(GetFlightListRequestDocument requestDoc) {
		this.requestDoc = requestDoc;
	}
	

	/* 
	 * @see aero.nettracer.serviceprovider.ws_1_0.res.FlightServiceInterface#getFlightData(aero.nettracer.serviceprovider.common.db.User, java.lang.String, java.util.Calendar)
	 */
	public FlightDataResponse getFlightData(User user, String station, Calendar date){
		GetFlightListRequestDocument doc= getRequestDoc(station, date);
		
		FlightDataResponse ret = FlightDataResponse.Factory.newInstance();
		try {
			BTWSStub stub = ConnectionUtil.getStub(btwsStub, user);
			
			logger.info(doc);
			GetFlightListResponseDocument response = stub.getFlightList(doc);
			logger.info(response);
			
			if(response != null && response.getGetFlightListResponse() != null && response.getGetFlightListResponse().getFlightList() != null){
				//map response for return to ntcore
				FlightList flightList = response.getGetFlightListResponse().getFlightList();
				for(Flight flight:flightList.getFlightArray()){
					Itinerary flightData = ret.addNewFlights();
					flightData.setActarrivetime(flight.getActualArrival());
					flightData.setScharrivetime(flight.getScheduleArrival());
					flightData.setAirline(flight.getAirlineCode());
					flightData.setFlightnum(flight.getFlight());
					flightData.setArrivalCity(flight.getArrivalCity());
					flightData.setDepartureCity(flight.getOriginCity());
				}
			} else {
				ret.addNewError().setDescription(ServiceConstants.FLIGHT_DATA_EXCEPTION);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.REMOTE_EXCEPTION);
		} catch (GetFlightListError e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.FLIGHT_DATA_EXCEPTION);
		} catch (Exception e){
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return ret;
	}
}
