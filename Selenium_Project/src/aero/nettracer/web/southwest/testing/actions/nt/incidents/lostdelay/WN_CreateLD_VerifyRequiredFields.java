package aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateLD_VerifyRequiredFields extends WN_SeleniumTest {

	@Test
	public void testVerifyText() throws Exception {
		// MJS: initial state is drivers license collection enabled
		// 		and view/edit drivers license disabled.
		verifyTrue(setPermissions(new String[] { "632", "633", "636", "637", "635", "661", "662", "663", "664", "665"}, new boolean[] { true, false, true, false, true, false, false, false, false, false }));
		createIncident(true);
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
		verifyTrue(setPermissions(new String[] { "632", "633" }, new boolean[] { true, true }));
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
		verifyTrue(setPermissions(new String[] { "636", "637" }, new boolean[] { true, true }));
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
		verifyTrue(setPermissions(new String[] { "635" }, new boolean[] { false }));
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
		assertEquals("Claim Check Number is not a valid claim check number.[10 digits or 8 character AN or marked UTB]", selenium.getAlert());
		SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
		String utbNum = "UTB" + df.format(new Date());
		selenium.type("name=claimcheck[0].claimchecknum", utbNum);
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		} else {
			System.out.println("CLDVRF: ERROR SAVING INCIDENT.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals(selenium.getValue("name=claimcheck[0].claimchecknum"), utbNum);
			selenium.click("id=menucol_1.4");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: ERROR NAVIGATING TO INCIDENT.");
			return;
		}
		

		if (checkNoErrorPage()) {
			selenium.type("name=claimchecknum", utbNum);
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
			selenium.type("id=quickSearchQuery3", utbNum);
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
		verifyTrue(setPermissions(new String[] { "632", "633", "636", "637" }, new boolean[] { true, false, true, false }));
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
		verifyTrue(setPermissions(new String[] { "632", "633" } , new boolean[] { true, true }));
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
		verifyTrue(setPermissions(new String[] { "636", "637" }, new boolean[] { true, true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isEditable("name=passenger[0].decryptedPassportNumber"));
		verifyEquals("", selenium.getValue("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(selenium.isEditable("name=passenger[0].passportIssuer"));
		verifyEquals("US", selenium.getValue("name=passenger[0].passportIssuer"));
		goToTaskManager();
	}

	@Test
	public void testCourtesyReasonDropdownValues() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check Voluntary Separation Late Check", selenium.getText("id=courtesyReasonId"));
		goToTaskManager();
	}

	@Test
	public void testSecureRemarksDisabled() {
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(selenium.isTextPresent("Secure Remark"));
		verifyFalse(selenium.isTextPresent("General Remark"));
		verifyFalse(selenium.isTextPresent("Remark is Secure"));
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
	public void testSecureRemarksEnabled() {
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("name=addremark");

		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
		verifyTrue(selenium.isTextPresent("Secure Remark"));
		selenium.check("name=remark[1].secure");
		selenium.type("id=remark[1]", "Secure Test");
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
		verifyTrue(setPermissions(new String[] { "335" }, new boolean[] { false }));
		goToTaskManager();
	}
	
	@Test
	public void testBagLossCode(){
		verifyTrue(setPermissions(new String[] { "661", "662"}, new boolean[] { true, true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isTextPresent("Chargeback Code"));
		verifyTrue(selenium.isTextPresent("Chargeback Station"));
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
			assertEquals("Chargeback Station must be in Passenger Itinerary", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to add Remark for loss code change");
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been modified"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save incident for loss code change");
		}
	}
	
	@Test
	public void testCreateBDO(){
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
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
			selenium.select("name=theitem[0].lossCode", "label=Please Select");
			selenium.select("name=theitem[0].faultStation_id", "label=Please Select");
			selenium.click("id=button");
			assertEquals("Chargeback Code is required.", selenium.getAlert());
			selenium.select("name=theitem[0].lossCode", "value=11");
			selenium.click("id=button");
			assertEquals("Chargeback Station is required.", selenium.getAlert());
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
			verifyTrue(selenium.isTextPresent("The Baggage Delivery Order has been successfully saved"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save BDO for incident");
		}
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
			selenium.select("name=theitem[0].lossCode", "value=11");
			selenium.click("name=doclose");
			assertEquals("Remark for Loss Code Change is required.", selenium.getAlert());
			selenium.type("name=remark[5].remarktext", "Loss Code Change Remark3");
			selenium.click("name=doclose");
			assertEquals("Chargeback Station is required.", selenium.getAlert());
			selenium.select("name=theitem[0].faultStation_id", "label=ATL");
			selenium.click("name=doclose");waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been closed."));
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
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
	
}
