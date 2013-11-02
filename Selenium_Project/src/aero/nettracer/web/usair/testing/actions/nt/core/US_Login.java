package aero.nettracer.web.usair.testing.actions.nt.core;

import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.usair.testing.US_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;

public class US_Login extends US_SeleniumTest {
	
	@Test
	public void testLogin() throws Exception {
		LoginUtil.loginNTAutoTest(driver, BASE_URL, COMPANY_CODE, LZ_STATION);
		goToTaskManager();
		System.out.println("Test started: " + (new Date()));
		boolean keepGoing = true;
		int count = 0;
		while (keepGoing) {
			if (count % 10 == 0) {
				System.out.println("Still going: " + count);
			}
			count++;
			clickMenu("menucol_10.13");
			waitForPageToLoadImproved();
			keepGoing = selenium.isTextPresent("Data retention length");
		}
		System.out.println("Error occurred: " + (new Date()));
		
	}
	
}
