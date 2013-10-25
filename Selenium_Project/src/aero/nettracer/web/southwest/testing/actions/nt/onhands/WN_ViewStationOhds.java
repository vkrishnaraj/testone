package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_ViewStationOhds extends WN_SeleniumTest {

	@Test
	public void testVerifyColumnsWithPosId() {
		verifyTrue(setCollectPosIdPermission(true));
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
		verifyTrue(setCollectPosIdPermission(false));
		selenium.click("xpath=(//a[contains(@href, 'onhands.do')])[2]");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("On-hand Bags in Station"));
		verifyTrue(selenium.isTextPresent("Date/Time Modified"));
		verifyFalse(selenium.isTextPresent("Position ID"));
		verifyTrue(selenium.isTextPresent("Destination"));
		verifyTrue(selenium.isTextPresent("Comments"));
		goToTaskManager();
	}
	
	private boolean setCollectPosIdPermission(boolean check) {
		if (!navigateToPermissionsPage()) return false;
		if (!isElementPresent("name=635")) return false;
		
		if (check) {
			selenium.check("name=635");			
		} else {
			selenium.uncheck("name=635");
		}
		
		if (!savePermissions()) return false;
		if (!logoutOfNt()) return false;
		if (!loginToNt()) return false;
		
		return true;
	}
	
}
