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
	public final static int LOST_RECEPIT_RPT = 15;
	public final static int DAMAGE_RECEPIT_RPT = 17;
	public final static int MISSING_RECEPIT_RPT = 18;
	public final static int BDO_RECEIPT_RPT = 16;

	public final static int RPT_3 = 3; // mbr listing and number of mbrs
	public final static String RPT_3_NAME = "stat_rpt_3";


	public final static int RPT_4 = 4; // mbrs per 1000 passengers
	public final static String RPT_4_NAME = "stat_rpt_4";


	public final static int RPT_5 = 5; // top # flights generated most mbrs
	public final static String RPT_5_NAME = "stat_rpt_5";


	public final static int RPT_6 = 6; // how much money paid out for different
	// paytypes
	public final static String RPT_6_NAME = "stat_rpt_6";


	public final static int RPT_7 = 7; // city pair mbr reports
	public final static String RPT_7_NAME = "stat_rpt_7";


	public final static int RPT_8 = 8; // recovery summary
	public final static String RPT_8_NAME = "stat_rpt_8";


	public final static int RPT_9 = 9; // recovery summary for closed report
	public final static String RPT_9_NAME = "stat_rpt_9";
	
	public final static int RPT_10 = 10; // onhand report
	public final static String RPT_10_NAME = "stat_rpt_10";

	// report titles
	public static String CLAIM_PAYOUT_TITLE = "Claim Payout";
	public static String PRORATE_NOTICE_TITLE = "Prorate Notice";
	public static String LOST_DELAY_RECEIPT = "Customer Receipt";

	// custome reports for airtran
	public final static int RPT_20 = 20;
	public final static int RPT_20_CUSTOM_1 = 1; // mishandled bags summary by location
	public final static String RPT_20_CUSTOM_1_NAME = "stat_rpt_20_custom_1";
	public final static String RPT_20_CUSTOM_1_TITLE = "Mishandled Bags Summary By Location";
	
}