/**
 * ReservationService_1_0Skeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.ws_1_0;

import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

/**
 * ReservationService_1_0Skeleton java skeleton for the axisService
 */
public class ReservationService_1_0Impl {
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
			User user = ServiceUtilities.getAndAuthorizeUser(username, password,
			    PermissionType.WRITE_REMARK);
			ReservationInterface reservation = ServiceUtilities
			    .getReservationSystem(user);
			String pnr = writeRemark.getWriteRemark().getPnr();
			String remark = writeRemark.getWriteRemark().getRemark();
			reservation.writeRemark(header, pnr, remark);
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.USER_NOT_AUTHORIZED);
			return doc;
		} catch (ConfigurationException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.CONFIGURATION_ERROR);
			return doc;
		} catch (UnexpectedException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.UNEXPECTED_EXCEPTION);
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
			User user = ServiceUtilities.getAndAuthorizeUser(username, password,
			    PermissionType.GET_PREPOP_DATA);
			ReservationInterface reservation = ServiceUtilities
			    .getReservationSystem(user);
			String pnr = getReservationData.getGetReservationData().getPnr();
			String bagTag = getReservationData.getGetReservationData().getBagTag();
			reservation.getReservationData(header, pnr, bagTag);
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.USER_NOT_AUTHORIZED);
			return doc;
		} catch (ConfigurationException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.CONFIGURATION_ERROR);
			return doc;
		} catch (UnexpectedException e) {
			WebServiceError error = res1.addNewError();
			error
			    .setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.UNEXPECTED_EXCEPTION);
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
}
