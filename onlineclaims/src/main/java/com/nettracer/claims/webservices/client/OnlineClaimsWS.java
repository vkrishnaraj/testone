package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
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

	boolean savePassengerInfo(PassengerBean passengerBean, Claim claim)	throws AxisFault, RemoteException;

	public Claim getClaim(WSPVAdvancedIncident passengerData,
			String lastName) throws AxisFault, RemoteException;

	public PassengerBean getPassengerData(WSPVAdvancedIncident passengerData,
			Claim claim) throws AxisFault, RemoteException;

	public boolean saveFlightInfo(PassengerBean passengerBean, Claim claim)
			throws AxisFault, RemoteException;

	public boolean saveBagInfo(PassengerBean passengerBean, Claim claim)
			throws AxisFault, RemoteException;

	public boolean saveFileInfo(PassengerBean passengerBean, Claim claim)throws AxisFault, RemoteException;

	public boolean saveFraudQuestion(PassengerBean passengerBean,
			Claim claim)throws AxisFault, RemoteException;

	public boolean saveFinalCLaim(PassengerBean passengerBean, Claim claim)throws AxisFault, RemoteException;
}
