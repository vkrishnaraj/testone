/*
 * Created on Nov 8, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import java.io.File;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

/**
 * @author Administrator
 * 
 * create date - Nov 8, 2004
 */
public class NettracerCron {
	private static Configuration cfg = new Configuration();
	private static String hibernate_main_path = HibernateWrapper.class
			.getResource("/hibernate_main.cfg.xml").getPath();
    private static String company;
	public static void main(String[] args) {
		cfg.configure(new File(hibernate_main_path)).buildSessionFactory();
        company = cfg.getProperties().getProperty("company.code");
		HibernateWrapper Hav = new HibernateWrapper();
		
		Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
		
		if ((csv.getWt_enabled() == 1)) {
			NettracerCron.runCron(cfg.getProperties());
		}
	}

	/** * called by hibernate starter * */
	public static void runCron(Properties properties) {

		// Archiving Thread
		//DataToBakThread ntarchive = new DataToBakThread(properties);
		//ntarchive.run();

		// Move to LZ Thread
		MoveToLZThread mbrthread = new MoveToLZThread(properties, MoveToLZThread.MBR);
		mbrthread.start();

		// RetrieveWTActionFiles rwtthread = new RetrieveWTActionFiles(properties);
		// rwtthread.start();

		// MoveToWTThread wtthread = new MoveToWTThread(properties);
		// wtthread.start();

		// WorldTracerActionQueue wtactionqueue = new WorldTracerActionQueue(properties);
		// wtactionqueue.start();

	}
}