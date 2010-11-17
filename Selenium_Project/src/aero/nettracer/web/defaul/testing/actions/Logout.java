package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class Logout extends DefaultSeleneseTestCase {
	
	@Test
	public void testLogout() throws Exception {
		//FIRST CAPTURE
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		//COMPARE
		verifyTrue(selenium.isTextPresent("Company : 	\r\nUsername : 	\r\nPassword :"));
	}
}
