package aero.nettracer.web.lfc.testing.actions.lfc.itementry;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.lfc.testing.LFC_SeleniumTest;

public class LF_ItemEntry extends LFC_SeleniumTest {

	private static String testId;
	
	@Test
	public void testAlertMessages() {
		clickMenu("menucol_3.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();

			selenium.select("//select[@id='foundCompanyId']", "label=Please select...");
			selenium.select("//select[@id='foundLocationId']", "label=Please select...");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item Received Date is required.", selenium.getAlert());
			
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("//input[@id='saveButton']");

			assertEquals("Company is required.", selenium.getAlert());
			
			selenium.select("//select[@name='found.companyId']", "label=SWA");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Found Station is required.", selenium.getAlert());
			
			selenium.select("//select[@name='found.locationId']", "label=LZ");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item ID is required.", selenium.getAlert());
			
			selenium.type("//input[@id='barcode']", "asdf");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item ID is not a valid number.", selenium.getAlert());
			
			selenium.type("//input[@id='barcode']", "-1");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item ID is not a valid number.", selenium.getAlert());
			
			selenium.type("//input[@id='barcode']", "1.2");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item ID is not a valid number.", selenium.getAlert());
			
			testId = String.valueOf(System.currentTimeMillis());
			selenium.type("//input[@id='barcode']", testId);
			selenium.click("//input[@id='saveButton']");
			assertEquals("Category is required.", selenium.getAlert());
			
			selenium.select("//select[@id='category']", "label=Bags");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFIE: Failed to load the Item Entry page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Bags"));
			verifyTrue(isTextPresent("Status:  Entered"));
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to enter an item after checking the alert messages.");
			return;
		}
		
	}
	
