package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WN_OHD_Sort_Search extends DefaultSeleneseTestCase {

	@Test
	public void testSortText() throws Exception {
		//TODO: Create OnHand Code and Use the ID (unable to guarantee it's use on a constant basis)
		
		selenium.click("id=menucol_4.4");
		waitForPageToLoadImproved();
		String sortCheck="";
		if(checkNoErrorPage()) {
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to reach OHD Search page - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td");
			selenium.click("link=On-hand Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to search for OHDs  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td",sortCheck);
			selenium.click("link=Color");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort By On-Hand Number  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[5]");
			selenium.click("link=Color");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort By Color  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[5]",sortCheck);
			selenium.click("link=Bag Tag Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Color - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[3]");
			selenium.click("link=Bag Tag Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Bag Tag Number - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[3]",sortCheck);
			selenium.click("link=Name");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Bag Tag Number - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[10]");
			selenium.click("link=Name");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Name - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[10]",sortCheck);
			selenium.click("link=Type");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Name - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[6]");
			selenium.click("link=Type");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Type - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[6]",sortCheck);
			selenium.click("link=Status");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Type - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("link=Status");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Status - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("link=Airline Found");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Status - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("link=Airline Found");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Airline Found - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("id=foundStation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Airline Found - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("id=foundStation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Station Found - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("id=holdStation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Station Found - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("id=holdStation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Holding Station - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			selenium.click("link=OHD Date/Time");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Holding Station - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[2]");
			selenium.click("link=OHD Date/Time");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on OHD Date - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table[2]/tbody/tr[2]/td[2]",sortCheck);
			goToTaskManager();
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on OHD Date - !!!!!!!!!!!!!!!!!!");
		}
		
	}
}
