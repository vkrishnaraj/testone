package aero.nettracer.web.azul.testing;

import java.text.SimpleDateFormat;
import java.util.Date;

import aero.nettracer.web.utility.DefaultSeleneseTestCase;
import aero.nettracer.web.utility.IncidentUtil;
import aero.nettracer.web.utility.LoginUtil;
import aero.nettracer.web.utility.OnhandUtil;
import aero.nettracer.web.utility.PermissionsUtil;
import aero.nettracer.web.utility.Settings;

public class AD_SeleniumTest extends DefaultSeleneseTestCase {
	
	protected static final String COMPANY_CODE = "AD";
	protected static final String LZ_STATION = "CSB";
	protected static final String BASE_URL = Settings.APP_URL_LOCAL + Settings.PORT1 + "azul/";
	protected static final String ADMIN_GROUP_ID = "152";
	
	protected static final String MENU_LD_SEARCH = "menucol_1.4";
	protected static final String MENU_OHD_SEARCH = "menucol_4.4";
	protected static final String MENU_ADMIN_AUDIT = "menucol_9.11";
	
	protected static final String INCIDENT_TYPE_LOSTDELAY = "Lost/Delayed";
	protected static final String INCIDENT_TYPE_MISSING = "Missing Articles";
	protected static final String INCIDENT_TYPE_DAMAGED = "Damaged";
	
	protected static String TODAY;
	protected static String TODAY_SIX_DIGIT;
	
	// set up the current date for use throughout the tests
	static {
		TODAY = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
		TODAY_SIX_DIGIT = new SimpleDateFormat("MMddyy").format(new Date(System.currentTimeMillis()));
	}
	
	protected boolean navigateToIncidentAuditTrailTest() {
		return IncidentUtil.navigateToIncidentAuditTrailTest(driver, MENU_ADMIN_AUDIT, Settings.INCIDENT_ID_AD);
	}
	
	protected boolean navigateToIncidentAuditTrail() {
		IncidentUtil.navigateToIncidentAuditTrail(driver, BASE_URL, Settings.INCIDENT_ID_AD);
		return true;
	}
	
	protected void navigateAuditPagination(String id) {
		IncidentUtil.navigateAuditPagination(driver, id);
	}
	
	protected boolean navigateToIncidentTest(String incidentType) {
		return IncidentUtil.navigateToIncidentTest(driver, MENU_LD_SEARCH, incidentType, Settings.INCIDENT_ID_AD);
	}
	
	protected boolean navigateToIncident(String incidentType) {
		IncidentUtil.navigateToIncident(driver, BASE_URL, Settings.INCIDENT_ID_AD);
		return true;
	}
	
	protected boolean navigateToOHDAuditTrailTest() {
		return OnhandUtil.navigateToOHDAuditTrailTest(driver, MENU_ADMIN_AUDIT, Settings.ONHAND_ID_AD);
	}
	
	protected boolean navigateToOHDAuditTrail() {
		OnhandUtil.navigateToOHDAuditTrail(driver, BASE_URL, Settings.ONHAND_ID_AD);
		return true;
	}
	
	protected boolean navigateToOnhandTest() {
		return OnhandUtil.navigateToOHDTest(driver, MENU_OHD_SEARCH, Settings.ONHAND_ID_AD);
	}
	
	protected boolean navigateToOnhand() {
		OnhandUtil.navigateToOHD(driver, BASE_URL, Settings.ONHAND_ID_AD);
		return true;
	}
	
	protected boolean logoutOfNt() {
		boolean success = true;
		LoginUtil.logout(driver, BASE_URL);
		return success;
	}
	
	protected boolean loginToNt() {
		boolean success = true;
		LoginUtil.loginNTAutoTest(driver, BASE_URL, COMPANY_CODE, LZ_STATION);
		return success;
	}
	
	protected boolean setPermissions(String[] permissions, boolean[] values) {
		logoutTest();
		boolean success = PermissionsUtil.setPermissions(driver, BASE_URL, COMPANY_CODE, ADMIN_GROUP_ID, permissions, values);
		success = loginToNt() && success;
		return success;
	}
	
}
