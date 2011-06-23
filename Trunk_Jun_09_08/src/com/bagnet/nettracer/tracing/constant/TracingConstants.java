package com.bagnet.nettracer.tracing.constant;

import java.text.DecimalFormat;
import java.util.Properties;

/**
 * Contains all the application constants.
 * 
 * @author Ankur Gupta
 */
public class TracingConstants {

	public enum AgentActiveStatus { ALL, ACTIVE, INACTIVE }
	public final static int TRACING_ELEMENT_X = 7;
	public final static String MAINTAIN_AGENTS_TYPE_AGENTS = "0";
	public final static String MAINTAIN_AGENTS_TYPE_WEBSRVICE = "1";
		
	public final static String OWENS_GROUP = "OW";
	public final static boolean LIMIT_FAULT_AIRLINE = true;
	//System components
	
	public final static String SYSTEM_COMPONENT_MANAGE_TASKS = "Manage Tasks";
	public final static String SYSTEM_COMPONENT_MANAGE_2DAY_CALL = "Manage Two Day Call";
	public final static String SYSTEM_COMPONENT_MANAGE_3DAY_CALL = "Manage Three Day Call";
	public final static String SYSTEM_COMPONENT_MANAGE_4DAY_CALL = "Manage Four Day Call";
	
	public final static String SYSTEM_COMPONENT_NAME_CANCEL_BDO = "Cancel BDO";
	public final static String SYSTEM_COMPONENT_NAME_BDOS_FOR_STATIONS = "BDOs For Stations";
	public final static String SYSTEM_COMPONENT_NAME_AUDIT_ON_HAND = "Audit On hand";
	public final static String SYSTEM_COMPONENT_NAME_VIEW_MATCHES = "View Matches";
	public final static String SYSTEM_COMPONENT_NAME_VIEW_INCOMING_REQUEST = "View Incoming Request";
	public final static String SYSTEM_COMPONENT_NAME_VIEW_CREATED_REQUESTS = "View Created Requests";
	public final static String SYSTEM_COMPONENT_NAME_TRACING = "Tracing";
	public final static String SYSTEM_COMPONENT_NAME_TEMPORARY_REPORTS = "Temporary Reports";
	public final static String SYSTEM_COMPONENT_NAME_TEMPORARY_ON_HAND = "Temporary On-hand";
	public final static String SYSTEM_COMPONENT_NAME_TASK_MANAGER = "Task Manager";
	public final static String SYSTEM_COMPONENT_NAME_STATION = "Station";
	public final static String SYSTEM_COMPONENT_NAME_SEARCH_ON_HAND = "Search On-hand";
	public final static String SYSTEM_COMPONENT_NAME_RETRIEVE_REPORTS = "Retrieve Reports";
	public final static String SYSTEM_COMPONENT_NAME_RETRIEVE_ON_HAND = "Retrieve On-hand";
	public final static String SYSTEM_COMPONENT_NAME_RETRIEVE = "Retrieve";
	public final static String SYSTEM_COMPONENT_NAME_RECOVERY = "Recovery";
	public final static String SYSTEM_COMPONENT_NAME_OTHER_TASKS = "Other Tasks";
	public final static String SYSTEM_COMPONENT_NAME_ON_HAND_BAG = "On-hand Bag";
	public final static String SYSTEM_COMPONENT_NAME_OHD_PHOTOS = "Ohd Photos";
	public final static String SYSTEM_COMPONENT_NAME_OCCURRENCES_PER_FLIGHT = "Occurrences per Flight";
	public final static String SYSTEM_COMPONENT_NAME_OCCURRENCES = "Occurrences";
	public final static String SYSTEM_COMPONENT_NAME_OHD_BAGGAGE_REPORT = "OHD Baggage Report";
	public final static String SYSTEM_COMPONENT_NAME_MY_INBOX = "My Inbox";
	public final static String SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION = "Passenger Communication";
	public final static String SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION_READ_ONLY = "Passenger Communication Read Only";
	public final static String SYSTEM_COMPONENT_NAME_MISSING_ARTICLES = "Missing Articles";
	public final static String SYSTEM_COMPONENT_NAME_MISHANDLED_BAG = "Mishandled Bag";
	public final static String SYSTEM_COMPONENT_NAME_MASS_ON_HANDS = "Mass On-hands";
	public final static String SYSTEM_COMPONENT_NAME_INTERIM_EXPENSE_REQUESTS = "Interim Expense Requests";
	public final static String SYSTEM_COMPONENT_NAME_CREATED_INTERIM_EXPENSE_REQUESTS = "Created Interim Expense Requests";
	public final static String SYSTEM_COMPONENT_NAME_MASS_ON_HAND_BAG = "Mass On-hand Bag";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_USER_ACTIVITY = "Maintain User Activity";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_USER_PROFILE = "Maintain User Profile";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_STATIONS = "Maintain Stations";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_SHIFTS = "Maintain Shifts";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_DELIVERY_COMPANIES = "Maintain Delivery Companies";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_GROUPS = "Maintain Groups";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_COMPANIES = "Maintain Companies";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_CODES = "Maintain Codes";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_AGENTS = "Maintain Agents";
	public final static String SYSTEM_COMPONENT_NAME_MAINTAIN_WEB_SERVICE_AGENTS = "Maintain Web Service Agents";
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_BAGS = "Incoming Bags";
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS = "Incoming Incidents";
	
	//TODO: adding three sub items under last one to get the itemized view for WS
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_DELAYED = "Incoming Incidents Type Delayed";
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_PILFERAGE = "Incoming Incidents Type Pilferage";
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_TYPE_DAMAGED = "Incoming Incidents Type Damaged";
	public final static String SYSTEM_COMPONENT_NAME_INCOMING_INCIDENTS_LAST_24_HOURS = "Local PIRs And DPRs In Last 24 Hours";
	
