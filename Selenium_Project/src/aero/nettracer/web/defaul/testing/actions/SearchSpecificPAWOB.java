package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;
import aero.nettracer.web.defaul.testing.Settings;

public class SearchSpecificPAWOB extends DefaultSeleneseTestCase {

	@Test
	public void testSearchSpecificPAWOB() throws Exception {
		System.out.println("Starting Search...");
		selenium.click("//a[@id='menucol_0.0']/u");
		selenium.waitForPageToLoad("30000");
		selenium.click("menucol_1.4");
		selenium.waitForPageToLoad("30000");
		selenium.select("itemType_ID", "label=All");
		System.out.println("PAWOB = " + Settings.PAWOB);
		selenium.type("incident_ID", Settings.PAWOB);
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
	}
}
