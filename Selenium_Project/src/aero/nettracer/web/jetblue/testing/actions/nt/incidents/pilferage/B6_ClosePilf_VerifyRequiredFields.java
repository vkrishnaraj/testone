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
			selenium.click("//td[@id='navmenucell']/div/dl/dd[6]/a/span[2]");
			selenium.waitForPageToLoad("30000");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=doclose");
				assertEquals("Arrival Flight is required.", selenium.getAlert());
				selenium.type("name=theitem[0].arrivedonflightnum", "123");
				selenium.click("name=doclose");
				assertEquals("Arrival Date is required.", selenium.getAlert());
				selenium.click("id=calendar20");
				selenium.click("link=Today");
				selenium.click("name=doclose");
				selenium.waitForPageToLoad("30000");
				verifyTrue(selenium.isTextPresent("Please select a fault airline and station"));
				selenium.select("name=faultstation_id", "label=CBS");
				selenium.click("name=doclose");
				selenium.waitForPageToLoad("30000");
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Pawob has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					selenium.waitForPageToLoad("30000");
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
