package aero.nettracer.web.southwest.testing.actions.nt.templates;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.Settings;

public class WN_EditTemplates extends WN_SeleniumTest {
	
	@Test
	public void testVerifyMaintainTemplatesPermissionDisabled() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.MAINTAIN_TEMPLATES_PERMISSION }, new boolean[] { false }));
		verifyFalse(selenium.isElementPresent("//a[contains(@href, 'searchTemplate.do')]"));
		goToTaskManager();
	}
	
	@Test
	public void testVarifyMaintainTemplatesPermissionEnabled() {
		verifyTrue(super.setPermissions(new String[] { WN_SeleniumTest.MAINTAIN_TEMPLATES_PERMISSION }, new boolean[] { true }));
		verifyTrue(selenium.isElementPresent("//a[contains(@href, 'searchTemplate.do')]"));
		goToTaskManager();
	}
	
	@Test
	public void testCreateTemplate() {
		selenium.click("//a[@id='menucol_9.16']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//input[@value='Create']");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("Create Document Template", selenium.getText("//table[@id='pageheaderright']/tbody/tr/td/h1"));
				selenium.type("id=name", "Test Template");
				selenium.click("name=active");
				selenium.type("id=description", "Test create template");
				selenium.click("//input[@value='Save']");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("Successfully created template: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
					verifyEquals("Edit Document Template", selenium.getText("//table[@id='pageheaderright']/tbody/tr/td/h1"));
					verifyEquals("Test Template", selenium.getValue("id=name"));
					verifyEquals("on", selenium.getValue("name=active"));
					verifyEquals("Test create template", selenium.getValue("id=description"));
					String templateId = selenium.getText("//input[@id='id']");
					Settings.TEMPLATE_ID_WN = templateId;
					System.out.println("WN: Created template with id: " + Settings.TEMPLATE_ID_WN);
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to create a new template. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the create template page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the template search page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testSaveAndPreview() {
		selenium.select("id=variableSelect", "label=Option1");
		selenium.doubleClick("id=variableSelect");
		selenium.select("id=variableSelect", "label=Today");
		selenium.doubleClick("id=variableSelect");
		selenium.select("id=variableSelect", "label=FirstName");
		selenium.doubleClick("id=variableSelect");
		selenium.select("id=variableSelect", "label=Initials");
		selenium.doubleClick("id=variableSelect");
		selenium.select("id=variableSelect", "label=LastName");
		selenium.doubleClick("id=variableSelect");
		selenium.click("//input[@value='Save']");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyEquals("Successfully updated template: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
			selenium.select("id=variableSelect", "label=Address1");
			selenium.doubleClick("id=variableSelect");
			selenium.click("//input[@value='Save & Preview']");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("Successfully updated template: Test Template\nUnable to generate document: The template contains unresolved variable dependencies.", selenium.getText("//div[@id='maincontent']/span/font"));
				selenium.select("id=variableSelect", "label=Id");
				selenium.doubleClick("id=variableSelect");
				selenium.click("//input[@value='Save & Preview']");
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyEquals("Successfully updated template: Test Template\nDocument successfully generated", selenium.getText("//div[@id='maincontent']/span/font"));
					verifyTrue(selenium.isElementPresent("link=Preview Document"));
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to save & preview on the edit template page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to save & preview on the edit template page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to save & preview on the edit template page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
	@Test
	public void testDelete() {
		selenium.chooseCancelOnNextConfirmation();
		selenium.click("//input[@value='Delete']");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete this template[\\s\\S]$"));
		selenium.click("//input[@value='Delete']");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete this template[\\s\\S]$"));
		verifyEquals("Successfully deleted template: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
		verifyEquals("Criteria", selenium.getText("//div[@id='maincontent']/h1"));
	}
	
}
