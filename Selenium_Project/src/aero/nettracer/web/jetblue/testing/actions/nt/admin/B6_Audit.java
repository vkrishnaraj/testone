package aero.nettracer.web.jetblue.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.jetblue.testing.B6_SeleniumTest;

public class B6_Audit extends B6_SeleniumTest {
	
	@Test
	public void testBasicAuditLoad(){
		navigateToIncidentAuditTrailTest();
	}
}
