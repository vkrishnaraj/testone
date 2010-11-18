package aero.nettracer.web.defaul.testing;


import aero.nettracer.web.defaul.testing.actions.AdminEditUser;
import aero.nettracer.web.defaul.testing.actions.Login;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({Login.class, AdminEditUser.class /* Add more test cases here */})
public class TestEditUser { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
