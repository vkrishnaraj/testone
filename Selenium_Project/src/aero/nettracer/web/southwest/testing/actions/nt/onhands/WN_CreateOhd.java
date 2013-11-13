package aero.nettracer.web.southwest.testing.actions.nt.onhands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;

import aero.nettracer.web.southwest.testing.WN_SeleniumTest;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class WN_CreateOhd extends WN_SeleniumTest {

	@Test
	public void testModifiedByInfoPresent() {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Date/Time Modified"));
			verifyTrue(isTextPresent("Modified Agent"));
			verifyTrue(isElementPresent(By.name("dispModifiedDate")));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testPosIdPermission() {
		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {false}));

		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyFalse(isTextPresent("Position ID"));
			verifyFalse(isTextPresent("Late Check"));
			verifyFalse(isElementPresent(By.name("posId")));
			verifyFalse(isElementPresent(By.name("lateCheckValue")));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}

		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {true}));

		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyTrue(isTextPresent("Position ID"));
			verifyTrue(isTextPresent("Late Check"));
			verifyTrue(isElementPresent(By.name("posId")));
			verifyTrue(isElementPresent(By.name("lateCheckValue")));
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testCreateOnhand() {
		clickMenu("menucol_4.1");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			selenium.type("name=posId", "123456");
			selenium.click("name=lateCheckValue");
			selenium.select("name=bagColor", "label=BK - Black");
			selenium.select("name=bagType", "label=22");
			selenium.type("name=itinerarylist[0].legfrom","ATL");
			selenium.type("name=itinerarylist[0].legto","LAS");
			selenium.type("name=itinerarylist[0].flightnum", "123");
			selenium.click("id=calendar20");
			selenium.click("link=Today");
			selenium.select("name=disposal_status.status_ID", "label=Disposed of Locally");
			selenium.click("name=savetracing");
			verifyEquals("Remark for Local Disposal is required.", selenium.getAlert());
			selenium.type("name=remark[0].remarktext", "Test Remark Text");
			selenium.type("name=dispBagArriveDate","a");
			selenium.click("name=savetracing");
			verifyEquals("Bag Arrival Date is not a valid date.[Refer to your date format]", selenium.getAlert());
			
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date d=cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String nowdate = dateFormat.format(d);
			selenium.type("name=dispBagArriveDate",nowdate);
			selenium.click("name=savetracing");
			verifyEquals("Bag Arrival Date may not be a future date", selenium.getAlert());
			selenium.click("id=calendar");
			selenium.click("link=Today");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				verifyTrue(isElementPresent(By.xpath("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a")));
				String ohdId = selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				Settings.ONHAND_ID_WN = ohdId;
				System.out.println("WN: Onhand Created: " + Settings.ONHAND_ID_WN);
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to save Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Navigate to Add Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		
		selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			checkCopyrightAndQuestionMarks();
			verifyEquals(Settings.ONHAND_ID_WN, selenium.getValue("name=ohd_id"));
			verifyEquals("123456", selenium.getValue("name=posId"));
			verifyEquals("on", selenium.getValue("name=lateCheckValue"));
			selenium.click("name=lateCheckValue");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();
			if (checkNoErrorPage()) {
				checkCopyrightAndQuestionMarks();
				selenium.click("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a");
				waitForPageToLoadImproved();
				if (checkNoErrorPage()) {
					verifyEquals("off", selenium.getValue("name=lateCheckValue"));
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Failed to reload Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					verifyTrue(false);
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Failed to save Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				verifyTrue(false);
			}
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to load the new Onhand. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			verifyTrue(false);
		}
		goToTaskManager();
	}
	
	@Test
	public void testPosIdOnAuditTrail() {
		verifyTrue(navigateToAuditTrail());
		verifyTrue(isTextPresent("Position ID"));
		assertEquals("123456", selenium.getText("//div[@id='maincontent']/table/tbody/tr[5]/td[2]"));
		verifyTrue(isTextPresent("Late Check"));
		assertEquals("No", selenium.getText("//div[@id='maincontent']/table/tbody/tr[6]/td[2]"));
		goToTaskManager();
	}
	
	@Test
	public void testPoisIdNotOnAuditTrail() {
		verifyTrue(setPermissions(new String[] {PermissionsUtil.COLLECT_POS_ID}, new boolean[] {false}));
		verifyTrue(navigateToAuditTrail());
		verifyFalse(isTextPresent("Position ID"));
		verifyFalse(isTextPresent("Late Check"));
		goToTaskManager();		
	}
	

	@Test
	public void testSecureRemarksDisabledOHD() {
		verifyTrue(setPermissions(new String[] { "335"}, new boolean[] { false}));
		verifyTrue(navigateToOnhand());
		selenium.click("name=addremark");
		waitForPageToLoadImproved();
		
		if (checkNoErrorPage()) {
			verifyFalse(isTextPresent("Secure Remark"));
			verifyFalse(isTextPresent("General Remark"));
			verifyFalse(isTextPresent("Remark is Secure"));
			selenium.type("name=remark[1].remarktext", "General Test");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();

		} else {
			System.out.println("Failed to add Remark");
		}
		if (checkNoErrorPage()) {
			goToTaskManager();
		} else {
			System.out.println("Failed to save Remark");
		}
		
	}
	
	@Test
	public void testSecureRemarksEnabledOHD() {
		verifyTrue(setPermissions(new String[] { "335"}, new boolean[] { true}));
		verifyTrue(navigateToOnhand());
		selenium.click("name=addremark");
		waitForPageToLoadImproved();
		if (checkNoErrorPage()) {
			verifyTrue(isTextPresent("Secure Remark"));
			selenium.click("name=remark[2].secure");
			selenium.type("name=remark[2].remarktext", "Secure Test");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();

		} else {
			System.out.println("Failed to add Remark");
		}
		if (checkNoErrorPage()) {

			verifyTrue(navigateToOnhand());
			verifyTrue(isTextPresent("Remark is Secure"));
			verifyTrue(setPermissions(new String[] { "335"}, new boolean[] { false}));
			goToTaskManager();
		} else {
			System.out.println("Failed to save OHD");
		}
	}

	@Test
	public void testDisposedLocalReq() {
		verifyTrue(navigateToOnhand());
		selenium.select("name=disposal_status.status_ID", "label=Please Select");
		selenium.click("name=savetracing");
		waitForPageToLoadImproved();

		if (checkNoErrorPage()) {
			verifyTrue(navigateToOnhand());
			selenium.select("name=disposal_status.status_ID", "label=Disposed of Locally");
			selenium.click("name=savetracing");
			assertEquals("Remark for Local Disposal is required.", selenium.getAlert());
			selenium.click("name=additem");
			waitForPageToLoadImproved();
		} else {
			System.out.println("Failed to save Onhand Disposal Status to 'Please Select'");
		}
		
		
		if (checkNoErrorPage()) {
			selenium.click("name=savetracing");
			assertEquals("Remark for Local Disposal is required.", selenium.getAlert());
			selenium.click("name=addremark");
			waitForPageToLoadImproved();

		} else {
			System.out.println("Failed to add content item to OHD");
		}

		if (checkNoErrorPage()) {
			selenium.type("name=remark[3].remarktext", "General Test");
			selenium.click("name=savetracing");
			waitForPageToLoadImproved();

		} else {
			System.out.println("Failed to add remark to OHD");
		}
		
		if (checkNoErrorPage()) {
			assertEquals(Settings.ONHAND_ID_WN,selenium.getText("//td[@id='middlecolumn']/table/tbody/tr/td/h1/p/a"));
			goToTaskManager();
		} else {
			System.out.println("Failed to save Remark");
		}
		
	}
	
	private boolean navigateToAuditTrail() {
		return navigateToOHDAuditTrail();
	}
	
}
