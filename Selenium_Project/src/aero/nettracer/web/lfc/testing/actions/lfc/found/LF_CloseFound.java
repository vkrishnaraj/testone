package aero.nettracer.web.lfc.testing.actions.lfc.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_CloseFound extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		selenium.select("name=found.statusId", "label=Closed");
		selenium.click("saveButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			System.out.println("VERIFYING CLOSE STATUS: " + selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
		} else {
			System.err.println("Edit Found Page Did Not Reload After Closing. Error Page Loaded Instead.");
			verifyTrue(false);
		}
		
	}
}
