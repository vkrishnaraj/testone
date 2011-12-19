package aero.nettracer.web.avis.testing.actions.lf.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class AB_CloseFound extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.select("name=found.statusId", "label=Closed");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			System.out.println("VERIFYING CLOSE STATUS: " + selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[4]/select"));
			verifyEquals("Close", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[4]/select"));
		} else {
			System.err.println("Edit Found Page Did Not Reload After Closing. Error Page Loaded Instead.");
			verifyTrue(false);
		}
		
	}
}
