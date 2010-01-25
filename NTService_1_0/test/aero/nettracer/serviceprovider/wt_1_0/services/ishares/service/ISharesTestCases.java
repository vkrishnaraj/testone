package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.io.IOException;
import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.Profile;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerTimeoutException;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.ISharesServiceManager;

public class ISharesTestCases {

	@Test
	public void TestParsingActionFile()	throws CommandNotProperlyFormedException, HttpException, IOException, WorldTracerTimeoutException {
		
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
	public void testPlaceActionFileFile()	throws CommandNotProperlyFormedException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.PLACE_ACTION_FILE;
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
	public void testEraseSuccessActionFile() throws CommandNotProperlyFormedException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ERASE_ACTION_FILE;

		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setStation("XAX");
		payload.setAirline("US");
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
	public void testEraseFailureActionFile()	throws CommandNotProperlyFormedException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ERASE_ACTION_FILE;

		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setStation("XAX");
		payload.setAirline("US");
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

	@Test
	public void testRequestOhd() throws CommandNotProperlyFormedException, WorldTracerException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.REQUEST_OHD;

		RequestOhd payload = new RequestOhd();
		Ahl myAhl = new Ahl();
		myAhl.setAhlId("JFKZZ14361");
		Passenger[] myPaxes = new Passenger[1];
		Passenger myPax = new Passenger();
		myPax.setLastname("PHILPOTT");
		myPaxes[0] = myPax;
		myAhl.setPax(myPaxes);
		payload.setAhl(myAhl);
		payload.setOhdId("LGWZZ33451");
		
		payload.setFurtherInfo("FURTHER INFO FREE TEXT.");
		Agent myAgent = new Agent();
		myAgent.setAirline("US");
		myAgent.setUsername("CHUCK");
		payload.setAgent(myAgent);
		
		String[] teletype = {"HDQZZUS"};
		payload.setTeletype(teletype);
		
		// create a User, Profile objs ()
		User user = new User();
		Profile profile = new Profile();
		profile.setAirline("US");
		user.setProfile(profile);
		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
		impl.requestOhd(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(true, response.isSuccess());
	}
	
	@Test
	public void testRequestQuickOhd() throws CommandNotProperlyFormedException, WorldTracerException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.REQUEST_QOHD;

		RequestOhd payload = new RequestOhd();
		Ahl myAhl = new Ahl();
		myAhl.setAhlId("JFKZZ14361");
		Passenger[] myPaxes = new Passenger[1];
		Passenger myPax = new Passenger();
		myPax.setLastname("PHILPOTT");
		myPaxes[0] = myPax;
		myAhl.setPax(myPaxes);
		payload.setAhl(myAhl);
		payload.setBagTagNumber("US123456");
		payload.setFurtherInfo("FURTHER INFO FREE TEXT.");
		Agent myAgent = new Agent();
		myAgent.setAirline("US");
		myAgent.setUsername("CHUCK");
		payload.setAgent(myAgent);
		
		payload.setFromAirline("US");
		payload.setFromStation("XAX");
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		
		// create a User, Profile objs ()
		User user = new User();
		Profile profile = new Profile();
		profile.setAirline("US");
		user.setProfile(profile);
	
		
		
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, null);
		
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
		impl.requestQuickOhd(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(true, response.isSuccess());
	}
	
	@Test
	public void testGetActionFileSummary() throws CommandNotProperlyFormedException, WorldTracerException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Payload & Type
		WorldTracerActionType type =  WorldTracerActionType.ACTION_FILE_SUMMARY;

		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setStation("ATL");
		payload.setAirline("US");
		payload.setType("AP");
		payload.setDay(3);
		

		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		
		// create a User, Profile objs ()
		User user = new User();
		Profile profile = new Profile();
		profile.setAirline("US");
		user.setProfile(profile);
	
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, null);
		
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
		impl.getActionFileSummary(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(true, response.isSuccess());
	}
	
	@Test
	public void testBDO()	throws CommandNotProperlyFormedException, HttpException, IOException, WorldTracerTimeoutException {
		
		// Create Pay load & Type
		WorldTracerActionType type =  WorldTracerActionType.CREATE_BDO;
		Calendar deliveryDate =  Calendar.getInstance();
		Agent BDOAgent = new Agent();
		BDOAgent.setUsername("Bruce");
		BDOAgent.setAirline("US");
 
		Bdo payload = new Bdo();

		payload.setStationCode("XAX");
		payload.setAirlineCode("US");
		payload.setAhlId("XAXUS10485");
		payload.setAgent(BDOAgent);
		payload.setDeliveryDate(deliveryDate);


		
		// Initialize other data (do not change)
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
		impl.insertBdo(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		Assert.assertEquals(true, response.isSuccess());
	}	
}
