package aero.nettracer.web.southwest.testing.actions.nt.incidents.missing;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class WN_CreateMissing extends WN_SeleniumTest {
	
	public final String ADDITIONAL_MISSING_ITEM_INFORMATION = "649";

	@Test
	public void testCreateMissingIncident() {
		clickMenu("menucol_3.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				
				selenium.click("id=button");
				selenium.click("name=saveButton");
				verifyEquals("Permanent Address is required.", selenium.getAlert());
				selenium.click("name=addPassenger");
				waitForPageToLoadImproved();

				if (checkNoErrorPage()) {
					selenium.click("name=saveButton");
					verifyEquals("Permanent Address check is required.", selenium.getAlert());
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
					selenium.click("id=saveButton");
					verifyEquals("Baggage Itinerary is required.", selenium.getAlert());
					selenium.click("name=addbagit");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger Itinerary'.");
				}
				
				if (checkNoErrorPage()) {
					//required fields first.
					selenium.click("id=saveButton");
					verifyEquals("Last Name is required.", selenium.getAlert());
					selenium.type("name=passenger[0].lastname", "doe");
					selenium.click("id=saveButton");
					verifyEquals("First Name is required.", selenium.getAlert());
					selenium.type("name=passenger[0].firstname", "jane");
					selenium.click("id=saveButton");
					verifyEquals("Street Address is required.", selenium.getAlert());
					selenium.type("name=addresses[0].address1", "123 circle round drive");
					selenium.click("id=saveButton");
					verifyEquals("City is required.", selenium.getAlert());
					selenium.type("name=addresses[0].city", "atlanta");
					selenium.click("id=saveButton");
					verifyEquals("State is required if country is set to 'United States'", selenium.getAlert());
					selenium.select("name=addresses[0].state_ID", "label=Georgia");
					selenium.click("id=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].legfrom", "ATL");
					selenium.click("id=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].legto", "AEX");
					selenium.click("id=saveButton");
					verifyEquals("Airline/Flight Number is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].flightnum", "1234");
					selenium.click("id=saveButton");
					verifyEquals("Depart Date is required.", selenium.getAlert());
					selenium.type("name=theitinerary[0].disdepartdate", "10/15/2013");
					selenium.click("id=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].legfrom", "ALS");
					selenium.click("id=saveButton");
					verifyEquals("From/To is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].legto", "ATL");
					selenium.click("id=saveButton");
					verifyEquals("Airline/Flight Number is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].flightnum", "3456");
					selenium.click("id=saveButton");
					verifyEquals("Depart Date is required.", selenium.getAlert());
					selenium.type("name=theitinerary[1].disdepartdate", "10/15/2013");				
					selenium.click("id=saveButton");
					verifyEquals("Color is required.", selenium.getAlert());
					selenium.select("name=theitem[0].color", "label=GY - Grey");
					selenium.click("id=saveButton");
					verifyEquals("Type is required.", selenium.getAlert());
					selenium.select("id=bagtype0", "label=25");
					selenium.click("id=saveButton");
					verifyEquals("Record Locator is required.", selenium.getAlert());
					selenium.type("name=recordlocator", "12345");
					selenium.click("id=saveButton");
					verifyEquals("Salutation is required.", selenium.getAlert());
					selenium.select("name=passenger[0].salutation", "label=Mr.");	
					selenium.click("id=saveButton");
					verifyEquals("Zip is required.", selenium.getAlert());
					selenium.type("name=addresses[0].zip", "33213");
					selenium.click("id=saveButton");
					verifyEquals("Phone is required.", selenium.getAlert());
					selenium.type("name=addresses[0].mobile", "4040213465");
					selenium.click("id=saveButton");
					verifyEquals("Claim Check Number is required.", selenium.getAlert());
					selenium.type("name=theitem[0].claimchecknum", "1234567890");
					selenium.click("id=saveButton");
					verifyEquals("Manufacturer is required.", selenium.getAlert());
					selenium.select("name=theitem[0].manufacturer_ID", "label=Bag Boy");
					selenium.click("id=saveButton");
					verifyEquals("Status is required.", selenium.getAlert());
					selenium.select("name=article[0].statusId", "label=Missing");
					selenium.click("id=saveButton");
					verifyEquals("Article is required.", selenium.getAlert());
					selenium.type("name=article[0].article", "Article Test1");
					selenium.click("id=saveButton");
					verifyEquals("Description is required.", selenium.getAlert());
					selenium.type("name=article[0].description", "Article Description Test 1");
					selenium.click("id=saveButton");
					verifyEquals("Remark is required.", selenium.getAlert());
					selenium.type("id=remark[0]", "Remarks noted");	
	
					//optional fields last	
					selenium.select("name=passenger[0].dlstate", "label=Georgia");
					selenium.type("name=addresses[0].email", "email@email.com");
					selenium.click("id=itcalendar0");
					selenium.click("link=Today");
					selenium.select("name=theitem[0].bagtype", "label=22");
					selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
					selenium.type("name=inventorylist[0].description", "TEST");
					selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
					selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
					selenium.type("name=inventorylist[1].description", "TEST");
					selenium.type("name=inventorylist[2].description", "TEST");
					selenium.click("id=saveButton");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						verifyTrue(isTextPresent("Missing Articles Incident has been submitted."));
						checkCopyrightAndQuestionMarks();
						String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
						Settings.INCIDENT_ID_WN = incident_id;
						System.out.println("WN: Missing Incident Created: " + Settings.INCIDENT_ID_WN);
						selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
						waitForPageToLoadImproved();
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Create Missing Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger Itinerary'.");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Edit Missing Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Missing Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testCourtesyReasonDropdownValuesMissing() {
		verifyTrue(setPermissions(new String[] { PermissionsUtil.INCIDENT_COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_MISSING));
		verifyEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check", selenium.getText("id=courtesyReasonId"));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalMissingItemInformationDisabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_MISSING_ITEM_INFORMATION }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_MISSING));
		verifyFalse(isElementPresent(By.name("article[0].disEnteredDate")));
		verifyFalse(isElementPresent(By.name("article[0].statusId")));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalMissingItemInformationAuditTrailDisabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Entered Date : " + WN_SeleniumTest.TODAY));
		verifyFalse(isTextPresent("Status : Returned"));
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalMissingItemInformationEnabled() {
		verifyTrue(setPermissions(new String[] { ADDITIONAL_MISSING_ITEM_INFORMATION }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_MISSING));
		verifyTrue(isElementPresent(By.name("article[0].disEnteredDate")));
		verifyTrue(isElementPresent(By.name("article[0].statusId")));

		selenium.type("name=article[0].dispurchasedate", WN_SeleniumTest.TODAY);
		selenium.type("name=article[0].discost", "1");
		selenium.select("name=article[0].statusId", "label=Returned");
		selenium.type("name=article[0].article", "Test Article");
		selenium.type("name=article[0].description", "Test article description.");
		selenium.click("id=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Missing Articles Incident has been modified."));
			selenium.click("link=" + Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals(WN_SeleniumTest.TODAY, selenium.getValue("name=article[0].dispurchasedate"));
				verifyEquals("1.00", selenium.getValue("name=article[0].discost"));
				verifyEquals("USD", selenium.getValue("name=article[0].currency_ID"));
				verifyEquals("800", selenium.getValue("name=article[0].statusId"));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to reload the incident in testAdditionalMissingItemInformationEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save the incident in testAdditionalMissingItemInformationEnabled. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testAdditionalMissingItemInformationAuditTrailEnabled() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Article : Test Article"));
		verifyTrue(isTextPresent("Entered Date : " + WN_SeleniumTest.TODAY));
		verifyTrue(isTextPresent("Purchase Date : " + WN_SeleniumTest.TODAY));
		verifyTrue(isTextPresent("Cost : 1.00 USD"));
		verifyTrue(isTextPresent("Status : Returned"));
		verifyTrue(isTextPresent("Description : Test article description."));
		goToTaskManager();
	}
	
	@Test
	public void testCreateBDOMissing(){
		verifyTrue(setPermissions(new String[] { "661", "662"}, new boolean[] { true, true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_MISSING));
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
			selenium.check("name=theitem[0].noAddFees");
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
}
