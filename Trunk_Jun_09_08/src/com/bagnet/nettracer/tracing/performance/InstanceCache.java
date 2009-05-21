package com.bagnet.nettracer.tracing.performance;

import java.util.concurrent.ConcurrentHashMap;

public class InstanceCache {
	
	ConcurrentHashMap<String, Object> basicMapParent = new ConcurrentHashMap<String, Object>();

	public void insert(String type, Object key, Object value) {
		ConcurrentHashMap<Object, Object> objectMap = (ConcurrentHashMap<Object, Object>) basicMapParent.get(type);
		if (objectMap == null) {
			objectMap = new ConcurrentHashMap<Object, Object>();
			basicMapParent.put(type, objectMap);
		} else {
			objectMap.put(key, value);
		}
	}

	public boolean contains(String type, Object key) {
		ConcurrentHashMap<Object, Object> objectMap = (ConcurrentHashMap<Object, Object>) basicMapParent.get(type);
		if (objectMap != null) {
			return objectMap.containsKey(key);
		} else {
			return false;
		}
	}
	
	public Object retrieve(String type, Object key) {
		ConcurrentHashMap<Object, Object> objectMap = (ConcurrentHashMap<Object, Object>) basicMapParent.get(type);
		return objectMap.get(key);
	}

	
}
