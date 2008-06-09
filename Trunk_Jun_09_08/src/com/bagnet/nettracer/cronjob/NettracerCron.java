/*
 * Created on Nov 8, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Administrator
 * 
 * create date - Nov 8, 2004
 */
public class NettracerCron {
	public static void main(String[] args) {

		if (args.length <= 0)
			return;
		String path = args[0];
		String company = "";
		if (args.length >= 2)
			company = args[1];

		File lf = new File(path + "log4j.properties");
		if (lf.exists())
			PropertyConfigurator.configure(path + "log4j.properties");

		//MoveToLZThread mbrthread = new MoveToLZThread(path, MoveToLZThread.MBR,company);
		//mbrthread.start();
		//MoveToLZThread ohdthread = new MoveToLZThread(path, MoveToLZThread.OHD,company);
		//ohdthread.start();

	}
	
	public void runCron(String path, String company) {
//	 move mbr to lz thread
		MoveToLZThread mbrthread = new MoveToLZThread(path, MoveToLZThread.MBR,company);
		mbrthread.start();

		//RetrieveWTActionFiles rwtthread = new RetrieveWTActionFiles(path,company);
		//rwtthread.start();
	}

	/*** called by hibernate starter **/
	public void runCron(Properties properties) {
//	 move mbr to lz thread
		MoveToLZThread mbrthread = new MoveToLZThread(properties, MoveToLZThread.MBR);
		mbrthread.start();
		//MoveToLZThread ohdthread = new MoveToLZThread(properties, MoveToLZThread.OHD,company);
		//ohdthread.start();
		//RetrieveWTActionFiles rwtthread = new RetrieveWTActionFiles(properties);
		//rwtthread.start();
		
		//MoveToWTThread wtthread = new MoveToWTThread(properties);
		//wtthread.start();
	}
}