	@Test
	public void testAddress1Present() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Briefcase/Portfolio");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='addr1']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(1);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with address 1 present.");
			return;			
		}
		
	}
	
	@Test
	public void testAddress2Present() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Camera/Photo Equipment");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='addr1']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Camera/Photo Equipment"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with address 1 present.");
			return;			
		}
		
	}
	
	@Test
	public void testCityPresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Cards (ATM, Credit, etc.)");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='city']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Cards (ATM, Credit, etc.)"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with city present.");
			return;			
		}
		
	}
	
	@Test
	public void testStatePresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Cellphone");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.select("//select[@id='state']", "label=Alabama");
		verifyEquals("United States", selenium.getSelectedLabel("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[5]/select"));
		verifyFalse(isEditable(By.xpath("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[3]/input")));
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Cellphone"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with state present.");
			return;			
		}
		
	}
	
	@Test
	public void testProvincePresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Cellphone Accessories");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='province']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with province present.");
			return;			
		}
		
	}
	
	@Test
	public void testZipPresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Clothing");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='zip']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Clothing"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with zip present.");
			return;			
		}
		
	}
	
	@Test
	public void testCountryPresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Computer-Related");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.select("//select[@id='country']", "label=Afghanistan");
		verifyFalse(isEditable(By.xpath("//select[@id='state']")));
		verifyTrue(isEditable(By.xpath("//input[@id='province']")));
		selenium.select("//select[@id='country']", "label=United States");
		verifyTrue(isEditable(By.xpath("//select[@id='state']")));
		verifyFalse(isEditable(By.xpath("//input[@id='province']")));
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Computer-Related"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with country present.");
			return;			
		}
		
	}
	
	@Test
	public void testPrimaryPhonePresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Cosmetic/Shaving Kit");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");

		selenium.type("//input[@name='primaryInternationalNumber']", "4"); //556666
		selenium.type("//input[@name='primaryAreaNumber']", "445");
		selenium.type("//input[@name='primaryExchangeNumber']", "556");
		selenium.type("//input[@name='primaryLineNumber']", "666");
		selenium.select("//select[@name='primaryNumberType']", "label=Home");
		selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td/select", "label=Home");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with primary phone number present.");
			return;			
		}
		
	}
	
	@Test
	public void testSecondaryPhonePresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Electronic Equipment");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@name='secondaryInternationalNumber']", "4"); //556666
		selenium.type("//input[@name='secondaryAreaNumber']", "445");
		selenium.type("//input[@name='secondaryExchangeNumber']", "556");
		selenium.type("//input[@name='secondaryLineNumber']", "666");
		selenium.select("//select[@name='secondaryNumberType']", "label=Mobile");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with secondary phone number present.");
			return;			
		}

	}
	
	@Test
	public void testEmailPresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Glasses");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		selenium.click("//div[@id='itementryleft']/center[2]/input");
		selenium.type("//input[@id='email']", "test@test.com");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Glasses"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with email present.");
			return;			
		}
		
	}
	
	@Test
	public void testEverythingPresent() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Jewelry/Watches");
		selenium.select("//select[@name='found.companyId']", "label=SWA");
		selenium.select("//select[@name='found.locationId']", "label=LZ");
		waitForPageToLoadImproved(500, false);
		selenium.select("//div[@id='subcategorydiv']/select", "label=Bracelet");
		selenium.type("//input[@id='brand']", "test");
		selenium.type("//input[@id='model']", "test");
		selenium.type("//input[@id='serialNumber']", "test");
		selenium.select("//select[@id='color']", "label=White");
		selenium.select("//select[@id='caseColor']", "label=White");
		selenium.type("//input[@name='disFoundInternationalNumber']", "7"); //556666
		selenium.type("//input[@name='disFoundAreaNumber']", "778");
		selenium.type("//input[@name='disFoundExchangeNumber']", "889");
		selenium.type("//input[@name='disFoundLineNumber']", "999");
		selenium.type("//input[@id='description']", "test");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='lastName']", "test");
		selenium.type("//input[@id='firstName']", "test");
		selenium.type("id=middleName", "t");
		selenium.type("//input[@id='addr1']", "test");
		selenium.type("//input[@id='addr2']", "test");
		selenium.type("//input[@id='city']", "test");
		selenium.select("//select[@id='state']", "label=Alabama");
		selenium.type("//input[@id='zip']", "test");
		selenium.type("//input[@name='primaryInternationalNumber']", "4"); //556666
		selenium.type("//input[@name='primaryAreaNumber']", "445");
		selenium.type("//input[@name='primaryExchangeNumber']", "556");
		selenium.type("//input[@name='primaryLineNumber']", "666");
		selenium.select("//select[@name='primaryNumberType']", "label=Home");
		selenium.type("//input[@name='secondaryInternationalNumber']", "4"); //556666
		selenium.type("//input[@name='secondaryAreaNumber']", "445");
		selenium.type("//input[@name='secondaryExchangeNumber']", "556");
		selenium.type("//input[@name='secondaryLineNumber']", "666");
		selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/select", "label=Mobile");
		selenium.type("//input[@id='email']", "test@test.net");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Item ID: " + testId));
			verifyTrue(isTextPresent("Desc: Jewelry/Watches, Bracelet, test, test, test"));
			verifyTrue(isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with everything present.");
			return;			
		}

	}
	
	private void verifySummaryDiv(int divId) {
		verifyTrue(isTextPresent("Bin #:"));
		verifyTrue(isElementPresent(By.xpath("//div[@id='moveDiv_" + divId + "']/input")));
		verifyTrue(isElementPresent(By.xpath("//div[@id='moveDiv_" + divId + "']/center/input[@type='button']")));
		waitForPageToLoadImproved(3000, false);
		verifyFalse(isEditable(By.xpath("//input[@id='saveButton']")));
		selenium.click("//div[@id='moveDiv_" + divId + "']/center/input");
		verifyFalse(isElementPresent(By.xpath("//div[@id='moveDiv_" + divId + "']/input")));
		verifyFalse(isElementPresent(By.xpath("//div[@id='moveDiv_" + divId + "']/center/input[@type='button']")));
		waitForPageToLoadImproved(3000, false);
		verifyTrue(isEditable(By.xpath("//input[@id='saveButton']")));
	}
	
	private void clearSummaryDivs() {
		
		if (isElementPresent(By.xpath("//div[@id='moveDiv_1']/center/input[@type='button']"))) {
			selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
		}
		
		if (isElementPresent(By.xpath("//div[@id='moveDiv_0']/center/input[@type='button']"))) {
			selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
		}
		
	}
	
}
