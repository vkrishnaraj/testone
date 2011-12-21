package aero.nettracer.lfc.model;

import java.io.Serializable;

public class KeyValueBean implements Serializable {
	
	private static final long serialVersionUID = -3971174419547833116L;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getKeyAsLong() {
		Long l = Long.getLong(key);
		if (l != null) {
			return l.longValue();
		} else {
			return -1;
		}
	}

	String key;
	String value;
}
