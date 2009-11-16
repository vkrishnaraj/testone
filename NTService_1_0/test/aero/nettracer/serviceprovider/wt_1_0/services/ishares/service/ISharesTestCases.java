package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.ISharesServiceManager;

public class ISharesTestCases {

	@Test
	public void TestParsingActionFile()	throws CommandNotProperlyFormedException, HttpException, IOException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_COUNTS;
		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setAirline("US");
		payload.setStation("XAX");
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true);
		
		// Perform Action
		impl.getActionFileCounts(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(response.isSuccess(), true); 
		Assert.assertEquals(response.getCounts().length, 13);
	}
	
	/**
	* END TO END TEST
	*/
	//@Test
	public void TestCountActionFiles() throws UserNotAuthorizedException {
		String username = "ishares_local";
		String password = "password";
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_COUNTS;
		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setAirline("US");
		payload.setStation("XAX");
		
		User user = ServiceUtilities.getAndAuthorizeUser(username, password, PermissionType.WORLDTRACER);
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, null);
		
		ISharesServiceManager svc = new ISharesServiceManager();
		response = svc.process(dto);
		int i = 0;

	}
	
	
	
	
}
