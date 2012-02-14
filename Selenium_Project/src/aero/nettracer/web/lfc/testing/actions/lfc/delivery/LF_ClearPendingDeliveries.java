package aero.nettracer.web.lfc.testing.actions.lfc.delivery;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

/*
 * This is a utility class to clear out all of the items pending
 * delivery that were created as part of testing that occurred 
 * previously. This test should always be ran immediately prior 
 * to LF_CreateDelivery and LF_CreateDeliveryFromFound.
 */
public class LF_ClearPendingDeliveries extends LoginUtil {

	@Test
	public void testClearPendingDeliveries() {
		selenium.click("//ul[@id='menubuilder0']/li[5]/a");
		waitForPageToLoadImproved();
		int i = 1;
		if (checkNoErrorPage()) {
			System.out.println("CPD: Clearing pending deliveries...");
			while (selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]")) {
				selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]");
				waitForPageToLoadImproved();
				if (!checkNoErrorPage()) {
					System.out.println("CPD: An error occurred while rejecting the previous delivery.");
					verifyTrue(false);
					break;
				}
				System.out.println("CPD: Cleared pending delivery: " + i);
				++i;
			}
		} else {
			System.out.println("CPD: Failed to load the Items to Deliver page.");
			verifyTrue(false);
		}
		
		goToTaskManager();
	}
	
}
