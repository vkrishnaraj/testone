package aero.nettracer.web.southwest.testing;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.Settings;

public class WN_SeleniumTest extends DefaultSeleneseTestCase {
	
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
	
}
