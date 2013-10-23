package aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CloseLD extends WN_SeleniumTest {

	
	@Test
	public void testCancelBDO(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("xpath=(//a[contains(@href, 'bdo.do?mbr_id=')])[1]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("link="+Settings.BDO_ID_WN);
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load BDO List for incident");
		}

		if (checkNoErrorPage()) {
			selenium.click("link=Cancel BDO");
			assertTrue(selenium.getConfirmation().matches("^Are you sure you want to cancel this BDO[\\s\\S]$"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create BDO for incident");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			verifyEquals("Passenger Pick Up",selenium.getValue("name=passengerpickedup0"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save BDO for incident");
		}
	}
	
	@Test
	public void testPassengerPickUpLD(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.select("name=theitem[0].lossCode", "label=Please Select");
		selenium.select("name=theitem[0].faultStation_id", "label=Please Select");
		selenium.click("name=passengerpickedup0");
		assertEquals("Chargeback Code is required.", selenium.getAlert());
		selenium.select("name=theitem[0].lossCode", "value=11");
		selenium.click("name=passengerpickedup0");
		assertEquals("Chargeback Station is required.", selenium.getAlert());
		selenium.select("name=theitem[0].faultStation_id", "label=ATL");
		selenium.click("name=passengerpickedup0");
		assertEquals("Be sure to check the Claim check or Positive ID", selenium.getAlert());
		waitForPageToLoadImproved();
		

		if (checkNoErrorPage()) {
			verifyEquals("Passenger Pick Up",selenium.getValue("name=theitem[0].status.description"));
			selenium.click("name=passengerpickedup0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to mark item as Passenger Pick Up. - !!!!!!!!!!!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open",selenium.getValue("name=theitem[0].status.description"));
			selenium.type("name=theitem[0].tempOHD_ID", Settings.ONHAND_ID_WN);
			selenium.click("name=matchbag0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to unmark item as Passenger Pick Up. - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			selenium.click("name=passengerpickedup0");
			assertEquals("Be sure to check the Claim check or Positive ID", selenium.getAlert());
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to match OHD to Item. - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			verifyEquals("Passenger Pick Up",selenium.getValue("name=theitem[0].status.description"));
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to mark item and OHD as Passenger Pick Up. - !!!!!!!!!!!!!!!!!!");
		}
		

		if(checkNoErrorPage()){
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			selenium.click("link="+Settings.ONHAND_ID_WN);
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save incident and mark the item OHD as Passenger Pick Up. - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			verifyEquals("Closed",selenium.getSelectedLabel("name=status.status_ID"));
			verifyEquals("Passenger Pick Up",selenium.getSelectedLabel("name=disposal_status.status_ID"));
			selenium.click("link="+Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to access OHD from Incident. - !!!!!!!!!!!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			selenium.click("name=passengerpickedup0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to access incident from OHD. - !!!!!!!!!!!!!!!!!!");
		}
		if (checkNoErrorPage()) {
			verifyEquals("To Be Delivered",selenium.getValue("name=theitem[0].status.description"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to reopen Item. - !!!!!!!!!!!!!!!!!!");
		}
	}
	
	@Test
	public void testCloseLD() throws Exception {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("xpath=(//a[contains(@href, 'lostDelay.do?close=1')])[1]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.select("name=theitem[0].lossCode", "label=Please Select");
			selenium.select("name=theitem[0].faultStation_id", "label=Please Select");
			selenium.click("name=doclose");
			assertEquals("Chargeback Code is required.", selenium.getAlert());
			selenium.select("name=theitem[0].lossCode", "value=10");
			selenium.click("name=doclose");
			assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
			selenium.type("name=remark[6].remarktext", "Loss Code Change Remark4");
			selenium.click("name=doclose");
			assertEquals("Chargeback Station is required.", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			selenium.click("name=doclose");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been closed."));
				goToTaskManager();
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
}