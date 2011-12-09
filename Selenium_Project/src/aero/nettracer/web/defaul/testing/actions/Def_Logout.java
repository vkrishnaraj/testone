package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class Def_Logout extends DefaultSeleneseTestCase {
	
	@Test
	public void testLogout() throws Exception {
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Log In"));
	}
}
