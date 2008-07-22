package com.bagnet.nettracer.tracing.constant;

import java.text.DecimalFormat;
import java.util.Properties;

/**
 * Contains all the application constants.
 * 
 * @author Ankur Gupta
 */
public class TracingConstants {

	public enum ActiveStatus { ALL, ACTIVE, INACTIVE }
	
	public final static String MAINTAIN_AGENTS_TYPE_AGENTS = "0";
	public final static String MAINTAIN_AGENTS_TYPE_WEBSRVICE = "1";
		
	public final static String OWENS_GROUP = "OW";
	public final static boolean LIMIT_FAULT_AIRLINE = true;
	
	//System components
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
	public final static String SYSTEM_COMPONENT_NAME_CLAIM_RESOLUTION = "Claim Resolution";
	public final static String SYSTEM_COMPONENT_NAME_CLAIM_PRORATE = "Claim Prorate";
	public final static String SYSTEM_COMPONENT_NAME_CBRO_VIEW = "CBRO View";
	public final static String SYSTEM_COMPONENT_NAME_BY_PASSENGER_BOARDINGS = "By Passenger Boardings";
	public final static String SYSTEM_COMPONENT_NAME_CUSTOM_REPORTS = "Custom Reports";
	public final static String SYSTEM_COMPONENT_NAME_BAGS_TO_BE_DELIVERED = "Bags To Be Delivered";
	public final static String SYSTEM_COMPONENT_NAME_ANALYTICAL_REPORTS = "Analytical Reports";
	public final static String SYSTEM_COMPONENT_NAME_ADMINISTRATION = "Administration";
	public final static String SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG = "Add On-hand Bag";
	public final static String SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES = "Add Missing Articles";
	public final static String SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG = "Add Mishandled Bag";
	public final static String SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG = "Add Damaged Bag";
	public final static String SYSTEM_COMPONENT_NAME_ACTIVE_TRACING = "Active Tracing";
	
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER = "WorldTracer";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_ACTION_FILES = "WorldTracer Action Files";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD = "WorldTracer FWD";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH = "WorldTracer ROH";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_TTY = "WorldTracer TTY";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT = "WorldTracer Insert Incident";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_OHD = "WorldTracer Insert OHD";
	public final static String SYSTEM_COMPONENT_NAME_WORLD_TRACER_BDO = "WorldTracer Insert BDO";
	
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
	// itinerary types
	public final static int PASSENGER_ROUTING = 0;
	public final static int BAGGAGE_ROUTING = 1;
	// claim payment types
	public final static int CHECK_PAYOUT = 1;
	public final static int VOUCHER_PAYOUT = 2;
	public final static int MILEAGE_PAYOUT = 3;

	// leg types
	public final static int LEG_B_STATION = 1; // orinating station
	public final static int LEG_T_STATION = 2; // transfer station
	public final static int LEG_E_STATION = 3; // terminating station

	// interim expensepayout
	public final static int EXPENSEPAYOUT_INTERIM = 3;

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

	public final static int OHD_REQUEST_STATUS_DENIED = 8;
	public final static int OHD_REQUEST_STATUS_FORWARDED = 9;
	public final static int OHD_STATUS_PROCESSFORDELIVERY = 51;
	
	public final static int LOG_NOT_RECEIVED = 0;
	public final static int LOG_RECEIVED = 1;

	public final static int TASK_STATUS_NOT_STARTED = 16;
	public final static int TASK_STATUS_IN_PROGRESS = 17;
	public final static int TASK_STATUS_COMPLETED = 18;
	public final static int TASK_STATUS_DEFERRED = 19;
	public final static int TASK_STATUS_WAITING = 20;
	public final static int TASK_STATUS_DELETED = 28;
	public final static int TASK_STATUS_NOT_COMPLETED = 79;

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
	
	// item xdesc element type x
	public final static int XDESC_TYPE_X = 7;

	// remark types
	public final static int REMARK_REGULAR = 1;
	public final static int REMARK_CLOSING = 2;

	// report output type
	public final static int REPORT_OUTPUT_PDF = 0;
	public final static int REPORT_OUTPUT_HTML = 1;
	public final static int REPORT_OUTPUT_XLS = 2;
	public final static int REPORT_OUTPUT_CSV = 3;
	public final static int REPORT_OUTPUT_XML = 4;

	public final static String COMPANY_PAGESTATE_INFO = "1";
	public final static String COMPANY_PAGESTATE_SETTINGS = "2";
	public final static String COMPANY_PAGESTATE_SECURITY = "3";
	public final static String COMPANY_PAGESTATE_AUDITING = "4";
	public final static String COMPANY_PAGESTATE_WEB_SERVICES = "5";
	public final static String COMPANY_PAGESTATE_WORLDTRACER = "6";
	public final static String COMPANY_PAGESTATE_MOVETOLZ = "7";
	
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

