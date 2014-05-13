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

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.BDOUtils;
import com.bagnet.nettracer.tracing.utils.CurrencyUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Item"
 */
public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5712336990487032404L;
	private int Item_ID;
	private int bagnumber;
	private int itemtype_ID;
	private Status status;
	private String claimchecknum; // ohd claimcheck matched with (for mishandled
	// only)
	private String claimchecknum_leading;
	private String claimchecknum_ticketingcode;
	private String claimchecknum_carriercode;
	private String claimchecknum_bagnumber;
	private String color;
	private String bagtype;
	private int childRestraint;
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
	private String externaldesc;
	private Date arrivedondate;
	private Date purchaseDate;
	private Incident incident;
	private BDO bdo;
	private Set<Item_BDO> item_bdo;
	private String _DATEFORMAT;
	//private int categorytype_ID = 0;

	private Set<Item_Photo> photoes;
	private Set<Item_Inventory> inventory;

	private List<Item_Photo> photolist;
	private List<Item_Inventory> inventorylist;

	private String OHD_ID; // ohd_id matched with (for mishandled only)
	private String tempOHD_ID;
	private int is_in_station; // is the matched ohd in station?

	private String locale;
	
	private int wt_bag_selected;
	private boolean isItemOrBdoCanceled;
	
	private int replacementBagIssued;
	
	private String posId;
	private String expediteTagNum;

	private int specialCondition;
	private int other;
	private boolean noAddFees;
	

	private int assistDeviceType;
	private String assistDeviceCheck;
	
	private int lossCode;
	private Station faultStation;
	private boolean bdoChosen = false;
	
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

	private double bag_weight;
	private String bag_weight_unit;
	
	
	/**
	 * @return Returns the wt_bag_selected;.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getWt_bag_selected() {
		return wt_bag_selected;
	}

	/**
	 * @param wt_bag_selected the wt_bag_selected to set
	 */
	public void setWt_bag_selected(int wt_bag_selected) {
		this.wt_bag_selected = wt_bag_selected;
	}

	/**
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *          The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Item() {
		super();
	}

	public String getDispcost() {
		return "" + cost;
	}

	public Item(int itemtype) {
		this.itemtype_ID = itemtype;
	}

	public JRBeanCollectionDataSource getPhotosForReport() {
		if (photoes == null || photoes.size() < 1)
			return null;

		return new JRBeanCollectionDataSource(new ArrayList<Item_Photo>(photoes));
	}
	
	public JRBeanCollectionDataSource getInventoriesForReport() {
		if (inventory == null || inventory.size() < 1)
			return null;

		return new JRBeanCollectionDataSource(new ArrayList<Item_Inventory>(inventory));
	}


	public String getDisplvlofdamage() {
		String ret = "";

		if (lvlofdamage == 0)
			ret = "Minor";
		else if (lvlofdamage == 1)
			ret = "Major";
		else if (lvlofdamage == 2)
			ret = "Complete";

		return ret;
	}

	public String getCurrency() {
		String ret = "";

		if (currency_ID != null && currency_ID.length() > 0 && !currency_ID.equals("0")) {
			ret = CurrencyUtils.getCurrency(currency_ID).getDescription();
		}

		return ret;
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
				if (this.getManufacturer_other() != null && this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				ret = TracerUtils.getCachedManufacturerDescription(this.getManufacturer_ID());
			}
		}

		return ret;
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
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID" not-null="true"
	 */
	public Incident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	

//	/**
//	 * @return Returns the bdo.
//	 * 
//	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.BDO"
//	 *                        column="bdo_ID" not-null="false"
//	 */
	public BDO getBdo() {
		if (item_bdo == null || item_bdo.size() == 0) {
			return null;
		} else {
			Iterator<Item_BDO> i = item_bdo.iterator();
			Item_BDO last = null;
			while(i.hasNext()) {
				last = (Item_BDO) i.next(); 
			}
			
			bdo = last.getBdo();
			isItemOrBdoCanceled = last.isCanceled() || bdo.isCanceled();
			return bdo;
		}
	}
	
	public boolean isBdoEntryCanceled() {
		if (bdo == null) {
			getBdo();
		} 
		return isItemOrBdoCanceled;
	}
	