	public final static String SYSTEM_COMPONENT_NAME_FORWARD_MESSAGE = "Forward Message";
	public final static String SYSTEM_COMPONENT_NAME_FORWARD_BAGS_TO_LZ = "Forward Bags to LZ";
	public final static String SYSTEM_COMPONENT_NAME_EXPRESS_ON_HAND_BAG = "Express On-hand Bag";
	public final static String SYSTEM_COMPONENT_NAME_EXPRESS_MISSING_ARTICLES = "Express Missing Articles";
	public final static String SYSTEM_COMPONENT_NAME_EXPRESS_MISHANDLED_BAG = "Express Mishandled Bag";
	public final static String SYSTEM_COMPONENT_NAME_EXPRESS_DAMAGED_BAG = "Express Damaged Bag";
	public final static String SYSTEM_COMPONENT_NAME_DISBURSEMENTS = "Disbursements";
	public final static String SYSTEM_COMPONENT_NAME_DAMAGED_PHOTOS = "Damaged Photos";
	public final static String SYSTEM_COMPONENT_NAME_DAMAGED_BAG = "Damaged Bag";
	public final static String SYSTEM_COMPONENT_NAME_CUSTOM_QUERY = "Custom Query";
	public final static String SYSTEM_COMPONENT_NAME_CLAIMS_TO_BE_PROCESSED = "Claims to be Processed";
	public final static String SYSTEM_COMPONENT_NAME_CLAIMS = "Claims";
	public final static String SYSTEM_COMPONENT_NAME_CREATE_CLAIM = "Create Claim";
	public final static String SYSTEM_COMPONENT_NAME_MODIFY_CLAIM = "Modify Claim";
	public final static String SYSTEM_COMPONENT_NAME_CLAIM_PRORATE = "Claim Prorate";
	public final static String SYSTEM_COMPONENT_NAME_CLAIM_SETTLEMENT = "Claim Settlement";
	public final static String SYSTEM_COMPONENT_NAME_PCN = "PCN";
	public final static String SYSTEM_COMPONENT_NAME_CREATE_EXPENSE = "Create Expense";
	public final static String SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE = "Approve Expense";
	public final static String SYSTEM_COMPONENT_NAME_APPROVED_EXPENSES = "Approved Payments";
	public final static String SYSTEM_COMPONENT_NAME_PAY_EXPENSE = "Pay Expense";
	public final static String SYSTEM_COMPONENT_NAME_CBRO_VIEW = "CBRO View";
	public final static String SYSTEM_COMPONENT_NAME_CBRO_MGMT = "CBRO Management";
	public final static String SYSTEM_COMPONENT_NAME_BY_PASSENGER_BOARDINGS = "By Passenger Boardings";
	public final static String SYSTEM_COMPONENT_NAME_CUSTOM_REPORTS = "Custom Reports";
	public final static String SYSTEM_COMPONENT_NAME_BAGS_TO_BE_DELIVERED = "Bags To Be Delivered";
	public final static String SYSTEM_COMPONENT_NAME_BAGS_IN_STATION = "On-hand Bags";
	public final static String SYSTEM_COMPONENT_NAME_SCANNER_DATA = "Scanner Data";
	public final static String SYSTEM_COMPONENT_NAME_FORWARD_NOTICES = "Forward Copies";
	public final static String SYSTEM_COMPONENT_NAME_CAPTCHAS = "Captcha";
	
	public final static String SYSTEM_COMPONENT_FORWARD_PCN = "pcn";

	public final static String FORWARD_SCANNER_DATA = "scannerData";
	
	public final static String SYSTEM_COMPONENT_NAME_ANALYTICAL_REPORTS = "Analytical Reports";
	public final static String SYSTEM_COMPONENT_NAME_ADMINISTRATION = "Administration";
	public final static String SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG = "Add On-hand Bag";
	public final static String SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES = "Add Missing Articles";
	public final static String SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG = "Add Mishandled Bag";
	public final static String SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG = "Add Damaged Bag";
	public final static String SYSTEM_COMPONENT_NAME_ACTIVE_TRACING = "Active Tracing";
	public final static String SYSTEM_COMPONENT_NAME_OSI_LD = "Other System Information LD";
	public final static String SYSTEM_COMPONENT_NAME_OSI_DAM = "Other System Information PIL";
	public final static String SYSTEM_COMPONENT_NAME_OSI_PIL = "Other System Information DAM";
	public final static String SYSTEM_COMPONENT_NAME_CUSTOMER_COMMENTS = "Customer Comments";
	public final static String SYSTEM_COMPONENT_NAME_CREATE_TEMP_INCIDENTS = "Create Temp Incidents";
	public final static String SYSTEM_COMPONENT_NAME_CREATE_TEMP_OHD = "Create Temp OHD";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_LOSS_CODES = "Update Loss Codes";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_DAMAGE_LOSS_CODES = "Update Damage Loss Codes";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_MISSING_LOSS_CODES = "Update Missing Loss Codes";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_OHD_LOSS_CODES = "Update OHD Loss Codes";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_REMARKS = "Update Remarks";
	public final static String SYSTEM_COMPONENT_NAME_REOPEN_LOSTDELAY = "Reopen Lost Delayed";
	public final static String SYSTEM_COMPONENT_NAME_REOPEN_DAMAGED_BAG = "Reopen Damaged";
	public final static String SYSTEM_COMPONENT_NAME_REOPEN_MISSING_ARTICLES= "Reopen Pilferage";
	
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER = "WorldTracer";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_ACTION_FILES = "WorldTracer Action Files";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD = "WorldTracer FWD";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH = "WorldTracer ROH";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_TTY = "WorldTracer TTY";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT = "WorldTracer Insert Incident";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_OHD = "WorldTracer Insert OHD";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_BDO = "WorldTracer Insert BDO";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_LOG = "WorldTracer Log";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_AUTO_AMEND = "WorldTracer Auto Amend";
	public static final String SYSTEM_COMPONENT_NAME_WORLD_TRACER_REQ_QOH = "WorldTracer Request QOH";
	// G for new PXF 
	public static final String SYSTEM_COMPONENT_NAME_WORLD_TRACER_PXF = "WorldTracer PXF";
	

	public final static String SYSTEM_COMPONENT_NAME_BDO = "Baggage Delivery Order";

	public final static String SYSTEM_COMPONENT_NAME_SEARCH_BDO = "Search Baggage Delivery Order";

	public final static String SYSTEM_COMPONENT_CLAIMS_TO_BE_PROCESSED_ACTION = "claims.do";
	
	public final static String SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD = "Remark Update LostDelay";
	public final static String SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA = "Remark Update Damaged";
	public final static String SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS = "Remark Update Missing";
	public final static String SYSTEM_COMPONENT_NAME_REMARK_UPDATE_OH = "Remark Update Onhand";
	
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_ASSIGNED_LD = "Assigned Station Update LostDelay";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_ASSIGNED_DA = "Assigned Station Update Damaged";
	public final static String SYSTEM_COMPONENT_NAME_UPDATE_ASSIGNED_MS = "Assigned Station Update Missing";
	
	public final static String SYSTEM_COMPONENT_NAME_QUERY_REPORTS = "Query Reports";
	
	public final static String SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST = "Incident Checklist";
	public final static String SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST_READ_ONLY = "Incident Checklist Read Only";
	
		public final static String SYSTEM_COMPONENT_NAME_SEND_MSG_ALL = "Send Messages to all Stations";

