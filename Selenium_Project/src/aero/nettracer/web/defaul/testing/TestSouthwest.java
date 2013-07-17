package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.southwest.testing.actions.nt.core.WN_Login;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay.WN_CreateLD_VerifyRequiredFields;

@RunWith(Suite.class)
@SuiteClasses({
//	 WESTJET
	WN_Login.class, WN_CreateLD_VerifyRequiredFields.class
	})
public class TestSouthwest { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
