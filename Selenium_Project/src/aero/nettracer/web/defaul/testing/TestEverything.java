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
import aero.nettracer.web.azul.testing.actions.nt.admin.AD_Security;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_Login;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_LoginFailure;
import aero.nettracer.web.azul.testing.actions.nt.core.AD_LoginTest;
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
import aero.nettracer.web.jetblue.testing.actions.nt.admin.B6_Security;
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
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_ForwardOHD;
import aero.nettracer.web.jetblue.testing.actions.nt.onhands.B6_ReceiveOHD;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_InboxMessage;
import aero.nettracer.web.jetblue.testing.actions.nt.taskman.B6_VerifyText_TaskManager;
import aero.nettracer.web.lfc.testing.actions.lfc.core.LF_Login;
import aero.nettracer.web.lfc.testing.actions.lfc.delivery.LF_CreateDeliveryFromFound;
import aero.nettracer.web.lfc.testing.actions.lfc.found.LF_CloseFound;
import aero.nettracer.web.lfc.testing.actions.lfc.found.LF_CreateFound_Detailed;
import aero.nettracer.web.lfc.testing.actions.lfc.found.LF_CreateFound_VerifyRequiredFields;
import aero.nettracer.web.lfc.testing.actions.lfc.found.LF_FoundReportSummary;
import aero.nettracer.web.lfc.testing.actions.lfc.found.LF_SearchFound;
import aero.nettracer.web.lfc.testing.actions.lfc.itementry.LF_ItemEntry;
import aero.nettracer.web.lfc.testing.actions.lfc.lost.LF_CloseLost;
import aero.nettracer.web.lfc.testing.actions.lfc.lost.LF_CreateLostAndFoundReadOnly;
import aero.nettracer.web.lfc.testing.actions.lfc.lost.LF_CreateLost_Detailed;
import aero.nettracer.web.lfc.testing.actions.lfc.lost.LF_CreateLost_VerifyRequiredFields;
import aero.nettracer.web.lfc.testing.actions.lfc.lost.LF_SearchLost;
import aero.nettracer.web.lfc.testing.actions.lfc.match.LF_ConfirmMatch;
import aero.nettracer.web.lfc.testing.actions.lfc.match.LF_ManualMatch_FoundToLost;
import aero.nettracer.web.lfc.testing.actions.lfc.processtraceresults.LF_ProcessTraceResults;
import aero.nettracer.web.lfc.testing.actions.lfc.taskman.LF_VerifyText_TaskManager;
import aero.nettracer.web.spirit.testing.actions.nt.admin.NK_Security;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_Login;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_LoginFailure;
import aero.nettracer.web.spirit.testing.actions.nt.core.NK_LoginTest;
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
import aero.nettracer.web.westjet.testing.actions.nt.admin.WS_Security;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_Login;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_LoginFailure;
import aero.nettracer.web.westjet.testing.actions.nt.core.WS_LoginTest;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CloseDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.damage.WS_CreateDam_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CloseLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay.WS_CreateLD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_ClosePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.incidents.pilferage.WS_CreatePilf_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_CreateOHD_VerifyRequiredFields;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_ForwardOHD;
import aero.nettracer.web.westjet.testing.actions.nt.onhands.WS_ReceiveOHD;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_InboxMessage;
import aero.nettracer.web.westjet.testing.actions.nt.taskman.WS_VerifyText_TaskManager;

