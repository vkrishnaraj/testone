package com.bagnet.nettracer.ws.wn.onhandscanning;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.ws.wn.onhandscanning.SaveBagDropTimeDocument.SaveBagDropTime;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.BagDrop;
import com.bagnet.nettracer.ws.wn.onhandscanning.pojo.xsd.ServiceResponse;
import com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication;

public class BagDropUtilTest {
	
	public static String username = "ntadmin";
	public static String password = "/LucasArts43!";
	public static String companycode = "WN";
	
	/**
	 * Tests the two possible entry types for this service: creating and updating
	 */
	@Test
	public void bagDropTest(){
		Calendar now = GregorianCalendar.getInstance();
		
		//test create new bagdrop
		SaveBagDropTimeDocument saveBagDropNew = createDoc();
		saveBagDropNew.getSaveBagDropTime().getBagDrop().setBagDropDatetime(now);
		saveBagDropNew.getSaveBagDropTime().getBagDrop().setScheduleArrivalDatetime(now);
		SaveBagDropTimeResponseDocument response = BagDropUtil.saveBagDropTime(saveBagDropNew);
		ServiceResponse ret = response.getSaveBagDropTimeResponse().getReturn();
		
		assertTrue(ret.getSuccess());
		assertTrue(ret.getCreateUpdateIndicator().equals(OnhandScanningServiceImplementation.STATUS_CREATE));
		assertTrue(ret.getBagDrop().getAirlineCode().equals("WN"));
		assertTrue(ret.getBagDrop().getArrivalStationCode().equals("ATL"));
		assertTrue(ret.getBagDrop().getFlightNumber().equals(saveBagDropNew.getSaveBagDropTime().getBagDrop().getFlightNumber()));
		assertTrue(ret.getBagDrop().getPreviouslyEnteredFlag() == false);
		assertTrue(ret.getBagDrop().getBagDropDatetime().getTime().toString().equals(now.getTime().toString()));
		
		
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
		assertTrue(retUpdate.getBagDrop().getAirlineCode().equals("WN"));
		assertTrue(retUpdate.getBagDrop().getArrivalStationCode().equals("ATL"));
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
		
		//since for bagdrop there can only be one unique flight per day,
		//for testing setting the flight number to be the minute of the day
		//this does mean that the test can only correctly run once a minute
		Date now = new Date();
		String flightNumber = "" + (now.getTime()/60000)%1440;
		
		bagdrop.setAirlineCode("WN");
		bagdrop.setFlightNumber(flightNumber);
		bagdrop.setArrivalStationCode("ATL");
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(now);
		bagdrop.setBagDropDatetime(cal);
		bagdrop.setScheduleArrivalDatetime(cal);
		return doc;
	}
}
