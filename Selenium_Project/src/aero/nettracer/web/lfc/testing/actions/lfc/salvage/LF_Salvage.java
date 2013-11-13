package aero.nettracer.web.lfc.testing.actions.lfc.salvage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_Salvage extends DefaultSeleneseTestCase {

	private static String SALVAGE_ID;
	private static String LOW_LT_30;
	private static String LOW_GT_30;
	private static String HIGH_LT_60;
	private static String HIGH_GT_60;
	private static String TODAY;
	private static int TIMEOUT = 2000;
	
	private static long DATE_DELTA_31 = 2678400000l;
	private static long DATE_DELTA_61 = 5270400000l;

	@Test
	public void testCreateSalvage() {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Please save the salvage to begin adding items."));
			verifyFalse(isElementPresent(By.xpath("//input[@id='addBarcode']")));
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when trying to navigate to the salvage page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The salvage was successfully saved."));
			verifyTrue(isElementPresent(By.xpath("//input[@id='addBarcode']")));
			LF_Salvage.SALVAGE_ID = selenium.getValue("//input[@id='salvageId']");
			System.out.println("LFS: Created salvage: " + LF_Salvage.SALVAGE_ID);
			clickMenu("menucol_4.2");
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
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]")));
			selenium.click("//a[contains(@href, 'lf_salvage.do?id=" + LF_Salvage.SALVAGE_ID + "')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Salvage Id: " + LF_Salvage.SALVAGE_ID + " was not found on the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//input[@id='salvageId']")));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
		} else {
			System.out.println("LFS: An error occurred when attempting to navigate back to the salvage page from the salvage search page.");
			return;
		}

	}

	@Test
	public void testCreateFoundItems() {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String lvCutoff = "";
		String hvCutoff = "";
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();

		// Low value, < 30 days
		if (checkNoErrorPage()) {
			LF_Salvage.LOW_LT_30 = String.valueOf(System.currentTimeMillis());
			selenium.type("//input[@name='found.barcode']", LF_Salvage.LOW_LT_30);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			LF_Salvage.TODAY = selenium.getValue("//input[@name='disReceivedDate']");
			selenium.type("name=item[0].brand", "Plantronics");
			selenium.type("name=item[0].serialNumber", "PN1234");
			selenium.type("name=item[0].model", "Backbeat");
			selenium.select("id=category_0", "label=Cellphone Accessories");
			selenium.select("id=subcategories_0", "label=Cordless Ear Plug");
			selenium.select("name=item[0].color", "label=Black");
			selenium.select("name=item[0].caseColor", "label=Black");
			selenium.type("name=item[0].description", "Low value, < 30 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when attempting to navigate to the Found Item page from the salvage search page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created Low value, < 30 Found Item: " + LF_Salvage.LOW_LT_30);
			clickMenu("menucol_2.1");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: LOW_LT_30.");
			return;
		}

		// Low value, > 30 days
		if (checkNoErrorPage()) {
			Date now = new Date();
			now.setTime(now.getTime() - DATE_DELTA_31);
//			Calendar now = Calendar.getInstance();
//			now.add(Calendar.DAY_OF_YEAR, -31);
			lvCutoff = df.format(now);

			LF_Salvage.LOW_GT_30 = String.valueOf(System.currentTimeMillis());
			selenium.type("//input[@name='found.barcode']", LF_Salvage.LOW_GT_30);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td[2]/input", "02/11/2012");
			selenium.type("name=item[0].brand", "Plantronics");
			selenium.type("name=item[0].serialNumber", "PN1234");
			selenium.type("name=item[0].model", "Backbeat");
			selenium.select("id=category_0", "label=Cellphone Accessories");
			selenium.select("id=subcategories_0", "label=Cordless Ear Plug");
			selenium.select("name=item[0].color", "label=Black");
			selenium.select("name=item[0].caseColor", "label=Black");
			selenium.type("name=item[0].description", "Low value, < 30 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out
					.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created Low value, > 30 Found Item: " + LF_Salvage.LOW_GT_30 + " with date: " + lvCutoff);
			clickMenu("menucol_2.1");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: LOW_GT_30.");
			return;
		}

		// High value, < 60 days
		if (checkNoErrorPage()) {

			LF_Salvage.HIGH_LT_60 = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_Salvage.HIGH_LT_60);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[2]/img");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.select("id=itemvalue_0", "label=High");
			selenium.type("name=item[0].brand", "Plantronics");
			selenium.type("name=item[0].serialNumber", "PN1234");
			selenium.type("name=item[0].model", "Backbeat");
			selenium.select("id=category_0", "label=Cellphone Accessories");
			selenium.select("id=subcategories_0", "label=Cordless Ear Plug");
			selenium.select("name=item[0].color", "label=Black");
			selenium.select("name=item[0].caseColor", "label=Black");
			selenium.type("name=item[0].description", "High value, < 60 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out
					.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			System.out.println("LFS: Created High value, < 60 Found Item: " + LF_Salvage.HIGH_LT_60);
			clickMenu("menucol_2.1");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: Failed to save found item: HIGH_LT_60.");
			return;
		}

		// High value, > 60 days
		if (checkNoErrorPage()) {
			Date now = new Date();
			now.setTime(now.getTime() - DATE_DELTA_61);
//			Calendar now = Calendar.getInstance();
//			now.add(Calendar.DAY_OF_YEAR, -61);
			hvCutoff = df.format(now);

			LF_Salvage.HIGH_GT_60 = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_Salvage.HIGH_GT_60);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td[2]/input", hvCutoff);
			selenium.select("id=itemvalue_0", "label=High");
			selenium.type("name=item[0].brand", "Plantronics");
			selenium.type("name=item[0].serialNumber", "PN1234");
			selenium.type("name=item[0].model", "Backbeat");
			selenium.select("id=category_0", "label=Cellphone Accessories");
			selenium.select("id=subcategories_0", "label=Cordless Ear Plug");
			selenium.select("name=item[0].color", "label=Black");
			selenium.select("name=item[0].caseColor", "label=Black");
			selenium.type("name=item[0].description", "High value, < 60 days");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: An error occurred when attempting to load the Found Item page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Your found item was successfully saved."));
			verifyEquals("600",selenium.getValue("name=found.statusId"));
			verifyEquals("0", selenium.getValue("name=found.itemLocation"));
			System.out.println("LFS: Created High value, > 60 Found Item: " + LF_Salvage.HIGH_GT_60 + " with date: " + hvCutoff);
		} else {
			System.out.println("LFS: Failed to save found item: HIGH_GT_60.");
			return;
		}
	}

	@Test
	public void testAddItemsToSalvage() {
		clickMenu("menucol_4.2");
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
			verifyTrue(isElementPresent(By.xpath("//input[@id='salvageId']")));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
		} else {
			System.out.println("LFS: failed to load salvage: " + LF_Salvage.SALVAGE_ID + " from the salvage search page.");
			return;
		}

		// try to enter LOW_LT_30

		driver.findElement(By.id("addBoxId")).sendKeys("BOX1" + Keys.ENTER);
		driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.LOW_LT_30 + Keys.ENTER);
		waitForPageToLoadImproved(3000, false);

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item: " + LF_Salvage.LOW_LT_30 + " was received on: " + LF_Salvage.TODAY + " and cannot be salvaged before: 30 days."));
		} else {
			System.out.println("LFS: failed to enter LOW_LT_30: " + LF_Salvage.LOW_LT_30);
			return;
		}

		// try to enter LOW_GT_30
		driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.LOW_GT_30 + Keys.ENTER);
		waitForPageToLoadImproved(3000, false);
		try {
			synchronized (selenium) {
				selenium.wait(LF_Salvage.TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent(LF_Salvage.LOW_GT_30));
			verifyTrue(isTextPresent("Cellphone Accessories, Cordless Ear Plug, Plantronics,"));
		} else {
			System.out.println("LFS: failed to enter LOW_GT_30: " + LF_Salvage.LOW_GT_30);
			return;
		}

		// try to enter HIGH_LT_60
		driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.HIGH_LT_60 + Keys.ENTER);
		waitForPageToLoadImproved(3000, false);
		try {
			synchronized (selenium) {
				selenium.wait(LF_Salvage.TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item: " + LF_Salvage.HIGH_LT_60 + " was received on: " + LF_Salvage.TODAY + " and cannot be salvaged before: 60 days."));
		} else {
			System.out.println("LFS: failed to enter HIGH_LT_60: " + LF_Salvage.HIGH_LT_60);
			return;
		}

		// try to enter HIGH_GT_60
		driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.HIGH_GT_60 + Keys.ENTER);
		waitForPageToLoadImproved(3000, false);
		try {
			synchronized (selenium) {
				selenium.wait(LF_Salvage.TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(checkNoErrorPage()) {
			WebElement element = driver.findElement(By.id("prevBoxId3"));
			element.clear();
			element.sendKeys("BOX2" + Keys.ENTER);
			waitForPageToLoadImproved(3000, false);
			try {
				synchronized (selenium) {
					selenium.wait(LF_Salvage.TIMEOUT);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent(LF_Salvage.HIGH_GT_60));
			verifyTrue(isTextPresent("Cellphone Accessories, Cordless Ear Plug, Plantronics,"));
			clickMenu("menucol_0.0");
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
			verifyEquals("601", selenium.getValue("name=found.statusId"));
			verifyEquals("4", selenium.getValue("name=found.itemLocation"));
			clickMenu("menucol_4.2");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to navigate back to Found Item HIGH_GT_60: " + LF_Salvage.HIGH_GT_60);
			return;
		}
		
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
			verifyTrue(isElementPresent(By.xpath("//input[@id='salvageId']")));
			verifyEquals(LF_Salvage.SALVAGE_ID, selenium.getValue("//input[@id='salvageId']"));
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to navigate back to salvage Id: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The salvage was successfully saved."));
			verifyFalse(isTextPresent(LF_Salvage.LOW_LT_30));
			verifyTrue(isTextPresent(LF_Salvage.LOW_GT_30));
			verifyFalse(isTextPresent(LF_Salvage.HIGH_LT_60));
			verifyTrue(isTextPresent(LF_Salvage.HIGH_GT_60));
		} else {
			System.out.println("LFS: An error occurred while attempting to save salvage: " + LF_Salvage.SALVAGE_ID);
			return;
		}

		driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.LOW_GT_30 + Keys.ENTER);
		waitForPageToLoadImproved(3000, false);

		try {
			synchronized (selenium) {
				selenium.wait(LF_Salvage.TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		verifyTrue(isTextPresent("Item: " + LF_Salvage.LOW_GT_30 + " has already been added to this salvage."));

	}

	@Test
	public void testAddToAnotherSalvage() {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: failed to create a new salvage for adding duplicate items.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The salvage was successfully saved."));
			driver.findElement(By.id("addBarcode")).sendKeys(LF_Salvage.LOW_GT_30 + Keys.ENTER);
			waitForPageToLoadImproved(3000, false);
		} else {
			System.out.println("LFS: failed to save the new salvage for adding duplicate items.");
			return;
		}

		try {
			synchronized (selenium) {
				selenium.wait(LF_Salvage.TIMEOUT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item: " + LF_Salvage.LOW_GT_30 + " was already included in salvage: " + LF_Salvage.SALVAGE_ID));
			selenium.select("//select[@id='statusId']", "label=Closed");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFS: an error occurred while attempting to add duplicate item LOW_GT_30: " + LF_Salvage.LOW_GT_30);
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The salvage was successfully saved."));
		} else {
			System.out.println("LFS: an error occurred while attempting to close duplicate savlage.");
			return;
		}

	}

	@Test
	public void testCloseSalvage() {
		clickMenu("menucol_4.2");
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
			verifyTrue(isElementPresent(By.xpath("//input[@id='salvageId']")));
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
			verifyTrue(isTextPresent("The salvage was successfully saved."));
		} else {
			System.out.println("LFS: an error occurred while attempting to close original savlage.");
			return;
		}

	}

}
