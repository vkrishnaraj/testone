package aero.nettracer.web.jetblue.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_LoginTest extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.open(Settings.START_URL_B6);
		loginTestProcedure();
	}
	
}
