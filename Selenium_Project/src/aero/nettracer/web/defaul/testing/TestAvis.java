package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.avis.testing.actions.lf.core.AB_Login;
import aero.nettracer.web.avis.testing.actions.lf.core.AB_LoginFailure;
import aero.nettracer.web.avis.testing.actions.lf.found.AB_CloseFound;
import aero.nettracer.web.avis.testing.actions.lf.found.AB_CreateFound_Detailed;
import aero.nettracer.web.avis.testing.actions.lf.found.AB_CreateFound_VerifyRequiredFields;
import aero.nettracer.web.avis.testing.actions.lf.found.AB_SearchFound;
import aero.nettracer.web.avis.testing.actions.lf.lost.AB_CloseLost;
import aero.nettracer.web.avis.testing.actions.lf.lost.AB_CreateLost_Detailed;
import aero.nettracer.web.avis.testing.actions.lf.lost.AB_CreateLost_VerifyRequiredFields;
import aero.nettracer.web.avis.testing.actions.lf.lost.AB_SearchLost;
import aero.nettracer.web.avis.testing.actions.lf.match.AB_ConfirmMatch;
import aero.nettracer.web.avis.testing.actions.lf.match.AB_CreateDelivery;
import aero.nettracer.web.avis.testing.actions.lf.match.AB_ManualMatch_FoundToLost;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({
    // AVIS
	AB_LoginFailure.class, AB_Login.class,
	AB_CreateLost_VerifyRequiredFields.class, AB_CreateFound_VerifyRequiredFields.class,
	AB_SearchLost.class, AB_SearchFound.class, AB_ManualMatch_FoundToLost.class,
	AB_CloseFound.class, AB_SearchLost.class, AB_CloseLost.class,
	AB_CreateLost_Detailed.class, AB_CreateFound_Detailed.class,
	AB_ConfirmMatch.class, AB_CreateDelivery.class
	})
public class TestAvis { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
