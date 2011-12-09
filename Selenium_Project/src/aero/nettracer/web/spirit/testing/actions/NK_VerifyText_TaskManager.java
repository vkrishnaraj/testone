package aero.nettracer.web.spirit.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class NK_VerifyText_TaskManager extends DefaultSeleneseTestCase {
	
	@Test
	public void testVerifyText() throws Exception {
		goToTaskManager();
		checkCopyrightAndQuestionMarks();

		verifyTrue(selenium.isTextPresent("Incident Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Trace Results"));
		verifyTrue(selenium.isTextPresent("Incoming Incidents"));
		verifyTrue(selenium.isTextPresent("Delayed Incidents"));
		verifyTrue(selenium.isTextPresent("Pilferage Incidents"));
		verifyTrue(selenium.isTextPresent("Damage Incidents"));
		verifyTrue(selenium.isTextPresent("Incidents Assigned in Last 24 Hours"));
		verifyTrue(selenium.isTextPresent("Created Requests"));
		verifyTrue(selenium.isTextPresent("Temporary Incident(s)"));

		verifyTrue(selenium.isTextPresent("On-Hand Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Forward Bags to LZ"));
		verifyTrue(selenium.isTextPresent("Inbound Bags"));
		verifyTrue(selenium.isTextPresent("Incoming Requests"));
		verifyTrue(selenium.isTextPresent("Bags To Be Delivered"));
		verifyTrue(selenium.isTextPresent("Temporary On-Hand(s)"));
		verifyTrue(selenium.isTextPresent("Mass On-hand(s)"));
		verifyTrue(selenium.isTextPresent("On-hand Bags"));

		verifyTrue(selenium.isTextPresent("Claim Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Claims to Be Processed"));
		verifyTrue(selenium.isTextPresent("Created Interim Expense Requests"));

		verifyTrue(selenium.isTextPresent("Other Tasks")); //SECTION HEADER
		verifyTrue(selenium.isTextPresent("Inbox"));
		verifyTrue(selenium.isTextPresent("Other Tasks"));
		verifyTrue(selenium.isTextPresent("Interim Expense Requests"));
		verifyTrue(selenium.isTextPresent("Bag Buzz"));
		verifyTrue(selenium.isTextPresent("Manage Fault Dispute"));
	}
	
}
