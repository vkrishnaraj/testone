package aero.nettracer.web.spirit.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class NK_CreateOHD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=savetracing");
			assertEquals("Color is required.", selenium.getAlert());
			selenium.select("name=bagColor", "label=BK - Black");
			selenium.click("name=savetracing");
			assertEquals("Type is required.", selenium.getAlert());
			selenium.select("name=bagType", "label=22");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("On-hand incident(s) have been submitted."));
				String onhand_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.ONHAND_ID_NK = onhand_id;
				System.out.println("NK: Onhand Created: " + Settings.ONHAND_ID_NK);
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
			} else {
				System.err.println("Create Onhand Success Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Create Onhand Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
