package aero.nettracer.web.fraud.testing.actions.fs;


import java.util.Date;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class FS_CreateFile extends LoginUtil {
	
	@Test
	public void testCreate_New_FS() throws Exception {
		selenium.open(Settings.START_URL_AA);
		loginAdminProcedure();
		selenium.click("id=menucol_1.1");
		waitForPageToLoadImproved();
		String name=String.valueOf(new Date().getTime());
		selenium.type("name=claimant.lastName", "WILL");
		selenium.type("name=claimant.firstName","THIS");
		selenium.type("name=address1", "WORK");
		selenium.type("name=city", "Testville");
		selenium.select("name=state", "label=Alabama");
		selenium.type("name=zip", "12345");
		selenium.click("name=save");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Fraud Results Summary Claim"));
		String claimmade_id = selenium.getText("//div[@id='maincontent']/h1/");
		Settings.CLAIMREQ_ID_FS = claimmade_id.substring(29, claimmade_id.length());
		System.out.println("FS: Claim Made ID: " + Settings.CLAIMREQ_ID_FS);

		selenium.click("link=[ Logout ]");
		selenium.open(Settings.START_URL_WS);
		loginAdminProcedure();
		selenium.click("id=menucol_8.1");
		waitForPageToLoadImproved();
		selenium.click("name=skipPrepopulate");
		waitForPageToLoadImproved();
		selenium.type("name=claimant.lastName", "WILL");
		selenium.type("name=claimant.firstName","THIS");
		selenium.type("name=address1", "WORK");
		selenium.type("name=city", "Testville");
		selenium.select("name=state", "label=Alabama");
		selenium.type("name=zip", "12345");
		selenium.click("name=save");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent("Fraud Results Summary Claim"));
		claimmade_id = selenium.getText("//div[@id='maincontent']/h1/");
		Settings.CLAIMMADE_ID_FS = claimmade_id.substring(29, claimmade_id.length());
		System.out.println("FS: Claim Made ID: " + Settings.CLAIMMADE_ID_FS);
		verifyTrue(selenium.isTextPresent(Settings.CLAIMREQ_ID_FS));
		selenium.click("name=primaryResults[0].requestSelected");
//		int tablesize=Integer.valueOf(selenium.getXpathCount("//div[@id='maincontent']/table[2]/tbody/tr").toString());
//		for(int i=2;  i<tablesize; i++){
//			String disabled=selenium
//					getAttribute("//div[@id='maincontent']/table/tbody/tr["+i+"]/td/input");
//			if(!disabled.contains("disabled")){
//				
//				String claimreq_id = selenium.getText("//div[@id='maincontent']/table/tbody/tr["+i+"]/td[3]/"); //broken
//				Settings.CLAIMREQ_ID_FS = claimreq_id;
//				break;
//			}
//		}
		
		selenium.click("id=button");
		waitForPageToLoadImproved();
		selenium.type("name=message", "Testing");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		System.out.println("FS: Claim Requested ID: " + Settings.CLAIMREQ_ID_FS);
		selenium.click("link=[ Logout ]");
		waitForPageToLoadImproved();
		selenium.open(Settings.START_URL_AA);
		loginAdminProcedure();
		selenium.click("xpath=(//a[contains(text(),'Pending Fraud Check Requests')])[2]");
		waitForPageToLoadImproved();
		verifyTrue(selenium.isTextPresent(Settings.CLAIMREQ_ID_FS));
	}
	
}