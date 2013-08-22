package aero.nettracer.web.southwest.testing.actions.nt.incidents.missing;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateMissing extends WN_SeleniumTest {

	@Test
	public void testCreateMissingIncident() {
		selenium.click("//a[@id='menucol_3.1']");
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
					verifyTrue(selenium.isTextPresent("Missing Articles Incident has been submitted."));
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
				System.out.println("!!!!!!!!!!!!!!! - Edit Missing Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Missing Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testCourtesyReasonDropdownValues() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.COURTESY_REASON_COLLECT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_MISSING));
		verifyEquals("Please Select Other Outside 4-Hour Claim Check on Another Carrier No Claim Check", selenium.getText("id=courtesyReasonId"));
		goToTaskManager();
	}
	
}
