package com.nettracer.claims.core.model;

public class IncidentBagBean {
	private String type;
	private String color;
	private String typeLabel = "None Selected";
	private String colorLabel = "None Selected";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTypeLabel() {
		if (type != null) {
			if (type.equals("22")) {
				typeLabel = "Roll-Around";
			} else if (type.equals("5")) {
				typeLabel = "Suitcase";
			} else if (type.equals("25")) {
				typeLabel = "Duffel";
			} else if (type.equals("69")) {
				typeLabel = "Sporting Equipment";
			} else if (type.equals("94")) {
				typeLabel = "Assistive Device";
			} else if (type.equals("99")) {
				typeLabel = "Other";
			}
		} 
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public String getColorLabel() {
		if (color != null) {
			if (color.equals("WT")) {
				colorLabel = "White/Clear";
			} else if (color.equals("BK")) {
				colorLabel = "Black";
			} else if (color.equals("GY")) {
				colorLabel = "Grey";
			} else if (color.equals("BU")) {
				colorLabel = "Blue";
			} else if (color.equals("PU")) {
				colorLabel = "Purple";
			} else if (color.equals("RD")) {
				colorLabel = "Red";
			} else if (color.equals("YW")) {
				colorLabel = "Yellow";
			} else if (color.equals("BE")) {
				colorLabel = "Beige";
			} else if (color.equals("BN")) {
				colorLabel = "Brown";
			} else if (color.equals("GN")) {
				colorLabel = "Green";
			} else if (color.equals("MC")) {
				colorLabel = "Multi-Colored";
			} else if (color.equals("PR")) {
				colorLabel = "Pattern";
			}
		} 
		return colorLabel;
	}

	public void setColorLabel(String colorLabel) {
		this.colorLabel = colorLabel;
	}
	

}
