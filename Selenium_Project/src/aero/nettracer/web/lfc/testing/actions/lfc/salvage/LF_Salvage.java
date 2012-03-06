package aero.nettracer.web.lfc.testing.actions.lfc.salvage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tools.ant.taskdefs.WaitFor;
import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_Salvage extends DefaultSeleneseTestCase {

	private static String SALVAGE_ID;
	private static String LOW_LT_30;
	private static String LOW_GT_30;
	private static String HIGH_LT_60;
	private static String HIGH_GT_60;
	private static String TODAY;

	@Test
	public void testCreateSalvage() {
		selenium.click("//a[contains(@href, 'lf_salvage.do?createNew=1')]");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Please save the salvage to begin adding items."));
			verifyFalse(selenium.isElementPresent("//input[@id='addBarcode']"));
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to navigate to the salvage page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The salvage was successfully saved."));
			verifyTrue(selenium.isElementPresent("//input[@id='addBarcode']"));
			LF_Salvage.SALVAGE_ID = selenium.getValue("//input[@id='salvageId']");
			System.out.println("LFS: Created salvage: " + LF_Salvage.SALVAGE_ID);
			selenium.click("//a[contains(@href, 'lf_search_salvage.do?clear=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to save salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			selenium.type("//input[@id='sId']", LF_Salvage.SALVAGE_ID);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to navigate to the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]"));
			selenium.click("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Salvage Id: " + LF_Salvage.SALVAGE_ID + " was not found on the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//input[@id='salvageId']"));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
		} else {
			System.out.println("LFS: An error occurred when attempting to navigate back to the salvage page from the salvage search page.");
			return;
		}

	}

	@Test
	public void testCreateFoundItems() {
		SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy");
		String lvCutoff = "";
		String hvCutoff = "";
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();

		// Low value, < 30 days
		if (checkNoErrorPage()) {
			LF_Salvage.LOW_LT_30 = String.valueOf(System.currentTimeMillis());
			selenium.type("//input[@name='found.barcode']", LF_Salvage.LOW_LT_30);
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			LF_Salvage.TODAY = selenium.getValue("//input[@name='disReceivedDate']");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "PN1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Backbeat");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[6]/td/input", "Low value, < 30 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when attempting to navigate to the Found Item page from the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created Low value, < 30 Found Item: " + LF_Salvage.LOW_LT_30);
			selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: LOW_LT_30.");
			return;
		}

		// Low value, > 30 days
		if (checkNoErrorPage()) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_YEAR, -40);
			lvCutoff = df.format(now.getTime());

			LF_Salvage.LOW_GT_30 = String.valueOf(System.currentTimeMillis());
			selenium.type("//input[@name='found.barcode']", LF_Salvage.LOW_GT_30);
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td[2]/input", lvCutoff);
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "PN5678");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Backbeat");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[6]/td/input", "Low value, > 30 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out
					.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created Low value, > 30 Found Item: " + LF_Salvage.LOW_GT_30 + " with date: " + lvCutoff);
			selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: LOW_GT_30.");
			return;
		}

		// High value, < 60 days
		if (checkNoErrorPage()) {

			LF_Salvage.HIGH_LT_60 = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_Salvage.HIGH_LT_60);
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[2]/img");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/select", "label=High");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "PN90123");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Backbeat");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[6]/td/input", "High value, < 60 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out
					.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created High value, < 60 Found Item: " + LF_Salvage.HIGH_LT_60);
			selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: HIGH_LT_60.");
			return;
		}

		// High value, > 60 days
		if (checkNoErrorPage()) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_YEAR, -61);
			hvCutoff = df.format(now.getTime());

			LF_Salvage.HIGH_GT_60 = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_Salvage.HIGH_GT_60);
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td[2]/input", hvCutoff);
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/select", "label=High");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Plantronics");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "PN4567");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Backbeat");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone Accessories");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/select", "label=Cordless Ear Plug");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[6]/td/input", "High value, > 60 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out
					.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyEquals("600", selenium.getValue("//div[@id='maincontent']/table/tbody/tr[2]/td[3]/select"));
			verifyEquals("0", selenium.getValue("//div[@id='maincontent']/table/tbody/tr[2]/td[4]/select"));
			System.out.println("LFS: Created High value, > 60 Found Item: " + LF_Salvage.HIGH_GT_60 + " with date: " + hvCutoff);
		} else {
			System.out.println("LFS: Failed to save found item: HIGH_GT_60.");
			return;
		}
	}

	@Test
	public void testAddItemsToSalvage() {
		selenium.click("//a[contains(@href, 'lf_search_salvage.do?clear=1')]");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			selenium.type("//input[@id='sId']", LF_Salvage.SALVAGE_ID);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to load the salvage search page from the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: the search page returned no results for salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//input[@id='salvageId']"));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
		} else {
			System.out.println("LFS: failed to load salvage: " + LF_Salvage.SALVAGE_ID + " from the salvage search page.");
			return;
		}

		// try to enter LOW_LT_30
		selenium.type("//input[@id='addBarcode']", LF_Salvage.LOW_LT_30);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyPressNative("10");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item: " + LF_Salvage.LOW_LT_30 + " was received on: " + LF_Salvage.TODAY + " and cannot be salvaged before: 30 days."));
		} else {
			System.out.println("LFS: failed to enter LOW_LT_30: " + LF_Salvage.LOW_LT_30);
			return;
		}

		// try to enter LOW_GT_30
		selenium.type("//input[@id='addBarcode']", LF_Salvage.LOW_GT_30);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyPressNative("10");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(LF_Salvage.LOW_GT_30));
			verifyTrue(selenium.isTextPresent("Cellphone Accessories, Cordless Ear Plug, Plantronics, Backbeat, PN5678"));
		} else {
			System.out.println("LFS: failed to enter LOW_GT_30: " + LF_Salvage.LOW_GT_30);
			return;
		}

		// try to enter HIGH_LT_60
		selenium.type("//input[@id='addBarcode']", LF_Salvage.HIGH_LT_60);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyPressNative("10");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item: " + LF_Salvage.HIGH_LT_60 + " was received on: " + LF_Salvage.TODAY + " and cannot be salvaged before: 60 days."));
		} else {
			System.out.println("LFS: failed to enter HIGH_LT_60: " + LF_Salvage.HIGH_LT_60);
			return;
		}

		// try to enter HIGH_GT_60
		selenium.type("//input[@id='addBarcode']", LF_Salvage.HIGH_GT_60);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyPressNative("10");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(LF_Salvage.HIGH_GT_60));
			verifyTrue(selenium.isTextPresent("Cellphone Accessories, Cordless Ear Plug, Plantronics, Backbeat, PN4567"));
			selenium.click("//a[@id='menucol_0.0']/u");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to enter HIGH_GT_60: " + LF_Salvage.HIGH_GT_60);
			return;
		}

		if (checkNoErrorPage()) {
			selenium.type("//input[@id='barcode']", LF_Salvage.HIGH_GT_60);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to load the task manager.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyEquals("601", selenium.getValue("//div[@id='maincontent']/table/tbody/tr[2]/td[3]/select"));
			verifyEquals("4", selenium.getValue("//div[@id='maincontent']/table/tbody/tr[2]/td[4]/select"));
			selenium.click("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to navigate back to Found Item HIGH_GT_60: " + LF_Salvage.HIGH_GT_60);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//input[@id='salvageId']"));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to navigate back to salvage Id: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The salvage was successfully saved."));
			verifyFalse(selenium.isTextPresent(LF_Salvage.LOW_LT_30));
			verifyTrue(selenium.isTextPresent(LF_Salvage.LOW_GT_30));
			verifyFalse(selenium.isTextPresent(LF_Salvage.HIGH_LT_60));
			verifyTrue(selenium.isTextPresent(LF_Salvage.HIGH_GT_60));
		} else {
			System.out.println("LFS: An error occurred while attempting to save salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		selenium.type("//input[@id='addBarcode']", LF_Salvage.LOW_GT_30);
		selenium.focus("//input[@id='addBarcode']");
		selenium.keyPressNative("10");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		verifyTrue(selenium.isTextPresent("Item: " + LF_Salvage.LOW_GT_30 + " has already been added to this salvage."));

	}

	@Test
	public void testAddToAnotherSalvage() {
		selenium.click("//a[contains(@href, 'lf_salvage.do?createNew=1')]");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to create a new salvage for adding duplicate items.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The salvage was successfully saved."));
			selenium.type("//input[@id='addBarcode']", LF_Salvage.LOW_GT_30);
			selenium.focus("//input[@id='addBarcode']");
			selenium.keyPressNative("10");
		} else {
			System.out.println("LFS: failed to save the new salvage for adding duplicate items.");
			return;
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item: " + LF_Salvage.LOW_GT_30 + " was already included in salvage: " + LF_Salvage.SALVAGE_ID));
			selenium.select("//select[@id='statusId']", "label=Closed");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: an error occurred while attempting to add duplicate item LOW_GT_30: " + LF_Salvage.LOW_GT_30);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The salvage was successfully saved."));
		} else {
			System.out.println("LFS: an error occurred while attempting to close duplicate savlage.");
			return;
		}

	}

	@Test
	public void testCloseSalvage() {
		selenium.click("//a[contains(@href, 'lf_search_salvage.do?clear=1')]");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			selenium.type("//input[@id='sId']", LF_Salvage.SALVAGE_ID);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to load the salvage search page after closing duplicate salvage.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: the search page returned no results for salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//input[@id='salvageId']"));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
		} else {
			System.out.println("LFS: failed to load salvage: " + LF_Salvage.SALVAGE_ID + " from the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.select("//select[@id='statusId']", "label=Closed");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: an error occurred while attempting to load salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("The salvage was successfully saved."));
		} else {
			System.out.println("LFS: an error occurred while attempting to close original savlage.");
			return;
		}

	}

}
