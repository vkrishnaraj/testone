package aero.nettracer.web.avis.testing.actions.lf.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_ManualMatch_FoundToLost extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
//		selenium.click("link=Confirm Match");
		selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td[3]/a");
		assertEquals("ID is required.", selenium.getAlert());
		selenium.type("id=foundInput", Settings.LOST_ID_AB);
		selenium.click("link=Confirm Match");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Match lost report Id: " + Settings.LOST_ID_AB));
			verifyTrue(selenium.isTextPresent("[Undo Confirmation]"));
		} else {
			System.err.println("Edit Lost Page Did Not Load After Manual Match. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
