package aero.nettracer.web.southwest.testing.actions.nt.incidents.damaged;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;
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
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				
				verifyFalse(isElementPresent(By.id("rxButton")));
				selenium.click("id=button");
				selenium.click("name=saveButton");
				verifyEquals("Permanent Address is required.", selenium.getAlert());
				selenium.click("name=addPassenger");
				waitForPageToLoadImproved();

				if (checkNoErrorPage()) {
					selenium.click("name=saveButton");
					verifyEquals("Permanent Address is required.", selenium.getAlert());
					selenium.click("id=addresses[0].permanent");
					selenium.click("xpath=(//input[@id='button'])[3]");
					selenium.click("name=saveButton");
					verifyEquals("Passenger Itinerary is required.", selenium.getAlert());
					selenium.click("name=addpassit");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger'.");
				}
				if (checkNoErrorPage()) {
					selenium.click("name=saveButton");
					assertEquals("Baggage Itinerary is required.", selenium.getAlert());
					selenium.click("name=addbagit");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger Itinerary'.");
				}
				
				if (checkNoErrorPage()) {
					//required fields first.
					selenium.click("name=saveButton");
					verifyEquals("Last Name is required.", selenium.getAlert());
					selenium.type("name=passenger[0].lastname", "Test1");
					selenium.click("name=saveButton");
					verifyEquals("First Name is required.", selenium.getAlert());
					selenium.type("name=passenger[0].firstname", "Test1");
					selenium.click("name=saveButton");
					verifyEquals("Street Address is required.", selenium.getAlert());
					selenium.type("name=addresses[0].address1", "123 circle round drive");
					selenium.click("name=saveButton");
					verifyEquals("City is required.", selenium.getAlert());
					selenium.type("name=addresses[0].city", "atlanta");
					selenium.click("name=saveButton");
					verifyEquals("State is required if country is set to 'United States'", selenium.getAlert());
					selenium.select("name=addresses[0].state_ID", "label=Georgia");						
					selenium.click("name=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].legfrom", "ATL");						
					selenium.click("name=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].legto", "AEX");						
					selenium.click("name=saveButton");
					verifyEquals("Airline/Flight Number is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].flightnum", "1234");						
					selenium.click("name=saveButton");
					verifyEquals("Depart Date is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].disdepartdate", "10/15/2013");
					selenium.click("name=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].legfrom", "ALS");
					selenium.click("name=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].legto", "ATL");
					selenium.click("name=saveButton");
					verifyEquals("Airline/Flight Number is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].flightnum", "3456");
					selenium.click("name=saveButton");
					verifyEquals("Depart Date is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].disdepartdate", "10/15/2013");						
					selenium.click("name=saveButton");
					verifyEquals("Color is required.", selenium.getAlert());
					selenium.select("name=theitem[0].color", "label=BN - Brown");						
					selenium.click("name=saveButton");
					verifyEquals("Type is required.", selenium.getAlert());
					selenium.select("id=bagtype0", "label=25");						
					selenium.click("name=saveButton");
					verifyEquals("Record Locator is required.", selenium.getAlert());
					selenium.type("name=recordlocator", "12345");						
					selenium.click("name=saveButton");
					verifyEquals("Salutation is required.", selenium.getAlert());
					selenium.select("name=passenger[0].salutation", "label=Mr.");				
					selenium.click("name=saveButton");
					verifyEquals("Zip is required.", selenium.getAlert());
					selenium.type("name=addresses[0].zip", "33213");						
					selenium.click("name=saveButton");
					verifyEquals("Phone is required.", selenium.getAlert());
					selenium.type("name=addresses[0].mobile", "4040213465");						
					selenium.click("name=saveButton");
					verifyEquals("Claim Check Number is required.", selenium.getAlert());
					selenium.type("name=theitem[0].claimchecknum", "1234567890");
					selenium.click("name=saveButton");
					verifyEquals("Manufacturer is required.", selenium.getAlert());
					selenium.select("name=theitem[0].manufacturer_ID", "label=Andiamo");
					selenium.click("name=saveButton");
					verifyEquals("Remark is required.", selenium.getAlert());
					selenium.type("id=remark[0]", "remark noted");
					
					//optional fields last					
					selenium.select("name=passenger[0].dlstate", "label=Georgia");
					selenium.type("name=addresses[0].email", "email@email.com");
					selenium.click("id=itcalendar0");
					selenium.click("link=Today");
					selenium.type("name=theitem[0].expediteTagNum", WN_SeleniumTest.EXPEDITE_TAG_NUM);
					selenium.select("name=theitem[0].bagtype", "label=22");
					selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
					selenium.type("name=inventorylist[0].description", "TEST");
					selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
					selenium.type("name=inventorylist[1].description", "TEST");
					selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
					selenium.type("name=inventorylist[2].description", "TEST");
					selenium.click("name=saveButton");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						verifyTrue(isTextPresent("Damaged Bag Incident has been submitted."));
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
					System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Baggage Itinerary'.");
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
		verifyTrue(isTextPresent("Expedite Tag Number"));
		verifyTrue(isElementPresent(By.name("theitem[0].expediteTagNum")));				
		verifyEquals(WN_SeleniumTest.EXPEDITE_TAG_NUM, selenium.getValue("name=theitem[0].expediteTagNum"));
		goToTaskManager();
	}
	
	@Test
	public void testExpediteTagNumEnabledAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Expedite Tag Number : " + WN_SeleniumTest.EXPEDITE_TAG_NUM));
		goToTaskManager();
	}
	
	@Test
	public void testExpediteTagNumDisabled() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(isTextPresent("Expedite Tag Number"));
		verifyFalse(isElementPresent(By.name("theitem[0].expediteTagNum")));				
		goToTaskManager();
	}

	@Test
	public void testExpediteTagNumDisabledAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Expedite Tag Number : " + WN_SeleniumTest.EXPEDITE_TAG_NUM));
		goToTaskManager();
	}
	
	@Test
	public void testInvalidExpediteTagNum() {
		verifyTrue(setPermissions(new String[] { EXPEDITE_TAG_NUM_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isElementPresent(By.name("theitem[0].expediteTagNum")));
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
		verifyTrue(isElementPresent(By.id("rxButton")));
		selenium.click("id=rxButton");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { 
				if (isElementPresent(By.name("dispRxTimestamp"))) break; 
			} catch (Exception e) { }
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}

		verifyEquals("Date/Time Received at LZ:", selenium.getText("xpath=(//div[@id='rxTimestampDiv'])[2]"));
		verifyTrue(isElementPresent(By.name("dispRxTimestamp")));
		verifyTrue(isElementPresent(By.id("undoRxButton")));
		
		WN_CreateDamaged.RX_TIMESTAMP = selenium.getValue("name=dispRxTimestamp");
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampPresent() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isElementPresent(By.name("dispRxTimestamp")));
		verifyEquals(WN_CreateDamaged.RX_TIMESTAMP, selenium.getValue("name=dispRxTimestamp"));
		verifyTrue(isElementPresent(By.id("undoRxButton")));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Date/Time Received at LZ"));
		verifyTrue(isTextPresent(WN_CreateDamaged.RX_TIMESTAMP));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampUndoDisabled() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_DELETE }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isElementPresent(By.name("dispRxTimestamp")));
		verifyFalse(isElementPresent(By.id("undoRxButton")));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampUndo() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_DELETE }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isElementPresent(By.id("undoRxButton")));
		selenium.click("id=undoRxButton");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { 
				if (isElementPresent(By.id("rxButton"))) break; 
			} catch (Exception e) { }
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}
		
		verifyFalse(isElementPresent(By.name("dispRxTimestamp")));
		verifyFalse(isElementPresent(By.id("undoRxButton")));
		verifyTrue(isElementPresent(By.id("rxButton")));
		goToTaskManager();
	}
	
	@Test
	public void testRxTimestampAuditTrailDisabled() {
		verifyTrue(setPermissions(new String[] { RX_TIMESTAMP_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Date/Time Received at LZ"));
		goToTaskManager();
	}
	
	@Test
	public void testCourtesyReasonDisabled() {
		verifyTrue(setPermissions(new String[] { PermissionsUtil.INCIDENT_COURTESY_REASON_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(isElementPresent(By.id("courtesyReasonId")));
		verifyFalse(isElementPresent(By.id("courtesyDescription")));
		selenium.select("courtesyreport", "label=yes");
		selenium.click("saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Damaged Baggage Incident has been modified."));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the damaged incident in testCourtesyReasonDisabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testCourtesyReasonAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Courtesy Reason :"));
		verifyFalse(isTextPresent("Courtesy Reason Description :"));
		goToTaskManager();
	}
	
	@Test
	public void testCourtesyReasonEnabled() {
		verifyTrue(setPermissions(new String[] { PermissionsUtil.INCIDENT_COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isElementPresent(By.id("courtesyReasonId")));
		assertEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check Conditional Acceptance", selenium.getText("id=courtesyReasonId"));
		verifyTrue(isElementPresent(By.id("courtesyDescription")));
		selenium.select("courtesyreport", "label=yes");
		selenium.select("courtesyReasonId", "label=Please Select");
		selenium.click("saveButton");
		assertEquals("Reason is required.", selenium.getAlert());
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
				verifyFalse(isEditable(By.id("courtesyDescription")));
				selenium.click("name=saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isTextPresent("Damaged Baggage Incident has been modified."));
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
		verifyTrue(isTextPresent("Reason : Outside 4-Hour"));
		verifyTrue(isTextPresent("Courtesy Reason Description :"));
		goToTaskManager();
	}
	
	@Test
	public void testSpecialConditionsEnabled() {
		verifyTrue(setPermissions(new String[] { SPECIAL_CONDITIONS_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isTextPresent("Special Conditions"));
		verifyTrue(isElementPresent(By.name("theitem[0].specialCondition")));
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
		verifyTrue(isTextPresent("Special Conditions : Overweight "));
		goToTaskManager();
	}
	
	@Test
	public void testSpecialConditionsDisabled() {
		verifyTrue(setPermissions(new String[] { SPECIAL_CONDITIONS_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(isTextPresent("Special Conditions"));
		verifyFalse(isElementPresent(By.name("theitem[0].specialCondition")));
		goToTaskManager();
	}

	@Test
	public void testSpecialConditionsAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Special Conditions : Overweight "));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationDisabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_ITEM_INFORMATION_COLLECT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyFalse(isTextPresent("Entered Date"));
		verifyFalse(isElementPresent(By.name("inventorylist[0].dispPurchaseDate")));
		verifyFalse(isElementPresent(By.name("inventorylist[0].dispInvItemCost")));
		verifyFalse(isElementPresent(By.name("inventorylist[0].invItemCurrency")));
		verifyFalse(isElementPresent(By.name("inventorylist[0].itemStatusId")));
		verifyTrue(isElementPresent(By.name("deleteinventory_0")));
		
		checkAdditionalItemInformationOtherIncidents();
		
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Entered Date: " + WN_SeleniumTest.TODAY + "\n     Purchase Date: \n     Cost: \n     Currency: US Dollar\n     Status:"));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalItemInformationEnabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_ITEM_INFORMATION_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		verifyTrue(isTextPresent("Entered Date"));
		verifyTrue(isElementPresent(By.name("inventorylist[0].dispPurchaseDate")));
		verifyTrue(isElementPresent(By.name("inventorylist[0].dispInvItemCost")));
		verifyTrue(isElementPresent(By.name("inventorylist[0].invItemCurrency")));
		verifyTrue(isElementPresent(By.name("inventorylist[0].itemStatusId")));
		verifyTrue(isElementPresent(By.name("deleteinventory_0")));
		
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
		verifyTrue(isTextPresent("Entered Date: " + WN_SeleniumTest.TODAY));
		verifyTrue(isTextPresent("Purchase Date: " + WN_SeleniumTest.TODAY));
		verifyTrue(isTextPresent("Cost: 1.00"));
		verifyTrue(isTextPresent("Currency: US Dollar"));
		verifyTrue(isTextPresent("Status: Returned"));
		goToTaskManager();		
	}
	
	@Test
	public void testCreateBDODamaged(){
		verifyTrue(setPermissions(new String[] { "661", "662"}, new boolean[] { true, true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_DAMAGED));
		waitForPageToLoadImproved(3000,false);
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
			waitForPageToLoadImproved(250);
			selenium.select("name=servicelevel_ID", "label=Regular");
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.type("name=cost","150");
			selenium.click("id=button");
			assertEquals("Pickup Date Time is required.", selenium.getAlert());
			selenium.click("id=calendar2");
			selenium.click("link=Today");
			selenium.click("id=button");
			assertEquals("Pickup Date Time is required.", selenium.getAlert());
			selenium.type("name=disppickuptime","12:00");
			selenium.click("id=button");
			assertEquals("Fault Code is required.", selenium.getAlert());
			selenium.select("name=theitem[0].lossCode", "value=11");
			selenium.click("id=button");
			assertEquals("Fault Station is required.", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			selenium.click("id=button");
			assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
			selenium.type("name=remark", "BDO Loss Code Change Remark");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create BDO for incident");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The Baggage Delivery Order has been successfully saved"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save BDO for incident");
		}
	}
	
	private void checkAdditionalItemInformationOtherIncidents() {
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyFalse(isTextPresent("Entered Date"));
				verifyFalse(isElementPresent(By.name("inventorylist[0].dispPurchaseDate")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].dispInvItemCost")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].invItemCurrency")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].itemStatusId")));
				verifyTrue(isElementPresent(By.name("deleteinventory_0")));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the lost/delay incident page after clicking skip prepopulate. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the lost/delay incident prepopulation page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}

		clickMenu("menucol_3.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyFalse(isElementPresent(By.name("inventorylist[0].dispPurchaseDate")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].dispInvItemCost")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].invItemCurrency")));
				verifyFalse(isElementPresent(By.name("inventorylist[0].itemStatusId")));
				verifyTrue(isElementPresent(By.name("deleteinventory_0")));
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
