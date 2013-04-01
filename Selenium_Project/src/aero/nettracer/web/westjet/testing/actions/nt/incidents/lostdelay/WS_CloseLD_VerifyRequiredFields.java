package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CloseLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("PIR Details"));
			selenium.click("//td[@id='navmenucell']/div/dl/dd[13]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.chooseOkOnNextConfirmation();
				selenium.click("name=doclose");
				assertTrue(selenium.getConfirmation().matches("^Have you verified any Travel Bank Credit\\(s\\) for this file has been created[\\s\\S]$"));
				System.out.println(selenium.getConfirmation());				
				
				waitForPageToLoadImproved();
				verifyTrue(selenium.isTextPresent("Please select a reason for loss"));
				selenium.select("name=loss_code", "label=32- Off-loaded by error");
				selenium.chooseOkOnNextConfirmation();
				selenium.click("name=doclose");
				assertTrue(selenium.getConfirmation().matches("^Have you verified any Travel Bank Credit\\(s\\) for this file has been created[\\s\\S]$"));
				System.out.println(selenium.getConfirmation());				
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Delayed PIR has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
