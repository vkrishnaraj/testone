package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;

public class WN_ViewStationOhds extends WN_SeleniumTest {

	@Test
	public void testVerifyColumnsWithPosId() {
		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {true}));
		selenium.click("xpath=(//a[contains(@href, 'onhands.do')])[2]");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("On-hand Bags in Station"));
		verifyTrue(selenium.isTextPresent("Date/Time Modified"));
		verifyTrue(selenium.isTextPresent("Position ID"));
		verifyTrue(selenium.isTextPresent("Destination"));
		verifyTrue(selenium.isTextPresent("Comments"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyColumnsWithoutPosId() {
		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {false}));
		selenium.click("xpath=(//a[contains(@href, 'onhands.do')])[2]");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("On-hand Bags in Station"));
		verifyTrue(selenium.isTextPresent("Date/Time Modified"));
		verifyFalse(selenium.isTextPresent("Position ID"));
		verifyTrue(selenium.isTextPresent("Destination"));
		verifyTrue(selenium.isTextPresent("Comments"));
		goToTaskManager();
	}
	
}
