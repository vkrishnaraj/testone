package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class IntoFirstPAWOB extends DefaultSeleneseTestCase {
	
	@Test
	public void testInto_First_PAWOB() throws Exception {
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[2]/td[1]/a");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[2]/a");
		selenium.waitForPageToLoad("30000");
		String pawob = selenium.getValue("incident_ID");
		System.out.println("Running Tests on PAWOB: " + pawob);
	}
}
