package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_ReceiveOHD extends WS_SeleniumTest {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		LoginUtil.setCbroStation(driver, "BGI");
		selenium.click("25link");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(@href, 'incomingBags.do?close=1&ohd_ID=" + Settings.ONHAND_ID_WS + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("OHD has been transfered to your BSO."));
				goToTaskManager();
				LoginUtil.setCbroStation(driver, LZ_STATION);
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
