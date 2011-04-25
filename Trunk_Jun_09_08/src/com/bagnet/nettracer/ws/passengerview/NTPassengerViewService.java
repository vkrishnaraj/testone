package com.bagnet.nettracer.ws.passengerview;

import com.bagnet.nettracer.ws.core.pojo.WS_PVIncident;
import com.bagnet.nettracer.ws.core.pojo.WS_PVAdvancedIncident;


public class NTPassengerViewService {


	/*
	 * getIncidentPV - Only returns first passenger in list.
	 * @deprecated
	 */
	public WS_PVIncident getIncidentPV(String incident_id, String lastname) {
		WS_PVIncident si = null;
		return si;
	}
	
	/*
	 * getIncidentPV2 - returns an array of passengers instead of just one and
	 * allows the user to indicate they do not want any authorization performed
	 * against the name provided. 
	 */
	public WS_PVAdvancedIncident getAdvancedIncidentPV(String incident_id, String lastname, boolean doNotAuthorize) {
		WS_PVAdvancedIncident si = null;
		return si;
	}
	
	/*
	 * getIncidentPV3 - returns an array of passengers instead of just one and
	 * allows the user to indicate they do not want any authorization performed
	 * against the name provided. Also records the agent that called this service.
	 */
	public WS_PVAdvancedIncident getAdvancedIncidentPVWithAgent(String incident_id, String lastname, String callingAgent, boolean doNotAuthorize) {
		WS_PVAdvancedIncident si = null;
		return si;
	}
	
	/*
	 * getIncidentsPV1 - returns an array of advanced incidents instead of just
	 * one and uses the frequent flyer id to fetch them.
	 */
	public WS_PVAdvancedIncident[] getAdvancedIncidentsPVFrequentFlyer(String frequentFlyer, String callingAgent) {
		WS_PVAdvancedIncident[] si = null;
		return si;
	}
	
	/*
	 * getIncidentsPV2 - returns an array of advanced incidents instead of just
	 * one and uses the reservation record locator to fetch them.
	 */
	public WS_PVAdvancedIncident[] getAdvancedIncidentsPVPnr(String recordLocator, String callingAgent) {
		WS_PVAdvancedIncident[] si = null;
		return si;
	}
	
	/*
	 * getIncidentsPV3 - returns an array of advanced incidents instead of just
	 * one and uses the passenger's phone number to fetch them.
	 */
	public WS_PVAdvancedIncident[] getAdvancedIncidentsPVPhone(String phoneNumber, String callingAgent) {
		WS_PVAdvancedIncident[] si = null;
		return si;
	}
	
	/*
	 * getActivePhoneList - returns an array of phone numbers that are tied to all
	 * open incidents in NetTracer.
	 */
	public String[] getActivePhoneList() {
		String[] si = null;
		return si;
	}
}
