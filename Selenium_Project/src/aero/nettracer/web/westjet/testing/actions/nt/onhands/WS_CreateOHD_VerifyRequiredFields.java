package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_CreateOHD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		goToTaskManager();
		selenium.click("id=menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=savetracing");
				assertEquals("Colour is required.", selenium.getAlert());
				selenium.select("name=bagColor", "label=BK - Black");
				selenium.click("name=savetracing");
				assertEquals("Type is required.", selenium.getAlert());
				selenium.select("name=bagType", "label=22");
				selenium.click("name=savetracing");
				assertEquals("From/To is required.", selenium.getAlert());
				selenium.type("name=itinerarylist[0].legfrom", "ATL");
				selenium.type("name=itinerarylist[0].legto", "LAX");
				selenium.click("name=savetracing");
				assertEquals("Airline/Flight Number is required.", selenium.getAlert());
				selenium.type("name=itinerarylist[0].flightnum", "123");
				selenium.click("name=savetracing");
				assertEquals("Departure Date is required.", selenium.getAlert());
				selenium.click("id=calendar20");
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
			} else {
				System.err.println("Create Onhand Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
}
