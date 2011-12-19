package aero.nettracer.web.avis.testing.actions.lf.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_SearchFound extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.click("id=menucol_1.3");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.select("name=type", "label=Found");
			selenium.type("name=id", Settings.FOUND_ID_AB);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Report Information"));
			} else {
				System.err.println("Found Item " + Settings.FOUND_ID_AB + " Did Not Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Search Page Did Not Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
