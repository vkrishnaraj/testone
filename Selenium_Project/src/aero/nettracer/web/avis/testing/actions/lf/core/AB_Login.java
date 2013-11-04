package aero.nettracer.web.avis.testing.actions.lf.core;

import org.junit.Test;

import aero.nettracer.web.avis.testing.AB_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.PermissionsUtil;

public class AB_Login extends AB_SeleniumTest {
	
	@Test
	public void testLoginFail() throws Exception {
		LoginUtil.loginFailureTest(driver, BASE_URL, null);
	}
	
	public void testNavigateToPermissionsPage() throws Exception {
		verifyTrue(PermissionsUtil.navigateToPermissionsPageTest(driver, BASE_URL, COMPANY_CODE, ADMIN_GROUP_ID));
	}
	
	@Test
	public void testLogout() throws Exception {
		logoutTest();
	}
	
	@Test
	public void testLoginNTAuto() throws Exception {
		LoginUtil.loginNTAutoTest(driver, BASE_URL, null, LZ_STATION);
	}
}
