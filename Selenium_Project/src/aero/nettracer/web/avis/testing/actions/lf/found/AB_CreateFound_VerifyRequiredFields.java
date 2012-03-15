package aero.nettracer.web.avis.testing.actions.lf.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_CreateFound_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_1.2");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("id=button");
//			assertEquals("Company is required.", selenium.getAlert());
//			selenium.select("name=found.companyId", "label=Avis");
//			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				String found_id = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
				Settings.FOUND_ID_AB = found_id;
				System.out.println("AB: Found Item Created: " + Settings.FOUND_ID_AB);
				verifyTrue(!Settings.FOUND_ID_AB.equals("0"));
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
