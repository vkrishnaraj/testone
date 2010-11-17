package aero.nettracer.web.defaul.testing.actions;

import org.junit.Test;

import aero.nettracer.web.defaul.testing.DefaultSeleneseTestCase;

public class LastNameRequired extends DefaultSeleneseTestCase {
	
	@Test
	public void testLast_Name_Required() throws Exception {
		System.out.println("Testing Lastname Required");
		String lastName = selenium.getValue("passenger[0].lastname");
		System.out.println("Lastname = " + lastName);
		selenium.type("passenger[0].lastname", "");
		selenium.click("saveButton");
		assertEquals("Last Name is required.", selenium.getAlert());
		selenium.type("passenger[0].lastname", lastName);
	}
}
