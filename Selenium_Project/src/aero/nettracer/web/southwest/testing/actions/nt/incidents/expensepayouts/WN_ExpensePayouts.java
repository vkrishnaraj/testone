package aero.nettracer.web.southwest.testing.actions.nt.incidents.expensepayouts;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_ExpensePayouts extends WN_SeleniumTest {


	@Test
	public void testSetBSOLimit(){
		verifyTrue(setPermissions(new String[] { "681","682", "684","685","687","688","671"}, new boolean[] { true,false,false,false,false,false,false}));
		clickMenu("menucol_10.3");

		if (checkNoErrorPage()) {
			click(By.xpath("(//a[contains(text(),'Admin')])[4]"));
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
		verifyTrue(setPermissions(new String[] { "680", "681"}, new boolean[] { true,false}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.name("addnewexpense"));

		if (checkNoErrorPage()) {
		    type(By.name("checkamt"),"151");
			type(By.name("newComment"),"test");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Loading Create Expense Payout Page !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Unable to Create Expense Payout. Draft Amount is over the BSO Limit: 150"));
		    type(By.name("checkamt"),"150");
			type(By.name("newComment"),"test");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Attempting to save Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {

			verifyTrue(isEditable(By.name("draft")));
			verifyTrue(isEditable(By.name("dispDraftpaiddate")));
			verifyTrue(isEditable(By.name("checkamt")));
			verifyTrue(isEditable(By.name("newComment")));
		    type(By.name("checkamt"),"151");
			type(By.name("newComment"),"test");
		    click(By.id("button"));
		    assertEquals("Unable to Create Expense Payout. Draft Amount is over the BSO Limit: 150",getAlert());
		    type(By.name("checkamt"),"150");
			type(By.name("newComment"),"test");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Saving Expense Payout  !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			waitForPageToLoadImproved(3000);
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
			verifyFalse(isEditable(By.name("newComment")));
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
	}

	@Test
	public void testCreateAdminBSOExpensePayouts(){
		verifyTrue(setPermissions(new String[] { "681"}, new boolean[] { true}));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.name("addnewexpense"));

		if (checkNoErrorPage()) {
		    type(By.name("checkamt"),"151");
    		type(By.name("newComment"),"test");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Loading Create Expense Payout Page !!!!!!!!!");
		}

		if (checkNoErrorPage()) {

			verifyTrue(isEditable(By.name("draft")));
			verifyTrue(isEditable(By.name("dispDraftpaiddate")));
			verifyTrue(isEditable(By.name("checkamt")));
			verifyTrue(isEditable(By.name("newComment")));
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
	
	//Test Payment Approval Process
	@Test
	public void testSetFraudOnReview(){
		clickMenu(MENU_ADMIN_COMPANY);
		
		if (checkNoErrorPage()) {
			click(By.xpath("//td[@id='navmenucell']/div/dl/dd[2]/a/span[2]"), false, true);
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Fraud Review Message 1");
		}

		if (checkNoErrorPage()) {
			selenium.select("name=fraudReview","label=yes");
			click(By.name("save"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Fraud Review Message 2");
		}
	}
	
	@Test
	public void testCreateFraudReview(){

		verifyTrue(setPermissions(new String[] { "680","681","687","684","688","671"}, new boolean[] { false,false,true,false,false,false}));
		
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		click(By.name("addnewexpense"));

		if (checkNoErrorPage()) {
		    type(By.name("checkamt"),"151");
		    type(By.name("newComment"),"test");
		    click(By.id("button"));
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Loading Create Expense Payout Page !!!!!!!!!");
		}

		if (checkNoErrorPage()) {
			click(By.id("createClaimSettlement"));
			waitForPageToLoadImproved(1000,false);

			List<WebElement> buttons = driver.findElements(By.className("ui-state-default"));
			selenium.select("id=claimSettleSelect", "label=Test Template");
			buttons.get(0).click();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Saving Expense Payout !!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {

			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Create Outgoing Communication"));
			verifyTrue(isElementPresent(By.id("documentTitle")));
			verifyEquals("Test Template", selenium.getValue("id=documentTitle"));
			verifyTrue(isElementPresent(By.id("documentId")));
			verifyEquals("0", selenium.getValue("id=documentId"));
			selenium.click("id=submitCustComm");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyEquals("Successfully created incident activity: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
				verifyNotEquals("0", selenium.getValue("id=documentId"));
				String incidentActivityId = selenium.getValue("id=id");
				Settings.PAYMENT_COMM_ID = incidentActivityId;
				System.out.println("WN: Payment Communications Created: " + Settings.PAYMENT_COMM_ID);
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to save the customer communications page.");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Creating Claim Settlement Letter !!!!!!!!!");
		}
	}
	
	@Test
	public void testRejectFraudReview(){

		verifyTrue(setPermissions(new String[] {"682", "684","685","687","688","671"}, new boolean[] {false,true,false,true,false,false}));
		goToTaskManager();
		click(By.id("684link"));
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1&gettask=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1&gettask=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Task manager page !!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			goToTaskManager();
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?gettask=1&communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?gettask=1&communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1')]");
			if (checkNoErrorPage()) {
				type(By.id("taskRemark"), "test");
				click(By.id("rejectButton"));
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Fraud Review", selenium.getText("//div[@id='maincontent']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to reject the fraud review task.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review  task.");
				verifyTrue(false);
			}
		}
	}

	@Test
	public void testResubmitFraudReview(){

		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,false,false,true,false,false}));
		
		if(checkNoErrorPage()){
			goToTaskManager();

			click(By.xpath("(//a[contains(@href, 'rejectedDisbursements.do')])[2]"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				click(By.xpath("//table[@id='rejectedDisbursements']/tbody/tr/td/a"));
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					click(By.id("submitCustComm"));
					waitForPageToLoadImproved();
					if (!checkNoErrorPage()) {
						System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review rejected task.");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review rejected task.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review rejected page.");
				verifyTrue(false);
			}
		}
		
	}
	
	@Test
	public void testApproveFraudReviewTask() {
		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,true,false,true,false,false}));
		
		goToTaskManager();
		click(By.id("684link"));
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1&gettask=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&fraudReview=1&gettask=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Task manager page !!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			click(By.id("approveButton"));
			waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Fraud Review", selenium.getText("//div[@id='maincontent']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to approve the fraud review task.");
					verifyTrue(false);
				}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review task.");
			verifyTrue(false);
		}
		
		goToTaskManager();
	}
	

	@Test
	public void testRejectSupervisorReview(){
		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,true,true,true,false,false}));
		
		goToTaskManager();
		click(By.id("685link"));
		
		if (checkNoErrorPage()) {

			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Task manager page !!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			goToTaskManager();
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?gettask=1&communicationsId="+Settings.PAYMENT_COMM_ID+"&supervisorReview=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?gettask=1&communicationsId="+Settings.PAYMENT_COMM_ID+"&supervisorReview=1')]");
			if (checkNoErrorPage()) {
				type(By.id("taskRemark"), "test");
				click(By.id("rejectButton"));
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Supervisor Review", selenium.getText("//div[@id='maincontent']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to reject the fraud review task.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the fraud review  task.");
				verifyTrue(false);
			}
		}
	}
	
	@Test
	public void testSetFraudOffReview(){
		clickMenu(MENU_ADMIN_COMPANY);
		
		if (checkNoErrorPage()) {
			click(By.xpath("//td[@id='navmenucell']/div/dl/dd[2]/a/span[2]"), false, true);
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Fraud Review Message 3");
		}

		if (checkNoErrorPage()) {
			selenium.select("name=fraudReview","label=no");
			click(By.name("save"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Fraud Review Message 4");
		}
	}

	@Test
	public void testResubmitSupervisorReview(){

		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,false,false,true,false,false}));
		
		if(checkNoErrorPage()){
			goToTaskManager();

			click(By.xpath("(//a[contains(@href, 'rejectedDisbursements.do')])[2]"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				click(By.xpath("//table[@id='rejectedDisbursements']/tbody/tr/td/a"));
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					click(By.id("submitCustComm"));
					waitForPageToLoadImproved();
					if (!checkNoErrorPage()) {
						System.out.println("!!!!!!!!!!!!!!!! Failed to load the supervisor review rejected task.");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to load the supervisor review rejected task.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the supervisor review rejected page.");
				verifyTrue(false);
			}
		}
	}
	
	@Test
	public void testApproveSupervisorTask() {
		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,false,true,true,false,false}));
		
		goToTaskManager();
		click(By.id("685link"));
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Task manager page !!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			click(By.id("approveButton"));
			waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Supervisor Review", selenium.getText("//div[@id='maincontent']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to approve the supervisor review task.");
					verifyTrue(false);
				}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the supervisor review task.");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testRejectPaymentApproval(){

		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {true,true,false,false,true,false,false}));
		
		goToTaskManager();
		click(By.id("682link"));
		
		if (checkNoErrorPage()) {

			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			click(By.linkText("Deny"));
			type(By.id("taskRemark"),"Deny Test");

			List<WebElement> buttons = driver.findElements(By.className("ui-state-default"));
			buttons.get(0).click();
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Payment Approval list page !!!!!!!!!");
		}
		
		if(checkNoErrorPage()){
			verifyEquals("No Payments to Approve at this time.", selenium.getText("//div[@id='maincontent']/div"));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Denying Payment !!!!!!!!!");
		}
	}
	

	@Test
	public void testResubmitPayment(){
		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,false,false,true,false,false}));
		
		
		if(checkNoErrorPage()){
			goToTaskManager();

			click(By.xpath("(//a[contains(@href, 'rejectedDisbursements.do')])[2]"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				click(By.xpath("//table[@id='rejectedDisbursements']/tbody/tr/td/a"));
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					click(By.id("submitCustComm"));
					waitForPageToLoadImproved();
					if (!checkNoErrorPage()) {
						System.out.println("!!!!!!!!!!!!!!!! Failed to load the payment review rejected task.");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to load the payment review rejected task.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the payment review rejected page.");
				verifyTrue(false);
			}
		}
	}

	@Test
	public void testReApproveSupervisorTask() {

		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {false,true,false,true,true,false,false}));
		
		goToTaskManager();
		click(By.id("685link"));
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]")));
			selenium.click("//a[contains(@href, 'customerCommunicationsTasks.do?communicationsId="+Settings.PAYMENT_COMM_ID+"&gettask=1&supervisorReview=1')]");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!! ERROR - Going to Task manager page !!!!!!!!!");
		}
		
		if (checkNoErrorPage()) {
			click(By.id("approveButton"));
			waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Supervisor Review", selenium.getText("//div[@id='maincontent']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to approve the supervisor review task.");
					verifyTrue(false);
				}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the supervisor review task.");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testApprovePaymentTask() {
		verifyTrue(setPermissions(new String[] {"682", "683","684","685","687","688","671"}, new boolean[] {true,true,false,true,true,false,false}));
		
		goToTaskManager();
		click(By.id("682link"));
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			type(By.name("iat[0].expensedraft"),"");
			type(By.name("iat[0].expensedraftdate"),"");
			type(By.name("iat[0].expensemaildate"),"");
			click(By.name("updateExpense"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the Payment Approval tasks.");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			type(By.name("iat[0].expensedraft"),"TEST123");
			click(By.name("updateExpense"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to attempt submit Payment Approval with no info.");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			type(By.name("iat[0].expensedraft"),"TEST123");
			click(By.id("expensedraftdatecalendar0"));
			click(By.linkText("Today"));
			

			type(By.name("iat[0].expensedraftdate"),"");
			type(By.name("iat[0].expensemaildate"),"");
			click(By.name("updateExpense"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to attempt submit Payment Approval with no info.");
		}

		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			type(By.name("iat[0].expensedraft"),"TEST123");
			click(By.id("expensemaildatecalendar0"));
			click(By.linkText("Today"));
			
			
			click(By.name("updateExpense"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to attempt submit Payment Approval with no info.");
		}
		
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			type(By.name("iat[0].expensedraft"),"TEST123");
			click(By.id("expensedraftdatecalendar0"));
			click(By.linkText("Today"));
			click(By.id("expensemaildatecalendar0"));
			click(By.linkText("Today"));
			
			
			click(By.name("updateExpense"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to attempt submit Payment Approval with no info.");
		}
		
		if (checkNoErrorPage()) {
			verifyFalse(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId="+Settings.PAYMENT_COMM_ID+"')]")));
			goToTaskManager();
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to attempt submit Payment Approval with no info.");
		}
	}
	
	@Test
	public void resetPaymentAppPermissions(){
		verifyTrue(setPermissions(new String[] { "682","683","685","687","684","688"}, new boolean[] { true,true,true,true,true,true}));
	}
	
	@Test
	public void resetBSOPermissions(){
		verifyTrue(setPermissions(new String[] { "680","681"}, new boolean[] { true,true}));
	}
}