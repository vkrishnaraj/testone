package aero.nettracer.serviceprovider.common;

public class ServiceConstants {
	public static final int SABRE_GET_ENPLANEMENTS = 3;
	public static final int SABRE_WRITE_REMARK = 4;
	
	public static final String USER_NOT_AUTHORIZED = "USER NOT AUTHORIZED";
	public static final String CONFIGURATION_ERROR = "CONFIGURATION ERROR";
	public static final String UNEXPECTED_EXCEPTION = "UNEXPECTED EXCEPTION ENCOUNTERED";
	public static final String PNR_NOT_VALID = "PNR NOT VALID";
	public static final String COMMAND_NOT_PROPERLY_FORMATTED = "COMMAND NOT PROPERLY FORMATTED";
	public static final String COMMAND_TIMED_OUT = "COMMAND TIMED OUT - WT DID NOT RESPOND";
	public static final String CAPTCHA_EXCEPTION = "MUST ENTER CAPTCHA";
	public static final String RECORD_NOT_FOUND_EXCEPTION = "RECORD NOT FOUND";
	
	public final static String DB_DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DB_DATEFORMAT = "yyyy-MM-dd";
	public final static String DB_TIMEFORMAT = "HH:mm:ss";
	public static final String WT_WTR_CONNECTION_KEY = "WT_WTR_CONNECTION_KEY";
	public static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";
	public static final String CAPTCHA_TIMESTAMP = "CAPTCHA_TIMESTAMP";
	public static final String USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE = "USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE";
	public static final String CRON_USER = "CRON_USER";
	public static final String REFERENCED_OBJECT_CLOSED = "REFERENCED OBJECT ALREADY CLOSED";
	
	public static final String REMOTE_EXCEPTION = "UNABLE TO CONNECT TO REMOTE SERVICE"; 
	public static final String FLIGHT_DATA_EXCEPTION = "ERROR RECEIVING FLIGHT DATA";
	public static final String VOUCHER_DATA_EXCEPTION = "ERROR RECEIVING VOUCHER DATA";
}
