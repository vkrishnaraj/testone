package aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateLD_VerifyRequiredFields extends WN_SeleniumTest {

	@Test
	public void testVerifyText() throws Exception {
		// MJS: initial state is drivers license collection enabled
		// 		and view/edit drivers license disabled.
		verifyTrue(setDriversLicensePermission(true, false));
		verifyTrue(setPassportPermission(true, false));
		verifyTrue(setCollectPosIdPermission(true));
		selenium.click("//a[@id='menucol_1.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("name=passenger[0].lastname", "Test");
				selenium.type("name=passenger[0].firstname", "Test");
				verifyTrue(selenium.isElementPresent("name=passenger[0].decriptedDriversLicense"));
				verifyTrue(selenium.isElementPresent("name=passenger[0].dlstate"));
				verifyTrue(selenium.isElementPresent("name=passenger[0].driversLicenseProvince"));
				verifyTrue(selenium.isElementPresent("name=passenger[0].driversLicenseCountry"));
				verifyTrue(selenium.isElementPresent("name=passenger[0].decryptedPassportNumber"));
				verifyTrue(selenium.isElementPresent("name=passenger[0].passportIssuer"));
				selenium.type("name=passenger[0].decriptedDriversLicense", WN_SeleniumTest.DRIVERS_LICENSE);
				selenium.select("name=passenger[0].dlstate", "label=Georgia");
				verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
				verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
				selenium.type("name=passenger[0].decryptedPassportNumber", WN_SeleniumTest.PASSPORT_NUMBER);
				selenium.select("name=passenger[0].passportIssuer", "label=United States");
				selenium.type("name=addresses[0].address1", "123 Test");
				selenium.type("name=addresses[0].city", "Test");
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.type("name=addresses[0].zip", "12345");
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.type("name=theitinerary[0].legto", "LAX");
				selenium.type("name=theitinerary[0].flightnum", "123");
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.type("name=theitem[0].posId", "123456");
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[0].description", "TEST");
				selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
				selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.type("name=inventorylist[2].description", "TEST");
				selenium.type("name=recordlocator", "TESTER");
				selenium.type("name=addresses[0].email", "email@email.com");
				selenium.type("name=claimcheck[0].claimchecknum", "WN123456");
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.click("savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been submitted."));
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
	public void testVerifyDriversLicenseRedacted() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(selenium.isEditable("name=passenger[0].redactedDriversLicense"));
		verifyEquals("*********", selenium.getValue("name=passenger[0].redactedDriversLicense"));
		verifyFalse(selenium.isEditable("name=passenger[0].dlstate"));
		verifyEquals("GA", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseCountry"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyPassportRedacted() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(selenium.isEditable("name=passenger[0].redactedPassportNumber"));
		verifyEquals("***************", selenium.getValue("name=passenger[0].redactedPassportNumber"));
		verifyFalse(selenium.isEditable("name=passenger[0].passportIssuer"));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyDriversLicensePrepopulateClaim() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyEquals("*********", selenium.getValue("//input[@name='claimant.redactedDriversLicenseNumber']"));
			verifyEquals("GA", selenium.getValue("name=claimant.driversLicenseState"));
			verifyEquals("US", selenium.getValue("name=claimant.driversLicenseCountry"));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Pre-populate Claim. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		
		selenium.click("name=save");
		waitForPageToLoadImproved();
		goToTaskManager();

		waitForPageToLoadImproved();
		
	}
	
	@Test
	public void testDriversLicenseRedactedAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Driver's License Number : *********"));
		verifyTrue(selenium.isTextPresent("State : GA"));
		verifyTrue(selenium.isTextPresent("Province :"));
		verifyTrue(selenium.isTextPresent("Country Of Issue : US"));
		goToTaskManager();
	}

	@Test
	public void testPassportRedactedAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Common Number (Passport) : ***************"));
		verifyTrue(selenium.isTextPresent("Country : US"));
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseViewEdit() {
		// MJS: enable view/edit drivers license
		verifyTrue(setDriversLicensePermission(true, true));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isEditable("name=passenger[0].decriptedDriversLicense"));
		verifyEquals(WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE, selenium.getValue("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(selenium.isEditable("name=passenger[0].dlstate"));
		verifyEquals("GA", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyTrue(selenium.isEditable("name=passenger[0].driversLicenseCountry"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));		
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testDriversLicenseViewEditAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Driver's License Number : " + WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE));
		verifyTrue(selenium.isTextPresent("State : GA"));
		verifyTrue(selenium.isTextPresent("Province :"));
		verifyTrue(selenium.isTextPresent("Country Of Issue : US"));
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testDriversLicenseStateProvinceFields() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.select("name=passenger[0].driversLicenseCountry", "label=United Kingdom");
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(selenium.isEditable("name=passenger[0].dlstate"));
		verifyTrue(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		selenium.type("name=passenger[0].driversLicenseProvince", "Test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isElementPresent("//a[contains(text(),'" + Settings.INCIDENT_ID_WN + "')]"));
			selenium.click("link=" + Settings.INCIDENT_ID_WN);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
				verifyFalse(selenium.isEditable("name=passenger[0].dlstate"));
				verifyTrue(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
				verifyEquals("Test", selenium.getValue("name=passenger[0].driversLicenseProvince"));
				
				selenium.select("name=passenger[0].driversLicenseCountry", "label=United States");
				verifyTrue(selenium.isEditable("name=passenger[0].dlstate"));
				verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
				verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
				selenium.select("name=passenger[0].dlstate", "label=Alabama");
				
				selenium.click("name=saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isElementPresent("//a[contains(text(),'" + Settings.INCIDENT_ID_WN + "')]"));
					selenium.click("link=" + Settings.INCIDENT_ID_WN);
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						verifyTrue(selenium.isEditable("name=passenger[0].dlstate"));
						verifyEquals("AL", selenium.getValue("name=passenger[0].dlstate"));
						verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
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
		verifyTrue(setPassportPermission(true, true));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isEditable("name=passenger[0].decryptedPassportNumber"));
		verifyEquals(WN_CreateLD_VerifyRequiredFields.PASSPORT_NUMBER, selenium.getValue("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(selenium.isEditable("name=passenger[0].passportIssuer"));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));		
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testPassportViewEditAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Common Number (Passport) : " + WN_CreateLD_VerifyRequiredFields.PASSPORT_NUMBER));
		verifyTrue(selenium.isTextPresent("Country : US"));
		goToTaskManager();
		waitForPageToLoadImproved();
	}
	
	@Test
	public void testCollectPosIdFields() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isTextPresent("Position ID"));
		verifyTrue(selenium.isElementPresent("name=theitem[0].posId"));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyTrue(selenium.isTextPresent("Position ID : 123456"));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdFieldsNotPresent() {
		verifyTrue(navigateToPermissionsPage());
		verifyTrue(setCollectPosIdPermission(false));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(selenium.isTextPresent("Position ID"));
		verifyFalse(selenium.isElementPresent("name=theitem[0].posId"));
		goToTaskManager();
	}
	
	@Test
	public void testPosIdNotPresentAuditTrail() {
		verifyTrue(navigateToIncidentAuditTrail());
		verifyFalse(selenium.isTextPresent("Position ID : 123456"));
		goToTaskManager();
	}
	
	@Test
	public void testPressEnterInRemarksField() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		String locator = "//textarea[@id='remark[0]']";
		verifyEquals("1500", selenium.getValue("//input[@id='remark[0].counter']"));
		typeString(locator, "Test line");
		selenium.focus(locator);
		selenium.setCursorPosition(locator, String.valueOf(selenium.getValue(locator).length()));
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		verifyEquals("1487", selenium.getValue("//input[@id='remark[0].counter']"));
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		goToTaskManager();
		waitForPageToLoadImproved();
	}

	@Test
	public void testUTBBagTag(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.type("name=claimcheck[0].claimchecknum", "UTB");
		selenium.click("name=saveButton");
		assertEquals("Claim Check Number is not a valid claim check number.[10 digits or 8 character AN]", selenium.getAlert());
		selenium.type("name=claimcheck[0].claimchecknum", "UTB12345678");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		} else {
			System.out.println("CLDVRF: ERROR SAVING INCIDENT.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals(selenium.getValue("name=claimcheck[0].claimchecknum"),"UTB12345678");
			selenium.click("id=menucol_1.4");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO INCIDENT.");
			return;
		}
		

		if (checkNoErrorPage()) {
			selenium.type("name=claimchecknum", "UTB12345678");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO THE LOST DELAY SEARCH PAGE.");
			return;
		}
		

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			goToTaskManager();
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: ERROR SEARCHING FOR THE INCIDENT BY CLAIMCHECK NUMBER.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.controlKeyDown();
			selenium.keyDown("id=header", "\\83");
			selenium.keyUp("id=header", "\\83");
			selenium.controlKeyUp();
			waitForPageToLoadImproved(3000,false);
			selenium.type("id=quickSearchQuery3", "UTB12345678");
			selenium.click("id=button");
			waitForPageToLoadImproved(10000,false);
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			selenium.keyDown("id=header", "\\27");
			selenium.keyUp("id=header", "\\27");
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO TASKMANAGER.");
			return;
		}
	}
	
	@Test
	public void testPopulateQSCheck() {
		if (checkNoErrorPage()) {
			selenium.click("id=menucol_1.1");
			waitForPageToLoadImproved();
			selenium.type("name=recordlocator", "TESTER");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load page.");
			return;
		}
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			selenium.click("menucol_0.0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load Quick Search and search on PNR.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.controlKeyDown();
			selenium.keyDown("id=header", "\\83");
			selenium.keyUp("id=header", "\\83");
			selenium.controlKeyUp();
			waitForPageToLoadImproved(3000,false);
			selenium.type("id=quickSearchQuery3", "TESTER");
			selenium.click("id=button");
			waitForPageToLoadImproved(10000,false);
		} else {
			System.out.println("CLDVRF: Failed to load prepopulate page.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			selenium.keyDown("id=header", "\\27");
			selenium.keyUp("id=header", "\\27");
		} else {
			System.out.println("CLDVRF: Failed to check for existing PNR Incidents.");
			return;			
		}
	}
	
	@Test
	public void testCreateIncidentWithEmptyDriversLicense() throws Exception {
		verifyTrue(setDriversLicensePermission(true, false));
		verifyTrue(setPassportPermission(true, false));
		selenium.click("//a[@id='menucol_1.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("name=passenger[0].lastname", "Test");
				selenium.type("name=passenger[0].firstname", "Test");
				selenium.type("name=addresses[0].address1", "123 Test");
				selenium.type("name=addresses[0].city", "Test");
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.type("name=addresses[0].zip", "12345");
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.type("name=theitinerary[0].legto", "LAX");
				selenium.type("name=theitinerary[0].flightnum", "123");
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[0].description", "TEST");
				selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
				selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
				selenium.type("name=inventorylist[1].description", "TEST");
				selenium.type("name=inventorylist[2].description", "TEST");
				selenium.type("name=recordlocator", "TESTER");
				selenium.type("name=addresses[0].email", "email@email.com");
				selenium.type("name=claimcheck[0].claimchecknum", "WN123456");
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.click("savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been submitted."));
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
		verifyFalse(selenium.isEditable("name=passenger[0].redactedDriversLicense"));
		verifyEquals("", selenium.getValue("name=passenger[0].redactedDriversLicense"));
		verifyFalse(selenium.isEditable("name=passenger[0].dlstate"));
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseCountry"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testEmptyDriversLicenseEnabled() {
		verifyTrue(setDriversLicensePermission(true, true));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isEditable("name=passenger[0].decriptedDriversLicense"));
		verifyEquals("", selenium.getValue("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(selenium.isEditable("name=passenger[0].dlstate"));
		verifyEquals("", selenium.getValue("name=passenger[0].dlstate"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyTrue(selenium.isEditable("name=passenger[0].driversLicenseCountry"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		goToTaskManager();
	}
	
	@Test
	public void testEmptyPassportDisabled() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(selenium.isEditable("name=passenger[0].redactedPassportNumber"));
		verifyEquals("", selenium.getValue("name=passenger[0].redactedPassportNumber"));
		verifyFalse(selenium.isEditable("name=passenger[0].passportIssuer"));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}

	@Test
	public void testEmptyPassportEnabled() {
		verifyTrue(setPassportPermission(true, true));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isEditable("name=passenger[0].decryptedPassportNumber"));
		verifyEquals("", selenium.getValue("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(selenium.isEditable("name=passenger[0].passportIssuer"));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}

	@Test
	public void testSecureRemarksDisabled() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("name=addremark");
		verifyFalse(selenium.isTextPresent("Secure Remark"));
		verifyFalse(selenium.isTextPresent("General Remark"));
		verifyFalse(selenium.isTextPresent("Remark is Secure"));
		selenium.type("id=remark[1]", "General Test");
		selenium.click("name=saveButton");
		goToTaskManager();
	}
	
	@Test
	public void testSecureRemarksEnabled() {
		verifyTrue(setSecureRemarksPermission(true));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("name=addremark");

		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
		verifyTrue(selenium.isTextPresent("Secure Remark"));
		selenium.click("name=remark[2].secure");
		selenium.type("id=remark[2]", "Secure Test");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to Added Incident Remark");
		}
		if (checkNoErrorPage()) {

			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			verifyTrue(selenium.isTextPresent("Remark is Secure"));
		} else {
			System.out.println("Failed to Save Remark");
		}
		verifyTrue(setSecureRemarksPermission(false));
		goToTaskManager();
	}
	
	private void typeString(String locator, String string) {
		char[] chars = string.toCharArray();
		StringBuffer sb = new StringBuffer(selenium.getText(locator));
		selenium.setCursorPosition(locator, String.valueOf(selenium.getValue(locator).length()));
		for (int i = 0; i < chars.length; i++) {
			char aChar = chars[i];
			String key = Character.toString(aChar);
			sb.append(aChar);
			selenium.keyDown(locator, key);
			selenium.type(locator, sb.toString());
			selenium.keyPress(locator, key);
			selenium.keyUp(locator, key);
		}
	}
	
	private boolean setDriversLicensePermission(boolean collectDl, boolean viewEditDl) {
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
				
		if (collectDl) {
			selenium.check("name=632");
		} else {
			selenium.uncheck("name=632");
		}
		
		if (viewEditDl) {
			selenium.check("name=633");
		} else {
			selenium.uncheck("name=633");
		}
		
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
			success = loginToNt();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		
		return success;
	}

	private boolean setPassportPermission(boolean collectPassport, boolean viewEditPassport) {
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
		
		if (collectPassport) {
			selenium.check("name=636");
		} else {
			selenium.uncheck("name=636");
		}
		
		if (viewEditPassport) {
			selenium.check("name=637");
		} else {
			selenium.uncheck("name=637");
		}
		
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
			success = loginToNt();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		
		return success;
	}
	
	private boolean setSecureRemarksPermission(boolean secureRemarks) {
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
		
		if (secureRemarks) {
			selenium.check("name=335");
		} else {
			selenium.uncheck("name=335");
		}
		
		
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
			success = loginToNt();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		
		return success;
	}
	
	private boolean setCollectPosIdPermission(boolean check) {
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
		
		if (check) {
			selenium.check("name=635");
		} else {
			selenium.uncheck("name=635");
		}
		
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
			success = loginToNt();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
}
