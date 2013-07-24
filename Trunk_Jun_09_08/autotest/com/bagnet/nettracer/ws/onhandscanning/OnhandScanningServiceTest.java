package com.bagnet.nettracer.ws.wn.onhandscanning;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.junit.Test;

import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.ForwardOnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger;
import com.bagnet.nettracer.ws.wn.onhandscanning.CreateUpdateOnhandDocument.CreateUpdateOnhand;
import com.bagnet.nettracer.ws.wn.onhandscanning.ReturnOnhandDocument.ReturnOnhand;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument.SaveBagDropTime;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

public class OnhandScanningServiceTest {
	
	public static OnhandScanningServiceImplementation service;
	
	public static String username = "ntadmin";
	public static String password = "/LucasArts43!";
	public static String companycode = "WS";
	
	public OnhandScanningServiceTest(){
		service = new OnhandScanningServiceImplementation();
	}
	
	@Test
	public void echoTest(){
		EchoDocument doc = EchoDocument.Factory.newInstance();
		doc.addNewEcho().setS("qwerty");
		EchoResponseDocument response = service.echo(doc);
		assertTrue("Echo service: qwerty".equals(response.getEchoResponse().getReturn()));
	}
	
	@Test
	public void isValidUserTest(){
		//null Authentication object
		IsValidUserDocument doc = IsValidUserDocument.Factory.newInstance();
		IsValidUserResponseDocument response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn().getValidUser());
		
		//successful login
		Authentication auth = doc.addNewIsValidUser().addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(response.getIsValidUserResponse().getReturn().getValidUser());
		
