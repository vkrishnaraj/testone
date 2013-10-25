package aero.nettracer.web.southwest.testing.actions.nt.founds;

import org.junit.Test;

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
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			cycleSearch("name=lastName", "id=lastname", "Test", true, false);
			cycleSearch("name=firstName", "id=firstname", "Test", true, false);
			cycleSearch("id=priInterNum", "id=priInterNum", "1", true, false);
			cycleSearch("id=priAreaNum", "id=priAreaNum", "222", true, false);
			cycleSearch("id=priExchaNum", "id=priExchaNum", "333", true, false);
			cycleSearch("id=priLineNum", "id=priLineNum", "4444", true, false);
			cycleSearch("name=category", "id=category_0", "Bags", false, true);
			cycleSearch("name=subCategory", "id=subcategories_0", "Baby Bag", false, true);
			cycleSearch("name=brand", "id=itembrand_0", "Test", true, false);
			cycleSearch("name=email", "id=email", "test@test.com", true, false);
			cycleSearch("name=serialNumber", "id=itemserial_0", "123", true, false);
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=calendar2");
			selenium.click("link=Today");
			selenium.select("name=statusId", "label=Open");
			selenium.type("name=email", "test@test.com");
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (isElementPresent("id=resultLink0")) {
				selenium.click("id=resultLink0");
				waitForPageToLoadImproved();
			}
		} else {
			System.out.println("testSearchFoundAllFields failure line 88");
		}
	}
	
	private void cycleSearch(String searchField, String formField, String value, boolean type, boolean select) {
		selenium.click("id=calendar");
		selenium.click("link=Today");
		selenium.click("id=calendar2");
		selenium.click("link=Today");
		selenium.select("name=statusId", "label=Open");
		if (type) {
			selenium.type(searchField, value);
		}
		if (select) {
			selenium.select(searchField, "label=" + value);
		}
		selenium.click("id=button");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			if (isElementPresent("id=resultLink0")) {
				selenium.click("id=resultLink0");
				waitForPageToLoadImproved();
			}
			if (type) {
				verifyEquals(value, selenium.getValue(formField));
			}
			if (select) {
				verifyEquals(value, selenium.getSelectedLabel(formField));
			}
			clickMenu("menucol_5.2");
			waitForPageToLoadImproved();
		} else {
			System.out.println("cycleSearch failure line 115 fields: " + searchField + ", " + formField + ", " + value);
		}
		if(checkNoErrorPage()){
			if (type) {
				selenium.type(searchField, "");
			}
			if (select) {
				selenium.select(searchField, "label=Please select...");
			}
		} else {
			System.out.println("cycleSearch failure line 125 fields: " + searchField + ", " + formField + ", " + value);
		}
	}

	@Test
	public void testDeliveryRequiredFields() throws Exception {
		if(checkNoErrorPage()){
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
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
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Picked Up", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testPassengerPickUp failure line 145");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
		} else {
			System.out.println("testPassengerPickUp failure line 151");
		}
	}

	@Test
	public void testDeliver() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Deliver to customer");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Delivered", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testDeliver failure line 166");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
		} else {
			System.out.println("testDeliver failure line 172");
		}
	}

	@Test
	public void testSendToLFC() throws Exception {
		selenium.type("id=trackingNumber", "123123123");
		selenium.click("link=Send to LFC");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Sent to LFC", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
			selenium.click("link=Undo");
			waitForPageToLoadImproved();
		} else {
			System.out.println("testSendToLFC failure line 187");
		}
		if (checkNoErrorPage()) {
			verifyEquals("Open", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("None", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
		} else {
			System.out.println("testSendToLFC failure line 193");
		}
	}

	@Test
	public void testRemove() throws Exception {
		selenium.type("id=removalReason", "Test Reason");
		selenium.click("link=Remove item");
		waitForPageToLoadImproved();
		if(checkNoErrorPage()){
			verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId"));
			verifyEquals("Removed", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
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
				verifyEquals("Closed", selenium.getSelectedLabel("name=found.statusId")); // Check statuses and tracking number for match
				verifyEquals("Sent to LFC", selenium.getSelectedLabel("name=foundItem.disposition.status_ID"));
				verifyEquals("Tracking Number:  " + trackingNumber, selenium.getText("//div[@id='maincontent']/table[4]/tbody/tr[2]/td"));
				goToTaskManager();
			} else {
				System.out.println("testRemove failure line 207");
			}
		}
	}
}
