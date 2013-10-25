package aero.nettracer.web.southwest.testing.actions.nt.templates;

import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_SearchTemplates extends WN_SeleniumTest {

	@Test
	public void testTemplateSearchResults() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.MAINTAIN_TEMPLATES_PERMISSION }, new boolean[] { true }));
		for (int i = 0; i < 2; ++i) {
			clickMenu("menucol_9.17");
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

		clickMenu("menucol_9.17");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("id=searchButton");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("Results", selenium.getText("//div[@id='maincontent']/h1[2]"));
				verifyTrue(isElementPresent("link=Template Id"));
				verifyTrue(isElementPresent("link=Template Name"));
				verifyEquals("Template Description", selenium.getText("//table[@id='templates']/thead/tr/th[3]"));
				verifyTrue(isElementPresent("link=Template Created Date/Time"));
				verifyTrue(isElementPresent("link=Active"));
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
