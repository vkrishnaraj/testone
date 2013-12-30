package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class WN_OHD_Sort_Search extends WN_SeleniumTest {

//	@Test
//	public void testOHDSearch() throws Exception {
//		verifyTrue(setPermissions(new String[] {PermissionsUtil.TO_BE_INVENTORIED}, new boolean[] {true}));
//
//		clickMenu("menucol_4.4");
//		waitForPageToLoadImproved();
//		if(checkNoErrorPage()) {
//			selenium.click("id=calendar3");
//			selenium.click("link=Today");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to reach OHD Search page - !!!!!!!!!!!!!!!!!!");
//		}
//
//		if(checkNoErrorPage()) {
//			verifyTrue(isTextPresent(Settings.ONHAND_ID_WN));
//			selenium.type("name=s_inventorydate", "");
//			selenium.click("id=calendar4");
//			selenium.click("link=Today");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on start inventory date- !!!!!!!!!!!!!!!!!!");
//		}
//
//		if(checkNoErrorPage()) {
//			verifyTrue(isTextPresent(Settings.ONHAND_ID_WN));
//			selenium.click("id=calendar3");
//			selenium.click("link=Today");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on end inventory date- !!!!!!!!!!!!!!!!!!");
//		}
//
//		if(checkNoErrorPage()) {
//			verifyTrue(isTextPresent(Settings.ONHAND_ID_WN));
//			selenium.type("name=s_inventorydate", "");
//			selenium.type("name=e_inventorydate", "");
//			selenium.click("id=calendar5");
//			selenium.click("link=Today");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on start and end inventory date- !!!!!!!!!!!!!!!!!!");
//		}
//		
//		if(checkNoErrorPage()) {
//			verifyTrue(isTextPresent(Settings.ONHAND_ID_WN));
//			selenium.type("name=routingstation", "ATL");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on routing date- !!!!!!!!!!!!!!!!!!");
//		}
//
//		if(checkNoErrorPage()) {
//			verifyTrue(isTextPresent(Settings.ONHAND_ID_WN));
//			selenium.type("name=routingstation", "LAX");
//			selenium.click("id=button");
//			waitForPageToLoadImproved();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on routing Station- !!!!!!!!!!!!!!!!!!");
//		}
//
//		if(checkNoErrorPage()) {
//			verifyFalse(isTextPresent(Settings.ONHAND_ID_WN));
//			goToTaskManager();
//		} else {
//			System.out.println("!!!!!!!!!!!!!!! - Failed to Search on routing date 2- !!!!!!!!!!!!!!!!!!");
//		}
//	}
	
	@Test
	public void testSortSearchText() throws Exception {

		clickMenu("menucol_4.4");
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
	
	@Test
	public void testStationOnHandSort() throws Exception {

		clickMenu("menucol_0.0");
		waitForPageToLoadImproved();
		String sortCheck="";
		if(checkNoErrorPage()) {
			selenium.click("xpath=(//a[contains(text(),'On-hand Bags')])[2]");

			waitForPageToLoadImproved(1000,false);
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to access Task Manager - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			click(By.linkText("On-hand Number"),true,true);
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to access Station On Hand page - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[2]");
			click(By.linkText("On-hand Number"),true,true);
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Number - !!!!!!!!!!!!!!!!!!");
		}

		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[2]",sortCheck);
			click(By.linkText("Incident Number"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Number Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[3]");
			click(By.linkText("Incident Number"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Incident Number - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[3]",sortCheck);
			click(By.linkText("OHD Date/Time"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Incident Number Reverse- !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[4]");
			click(By.linkText("OHD Date/Time"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Create Date - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[4]",sortCheck);
			click(By.linkText("Date/Time Modified"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Create Date Reverse- !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[5]");
			click(By.linkText("Date/Time Modified"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Modified Date - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[5]",sortCheck);
			click(By.linkText("Bag Tag Number"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by OHD Modified Date Reverse- !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[6]");
			click(By.linkText("Bag Tag Number"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Bag Tag- !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[6]",sortCheck);
			click(By.linkText("Status"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Bag Tag Reverse- !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[7]");
			click(By.linkText("Status"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Status - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[7]",sortCheck);
			click(By.linkText("Color"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Status Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[8]");
			click(By.linkText("Color"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Color - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[8]",sortCheck);
			click(By.linkText("Type"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Color Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[9]");
			click(By.linkText("Type"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Type - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[9]",sortCheck);
			click(By.linkText("Destination"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Type Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[10]");
			click(By.linkText("Destination"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Destination - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[10]",sortCheck);
			click(By.linkText("Name"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Destination Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[11]");
			click(By.linkText("Name"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Name - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[11]",sortCheck);
			click(By.linkText("Comments"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Name Reverse - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			sortCheck=selenium.getText("//td[@id='middlecolumn']/div/table/tbody/tr/td[12]");
			click(By.linkText("Comments"));
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Comments - !!!!!!!!!!!!!!!!!!");
		}
		if(checkNoErrorPage()) {
			verifyNotEquals("//td[@id='middlecolumn']/div/table/tbody/tr/td[12]",sortCheck);
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Sort by Comments Reverse - !!!!!!!!!!!!!!!!!!");
		}
	}
}
