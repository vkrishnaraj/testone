package aero.nettracer.web.jetblue.testing.actions.nt.incidents.pilferage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class B6_ClosePilf_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Pawob Information"));
			selenium.click("//td[@id='navmenucell']/div/dl/dd[9]/a/span[2]");
			selenium.waitForPageToLoad("30000");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=doclose");
				selenium.waitForPageToLoad("30000");
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Pilferage Pawob has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					selenium.waitForPageToLoad("30000");
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Close Pilferage Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Pilferage Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Pilferage Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
