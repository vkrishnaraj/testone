/**
 * NTPassengerViewServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.passengerview;

import com.bagnet.nettracer.ws.core.NTCoreServiceSkeleton;

/**
 *  NTPassengerViewServiceSkeleton java skeleton for the axisService
 */
public class NTPassengerViewServiceImplementation extends NTPassengerViewServiceSkeleton {
  
	@Deprecated
	/**
     * Auto generated method signature
     * @param getIncidentPV
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument getIncidentPV(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument getIncidentPV) {
      return new PassengerViewUtil().getIncidentPV(getIncidentPV);
    }
    
    /**
     * Auto generated method signature
     * @param getIncidentPV2
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument getIncidentPV2(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document getIncidentPV2) {
      return new PassengerViewUtil().getIncidentPV2(getIncidentPV2);
    }

}
