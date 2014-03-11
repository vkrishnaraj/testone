package aero.nettracer.web.azul.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.azul.testing.AD_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class AD_ReceiveOHD extends AD_SeleniumTest {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		LoginUtil.setCbroStation(driver, "BEL");
		selenium.click("25link");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(@href, 'incomingBags.do?close=1&ohd_ID=" + Settings.ONHAND_ID_AD + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isTextPresent("On-hand bag has been transferred to your station."));
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
