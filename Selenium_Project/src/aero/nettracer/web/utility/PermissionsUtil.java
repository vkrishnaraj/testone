package aero.nettracer.web.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class PermissionsUtil {
	
	// PERMISSION IDS
	
	// ACTIVE TRACING SECTION
	public static final String ACTIVE_TRACE_ONHAND = "47";
	
	// ON-HAND SECTION
	public static final String ADD_ON_HAND_BAG = "2";
	public static final String COLLECT_POS_ID = "635";
	public static final String ASSIGN_WAREHOUSE_DATES = "704";
	public static final String UPDATE_OHD = "318";
	public static final String BAGGAGE_DELIVERY_ORDER_OHD = "68";
	public static final String RETRIEVE_ON_HAND = "14";
	public static final String REMARK_UPDATE_ONHAND = "87";
	public static final String CREATE_TEMP_OHD = "303";
	public static final String UPDATE_OHD_LOSS_CODES = "317";
	public static final String MASS_ON_HAND_BAG = "5";
	public static final String FORWARD_MESSAGE = "4";
	public static final String OHD_PHOTOS = "3";
	public static final String EXPRESS_ON_HAND_BAG = "51";
	public static final String TO_BE_INVENTORIED = "412";
	
	// LOST DELAY SECTION
	public static final String REMARK_UPDATE_LOSTDELAY = "88";
	public static final String UPDATE_INCIDENT_LOSTDELAY = "203";
	public static final String PASSENGER_COMMUNICATION_READ_ONLY = "321";
	public static final String SEARCH_BAGGAGE_DELIVERY_ORDER = "70";
	public static final String BAGGAGE_DELIVERY_ORDER_LD = "69";
	public static final String ISSUE_RON_KITS = "626";
	public static final String PUSH_LOST_DELAY_TO_CRM = "403";
	public static final String EXPRESS_MISHANDLED_BAG = "48";
	public static final String CANCEL_BDO = "407";
	public static final String BAGGAGE_WEIGHT = "330";
	public static final String ISSUANCE_ITEM_LOSTDELAY = "645";
	public static final String REOPEN_LOST_DELAYED = "309";
	public static final String CUSTOMER_COMMENTS = "212";
	public static final String RETRIEVE_REPORTS = "11";
	public static final String OTHER_SYSTEM_INFORMATION_LD = "209";
	public static final String CREATE_TEMP_INCIDENTS = "302";
	public static final String EDIT_TRACING_STATUS = "705";
	public static final String UPDATE_CREATED_LOSTDELAY = "206";
	public static final String ADD_MISHANDLED_BAG = "7";
	public static final String UPDATE_REMARKS = "305";
	public static final String UPDATE_LOSS_CODES = "304";
	public static final String PASSENGER_PICK_UP = "666";
	
	// DAMAGED SECTION
	public static final String PUSH_DAMAGED_TO_CRM = "404";
	public static final String UPDATE_CREATED_DAMAGED = "207";
	public static final String REMARK_UPDATE_DAMAGED = "86";
	public static final String DAMAGED_PHOTOS = "55";
	public static final String OTHER_SYSTEM_INFORMATION_DAM = "210";
	public static final String UPDATE_DAMAGE_LOSS_CODES = "306";
	public static final String UPDATE_INCIDENT_DAMAGED = "204";
	public static final String RECEIVE_TIMESTAMP_COLLECT = "640";
	public static final String RECEIVE_TIMESTAMP_DELETE = "641";
	public static final String SPECIAL_CONDITIONS = "642";
	public static final String ADDITIONAL_ITEM_INFORMATION_COLLECT = "644";
	public static final String ADD_DAMAGED_BAG = "9";
	public static final String REOPEN_DAMAGED = "310";
	public static final String RETRIEVE_DAMAGED = "10";
	public static final String ISSUE_REPLACEMENT_BAGS = "627";
	public static final String EXPEDITE_TAG_NUM_COLLECT = "639";
	public static final String EXPRESS_DAMAGED_BAG = "49";
	public static final String ISSUANCE_ITEM_DAMAGE = "646";
	
	// MISSING SECTION
	public static final String EXPRESS_MISSING_ARTICLES = "50";
	public static final String REMARK_UPDATE_MISSING = "89";
	public static final String REOPEN_PILFERAGE = "311";
	public static final String ADDITIONAL_MISSING_ITEM_INFORMATION_COLLECT = "649";
	public static final String ISSUANCE_ITEM_MISSING = "647";
	public static final String UPDATE_MISSING_LOSS_CODES = "307";
	public static final String OTHER_SYSTEM_INFORMATION_PIL = "211";
	public static final String PUSH_PILFERAGE_TO_CRM = "406";
	public static final String RETRIEVE_MISSING_ARTICLES = "37";
	public static final String ADD_MISSING_ARTICLES = "13";
	public static final String UPDATE_CREATED_MISSING = "208";
	public static final String UPDATE_INCIDENT_MISSING = "205";
	
	// TASK MANAGER SECTION
	public static final String DELETE_BAGTAGS_TO_BAGS = "708";
	public static final String LIMITED_LOSS_CODES = "702";
	public static final String MANAGE_CSS_DAILY_CALLS = "658";
	public static final String NTLF_TM_OPEN_HV_ITEMS = "655";
	public static final String GET_NEXT_DISPUTE = "701";
	public static final String SPLIT_CODE_AND_STATION_LOCK = "700";
	public static final String NTLF_TM_SHIP_TO_LFC = "660";
	public static final String FORWARD_COPIES = "216";
	public static final String ADD_NEW_CONTENTS = "707";
	public static final String FRAUD_REQUESTS = "999";
	public static final String CENTRAL_BAGGAGE_CLAIMS_FEATURES = "411";
	public static final String ON_HAND_BAGS = "200";
	public static final String TELETYPE_REPORT = "369";
	public static final String MANAGE_FAULT_DISPUTE = "352";
	public static final String DISPUTE_FAULT_CODE = "351";
	public static final String LOCAL_PIRS_AND_DPRS_IN_LAST_24_HOURS = "334";
	public static final String INCOMING_INCIDENTS_TYPE_DAMAGED = "333";
	public static final String INCOMING_INCIDENTS_TYPE_PILFERAGE = "332";
	public static final String CHILD_RESTRAINT_SYSTEM = "630";
	public static final String ITEMS_READY_FOR_SHIPPING = "631";
	public static final String INCOMING_INCIDENTS_TYPE_DELAYED = "331";
	public static final String PASSENGER_COMMUNICATION = "320";
	public static final String BAGBUZZ = "503";
	public static final String APPROVED_PAYMENTS = "316";
	public static final String CLERICAL_CLAIMS_FEATURES = "410";
	public static final String CBRO_MANAGEMENT = "213";
	public static final String VIEW_MATCHES_TM = "20";
	public static final String CBRO_VIEW = "29";
	public static final String VIEW_CREATED_REQUESTS = "31";
	public static final String INCOMING_BAGS = "25";
	public static final String INTERIM_EXPENSE_REQUESTS = "56";
	public static final String BAGS_TO_BE_DELIVERED = "24";
	public static final String TEMPORARY_ON_HAND = "26";
	public static final String MASS_ON_HANDS = "22";
	public static final String VIEW_INCOMING_REQUEST = "21";
	public static final String TEMPORARY_REPORTS = "27";
	public static final String OTHER_TASKS = "19";
	public static final String FORWARD_BAGS_TO_LZ = "23";
	public static final String CLAIMS_TO_BE_PROCESSED = "18";
	public static final String CREATED_INTERIM_EXPENSE_REQUESTS = "58";
	public static final String MY_INBOX = "16";
	public static final String INCOMING_INCIDENTS = "90";
	
	// REPORTING SECTION
	public static final String BY_PASSENGER_BOARDINGS = "33";
	public static final String OCCURRENCES_PER_FLIGHT = "34";
	public static final String RECOVERY = "38";
	public static final String DISBURSEMENTS = "35";
	public static final String CUSTOM_REPORTS = "85";
	public static final String STATION = "36";
	public static final String OCCURRENCES = "32";
	public static final String OHD_BAGGAGE_REPORT = "91";
	public static final String QUERY_REPORTS = "201";
	
	// ADMIN SECTION
	public static final String MAINTAIN_GROUPS = "43";
	public static final String REGION = "510";
	public static final String MAINTAIN_STATIONS = "41";
	public static final String FRAUD = "507";
	public static final String BAGBUZZADMIN = "504";
	public static final String MAINTAIN_SHIFTS = "45";
	public static final String SAVE_BUTTON_AT_TOP_OF_PAGE = "625";
	public static final String MAINTAIN_USER_PROFILE = "44";
	public static final String MAINTAIN_SUBCOMPANIES = "628";
	public static final String DRIVERS_LICENSE_VIEW_EDIT = "633";
	public static final String ISSUANCE_ITEM_STATION_ADMIN = "634";
	public static final String PASSPORT_COLLECT = "636";
	public static final String DRIVERS_LICENSE_COLLECT = "632";
	public static final String REOPEN_LOST_AND_FOUND = "618";
	public static final String PASSPORT_VIEW_EDIT = "637";
	public static final String MAINTAIN_AGENTS = "42";
	public static final String EXTERNAL_LINKS = "638";
	public static final String SALVAGE = "601";
	public static final String DOCUMENT_TEMPLATES_MANAGE = "651";
	public static final String MAINTAIN_COMPANIES = "40";
	public static final String DEPRECIATION_CALCULATOR_ADMINISTRATION = "656";
	public static final String MAINTAIN_DELIVERY_COMPANIES = "92";
	public static final String BILLING = "72";
	public static final String MAINTAIN_WEB_SERVICE_AGENTS = "101";
	public static final String MAINTAIN_SUBCOMPANIES_2 = "650";
	public static final String MAINTAIN_USER_ACTIVITY = "30";
	public static final String AUDIT_TRAIL = "71";
	public static final String ISSUANCE_ITEM_GLOBAL_ADMIN = "643";
	public static final String SEND_MESSAGES_TO_ALL_STATIONS = "402";
	public static final String MAINTAIN_CODES = "57";
	public static final String PASSENGER_VIEW = "53";
	public static final String MAINTAIN_AIRPORTS = "52";
	public static final String MANAGE_TASKS = "659";
	
	// CLAIMS SECTION
	public static final String FRAUD_FORUM_CREATE_THREAD = "802";
	public static final String CLAIM_DEPRECIATION_CALCULATOR = "657";
	public static final String FRAUD_FORUM_SEARCH = "801";
	public static final String CREATE_CLAIM = "602";
	public static final String PAY_EXPENSE = "314";
	public static final String ALL_STATION_CLAIMS = "315";
	public static final String CREATE_EXPENSE = "312";
	public static final String CLAIM_PRORATE = "61";
	public static final String EXPENSE_PAYOUT = "60";
	public static final String MODIFY_CLAIM = "603";
	public static final String VIEW_FRAUD_RESULTS = "604";
	public static final String SHARED_ATTACHMENTS = "605";
	public static final String CLAIM_SETTLEMENT = "214";
	public static final String APPROVE_EXPENSE = "313";
	
	// TRACING SECTION
	public static final String CUSTOM_QUERY = "17";
	public static final String SCANNER_DATA = "202";
	public static final String VIEW_MATCHES_TRC = "63";
	
	// LOST AND FOUND SECTION
	public static final String ADD_LOST_REPORT = "65";
	public static final String RETRIEVE_LOSTFOUND = "67";
	public static final String NTLF_FOUND_ITEMS = "653";
	public static final String NTLF_ENTER_ITEMS = "652";
	public static final String NTLF_SEARCH_ITEMS = "654";
	public static final String ADD_FOUND_REPORT = "66";
	
	// WORLD TRACER SECTION
	public static final String WORLDTRACER_ACTION_FILES = "94";
	public static final String WORLDTRACER_INSERT_BDO = "100";
	public static final String WORLDTRACER_TTY = "97";
	public static final String WORLDTRACER_FWD = "95";
	public static final String WORLDTRACER_ROH = "96";
	public static final String WORLDTRACER_REQUEST_QOH = "308";
	public static final String WORLDTRACER_SUS_RIT = "103";
	public static final String WORLDTRACER_PXF = "319";
	public static final String WORLDTRACER_INSERT_OHD = "99";
	public static final String CLONE_OHDS = "511";
	public static final String VIEW_OHD = "502";
	public static final String VIEW_AHL = "501";
	public static final String CREATE_WT_FILES_FOR_OTHER_CARRIERS = "370";
	public static final String WORLDTRACER_LOG = "300";
	public static final String WORLDTRACER_INSERT_INCIDENT = "98";
	
	// LFC SECTION
	public static final String BOX_COUNT = "703";
	public static final String SEARCH_FOUND_ITEMS = "623";
	public static final String SEARCH_LOST_REPORTS = "622";
	public static final String LOAD_FOUND_FROM_TASK_MANAGER = "616";
	public static final String UPDATE_LF_REMARKS = "624";
	public static final String PROCESS_TRACE_RESULTS = "617";
	public static final String ENTER_ITEMS = "615";
	public static final String CREATE_SALVAGE = "620";
	public static final String SEARCH_SALVAGE = "621";
	
	// GENERAL SECTION
	public static final String LOSS_CODES_AT_PER_BAG_LEVEL = "661";
	public static final String EDIT_NON_CLOSED_NON_DELIVERED_BAGS_SAME = "662";
	public static final String EDIT_CLOSED_DELIVERED_BAGS_SAME = "663";
	public static final String EDIT_NON_CLOSED_NON_DELIVERED_BAGS_OTHER = "664";
	public static final String EDIT_CLOSED_DELIVERED_BAGS_OTHER = "665";
	public static final String EDIT_ANY_BAGS= "675";
	public static final String SECURE_REMARKS = "335";
	public static final String INCIDENT_COURTESY_REASON_COLLECT = "648";
	public static final String CAPTURE_ACAA_INFORMATION = "336";
	
	public static boolean navigateToPermissionsPageTest(WebDriver driver, String location, String companyCode, String groupId) {
		boolean success = false;
		LoginUtil.loginOGAdmin(driver, location);	
		if (WebDriverUtil.checkNoErrorPage(driver)) {
			WebDriverUtil.checkCopyrightAndQuestionMarks(driver);
			WebDriverUtil.clickMenu(driver, "menucol_9.2");
			if (WebDriverUtil.checkNoErrorPage(driver)) {
				driver.findElement(By.name("companySearchName")).sendKeys(companyCode);
				driver.findElement(By.id("button")).click();
				if (WebDriverUtil.checkNoErrorPage(driver)) {
					driver.findElement(By.xpath("//a[contains(@href, 'createGroup.do?companyCode=" + companyCode + "')]")).click();
					if (WebDriverUtil.checkNoErrorPage(driver)) {
						driver.findElement(By.xpath("//a[contains(@href, 'componentAdmin.do?groupId=" + groupId + "')]")).click();
						if (WebDriverUtil.checkNoErrorPage(driver)) {
							success = driver.getPageSource().contains("Maintain Group Permissions");
						} else {
							System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Permissions. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
						}
					} else {
						System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Trying to Maintain Groups. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!! - Error Occurred When Searching For Southwest. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
				}
			} else {
				System.out.println("!!!!!!!!!!!!!!! - Maintain Companies Page Failed To Load. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
			}			
		} else {
			System.out.println("!!!!!!!!!!!!!!! - Failed to Log In. Error Page Loaded Instead. - !!!!!!!!!!!!!!!!!!");
		}
		return success;
	}
	
	public static void navigateToPermissionsPage(WebDriver driver, String location, String groupId) {
		LoginUtil.loginOGAdmin(driver, location);
		driver.get(location + "componentAdmin.do?groupId=" + groupId);
	}
	
	public static boolean setPermissions(WebDriver driver, String location, String companyCode, String groupId, String[] permissions, boolean[] values) {
		if (permissions == null || values == null || permissions.length != values.length) {
			throw new IllegalArgumentException("Please provide the permissions and a value to which each one should be set.");
		}
		navigateToPermissionsPage(driver, location, groupId);
			
		for (int i = 0; i < permissions.length; ++i) {
			if (values[i]) {
				WebDriverUtil.check(driver.findElement(By.name(permissions[i])));
			} else {
				WebDriverUtil.uncheck(driver.findElement(By.name(permissions[i])));
			}
		}
			
		WebElement element = driver.findElement(By.xpath("(//input[@id='button'])[2]"));
		element.click();
		WebDriverUtil.waitForPageToLoadImproved(3000);
		return true;
	}
}
