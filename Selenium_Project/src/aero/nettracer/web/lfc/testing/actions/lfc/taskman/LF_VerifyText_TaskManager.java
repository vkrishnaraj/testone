package aero.nettracer.web.lfc.testing.actions.lfc.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class LF_VerifyText_TaskManager extends LoginUtil {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		
		verifyTrue(selenium.isTextPresent("Load Found Item")); //SECTION HEADER
		
		verifyTrue(selenium.isTextPresent("Lost and Found Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Trace Results"));
		verifyTrue(selenium.isTextPresent("Open Lost Reports"));
		verifyTrue(selenium.isTextPresent("Open Found Items"));
		verifyTrue(selenium.isTextPresent("Items to Salvage"));
		verifyTrue(selenium.isTextPresent("Items to Deliver"));
		
		checkCopyrightAndQuestionMarks();
	}

	@Test
	public void testFoundItemField() throws Exception {
//		Settings.FOUND_ID_LF = "1326484684085";
		checkCopyrightAndQuestionMarks();
		verifyTrue(selenium.isTextPresent("Load Found Item"));
		selenium.click("//input[@id='button']");
		assertEquals("Please enter a valid barcode", selenium.getAlert());
//		selenium.type("//div[@id='maincontent']/form/table/tbody/tr[2]/td/center/input", Settings.FOUND_ID_LF);
//		selenium.click("//input[@id='button']");
//		waitForPageToLoadImproved();
//		if (checkNoErrorPage()) {
//			verifyEquals(Settings.FOUND_ID_LF, selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input"));
//		} else {
//			return;
//		}
	}
	
}
