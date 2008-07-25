package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Lz"
 */
public class Lz implements Serializable {
	private int lz_id;
	private boolean is_default;
	private double percent_load;
	private Station station;
	private boolean isUsed;
	private String companyCode_ID;
	
	/**
	 * @return Returns the lz_id.
	 * 
	 * @hibernate.id generator-class = "native" type="integer" column="lz_id"
	 * @hibernate.generator-param name="sequence" value="Lz_0"
	 */
	
	
	
	public int getLz_ID() {
		return lz_id;
	}

	/**
	 * @param lz_id
	 *          The deliverco_station_ID to set.
	 */
	public void setLz_ID(int lz_ID) {
		this.lz_id = lz_ID;

	}
	
	/**
	 * @return Returns the percent.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getPercent_load() {
		return percent_load;
	}

	/**
	 * @param percent
	 *          The percent to set.
	 */
	public void setPercent_load(double percent) {
		this.percent_load = percent;
	}
	
	/**
	 * @return Returns the station.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID" foreign-key="station_ID"
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	
	/**
	 * @return Returns the companyCode_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCompanyCode_ID() {
		return companyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		this.companyCode_ID = companyCode_ID;
	}

	/**
	 * @return Returns whether the station is the default incident LZ.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isIs_default() {
		return is_default;
	}
	/**
	 * @param is_default The is_default to set.
	 */
	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}
	
	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public boolean equals (Object a) {
		
		if (!(a instanceof Lz))
			return false;
				
		Lz b = (Lz) a;
		if (this.lz_id == b.getLz_ID())
			return true;
		return false;
	}
	
	public int hashCode() {
		return this.lz_id;
	}
}