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
		
		ActionFileRequestData payload = new ActionFileRequestData();
		payload.setAirline("US");
		payload.setStation("XAX");
		
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionDTO dto = new WorldTracerActionDTO(WorldTracerActionType.ACTION_FILE_COUNTS, null, payload, true, null);
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, true);
		impl.getActionFileCounts(dto, payload, response);
		
		// Perform any necessary assertions here
		// Also use debug statement here to manually validate response data 
		Assert.assertEquals(response.getCounts().length, 13);
	}
	
	
}
