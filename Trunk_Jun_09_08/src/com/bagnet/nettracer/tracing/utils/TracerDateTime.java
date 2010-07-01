/*
 * Created on Sep 27, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author Administrator
 * 
 * create date - Sep 27, 2004
 */
public class TracerDateTime {
	private static Logger logger = Logger.getLogger(TracerDateTime.class);

	public static Date getGMTDate() {
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT);
			df.setTimeZone(TimeZone.getTimeZone("GMT"));

			//System.out.println("now: " + df.format(now));
			Date newdate = DateUtils.convertToDate(df.format(now), TracingConstants.DB_DATETIMEFORMAT,
					null);

			//TimeZone tz = TimeZone.getTimeZone("US/Eastern");
			//long difference = tz.getOffset(newdate.getTime());
			//Date d = new Date(newdate.getTime() + difference);

			return newdate;
		} catch (Exception e) {
			return new Date();
		}

	}

	public static void main(String args[]) {

		Date now = getGMTDate();
    // System.out.println(now);
		//getAllTimeZones();

	}

	public static ArrayList getAllTimeZones() {
		String[] timeZoneIds = TimeZone.getAvailableIDs();

		for (int i = 0; i < timeZoneIds.length; i++) {
			TimeZone tz = TimeZone.getTimeZone(timeZoneIds[i]);
			String zone = tz.getDisplayName(true, TimeZone.SHORT);
			String zoneName = tz.getDisplayName(true, TimeZone.LONG);

			System.out.println(zone + " \t \t \t" + zoneName + "  (" + tz.getID() + ")");

		}
		return null;
	}
	
	public static int getHourDiff(TimeZone tz) {
		long difference = tz.getOffset((new Date()).getTime());
		int value = (int)(difference / 1000 / 3600);
		return 0-value;
	}
	
	public static Date getGMTDateDay() {
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			Date newdate = DateUtils.convertToDate(df.format(now), TracingConstants.DB_DATEFORMAT, null);

			return newdate;
		} catch (Exception e) {
			return new Date();
		}
	}

	public static Date getGMTDateTime() {
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat(TracingConstants.DB_TIMEFORMAT);
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			Date newdate = DateUtils.convertToDate(df.format(now), TracingConstants.DB_TIMEFORMAT, null);

			return newdate;
		} catch (Exception e) {
			return new Date();
		}
	}
	
	public static String getGMTDateDayString() {
		String newdate = "";
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat("EEE MMM dd");
			//df.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			newdate = df.format(now);
		} catch (Exception e) {
			logger.error("Exception with date : " + e.getMessage());
		}
		return newdate;
	}

	public static String getGMTDateTimeString() {
		String newdate = "";
		try {
			Date now = new Date(); // current system date
			DateFormat df = new SimpleDateFormat("HH:mm:ss zzz yyyy");
			//df.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			newdate = df.format(now);
		} catch (Exception e) {
			logger.error("Exception with date : " + e.getMessage());
		}
		return newdate;
	}
}