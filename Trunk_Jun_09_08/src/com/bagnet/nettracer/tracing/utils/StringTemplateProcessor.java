package com.bagnet.nettracer.tracing.utils;

import java.lang.reflect.Method;
import java.util.HashMap;

public class StringTemplateProcessor {

	HashMap<Class<?>, Object> classReference = new HashMap<Class<?>, Object>();
	HashMap<String, Method> methodReference = new HashMap<String, Method>();
	
	public static String process(String template, Object... objects) {
		String output = "";
		StringTemplateProcessor a = new StringTemplateProcessor();
		for (Object o: objects) {
			try {
				a.addClass(o);
			} catch (Exception e) {
				// Ignore
				e.printStackTrace();
			}
		}
		try {
			output = a.fillValues(template);
		} catch (Exception e) {
			// Ignore
			e.printStackTrace();
		}
		return output;
	}

	public void addClass(Object obj) throws Exception {
		if (classReference.containsKey(obj.getClass())) {
			throw new Exception("Object type already exists in processor.");
		}
		classReference.put(obj.getClass(), obj);

		Method[] methods = obj.getClass().getMethods();

		for (Method method : methods) {
			if (isStringGetter(method)) {
				String key = method.getName().substring(3).toUpperCase();
				if (!methodReference.containsKey(key)) {
					methodReference.put(key, method);
				}
			}
		}
	}

	private boolean isStringGetter(Method method) {
		if (!method.getName().startsWith("get"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (java.lang.String.class.equals(method.getReturnType()))
			return true;
		return false;
	}
	
	public String fillValues(String input) throws Exception {
		String output = input;
		
		int i1, i2;
		String toparse = null;

		
		while (output.indexOf("{") >= 0) {
			i1 = output.indexOf('{');
			i2 = output.indexOf('}', i1);
			if (i2 > i1) {
				toparse = output.substring(i1 + 1, i2);
//				System.out.println(toparse);
				Method method = methodReference.get(toparse.toUpperCase());
				try {
					if (method != null) {
						Object o = classReference.get(method.getDeclaringClass());
						String str = (String) method.invoke(o);
						if (str != null) {
							output = output.replaceAll("\\{" + toparse + "\\}", str);
						} else {
							output = output.replaceAll("\\{" + toparse + "\\}", "");
						}
					} else {
						output = output.replaceAll("\\{" + toparse + "\\}", "");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		}

		return output;
	}
}
