package aero.nettracer.web.utility;

public class Settings {
	
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
	
	public static final boolean ECLIPSE_RUNS_SERVER = true;
	
	//URL ROOT FOR WEB APP BEING TESTED
	public static final String APP_URL_LOCAL = "http://10.41.103.66:8180/";
	
	//DELAY IN MS BETWEEN ACTIONS
	public static final String EXECUTION_SPEED_FAST = "2";
	public static final String EXECUTION_SPEED_NORMAL = "250";
	public static final String EXECUTION_SPEED_SLOW = "1000";
	
	//LOGIN CREDENTIALS
	public static final String USERNAME_ADMIN = "ntadminauto";
	public static final String USERNAME_TEST = "nttestauto";
	public static final String PASSWORD_ADMIN = "ntadmin@hudson1";
	public static final String PASSWORD_TEST = "nttest@hudson1";
	public static final String PASSWORD_CHANGE = "nttest@hudson2";
	
	//START URLS
	public static final String START_URL = "/tracer/logoff.do";
	public static final String START_URL_B6 = "/jetblue/logoff.do";
	public static final String START_URL_WS = "/westjet/logoff.do";
	public static final String START_URL_AD = "/azul/logoff.do";
	public static final String START_URL_NK = "/spirit/logoff.do";
	
	//VARIABLE SETTINGS / GLOBALS
	public static String PAWOB = "";
	public static int SEARCH_ALL = 0;
	public static int SEARCH_LD = 1;
	public static int SEARCH_DAM = 2;
	public static int SEARCH_PILF = 3;
	public static int SEARCH_CURRENT = SEARCH_ALL;
	public static boolean LOGGED_IN = false;
	
}
