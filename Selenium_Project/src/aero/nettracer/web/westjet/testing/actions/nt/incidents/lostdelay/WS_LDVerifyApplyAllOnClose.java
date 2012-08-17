package aero.nettracer.web.westjet.testing.actions.nt.incidents.lostdelay;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WS_LDVerifyApplyAllOnClose extends DefaultSeleneseTestCase {
	
	@Test
	public void testApplyToAllOnClose() {
		selenium.click("id=menucol_1.1");
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
			selenium.type("//div[@id='pax_0']/table/tbody/tr[9]/td[2]/input", "test@test.com");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td/input", "ABR");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td/input[2]", "ABR");
			selenium.type("//table[@id='hidexItinerary0']/tbody/tr/td[2]/input", "1234");
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
			selenium.select("//div[@id='item_0']/table/tbody/tr[3]/td/select", "label=WT - White/clear");
			selenium.select("//div[@id='item_0']/table/tbody/tr[3]/td/select[2]", "label=02");
			selenium.select("//tr[@id='inventory_0_0']/td/select", "label=Alcohol");
			selenium.select("//tr[@id='inventory_0_1']/td/select", "label=Art");
			selenium.select("//tr[@id='inventory_0_2']/td/select", "label=Audio");
			selenium.type("//tr[@id='inventory_0_0']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_0_1']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_0_2']/td[2]/input", "test");
			selenium.click("//div[@id='maincontent']/center[5]/input");
			waitForPageToLoadImproved();
			selenium.select("//div[@id='item_1']/table/tbody/tr[3]/td/select", "label=BK - Black");
			selenium.select("//div[@id='item_1']/table/tbody/tr[3]/td/select[2]", "label=03");
			selenium.select("//tr[@id='inventory_1_0']/td/select", "label=Alcohol");
			selenium.select("//tr[@id='inventory_1_1']/td/select", "label=Art");
			selenium.select("//tr[@id='inventory_1_2']/td/select", "label=Audio");
			selenium.type("//tr[@id='inventory_1_0']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_1_1']/td[2]/input", "test");
			selenium.type("//tr[@id='inventory_1_2']/td[2]/input", "test");
			//selenium.select("id=numRonKitsIssued", "label=1");
			selenium.click("//input[@id='saveButton']");
			waitForPageToLoadImproved();
		} else {
			System.out.println("LDVAAOC: Failed to load the lost delayed incident page.");
			verifyTrue(false);
			return;
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isElementPresent("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a"));
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
		} else {
			System.out.println("LDVAAOC: Failed to reload the lost delayed incident.");
			verifyTrue(false);
			return;
		}
		
	}

}
