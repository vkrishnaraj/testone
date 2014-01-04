package aero.nettracer.portal.model;

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
	private boolean hardSided;
	private boolean wheelsRollers;
	private boolean feet;
	private boolean trim;
	private boolean softSided;
	private boolean zippers;
	private boolean retractableHandel;
	private boolean pockets;
	private boolean locks;
	private boolean pullStrap;
	private boolean nameTag;
	private boolean ribbonsPersonalMarkings;
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

	public boolean getHardSided() {
		return hardSided;
	}

	public void setHardSided(boolean hardSided) {
		this.hardSided = hardSided;
	}

	public boolean getWheelsRollers() {
		return wheelsRollers;
	}

	public void setWheelsRollers(boolean wheelsRollers) {
		this.wheelsRollers = wheelsRollers;
	}

	public boolean getFeet() {
		return feet;
	}

	public void setFeet(boolean feet) {
		this.feet = feet;
	}

	public boolean getTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public boolean getSoftSided() {
		return softSided;
	}

	public void setSoftSided(boolean softSided) {
		this.softSided = softSided;
	}

	public boolean getZippers() {
		return zippers;
	}

	public void setZippers(boolean zippers) {
		this.zippers = zippers;
	}

	public boolean getRetractableHandel() {
		return retractableHandel;
	}

	public void setRetractableHandel(boolean retractableHandel) {
		this.retractableHandel = retractableHandel;
	}

	public boolean getPockets() {
		return pockets;
	}

	public void setPockets(boolean pockets) {
		this.pockets = pockets;
	}

	public boolean getLocks() {
		return locks;
	}

	public void setLocks(boolean locks) {
		this.locks = locks;
	}

	public boolean getPullStrap() {
		return pullStrap;
	}

	public void setPullStrap(boolean pullStrap) {
		this.pullStrap = pullStrap;
	}

	public boolean getNameTag() {
		return nameTag;
	}

	public void setNameTag(boolean nameTag) {
		this.nameTag = nameTag;
	}

	public boolean getRibbonsPersonalMarkings() {
		return ribbonsPersonalMarkings;
	}

	public void setRibbonsPersonalMarkings(boolean ribbonsPersonalMarkings) {
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

	public String getBagArrivalStatusDisp() {
		return ( bagArrivalStatus != null && bagArrivalStatus.equals("true") ? "Yes" : "No");
	}
	
} 
