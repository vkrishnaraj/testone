/**
 * NTCoreServiceImplementation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.core;

import com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse;

/**
 *  NTCoreServiceSkeleton java skeleton for the axisService
 */
public class NTCoreServiceImplementation extends NTCoreServiceSkeleton{
    /**
     * Auto generated method signature
     * @param insertIncident
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument insertIncident(
        com.bagnet.nettracer.ws.core.InsertIncidentDocument insertIncident) {
    	return new WSCoreIncidentUtil().insertIncident(insertIncident);
    }

    /**
     * Auto generated method signature
     * @param insertQuickOHD
     */
    public com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument insertQuickOHD(
        com.bagnet.nettracer.ws.core.InsertQuickOHDDocument insertQuickOHD) {
    	return new WSCoreOHDUtil().insertQOHD(insertQuickOHD);
    }

    /**
     * Auto generated method signature
     * @param getIncident
     */
    public com.bagnet.nettracer.ws.core.GetIncidentResponseDocument getIncident(
        com.bagnet.nettracer.ws.core.GetIncidentDocument getIncident) {
    	return new WSCoreIncidentUtil().getIncident(getIncident);
    }

    /**
     * Auto generated method signature
     * @param getOHD
     */
    public com.bagnet.nettracer.ws.core.GetOHDResponseDocument getOHD(
        com.bagnet.nettracer.ws.core.GetOHDDocument getOHD) {
    	return new WSCoreOHDUtil().getOHD(getOHD);
    }

    /**
     * Auto generated method signature
     * @param logoff
     */
    public com.bagnet.nettracer.ws.core.LogoffResponseDocument logoff(
        com.bagnet.nettracer.ws.core.LogoffDocument logoff) {
	  	return new WSCoreUtil().logoff(logoff);
    }

    /**
     * Auto generated method signature
     * @param authenticate
     */
    public com.bagnet.nettracer.ws.core.AuthenticateResponseDocument authenticate(
        com.bagnet.nettracer.ws.core.AuthenticateDocument authenticate) {
  		return new WSCoreUtil().authenticate(authenticate);
    }

    /**
     * Auto generated method signature
     * @param insertOHD
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument insertOHD(
        com.bagnet.nettracer.ws.core.InsertOHDDocument insertOHD) {
    	return new WSCoreOHDUtil().insertOHD(insertOHD);
    }

    /**
     * Auto generated method signature
     * @param beornOHD
     */
    public com.bagnet.nettracer.ws.core.BeornOHDResponseDocument beornOHD(
        com.bagnet.nettracer.ws.core.BeornOHDDocument beornOHD) {
    	return new WSCoreOHDUtil().beornOHD(beornOHD);
    }
    
    /**
     * Auto generated method signature
     * @param beornOHD
     */
//    public BeornOHDResponseDocument[] bulkBeornOHD(
//        com.bagnet.nettracer.ws.core.BeornOHDDocument[] beornOHDList) {
//    	return new WSCoreOHDUtil().bulkdBeornOHD(beornOHDList);
//    }
    
    public com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument bulkBeornOHD(
            com.bagnet.nettracer.ws.core.BulkBeornOHDDocument bulkBeornOHD) {
    	return new WSCoreOHDUtil().bulkBeornOHD(bulkBeornOHD);
     }
    
    
    /**
     * Auto generated method signature
     * @param updateIncidentFaultCodes
     */
    public com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument updateIncidentFaultCodes(
        com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument updateIncidentFaultCodes) {
        
    	return new WSCoreIncidentUtil().updateIncidentFaultCodes(updateIncidentFaultCodes);
    }
    
    /**
     * Auto generated method signature
     * @param queryForFaultCode
     */
    public com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument queryForFaultCode(
        com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument queryForFaultCode) {
    	return new WSCoreIncidentUtil().queryForFaultCode(queryForFaultCode);   	
    }
    
  	public com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument UpdateBdoDelivery(com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument updateBdoDelivery) {
  		return new WSCoreIncidentUtil().createBdo(updateBdoDelivery);
  	}

}
