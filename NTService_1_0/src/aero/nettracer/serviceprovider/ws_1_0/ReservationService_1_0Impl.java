/**
 * ReservationService_1_0Skeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.ws_1_0;

import java.util.Calendar;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.GetFlightDataResponseDocument.GetFlightDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.ws_1_0.res.FlightServiceInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

/**
 * ReservationService_1_0Skeleton java skeleton for the axisService
 */
public class ReservationService_1_0Impl extends ReservationService_1_0Skeleton{
	/**
	 * Auto generated method signature
	 * 
	 * @param getEnplanements
	 */
	public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument getEnplanements(
			aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument getEnplanements) {
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#getEnplanements");
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param writeRemark
	 */
	public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument writeRemark(
			aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument writeRemark) {

		WriteRemarkResponseDocument doc = WriteRemarkResponseDocument.Factory
				.newInstance();
		WriteRemarkResponse res = doc.addNewWriteRemarkResponse();
		RemarkResponse res1 = res.addNewReturn();
		RequestHeader header = writeRemark.getWriteRemark().getHeader();
		String username = header.getUsername();
		String password = header.getPassword();

		try {
			User user = ServiceUtilities.getAndAuthorizeUser(username,
					password, PermissionType.WRITE_REMARK);
			ReservationInterface reservation = ServiceUtilities
					.getReservationSystem(user);
			String pnr = writeRemark.getWriteRemark().getPnr();
			String remark = writeRemark.getWriteRemark().getRemark();
			reservation.writeRemark(user, pnr, remark);
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
			return doc;
		} catch (ConfigurationException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			return doc;
		} catch (UnexpectedException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
			return doc;

		}

		return doc;

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getReservationData
	 */
	
	public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument getReservationData(
			aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument getReservationData) {

		GetReservationDataResponseDocument doc = GetReservationDataResponseDocument.Factory
				.newInstance();
		GetReservationDataResponse res = doc.addNewGetReservationDataResponse();
		ReservationResponse res1 = res.addNewReturn();

		RequestHeader header = getReservationData.getGetReservationData()
				.getHeader();
		String username = header.getUsername();
		String password = header.getPassword();

		try {
			User user = ServiceUtilities.getAndAuthorizeUser(username,
					password, PermissionType.GET_PREPOP_DATA);
			ReservationInterface reservation = ServiceUtilities
					.getReservationSystem(user);
			String pnr = getReservationData.getGetReservationData().getPnr();
			String bagTag = getReservationData.getGetReservationData()
					.getBagTag();
			
			res1 = reservation.getReservationData(user, pnr, bagTag);
			res.setReturn(res1);
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
			return doc;
		} catch (ConfigurationException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			return doc;
		} catch (UnexpectedException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
			return doc;
		}

		return doc;

	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getOsiContents
	 */
	public aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument getOsiContents(
			aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument getOsiContents) {

		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#getOsiContents");
	}
	
	/**
	 * Service implementation for retrieving flight itinerary segments for a given arrival station and date
	 * 
	 * @param getFlightData
	 */

	public aero.nettracer.serviceprovider.ws_1_0.GetFlightDataResponseDocument getFlightData(
			aero.nettracer.serviceprovider.ws_1_0.GetFlightDataDocument getFlightData){
		
		GetFlightDataResponseDocument doc = GetFlightDataResponseDocument.Factory.newInstance();
		GetFlightDataResponse res = doc.addNewGetFlightDataResponse();
		FlightDataResponse ret = res.addNewReturn();

		RequestHeader header = getFlightData.getGetFlightData().getHeader();
		String username = header.getUsername();
		String password = header.getPassword();
		
		try {
			//Auth user
			User user = ServiceUtilities.getAndAuthorizeUser(username,
					password, PermissionType.GET_FLIGHT_DATA);
			FlightServiceInterface service = ServiceUtilities.getFlightService(user);
			
			//map request parameters
			String station = getFlightData.getGetFlightData().getStation();
			Calendar date = getFlightData.getGetFlightData().getDate();
			
			//submit request and return
			ret = service.getFlightData(user, station, date);
			res.setReturn(ret);
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = ret.addNewError();
			error.setDescription(ServiceConstants.USER_NOT_AUTHORIZED);
			return doc;
		} catch (ConfigurationException e) {
			WebServiceError error = ret.addNewError();
			error.setDescription(ServiceConstants.CONFIGURATION_ERROR);
			return doc;
		} catch (UnexpectedException e) {
			WebServiceError error = ret.addNewError();
			error.setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
			return doc;
		}
		
		return doc;
		
	}
}
