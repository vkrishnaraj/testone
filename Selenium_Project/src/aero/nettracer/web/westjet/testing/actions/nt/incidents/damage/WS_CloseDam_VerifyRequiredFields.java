package aero.nettracer.web.westjet.testing.actions.nt.incidents.damage;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CloseDam_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("DPR Details"));
			selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.chooseOkOnNextConfirmation();
				selenium.click("name=doclose");
				assertTrue(selenium.getConfirmation().matches("^Have you verified any Travel Bank Credit\\(s\\) for this file has been created[\\s\\S]$"));
				System.out.println(selenium.getConfirmation());				
				
				waitForPageToLoadImproved();
				verifyTrue(selenium.isTextPresent("Please select a reason for loss"));
				selenium.select("name=loss_code", "label=80- Damage");
				selenium.chooseOkOnNextConfirmation();
				selenium.click("name=doclose");
				assertTrue(selenium.getConfirmation().matches("^Have you verified any Travel Bank Credit\\(s\\) for this file has been created[\\s\\S]$"));
				System.out.println(selenium.getConfirmation());				
				
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Damaged Baggage Report has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Close Damage Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Damage Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Damage Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}

}
