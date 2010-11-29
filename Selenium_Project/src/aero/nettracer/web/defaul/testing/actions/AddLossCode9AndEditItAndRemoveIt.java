package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class AddLossCode9AndEditItAndRemoveIt extends DefaultSeleneseTestCase {

	@Test
	public void testAddLossCode9AndEditItAndRemoveIt() throws Exception {
		selenium.open("/tracer/logoff.do");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.6");
		selenium.waitForPageToLoad("30000");
		selenium.type("codeSearchName", "9");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("addNew");
		selenium.waitForPageToLoad("30000");
		selenium.type("loss_code", "9");
		selenium.type("description", "Selenium Enter New Loss Code Test Case");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.6");
		selenium.waitForPageToLoad("30000");
		selenium.type("codeSearchName", "9");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=9");
		selenium.waitForPageToLoad("30000");
		selenium.type("description", "Selenium Edit Loss Code Test Case");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.6");
		selenium.waitForPageToLoad("30000");
		selenium.type("codeSearchName", "9");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=9");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Selenium Edit Loss Code Test Case", selenium.getValue("description"));
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.6");
		selenium.waitForPageToLoad("30000");
		selenium.type("codeSearchName", "9");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=9");
		selenium.waitForPageToLoad("30000");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("dCode");
		selenium.click("batch");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete the selected code\\(s\\)[\\s\\S]$"));
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
	}

}
