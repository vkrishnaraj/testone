package aero.nettracer.web.westjet.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("//a[contains(@href, 'lostDelay.do')]");
		selenium.waitForPageToLoad("30000");
		checkCopyrightAndQuestionMarks();
		selenium.click("name=savetracingButton");
		assertEquals("Last Name is required.", selenium.getAlert());
		selenium.type("name=passenger[0].lastname", "Test");
		selenium.click("name=savetracingButton");
		assertEquals("First Name is required.", selenium.getAlert());
		selenium.type("name=passenger[0].firstname", "Test");
		selenium.click("name=savetracingButton");
		assertEquals("Street Address is required.", selenium.getAlert());
		selenium.type("name=addresses[0].address1", "123 Test");
		selenium.click("name=savetracingButton");
		assertEquals("City is required.", selenium.getAlert());
		selenium.type("name=addresses[0].city", "Test");
		selenium.click("name=savetracingButton");
		assertEquals("From/To is required.", selenium.getAlert());
		selenium.type("name=theitinerary[0].legfrom", "ATL");
		selenium.type("name=theitinerary[0].legto", "LAX");
		selenium.click("name=savetracingButton");
		assertEquals("Airline/Flight Number is required.", selenium.getAlert());
		selenium.type("name=theitinerary[0].flightnum", "123");
		selenium.click("name=savetracingButton");
		assertEquals("Departure Date is required.", selenium.getAlert());
		selenium.click("id=itcalendar0");
		selenium.click("link=Today");
		selenium.click("name=savetracingButton");
		assertEquals("Colour is required.", selenium.getAlert());
		selenium.select("name=theitem[0].color", "label=BK - Black");
		selenium.click("name=savetracingButton");
		assertEquals("Type is required.", selenium.getAlert());
		selenium.select("name=theitem[0].bagtype", "label=22");
		selenium.click("name=savetracingButton");
		assertEquals("Content Category is required.", selenium.getAlert());
		selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
		selenium.click("name=savetracingButton");
		assertEquals("Content Description is required.", selenium.getAlert());
		selenium.type("name=inventorylist[0].description", "TEST");
		selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
		selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
		selenium.type("name=inventorylist[1].description", "TEST");
		selenium.type("name=inventorylist[2].description", "TEST");
		selenium.click("name=savetracingButton");
		assertEquals("PNR is required.", selenium.getAlert());
		selenium.type("name=recordlocator", "TESTER");
		selenium.click("name=savetracingButton");
		assertEquals("Email is required.", selenium.getAlert());
		selenium.type("name=addresses[0].email", "email@email.com");
		selenium.click("name=savetracingButton");
		assertEquals("Baggage Tag Number is required.", selenium.getAlert());
		selenium.type("name=claimcheck[0].claimchecknum", "WS123456");
		selenium.click("name=savetracingButton");
		assertEquals("Phone is required.", selenium.getAlert());
		selenium.type("name=addresses[0].mobile", "(555) 555-4444");
		selenium.click("name=savetracingButton");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Delayed PIR has been submitted."));
		checkCopyrightAndQuestionMarks();
		selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		selenium.waitForPageToLoad("30000");
	}
	
}
