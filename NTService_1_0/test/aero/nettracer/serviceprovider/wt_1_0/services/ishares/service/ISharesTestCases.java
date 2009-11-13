package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import junit.framework.Assert;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.WorldTracerServiceImpl;

public class ISharesTestCases {

	@Test
	public void TestParsingActionFile()	throws CommandNotProperlyFormedException {
		
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
	
	
	
	
}
