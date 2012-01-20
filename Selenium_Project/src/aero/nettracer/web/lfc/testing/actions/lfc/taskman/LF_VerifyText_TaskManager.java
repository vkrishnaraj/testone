package aero.nettracer.web.lfc.testing.actions.lfc.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class LF_VerifyText_TaskManager extends LoginUtil {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		
//		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
//		verifyTrue(selenium.isTextPresent("Pending Fraud Check Requests"));
		
		checkCopyrightAndQuestionMarks();
	}

	@Test
	public void testFoundItemField() throws Exception {
		Settings.FOUND_ID_LF = "1326484684085";
		checkCopyrightAndQuestionMarks();
		verifyTrue(selenium.isTextPresent("Load Found Item"));
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[2]/td/center/input[2]");
		assertEquals("Please enter a valid barcode", selenium.getAlert());
		selenium.type("//div[@id='maincontent']/form/table/tbody/tr[2]/td/center/input", Settings.FOUND_ID_LF);
		selenium.click("//div[@id='maincontent']/form/table/tbody/tr[2]/td/center/input[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyEquals(Settings.FOUND_ID_LF, selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input"));
		} else {
			return;
		}
	}
	
}
