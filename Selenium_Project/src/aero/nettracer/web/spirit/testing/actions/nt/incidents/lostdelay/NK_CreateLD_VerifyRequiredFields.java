package aero.nettracer.web.spirit.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class NK_CreateLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
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
				assertEquals("State is required if country is set to 'United States'", selenium.getAlert());
				selenium.select("name=addresses[0].state_ID", "label=Georgia");
				selenium.click("name=savetracingButton");
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].legfrom", "ATL");
				selenium.type("name=theitinerary[0].legto", "LAX");
				selenium.click("name=savetracingButton");
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
				selenium.type("name=theitinerary[0].flightnum", "123");
				selenium.click("name=savetracingButton");
				assertEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=itcalendar0");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].legfrom", "ATL");
				selenium.type("name=theitinerary[1].legto", "LAX");
				selenium.click("name=savetracingButton");
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
				selenium.type("name=theitinerary[1].flightnum", "123");
				selenium.click("name=savetracingButton");
				assertEquals("Depart Date is required.", selenium.getAlert());
				selenium.click("id=calendar31");
				selenium.click("link=Today");
				selenium.click("name=savetracingButton");
				assertEquals("Bags Checked Location is required.", selenium.getAlert());
				selenium.select("name=checkedlocation", "label=Ticket Counter");
				selenium.click("name=savetracingButton");
				assertEquals("Type is required.", selenium.getAlert());
				selenium.select("name=theitem[0].bagtype", "label=22");
				selenium.click("name=savetracingButton");
				assertEquals("Content Category is required.", selenium.getAlert());
				selenium.select("name=inventorylist[0].categorytype_ID", "label=Art");
				selenium.click("name=savetracingButton");
				assertEquals("Description is required.", selenium.getAlert());
				selenium.type("name=inventorylist[0].description", "TEST");
//				selenium.type("name=inventorylist[1].description", "TEST");
//				selenium.type("name=inventorylist[2].description", "TEST");
//				selenium.select("name=inventorylist[1].categorytype_ID", "label=Art");
//				selenium.select("name=inventorylist[2].categorytype_ID", "label=Art");
				selenium.click("name=savetracingButton");
				assertEquals("Mobile Phone is required.", selenium.getAlert());
				selenium.type("name=addresses[0].mobile", "(555) 555-4444");
				selenium.click("savetracingButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been submitted."));
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_NK = incident_id;
					System.out.println("NK: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_NK);
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
