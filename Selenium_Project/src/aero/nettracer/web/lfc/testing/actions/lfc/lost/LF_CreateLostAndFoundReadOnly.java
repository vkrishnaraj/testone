package aero.nettracer.web.lfc.testing.actions.lfc.lost;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_CreateLostAndFoundReadOnly extends DefaultSeleneseTestCase {

	private static String lostId;
	private static String foundId;
	
	@Test
	public void testARemoveReopenPermission() {
		selenium.click("//a[contains(@href, 'logoff.do')]");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			selenium.open("/lostandfound/logon.do?companyCode=OW&username=ogadmin&password=Ladendead51!");
			selenium.click("//a[contains(@href, 'companyAdmin.do')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to logout.");
			return;
		}
			
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'createGroup.do?companyCode=LF')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: Failed to login as ogadmin.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'componentAdmin.do?groupId=240')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to open the Admin group for LF.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[6]/td[2]/table/tbody/tr[18]/td/input");
			selenium.click("xpath=(//input[@id='button'])[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to open the permissions page for the Admin group.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'logoff.do')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to save the permissions page for the Admin group.");
			return;			
		}
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[4]/td[2]/input", "ntauto");
			selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[5]/td[2]/input", "IpoL!Jan7");
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to log off.");
			return;			
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("CLAFRO: An error occurred when attempting to log in as: " + Settings.USERNAME_ADMIN + ".");
		}
		
	}
	
	@Test
	public void testBCreateLost() throws Exception {
		selenium.click("//a[contains(@href, 'create_lost_report.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td/input", "Gordon");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[2]/input", "Chris");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/input", "420 East Ave");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td/input", "Lithia Springs");
			selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[2]/select", "label=Georgia");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[3]/td[4]/input", "30314");
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr[4]/td/input", "5556667777");
			selenium.select("document.forms['lostReportForm'].elements[24]", "label=Home");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/input", "Nokia");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[2]/input", "NKC987");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[2]/td[3]/input", "Old Brick");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/select", "label=Yellow");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Red");
			selenium.click("xpath=(//input[@id='button'])[3]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: Failed to load the Lost Report page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your lost report was successfully saved."));
			LF_CreateLostAndFoundReadOnly.lostId = selenium.getValue("//div[@id='maincontent']/table/tbody/tr/td/input");
			System.out.println("CLAFRO: Created Lost Report: " + LF_CreateLostAndFoundReadOnly.lostId);
		} else {
			System.out.println("CLAFRO: An error occurred while saving the Lost Report.");
			return;
		}
		
	}
	
	@Test
	public void testCCreateFoundItem() {
		selenium.click("//a[contains(@href, 'create_found_item.do?createNew=1')]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_CreateLostAndFoundReadOnly.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_CreateLostAndFoundReadOnly.foundId);
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[2]/img");
			selenium.click("//a[contains(text(),'Today')]");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td/input", "Nokia");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[2]/input", "NKC987");
			selenium.type("//div[@id='maincontent']/table[3]/tbody/tr[3]/td[3]/input", "Old Brick");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td/select", "label=Cellphone");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[4]/td[3]/select", "label=Yellow");
			selenium.select("//div[@id='maincontent']/table[3]/tbody/tr[5]/td[3]/select", "label=Red");
			selenium.click("xpath=(//input[@id='button'])[3]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: Failed to load the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("CDFF: Created Found Item: " + LF_CreateLostAndFoundReadOnly.foundId);
			
			// Manually match to the Lost Report that we just created.
			selenium.type("//div[@id='maincontent']/table[4]/tbody/tr/td/input", LF_CreateLostAndFoundReadOnly.lostId);
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: Failed to create the Found Item.");
			return;
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("CLAFRO: An error occurred while attempting to create a manual match.");
		}
		
	}
	
	@Test
	public void testDLostAndFoundReadOnly() {
		selenium.select("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/select", "label=Closed");
		selenium.click("//div[@id='maincontent']/center[3]/input[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyTrue(selenium.isTextPresent("[Undo Confirmation]"));
			verifyFalse(selenium.isElementPresent("xpath=(//a[contains(text(),'Undo Confirmation')])[2]"));
			verifyFalse(selenium.isEditable("//div[@id='maincontent']/table[5]/tbody/tr[2]/td/input"));
			verifyFalse(selenium.isEditable("//div[@id='maincontent']/table[5]/tbody/tr[3]/td/input"));
			verifyFalse(selenium.isEditable("//div[@id='maincontent']/table[5]/tbody/tr[3]/td[2]/input"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred while saving the Found Item after closing.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isElementPresent("xpath=(//a[contains(text(),'Undo Confirmation')])[2]"));
		} else {
			System.out.println("CLAFRO: An error occurred while attempting to open the Lost Report from the Found Item.");
			return;
		}
		
	}
	
	@Test
	public void testEResetReopenPermission() {
		selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.open("/lostandfound/logon.do?companyCode=OW&username=ogadmin&password=Ladendead51!");
			selenium.click("//a[@id='menucol_9.2']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred while attempting to log out.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'createGroup.do?companyCode=LF')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred while attempting to log in as ogadmin.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//a[contains(@href, 'componentAdmin.do?groupId=240')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to open the Admin group for LF.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[6]/td[2]/table/tbody/tr[18]/td/input");
			selenium.click("//div[@id='maincontent']/table/tbody/tr[32]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to open the permissions page for the Admin group.");
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to save the permissions page for the Admin group.");
			return;	
		}
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[4]/td[2]/input", "ntauto");
			selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[5]/td[2]/input", "IpoL!Jan7");
			selenium.click("//input[@id='button']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred when attempting to log off.");
			return;
		}
		
		if (!checkNoErrorPage()) {
			System.out.println("CLAFRO: An error occurred when attempting to log in as: " + Settings.USERNAME_ADMIN + ".");
		}
		
	}
	
}
