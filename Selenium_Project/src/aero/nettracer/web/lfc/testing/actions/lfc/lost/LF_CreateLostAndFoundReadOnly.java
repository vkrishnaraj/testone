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
			waitForPageToLoadImproved();
			clickMenu("menucol_9.2");
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
			selenium.uncheck("name=618");
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
		clickMenu("menucol_2.3");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {

			selenium.select("name=lost.companyId", "label=Southwest Airlines");
			selenium.type("name=lost.client.lastName", "Gordon");
			selenium.type("name=lost.client.firstName", "Chris");
			selenium.type("name=lost.client.address.decryptedAddress1", "420 East Ave");
			selenium.type("name=lost.client.address.decryptedCity", "Lithia Springs");
			selenium.select("id=state", "label=Georgia");
			selenium.type("name=lost.client.address.decryptedZip", "30314");
			selenium.type("id=priInterNum", "1"); //112223333
			selenium.type("id=priAreaNum", "122");
			selenium.type("id=priExchaNum", "23");
			selenium.type("id=priLineNum", "333");
			selenium.select("id=priPhoneType", "label=Home");
			selenium.type("name=item[0].brand", "Nokia");
			selenium.type("name=item[0].serialNumber", "NKC987");
			selenium.type("name=item[0].description", "Old Brick");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("name=item[0].color", "label=Yellow");
			selenium.select("name=item[0].caseColor", "label=Red");
			selenium.select("segment[0].originId", "label=ATL");
			selenium.select("segment[0].destinationId", "label=BOS");
			selenium.click("xpath=(//input[@id='button'])[5]");
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
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			LF_CreateLostAndFoundReadOnly.foundId = String.valueOf(System.currentTimeMillis());
			selenium.type("//div[@id='maincontent']/table/tbody/tr/td/input", LF_CreateLostAndFoundReadOnly.foundId);
			selenium.select("name=found.companyId", "label=Southwest Airlines");
			selenium.select("name=found.locationId", "label=LZ");
			selenium.click("//div[@id='maincontent']/table/tbody/tr/td[2]/img");
			selenium.click("//a[contains(text(),'Today')]");
			selenium.type("name=item[0].brand", "Nokia");
			selenium.type("name=item[0].serialNumber", "NKC987");
			selenium.type("name=item[0].description", "Old Brick");
			selenium.select("id=category_0", "label=Cellphone");
			selenium.select("name=item[0].color", "label=Yellow");
			selenium.select("name=item[0].caseColor", "label=Red");
			selenium.click("xpath=(//input[@id='button'])[3]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: Failed to load the Found Item page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			System.out.println("CLAFRO: Created Found Item: " + LF_CreateLostAndFoundReadOnly.foundId);
			
			// Manually match to the Lost Report that we just created.
			selenium.type("id=foundInput", LF_CreateLostAndFoundReadOnly.lostId);
			selenium.click("id=confirmInput");
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
		selenium.select("name=found.statusId", "label=Closed");
		selenium.click("//div[@id='maincontent']/center[3]/input[2]");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Your found item was successfully saved."));
			verifyTrue(selenium.isTextPresent("[Undo Confirmation]"));
			selenium.click("//div[@id='maincontent']/table[4]/tbody/tr/td/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("CLAFRO: An error occurred while saving the Found Item after closing.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(isElementPresent("xpath=(//a[contains(text(),'Undo Confirmation')])[2]"));
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
			clickMenu("menucol_9.2");
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
			selenium.check("name=618");
			selenium.click("xpath=(//input[@id='button'])[2]");
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
