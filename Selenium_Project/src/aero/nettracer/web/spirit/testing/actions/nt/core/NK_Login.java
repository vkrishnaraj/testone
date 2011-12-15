package aero.nettracer.web.spirit.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class NK_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_NK);
		loginAdminProcedure();
		selenium.select("name=cbroStation", "label=CLAIM");
		waitForPageToLoadImproved();
	}
	
}
