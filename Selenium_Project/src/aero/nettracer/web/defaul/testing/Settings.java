package aero.nettracer.web.defaul.testing;

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
	
	public static final boolean ECLIPSE_RUNS_SERVER = true;
	
	//URL ROOT FOR WEB APP BEING TESTED
	public static final String APP_URL_LOCAL8080 = "http://localhost:8080/";
	
	//DELAY IN MS BETWEEN ACTIONS
	public static final String EXECUTION_SPEED_FAST = "2";
	public static final String EXECUTION_SPEED_NORMAL = "250";
	public static final String EXECUTION_SPEED_SLOW = "1000";
	
	//LOGIN CREDENTIALS
	public static final String USERNAME = "ntadmin";
	public static final String PASSWORD = "Nettracer1";
	
	//VARIABLE SETTINGS / GLOBALS
	public static String PAWOB = "";
	public static int SEARCH_ALL = 0;
	public static int SEARCH_LD = 1;
	public static int SEARCH_DAM = 2;
	public static int SEARCH_PILF = 3;
	public static int SEARCH_CURRENT = SEARCH_ALL;
	
}
