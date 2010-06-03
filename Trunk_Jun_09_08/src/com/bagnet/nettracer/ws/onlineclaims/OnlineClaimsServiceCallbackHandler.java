/**
 * OnlineClaimsServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 *  OnlineClaimsServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class OnlineClaimsServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public OnlineClaimsServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public OnlineClaimsServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for saveClaim method
     * override this method for handling normal response from saveClaim operation
     */
    public void receiveResultsaveClaim(
        com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveClaim operation
     */
    public void receiveErrorsaveClaim(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for authAdminUser method
     * override this method for handling normal response from authAdminUser operation
     */
    public void receiveResultauthAdminUser(
        com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from authAdminUser operation
     */
    public void receiveErrorauthAdminUser(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for uploadFile method
     * override this method for handling normal response from uploadFile operation
     */
    public void receiveResultuploadFile(
        com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from uploadFile operation
     */
    public void receiveErroruploadFile(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for authPassenger method
     * override this method for handling normal response from authPassenger operation
     */
    public void receiveResultauthPassenger(
        com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from authPassenger operation
     */
    public void receiveErrorauthPassenger(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for loadClaim method
     * override this method for handling normal response from loadClaim operation
     */
    public void receiveResultloadClaim(
        com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from loadClaim operation
     */
    public void receiveErrorloadClaim(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for deleteFile method
     * override this method for handling normal response from deleteFile operation
     */
    public void receiveResultdeleteFile(
        com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from deleteFile operation
     */
    public void receiveErrordeleteFile(java.lang.Exception e) {
    }
}
