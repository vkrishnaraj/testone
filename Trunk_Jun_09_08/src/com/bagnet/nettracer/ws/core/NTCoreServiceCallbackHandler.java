/**
 * NTCoreServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.core;


/**
 *  NTCoreServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class NTCoreServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public NTCoreServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public NTCoreServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for insertIncident method
     * override this method for handling normal response from insertIncident operation
     */
    public void receiveResultinsertIncident(
        com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from insertIncident operation
     */
    public void receiveErrorinsertIncident(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for insertQuickOHD method
     * override this method for handling normal response from insertQuickOHD operation
     */
    public void receiveResultinsertQuickOHD(
        com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from insertQuickOHD operation
     */
    public void receiveErrorinsertQuickOHD(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getIncident method
     * override this method for handling normal response from getIncident operation
     */
    public void receiveResultgetIncident(
        com.bagnet.nettracer.ws.core.GetIncidentResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getIncident operation
     */
    public void receiveErrorgetIncident(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getOHD method
     * override this method for handling normal response from getOHD operation
     */
    public void receiveResultgetOHD(
        com.bagnet.nettracer.ws.core.GetOHDResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getOHD operation
     */
    public void receiveErrorgetOHD(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for logoff method
     * override this method for handling normal response from logoff operation
     */
    public void receiveResultlogoff(
        com.bagnet.nettracer.ws.core.LogoffResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from logoff operation
     */
    public void receiveErrorlogoff(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for authenticate method
     * override this method for handling normal response from authenticate operation
     */
    public void receiveResultauthenticate(
        com.bagnet.nettracer.ws.core.AuthenticateResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from authenticate operation
     */
    public void receiveErrorauthenticate(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for insertOHD method
     * override this method for handling normal response from insertOHD operation
     */
    public void receiveResultinsertOHD(
        com.bagnet.nettracer.ws.core.InsertOHDResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from insertOHD operation
     */
    public void receiveErrorinsertOHD(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for beornOHD method
     * override this method for handling normal response from beornOHD operation
     */
    public void receiveResultbeornOHD(
        com.bagnet.nettracer.ws.core.BeornOHDResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from beornOHD operation
     */
    public void receiveErrorbeornOHD(java.lang.Exception e) {
    }
}
