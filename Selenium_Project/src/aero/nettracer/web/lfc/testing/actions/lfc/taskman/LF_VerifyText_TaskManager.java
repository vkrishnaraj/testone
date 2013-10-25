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
		checkCopyrightAndQuestionMarks();
		verifyTrue(selenium.isTextPresent("Load Found Item"));
		selenium.click("id=button");
		waitForPageToLoadImproved(1000, false);
	}
	
}
