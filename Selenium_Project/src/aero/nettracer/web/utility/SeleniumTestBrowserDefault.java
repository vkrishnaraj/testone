package aero.nettracer.web.utility;

//custom settings class, read in some configs for your
//tests from a properties file blah blah blah use your
//imagination
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTestBrowserDefault extends Settings {

	private static Selenium browser;
	private static SeleniumServer server;
	private static WebDriver driver;
	private static WebDriver ogDriver;

	public synchronized static void setBrowser(Selenium _browser) {
		browser = _browser;
	}

	public synchronized static Selenium getBrowser() {
		return browser;
	}

	public synchronized static void setDriver(WebDriver _driver) {
		driver = _driver;
	}

	public synchronized static WebDriver getDriver() {
		return driver;
	}

	public synchronized static void setOgDriver(WebDriver _driver) {
		ogDriver = _driver;
	}

	public synchronized static WebDriver getOgDriver() {
		return ogDriver;
	}

	public synchronized static void stopBrowser() {
		stopBrowser("DEFAULT", false);
	}

	public synchronized static void stopBrowser(String client) {
		stopBrowser(client, false);
	}

	public synchronized static void stopBrowser(String client, boolean useWD) {
		if (useWD) {
			driver.quit();
		} else {
			browser.stop();
		}
		System.out.println(client + ": Selenium Tests Complete");
	}

	public synchronized static void initBrowser() {
		initBrowser(PORT, false);
	}

	public synchronized static void initBrowser(boolean useWD) {
		initBrowser(PORT, useWD);
	}
	
	public synchronized static void initBrowser(String portnum) {
		initBrowser(portnum, false);
	}
	
	public synchronized static void initBrowser(String portnum, boolean useWD) {
		if (useWD) {
			driver = new InternetExplorerDriver();
			driver.manage().timeouts().implicitlyWait(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(Settings.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			browser = new WebDriverBackedSelenium(driver, APP_URL_LOCAL + portnum);
		} else {
			int port = 6789;
			driver = null;
			if (System.getProperty("selenium.port") != null) {
				port = Integer.parseInt(System.getProperty("selenium.port"));
			}
			
			System.out.println("Port set to: " + port);
			
			if (browser == null) {
				
				if (ECLIPSE_RUNS_SERVER) {
					RemoteControlConfiguration config = new RemoteControlConfiguration();
			
					config.setPort(port);
					try {
						server = new SeleniumServer(config);
						server.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				browser = new DefaultSelenium("localhost", port, BROWSER_IEXPLORE,
						APP_URL_LOCAL+portnum);
				 
	
				
				browser.start();
				browser.useXpathLibrary("javascript-xpath");
			}
		}
		
	}
	
	
}