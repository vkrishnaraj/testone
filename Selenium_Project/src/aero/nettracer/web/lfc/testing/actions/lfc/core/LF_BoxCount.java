package aero.nettracer.web.lfc.testing.actions.lfc.core;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class LF_BoxCount extends DefaultSeleneseTestCase {
	
	@Test
	public void testBoxCount() {
		clickMenu("menucol_2.5");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=loadButton");
			waitForPageToLoadImproved();
		}

		if(checkNoErrorPage())
		{
			selenium.select("id=stationLists", "label=ABE");
			selenium.click("css=input[type=\"button\"]");
			waitForPageToLoadImproved(3000, false);
			selenium.select("id=stationLists", "label=BOI");
			selenium.click("css=input[type=\"button\"]");
			waitForPageToLoadImproved(3000, false);
			selenium.select("id=stationLists", "label=AUS");
			selenium.click("css=input[type=\"button\"]");
			waitForPageToLoadImproved(3000, false);
			selenium.select("id=stationLists", "label=AUS");
			selenium.click("css=input[type=\"button\"]");
			waitForPageToLoadImproved(3000, false);
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("link=Remove");
			waitForPageToLoadImproved(3000, false);
			selenium.click("link=Remove");
			waitForPageToLoadImproved(3000, false);
			selenium.click("link=Remove");
			waitForPageToLoadImproved(3000, false);
			selenium.click("link=Remove");
			waitForPageToLoadImproved(3000, false);
		}
		
	}
}