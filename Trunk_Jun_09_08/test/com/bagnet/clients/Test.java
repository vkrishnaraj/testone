package com.bagnet.clients;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;

import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.OnlineClaimsServiceStub;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.LoadClaim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;

public class Test {

	private static final ConfigurationContext ENDPOINT = null;

	public void onlineClaim() {
		try {
			OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
			
			LoadClaimDocument doc = LoadClaimDocument.Factory.newInstance();
			
			LoadClaim doc2 = doc.addNewLoadClaim();
			
			NtAuth auth = doc2.addNewAuth();
			auth.setPassword("B651kLN5");
			auth.setUsername("onlineclaims");
			doc2.setClaimId(1);
			doc2.setIncidentId("CBSB600057287");
			doc2.setName("SMITH");
			
			stub.loadClaim(doc);
			
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
