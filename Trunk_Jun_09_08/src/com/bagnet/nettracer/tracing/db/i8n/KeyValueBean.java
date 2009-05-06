package com.bagnet.nettracer.tracing.db.i8n;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class KeyValueBean extends LabelValueBean {

	public KeyValueBean() {
	}

	public KeyValueBean(String label, String key) {
		super(label, null);
		this.setValue(TracerUtils.getText(key, (String) null));
	}
	
	public KeyValueBean(String key, String value, String locale) {
		super(key, value);
		this.setLabel(TracerUtils.getText(key, locale));
	}
	
	public KeyValueBean(String key, String value, Agent user) {
		super(key, value);
		this.setLabel(TracerUtils.getText(key, user.getCurrentlocale()));
	}

}
