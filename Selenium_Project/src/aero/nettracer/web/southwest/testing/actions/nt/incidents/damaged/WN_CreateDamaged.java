package aero.nettracer.web.southwest.testing.actions.nt.incidents.damaged;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateDamaged extends WN_SeleniumTest {
	
	private static String RX_TIMESTAMP = "";

	private String EXPEDITE_TAG_NUM_COLLECT = "639";
	private String RX_TIMESTAMP_COLLECT = "640";
	private String RX_TIMESTAMP_DELETE = "641";
	private String SPECIAL_CONDITIONS_COLLECT = "642";
	private String ADDITIONAL_ITEM_INFORMATION_COLLECT = "644";
	
	@Test
	public void testCreateDamagedIncident() {
		String[] permissions = new String[] {
											  EXPEDITE_TAG_NUM_COLLECT, 
											  RX_TIMESTAMP_COLLECT, 
											  RX_TIMESTAMP_DELETE
										    };
		
		boolean[] values = new boolean[] { 
											true, 
											true,
											true
										 };
		
		verifyTrue(setPermissions(permissions, values));
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
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
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
	
	@Test
	public void testCourtesyReasonDisabled() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.COURTESY_REASON_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(selenium.isElementPresent("id=courtesyReasonId"));
		verifyFalse(selenium.isElementPresent("id=courtesyDescription"));
		selenium.select("courtesyreport", "label=yes");
		selenium.click("saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Damaged Baggage Report has been modified."));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the damaged incident in testCourtesyReasonDisabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testCourtesyReasonAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Courtesy Reason :"));
		verifyFalse(selenium.isTextPresent("Courtesy Reason Description :"));
		goToTaskManager();
	}
	
	@Test
	public void testCourtesyReasonEnabled() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isElementPresent("id=courtesyReasonId"));
		assertEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check Conditional Acceptance", selenium.getText("id=courtesyReasonId"));
		verifyTrue(selenium.isElementPresent("id=courtesyDescription"));
		selenium.select("courtesyreport", "label=yes");
		selenium.select("courtesyReasonId", "label=Please Select");
		selenium.click("saveButton");
		assertEquals("Courtesy Reason is required.", selenium.getAlert());
		selenium.select("id=courtesyReasonId", "label=Other");
		selenium.click("name=saveButton");
		assertEquals("Courtesy Reason Description is required.", selenium.getAlert());
		selenium.type("id=courtesyDescription", "test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("link=" + Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.select("id=courtesyReasonId", "label=Outside 4-Hour");
				verifyFalse(selenium.isEditable("id=courtesyDescription"));
				selenium.click("name=saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Damaged Baggage Report has been modified."));
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to save the damaged incident in testCourtesyReasonEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the damaged incident in testCourtesyReasonEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the damaged incident in testCourtesyReasonEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testCourtesyReasonAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Courtesy Reason : Outside 4-Hour"));
		verifyTrue(selenium.isTextPresent("Courtesy Reason Description :"));
		goToTaskManager();
	}
	
	@Test
	public void testSpecialConditionsEnabled() {
		verifyTrue(setPermissions(new String[] { SPECIAL_CONDITIONS_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isTextPresent("Special Conditions"));
		verifyTrue(selenium.isElementPresent("name=theitem[0].specialCondition"));
		verifyEquals("Please Select Overweight Oversized Both", selenium.getText("name=theitem[0].specialCondition"));
		selenium.select("name=theitem[0].specialCondition", "label=Overweight");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (!checkNoErrorPage()) {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the damaged incident after setting the special conditions field. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testSpecialConditionsAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Special Conditions : Overweight "));
		goToTaskManager();
	}
	
	@Test
	public void testSpecialConditionsDisabled() {
		verifyTrue(setPermissions(new String[] { SPECIAL_CONDITIONS_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(selenium.isTextPresent("Special Conditions"));
		verifyFalse(selenium.isElementPresent("name=theitem[0].specialCondition"));
		goToTaskManager();
	}

	@Test
	public void testSpecialConditionsAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Special Conditions : Overweight "));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationDisabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_ITEM_INFORMATION_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(selenium.isTextPresent("Entered Date"));
		verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispPurchaseDate"));
		verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispInvItemCost"));
		verifyFalse(selenium.isElementPresent("name=inventorylist[0].invItemCurrency"));
		verifyFalse(selenium.isElementPresent("name=inventorylist[0].itemStatusId"));
		verifyTrue(selenium.isElementPresent("name=deleteinventory_0"));
		
		checkAdditionalItemInformationOtherIncidents();
		
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Entered Date: " + WN_SeleniumTest.TODAY + "\n     Purchase Date: \n     Cost: \n     Currency: US Dollar\n     Status:"));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationEnabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_ITEM_INFORMATION_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(selenium.isTextPresent("Entered Date"));
		verifyTrue(selenium.isElementPresent("name=inventorylist[0].dispPurchaseDate"));
		verifyTrue(selenium.isElementPresent("name=inventorylist[0].dispInvItemCost"));
		verifyTrue(selenium.isElementPresent("name=inventorylist[0].invItemCurrency"));
		verifyTrue(selenium.isElementPresent("name=inventorylist[0].itemStatusId"));
		verifyTrue(selenium.isElementPresent("name=deleteinventory_0"));
		
		selenium.type("name=inventorylist[0].dispPurchaseDate", WN_SeleniumTest.TODAY);
		selenium.type("name=inventorylist[0].dispInvItemCost", "1");
		selenium.select("name=inventorylist[0].itemStatusId", "label=Returned");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("link=" + Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals(WN_SeleniumTest.TODAY, selenium.getValue("name=inventorylist[0].dispPurchaseDate"));
				verifyEquals("1.00", selenium.getValue("name=inventorylist[0].dispInvItemCost"));
				verifyEquals("USD", selenium.getValue("name=inventorylist[0].invItemCurrency"));
				verifyEquals("800", selenium.getValue("name=inventorylist[0].itemStatusId"));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to reload the incident in testAdditionalItemInformationEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the incident in testAdditionalItemInformationEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		checkAdditionalItemInformationOtherIncidents();
		
		goToTaskManager();
		
	}
	
	@Test
	public void testAdditionalItemInformationAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Entered Date: " + WN_SeleniumTest.TODAY));
		verifyTrue(selenium.isTextPresent("Purchase Date: " + WN_SeleniumTest.TODAY));
		verifyTrue(selenium.isTextPresent("Cost: 1.00"));
		verifyTrue(selenium.isTextPresent("Currency: US Dollar"));
		verifyTrue(selenium.isTextPresent("Status: Returned"));
		goToTaskManager();		
	}
	
	@Test
	public void testCreateBDO(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		selenium.click("xpath=(//a[contains(@href, 'bdo.do?mbr_id=')])[1]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("name=createnewbdo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load BDO List for incident");
		}

		if (checkNoErrorPage()) {
			selenium.select("name=delivercompany_ID", "label=Dynamex");
			selenium.select("name=servicelevel_ID", "label=Regular");
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.type("name=cost","150");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create BDO for incident");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The Baggage Delivery Order has been successfully saved"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save BDO for incident");
		}
	}
	
	private void checkAdditionalItemInformationOtherIncidents() {
		selenium.click("id=menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyFalse(selenium.isTextPresent("Entered Date"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispPurchaseDate"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispInvItemCost"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].invItemCurrency"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].itemStatusId"));
				verifyTrue(selenium.isElementPresent("name=deleteinventory_0"));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the lost/delay incident page after clicking skip prepopulate. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the lost/delay incident prepopulation page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}

		selenium.click("id=menucol_3.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyFalse(selenium.isTextPresent("Entered Date"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispPurchaseDate"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].dispInvItemCost"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].invItemCurrency"));
				verifyFalse(selenium.isElementPresent("name=inventorylist[0].itemStatusId"));
				verifyTrue(selenium.isElementPresent("name=deleteinventory_0"));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the missing incident page after clicking skip prepopulate. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the missing incident prepopulation page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
}
