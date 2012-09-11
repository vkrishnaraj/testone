package aero.nettracer.web.westjet.testing.actions.nt.lostfound;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_LostfoundTest extends LoginUtil {
	@Test
	public void testFirstLastCategory() throws Exception {
		selenium.click("id=menucol_5.2");
		waitForPageToLoadImproved();
		selenium.type("name=customer_firstname", "TestFirst");
		selenium.type("name=customer_lastname", "TestLast");
		selenium.select("name=category_id", "label=Book");
		selenium.click("name=save");
		waitForPageToLoadImproved();
		String damage_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		Settings.FOUND_ID_WS = damage_id;
		System.out.println("WS: Found Item Created: " + Settings.FOUND_ID_WS);
		selenium.click("id=menucol_5.3");
		waitForPageToLoadImproved();
		selenium.type("name=lastname", "TestLast");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent(Settings.FOUND_ID_WS));
		selenium.type("name=lastname", "");
		selenium.type("name=firstname", "TestFirst");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent(Settings.FOUND_ID_WS));
		selenium.type("name=firstname", "");
		selenium.select("name=category", "label=Book");
		selenium.click("id=button");
		verifyTrue(selenium.isTextPresent(Settings.FOUND_ID_WS));
	}
}