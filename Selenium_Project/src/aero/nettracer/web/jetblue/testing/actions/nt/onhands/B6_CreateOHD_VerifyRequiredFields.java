package aero.nettracer.web.jetblue.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class B6_CreateOHD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=savetracing");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isTextPresent("BOH(s) have been submitted."));
					String onhand_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.ONHAND_ID_B6 = onhand_id;
					System.out.println("B6: Onhand Created: " + Settings.ONHAND_ID_B6);
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.err.println("Create Onhand Success Page Failed To Load. Error Page Loaded Instead.");
					verifyTrue(false);
				}
			} else {
				System.err.println("Create Onhand Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Prepopulate Onhand Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testAD_CreateOHD_VerifyBagHop() throws Exception {
		if (checkNoErrorPage()) {
			selenium.select("name=disposal_status.status_ID", "label=BagHop Pickup");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Failed to save change to BagHop Pickup");
		}
		if (checkNoErrorPage()) {
			selenium.click("link="+Settings.ONHAND_ID_B6);
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Reload On Hand");
		}
		if(checkNoErrorPage()){
			selenium.select("name=status.status_ID", "label=Closed");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Failed to load Onhand");
		}
		if (checkNoErrorPage()) {
			selenium.click("link="+Settings.ONHAND_ID_B6);
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Failed to close Onhand");
		}
		if (checkNoErrorPage()) {
			verifyEquals("98", selenium.getValue("name=disposal_status.status_ID"));
		} else {
			System.out.println("Error: Failed to load Onhand");
		}
		if(checkNoErrorPage()){
			selenium.select("name=status.status_ID", "label=Open");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Failed to load Onhand");
		}
		if (checkNoErrorPage()) {
			selenium.click("link="+Settings.ONHAND_ID_B6);
			waitForPageToLoadImproved();
		} else {
			System.out.println("Error: Failed to close Onhand");
		}
	}
}
