package aero.nettracer.serviceprovider.wt_1_0.common;

public class RequestOhd {
	private Ahl ahl;
	private String ohdId;
	private String bagTagNumber;
	private String fromAirline;
	private String fromStation;
	private Agent agent;
	private String furtherInfo;
	private String[] teletype;
	
	public String getFurtherInfo() {
  	return furtherInfo;
  }

	public void setFurtherInfo(String furtherInfo) {
  	this.furtherInfo = furtherInfo;
  }

	public Agent getAgent() {
  	return agent;
  }

	public void setAgent(Agent agent) {
  	this.agent = agent;
  }


	public String getOhdId() {
		return ohdId;
	}

	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	public Ahl getAhl() {
		return ahl;
	}

	public void setAhl(Ahl ahl) {
		this.ahl = ahl;
	}

	public String getFromAirline() {
		return fromAirline;
	}

	public void setFromAirline(String fromAirline) {
		this.fromAirline = fromAirline;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getBagTagNumber() {
		return bagTagNumber;
	}

	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	public String[] getTeletype() {
  	return teletype;
  }

	public void setTeletype(String[] teletype) {
  	this.teletype = teletype;
  }

}
