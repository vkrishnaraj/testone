package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class AddStationSELAndEditItAndRemoveIt extends DefaultSeleneseTestCase {

	@Test
	public void testAddStationSELAndEditItAndRemoveIt() throws Exception {
		selenium.open("/tracer/logoff.do");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.4");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationSearchName", "SEL");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("addNew");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationCode", "SEL");
		selenium.type("stationDesc", "Selenium");
		selenium.type("addr1", "123 Summerville");
		selenium.type("city", "Springtown");
		selenium.select("state_ID", "label=Georgia");
		selenium.select("countrycode_ID", "label=United States");
		selenium.type("zip", "32101");
		selenium.type("phone", "000-000-0000");
		selenium.select("associated_airport", "label=SMR");
		selenium.select("station_region", "label=SOUTHERN REGION");
		selenium.type("station_region_mgr", "RM002");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.4");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationSearchName", "SEL");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=SEL");
		selenium.waitForPageToLoad("30000");
		selenium.type("phone", "987-654-3210");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.4");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationSearchName", "SEL");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=SEL");
		selenium.waitForPageToLoad("30000");
		verifyEquals("987-654-3210", selenium.getValue("phone"));
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
		selenium.type("username", "ntadmin");
		selenium.type("password", "password");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.4");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationSearchName", "SEL");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("a");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete the selected code\\(s\\)SEL[\\s\\S]$"));
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_8.4");
		selenium.waitForPageToLoad("30000");
		selenium.type("stationSearchName", "SEL");
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=[ Logout ]");
		selenium.waitForPageToLoad("30000");
	}

}
