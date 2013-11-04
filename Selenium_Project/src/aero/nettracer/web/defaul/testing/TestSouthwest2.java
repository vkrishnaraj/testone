package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.southwest.testing.actions.nt.admin.WN_StatusMessageTest;
import aero.nettracer.web.southwest.testing.actions.nt.claims.WN_CreateClaim;
import aero.nettracer.web.southwest.testing.actions.nt.core.WN_Login;
import aero.nettracer.web.southwest.testing.actions.nt.founds.WN_FoundItem;
import aero.nettracer.web.southwest.testing.actions.nt.taskmanager.WN_CSSCallSystem;
import aero.nettracer.web.southwest.testing.actions.nt.templates.WN_EditTemplates;
import aero.nettracer.web.southwest.testing.actions.nt.templates.WN_SearchTemplates;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.utility.Settings;

@RunWith(Suite.class)
@SuiteClasses({
	WN_Login.class
//	, WN_CSSCallSystem.class
//	, WN_EditTemplates.class
//	, WN_SearchTemplates.class
//	, WN_CreateClaim.class
	, WN_FoundItem.class
//	, WN_StatusMessageTest.class
	})
public class TestSouthwest2 { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser(Settings.PORT3, true);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("NTSOUTHWEST2", true);
	}
	
}
