package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.southwest.testing.actions.nt.core.WN_Login;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.communications.WN_CustomerCommunications;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.communications.WN_ViewTasksNotInWork;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.damaged.WN_CreateDamaged;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.expensepayouts.WN_ExpensePayouts;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay.WN_CloseLD;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.lostdelay.WN_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.southwest.testing.actions.nt.incidents.missing.WN_CreateMissing;
import aero.nettracer.web.southwest.testing.actions.nt.onhands.WN_CreateOhd;
import aero.nettracer.web.southwest.testing.actions.nt.onhands.WN_OHD_Sort_Search;
import aero.nettracer.web.southwest.testing.actions.nt.onhands.WN_ViewStationOhds;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.utility.Settings;

@RunWith(Suite.class)
@SuiteClasses({
	WN_Login.class
	, WN_CreateLD_VerifyRequiredFields.class
	, WN_ExpensePayouts.class
	, WN_CreateOhd.class
	, WN_OHD_Sort_Search.class
	, WN_ViewStationOhds.class
	, WN_CloseLD.class
	, WN_CreateDamaged.class
	, WN_CreateMissing.class
	, WN_CustomerCommunications.class
	, WN_ViewTasksNotInWork.class
	})
public class TestSouthwest { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser(Settings.PORT3, true);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("NTSOUTHWEST", true);
	}
	
}
