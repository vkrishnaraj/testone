package aero.nettracer.web.westjet.testing.actions.nt.incidents.damage;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_LDVerifyReplacementBags extends LoginUtil {

	@Test
	public void testRonKits() throws Exception {
		clickMenu("menucol_2.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(selenium.isTextPresent("Tradeout Issued"));
			verifyFalse(isElementPresent("//select[@id='replacementBagIssued']"));
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
			clickMenu("menucol_9.2");
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
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[12]/td[5]/a");
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
			selenium.check("name=627");
			selenium.click("name=save");
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
			System.out.println("LDVRK: Failed on log out after setting the issue replacement bag permission.");
			return;
		}
		
		if (checkNoErrorPage()) {
			clickMenu("menucol_2.1");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed on log back in after setting the issue replacement bag permission.");
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
			verifyTrue(selenium.isTextPresent("Tradeout Issued"));
			verifyTrue(isElementPresent("//select[@id='replacementBagIssued']"));
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[4]/input", "444444");

			selenium.type("name=passenger[0].lastname", "Test");
			selenium.type("name=passenger[0].firstname", "Test");
			selenium.type("name=addresses[0].address1", "Test");
			selenium.type("name=addresses[0].city", "Test");
			selenium.type("name=addresses[0].mobile", "3333333333");
			selenium.type("name=addresses[0].email", "Test@Test.com");
			selenium.type("name=theitinerary[0].legfrom", "ABI");
			selenium.type("name=theitinerary[0].legto", "ABI");
			selenium.type("name=theitinerary[0].flightnum", "1234");
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");
			selenium.click("id=itcalendar20");
			selenium.click("link=Today");
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Art");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Audio");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Infant");
			selenium.type("name=inventorylist[0].description", "test");
			selenium.type("name=inventorylist[1].description", "test");
			selenium.type("name=inventorylist[2].description", "test");
			selenium.select("name=theitem[0].bagtype", "label=50");
			selenium.select("name=theitem[0].color", "label=WT - White/clear");
			selenium.type("//div[@id='item_0']/table/tbody/tr/td[2]/input", "3333333333");
			selenium.type("//div[@id='item_0']/table/tbody/tr[7]/td/input", "test");
			selenium.click("name=saveButton");
			assertEquals("Please select a value for  Tradeout Issued", selenium.getAlert());
			selenium.select("id=replacementBagIssued", "label=yes");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: Failed on load the damaged incident page after pressing skip prepopulation.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a"));
		    selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
	    	waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to create the incident.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("1", selenium.getValue("//select[@id='replacementBagIssued']"));
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
			clickMenu("menucol_9.2");
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
			selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[12]/td[5]/a");
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
			selenium.uncheck("name=627");
			selenium.click("name=save");
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
