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
		verifyTrue(isTextPresent("On-hand Bags in Station"));
		verifyTrue(isTextPresent("Date/Time Modified"));
		verifyTrue(isTextPresent("Position ID"));
		verifyTrue(isTextPresent("Destination"));
		verifyTrue(isTextPresent("Comments"));
		goToTaskManager();
	}
	
	@Test
	public void testVerifyColumnsWithoutPosId() {
		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {false}));
		selenium.click("xpath=(//a[contains(@href, 'onhands.do')])[2]");
		selenium.waitForPageToLoad("30000");
		verifyTrue(isTextPresent("On-hand Bags in Station"));
		verifyTrue(isTextPresent("Date/Time Modified"));
		verifyFalse(isTextPresent("Position ID"));
		verifyTrue(isTextPresent("Destination"));
		verifyTrue(isTextPresent("Comments"));
		goToTaskManager();
	}
	
}
