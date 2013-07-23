package aero.nettracer.web.southwest.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WN_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_WN);
		loginAdminProcedure();
		String logStation = selenium.getSelectedLabel("name=cbroStation");
		if (logStation != null && !logStation.equals("LZ")) {
			selenium.select("name=cbroStation", "label=LZ");
			waitForPageToLoadImproved();
		}
	}
	
}
