package aero.nettracer.web.jetblue.testing.actions.nt.admin;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class B6_Security extends DefaultSeleneseTestCase{
	@Test
	public void testMinPasswordLength(){
//		goToTaskManager();
		selenium.click("id=menucol_8.2");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='navmenucell']/div/dl/dd[4]/a/span[2]");
		waitForPageToLoadImproved();
		selenium.type("min_pass_size", "8");
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		verifyEquals("8", selenium.getValue("min_pass_size"));
		verifyTrue(selenium.isTextPresent("Company Information Saved."));
		
//		goToTaskManager();
		selenium.click("id=menucol_8.2");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='navmenucell']/div/dl/dd[4]/a/span[2]");
		waitForPageToLoadImproved();
		selenium.type("min_pass_size", "7");
		selenium.click("xpath=(//input[@id='button'])[2]");
		assertEquals("Minimum password length must be at least eigth(8) characters", selenium.getAlert());
		verifyEquals("7", selenium.getValue("min_pass_size"));
		verifyTrue(!selenium.isTextPresent("Company Information Saved."));
		
//		goToTaskManager();
		selenium.click("id=menucol_8.2");
		waitForPageToLoadImproved();
		selenium.click("//td[@id='navmenucell']/div/dl/dd[4]/a/span[2]");
		waitForPageToLoadImproved();
		verifyNotEquals("7", selenium.getValue("min_pass_size"));
		
		selenium.type("min_pass_size", "9");
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Company Information Saved."));
	}
}