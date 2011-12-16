package aero.nettracer.web.spirit.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class NK_ReceiveOHD extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		selenium.select("name=cbroStation", "label=BOS");
		waitForPageToLoadImproved();
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[13]/td/a");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(@href, 'incomingBags.do?close=1&ohd_ID=" + Settings.ONHAND_ID_NK + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("On-hand incident has been transfered to your station."));
				goToTaskManager();
				selenium.select("name=cbroStation", "label=CLAIM");
				waitForPageToLoadImproved();
			} else {
				System.err.println("Receive Onhand Success Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Receive Onhand Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
