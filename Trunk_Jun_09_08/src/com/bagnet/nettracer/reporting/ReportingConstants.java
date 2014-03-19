/*
 * Created on Aug 13, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.reporting;

/**
 * @author Administrator
 * 
 * create date - Aug 13, 2004
 */
public class ReportingConstants {
	
	public static String CLASS_PATH = "jasper.reports.compile.class.path";
	public static String JASPER_JAR = "/WEB-INF/lib/jasperreports-0.6.5.jar";
	public static String IREPORT_JAR = "/WEB-INF/lib/v00-iReport.jar";
	public static String COMPILE = "jasper.reports.compile.temp";
	public static String REPORT_TMP_PATH = "/reports/tmp/";
	// reports
	public final static int CLAIM_PAYOUT_RPT = 1;
	public final static int CLAIM_PRORATE_RPT = 2;

	public final static int RPT_3 = 3; // mbr listing and number of mbrs
	public final static String RPT_3_NAME = "stat_rpt_3";

	public final static int RPT_4 = 4; // mbrs per 1000 passengers
	public final static String RPT_4_NAME = "stat_rpt_4";

	public final static int RPT_5 = 5; // top # flights generated most mbrs
	public final static String RPT_5_NAME = "stat_rpt_5";

	public final static int RPT_6 = 6; // how much money paid out for different
	public final static String RPT_6_NAME = "stat_rpt_6";

	public final static int RPT_7 = 7; // city pair mbr reports
	public final static String RPT_7_NAME = "stat_rpt_7";

	public final static int RPT_8 = 8; // recovery summary
	public final static String RPT_8_NAME = "stat_rpt_8";

	public final static int RPT_9 = 9; // recovery summary for closed report
	public final static String RPT_9_NAME = "stat_rpt_9";
	
	public final static int RPT_10 = 10; // onhand report
	public final static String RPT_10_NAME = "stat_rpt_10";
	
	public static final int RPT_11 = 11; //Depreciation Summary
	public final static String RPT_11_NAME = "depreciation_summary";
	
	public final static int LOST_RECEIPT_RPT = 15;
	public final static int BDO_RECEIPT_RPT = 16;
	public final static int DAMAGE_RECEPIT_RPT = 17;
	public final static int MISSING_RECEPIT_RPT = 18;
	public static final int PPLC_RPT = 19;
	public static final int DEPREC_SUMMARY = 20;
	public static final int CRAP_SHEET = 21; //For NT-751, Not NT-772
	public static final int EXPENSE_LUV_VOUCHER = 22; 
	
	// report titles
	public static String CLAIM_PAYOUT_TITLE = "Claim Payout";
	public static String PRORATE_NOTICE_TITLE = "Prorate Notice";
	public static String LOST_DELAY_RECEIPT = "Customer Receipt";

	// Custom reports for airtran
	public final static int RPT_20 = 20;
	public final static String RPT_20_CUSTOM_1_NAME = "stat_rpt_20_custom_1";
	public final static String RPT_20_CUSTOM_501_NAME = "stat_rpt_20_custom_501";
	public final static String RPT_20_CUSTOM_11_NAME = "stat_rpt_20_custom_11";
	public final static int RPT_20_CUSTOM_1 = 1; // AirTran, mishandled bags summary by location
	
	//Incoming Incident Report
	public final static int RPT_INCOMING_INCIDENT = 21;
	public final static String RPT_INCOMING_INCIDENT_NAME = "incoming_incident_report";
	
	public final static int RPT_20_CUSTOM_2 = 2;
	public final static int RPT_20_CUSTOM_501 = 501;
	

	//Payment Approval Report
	public final static int RPT_PAYMENT_APPROVAL = 22;
	public final static String RPT_PAYMENT_APPROVAL_NAME = "payment_approval_report";
	
	//custom reports for in-bound expedite baggage list
	public final static int RPT_INBOUND_EXPEDITE_BAGS = 30;
	public final static String RPT_INBOUND_EXPEDITE_BAGS_NAME = "stat_rpt_30";
	
	public final static int SEARCH_INCIDENT_RPT = 25;
	public final static String SEARCH_INCIDENT_RPT_NAME = "search_incident";
	public final static int SEARCH_OHD_RPT = 26;
	public final static String SEARCH_ONHAND_RPT_NAME = "search_onhand";
	public final static String STATION_ONHAND_RPT_NAME = "station_onhand";	
	public final static int SEARCH_SCANDATA_RPT = 27;
	public final static String SEARCH_SCANDATA_RPT_NAME = "search_scandata";
	public final static String SEARCH_FOUNDITEM_RPT_NAME = "search_founditem";
	public static final int RPT_20_CUSTOM_3 = 3;
	public static final int RPT_20_CUSTOM_4 = 4;
	public static final int RPT_20_CUSTOM_5 = 5;
	public static final int RPT_20_CUSTOM_6 = 6;
	
