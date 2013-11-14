package aero.nettracer.web.azul.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.azul.testing.AD_SeleniumTest;

public class AD_Audit extends AD_SeleniumTest {
	
	@Test
	public void testBasicAuditLoad(){
		navigateToIncidentAuditTrailTest();
	}
}
