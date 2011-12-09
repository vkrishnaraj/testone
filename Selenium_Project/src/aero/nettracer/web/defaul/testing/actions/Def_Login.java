package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class Def_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.open(Settings.START_URL);
		loginAdminProcedure();
	}
	
}
