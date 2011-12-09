package aero.nettracer.web.defaul.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.AddAirportSSSAndEditItAndRemoveIt;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({AddAirportSSSAndEditItAndRemoveIt.class /* Add more test cases here */})
public class TestAddAirportSSSAndEditItAndRemoveIt {
		
		@BeforeClass
		public static void oneTimeSetUp() {
			SeleniumTestBrowserDefault.initBrowser();
		}
		
		@AfterClass
		public static void oneTimeTearDown() {
			SeleniumTestBrowserDefault.stopBrowser();
		}

}
