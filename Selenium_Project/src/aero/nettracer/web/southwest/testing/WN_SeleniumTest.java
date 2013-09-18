package aero.nettracer.web.southwest.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WN_SeleniumTest extends DefaultSeleneseTestCase {
	
	protected static final String DRIVERS_LICENSE = "12345";
	protected static final String PASSPORT_NUMBER = "1234567890";
	protected static final String EXPEDITE_TAG_NUM = "012345678912";
	protected static String TODAY;
	
	// set up the current date for use throughout the tests
	static {
		TODAY = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
	}
	
	protected static final String INCIDENT_TYPE_LOSTDELAY = "Lost/Delayed";
	protected static final String INCIDENT_TYPE_MISSING = "Missing Articles";
	protected static final String INCIDENT_TYPE_DAMAGED = "Damaged";
	
	protected static final String COURTESY_REASON_COLLECT = "648";
	protected final static String MAINTAIN_TEMPLATES_PERMISSION = "651";
	
	protected boolean navigateToPermissionsPage() {
		boolean success = false;
		selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
		waitForPageToLoadImproved();
		
		selenium.select("//select[@name='companyCode']", "label=Owens Group");
		selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[4]/td[2]/input", Settings.USERNAME_OGADMIN);
		selenium.type("//div[@id='mainlogin']/form/table/tbody/tr[5]/td[2]/input", Settings.PASSWORD_OGADMIN);
		selenium.click("//input[@id='button']");
		waitForPageToLoadImproved();		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//a[contains(text(),'Maintain Companies')]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				selenium.type("//input[@name='companySearchName']", "wn");
				selenium.click("//input[@id='button']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					selenium.click("xpath=(//a[contains(text(),'Maintain')])[14]");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						selenium.click("xpath=(//a[contains(text(),'Maintain')])[12]");
						waitForPageToLoadImproved();
						if (checkNoErrorPage()) {
							success = selenium.isTextPresent("Maintain Group Permissions");
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Groups. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Searching For Southwest. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Maintain Companies Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}			
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Log In. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	protected boolean navigateToIncidentAuditTrail() {
		boolean success = false;
		selenium.click("//a[contains(@href, 'audit.do')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//td[@id='navmenucell']/div/dl/dd[3]/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("//input[@name='incident_ID']", Settings.INCIDENT_ID_WN);
				selenium.click("//input[@id='button']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					selenium.click("//a[contains(@href, 'audit_mbr.do?detail=1&incident_ID=" + Settings.INCIDENT_ID_WN + "')]");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						int highestAuditItemIndex = 0;
						for (int i = 1;;++i) {
							if (selenium.isElementPresent("xpath=(//input[@name='audit_id'])[" + i + "]")) {
								continue;
							} else {
								highestAuditItemIndex = i - 1;
								break;
							}
						}
						selenium.check(highestAuditItemIndex == 0 ? "xpath=(//input[@name='audit_id'])" : "xpath=(//input[@name='audit_id'])[" + highestAuditItemIndex + "]");
						selenium.click("xpath=(//input[@id='button'])[3]");
						waitForPageToLoadImproved();
						if (checkNoErrorPage()) {
							checkCopyrightAndQuestionMarks();
							success = true;
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Tab. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	protected boolean savePermissions() {
		boolean success = false;
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			success = true;
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		
		return success;
	}
	
	protected boolean navigateToIncident(String incidentType) {
		boolean success = false;
		selenium.click("//a[contains(@href, 'searchIncident.do?ld=1')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=incident_ID", Settings.INCIDENT_ID_WN);
			selenium.select("id=itemType_ID", "label=" + incidentType);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				success = true;
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Search Page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	protected boolean navigateToOnhand() {
		boolean success = false;
		selenium.click("//a[contains(@href, 'searchOnHand.do')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.type("name=incident_ID", Settings.ONHAND_ID_WN);
			selenium.click("id=button");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {

				selenium.click("link="+Settings.ONHAND_ID_WN);
				waitForPageToLoadImproved();

				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					success = true;
					
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD" + Settings.INCIDENT_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD Results. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Search Page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	protected boolean logoutOfNt() {
		boolean success = true;
		selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
		waitForPageToLoadImproved();
		checkCopyrightAndQuestionMarks();
		return success;
	}
	
	protected boolean loginToNt() {
		boolean success = false;
		selenium.type("document.logonForm.elements[1]", Settings.USERNAME_ADMIN);
		selenium.type("document.logonForm.elements[2]", Settings.PASSWORD_ADMIN);
		selenium.click("document.logonForm.elements[3]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(selenium.isTextPresent("Task Manager Home"));
			success = true;
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Log In. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	protected boolean setPermissions(String[] permissions, boolean[] values) {
		if (permissions == null || values == null || permissions.length != values.length) {
			throw new IllegalArgumentException("Please provide the permissions and a value to which each one should be set.");
		}
		
		boolean success = false;
		if (!navigateToPermissionsPage()) {
			return success;
		}
		
		for (int i = 0; i < permissions.length; ++i) {
			if (values[i]) {
				selenium.check("name=" + permissions[i]);
			} else {
				selenium.uncheck("name=" + permissions[i]);
			}
		}
				
		selenium.click("xpath=(//input[@id='button'])[2]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.click("//table[@id='headercontent']/tbody/tr[4]/td/a");
			waitForPageToLoadImproved();
			success = loginToNt();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Save Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
}
