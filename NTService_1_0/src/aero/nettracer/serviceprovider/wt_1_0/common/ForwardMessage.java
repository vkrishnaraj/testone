package aero.nettracer.serviceprovider.wt_1_0.common;

public class ForwardMessage {
	private String fromStation;
	private String fromAirline;
	private String destinationStation;
	private String destinationAirline;
	private Agent agent;
	private String expediteTag;
	private String[] name;
	private String[] teletype;
	private String suplementaryInfo;
	private String textInfo;
	private WtqSegment[] itinerary;

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getFromAirline() {
		return fromAirline;
	}

	public void setFromAirline(String fromAirline) {
		this.fromAirline = fromAirline;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getExpediteTag() {
		return expediteTag;
	}

	public void setExpediteTag(String expediteTag) {
		this.expediteTag = expediteTag;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getTeletype() {
		return teletype;
	}

	public void setTeletype(String[] teletype) {
		this.teletype = teletype;
	}

	public String getSuplementaryInfo() {
		return suplementaryInfo;
	}

	public void setSuplementaryInfo(String suplementaryInfo) {
		this.suplementaryInfo = suplementaryInfo;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public WtqSegment[] getItinerary() {
		return itinerary;
	}

	public void setItinerary(WtqSegment[] itinerary) {
		this.itinerary = itinerary;
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

}
