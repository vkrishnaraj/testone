/**
 * NTPassengerViewServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.passengerview;

/**
 * NTPassengerViewServiceSkeleton java skeleton for the axisService
 */
public class NTPassengerViewServiceImplementation extends
		NTPassengerViewServiceSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * @param getIncidentPV
	 */
	public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument getIncidentPV(
			com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument getIncidentPV) {
		return new PassengerViewUtil().getIncidentPV(getIncidentPV);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getAdvancedIncidentPV
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument getAdvancedIncidentPV(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument getAdvancedIncidentPV) {
		return new PassengerViewUtil().getAdvancedIncidentPV(getAdvancedIncidentPV);
	}
    
    /**
     * Auto generated method signature
     * @param getAdvancedIncidentPVWithAgent
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument getAdvancedIncidentPVWithAgent
        (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentDocument getAdvancedIncidentPVWithAgent) {
		return new PassengerViewUtil().getAdvancedIncidentPVWithAgent(getAdvancedIncidentPVWithAgent);
    }

	/**
	 * Auto generated method signature
	 * 
	 * @param getAdvancedIncidentsPVFrequentFlyer
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerResponseDocument getAdvancedIncidentsPVFrequentFlyer(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument getAdvancedIncidentsPVFrequentFlyer) {
		return new PassengerViewUtil().getAdvancedIncidentsPVFrequentFlyer(getAdvancedIncidentsPVFrequentFlyer);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getAdvancedIncidentsPVPnr
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument getAdvancedIncidentsPVPnr(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument getAdvancedIncidentsPVPnr) {
		return new PassengerViewUtil().getAdvancedIncidentsPVPnr(getAdvancedIncidentsPVPnr);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @param getAdvancedIncidentsPVPhone
	 */
	public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneResponseDocument getAdvancedIncidentsPVPhone(
			com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument getAdvancedIncidentsPVPhone) {
		return new PassengerViewUtil().getAdvancedIncidentsPVPhone(getAdvancedIncidentsPVPhone);
	}

	/**
	 * Auto generated method signature
	 * 
	 */
	public com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument getActivePhoneList() {
		return new PassengerViewUtil().getActivePhoneList();
	}

}
