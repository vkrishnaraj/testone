package com.bagnet.nettracer.ws.onlineclaims;

import java.rmi.RemoteException;

import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;

public class OnlineClaimsSampleClient {
	
	private final String DB_DRIVEN_ENDPOINT = "http://somewhere.com";
	
	private boolean adminLoginTest() throws RemoteException {
		
		// Prepare stub with endpoint
		OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(DB_DRIVEN_ENDPOINT);
		
		// Prepare XML documents for request
		AuthAdminUserDocument request = AuthAdminUserDocument.Factory.newInstance();
		AuthAdminUser subDoc1 = request.addNewAuthAdminUser();
		
		subDoc1.setUsername("user's username");
		subDoc1.setPassword("user's password");
		
		// Set System Username & PW
		NtAuth subDoc2 = subDoc1.addNewAuth();
		subDoc2.setUsername("system username");
		subDoc2.setPassword("system password");
		
		// Perform Web Service Request
		AuthAdminUserResponseDocument response = stub.authAdminUser(request);
		
		// Process response
		boolean successfulLogin = response.getAuthAdminUserResponse().getReturn();
		return successfulLogin;
	}
}
