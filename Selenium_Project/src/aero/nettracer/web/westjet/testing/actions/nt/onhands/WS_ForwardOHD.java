package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WS_ForwardOHD extends DefaultSeleneseTestCase {

	@Test
	public void testAD_CreateOHD_VerifyRequiredFields() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//div[@id='pageheaderright']/a/b");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.select("name=destStation", "label=BGI");
				selenium.click("name=save");
				assertEquals("Expedite Tag Number is required.", selenium.getAlert());
				selenium.type("name=ohdList[0].value", "WS123456");
				selenium.click("name=save");
				assertEquals("From is required.", selenium.getAlert());
				selenium.type("name=itinerarylist[0].legfrom", "ATL");
				selenium.click("name=save");
				assertEquals("To is required.", selenium.getAlert());
				selenium.type("name=itinerarylist[0].legto", "LAX");
				selenium.click("name=save");
				assertEquals("Flight Number is required.", selenium.getAlert());
				selenium.type("name=itinerarylist[0].flightnum", "123");
				selenium.click("name=save");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(isTextPresent("OHD Baggage has been forwarded."));
				} else {
					System.err.println("Forward Onhand Success Page Failed To Load. Error Page Loaded Instead.");
					verifyTrue(false);
				}
			} else {
				System.err.println("Forward Onhand Page Failed To Load. Error Page Loaded Instead.");
				verifyTrue(false);
			}
		} else {
			System.err.println("Edit Onhand Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
	}
}
