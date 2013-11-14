package aero.nettracer.web.westjet.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_Audit extends WS_SeleniumTest {
	
	@Test
	public void testBasicAuditLoad(){
		navigateToIncidentAuditTrailTest();
	}
}
