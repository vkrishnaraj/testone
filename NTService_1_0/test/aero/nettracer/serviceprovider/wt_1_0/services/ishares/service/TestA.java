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
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.ISharesServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.webservices.WorldTracerServiceImpl;

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
	
	@Test
	public void testAHL()	throws CommandNotProperlyFormedException, HttpException, IOException {
		/*
		 WM BDO AHL XAXUS10491
		 DS XAXUS01/CT01
		 */
		// Create Pay load & Type
		WorldTracerActionType type =  WorldTracerActionType.CREATE_AHL;
		Calendar departureDate =  Calendar.getInstance();
		Calendar arrivalDate =  Calendar.getInstance();
		Agent AHLAgent = new Agent();
		AHLAgent.setUsername("TEST");
		AHLAgent.setAirline("US");
		
		Ahl ahl=new Ahl();
		
		ahl.setAgent(AHLAgent);
		ahl.setAirlineCode("US");
		ahl.setStationCode("XAX");
		ahl.setAhlId("XAXUS10491");
		
		Item[] ilist=new Item[1];
		Item i=new Item();
		i.setColor("BK");
		i.setType("22");
		Content[] clist=new Content[3];
		Content c=new Content();
		c.setCategory("Art");
		c.setDescription("This is a test for world tracer");
		clist[0]=c;
		c=new Content();
		c.setCategory("Art");
		c.setDescription("And then splitted and accepted by worldtracer");
		clist[1]=c;
		c=new Content();
		c.setCategory("Nature");
		c.setDescription("This isn't art");
		clist[2]=c;
		i.setContent(clist);
		ilist[0]=i;
		ahl.setItem(ilist);
		
		Itinerary [] itlist=new Itinerary[1];
		Itinerary it=new Itinerary();
		it.setAirline("WN");
		it.setArrivalCity("ATL");
		it.setDepartureCity("LAS");
		it.setFlightDate(departureDate);
		it.setFlightNumber("123456");
		itlist[0]=it;
		ahl.setPaxItinerary(itlist);
		ahl.setBagItinerary(itlist);

		// Initialize other data (do not change)
		
		Session sess = HibernateWrapper.getSession().openSession();
		User user = (User) sess.load(User.class, 1);
		
		
		
		
		WorldTracerActionDTO dto = new WorldTracerActionDTO(type, user, ahl, true, null);
		AbstractServiceManager a = ISharesServiceManager.getInstance();
		//WorldTracerResponse response = a.process(dto);
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerServiceImpl wrsi= new WorldTracerServiceImpl(dto);
		try {
			wrsi.createAhl(dto, ahl,response);
		} catch (WorldTracerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto, false, WorldTracerServiceImpl.UNIT_TEST_SUCCESS);
		
		// Perform Action
//		impl.insertBdo(dto, payload, response);
		
		// Case-Specific Test of Data (Also use debugger to review contents of the "response" object.
		sess.close();
		System.out.println(response);
		Assert.assertEquals(true, response.isSuccess());
	}	
}
