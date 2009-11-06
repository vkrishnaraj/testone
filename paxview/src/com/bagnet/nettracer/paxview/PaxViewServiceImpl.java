package com.bagnet.nettracer.paxview;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.ws.v1_1.PaxViewLocator;
import com.bagnet.nettracer.ws.v1_1.PaxViewPortType;
import com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident;

public class PaxViewServiceImpl implements PaxViewService {

	private static final Logger logger = Logger.getLogger(PaxViewServiceImpl.class);

	private PaxViewLocator serviceLocator;

	public PaxViewServiceImpl() {
	}


	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.paxview.PaxViewService#getIncidentPV(java.lang.String, java.lang.String)
	 */
	public WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname, boolean msgSeenByUser)
			throws java.rmi.RemoteException, ServiceException {
		
		PaxViewPortType stub;
		try {
			
			stub = serviceLocator.getPaxViewSOAP12port_http();
		}
		catch (ServiceException e) {
			logger.error("unable to get WebService Stub", e);
			throw e;
		}

		try {
			return stub.getPaxView(incident_id, lastname, "PaxViewUser", "Password", msgSeenByUser);
		}
		catch (RemoteException e) {
			logger.error("error executing getIncidentPV", e);
			throw e;
		}
	}

	public void setServiceLocator(PaxViewLocator sl) {
		this.serviceLocator = sl;
	}


	@Override
	public WS_PVIncident writeComment(String claimnumber, String comment) throws ServiceException, RemoteException {
		PaxViewPortType stub;
		try {
			
			stub = serviceLocator.getPaxViewSOAP12port_http();
		}
		catch (ServiceException e) {
			logger.error("unable to get WebService Stub", e);
			throw e;
		}

		try {
			return stub.writePassengerComment(claimnumber, comment, "PaxViewUser", "Password");
		}
		catch (RemoteException e) {
			logger.error("error executing getIncidentPV", e);
			throw e;
		}
	}



	
}
