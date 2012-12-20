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

			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "Sanders");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "Mike");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "950 Marietta St");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "Atlanta");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Georgia");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "30318");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/select", "label=Home");
			selenium.type("//div[@id='maincontent']/table[4]/tbody/tr[2]/td/input", "Apple");
			selenium.type("//div[@id='maincontent']/table[4]/tbody/tr[2]/td[2]/input", "AP1234");
			selenium.type("//div[@id='maincontent']/table[4]/tbody/tr[2]/td[3]/input", "iPhone 4S");
			selenium.select("//div[@id='maincontent']/table[4]/tbody/tr[3]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[4]/tbody/tr[3]/td[3]/select", "label=White");
			selenium.select("//div[@id='maincontent']/table[4]/tbody/tr[4]/td[3]/select", "label=Black");
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
			selenium.click("//img[@id='calendar']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/select", "label=High");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Apple");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "AP1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "iPhone 4S");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=White");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: An error occurred while creating the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
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
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Found Item:  " + LF_ProcessTraceResults.foundId));
			verifyTrue(selenium.isTextPresent("Lost Report:  " + LF_ProcessTraceResults.lostId));
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[5]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to verify and confirm trace result info on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to navigate to Lost Report: " + LF_ProcessTraceResults.lostId + " from the Found Item page.");
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select", "label=Closed");
			selenium.click("//div[@id='maincontent']/center[4]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to close Lost Report: " + LF_ProcessTraceResults.lostId);
			return;		
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to save Lost Report: " + LF_ProcessTraceResults.lostId);
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to navigate to Found Item: " + LF_ProcessTraceResults.foundId + " from the Lost Report page.");
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'shelved_trace_results.do')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to save the Found Item.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isTextPresent(LF_ProcessTraceResults.foundId));
			verifyFalse(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			selenium.click("//a[contains(@href, 'logon.do?taskmanager=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to load the Shelved Trace Results page after closing the Lost Report and Found Item.");
			return;
		}
		
	}

}

