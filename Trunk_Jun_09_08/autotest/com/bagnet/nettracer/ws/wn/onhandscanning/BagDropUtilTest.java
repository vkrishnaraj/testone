package com.bagnet.nettracer.ws.wn.onhandscanning;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.BagDropUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument.SaveBagDropTime;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

public class BagDropUtilTest {
	
	public static String username = "ntadmin";
	public static String password = "/LucasArts43!";
	public static String companycode = "WN";
	public static String stationcode = "ATL";
	public static String flightNumber = null;
	
	/**
	 * Test updating bagdrop time through the web service
	 */
	@Test
	public void bagDropTest(){
		GeneralServiceBean gbean = new GeneralServiceBean();
		Agent agent = gbean.getAgent("ntadmin", "WN");
		
		Calendar now = GregorianCalendar.getInstance();
		
		//test failure to update bagdrop that doesn't exist
		SaveBagDropTimeDocument saveBagDropNew = createDoc();
		saveBagDropNew.getSaveBagDropTime().getBagDrop().setBagDropDatetime(now);
		SaveBagDropTimeResponseDocument response = BagDropUtil.saveBagDropTime(saveBagDropNew);
		ServiceResponse ret = response.getSaveBagDropTimeResponse().getReturn();
		
		assertFalse(ret.getSuccess());
		assertTrue(ret.getErrorArray().length == 1);
		assertTrue(ret.getErrorArray(0).equals("Unable to update, a BagDrop entry does not exist for the provided flight and arrival station"));
		
		//creating bagdrop to test updated
		com.bagnet.nettracer.tracing.db.BagDrop bagdrop = new com.bagnet.nettracer.tracing.db.BagDrop();
		bagdrop.setAirline(companycode);
		bagdrop.setFlight(getFlightNumber());
		bagdrop.setArrivalStationCode(stationcode);
		bagdrop.setBagDropTime(DateUtils.convertToGMTDate(now.getTime()));
		bagdrop.setCreateAgent(agent);
		bagdrop.setEntryMethod(TracingConstants.BAGDROP_ENTRY_METHOD_WEB);
		bagdrop.setOriginStationCode("LAX");
		bagdrop.setSchArrivalDate(DateUtils.convertToGMTDate(now.getTime()));
		
		//save bagdrop and assert that it does exist now
		long id = BagDropUtils.saveOrUpdateBagDrop(agent, bagdrop);
		assertTrue(id > 0);
		assertTrue(BagDropUtils.bagdropExists(companycode, getFlightNumber(), stationcode, DateUtils.convertToGMTDate(now.getTime())) == id);
		
		//test updating bagdrop
		now.add(Calendar.HOUR, 1);
		SaveBagDropTimeDocument saveBagDropUpdate = createDoc();
		saveBagDropUpdate.getSaveBagDropTime().getBagDrop().setBagDropDatetime(now);
		saveBagDropUpdate.getSaveBagDropTime().getBagDrop().setFlightNumber(
				saveBagDropNew.getSaveBagDropTime().getBagDrop().getFlightNumber());
		SaveBagDropTimeResponseDocument responseUpdate = BagDropUtil.saveBagDropTime(saveBagDropUpdate);
		ServiceResponse retUpdate = responseUpdate.getSaveBagDropTimeResponse().getReturn();
		
		assertTrue(retUpdate.getSuccess());
		assertTrue(retUpdate.getCreateUpdateIndicator().equals(OnhandScanningServiceImplementation.STATUS_UPDATE));
		assertTrue(retUpdate.getBagDrop().getAirlineCode().equals(companycode));
		assertTrue(retUpdate.getBagDrop().getArrivalStationCode().equals(stationcode));
		assertTrue(retUpdate.getBagDrop().getFlightNumber().equals(saveBagDropNew.getSaveBagDropTime().getBagDrop().getFlightNumber()));
		assertTrue(retUpdate.getBagDrop().getPreviouslyEnteredFlag() == true);
		assertTrue(retUpdate.getBagDrop().getBagDropDatetime().getTime().toString().equals(now.getTime().toString()));
	}
	
	private SaveBagDropTimeDocument createDoc(){
		SaveBagDropTimeDocument doc = SaveBagDropTimeDocument.Factory.newInstance();
		SaveBagDropTime SaveBagdropTime = doc.addNewSaveBagDropTime();
		BagDrop bagdrop = SaveBagdropTime.addNewBagDrop();
		
		Authentication auth = SaveBagdropTime.addNewAuthentication();
		auth.setSystemName(username);
		auth.setSystemPassword(password);
		auth.setAirlineCode(companycode);
		bagdrop.setAirlineCode(companycode);
		bagdrop.setFlightNumber(getFlightNumber());
		bagdrop.setArrivalStationCode(stationcode);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		bagdrop.setBagDropDatetime(cal);
		return doc;
	}
	
	private String getFlightNumber(){
		//since for bagdrop there can only be one unique flight per day,
		//for testing setting the flight number to be the minute of the day
		//this does mean that the test can only correctly run once a minute
		
		if(flightNumber == null){
			Date now = new Date();
			flightNumber = "" + (now.getTime()/60000)%1440;
		}
		return flightNumber;
	}
}
