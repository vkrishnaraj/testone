package com.bagnet.nettracer.ws.wn.onhandscanning;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.ForwardOnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.ws.core.WSCoreOHDUtil;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger;
import com.bagnet.nettracer.ws.wn.onhandscanning.AddBagForLZDocument.AddBagForLZ;
import com.bagnet.nettracer.ws.wn.onhandscanning.CreateUpdateOnhandDocument.CreateUpdateOnhand;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZDocument.LookupOnhandLZ;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandLZResponseDocument.LookupOnhandLZResponse;
import com.bagnet.nettracer.ws.wn.onhandscanning.LookupOnhandReturnDocument.LookupOnhandReturn;
import com.bagnet.nettracer.ws.wn.onhandscanning.ReturnOnhandDocument.ReturnOnhand;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument.SaveBagDropTime;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

public class OnhandScanningServiceTest {

	public static OnhandScanningServiceImplementation service;

	public static String username = "ntadmin";
	public static String password = "/LucasArts43!";
	public static String companycode = "WN";

	public static String stationcode = "LZ";
	public static String altStationcode = "BDL";

	public OnhandScanningServiceTest() {
		service = new OnhandScanningServiceImplementation();
	}

	@Test
	public void echoTest() {
		EchoDocument doc = EchoDocument.Factory.newInstance();
		doc.addNewEcho().setS("qwerty");
		EchoResponseDocument response = service.echo(doc);
		assertTrue("Echo service: qwerty".equals(response.getEchoResponse()
				.getReturn()));
	}

	@Test
	public void isValidUserTest() {
		// null Authentication object
		IsValidUserDocument doc = IsValidUserDocument.Factory.newInstance();
		IsValidUserResponseDocument response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn()
				.getValidUser());

		// successful login
		Authentication auth = doc.addNewIsValidUser().addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(response.getIsValidUserResponse().getReturn().getValidUser());

