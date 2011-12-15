package aero.nettracer.web.defaul.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class Def_PasswordResetNTTest extends DefaultSeleneseTestCase {
	
	@Test
	public void testPasswordReset4nttest() throws Exception {
		resetPassProcedure(Settings.PASSWORD_CHANGE);
		resetPassProcedure(Settings.PASSWORD_TEST);
	}
	
	private void resetPassProcedure(String pass) {
		selenium.click("//a[contains(@href, 'agentAdmin.do?self_edit=1')]");
		waitForPageToLoadImproved();
		selenium.type("password", pass);
		selenium.type("password2", pass);
		selenium.click("save");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("has been updated."));
	}
}
