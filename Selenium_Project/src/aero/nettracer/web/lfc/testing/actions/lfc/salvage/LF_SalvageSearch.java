package aero.nettracer.web.lfc.testing.actions.lfc.salvage;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_SalvageSearch extends DefaultSeleneseTestCase {

	private static String OPEN_SALVAGE_ID;
	private static String CLOSED_SALVAGE_ID;
	
	@Test
	public void testCreateSalvages() {

		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: Failed to open salvage page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("The salvage was successfully saved."));
			LF_SalvageSearch.OPEN_SALVAGE_ID = selenium.getValue("//input[@id='salvageId']");
			clickMenu("menucol_4.1");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: Failed to save the open salvage.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: Failed to open salvage page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//select[@id='statusId']", "label=Closed");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: Failed to save the close salvage.");
			return;
		}
		
		verifyTrue(isTextPresent("The salvage was successfully saved."));
		LF_SalvageSearch.CLOSED_SALVAGE_ID = selenium.getValue("//input[@id='salvageId']");
		
	}
	
	@Test
	public void testSalvageSearchPage() {
		clickMenu("menucol_4.2");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.type("//input[@id='sId']", LF_SalvageSearch.OPEN_SALVAGE_ID);
			selenium.select("//select[@id='salvageStatus']", "label=Open");
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[3]/img");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[3]/img[2]");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			verifyEquals("", selenium.getValue("//input[@id='sId']"));
			verifyEquals("-1", selenium.getValue("//select[@id='salvageStatus']"));
			verifyEquals("", selenium.getValue("//input[@id='startTime']"));
			verifyEquals("", selenium.getValue("//input[@id='endTime']"));
			selenium.type("//input[@id='sId']", LF_SalvageSearch.OPEN_SALVAGE_ID);
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: Failed to open salvage search page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'lf_salvage.do?id=" + LF_SalvageSearch.OPEN_SALVAGE_ID + "')]")));
			selenium.select("//select[@id='salvageStatus']", "label=Open");
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: an error occurred when trying to search for all salvages.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'lf_salvage.do?id=" + LF_SalvageSearch.OPEN_SALVAGE_ID + "')]")));
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.select("//select[@id='salvageStatus']", "label=All");
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: an error occurred when trying to search for open salvages.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'lf_salvage.do?id=" + LF_SalvageSearch.OPEN_SALVAGE_ID + "')]")));
			selenium.type("//input[@id='startTime']", "");
			selenium.select("//select[@id='salvageStatus']", "label=Closed");
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFSS: an error occurred when trying to search for all salvages.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'lf_salvage.do?id=" + LF_SalvageSearch.CLOSED_SALVAGE_ID + "')]")));
		} else {
			System.out.println("LFSS: an error occurred when trying to search for closed salvages.");
			return;
		}
	}
	
}
