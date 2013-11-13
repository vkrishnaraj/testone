package aero.nettracer.web.spirit.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.spirit.testing.NK_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.PermissionsUtil;

public class NK_Login extends NK_SeleniumTest {
	
	@Test
	public void testLoginFail() throws Exception {
		LoginUtil.loginFailureTest(driver, BASE_URL, COMPANY_CODE);
	}
	
	@Test
	public void testLoginOGAdmin() throws Exception {
		LoginUtil.loginOGAdminTest(driver, BASE_URL);
	}

	@Test
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
