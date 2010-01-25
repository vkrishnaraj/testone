package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.io.IOException;
import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpException;
import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.ISharesServiceManager;

public class TestA {

	@Test
	public void testBDO()	throws CommandNotProperlyFormedException, HttpException, IOException {
		/*
		 WM BDO AHL XAXUS10491
		 DS XAXUS01/CT01
		 */
		// Create Pay load & Type
		WorldTracerActionType type =  WorldTracerActionType.CREATE_BDO;
		Calendar deliveryDate =  Calendar.getInstance();
		Agent BDOAgent = new Agent();
		BDOAgent.setUsername("TEST");
		BDOAgent.setAirline("US");
 
		Bdo payload = new Bdo();

		payload.setStationCode("XAX");
		payload.setAirlineCode("US");
		payload.setAhlId("XAXUS10491");
		payload.setAgent(BDOAgent);
		payload.setDeliveryDate(deliveryDate);


		
		// Initialize other data (do not change)
		
		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 1);
		
		
		
		
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, payload, true, null);
		AbstractServiceManager a = ISharesServiceManager.getInstance();
		WorldTracerResponse response = a.process(dto);
//		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, false, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
//		impl.insertBdo(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		sess.close();
		System.out.println(response);
		Assert.assertEquals(true, response.isSuccess());
	}	
}
