package org.openqa.selenium.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverExample  {
	
	WebDriver driver;
	
	@Before
	public void init() {
		driver = new InternetExplorerDriver();
	}
    
	@Test
    public void testIEPopup() {

        driver.get("http://jsfiddle.net/zalun/yFuPr/");

        driver.get("http://steveire.files.wordpress.com/2010/07/funny-pictures-kitten-looks-for-bugs-in-your-computer.jpg");
        
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