package aero.nettracer.web.lfc.testing.actions.lfc.match;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.lfc.testing.LFC_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class LF_ManualMatch_FoundToLost extends LFC_SeleniumTest {

	@Test
	public void testAB_Login() throws Exception {
		click(By.id("confirmInput"));
		assertEquals("Id is required.", getAlert());
		type(By.id("foundInput"), Settings.LOST_ID_LF);
		click(By.id("confirmInput"), false, true);
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Match lost report Id: " + Settings.LOST_ID_LF));
			verifyTrue(isTextPresent("[Undo Confirmation]"));
		} else {
			System.err.println("Edit Lost Page Did Not Load After Manual Match. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
