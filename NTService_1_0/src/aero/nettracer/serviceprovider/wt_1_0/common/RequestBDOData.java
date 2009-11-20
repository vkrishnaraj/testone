package aero.nettracer.serviceprovider.wt_1_0.common;


public class RequestBDOData {

	private String bdoId;
	private String stationCode;
	private String airlineCode;
	private String ahlId;
	private String originationStationCode;
	private String deliveryStationCode;
	private String deliveryCompany;
	private String address1;
	private String address2;
	private Passenger[] passengers;
	private Item[] items;
	private String deliveryComments;
	private Agent agent;
	
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

	public String getBdoId() {
  	return bdoId;
  }

	public void setBdoId(String bdoId) {
  	this.bdoId = bdoId;
  }

	public Item[] getItems() {
  	return items;
  }

	public void setItems(Item[] items) {
  	this.items = items;
  }

	public String getDeliveryComments() {
  	return deliveryComments;
  }

	public void setDeliveryComments(String deliveryComments) {
  	this.deliveryComments = deliveryComments;
  }

	public Agent getAgent() {
  	return agent;
  }

	public void setAgent(Agent agent) {
  	this.agent = agent;
  }

	public Passenger[] getPassengers() {
  	return passengers;
  }

	public void setPassengers(Passenger[] passengers) {
  	this.passengers = passengers;
  }

}
	