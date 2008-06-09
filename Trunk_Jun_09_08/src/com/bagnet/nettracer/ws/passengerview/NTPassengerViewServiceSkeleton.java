/**
 * NTPassengerViewServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.passengerview;

/**
 *  NTPassengerViewServiceSkeleton java skeleton for the axisService
 */
public class NTPassengerViewServiceSkeleton {
    /**
     * Auto generated method signature
     * @param getIncidentPV
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument getIncidentPV(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument getIncidentPV) {
        //TODO : fill this with the necessary business logic
    	PassengerViewUtil pv = new PassengerViewUtil();
      return pv.getIncidentPV(getIncidentPV);
    }
}
