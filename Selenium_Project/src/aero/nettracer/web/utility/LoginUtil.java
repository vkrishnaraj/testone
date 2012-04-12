package aero.nettracer.web.utility;


public class LoginUtil extends DefaultSeleneseTestCase {
	
	public void loginAdminProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_ADMIN + " " + Settings.PASSWORD_ADMIN);
		selenium.type("username", Settings.USERNAME_ADMIN);
		selenium.type("password", Settings.PASSWORD_ADMIN);
		selenium.click("button");
		waitForPageToLoadImproved();
		boolean pleaseTryAgain = selenium.isTextPresent("Invalid username and/or password, please try again");
		boolean lockedOut = selenium.isTextPresent("This agent has been locked out due to excessive failed login attempts. Please contact system administrator.");
		verifyFalse(pleaseTryAgain || lockedOut);
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginTestProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_TEST);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_TEST);
		selenium.click("button");
		waitForPageToLoadImproved();
		boolean pleaseTryAgain = selenium.isTextPresent("Invalid username and/or password, please try again");
		boolean lockedOut = selenium.isTextPresent("This agent has been locked out due to excessive failed login attempts. Please contact system administrator.");
		verifyFalse(pleaseTryAgain || lockedOut);
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void loginFailProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_TEST + " " + Settings.PASSWORD_CHANGE);
		selenium.type("username", Settings.USERNAME_TEST);
		selenium.type("password", Settings.PASSWORD_CHANGE);
		selenium.click("button");
		waitForPageToLoadImproved();
		boolean pleaseTryAgain = selenium.isTextPresent("Invalid username and/or password, please try again");
		boolean lockedOut = selenium.isTextPresent("This agent has been locked out due to excessive failed login attempts. Please contact system administrator.");
		verifyTrue(pleaseTryAgain || lockedOut);
	}
	
	public void loginOGAdminProcedure() throws Exception {
		System.out.println("Logging In Using " + Settings.USERNAME_OGADMIN + " " + Settings.PASSWORD_OGADMIN);
		selenium.select("name=companyCode", "label=Owens Group");
		selenium.type("username", Settings.USERNAME_OGADMIN);
		selenium.type("password", Settings.PASSWORD_OGADMIN);
		selenium.click("button");
		waitForPageToLoadImproved();
		boolean pleaseTryAgain = selenium.isTextPresent("Invalid username and/or password, please try again");
		boolean lockedOut = selenium.isTextPresent("This agent has been locked out due to excessive failed login attempts. Please contact system administrator.");
		verifyFalse(pleaseTryAgain || lockedOut);
		verifyTrue(selenium.isTextPresent("Task Manager Home"));
	}
	
	public void waitForPageToLoadImproved() {
		selenium.waitForPageToLoad(Settings.LOGIN_TIMEOUT);
	}

	
}
