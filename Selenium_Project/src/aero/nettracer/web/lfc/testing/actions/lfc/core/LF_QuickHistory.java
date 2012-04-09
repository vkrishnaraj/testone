package aero.nettracer.web.lfc.testing.actions.lfc.core;

import org.junit.Test;

import aero.nettracer.web.lfc.testing.actions.lfc.salvage.LF_Salvage;
import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_QuickHistory extends DefaultSeleneseTestCase {
	
	@Test
	public void testQuickHistory() {
		String LOW_LT_30;
			selenium.click("id=menucol_3.1");
			waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			selenium.click("id=calendar");
			selenium.click("link=Today");
			LOW_LT_30= String.valueOf(System.currentTimeMillis());
			selenium.type("id=barcode",LOW_LT_30);
			selenium.select("id=category", "label=Bags");
			selenium.click("id=saveButton");
			waitForPageToLoadImproved();
			
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after clicking Enter Items link");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_2.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a new found item");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after clicking Search Lost/Found Items link - 1");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=3");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Searching Lost/Found Items");
			return;
		}	
		
		if(checkNoErrorPage())
		{
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after loading Lost Items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_2.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving Lost items");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.select("name=type", "label=Found");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after clicking Search Lost/Found Items link - 2");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("link=4312");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after changing to found and searching for Found Items");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.controlKeyDown();
			selenium.keyPress("id=header", "\\72");
			selenium.controlKeyUp();
			//verifyTrue(false);
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Loading Found Item");
			return;
		}
	}
	
}
