package aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class WN_CreateLD_VerifyRequiredFields extends WN_SeleniumTest {

	@Test
	public void testVerifyText() throws Exception {
		// MJS: initial state is drivers license collection enabled
		// 		and view/edit drivers license disabled.
		verifyTrue(setPermissions(new String[] { "632", "633", "636", "637", "635", "661", "662", "663", "664", "665", "666","675"}, new boolean[] { true, false, true, false, true, false, false, false, false, false, true,false }));


		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}

		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=savetracingButton");
			verifyEquals("Permanent Address check is required.",selenium.getAlert());
			selenium.click("id=addresses[0].permanent");
			selenium.click("xpath=(//input[@id='button'])[3]");
			selenium.click("name=savetracingButton");
			assertEquals("Passenger Itinerary is required.",selenium.getAlert());
			selenium.click("name=addpassit");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("name=savetracingButton");
				assertEquals("Baggage Itinerary is required.",selenium.getAlert());
				selenium.click("name=addbagit");
				waitForPageToLoadImproved();
			} else {
				System.out
						.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger Itinerary'.");
			}

			if (checkNoErrorPage()) {
				selenium.click("name=savetracingButton");
				verifyEquals("Last Name is required.", selenium.getAlert());
				selenium.type("name=passenger[0].lastname", "Test1");
				selenium.click("name=savetracingButton");
				verifyEquals("First Name is required.", selenium.getAlert());
				selenium.type("name=passenger[0].firstname", "Test1");
				selenium.click("name=savetracingButton");
				verifyEquals("Street Address is required.", selenium.getAlert());
				selenium.type("name=addresses[0].address1",
						"123 circle round drive");
				selenium.click("name=savetracingButton");
				verifyEquals("City is required.", selenium.getAlert());
				selenium.type("name=addresses[0].city", "atlanta");
				selenium.click("name=savetracingButton");
				verifyEquals("State is required if country is set to 'United States'",selenium.getAlert());
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.click("name=savetracingButton");
				verifyEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.click("name=savetracingButton");
				verifyEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].legto", "AEX");
				selenium.click("name=savetracingButton");
				verifyEquals("Airline/Flight Number is required.",selenium.getAlert());
				selenium.type("name=theitinerary[0].flightnum", "1234");
				selenium.click("name=savetracingButton");
				verifyEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				verifyEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].legfrom", "ALS");
				selenium.click("name=savetracingButton");
				verifyEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].legto", "ATL");
				selenium.click("name=savetracingButton");
				verifyEquals("Airline/Flight Number is required.",selenium.getAlert());
				selenium.type("name=theitinerary[1].flightnum", "3456");
				selenium.click("name=savetracingButton");
				verifyEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=calendar31");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				verifyEquals("Color is required.", selenium.getAlert());
				selenium.select("name=theitem[0].color", "label=BN - Brown");
				selenium.click("name=savetracingButton");
				verifyEquals("Type is required.", selenium.getAlert());
				selenium.select("id=bagtype0", "label=25");
				selenium.click("name=savetracingButton");
				verifyEquals("Content Category is required.",selenium.getAlert());
				selenium.select("name=inventorylist[0].categorytype_ID","label=Alcohol");
				selenium.click("name=savetracingButton");
				verifyEquals("Content Description is required.",selenium.getAlert());
				selenium.type("name=inventorylist[0].description", "TEST");

				selenium.select("name=inventorylist[1].categorytype_ID","label=Alcohol");
				selenium.select("name=inventorylist[2].categorytype_ID","label=Alcohol");
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.type("name=inventorylist[2].description", "TEST");
				selenium.click("name=savetracingButton");
				verifyEquals("Record Locator is required.", selenium.getAlert());
				selenium.type("name=recordlocator", TODAY_SIX_DIGIT);
				selenium.click("name=savetracingButton");
				verifyEquals("Salutation is required.", selenium.getAlert());
				selenium.select("name=passenger[0].salutation", "label=Mr.");
				selenium.click("name=savetracingButton");
				verifyEquals("Zip is required.", selenium.getAlert());
				selenium.type("name=addresses[0].zip", "33213");
				selenium.click("name=savetracingButton");
				verifyEquals("Phone is required.", selenium.getAlert());
				selenium.type("name=addresses[0].mobile", "4040213465");
				selenium.click("name=savetracingButton");
				verifyEquals("Claim Check Number is required.",selenium.getAlert());
				selenium.type("name=claimcheck[0].claimchecknum", "1234567890");
				selenium.click("name=savetracingButton");
				verifyEquals("Manufacturer is required.", selenium.getAlert());
				selenium.select("name=theitem[0].manufacturer_ID","label=Andiamo");
				selenium.click("name=savetracingButton");
				verifyEquals("Remark is required.", selenium.getAlert());
				selenium.type("id=remark[0]", "remark noted");
			} else {
				System.out
						.println("!!!!!!!!!!!!!!!! failed to 'Add Bag Itinerary'.");
			}

			verifyTrue(isElementPresent(By.name("passenger[0].decriptedDriversLicense")));
			verifyTrue(isElementPresent(By.name("passenger[0].dlstate")));
			verifyTrue(isElementPresent(By.name("passenger[0].driversLicenseProvince")));
			verifyTrue(isElementPresent(By.name("passenger[0].driversLicenseCountry")));
			verifyTrue(isElementPresent(By.name("passenger[0].decryptedPassportNumber")));
			verifyTrue(isElementPresent(By.name("passenger[0].passportIssuer")));
			selenium.type("name=passenger[0].decriptedDriversLicense",WN_SeleniumTest.DRIVERS_LICENSE);
			selenium.select("name=passenger[0].dlstate", "label=Georgia");
			verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
			verifyEquals("US",selenium.getValue("name=passenger[0].driversLicenseCountry"));
			selenium.type("name=passenger[0].decryptedPassportNumber",WN_SeleniumTest.PASSPORT_NUMBER);
			selenium.select("name=passenger[0].passportIssuer","label=United States");
			selenium.type("name=theitem[0].posId", "123456");
			selenium.type("name=addresses[0].email", "email@email.com");
			selenium.click("savetracingButton");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been submitted."));

				checkCopyrightAndQuestionMarks();
				String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.INCIDENT_ID_WN = incident_id;
				System.out.println("WN: Lost/Delay Incident Created: "+ Settings.INCIDENT_ID_WN);

				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testVerifyDriversLicenseRedacted() {
		verifyTrue(navigateToIncidentTest(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isEditable(By.name("passenger[0].redactedDriversLicense")));
		verifyEquals("*********", selenium.getValue("name=passenger[0].redactedDriversLicense"));
		verifyFalse(isEditable(By.name("passenger[0].dlstate")));
		verifyEquals("GA", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseCountry")));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyPassportRedacted() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isEditable(By.name("passenger[0].redactedPassportNumber")));
		verifyEquals("***************", selenium.getValue("name=passenger[0].redactedPassportNumber"));
		verifyFalse(isEditable(By.name("passenger[0].passportIssuer")));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyDriversLicensePrepopulateClaim() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.xpath("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]"));
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyEquals("*********", getText(By.xpath("//input[@name='claimant.redactedDriversLicenseNumber']")));
			verifyEquals("GA", getValue(By.name("claimant.driversLicenseState")));
			verifyEquals("US", getValue(By.name("claimant.driversLicenseCountry")));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Pre-populate Claim. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		click(By.name("save"));
		driver.manage().timeouts().pageLoadTimeout(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);		
	}
	
	@Test
	public void testDriversLicenseRedactedAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrailTest());
		verifyTrue(isTextPresent("Driver's License Number : *********"));
		verifyTrue(isTextPresent("State : GA"));
		verifyTrue(isTextPresent("Province :"));
		verifyTrue(isTextPresent("Country Of Issue : US"));
		goToTaskManager();
	}

	@Test
	public void testPassportRedactedAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Common Number (Passport) : ***************"));
		verifyTrue(isTextPresent("Country : US"));
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseViewEdit() {
		// MJS: enable view/edit drivers license
		verifyTrue(setPermissions(new String[] { "632", "633" }, new boolean[] { true, true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isEditable(By.name("passenger[0].decriptedDriversLicense")));
		verifyEquals(WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE, selenium.getValue("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(isEditable(By.name("passenger[0].dlstate")));
		verifyEquals("GA", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyTrue(isEditable(By.name("passenger[0].driversLicenseCountry")));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));		
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testDriversLicenseViewEditAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Driver's License Number : " + WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE));
		verifyTrue(isTextPresent("State : GA"));
		verifyTrue(isTextPresent("Province :"));
		verifyTrue(isTextPresent("Country Of Issue : US"));
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testDriversLicenseStateProvinceFields() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.select("name=passenger[0].driversLicenseCountry", "label=United Kingdom");
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(isEditable(By.name("passenger[0].dlstate")));
		verifyTrue(isEditable(By.name("passenger[0].driversLicenseProvince")));
		selenium.type("name=passenger[0].driversLicenseProvince", "Test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isElementPresent(By.xpath("//a[contains(text(),'" + Settings.INCIDENT_ID_WN + "')]")));
			selenium.click("link=" + Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
				verifyFalse(isEditable(By.name("passenger[0].dlstate")));
				verifyTrue(isEditable(By.name("passenger[0].driversLicenseProvince")));
				verifyEquals("Test", selenium.getValue("name=passenger[0].driversLicenseProvince"));
				
				selenium.select("name=passenger[0].driversLicenseCountry", "label=United States");
				verifyTrue(isEditable(By.name("passenger[0].dlstate")));
				verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
				verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
				selenium.select("name=passenger[0].dlstate", "label=Alabama");
				
				selenium.click("name=saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isElementPresent(By.xpath("//a[contains(text(),'" + Settings.INCIDENT_ID_WN + "')]")));
					selenium.click("link=" + Settings.INCIDENT_ID_WN);
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						verifyTrue(isEditable(By.name("passenger[0].dlstate")));
						verifyEquals("AL", selenium.getValue("name=passenger[0].dlstate"));
						verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
						verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						verifyTrue(false);
					} 
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to save Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testPassportViewEdit() {
		verifyTrue(setPermissions(new String[] { "636", "637" }, new boolean[] { true, true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isEditable(By.name("passenger[0].decryptedPassportNumber")));
		verifyEquals(WN_CreateLD_VerifyRequiredFields.PASSPORT_NUMBER, selenium.getValue("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(isEditable(By.name("passenger[0].passportIssuer")));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));		
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testPassportViewEditAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Common Number (Passport) : " + WN_CreateLD_VerifyRequiredFields.PASSPORT_NUMBER));
		verifyTrue(isTextPresent("Country : US"));
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testCollectPosIdFields() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isTextPresent("Position ID"));
		verifyTrue(isElementPresent(By.name("theitem[0].posId")));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(isTextPresent("Position ID : 123456"));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdFieldsNotPresent() {
		verifyTrue(setPermissions(new String[] { "635" }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isTextPresent("Position ID"));
		verifyFalse(isElementPresent(By.name("theitem[0].posId")));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdNotPresentAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(isTextPresent("Position ID : 123456"));
		goToTaskManager();
	}
	
	@Test
	public void testPressEnterInRemarksField() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.type("id=remark[0]", "");
		verifyEquals("1500", selenium.getValue("//input[@id='remark[0].counter']"));
		driver.findElement(By.id("remark[0]")).sendKeys("Test line" + Keys.ENTER + Keys.ENTER + Keys.ENTER);
		verifyEquals("1485", selenium.getValue("//input[@id='remark[0].counter']"));
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		goToTaskManager();
		waitForPageToLoadImproved();
	}

	@Test
	public void testUTBBagTag(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		type(By.name("claimcheck[0].claimchecknum"), "UTB");
		click(By.name("saveButton"));
		assertEquals("Claim Check Number is not a valid claim check number.[10 digits or 8 character AN or marked UTB]", getAlert());
		SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
		String utbNum = "UTB" + df.format(new Date());
		type(By.name("claimcheck[0].claimchecknum"), utbNum);
		click(By.name("saveButton"));

		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		} else {
			System.out.println("CLDVRF: ERROR SAVING INCIDENT.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals(getValue(By.name("claimcheck[0].claimchecknum")), utbNum);
			clickMenu("menucol_1.4");
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO INCIDENT.");
			return;
		}
		

		if (checkNoErrorPage()) {
			type(By.name("incident_ID"), Settings.INCIDENT_ID_WN);
			type(By.name("claimchecknum"), utbNum);
			click(By.id("button"));
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO THE LOST DELAY SEARCH PAGE.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals(Settings.INCIDENT_ID_WN, getValue(By.name("incident_ID")));
			goToTaskManager();
		} else {
			System.out.println("CLDVRF: ERROR SEARCHING FOR THE INCIDENT BY CLAIMCHECK NUMBER.");
			return;
		}
		
		if (checkNoErrorPage()) {
			loadQuickSearch();
			searchQuickSearch(utbNum);
			verifyTrue(isTextPresent(Settings.INCIDENT_ID_WN));
			closeQuickSearch();
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO TASKMANAGER.");
			return;
		}
	}
	
	@Test
	public void testPopulateQSCheck() {
		if (checkNoErrorPage()) {
			clickMenu("menucol_1.1");
			waitForPageToLoadImproved(10000);
			selenium.type("name=recordlocator", TODAY_SIX_DIGIT);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load page.");
			return;
		}
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent(Settings.INCIDENT_ID_WN));
			clickMenu("menucol_0.0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load Quick Search and search on PNR.");
			return;
		}

		if (checkNoErrorPage()) {
			loadQuickSearch();
			selenium.type("id=quickSearchQuery3", TODAY_SIX_DIGIT);
			selenium.click("id=button");
			waitForPageToLoadImproved(3000,false);
		} else {
			System.out.println("CLDVRF: Failed to load prepopulate page.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent(Settings.INCIDENT_ID_WN));
			closeQuickSearch();
		} else {
			System.out.println("CLDVRF: Failed to check for existing PNR Incidents.");
			return;			
		}
	}
	
	@Test
	public void testCreateIncidentWithEmptyDriversLicense() throws Exception {
		verifyTrue(setPermissions(new String[] { "632", "633", "636", "637" }, new boolean[] { true, false, true, false }));
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("name=addbagit");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Passenger Itinerary'.");
			}

			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("id=addresses[0].permanent");
				selenium.type("name=passenger[0].lastname", "Test1");
				selenium.type("name=passenger[0].firstname", "Test1");
				selenium.type("name=addresses[0].address1", "123 circle round drive");
				selenium.type("name=addresses[0].city", "atlanta");
				selenium.select("name=addresses[0].state_ID", "label=Georgia");			
				selenium.type("name=theitinerary[0].legfrom", "ATL");	
				selenium.type("name=theitinerary[0].legto", "AEX");						
				selenium.type("name=theitinerary[0].flightnum", "1234");			
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");					
				selenium.type("name=theitinerary[1].legfrom", "ALS");
				selenium.type("name=theitinerary[1].legto", "ATL");
				selenium.type("name=theitinerary[1].flightnum", "3456");
				selenium.click("id=calendar31");
				selenium.click("link=Today");
				selenium.select("name=theitem[0].color", "label=BN - Brown");
				selenium.select("id=bagtype0", "label=25");			
				selenium.type("name=recordlocator", "TESTER");			
				selenium.select("name=passenger[0].salutation", "label=Mr.");	
				selenium.type("name=addresses[0].zip", "33213");
				selenium.type("name=addresses[0].mobile", "4040213465");		
				selenium.select("name=theitem[0].manufacturer_ID", "label=Andiamo");
				selenium.type("id=remark[0]", "remark noted");
				selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[0].description", "TEST");
				selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
				selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.type("name=inventorylist[2].description", "TEST");
				
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.type("name=addresses[0].email", "email@email.com");
				selenium.type("name=claimcheck[0].claimchecknum", "WN123456");
				selenium.click("savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_WN = incident_id;
					System.out.println("WN: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_WN);
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
	}
	
	@Test
	public void testEmptyDriversLicenseDisabled() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isEditable(By.name("passenger[0].redactedDriversLicense")));
		verifyEquals("", selenium.getValue("name=passenger[0].redactedDriversLicense"));
		verifyFalse(isEditable(By.name("passenger[0].dlstate")));
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseProvince")));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyFalse(isEditable(By.name("passenger[0].driversLicenseCountry")));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testEmptyDriversLicenseEnabled() {
		verifyTrue(setPermissions(new String[] { "632", "633" } , new boolean[] { true, true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isEditable(By.name("passenger[0].decriptedDriversLicense")));
		verifyEquals("", selenium.getValue("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(isEditable(By.name("passenger[0].dlstate")));
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyTrue(isEditable(By.name("passenger[0].driversLicenseCountry")));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testEmptyPassportDisabled() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isEditable(By.name("passenger[0].redactedPassportNumber")));
		verifyEquals("", selenium.getValue("name=passenger[0].redactedPassportNumber"));
		verifyFalse(isEditable(By.name("passenger[0].passportIssuer")));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}

	@Test
	public void testEmptyPassportEnabled() {
		verifyTrue(setPermissions(new String[] { "636", "637" }, new boolean[] { true, true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isEditable(By.name("passenger[0].decryptedPassportNumber")));
		verifyEquals("", selenium.getValue("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(isEditable(By.name("passenger[0].passportIssuer")));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}

	@Test
	public void testCourtesyReasonDropdownValuesLD() {
		verifyTrue(setPermissions(new String[] { PermissionsUtil.INCIDENT_COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check Voluntary Separation Late Check", selenium.getText("id=courtesyReasonId"));
		goToTaskManager();
	}

	@Test
	public void testSecureRemarksDisabledLD() {
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isTextPresent("Secure Remark"));
		verifyFalse(isTextPresent("General Remark"));
		verifyFalse(isTextPresent("Remark is Secure"));
		selenium.type("id=remark[0]", "General Test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			goToTaskManager();
		} else {
			System.out.println("Failed to save general remark");
		}
	}
	
	@Test
	public void testSecureRemarksEnabledLD() {
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("name=addremark");

		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
		verifyTrue(isTextPresent("Secure Remark"));
		selenium.check("name=remark[1].secure");
		selenium.type("id=remark[1]", "Secure Test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to Added Incident Remark");
		}
		if (checkNoErrorPage()) {

			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			verifyTrue(isTextPresent("Remark is Secure"));
		} else {
			System.out.println("Failed to Save Remark");
		}
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { false }));
		goToTaskManager();
	}
	
	@Test
	public void testBagLossCode(){
		verifyTrue(setPermissions(new String[] { "661", "662", "663"}, new boolean[] { true, true,false}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isTextPresent("Fault Code"));
		verifyTrue(isTextPresent("Fault Station"));
		verifyTrue(isEditable(By.name("theitem[0].lossCode")));
		verifyTrue(isEditable(By.name("theitem[0].faultStation_id")));
		selenium.select("name=theitem[0].lossCode", "value=11");
		selenium.select("name=theitem[0].faultStation_id", "label=LZ");
		selenium.click("name=saveButton");
		assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
		selenium.click("name=addremark");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("name=saveButton");
			assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
			selenium.type("id=remark[2]", "Loss Code Change Remark");
			selenium.click("name=saveButton");
			assertEquals("Fault Station must be in Passenger Itinerary", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to add Remark for loss code change");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been modified"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save incident for loss code change");
		}
	}
	
	@Test
	public void testCreateBDOLD(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyEquals("Passenger Pick Up",selenium.getValue("name=passengerpickedup0"));
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
			selenium.select("name=theitem[0].lossCode", "label=Please Select");
			selenium.select("name=theitem[0].faultStation_id", "label=Please Select");
			waitForPageToLoadImproved(1000,false);
			selenium.click("id=button");
			assertEquals("Fault Code is required.", selenium.getAlert());
			selenium.select("name=theitem[0].lossCode", "value=11");
			waitForPageToLoadImproved(1000,false);
			selenium.click("id=button");
			assertEquals("Fault Station is required.", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			waitForPageToLoadImproved(1000,false);
			selenium.click("id=button");
			assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
			selenium.type("name=remark", "BDO Loss Code Change Remark");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create BDO for incident");
		}
		
		if (checkNoErrorPage()) {
			Settings.BDO_ID_WN=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr[2]/td");
			verifyTrue(isTextPresent("The Baggage Delivery Order has been successfully saved"));
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			verifyTrue(isTextPresent("Cannot Passenger Pick Up - There is a Non-Cancelled BDO for this Item"));
			verifyFalse(isEditable(By.name("theitem[0].lossCode")));
			verifyFalse(isEditable(By.name("theitem[0].faultStation_id")));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save BDO for incident");
		}
	}
	
	@Test
	public void testBagLossEditDelivered(){
		verifyTrue(setPermissions(new String[] { "662","663"}, new boolean[] { false, true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isEditable(By.name("theitem[0].lossCode")));
		verifyFalse(isEditable(By.name("theitem[0].faultStation_id")));
		verifyTrue(setPermissions(new String[] { "662","663"}, new boolean[] { true, true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isEditable(By.name("theitem[0].lossCode")));
		verifyTrue(isEditable(By.name("theitem[0].faultStation_id")));
		goToTaskManager();
	}
	
	@Test
	public void testClonePassItin(){
		createIncident(false);
		selenium.type("name=theitinerary[0].legfrom", "LAX");
		selenium.type("name=theitinerary[0].legto", "ATL");
		selenium.type("name=theitinerary[0].flightnum", "123");
		selenium.type("name=theitinerary[0].disdepartdate", "01/01/2013");
		selenium.type("name=theitinerary[0].disarrivedate", "01/01/2013");
		selenium.type("name=theitinerary[0].disschdeparttime", "12:00");
		selenium.type("name=theitinerary[0].disscharrivetime", "12:00");
		selenium.type("name=theitinerary[0].disactdeparttime", "12:00");
		selenium.type("name=theitinerary[0].disactarrivetime", "12:00");
		selenium.click("name=clonepassit");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyEquals("LAX", selenium.getValue("name=theitinerary[1].legfrom"));
			verifyEquals("ATL", selenium.getValue("name=theitinerary[1].legto"));
			verifyEquals("WN", selenium.getSelectedLabel("name=theitinerary[1].airline"));
			verifyEquals("01/01/2013", selenium.getValue("name=theitinerary[1].disdepartdate"));
			verifyEquals("01/01/2013", selenium.getValue("name=theitinerary[1].disarrivedate"));
			verifyEquals("12:00", selenium.getValue("name=theitinerary[1].disschdeparttime"));
			verifyEquals("12:00", selenium.getValue("name=theitinerary[1].disscharrivetime"));
			verifyEquals("12:00", selenium.getValue("name=theitinerary[1].disactdeparttime"));
			verifyEquals("12:00", selenium.getValue("name=theitinerary[1].disactarrivetime"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to verify clone itinerary for incident");
		}
	}
	
	@Test
	public void testIssuanceDocument(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.select("id=issuance_category", "label=Assistive Device");
		selenium.click("name=issueItem");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("link=Preview"));
			selenium.select("id=issuance_category", "label=Test");
			selenium.select("id=issuance_type", "label=item1 - A test (33454)");
			selenium.click("name=loanItem");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create document for incident");
		}		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("link=Preview"));
			selenium.select("id=issuance_category", "label=Test");
			selenium.select("id=issuance_type", "label=item1 - ze test (445566)");
			selenium.click("name=tradeItem");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create document for incident");
		}				
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("link=Preview"));
			selenium.click("name=issuance_edit_0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create document for incident");
		}	
		if (checkNoErrorPage()) {
			selenium.click("name=issuance_edit_0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create document for incident");
		}	
		if (checkNoErrorPage()) {
			selenium.click("name=issuance_edit_0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create document for incident");
		}	
	}
	
}
