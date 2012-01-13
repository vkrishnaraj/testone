package aero.nettracer.web.lfc.testing.actions.lfc.itementry;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

public class LF_ItemEntry extends LoginUtil {

	@Test
	public void testItemEntry1() throws Exception {

		selenium.click("//a[contains(@href, 'enter_items.do')]");

		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td/input", "");
			selenium.click("id=saveButton");
			assertEquals("Item Received Date is required.", selenium.getAlert());
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td/img");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			assertEquals("Item ID is required.", selenium.getAlert());
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "1");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			assertEquals("Category is required.", selenium.getAlert());
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Bags");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 1"));
			verifyTrue(selenium.isTextPresent("Desc: Bags"));
			verifyTrue(selenium.isTextPresent("Status:  Entered"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "2");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Blueprints/Posters");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[2]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
	}

	@Test
	public void testItemEntry2() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 2"));
			verifyTrue(selenium.isTextPresent("Desc: Blueprints/Posters"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_1']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_1']/center/input");

			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 2"));
			verifyTrue(selenium.isTextPresent("Desc: Blueprints/Posters"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "3");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Books/Notebooks");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td[2]/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
	}

	@Test
	public void testItemEntry3() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 3"));
			verifyTrue(selenium.isTextPresent("Desc: Books/Notebooks"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");

			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 3"));
			verifyTrue(selenium.isTextPresent("Desc: Books/Notebooks"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "4");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Briefcase/Portfolio");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td[3]/input", "t");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
	}
	
	@Test
	public void testItemEntry4() throws Exception {

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 4"));
			verifyTrue(selenium.isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 4"));
			verifyTrue(selenium.isTextPresent("Desc: Briefcase/Portfolio"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "5");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Camera/Photo Equipment");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[2]/td[1]/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry5() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 5"));
			verifyTrue(selenium.isTextPresent("Desc: Camera/Photo Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 5"));
			verifyTrue(selenium.isTextPresent("Desc: Camera/Photo Equipment"));
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "6");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Cards (ATM/Credit/ID)");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[2]/td[2]/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry6() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 6"));
			verifyTrue(selenium.isTextPresent("Desc: Cards (ATM/Credit/ID)"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 6"));
			verifyTrue(selenium.isTextPresent("Desc: Cards (ATM/Credit/ID)"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "7");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Cellphone");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[1]/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry7() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 7"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 7"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "8");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Cellphone Accessories");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[2]/select", "label=Alabama");
			verifyFalse(selenium.isEditable("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[3]/input"));
			verifyEquals("US", selenium.getValue("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[5]/select"));
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry8() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 8"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 8"));
			verifyTrue(selenium.isTextPresent("Desc: Cellphone Accessories"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "9");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Clothing");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("id=button");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[3]/input", "test");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
	}
	
	@Test
	public void testItemEntry9() throws Exception {

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 9"));
			verifyTrue(selenium.isTextPresent("Desc: Clothing"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 9"));
			verifyTrue(selenium.isTextPresent("Desc: Clothing"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "10");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Computer-Related");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[5]/select", "label=Afghanistan");
			verifyFalse(selenium.isEditable("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[2]/select"));
			verifyTrue(selenium.isEditable("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[3]/input"));
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry10() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 10"));
			verifyTrue(selenium.isTextPresent("Desc: Computer-Related"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 10"));
			verifyTrue(selenium.isTextPresent("Desc: Computer-Related"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "11");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Cosmetic/Shaving Kit");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[1]/input", "1112223333");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry11() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 11"));
			verifyTrue(selenium.isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/input"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 11"));
			verifyTrue(selenium.isTextPresent("Desc: Cosmetic/Shaving Kit"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "12");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Electronic Equipment");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/input", "1112223333");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry12() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 12"));
			verifyTrue(selenium.isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 12"));
			verifyTrue(selenium.isTextPresent("Desc: Electronic Equipment"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "13");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Glasses");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[3]");
			selenium.click("//div[@id='itementryleft']/center/input");
			verifyFalse(selenium.isEditable("button"));
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[5]/td[1]/input", "test@test.com");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry13() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 13"));
			verifyTrue(selenium.isTextPresent("Desc: Glasses"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			verifyFalse(selenium.isEditable("id=saveButton"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			
			if (selenium.isElementPresent("//div[@id='moveDiv_1']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_1']/center/input[@type='button']");
			}
			
			if (selenium.isElementPresent("//div[@id='moveDiv_0']/center/input[@type='button']")) {
				selenium.click("//div[@id='moveDiv_0']/center/input[@type='button']");
			}
			
			verifyTrue(selenium.isEditable("id=saveButton"));
			verifyTrue(selenium.isTextPresent("Item ID: 13"));
			verifyTrue(selenium.isTextPresent("Desc: Glasses"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td/input", "01/01/2012");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "14");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Jewelry/Watches");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[15]");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[5]/select", "label=High");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry14() throws Exception {
		if (checkNoErrorPage()) {
			verifyEquals("01/01/2012", selenium.getValue("//div[@id='itementryleft']/table/tbody/tr/td/input"));
			verifyEquals("2", selenium.getValue("//div[@id='itementryleft']/table/tbody/tr/td[5]/select"));
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td/img");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr/td[2]/input", "15");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[3]/select", "label=Bags");
			selenium.click("//div[@id='itementryleft']/table/tbody/tr/td[3]/select/option[16]");
			selenium.select("//div[@id='subcategorydiv']/select", "label=Baby Bag");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr/td[5]/select", "label=Low");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr[2]/td[2]/input", "test");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr[2]/td[3]/input", "test");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr[2]/td[4]/select", "label=White");
			selenium.select("//div[@id='itementryleft']/table/tbody/tr[2]/td[5]/select", "label=Black");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr[3]/td/input", "test");
			selenium.type("//div[@id='itementryleft']/table/tbody/tr[3]/td[2]/input", "1112223333");
			selenium.click("//div[@id='itementryleft']/center/input");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td/input", "test");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td[2]/input", "test");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr/td[3]/input", "t");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[2]/td[2]/input", "test");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td/input", "test");
			selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[2]/select", "label=Tennessee");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[3]/td[4]/input", "11111");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td/select", "label=Home");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/input", "4445556666");
			selenium.select("//div[@id='contactInfoDiv']/table/tbody/tr[4]/td[2]/select", "label=Mobile");
			selenium.type("//div[@id='contactInfoDiv']/table/tbody/tr[5]/td/input", "test@test.com");
			selenium.click("//div[@id='itementryleft']/center/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}

	}
	
	@Test
	public void testItemEntry15() throws Exception {
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Item ID: 15"));
			verifyTrue(selenium.isTextPresent("Desc: Bags,test,test"));
			verifyTrue(selenium.isTextPresent("Status:  Verification needed"));
			verifyTrue(selenium.isTextPresent("Bin #:"));
			verifyTrue(selenium.isElementPresent("name=found.binId"));
			verifyTrue(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
			selenium.click("//div[@id='moveDiv_2']/center/input");
			verifyTrue(selenium.isTextPresent("Item ID: 15"));
			verifyTrue(selenium.isTextPresent("Desc: Bags,test,test"));
			verifyTrue(selenium.isTextPresent("Status:  Moved"));
			verifyFalse(selenium.isElementPresent("name=found.binId"));
			verifyFalse(selenium.isElementPresent("//div[@id='moveDiv_2']/center/input[@type='button']"));
		} else {
			return;
		}

	}

}

