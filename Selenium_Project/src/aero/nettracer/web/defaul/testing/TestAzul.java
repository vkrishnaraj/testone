package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
//	AD_Login.class, AD_VerifyText_TaskManager.class,
//	AD_CreateLD_VerifyRequiredFields.class, AD_CloseLD_VerifyRequiredFields.class, AD_InboxMessage.class,
//	AD_CreateDam_VerifyRequiredFields.class, AD_CloseDam_VerifyRequiredFields.class,
//	AD_CreatePilf_VerifyRequiredFields.class, AD_ClosePilf_VerifyRequiredFields.class,
//	AD_CreateOHD_VerifyRequiredFields.class, AD_ForwardOHD.class, AD_ReceiveOHD.class,
//	AD_Security.class
	
	})
public class TestAzul { 
	
	
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser();
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser();
	}

	
}
