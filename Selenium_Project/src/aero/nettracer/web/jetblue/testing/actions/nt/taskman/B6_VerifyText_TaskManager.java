package aero.nettracer.web.jetblue.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class B6_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(isTextPresent("Pawob Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Trace Results"));
		verifyTrue(isTextPresent("Incoming Pawobs"));
		verifyTrue(isTextPresent("Delayed Pawobs"));
		verifyTrue(isTextPresent("Missing Items Pawobs"));
		verifyTrue(isTextPresent("Damaged Pawobs"));
		verifyTrue(isTextPresent("Pawobs Assigned in Last 24 Hours"));
		verifyTrue(isTextPresent("Created Requests"));
		verifyTrue(isTextPresent("Temporary Pawob(s)"));

		verifyTrue(isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Forward Bags to LZ"));
		verifyTrue(isTextPresent("Inbound Bags"));
		verifyTrue(isTextPresent("BOH Requests"));
		verifyTrue(isTextPresent("Bags To Be Delivered"));
		verifyTrue(isTextPresent("Temporary BOH(s)"));
		verifyTrue(isTextPresent("Mass BOH(s)"));
		verifyTrue(isTextPresent("On-hand Bags"));

		verifyTrue(isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Claims To Be Processed"));
		verifyTrue(isTextPresent("Expense Requests"));
		verifyTrue(isTextPresent("Created Expense Requests"));
		verifyTrue(isTextPresent("Approved Payments"));

		verifyTrue(isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Email"));
		verifyTrue(isTextPresent("Other Tasks"));
		verifyTrue(isTextPresent("Briefing Items"));
		verifyTrue(isTextPresent("Manage Fault Dispute"));
		checkCopyrightAndQuestionMarks();
	}
	
}
