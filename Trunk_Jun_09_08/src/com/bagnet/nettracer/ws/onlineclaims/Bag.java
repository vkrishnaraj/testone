package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Date;

public class Bag {
	private long id;
	private String tag;
	private boolean bagArrive;
	private String nameOnBag;
	private String brand;
	private String externalMarkings;
	private Date purchaseDate;
	private String bagColor;
	private String bagType;
	private boolean hardSided;
	private boolean softSided;
	private boolean locks;
	private boolean wheels;
	private boolean zippers;
	private boolean pullStrap;
	private boolean feet;
	private boolean retractibleHandle;
	private boolean nameTag;
	private boolean trim;
	private boolean pockets;
	private boolean ribbonsOrMarkings;
	private Contents[] contents;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isBagArrive() {
		return bagArrive;
	}

	public void setBagArrive(boolean bagArrive) {
		this.bagArrive = bagArrive;
	}

	public String getNameOnBag() {
		return nameOnBag;
	}

	public void setNameOnBag(String nameOnBag) {
		this.nameOnBag = nameOnBag;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getExternalMarkings() {
		return externalMarkings;
	}

	public void setExternalMarkings(String externalMarkings) {
		this.externalMarkings = externalMarkings;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getBagColor() {
		return bagColor;
	}

	public void setBagColor(String bagColor) {
		this.bagColor = bagColor;
	}

	public String getBagType() {
		return bagType;
	}

	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	public boolean isHardSided() {
		return hardSided;
	}

	public void setHardSided(boolean hardSided) {
		this.hardSided = hardSided;
	}

	public boolean isSoftSided() {
		return softSided;
	}

	public void setSoftSided(boolean softSided) {
		this.softSided = softSided;
	}

	public boolean isLocks() {
		return locks;
	}

	public void setLocks(boolean locks) {
		this.locks = locks;
	}

	public boolean isWheels() {
		return wheels;
	}

	public void setWheels(boolean wheels) {
		this.wheels = wheels;
	}

	public boolean isZippers() {
		return zippers;
	}

	public void setZippers(boolean zippers) {
		this.zippers = zippers;
	}

	public boolean isPullStrap() {
		return pullStrap;
	}

	public void setPullStrap(boolean pullStrap) {
		this.pullStrap = pullStrap;
	}

	public boolean isFeet() {
		return feet;
	}

	public void setFeet(boolean feet) {
		this.feet = feet;
	}

	public boolean isRetractibleHandle() {
		return retractibleHandle;
	}

	public void setRetractibleHandle(boolean retractibleHandle) {
		this.retractibleHandle = retractibleHandle;
	}

	public boolean isNameTag() {
		return nameTag;
	}

	public void setNameTag(boolean nameTag) {
		this.nameTag = nameTag;
	}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public boolean isPockets() {
		return pockets;
	}

	public void setPockets(boolean pockets) {
		this.pockets = pockets;
	}

	public boolean isRibbonsOrMarkings() {
		return ribbonsOrMarkings;
	}

	public void setRibbonsOrMarkings(boolean ribbonsOrMarkings) {
		this.ribbonsOrMarkings = ribbonsOrMarkings;
	}

	public Contents[] getContents() {
		return contents;
	}

	public void setContents(Contents[] contents) {
		this.contents = contents;
	}

	public long getId() {
  	return id;
  }

	public void setId(long id) {
  	this.id = id;
  }

}
