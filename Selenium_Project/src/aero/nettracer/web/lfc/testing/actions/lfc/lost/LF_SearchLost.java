package aero.nettracer.web.lfc.testing.actions.lfc.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_SearchLost extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.click("id=menucol_2.3");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.select("name=type", "label=Lost");
			selenium.type("name=id", Settings.LOST_ID_LF);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Report Information"));
			} else {
				System.err.println("Lost ID " + Settings.LOST_ID_LF + " Did Not Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Search Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
