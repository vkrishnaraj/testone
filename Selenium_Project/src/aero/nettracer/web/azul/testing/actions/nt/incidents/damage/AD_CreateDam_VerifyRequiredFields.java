package aero.nettracer.web.azul.testing.actions.nt.incidents.damage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AD_CreateDam_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_2.1");
		selenium.waitForPageToLoad("30000");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=saveButton");
			assertEquals("Record Locator is required.", selenium.getAlert());
			selenium.type("name=recordlocator", "TESTER");
			selenium.click("name=saveButton");
			assertEquals("Last Name is required.", selenium.getAlert());
			selenium.type("name=passenger[0].lastname", "Test");
			selenium.click("name=saveButton");
			assertEquals("First Name is required.", selenium.getAlert());
			selenium.type("name=passenger[0].firstname", "Test");
			selenium.click("name=saveButton");
			assertEquals("Street Address is required.", selenium.getAlert());
			selenium.type("name=addresses[0].address1", "123 Test");
			selenium.click("name=saveButton");
			assertEquals("City is required.", selenium.getAlert());
			selenium.type("name=addresses[0].city", "Test");
			selenium.click("name=saveButton");
			assertEquals("From/To is required.", selenium.getAlert());
			selenium.type("name=theitinerary[0].legfrom", "ATL");
			selenium.type("name=theitinerary[0].legto", "LAX");
			selenium.click("name=saveButton");
			assertEquals("Airline/Flight Number is required.", selenium.getAlert());
			selenium.type("name=theitinerary[0].flightnum", "123");
			selenium.click("name=saveButton");
			assertEquals("Depart Date is required.", selenium.getAlert());
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");
			selenium.click("name=saveButton");
			assertEquals("From/To is required.", selenium.getAlert());
			selenium.type("name=theitinerary[1].legfrom", "ATL");
			selenium.type("name=theitinerary[1].legto", "LAX");
			selenium.click("name=saveButton");
			assertEquals("Airline/Flight Number is required.", selenium.getAlert());
			selenium.type("name=theitinerary[1].flightnum", "123");
			selenium.click("name=saveButton");
			assertEquals("Depart Date is required.", selenium.getAlert());
			selenium.click("id=calendar31");
			selenium.click("link=Today");
			selenium.click("name=saveButton");
			assertEquals("Color is required.", selenium.getAlert());
			selenium.select("name=theitem[0].color", "label=BK - Black");
			selenium.click("name=saveButton");
			assertEquals("Type is required.", selenium.getAlert());
			selenium.select("name=theitem[0].bagtype", "label=22");
			selenium.click("name=saveButton");
			assertEquals("Content Category is required.", selenium.getAlert());
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Art");
			selenium.click("name=saveButton");
			assertEquals("Description is required.", selenium.getAlert());
			selenium.type("name=inventorylist[0].description", "Test");
			selenium.click("name=saveButton");
			assertEquals("Damage Description is required.", selenium.getAlert());
			selenium.type("name=theitem[0].damage", "Test");
			selenium.click("name=saveButton");
			assertEquals("Phone is required.", selenium.getAlert());
			selenium.type("name=addresses[0].mobile", "(555) 555-4444");
			selenium.click("name=saveButton");
			selenium.waitForPageToLoad("30000");
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Damaged Bag Incident has been submitted."));
				checkCopyrightAndQuestionMarks();
				String damage_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.DAMAGE_ID = damage_id;
				System.out.println("AD: Damaged Incident Created: " + Settings.DAMAGE_ID);
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				selenium.waitForPageToLoad("30000");
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Create Damaged Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Damaged Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
