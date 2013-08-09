package aero.nettracer.web.utility;

//custom settings class, read in some configs for your
//tests from a properties file blah blah blah use your
//imagination
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumTestBrowserDefault extends Settings {

	private static Selenium browser;
	private static SeleniumServer server;

	public synchronized static void setBrowser(Selenium _browser) {
		browser = _browser;
	}

	public synchronized static Selenium getBrowser() {
		return browser;
	}

	public synchronized static void stopBrowser() {
		stopBrowser("DEFAULT");
	}

	public synchronized static void stopBrowser(String client) {
		browser.stop();
		System.out.println(client + ": Selenium Tests Complete");
	}

	public synchronized static void initBrowser() {
		initBrowser(PORT);
	}
	public synchronized static void initBrowser(String portnum) {
		int port = 6789;
		
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
//			browser.setSpeed();
		}
	}
	
	
}