package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
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
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Delayed PIR has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_WS = incident_id;
					System.out.println("WS: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_WS);
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
	public void testPressEnterInRemarksField() {
		String locator = "//textarea[@id='remark[0]']";
		verifyEquals("1500", selenium.getValue("//input[@id='remark[0].counter']"));
		typeString(locator, "Test line");
		selenium.focus(locator);
		selenium.setCursorPosition(locator, String.valueOf(selenium.getValue(locator).length()));
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		selenium.keyDown(locator, "\\13");
		verifyEquals("1487", selenium.getValue("//input[@id='remark[0].counter']"));

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
