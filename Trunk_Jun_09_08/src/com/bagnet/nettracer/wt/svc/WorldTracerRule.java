package com.bagnet.nettracer.wt.svc;

import java.util.ArrayList;
import java.util.List;

import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

/**
 * @author Noah
 *
 */
public interface WorldTracerRule<T> {
	enum Format {
		NUMERIC("[^0-9\\s/]"),
		ALPHA("[^a-zA-Z\\s/]"),
		ALPHA_NUMERIC("[^a-zA-Z\\s0-9/]"),
		FREE_FLOW("\\."),
		ALL(null);
		
		private String replaceChars;

		Format(String replaceStr) {
			this.replaceChars = replaceStr;
		}
		
		public String replaceChars() {
			return replaceChars;
		}
	}
	int DEFAULT_LENGTH = 58;
	/**
	 * @param resultSets
	 * @return
	 * @throws WorldTracerException 
	 */
	String getFieldString(WorldTracerField field, List<String> list) throws WorldTracerException;
}