	public final static String SYSTEM_COMPONENT_NAME_PUSH_LD = "Push Lost Delay to CRM";
	public final static String SYSTEM_COMPONENT_NAME_PUSH_DAM = "Push Damaged to CRM";
	public final static String SYSTEM_COMPONENT_NAME_PUSH_PIL = "Push Pilferage to CRM";
	
	public static final String SYSTEM_COMPONENT_NAME_CREATE_LOST_REPORT = "Create Lost Report";
	public static final String SYSTEM_COMPONENT_NAME_CREATE_FOUND_ITEM = "Create Found Item";
	public static final String SYSTEM_COMPONENT_NAME_SEARCH_LOST_FOUND = "Search Lost/Found";
	
	public static final String SYSTEM_COMPONENT_NAME_LF_TRACE_RESULTS = "Trace Results";
	public static final String SYSTEM_COMPONENT_NAME_LF_OPEN_LOST_REPORTS = "Open Lost Reports";
	public static final String SYSTEM_COMPONENT_NAME_LF_OPEN_FOUND_ITEMS = "Open Found Items";
	public static final String SYSTEM_COMPONENT_NAME_LF_ITEMS_TO_SALVAGE = "Items to Salvage";
	public static final String SYSTEM_COMPONENT_NAME_LF_ITEMS_TO_DELIVER = "Items to Deliver";
	
	//Table numbers for status
	public final static int AJAX_STATUS_INC = 1;
	public final static int AJAX_STATUS_OHD = 2;
	
	public final static int TABLE_LOST_FOUND = 8;
	public final static int TABLE_DISPOSAL_LOST_FOUND = 9;
	public final static int TABLE_ON_HAND = 2;
	public final static int TABLE_INCIDENT = 1;
	public final static int TABLE_CLAIM = 7;
	public final static int TABLE_ITEM = 10;
	public final static int TABLE_EXPENSEPAYOUT = 11;
	public final static int TABLE_MESSAGE = 5;
	public final static int TABLE_TASK = 4;
	public final static int TABLE_TASK_MANAGER = 13;
	public final static int TABLE_BAGBUZZ = 14;
	public final static int TABLE_LF_STATUS = 16;
	public final static int TABLE_LF_DISPOSITION = 17;
	public final static int TABLE_LF_TRACE_STATUS = 18;
	
	public final static int PRIORITY_LOW = 1;
	public final static int PRIORITY_MEDIUM = 2;
	public final static int PRIORITY_HIGH = 3;
	



	public final static String DEFAULT_AGENT_TIMEOUT = "30";
	public final static String DEFAULT_AGENT_TIMEZONE = "14";
	public final static String DEFAULT_AGENT_CURRENCY = "USD";
	public final static String DEFAULT_LOCALE = "en";

	public final static int MASS_OHD_TYPE = 1;
	public final static int NOT_MASS_OHD_TYPE = 0;

	//Lost/Found status
	public final static int LOST_FOUND_OPEN = 40;
	public final static int LOST_FOUND_CLOSED = 41;
	
	public final static int LOST_REPORT = 1;
	public final static int FOUND_REPORT = 0;
	
	public final static String DISPLAY_DATETIMEFORMAT = "MM/dd/yyyy hh:mm:ss a";
	public final static String DISPLAY_DATEFORMAT = "MM/dd/yyyy";
	public final static String DISPLAY_TIMEFORMAT = "hh:mm:ss a";
	public final static String DISPLAY_TIMEFORMAT_B = "hh:mm a";
	public final static String DISPLAY_TIMEFORMAT_C = "HH:mm";
	
	public final static String DB_DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DB_DATEFORMAT = "yyyy-MM-dd";
	public final static String DB_TIMEFORMAT = "HH:mm:ss";
	
	public final static String DB_DATETIMEFORMAT_MSSQL = "MM/dd/yyyy hh:mm:ss a";
	public final static String DB_DATEFORMAT_MSSQL = "MM/dd/yyyy";
	public final static String DB_TIMEFORMAT_MSSQL = "hh:mm:ss a";
	


	public final static int ROWS_PER_PAGE = 15;
	public final static double MIN_MATCH_PERCENT = 5;

	// currency format
	public final static DecimalFormat DECIMALFORMAT = new DecimalFormat("#0.00");
	public final static int INCIDENT_LEN = 13;
	// types
	public final static int LOST_DELAY = 1;
	public final static int MISSING_ARTICLES = 2;
	public final static int DAMAGED_BAG = 3;
	public final static int OHD = 4;
	
	public final static String SYSTEM_COMPONENT_NAME_LOST_DELAY_UPDATE_ALL = "Update Incident LostDelay";
	public final static String SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_UPDATE_ALL = "Update Incident Damaged";
	public final static String SYSTEM_COMPONENT_NAME_DAMAGED_BAG_UPDATE_ALL = "Update Incident Missing";
	public final static String SYSTEM_COMPONENT_NAME_LOST_DELAY_UPDATE_CREATED = "Update Created LostDelay";
	public final static String SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_UPDATE_CREATED = "Update Created Damaged";
	public final static String SYSTEM_COMPONENT_NAME_DAMAGED_BAG_UPDATE_CREATED = "Update Created Missing";
	public final static String SYSTEM_COMPONENT_NAME_LOST_DELAY_REMARK = "Remark Update LostDelay";
	public final static String SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_REMARK = "Remark Update Missing";
	public final static String SYSTEM_COMPONENT_NAME_DAMAGED_BAG_REMARK = "Remark Update Damaged";
	public static final String SYSTEM_COMPONENT_NAME_OHD_UPDATE_ALL = "Update OHD";
	
	public static final String SYSTEM_COMPONENT_NAME_BAGGAGE_WEIGHT = "Baggage Weight";
	
	public static final String SYSTEM_COMPONENT_NAME_BAGBUZZ = "BagBuzz";
	public static final String SYSTEM_COMPONENT_NAME_BAGBUZZ_ADMIN = "BagBuzzAdmin";
	
	public static final String SYSTEM_COMPONENT_NAME_PRIVACY_PERMISSIONS = "Fraud";
	
	public static final String SYSTEM_COMPONENT_NAME_MANAGE_TASK = "Manage Tasks";
	public static final String SYSTEM_COMPONENT_NAME_MANAGE_TWO_DAY_CALL = "Manage Two Day Call";
	public static final String SYSTEM_COMPONENT_NAME_MANAGE_THREE_DAY_CALL = "Manage Three Day Call";
	public static final String SYSTEM_COMPONENT_NAME_MANAGE_FOUR_DAY_CALL = "Manage Four Day Call";
	
	//fault dispute
	public static final String SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE = "Dispute Fault Code"; 
	public final static String SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE = "Manage Fault Dispute";
	
	//teletype print
	public static final String SYSTEM_COMPONENT_NAME_TELETYPE_PRINT = "Teletype Report";
	
