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

	private static final String HIBERNATE_GEO_CFG_XML = "/hibernate_geo.cfg.xml";

	private static String hibernate_geo_path = FraudWrapper.class.getResource(HIBERNATE_GEO_CFG_XML).getPath();

	private static Logger logger = Logger.getLogger(FraudWrapper.class);

	private static AnnotationConfiguration geoConfig = new AnnotationConfiguration();
	private static SessionFactory geoSessionFactory;
	
	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getGeoSession() {
		try {
			// Obtain the correct session factory.

			if (geoSessionFactory == null) {
				geoSessionFactory = geoConfig.configure(new File(hibernate_geo_path)).buildSessionFactory();
			}
			return geoSessionFactory;
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
	}
}