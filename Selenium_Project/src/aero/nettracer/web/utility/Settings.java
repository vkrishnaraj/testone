package aero.nettracer.web.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Settings {
	
	///////////////////////////////////////////////////////////SELENIUM SETTINGS ////////////////////////////////////////////////////////
	
	//browser options
	public static final String BROWSER_IEXPLORE = "*iexplore";
	public static final String BROWSER_MOCK = "*mock";
	public static final String BROWSER_FIREFOX = "*firefox";
	public static final String BROWSER_FIREFOXPROXY = "*firefoxproxy";
	public static final String BROWSER_PIFIREFOX = "*pifirefox";
	public static final String BROWSER_CHROME = "*chrome";
	public static final String BROWSER_IEXPLOREPROXY = "*iexploreproxy";
	public static final String BROWSER_FIREFOX3 = "*firefox3";
	public static final String BROWSER_SAFARIPROXY = "*safariproxy";
	public static final String BROWSER_GOOGLECHROME = "*googlechrome";
	public static final String BROWSER_KONQUEROR = "*konqueror";
	public static final String BROWSER_FIREFOX2 = "*firefox2";
	public static final String BROWSER_SAFARI = "*safari";
	public static final String BROWSER_PIIEXPLORE = "*piiexplore";
	public static final String BROWSER_FIREFOXCHROME = "*firefoxchrome";
	public static final String BROWSER_OPERA = "*opera";
	public static final String BROWSER_IEHTA = "*iehta";
	public static final String BROWSER_CUSTOM = "*custom";
	public static final String BROWSER_FIREFOXCUSTOM = "*firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
	
	public static final boolean ECLIPSE_RUNS_SERVER = false;
	
	//DELAY IN MS BETWEEN ACTIONS
	public static final String EXECUTION_SPEED_FAST = "2";
	public static final String EXECUTION_SPEED_NORMAL = "250";
	public static final String EXECUTION_SPEED_SLOW = "1000";
	
	
	
	//////////////////////////////////////////////////////////GENERIC TEST SETTINGS////////////////////////////////////////////////////////

	//URL ROOT FOR WEB APP BEING TESTED
//	public static final String APP_URL_LOCAL = "https://hudson.nettracer.aero/";
//	public static final String APP_URL_LOCAL = "http://172.30.68.3:8180/";
	public static final String APP_URL_LOCAL = "http://172.30.68.3";
//	public static final String APP_URL_LOCAL = "http://172.30.68.3:8380/";
//	public static final String APP_URL_LOCAL = "http://localhost";
	

	public static String PORT1=":8180/";
	public static String PORT2=":8280/";
	public static String PORT3=":8380/";
	
	//LOGIN CREDENTIALS
	public static final String USERNAME_ADMIN = "ntauto";
//	public static final String USERNAME_ADMIN = "ntadmin";
	public static final String USERNAME_OGADMIN = "ogadmin";
	public static final String USERNAME_TEST = "nttestauto";
	public static final String PASSWORD_ADMIN = "IpoL!Jan7";
//	public static final String PASSWORD_ADMIN = "SoftSprint1011";
	public static final String PASSWORD_OGADMIN = "Ladendead51!";
//	public static final String PASSWORD_OGADMIN = "InstaNokia41!";
	public static final String PASSWORD_TEST = "nttest@Hud1";
	public static final String PASSWORD_CHANGE = "nttest@Hud2";
//	public static final String USERNAME_ADMIN = "ntadmin";
//	public static final String PASSWORD_ADMIN = "InstaNokia41!";
	
	//TIMEOUT SETTINGS
	public static final String LOGIN_TIMEOUT = "120000";
	public static final String PAGE_LOAD_TIMEOUT = "120000";
	public static final int ELEMENT_TIMEOUT_SECONDS = 120;
	public static final int CHECK_TIMES = 1;
	
	//VARIABLE SETTINGS / GLOBALS
	public static int SEARCH_ALL = 0;
	public static int SEARCH_LD = 1;
	public static int SEARCH_DAM = 2;
	public static int SEARCH_PILF = 3;
	public static int SEARCH_CURRENT = SEARCH_ALL;
	public static boolean LOGGED_IN = false;
	public static DateFormat DEFAULT_SDF = new SimpleDateFormat("MM/dd/yyyy");
	public static String TODAYS_DATE = DEFAULT_SDF.format(Calendar.getInstance().getTime());
	
	
	///////////////////////////////////////////////////////////// COMPANY SPECIFIC SETTINGS //////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////// DEFAULT COMPANY ///////////////////////////////////////////////////////
	
	public static final String START_URL = "tracer";
	public static String INCIDENT_ID = "";
	public static String DAMAGE_ID = "";
	public static String PILFERAGE_ID = "";
	public static String ONHAND_ID = "";
	public static String PORT=":8180/";
	

	
	/////////////////////////////////////////////////////////// AZUL ///////////////////////////////////////////////////////

	public static final String START_URL_AD = "azul";
	public static String INCIDENT_ID_AD = "";
	public static String DAMAGE_ID_AD = "";
	public static String PILFERAGE_ID_AD = "";
	public static String ONHAND_ID_AD = "";
	

	
	/////////////////////////////////////////////////////////// JETBLUE ///////////////////////////////////////////////////////
		
	public static final String START_URL_B6 = "jetblue";
	public static String INCIDENT_ID_B6 = "";
	public static String DAMAGE_ID_B6 = "";
	public static String PILFERAGE_ID_B6 = "";
	public static String ONHAND_ID_B6 = "";
	

	
	/////////////////////////////////////////////////////////// SPIRIT ///////////////////////////////////////////////////////

	public static final String START_URL_NK = "spirit";
	public static String INCIDENT_ID_NK = "";
	public static String DAMAGE_ID_NK = "";
	public static String PILFERAGE_ID_NK = "";
	public static String ONHAND_ID_NK = "";
	

	
	/////////////////////////////////////////////////////////// WESTJET ///////////////////////////////////////////////////////
	
	public static final String START_URL_WS = "westjet";
//	public static final String START_URL_WS = "wjtracer/";
	public static String INCIDENT_ID_WS = "";
	public static String DAMAGE_ID_WS = "";
	public static String PILFERAGE_ID_WS = "";
	public static String ONHAND_ID_WS = "";
	public static String FOUND_ID_WS = "";
	

	
	/////////////////////////////////////////////////////////// AVIS ///////////////////////////////////////////////////////
	
	public static final String START_URL_AB = "avis";
	public static String LOST_ID_AB = "";
	public static String FOUND_ID_AB = "";
	

	
	/////////////////////////////////////////////////////////// DELTA ////////////////////////////////////////////////////////
	
	public static final String START_URL_DL = "delta";
	public static String CLAIM_ID_DL = "";
	

	
	/////////////////////////////////////////////////////////// UNITED ////////////////////////////////////////////////////////
	
	public static final String START_URL_UA = "united";
	public static String CLAIM_ID_UA = "";
	

	
	/////////////////////////////////////////////////////////// AMERICAN ////////////////////////////////////////////////////////
	
	public static final String START_URL_AA = "american";
	public static String CLAIM_ID_AA = "";
	

	
	/////////////////////////////////////////////////////////// LOSTANDFOUND ////////////////////////////////////////////////////////
	
	public static final String START_URL_LF = "lostandfound";
	public static String LOST_ID_LF = "";
	public static String FOUND_ID_LF = "";
	
	// CLIENT VIEW SOUTHWEST
	
	public static final String START_URL_LF_WN = "/client/southwest/landing.do";
	public static final String START_URL_LF_LG = "/client/southwest/login.do";
	public static String LOST_ID_LF_WN = "";
	

	
	/////////////////////////////////////////////////////////// WEBJET ///////////////////////////////////////////////////////
	
	public static final String START_URL_WH = "webjet";
	public static String INCIDENT_ID_WH = "";
	public static String DAMAGE_ID_WH = "";
	public static String PILFERAGE_ID_WH = "";
	public static String ONHAND_ID_WH = "";
	
	/////////////////////////////////////////////////////////// WEBJET ///////////////////////////////////////////////////////
		
	public static String CLAIMMADE_ID_FS = "";
	public static String CLAIMREQ_ID_FS = "";
	

	
	/////////////////////////////////////////////////////////// USAIR ///////////////////////////////////////////////////////
	
	public static final String START_URL_US = "tracer";

	/////////////////////////////////////////////////////////// SOUTHWEST ///////////////////////////////////////////////////////
		
	public static final String START_URL_WN = "ntsouthwest";
	public static String CLAIMREQ_ID_WN = "";
	//public static final String START_URL_WS = "wjtracer/";
	public static String INCIDENT_ID_WN = "";
	public static String DAMAGE_ID_WN = "";
	public static String PILFERAGE_ID_WN = "";
	public static String ONHAND_ID_WN = "";
	public static String FOUND_ID_WN = "";
	public static String TEMPLATE_ID_WN = "";
	public static String BDO_ID_WN = "";
	public static String CUST_COMM_ID = "";
	public static String PAYMENT_COMM_ID ="";
	
}
