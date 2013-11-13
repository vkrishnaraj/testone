package aero.nettracer.web.lfc.testing.actions.lfc.delivery;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_DeliveredFound extends DefaultSeleneseTestCase {
	

	@Test
	public void testDeliverFound() throws Exception {
		clickMenu("menucol_2.6");
		waitForPageToLoadImproved();
		
		selenium.type("id=newRemark", "Delivered for DMV");
		selenium.type("id=addBarcode", Settings.FOUND_ID_LF);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyDown("//input[@id='addBarcode']", "\\13");
		waitForPageToLoadImproved(1000,false);
		if (checkNoErrorPage()) {
			isTextPresent("Found Item: "+Settings.FOUND_ID_LF+", matched with Lost Report: "+Settings.LOST_ID_LF+", has been delivered with Tracking Number: Delivered for DMV");
		} else {
			System.out.println("LFDF: failed to deliver: " + Settings.FOUND_ID_LF);
			return;
		}
		

		selenium.type("id=addBarcode", Settings.FOUND_ID_LF);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyDown("//input[@id='addBarcode']", "\\13");
		waitForPageToLoadImproved(1000,false);

		if (checkNoErrorPage()) {
			isTextPresent("Found Item: "+Settings.FOUND_ID_LF+" is already delivered");
		} else {
			System.out.println("Error delivering delivered Found Item: "+Settings.FOUND_ID_LF);
			return;
		}

	}
}