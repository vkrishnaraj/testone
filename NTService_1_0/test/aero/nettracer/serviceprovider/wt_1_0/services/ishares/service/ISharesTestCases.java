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
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
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
	
	@Test
	public void testPlaceActionFileFile()	throws CommandNotProperlyFormedException, HttpException, IOException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_COUNTS;
		Pxf payload = new Pxf();
		payload.setContent("TEXT CONTENT");
		payload.setDestination(1);
		payload.setSendingStation("XAX");
		
		PxfDetails details1 = new PxfDetails();
		details1.setStation("XLF");
		details1.setArea("AP");
		details1.setAirline("US");
		
		PxfDetails details2 = new PxfDetails();
		details2.setStation("XAX");
		details2.setArea("AA");
		details2.setAirline("US");
		
		PxfDetails[] d = new PxfDetails[2];
		d[0] = details1;
		d[1] = details2;
		payload.setPxfDetails(d);
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true);
		
		// Perform Action
		impl.placeActionFile(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		int i = 0;
	}
	
	@Test
	public void testEraseSuccessActionFile()	throws CommandNotProperlyFormedException, HttpException, IOException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_COUNTS;

		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setStation("XAX");
		payload.setDay(1);
		payload.setNumber(1);
		payload.setType("AA");
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
		impl.eraseActionFile(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(true, response.isSuccess());
	}	
	

	@Test
	public void testEraseFailureActionFile()	throws CommandNotProperlyFormedException, HttpException, IOException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_COUNTS;

		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setStation("XAX");
		payload.setDay(1);
		payload.setNumber(1);
		payload.setType("AA");
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_FAILURE);
		
		// Perform Action
		impl.eraseActionFile(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(false, response.isSuccess());
	}	


}
