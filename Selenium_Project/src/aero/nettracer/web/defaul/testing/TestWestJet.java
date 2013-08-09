package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.westjet.testing.actions.nt.admin.WS_Audit;
import aero.nettracer.web.westjet.testing.actions.nt.admin.WS_Security;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_Login;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_LoginFailure;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_QuickHistory;
import aero.nettracer.web.westjet.testing.actions.nt.lostfound.WS_LostfoundTest;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_LDVerifyReplacementBags;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_LDVerifyApplyAllOnClose;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_LDVerifyRonKits;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_ForwardOHD;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_ForwardMessage;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_ReceiveOHD;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_TestWarehouse;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.US_DisputeManage;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_InboxMessage;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_VerifyText_TaskManager;

@RunWith(Suite.class)
@SuiteClasses({
//	 WESTJET
	WS_Login.class, 
	Def_Logout.class, WS_LoginFailure.class, 
	WS_Login.class, WS_VerifyText_TaskManager.class,
	WS_CreateLD_VerifyRequiredFields.class, WS_CloseLD_VerifyRequiredFields.class, 
	WS_InboxMessage.class,
	WS_CreateDam_VerifyRequiredFields.class, WS_CloseDam_VerifyRequiredFields.class,
	WS_CreatePilf_VerifyRequiredFields.class, WS_ClosePilf_VerifyRequiredFields.class,  WS_LostfoundTest.class,
	WS_CreateOHD_VerifyRequiredFields.class, WS_ForwardOHD.class, WS_ForwardMessage.class, WS_ReceiveOHD.class, WS_QuickHistory.class, WS_TestWarehouse.class,
	WS_Security.class, WS_LDVerifyApplyAllOnClose.class, US_DisputeManage.class,
	WS_LDVerifyRonKits.class, WS_LDVerifyReplacementBags.class, 
	WS_Audit.class
	})
public class TestWestJet { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("WESTJET");
	}
	
}
