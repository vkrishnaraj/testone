package aero.nettracer.serviceprovider.wt_1_0.common;

import java.util.Calendar;

public class Bdo {
	private String stationCode;
	private String airlineCode;
	private String ahlId;
	private String originationStationCode;
	private String deliveryStationCode;
	private String deliveryCompany;
	private Calendar deliveryDate;
	private String address1;
	private String address2;
	private Passenger[] passenger;
	private Item[] item;

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getAhlId() {
		return ahlId;
	}

	public void setAhlId(String ahlId) {
		this.ahlId = ahlId;
	}

	public String getOriginationStationCode() {
		return originationStationCode;
	}

	public void setOriginationStationCode(String originationStationCode) {
		this.originationStationCode = originationStationCode;
	}

	public String getDeliveryStationCode() {
		return deliveryStationCode;
	}

	public void setDeliveryStationCode(String deliveryStationCode) {
		this.deliveryStationCode = deliveryStationCode;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public Calendar getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Passenger[] getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger[] passenger) {
		this.passenger = passenger;
	}

	public Item[] getItem() {
		return item;
	}

	public void setItem(Item[] item) {
		this.item = item;
	}

}
