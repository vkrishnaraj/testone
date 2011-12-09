package aero.nettracer.web.westjet.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		checkCopyrightAndQuestionMarks();

		verifyTrue(selenium.isTextPresent("PIR/DPR Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Trace Results"));
		verifyTrue(selenium.isTextPresent("Local PIRs and DPRs"));
		verifyTrue(selenium.isTextPresent("Local Delayed"));
		verifyTrue(selenium.isTextPresent("Local Pilferage"));
		verifyTrue(selenium.isTextPresent("Local Damaged"));
		verifyTrue(selenium.isTextPresent("Local PIRs and DPRs Assigned in Last 24 Hours"));
		verifyTrue(selenium.isTextPresent("Local ROHs"));
		verifyTrue(selenium.isTextPresent("Temporary PIR(s)"));

		verifyTrue(selenium.isTextPresent("OHD Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Forward Baggage to LZ"));
		verifyTrue(selenium.isTextPresent("Inbound Expedite Baggage"));
		verifyTrue(selenium.isTextPresent("Incoming ROH"));
		verifyTrue(selenium.isTextPresent("Baggage To Be Delivered"));
		verifyTrue(selenium.isTextPresent("Temporary OHD(s)"));
		verifyTrue(selenium.isTextPresent("Local OHD(s)"));
		verifyTrue(selenium.isTextPresent("Forward Copies"));

		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Claims to Be Processed"));
		verifyTrue(selenium.isTextPresent("Created Interim Expense Requests"));
		verifyTrue(selenium.isTextPresent("Approved Payments"));

		verifyTrue(selenium.isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Inbox"));
		verifyTrue(selenium.isTextPresent("Local Tasks"));
		verifyTrue(selenium.isTextPresent("Expense Requests"));
		verifyTrue(selenium.isTextPresent("Local Guest Messages"));
		verifyTrue(selenium.isTextPresent("Bag Buzz"));
		verifyTrue(selenium.isTextPresent("Manage Fault Dispute"));
	}
	
}