	// itinerary types
	public final static int PASSENGER_ROUTING = 0;
	public final static int BAGGAGE_ROUTING = 1;
	// claim payment types
	public final static int CHECK_PAYOUT = 1;
	public final static int VOUCHER_PAYOUT = 2;
	public final static int MILEAGE_PAYOUT = 3;
	
	public final static String ENUM_VOUCHER = "VOUCH";
	public final static String ENUM_DRAFT = "DRAFT";
	public final static String ENUM_MILE = "MILE";
	public final static String ENUM_INC = "INC";
	public final static String ENUM_CCREF = "CCREF";

	// leg types
	public final static int LEG_B_STATION = 1; // orinating station
	public final static int LEG_T_STATION = 2; // transfer station
	public final static int LEG_E_STATION = 3; // terminating station

	// expensepayout
	public final static int EXPENSEPAYOUT_DELIVERY = 2;
	public final static int EXPENSEPAYOUT_INTERIM = 3;
	
	public final static String EXPENSEPAYOUT_DELIVERY_CODE = "DEL";
	

	/** ** manufacturer id in database *** */
	public final static int MANUFACTURER_OTHER_ID = 71;

	/** *** report method ***** */
	public final static int MBR_REPORT_METHOD_IN_PERSON = 0;
	public final static int MBR_REPORT_METHOD_BSO_PHONE = 1;
	public final static int MBR_REPORT_METHOD_CALL_CENTER = 2;
	public final static int MBR_REPORT_METHOD_ONLINE = 3;

	/** ********************************* */
	/** ******** statuses in database** */
	/** ********************************* */
	public final static int MBR_STATUS_TEMP = 10;
	public final static int MBR_STATUS_PENDING = 11;
	public final static int MBR_STATUS_OPEN = 12;
	public final static int MBR_STATUS_CLOSED = 13;

	public final static int MESSAGE_STATUS_NEW = 24;
	public final static int MESSAGE_STATUS_REPLIED = 25;
	public final static int MESSAGE_STATUS_SEEN = 26;
	public final static int MESSAGE_STATUS_DELETED = 27;
	public final static int MESSAGE_STATUS_SENT = 29;

	//	status OHD
	public final static int OHD_STATUS_HOLD_FOR_PICKUP = 1;
	public final static int OHD_STATUS_TEMP = 14;
	public final static int OHD_STATUS_OPEN = 3;
	public final static int OHD_STATUS_CLOSED = 4;
	public final static int OHD_STATUS_MATCH_IN_TRANSIT = 5;
	public final static int OHD_STATUS_IN_TRANSIT = 6;
	public final static int OHD_STATUS_TO_BE_DELIVERED = 7;
	public final static int OHD_STATUS_EARLY_BAG = 57;

	public final static int OHD_REQUEST_STATUS_DENIED = 8;
	public final static int OHD_REQUEST_STATUS_FORWARDED = 9;
	public final static int OHD_REQUEST_CANCELLED = 80;
	
	public final static int OHD_STATUS_PROCESSFORDELIVERY = 51;
	public final static int OHD_STATUS_ALL=0;
	public final static int OHD_STATUS_ACTIVE=-1;
	
	public final static int LOG_NOT_RECEIVED = 0;
	public final static int LOG_RECEIVED = 1;
	public final static int LOG_CANCELLED = 2;

	public final static int TASK_STATUS_NOT_STARTED = 16;
	public final static int TASK_STATUS_IN_PROGRESS = 17;
	public final static int TASK_STATUS_COMPLETED = 18;
	public final static int TASK_STATUS_DEFERRED = 19;
	public final static int TASK_STATUS_WAITING = 20;
	public final static int TASK_STATUS_DELETED = 28;
	public final static int TASK_STATUS_NOT_COMPLETED = 79;
	
	public final static int LF_STATUS_SALVAGED = 45;

	// match status
	public final static int MATCH_STATUS_OPEN = 15;
	public final static int MATCH_STATUS_REJECTED = 21;
	public final static int MATCH_STATUS_MATCHED = 22;
	public final static int MATCH_STATUS_CLOSED = 23;

	// claim status
	public final static int CLAIM_STATUS_INPROCESS = 31;

	// expense payout status
	public final static int EXPENSEPAYOUT_STATUS_PENDING = 52;
	public final static int EXPENSEPAYOUT_STATUS_APPROVED = 53;
	public final static int EXPENSEPAYOUT_STATUS_DENIED = 54;
	public final static int EXPENSEPAYOUT_STATUS_PAID = 55;

	// bag status
	public final static int ITEM_STATUS_OPEN = 47;
	public final static int ITEM_STATUS_MATCHED = 48;
	public final static int ITEM_STATUS_TOBEDELIVERED = 49;
	public final static int ITEM_STATUS_PROCESSFORDELIVERY = 50;
	public final static int ITEM_STATUS_IN_TRANSIT = 56;
	
	// Task Manager status
	public final static int TASK_MANAGER_OPEN = 83;
	public final static int TASK_MANAGER_PROCESSED = 84;
	public final static int TASK_MANAGER_CLOSED = 85;
	public final static int TASK_MANAGER_PAUSED = 96;
	public final static int TASK_MANAGER_WORKING = 97;
	
	
	// BagBuzz status
	public final static int BAGBUZZ_NEW = 86;
	public final static int BAGBUZZ_PUBLISHED = 87;
	public final static int BAGBUZZ_DELETED = 88;
	public final static int BAGBUZZ_UNPUBLISHED = 95;
	
	// Dispute Resolution
	public final static int DISPUTE_RESOLUTION_STATUS_DENIED = 90;
	public final static int DISPUTE_RESOLUTION_STATUS_OPEN = 91;
	public final static int DISPUTE_RESOLUTION_STATUS_APPROVED = 92;
	public final static int DISPUTE_RESOLUTION_STATUS_MANUAL_CHANGE = 93;
	
	// item xdesc element type x
	public final static int XDESC_TYPE_X = 7;

	// remark types
	public final static int REMARK_REGULAR = 1;
	public final static int REMARK_CLOSING = 2;
	public final static int REMARK_WORLDTRACER_REQUEST = 3;
	public final static int REMARK_WORLDTRACER_RESPONSE = 4;

	// report output type
	public final static int REPORT_OUTPUT_UNDECLARED = -1;
	public final static int REPORT_OUTPUT_PDF = 0;
	public final static int REPORT_OUTPUT_HTML = 1;
	public final static int REPORT_OUTPUT_XLS = 2;
	public final static int REPORT_OUTPUT_CSV = 3;
	public final static int REPORT_OUTPUT_XML = 4;
	public final static int REPORT_OUTPUT_TELETYPE = 5;

