/*
 * Created on Jul 27, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.hibernate;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 * create date - Jul 27, 2004
 */
public class HibernateOHDWrapper {
	private static Logger logger = Logger.getLogger(HibernateOHDWrapper.class);
	private static Configuration cfg = new Configuration();
	private static SessionFactory sf;
	private static String hibernate_ohd_path = HibernateOHDWrapper.class.getResource(
			"/hibernate_ohd.cfg.xml").getPath();
	static {
		try {

			sf = cfg.configure(new File(hibernate_ohd_path)).buildSessionFactory();
		} catch (Exception e) {
			logger.fatal("Unable to initiate hibernate: " + e);
		}
	}

	public static SessionFactory getSession() {
		try {

			if (sf == null) sf = cfg.configure(new File(hibernate_ohd_path)).buildSessionFactory();
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
		}
		return sf;
	}
}