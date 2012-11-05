package aero.nettracer.web.westjet.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_ForwardMessage extends DefaultSeleneseTestCase {

	@Test
	public void testForwardMassage() throws Exception {
		selenium.click("id=menucol_4.5");
		waitForPageToLoadImproved();
		selenium.select("name=addBagNum", "label=3");
		selenium.click("name=addBags");
		waitForPageToLoadImproved();
		selenium.click("xpath=(//input[@id='button'])[3]");
		selenium.type("name=tagNumber[0].bagTagNumber", "12345678");
		selenium.type("name=tagNumber[1].bagTagNumber", "87654321");
		selenium.type("name=tagNumber[3].bagTagNumber", "12378945");
		selenium.type("name=tagNumber[0].expediteNumber", "11111111");
		selenium.type("name=tagNumber[1].expediteNumber", "22222222");
		selenium.type("name=tagNumber[3].expediteNumber", "33333333");
		selenium.click("name=tagNumber[1].expediteSticker");
		selenium.select("name=destStation", "label=AUA");
		selenium.select("name=faultStation", "label=CMW");
		selenium.select("name=lossCode", "label=14 - Baggage not labelled");
		selenium.type("name=bagItinerary[0].legfrom", "ATL");
		selenium.type("name=bagItinerary[0].legto", "LAS");
		selenium.type("name=bagItinerary[0].flightnum", "123");
		selenium.click("id=calendar0");
		selenium.click("link=Today");
		selenium.click("id=calendar20");
		selenium.click("link=Today");
		selenium.type("name=itinerary[0].legfrom", "ATL");
		selenium.type("name=itinerary[0].legto", "LAS");
		selenium.type("name=itinerary[0].flightnum", "123");
		selenium.click("id=calendar30");
		selenium.click("link=Today");
		selenium.click("id=calendar40");
		selenium.click("link=Today");
		selenium.type("name=message", "Test");
		selenium.click("name=save1");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("OHD Baggage has been forwarded."));
		} else {
			System.err.println("Forward Onhand Success Page Failed To Load. Error Page Loaded Instead.");
			verifyTrue(false);
		}
		
	}
}