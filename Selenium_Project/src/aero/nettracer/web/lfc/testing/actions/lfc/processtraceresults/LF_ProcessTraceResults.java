package aero.nettracer.web.lfc.testing.actions.lfc.processtraceresults;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;

public class LF_ProcessTraceResults extends LoginUtil {
	
	private static String testId;
	
	@Test
	public void createLostReport() throws Exception {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "test");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Alabama");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/select", "label=Home");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/input", "Apple");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/input", "AP1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/input", "iPhone 4S");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/select", "label=White");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[2]/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
			if (!checkNoErrorPage()) {
				return;
			}
		} else {
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
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "test");
			selenium.type("name=found.client.firstName", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "test");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Alabama");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "test");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "1112223333");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/select", "label=Home");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/select", "label=New");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/select", "label=High");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Apple");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "AP1234");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "iPhone 4S");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=White");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Black");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
			if (!checkNoErrorPage()) {
				return;
			}
		} else {
			return;
		}
	}
	
	@Test
	public void testProcessTraceResult() throws Exception {
		selenium.click("//a[contains(@href, 'logon.do?taskmanager=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/form/table[2]/tbody/tr[3]/td/a");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr/td/center/select", "label=High");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
			verifyTrue(selenium.isTextPresent("Cellphone,Apple,iPhone 4S,AP1234"));
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td/center/input");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals(testId, selenium.getValue("//div[@id='maincontent']/table[3]/tbody/tr/td/input"));
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/select", "label=Closed");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr/td[3]/a");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.select("//div[@id='maincontent']/table/tbody/tr[2]/td[2]/select", "label=Closed");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			selenium.click("//div[@id='maincontent']/center[3]/input[2]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'shelved_trace_results.do')]");
			waitForPageToLoadImproved();
		} else {
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isElementPresent("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a"));
		} else {
			return;
		}
		
	}

}

