package aero.nettracer.web.lfc.testing.actions.lfc.core;

import org.junit.Test;

import aero.nettracer.web.lfc.testing.LFC_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.PermissionsUtil;

public class LF_Login extends LFC_SeleniumTest {
	
	@Test
	public void testLoginFail() throws Exception {
		LoginUtil.loginFailureTest(driver, BASE_URL, null);
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
		LoginUtil.loginNTAutoTest(driver, BASE_URL, null, LZ_STATION);
	}
	
}
