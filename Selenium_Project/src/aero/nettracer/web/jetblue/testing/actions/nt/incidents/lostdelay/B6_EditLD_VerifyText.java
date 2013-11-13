package aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class B6_EditLD_VerifyText extends DefaultSeleneseTestCase {

	@Test
	public void testVerifyText() throws Exception {
		verifyTrue(isTextPresent("Pawob Information"));
		verifyTrue(isTextPresent("Baggage Itinerary (as listed on claim check)"));
	}

}
