package aero.nettracer.serviceprovider.wt_1_0.common;

public class ForwardOhd {
	private Agent agent;
	private String ahlId;
	private String ohdId;
	private String[] teletype;
	private String[] fwdName;
	private Itinerary[] itinerary;
	private String supplementaryInfo;
	private String expediteNumber;
	private String destinationStation;
	private String destinationAirline;

	public String getSupplementaryInfo() {
  	return supplementaryInfo;
  }

	public void setSupplementaryInfo(String supplementaryInfo) {
  	this.supplementaryInfo = supplementaryInfo;
  }


	public Itinerary[] getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary[] itinerary) {
		this.itinerary = itinerary;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String[] getTeletype() {
		return teletype;
	}

	public void setTeletype(String[] teletype) {
		this.teletype = teletype;
	}


	public String getOhdId() {
		return ohdId;
	}

	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	public String getExpediteNumber() {
  	return expediteNumber;
  }

	public void setFwdName(String[] fwdName) {
  	this.fwdName = fwdName;
  }

	public String[] getFwdName() {
  	return fwdName;
  }

	public void setExpediteNumber(String expediteNumber) {
  	this.expediteNumber = expediteNumber;
  }

	public String getDestinationStation() {
  	return destinationStation;
  }

	public void setDestinationStation(String destinationStation) {
  	this.destinationStation = destinationStation;
  }

	public String getDestinationAirline() {
  	return destinationAirline;
  }

	public void setDestinationAirline(String destinationAirline) {
  	this.destinationAirline = destinationAirline;
  }

	public String getAhlId() {
  	return ahlId;
  }

	public void setAhlId(String ahlId) {
  	this.ahlId = ahlId;
  }

}