	public static final int RPT_20_CUSTOM_77 = 77;
	public static final int RPT_20_CUSTOM_55 = 55;
	public static final int RPT_20_CUSTOM_101 = 101;
	public static final int RPT_20_CUSTOM_81 = 81;
	public static final int RPT_20_CUSTOM_82 = 82;
	public static final int RPT_20_CUSTOM_83 = 83;
	public static final int RPT_20_CUSTOM_84 = 84;
	public static final int RPT_20_CUSTOM_85 = 85;
	public static final int RPT_20_CUSTOM_97 = 97;
	public static final int RPT_20_CUSTOM_210 = 210;
	
	public static final String RPT_20_CUSTOM_81_NAME = "loss_report";
	public static final String RPT_20_CUSTOM_82_NAME = "pilferage_report";
	public static final String RPT_20_CUSTOM_83_NAME = "dpr_report";
	public static final String RPT_20_CUSTOM_84_NAME = "oa_pirs";
	public static final String RPT_20_CUSTOM_85_NAME = "delayed_summary";
	public static final String RPT_20_CUSTOM_97_NAME = "child_restraint";
	public static final String RPT_20_CUSTOM_210_NAME = "dispute_count";
	
	public final static String RPT_55_NAME = "stat_rpt_55";
	public final static String RPT_101_NAME = "stat_rpt_101";
	
	public static final int RPT_20_CUSTOM_MAX_ALLOWED_ROWS = 20000;
	
	public static final String SALVAGE_REPORT_NAME = "salvage_report";
	public static final String LOST_FOUND_REPORT_NAME = "lost_and_found_report";
	
	
	public static final String DATETIME_FORMAT = "MMddyyyyhhmmss";
	public static final String WRITTEN_DATE_FORMAT = "MMMMM dd, yyyy";
	
	public static final String EXCEL_FILE_TYPE = ".xls";
	
	public static final int RPT_20_CUSTOM_78 = 78;
	public static final int RPT_20_CUSTOM_86 = 86;
	public static final String RPT_20_CUSTOM_86_NAME = "lost_and_found_log_report";
	
	public static final int RPT_20_CUSTOM_87 = 87;
	public static final String RPT_20_CUSTOM_87_LOST_NAME = "lost_item_recovery_report";
	public static final String RPT_20_CUSTOM_87_FOUND_NAME = "found_item_recovery_report";

	public static final int RPT_20_CUSTOM_88 = 88;
	public static final String RPT_20_CUSTOM_88_NAME = "management_summary_report";
	
	// Custom reports for LFC
	public static final int RPT_20_CUSTOM_89 = 89;
	public static final String RPT_20_CUSTOM_89_NAME = "daily_status_report";
	
	public static final int RPT_20_CUSTOM_90 = 90;
	public static final String RPT_20_CUSTOM_90_NAME = "itemization_report";
	
	public static final int RPT_20_CUSTOM_91 = 91;
	public static final String RPT_20_CUSTOM_91_NAME = "summary_report";
	
	public static final int RPT_20_CUSTOM_92 = 92;
	public static final String RPT_20_CUSTOM_92_NAME = "disbursements";

	public static final int RPT_20_CUSTOM_93 = 93;
	public static final String RPT_20_CUSTOM_93_NAME = "salvage_report";

	public static final int RPT_20_CUSTOM_94 = 94;
	public static final String RPT_20_CUSTOM_94_NAME = "lost_itemization_report";
	
	public static final int RPT_20_CUSTOM_95 = 95;
	public static final String RPT_20_CUSTOM_95_NAME = "ron_kit_issuance_summary";
	
	public static final int RPT_20_CUSTOM_96 = 96;
	public static final String RPT_20_CUSTOM_96_NAME = "replacement_baggage_summary";
	
	public static final int RPT_20_CUSTOM_201 = 201;
	public static final String RPT_20_CUSTOM_201_NAME = "fraud_valuation_summary";
	
	public static final int RPT_20_CUSTOM_202 = 202;
	public static final String RPT_20_CUSTOM_202_NAME = "wh_daily_status_report";
}