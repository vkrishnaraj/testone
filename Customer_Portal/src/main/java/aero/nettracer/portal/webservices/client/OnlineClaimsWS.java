package aero.nettracer.portal.webservices.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import aero.nettracer.portal.model.PassengerBean;
import aero.nettracer.portal.utils.ClaimsProperties;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;

//@WebService()
public interface OnlineClaimsWS {
	public static final String ENDPOINT = ClaimsProperties.get(ClaimsProperties.WS_LOCATION);
	public static final String SYSTEM_USERNAME = "onlineclaims";
	public static final String SYSTEM_PASSWORD = "B651kLN5";

	PassengerView getPassengerView(String claimNumber, String lastName, String firstName)
			throws AxisFault, RemoteException;

	public Claim getClaim(WSPVAdvancedIncident passengerData,
			String lastName, String firstName) throws AxisFault, RemoteException;

	public PassengerBean getPassengerData(WSPVAdvancedIncident passengerData,
			Claim claim) throws AxisFault, RemoteException;

	public boolean sendToNT(PassengerBean passengerBean, Claim claim)
			throws AxisFault, RemoteException;
}
