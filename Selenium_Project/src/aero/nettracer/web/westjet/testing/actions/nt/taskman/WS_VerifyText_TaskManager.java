package aero.nettracer.web.westjet.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WS_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();

		verifyTrue(isTextPresent("PIR/DPR Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Trace Results"));
		verifyTrue(isTextPresent("Local PIRs and DPRs"));
		verifyTrue(isTextPresent("Local Delayed"));
		verifyTrue(isTextPresent("Local Pilferage"));
		verifyTrue(isTextPresent("Local Damaged"));
		verifyTrue(isTextPresent("Local PIRs and DPRs Assigned in Last 24 Hours"));
		verifyTrue(isTextPresent("Local ROHs"));
		verifyTrue(isTextPresent("Temporary PIR(s)"));

		verifyTrue(isTextPresent("OHD Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Forward Baggage to LZ"));
		verifyTrue(isTextPresent("Inbound Expedite Baggage"));
		verifyTrue(isTextPresent("Incoming ROH"));
		verifyTrue(isTextPresent("Baggage To Be Delivered"));
		verifyTrue(isTextPresent("Temporary OHD(s)"));
		verifyTrue(isTextPresent("Mass OHD(s)"));
		verifyTrue(isTextPresent("Local OHDs"));
		verifyTrue(isTextPresent("Forward Copies"));

		verifyTrue(isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Claims To Be Processed"));
		verifyTrue(isTextPresent("Created Interim Expense Requests"));
		verifyTrue(isTextPresent("Expense Requests"));
		verifyTrue(isTextPresent("Approved Payments"));

		verifyTrue(isTextPresent("Local Tasks")); //SECTION HEADER
		verifyTrue(isTextPresent("Inbox"));
		verifyTrue(isTextPresent("Local Tasks"));
		verifyTrue(isTextPresent("Local Guest Messages"));
		verifyTrue(isTextPresent("Bag Buzz"));
		verifyTrue(isTextPresent("Manage Fault Dispute"));
		checkCopyrightAndQuestionMarks();
	}
	
}
