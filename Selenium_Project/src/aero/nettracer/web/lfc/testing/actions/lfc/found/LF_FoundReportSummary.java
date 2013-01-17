package aero.nettracer.web.lfc.testing.actions.lfc.found;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_FoundReportSummary extends DefaultSeleneseTestCase {

	@Test
	public void testContactInfoNotification() {
		selenium.click("id=menucol_2.2");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("name=barcode", Settings.FOUND_ID_LF);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("FRS: Failed to load the task manager page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Report Summary"));
//			selenium.type("name=found.client.lastName", "Test");
//			selenium.click("name=saveButton");
			selenium.type("name=found.client.address.decryptedAddress1", "Test");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("FRS: failed to load Lost Item: " + Settings.FOUND_ID_LF);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyTrue(selenium.isTextPresent("This item has identification information. See below for further details."));
			selenium.type("name=found.client.address.decryptedAddress1", "");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to load after adding name: Test");
			verifyTrue(false);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyFalse(selenium.isTextPresent("This item has identification information. See below for further details."));
		} else {
			System.out.println("Failed to load after removing name: Test");
			verifyTrue(false);
			return;
		}
		
	}
	
	@Test
	public void testTraceResults() throws Exception {
		verifyTrue(selenium.isTextPresent("Found Item:  " + Settings.FOUND_ID_LF));
		verifyTrue(selenium.isTextPresent("Lost Report:  " + Settings.LOST_ID_LF));
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a"));
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]"));
		selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a"));
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed in test found item trace results summary.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]"));
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed in test found item trace results summary.");
			verifyTrue(false);
			return;
		}
		
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Rejected Matches"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/span/a"));
			selenium.click("//div[@id='maincontent']/span/a");
			verifyTrue(selenium.isElementPresent("//div[@id='rejectedMatches']/table/tbody/tr[2]/td[4]/a"));
			selenium.click("//div[@id='rejectedMatches']/table/tbody/tr[2]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed in test found item trace results summary.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isTextPresent("Rejected Matches"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]"));
		} else {
			System.out.println("Failed in test found item trace results summary.");
			verifyTrue(false);
			return;
		}
	}
	
	@Test
	public void testLocationField() {
		verifyTrue(selenium.isTextPresent("Item Location"));
		verifyTrue(selenium.isElementPresent("name=found.itemLocation"));
		selenium.select("name=found.itemLocation", "label=Verification Bin");
		selenium.click("name=saveButton");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyEquals("1", selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[5]/select"));
		} else {
			System.out.println("Failed in test found item trace results summary.");
			verifyTrue(false);
			return;
		}
	}
}
