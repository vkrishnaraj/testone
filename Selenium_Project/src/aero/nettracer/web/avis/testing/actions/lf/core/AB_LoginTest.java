package aero.nettracer.web.avis.testing.actions.lf.core;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class AB_LoginTest extends LoginUtil {

	@Test
	public void testAB_Login() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_AB);
		loginTestProcedure();
	}
}
