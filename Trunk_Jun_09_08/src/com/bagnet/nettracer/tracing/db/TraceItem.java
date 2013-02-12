/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.CurrencyUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Item"
 */
public class TraceItem implements Serializable {
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
	private String fnameonbag;
	private String mnameonbag;
	private String lnameonbag;
	private String externaldesc;
	private String arrivedonflightnum;
	private String arrivedonairline_ID;
	private Date arrivedondate;
	private Date purchaseDate;
	private TraceIncident incident;

	private Set inventory;
	private List inventorylist;

	private String OHD_ID; // ohd_id matched with (for mishandled only)
	private String tempOHD_ID;
	private int is_in_station; // is the matched ohd in station?

	private double bag_weight;
	private String bag_weight_unit;

	/**
	 * @return Returns the bag_weight;
	 * 
	 * @hibernate.property type="double"
	 */
	public double getBag_weight() {
		return bag_weight;
	}

	public void setBag_weight(double bag_weight) {
		this.bag_weight = roundToTwoDecimals(bag_weight);
	}

	/**
	 * @return Returns the bag_weight_unit;
	 * 
	 * @hibernate.property type="string"
	 */
	public String getBag_weight_unit() {
		return bag_weight_unit;
	}

	public void setBag_weight_unit(String bag_weight_unit) {
		this.bag_weight_unit = bag_weight_unit;
	}


	public String getDispstatus() {
		String ret = "";

		if (status != null)
			ret = status.getKey();

		return ret;
	}

	public String getCachedManufacturerDescription() {
		String ret = "";

		if (this.getManufacturer_ID() > 0) {
			if (this.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
				if (this.getManufacturer_other() != null
						&& this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				ret = TracerUtils.getCachedManufacturerDescription(this
						.getManufacturer_ID());
			}
		}

		return ret;
	}

	public String getManufacturer() {
		String ret = "";

		if (this.getManufacturer_ID() > 0) {
			if (this.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
				if (this.getManufacturer_other() != null
						&& this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				Manufacturer manuf = TracerUtils.getManufacturer(this
						.getManufacturer_ID());
				if (manuf != null) {
					ret = manuf.getDescription();
				}
			}
		}

		return ret;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID" fetch="select"
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
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
	 *            The oHD_ID to set.
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
	}

	public String getTempOHD_ID() {
		return tempOHD_ID;
	}

	public void setTempOHD_ID(String tempOHD_ID) {
		this.tempOHD_ID = tempOHD_ID;
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
	 *            The is_in_station to set.
	 */
	public void setIs_in_station(int is_in_station) {
		this.is_in_station = is_in_station;
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
	 *            The itemtype_ID to set.
	 */
	public void setItemtype_ID(int itemtype_ID) {
		this.itemtype_ID = itemtype_ID;
	}
	
	/**
	 * @param externaldesc
	 *          The externaldesc to set.
	 */
	public void setExternaldesc(String externaldesc) {
		this.externaldesc = externaldesc;
	}
	
	/**
	 * @return Returns the externaldesc.
	 * 
	 * @hibernate.property type="string" length="50"
	 *  
	 */
	public String getExternaldesc() {
		return externaldesc;
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
	 *            The bagnumber to set.
	 */
	public void setBagnumber(int bagnumber) {
		this.bagnumber = bagnumber;
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
	 *            The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return Returns the item_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Item_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="item_0"
	 * 
	 */
	public int getItem_ID() {
		return Item_ID;
	}

	/**
	 * @param item_ID
	 *            The item_ID to set.
	 */
	public void setItem_ID(int item_ID) {
		Item_ID = item_ID;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="inventory_ID"
	 * @hibernate.key column="item_ID"
	 * @hibernate.one-to-many 
	 *                        class="com.bagnet.nettracer.tracing.db.TraceItem_Inventory"
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
	 *            The items to set.
	 */
	public void setInventory(Set inventory) {
		this.inventory = inventory;
		if (inventory != null)
			this.inventorylist = new ArrayList(inventory);
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
	 *            The inventorylist to set.
	 */
	public void setInventorylist(List inventorylist) {
		this.inventorylist = inventorylist;
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
	 *            The type to set.
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
	 *            The arrivedonairline_ID to set.
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
	 *            The arrivedondate to set.
	 */
	public void setArrivedondate(Date arrivedondate) {
		this.arrivedondate = arrivedondate;
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
	 *            The arrivedonflightnum to set.
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
	 *            The claimchecknum to set.
	 */
	public void setClaimchecknum(String claimchecknum) {
		if (claimchecknum != null)
			claimchecknum = TracerUtils.removeSpaces(claimchecknum);
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
	 *            The fnameonbag to set.
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
	 *            The lnameonbag to set.
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
	 *            The mnameonbag to set.
	 */
	public void setMnameonbag(String mnameonbag) {
		this.mnameonbag = mnameonbag;
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
	 *            The manufacturer_ID to set.
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
	 *            The manufacturer_other to set.
	 */
	public void setManufacturer_other(String manufacturer_other) {
		this.manufacturer_other = manufacturer_other;
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
	 *            The xdescelement_ID_1 to set.
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
	 *            The xdescelement_ID_2 to set.
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
	 *            The xdescelement_ID_3 to set.
	 */
	public void setXdescelement_ID_3(int xdescelement_ID_3) {
		this.xdescelement_ID_3 = xdescelement_ID_3;
	}

	/**
	 * @return Returns the original purchase date
	 * 
	 * @hibernate.property type="date" column="purchase_date"
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getManuname() {
		if (manufacturer_ID <= 0)
			return "";
		Manufacturer manu = TracerUtils.getManufacturer(manufacturer_ID);
		return manu.getDescription();
	}

	public String getXdescelement1() {
		if (xdescelement_ID_1 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getDescription();
	}

	public String getXdescelement2() {
		if (xdescelement_ID_2 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getDescription();
	}

	public String getXdescelement3() {
		if (xdescelement_ID_3 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getDescription();
	}

	public String getX1() {
		if (xdescelement_ID_1 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getCode();
	}

	public String getX2() {
		if (xdescelement_ID_2 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getCode();
	}

	public String getX3() {
		if (xdescelement_ID_3 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getCode();
	}

	public String getXdescelement1Key() {
		if (xdescelement_ID_1 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getKey();
	}

	public String getXdescelement2Key() {
		if (xdescelement_ID_2 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getKey();
	}

	public String getXdescelement3Key() {
		if (xdescelement_ID_3 <= 0)
			return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getKey();
	}

	private double roundToTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}

}