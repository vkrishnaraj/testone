package aero.nettracer.web.usair.testing.actions.nt.core;

import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class US_Login extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		selenium.setTimeout(Settings.LOGIN_TIMEOUT);
		selenium.open(Settings.START_URL_US);
		waitForPageToLoadImproved();
		loginAdminProcedure();
//		String logStation = selenium.getSelectedLabel("name=cbroStation");
//		if (logStation != null && !logStation.equals("YYC")) {
//			selenium.select("name=cbroStation", "label=YYC");
//			waitForPageToLoadImproved();
//		}
		goToTaskManager();
		System.out.println("Test started: " + (new Date()));
		boolean keepGoing = true;
		int count = 0;
		while (keepGoing) {
			if (count % 10 == 0) {
				System.out.println("Still going: " + count);
			}
			count++;
			selenium.click("menucol_10.13");
			waitForPageToLoadImproved();
			keepGoing = selenium.isTextPresent("Data retention length");
		}
		System.out.println("Error occurred: " + (new Date()));
		
	}
	
}
