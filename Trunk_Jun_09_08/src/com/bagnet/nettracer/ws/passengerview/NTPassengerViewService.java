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
}
