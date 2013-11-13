package aero.nettracer.web.lfc.testing.actions.lfc.core;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class LF_QuickHistory extends DefaultSeleneseTestCase {
	
	@Test
	public void testQuickHistory() {

		clickMenu("menucol_2.2");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			selenium.type("name=barcode",Settings.FOUND_ID_LF);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after clicking Enter Items link");
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
			clickMenu("menucol_2.4");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after saving a new found item");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.type("name=id",Settings.LOST_ID_LF);
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
