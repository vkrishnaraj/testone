package com.bagnet.nettracer.tracing.performance;

import com.bagnet.nettracer.tracing.db.TimeZone;


public enum Cache {
	TIMEZONES {
		public void insert(Object key, Object value) {
			instanceCache.insert(this.name(), key, value);
		}
				
		public TimeZone retrieve(Object key) {
			return (TimeZone) instanceCache.retrieve(this.name(), key);
		}
		
		public boolean contains(Object key) {
			return instanceCache.contains(this.name(), key);
		}
	};
	
	public static InstanceCache instanceCache = new InstanceCache();
	public abstract void insert(Object key, Object o);
	public abstract Object retrieve(Object key);
	public abstract boolean contains(Object key);

}
