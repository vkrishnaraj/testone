package aero.nettracer.web.spirit.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class NK_ForwardOHD extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//div[@id='pageheaderright']/a/b");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.select("name=destStation", "label=BOS");
				selenium.click("name=save");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isTextPresent("On-hand Baggage has been forwarded."));
				} else {
					System.err.println("Forward Onhand Success Page Failed To Load. Error Page Loaded Instead.");
					verifyTrue(false);
				}
			} else {
				System.err.println("Forward Onhand Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Edit Onhand Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
