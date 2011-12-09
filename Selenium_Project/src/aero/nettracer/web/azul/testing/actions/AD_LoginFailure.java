package aero.nettracer.web.azul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class AD_LoginFailure extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.open(Settings.START_URL_AD);
		loginFailProcedure();
	}
	
}
