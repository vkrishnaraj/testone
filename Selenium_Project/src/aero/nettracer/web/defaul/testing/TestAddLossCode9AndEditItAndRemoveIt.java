package aero.nettracer.web.defaul.testing;

import aero.nettracer.web.defaul.testing.actions.AddLossCode9AndEditItAndRemoveIt;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AddLossCode9AndEditItAndRemoveIt.class /* Add more test cases here */})
public class TestAddLossCode9AndEditItAndRemoveIt {
		
		@BeforeClass
		public static void oneTimeSetUp() {
			SeleniumTestBrowserDefault.initBrowser();
		}
		
		@AfterClass
		public static void oneTimeTearDown() {
			SeleniumTestBrowserDefault.stopBrowser();
		}

}
