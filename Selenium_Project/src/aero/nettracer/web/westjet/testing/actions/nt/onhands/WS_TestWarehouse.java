package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_TestWarehouse extends WS_SeleniumTest {

	@Test
	public void testWarehouseDates() throws Exception {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		selenium.click("name=skip_prepopulate");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
				verifyFalse(isTextPresent("Date Received by Warehouse"));

				verifyFalse(isTextPresent("Date Sent from Warehouse"));
		} else {
			System.err.println("Failed to enter create OHD page.");
			verifyTrue(false);
		}
		
		if(checkNoErrorPage()) {	
			setPermissions(new String[] {"704"}, new boolean[] {true});
		} else {
			System.err.println("Failed to verifyFalse Warehouse Date Received Fields.");
			verifyTrue(false);
		}
		
		if(checkNoErrorPage()) {
			clickMenu("menucol_4.1");
			waitForPageToLoadImproved();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
					verifyTrue(isTextPresent("Date Received by Warehouse"));

					verifyTrue(isTextPresent("Date Sent from Warehouse"));
			} else {
				System.err.println("Update Permissions.");
				verifyTrue(false);
			}
		}
		
		if(checkNoErrorPage()) {

			selenium.select("name=bagColor", "label=WT - White/clear");
			selenium.select("name=bagType", "label=01");
			selenium.click("id=calendar2");
			selenium.click("link=Today");
			selenium.click("id=calendar3");
			selenium.click("link=Today");
			selenium.type("name=itinerarylist[0].legfrom", "ATL");
			selenium.type("name=itinerarylist[0].legto", "LAS");
			selenium.type("name=itinerarylist[0].flightnum", "1234");
			selenium.click("id=calendar20");
			selenium.click("link=Today");
			selenium.click("id=calendar30");
			selenium.click("link=Today");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isTextPresent("OHD has been submitted."));
				String onhand_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.ONHAND_ID_WS = onhand_id;
				System.out.println("WS: Onhand Created: " + Settings.ONHAND_ID_WS);
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
			} else {
				System.err.println("Create Onhand Success Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		}
		
		if(checkNoErrorPage()) {	
			setPermissions(new String[] {"704"}, new boolean[] {false});
		} else {
			System.err.println("Failed to verifyFalse Warehouse Date Received Fields.");
			verifyTrue(false);
		}

	}
}