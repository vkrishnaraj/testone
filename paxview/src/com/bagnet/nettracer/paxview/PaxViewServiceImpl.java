package com.bagnet.nettracer.paxview;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVIncident;
import com.bagnet.nettracer.ws.passengerview.NTPassengerViewServiceLocator;
import com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType;

public class PaxViewServiceImpl implements PaxViewService {

	private static final Logger logger = Logger.getLogger(PaxViewServiceImpl.class);

	private NTPassengerViewServiceLocator serviceLocator;

	public PaxViewServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.paxview.PaxViewService#getAdvancedIncidentPV(java.lang.String, java.lang.String, boolean)
	 */
	public WS_PVAdvancedIncident getAdvancedIncidentPV(String incident_id, String lastname, boolean doNotAuthorize)
			throws RemoteException, ServiceException {

		NTPassengerViewServicePortType stub;
		try {
			stub = serviceLocator.getNTPassengerViewServiceSOAP12port_http();
		}
		catch (ServiceException e) {
			logger.error("unable to get WebService Stub", e);
			throw e;
		}

		try {
			return stub.getAdvancedIncidentPV(incident_id, lastname, doNotAuthorize);
		}
		catch (RemoteException e) {
			logger.error("error executing AdvancedIncidentPV", e);
			throw e;
		}

	}

	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.paxview.PaxViewService#getIncidentPV(java.lang.String, java.lang.String)
	 */
	public WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname)
			throws java.rmi.RemoteException, ServiceException {
		NTPassengerViewServicePortType stub;
		try {
			stub = serviceLocator.getNTPassengerViewServiceSOAP12port_http();
		}
		catch (ServiceException e) {
			logger.error("unable to get WebService Stub", e);
			throw e;
		}

		try {
			return stub.getIncidentPV(incident_id, lastname);
		}
		catch (RemoteException e) {
			logger.error("error executing getIncidentPV", e);
			throw e;
		}
	}

	public void setServiceLocator(NTPassengerViewServiceLocator sl) {
		this.serviceLocator = sl;
	}

	
}
