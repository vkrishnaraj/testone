package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.jetblue.testing.actions.nt.admin.B6_Audit;
import aero.nettracer.web.jetblue.testing.actions.nt.admin.B6_Security;
import aero.nettracer.web.jetblue.testing.actions.nt.core.B6_Login;
import aero.nettracer.web.jetblue.testing.actions.nt.core.B6_LoginFailure;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.damage.B6_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.damage.B6_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_EditLD_VerifyText;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.pilferage.B6_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.pilferage.B6_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_ForwardOHD;
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_ReceiveOHD;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_InboxMessage;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_VerifyText_TaskManager;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.utility.Settings;

@RunWith(Suite.class)
@SuiteClasses({
	// JETBLUE
	B6_Login.class, Def_Logout.class, B6_LoginFailure.class, 
	B6_Login.class, B6_VerifyText_TaskManager.class,
	B6_CreateLD_VerifyRequiredFields.class, B6_EditLD_VerifyText.class, B6_CloseLD_VerifyRequiredFields.class,
	B6_InboxMessage.class, B6_CreateDam_VerifyRequiredFields.class, B6_CloseDam_VerifyRequiredFields.class,
	B6_CreatePilf_VerifyRequiredFields.class, B6_ClosePilf_VerifyRequiredFields.class,
	B6_CreateOHD_VerifyRequiredFields.class, B6_ForwardOHD.class, B6_ReceiveOHD.class,
	B6_Security.class,B6_Audit.class	
	})
public class TestJetBlue { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser(Settings.PORT2);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
