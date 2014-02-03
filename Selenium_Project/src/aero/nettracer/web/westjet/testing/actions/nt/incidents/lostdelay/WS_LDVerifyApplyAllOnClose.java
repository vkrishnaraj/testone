package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_LDVerifyApplyAllOnClose extends DefaultSeleneseTestCase {
	
	@Test
	public void testApplyToAllOnClose() {
		clickMenu("menucol_1.1");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			selenium.click("//div[@id='maincontent']/table/tbody/tr[2]/td/input[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to load the lost delay prepopulate screen.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.type("//div[@id='maincontent']/table[2]/tbody/tr/td[4]/input", "888888");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[2]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[2]/td[2]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[5]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td[3]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[6]/td[4]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[8]/td[3]/input", "test");
			selenium.type("//div[@id='pax_0']/table/tbody/tr[9]/td[3]/input", "test@test.com");
			selenium.type("//table[@id='hidexItinerary']/tbody/tr/td/input[3]", "ABR");
			selenium.type("//table[@id='hidexItinerary']/tbody/tr/td/input[4]", "ABR");
			selenium.type("//table[@id='hidexItinerary']/tbody/tr/td[2]/input", "1234");
			selenium.click("//img[@id='itcalendar0']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.click("//img[@id='itcalendar20']");
			selenium.click("//div[@id='calstyle']/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a");
			selenium.type("//tr[@id='claimcheck_0']/td[2]/input", "3333333333");
			selenium.click("document.forms['incidentForm'].elements['addclaimcheck']");
			waitForPageToLoadImproved();
			if (!checkNoErrorPage()) {
				System.out.println("LDVAAOC: failed to add a second claim check.");
				verifyTrue(false);
				return;
			}
			selenium.type("//tr[@id='claimcheck_1']/td[2]/input", "4444444444");
			selenium.select("//div[@id='item_0']/table/tbody/tr[4]/td/select", "label=WT - White/clear");
			selenium.select("//div[@id='item_0']/table/tbody/tr[4]/td/select[2]", "label=02");
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Art");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Audio");
			selenium.type("name=inventorylist[0].description", "test");
			selenium.type("name=inventorylist[1].description", "test");
			selenium.type("name=inventorylist[2].description", "test");
			selenium.click("//div[@id='maincontent']/center[5]/input");
			waitForPageToLoadImproved();
			selenium.select("//div[@id='item_1']/table/tbody/tr[4]/td/select", "label=BK - Black");
			selenium.select("//div[@id='item_1']/table/tbody/tr[4]/td/select[2]", "label=03");
			selenium.select("name=inventorylist[20].categorytype_ID", "label=Alcohol");
			selenium.select("name=inventorylist[21].categorytype_ID", "label=Art");
			selenium.select("name=inventorylist[22].categorytype_ID", "label=Audio");
			selenium.type("name=inventorylist[20].description", "test");
			selenium.type("name=inventorylist[21].description", "test");
			selenium.type("name=inventorylist[22].description", "test");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to load the lost delayed incident page.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a")));
 		    selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to create the lost delayed incident.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.click("//td[@id='navmenucell']/div/dl/dd[13]/a/span[2]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to save the lost delayed incident.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			selenium.type("name=theitem[0].arrivedonflightnum", "1234");
			selenium.click("id=calendar20");
			selenium.click("link=Today");
			verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[2]/input"));
			verifyEquals("", selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/input"));
			selenium.click("id=button");
			verifyEquals("1234", selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[2]/input"));
			verifyEquals(Settings.TODAYS_DATE, selenium.getValue("//div[@id='maincontent']/table[2]/tbody/tr[2]/td[3]/input"));
			selenium.click("//input[@name='save']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to reload the lost delayed incident.");
			verifyTrue(false);
			return;
		}
		
	}

}
