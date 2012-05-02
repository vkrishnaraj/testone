package aero.nettracer.web.lfc.testing.actions.lfc.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_SearchFound extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.click("id=menucol_2.2");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
//			selenium.select("name=type", "label=Found");
			selenium.type("name=barcode", Settings.FOUND_ID_LF);
			selenium.type("name=agentName", Settings.USERNAME_ADMIN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Report Information"));
			} else {
				System.err.println("Found Item " + Settings.FOUND_ID_LF + " Did Not Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Search Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