//	/**
//	 * @param bdo The bdo to set.
//	 */
//	public void setBdo(BDO bdo) {
//		this.bdo = bdo;
//	}

	/**
	 * @return Returns the photoes.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="photo_ID"
	 * @hibernate.key column="item_ID" not-null="false"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item_Photo"
	 */
	public Set<Item_Photo> getPhotoes() {
		if (photolist == null)
			return null;
		return new LinkedHashSet<Item_Photo>(photolist);
	}

	/**
	 * @param photoes
	 *          The photoes to set.
	 */
	public void setPhotoes(Set<Item_Photo> photoes) {
		this.photoes = photoes;
		if (photoes != null) this.photolist = new ArrayList<Item_Photo>(photoes);
	}

	/**
	 * @return Returns the photolist.
	 */
	public List<Item_Photo> getPhotolist() {
		if (photolist == null)
			photolist = new ArrayList<Item_Photo>();
		return photolist;
	}

	/**
	 * @param photolist
	 *          The photolist to set.
	 */
	public void setPhotolist(List<Item_Photo> photolist) {
		this.photolist = photolist;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"  fetch="select"
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
	 *                        column="resolution_ID" not-null="false" fetch="select"
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
		if (getCost() != 0)
			return TracingConstants.DECIMALFORMAT.format(getCost());
		else
			return "";
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
	 *          The item_ID to set.
	 */
	public void setItem_ID(int item_ID) {
		Item_ID = item_ID;
	}

		 	
	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="inventory_ID"
	 * @hibernate.key column="item_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item_Inventory"
	 * 
	 * @return Returns the items.
	 */
	
	public Set<Item_Inventory> getInventory() {
		if (inventorylist == null)
			return null;
		return new LinkedHashSet<Item_Inventory>(inventorylist);
	}

	/**
	 * @param items
	 *          The items to set.
	 */
	public void setInventory(Set<Item_Inventory> inventory) {
		this.inventory = inventory;
		if (inventory != null) this.inventorylist = new ArrayList<Item_Inventory>(inventory);
	}

	/**
	 * @return Returns the inventorylist.
	 */
	public List<Item_Inventory> getInventorylist() {
		if (inventorylist == null)
			inventorylist = new ArrayList<Item_Inventory>();
		return inventorylist;
	}

	/**
	 * @param inventorylist
	 *          The inventorylist to set.
	 */
	public void setInventorylist(List<Item_Inventory> inventorylist) {
		this.inventorylist = inventorylist;
	}
	
	/**
	 * @return Returns the lvlofdamage.
	 * 
	 * @hibernate.property type="integer"
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
	 * @return Returns the childRestraint.
	 * 
	 * @hibernate.property type="integer" length="2" column="childrestraint"
	 */
	public int getChildRestraint() {
		return childRestraint;
	}

	/**
	 * @param childRestraint
	 *          The childRestraint to set.
	 */
	public void setChildRestraint(int childRestraint) {
		this.childRestraint = childRestraint;
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
		if (claimchecknum != null)
			claimchecknum = TracerUtils.removeSpaces(claimchecknum);
		this.claimchecknum = claimchecknum;
		setClaimSearchParams(claimchecknum);
	}
	
	private void setClaimSearchParams(String claimchecknum) {
		if (claimchecknum != null && claimchecknum.length() > 3 && claimchecknum.length() < 12) {
			String leading = null;
			String ticketing = null;
			String carrier = null;
			String bagnum = null;
			
			/*
	    	 * Check if claimchecknum is Untagged Bagtag and mark carrier and bagnum if so	
	    	 */
			if(claimchecknum.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
				ticketing = claimchecknum.substring(0, 3);
				bagnum = claimchecknum.substring(3);
			} else if (claimchecknum.length() == 8) {
				carrier = claimchecknum.substring(0, 2);
				ticketing = LookupAirlineCodes.getThreeDigitTicketingCode(carrier);
				bagnum = claimchecknum.substring(2);
			} else if (claimchecknum.length() == 9) {
				ticketing = claimchecknum.substring(0, 3);
				carrier = LookupAirlineCodes.getTwoLetterAirlineCode(ticketing);
				bagnum = claimchecknum.substring(3);
			} else if (claimchecknum.length() == 10) {
				leading = claimchecknum.substring(0, 1);
				ticketing = claimchecknum.substring(1, 4);
				carrier = LookupAirlineCodes.getTwoLetterAirlineCode(ticketing);
				bagnum = claimchecknum.substring(4);
			}
			
			if (leading != null && leading.matches("^[0-9]{1}$")) {
				setClaimchecknum_leading(leading);
			}
			setClaimchecknum_carriercode(carrier);
			setClaimchecknum_ticketingcode(ticketing);
			setClaimchecknum_bagnumber(bagnum);
		}
	}

	/**
	 * @return Returns the claimchecknum_leading.
	 * 
	 * @hibernate.property type="string" length="1"
	 */
	public String getClaimchecknum_leading() {
		return claimchecknum_leading;
	}

	/**
	 * @param claimchecknum_leading
	 *          The claimchecknum_leading to set.
	 */
	public void setClaimchecknum_leading(String claimchecknum_leading) {
		this.claimchecknum_leading = claimchecknum_leading;
	}

	/**
	 * @return Returns the claimchecknum_ticketingcode.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getClaimchecknum_ticketingcode() {
		return claimchecknum_ticketingcode;
	}

	/**
	 * @param claimchecknum_ticketingcode
	 *          The claimchecknum_ticketingcode to set.
	 */
	public void setClaimchecknum_ticketingcode(String claimchecknum_ticketingcode) {
		this.claimchecknum_ticketingcode = claimchecknum_ticketingcode;
	}

	/**
	 * @return Returns the claimchecknum_carriercode.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getClaimchecknum_carriercode() {
		return claimchecknum_carriercode;
	}

	/**
	 * @param claimchecknum_carriercode
	 *          The claimchecknum_carriercode to set.
	 */
	public void setClaimchecknum_carriercode(String claimchecknum_carriercode) {
		this.claimchecknum_carriercode = claimchecknum_carriercode;
	}

	/**
	 * @return Returns the claimchecknum_bagnumber.
	 * 
	 * @hibernate.property type="string" length="6"
	 */
	public String getClaimchecknum_bagnumber() {
		return claimchecknum_bagnumber;
	}

	/**
	 * @param claimchecknum_bagnumber
	 *          The claimchecknum_bagnumber to set.
	 */
	public void setClaimchecknum_bagnumber(String claimchecknum_bagnumber) {
		this.claimchecknum_bagnumber = claimchecknum_bagnumber;
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
	
	public String getX1() {
		if (xdescelement_ID_1 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getCode();
	}

	public String getX2() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getCode();
	}

	public String getX3() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getCode();
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
	 * @hibernate.set cascade="all" inverse="true" order-by="bdo_ID"
	 * @hibernate.key column="item_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item_BDO"
	 * @hibernate.index column="bdo_ID" 
	 * @return Returns the items.
	 */
	public Set<Item_BDO> getItem_bdo() {
		return item_bdo;
	}

	public void setItem_bdo(Set<Item_BDO> item_bdo) {
		this.item_bdo = item_bdo;
	}
	
	private double roundToTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
	
	/** 
	 * @hibernate.property type="integer"
	 */
	public int getReplacementBagIssued() {
		return replacementBagIssued;
	}

	public void setReplacementBagIssued(int replacementBagIssued) {
		this.replacementBagIssued = replacementBagIssued;
	}

	/**
	 * @hibernate.property type="string" length="8"
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

	/**
	 * @return The Category of the Item
	 * 
	 * @hibernate.property type="int"
	 */
	public int getOther() {
		return other;
	}

	/**
	 * @param other
	 *          The other to set.
	 */
	public void setOther(int other) {
		this.other = other;
	}

	/**
	 * @hibernate.property type="boolean"
	 */
	public boolean isNoAddFees() {
		return noAddFees;
	}

	public void setNoAddFees(boolean noAddFees) {
		this.noAddFees = noAddFees;
	}


	/**
	 * @return The AssistDeviceType of the Item if the bag is type 94 or 95
	 * 
	 * @hibernate.property type="int"
	 */
	public int getAssistDeviceType() {
		return assistDeviceType;
	}

	/**
	 * @param assistDeviceType
	 *          The assistDeviceType to set.
	 */
	public void setAssistDeviceType(int assistDeviceType) {
		this.assistDeviceType = assistDeviceType;
	}

	/**
	 * @return If the assistDevice was tag checked
	 * 
	 * @hibernate.property type="string" length="12"
	 */
	public String getAssistDeviceCheck() {
		return assistDeviceCheck;
	}


	/**
	 * @param assistDeviceTypeCheck
	 *          The assistDeviceTypeCheck to set.
	 */
	public void setAssistDeviceCheck(String assistDeviceCheck) {
		this.assistDeviceCheck = assistDeviceCheck;
	}

	/**
	 * @return Returns the lossCode
	 * 
	 * @hibernate.property type="int"
	 */
	public int getLossCode() {
		return lossCode;
	}
	
	/**
	 * @param lossCode
	 *          The lossCode to set.
	 */
	public void setLossCode(int lossCode) {
		this.lossCode = lossCode;
	}

	/**
	 * @return Returns the faultStation.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station" column="faultStation_id" not-found="ignore"
	 */
	public Station getFaultStation() {
		return faultStation;
	}
	/**
	 * @param faultStation
	 *          The faultStation to set.
	 */
	public void setFaultStation(Station faultStation) {
		this.faultStation=faultStation;
	}
	

	public int getFaultStation_id() {
		if(getFaultStation()==null)
			return 0;
		return getFaultStation().getStation_ID();

	}

	public void setFaultStation_id(int station_id) {
		if(station_id!=0){
			Station s=StationBMO.getStation(station_id);
			if(s!=null)
				setFaultStation(s);
			else 
				setFaultStation(null);
		} else {
			setFaultStation(null);
		}
	}
	
	/**
	 * Method to get the number of NonCancelled BDOs the Item is associated with
	 */
	public long countBdos(){
		return BDOUtils.findBDOByItemCount(getItem_ID());
	}
	
	/**
	 * Method to check status of item to see if it's delivered.
	 * If status is unavailable, it is assumed the item is not delivered
	 */
	public boolean isNotDelivered(){
		if(getStatus()!=null)
			return getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY;
		return true;
	}

	public boolean isBdoChosen() {
		return bdoChosen;
	}

	public void setBdoChosen(boolean bdoChosen) {
		this.bdoChosen = bdoChosen;
	}
}