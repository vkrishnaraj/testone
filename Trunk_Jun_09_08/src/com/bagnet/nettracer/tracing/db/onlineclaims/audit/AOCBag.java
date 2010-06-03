package com.bagnet.nettracer.tracing.db.onlineclaims.audit;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "oc_audit_bag")
@Proxy(lazy = false)
public class AOCBag {

	@Id
	@GeneratedValue
	private long abagId;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class)
	@JoinColumn(name = "auditClaimId", nullable = false)
	private AOCClaim claim;

	@Column(length = 10)
	private String tag;

	@Basic
	private boolean bagArrive;

	@Column(length = 40)
	private String nameOnBag;

	@Column(length = 10)
	private String brand;

	@Column(length = 50)
	private String externalMarkings;

	@Temporal(TemporalType.DATE)
	private Date purchaseDate;

	@Column(length = 2)
	private String bagColor;

	@Column(length = 2)
	private String bagType;

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

	@OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<AOCContents> contents;

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


	public AOCClaim getClaim() {
		return claim;
	}

	public void setClaim(AOCClaim claim) {
		this.claim = claim;
	}

	public Set<AOCContents> getContents() {
		return contents;
	}

	public void setContents(Set<AOCContents> contents) {
		this.contents = contents;
	}

	public long getAbagId() {
		return abagId;
	}

	public void setAbagId(long abagId) {
		this.abagId = abagId;
	}

}
