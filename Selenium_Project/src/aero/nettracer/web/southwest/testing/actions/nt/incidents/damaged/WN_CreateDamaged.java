package aero.nettracer.web.southwest.testing.actions.nt.incidents.damaged;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateDamaged extends WN_SeleniumTest {
	
	private static String RX_TIMESTAMP = "";

	private String EXPEDITE_TAG_NUM_COLLECT = "639";
	private String RX_TIMESTAMP_COLLECT = "640";
	private String RX_TIMESTAMP_DELETE = "641";
	
	
	@Test
	public void testCreateDamagedIncident() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT, RX_TIMESTAMP_COLLECT, RX_TIMESTAMP_DELETE }, new boolean[] { true, true, true }));
		selenium.click("//a[@id='menucol_2.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				
				verifyFalse(selenium.isElementPresent("id=rxButton"));
				
				selenium.type("name=passenger[0].lastname", "Test1");
				selenium.type("name=passenger[0].firstname", "Test1");
				selenium.select("name=passenger[0].dlstate", "label=Georgia");
				selenium.type("name=addresses[0].address1", "123 Test1");
				selenium.type("name=addresses[0].city", "Test1");
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.type("name=addresses[0].zip", "12345");
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.type("name=addresses[0].email", "email@email.com");
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.type("name=theitinerary[0].legto", "LAX");
				selenium.type("name=theitinerary[0].flightnum", "1233");
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.type("name=theitem[0].expediteTagNum", WN_SeleniumTest.EXPEDITE_TAG_NUM);
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[0].description", "TEST");
				selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
				selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.type("name=inventorylist[2].description", "TEST");
				selenium.type("name=recordlocator", "TESTER");
				selenium.click("saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Damaged Bag Incident has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_WN = incident_id;
					System.out.println("WN: Damaged Incident Created: " + Settings.INCIDENT_ID_WN);
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Create Damaged Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Edit Damaged Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Damaged Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testExpediteTagNumEnabled() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isTextPresent("Expedite Tag Number"));
		verifyTrue(selenium.isElementPresent("name=theitem[0].expediteTagNum"));				
		verifyEquals(WN_SeleniumTest.EXPEDITE_TAG_NUM, selenium.getValue("name=theitem[0].expediteTagNum"));
		goToTaskManager();
	}
	
	@Test
	public void testExpediteTagNumEnabledAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Expedite Tag Number : " + WN_SeleniumTest.EXPEDITE_TAG_NUM));
		goToTaskManager();
	}
	
	@Test
	public void testExpediteTagNumDisabled() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(selenium.isTextPresent("Expedite Tag Number"));
		verifyFalse(selenium.isElementPresent("name=theitem[0].expediteTagNum"));				
		goToTaskManager();
	}

	@Test
	public void testExpediteTagNumDisabledAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Expedite Tag Number : " + WN_SeleniumTest.EXPEDITE_TAG_NUM));
		goToTaskManager();
	}
	
	@Test
	public void testInvalidExpediteTagNum() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isElementPresent("name=theitem[0].expediteTagNum"));
		selenium.type("name=theitem[0].expediteTagNum", "01234567891");
		selenium.click("name=saveButton");
		assertEquals("Expedite Number is an invalid expedite number.[10 or 12 digits or 8 character AN]", selenium.getAlert());
		selenium.type("name=theitem[0].expediteTagNum", "012345678912");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Create Damaged Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testRxTimestampSet() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyEquals("Additional Functions", selenium.getText("//div[@id='maincontent']/h1"));
		verifyTrue(selenium.isElementPresent("id=rxButton"));
		selenium.click("id=rxButton");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { 
				if (selenium.isElementPresent("name=dispRxTimestamp")) break; 
			} catch (Exception e) { }
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}

		verifyEquals("Date/Time Received at LZ:", selenium.getText("xpath=(//div[@id='rxTimestampDiv'])[2]"));
		verifyTrue(selenium.isElementPresent("name=dispRxTimestamp"));
		verifyTrue(selenium.isElementPresent("id=undoRxButton"));
		
		WN_CreateDamaged.RX_TIMESTAMP = selenium.getValue("name=dispRxTimestamp");
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampPresent() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isElementPresent("name=dispRxTimestamp"));
		verifyEquals(WN_CreateDamaged.RX_TIMESTAMP, selenium.getValue("name=dispRxTimestamp"));
		verifyTrue(selenium.isElementPresent("id=undoRxButton"));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Date/Time Received at LZ"));
		verifyTrue(selenium.isTextPresent(WN_CreateDamaged.RX_TIMESTAMP));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampUndoDisabled() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_DELETE }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isElementPresent("name=dispRxTimestamp"));
		verifyFalse(selenium.isElementPresent("id=undoRxButton"));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampUndo() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_DELETE }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isElementPresent("id=undoRxButton"));
		selenium.click("id=undoRxButton");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { 
				if (selenium.isElementPresent("id=rxButton")) break; 
			} catch (Exception e) { }
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}
		
		verifyFalse(selenium.isElementPresent("name=dispRxTimestamp"));
		verifyFalse(selenium.isElementPresent("id=undoRxButton"));
		verifyTrue(selenium.isElementPresent("id=rxButton"));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampAuditTrailDisabled() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Date/Time Received at LZ"));
		goToTaskManager();
	}
	
}
