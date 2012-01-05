package aero.nettracer.web.delta.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class DL_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Pending Fraud Check Requests"));
		
		checkCopyrightAndQuestionMarks();
	}
	
}
