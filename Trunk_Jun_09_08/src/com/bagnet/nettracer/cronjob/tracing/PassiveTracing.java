/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.tracing;

import java.io.File;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import com.bagnet.nettracer.tracing.db.Agent;

/**
 * @author Administrator
 * 
 * args: directory path to the configuration file (tracing.cfg.properties)
 * steps:
 * 
 * 1. find incident order by lastupdated and not closed 2. look at the
 * ohd_lasttraced column to start tracing ohd.lastupdated > ohd_lasttraced and
 * ohd.status <>closed ...
 * 
 * create date - Sep 14, 2004
 */
public class PassiveTracing {
	public static void main(String[] args) {
		if (args.length <= 0) return;
		String path = args[0];
		String company = null;
		if (args.length >= 2) company = args[1];
		if (company == null) {
			System.out.println("please pass in company");
			return;
		}
		company = company.trim().toUpperCase();
		File lf = new File(path + "log4j.properties");
		if (lf.exists()) PropertyConfigurator.configure(path + "log4j.properties");

		// start main thread to make sure there are enough sub threads running
		MainMBRThread mt = new MainMBRThread(path, company, null, 0, null, null, null, false);
		mt.start();
	}

	public PassiveTracing() {}
	
	public PassiveTracing(String configPath, String company) {
		MainMBRThread mt = new MainMBRThread(configPath, company, null, 0, null, null, null, false);
		mt.start();
	}

	/**
	 * active tracing method call
	 * 
	 * @param incident_ID
	 * @param path
	 */
	public void trace(String incident_ID, int scope, Date sdate, Date edate, Agent user, String path) {
		MainMBRThread mt = new MainMBRThread(path, user.getStation().getCompany().getCompanyCode_ID(),
				incident_ID, scope, sdate, edate, user, true);
		mt.start();
	}
}