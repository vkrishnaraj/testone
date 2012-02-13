package aero.nettracer.web.lfc.testing.actions.lfc.match;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateDelivery extends DefaultSeleneseTestCase {

	public static String lostId;
	public static String foundId;
	
	@Test
	public void testCreateLost() {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "Loupas");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "Matt");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "420 East Ave");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "Lithia Springs");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Georgia");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "30314");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/select", "label=Mobile");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/input", "PN1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/input", "Backbeat 903");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the Lost Report page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			LF_CreateDelivery.lostId = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("CD: Created Lost Report: " + LF_CreateDelivery.lostId);
		} else {
			System.out.println("CD: Failed to create the Lost Report.");
			return;
		}
	}
	
	@Test
	public void testCreateFound() {
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_CreateDelivery.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_CreateDelivery.foundId);
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "PN1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Backbeat 903");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();						
		} else {
			System.out.println("CD: Failed to load the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("CD: Created Found Item: " + LF_CreateDelivery.foundId);
			selenium.type("//input[@id='foundInput']", LF_CreateDelivery.lostId);
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to create the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Manually Matched"));
			verifyTrue(selenium.isTextPresent("Match lost report Id: " + LF_CreateDelivery.lostId));
		} else {
			System.out.println("CD: Failed to manually match Found Item: " + LF_CreateDelivery.foundId + " to LostReport: " + LF_CreateDelivery.lostId);
			return;
		}
	}
	
	@Test
	public void testCreateDelivery() {
		selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyDeliveryInformationPresent();
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();			
		} else {
			System.out.println("CD: No information present on View Items to Deliver page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td[2]/input", "654654654");
			selenium.click("//div[@id='maincontent']/center/input[2]");
			waitForPageToLoadImproved();			
		} else {
			System.out.println("CD: Failed to load the Create Delivery page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'search_lost_found.do?clear=1')]");
			waitForPageToLoadImproved();				
		} else {
			System.out.println("CD: Failed to save the Create Delivery page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr/td/select", "label=Found");
			selenium.type("//td[@id='found.id.cell']/input", LF_CreateDelivery.foundId);
			selenium.click("//div[@id='maincontent']/table/tbody/tr[5]/td/center/input");
			waitForPageToLoadImproved();			
		} else {
			System.out.println("CD: Failed to load the search lost/found page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals(LF_CreateDelivery.foundId, selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr/td/input"));
			verifyTrue(selenium.isTextPresent("Tracking Number:  654654654"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a"));
			selenium.click("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyDeliveryOptions();
			selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
		} else {
			System.out.println("CD: Failed to undo the delivery information for Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("CD: Failed to load the Items to Deliver page.");
			return;
		}
			
	}
	
	@Test
	public void testDeliveryRejected() {
		selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
		waitForPageToLoadImproved();
			
		if (checkNoErrorPage()) {
			verifyDeliveryInformationPresent();
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]");
			waitForPageToLoadImproved();
		} else { 
			System.out.println("CD: Failed to undo the delivery information for Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'search_lost_found.do?clear=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: An error occurred while rejecting the delivery.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[5]/td/center/input");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the search lost/found page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals(LF_CreateDelivery.foundId, selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr/td/input"));
			verifyTrue(selenium.isTextPresent("Delivery rejected"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a"));
			selenium.click("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyDeliveryOptions();
			selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to undo the rejected delivery information for Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("CD: Failed to load the Items to Deliver page.");
			return;
		}
		
	}
	
	@Test
	public void testPickedUpByCustomer() {
		selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyDeliveryInformationPresent();
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[3]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to undo the delivery information for Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'search_lost_found.do?clear=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: An error occurred while indicating that the item was picked up by the customer.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[5]/td/center/input");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the search lost/found page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals(LF_CreateDelivery.foundId, selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr/td/input"));
			verifyTrue(selenium.isTextPresent("Picked up by customer"));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a"));
			selenium.click("//div[@id='maincontent']/table[5]/tbody/tr[2]/td[2]/a");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: Failed to load the Found Item: " + LF_CreateDelivery.foundId);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyDeliveryOptions();
			selenium.click("//a[contains(@href, 'view_items_deliver.do')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CD: An error occurred after un-doing the picked up by customer status.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyDeliveryInformationPresent();
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[3]");
		} else {
			System.out.println("CD: Failed to load the Items to Deliver page.");
			return;
		}
		
	}
	
	private void verifyDeliveryInformationPresent() {
		verifyTrue(selenium.isTextPresent(LF_CreateDelivery.foundId));
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td/a"));
		verifyTrue(selenium.isTextPresent("To Be Delivered"));
		// Create Delivery
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a"));
		// Delivery Rejected
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[2]"));
		// Picked Up by Customer
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a[3]"));
	}
	
	private void verifyDeliveryOptions() {
		verifyTrue(selenium.isTextPresent("Tracking Number:"));
		verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
		verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
		verifyTrue(selenium.isElementPresent("link=Delivery rejected"));
		verifyTrue(selenium.isElementPresent("link=Picked up by customer"));
	}
	
}
