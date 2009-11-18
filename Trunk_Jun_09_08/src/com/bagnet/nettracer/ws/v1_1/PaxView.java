package com.bagnet.nettracer.ws.v1_1;

import com.bagnet.nettracer.ws.v1_1.paxview.WS_PVIncident;

public class PaxView {

	public WS_PVIncident getPaxViewIvr(String incident_id, String username, String password) {
		WS_PVIncident si = null;
		return si;
	}
	
	public WS_PVIncident getPaxView(String incident_id, String lastname, String username, String password, boolean msgsReadByUser) {
		WS_PVIncident si = null;
		return si;
	}
	
	public WS_PVIncident writePassengerComment(String incident_id, String comment, String username, String password) {
		return null;
	}
}
