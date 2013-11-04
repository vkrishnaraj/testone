package aero.nettracer.web.southwest.testing.actions.nt.founds;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_FoundItem extends WN_SeleniumTest {

	@Test
	public void testCreateFoundRequiredFields() throws Exception {
		clickMenu("menucol_5.1");
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
			System.out.println("testCreateFoundRequiredFields failure line 24");
		}
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved"));
		} else {
			System.out.println("testCreateFoundRequiredFields failure line 29");
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
			System.out.println("testCreateFoundAllFields failure line 61");
		}
	}

	@Test
	public void testSearchFoundAllFields() throws Exception {
		clickMenu("menucol_5.2");
		if(checkNoErrorPage()){
			cycleSearch(By.name("lastName"), By.id("lastname"), "Test", true);
			cycleSearch(By.name("firstName"), By.id("firstname"), "Test", true);
			cycleSearch(By.id("priInterNum"), By.id("priInterNum"), "1", true);
			cycleSearch(By.id("priAreaNum"), By.id("priAreaNum"), "222", true);
			cycleSearch(By.id("priExchaNum"), By.id("priExchaNum"), "333", true);
			cycleSearch(By.id("priLineNum"), By.id("priLineNum"), "4444", true);
			cycleSearch(By.name("category"), By.id("category_0"), "Bags", false);
			cycleSearch(By.name("subCategory"), By.id("subcategories_0"), "Baby Bag", false);
			cycleSearch(By.name("brand"), By.id("itembrand_0"), "Test", true);
			cycleSearch(By.name("email"), By.id("email"), "test@test.com", true);
			cycleSearch(By.name("serialNumber"), By.id("itemserial_0"), "123", true);
			driver.findElement(By.id("calendar")).click();
			waitForPageToLoadImproved(500);
			driver.findElement(By.linkText("Today")).click();
			driver.findElement(By.id("calendar2")).click();
			waitForPageToLoadImproved(500);
			driver.findElement(By.linkText("Today")).click();
			(new Select(driver.findElement(By.name("statusId")))).selectByVisibleText("Open");
			driver.findElement(By.name("email")).sendKeys("test@test.com");
			driver.findElement(By.id("button")).click();
			if (isElementPresent(By.id("resultLink0"))) {
				driver.findElement(By.id("resultLink0")).click();
			}
		} else {
			System.out.println("testSearchFoundAllFields failure line 88");
		}
	}
	
	private void cycleSearch(By searchField, By formField, String value, boolean type) {
		driver.findElement(By.id("calendar")).click();
		waitForPageToLoadImproved(500);
		driver.findElement(By.linkText("Today")).click();
		driver.findElement(By.id("calendar2")).click();
		waitForPageToLoadImproved(500);
		driver.findElement(By.linkText("Today")).click();
		(new Select(driver.findElement(By.name("statusId")))).selectByVisibleText("Open");
		if (type) {
			driver.findElement(searchField).sendKeys(value);
		} else {
			(new Select(driver.findElement(searchField))).selectByVisibleText(value);
		}
		driver.findElement(By.id("button")).click();
		if(checkNoErrorPage()){
			if (isElementPresent(By.id("resultLink0"))) {
				driver.findElement(By.id("resultLink0")).click();
			}
			if (type) {
				verifyEquals(value, driver.findElement(formField).getAttribute("value"));
			} else {
				verifyEquals(value, getSelectedLabel(formField));
			}
			clickMenu("menucol_5.2");
		} else {
			System.out.println("cycleSearch failure line 115 fields: " + searchField + ", " + formField + ", " + value);
		}
		if(checkNoErrorPage()){
			if (type) {
				driver.findElement(searchField).clear();
			} else {
				(new Select(driver.findElement(searchField))).selectByVisibleText("Please select...");
			}
		} else {
			System.out.println("cycleSearch failure line 125 fields: " + searchField + ", " + formField + ", " + value);
		}
	}

	@Test
	public void testDeliveryRequiredFields() throws Exception {
		if(checkNoErrorPage()){
			verifyEquals("Open", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("None", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
			selenium.click("link=Deliver to customer");
			assertEquals("Tracking Number is required.", selenium.getAlert());
			selenium.click("link=Send to LFC");
			assertEquals("Tracking Number is required.", selenium.getAlert());
			selenium.click("link=Remove item");
			assertEquals("Removal Reason is required.", selenium.getAlert());
		} else {
			System.out.println("testDeliveryRequiredFields failure line 131");
		}
	}

	@Test
	public void testPassengerPickUpFound() throws Exception {
		selenium.click("link=Picked up by customer");
		waitForPageToLoadImproved(500);
		if(checkNoErrorPage()){
			verifyEquals("Closed", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("Picked Up", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testPassengerPickUp failure line 145");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("None", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
		} else {
			System.out.println("testPassengerPickUp failure line 151");
		}
	}

	@Test
	public void testDeliver() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Deliver to customer");
		waitForPageToLoadImproved(500);
		if(checkNoErrorPage()){
			verifyEquals("Closed", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("Delivered", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testDeliver failure line 166");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("None", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
		} else {
			System.out.println("testDeliver failure line 172");
		}
	}

	@Test
	public void testSendToLFC() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Send to LFC");
		waitForPageToLoadImproved(500);
		if(checkNoErrorPage()){
			verifyEquals("Closed", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("Sent to LFC", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testSendToLFC failure line 187");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("None", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
		} else {
			System.out.println("testSendToLFC failure line 193");
		}
	}

	@Test
	public void testRemove() throws Exception {
		selenium.type("id=removalReason", "Test Reason");
		selenium.click("link=Remove item");
		waitForPageToLoadImproved(500);
		if(checkNoErrorPage()){
			verifyEquals("Closed", getSelectedLabel(By.name("found.statusId")));
			verifyEquals("Removed", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
			goToTaskManager();
		} else {
			System.out.println("testRemove failure line 207");
		}
	}

	@Test
	public void testTaskManagerShipping() throws Exception {
		String ship_item_nbr = selenium.getText("id=660entry"); // Stores the number of items ready to ship
		int ship_item_int = Integer.parseInt(ship_item_nbr);
		if (ship_item_int > 1) { // Runs the rest of the test if the number is high enough
			String shipped_found_id = "";
			String trackingNumber = "";
			selenium.click("id=660link");
			waitForPageToLoadImproved();
			if(checkNoErrorPage()){
				shipped_found_id = selenium.getText("id=link0"); // Store the id of the found we will be shipping
				selenium.click("name=found[0].selected");
				selenium.click("name=ship");
				assertEquals("Tracking Number is required.", selenium.getAlert()); // Check validation
				trackingNumber = "111222333";
				selenium.type("id=trackingNumber", trackingNumber);
				selenium.click("name=ship");
				waitForPageToLoadImproved();
			} else {
				System.out.println("testRemove failure line 207");
			}

			if(checkNoErrorPage()){
				verifyNotEquals(shipped_found_id, selenium.getText("id=link0")); // Check that found is no longer in the list
				clickMenu("menucol_5.2");
				waitForPageToLoadImproved();
			} else {
				System.out.println("testRemove failure line 207");
			}

			if(checkNoErrorPage()){
				selenium.type("name=id", shipped_found_id); // Load the shipped found item
				selenium.click("id=button");
				waitForPageToLoadImproved();
			} else {
				System.out.println("testRemove failure line 207");
			}

			if(checkNoErrorPage()){
				verifyEquals("Closed", getSelectedLabel(By.name("found.statusId"))); // Check statuses and tracking number for match
				verifyEquals("Sent to LFC", getSelectedLabel(By.name("foundItem.disposition.status_ID")));
				verifyEquals("Tracking Number:  " + trackingNumber, selenium.getText("//div[@id='maincontent']/table[4]/tbody/tr[2]/td"));
				goToTaskManager();
			} else {
				System.out.println("testRemove failure line 207");
			}
		}
	}
}
