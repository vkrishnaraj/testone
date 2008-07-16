/*
 * Created on Nov 8, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.cfg.Configuration;

import com.bagnet.nettracer.cronjob.archive.DataToBakThread;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import org.hibernate.Session; 
/**
 * @author Administrator
 * 
 * create date - Nov 8, 2004
 */
public class NettracerCron {
	private static Configuration cfg = new Configuration();
	private static String hibernate_main_path = HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath();
	public static void main(String[] args) {
/*
		if (args.length <= 0)
			return;
		String path = args[0];
		String company = "";
		if (args.length >= 2)
			company = args[1];

		File lf = new File(path + "log4j.properties");
		if (lf.exists())
			PropertyConfigurator.configure(path + "log4j.properties");
*/
		//MoveToLZThread mbrthread = new MoveToLZThread(path, MoveToLZThread.MBR,company);
		//mbrthread.start();
		//MoveToLZThread ohdthread = new MoveToLZThread(path, MoveToLZThread.OHD,company);
		//ohdthread.start();
        //HibernateCronWrapper.runCron();
	

		HibernateWrapper Hav = new HibernateWrapper();
		cfg.configure(new File(hibernate_main_path)).buildSessionFactory();
        NettracerCron.runCron(cfg.getProperties());

		//WorldTracerUtils.connectWT(WorldTracerUtils.getWt_suffix_airline("DA") + "/", "DA");
		//System.out.println(WorldTracerUtils.getWt_suffix_airline("DA"));
	}


	/*** called by hibernate starter **/
	public static void runCron(Properties properties) {

		
		//DataToBakThread ntarchive = new DataToBakThread(properties);
		//ntarchive.run();
		
		MoveToLZThread mbrthread = new MoveToLZThread(properties, MoveToLZThread.MBR);
		mbrthread.start();
		
		Company_Specific_Variable csv = AdminUtils.getCompVariable(properties.getProperty("company.code"));
		if ((csv != null) && (csv.getWt_enabled() == 1)) {
			RetrieveWTActionFiles rwtthread = new RetrieveWTActionFiles(properties);
			rwtthread.start();
			
			//MoveToWTThread wtthread = new MoveToWTThread(properties);
			//wtthread.start();
			
			//WorldTracerActionQueue wtactionqueue = new WorldTracerActionQueue(properties);
			//wtactionqueue.start();
		}
		else {
			System.out.println("no worldtracer");
		}
//	 move mbr to lz thread
//		
		//MoveToLZThread ohdthread = new MoveToLZThread(properties, MoveToLZThread.OHD,company);
		//ohdthread.start();
		//RetrieveWTActionFiles rwtthread = new RetrieveWTActionFiles(properties);
		//rwtthread.start();
		
//		MoveToWTThread wtthread = new MoveToWTThread(properties);
//		wtthread.start();
		
		//WorldTracerActionQueue wtactionqueue = new WorldTracerActionQueue(properties);
		//wtactionqueue.start();
	}

}