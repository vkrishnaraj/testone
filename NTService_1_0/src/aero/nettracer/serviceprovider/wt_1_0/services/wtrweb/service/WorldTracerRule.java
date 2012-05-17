package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.List;

import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;



/**
 * @author Noah
 *
 */
public interface WorldTracerRule<T> {
	enum Format {
		NUMERIC("[^0-9\\s/]"),
		ALPHA("[^a-zA-Z\\s/]"),
		ALPHA_NUMERIC("[^a-zA-Z\\s0-9/]"),
		FREE_FLOW("[\\.#\"><%]\\?"),
		CONTENT_FIELD("[\\:\\.#\"><%!@$%^&*()_+-?]"),
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
	String formatEntry(String string) throws WorldTracerException;
	int getMaxAllowed();
}
