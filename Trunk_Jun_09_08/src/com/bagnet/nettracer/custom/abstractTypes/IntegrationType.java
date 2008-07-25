package com.bagnet.nettracer.custom.abstractTypes;

public abstract class IntegrationType {
	protected static String integrationName;
	protected static String airlineCode;
	protected static String packageName = "com.bagnet.nettracer.custom";
	protected static Object implementationObject = null;
	
	public static boolean isImplemented() {
		return implementationObject == null;
	}
	
}
