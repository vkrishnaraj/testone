package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Property;

public class PropertyBMO {
	
	private static Logger logger = Logger.getLogger(PropertyBMO.class);
	
	// PROPERTIES USED TO LIMIT STATIONS TO CODE TO
	public static final String PROPERTY_LIMIT_LD_STATIONS = "losscode.ld.limitstations";
	public static final String PROPERTY_LIMIT_MISSING_STATIONS = "losscode.missing.limitstations";
	public static final String PROPERTY_LIMIT_DAMAGED_STATIONS = "losscode.dam.limitstations";
	public static final String PROPERTY_LIMIT_LD_ADDSTATIONS = "losscode.ld.addstations";
	public static final String PROPERTY_LIMIT_MISSING_ADDSTATIONS = "losscode.missing.addstations";
	public static final String PROPERTY_LIMIT_DAMAGED_ADDSTATIONS = "losscode.dam.addstations";


	/**
	 * Retrieves the value of the property from the database.
	 * 
	 * @param keyVal Key
	 * @return String property value.
	 * @throws HibernateException
	 */
	public static String getValue(String keyVal) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Property property where keyStr= :key");
			q.setString("key", keyVal);
			List list = q.list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return (String) ((Property)list.get(0)).getValueStr();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
}