		// provide incorrect password
		auth.setSystemName(username);
		auth.setSystemPassword("fdasfs");
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn()
				.getValidUser());

		// disable webservice user
		ActionMessages errors = new ActionMessages();
		Agent agent = SecurityUtils.authUser(username, password, companycode,
				0, errors);
		agent.setWs_enabled(false);
		HibernateUtils.save(agent);
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(!response.getIsValidUserResponse().getReturn()
				.getValidUser());
		assertTrue("user is not authorized for web services".equals(response
				.getIsValidUserResponse().getReturn().getErrorArray(0)));
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0,
				errors);
		agent.setWs_enabled(true);
		HibernateUtils.save(agent);

		// locked out
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0,
				errors);
		agent.setAccount_locked(true);
		HibernateUtils.save(agent);
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		response = service.isValidUser(doc);
		assertTrue(response.getIsValidUserResponse().getReturn().getValidUser());
		errors = new ActionMessages();
		agent = SecurityUtils.authUser(username, password, companycode, 0,
				errors);
		agent.setAccount_locked(false);
		HibernateUtils.save(agent);
	}

	@Test
	public void saveBagDropTimeTest() {
		// null Authentication object
		SaveBagDropTimeDocument doc = SaveBagDropTimeDocument.Factory
				.newInstance();
		SaveBagDropTimeResponseDocument response = service.saveBagDropTime(doc);
		assertTrue(!response.getSaveBagDropTimeResponse().getReturn()
				.getSuccess());
		assertTrue(!response.getSaveBagDropTimeResponse().getReturn()
				.getValidUser());

		// test happy path since BagDrop is not implemented until a later ticket
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

	@Test
	public void createOnhandTest() {
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();

		String bagtag = "WN000009";
		closeOHD(bagtag, stationcode);

		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		populateWSOHDObject(ohd, bagtag);

		CreateUpdateOnhandResponseDocument response = service
				.createUpdateOnhand(doc);

		ServiceResponse ret = response.getCreateUpdateOnhandResponse()
				.getReturn();
		if (ret.getErrorArray() != null) {
			for (String error : ret.getErrorArray()) {
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_CREATE.equals(ret
				.getCreateUpdateIndicator()));

		WSOHD retohd = ret.getOnhand();
		assertWSOHD(bagtag, retohd);

		// verify we flag the OHD create method as created by web service
		OHD loadOHD = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		assertTrue(loadOHD.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);
	}

	@Test
	public void updateOnhandTest() {
		// First create a new OHD to test with
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();

		String bagtag = "WN000009";
		closeOHD(bagtag, stationcode);

		String ohdId = createOHD(bagtag, stationcode);

		// Now test updating the OHD we just created
		doc = getBlankOhdDocuement();
		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setHoldingStation(stationcode);
		CreateUpdateOnhandResponseDocument response = service.createUpdateOnhand(doc);
		ServiceResponse ret = response.getCreateUpdateOnhandResponse().getReturn();
		if (ret.getErrorArray() != null) {
			for (String error : ret.getErrorArray()) {
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_UPDATE.equals(ret
				.getCreateUpdateIndicator()));

		// verify that fields that are not to be alter are in fact not altered.
		WSOHD retohd = ret.getOnhand();
		assertTrue("X".equals(retohd.getXdescelement1()));
		assertTrue("X".equals(retohd.getXdescelement2()));
		assertTrue("X".equals(retohd.getXdescelement3()));

		/*
		 * if running locally, make sure you have the
		 * ApplicationResource.properties copied into
		 * src.com.bagent.nettracer.tracing.resources, otherwise it will not be
		 * able to label and this assert will fail
		 */
		assertTrue("Open".equals(retohd.getStatus()));

		// testing the service to handling incoming OHD
		// first, forward the OHD to the alternate station
		OHD toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		Agent agent = AdminUtils.getAgentBasedOnUsername(username, companycode);
		assertTrue(forwardOHD(agent, toRecieve));

		toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);

		// now create a new web service request for the alternate station
		doc = getBlankOhdDocuement();
		ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		ohd.setBagtagnum(bagtag);
		ohd.setFoundAtStation(altStationcode);
		ohd.setHoldingStation(altStationcode);
		response = service.createUpdateOnhand(doc);
		ret = response.getCreateUpdateOnhandResponse().getReturn();
		if (ret.getErrorArray() != null) {
			for (String error : ret.getErrorArray()) {
				System.out.println(error);
			}
		}
		// assert the bag was received and updated
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_UPDATE.equals(ret
				.getCreateUpdateIndicator()));

		// closing ohd
		closeOHD(bagtag, altStationcode);
	}

	@Test
	public void returnOhdTest() {
		String bagtag = "WN000010";
		closeOHD(bagtag, stationcode);

		String ohdId = createOHD(bagtag, stationcode);

		OHD updateOhd = OhdBMO.getOHDByID(ohdId, null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);

		ReturnOnhandDocument requestdoc = ReturnOnhandDocument.Factory
				.newInstance();
		ReturnOnhand retohddoc = requestdoc.addNewReturnOnhand();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);
		retohddoc.setHoldingStation(stationcode);
		retohddoc.setTagNumber(bagtag);

		ReturnOnhandResponseDocument responsedoc = service
				.returnOnhand(requestdoc);
		ServiceResponse res = responsedoc.getReturnOnhandResponse().getReturn();
		assertTrue(res.getSuccess());
		assertTrue("Closed".equals(res.getOnhand().getStatus()));
		assertTrue("Owner Picked Up"
				.equals(res.getOnhand().getDisposalStatus()));

	}

	@Test
	public void lookupOhdReturnMissingFieldsTest() {
		LookupOnhandReturnDocument requestdoc = LookupOnhandReturnDocument.Factory
				.newInstance();
		LookupOnhandReturn retohddoc = requestdoc.addNewLookupOnhandReturn();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);

		LookupOnhandReturnResponseDocument responsedoc = service
				.lookupOnhandReturn(requestdoc);
		ServiceResponse res = responsedoc.getLookupOnhandReturnResponse()
				.getReturn();
		assertTrue(res.getSuccess() == false);
		assertTrue("Manual Add Required".equals(res.getReturnStatus()));
	}

	@Test
	public void lookupOhdReturnCurrentStationNoAssociateIncidentTest() {
		String bagtag = "WN000011";
		closeOHD(bagtag, stationcode);

		String ohdId = createOHD(bagtag, stationcode);

		OHD updateOhd = OhdBMO.getOHDByID(ohdId, null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);

		LookupOnhandReturnDocument requestdoc = LookupOnhandReturnDocument.Factory
				.newInstance();
		LookupOnhandReturn retohddoc = requestdoc.addNewLookupOnhandReturn();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);
		retohddoc.setHoldingStation(stationcode);
		retohddoc.setTagNumber(bagtag);

		LookupOnhandReturnResponseDocument responsedoc = service
				.lookupOnhandReturn(requestdoc);
		ServiceResponse res = responsedoc.getLookupOnhandReturnResponse()
				.getReturn();
		assertTrue(res.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_ALLOWED
				.equals(res.getReturnStatus()));

		// close ohd
		closeOHD(bagtag, stationcode);
	}

	@Test
	public void lookupOhdReturnCurrentStationAssociateIncidentTest() {
		String bagtag = "WN000012";
		closeOHD(bagtag, stationcode);

		String ohdId = createOHD(bagtag, stationcode);

		OHD updateOhd = OhdBMO.getOHDByID(ohdId, null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);
		updateOhd.setMatched_incident("ACYWN00000001");
		HibernateUtils.save(updateOhd);

		LookupOnhandReturnDocument requestdoc = LookupOnhandReturnDocument.Factory
				.newInstance();
		LookupOnhandReturn retohddoc = requestdoc.addNewLookupOnhandReturn();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);
		retohddoc.setHoldingStation(stationcode);
		retohddoc.setTagNumber(bagtag);

		LookupOnhandReturnResponseDocument responsedoc = service
				.lookupOnhandReturn(requestdoc);
		ServiceResponse res = responsedoc.getLookupOnhandReturnResponse()
				.getReturn();
		assertTrue(res.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_ASSOCIATED_REPORT
				.equals(res.getReturnStatus()));
		assertTrue("ACYWN00000001".equals(res.getAssoicatedIncidentId()));

		// close ohd
		closeOHD(bagtag, stationcode);
	}

	@Test
	public void lookupOhdReturnNoOhdTest() {

		String bagtag = "WN000013";
		closeOHD(bagtag, stationcode);

		LookupOnhandReturnDocument requestdoc = LookupOnhandReturnDocument.Factory
				.newInstance();
		LookupOnhandReturn retohddoc = requestdoc.addNewLookupOnhandReturn();
		Authentication ohdauth = retohddoc.addNewAuthentication();
		ohdauth.setSystemName(username);
		ohdauth.setSystemPassword(password);
		ohdauth.setAirlineCode(companycode);
		retohddoc.setHoldingStation(stationcode);
		retohddoc.setTagNumber(bagtag);

		LookupOnhandReturnResponseDocument responsedoc = service
				.lookupOnhandReturn(requestdoc);
		ServiceResponse res = responsedoc.getLookupOnhandReturnResponse()
				.getReturn();
		assertTrue(res.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_NO_OHD
				.equals(res.getReturnStatus()));
	}

	@Test
	public void lookupOhdLZIncomingTest() {
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();

		String bagtag = "WN000009";
		closeOHD(bagtag, stationcode);

		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		populateWSOHDObject(ohd, bagtag);

		CreateUpdateOnhandResponseDocument response = service
				.createUpdateOnhand(doc);

		ServiceResponse ret = response.getCreateUpdateOnhandResponse()
				.getReturn();
		if (ret.getErrorArray() != null) {
			for (String error : ret.getErrorArray()) {
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_CREATE.equals(ret
				.getCreateUpdateIndicator()));
		WSOHD retohd = ret.getOnhand();

		OHD updateOhd = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		assertTrue(updateOhd.getCreationMethod() == TracingConstants.FILE_CREATION_METHOD_WEBSERVICE);

		OHD toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		Agent agent = AdminUtils.getAgentBasedOnUsername(username, companycode);
		assertTrue(forwardOHD(agent, toRecieve));

		toRecieve = OhdBMO.getOHDByID(retohd.getOHDID(), null);

		LookupOnhandLZDocument lzdoc = LookupOnhandLZDocument.Factory
				.newInstance();
		LookupOnhandLZ lzreq = lzdoc.addNewLookupOnhandLZ();
		lzreq.setTagNumber(bagtag);
		lzreq.setHoldingStation(altStationcode);
		Authentication lzauth = lzreq.addNewAuthentication();
		lzauth.setSystemName(username);
		lzauth.setSystemPassword(password);
		lzauth.setAirlineCode(companycode);

		LookupOnhandLZResponseDocument lzresponse = service
				.lookupOnhandLZ(lzdoc);
		LookupOnhandLZResponse lzret = lzresponse.getLookupOnhandLZResponse();
		if (lzret.getReturn().getErrorArray() != null) {
			for (String error : lzret.getReturn().getErrorArray()) {
				System.out.println(error);
			}
		}
		assertTrue(lzret.getReturn().getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_LZ_UPDATE_REQUIRED
				.equals(lzret.getReturn().getReturnStatus()));

		// closing ohd
		OHD toClose = OhdBMO.getOHDByID(retohd.getOHDID(), null);
		toClose.getStatus().setStatus_ID(4);
		HibernateUtils.save(toClose);

	}

	@Test
	public void lookupOhdLZDeleteOHDTest(){
		String bagtag = "WN000009";
		closeOHD(bagtag, stationcode);
		String ohdId = createOHD(bagtag, altStationcode);
		OhdBMO obmo = new OhdBMO();
		OHD ntohd = obmo.getOHDByID(ohdId, null);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		ntohd.setStatus(status);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(DateUtils.convertToGMTDate(new Date()));
		c.add(Calendar.DATE, -3);
		ntohd.setClose_date(c.getTime());
		obmo.updateOhdNoAudit(ntohd);
		
		LookupOnhandLZDocument lzdoc = LookupOnhandLZDocument.Factory
				.newInstance();
		LookupOnhandLZ lzreq = lzdoc.addNewLookupOnhandLZ();
		lzreq.setTagNumber(bagtag);
		lzreq.setHoldingStation(stationcode);
		Authentication lzauth = lzreq.addNewAuthentication();
		lzauth.setSystemName(username);
		lzauth.setSystemPassword(password);
		lzauth.setAirlineCode(companycode);
		
		LookupOnhandLZResponseDocument lzresponse = service
				.lookupOnhandLZ(lzdoc);
		LookupOnhandLZResponse lzret = lzresponse.getLookupOnhandLZResponse();
		
		assertTrue(lzret.getReturn().getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_UPDATE_SUCCESS
				.equals(lzret.getReturn().getReturnStatus()));
		assertTrue(lzret.getReturn().getOnhand().getHoldingStation().equals(stationcode));
		assertTrue(lzret.getReturn().getOnhand().getStatus().equals("TBI"));
		
	}
	
	@Test
	public void lookupOhdLzTbiTest(){
		String bagtag = "WN264115";//this tag has to be unique and not associated with a closed OHD
		closeOHD(bagtag, stationcode);
		String ohdId = createOHD(bagtag, stationcode);
		OhdBMO obmo = new OhdBMO();
		OHD ntohd = obmo.getOHDByID(ohdId, null);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.OHD_STATUS_TBI);
		ntohd.setStatus(status);
		obmo.updateOhdNoAudit(ntohd);
		
		LookupOnhandLZDocument lzdoc = LookupOnhandLZDocument.Factory
				.newInstance();
		LookupOnhandLZ lzreq = lzdoc.addNewLookupOnhandLZ();
		lzreq.setTagNumber(bagtag);
		lzreq.setHoldingStation(stationcode);
		Authentication lzauth = lzreq.addNewAuthentication();
		lzauth.setSystemName(username);
		lzauth.setSystemPassword(password);
		lzauth.setAirlineCode(companycode);

		LookupOnhandLZResponseDocument lzresponse = service
				.lookupOnhandLZ(lzdoc);
		LookupOnhandLZResponse lzret = lzresponse.getLookupOnhandLZResponse();
		
		assertTrue(lzret.getReturn().getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_UPDATE_SUCCESS
				.equals(lzret.getReturn().getReturnStatus()));
		assertTrue(lzret.getReturn().getOnhand().getHoldingStation().equals(stationcode));
		assertTrue(lzret.getReturn().getOnhand().getStatus().equals("TBI"));
	}
	
	@Test
	public void lookupOhdLz45Test(){
		String bagtag = "WN264110";//this tag has to be unique and not associated with a closed OHD
		closeOHD(bagtag, stationcode);
		String ohdId = createOHD(bagtag, altStationcode);
		OhdBMO obmo = new OhdBMO();
		OHD ntohd = obmo.getOHDByID(ohdId, null);
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(DateUtils.convertToGMTDate(new Date()));
		c.add(Calendar.DATE, -44);
		ntohd.setFounddate(c.getTime());
		obmo.updateOhdNoAudit(ntohd);
		
		LookupOnhandLZDocument lzdoc = LookupOnhandLZDocument.Factory
				.newInstance();
		LookupOnhandLZ lzreq = lzdoc.addNewLookupOnhandLZ();
		lzreq.setTagNumber(bagtag);
		lzreq.setHoldingStation(stationcode);
		Authentication lzauth = lzreq.addNewAuthentication();
		lzauth.setSystemName(username);
		lzauth.setSystemPassword(password);
		lzauth.setAirlineCode(companycode);

		LookupOnhandLZResponseDocument lzresponse = service
				.lookupOnhandLZ(lzdoc);
		LookupOnhandLZResponse lzret = lzresponse.getLookupOnhandLZResponse();
		
		assertTrue(lzret.getReturn().getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_RETURN_UPDATE_SUCCESS
				.equals(lzret.getReturn().getReturnStatus()));
		assertTrue(lzret.getReturn().getOnhand().getHoldingStation().equals(stationcode));
		assertTrue(lzret.getReturn().getOnhand().getStatus().equals("Open"));
	}
	
	@Test
	public void addBagForLZAddTest() {
		String bagtag = "WN000102";
		closeOHD(bagtag, stationcode);

		AddBagForLZDocument addBagForLZDoc = AddBagForLZDocument.Factory
				.newInstance();
		AddBagForLZ addBagForLZ = addBagForLZDoc.addNewAddBagForLZ();
		Authentication auth = addBagForLZ.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);

		WSOHD ohd = addBagForLZ.addNewOnhand();
		populateWSOHDObject(ohd, bagtag);

		addBagForLZ.setTBI(true);
		AddBagForLZResponseDocument ret = service.addBagForLZ(addBagForLZDoc);
		ServiceResponse response = ret.getAddBagForLZResponse().getReturn();

		assertTrue(response.getSuccess());
		assertWSOHD(bagtag, response.getOnhand());
		assertTrue(response.getOnhand().getStatus().equals("TBI"));
		assertTrue(response.getReturnStatus().equals("Successful Create/Update"));
	}

	@Test
	public void addBagForLZUpdateTest() {
		String bagtag = "WN000103";
		closeOHD(bagtag, stationcode);

		createOHD(bagtag, stationcode);

		AddBagForLZDocument addBagForLZDoc = AddBagForLZDocument.Factory
				.newInstance();
		AddBagForLZ addBagForLZ = addBagForLZDoc.addNewAddBagForLZ();
		Authentication auth = addBagForLZ.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);

		WSOHD ohd = addBagForLZ.addNewOnhand();
		populateWSOHDObject(ohd, bagtag);

		addBagForLZ.setTBI(false);

		AddBagForLZResponseDocument retLZ = service.addBagForLZ(addBagForLZDoc);
		ServiceResponse responseLZ = retLZ.getAddBagForLZResponse().getReturn();

		assertTrue(responseLZ.getSuccess());
		assertWSOHD(bagtag, responseLZ.getOnhand());
		assertTrue(responseLZ.getOnhand().getStatus().equals("Closed"));
		assertTrue(responseLZ.getReturnStatus().equals("Successful Create/Update"));
	}

	private void closeOHD(String bagtag, String foundStation) {
		Station foundstation = StationBMO.getStationByCode(foundStation,
				companycode);
		String lookupOHD = null;
		do {
			lookupOHD = OnhandScanningServiceUtil.lookupBagtag(bagtag,
					foundstation.getStation_ID());
			if (lookupOHD != null) {
				OHD toClose = OhdBMO.getOHDByID(lookupOHD, null);
				toClose.getStatus().setStatus_ID(4);
				HibernateUtils.save(toClose);
			}
		} while (lookupOHD != null);
	}

	private CreateUpdateOnhandDocument getBlankOhdDocuement() {
		CreateUpdateOnhandDocument doc = CreateUpdateOnhandDocument.Factory
				.newInstance();
		CreateUpdateOnhand createohd = doc.addNewCreateUpdateOnhand();
		Authentication auth = createohd.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		return doc;
	}

	private void populateWSOHDObject(WSOHD ohd, String bagtag) {
		ohd.setBagtagnum(bagtag);
		ohd.setColor("BK");
		ohd.setType("21");
		ohd.setFirstname("Bill");
		ohd.setMiddlename("Billy");
		ohd.setLastname("Nettracer");
		ohd.setRecordLocator("nttest");
		ohd.setHoldingStation(stationcode);

		WSItinerary itin = ohd.addNewItineraries();
		itin.setLegfrom("ATL");
		itin.setLegto("LAX");
		itin.setAirline("WN");
		itin.setFlightnum("123");
		itin.setDepartdate("2013-07-16T08:47:35.000-05:00");
		itin.setArrivedate("2013-07-16T08:47:35.000-05:00");

		WSPassenger pax = ohd.addNewPassengers();
		pax.setAddress1("2675 Paces Ferry Rd");
		pax.setAddress2("Suite 240");
		pax.setCity("Atlanta");
		pax.setStateID("GA");
		pax.setZip("30339");
		pax.setFirstname("John");
		pax.setLastname("Doe");
		pax.setHomephone("555-555-5555");
	}

	private void assertWSOHD(String bagtag, WSOHD retohd) {
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
	}

	private boolean forwardOHD(Agent user, OHD ohd) {

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

		Station station = StationBMO.getStationByCode(altStationcode,
				companycode);
		theform.setDestStation("" + station.getStation_ID());
		theform.setMessage("forward message");

		OHD_Log_Itinerary log = new OHD_Log_Itinerary();
		log.setItinerarytype(0);
		log.setLegfrom(stationcode);
		log.setLegto(altStationcode);

		ArrayList<OHD_Log_Itinerary> list = new ArrayList<OHD_Log_Itinerary>();
		list.add(log);
		theform.setItinerarylist(list);

		return bs.forwardOnHand(theform, user, messages);
	}
	
	private String createOHD(String bagtag, String stationcode){
		CreateUpdateOnhandDocument doc = getBlankOhdDocuement();

		closeOHD(bagtag, stationcode);

		WSOHD ohd = doc.getCreateUpdateOnhand().addNewOnhand();
		populateWSOHDObject(ohd, bagtag);

		CreateUpdateOnhandResponseDocument response = service
				.createUpdateOnhand(doc);

		ServiceResponse ret = response.getCreateUpdateOnhandResponse()
				.getReturn();
		if (ret.getErrorArray() != null) {
			for (String error : ret.getErrorArray()) {
				System.out.println(error);
			}
		}
		assertTrue(ret.getSuccess());
		assertTrue(OnhandScanningServiceImplementation.STATUS_CREATE.equals(ret
				.getCreateUpdateIndicator()));
		
		assertWSOHD(bagtag,ret.getOnhand());
		return ret.getOnhand().getOHDID();
	}
}