package aero.nettracer.web.westjet.testing.actions.nt.core;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class WS_QuickHistory extends WS_SeleniumTest {
	
	@Test
	public void testLogin() throws Exception {

		clickMenu("menucol_1.4");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			selenium.type("incident_ID", Settings.INCIDENT_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after clicking View Delayed Items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			clickMenu("menucol_2.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a delayed item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.type("incident_ID", Settings.DAMAGE_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a delayed item and/or view Damaged items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			clickMenu("menucol_3.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a damaged item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.type("incident_ID", Settings.PILFERAGE_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a damaged item and/or view Pilfered items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			clickMenu("menucol_4.4");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a pilfered item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.type("incident_ID", Settings.ONHAND_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a pilfered item and view on-hand");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("xpath=(//a[contains(@href, 'addOnHandBag.do?ohd_ID=')])[1]");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a delayed item and/or view Damaged items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.select("name=bagColor", "label=WT - White/clear");
			selenium.select("name=bagType", "label=01");
			selenium.type("itinerarylist[0].legfrom", "LAS");
			selenium.type("itinerarylist[0].legto", "ATL");
			selenium.type("itinerarylist[0].flightnum", "1234");
			selenium.click("id=calendar20");
			selenium.click("link=Today");
			selenium.click("id=calendar30");
			selenium.click("link=Today");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading on-hand");
			return;
		}
		
		if(checkNoErrorPage())
		{
			clickMenu("menucol_5.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving on-hand");
			return;
		}
		
		if(checkNoErrorPage())
		{	
			selenium.select("name=report_type", "label=Found");
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after viewing found/lost items - 1");
			return;
		}
		if(checkNoErrorPage())
		{
			selenium.click("xpath=(//a[contains(@href, 'addFound.do?file_ref_number=')])[1]");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching found/lost items - 1");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=save");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a found item ");
			return;
		}
		
		if(checkNoErrorPage())
		{
			clickMenu("menucol_5.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving found item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			clickMenu("menucol_8.2");
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving lost item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			click(By.id("button"));
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after viewing claims list");
			return;
		}
		
		if(checkNoErrorPage())
		{
			click(By.xpath("//div[@id='maincontent']/table[3]/tbody/tr[2]/td/a"));
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching claims");
			return;
		}
		
		if(checkNoErrorPage())
		{
			driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
			click(By.name("save"));
			driver.manage().timeouts().pageLoadTimeout(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a claim");
			return;
		}

		if(checkNoErrorPage())
		{
			loadQuickHistory();
			closeQuickHistory();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a claim");
			return;
		}
	}
	
}
