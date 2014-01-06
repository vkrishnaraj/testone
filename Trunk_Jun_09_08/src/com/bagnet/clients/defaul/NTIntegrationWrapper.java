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
import aero.nettracer.serviceprovider.ws_1_0.SubmitVoucherDocument.SubmitVoucher;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Voucher;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.GetFlightDataResponseDocument;
import aero.nettracer.serviceprovider.ws_1_0.SubmitVoucherDocument;
import aero.nettracer.serviceprovider.ws_1_0.SubmitVoucherResponseDocument;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
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

	public ArrayList<String> submitVoucher(com.bagnet.nettracer.tracing.db.Incident inc, String status, ExpensePayoutForm epf){
		try {
			//create service stub
			ReservationService_1_0Stub stub = new ReservationService_1_0Stub(endpoint);
			ServiceClient sc = stub._getServiceClient();
			Options opts = sc.getOptions();
			opts.setTimeOutInMilliSeconds(60*1000);
			
			SubmitVoucherDocument doc = SubmitVoucherDocument.Factory.newInstance();
			SubmitVoucher voucherDoc = doc.addNewSubmitVoucher();
			
			voucherDoc.setHeader(createHeader());

			
			Voucher voucher = voucherDoc.addNewVoucher();

			voucher.setPnr((inc.getRecordlocator() != null) ? inc.getRecordlocator() : "");//ntvoucher.getPnr();
			String stationId = inc.getStation_ID() + "";//String stationId = Integer.toString(epf.getExpenselocation_ID());
			Station station = StationBMO.getStation(stationId);

			voucher.setStation((station.getStationcode() != null) ? station.getStationcode() : "");
			voucher.setStatus(status);
			voucher.setAgentUserName((inc.getAgent_username() != null) ? inc.getAgent_username() : "");
			voucher.setAmount((epf.getCheckamt() != 0) ? epf.getCheckamt() : 0);
//			voucher.setDepartment(null);//ignore
			String distributemethod = (epf.getDistributemethod() != null) ? epf.getDistributemethod() : "" ;
			if (distributemethod.equals("IMME")) distributemethod = "IMMEDIATE";
			if (distributemethod.equals("MAIL")) distributemethod = "USPS";

			voucher.setDistributionMethod(distributemethod);
			voucher.setNtIncidentId((inc.getIncident_ID() != null) ? inc.getIncident_ID() : "");
			if (status.equals("cancel")){
				voucher.setRemark((epf.getCancelreason() != null) ? epf.getCancelreason() : "");//cancel reason for cancel status
				ExpensePayout ep = ExpensePayoutBMO.findExpensePayout(epf.getExpensepayout_ID());
				voucher.setOrderNumber((ep.getOrdernum() != null) ? ep.getOrdernum() : "");
			}
			Passenger pax = voucher.addNewPassenger();
			if (inc.getPassengers() != null && inc.getPassengers().size() > 0) {
				for (com.bagnet.nettracer.tracing.db.Passenger pa : inc.getPassengers()) {
					pa.setPassenger_ID(0);
					pax.setFirstname((pa.getFirstname() != null) ? pa.getFirstname() : "");
					pax.setLastname((pa.getLastname() != null) ? pa.getLastname() : "");
					Address addr = pax.addNewAddresses();
					addr.setAddress1((pa.getAddress(0).getAddress1() != null) ? pa.getAddress(0).getAddress1() : "");
					addr.setAddress2((pa.getAddress(0).getAddress2() != null) ? pa.getAddress(0).getAddress2() : "");
					addr.setCity((pa.getAddress(0).getCity() != null) ? pa.getAddress(0).getCity() : "");
					addr.setState((pa.getAddress(0).getState_ID() != null) ? pa.getAddress(0).getState_ID() : "");
					addr.setZip((pa.getAddress(0).getZip() != null) ? pa.getAddress(0).getZip() : "");
					addr.setCountry((pa.getAddress(0).getCountrycode_ID() != null) ? pa.getAddress(0).getCountrycode_ID() : "");
					addr.setHomePhone((pa.getAddress(0).getHomephone_norm() != null) ? pa.getAddress(0).getHomephone_norm() : "");
					addr.setWorkPhone((pa.getAddress(0).getWorkphone_norm() != null) ? pa.getAddress(0).getWorkphone_norm() : "");
					addr.setMobilePhone((pa.getAddress(0).getMobile_norm() != null) ? pa.getAddress(0).getMobile_norm() : "");
					addr.setEmailAddress((pa.getAddress(0).getEmail() != null) ? pa.getAddress(0).getEmail() : "");
					
					break;
				}
			}			
			

			System.out.println(doc);
			SubmitVoucherResponseDocument response = stub.submitVoucher(doc);
			System.out.println(response);
			ArrayList<String> ret = new ArrayList<String>();
			if (response != null && response.getSubmitVoucherResponse() != null && response.getSubmitVoucherResponse().getReturn() != null) {
				String success = (response.getSubmitVoucherResponse().getReturn().getSuccess()) ? "true" : "false" ;
				ret.add(success);
				if (response.getSubmitVoucherResponse().getReturn().getOrderNumber() !=null ) {
					ret.add(response.getSubmitVoucherResponse().getReturn().getOrderNumber());					
				} else {
					ret.add("");
				}
				if (response.getSubmitVoucherResponse().getReturn().getCardNumber() != null) {
					ret.add(response.getSubmitVoucherResponse().getReturn().getCardNumber());					
				} else {
					ret.add("");
				}
				if (response.getSubmitVoucherResponse().getReturn().getSecurityCode() !=null ) {
					ret.add(response.getSubmitVoucherResponse().getReturn().getSecurityCode());					
				} else {
					ret.add("");
				}
				if (response.getSubmitVoucherResponse().getReturn().getError() !=null && response.getSubmitVoucherResponse().getReturn().getError().getDescription() != null ) {
					ret.add(response.getSubmitVoucherResponse().getReturn().getError().getDescription());					
				} else {
					ret.add("");
				}				
			} else 
				ret=null;

			return ret;
		} catch (Exception e){
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
			return null;
		}

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
