package com.bagnet.nettracer.tracing.db.i8n;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public abstract class LocaleBasedObject {
	private String locale = null;
	
	public abstract String getKey();
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public void setLocale(Agent agent) {
		this.locale = agent.getCurrentlocale();
	}
	
	/**
	 * Used to return the locale sensitive version of the object for viewing in lists.
	 * 
	 * @return String from message resource.
	 */
	public String getDescription() {
		return TracerUtils.getText(getKey(), locale);
	}
	
	public String getDescription(String loc) {
		return TracerUtils.getText(getKey(), loc);
	}
	
	public String getTextDescription(Agent user) {
		return TracerUtils.getText(getKey(), user);
	}
}
