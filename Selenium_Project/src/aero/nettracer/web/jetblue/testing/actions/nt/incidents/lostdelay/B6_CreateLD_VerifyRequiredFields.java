package aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class B6_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
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
				assertEquals("State is required if country is set to 'United States'", selenium.getAlert());
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.click("name=savetracingButton");
				assertEquals("Zip is required.", selenium.getAlert());
				selenium.type("name=addresses[0].zip", "30152");
				selenium.click("name=savetracingButton");
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].legfrom", "LAX");
				selenium.type("name=theitinerary[0].legto", "ATL");
				selenium.click("name=savetracingButton");
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
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
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].flightnum", "123");
				selenium.click("name=savetracingButton");
				assertEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=calendar31");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				assertEquals("Bag Tag Number is required.", selenium.getAlert());
				selenium.type("name=claimcheck[0].claimchecknum", "B6123456");
				selenium.click("name=savetracingButton");
				assertEquals("Color is required.", selenium.getAlert());
				selenium.select("name=theitem[0].color", "label=BK - Black");
				selenium.click("name=savetracingButton");
				assertEquals("Type is required.", selenium.getAlert());
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.click("name=savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Report has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_B6 = incident_id;
					System.out.println("B6: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_B6);
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
		verifyEquals("1500", selenium.getValue("//input[@id='remark[0].counter']"));
		driver.findElement(By.id("remark[0]")).sendKeys("Test line" + Keys.ENTER + Keys.ENTER + Keys.ENTER);
		verifyEquals("1485", selenium.getValue("//input[@id='remark[0].counter']"));
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		waitForPageToLoadImproved();

	}

}
