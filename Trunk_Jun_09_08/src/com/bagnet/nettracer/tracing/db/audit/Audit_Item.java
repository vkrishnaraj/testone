/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Manufacturer;
import com.bagnet.nettracer.tracing.db.Resolution;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.XDescElement;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_Item"
 */
public class Audit_Item implements Serializable {
	private int audit_item_id;

	private int Item_ID;
	private int bagnumber;
	private int itemtype_ID;
	private Status status;
	private String claimchecknum; // ohd claimcheck matched with (for mishandled
	// only)
	private String color;
	private String bagtype;
	private int xdescelement_ID_1;
	private int xdescelement_ID_2;
	private int xdescelement_ID_3;
	private int manufacturer_ID;
	private String manufacturer_other;
	private int lvlofdamage;
	private String damage;
	private Resolution resolution;
	private String resolutiondesc;
	private double cost;
	private String drafts;
	private String currency_ID;
	private String fnameonbag;
	private String mnameonbag;
	private String lnameonbag;
	private String arrivedonflightnum;
	private String arrivedonairline_ID;
	private Date arrivedondate;

	private String _DATEFORMAT;

	private Set photoes;
	private Set inventory;

	private List photolist;
	private List inventorylist;
	
	private String OHD_ID; // ohd_id matched with (for mishandled only)
	private int is_in_station; // is the matched ohd in station?

	private Audit_Incident audit_incident;
	
	private String posId;
	private String expediteTagNum;
	
	private int specialCondition;
	
	/**
	 * @return Returns the bag_weight.
	 * 
	 * @hibernate.property type="double"
	 */
	public Double getBag_weight() {
		return bag_weight;
	}

	public void setBag_weight(Double bag_weight) {
		this.bag_weight = bag_weight;
	}
	
	/**
	 * @return Returns the bag_weight_unit.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getBag_weight_unit() {
		return bag_weight_unit;
	}

	public void setBag_weight_unit(String bag_weight_unit) {
		this.bag_weight_unit = bag_weight_unit;
	}

	private Double bag_weight;
	private String bag_weight_unit;
	
	
	public Audit_Item() {
		super();
	}

	public Audit_Item(int itemtype) {
		this.itemtype_ID = itemtype;
	}

	/**
	 * @return Returns the audit_item_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_item_0"
	 * 
	 * 
	 *  
	 */
	public int getAudit_item_id() {
		return audit_item_id;
	}

	/**
	 * @param audit_item_id
	 *          The audit_item_id to set.
	 */
	public void setAudit_item_id(int audit_item_id) {
		this.audit_item_id = audit_item_id;
	}

	/**
	 * @return Returns the audit_incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"
	 *                        column="audit_incident_id"
	 */
	public Audit_Incident getAudit_incident() {
		return audit_incident;
	}
	/**
	 * @param audit_incident The audit_incident to set.
	 */
	public void setAudit_incident(Audit_Incident audit_incident) {
		this.audit_incident = audit_incident;
	}
	
	
	/**
	 * @return Returns the photoes.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="photo_ID"
	 * @hibernate.key column="audit_item_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Item_Photo"
	 */
	public Set getPhotoes() {
		if (photolist == null) return null;
		return new LinkedHashSet(photolist);
	}

	/**
	 * @param photoes
	 *          The photoes to set.
	 */
	public void setPhotoes(Set photoes) {
		this.photoes = photoes;
		if (photoes != null) this.photolist = new ArrayList(photoes);
	}

	/**
	 * @return Returns the photolist.
	 */
	public List getPhotolist() {
		if (photolist == null) photolist = new ArrayList();
		return photolist;
	}

	/**
	 * @param photolist
	 *          The photolist to set.
	 */
	public void setPhotolist(List photolist) {
		this.photolist = photolist;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return Returns the currency_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}

	/**
	 * @return Returns the oHD_ID.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getOHD_ID() {
		return OHD_ID;
	}

	/**
	 * @param ohd_id
	 *          The oHD_ID to set.
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
	}

	/**
	 * for bdo use only
	 * 
	 * @return Returns the is_in_station.
	 */
	public int getIs_in_station() {
		return is_in_station;
	}

	/**
	 * @param is_in_station
	 *          The is_in_station to set.
	 */
	public void setIs_in_station(int is_in_station) {
		this.is_in_station = is_in_station;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}

	/**
	 * @return Returns the itemtype_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getItemtype_ID() {
		return itemtype_ID;
	}

	/**
	 * @param itemtype_ID
	 *          The itemtype_ID to set.
	 */
	public void setItemtype_ID(int itemtype_ID) {
		this.itemtype_ID = itemtype_ID;
	}

	/**
	 * @return Returns the bagnumber.
	 * 
	 * @hibernate.property type="integer"
	 *  
	 */
	public int getBagnumber() {
		return bagnumber;
	}

	/**
	 * @param bagnumber
	 *          The bagnumber to set.
	 */
	public void setBagnumber(int bagnumber) {
		this.bagnumber = bagnumber;
	}

