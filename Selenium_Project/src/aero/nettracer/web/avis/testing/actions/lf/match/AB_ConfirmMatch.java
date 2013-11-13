package aero.nettracer.web.avis.testing.actions.lf.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_ConfirmMatch extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		Thread.sleep(5000);
		goToTaskManager();
		selenium.click("id=608link");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Trace Results"));
			selenium.type("name=filter.lostId", Settings.LOST_ID_AB);
			selenium.type("name=filter.foundId", Settings.FOUND_ID_AB);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isTextPresent("Match Details"));
				verifyEquals(Settings.LOST_ID_AB, selenium.getText("//div[@id='maincontent']/table/tbody/tr[2]/td/a"));
				verifyEquals(Settings.FOUND_ID_AB, selenium.getText("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/a"));
				clickMenu("menucol_0.0");
				waitForPageToLoadImproved();
				selenium.click("id=608link");
				waitForPageToLoadImproved();
				selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[7]/a[2]");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					clickMenu("menucol_1.3");
					waitForPageToLoadImproved();
					selenium.select("name=type", "label=Lost");
					selenium.type("name=id", Settings.LOST_ID_AB);
					selenium.click("id=button");
					waitForPageToLoadImproved();
					verifyTrue(isTextPresent("Match found item Id: " + Settings.FOUND_ID_AB + "  [Undo Confirmation]"));
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
