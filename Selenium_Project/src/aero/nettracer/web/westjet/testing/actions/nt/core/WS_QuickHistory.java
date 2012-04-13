package aero.nettracer.web.westjet.testing.actions.nt.core;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class WS_QuickHistory extends LoginUtil {
	
	@Test
	public void testLogin() throws Exception {
		
		selenium.click("id=menucol_1.4");
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
			selenium.click("link=" + Settings.INCIDENT_ID_WS);
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching Delayed Items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			selenium.click("id=menucol_2.3");
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
			selenium.click("link=" + Settings.DAMAGE_ID_WS);
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching damaged items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			selenium.click("id=menucol_3.3");
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
			selenium.click("link=" + Settings.PILFERAGE_ID_WS);
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching Pilfered Items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
			selenium.click("id=menucol_4.4");
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
			selenium.click("link=" + Settings.ONHAND_ID_WS);
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving searching on-hands");
			return;
		}
		
		if(checkNoErrorPage())
		{
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
			selenium.click("id=menucol_5.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving on-hand");
			return;
		}
		
		if(checkNoErrorPage())
		{
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
			selenium.click("link=ACYWS00000041");
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
			selenium.click("id=menucol_5.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving found item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after viewing found/lost items - 2");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("link=CUNWS00000021");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching found/lost items - 2");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=save");
			waitForPageToLoadImproved();	
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a lost item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_8.2");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving lost item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=button");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after viewing claims list");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("link=6");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after searching claims");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("name=save");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading a claim");
			return;
		}

		if(checkNoErrorPage())
		{
			selenium.controlKeyDown();
			selenium.keyPress("id=header", "\\72");
			selenium.controlKeyUp();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a claim");
			return;
		}
	}
	
}
