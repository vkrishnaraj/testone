package aero.nettracer.web.southwest.testing.actions.nt.claims;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	/**
	 * Test to create a claim to use for the Depreciation Calculator
	 * @throws Exception
	 */
	@Test
	public void testCreate_New_Claim() throws Exception {
		verifyTrue(setPermissions(new String[] { CLAIM_DEPREC_CALC,DEPREC_CALC_ADMIN, "674"}, new boolean[] { false, false, true }));

		clickMenu("menucol_8.1");
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
			selenium.type("name=claim.totalLiability", "150");
			selenium.type("name=claim.excessValueAmt", "50");
			selenium.select("name=claim.claimCheck", "label=WAIVE");
			selenium.check("name=claim.ix");
			selenium.check("name=claim.carryon");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to skip population");
		}
		
//TODO: Need to check how to set up FS on Hudson for NTWN - Cannot save claims directly otherwise as the communication breaks.
//		if (checkNoErrorPage()) { 
//			verifyTrue(isTextPresent("Fraud Results Summary Claim"));
//			String claimmade_id = selenium.getText("//div[@id='maincontent']/h1/");
//			Settings.CLAIMREQ_ID_WN = claimmade_id.substring(29, claimmade_id.length());
//			System.out.println("WN: Claim Made ID: " + Settings.CLAIMREQ_ID_WN);
//		} else {
//			System.out.println("Failed to create Claim page");
//		}
		
	}

	/**
	 * Test to go through the Manage Depreciation Calculator page and set certain percentages 
	 * and values to properly control testing the calculator. 
	 * Also testing against deleting a category being used. 
	 * Tests Assumes 2 Categories at present minimum
	 * @throws Exception
	 */
	@Test
	public void testUpdate_Depreciation_Categories() throws Exception {
		verifyTrue(setPermissions(new String[] { CLAIM_DEPREC_CALC,DEPREC_CALC_ADMIN }, new boolean[] { true, true }));
		clickMenu("menucol_9.18");
		waitForPageToLoadImproved();
			
		if(checkNoErrorPage()){
			selenium.click("name=deleteCategory[0]");
			waitForPageToLoadImproved();
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Cannot delete category currently in use"));
			
			selenium.select("name=category[0].calcMethod", "label=Defined Rate");
			selenium.type("name=category[0].firstYear", "10");
			selenium.type("name=category[0].secondYear", "25");
			selenium.type("name=category[0].thirdYear", "40");
			selenium.type("name=category[0].eachYear", "5");
			selenium.type("name=category[0].maxDeprec", "80");
			selenium.check("name=category[0].notCoveredCoc");
			selenium.select("name=category[1].calcMethod", "label=Flat Rate");
			selenium.type("name=category[1].flatRate", "10");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to open Depreciation Calcular Admin page");
		}

		if (checkNoErrorPage()) {

			verifyTrue(isTextPresent("General Depreciation Rules and Categories Saved"));
			goToTaskManager();
		} else {
			System.out.println("Failed to save Depreciation Calculator Admin page");
		}
	}
	
	
	/**
	 * Test to load earlier made claim and use the Depreciation Calculator and confirm that items can be created, updated, and deleted
	 * Because of issues with FS on Hudson, accessing the claim is using the claim Search table to reach it.
	 * @throws Exception
	 */
	@Test
	public void testCreate_Depreciation_Items() throws Exception {	
		if(checkNoErrorPage()){
			clickMenu("menucol_8.2");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to reach TaskManager");
		}
		
		if (checkNoErrorPage()) {
//			selenium.type("id=sId", Settings.CLAIMREQ_ID_WN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}  else {
			System.out.println("Failed to open search claim page");
		}
		

		if (checkNoErrorPage()) {
//			selenium.type("id=sId", Settings.CLAIMREQ_ID_WN);
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
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.YEAR, -5);
			Date d=cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String fiveYearsAgo = dateFormat.format(d);

			
			selenium.type("id=date_purch_0", fiveYearsAgo);
			selenium.select("id=dep_content_0_type", "index=2");
			selenium.uncheck("id=dep_content_0_coc");
			selenium.select("id=dep_content_0_rec", "label=Receipt");
			verifyEquals("50.00",selenium.getValue("calc0"));
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to add items to calculator");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Claim Depreciation and Items successfully saved"));
			selenium.click("name=deleteItem[1]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to populate item 1 and save");
		}
		

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Depreciation Item successfully deleted"));
			selenium.type("id=totalApprovedPayout", "30");
			selenium.click("name=save");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to delete empty item");
		}

		if (checkNoErrorPage()) {
			verifyEquals("30.00", selenium.getValue("id=totalApprovedPayout"));
			goToTaskManager();
		} else {
			System.out.println("Failed to manually update the Total Approved Payout");
		}
	}
	
}