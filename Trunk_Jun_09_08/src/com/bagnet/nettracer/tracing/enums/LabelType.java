/**
 * 
 */
package com.bagnet.nettracer.tracing.enums;

public enum LabelType {
	
	//5660 template
	_5160("5160", 5660),
	_5388("5388", 5660),
	_5390("5390", 5660),
	_5660("5660", 5660),
	_5960("5960", 5660),
	
	//other templates

	UNSUPPORTED("Unsupported", -1);

	private final String name;
	private final int template;
	
	private LabelType(String name, int template) {
		this.name = name;
		this.template = template;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTemplate() {
		return template;
	}
	
	public static LabelType fromName(String name) {
		for (LabelType type: LabelType.values()) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		
		return UNSUPPORTED;
	}
}
