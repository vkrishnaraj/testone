package aero.nettracer.web.lfc.testing.actions.lfc.processtraceresults;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class LF_ProcessTraceResults extends LoginUtil {
	
	private static String testId;
	
	@Test
	public void createLostReport() throws Exception {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "Sanders");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "Mike");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "950 Marietta St");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "Atlanta");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Georgia");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "30318");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/select", "label=Home");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/input", "Apple");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/input", "AP1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/input", "iPhone 4S");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/select", "label=White");
			selenium.select("document.forms[0].elements[38]", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: An error occurred while creating the Lost Report.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			Settings.LOST_ID_LF = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("LFPTR: created Found Item: " + Settings.LOST_ID_LF);
		} else {
			System.out.println("LFPTR: An error occurred while saving the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void createFoundItem() throws Exception {
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			testId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", testId);
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
			verifyTrue(selenium.isTextPresent("Report Summary"));
			verifyTrue(selenium.isTextPresent("Found Item:  " + Settings.FOUND_ID_LF));
			verifyTrue(selenium.isTextPresent("Lost Report:  " + Settings.LOST_ID_LF));
			Settings.FOUND_ID_LF = testId;
			System.out.println("LFPTR: created Found Item: " + testId);
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
			verifyTrue(selenium.isTextPresent(Settings.LOST_ID_LF));
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			verifyTrue(selenium.isTextPresent("Cellphone,Apple,iPhone 4S,AP1234"));
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td/center/input");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to verify trace result info on the Shelved Trace Results page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Found Item:  " + Settings.FOUND_ID_LF));
			verifyTrue(selenium.isTextPresent("Lost Report:  " + Settings.LOST_ID_LF));
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to verify and confirm trace result info on the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[3]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to navigate to Lost Report: " + Settings.LOST_ID_LF + " from the Found Item page.");
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select", "label=Closed");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to close Lost Report: " + Settings.LOST_ID_LF);
			return;		
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			selenium.click("//div[@id='maincontent']/table[3]/tbody/tr/td[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to save Lost Report: " + Settings.LOST_ID_LF);
			return;		
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to navigate to Found Item: " + Settings.FOUND_ID_LF + " from the Lost Report page.");
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
			verifyFalse(selenium.isTextPresent(Settings.FOUND_ID_LF));
			verifyFalse(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			selenium.click("//a[contains(@href, 'logon.do?taskmanager=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LFPTR: Failed to load the Shelved Trace Results page after closing the Lost Report and Found Item.");
			return;
		}
		
	}

}

