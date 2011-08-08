package com.bagnet.nettracer.tracing.db.onlineclaims;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "oc_bag")
@Proxy(lazy = false)
public class OCBag {
	
	@Id
	@GeneratedValue
	private long bagId;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
	@JoinColumn(name = "claimId", nullable = false)
	private OnlineClaim claim;
	
	@Column(length = 10)
	private String tag;
	
	@Basic
	private boolean bagArrive;
	
	@Column(length = 40)
	private String nameOnBag;
	
	@Column(length = 25)
	private String brand;
	
	@Column(length = 50)
	private String externalMarkings;
	
	@Temporal(TemporalType.DATE)
	private Date purchaseDate;
	
	@Column(length = 2)
	private String bagColor;
	
	@Column(length = 2)
	private String bagType;

	@Column(length = 20)
	private String bagValue;

	@Column(length = 3)
	private String bagCurrency;

	@Column(length = 50)
	private String bagOwner;
	
	@Basic
	private boolean hardSided;
	
	@Basic
	private boolean softSided;
	
	@Basic
	private boolean locks;
	
	@Basic
	private boolean wheels;
	
	@Basic
	private boolean zippers;
	
	@Basic
	private boolean pullStrap;
	
	@Basic
	private boolean feet;
	
	@Basic
	private boolean retractibleHandle;
	
	@Basic
	private boolean nameTag;
	
	@Basic
	private boolean trim;
	
	@Basic
	private boolean pockets;
	
	@Basic
	private boolean ribbonsOrMarkings;
	
	@Basic
	private boolean leather;
	
	@Basic
	private boolean metal;
	
	@Column(length = 20)
	private String trimDescription;
	
	@OneToMany(mappedBy = "bag", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<OCContents> contents;

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

	public OnlineClaim getClaim() {
		return claim;
	}

	public void setClaim(OnlineClaim claim) {
		this.claim = claim;
	}

	public Set<OCContents> getContents() {
  	return contents;
  }

	public void setContents(Set<OCContents> contents) {
  	this.contents = contents;
  }

	public long getBagId() {
		return bagId;
	}

	public void setBagId(long bagId) {
		this.bagId = bagId;
	}

	public String getBagValue() {
		return bagValue;
	}

	public void setBagValue(String bagValue) {
		this.bagValue = bagValue;
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

	@Transient
	public String getGrandTotal() {
		if (getContents() != null) {
			double total = 0D;
			String currency = null;
			for (OCContents cont : getContents()) {
				if (currency == null) {
					currency = cont.getCurrency();
				} else if (!currency.equals(cont.getCurrency())) {
					return "Multiple Currencies.";
				}
				total += cont.getPrice();
			}
			total = Math.round(total * 100)/100.0D;
			return total + "";
		}
		return "0.00";
	}
	
	public void setGrandTotal(String grandTotal) {
	}

}
