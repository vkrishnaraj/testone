package com.nettracer.claims.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

public class IncidentProperties {

	private static Properties properties = new Properties();
	
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private static final Logger logger = Logger.getLogger(IncidentProperties.class);

	static {
		try {
			properties.load(IncidentProperties.class
					.getResourceAsStream("/incident.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String property) {
		
		try {
			lock.readLock().lock();
			return properties.getProperty(property);
		} finally {
			lock.readLock().unlock();
		}
	}

	public static boolean isTrue(String property) {
		String value = null;
		try {
			lock.readLock().lock();
			value = properties.getProperty(property);
		}finally {
			lock.readLock().unlock();
		}
		if ( value != null) {
			return value.equals("1");
		} else {
			// If the value is not found a null value will be returned
			// and we need to return false.
			return false;
		}
	}
	
	public static void reloadProperties() {
		try {
		lock.writeLock().lock();
		properties.clear();
		properties.load(IncidentProperties.class
				.getResourceAsStream("/incident.properties"));
		} catch (IOException e) {
			logger.error("unable to reload properties", e);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
