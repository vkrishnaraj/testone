package com.nettracer.claims.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

public class ClaimsProperties {

	private static Properties properties = new Properties();
	
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public static final String WS_LOCATION = "ws_location";

	private static final Logger logger = Logger.getLogger(ClaimsProperties.class);

	static {
		try {
			properties.load(ClaimsProperties.class
					.getResourceAsStream("/claims.properties"));

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
	
	public static String getInstanceLabel() {
		String instanceRef = System.getProperty("instance.ref");
		if (instanceRef != null) {
			return instanceRef;
		}
		return "";
	}
	
	public static boolean isFrontend() {
		String instanceRef = System.getProperty("instance.ref");
		if (instanceRef != null) {
			return instanceRef.startsWith("I");
		}
		return false;
	}
	
	public static void reloadProperties() {
		try {
		lock.writeLock().lock();
		properties.clear();
		properties.load(ClaimsProperties.class
				.getResourceAsStream("/claims.properties"));
		} catch (IOException e) {
			logger.error("unable to reload properties", e);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
