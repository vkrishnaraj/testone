package aero.nettracer.web.azul.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class AD_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(isTextPresent("Incident Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Trace Results"));
		verifyTrue(isTextPresent("Incoming Incidents"));
		verifyTrue(isTextPresent("Created Requests"));

		verifyTrue(isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Forward Bags to LZ"));
		verifyTrue(isTextPresent("Inbound Bags"));
		verifyTrue(isTextPresent("Incoming Requests"));
		verifyTrue(isTextPresent("Bags To Be Delivered"));
		verifyTrue(isTextPresent("Mass On-hand(s)"));
		verifyTrue(isTextPresent("On-hand Bags"));

		verifyTrue(isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Claims To Be Processed"));
		verifyTrue(isTextPresent("Expense Requests"));
		verifyTrue(isTextPresent("Approved Payments"));

		verifyTrue(isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Inbox"));
		verifyTrue(isTextPresent("Other Tasks"));
		checkCopyrightAndQuestionMarks();
	}
	
}
