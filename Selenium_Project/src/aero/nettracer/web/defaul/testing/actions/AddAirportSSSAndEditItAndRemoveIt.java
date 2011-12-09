package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class AddAirportSSSAndEditItAndRemoveIt extends DefaultSeleneseTestCase {

	@Test
	public void testAddAirportSSSAndEditItAndRemoveIt() throws Exception {
		selenium.open("/tracer/logoff.do");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.10");
		selenium.waitForPageToLoad("30000");
		selenium.type("airportSearchName", "SSS");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("addNew");
		selenium.waitForPageToLoad("30000");
		selenium.type("airport_code", "SSS");
		selenium.type("airport_description", "Selenium Enter New Airport Test Case");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.10");
		selenium.waitForPageToLoad("30000");
		selenium.type("airportSearchName", "SSS");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=SSS");
		selenium.waitForPageToLoad("30000");
		selenium.type("airport_description", "Selenium Edit Airport Test Case");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.10");
		selenium.waitForPageToLoad("30000");
		selenium.type("airportSearchName", "SSS");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Selenium Edit Airport Test Case"));
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.10");
		selenium.waitForPageToLoad("30000");
		selenium.type("airportSearchName", "SSS");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Selenium Edit Airport Test Case"));
		selenium.click("a");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete the selected code\\(s\\)SSS[\\s\\S]$"));
		selenium.click("menucol_8.10");
		selenium.waitForPageToLoad("30000");
		selenium.type("airportSearchName", "SSS");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
	}

}
