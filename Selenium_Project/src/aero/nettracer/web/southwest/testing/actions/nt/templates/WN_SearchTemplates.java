package aero.nettracer.web.southwest.testing.actions.nt.templates;

import org.junit.Before;
import org.junit.Test;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;

public class WN_SearchTemplates extends WN_SeleniumTest {

	@Before
	public void setup() {
		verifyTrue(setPermissions(new String[] { WN_SeleniumTest.MAINTAIN_TEMPLATES_PERMISSION }, new boolean[] { true }));
		for (int i = 0; i < 2; ++i) {
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
	}
	
	@Test
	public void testTemplateSearchResults() {
		selenium.click("id=menucol_9.16");
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			selenium.click("//input[@value='Search']");
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyEquals("Results", selenium.getText("//div[@id='maincontent']/h1[2]"));
				verifyTrue(selenium.isElementPresent("link=Template Id"));
				verifyTrue(selenium.isElementPresent("link=Template Name"));
				verifyEquals("Template Description", selenium.getText("//table[@id='templates']/thead/tr/th[3]"));
				verifyTrue(selenium.isElementPresent("link=Template Created Date/Time"));
				verifyTrue(selenium.isElementPresent("link=Active"));
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