	public final static String COMPANY_PAGESTATE_INFO = "1";
	public final static String COMPANY_PAGESTATE_SETTINGS = "2";
	public final static String COMPANY_PAGESTATE_SECURITY = "3";
	public final static String COMPANY_PAGESTATE_AUDITING = "4";
	public final static String COMPANY_PAGESTATE_WEB_SERVICES = "5";
	public final static String COMPANY_PAGESTATE_WORLDTRACER = "6";
	public final static String COMPANY_PAGESTATE_MOVETOLZ = "7";
	
	public final static int STATUS_SUSPECTED_FRAUD = 37;
	public final static int STATUS_KNOWN_FRAUD = 38;
	
	public final static int LF_STATUS_ALL = -1;
	public final static int LF_STATUS_OPEN = 600;
	public final static int LF_STATUS_CLOSED = 601;
	public final static int LF_DISPOSITION_TO_BE_DELIVERED = 602;
	public final static int LF_DISPOSITION_DELIVERED = 603;
	public final static int LF_DISPOSITION_PICKED_UP = 604;
	public final static int LF_DISPOSITION_SALVAGED = 605;
	public final static int LF_DISPOSITION_OTHER = 606;
	public final static int LF_TRACING_OPEN = 607;
	public final static int LF_TRACING_CONFIRMED = 608;
	public final static int LF_TRACING_REJECTED = 609;
	public final static int LF_TRACING_CLOSED = 610;
	
	public final static int LF_TYPE_LOST = 1;
	public final static int LF_TYPE_FOUND = 2;
	
	public final static String LF_VIEW_TRACE_RESULTS = "view_trace_results";
	public final static String LF_VIEW_TRACE_DETAILS = "view_trace_details";
	public final static String LF_VIEW_OPEN_LOST = "view_open_lost";
	public final static String LF_VIEW_OPEN_FOUND = "view_open_found";
	public final static String LF_VIEW_ITEMS_TO_SALVAGE = "view_items_salvage";
	public final static String LF_VIEW_ITEMS_TO_DELIVER = "view_items_deliver";
	
	public final static int LF_PHONE_TYPE_HOME = 3;
	public final static int LF_PHONE_TYPE_MOBILE = 4;
	public final static int LF_PHONE_TYPE_WORK = 5;
	public final static int LF_PHONE_TYPE_OTHER = 6;
	
	/** ********* forwards *********** */
	// no permission
	public final static String NO_PERMISSION = "nopermission";

	// logon
	public final static String LOGON = "logon";
	
	// pass reset
	public final static String PASS_RESET = "passreset";
	
	// error
	public final static String ERROR_MAIN = "error";

	// search
	public final static String RECEIPT_PARAMS = "receiptparams";

	// search
	public final static String SEARCH_INCIDENT = "searchincident";
	// search
	public final static String SEARCH_ONHAND = "searchOnHand";
	// custom query
	public final static String CUSTOM_QUERY = "customquery";
	public final static String SEARCH_FORWARD_NOTICE = "search_notices";

	// activetracing
	public final static String ACTIVE_TRACING = "activetracing";

	// search
	public final static String DELIVERY_LIST = "deliveryList";
	public final static String ONHAND_LIST = "onhandList";
	

	// integration prepopulate page
	public final static String PREPOPULATE_INCIDENT = "prepopulateincident";
	
	// lostdelay main page
	public final static String LD_MAIN = "ldmain";
	// missing articles main page
	public final static String MISSING_MAIN = "missingmain";
	// damaged bag main page
	public final static String DAMAGED_MAIN = "damagedmain";

	// lostdelay close page
	public final static String LD_CLOSE = "ldclose";
	public static final String LD_CLOSE_READ_ONLY = "ldclose_ro";
	// missing articles close page
	public final static String MISSING_CLOSE = "maclose";
	public final static String MISSING_CLOSE_READ_ONLY = "maclose_ro";
	// damaged bag close page
	public final static String DAMAGED_CLOSE = "damagedclose";
	public final static String DAMAGED_CLOSE_READ_ONLY = "damagedclose_ro";

	// on hand bag main page
	public final static String OHD_MAIN = "ohdmain";
	public final static String OHD_QUICK_MAIN = "ohdquickmain";
	public final static String OHD_MASS_MAIN = "ohdmassmain";
	public final static String OHD_HISTORICAL = "ohdhistorical";

	public final static String FOUND_HISTORICAL = "foundhistorical";
	public final static String LOST_HISTORICAL = "losthistorical";

	public final static String MA_HISTORICAL = "mahistorical";
	public final static String LD_HISTORICAL = "ldhistorical";
	public final static String DAMAGED_HISTORICAL = "damagedhistorical";

	public final static String VIEW_OHD_MAIN = "viewohdmain";
	// claim payout main page
	public final static String CLAIM_PAY_MAIN = "claimmain";
	public final static String CLAIM_SETTLEMENT = "claimsettlement";
	public final static String CLAIM_SETTLEMENT1 = "claimsettlement1";
	public final static String CLAIM_SETTLEMENT2 = "claimsettlement2";
	public final static String CLAIM_SETTLEMENT3 = "claimsettlement3";
	public final static String CLAIM_SETTLEMENT4 = "claimsettlement4";
	// claim prorate main page
	public final static String CLAIM_PRORATE_MAIN = "claimprorate";
	// claim address check main page
	public final static String ADDR_CHECK_MAIN = "addresscheck";
	// reporting options main page
	public final static String STAT_REPORT_MAIN = "reportmain";

	public final static String BILLING_REPORT = "billingReport";

	public final static String VIEW_OHD_LZED_LIST = "viewohdlzed";

	// form submit result
	public final static String INSERT_SUCCESS = "insertsuccess";
	// to distinguish update from addnew
	public final static String UPDATE_FILE_SUCCESS = "updatesuccess";
	public final static String CLOSE_FILE_SUCCESS = "closesuccess";

	//view reminders
	public final static String VIEW_REMINDERS_LIST = "viewReminders";

	public final static String VIEW_REQUEST_DETAILS = "view_request_details";

	public final static String VIEW_FORWARD_DETAILS = "view_forward_details";

	public final static String VIEW_MASS_OHD = "viewmassohd";
	public final static String VIEW_TEMP_OHD = "viewtempohd";
	public final static String VIEW_TEMP_REPORTS = "viewtempreports";

	//view task
	public final static String VIEW_TASK = "viewtask";

	public final static String VIEW_TASK_LIST = "viewtasks";

	public final static String TASK_SUCCESS = "tasksuccess";

	//update task
	public final static String UPDATE_TASK = "updatetask";

	//	update task
	public final static String UPDATE_SUCCESS = "updatesuccess";

	public final static String MATCH_LIST_VIEW = "viewmatches";

