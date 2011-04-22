package aero.nettracer.serviceprovider.common.hibernate;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routine to obtain hibernate sessions.
 */
public class HibernateWrapper {


	private static final String HIBERNATE_MAIN_CFG_XML = "/hibernate_main.cfg.xml";
	private static final String HIBERNATE_GEO_CFG_XML = "/hibernate_geo.cfg.xml";

	private static String hibernate_main_path = HibernateWrapper.class.getResource(HIBERNATE_MAIN_CFG_XML).getPath();
	private static String hibernate_geo_path = HibernateWrapper.class.getResource(HIBERNATE_GEO_CFG_XML).getPath();

	private static Logger logger = Logger.getLogger(HibernateWrapper.class);

	private static AnnotationConfiguration cfg_prod = new AnnotationConfiguration();
	private static AnnotationConfiguration geoConfig = new AnnotationConfiguration();
	
	private static SessionFactory sf_prod;
	private static SessionFactory geoSessionFactory;

	static {
		try {
			sf_prod = cfg_prod.configure(HibernateWrapper.class.getResource("/hibernate_main.cfg.xml")).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SessionFactory getSession() {
		try {
			//sf_prod = cfg_prod.configure(new File(hibernate_main_path)).buildSessionFactory();
			return sf_prod;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static AnnotationConfiguration getConfig() {
		return cfg_prod;
	}
	
	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getGeoSession() {
		try {
			// Obtain the correct session factory.

			if (geoSessionFactory == null) {
				geoSessionFactory = geoConfig.configure(HibernateWrapper.class.getResource("/hibernate_geo.cfg.xml")).buildSessionFactory();
			}
			return geoSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}

}