	/**
	 * @return Returns the resolution.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Resolution"
	 *                        column="resolution_ID"
	 */
	public Resolution getResolution() {
		return resolution;
	}

	/**
	 * @param resolution
	 *          The resolution to set.
	 */
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return Returns the color.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *          The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return Returns the cost.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *          The cost to set.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDiscost() {
		if (getCost() != 0) return TracingConstants.DECIMALFORMAT.format(getCost());
		else return "";
	}

	public void setDiscost(String s) {
		setCost(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the damage.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getDamage() {
		return damage;
	}

	/**
	 * @param damage
	 *          The damage to set.
	 */
	public void setDamage(String damage) {
		this.damage = damage;
	}

	/**
	 * @return Returns the drafts.
	 * 
	 * @hibernate.property type="string" length="30"
	 */
	public String getDrafts() {
		return drafts;
	}

	/**
	 * @param drafts
	 *          The drafts to set.
	 */
	public void setDrafts(String drafts) {
		this.drafts = drafts;
	}

	/**
	 * @return Returns the item_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getItem_ID() {
		return Item_ID;
	}

	/**
	 * @param item_ID
	 *          The item_ID to set.
	 */
	public void setItem_ID(int item_ID) {
		Item_ID = item_ID;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="inventory_ID"
	 * @hibernate.key column="audit_item_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Item_Inventory"
	 * 
	 * @return Returns the items.
	 */
	
	public Set getInventory() {
		if (inventorylist == null)
			return null;
		return new LinkedHashSet(inventorylist);
	}

	/**
	 * @param items
	 *          The items to set.
	 */
	public void setInventory(Set inventory) {
		this.inventory = inventory;
		if (inventory != null) this.inventorylist = new ArrayList(inventory);
	}

	/**
	 * @return Returns the inventorylist.
	 */
	public List getInventorylist() {
		if (inventorylist == null)
			inventorylist = new ArrayList();
		return inventorylist;
	}

	/**
	 * @param inventorylist
	 *          The inventorylist to set.
	 */
	public void setInventorylist(List inventorylist) {
		this.inventorylist = inventorylist;
	}

	/**
	 * @return Returns the lvlofdamage.
	 * 
	 * @hibernate.property type="integer" length="4"
	 */
	public int getLvlofdamage() {
		return lvlofdamage;
	}

	/**
	 * @param lvlofdamage
	 *          The lvlofdamage to set.
	 */
	public void setLvlofdamage(int lvlofdamage) {
		this.lvlofdamage = lvlofdamage;
	}

	/**
	 * @return Returns the type.
	 * 
	 * @hibernate.property type="string" length="2" column="bagtype"
	 */
	public String getBagtype() {
		return bagtype;
	}

	/**
	 * @param type
	 *          The type to set.
	 */
	public void setBagtype(String bagtype) {
		this.bagtype = bagtype;
	}

	/**
	 * @return Returns the arrivedonairline_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 *  
	 */
	public String getArrivedonairline_ID() {
		return arrivedonairline_ID;
	}

	/**
	 * @param arrivedonairline_ID
	 *          The arrivedonairline_ID to set.
	 */
	public void setArrivedonairline_ID(String arrivedonairline_ID) {
		this.arrivedonairline_ID = arrivedonairline_ID;
	}

	/**
	 * @return Returns the arrivedondate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getArrivedondate() {
		return arrivedondate;
	}

	/**
	 * @param arrivedondate
	 *          The arrivedondate to set.
	 */
	public void setArrivedondate(Date arrivedondate) {
		this.arrivedondate = arrivedondate;
	}

	public void setDisarrivedondate(String s) {
		setArrivedondate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisarrivedondate() {
		return DateUtils.formatDate(getArrivedondate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the arrivedonflightnum.
	 * 
	 * @hibernate.property type="string" length="5"
	 */
	public String getArrivedonflightnum() {
		return arrivedonflightnum;
	}

	/**
	 * @param arrivedonflightnum
	 *          The arrivedonflightnum to set.
	 */
	public void setArrivedonflightnum(String arrivedonflightnum) {
		this.arrivedonflightnum = arrivedonflightnum;
	}

	/**
	 * @return Returns the claimchecknum.
	 * 
	 * @hibernate.property type="string" length="13"
	 */
	public String getClaimchecknum() {
		return claimchecknum;
	}

	/**
	 * @param claimchecknum
	 *          The claimchecknum to set.
	 */
	public void setClaimchecknum(String claimchecknum) {
		if (claimchecknum != null) claimchecknum = TracerUtils.removeSpaces(claimchecknum);
		this.claimchecknum = claimchecknum;
	}

	/**
	 * @return Returns the fnameonbag.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getFnameonbag() {
		return fnameonbag;
	}

	/**
	 * @param fnameonbag
	 *          The fnameonbag to set.
	 */
	public void setFnameonbag(String fnameonbag) {
		this.fnameonbag = fnameonbag;
	}

	/**
	 * @return Returns the lnameonbag.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getLnameonbag() {
		return lnameonbag;
	}

	/**
	 * @param lnameonbag
	 *          The lnameonbag to set.
	 */
	public void setLnameonbag(String lnameonbag) {
		this.lnameonbag = lnameonbag;
	}

	/**
	 * @return Returns the mnameonbag.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getMnameonbag() {
		return mnameonbag;
	}

	/**
	 * @param mnameonbag
	 *          The mnameonbag to set.
	 */
	public void setMnameonbag(String mnameonbag) {
		this.mnameonbag = mnameonbag;
	}

	/**
	 * @return Returns the resolutiondesc.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getResolutiondesc() {
		return resolutiondesc;
	}

	/**
	 * @param resolutiondesc
	 *          The resolutiondesc to set.
	 */
	public void setResolutiondesc(String resolutiondesc) {
		this.resolutiondesc = resolutiondesc;
	}

	/**
	 * @return Returns the manufacturer_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getManufacturer_ID() {
		return manufacturer_ID;
	}

	/**
	 * @param manufacturer_ID
	 *          The manufacturer_ID to set.
	 */
	public void setManufacturer_ID(int manufacturer_ID) {
		this.manufacturer_ID = manufacturer_ID;
	}

	/**
	 * @return Returns the manufacturer_other.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getManufacturer_other() {
		return manufacturer_other;
	}

	/**
	 * @param manufacturer_other
	 *          The manufacturer_other to set.
	 */
	public void setManufacturer_other(String manufacturer_other) {
		this.manufacturer_other = manufacturer_other;
	}

	public String getManufacturer() {
		String ret = "";

		if (this.getManufacturer_ID() > 0) {
			if (this.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
				if (this.getManufacturer_other() != null && this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				Manufacturer manuf = TracerUtils.getManufacturer(this.getManufacturer_ID());
				if (manuf != null) {
					ret = manuf.getDescription();
				}
			}
		}

		return ret;
	}

	/**
	 * @return Returns the xdescelement_ID_1.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getXdescelement_ID_1() {
		return xdescelement_ID_1;
	}

	/**
	 * @param xdescelement_ID_1
	 *          The xdescelement_ID_1 to set.
	 */
	public void setXdescelement_ID_1(int xdescelement_ID_1) {
		this.xdescelement_ID_1 = xdescelement_ID_1;
	}

	/**
	 * @return Returns the xdescelement_ID_2.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getXdescelement_ID_2() {
		return xdescelement_ID_2;
	}

	/**
	 * @param xdescelement_ID_2
	 *          The xdescelement_ID_2 to set.
	 */
	public void setXdescelement_ID_2(int xdescelement_ID_2) {
		this.xdescelement_ID_2 = xdescelement_ID_2;
	}

	/**
	 * @return Returns the xdescelement_ID_3.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getXdescelement_ID_3() {
		return xdescelement_ID_3;
	}

	/**
	 * @param xdescelement_ID_3
	 *          The xdescelement_ID_3 to set.
	 */
	public void setXdescelement_ID_3(int xdescelement_ID_3) {
		this.xdescelement_ID_3 = xdescelement_ID_3;
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	// for passenger view only pages

	public String getManuname() {
		if (manufacturer_ID <= 0) return "";
		Manufacturer manu = TracerUtils.getManufacturer(manufacturer_ID);
		return manu.getDescription();
	}

	public String getXdescelement1() {
		if (xdescelement_ID_1 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getDescription();
	}

	public String getXdescelement2() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getDescription();
	}

	public String getXdescelement3() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getDescription();
	}
	
	public String getXdescelement1Key() {
		if (xdescelement_ID_1 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getKey();
	}

	public String getXdescelement2Key() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getKey();
	}

	public String getXdescelement3Key() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getKey();
	}

	/**
	 * @hibernate.property type="string" length="6"
	 */
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	/**
	 * @hibernate.property type="string" length="12"
	 */
	public String getExpediteTagNum() {
		return expediteTagNum;
	}

	public void setExpediteTagNum(String expediteTagNum) {
		this.expediteTagNum = expediteTagNum;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getSpecialCondition() {
		return specialCondition;
	}

	public void setSpecialCondition(int specialCondition) {
		this.specialCondition = specialCondition;
	}
	
	public String getDispSpecialCondition() {
		switch (getSpecialCondition()) {
			case TracingConstants.SPECIAL_CONDITION_OVERWEIGHT:
				return TracingConstants.SPECIAL_CONDITION_NAME_OVERWEIGHT;
			case TracingConstants.SPECIAL_CONDITION_OVERSIZED:
				return TracingConstants.SPECIAL_CONDITION_NAME_OVERSIZED;
			case TracingConstants.SPECIAL_CONDITION_BOTH:
				return TracingConstants.SPECIAL_CONDITION_NAME_BOTH;
			default:
				return "";
		}
	}
	
}