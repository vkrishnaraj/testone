package aero.nettracer.web.southwest.testing.actions.nt.incidents.communications;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CustomerCommunications extends WN_SeleniumTest {
	
	private String CUST_COMM_CREATE = "667";
	private String CUST_COMM_EDIT = "668";
	private String CUST_COMM_DELETE = "669";
	private String CUST_COMM_VIEW_PUBLISHED = "670";
	private String CUST_COMM_APPROVAL = "671";
	
	private String[] searchLinks = { "menucol_1.4", "menucol_2.3", "menucol_3.3" };
	private String[] newLinks = { "menucol_1.1", "menucol_2.1", "menucol_3.1" };
	private String[] types = { "Lost/Delayed", "Damaged", "Missing Articles" };
	
	@Test
	public void testCustomerCommunicationsCreateDisabled() {
		String[] permissions = new String[] {
				  CUST_COMM_CREATE, 
				  CUST_COMM_EDIT, 
				  CUST_COMM_DELETE,
				  CUST_COMM_VIEW_PUBLISHED,
				  CUST_COMM_APPROVAL
			    };

		boolean[] values = new boolean[] { 
						false, 
						false,
						false,
						false,
						false
					 };

		verifyTrue(setPermissions(permissions, values));
		
		for (int i = 0; i < searchLinks.length; ++i) {
			clickMenu(searchLinks[i]);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("id=button");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						verifyFalse(isTextPresent("Customer Communications & Activity"));
					} else {
						System.out.println("!!!!!!!!!!!!!!!! Failed to load the first " + types[i] + " incident.");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to search for " + types[i] + " incidents.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load " + types[i] + " incident page.");
				verifyTrue(false);
			}
		}
		
		goToTaskManager();
	}
	
	@Test
	public void testCustomerCommunicationsCreateEnabledNewIncidents() {
		String[] permissions = new String[] { CUST_COMM_CREATE };
		boolean[] values = new boolean[] { true };
		verifyTrue(setPermissions(permissions, values));
		
		for (int i = 0; i < newLinks.length; ++i) {
			clickMenu(newLinks[i]);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("name=skip_prepopulate");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyFalse(isTextPresent("Customer Communications & Activity"));
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to load " + types[i] + " incident page.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to navigate to " + types[i] + " incident create page.");
				verifyTrue(false);
			}
		}
		
		goToTaskManager();
	}
	
	@Test
	public void testCustomerCommunicationsCreateEnabled() {
		for (int i = 0; i < searchLinks.length; ++i) {
			clickMenu(searchLinks[i]);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("id=button");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						verifyTrue(isTextPresent("Customer Communications & Activity"));
					} else {
						System.out.println("!!!!!!!!!!!!!!!! Failed to load the first " + types[i] + " incident.");
						verifyTrue(false);
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!! Failed to search for " + types[i] + " incidents.");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load " + types[i] + " incident page.");
				verifyTrue(false);
			}
		}
		
		goToTaskManager();
	}
	
	@Test
	public void testSetCustomerPreference() {
		String[] permissions = new String[] { CUST_COMM_CREATE, "635" };
		boolean[] values = new boolean[] { true, true };
		verifyTrue(setPermissions(permissions, values));
		
		createIncident(true);
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isElementPresent(By.name("custCommId")));
		selenium.select("name=custCommId", "label=Web Portal");
		selenium.click("id=setCommMethodButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Lost/Delayed Bag Incident has been modified."));
			selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyEquals("1302", selenium.getValue("name=custCommId"));
			} else {
				System.out.println("!!!!!!!!!!!!!!!! Failed to load the incident in testSetCustomerPreference().");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to save the incident in testSetCustomerPreference().");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testCreateCustomerCommunication() {
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.click("id=addCommButton");
		assertEquals("Customer Communications Type is required.", selenium.getAlert());
		selenium.select("id=activityIdSelect", "label=CREATE LETTER");
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved(500,false);
		
		List<WebElement> buttons = driver.findElements(By.className("ui-state-default"));

		buttons.get(1).click();
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved(500,false);
		
		buttons.get(0).click();
		assertEquals("You must select a document to send to the passenger.", selenium.getAlert());
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved(500,false);
		
		selenium.select("id=templateSelect", "label=Test Template");
		buttons.get(0).click();
		waitForPageToLoadImproved();
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
				Settings.CUST_COMM_ID = incidentActivityId;
				System.out.println("WN: Customer Communications Created: " + Settings.CUST_COMM_ID);
				selenium.click("id=savePreviewButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyTrue(isElementPresent(By.linkText("Preview")));
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
					System.out.println("!!!!!!!!!!!!!!!! Failed to save & preview the customer communications page.");
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
		
	}
	
	@Test
	public void testEditCustomerCommunication() {
		verifyTrue(setPermissions(new String[] { CUST_COMM_EDIT }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId=" + Settings.CUST_COMM_ID + "')]")));
		verifyFalse(isElementPresent(By.xpath("//a[@onclick=\"openPreviewWindow('" + Settings.CUST_COMM_ID + "')\"]")));
		
		verifyTrue(setPermissions(new String[] { CUST_COMM_EDIT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId=" + Settings.CUST_COMM_ID + "')]")));
		verifyTrue(isElementPresent(By.xpath("//a[contains(text(),'View Sample Printout')]")));
		
		selenium.click("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId=" + Settings.CUST_COMM_ID + "')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyEquals(Settings.CUST_COMM_ID, selenium.getValue("id=id"));
		}  else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to load the customer communications page for Customer Communication with id: " + Settings.CUST_COMM_ID);
			verifyTrue(false);
		}
	}
	
	@Test
	public void testDeleteCustomerCommunication() {
		verifyTrue(setPermissions(new String[] { CUST_COMM_DELETE }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyFalse(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "')]")));
		
		verifyTrue(setPermissions(new String[] { CUST_COMM_DELETE }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "')]")));
		
		selenium.click("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "&incident=" + Settings.INCIDENT_ID_WN + "')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyFalse(isElementPresent(By.xpath("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "&incident=" + Settings.INCIDENT_ID_WN + "')]")));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to delete Customer Communication with id: " + Settings.CUST_COMM_ID);
			verifyTrue(false);
		}
	}
	
	@Test
	public void testAddAssignedToActivity() {
		verifyTrue(setPermissions(new String[] { CUST_COMM_DELETE }, new boolean[] { false }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		selenium.select("id=activityIdSelect", "label=ASSIGNED TO");
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyEquals("ASSIGNED TO: ntadmin", selenium.getText("//div[@id='maincontent']/table[9]/tbody/tr[2]/td[3]"));
			verifyFalse(isElementPresent(By.xpath("//a[contains(text(),'Delete')]")));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to create Incident Activity");
			verifyTrue(false);
		}

		verifyTrue(setPermissions(new String[] { CUST_COMM_DELETE }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyEquals("ASSIGNED TO: ntadmin", selenium.getText("//div[@id='maincontent']/table[9]/tbody/tr[2]/td[3]"));
		verifyTrue(isElementPresent(By.xpath("//a[contains(text(),'Delete')]")));
		selenium.click("//a[contains(text(),'Delete')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyFalse(isTextPresent("ASSIGNED TO: ntadmin"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to delete Incident Activity");
			verifyTrue(false);
		}
		
		goToTaskManager();
	}

}
