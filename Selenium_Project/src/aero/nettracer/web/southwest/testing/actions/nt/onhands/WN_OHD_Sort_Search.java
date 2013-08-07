package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;

public class WN_OHD_Sort_Search extends DefaultSeleneseTestCase {

	@Test
	public void testSortText() throws Exception {
		//TODO: Create OnHand Code and Use the ID (unable to guarantee it's use on a constant basis)
		
		selenium.click("id=menucol_4.4");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage()) {
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to reach OHD Search page - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=On-hand Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to search for OHDs  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Color");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort By On-Hand Number  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Color");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort By Color  - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Bag Tag Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Color - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Bag Tag Number");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Bag Tag Number - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Name");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Bag Tag Number - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Name");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Name - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Type");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Name - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			selenium.click("link=Type");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on Type - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
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
			selenium.click("link=OHD Date/Time");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on OHD Date - !!!!!!!!!!!!!!!!!!");
		}
		
		if(checkNoErrorPage()) {
			goToTaskManager();
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to sort on OHD Date - !!!!!!!!!!!!!!!!!!");
		}
		
	}
}
