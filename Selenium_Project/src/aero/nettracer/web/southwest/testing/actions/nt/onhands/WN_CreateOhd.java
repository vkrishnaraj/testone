package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_CreateOhd extends WN_SeleniumTest {

	@Test
	public void testModifiedByInfoPresent() {
		selenium.click("//a[@id='menucol_4.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Date/Time Modified"));
			verifyTrue(selenium.isTextPresent("Modified Agent"));
			verifyTrue(selenium.isElementPresent("name=dispModifiedDate"));
			verifyTrue(selenium.isElementPresent("name=dispModifiedDate"));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testPosIdPermission() {
		verifyTrue(navigateToPermissionsPage());
		verifyTrue(selenium.isElementPresent("name=635"));
		selenium.uncheck("name=635");
		verifyTrue(savePermissions());
		verifyTrue(logoutOfNt());
		verifyTrue(loginToNt());
		
		selenium.click("//a[@id='menucol_4.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyFalse(selenium.isTextPresent("Position ID"));
			verifyFalse(selenium.isTextPresent("Late Check"));
			verifyFalse(selenium.isElementPresent("name=posId"));
			verifyFalse(selenium.isElementPresent("name=lateCheckValue"));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		verifyTrue(navigateToPermissionsPage());
		verifyTrue(selenium.isElementPresent("name=635"));
		selenium.check("name=635");
		verifyTrue(savePermissions());
		verifyTrue(logoutOfNt());
		verifyTrue(loginToNt());
		
		selenium.click("//a[@id='menucol_4.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(selenium.isTextPresent("Position ID"));
			verifyTrue(selenium.isTextPresent("Late Check"));
			verifyTrue(selenium.isElementPresent("name=posId"));
			verifyTrue(selenium.isElementPresent("name=lateCheckValue"));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testCreateOnhand() {
		selenium.click("//a[@id='menucol_4.1']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("name=posId", "123456");
			selenium.click("name=lateCheckValue");
			selenium.select("name=bagColor", "label=BK - Black");
			selenium.select("name=bagType", "label=22");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(selenium.isElementPresent("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a"));
				String ohdId = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.ONHAND_ID_WN = ohdId;
				System.out.println("WN: Onhand Created: " + Settings.ONHAND_ID_WN);
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to save Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyEquals(Settings.ONHAND_ID_WN, selenium.getValue("name=ohd_id"));
			verifyEquals("123456", selenium.getValue("name=posId"));
			verifyEquals("on", selenium.getValue("name=lateCheckValue"));
			selenium.click("name=lateCheckValue");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("off", selenium.getValue("name=lateCheckValue"));
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to reload Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to save Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the new Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testPosIdOnAuditTrail() {
		verifyTrue(navigateToAuditTrail());
		verifyTrue(selenium.isTextPresent("Position ID"));
		assertEquals("123456", selenium.getText("//div[@id='maincontent']/table/tbody/tr[5]/td[2]"));
		verifyTrue(selenium.isTextPresent("Late Check"));
		assertEquals("Yes", selenium.getText("//div[@id='maincontent']/table/tbody/tr[6]/td[2]"));
		goToTaskManager();
	}
	
	@Test
	public void testPoisIdNotOnAuditTrail() {
		verifyTrue(navigateToPermissionsPage());
		verifyTrue(selenium.isElementPresent("name=635"));
		selenium.uncheck("name=635");
		verifyTrue(savePermissions());
		verifyTrue(logoutOfNt());
		verifyTrue(loginToNt());
		verifyTrue(navigateToAuditTrail());
		verifyFalse(selenium.isTextPresent("Position ID"));
		verifyFalse(selenium.isTextPresent("Late Check"));
		goToTaskManager();		
	}
	
	private boolean navigateToAuditTrail() {
		boolean success = false;
		selenium.click("//a[contains(@href, 'audit.do')]");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//td[@id='navmenucell']/div/dl/dd/a/span[2]");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.type("//input[@name='ohd_ID']", Settings.ONHAND_ID_WN);
				selenium.click("//input[@id='button']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					selenium.click("//a[contains(@href, 'audit_ohd.do?detail=1&ohd_ID=" + Settings.ONHAND_ID_WN + "')]");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						checkCopyrightAndQuestionMarks();
						selenium.check("//input[@name='audit_id']");
						selenium.click("xpath=(//input[@id='button'])[3]");
						waitForPageToLoadImproved();
						if (checkNoErrorPage()) {
							checkCopyrightAndQuestionMarks();
							success = true;
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Onhand: " + Settings.ONHAND_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail for Incident: " + Settings.ONHAND_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident: " + Settings.ONHAND_ID_WN + ". Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to Load Incident Tab. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Load Audit Trail. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
}
