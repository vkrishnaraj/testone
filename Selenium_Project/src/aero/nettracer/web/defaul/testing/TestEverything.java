package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.azul.testing.actions.AD_Login;
import aero.nettracer.web.azul.testing.actions.AD_LoginFailure;
import aero.nettracer.web.azul.testing.actions.AD_LoginTest;
import aero.nettracer.web.azul.testing.actions.AD_VerifyText_TaskManager;
import aero.nettracer.web.defaul.testing.actions.Def_Logout;
import aero.nettracer.web.defaul.testing.actions.Def_PasswordResetNTTest;
import aero.nettracer.web.jetblue.testing.actions.B6_Login;
import aero.nettracer.web.jetblue.testing.actions.B6_LoginFailure;
import aero.nettracer.web.jetblue.testing.actions.B6_LoginTest;
import aero.nettracer.web.jetblue.testing.actions.B6_VerifyText_TaskManager;
import aero.nettracer.web.spirit.testing.actions.NK_Login;
import aero.nettracer.web.spirit.testing.actions.NK_LoginFailure;
import aero.nettracer.web.spirit.testing.actions.NK_LoginTest;
import aero.nettracer.web.spirit.testing.actions.NK_VerifyText_TaskManager;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.westjet.testing.actions.WS_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.WS_Login;
import aero.nettracer.web.westjet.testing.actions.WS_LoginFailure;
import aero.nettracer.web.westjet.testing.actions.WS_LoginTest;
import aero.nettracer.web.westjet.testing.actions.WS_VerifyText_TaskManager;

@RunWith(Suite.class)
@SuiteClasses({
//	AD_Login.class, Def_Logout.class, AD_LoginFailure.class, AD_LoginTest.class, 
//	Def_PasswordResetNTTest.class, Def_Logout.class, 
//	AD_Login.class, AD_VerifyText_TaskManager.class,
//	B6_Login.class, Def_Logout.class, B6_LoginFailure.class, B6_LoginTest.class, 
//	Def_PasswordResetNTTest.class, Def_Logout.class, 
//	B6_Login.class, B6_VerifyText_TaskManager.class,
//	NK_Login.class, Def_Logout.class, NK_LoginFailure.class, NK_LoginTest.class, 
//	Def_PasswordResetNTTest.class, Def_Logout.class, 
//	NK_Login.class, NK_VerifyText_TaskManager.class,
//	WS_Login.class, Def_Logout.class, WS_LoginFailure.class, WS_LoginTest.class, 
//	Def_PasswordResetNTTest.class, Def_Logout.class, 
	WS_Login.class, //WS_VerifyText_TaskManager.class,
	WS_CreateLD_VerifyRequiredFields.class
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
