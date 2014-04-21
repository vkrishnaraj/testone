package aero.nettracer.web.lfc.testing.actions.lfc.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateFound_Detailed extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			String found_id = String.valueOf(System.currentTimeMillis());
			selenium.select("name=found.companyId", "label=Southwest");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.type("name=found.barcode", found_id);
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.type("name=item[0].brand", "Test Brand");
			selenium.type("name=item[0].serialNumber", "123321456");
			selenium.select("id=category_0", "label=Bags");
			selenium.select("name=item[0].color", "label=Black");
			selenium.type("name=item[0].description", "Test Description Test Description Test Description Test Description Test Description Test Description Test DescriptionTest Description Test Description Test Description Test Description Test Description Test Description Test Description 241 245");
			selenium.fireEvent("name=item[0].description", "blur");
			selenium.click("saveButton");
			waitForPageToLoadImproved(3000);//since trace results are async, wait a second
			selenium.click("saveButton");//resubmitting to reload the page hopefully with trace results
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
