1. Project Setup

The project should be mainly good to go on your box you will just need to add one library to your projects classpath.

1. Right click your project in Navigator and go to Properties.
2. Go to Java Build Path
3. Add the jars in the lib folder to the project.


2. RC Server Info

If you want Eclipse to start and run your server for you when running your tests just change ECLIPSE_RUNS_SERVER in Settings.class to true.
For IE8 you may need to start the server with Administrator privileges outside of Eclipse.
For this just start cmd with Admin, navigate to the lib folder, and run this command "java -jar selenium-server.jar -port 6789"


3. Creating a template for TestSuites

Go to Window, Preferences, and type in Template
You will see a lot of choices but you want the one under Java, most likely Java, Editor, Templates
Click New
Put in a name and description (This is what you will see in the autoComplete dialog)
Under pattern paste the following:
import junit.framework.Test;
import junit.framework.TestSuite;

${line_selection} extends SeleniumTestSuite {

	public static Test suite() {

		TestSuite suite = prepareSuite();

		// Add Tests to run in the order you want to run them here.
		suite.addTestSuite(Login.class);
		${cursor}
		
		return wrapSuite(suite);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
**************************************
Now when you create a new TestSuite just highlight the line with public class ClassName and press Ctrl+Space
select your template name and everything is done for you.


4. Exporting Testcases from selenium IDE

First save or rename your testcase so that the class name will be understandable.
Then choose export and Java (JUnit)
navigate to whatever package you want it to be in and type in the class name including the .java suffix
Open the new class in Eclipse
Delete the setUp() method and replace SeleneseTestCase with DefaultSeleneseTestCase
Ctrl+Shift+O and your all set.


5. Running Test Suites

Right click the suite, Run As, JUnit Test