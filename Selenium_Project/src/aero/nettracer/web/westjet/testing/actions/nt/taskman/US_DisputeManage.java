package aero.nettracer.web.westjet.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.Settings;
import aero.nettracer.web.westjet.testing.WS_SeleniumTest;

public class US_DisputeManage extends WS_SeleniumTest {
	@Test
	public void testDisputeManage() throws Exception {
		setPermissions(new String[] {"700", "701"}, new boolean[] {true, true});
		
		if(checkNoErrorPage()) {
			clickMenu("menucol_2.1");
			waitForPageToLoadImproved();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeManageError: Failed after Logging in");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.type("name=passenger[0].lastname", "Test");
			selenium.type("name=passenger[0].firstname", "Test");
			selenium.type("name=recordlocator", "123456");
			selenium.type("name=addresses[0].address1", "Test");
			selenium.type("name=addresses[0].city", "Test");
			selenium.type("name=addresses[0].homephone", "5555555555");
			selenium.type("name=addresses[0].mobile", "5555555555");
			selenium.type("name=addresses[0].email", "Test@Test.com");
			selenium.click("name=email_customer");
			selenium.type("name=theitinerary[0].legfrom", "ATL");
			selenium.type("name=theitinerary[0].legto", "LAS");
			selenium.type("name=theitinerary[0].flightnum", "1234");
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");
			selenium.click("id=itcalendar20");
			selenium.click("link=Today");
			selenium.type("name=theitem[0].lnameonbag", "Test");
			selenium.type("name=theitem[0].fnameonbag", "Test");
			selenium.select("name=theitem[0].color", "label=WT - White/clear");
			selenium.select("name=theitem[0].bagtype", "label=02");
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Art");
			selenium.type("name=inventorylist[0].description", "Test");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Book");
			selenium.type("name=inventorylist[1].description", "Test");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Book");
			selenium.type("name=inventorylist[2].description", "Test");
			selenium.type("name=theitem[0].damage", "Test");
			selenium.type("name=theitem[0].discost", "30");
			selenium.type("name=theitem[0].resolutiondesc", "Test");
			selenium.type("name=theitem[0].claimchecknum", "12345678");
			selenium.click("name=saveButton");
			waitForPageToLoadImproved();
		
		} else {
			System.out.println("DisputeTestError: Failed after beginning to create a new damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
			Settings.DAMAGE_ID_WS = incident_id;
			clickMenu("menucol_2.3");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeTestError: Failed after creating a damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("id=calendar2");
			selenium.click("link=Today");
			selenium.type("name=lastname", "Test");
			selenium.type("name=firstname", "Test");
			selenium.type("name=incident_ID", Settings.DAMAGE_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Searching for Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("//td[@id='navmenucell']/div/dl/dd[11]/a/span");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after filtering for created Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.select("name=loss_code", "label=80- Damage");
			selenium.chooseOkOnNextConfirmation();
			selenium.click("name=doclose");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Closing created Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			clickMenu("menucol_2.3");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Closing Created Damaged - 2");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.type("name=incident_ID", Settings.DAMAGE_ID_WS);
			selenium.click("id=button");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Searching Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("//a[contains(@href,'damaged.do?close=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Filtering for Created Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("name=lock_faultstation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Closing Damaged");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("name=unlock_faultstation");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after locking Station");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("name=lock_faultcode");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after unlocking station");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("name=unlock_faultcode");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after locking code");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.click("xpath=(//input[@id='button'])[5]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after unlocking code");
			return;
		}
		
		if(checkNoErrorPage()) {
			selenium.select("name=faultstation_id", "label=AUA");
			selenium.select("name=loss_code", "label=80- Damage");
			selenium.click("id=button");		
			waitForPageToLoadImproved();
		} else {
			System.out.println("QuickHistoryError: Failed after Disputing the Fault");
			return;
		}
		
		//Manage Disputes?
		if (checkNoErrorPage()) {
			clickMenu("menucol_0.26");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeManageError: Failed Attempting to save Fault");
			return;
		}

		if (checkNoErrorPage()) {

			selenium.select("name=dispute_type", "label=Code Only Disputes");
			selenium.click("name=search");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeManageError: Failed after loading manage fault dispute page");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.select("name=dispute_type", "label=Station Only Disputes");
			selenium.click("name=search");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeManageError: Failed after searching for Code Only Disputes");
			return;
		}

		if (checkNoErrorPage()) {
			selenium.select("name=dispute_type",
					"label=Code and Station Disputes");
			selenium.click("name=search");
			waitForPageToLoadImproved();
		} else {
			System.out.println("DisputeManageError: Failed after searching for Station Only Disputes");
			return;
		}

		if (checkNoErrorPage()) {
			setPermissions(new String[] {"700", "701"}, new boolean[] {false, false});
		} else {
			System.out.println("DisputeManageError: Failed after giving returning to Dispute Search");
			return;
		}
	}
}
