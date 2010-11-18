package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class AdminEditUser extends DefaultSeleneseTestCase {
	@Test
	public void testAdminEditUser() throws Exception {
		selenium.click("menucol_8.5");
		selenium.waitForPageToLoad("30000");
		selenium.type("agentSearchName", "nttest");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=nttest");
		selenium.waitForPageToLoad("30000");
		selenium.type("mname", "ST");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.type("agentSearchName", "nttest");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=nttest");
		selenium.waitForPageToLoad("30000");
		verifyEquals("ST", selenium.getValue("mname"));
		selenium.click("menucol_8.5");
		selenium.waitForPageToLoad("30000");
		selenium.type("agentSearchName", "nttest");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=nttest");
		selenium.waitForPageToLoad("30000");
		selenium.type("mname", "");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
	}
}
