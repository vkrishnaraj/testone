package aero.nettracer.web.westjet.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_Login extends WS_SeleniumTest {
	
	@Test
	public void testLoginFail() throws Exception {
		LoginUtil.loginFailureTest(driver, BASE_URL, COMPANY_CODE);
	}
	
	@Test
	public void testLoginOGAdmin() throws Exception {
		LoginUtil.loginOGAdminTest(driver, BASE_URL);
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
		LoginUtil.loginNTAutoTest(driver, BASE_URL, COMPANY_CODE, LZ_STATION);
	}
}
