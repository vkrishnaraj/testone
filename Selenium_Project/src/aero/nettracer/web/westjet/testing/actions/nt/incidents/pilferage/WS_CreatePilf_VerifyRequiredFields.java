package aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CreatePilf_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("menucol_1.1");
		selenium.waitForPageToLoad("30000");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("savetracingButton");
			assertEquals("Last Name is required.", selenium.getAlert());
			selenium.type("name=passenger[0].lastname", "Test");
			selenium.click("savetracingButton");
			assertEquals("First Name is required.", selenium.getAlert());
			selenium.type("name=passenger[0].firstname", "Test");
			selenium.click("savetracingButton");
			assertEquals("Street Address is required.", selenium.getAlert());
			selenium.type("name=addresses[0].address1", "123 Test");
			selenium.click("savetracingButton");
			assertEquals("City is required.", selenium.getAlert());
			selenium.type("name=addresses[0].city", "Test");
			selenium.click("savetracingButton");
			assertEquals("From/To is required.", selenium.getAlert());
			selenium.type("name=theitinerary[0].legfrom", "ATL");
			selenium.type("name=theitinerary[0].legto", "LAX");
			selenium.click("savetracingButton");
			assertEquals("Airline/Flight Number is required.", selenium.getAlert());
			selenium.type("name=theitinerary[0].flightnum", "123");
			selenium.click("savetracingButton");
			assertEquals("Departure Date is required.", selenium.getAlert());
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");
			selenium.click("savetracingButton");
			assertEquals("Colour is required.", selenium.getAlert());
			selenium.select("name=theitem[0].color", "label=BK - Black");
			selenium.click("savetracingButton");
			assertEquals("Type is required.", selenium.getAlert());
			selenium.select("name=theitem[0].bagtype", "label=22");
			selenium.click("savetracingButton");
			assertEquals("Content Category is required.", selenium.getAlert());
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
			selenium.click("savetracingButton");
			assertEquals("Content Description is required.", selenium.getAlert());
			selenium.type("name=inventorylist[0].description", "TEST");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
			selenium.type("name=inventorylist[1].description", "TEST");
			selenium.type("name=inventorylist[2].description", "TEST");
			selenium.click("savetracingButton");
			assertEquals("PNR is required.", selenium.getAlert());
			selenium.type("name=recordlocator", "TESTER");
			selenium.click("savetracingButton");
			assertEquals("Email is required.", selenium.getAlert());
			selenium.type("name=addresses[0].email", "email@email.com");
			selenium.click("savetracingButton");
			assertEquals("Baggage Tag Number is required.", selenium.getAlert());
			selenium.type("name=claimcheck[0].claimchecknum", "WS123456");
			selenium.click("savetracingButton");
			assertEquals("Phone is required.", selenium.getAlert());
			selenium.type("name=addresses[0].mobile", "(555) 555-4444");
			selenium.click("savetracingButton");
			selenium.waitForPageToLoad("30000");
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Delayed PIR has been submitted."));
				checkCopyrightAndQuestionMarks();
				String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.INCIDENT_ID = incident_id;
				System.out.println("WS: Lost/Delay Incident Created: " + Settings.INCIDENT_ID);
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				selenium.waitForPageToLoad("30000");
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
