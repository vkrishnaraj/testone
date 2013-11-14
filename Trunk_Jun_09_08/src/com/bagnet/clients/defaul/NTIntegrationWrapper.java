package com.bagnet.clients.defaul;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.ws_1_0.GetFlightDataDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetFlightDataDocument.GetFlightData;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument;
import aero.nettracer.serviceprovider.ws_1_0.ReservationService_1_0Stub;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.GetFlightDataResponseDocument;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

public class NTIntegrationWrapper extends IntegrationWrapper {

	private Logger logger = Logger.getLogger(NTIntegrationWrapper.class);
	private String endpoint = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_BOOKING_ENDPOINT);
	
	private static RequestHeader createHeader() {
		RequestHeader header = RequestHeader.Factory.newInstance();
		header.setPassword(PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_RES_PASSWORD));
		header.setUsername(PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_RES_USERNAME));
		return header;
	}
	
	public Reservation getReservationData(String pnr,
			String bagTag) {
		GetReservationDataResponseDocument resDoc = null;
		ReservationService_1_0Stub stub = null;
		try {
			stub = new ReservationService_1_0Stub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		
		GetReservationDataDocument getResDataDoc = GetReservationDataDocument.Factory
				.newInstance();
		
		GetReservationData getResData = getResDataDoc
				.addNewGetReservationData();
		getResData.setHeader(createHeader());

		try {
			if (pnr != null) {
				getResData.setPnr(pnr);
				resDoc = stub.getReservationData(getResDataDoc);

			} else if (bagTag != null) {
				bagTag = bagTag.toUpperCase();
				try {
					bagTag = LookupAirlineCodes.getFullBagTag(bagTag);
				} catch (BagtagException e) {
					// Ignore
				}
				getResData.setBagTag(bagTag);
				resDoc = stub.getReservationData(getResDataDoc);
			} else {
				this.setErrorMessage("NO MATCHING DATA FOUND IN RESERVATION SYSTEM");
			}
		} catch (Exception e) {
			if (pnr != null) {
				logger.error("Unable to perform lookup for PNR: " + pnr, e);
			} else if (bagTag != null) {
				logger.error("Unable to perform lookup for TAG: " + bagTag, e);
			}
			this.setErrorMessage("error.communication.reservation.system");
		}
		
		if (resDoc == null) {
			return null;
		} else if (resDoc.getGetReservationDataResponse().getReturn().getError() != null) {
			String error = resDoc.getGetReservationDataResponse().getReturn().getError().getDescription();
			if (error != null) {
				this.setErrorMessage(error);
				if (error.equals("PNR NOT VALID")) {
					this.setErrorMessage("error.pnr.not.available");
				}
			}
			return null;
		}
		else if (resDoc.getGetReservationDataResponse().getReturn().getReservation() != null) {
			return resDoc.getGetReservationDataResponse().getReturn().getReservation();
		} else {
			return null;
		}
	}
	
	public boolean writeCommentToPNR(String recordLocator, String comment) {
		if (TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_UPDATE_COMMENT_ON)
				&& recordLocator != null && recordLocator.trim().length() == 6) {
			try {
				ReservationService_1_0Stub stub = new ReservationService_1_0Stub(endpoint);
				ServiceClient sc = stub._getServiceClient();
				Options opts = sc.getOptions();
				opts.setTimeOutInMilliSeconds(60*1000);
				
				WriteRemarkDocument writeDoc = WriteRemarkDocument.Factory.newInstance();
				WriteRemark writeRem = writeDoc.addNewWriteRemark();
				writeRem.setHeader(createHeader());
				writeRem.setPnr(recordLocator);
				writeRem.setRemark(comment);
				
				stub.writeRemark(writeDoc);
				
				System.out.println("Writing Comments Complete...");
				
			} catch (Exception e) {
				e.printStackTrace();
				setErrorMessage("Error calling webservice: " + e.toString());
				return false;
			}
			return true;
		}
		return false;

	}

	/**
	 * Service integration - retrieves flight information from client integrations via central service
	 * 
	 * @param stationcode
	 * @param date
	 * @return
	 */
	public ArrayList<BagDrop> getFlightInfo(String stationcode, Calendar date){
		try {
			//create service stub
			ReservationService_1_0Stub stub = new ReservationService_1_0Stub(endpoint);
			ServiceClient sc = stub._getServiceClient();
			Options opts = sc.getOptions();
			opts.setTimeOutInMilliSeconds(60*1000);
			
			//set request header and parameters
			GetFlightDataDocument doc = GetFlightDataDocument.Factory.newInstance();
			GetFlightData getFlightDataDoc = doc.addNewGetFlightData();
			getFlightDataDoc.setDate(date);
			getFlightDataDoc.setStation(stationcode);
			getFlightDataDoc.setHeader(createHeader());
			
			//retrieve data
			ArrayList<BagDrop> bagDropList = new ArrayList<BagDrop>();
			GetFlightDataResponseDocument response = stub.getFlightData(doc);
			
			//handle response
			if(response != null && response.getGetFlightDataResponse() != null && response.getGetFlightDataResponse().getReturn() != null){
				FlightDataResponse flightDataResponse = response.getGetFlightDataResponse().getReturn();
				if(flightDataResponse != null && flightDataResponse.getFlightsArray() != null){
					for(Itinerary itin:flightDataResponse.getFlightsArray()){
						bagDropList.add(mapFlightData(itin));
					}
				}
			}
			return bagDropList;
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
			return null;
		}
	}
	
	/**
	 * Maps Itinerary data to BagDrop
	 * 
	 * @param data
	 * @return
	 */
	private BagDrop mapFlightData(Itinerary data){
		if(data == null){
			return null;
		}
		BagDrop ret = new BagDrop();
		ret.setCreateDate(DateUtils.convertSystemDateToGMTDate(new Date()));
		ret.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_RESERVATION);
		
		ret.setAirline(data.getAirline());
		ret.setFlight(data.getFlightnum());
		
		ret.setOriginStationCode(data.getDepartureCity());
		ret.setArrivalStationCode(data.getArrivalCity());
		
		if(data.getScharrivetime() != null){
			ret.setSchArrivalDate(data.getScharrivetime().getTime());
		}
		if(data.getActarrivetime() != null){
			ret.setActArrivalDate(data.getActarrivetime().getTime());
		}
		return ret;
	}

}