	public final static String MATCH_VIEW = "viewmatch";

	public final static String VIEW_REQUEST_LIST = "viewrequests";

	public final static String VIEW_CREATED_REQUEST_LIST = "viewcreatedrequests";

	public final static String VIEW_INCOMING_BAG_LIST = "viewincomingbags";
	public final static String VIEW_INCOMING_INCIDENTS = "viewincomingincidents";
	public final static String VIEW_MORNING_DUTIES = "viewmorningduties";
	public final static String MORNING_DUTIES_UPDATED = "morningdutiesupdated";
	
	// world tracer forwards
	public final static String VIEW_WORLDTRACER_ACTION_FILES = "viewworldtraceractionfiles";
	public final static String VIEW_WORLDTRACER_ACTION_FILES_COUNT = "viewworldtracercount";
	public final static String VIEW_WORLDTRACER_AF_VIEW_RAW_INC = "viewworldtracerafviewraw";
	public final static String VIEW_WORLDTRACER_SEARCH = "viewworldtracersearch";
	public final static String VIEW_WORLDTRACER_AHL = "viewworldtracerahl";
	public final static String VIEW_WORLDTRACER_OHD = "viewworldtracerohd";
	public final static String VIEW_WORLDTRACER_FWD = "viewworldtracerfwd";
	public final static String VIEW_WORLDTRACER_FOH = "viewworldtracerfoh";
	public final static String VIEW_WORLDTRACER_ROH = "viewworldtracerroh";
	public final static String VIEW_WORLDTRACER_TTY = "viewworldtracertty";
	public static final String VIEW_WORLDTRACER_REQ_QOH = "viewworldtracerreqqoh";
	public static final String VIEW_WORLDTRACER_PXF = "viewworldtracerpxf";

	public final static String VIEW_WORLDTRACER_SUSRIT = "viewworldtracersusrit";

	
	//bagbuzz
	public final static String VIEW_BAGBUZZ_EDITOR = "viewbagbuzzeditor";
	public final static String VIEW_BAGBUZZ_SEARCH = "viewbagbuzzsearch";
	public final static String VIEW_BAGBUZZ_VIEW = "viewbagbuzzview";

    public final static String VIEW_PRIVACY_PERMISSIONS = "viewprivacypermissions";
	
	//success on inserting on hand
	public final static String INSERT_ON_HAND_SUCCESS = "insertonhandsuccess";
	public final static String MASS_ON_HAND_SUCCESS = "massonhandsuccess";
	public final static String UPDATE_ON_HAND_SUCCESS = "updateonhandsuccess";

	public static final String FORWARD_OC_LIST = "forwardOcList";
	public static final String FORWARD_DISPLAY_OC = "forwardDisplayOc";
	public static final String FORWARD_DISPLAY_OC_AJAX = "forwardDisplayOcAjax";
	public static final String FORWARD_CHANGE_OC_STATUS = "forwardChangeStatus";
	
	//success on forward
	public final static String FORWARD_ON_HAND_SUCCESS = "forwardonhandsuccess";

	public final static String FORWARD_ERROR = "forwarderror";
	//success wt bag forward
	public final static String FORWARD_WT_BAG_SUCCESS = "forwardwtbagsuccess";

	//success wt tty
	public final static String SEND_WT_TTY_SUCCESS = "sendwtttysuccess";
	
	//success wt roh
	public final static String SEND_WT_ROH_SUCCESS = "sendwtrohsuccess";
	
	//success wt pxf
	public final static String SEND_WT_PXF_SUCCESS = "sendwtpxfsuccess";
	
	//enter forward
	public final static String ENTER_FORWARD_ON_HAND = "enterforwardonhand";

	public final static String ENTER_FORWARD_MESSAGE = "enterforwardmessage";

	//success on hand forward
	public final static String REQUEST_ON_HAND_SUCCESS = "requestonhandsuccess";

	public final static String CLOSE_ON_HAND_SUCCESS = "closeonhandsuccess";
	
	public final static String CONFIRM_CLOSE_ON_HAND_SUCCESS = "confirmcloseonhandsuccess";

	public final static String EXPENSE_PAYOUT_APPROVED_SUCCESS = "expensepayoutapprovedsuccess";

	public final static String EXPENSE_PAYOUT_DENIED_SUCCESS = "expensepayoutdeniedsuccess";

	//do a request on hand
	public final static String REQUEST_ON_HAND = "enterrequestonhand";

	public final static String CLAIMS_LIST = "claimslist";

	public final static String DENY_REQUEST_ON_HAND = "denyrequestonhand";

	public final static String DENY_REQUEST_ON_HAND_SUCCESS = "successdenyrequestonhand";

	//cannot allow the user to forward
	public final static String CANNOT_FORWARD_ON_HAND = "errorforwardonhand";

	//relative path to the images directory.
	public final static String IMAGE_PATH = "/deployment/main/images/";

	//lost-found
	public final static String SEARCH_LOST_FOUND = "searchlostfound";
	public final static String LOST_FOUND = "lostfound";
	public final static String LOST_FOUND_SUCCESS = "lostfound_success";
	
	public final static String UPDATE_LOST_FOUND_SUCCESS = "update_lostfound_success";
	
	public final static String AUTO_CHECKLIST_STEP_ONE = "auto_checklist_step_one";
	public final static String INCIDENT_CHECKLIST = "incident_checklist";
	public final static String AJAX_PUSH_TO_CRM = "ajax_push_to_crm";

	// customer view only
	public final static String PASS_VIEW_ONLY = "passview";
	
	// OSI
	public final static String FORWARD_OSI = "osi";
	public final static String FORWARD_CUSTOMER_COMMENTS = "customer_comments";
	public final static String PAX_COMMUNICATION = "pax_communication";
	public final static String LIST_NEW_PAX_COMMUNICATION ="listnewpaxcommunication";

	// bdo
	public final static String BDO_MAIN = "bdo";
	public final static String SEARCH_BDO_MAIN = "searchbdo";

	//ADMIN LINKS
	public final static String EDIT_GROUP = "editcomponent";
	public final static String EDIT_AGENT_GROUP = "editagentgroup";
	public final static String VIEW_GROUPS = "viewComponent";
	public final static String SAVE_GROUP_SUCCESS = "groupsuccess";

	public final static String EDIT_COMPANY = "editcompany";
	public final static String VIEW_COMPANIES = "viewcompanies";
	public final static String SAVE_COMPANY_SUCCESS = "companysuccess";

	public final static String EDIT_STATION = "editstation";
	public final static String EDIT_AGENT_STATION = "editagentstation";
	public final static String VIEW_STATIONS = "viewstations";
	public final static String SAVE_STATION_SUCCESS = "stationsuccess";

