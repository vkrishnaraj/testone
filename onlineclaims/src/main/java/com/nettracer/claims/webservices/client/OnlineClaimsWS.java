package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.nettracer.claims.core.model.PassengerBean;

//@WebService()
public interface OnlineClaimsWS {
	public static final String ENDPOINT = "http://74.188.84.58:8080/tracer/services/OnlineClaimsService";
	public static final String SYSTEM_USERNAME = "onlineclaims";
	public static final String SYSTEM_PASSWORD = "B651kLN5";
	//@WebMethod(operationName="authAdminUser")	
    //@WebResult(targetNamespace="http://onlineclaims.ws.nettracer.bagnet.com", name="authAdmin")
	public boolean authAdminUser(/*@WebParam(name="userName")*/String userName,/*@WebParam(name="password")*/ String password)
	 	throws AxisFault,RemoteException;

	PassengerView getPassengerView(String claimNumber, String lastName)
			throws AxisFault, RemoteException;

	public PassengerBean getPassengerData(WSPVAdvancedIncident passengerData);

	public boolean savePassengerInfo(PassengerBean passengerBean) throws AxisFault, RemoteException;
}
