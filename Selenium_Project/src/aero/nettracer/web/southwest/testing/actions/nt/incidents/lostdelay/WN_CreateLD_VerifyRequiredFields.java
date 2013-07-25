package aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WN_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	private static final String DRIVERS_LICENSE = "12345";
	
	@Test
	public void testVerifyText() throws Exception {
		// MJS: initial state is drivers license collection enabled
		// 		and view/edit drivers license disabled.
		setDriversLicensePermission(true, false);
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
				selenium.type("name=passenger[0].decriptedDriversLicense", WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE);
				selenium.select("name=passenger[0].dlstate", "label=Georgia");
				verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
				verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
				selenium.type("name=addresses[0].address1", "123 Test");
				selenium.type("name=addresses[0].city", "Test");
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
	public void testVerifyDriversLicenseRedacted() {
		verifyTrue(navigateToIncident());
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
	public void testVerifyDriversLicensePrepopulateClaim() {
		navigateToIncident();
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
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseRedactedAuditTrail() {
		verifyTrue(navigateToAuditTrail());
		verifyTrue(selenium.isTextPresent("Driver's License Number : *********"));
		verifyTrue(selenium.isTextPresent("State : GA"));
		verifyTrue(selenium.isTextPresent("Province :"));
		verifyTrue(selenium.isTextPresent("Country Of Issue : US"));
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseViewEdit() {
		// MJS: enable view/edit drivers license
		verifyTrue(setDriversLicensePermission(true, true));
		verifyTrue(navigateToIncident());
		verifyTrue(selenium.isEditable("name=passenger[0].decriptedDriversLicense"));
		verifyEquals(WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE, selenium.getValue("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(selenium.isEditable("name=passenger[0].dlstate"));
		verifyEquals("GA", selenium.getValue("name=passenger[0].dlstate"));
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		verifyEquals("", selenium.getValue("name=passenger[0].driversLicenseProvince"));
		verifyTrue(selenium.isEditable("name=passenger[0].driversLicenseCountry"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));		
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseViewEditAuditTrail() {
		verifyTrue(navigateToAuditTrail());
		verifyTrue(selenium.isTextPresent("Driver's License Number : " + WN_CreateLD_VerifyRequiredFields.DRIVERS_LICENSE));
		verifyTrue(selenium.isTextPresent("State : GA"));
		verifyTrue(selenium.isTextPresent("Province :"));
		verifyTrue(selenium.isTextPresent("Country Of Issue : US"));
		goToTaskManager();
	}
	
	@Test
	public void testDriversLicenseStateProvinceFields() {
		verifyTrue(navigateToIncident());
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
	}
	
	@Test
	public void testPressEnterInRemarksField() {
		verifyTrue(navigateToIncident());
		String locator = "//textarea[@id='remark[0]']";
		verifyEquals("1500", selenium.getValue("//input[@id='remark[0].counter']"));
		typeString(locator, "Test line");
		selenium.focus(locator);
		selenium.setCursorPosition(locator, String.valueOf(selenium.getValue(locator).length()));
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		verifyEquals("1487", selenium.getValue("//input[@id='remark[0].counter']"));
		goToTaskManager();
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
			selenium.type("id=quickSearchQuery3", "TESTER");
			selenium.click("id=button");
			waitForPageToLoadImproved(1000,false);
		} else {
			System.out.println("CLDVRF: Failed to load prepopulate page.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
		} else {
			System.out.println("CLDVRF: Failed to check for existing PNR Incidents.");
			return;			
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
	
	private boolean navigateToPermissionsPage() {
		boolean success = false;
		selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
		waitForPageToLoadImproved();
		
		selenium.select("//select[@name='companyCode']", "label=Owens Group");
		selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[4]/td[2]/input", Settings.USERNAME_OGADMIN);
		selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[5]/td[2]/input", Settings.PASSWORD_OGADMIN);
		selenium.click("//input[@id='button']");
		waitForPageToLoadImproved();		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(text(),'Maintain Companies')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.type("//input[@name='companySearchName']", "wn");
				selenium.click("//input[@id='button']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("xpath=(//a[contains(text(),'Maintain')])[14]");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						selenium.click("xpath=(//a[contains(text(),'Maintain')])[12]");
						waitForPageToLoadImproved();
						if (checkNoErrorPage()) {
							success = selenium.isTextPresent("Collect Driver's License") && selenium.isTextPresent("View/Edit Driver's License");
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Groups. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Searching For SouthWest. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Maintain Companies Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}			
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Log In. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
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
	
	private boolean loginToNt() {
		boolean success = false;
		selenium.type("document.logonForm.elements[1]", Settings.USERNAME_ADMIN);
		selenium.type("document.logonForm.elements[2]", Settings.PASSWORD_ADMIN);
		selenium.click("document.logonForm.elements[3]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Task Manager Home"));
			success = true;
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Log In. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	private boolean navigateToIncident() {
		boolean success = false;
		selenium.click("//a[contains(@href, 'searchIncident.do?ld=1')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=incident_ID", Settings.INCIDENT_ID_WN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				success = true;
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Search Page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	private boolean navigateToAuditTrail() {
		boolean success = false;
		selenium.click("//a[@id='menucol_9.12']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//td[@id='navmenucell']/div/dl/dd[3]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("//input[@name='incident_ID']", Settings.INCIDENT_ID_WN);
				selenium.click("//input[@id='button']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					selenium.click("//a[contains(@href, 'audit_mbr.do?detail=1&incident_ID=" + Settings.INCIDENT_ID_WN + "')]");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						selenium.check("//input[@name='audit_id']");
						selenium.click("xpath=(//input[@id='button'])[3]");
						waitForPageToLoadImproved();
						if (checkNoErrorPage()) {
							checkCopyrightAndQuestionMarks();
							success = true;
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Tab. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}

}
