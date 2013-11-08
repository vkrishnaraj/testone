package aero.nettracer.web.jetblue.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.jetblue.testing.B6_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_ReceiveOHD extends B6_SeleniumTest {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		LoginUtil.setCbroStation(driver, "BOS");
		selenium.click("25link");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(@href, 'incomingBags.do?close=1&ohd_ID=" + Settings.ONHAND_ID_B6 + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isTextPresent("BOH has been received by your station."));
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
