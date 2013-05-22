package aero.nettracer.web.lfc.testing.actions.lfc.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_ManualMatch_FoundToLost extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.click("link=Confirm Match");
		assertEquals("ID is required.", selenium.getAlert());
		selenium.type("id=foundInput", Settings.LOST_ID_LF);
		selenium.click("id=confirmInput");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Match lost report Id: " + Settings.LOST_ID_LF));
			verifyTrue(selenium.isTextPresent("[Undo Confirmation]"));
		} else {
			System.err.println("Edit Lost Page Did Not Load After Manual Match. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
