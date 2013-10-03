package aero.nettracer.web.lfc.testing.actions.lfc.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_ConfirmMatch extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		Thread.sleep(5000);
		goToTaskManager();
		selenium.click("id=608link");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Trace Results"));
			selenium.type("name=filter.lostId", Settings.LOST_ID_LF);
			selenium.type("name=filter.barcode", Settings.FOUND_ID_LF);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("Match Details"));
				verifyEquals(Settings.LOST_ID_LF, selenium.getText("//div[@id='maincontent']/table/tbody/tr[2]/td/a"));
				verifyEquals(Settings.FOUND_ID_LF, selenium.getText("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/a"));
//				verifyEquals("55.0", selenium.getText("//div[@id='maincontent']/table/tbody/tr[2]/td[3]"));
				selenium.click("id=menucol_0.6");
				waitForPageToLoadImproved();
				selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[7]/a[2]");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("id=menucol_2.4");
					waitForPageToLoadImproved();
//					selenium.select("name=type", "label=Lost");
					selenium.type("name=id", Settings.LOST_ID_LF);
					selenium.click("id=button");
					waitForPageToLoadImproved();
					verifyTrue(selenium.isTextPresent("Match found item Id: " + Settings.FOUND_ID_LF + "  [Undo Confirmation]"));
				} else {
					System.out.println("Trace Results Page Didn't Load After Confirmation. Error Page Loaded Instead.");
					verifyTrue(false);
				}
			} else {
				System.out.println("Details Page Didn't Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.out.println("Trace Results Page Didn't Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
