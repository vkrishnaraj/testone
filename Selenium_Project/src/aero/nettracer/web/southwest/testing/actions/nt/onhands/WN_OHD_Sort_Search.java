package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WN_OHD_Sort_Search extends DefaultSeleneseTestCase {

	@Test
	public void testSortText() throws Exception {
		// MJS: initial state is drivers license collection enabled
		// 		and view/edit drivers license disabled.


		selenium.click("id=menucol_4.4");
		waitForPageToLoadImproved();
		selenium.click("id=button");
		waitForPageToLoadImproved();
		verifyEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		selenium.click("link=Color");
		waitForPageToLoadImproved();
		verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		selenium.click("link=Bag Tag Number");
		waitForPageToLoadImproved();
		verifyEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		selenium.click("link=Name");
		waitForPageToLoadImproved();
		verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		selenium.click("link=Type");
		waitForPageToLoadImproved();
		verifyEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		selenium.click("link=On-hand Number");
		waitForPageToLoadImproved();
		verifyEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td", "LZWN000000006");
		goToTaskManager();
		waitForPageToLoadImproved();
		
	}
}
