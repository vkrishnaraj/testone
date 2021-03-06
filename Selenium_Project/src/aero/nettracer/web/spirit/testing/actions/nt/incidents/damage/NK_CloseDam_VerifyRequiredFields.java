package aero.nettracer.web.spirit.testing.actions.nt.incidents.damage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class NK_CloseDam_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Incident Information"));
			selenium.click("//td[@id='navmenucell']/div/dl/dd[9]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=doclose");
				waitForPageToLoadImproved();
				verifyTrue(isTextPresent("Please select a reason for loss"));
				selenium.select("name=loss_code", "label=22- Bag received too late from check-in actions");
				selenium.click("name=doclose");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isTextPresent("Damaged Baggage Report has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Close Damaged Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Damaged Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Damaged Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
