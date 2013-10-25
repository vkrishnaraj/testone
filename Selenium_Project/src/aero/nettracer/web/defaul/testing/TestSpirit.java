package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.spirit.testing.actions.nt.admin.NK_Audit;
import aero.nettracer.web.spirit.testing.actions.nt.admin.NK_Security;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_Login;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_LoginFailure;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.damage.NK_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.damage.NK_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.lostdelay.NK_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.lostdelay.NK_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.pilferage.NK_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.pilferage.NK_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.onhands.NK_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.onhands.NK_ForwardOHD;
import aero.nettracer.web.spirit.testing.actions.nt.onhands.NK_ReceiveOHD;
import aero.nettracer.web.spirit.testing.actions.nt.taskman.NK_InboxMessage;
import aero.nettracer.web.spirit.testing.actions.nt.taskman.NK_VerifyText_TaskManager;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({
//	 SPIRIT
	NK_Login.class
	, Def_Logout.class
	, NK_LoginFailure.class
	, NK_Login.class
	, NK_VerifyText_TaskManager.class
	, NK_CreateLD_VerifyRequiredFields.class
	, NK_CloseLD_VerifyRequiredFields.class
	, NK_InboxMessage.class
	, NK_CreateDam_VerifyRequiredFields.class
	, NK_CloseDam_VerifyRequiredFields.class
	, NK_CreatePilf_VerifyRequiredFields.class
	, NK_ClosePilf_VerifyRequiredFields.class
	, NK_CreateOHD_VerifyRequiredFields.class
	, NK_ForwardOHD.class
	, NK_ReceiveOHD.class
	, NK_Security.class
	, NK_Audit.class
	})
public class TestSpirit { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser(true);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("SPIRIT", true);
	}
	
}
