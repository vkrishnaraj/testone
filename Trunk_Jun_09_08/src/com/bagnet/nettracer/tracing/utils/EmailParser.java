/*
 * Created on Apr 28, 2005
 *
 * matt
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author matt
 */
public class EmailParser {
	public static String parse(String filename, HashMap h) {
		return parse(filename, h, TracingConstants.DEFAULT_LOCALE);
	}
	public static String parse(String filename, HashMap h, String locale) {
		//MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(locale));
		try {
			FileReader freader = new FileReader(filename);

			BufferedReader in = new BufferedReader(freader);
			int i1, i2;
			StringBuffer sb = new StringBuffer();
			String s = null;
			String toparse = null;
			String tmpValue = null;
			while ((s = in.readLine()) != null) {
				while (s.indexOf("{") >= 0) {
					i1 = s.indexOf('{');
					i2 = s.indexOf('}', i1);
					if (i2 > i1) {
						toparse = s.substring(i1 + 1, i2);
						tmpValue = String.valueOf( h.get(toparse)); // get the parsed string
						if (tmpValue != null && !"null".equals(tmpValue)) {
							toparse = tmpValue;
						}
						if (tmpValue == null || "null".equals(tmpValue)) {
							try {
								toparse = myResources.getString(toparse);
							} catch (Exception e) {
								toparse = "";
							}
						}
						s = s.substring(0, i1) + toparse + s.substring(i2 + 1);
					}
				}
				sb.append(s);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String parsePaxMessageTrigger(String template, HashMap h) {
		try {
			int i1, i2;
			String toparse = null;
			String tmpValue = null;
			while (template.indexOf("{") >= 0) {
				i1 = template.indexOf('{');
				i2 = template.indexOf('}', i1);
				if (i2 > i1) {
					toparse = template.substring(i1 + 1, i2);
					tmpValue = (String) h.get(toparse); // get the parsed string
					if (tmpValue != null) {
						toparse = tmpValue;
					}
					if (tmpValue == null) {
						toparse = "";
					}
					template = template.substring(0, i1) + toparse + template.substring(i2 + 1);
				}
			}

			return template;
		} catch (Exception e) {
			return null;
		}
	}
}