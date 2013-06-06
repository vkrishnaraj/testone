package aero.nettracer.web.lfc.testing.actions.lfc.processtraceresults;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

public class LF_ProcessTraceResults extends LoginUtil {
	
	private static String lostId;
	private static String foundId;
	
	@Test
	public void testCreateLostReport() throws Exception {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {

			selenium.select("name=lost.companyId", "label=Southwest Airlines");
			selenium.type("id=lastName", "Sanders");
			selenium.type("id=firstName", "Mike");
			selenium.type("id=address1", "950 Marietta St");
			selenium.type("id=city", "Atlanta");
			selenium.select("id=state", "label=Georgia");
			selenium.type("id=zip", "30318");
			selenium.type("id=priInterNum", "1"); //112223333
			selenium.type("id=priAreaNum", "122");
			selenium.type("id=priExchaNum", "23");
			selenium.type("id=priLineNum", "333");
			selenium.select("id=priPhoneType", "label=Home");
			selenium.type("id=itembrand_0", "Apple");
			selenium.type("id=itemserial_0", "AP1234");
			selenium.type("id=itemmodel_0", "iPhone 4S");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("id=itemcolor_0", "label=White");
			selenium.select("id=itemcasecolor_0", "label=Black");
			selenium.select("segment[0].originId", "label=ATL");
			selenium.select("segment[0].destinationId", "label=BOS");
			selenium.click("//div[@id='maincontent']/center[4]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: An error occurred while creating the Lost Report.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			LF_ProcessTraceResults.lostId = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("LFPTR: created Lost Report: " + LF_ProcessTraceResults.lostId);
		} else {
			System.out.println("LFPTR: An error occurred while saving the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void testCreateFoundItem() throws Exception {
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_ProcessTraceResults.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_ProcessTraceResults.foundId);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.select("id=itemvalue_0", "label=High");
			selenium.type("id=itembrand_0", "Apple");
			selenium.type("id=itemserial_0", "AP1234");
			selenium.type("id=itemmodel_0", "iPhone 4S");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("id=itemcolor_0", "label=White");
			selenium.select("id=itemcasecolor_0", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved(10000);
		} else {
			System.out.println("LFPTR: An error occurred while creating the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved(10000);
			System.out.println("LFPTR: created Found Item: " + LF_ProcessTraceResults.foundId);
		} else {
			System.out.println("LFPTR: An error occurred while saving the Found Item.");
			return;
		}
		
	}
	
	@Test
	public void testProcessTraceResult() throws Exception {
		selenium.click("//a[contains(@href, 'shelved_trace_results.do')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr/td/center/select", "label=High");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to pull up high value trace results.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent(LF_ProcessTraceResults.foundId));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			verifyTrue(selenium.isTextPresent("Cellphone, Apple, iPhone 4S, AP1234"));
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td/center/input");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to verify trace result info on the Shelved Trace Results page.");
			return;
		}
		System.out.println("LFPTR: 1");
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Found Item:  " + LF_ProcessTraceResults.foundId));
			verifyTrue(selenium.isTextPresent("Lost Report:  " + LF_ProcessTraceResults.lostId));
			selenium.click("xpath=(//a[contains(text(),'Confirm Match')])[2]");
//			assertEquals("ID is required.", selenium.getAlert());
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to verify and confirm trace result info on the Found Item page.");
			return;
		}
		System.out.println("LFPTR: 2");
		
		if (checkNoErrorPage()) {
			selenium.click("link=" + LF_ProcessTraceResults.lostId);
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to navigate to Lost Report: " + LF_ProcessTraceResults.lostId + " from the Found Item page.");
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.select("name=lost.statusId", "label=Closed");
			selenium.click("//div[@id='maincontent']/center[4]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to close Lost Report: " + LF_ProcessTraceResults.lostId);
			return;		
		}
		System.out.println("LFPTR: 3");
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			selenium.click("//a[contains(@href, 'shelved_trace_results.do')]");
			waitForPageToLoadImproved();
//			selenium.click("link="+LF_ProcessTraceResults.foundId);
//			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to save Lost Report: " + LF_ProcessTraceResults.lostId);
			return;		
		}
		System.out.println("LFPTR: 4");
		
//		if (checkNoErrorPage()) {
//			selenium.click("saveButton");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("LFPTR: Failed to navigate to Found Item: " + LF_ProcessTraceResults.foundId + " from the Lost Report page.");
//			return;		
//		}
//		System.out.println("LFPTR: 5");
//		
//		if (checkNoErrorPage()) {
//			selenium.click("//a[contains(@href, 'shelved_trace_results.do')]");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("LFPTR: Failed to save the Found Item.");
//			return;
//		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isTextPresent(LF_ProcessTraceResults.foundId));
			verifyFalse(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			selenium.click("//a[contains(@href, 'logon.do?taskmanager=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to load the Shelved Trace Results page after closing the Lost Report and Found Item.");
			return;
		}
		System.out.println("LFPTR: 6");
		
	}

}

