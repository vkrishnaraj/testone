package com.bagnet.nettracer.paxview;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVIncident;

public interface PaxViewService {

	public abstract WS_PVAdvancedIncident getAdvancedIncidentPV(String incident_id, String lastname,
			boolean doNotAuthorize) throws RemoteException, ServiceException;

	public abstract WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname)
			throws java.rmi.RemoteException, ServiceException;

}