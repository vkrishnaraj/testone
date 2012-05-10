package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_LDVerifyRonKits extends LoginUtil {

	@Test
	public void testRonKits() throws Exception {
		selenium.click("//ul[@id='menubuilder1']/li/a");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page.");
			return;
		}
		
		if (checkNoErrorPage()) {
//			verifyFalse(selenium.isTextPresent("# RON Kits issued:"));
//			verifyFalse(selenium.isElementPresent("//select[@id='numRonKitsIssued']"));
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page after pressing skip prepopulation.");
			return;
		}
		
		if (checkNoErrorPage()) {
			loginOGAdminProcedure();
		} else {
			System.out.println("LDVRK: Failed to log out of the application.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//ul[@id='menubuilder9']/li[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to log in as ogadmin.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[17]/td/a[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the companies page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[12]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the 3rd companies page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[11]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the groups page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.check("//div[@id='maincontent']/table/tbody/tr[18]/td[2]/table/tbody/tr[8]/td/input");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[32]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the permissions page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to save the permissions page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			loginAdminProcedure();
		} else {
			System.out.println("LDVRK: Failed on log out after setting the issue ron kit permission.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//ul[@id='menubuilder1']/li/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed on log back in after setting the issue ron kit permission.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed on load the damaged incident page.");
			return;
		}

		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("# RON Kits issued:"));
			verifyTrue(selenium.isElementPresent("//select[@id='numRonKitsIssued']"));
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[4]/input", "444444");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[2]/td[2]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[5]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td[3]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td[4]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[8]/td[3]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[9]/td[2]/input", "test@test.com");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td/input", "ABI");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td/input[2]", "ABI");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td[2]/input", "1234");
			selenium.click("id=itcalendar0");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("id=itcalendar20");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.type("//tr[@id='claimcheck_0']/td[2]/input", "3333333333");
			selenium.select("//div[@id='item_0']/table/tbody/tr[3]/td/select", "label=WT - White/clear");
			selenium.select("//div[@id='item_0']/table/tbody/tr[3]/td/select[2]", "label=50");
			selenium.select("//tr[@id='inventory_0_0']/td/select", "label=Art");
			selenium.select("//tr[@id='inventory_0_1']/td/select", "label=Audio");
			selenium.select("//tr[@id='inventory_0_2']/td/select", "label=Infant");
			selenium.type("//tr[@id='inventory_0_0']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_0_1']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_0_2']/td[2]/input", "test");
			selenium.click("//input[@id='saveButton']");
			assertEquals("Please select a value for  # RON Kits issued", selenium.getAlert());
			selenium.select("//select[@id='numRonKitsIssued']", "label=1");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed on load the damaged incident page after pressing skip prepopulation.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a"));
		    selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
	    	waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to create the incident.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("1", selenium.getValue("//select[@id='numRonKitsIssued']"));
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to reload the damaged incident.");
			return;
		}
		
		if (checkNoErrorPage()) {
			loginOGAdminProcedure();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to verify the damaged incident.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//ul[@id='menubuilder9']/li[2]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to load back in as ogadmin.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[17]/td/a[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to load the companies page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[12]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to load the 3rd companies page.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[11]/td[4]/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to load the permissions page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.uncheck("//div[@id='maincontent']/table/tbody/tr[18]/td[2]/table/tbody/tr[8]/td/input");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[32]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to load the permissions page.");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to save the permissions page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			loginAdminProcedure();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to log out of the application.");
			return;
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("LDVRK: An error occurred while attempting to log back into the application.");
			return;
		}
		
	}
	
}
