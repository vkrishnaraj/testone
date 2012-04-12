package aero.nettracer.web.westjet.testing.actions.nt.taskman;

import org.junit.Test;

import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.Settings;

public class US_DisputeManage extends LoginUtil {
	@Test
	public void testDisputeManage() throws Exception {
		selenium.click("link=[ Logout ]");
		waitForPageToLoadImproved();
		
		if(checkNoErrorPage())
		{
			loginOGAdminProcedure();
		}
		else
		{
			System.out.println("DisputeManageError: After Logging out");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_9.2");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Logging in as OGAdmin");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=3");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after going into Maintain Companies");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//a[contains(@href, 'createGroup.do?companyCode=WS')]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after going into last page");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//a[contains(@href, 'componentAdmin.do?groupId=151')]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Selecting Company");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.check("name=700");
		selenium.check("name=701");
		selenium.click("name=save");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after selecting Admin Group");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=[ Logout ]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after giving permissions to Admin Group");
			return;
		}
				
		if(checkNoErrorPage())
		{
		loginAdminProcedure();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Logging out");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=menucol_2.1");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Logging in");
			return;
		}
		
		if(checkNoErrorPage())
		{
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
		
		}
		else
		{
			System.out.println("DisputeTestError: Failed after beginning to create a new damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
			String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
			Settings.INCIDENT_ID_WS = incident_id;
			selenium.click("id=menucol_2.3");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeTestError: Failed after creating a damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=calendar");
		selenium.click("link=Today");
		selenium.click("id=calendar2");
		selenium.click("link=Today");
		selenium.type("name=lastname", "Test");
		selenium.type("name=firstname", "Test");
		selenium.type("name=incident_ID", Settings.INCIDENT_ID_WS);
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Searching for Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after filtering for created Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.select("name=loss_code", "label=10- Station Errors -- Tagging");
		selenium.click("name=doclose");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Closing created Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=menucol_2.3");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Closing Created Damaged - 2");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=calendar");
		selenium.click("link=Today");
		selenium.click("id=calendar2");
		selenium.click("link=Today");
		selenium.type("name=lastname", "Test");
		selenium.type("name=firstname", "Test");
		selenium.type("name=incident_ID", Settings.INCIDENT_ID_WS);
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Searching Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//td[@id='navmenucell']/div/dl/dd[10]/a/span[2]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Filtering for Created Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Closing Damaged");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after locking Station");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("name=lock_faultcode");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after unlocking station");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("name=unlock_faultcode");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after locking code");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("xpath=(//input[@id='button'])[5]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after unlocking code");
			return;
		}
		
		if(checkNoErrorPage())
		{
			selenium.select("name=faultstation_id", "label=AUA");
			selenium.select("name=loss_code", "label=10- Station Errors -- Tagging");
		selenium.click("id=button");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("QuickHistoryError: Failed after Disputing the Fault");
			return;
		}
		
		
		//Manage Disputes?
		if(checkNoErrorPage())
		{
			selenium.click("id=menucol_0.28");
			waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed Attempting to save Fault");
			return;
		}
		
		/*if(checkNoErrorPage())
		{
		selenium.click("xpath=(//a[contains(text(),'Manage Fault Dispute')])[2]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after logging in");
			return;
		}*/
		
		if(checkNoErrorPage())
		{
			
		selenium.select("name=dispute_type", "label=Code Only Disputes");
		selenium.click("name=search");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after loading manage fault dispute page");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.select("name=dispute_type", "label=Station Only Disputes");
		selenium.click("name=search");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after searching for Code Only Disputes");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.select("name=dispute_type", "label=Code and Station Disputes");
		selenium.click("name=search");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after searching for Station Only Disputes");
			return;
		}
		
//		if(checkNoErrorPage()) //COMMENTED FOR ERROR PURPOSES. MAY NEED LATER
//		{
//		selenium.type("name=incident_ID", Settings.INCIDENT_ID_WS);
//		selenium.click("name=getnext");
//		waitForPageToLoadImproved();
//		}
//		else
//		{
//			System.out.println("DisputeManageError: Failed after searching for Code and Station Disputes");
//			return;
//		}
//		
//		if(checkNoErrorPage())
//		{
//		selenium.click("xpath=(//input[@id='button'])[3]");
//		waitForPageToLoadImproved();
//		}
//		else
//		{
//			System.out.println("DisputeManageError: Failed after getting the next");
//			return;
//		}
//		
//		if(checkNoErrorPage())
//		{
//		selenium.click("id=button");
//		waitForPageToLoadImproved();
//		}
//		else
//		{
//			System.out.println("DisputeManageError: Failed after Modifying the Dispute");
//			return;
//		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=[ Logout ]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after giving returning to Dispute Search");
			return;
		}
		
		if(checkNoErrorPage())
		{
			loginOGAdminProcedure();
		}
		else
		{
			System.out.println("DisputeManageError: After Logging out");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("id=menucol_9.2");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Logging in as OGAdmin");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=3");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after going into Maintain Companies");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//a[contains(@href, 'createGroup.do?companyCode=WS')]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after going into last page");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("//a[contains(@href, 'componentAdmin.do?groupId=151')]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Selecting Company");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.uncheck("name=700");
		selenium.uncheck("name=701");
		selenium.click("name=save");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after selecting Admin Group");
			return;
		}
		
		if(checkNoErrorPage())
		{
		selenium.click("link=[ Logout ]");
		waitForPageToLoadImproved();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after giving permissions to Admin Group");
			return;
		}
		
		if(checkNoErrorPage())
		{
		loginAdminProcedure();
		}
		else
		{
			System.out.println("DisputeManageError: Failed after Logging out");
			return;
		}
	}
}
