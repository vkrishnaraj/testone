package aero.nettracer.web.lfc.testing.actions.lfc.itementry;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

public class LF_ItemEntry extends LoginUtil {

	private static String testId;
	
	@Test
	public void testAlertMessages() {
		selenium.click("//a[contains(@href, 'enter_items.do')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			
			selenium.select("//select[@id='foundLocationId']", "label=Please select...");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Item Received Date is required.", selenium.getAlert());
			
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
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
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Bags"));
			verifyTrue(selenium.isTextPresent("Status:  Entered"));
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to enter an item after checking the alert messages.");
			return;
		}
		
	}
		
//	@Test
//	public void testLastNamePresent() {
//		testId = String.valueOf(System.currentTimeMillis());
//		selenium.type("//input[@id='barcode']", testId);
//		selenium.select("//select[@id='category']", "label=Bags");
//		selenium.click("//input[@id='button']");
//		verifyFalse(selenium.isEditable("//input[@id='button']"));
//		selenium.type("//input[@id='lastName']", "test");
//		selenium.click("//input[@id='saveButton']");
//		waitForPageToLoadImproved();
//		
//		if (checkNoErrorPage()) {
//			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
//			verifyTrue(selenium.isTextPresent("Desc: Bags"));
//			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
//			verifySummaryDiv(1);
//			clearSummaryDivs();
//		} else {
//			System.out.println("LFIE: Failed to save item with last name present.");
//			return;
//		}
//	}
	
//	@Test
//	public void testFirstNamePresent() {
//		testId = String.valueOf(System.currentTimeMillis());
//		selenium.type("//input[@id='barcode']", testId);
//		selenium.select("//select[@id='category']", "label=Blueprints/Posters");
//		selenium.click("//select[@id='category']/option[3]");
//		selenium.click("//input[@id='button']");
//		selenium.type("//input[@id='firstName']", "test");
//		selenium.click("//input[@id='saveButton']");
//		waitForPageToLoadImproved();
//		
//		if (checkNoErrorPage()) {
//			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
//			verifyTrue(selenium.isTextPresent("Desc: Blueprints/Posters"));
//			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
//			verifySummaryDiv(2);
//			clearSummaryDivs();
//		} else {
//			System.out.println("LFIE: Failed to save item with first name present.");
//			return;			
//		}
//
//	}
	
//	@Test
//	public void testMiddleNamePresent() {
//		testId = String.valueOf(System.currentTimeMillis());
//		selenium.type("//input[@id='barcode']", testId);
//		selenium.select("//select[@id='category']", "label=Books/Notebooks");
//		selenium.click("//select[@id='category']/option[4]");
//		selenium.click("//input[@id='button']");
//		selenium.type("//input[@id='middleName']", "t");
//		selenium.click("//input[@id='saveButton']");
//		waitForPageToLoadImproved();
//		
//		if (checkNoErrorPage()) {
//			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
//			verifyTrue(selenium.isTextPresent("Desc: Books/Notebooks"));
//			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
//			verifySummaryDiv(2);
//			clearSummaryDivs();
//		} else {
//			System.out.println("LFIE: Failed to save item with middle name present.");
//			return;			
//		}
//		
//	}
	
	@Test
	public void testAddress1Present() {
		testId = String.valueOf(System.currentTimeMillis());
		selenium.type("//input[@id='barcode']", testId);
		selenium.select("//select[@id='category']", "label=Briefcase/Portfolio");
		selenium.click("//select[@id='category']/option[5]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='addr1']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[6]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='addr1']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Camera/Photo Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.select("//select[@id='category']", "label=Cards (ATM/Credit/ID)");
		selenium.click("//select[@id='category']/option[7]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='city']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Cards (ATM/Credit/ID)"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("css=option[value=\"7\"]");
		selenium.click("//input[@id='button']");
		selenium.select("//select[@id='state']", "label=Alabama");
		verifyEquals("United States", selenium.getSelectedLabel("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[5]/select"));
		verifyFalse(selenium.isEditable("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[3]/input"));
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[9]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='province']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//option[@value='9']");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='zip']", "test");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Clothing"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[11]");
		selenium.click("//input[@id='button']");
		selenium.select("//select[@id='country']", "label=Afghanistan");
		verifyFalse(selenium.isEditable("//select[@id='state']"));
		verifyTrue(selenium.isEditable("//input[@id='province']"));
		selenium.select("//select[@id='country']", "label=United States");
		verifyTrue(selenium.isEditable("//select[@id='state']"));
		verifyFalse(selenium.isEditable("//input[@id='province']"));
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Computer-Related"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[12]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='primaryPhone']", "1112223333");
		selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td/select", "label=Home");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[13]");
		selenium.click("//input[@id='button']");
		selenium.type("//input[@id='secondaryPhone']", "4445556666");
		selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/select", "label=Mobile");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[14]");
		selenium.click("//div[@id='itementryleft']/center[2]/input");
		selenium.type("//input[@id='email']", "test@test.com");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Glasses"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
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
		selenium.click("//select[@id='category']/option[15]");
		selenium.select("//div[@id='subcategorydiv']/select", "label=Bracelet");
		selenium.type("//input[@id='brand']", "test");
		selenium.type("//input[@id='model']", "test");
		selenium.type("//input[@id='serialNumber']", "test");
		selenium.select("//select[@id='color']", "label=White");
		selenium.select("//select[@id='caseColor']", "label=White");
		selenium.type("//input[@id='disFoundPhoneNumber']", "7778889999");
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
		selenium.type("//input[@id='primaryPhone']", "0001112222");
		selenium.select("//select[@name='primaryNumberType']", "label=Home");
		selenium.type("//input[@id='secondaryPhone']", "3334445555");
		selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/select", "label=Mobile");
		selenium.type("//input[@id='email']", "test@test.net");
		selenium.click("//input[@id='saveButton']");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: " + testId));
			verifyTrue(selenium.isTextPresent("Desc: Jewelry/Watches, Bracelet, test, test, test"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifySummaryDiv(2);
			clearSummaryDivs();
		} else {
			System.out.println("LFIE: Failed to save item with everything present.");
			return;			
		}

	}
	
	private void verifySummaryDiv(int divId) {
		verifyTrue(selenium.isTextPresent("Bin #:"));
		verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_" + divId + "']/input"));
		verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_" + divId + "']/center/input[@type='button']"));
		verifyFalse(selenium.isEditable("//input[@id='saveButton']"));
		selenium.click("//div[@id='moveDiv_" + divId + "']/center/input");
		verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_" + divId + "']/input"));
		verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_" + divId + "']/center/input[@type='button']"));
		verifyTrue(selenium.isEditable("//input[@id='saveButton']"));
	}
	
	private void clearSummaryDivs() {
		
		if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
			selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
		}
		
		if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
			selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
		}
		
	}
	
}
