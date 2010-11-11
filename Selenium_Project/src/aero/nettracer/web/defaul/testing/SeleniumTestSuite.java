package aero.nettracer.web.defaul.testing;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SeleniumTestSuite {

	public static Test wrapSuite(TestSuite suite) {		
		
		TestSetup wrapper = new TestSetup(suite) {

			protected void setUp() {
				oneTimeSetUp();
			}

			private void oneTimeSetUp() {
				SeleniumTestBrowserDefault.initBrowser();
			}

			private void oneTimeTearDown() {
				SeleniumTestBrowserDefault.stopBrowser();
			}

			protected void tearDown() {
				oneTimeTearDown();
			}
		};

		return wrapper;
	}

	public static TestSuite prepareSuite() {		
		
		TestSuite suite = new TestSuite();

		return suite;
	}

}
