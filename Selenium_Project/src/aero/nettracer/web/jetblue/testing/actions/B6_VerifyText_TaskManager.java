package aero.nettracer.web.jetblue.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		checkCopyrightAndQuestionMarks();

		verifyTrue(selenium.isTextPresent("Pawob Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Trace Results"));
		verifyTrue(selenium.isTextPresent("Incoming Pawobs"));
		verifyTrue(selenium.isTextPresent("Delayed Pawobs"));
		verifyTrue(selenium.isTextPresent("Missing Items Pawobs"));
		verifyTrue(selenium.isTextPresent("Damaged Pawobs"));
		verifyTrue(selenium.isTextPresent("Pawobs Assigned in Last 24 Hours"));
		verifyTrue(selenium.isTextPresent("Created Requests"));
		verifyTrue(selenium.isTextPresent("Temporary Pawob(s)"));

		verifyTrue(selenium.isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Forward Bags to LZ"));
		verifyTrue(selenium.isTextPresent("Inbound Bags"));
		verifyTrue(selenium.isTextPresent("BOH Requests"));
		verifyTrue(selenium.isTextPresent("Bags To Be Delivered"));
		verifyTrue(selenium.isTextPresent("Temporary BOH(s)"));
		verifyTrue(selenium.isTextPresent("Mass BOH(s)"));
		verifyTrue(selenium.isTextPresent("On-hand Bags"));

		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Claims To Be Processed"));
		verifyTrue(selenium.isTextPresent("Expense Requests"));
		verifyTrue(selenium.isTextPresent("Created Expense Requests"));
		verifyTrue(selenium.isTextPresent("Approved Payments"));

		verifyTrue(selenium.isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Email"));
		verifyTrue(selenium.isTextPresent("Other Tasks"));
		verifyTrue(selenium.isTextPresent("Briefing Items"));
		verifyTrue(selenium.isTextPresent("Manage Fault Dispute"));
	}
	
}
