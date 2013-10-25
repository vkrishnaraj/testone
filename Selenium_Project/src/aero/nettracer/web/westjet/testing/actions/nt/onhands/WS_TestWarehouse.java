package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_TestWarehouse extends LoginUtil {

	@Test
	public void testWarehouseDates() throws Exception {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		selenium.click("name=skip_prepopulate");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
				verifyFalse(selenium.isTextPresent("Date Received by Warehouse"));

				verifyFalse(selenium.isTextPresent("Date Sent from Warehouse"));
		}
		else{
			System.err.println("Failed to enter create OHD page.");
			verifyTrue(false);
		}
		
		if(checkNoErrorPage())
		{	
			selenium.click("link=[ Logout ]");
			waitForPageToLoadImproved();
			loginOGAdminProcedure();
			clickMenu("menucol_9.2");
			waitForPageToLoadImproved();
			selenium.type("name=companySearchName", "WS");
			selenium.click("id=button");
			waitForPageToLoadImproved();
			selenium.click("xpath=(//a[contains(text(),'Maintain')])[13]");
			waitForPageToLoadImproved();
			selenium.click("xpath=(//a[contains(text(),'Maintain')])[20]");
			waitForPageToLoadImproved();
			selenium.click("name=704");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		}

		else{
			System.err.println("Failed to verifyFalse Warehouse Date Received Fields.");
			verifyTrue(false);
		}
		
		if(checkNoErrorPage())
		{

			selenium.click("link=[ Logout ]");
			waitForPageToLoadImproved();
			loginAdminProcedure();
			clickMenu("menucol_4.1");
			waitForPageToLoadImproved();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
					verifyTrue(selenium.isTextPresent("Date Received by Warehouse"));

					verifyTrue(selenium.isTextPresent("Date Sent from Warehouse"));
			}
			else{
				System.err.println("Update Permissions.");
				verifyTrue(false);
			}
		}
		
		if(checkNoErrorPage())
		{

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
				verifyTrue(selenium.isTextPresent("OHD has been submitted."));
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
		
		if(checkNoErrorPage())
		{	
			selenium.click("link=[ Logout ]");
			waitForPageToLoadImproved();
			loginOGAdminProcedure();
			clickMenu("menucol_9.2");
			waitForPageToLoadImproved();
			selenium.type("name=companySearchName", "WS");
			selenium.click("id=button");
			waitForPageToLoadImproved();
			selenium.click("xpath=(//a[contains(text(),'Maintain')])[13]");
			waitForPageToLoadImproved();
			selenium.click("xpath=(//a[contains(text(),'Maintain')])[20]");
			waitForPageToLoadImproved();
			selenium.click("name=704");
			
			selenium.click("name=save");
			waitForPageToLoadImproved();
			selenium.click("link=[ Logout ]");
			waitForPageToLoadImproved();
			loginAdminProcedure();
		}

		else{
			System.err.println("Failed to verifyFalse Warehouse Date Received Fields.");
			verifyTrue(false);
		}

	}
}