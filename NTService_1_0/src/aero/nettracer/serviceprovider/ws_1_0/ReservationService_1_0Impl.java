/**
 * ReservationService_1_0Skeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.ws_1_0;

import aero.nettracer.serviceprovider.common.PermissionConstants;
import aero.nettracer.serviceprovider.common.auth.ServiceAuthenticator;
import aero.nettracer.serviceprovider.common.auth.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;

/**
 *  ReservationService_1_0Skeleton java skeleton for the axisService
 */
public class ReservationService_1_0Impl {
    /**
     * Auto generated method signature
     * @param getEnplanements
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument getEnplanements(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument getEnplanements) {
    	//TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getEnplanements");
    }

    /**
     * Auto generated method signature
     * @param writeRemark
     */
    public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument writeRemark(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument writeRemark) {
        //TODO : fill this with the necessary business logic
    	
    	WriteRemarkResponseDocument doc = WriteRemarkResponseDocument.Factory.newInstance();
    	WriteRemarkResponse res = doc.addNewWriteRemarkResponse();
    	RemarkResponse res1 = res.addNewReturn();
    	
    	RequestHeader header = writeRemark.getWriteRemark().getHeader();
    	String username = header.getUsername();
    	String password = header.getPassword();
    	
    	try {
			User user = ServiceAuthenticator.getAndAuthorizeUser(username, password, PermissionConstants.WRITE_REMARK);
			//user.getProfile()
		} catch (UserNotAuthorizedException e) {
			WebServiceError error = res1.addNewError();
			error.setDescription(aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError.USER_NOT_AUTHORIZED);
			// TODO Auto-generated catch block
		}
		
		return doc;
    	
//        throw new java.lang.UnsupportedOperationException("Please implement " +
//            this.getClass().getName() + "#writeRemark");
    }

    /**
     * Auto generated method signature
     * @param getReservationData
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument getReservationData(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument getReservationData) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getReservationData");
    }

    /**
     * Auto generated method signature
     * @param getOsiContents
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument getOsiContents(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument getOsiContents) {
        //TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement " +
            this.getClass().getName() + "#getOsiContents");
    }
}