		//provide incorrect password
		auth.setSystemName(username);
		auth.setSystemPassword("fdasfs");
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn().getValidUser());
		
		//disable webservice user
		ActionMessages errors = new ActionMessages();
		Agent agent = SecurityUtils.authUser(username, password, companycode, 0, errors);
		agent.setWs_enabled(false);
		HibernateUtils.save(agent);
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn().getValidUser());
		assertTrue("user is not authorized for web services".equals(response.getIsValidUserResponse().getReturn().getErrorArray(0)));
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0, errors);
		agent.setWs_enabled(true);
		HibernateUtils.save(agent);
		
		//locked out
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0, errors);
		agent.setAccount_locked(true);
		HibernateUtils.save(agent);
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(response.getIsValidUserResponse().getReturn().getValidUser());
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0, errors);
		agent.setAccount_locked(false);
		HibernateUtils.save(agent);
	}
	
	@Test
	public void saveBagDropTimeTest(){
		//null Authentication object
		SaveBagDropTimeDocument doc = SaveBagDropTimeDocument.Factory.newInstance();
		SaveBagDropTimeResponseDocument response = service.saveBagDropTime(doc);
		assertTrue(!response.getSaveBagDropTimeResponse().getReturn().getSuccess());
		assertTrue(!response.getSaveBagDropTimeResponse().getReturn().getValidUser());
		
		//test happy path
		SaveBagDropTime savebagdrop = doc.addNewSaveBagDropTime();
		Authentication auth = savebagdrop.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		BagDrop bagdrop = savebagdrop.addNewBagDrop();
		bagdrop.setAirlineCode("WN");
		bagdrop.setArrivalStationCode("ATL");
		bagdrop.setFlightNumber("WN123");
		bagdrop.setBagDropDatetime(new GregorianCalendar());
		bagdrop.setPreviouslyEnteredFlag(false);
		bagdrop.setScheduleArrivalDatetime(new GregorianCalendar());
		
		response = service.saveBagDropTime(doc);
		ServiceResponse ret = response.getSaveBagDropTimeResponse().getReturn();
		assertTrue(ret.getSuccess());
		assertTrue(ret.getValidUser());
		BagDrop retBag = ret.getBagDrop();
		assertTrue("WN".equals(retBag.getAirlineCode()));
		assertTrue("ATL".equals(retBag.getArrivalStationCode()));
		assertTrue("WN123".equals(retBag.getFlightNumber()));
		
	}
	
	private CreateUpdateOnhandDocument getBlankOhdDocuement(){
		CreateUpdateOnhandDocument doc = CreateUpdateOnhandDocument.Factory.newInstance();
		CreateUpdateOnhand createohd = doc.addNewCreateUpdateOnhand();
		Authentication auth = createohd.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		return doc;
	}
	
	@Test
	public void createUpdateOnhandTest(){
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();
		
		String bagtag = "WN000009";
		Station foundstation = StationBMO.getStationByCode("ACY", companycode);
		String lookupOHD = service.lookupBagtag(bagtag, foundstation.getStation_ID());
		if(lookupOHD != null){
			OHD toClose = OhdBMO.getOHDByID(lookupOHD, null);
			toClose.getStatus().setStatus_ID(4);
			HibernateUtils.save(toClose);
		}
		
		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setColor("BK");
		ohd.setType("21");
		ohd.setFirstname("Bill");
		ohd.setMiddlename("Billy");
		ohd.setLastname("Nettracer");
		ohd.setRecordLocator("nttest");
		ohd.setFoundAtStation("ACY");
		ohd.setHoldingStation("ACY");
		
		
		WSItinerary itin = ohd.addNewItineraries();
		itin.setLegfrom("ATL");
		itin.setLegto("LAX");
		itin.setAirline("WN");
		itin.setFlightnum("123");
		itin.setDepartdate("2013-07-16T08:47:35.000-05:00");
		itin.setArrivedate("2013-07-16T08:47:35.000-05:00");
//		itin.setSchdeparttime("2013-07-16T08:47:35.000-05:00");
//		itin.setScharrivetime("2013-07-16T08:47:35.000-05:00");
		
		WSPassenger pax = ohd.addNewPassengers();
		pax.setAddress1("2675 Paces Ferry Rd");
		pax.setAddress2("Suite 240");
		pax.setCity("Atlanta");
		pax.setStateID("GA");//TODO state validation
		pax.setZip("30339");
		pax.setFirstname("John");
		pax.setLastname("Doe");
		pax.setHomephone("555-555-5555");
		
		CreateUpdateOnhandResponseDocument response = service.createUpdateOnhand(doc);
		
		ServiceResponse ret = response.getCreateUpdateOnhandResponse().getReturn();
		if(ret.getErrorArray() != null){
			for(String error:ret.getErrorArray()){
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_CREATE.equals(ret.getCreateUpdateIndicator()));
		WSOHD retohd = ret.getOnhand();
		assertTrue(bagtag.equals(retohd.getBagtagnum()));
		assertTrue("BK".equals(retohd.getColor()));
		assertTrue("21".equals(retohd.getType()));
		assertTrue("Bill".equals(retohd.getFirstname()));
		assertTrue("Billy".equals(retohd.getMiddlename()));
		assertTrue("Nettracer".equals(retohd.getLastname()));
		assertTrue("nttest".equals(retohd.getRecordLocator()));
		assertTrue("X".equals(retohd.getXdescelement1()));
		assertTrue("X".equals(retohd.getXdescelement2()));
		assertTrue("X".equals(retohd.getXdescelement3()));
		
		WSItinerary retitin = retohd.getItinerariesArray(0);
		assertTrue("ATL".equals(retitin.getLegfrom()));
		assertTrue("LAX".equals(retitin.getLegto()));
		assertTrue("WN".equals(retitin.getAirline()));
		assertTrue("123".equals(retitin.getFlightnum()));
//		System.out.println(retitin.getSchdeparttime());
//		System.out.println(retitin.getScharrivetime());
		assertTrue("2013-07-16".equals(retitin.getDepartdate()));
		assertTrue("2013-07-16".equals(retitin.getArrivedate()));
		
		WSPassenger retpax = retohd.getPassengersArray(0);
		assertTrue("2675 Paces Ferry Rd".equals(retpax.getAddress1()));
		assertTrue("Suite 240".equals(retpax.getAddress2()));
		assertTrue("Atlanta".equals(retpax.getCity()));
		assertTrue("GA".equals(retpax.getStateID()));
		assertTrue("30339".equals(retpax.getZip()));
		assertTrue("John".equals(retpax.getFirstname()));
		assertTrue("Doe".equals(retpax.getLastname()));
		assertTrue("555-555-5555".equals(retpax.getHomephone()));
		
		OHD updateOhd = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);
		
		//test update
		doc = getBlankOhdDocuement();
		ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setFoundAtStation("ACY");
		ohd.setHoldingStation("ACY");
		response = service.createUpdateOnhand(doc);
		ret = response.getCreateUpdateOnhandResponse().getReturn();
		if(ret.getErrorArray() != null){
			for(String error:ret.getErrorArray()){
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_UPDATE.equals(ret.getCreateUpdateIndicator()));
		
		//verify that fields that are not to be alter are in fact not altered.
		assertTrue("X".equals(retohd.getXdescelement1()));
		assertTrue("X".equals(retohd.getXdescelement2()));
		assertTrue("X".equals(retohd.getXdescelement3()));
		
		/*
		 * if running locally, make sure you have the ApplicationResource.properties copied into
		 * src.com.bagent.nettracer.tracing.resources, otherwise it will not be able to label and this assert will fail
		 */
		assertTrue("Open".equals(retohd.getStatus()));

		OHD toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		Agent agent = AdminUtils.getAgentBasedOnUsername(username, companycode);
		assertTrue(forwardOHD(agent,toRecieve));
		
		toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		
		doc = getBlankOhdDocuement();
		ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setFoundAtStation("FLL");
		ohd.setHoldingStation("FLL");
		response = service.createUpdateOnhand(doc);
		ret = response.getCreateUpdateOnhandResponse().getReturn();
		if(ret.getErrorArray() != null){
			for(String error:ret.getErrorArray()){
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_UPDATE.equals(ret.getCreateUpdateIndicator()));


		//closing ohd
		OHD toClose = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		toClose.getStatus().setStatus_ID(4);
		HibernateUtils.save(toClose);
		
	}
	
	private boolean forwardOHD(Agent user, OHD ohd){
		
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		BagService bs = new BagService();
		ForwardOnHandForm theform = new ForwardOnHandForm();
		LabelValueBean bean = new LabelValueBean();
		bean.setLabel(ohd.getOHD_ID());
		bean.setValue("WN123456");
		ArrayList<LabelValueBean> l = new ArrayList<LabelValueBean>();
		l.add(bean);
		theform.setOhdList(l);
		
		Station station = StationBMO.getStationByCode("FLL", companycode);
		theform.setDestStation("" + station.getStation_ID());
		theform.setMessage("forward message");
		
		OHD_Log_Itinerary log = new OHD_Log_Itinerary();
		log.setItinerarytype(0);
		log.setLegfrom("ACY");
		log.setLegto("FLL");
		
		ArrayList<OHD_Log_Itinerary> list = new ArrayList<OHD_Log_Itinerary>();
		list.add(log);
		theform.setItinerarylist(list);
		
		return bs.forwardOnHand(theform, user, messages);
	}
	
	@Test
	public void returnOhd(){
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();
		
		String bagtag = "WN000010";
		Station foundstation = StationBMO.getStationByCode("ACY", companycode);
		String lookupOHD = service.lookupBagtag(bagtag, foundstation.getStation_ID());
		if(lookupOHD != null){
			OHD toClose = OhdBMO.getOHDByID(lookupOHD, null);
			toClose.getStatus().setStatus_ID(4);
			HibernateUtils.save(toClose);
		}
		
		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setColor("BK");
		ohd.setType("21");
		ohd.setFirstname("Bill");
		ohd.setMiddlename("Billy");
		ohd.setLastname("Nettracer");
		ohd.setRecordLocator("nttest");
		ohd.setFoundAtStation("ACY");
		ohd.setHoldingStation("ACY");
		
		
		WSItinerary itin = ohd.addNewItineraries();
		itin.setLegfrom("ATL");
		itin.setLegto("LAX");
		itin.setAirline("WN");
		itin.setFlightnum("123");
		itin.setDepartdate("2013-07-16T08:47:35.000-05:00");
		itin.setArrivedate("2013-07-16T08:47:35.000-05:00");
//		itin.setSchdeparttime("2013-07-16T08:47:35.000-05:00");
//		itin.setScharrivetime("2013-07-16T08:47:35.000-05:00");
		
		WSPassenger pax = ohd.addNewPassengers();
		pax.setAddress1("2675 Paces Ferry Rd");
		pax.setAddress2("Suite 240");
		pax.setCity("Atlanta");
		pax.setStateID("GA");//TODO state validation
		pax.setZip("30339");
		pax.setFirstname("John");
		pax.setLastname("Doe");
		pax.setHomephone("555-555-5555");
		
		CreateUpdateOnhandResponseDocument response = service.createUpdateOnhand(doc);
		
		ServiceResponse ret = response.getCreateUpdateOnhandResponse().getReturn();
		if(ret.getErrorArray() != null){
			for(String error:ret.getErrorArray()){
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_CREATE.equals(ret.getCreateUpdateIndicator()));
		WSOHD retohd = ret.getOnhand();
		assertTrue(bagtag.equals(retohd.getBagtagnum()));
		assertTrue("BK".equals(retohd.getColor()));
		assertTrue("21".equals(retohd.getType()));
		assertTrue("Bill".equals(retohd.getFirstname()));
		assertTrue("Billy".equals(retohd.getMiddlename()));
		assertTrue("Nettracer".equals(retohd.getLastname()));
		assertTrue("nttest".equals(retohd.getRecordLocator()));
		assertTrue("X".equals(retohd.getXdescelement1()));
		assertTrue("X".equals(retohd.getXdescelement2()));
		assertTrue("X".equals(retohd.getXdescelement3()));
		
		WSItinerary retitin = retohd.getItinerariesArray(0);
		assertTrue("ATL".equals(retitin.getLegfrom()));
		assertTrue("LAX".equals(retitin.getLegto()));
		assertTrue("WN".equals(retitin.getAirline()));
		assertTrue("123".equals(retitin.getFlightnum()));
//		System.out.println(retitin.getSchdeparttime());
//		System.out.println(retitin.getScharrivetime());
		assertTrue("2013-07-16".equals(retitin.getDepartdate()));
		assertTrue("2013-07-16".equals(retitin.getArrivedate()));
		
		WSPassenger retpax = retohd.getPassengersArray(0);
		assertTrue("2675 Paces Ferry Rd".equals(retpax.getAddress1()));
		assertTrue("Suite 240".equals(retpax.getAddress2()));
		assertTrue("Atlanta".equals(retpax.getCity()));
		assertTrue("GA".equals(retpax.getStateID()));
		assertTrue("30339".equals(retpax.getZip()));
		assertTrue("John".equals(retpax.getFirstname()));
		assertTrue("Doe".equals(retpax.getLastname()));
		assertTrue("555-555-5555".equals(retpax.getHomephone()));
		
		OHD updateOhd = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);
		
		ReturnOnhandDocument requestdoc = ReturnOnhandDocument.Factory.newInstance();
		ReturnOnhand retohddoc = requestdoc.addNewReturnOnhand();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);
		retohddoc.setFoundStation("ACY");
		retohddoc.setTagNumber(bagtag);
		
		ReturnOnhandResponseDocument responsedoc = service.returnOnhand(requestdoc);
		ServiceResponse res = responsedoc.getReturnOnhandResponse().getReturn();
		assertTrue(res.getSuccess());
		assertTrue("Closed".equals(res.getOnhand().getStatus()));
		assertTrue("Owner Picked Up".equals(res.getOnhand().getDisposalStatus()));
		
	}
}
