package aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class B6_CloseLD_VerifyRequiredFields extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Pawob Information"));
			selenium.click("//td[@id='navmenucell']/div/dl/dd[6]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("name=doclose");
				assertEquals("Arrival Flight is required.", selenium.getAlert());
				selenium.type("name=theitem[0].arrivedonflightnum", "123");
				selenium.click("name=doclose");
				assertEquals("Arrival Date is required.", selenium.getAlert());
				selenium.click("id=calendar20");
				selenium.click("link=Today");
				selenium.click("name=doclose");
				waitForPageToLoadImproved();
				if (selenium.isTextPresent("Please select a fault airline and station")) {
					selenium.select("name=faultstation_id", "label=CBS");
					selenium.click("name=doclose");
					waitForPageToLoadImproved();
				}
				if (selenium.isTextPresent("Please select a reason for loss")) {
					selenium.select("name=loss_code", "label=21- Failed to Load");
					selenium.click("name=doclose");
					waitForPageToLoadImproved();
				}
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Pawob has been closed."));
					selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					waitForPageToLoadImproved();
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Close Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testPopulateQSCheck() {
		if (checkNoErrorPage()) {
			selenium.click("id=menucol_1.1");
			waitForPageToLoadImproved();
			selenium.type("name=recordlocator", "TESTER");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load page.");
			return;
		}
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			selenium.click("menucol_0.0");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLDVRF: Failed to load Quick Search and search on PNR.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.controlKeyDown();
			selenium.keyDown("id=header", "\\83");
			selenium.keyUp("id=header", "\\83");
			selenium.controlKeyUp();
			selenium.type("id=quickSearchQuery3", "TESTER");
			selenium.click("id=button");
			waitForPageToLoadImproved(1000,false);
		} else {
			System.out.println("CLDVRF: Failed to load prepopulate page.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(Settings.INCIDENT_ID_WN));
			selenium.keyDown("id=header", "\\27");
			selenium.keyUp("id=header", "\\27");
		} else {
			System.out.println("CLDVRF: Failed to check for existing PNR Incidents.");
			return;			
		}
		
	}
	
}
