package aero.nettracer.web.lfc.testing.actions.lfc.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateFound_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("saveButton");
			assertEquals("Report Id is required.", selenium.getAlert());
			selenium.type("name=found.barcode", "Test");
			selenium.click("saveButton");
			assertEquals("Report Id is not a valid number.", selenium.getAlert());
			String found_id = String.valueOf(System.currentTimeMillis());
			selenium.type("name=found.barcode", found_id);
			selenium.click("saveButton");
			assertEquals("Date Received is required.", selenium.getAlert());
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("name=saveButton");
			assertEquals("Company is required.", selenium.getAlert());
			selenium.select("name=found.companyId", "label=Southwest");
			selenium.click("name=saveButton");
			assertEquals("Found Station is required.", selenium.getAlert());
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("name=saveButton");
			assertEquals("Category is required.", selenium.getAlert());
			selenium.select("//select[@id='category_0']", "label=Bags");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isTextPresent("Your found item was successfully saved."));
				found_id = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
				Settings.FOUND_ID_LF = found_id;
				System.out.println("LF: Found Item Created: " + Settings.FOUND_ID_LF);
				verifyTrue(!Settings.FOUND_ID_LF.equals("0"));
			} else {
				System.err.println("Edit Found Page Did Not Load After Report Creation. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Create Found Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
