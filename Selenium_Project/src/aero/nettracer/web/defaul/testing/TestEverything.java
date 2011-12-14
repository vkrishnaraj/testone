package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.azul.testing.actions.nt.core.AD_Login;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_LoginFailure;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_LoginTest;
import aero.nettracer.web.azul.testing.actions.nt.incidents.damage.AD_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.damage.AD_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.lostdelay.AD_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.lostdelay.AD_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.pilferage.AD_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.incidents.pilferage.AD_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.azul.testing.actions.nt.taskman.AD_InboxMessage;
import aero.nettracer.web.azul.testing.actions.nt.taskman.AD_VerifyText_TaskManager;
import aero.nettracer.web.defaul.testing.actions.nt.core.Def_Logout;
import aero.nettracer.web.defaul.testing.actions.nt.core.Def_PasswordResetNTTest;
import aero.nettracer.web.jetblue.testing.actions.nt.core.B6_Login;
import aero.nettracer.web.jetblue.testing.actions.nt.core.B6_LoginFailure;
import aero.nettracer.web.jetblue.testing.actions.nt.core.B6_LoginTest;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.damage.B6_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.damage.B6_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.lostdelay.B6_EditLD_VerifyText;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.pilferage.B6_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.incidents.pilferage.B6_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_InboxMessage;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_VerifyText_TaskManager;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_Login;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_LoginFailure;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_LoginTest;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.damage.NK_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.damage.NK_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.lostdelay.NK_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.lostdelay.NK_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.pilferage.NK_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.incidents.pilferage.NK_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.spirit.testing.actions.nt.taskman.NK_InboxMessage;
import aero.nettracer.web.spirit.testing.actions.nt.taskman.NK_VerifyText_TaskManager;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_Login;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_LoginFailure;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_LoginTest;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_InboxMessage;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_VerifyText_TaskManager;

@RunWith(Suite.class)
@SuiteClasses({
	AD_Login.class, Def_Logout.class, AD_LoginFailure.class, AD_LoginTest.class, 
	Def_PasswordResetNTTest.class, Def_Logout.class, 
	AD_Login.class, AD_VerifyText_TaskManager.class,
	AD_CreateLD_VerifyRequiredFields.class, AD_CloseLD_VerifyRequiredFields.class, AD_InboxMessage.class,
	AD_CreateDam_VerifyRequiredFields.class, AD_CloseDam_VerifyRequiredFields.class,
	AD_CreatePilf_VerifyRequiredFields.class, AD_ClosePilf_VerifyRequiredFields.class,
	B6_Login.class, Def_Logout.class, B6_LoginFailure.class, B6_LoginTest.class, 
	Def_PasswordResetNTTest.class, Def_Logout.class, 
	B6_Login.class, B6_VerifyText_TaskManager.class,
	B6_CreateLD_VerifyRequiredFields.class, B6_EditLD_VerifyText.class, B6_CloseLD_VerifyRequiredFields.class,
	B6_InboxMessage.class, B6_CreateDam_VerifyRequiredFields.class, B6_CloseDam_VerifyRequiredFields.class,
	B6_CreatePilf_VerifyRequiredFields.class, B6_ClosePilf_VerifyRequiredFields.class,
	NK_Login.class, Def_Logout.class, NK_LoginFailure.class, NK_LoginTest.class, 
	Def_PasswordResetNTTest.class, Def_Logout.class, 
	NK_Login.class, NK_VerifyText_TaskManager.class,
	NK_CreateLD_VerifyRequiredFields.class, NK_CloseLD_VerifyRequiredFields.class, NK_InboxMessage.class,
	NK_CreateDam_VerifyRequiredFields.class, NK_CloseDam_VerifyRequiredFields.class,
	NK_CreatePilf_VerifyRequiredFields.class, NK_ClosePilf_VerifyRequiredFields.class,
	WS_Login.class, Def_Logout.class, WS_LoginFailure.class, WS_LoginTest.class, 
	Def_PasswordResetNTTest.class, Def_Logout.class, 
	WS_Login.class, WS_VerifyText_TaskManager.class,
	WS_CreateLD_VerifyRequiredFields.class, WS_CloseLD_VerifyRequiredFields.class, WS_InboxMessage.class,
	WS_CreateDam_VerifyRequiredFields.class, WS_CloseDam_VerifyRequiredFields.class,
	WS_CreatePilf_VerifyRequiredFields.class, WS_ClosePilf_VerifyRequiredFields.class
	})
public class TestEverything { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}
	
}