	public final static String EDIT_DELIVERY_COMPANY = "editdeliverycompany";
	public final static String EDIT_STATION_DELIVERY_COMPANY = "editstationdeliverycompany";
	public final static String VIEW_DELIVERY_COMPANIES = "viewdeliverycompanies";
	public final static String EDIT_SERVICE_LEVEL = "editservicelevels";
		
	public final static String EDIT_AIRPORT = "editairport";
	public final static String VIEW_AIRPORTS = "viewairports";

	public final static String EDIT_SHIFT = "editshift";
	public final static String VIEW_SHIFTS = "viewshifts";
	public final static String SAVE_SHIFT_SUCCESS = "shiftsuccess";

	public final static String EDIT_CODE = "editcode";
	public final static String VIEW_CODES = "viewcodes";
	public final static String SAVE_CODE_SUCCESS = "codesuccess";

	public final static String VIEW_COMPONENT = "viewSystemComponents";
	public final static String SAVE_COMPONENT_SUCCESS = "componentsuccess";

	public final static String EDIT_AGENT = "editagent";
	public final static String EDIT_SELF = "editself";
	public final static String VIEW_AGENTS = "viewagents";
	public final static String VIEW_LOGGED_AGENTS = "viewloggedagents";

	public final static String SAVE_AGENT_SUCCESS = "agentsuccess";
	public final static String SELF_UPDATE_SUCCESS = "selfupdatesuccess";

	public final static String VIEW_MESSAGE = "viewmessage";
	public final static String COMPOSE_MESSAGE = "composemessage";
	public final static String COMPOSE_MESSAGE_SUCCESS = "composemessagesuccess";
	public final static String DELETE_MESSAGE_SUCCESS = "deletemessagesuccess";
	public final static String MESSAGE_LIST = "viewmessages";

	public final static String INTERIM_EXPENSE_REQUEST = "interimexpenserequest";
	public final static String CREATED_INTERIM_EXPENSE_REQUEST = "createdinterimexpenserequest";

	public final static String AUDIT_ONHAND = "auditOnHand";
	public final static String AUDIT_ONHAND_DETAIL = "auditOnHandDetail";
	public final static String AUDIT_ONHAND_COMPARE_DETAIL = "auditOnHandCompareDetail";

	public final static String AUDIT_LOSTFOUND = "auditLostFound";
	public final static String AUDIT_LOSTFOUND_DETAIL = "auditLostFoundDetail";
	public final static String AUDIT_LOSTFOUND_COMPARE_DETAIL = "auditLostFoundCompareDetail";

	public final static String AUDIT_OPTIONS = "auditOptions";

	public final static String AUDIT_AGENT = "auditAgent";
	public final static String AUDIT_AGENT_DETAIL = "auditAgentDetail";
	public final static String AUDIT_AGENT_COMPARE_DETAIL = "auditAgentCompareDetail";

	public final static String AUDIT_GROUP = "auditGroup";
	public final static String AUDIT_GROUP_DETAIL = "auditGroupDetail";
	public final static String AUDIT_GROUP_COMPARE_DETAIL = "auditGroupCompareDetail";

	public final static String AUDIT_COMPANY = "auditCompany";
	public final static String AUDIT_COMPANY_DETAIL = "auditCompanyDetail";
	public final static String AUDIT_COMPANY_COMPARE_DETAIL = "auditCompanyCompareDetail";

	public final static String AUDIT_LOSSCODE = "auditLosscode";
	public final static String AUDIT_LOSSCODE_DETAIL = "auditLosscodeDetail";
	public final static String AUDIT_LOSSCODE_COMPARE_DETAIL = "auditLosscodeCompareDetail";

	public final static String AUDIT_SHIFT = "auditWorkshift";
	public final static String AUDIT_SHIFT_DETAIL = "auditWorkshiftDetail";
	public final static String AUDIT_SHIFT_COMPARE_DETAIL = "auditWorkshiftCompareDetail";

	public final static String AUDIT_AIRPORT = "auditAirport";
	public final static String AUDIT_AIRPORT_DETAIL = "auditAirportDetail";
	public final static String AUDIT_AIRPORT_COMPARE_DETAIL = "auditAirportCompareDetail";

	public final static String AUDIT_STATION = "auditStation";
	public final static String AUDIT_STATION_DETAIL = "auditStationDetail";
	public final static String AUDIT_STATION_COMPARE_DETAIL = "auditStationCompareDetail";

	public final static String AUDIT_MBR = "auditMBR";
	public final static String AUDIT_MBR_DETAIL = "auditMBRDetail";
	public final static String AUDIT_MBR_COMPARE_DETAIL = "auditMBRCompareDetail";

	// MATCHING CONSTANTS TO USE TO MAP TO RESOURCE FILE FOR DISPLAY
	//elements to match
	public final static String AUDIT_CLAIMS = "auditClaims";
	public final static String AUDIT_CLAIMS_DETAIL = "auditClaimsDetail";
	public final static String AUDIT_CLAIMS_COMPARE_DETAIL = "auditClaimsCompareDetail";

	// MATCHING CONSTANTS TO USE TO MAP TO RESOURCE FILE FOR DISPLAY
	// elements to match
	public final static String MATCH_CLAIMCHECK = "match.claimchecknum";
	public final static String MATCH_RECORDLOC = "match.recordlocator";
	public final static String MATCH_MEMBER = "match.membership";
	public final static String MATCH_EMAIL = "match.email";
	public final static String MATCH_ADDRESS = "match.address";
	public final static String MATCH_NAME = "match.name";
	public final static String MATCH_PHONE = "match.phone";

	public final static String MATCH_ITINERARY = "match.itinerary";

	public final static String MATCH_CONTENTS = "match.contents";

	public final static String MATCH_ONBAG_NAME = "match.name_on_bag";
	public final static String MATCH_MANUFACTURER = "match.manufacturer";
	public final static String MATCH_COLOR = "match.color";
	public final static String MATCH_COLOR_2 = "match.color.secondary";
	public final static String MATCH_COLOR_3 = "match.color.tertiary";
	public final static String MATCH_BAGTYPE = "match.bagtype";
	public final static String MATCH_BAGTYPE_2 = "match.bagtype.secondary";
	public final static String MATCH_BAGTYPE_3 = "match.bagtype.tertiary";
	public final static String MATCH_XDESC = "match.xdesc";

	public final static int PASSIVE_MATCH_TYPE = 0;
	public final static int ACTIVE_MATCH_TYPE = 1;

	public final static String BANNER_IMAGE = "header_bg_noconf.gif";
	public final static String LOGO_IMAGE = "poweredby_net_tracer.jpg";
	
