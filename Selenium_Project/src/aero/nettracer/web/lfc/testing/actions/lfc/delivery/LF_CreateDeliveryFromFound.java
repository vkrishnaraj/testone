package aero.nettracer.web.lfc.testing.actions.lfc.delivery;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_CreateDeliveryFromFound extends DefaultSeleneseTestCase {
	
	public static String lostId;
	public static String foundId;
	public static String today;

	@Test
	public void testCreateLost() throws Exception {
		clickMenu("menucol_2.3");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.select("name=lost.companyId", "label=Southwest");
			selenium.type("name=lost.client.lastName", "Sanders");
			selenium.type("name=lost.client.firstName", "Mike");
			selenium.type("name=lost.client.address.decryptedAddress1", "950 Marietta St");
			selenium.type("name=lost.client.address.decryptedCity", "Atlanta");
			selenium.select("id=state", "label=Georgia");
			selenium.type("name=lost.client.address.decryptedZip", "30318");
			selenium.type("id=priInterNum", "1");
			selenium.type("id=priAreaNum", "122");
			selenium.type("id=priExchaNum", "23");
			selenium.type("id=priLineNum", "333");
			selenium.type("id=priLineNum", "333");
			selenium.select("id=priPhoneType", "label=Home");
			selenium.type("id=itembrand_0", "HTC");
			selenium.type("id=itemserial_0", "HTC1234");
			selenium.type("id=itemmodel_0", "Eris");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("id=itemcolor_0", "label=White");
			selenium.select("id=itemcasecolor_0", "label=Black");
			selenium.select("segment[0].originId", "label=ATL");
			selenium.select("segment[0].destinationId", "label=BOS");
			selenium.click("//div[@id='maincontent']/center[4]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while creating the Lost Report.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your lost report was successfully saved."));
			LF_CreateDeliveryFromFound.lostId = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("CDFF: Created Lost Report: " + LF_CreateDeliveryFromFound.lostId);
		} else {
			System.out.println("CDFF: An error occurred while saving the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void testCreateFound() throws Exception {
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_CreateDeliveryFromFound.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_CreateDeliveryFromFound.foundId);
			selenium.select("name=found.companyId", "label=Southwest");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			LF_CreateDeliveryFromFound.today = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td[2]/input");
			selenium.type("id=itembrand_0", "HTC");
			selenium.type("id=itemserial_0", "HTC1234");
			selenium.type("id=itemmodel_0", "Eris");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("id=itemcolor_0", "label=White");
			selenium.select("id=itemcasecolor_0", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while creating the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			System.out.println("CDFF: Created Found Item: " + LF_CreateDeliveryFromFound.foundId);
		} else {
			System.out.println("CDFF: An error occurred while saving the Lost Report.");
			return;
		}
		
		selenium.type("//input[@id='foundInput']", LF_CreateDeliveryFromFound.lostId);
		selenium.click("id=confirmInput");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: An error occurred while manually matching the Lost Report and Found Item.");
			return;
		}

		// Verify the Items to Deliver page
		clickMenu("menucol_0.5");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(text(),'" + LF_CreateDeliveryFromFound.foundId + "')]")));
			verifyTrue(isTextPresent("To Be Delivered"));
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
    	selenium.type("name=foundItem.trackingNumber", "12341234");
		selenium.click("//div[@id='maincontent']/center[3]/input[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Tracking Number:  12341234"));
			verifyEquals(LF_CreateDeliveryFromFound.today, selenium.getTable("//div[@id='maincontent']/table[5].1.1"));
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred while entering the tracking number on the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(isElementPresent(By.name("trackingNumber")));
			verifyEquals("12341234", selenium.getValue("name=trackingNumber"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: Failed to load the Lost Report after saving the tracking number.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=lost.statusId"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing the tracking number.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("", selenium.getValue("id=trackingNumber"));
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
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			verifyTrue(isTextPresent("Tracking Number:  123123123"));
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
			verifyTrue(isTextPresent("Your found item was successfully saved."));
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
			verifyTrue(isTextPresent("Delivery rejected"));
			verifyEquals(LF_CreateDeliveryFromFound.today, selenium.getTable("//div[@id='maincontent']/table[5].1.1"));
			verifyTrue(isElementPresent(By.xpath("(//a[contains(text(),'Undo')])[2]")));
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[4]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred after clicking Delivery Rejected on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(isTextPresent("Delivery rejected"));
			verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/table[4]/tbody/tr/td[2]/a")));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: Failed to load the Lost Report from the Found Item page.");
			return;
			
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing Delivery Rejected.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[4]/select"));
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
			verifyTrue(isTextPresent("Picked up by customer"));
			verifyEquals(LF_CreateDeliveryFromFound.today, selenium.getTable("//div[@id='maincontent']/table[5].1.1"));
			verifyTrue(isElementPresent(By.xpath("(//a[contains(text(),'Undo')])[2]")));
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[4]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occured after clicking Picked Up by Customer on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Closed", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			verifyTrue(isTextPresent("Picked up by customer"));
			verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/table[4]/tbody/tr/td[2]/a")));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occured while loading the Lost Report from the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CDFF: An error occurred on the Lost Report page while un-doing Picked Up by Customer.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[4]/select"));
			verifyDeliveryOptions();
		} else {
			System.out.println("CDFF: Failed to load the Found Item after verifying the Lost Report.");
			return;
		}
		
	}
	
	private void verifyDeliveryOptions() {
		verifyTrue(isTextPresent("Tracking Number:"));
		verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input")));
		verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
		verifyTrue(isElementPresent(By.linkText("Delivery rejected")));
		verifyTrue(isElementPresent(By.linkText("Picked up by customer")));
	}
	
}
