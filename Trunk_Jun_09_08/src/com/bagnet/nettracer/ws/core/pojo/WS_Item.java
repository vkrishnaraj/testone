package com.bagnet.nettracer.ws.core.pojo;


public class WS_Item {
	private int bagnumber;
	private String itemtype;
	private String bagstatus;
	private String claimchecknum; // ohd claimcheck matched with (for mishandled
	// only)
	private String color;
	private String bagtype;
	private String xdescelement_1;
	private String xdescelement_2;
	private String xdescelement_3;
	private String manufacturer;
	private int lvlofdamage;
	private String damage;
	private String resolutionstatus;
	private String resolutiondesc;
	private double cost;
	private String drafts;
	private String currency_ID;
	private String fnameonbag;
	private String mnameonbag;
	private String lnameonbag;
	private String arrivedonflightnum;
	private String arrivedonairline_ID;
	private String arrivedondate;

	//private BDO bdo;

	//private Set photoes;	// no photos
	
	private WS_Inventory[] inventories = null;


	private String matched_ohd; // ohd_id matched with


	/**
	 * @return the bagnumber
	 */
	public int getBagnumber() {
		return bagnumber;
	}


	/**
	 * @param bagnumber the bagnumber to set
	 */
	public void setBagnumber(int bagnumber) {
		this.bagnumber = bagnumber;
	}


	/**
	 * @return the itemtype
	 */
	public String getItemtype() {
		return itemtype;
	}


	/**
	 * @param itemtype the itemtype to set
	 */
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}


	/**
	 * @return the bagstatus
	 */
	public String getBagstatus() {
		return bagstatus;
	}


	/**
	 * @param bagstatus the bagstatus to set
	 */
	public void setBagstatus(String bagstatus) {
		this.bagstatus = bagstatus;
	}


	/**
	 * @return the claimchecknum
	 */
	public String getClaimchecknum() {
		return claimchecknum;
	}


	/**
	 * @param claimchecknum the claimchecknum to set
	 */
	public void setClaimchecknum(String claimchecknum) {
		this.claimchecknum = claimchecknum;
	}


	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}


	/**
	 * @return the bagtype
	 */
	public String getBagtype() {
		return bagtype;
	}


	/**
	 * @param bagtype the bagtype to set
	 */
	public void setBagtype(String bagtype) {
		this.bagtype = bagtype;
	}


	/**
	 * @return the xdescelement_1
	 */
	public String getXdescelement_1() {
		return xdescelement_1;
	}


	/**
	 * @param xdescelement_1 the xdescelement_1 to set
	 */
	public void setXdescelement_1(String xdescelement_1) {
		this.xdescelement_1 = xdescelement_1;
	}


	/**
	 * @return the xdescelement_2
	 */
	public String getXdescelement_2() {
		return xdescelement_2;
	}


	/**
	 * @param xdescelement_2 the xdescelement_2 to set
	 */
	public void setXdescelement_2(String xdescelement_2) {
		this.xdescelement_2 = xdescelement_2;
	}


	/**
	 * @return the xdescelement_3
	 */
	public String getXdescelement_3() {
		return xdescelement_3;
	}


	/**
	 * @param xdescelement_3 the xdescelement_3 to set
	 */
	public void setXdescelement_3(String xdescelement_3) {
		this.xdescelement_3 = xdescelement_3;
	}


	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}


	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	/**
	 * @return the lvlofdamage
	 */
	public int getLvlofdamage() {
		return lvlofdamage;
	}


	/**
	 * @param lvlofdamage the lvlofdamage to set
	 */
	public void setLvlofdamage(int lvlofdamage) {
		this.lvlofdamage = lvlofdamage;
	}


	/**
	 * @return the damage
	 */
	public String getDamage() {
		return damage;
	}


	/**
	 * @param damage the damage to set
	 */
	public void setDamage(String damage) {
		this.damage = damage;
	}


	/**
	 * @return the resolutionstatus
	 */
	public String getResolutionstatus() {
		return resolutionstatus;
	}


	/**
	 * @param resolutionstatus the resolutionstatus to set
	 */
	public void setResolutionstatus(String resolutionstatus) {
		this.resolutionstatus = resolutionstatus;
	}


	/**
	 * @return the resolutiondesc
	 */
	public String getResolutiondesc() {
		return resolutiondesc;
	}


	/**
	 * @param resolutiondesc the resolutiondesc to set
	 */
	public void setResolutiondesc(String resolutiondesc) {
		this.resolutiondesc = resolutiondesc;
	}


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}


	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}


	/**
	 * @return the drafts
	 */
	public String getDrafts() {
		return drafts;
	}


	/**
	 * @param drafts the drafts to set
	 */
	public void setDrafts(String drafts) {
		this.drafts = drafts;
	}


	/**
	 * @return the currency_ID
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}


	/**
	 * @param currency_ID the currency_ID to set
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}


	/**
	 * @return the fnameonbag
	 */
	public String getFnameonbag() {
		return fnameonbag;
	}


	/**
	 * @param fnameonbag the fnameonbag to set
	 */
	public void setFnameonbag(String fnameonbag) {
		this.fnameonbag = fnameonbag;
	}


	/**
	 * @return the mnameonbag
	 */
	public String getMnameonbag() {
		return mnameonbag;
	}


	/**
	 * @param mnameonbag the mnameonbag to set
	 */
	public void setMnameonbag(String mnameonbag) {
		this.mnameonbag = mnameonbag;
	}


	/**
	 * @return the lnameonbag
	 */
	public String getLnameonbag() {
		return lnameonbag;
	}


	/**
	 * @param lnameonbag the lnameonbag to set
	 */
	public void setLnameonbag(String lnameonbag) {
		this.lnameonbag = lnameonbag;
	}


	/**
	 * @return the arrivedonflightnum
	 */
	public String getArrivedonflightnum() {
		return arrivedonflightnum;
	}


	/**
	 * @param arrivedonflightnum the arrivedonflightnum to set
	 */
	public void setArrivedonflightnum(String arrivedonflightnum) {
		this.arrivedonflightnum = arrivedonflightnum;
	}


	/**
	 * @return the arrivedonairline_ID
	 */
	public String getArrivedonairline_ID() {
		return arrivedonairline_ID;
	}


	/**
	 * @param arrivedonairline_ID the arrivedonairline_ID to set
	 */
	public void setArrivedonairline_ID(String arrivedonairline_ID) {
		this.arrivedonairline_ID = arrivedonairline_ID;
	}


	/**
	 * @return the arrivedondate
	 */
	public String getArrivedondate() {
		return arrivedondate;
	}


	/**
	 * @param arrivedondate the arrivedondate to set
	 */
	public void setArrivedondate(String arrivedondate) {
		this.arrivedondate = arrivedondate;
	}


	/**
	 * @return the inventories
	 */
	public WS_Inventory[] getInventories() {
		return inventories;
	}


	/**
	 * @param inventories the inventories to set
	 */
	public void setInventories(WS_Inventory[] inventories) {
		this.inventories = inventories;
	}


	/**
	 * @return the matched_ohd
	 */
	public String getMatched_ohd() {
		return matched_ohd;
	}


	/**
	 * @param matched_ohd the matched_ohd to set
	 */
	public void setMatched_ohd(String matched_ohd) {
		this.matched_ohd = matched_ohd;
	}
	
	

}
