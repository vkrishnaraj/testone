package com.bagnet.nettracer.tracing.enums;

public enum TemplateType {

	INCIDENT("Incident"),
	CLAIM("Claim"),
	FOUND_ITEM("FoundItem"),
	STATIC("Static"),
	INVALID("Invalid");
	
	private String defaultName;
	
	private TemplateType(String defaultName) {
		this.defaultName = defaultName;
	}
	
	public String getDefaultName() {
		return this.defaultName;
	}
	
	public static TemplateType fromOrdinal(int ordinal) {
		for (TemplateType type: TemplateType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return INVALID;
	}
	
	/**
	 * Returns an array of TemplateTypes that can be dependencies of variables
	 * defined in Template data.
	 * @return an array of dependency TemplateTypes
	 */
	public static TemplateType[] getDependencyTemplateTypes() {
		return new TemplateType[] { INCIDENT, CLAIM, FOUND_ITEM };
	}
	
}
