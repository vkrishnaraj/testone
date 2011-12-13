package aero.nettracer.web.defaul.testing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.CreateNewLD;
import aero.nettracer.web.defaul.testing.actions.SearchSpecificPAWOB;
import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Login;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({Def_Login.class, CreateNewLD.class, SearchSpecificPAWOB.class})
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
