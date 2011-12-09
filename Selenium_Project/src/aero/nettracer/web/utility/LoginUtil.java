package aero.nettracer.web.utility;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LoginUtil extends DefaultSeleneseTestCase {
	
	public void loginAdminProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_ADMIN + " " + Settings.PASSWORD_ADMIN);
		selenium.type("username", Settings.USERNAME_ADMIN);
		selenium.type("password", Settings.PASSWORD_ADMIN);
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginTestProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_TEST);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_TEST);
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginFailProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_CHANGE);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_CHANGE);
		selenium.click("button");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Invalid username and/or password, please try again"));
	}

	
}
