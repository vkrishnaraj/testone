package aero.nettracer.web.defaul.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.Admin_overall;
import aero.nettracer.web.defaul.testing.actions.Def_Login;
import aero.nettracer.web.defaul.testing.actions.Def_Logout;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({Def_Login.class, Admin_overall.class, Def_Logout.class /* Add more test cases here */})
public class TestAdminOverall {
		
		@BeforeClass
		public static void oneTimeSetUp() {
			SeleniumTestBrowserDefault.initBrowser();
		}
		
		@AfterClass
		public static void oneTimeTearDown() {
			SeleniumTestBrowserDefault.stopBrowser();
		}

}
