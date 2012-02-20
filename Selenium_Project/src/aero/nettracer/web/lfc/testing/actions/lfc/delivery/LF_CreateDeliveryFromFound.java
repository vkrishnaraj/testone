package aero.nettracer.web.lfc.testing.actions.lfc.delivery;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_CreateDeliveryFromFound extends DefaultSeleneseTestCase {
	
	public static String lostId;
	public static String foundId;

	@Test
	public void testCreateLost() throws Exception {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "Sanders");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "Mike");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "950 Marietta St");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "Atlanta");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Georgia");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "30318");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("document.forms['lostReportForm'].elements[24]", "label=Home");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/input", "HTC");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/input", "HTC1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/input", "Eris");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=White");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while creating the Lost Report.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			LF_CreateDeliveryFromFound.lostId = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("CDFF: Created Lost Report: " + LF_CreateDeliveryFromFound.lostId);
		} else {
			System.out.println("CDFF: An error occurred while saving the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void testCreateFound() throws Exception {
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_CreateDeliveryFromFound.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_CreateDeliveryFromFound.foundId);
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "HTC");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "HTC1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Eris");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=White");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while creating the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("CDFF: Created Found Item: " + LF_CreateDeliveryFromFound.foundId);
		} else {
			System.out.println("CDFF: An error occurred while saving the Lost Report.");
			return;
		}
		
		selenium.type("//div[@id='maincontent']/table[4]/tbody/tr/td/input", LF_CreateDeliveryFromFound.lostId);
		selenium.click("xpath=(//a[contains(text(),'Confirm Match')])[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: An error occurred while manually matching the Lost Report and Found Item.");
			return;
		}

		// Verify the Items to Deliver page
		selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(LF_CreateDeliveryFromFound.foundId));
			verifyTrue(selenium.isElementPresent("//a[contains(text(),'" + LF_CreateDeliveryFromFound.foundId + "')]"));
			verifyTrue(selenium.isTextPresent("To Be Delivered"));
		} else {
			System.out.println("CDFF: Failed to load the Items to Deliver page.");
			verifyTrue(false);
		}
		
		selenium.click("//a[contains(text(),'" + LF_CreateDeliveryFromFound.foundId + "')]");
		waitForPageToLoadImproved();
		
		if (!checkNoErrorPage()) {
			System.out.println("CDFF: Failed to return to the Found Item page.");
			return;
		}
		
	}
	
	@Test
	public void testTrackingNumber() throws Exception {
    	selenium.type("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input", "12341234");
		selenium.click("//div[@id='maincontent']/center[3]/input[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Tracking Number:  12341234"));
			verifyTrue(selenium.isElementPresent("xpath=(//a[contains(text(),'Undo')])[2]"));
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while entering the tracking number on the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/input"));
			verifyEquals("12341234", selenium.getValue("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/input"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: Failed to load the Lost Report after saving the tracking number.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing the tracking number.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: Failed to load the Found Item after verifying the Lost Report.");
			return;
		}
		
		selenium.type("//input[@id='checkAmount']", "a");
		selenium.click("xpath=(//input[@id='button'])[2]");
		assertEquals("Check Amount must be a number greater than zero.", selenium.getAlert());
		selenium.type("id=checkAmount", "-1");
		selenium.click("xpath=(//input[@id='button'])[2]");
		assertEquals("Check Amount must be a number greater than zero.", selenium.getAlert());
		selenium.type("//input[@id='checkAmount']", "15");
		selenium.click("xpath=(//input[@id='button'])[2]");
		assertEquals("Tracking Number and Check Number must be supplied if a Check Amount is entered.", selenium.getAlert());
		selenium.type("//input[@id='checkNumber']", "1234");
		selenium.click("xpath=(//input[@id='button'])[2]");
		assertEquals("Tracking Number and Check Number must be supplied if a Check Amount is entered.", selenium.getAlert());
		selenium.type("//input[@id='trackingNumber']", "123123123");
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyTrue(selenium.isTextPresent("Tracking Number:  123123123"));
			verifyEquals("1234", selenium.getValue("//input[@id='checkNumber']"));
			verifyEquals("15.00", selenium.getValue("//input[@id='checkAmount']"));
			selenium.click("xpath=(//a[contains(text(),'Undo')])[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while saving the Found Item with check information.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.type("//input[@id='checkNumber']", "");
			selenium.type("//input[@id='checkAmount']", "");
			selenium.click("xpath=(//input[@id='button'])[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while removing delivery information from the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
		} else {
			System.out.println("CDFF: Failed to save the Found Item after removing check number and check amount.");
			return;
		}
	}
	
	@Test
	public void testDeliveryRejected() {
		selenium.click("//a[contains(text(),'Delivery rejected')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Delivery rejected"));
			verifyTrue(selenium.isElementPresent("xpath=(//a[contains(text(),'Undo')])[2]"));
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred after clicking Delivery Rejected on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(selenium.isTextPresent("Delivery rejected"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: Failed to load the Lost Report from the Found Item page.");
			return;
			
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing Delivery Rejected.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: Failed to load the Found Item after verifying the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void testPickedUpByCustomer() {
		selenium.click("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[3]/a");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Picked up by customer"));
			verifyTrue(selenium.isElementPresent("xpath=(//a[contains(text(),'Undo')])[2]"));
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occured after clicking Picked Up by Customer on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(selenium.isTextPresent("Picked up by customer"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occured while loading the Lost Report from the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing Picked Up by Customer.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select"));
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: Failed to load the Found Item after verifying the Lost Report.");
			return;
		}
		
	}
	
	private void verifyDeliveryOptions() {
		verifyTrue(selenium.isTextPresent("Tracking Number:"));
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
		verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
		verifyTrue(selenium.isElementPresent("link=Delivery rejected"));
		verifyTrue(selenium.isElementPresent("link=Picked up by customer"));
	}
	
}
