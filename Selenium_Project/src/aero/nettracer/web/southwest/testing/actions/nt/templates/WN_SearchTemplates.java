package aero.nettracer.web.southwest.testing.actions.nt.templates;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;

public class WN_SearchTemplates extends WN_SeleniumTest {

	@Test
	public void testTemplateSearchResults() {
		verifyTrue(setPermissions(new String[] { PermissionsUtil.DOCUMENT_TEMPLATES_MANAGE }, new boolean[] { true }));
		for (int i = 0; i < 2; ++i) {
			clickMenu(MENU_ADMIN_TEMPLATES);
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("id=createButton");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					checkCopyrightAndQuestionMarks();
					verifyEquals("Create Document Template", selenium.getText("//table[@id='pageheaderright']/tbody/tr/td/h1"));
					selenium.type("id=name", "Test Template");
					selenium.click("name=active");
					selenium.type("id=description", "Test create template");
					selenium.click("id=saveButton");
					waitForPageToLoadImproved();
					if (checkNoErrorPage()) {
						verifyEquals("Successfully created template: Test Template", selenium.getText("//div[@id='maincontent']/span/font"));
						verifyEquals("Edit Document Template", selenium.getText("//table[@id='pageheaderright']/tbody/tr/td/h1"));
						verifyEquals("Test Template", selenium.getValue("id=name"));
						verifyEquals("on", selenium.getValue("name=active"));
						verifyEquals("Test create template", selenium.getValue("id=description"));
						String templateId = selenium.getText("//input[@id='id']");
						System.out.println("WN: Created template " + i + " with id: " + templateId);
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

		clickMenu(MENU_ADMIN_TEMPLATES);
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("id=searchButton");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("Results", selenium.getText("//div[@id='maincontent']/h1[2]"));
				verifyTrue(isElementPresent(By.linkText("Template Id")));
				verifyTrue(isElementPresent(By.linkText("Template Name")));
				verifyEquals("Template Description", selenium.getText("//table[@id='templates']/thead/tr/th[3]"));
				verifyTrue(isElementPresent(By.linkText("Template Created Date/Time")));
				verifyTrue(isElementPresent(By.linkText("Active")));
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to load the template search page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the template search page. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
	}
	
}
