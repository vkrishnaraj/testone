package aero.nettracer.web.lfc.testing.actions.lfc.taskman;

import org.junit.Test;

import aero.nettracer.web.lfc.testing.LFC_SeleniumTest;

public class LF_VerifyText_TaskManager extends LFC_SeleniumTest {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		
		verifyTrue(isTextPresent("Load Found Item")); //SECTION HEADER
		
		verifyTrue(isTextPresent("Lost and Found Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Trace Results"));
		verifyTrue(isTextPresent("Open Lost Reports"));
		verifyTrue(isTextPresent("Open Found Items"));
		verifyTrue(isTextPresent("Items to Salvage"));
		verifyTrue(isTextPresent("Items to Deliver"));
		
		checkCopyrightAndQuestionMarks();
	}

	@Test
	public void testFoundItemField() throws Exception {
		checkCopyrightAndQuestionMarks();
		verifyTrue(isTextPresent("Load Found Item"));
		selenium.click("id=button");
		waitForPageToLoadImproved(1000, false);
	}
	
}
