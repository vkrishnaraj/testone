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
						navigateAuditPagination("audit_id");
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
	
	protected void navigateAuditPagination(String id) {
		if (id == null || id.isEmpty()) {
			return;
		}
		
		while (selenium.isElementPresent("//a[contains(text(),'Next >')]")) {
			selenium.click("//a[contains(text(),'Next >')]");
			waitForPageToLoadImproved();
		}
		
		String element = "xpath=(//input[@name='" + id + "'])";
		int highestAuditItemIndex = 0;
		for (int i = 1;;++i) {
			if (selenium.isElementPresent(element + "[" + i + "]")) {
				continue;
			} else {
				highestAuditItemIndex = i - 1;
				break;
			}
		}
		selenium.check(highestAuditItemIndex == 0 ? element : element + "[" + highestAuditItemIndex + "]");
		selenium.click("xpath=(//input[@id='button'])[3]");
		waitForPageToLoadImproved();
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
		try {
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
		} catch (Exception e) {
			System.out.println("!!!!!!!!!!!!!!! - Failed to navigate to Incident: " + Settings.INCIDENT_ID_WN + " - !!!!!!!!!!!!!!!!!!");
			e.printStackTrace();
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
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load OHD" + Settings.ONHAND_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
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
	
	protected void createIncident(boolean forTest) {
		selenium.click("//a[@id='menucol_1.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("name=skip_prepopulate");
			waitForPageToLoadImproved();
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Prepopulate Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			fillRequiredFields();
			if (forTest) {
				fillOptionalFields();
			}
			selenium.click("savetracingButton");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				verifyTrue(selenium.isTextPresent("Lost/Delayed Bag Incident has been submitted."));
				if (forTest) {
					checkCopyrightAndQuestionMarks();
					String incident_id = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
					Settings.INCIDENT_ID_WN = incident_id;
					System.out.println("WN: Lost/Delay Incident Created: " + Settings.INCIDENT_ID_WN);
				}
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Create Lost/Delay Success Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Edit Lost/Delay Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	private void fillRequiredFields() {

		selenium.check("id=addresses[0].permanent");
		selenium.click("name=addbagit");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("name=passenger[0].lastname", "Test1");
			selenium.type("name=passenger[0].firstname", "Test1");
			selenium.type("name=addresses[0].address1", "123 circle round drive");
			selenium.type("name=addresses[0].city", "atlanta");
			selenium.select("name=addresses[0].state_ID", "label=Georgia");						
			selenium.type("name=theitinerary[0].legfrom", "ATL");						
			selenium.type("name=theitinerary[0].legto", "AEX");						
			selenium.type("name=theitinerary[0].flightnum", "1234");						
			selenium.click("id=itcalendar0");
			selenium.click("link=Today");					
			selenium.type("name=theitinerary[1].legfrom", "ALS");
			selenium.type("name=theitinerary[1].legto", "ATL");
			selenium.type("name=theitinerary[1].flightnum", "3456");
			selenium.click("id=calendar31");
			selenium.click("link=Today");					
			selenium.select("name=theitem[0].color", "label=BN - Brown");						
			selenium.select("id=bagtype0", "label=25");						
			selenium.select("name=inventorylist[0].categorytype_ID", "label=Alcohol");
			selenium.type("name=inventorylist[0].description", "TEST");
			selenium.select("name=inventorylist[1].categorytype_ID", "label=Alcohol");
			selenium.select("name=inventorylist[2].categorytype_ID", "label=Alcohol");
			selenium.type("name=inventorylist[1].description", "TEST");
			selenium.type("name=inventorylist[2].description", "TEST");
			selenium.type("name=recordlocator", "TESTER");					
			selenium.type("name=passenger[0].jobtitle", "agent");			
			selenium.type("name=addresses[0].zip", "33213");					
			selenium.type("name=addresses[0].mobile", "4040213465");				
			selenium.type("name=claimcheck[0].claimchecknum", "1234567890");
			selenium.select("name=theitem[0].manufacturer_ID", "label=Andiamo");
			selenium.type("id=remark[0]", "remark noted");
		} else {
			System.out.println("!!!!!!!!!!!!!!!! failed to 'Add Bag Itinerary'.");
		}
	}
	
	private void fillOptionalFields() {
		verifyTrue(selenium.isElementPresent("name=passenger[0].decriptedDriversLicense"));
		verifyTrue(selenium.isElementPresent("name=passenger[0].dlstate"));
		verifyTrue(selenium.isElementPresent("name=passenger[0].driversLicenseProvince"));
		verifyTrue(selenium.isElementPresent("name=passenger[0].driversLicenseCountry"));
		verifyTrue(selenium.isElementPresent("name=passenger[0].decryptedPassportNumber"));
		verifyTrue(selenium.isElementPresent("name=passenger[0].passportIssuer"));
		selenium.type("name=passenger[0].decriptedDriversLicense", WN_SeleniumTest.DRIVERS_LICENSE);
		selenium.select("name=passenger[0].dlstate", "label=Georgia");
		verifyFalse(selenium.isEditable("name=passenger[0].driversLicenseProvince"));
		verifyEquals("US", selenium.getValue("name=passenger[0].driversLicenseCountry"));
		selenium.type("name=passenger[0].decryptedPassportNumber", WN_SeleniumTest.PASSPORT_NUMBER);
		selenium.select("name=passenger[0].passportIssuer", "label=United States");
		selenium.type("name=theitem[0].posId", "123456");
		selenium.type("name=addresses[0].email", "email@email.com");
	}
	
}