	// AJAX DIVS
	public final static String AJAX_AGENTASSIGNED = "ajax_agentassigned";
	public final static String AJAX_FAULTSTATION = "ajax_faultstation";
	public final static String AJAX_FAULTSTATION_ALL = "ajax_faultstationall";
	public final static String AJAX_SERVICELEVEL = "ajax_servicelevel";
	public final static String AJAX_STATUSES = "ajax_statuses";
	
	// defaults
	public final static String US_COUNTRY_CODE = "US";
	//public final static String DEFAULT_AIRLINE = "FL";
	
	// update comment integration with airtran
	public final static String CMT_CREATE_INTERIM = "Expense requested, pending approval on ";
	public final static String CMT_CREATE_INTERIM_UNDERLIMIT = "Expense created, under limit auto approved on ";
	public final static String CMT_APPROVED_INTERIM = "Expense approved on ";
	public final static String CMT_DENIED_INTERIM = "Expense denied on ";
	public final static String CMT_CREATE_EXPENSE = "Baggage expense created on ";
	
	//suspend and RIT

	
	//wt ohd
	public final static int WTFWD_LOG_NOT_RECEIVED = 0;
	public final static int WTFWD_LOG_RECEIVED = 1;
	
	public final static int MOVETOLZ_MODE_ASSIGNMENT = 1;
	public final static int MOVETOLZ_MODE_PERCENTAGE = 2;
	public static final String NEW_DEFAULT_WT_URL="wtrweb.worldtracer.aero";
	
	public static final String DEFAULT_WT_URL = "www.worldtracer.aero";
	public static final String WT_PENDING_CREATE = "WT_PENDING_CREATE";
	public static final String WT_PENDING_AMEND = "WT_PENDING_AMEND";
	public static final String WT_PENDING_SUSPEND = "WT_PENDING_SUSPEND";
	public static final String WT_PENDING_REINSTATE = "WT_PENDING_REINSTATE";
	public static final String WT_PENDING_CLOSE = "WT_PENDING_CLOSE";
	public static final Object WT_PENDING_FOH = "WT_PENDING_FOH";
	public static final String INVALID_TOKEN = "invalidtoken";
	public static final String MANUAL_QUEUE = "manualQueue";
	public static final String ROWSPERPAGE_MAP = "rowsperpage_map";
	public static final String ROWS_ADMIN_PAGES = "rowsAdminPages";
	public static final String ROWS_AUDIT_PAGES = "rowsAuditPages";
	public static final String ROWS_SEARCH_PAGES = "rowsSearchPages";
	public static final String JSP_DELETE_ITINERARY = "itin";
	public static final String JSP_DELETE_CLAIMCHECK = "claimcheck";
	public static final String JSP_DELETE_ITEM = "item";
	public static final String JSP_DELETE_INVENTORY = "inventory";
	public static final String JSP_DELETE_PAX = "pax";
	public static final String JSP_DELETE_ARTICLE = "article";
	public static final String WEB_SERVICE_DTO = "web.service.dto";
	public static final String SYSTEM_COMPONENT_NAME_CLERICAL_CLAIMS_FEATURES = "Clerical Claims Features";
	public static final String SYSTEM_COMPONENT_NAME_CENTRAL_BAGGAGE_CLAIMS_FEATURES = "Central Baggage Claims Features";

	
	// dispute resolution page
	public final static String DISPUTE_RESOLUTION = "dispute_resolution";
	public static final String DISPUTE_RESOLUTION_READ_ONLY = "dispute_resolution_ro";
	public static final String DISPUTE_RESOLUTION_INSERT_NEW_SUCCESS = "insert_dispute_success";
	public static final String DISPUTE_RESOLUTION_UPDATE_SUCCESS = "update_dispute_success";
	
	public final static String VIEW_DISPUTES = "view_disputes";
	public final static String VIEW_ONLY_DISPUTE = "view_only_dispute";
	
	public final static String MANAGE_DISPUTE = "manage_dispute";
	
	public static final String WT_AFC_DEFAULT_SEQ = "";
	
	public static final String NON_REVENUE_CODES_KEY = "non.revenue.codes";
	
	// MJS: salvage constants
	public static final int SALVAGE_DEFAULT = 0;
	public static final int SALVAGE_LOW_VALUE = 1;
	public static final int SALVAGE_HIGH_VALUE = 2;
	
	public static final int SALVAGE_OPEN = 0;
	public static final int SALVAGE_CLOSED = 1;
	public static final int SALVAGE_ALL = 2;
	
	public static final int SALVAGE_HEADER_DEFAULT = 0;
	public static final int SALVAGE_HEADER_BOX = 1;
	public static final int SALVAGE_HEADER_OHD = 2;
	public static final int SALVAGE_HEADER_SUMMARY = 3;
	
	public static final String SALVAGE_SEARCH = "salvage_search";
	public static final String SALVAGE_EDIT = "salvage_edit";
	
	public static final String JSP_DELETE_SALVAGE_BOX = "delSalvageBox";
	public static final String JSP_DELETE_SALVAGE_ITEM = "delSalvageItem";
	public static final String JSP_DELETE_SALVAGE_OHD= "delSalvageOhd";
	
	public final static String CLAIM_CREATE_NEW = "create_claim";
	public final static String CLAIM_FRAUD_RESULTS = "fraud_results";
	public final static String CLAIM_MATCH_DETAILS = "match_details";
	public final static String CLAIM_SEARCH = "search_claim";
	public final static String CLAIM_REQUEST_INFO = "request_info";
	public static final String SYSTEM_COMPONENT_NAME_FRAUD_REQUESTS = "Fraud Requests";
	public static final String JSP_DELETE_ASSOCIATED_NAME = "delName";
	public static final String JSP_DELETE_ASSOCIATED_RECEIPT = "delReceipt";
	
	public static final String LF_CREATE_LOST_REPORT = "create_lost_report";
	public static final String LF_CREATE_FOUND_ITEM = "create_found_item";
	public static final String LF_SEARCH_LOST_FOUND = "search_lost_found";
	public static final String LF_CREATE_DELIVERY = "create_delivery";
	
	public static String getDBDateFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.SQLServerDialect")) {
			return DB_DATEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.MySQLDialect")) {
			return DB_DATEFORMAT;
		} else {
			return DB_DATEFORMAT;
		}
	}

	public static String getDBTimeFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.SQLServerDialect")) {
			return DB_TIMEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.MySQLDialect")) {
			return DB_TIMEFORMAT;
		} else {
			return DB_TIMEFORMAT;
		}
	}
	public static String getDBDateTimeFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.SQLServerDialect")) {
			return DB_DATETIMEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("org.hibernate.dialect.MySQLDialect")) {
			return DB_DATETIMEFORMAT;
		} else {
			return DB_DATETIMEFORMAT;
		}
	}
}
