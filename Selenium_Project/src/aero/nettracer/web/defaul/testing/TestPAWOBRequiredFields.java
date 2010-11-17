package aero.nettracer.web.defaul.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.IntoFirstPAWOB;
import aero.nettracer.web.defaul.testing.actions.LastNameRequired;
import aero.nettracer.web.defaul.testing.actions.Login;

@RunWith(Suite.class)
@SuiteClasses({Login.class, IntoFirstPAWOB.class, LastNameRequired.class})
public class TestPAWOBRequiredFields {

	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}

	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}

}