package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_LDVerifyRonKits extends WS_SeleniumTest {

	@Test
	public void testRonKits() throws Exception {
		clickMenu("menucol_1.1");
		
		if (checkNoErrorPage()) {
			driver.findElement(By.name("skip_prepopulate")).click();
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page.");
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(driver.getPageSource().contains("# RON Kits issued:"));
			setPermissions(new String[] {"626"}, new boolean[] {true});
		} else {
			System.out.println("LDVRK: Failed to load the delayed incident page after pressing skip prepopulation.");
			return;
		}
		
		try {
			if (checkNoErrorPage()) {
				clickMenu("menucol_1.1");
			} else {
				System.out.println("LDVRK: Failed on log back in after setting the issue ron kit permission.");
				return;
			}
			
			if (checkNoErrorPage()) {
				driver.findElement(By.name("skip_prepopulate")).click();
			} else {
				System.out.println("LDVRK: Failed on load the damaged incident page.");
				return;
			}
	
			if (checkNoErrorPage()) {
				verifyTrue(driver.getPageSource().contains("# Toiletry Kits issued:"));
				verifyTrue(isElementPresent(By.xpath("//select[@id='numRonKitsIssued']")));
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
				selenium.type("name=claimcheck[0].claimchecknum", "3333333333");
				selenium.click("savetracingButton");
				assertEquals("Please select a value for  # Toiletry Kits issued", selenium.getAlert());
				selenium.select("//select[@id='numRonKitsIssued']", "label=1");
				selenium.click("savetracingButton");
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
				verifyEquals("1", selenium.getValue("//select[@id='numRonKitsIssued']"));
			} else {
				System.out.println("LDVRK: An error occurred while attempting to reload the damaged incident.");
				return;
			}
		} finally {
			setPermissions(new String[] {"626"}, new boolean[] {false});
		}
	}
	
}
