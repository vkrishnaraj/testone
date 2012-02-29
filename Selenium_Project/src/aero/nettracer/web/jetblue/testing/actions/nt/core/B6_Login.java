package aero.nettracer.web.jetblue.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class B6_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_B6);
		loginAdminProcedure();
		String logStation = selenium.getSelectedLabel("name=cbroStation");
		if (logStation != null && !logStation.equals("CBS")) {
			selenium.select("name=cbroStation", "label=CBS");
			waitForPageToLoadImproved();
		}
	}
	
}
