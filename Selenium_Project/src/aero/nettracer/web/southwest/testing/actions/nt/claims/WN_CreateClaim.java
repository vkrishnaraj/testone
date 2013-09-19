package aero.nettracer.web.southwest.testing.actions.nt.claims;


import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WN_CreateClaim extends WN_SeleniumTest {

	private static String RX_TIMESTAMP = "";

	private String CLAIM_DEPREC_CALC = "656";
	private String DEPREC_CALC_ADMIN = "657";
	
	@Test
	public void testCreate_New_FS() throws Exception {
		verifyTrue(setPermissions(new String[] { CLAIM_DEPREC_CALC,DEPREC_CALC_ADMIN }, new boolean[] { false, false }));
		
		selenium.click("id=menucol_8.1");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage()){
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skipPrepopulate");
			waitForPageToLoadImproved();
		} else {

			System.out.println("Failed to open create Claim page");
		}
		if (checkNoErrorPage()) {
			String name=String.valueOf(new Date().getTime());
			selenium.type("name=claimant.lastName", "WILL"+name);
			selenium.type("name=claimant.firstName","THIS"+name);
			selenium.type("name=address1", "WORK"+name);
			selenium.type("name=city", "Testville");
			selenium.select("name=state", "label=Alabama");
			selenium.type("name=zip", "12345");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to skip population");
		}
		

//		if (checkNoErrorPage()) {
//			verifyTrue(selenium.isTextPresent("Fraud Results Summary Claim"));
//			String claimmade_id = selenium.getText("//div[@id='maincontent']/h1/");
//			Settings.CLAIMREQ_ID_WN = claimmade_id.substring(29, claimmade_id.length());
//			System.out.println("WN: Claim Made ID: " + Settings.CLAIMREQ_ID_WN);
//		} else {
//			System.out.println("Failed to create Claim page");
//		}
		
		

//		if (checkNoErrorPage()) {
			verifyTrue(setPermissions(new String[] { CLAIM_DEPREC_CALC,DEPREC_CALC_ADMIN }, new boolean[] { true, true }));
			selenium.click("id=menucol_9.17");
			waitForPageToLoadImproved();
//		} else {
//			
//		}
		

		if (checkNoErrorPage()) {
			selenium.select("name=category[0].calcMethod", "label=Flat Rate");
			selenium.type("name=category[0].flatRate", "10");
			selenium.type("name=category[0].firstYear", "10");
			selenium.type("name=category[0].secondYear", "25");
			selenium.type("name=category[0].thirdYear", "40");
			selenium.type("name=category[0].eachYear", "5");
			selenium.type("name=category[0].maxDeprec", "75");
			selenium.check("name=category[0].notCoveredCoc");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to open Depreciation Calcular Admin page");
		}

		if (checkNoErrorPage()) {
			selenium.click("id=menucol_8.2");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to save Depreciation Calcular Admin page");
		}
		

		if (checkNoErrorPage()) {
//			selenium.type("id=sId", Settings.CLAIMREQ_ID_WN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}  else {
			System.out.println("Failed to open search claim page");
		}
		

		if (checkNoErrorPage()) {
//			selenium.type("id=sId", Settings.CLAIMREQ_ID_WN); /html/body/table/tbody/tr/td/table/tbody/tr[5]/td/div/table[3]/tbody/tr[2]/td
			selenium.click("xpath=(//a[contains(@href, 'claim_resolution.do?claimId=')])[1]"); 
			waitForPageToLoadImproved();
		}  else {
			System.out.println("Failed to open search claim page");
		}
		
		if (checkNoErrorPage()) {
			selenium.click("xpath=(//a[contains(@href, 'claim_deprec_calc.do?claim_id=')])[1]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to search for claim ");
		}
		

		if (checkNoErrorPage()) {
			selenium.select("name=addNum", "label=2");
			selenium.click("name=addItems");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to open claim depreciation calculater");
		}

		if (checkNoErrorPage()) {
			selenium.type("id=dep_amount_0", "100");
			selenium.type("id=date_purch_0", "09/13/2001");
			selenium.select("id=dep_content_0_type", "index=1");
			selenium.uncheck("id=dep_content_0_coc");
			selenium.select("id=dep_content_0_rec", "label=Receipt");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to add items to calculator");
		}

		if (checkNoErrorPage()) {
			selenium.click("name=deleteItem[1]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to populate item 1 and save");
		}
		

		if (checkNoErrorPage()) {
			selenium.type("id=totalApprovedPayout", "30");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to delete empty item");
		}

		if (checkNoErrorPage()) {
			goToTaskManager();
		} else {
			System.out.println("Failed to manually update the Total Approved Payout");
		}
	}
	
}