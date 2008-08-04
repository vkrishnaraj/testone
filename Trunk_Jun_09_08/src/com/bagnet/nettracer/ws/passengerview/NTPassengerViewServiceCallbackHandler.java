/**
 * NTPassengerViewServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 *  NTPassengerViewServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class NTPassengerViewServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public NTPassengerViewServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public NTPassengerViewServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getIncidentPV method
     * override this method for handling normal response from getIncidentPV operation
     */
    public void receiveResultgetIncidentPV(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getIncidentPV operation
     */
    public void receiveErrorgetIncidentPV(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getIncidentPV2 method
     * override this method for handling normal response from getIncidentPV2 operation
     */
    public void receiveResultgetIncidentPV2(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getIncidentPV2 operation
     */
    public void receiveErrorgetIncidentPV2(java.lang.Exception e) {
    }
}
