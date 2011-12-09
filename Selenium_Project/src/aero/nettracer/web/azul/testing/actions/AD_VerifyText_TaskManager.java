package aero.nettracer.web.azul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class AD_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(selenium.isTextPresent("Incident Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Trace Results"));
		verifyTrue(selenium.isTextPresent("Incoming Incidents"));
		verifyTrue(selenium.isTextPresent("Created Requests"));

		verifyTrue(selenium.isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Forward Bags to LZ"));
		verifyTrue(selenium.isTextPresent("Inbound Bags"));
		verifyTrue(selenium.isTextPresent("Incoming Requests"));
		verifyTrue(selenium.isTextPresent("Bags To Be Delivered"));
		verifyTrue(selenium.isTextPresent("Mass On-hand(s)"));
		verifyTrue(selenium.isTextPresent("On-hand Bags"));

		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Claims To Be Processed"));
		verifyTrue(selenium.isTextPresent("Expense Requests"));
		verifyTrue(selenium.isTextPresent("Approved Payments"));

		verifyTrue(selenium.isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Inbox"));
		verifyTrue(selenium.isTextPresent("Other Tasks"));
		checkCopyrightAndQuestionMarks();
	}
	
}
