package aero.nettracer.web.defaul.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class Def_Logout extends DefaultSeleneseTestCase {
	
	@Test
	public void testLogout() throws Exception {
		logout();
	}
}
