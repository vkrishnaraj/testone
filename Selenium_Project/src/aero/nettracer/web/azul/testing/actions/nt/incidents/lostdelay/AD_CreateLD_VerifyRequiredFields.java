package aero.nettracer.web.azul.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AD_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

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
				selenium.click("name=savetracingButton");
				assertEquals("Record Locator is required.", selenium.getAlert());
				selenium.type("name=recordlocator", "TESTER");
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
				selenium.type("name=theitinerary[0].legfrom", "LAX");
				selenium.type("name=theitinerary[0].legto", "ATL");
				selenium.click("name=savetracingButton");
				assertEquals("Airline/Flight Number is required.",
						selenium.getAlert());
				selenium.type("name=theitinerary[0].flightnum", "123");
				selenium.click("name=savetracingButton");
				assertEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].legfrom", "LAX");
				selenium.type("name=theitinerary[1].legto", "ATL");
				selenium.click("name=savetracingButton");
				assertEquals("Airline/Flight Number is required.",
						selenium.getAlert());
				selenium.type("name=theitinerary[1].flightnum", "123");
				selenium.click("name=savetracingButton");
				assertEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=calendar31");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				assertEquals("Color is required.", selenium.getAlert());
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.click("name=savetracingButton");
				assertEquals("Type is required.", selenium.getAlert());
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.click("name=savetracingButton");
				assertEquals("Content Category is required.",
						selenium.getAlert());
				selenium.select("name=inventorylist[0].categorytype_ID",
						"label=Art");
				selenium.click("name=savetracingButton");
				assertEquals("Description is required.", selenium.getAlert());
				selenium.type("name=inventorylist[0].description", "TEST");
				selenium.click("name=savetracingButton");
				assertEquals("Phone is required.", selenium.getAlert());
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.click("name=savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium
							.isTextPresent("Lost/Delayed Bag Incident has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium
							.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_AD = incident_id;
					System.out.println("AD: Lost/Delay Incident Created: "
							+ Settings.INCIDENT_ID_AD);
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out
							.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out
						.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out
					.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
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
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		waitForPageToLoadImproved();

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
