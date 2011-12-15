package aero.nettracer.web.westjet.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_LoginTest extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_WS);
		loginTestProcedure();
	}
	
}
