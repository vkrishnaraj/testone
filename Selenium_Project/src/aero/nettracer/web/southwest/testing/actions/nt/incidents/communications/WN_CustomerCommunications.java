package aero.nettracer.web.southwest.testing.actions.nt.incidents.communications;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CustomerCommunications extends WN_SeleniumTest {
	
	private String CUST_COMM_CREATE = "667";
	private String CUST_COMM_EDIT = "668";
	private String CUST_COMM_DELETE = "669";
	private String CUST_COMM_VIEW_PUBLISHED = "670";
	private String CUST_COMM_APPROVAL = "671";
	
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
		
		String[] links = { "searchIncident.do?ld=1", "searchIncident.do?damage=1", "searchIncident.do?missing=1" };
		
		for (int i = 0; i < links.length; ++i) {
			selenium.click("//a[contains(@href, '" + links[i] + "')]");
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
						verifyFalse(selenium.isTextPresent("Customer Communications & Activity"));
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
		
		String[] links = { "lostDelay.do", "damaged.do", "missing.do" };
		
		for (int i = 0; i < links.length; ++i) {
			selenium.click("//a[contains(@href, '" + links[i] + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("name=skip_prepopulate");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyFalse(selenium.isTextPresent("Customer Communications & Activity"));
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
		String[] links = { "searchIncident.do?ld=1", "searchIncident.do?damage=1", "searchIncident.do?missing=1" };
		
		for (int i = 0; i < links.length; ++i) {
			selenium.click("//a[contains(@href, '" + links[i] + "')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.click("id=button");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("//div[@id='maincontent']/table[2]/tbody/tr[2]/td/a");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						verifyTrue(selenium.isTextPresent("Customer Communications & Activity"));
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
		verifyTrue(selenium.isElementPresent("name=custCommId"));
		selenium.select("name=custCommId", "label=Web Portal");
		selenium.click("id=setCommMethodButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been modified."));
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
		selenium.select("id=templateIdSelect", "label=Test Template");
		selenium.click("id=addCommButton");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Create Outgoing Communication"));
			verifyTrue(selenium.isElementPresent("id=documentTitle"));
			verifyEquals("Test Template", selenium.getValue("id=documentTitle"));
			verifyTrue(selenium.isElementPresent("id=documentId"));
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
					verifyTrue(selenium.isElementPresent("link=Preview"));
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
		verifyFalse(selenium.isElementPresent("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId=" + Settings.CUST_COMM_ID + "')]"));
		verifyFalse(selenium.isElementPresent("//a[@onclick=\"openPreviewWindow('" + Settings.CUST_COMM_ID + "')\"]"));
		
		verifyTrue(setPermissions(new String[] { CUST_COMM_EDIT }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isElementPresent("//a[contains(@href, 'customerCommunications.do?command=edit&communicationsId=" + Settings.CUST_COMM_ID + "')]"));
		verifyTrue(selenium.isElementPresent("//a[contains(text(),'View Sample Printout')]"));
		
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
		verifyFalse(selenium.isElementPresent("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "')]"));
		
		verifyTrue(setPermissions(new String[] { CUST_COMM_DELETE }, new boolean[] { true }));
		verifyTrue(navigateToIncident(WN_SeleniumTest.INCIDENT_TYPE_LOSTDELAY));
		verifyTrue(selenium.isElementPresent("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "')]"));
		
		selenium.chooseCancelOnNextConfirmation();
		selenium.click("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "&incident=" + Settings.INCIDENT_ID_WN + "')]");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete this template[\\s\\S]$"));
		selenium.click("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "&incident=" + Settings.INCIDENT_ID_WN + "')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete this template[\\s\\S]$"));
			verifyFalse(selenium.isElementPresent("//a[contains(@href, 'customerCommunications.do?command=delete&communicationsId=" + Settings.CUST_COMM_ID + "&incident=" + Settings.INCIDENT_ID_WN + "')]"));
		} else {
			System.out.println("!!!!!!!!!!!!!!!! Failed to delete Customer Communication with id: " + Settings.CUST_COMM_ID);
			verifyTrue(false);
		}
	}

}