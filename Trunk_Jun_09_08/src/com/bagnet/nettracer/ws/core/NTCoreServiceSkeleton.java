/**
 * NTCoreServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.core;

/**
 *  NTCoreServiceSkeleton java skeleton for the axisService
 */
public class NTCoreServiceSkeleton {
    /**
     * Auto generated method signature
     * @param authenticate
     */
    public com.bagnet.nettracer.ws.core.AuthenticateResponseDocument authenticate(
        com.bagnet.nettracer.ws.core.AuthenticateDocument authenticate) {
        //TODO : fill this with the necessary business logic
    	WSCoreUtil coreutil = new WSCoreUtil();
  		return coreutil.authenticate(authenticate);
    }

    /**
     * Auto generated method signature
     * @param getOHDsForWT
     */
    public com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument getOHDsForWT(
        com.bagnet.nettracer.ws.core.GetOHDsForWTDocument getOHDsForWT) {
        //TODO : fill this with the necessary business logic
    	WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.getOHDsForWT(getOHDsForWT);
    }

    /**
     * Auto generated method signature
     * @param getOHD
     */
    public com.bagnet.nettracer.ws.core.GetOHDResponseDocument getOHD(
        com.bagnet.nettracer.ws.core.GetOHDDocument getOHD) {
        //TODO : fill this with the necessary business logic
    	WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.getOHD(getOHD);
    }

    /**
     * Auto generated method signature
     * @param insertOHD
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument insertOHD(
        com.bagnet.nettracer.ws.core.InsertOHDDocument insertOHD) {
        //TODO : fill this with the necessary business logic
    	WSCoreOHDUtil coreutil = new WSCoreOHDUtil();
    	return coreutil.insertOHD(insertOHD);
    }

    /**
     * Auto generated method signature
     * @param getIncident
     */
    public com.bagnet.nettracer.ws.core.GetIncidentResponseDocument getIncident(
        com.bagnet.nettracer.ws.core.GetIncidentDocument getIncident) {
        //TODO : fill this with the necessary business logic
    	WSCoreIncidentUtil coreutil = new WSCoreIncidentUtil();
    	return coreutil.getIncident(getIncident);
    }

    /**
     * Auto generated method signature
     * @param logoff
     */
    public com.bagnet.nettracer.ws.core.LogoffResponseDocument logoff(
        com.bagnet.nettracer.ws.core.LogoffDocument logoff) {
        //TODO : fill this with the necessary business logic
    	WSCoreUtil coreutil = new WSCoreUtil();
  		return coreutil.logoff(logoff);
    }

    /**
     * Auto generated method signature
     * @param insertIncident
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument insertIncident(
        com.bagnet.nettracer.ws.core.InsertIncidentDocument insertIncident) {
        //TODO : fill this with the necessary business logic
    	WSCoreIncidentUtil coreutil = new WSCoreIncidentUtil();
    	return coreutil.insertIncident(insertIncident);
    }
}
