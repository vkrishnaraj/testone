package aero.nettracer.web.defaul.testing;

import junit.framework.Test;
import junit.framework.TestSuite;
import aero.nettracer.web.defaul.testing.actions.IntoFirstPAWOB;
import aero.nettracer.web.defaul.testing.actions.LastNameRequired;
import aero.nettracer.web.defaul.testing.actions.Login;

public class TestPAWOBRequiredFields extends SeleniumTestSuite {

	public static Test suite() {

		TestSuite suite = prepareSuite();

		// Add Tests to run in the order you want to run them here.
		suite.addTestSuite(Login.class);
		suite.addTestSuite(IntoFirstPAWOB.class);
		suite.addTestSuite(LastNameRequired.class);
		
		return wrapSuite(suite);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
