package aero.nettracer.web.defaul.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.CreateNewLD;
import aero.nettracer.web.defaul.testing.actions.Login;

@RunWith(Suite.class)
@SuiteClasses({Login.class, CreateNewLD.class})
public class TestCreateNewIncident {

	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}

	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}

}
