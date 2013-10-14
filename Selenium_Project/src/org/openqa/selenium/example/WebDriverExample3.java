package org.openqa.selenium.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverExample3  {
	
	WebDriver driver;
	
	@Before
	public void init() {
        driver = new InternetExplorerDriver();
	}
    
	@Test
    public void testIEPopup() {

        driver.get("http://jsfiddle.net/zalun/yFuPr/");

        driver.get("http://www.google.com");
        
        Alert alert = driver.switchTo().alert();
        alert.accept();
        
        try {
        	Thread.sleep(15000);
        } catch (Exception e) {}
    }
	
	@After
	public void tearDown() {
		driver.quit();
	}
}