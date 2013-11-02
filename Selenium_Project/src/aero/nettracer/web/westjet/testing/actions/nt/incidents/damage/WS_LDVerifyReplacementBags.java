package aero.nettracer.web.westjet.testing.actions.nt.incidents.damage;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_LDVerifyReplacementBags extends WS_SeleniumTest {

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
			verifyFalse(isElementPresent(By.xpath("//select[@id='replacementBagIssued']")));
			setPermissions(new String[] {"627"}, new boolean[] {true});
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page after pressing skip prepopulation.");
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
			verifyTrue(isElementPresent(By.xpath("//select[@id='replacementBagIssued']")));
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
			verifyTrue(isElementPresent(By.xpath("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a")));
		    selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
	    	waitForPageToLoadImproved();
		} else {
			System.out.println("LDVRK: An error occurred while attempting to create the incident.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyEquals("1", selenium.getValue("//select[@id='replacementBagIssued']"));
			setPermissions(new String[] {"627"}, new boolean[] {false});
		} else {
			System.out.println("LDVRK: An error occurred while attempting to reload the damaged incident.");
			return;
		}
	}
	
}
