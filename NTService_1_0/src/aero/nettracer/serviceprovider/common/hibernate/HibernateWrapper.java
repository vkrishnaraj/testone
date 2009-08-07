package aero.nettracer.serviceprovider.common.hibernate;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routine to obtain hibernate sessions.
 */
public class HibernateWrapper {

	private static AnnotationConfiguration cfg_prod = new AnnotationConfiguration();
	private static SessionFactory sf_prod;
	private static String hibernate_main_path = HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath();

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

}