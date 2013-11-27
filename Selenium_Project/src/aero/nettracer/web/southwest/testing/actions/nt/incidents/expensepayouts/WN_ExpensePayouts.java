package aero.nettracer.web.southwest.testing.actions.nt.incidents.expensepayouts;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_ExpensePayouts extends WN_SeleniumTest {


	@Test
	public void testSetBSOLimit(){
		clickMenu("menucol_10.3");

		if (checkNoErrorPage()) {
			click(By.xpath("(//a[contains(text(),'Admin')])[3]"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR Loading Maintain Groups page !!!!!!!!!!!");
		}
	    
		if (checkNoErrorPage()) {
			type(By.name("bsoLimit"),"150");
		    click(By.name("save"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR loading Admin Group page !!!!!!!!!!!");
		}
		

		if (checkNoErrorPage()) {
			click(By.xpath("(//a[contains(text(),'Admin')])[3]"));

		} else {
			System.out.println("!!!!!!!!!!! ERROR saving BSO Limit to Admin Group !!!!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			assertEquals("150.0",getValue(By.name("bsoLimit")));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!! ERROR saving BSO Limit to Admin Group 2 !!!!!!!!!!!");
		}
	  
	}
	
	@Test
	public void testCreateBSOExpensePayouts(){
		verifyTrue(setPermissions(new String[] { "680"}, new boolean[] { true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.name("addnewexpense"));

		if (checkNoErrorPage()) {
		    type(By.name("checkamt"),"151");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Loading Create Expense Payout Page !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Unable to Create Expense Payout. Draft Amount is over the BSO Limit: 150"));
		    type(By.name("checkamt"),"150");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Attempting to save Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {

			verifyTrue(isEditable(By.name("draft")));
			verifyTrue(isEditable(By.name("dispDraftpaiddate")));
			verifyTrue(isEditable(By.name("checkamt")));
			verifyTrue(isEditable(By.name("voucheramt")));
			verifyTrue(isEditable(By.name("newComment")));
			verifyTrue(isEditable(By.name("lastname")));
			verifyTrue(isEditable(By.name("firstname")));
			verifyTrue(isEditable(By.name("middlename")));
		    type(By.name("checkamt"),"151");
		    click(By.id("button"));
		    assertEquals("Unable to Create Expense Payout. Draft Amount is over the BSO Limit: 150",getAlert());
		    type(By.name("checkamt"),"150");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Saving Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			goToTaskManager();
			selenium.select("name=cbroStation", "label=ATL");
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Updating Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			click(By.xpath("(//a[contains(text(),'Modify')])[3]"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Changing Station  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyFalse(isEditable(By.name("draft")));
			verifyFalse(isEditable(By.name("dispDraftpaiddate")));
			verifyFalse(isEditable(By.name("checkamt")));
			verifyFalse(isEditable(By.name("voucheramt")));
			verifyFalse(isEditable(By.name("newComment")));
			verifyFalse(isEditable(By.name("lastname")));
			verifyFalse(isEditable(By.name("firstname")));
			verifyFalse(isEditable(By.name("middlename")));
			goToTaskManager();
			selenium.select("name=cbroStation", "label=LZ");
			
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Entering Existing Expense Payout  !!!!!!!!!");
		}
		if (checkNoErrorPage()) {
			verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
			click(By.xpath("(//a[contains(text(),'Modify')])[3]"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Changing Station  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isEditable(By.name("voucheramt")));
		    type(By.name("voucheramt"),"151");
		    click(By.id("button"));
			
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Entering Existing Expense Payout  !!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(isEditable(By.name("voucheramt")));
		    goToTaskManager();			
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Updating Voucher on Expense Payout  !!!!!!!!!");
		}
	}

	@Test
	public void testCreateNonBSOExpensePayouts(){
		verifyTrue(setPermissions(new String[] { "680"}, new boolean[] { false}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.name("addnewexpense"));

		if (checkNoErrorPage()) {
		    type(By.name("checkamt"),"151");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Loading Create Expense Payout Page !!!!!!!!!");
		}

		if (checkNoErrorPage()) {

			verifyTrue(isEditable(By.name("draft")));
			verifyTrue(isEditable(By.name("dispDraftpaiddate")));
			verifyTrue(isEditable(By.name("checkamt")));
			verifyTrue(isEditable(By.name("voucheramt")));
			verifyTrue(isEditable(By.name("newComment")));
			verifyTrue(isEditable(By.name("lastname")));
			verifyTrue(isEditable(By.name("firstname")));
			verifyTrue(isEditable(By.name("middlename")));
			verifyFalse(isTextPresent("Unable to Create Expense Payout. Draft Amount is over the BSO Limit: 150"));
		    type(By.name("voucheramt"),"150");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Saving Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isEditable(By.name("voucheramt")));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Updating Expense Payout  !!!!!!!!!");
		}
	}
	
}