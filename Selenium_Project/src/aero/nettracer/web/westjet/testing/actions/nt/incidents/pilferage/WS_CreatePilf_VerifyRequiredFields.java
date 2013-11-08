package aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CreatePilf_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		waitForPageToLoadImproved(1000,false);
		clickMenu("menucol_3.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Last Name is required.", selenium.getAlert());
				selenium.type("name=passenger[0].lastname", "Test");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("First Name is required.", selenium.getAlert());
				selenium.type("name=passenger[0].firstname", "Test");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Street Address is required.", selenium.getAlert());
				selenium.type("name=addresses[0].address1", "123 Test");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("City is required.", selenium.getAlert());
				selenium.type("name=addresses[0].city", "Test");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.type("name=theitinerary[0].legto", "LAX");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].flightnum", "123");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Departure Date is required.", selenium.getAlert());
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Colour is required.", selenium.getAlert());
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Type is required.", selenium.getAlert());
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("PNR is required.", selenium.getAlert());
				selenium.type("name=recordlocator", "TESTER");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Email is required.", selenium.getAlert());
				selenium.type("name=addresses[0].email", "email@email.com");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Baggage Tag Number is required.", selenium.getAlert());
				selenium.type("name=theitem[0].claimchecknum", "WS123456");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved(1000,false);
				assertEquals("Phone is required.", selenium.getAlert());
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.click("name=saveButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(isTextPresent("Pilferage PIR has been submitted."));
					checkCopyrightAndQuestionMarks();
					String pilferage_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.PILFERAGE_ID_WS = pilferage_id;
					System.out.println("WS: Pilferage Incident Created: " + Settings.PILFERAGE_ID_WS);
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Create Pilferage Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Edit Pilferage Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
