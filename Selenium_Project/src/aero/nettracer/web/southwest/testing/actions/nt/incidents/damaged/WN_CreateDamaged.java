package aero.nettracer.web.southwest.testing.actions.nt.incidents.damaged;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateDamaged extends WN_SeleniumTest {
	
	@Test
	public void testCreateDamagedIncident() {
		verifyTrue(setCollectExpediteTagNumberPermission(true));
		selenium.click("//a[@id='menucol_2.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
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
		verifyTrue(setCollectExpediteTagNumberPermission(true));
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
		verifyTrue(setCollectExpediteTagNumberPermission(false));
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
	
	private boolean setCollectExpediteTagNumberPermission(boolean check) {
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
		
		if (check) {
			selenium.check("name=638");
		} else {
			selenium.uncheck("name=638");
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
