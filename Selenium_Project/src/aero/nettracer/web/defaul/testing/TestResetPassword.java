package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.Logout;
import aero.nettracer.web.defaul.testing.actions.PasswordReset4nttest;
import aero.nettracer.web.defaul.testing.actions.Login;

@RunWith(Suite.class)
@SuiteClasses({Login.class, PasswordReset4nttest.class /* Add more test cases here */})
public class TestResetPassword { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
