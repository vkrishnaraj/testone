package aero.nettracer.web.defaul.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class Def_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL);
		loginAdminProcedure();
	}
	
}
