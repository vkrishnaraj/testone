package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class PasswordReset4nttest extends DefaultSeleneseTestCase {
	
	@Test
	public void testPasswordReset4nttest() throws Exception {
		selenium.click("menucol_8.5");
		selenium.waitForPageToLoad("30000");
		selenium.type("agentSearchName", "nttest");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=nttest");
		selenium.waitForPageToLoad("30000");
		selenium.type("password", "pass");
		selenium.type("password2", "pass");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "nttest");
		selenium.type("password", "pass");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.type("password1", "passsword");
		selenium.type("password2", "password");
		selenium.type("password1", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "nttest");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
	}
}