	// activetracing
	public final static String ACTIVE_TRACING = "activetracing";

	// search
	public final static String DELIVERY_LIST = "deliveryList";

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
	// missing articles close page
	public final static String MISSING_CLOSE = "maclose";
	// damaged bag close page
	public final static String DAMAGED_CLOSE = "damagedclose";

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
	
	// world tracer forwards
	public final static String VIEW_WORLDTRACER_ACTION_FILES = "viewworldtraceractionfiles";
	public final static String VIEW_WORLDTRACER_AF_VIEW_RAW_INC = "viewworldtracerafviewraw";
	public final static String VIEW_WORLDTRACER_FWD = "viewworldtracerfwd";
	public final static String VIEW_WORLDTRACER_ROH = "viewworldtracerroh";
	public final static String VIEW_WORLDTRACER_TTY = "viewworldtracertty";
	//success on inserting on hand
	public final static String INSERT_ON_HAND_SUCCESS = "insertonhandsuccess";
	public final static String MASS_ON_HAND_SUCCESS = "massonhandsuccess";

	//success on forward
	public final static String FORWARD_ON_HAND_SUCCESS = "forwardonhandsuccess";

	public final static String FORWARD_ERROR = "forwarderror";
	//success wt bag forward
	public final static String FORWARD_WT_BAG_SUCCESS = "forwardwtbagsuccess";

	//success wt tty
	public final static String SEND_WT_TTY_SUCCESS = "sendwtttysuccess";
	
	//success wt roh
	public final static String SEND_WT_ROH_SUCCESS = "sendwtrohsuccess";
	//enter forward
	public final static String ENTER_FORWARD_ON_HAND = "enterforwardonhand";

	public final static String ENTER_FORWARD_MESSAGE = "enterforwardmessage";

	//success on hand forward
	public final static String REQUEST_ON_HAND_SUCCESS = "requestonhandsuccess";

	public final static String CLOSE_ON_HAND_SUCCESS = "closeonhandsuccess";

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
	public final static String IMAGE_PATH = "/tracer/deployment/main/images/";

	//lost-found
	public final static String SEARCH_LOST_FOUND = "searchlostfound";
	public final static String LOST_FOUND = "lostfound";
	public final static String LOST_FOUND_SUCCESS = "lostfound_success";

	// customer view only
	public final static String PASS_VIEW_ONLY = "passview";

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
	public final static String AJAX_SERVICELEVEL = "ajax_servicelevel";
	public final static String AJAX_STATUSES = "ajax_statuses";
	
	// defaults
	public final static String DEFAULT_COUNTRY = "US";
	//public final static String DEFAULT_AIRLINE = "FL";
	
	// update comment integration with airtran
	public final static String CMT_CREATE_INTERIM = "Interim expense requested, pending approval on ";
	public final static String CMT_CREATE_INTERIM_UNDERLIMIT = "Interim expense created, under limit auto approved on ";
	public final static String CMT_APPROVED_INTERIM = "Interim expense approved on ";
	public final static String CMT_DENIED_INTERIM = "Interim expense denied on ";
	public final static String CMT_CREATE_EXPENSE = "Baggage expense created on ";
	
	//wt ohd
	public final static int WTFWD_LOG_NOT_RECEIVED = 0;
	public final static int WTFWD_LOG_RECEIVED = 1;
	
	public final static int MOVETOLZ_MODE_ASSIGNMENT = 1;
	public final static int MOVETOLZ_MODE_PERCENTAGE = 2;
	
	
	public static String getDBDateFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.SQLServerDialect")) {
			return DB_DATEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.MySQLDialect")) {
			return DB_DATEFORMAT;
		} else {
			return DB_DATEFORMAT;
		}
	}

	public static String getDBTimeFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.SQLServerDialect")) {
			return DB_TIMEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.MySQLDialect")) {
			return DB_TIMEFORMAT;
		} else {
			return DB_TIMEFORMAT;
		}
	}
	public static String getDBDateTimeFormat(Properties properties) {
		if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.SQLServerDialect")) {
			return DB_DATETIMEFORMAT_MSSQL;
		} else if (properties.getProperty("hibernate.dialect").equals("net.sf.hibernate.dialect.MySQLDialect")) {
			return DB_DATETIMEFORMAT;
		} else {
			return DB_DATETIMEFORMAT;
		}
	}
}