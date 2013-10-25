package aero.nettracer.web.defaul.testing;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aero.nettracer.web.lfc.testing.actions.client.southwest.LF_WN_CreateLost;
import aero.nettracer.web.lfc.testing.actions.lfc.core.LF_BoxCount;
import aero.nettracer.web.lfc.testing.actions.lfc.core.LF_Login;
import aero.nettracer.web.lfc.testing.actions.lfc.core.LF_QuickHistory;
import aero.nettracer.web.lfc.testing.actions.lfc.delivery.LF_CreateDeliveryFromFound;
import aero.nettracer.web.lfc.testing.actions.lfc.delivery.LF_DeliveredFound;
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
import aero.nettracer.web.lfc.testing.actions.lfc.salvage.LF_Salvage;
import aero.nettracer.web.lfc.testing.actions.lfc.salvage.LF_SalvageSearch;
import aero.nettracer.web.lfc.testing.actions.lfc.taskman.LF_VerifyText_TaskManager;
import aero.nettracer.web.utility.SeleniumTestBrowserDefault;
import aero.nettracer.web.utility.Settings;

@RunWith(Suite.class)
@SuiteClasses({
	// LFC
	LF_Login.class
	, LF_CreateLost_VerifyRequiredFields.class
	, LF_CreateFound_VerifyRequiredFields.class
	, LF_SearchLost.class
	, LF_SearchFound.class
	, LF_ManualMatch_FoundToLost.class
	, LF_CloseFound.class
	, LF_SearchLost.class
	, LF_CloseLost.class
	, LF_CreateLost_Detailed.class
	, LF_CreateFound_Detailed.class
	, LF_FoundReportSummary.class
	, LF_VerifyText_TaskManager.class
	, LF_ConfirmMatch.class
	, LF_CreateDeliveryFromFound.class
	, LF_ItemEntry.class
	, LF_ProcessTraceResults.class
	, LF_CreateLostAndFoundReadOnly.class
	, LF_Salvage.class
	, LF_SalvageSearch.class
	, LF_BoxCount.class
	, LF_DeliveredFound.class
	, LF_QuickHistory.class
	, LF_WN_CreateLost.class
	})
public class TestLfc { 
	
	@BeforeClass
	public static void oneTimeSetUp() {
		SeleniumTestBrowserDefault.initBrowser(Settings.PORT2, true);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		SeleniumTestBrowserDefault.stopBrowser("LFC", true);
	}
	
}
