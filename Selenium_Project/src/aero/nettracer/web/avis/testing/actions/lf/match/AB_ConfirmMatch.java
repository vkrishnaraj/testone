package aero.nettracer.web.avis.testing.actions.lf.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class AB_ConfirmMatch extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		Thread.sleep(5000);
		goToTaskManager();
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[3]/td/a");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=filter.lostId", Settings.LOST_ID_AB);
			selenium.type("name=filter.foundId", Settings.FOUND_ID_AB);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isTextPresent("Lost Report Id 	Found Item Id 	Score \n" +
						Settings.LOST_ID_AB + " 	" + Settings.FOUND_ID_AB + " 	55.0"));
				selenium.click("id=button");
				waitForPageToLoadImproved();
				selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[7]/a[2]");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("id=menucol_1.3");
					waitForPageToLoadImproved();
					selenium.select("name=type", "label=Lost");
					selenium.type("name=id", Settings.LOST_ID_AB);
					selenium.click("id=button");
					waitForPageToLoadImproved();
					verifyTrue(selenium.isTextPresent("Match found item Id: " + Settings.FOUND_ID_AB + "  [Undo Confirmation]"));
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
