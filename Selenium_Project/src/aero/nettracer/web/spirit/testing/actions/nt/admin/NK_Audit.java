package aero.nettracer.web.spirit.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.spirit.testing.NK_SeleniumTest;

public class NK_Audit extends NK_SeleniumTest {
	
	@Test
	public void testBasicAuditLoad(){
		navigateToIncidentAuditTrailTest();
	}
}
