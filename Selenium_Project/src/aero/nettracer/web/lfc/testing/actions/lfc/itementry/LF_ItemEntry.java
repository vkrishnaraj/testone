package aero.nettracer.web.lfc.testing.actions.lfc.itementry;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

public class LF_ItemEntry extends LoginUtil {

	@Test
	public void testItemEntry() throws Exception {

		selenium.click("//a[contains(@href, 'enter_items.do')]");

		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=disFoundDate", "");
			selenium.click("id=saveButton");
			assertEquals("Item Received Date is required.", selenium.getAlert());
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=saveButton");
			assertEquals("Item ID is required.", selenium.getAlert());
			selenium.type("//input[@name='found.barcode']", "a");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Category is required.", selenium.getAlert());
			selenium.select("//select[@name='found.item.category']",
					"label=Bags");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: a"));
			verifyTrue(selenium.isTextPresent("Desc: Bags"));
			verifyTrue(selenium.isTextPresent("Status:  Entered"));
			selenium.type("//input[@name='found.barcode']", "b");
			selenium.select("//select[@name='found.item.category']",
					"label=Blueprints/Posters");
			selenium.click("css=option[value=\"1\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@id='lastName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: b"));
			verifyTrue(selenium.isTextPresent("Desc: Blueprints/Posters"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_1 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: b"));
			verifyTrue(selenium.isTextPresent("Desc: Blueprints/Posters"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "c");
			selenium.select("//select[@name='found.item.category']",
					"label=Books/Notebooks");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: c"));
			verifyTrue(selenium.isTextPresent("Desc: Books/Notebooks"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: c"));
			verifyTrue(selenium.isTextPresent("Desc: Books/Notebooks"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "d");
			selenium.select("//select[@name='found.item.category']",
					"label=Briefcase/Portfolio");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "t");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: d"));
			verifyTrue(selenium.isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: d"));
			verifyTrue(selenium.isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "e");
			selenium.select("//select[@name='found.item.category']",
					"label=Camera/Photo Equipment");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: e"));
			verifyTrue(selenium.isTextPresent("Desc: Camera/Photo Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: e"));
			verifyTrue(selenium.isTextPresent("Desc: Camera/Photo Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "f");
			selenium.select("//select[@name='found.item.category']",
					"label=Cards (ATM/Credit/ID)");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: f"));
			verifyTrue(selenium.isTextPresent("Desc: Cards (ATM/Credit/ID)"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: f"));
			verifyTrue(selenium.isTextPresent("Desc: Cards (ATM/Credit/ID)"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "g");
			selenium.select("//select[@name='found.item.category']",
					"label=Cellphone");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: g"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: g"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "h");
			selenium.select("//select[@name='found.item.category']",
					"label=Cellphone Accessories");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.select("//select[@id='state']", "label=Alabama");
			verifyFalse(selenium
					.isEditable("//input[@name='found.client.address.decryptedProvince']"));
			verifyEquals(
					"US",
					selenium.getValue("//select[@name='found.client.address.country']"));
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: h"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: h"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "i");
			selenium.select("//select[@name='found.item.category']",
					"label=Clothing");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: i"));
			verifyTrue(selenium.isTextPresent("Desc: Clothing"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: i"));
			verifyTrue(selenium.isTextPresent("Desc: Clothing"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "j");
			selenium.select("//select[@name='found.item.category']",
					"label=Computer-Related");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.select("//select[@id='country']", "label=Afghanistan");
			verifyFalse(selenium
					.isEditable("//select[@name='found.client.address.decryptedState']"));
			verifyTrue(selenium
					.isEditable("//input[@name='found.client.address.decryptedProvince']"));
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: j"));
			verifyTrue(selenium.isTextPresent("Desc: Computer-Related"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: j"));
			verifyTrue(selenium.isTextPresent("Desc: Computer-Related"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "k");
			selenium.select("//select[@name='found.item.category']",
					"label=Cosmetic/Shaving Kit");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='primaryPhoneNumber']", "1112223333");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: k"));
			verifyTrue(selenium.isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: k"));
			verifyTrue(selenium.isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "l");
			selenium.select("//select[@name='found.item.category']",
					"label=Electronic Equipment");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='secondaryPhoneNumber']", "1112223333");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: l"));
			verifyTrue(selenium.isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: l"));
			verifyTrue(selenium.isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='found.barcode']", "m");
			selenium.select("//select[@name='found.item.category']",
					"label=Glasses");
			selenium.click("css=option[value=\"2\"]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//input[@name='found.client.decryptedEmail']",
					"test@test.com");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: m"));
			verifyTrue(selenium.isTextPresent("Desc: Glasses"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: m"));
			verifyTrue(selenium.isTextPresent("Desc: Glasses"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//input[@name='disFoundDate']", "01/01/2012");
			selenium.type("//input[@name='found.barcode']", "n");
			selenium.select("//select[@name='found.item.category']",
					"label=Jewelry/Watches");
			selenium.click("css=option[value=\"14\"]");
			selenium.select("//select[@name='found.item.value']", "label=High");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals("01/01/2012",
					selenium.getValue("//input[@name='disFoundDate']"));
			verifyEquals("2",
					selenium.getValue("//select[@name='found.item.value']"));
			selenium.click("//img[@id='calendar']");
			selenium.click("link=Today");
			selenium.type("//input[@name='found.barcode']", "o");
			selenium.select("//select[@name='found.item.category']",
					"label=Bags");
			selenium.click("//option[@value='15']");
			selenium.select("//select[@name='subCategory']", "label=Baby Bag");
			selenium.select("//select[@name='found.item.value']", "label=Low");
			selenium.type("//input[@name='found.item.brand']", "test");
			selenium.type("//input[@name='found.item.model']", "test");
			selenium.type("//input[@name='found.item.serialNumber']", "test");
			selenium.select("//select[@name='found.item.color']", "label=White");
			selenium.select("//select[@name='found.item.caseColor']",
					"label=Black");
			selenium.type("//input[@name='found.item.description']", "test");
			selenium.type("//input[@name='disFoundPhoneNumber']", "1112223333");
			selenium.click("//input[@id='button']");
			selenium.type("//input[@id='lastName']", "test");
			selenium.type("//input[@name='found.client.firstName']", "test");
			selenium.type("//input[@name='found.client.middleName']", "t");
			selenium.type(
					"//input[@name='found.client.address.decryptedAddress1']",
					"test");
			selenium.type(
					"//input[@name='found.client.address.decryptedAddress2']",
					"test");
			selenium.type(
					"//input[@name='found.client.address.decryptedCity']",
					"test");
			selenium.select("//select[@id='state']", "label=Tennessee");
			selenium.type("//input[@name='found.client.address.decryptedZip']",
					"11111");
			selenium.type("//input[@name='primaryPhoneNumber']", "1112223333");
			selenium.select("//select[@name='primaryNumberType']", "label=Home");
			selenium.type("//input[@name='secondaryPhoneNumber']", "4445556666");
			selenium.select("//select[@name='secondaryNumberType']",
					"label=Mobile");
			selenium.type("//input[@name='found.client.decryptedEmail']",
					"test@test.com");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: o"));
			verifyTrue(selenium.isTextPresent("Desc: Bags,test,test"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.click("css=#moveDiv_2 > center > input.button");
			verifyTrue(selenium.isTextPresent("Item ID: o"));
			verifyTrue(selenium.isTextPresent("Desc: Bags,test,test"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium
					.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
		} else {
			return;
		}

	}

}