@RunWith(Suite.class)
@SuiteClasses({
	// AZUL
	AD_Login.class, Def_Logout.class, AD_LoginFailure.class, AD_LoginTest.class, 
//	Def_PasswordResetNTTest.class,
	Def_Logout.class, 
	AD_Login.class, AD_VerifyText_TaskManager.class,
	AD_CreateLD_VerifyRequiredFields.class, AD_CloseLD_VerifyRequiredFields.class, AD_InboxMessage.class,
	AD_CreateDam_VerifyRequiredFields.class, AD_CloseDam_VerifyRequiredFields.class,
	AD_CreatePilf_VerifyRequiredFields.class, AD_ClosePilf_VerifyRequiredFields.class,
	AD_CreateOHD_VerifyRequiredFields.class, AD_ForwardOHD.class, AD_ReceiveOHD.class,
	AD_Security.class,
	// JETBLUE
	B6_Login.class, Def_Logout.class, B6_LoginFailure.class, B6_LoginTest.class, 
//	Def_PasswordResetNTTest.class, 
	Def_Logout.class, 
	B6_Login.class, B6_VerifyText_TaskManager.class,
	B6_CreateLD_VerifyRequiredFields.class, B6_EditLD_VerifyText.class, B6_CloseLD_VerifyRequiredFields.class,
	B6_InboxMessage.class, B6_CreateDam_VerifyRequiredFields.class, B6_CloseDam_VerifyRequiredFields.class,
	B6_CreatePilf_VerifyRequiredFields.class, B6_ClosePilf_VerifyRequiredFields.class,
	B6_CreateOHD_VerifyRequiredFields.class, B6_ForwardOHD.class, B6_ReceiveOHD.class,
	B6_Security.class,
	// SPIRIT
	NK_Login.class, Def_Logout.class, NK_LoginFailure.class, NK_LoginTest.class, 
//	Def_PasswordResetNTTest.class, 
	Def_Logout.class, 
	NK_Login.class, NK_VerifyText_TaskManager.class,
	NK_CreateLD_VerifyRequiredFields.class, NK_CloseLD_VerifyRequiredFields.class, NK_InboxMessage.class,
	NK_CreateDam_VerifyRequiredFields.class, NK_CloseDam_VerifyRequiredFields.class,
	NK_CreatePilf_VerifyRequiredFields.class, NK_ClosePilf_VerifyRequiredFields.class,
	NK_CreateOHD_VerifyRequiredFields.class, NK_ForwardOHD.class, NK_ReceiveOHD.class,
	NK_Security.class,
	// WESTJET
	WS_Login.class, Def_Logout.class, WS_LoginFailure.class, WS_LoginTest.class, 
//	Def_PasswordResetNTTest.class, 
	Def_Logout.class, 
	WS_Login.class, WS_VerifyText_TaskManager.class,
	WS_CreateLD_VerifyRequiredFields.class, WS_CloseLD_VerifyRequiredFields.class, WS_InboxMessage.class,
	WS_CreateDam_VerifyRequiredFields.class, WS_CloseDam_VerifyRequiredFields.class,
	WS_CreatePilf_VerifyRequiredFields.class, WS_ClosePilf_VerifyRequiredFields.class,
	WS_CreateOHD_VerifyRequiredFields.class, WS_ForwardOHD.class, WS_ReceiveOHD.class,
	WS_Security.class,
	// AVIS
	AB_LoginFailure.class, AB_Login.class,
	AB_CreateLost_VerifyRequiredFields.class, AB_CreateFound_VerifyRequiredFields.class,
	AB_SearchLost.class, AB_SearchFound.class, AB_ManualMatch_FoundToLost.class,
	AB_CloseFound.class, AB_SearchLost.class, AB_CloseLost.class,
	AB_CreateLost_Detailed.class, AB_CreateFound_Detailed.class,
	AB_ConfirmMatch.class, AB_CreateDelivery.class,
	// LFC
	LF_Login.class, LF_CreateLost_VerifyRequiredFields.class, LF_CreateFound_VerifyRequiredFields.class,
	LF_SearchLost.class, LF_SearchFound.class, LF_ManualMatch_FoundToLost.class,
	LF_CloseFound.class, LF_SearchLost.class, LF_CloseLost.class, 
	LF_CreateLost_Detailed.class, LF_CreateFound_Detailed.class, LF_VerifyText_TaskManager.class, LF_FoundReportSummary.class,
	LF_ConfirmMatch.class, LF_CreateDeliveryFromFound.class, LF_ItemEntry.class, LF_ProcessTraceResults.class,
	LF_CreateLostAndFoundReadOnly.class
	
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
