package com.bagnet.nettracer.hibernate;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routine to obtain hibernate sessions.
 */
public class FraudWrapper {

	private static final String HIBERNATE_AZUL_CFG_XML = "/hibernate_azul.cfg.xml";
	private static final String HIBERNATE_WESTJET_CFG_XML = "/hibernate_westjet.cfg.xml";
	private static final String HIBERNATE_USAIR_CFG_XML = "/hibernate_usair.cfg.xml";
	private static final String HIBERNATE_AIRTRAN_CFG_XML = "/hibernate_airtran.cfg.xml";
	private static final String HIBERNATE_SPIRIT_CFG_XML = "/hibernate_spirit.cfg.xml";
	private static final String HIBERNATE_JETBLUE_CFG_XML = "/hibernate_jetblue.cfg.xml";
	private static final String HIBERNATE_FRAUD_CFG_XML = "/hibernate_fraud.cfg.xml";

	
	private static String hibernate_fraud_path = FraudWrapper.class.getResource(HIBERNATE_FRAUD_CFG_XML).getPath();
	private static String hibernate_jetblue_path = FraudWrapper.class.getResource(HIBERNATE_JETBLUE_CFG_XML).getPath();
	private static String hibernate_spirit_path = FraudWrapper.class.getResource(HIBERNATE_SPIRIT_CFG_XML).getPath();
	private static String hibernate_airtran_path = FraudWrapper.class.getResource(HIBERNATE_AIRTRAN_CFG_XML).getPath();
	private static String hibernate_usair_path = FraudWrapper.class.getResource(HIBERNATE_USAIR_CFG_XML).getPath();
	private static String hibernate_westjet_path = FraudWrapper.class.getResource(HIBERNATE_WESTJET_CFG_XML).getPath();
	private static String hibernate_azul_path = FraudWrapper.class.getResource(HIBERNATE_AZUL_CFG_XML).getPath();

	private static Logger logger = Logger.getLogger(FraudWrapper.class);
	
	private static AnnotationConfiguration fraudConfig = new AnnotationConfiguration();
	private static SessionFactory fraudSessionFactory;

	
	private static AnnotationConfiguration jetBlueConfig = new AnnotationConfiguration();
	private static SessionFactory jetblueSessionFactory;


	private static AnnotationConfiguration spiritConfig = new AnnotationConfiguration();
	private static SessionFactory spiritSessionFactory;

	

	private static AnnotationConfiguration westjetConfig = new AnnotationConfiguration();
	private static SessionFactory westjetSessionFactory;

	

	private static AnnotationConfiguration azulConfig = new AnnotationConfiguration();
	private static SessionFactory azulSessionFactory;


	private static AnnotationConfiguration usairConfig = new AnnotationConfiguration();
	private static SessionFactory usairSessionFactory;
	

	private static AnnotationConfiguration airtranConfig = new AnnotationConfiguration();
	private static SessionFactory airtranSessionFactory;
	
	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getFraudSession() {
		try {
			// Obtain the correct session factory.

			if (fraudSessionFactory == null) {
				fraudSessionFactory = fraudConfig.configure(new File(hibernate_fraud_path)).buildSessionFactory();
			}
			return fraudSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}


	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getJetblueSession() {
		try {
			// Obtain the correct session factory.

			if (jetblueSessionFactory == null) {
				jetblueSessionFactory = jetBlueConfig.configure(new File(hibernate_jetblue_path)).buildSessionFactory();
			}
			return jetblueSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
	
	public static SessionFactory getAzulSession() {
		try {
			// Obtain the correct session factory.

			if (azulSessionFactory == null) {
				azulSessionFactory = azulConfig.configure(new File(hibernate_azul_path)).buildSessionFactory();
			}
			return azulSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
	
	public static SessionFactory getSpiritSession() {
		try {
			// Obtain the correct session factory.

			if (spiritSessionFactory == null) {
				spiritSessionFactory = spiritConfig.configure(new File(hibernate_spirit_path)).buildSessionFactory();
			}
			return spiritSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
	

	public static SessionFactory getWestJetSession() {
		try {
			// Obtain the correct session factory.

			if (westjetSessionFactory == null) {
				westjetSessionFactory = westjetConfig.configure(new File(hibernate_westjet_path)).buildSessionFactory();
			}
			return westjetSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
	

	public static SessionFactory getUsairSession() {
		try {
			// Obtain the correct session factory.

			if (usairSessionFactory == null) {
				usairSessionFactory = usairConfig.configure(new File(hibernate_usair_path)).buildSessionFactory();
			}
			return usairSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
	

	public static SessionFactory getAirtranSession() {
		try {
			// Obtain the correct session factory.

			if (airtranSessionFactory == null) {
				airtranSessionFactory = airtranConfig.configure(new File(hibernate_airtran_path)).buildSessionFactory();
			}
			return airtranSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
}