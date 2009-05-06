package com.bagnet.nettracer.jmx;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;


import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class NetTracerManagementBean {

	public void reloadDatabaseProperties() {
		PropertyBMO.resetCache();
	}
	
	public void reloadTracerFileProperties() {
		TracerProperties.reloadProperties();
	}
	
	public boolean reloadApplicationResources() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		try {
			ResourceBundle.clearCache(cl);
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
}
