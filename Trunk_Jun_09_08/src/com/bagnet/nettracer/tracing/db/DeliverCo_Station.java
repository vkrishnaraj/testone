package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="deliverco_station"
 * @hibernate.cache usage="read-write"
 */
public class DeliverCo_Station implements Serializable {
	private int deliverco_station_ID;
	private int station_ID;
	private DeliverCompany delivercompany;

	/**
	 * @return Returns the deliverco_station_ID.
	 * 
	 * @hibernate.id generator-class = "native" type="integer"
	 * @hibernate.generator-param name="sequence" value="deliverco_station_0"
	 */
	public int getDeliverco_station_ID() {
		return deliverco_station_ID;
	}

	/**
	 * @param deliverco_station_ID
	 *          The deliverco_station_ID to set.
	 */
	public void setDeliverco_station_ID(int deliverco_station_ID) {
		this.deliverco_station_ID = deliverco_station_ID;
	}

	/**
	 * @return Returns the station.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getStation_ID() {
		return station_ID;
	}
	/**
	 * @param station_ID The station_ID to set.
	 */
	public void setStation_ID(int station_ID) {
		this.station_ID = station_ID;
	}

	/**
	 * @return Returns the delivercompany.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.DeliverCompany"
	 *                        column="delivercompany_ID"
	 */
	public DeliverCompany getDelivercompany() {
		return delivercompany;
	}


	/**
	 * @param delivercompany
	 *          The delivercompany to set.
	 */
	public void setDelivercompany(DeliverCompany delivercompany) {
		this.delivercompany = delivercompany;
	}
}