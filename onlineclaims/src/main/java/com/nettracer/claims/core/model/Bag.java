package com.nettracer.claims.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Bag {
	
	private String bagTagNumber;
	private String bagArrivalStatus;
	private String nameonBag;
	private String brandOftheBag;
	private String externalMarkings;
	private Date bagPurchaseDate;
	private String bagColor;
	private String bagType;
	private String bagPrice;
	private String bagCurrency;
	private String bagOwner;
	private Boolean hardSided;
	private Boolean wheelsRollers;
	private Boolean feet;
	private Boolean trim;
	private Boolean softSided;
	private Boolean zippers;
	private Boolean retractableHandel;
	private Boolean pockets;
	private Boolean locks;
	private Boolean pullStrap;
	private Boolean nameTag;
	private Boolean ribbonsPersonalMarkings;
	private boolean leather;
	private boolean metal;
	private String trimDescription;
	private String grandTotal;
	
	private List<Content> contentList=new ArrayList<Content>();

	public String getBagPrice() {
		return bagPrice;
	}

	public void setBagPrice(String bagPrice) {
		this.bagPrice = bagPrice;
	}

	public String getBagCurrency() {
		return bagCurrency;
	}

	public void setBagCurrency(String bagCurrency) {
		this.bagCurrency = bagCurrency;
	}

	public String getBagOwner() {
		return bagOwner;
	}

	public void setBagOwner(String bagOwner) {
		this.bagOwner = bagOwner;
	}

	public String getBagTagNumber() {
		return bagTagNumber;
	}

	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	public String getBagArrivalStatus() {
		return bagArrivalStatus;
	}

	public void setBagArrivalStatus(String bagArrivalStatus) {
		this.bagArrivalStatus = bagArrivalStatus;
	}

	public String getNameonBag() {
		return nameonBag;
	}

	public void setNameonBag(String nameonBag) {
		this.nameonBag = nameonBag;
	}

	public String getBrandOftheBag() {
		return brandOftheBag;
	}

	public void setBrandOftheBag(String brandOftheBag) {
		this.brandOftheBag = brandOftheBag;
	}

	public String getExternalMarkings() {
		return externalMarkings;
	}

	public void setExternalMarkings(String externalMarkings) {
		this.externalMarkings = externalMarkings;
	}

	public Date getBagPurchaseDate() {
		return bagPurchaseDate;
	}

	public void setBagPurchaseDate(Date bagPurchaseDate) {
		this.bagPurchaseDate = bagPurchaseDate;
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

	public Boolean getHardSided() {
		return hardSided;
	}

	public void setHardSided(Boolean hardSided) {
		this.hardSided = hardSided;
	}

	public Boolean getWheelsRollers() {
		return wheelsRollers;
	}

	public void setWheelsRollers(Boolean wheelsRollers) {
		this.wheelsRollers = wheelsRollers;
	}

	public Boolean getFeet() {
		return feet;
	}

	public void setFeet(Boolean feet) {
		this.feet = feet;
	}

	public Boolean getTrim() {
		return trim;
	}

	public void setTrim(Boolean trim) {
		this.trim = trim;
	}

	public Boolean getSoftSided() {
		return softSided;
	}

	public void setSoftSided(Boolean softSided) {
		this.softSided = softSided;
	}

	public Boolean getZippers() {
		return zippers;
	}

	public void setZippers(Boolean zippers) {
		this.zippers = zippers;
	}

	public Boolean getRetractableHandel() {
		return retractableHandel;
	}

	public void setRetractableHandel(Boolean retractableHandel) {
		this.retractableHandel = retractableHandel;
	}

	public Boolean getPockets() {
		return pockets;
	}

	public void setPockets(Boolean pockets) {
		this.pockets = pockets;
	}

	public Boolean getLocks() {
		return locks;
	}

	public void setLocks(Boolean locks) {
		this.locks = locks;
	}

	public Boolean getPullStrap() {
		return pullStrap;
	}

	public void setPullStrap(Boolean pullStrap) {
		this.pullStrap = pullStrap;
	}

	public Boolean getNameTag() {
		return nameTag;
	}

	public void setNameTag(Boolean nameTag) {
		this.nameTag = nameTag;
	}

	public Boolean getRibbonsPersonalMarkings() {
		return ribbonsPersonalMarkings;
	}

	public void setRibbonsPersonalMarkings(Boolean ribbonsPersonalMarkings) {
		this.ribbonsPersonalMarkings = ribbonsPersonalMarkings;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}
	
	public boolean isLeather() {
		return leather;
	}

	public void setLeather(boolean leather) {
		this.leather = leather;
	}

	public boolean isMetal() {
		return metal;
	}

	public void setMetal(boolean metal) {
		this.metal = metal;
	}

	public String getTrimDescription() {
		return trimDescription;
	}

	public void setTrimDescription(String trimDescription) {
		this.trimDescription = trimDescription;
	}

	public String getGrandTotal() {
		double total = 0D;
		if (getBagPrice() != null && getBagPrice().trim().length() > 0 && getBagPrice().matches("^[0-9]*\\.?[0-9]*$")) {
			total = Double.parseDouble(getBagPrice());
		}
		String currency = getBagCurrency();
		if (contentList != null) {
			for (Content cont : contentList) {
				if (currency == null) {
					currency = cont.getCurrency();
				} else if (!currency.equals(cont.getCurrency())) {
					return "Multiple Currencies.";
				}
				total += (cont.getPrice() != null ? cont.getPrice() : 0D);
			}
		}
		total = Math.round(total * 100)/100.0D;
		return total + "";
	}
	
	public void setGrandTotal(String grandTotal) {
	}

	
	

	
	
} 
