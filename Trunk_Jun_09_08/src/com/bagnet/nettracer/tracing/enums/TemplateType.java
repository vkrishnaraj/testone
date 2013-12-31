package com.bagnet.nettracer.tracing.enums;

import java.util.ArrayList;
import java.util.List;

public enum TemplateType {

	INCIDENT("Incident", "Incidents"),
	CLAIM("Claim", "Claims"),
	FOUND_ITEM("FoundItem", "Found Items"),
	STATIC("Static", "All"),
	INVALID("Invalid", "None"),
	CLAIM_SETTLEMENT("Expense", "Claim Settlements");
	
	private String defaultName;
	private String displayName;
	
	private TemplateType(String defaultName, String displayName) {
		this.defaultName = defaultName;
		this.displayName = displayName;
	}
	
	public String getDefaultName() {
		return this.defaultName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public int getOrdinal() {
		return this.ordinal();
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
	public static List<TemplateType> getDependencyTemplateTypes() {
		ArrayList<TemplateType> types = new ArrayList<TemplateType>();
		types.add(INCIDENT);
		types.add(CLAIM);
		types.add(FOUND_ITEM);
		types.add(CLAIM_SETTLEMENT);
		return types;
	}
	
	public static Integer[] getOrdinals(List<TemplateType> types) {
		if (types == null || types.isEmpty()) return null;
		Integer[] ordinals = new Integer[types.size()];
		for (int i = 0; i < ordinals.length; ++i) {
			ordinals[i] = types.get(i).ordinal();
		}
		return ordinals;
	}
	
}
