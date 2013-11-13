package aero.nettracer.web.spirit.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class NK_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(isTextPresent("Incident Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Trace Results"));
		verifyTrue(isTextPresent("Incoming Incidents"));
		verifyTrue(isTextPresent("Delayed Incidents"));
		verifyTrue(isTextPresent("Pilferage Incidents"));
		verifyTrue(isTextPresent("Damaged Incidents"));
		verifyTrue(isTextPresent("Incidents Assigned in Last 24 Hours"));
		verifyTrue(isTextPresent("Created Requests"));
		verifyTrue(isTextPresent("Temporary Incident(s)"));

		verifyTrue(isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Forward Bags to LZ"));
		verifyTrue(isTextPresent("Inbound Bags"));
		verifyTrue(isTextPresent("Incoming Requests"));
		verifyTrue(isTextPresent("Bags To Be Delivered"));
		verifyTrue(isTextPresent("Temporary On-hand(s)"));
		verifyTrue(isTextPresent("Mass On-hand(s)"));
		verifyTrue(isTextPresent("On-hand Bags"));

		verifyTrue(isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Claims To Be Processed"));
		verifyTrue(isTextPresent("Created Interim Expense Requests"));

		verifyTrue(isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Inbox"));
		verifyTrue(isTextPresent("Other Tasks"));
		verifyTrue(isTextPresent("Interim Expense Requests"));
		verifyTrue(isTextPresent("Bag Buzz"));
		verifyTrue(isTextPresent("Manage Fault Dispute"));
		checkCopyrightAndQuestionMarks();
	}
	
}
