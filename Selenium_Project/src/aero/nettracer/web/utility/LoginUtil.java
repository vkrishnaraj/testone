package aero.nettracer.web.utility;


public class LoginUtil extends DefaultSeleneseTestCase {
	
	public void loginAdminProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_ADMIN + " " + Settings.PASSWORD_ADMIN);
		selenium.type("username", Settings.USERNAME_ADMIN);
		selenium.type("password", Settings.PASSWORD_ADMIN);
		selenium.click("button");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginTestProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_TEST);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_TEST);
		selenium.click("button");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginFailProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_CHANGE);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_CHANGE);
		selenium.click("button");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Invalid username and/or password, please try again"));
	}
	
	public void waitForPageToLoadImproved() {
		selenium.waitForPageToLoad(Settings.LOGIN_TIMEOUT);
	}

	
}
