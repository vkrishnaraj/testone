package com.bagnet.nettracer.paxview;

import javax.xml.rpc.ServiceException;

import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;

public interface PaxViewService {

	public abstract WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname)
			throws java.rmi.RemoteException, ServiceException;

}