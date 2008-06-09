/*
 * Created on Apr 28, 2005
 *
 * matt
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * @author matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailParser {
	public static String parse(String filename, HashMap h) {
		try {
			FileReader freader = new FileReader(filename);

			BufferedReader in = new BufferedReader(freader);
			int i1, i2;
			StringBuffer sb = new StringBuffer();
			String s = null;
			String toparse = null;
			while ((s = in.readLine()) != null) {
				while (s.indexOf("{") >= 0) {
					i1 = s.indexOf('{');
					i2 = s.indexOf('}', i1);
					if (i2 > i1) {
						toparse = s.substring(i1 + 1, i2);
						toparse = (String) h.get(toparse); // get the parsed string
						if (toparse == null) toparse = "";
						s = s.substring(0, i1) + toparse + s.substring(i2 + 1);
					}
				}
				sb.append(s);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}