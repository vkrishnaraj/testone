package aero.nettracer.web.lfc.testing.actions.lfc.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateDelivery extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_0.5");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			System.out.println("Setting Manual Match To Picked Up.");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[3]");
			waitForPageToLoadImproved();
			System.out.println("Setting Confirmed Match To Delivery.");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("name=foundItem.trackingNumber", "123123123123");
				selenium.click("name=save");
				waitForPageToLoadImproved();
				selenium.click("id=menucol_2.3");
				waitForPageToLoadImproved();
				selenium.type("name=id", Settings.LOST_ID_LF);
				selenium.select("name=type", "label=Lost");
				selenium.click("id=button");
				waitForPageToLoadImproved();
				verifyEquals("123123123123", selenium.getValue("name=foundItem.trackingNumber"));
			} else {
				System.out.println("Delivery Info Page Didn't Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.out.println("Needs Delivery Page Didn't Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
