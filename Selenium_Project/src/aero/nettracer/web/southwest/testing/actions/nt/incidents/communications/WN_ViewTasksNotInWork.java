package aero.nettracer.web.southwest.testing.actions.nt.incidents.communications;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_ViewTasksNotInWork extends WN_SeleniumTest {
	
	@Test
	public void testViewTasksNotInWorkDisabled() {
		verifyTrue(setPermissions(new String[] { VIEW_TASKS_NOT_IN_WORK }, new boolean[] { false }));
		verifyFalse(selenium.isElementPresent("id=menucol_10.21"));
	}
	
	@Test
	public void testViewTasksNotInWorkEnabled() {
		verifyTrue(setPermissions(new String[] { VIEW_TASKS_NOT_IN_WORK }, new boolean[] { true }));
		verifyTrue(selenium.isElementPresent("id=menucol_10.21"));
	}
	
	@Test
	public void testNavigateToTasksNotInWork() {
		clickMenu("menucol_10.21");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(isElementPresent(By.id("agentName")));
			verifyTrue(isElementPresent(By.id("s_createtime")));
			verifyTrue(isElementPresent(By.id("e_createtime")));
			verifyTrue(isElementPresent(By.id("status")));
			verifyTrue(isElementPresent(By.id("taskType")));
			verifyTrue(isElementPresent(By.id("passengerLastName")));
			verifyTrue(isElementPresent(By.id("passengerFirstName")));
			verifyTrue(isElementPresent(By.id("acaa")));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to navigate to the tasks not in work page.");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testSearchForTasksNotInWork() {
		String[] permissions = { CUST_COMM_CREATE, CUST_COMM_EDIT, CUST_COMM_DELETE, CUST_COMM_APPROVAL, CUST_COMM_APPROVAL_QUEUE, VIEW_TASKS_NOT_IN_WORK, 
				"682","683","685","687","684","688" };
		boolean[] values = { true, true, true, false, false, true, 
				false, false, false, false, false, false };
		verifyTrue(setPermissions(permissions, values));
		createIncident(true);
		createCustomerCommunication();
		clickMenu("menucol_10.21");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("id=agentName", Settings.USERNAME_ADMIN);
			click(By.id("searchButton"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/h1[2]")));
				click(By.id("resetButton"));
				waitForPageToLoadImproved();
			} else {
				System.out.println("Failed to load the pending tasks page while searching by agent name.");
				verifyTrue(false);
			}
			
			selenium.type("id=passengerFirstName", "Test1");
			click(By.id("searchButton"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/h1[2]")));
				click(By.id("resetButton"));
				waitForPageToLoadImproved();
			} else {
				System.out.println("Failed to load the pending tasks page while searching by passenger first name.");
				verifyTrue(false);
			}

			selenium.select("id=status", "label=Letter Pending Approval");
			click(By.id("searchButton"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/h1[2]")));
				click(By.id("resetButton"));
				waitForPageToLoadImproved();
			} else {
				System.out.println("Failed to load the pending tasks page while searching by status.");
				verifyTrue(false);
			}
			
			selenium.click("id=calendar");
			selenium.click("link=Today");
			click(By.id("searchButton"));
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(isElementPresent(By.xpath("//div[@id='maincontent']/h1[2]")));
				click(By.id("resetButton"));
				waitForPageToLoadImproved();
			} else {
				System.out.println("Failed to load the pending tasks page while searching by create date.");
				verifyTrue(false);
			}
			
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to navigate to the tasks not in work page.");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	private void createCustomerCommunication() {
		verifyTrue(navigateToIncident(INCIDENT_TYPE_LOSTDELAY));
		selenium.select("id=activityIdSelect", "label=CREATE LETTER");
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved(500,false);
		
		List<WebElement> buttons = driver.findElements(By.className("ui-state-default"));

		selenium.select("id=templateSelect", "label=Test Template");
		buttons.get(0).click();
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("id=submitCustComm");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyEquals("Successfully created incident activity: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
				verifyNotEquals("0", selenium.getValue("id=documentId"));
				String incidentActivityId = selenium.getValue("id=id");
				Settings.CUST_COMM_ID = incidentActivityId;
				System.out.println("WN: Customer Communications Created: " + Settings.CUST_COMM_ID);
				selenium.click("css=span.bb");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyEquals("Lost/Delayed Bag Incident", selenium.getText("//div[@id='pageheaderleft']/h1"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to reload the incident from the customer communications page.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to save the customer communications page.");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the customer communications page.");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void resetPaymentAppPermissions(){
		verifyTrue(setPermissions(new String[] { "682","683","685","687","684","688","689"}, new boolean[] { true,true,true,true,true,true}));
	}
	
}
