package aero.nettracer.web.lfc.testing.actions.lfc.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_CloseLost extends DefaultSeleneseTestCase {

	@Test
	public void testAB_Login() throws Exception {
		verifyEquals(selenium.getValue("name=lost.statusId"), "601");
	}
}
