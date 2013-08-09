package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.azul.testing.actions.nt.admin.AD_Audit;
import aero.nettracer.web.azul.testing.actions.nt.admin.AD_Security;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_Login;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_LoginFailure;
import aero.nettracer.web.azul.testing.actions.nt.incidents.damage.AD_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.damage.AD_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.lostdelay.AD_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.lostdelay.AD_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.pilferage.AD_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.pilferage.AD_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.onhands.AD_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.onhands.AD_ForwardOHD;
import aero.nettracer.web.azul.testing.actions.nt.onhands.AD_ReceiveOHD;
import aero.nettracer.web.azul.testing.actions.nt.taskman.AD_InboxMessage;
import aero.nettracer.web.azul.testing.actions.nt.taskman.AD_VerifyText_TaskManager;
import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;

@RunWith(Suite.class)
@SuiteClasses({
	// AZUL
	AD_Login.class,Def_Logout.class, AD_LoginFailure.class, 
	AD_Login.class, AD_VerifyText_TaskManager.class,
	AD_CreateLD_VerifyRequiredFields.class, AD_CloseLD_VerifyRequiredFields.class, AD_InboxMessage.class,
	AD_CreateDam_VerifyRequiredFields.class, AD_CloseDam_VerifyRequiredFields.class,
	AD_CreatePilf_VerifyRequiredFields.class, AD_ClosePilf_VerifyRequiredFields.class,
	AD_CreateOHD_VerifyRequiredFields.class, AD_ForwardOHD.class, AD_ReceiveOHD.class,
	AD_Security.class,AD_Audit.class	
	})
public class TestAzul { 
	
	
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("AZUL");
	}

	
}
