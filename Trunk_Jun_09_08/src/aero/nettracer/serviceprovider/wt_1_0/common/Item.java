package aero.nettracer.serviceprovider.wt_1_0.common;

public class Item {
	private int bagNumber;
	private String firstNameOnBag;
	private String lastNameOnBag;
	private String color;
	private String type;
	private String desc1;
	private String desc2;
	private String desc3;
	private String manufacturer;
	private String externaldesc;
	private Content[] content;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getExternaldesc() {
		return externaldesc;
	}

	public void setExternaldesc(String externaldesc) {
		this.type = externaldesc;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDesc3() {
		return desc3;
	}

	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}

	public Content[] getContent() {
		return content;
	}

	public void setContent(Content[] content) {
		this.content = content;
	}

	public String getFirstNameOnBag() {
		return firstNameOnBag;
	}

	public void setFirstNameOnBag(String firstNameOnBag) {
		this.firstNameOnBag = firstNameOnBag;
	}

	public String getLastNameOnBag() {
		return lastNameOnBag;
	}

	public void setLastNameOnBag(String lastNameOnBag) {
		this.lastNameOnBag = lastNameOnBag;
	}

	public String getManufacturer() {
  	return manufacturer;
  }

	public void setManufacturer(String manufacturer) {
  	this.manufacturer = manufacturer;
  }

	public int getBagNumber() {
  	return bagNumber;
  }

	public void setBagNumber(int bagNumber) {
  	this.bagNumber = bagNumber;
  }
}
