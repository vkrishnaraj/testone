package aero.nettracer.web.southwest.testing.actions.nt.founds;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_FoundItem extends WN_SeleniumTest {

	@Test
	public void testCreateFoundRequiredFields() throws Exception {
		selenium.click("id=menucol_5.1");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			selenium.click("id=saveButton");
			assertEquals("Item Received Date is required.", selenium.getAlert());
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=saveButton");
			assertEquals("Category is required.", selenium.getAlert());
			selenium.select("id=category", "label=Bags");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testCreateFoundRequiredFields failure line 29");
		}
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved"));
		} else {
			System.out.println("testCreateFoundRequiredFields failure line 34");
		}
	}

	@Test
	public void testCreateFoundAllFields() throws Exception {
		selenium.type("id=flightNumber", "123");
		selenium.select("id=category", "label=Bags");
		selenium.select("id=subCategory", "label=Baby Bag");
		selenium.type("id=brand", "Test");
		selenium.type("id=model", "Test");
		selenium.type("id=serialNumber", "123");
		selenium.select("id=color", "label=Black");
		selenium.select("id=caseColor", "label=Black");
		selenium.type("id=description", "Test");
		selenium.click("id=button");
		selenium.type("id=lastName", "Test");
		selenium.type("id=firstName", "Test");
		selenium.type("id=addr1", "Test");
		selenium.type("id=city", "Test");
		selenium.select("id=state", "label=Georgia");
		selenium.type("id=zip", "30339");
		selenium.type("name=primaryInternationalNumber", "1");
		selenium.type("name=primaryAreaNumber", "222");
		selenium.type("name=primaryExchangeNumber", "333");
		selenium.type("name=primaryLineNumber", "4444");
		selenium.type("id=email", "test@test.com");
		selenium.click("id=saveButton");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved"));
		} else {
			System.out.println("testCreateFoundAllFields failure line 68");
		}
	}

	@Test
	public void testSearchFoundAllFields() throws Exception {
		selenium.click("id=menucol_5.2");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=calendar2");
			selenium.click("link=Today");
			selenium.type("name=lastName", "Test");
			selenium.type("name=firstName", "Test");
			selenium.type("id=priInterNum", "1");
			selenium.type("id=priAreaNum", "222");
			selenium.type("id=priExchaNum", "333");
			selenium.type("id=priLineNum", "4444");
			selenium.select("name=category", "label=Bags");
			selenium.select("name=subCategory", "label=Baby Bag");
			selenium.type("name=brand", "Test");
			selenium.type("name=email", "test@test.com");
			selenium.type("name=serialNumber", "123");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testSearchFoundAllFields failure line 93");
		}
	}

	@Test
	public void testDeliveryRequiredFields() throws Exception {
		if(checkNoErrorPage()){
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=found.disposition.status_ID"));
			selenium.click("link=Deliver to customer");
			assertEquals("Tracking Number is required.", selenium.getAlert());
			selenium.click("link=Send to LFC");
			assertEquals("Tracking Number is required.", selenium.getAlert());
			selenium.click("link=Remove item");
			assertEquals("Removal Reason is required.", selenium.getAlert());
		} else {
			System.out.println("testDeliveryRequiredFields failure line 109");
		}
	}

	@Test
	public void testPassengerPickUp() throws Exception {
		selenium.click("link=Picked up by customer");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Picked Up", selenium.getSelectedLabel("name=found.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testPassengerPickUp failure line 123");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=found.disposition.status_ID"));
		} else {
			System.out.println("testPassengerPickUp failure line 129");
		}
	}

	@Test
	public void testDeliver() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Deliver to customer");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Delivered", selenium.getSelectedLabel("name=found.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testDeliver failure line 144");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=found.disposition.status_ID"));
		} else {
			System.out.println("testDeliver failure line 150");
		}
	}

	@Test
	public void testSendToLFC() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Send to LFC");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Sent to LFC", selenium.getSelectedLabel("name=found.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testSendToLFC failure line 165");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=found.disposition.status_ID"));
		} else {
			System.out.println("testSendToLFC failure line 171");
		}
	}

	@Test
	public void testRemove() throws Exception {
		selenium.type("id=removalReason", "Test Reason");
		selenium.click("link=Remove item");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Removed", selenium.getSelectedLabel("name=found.disposition.status_ID"));
			goToTaskManager();
		} else {
			System.out.println("testRemove failure line 185");
		}
	}
}
