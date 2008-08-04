package com.bagnet.nettracer.ws.passengerview;

import com.bagnet.nettracer.ws.core.pojo.WS_PVIncident;
import com.bagnet.nettracer.ws.core.pojo.WS_PVIncident2;


public class NTPassengerViewService {


	@Deprecated
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
	public WS_PVIncident2 getIncidentPV2(String incident_id, String lastname, boolean doNotAuthorize) {
		WS_PVIncident2 si = null;
		return si;
	}
}
