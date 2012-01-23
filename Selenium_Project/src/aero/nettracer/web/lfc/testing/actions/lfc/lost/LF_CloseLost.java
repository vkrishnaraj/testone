package aero.nettracer.web.lfc.testing.actions.lfc.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_CloseLost extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.select("name=lost.statusId", "label=Closed");
		selenium.click("saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			System.out.println("VERIFYING CLOSE AGENT: " + selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td[5]/input"));
			verifyEquals("ntauto", selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td[5]/input"));
		} else {
			System.err.println("Edit Lost Page Did Not Load After Close. Error Page Loaded Instead.");
			verifyTrue(false);
		}
		
	}
}
