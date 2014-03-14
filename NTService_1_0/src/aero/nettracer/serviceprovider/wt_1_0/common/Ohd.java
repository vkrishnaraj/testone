package aero.nettracer.serviceprovider.wt_1_0.common;

import java.util.Calendar;

public class Ohd {
	private String ohdId;
	private String stationCode;
	private String airlineCode;
	private String faultStation;
	private int faultReason;
	private Passenger[] pax;
	private Itinerary[] paxItinerary;
	private Itinerary[] bagItinerary;
	private Item item;
	private ClaimCheck claimCheck;
	private Calendar createDate;
	private String storageLocation;
	private Agent agent;
	private String pnrLocator;
	private String furtherInfo;
	private String[] routes;
	private String[] messageInfo;
	private String[] matchInfo;
	
	public String getStorageLocation() {
  	return storageLocation;
  }

	public void setStorageLocation(String storageLocation) {
  	this.storageLocation = storageLocation;
  }

	public Calendar getCreateDate() {
  	return createDate;
  }

	public void setCreateDate(Calendar createDate) {
  	this.createDate = createDate;
  }

	public String getOhdId() {
		return ohdId;
	}

	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getFaultStation() {
		return faultStation;
	}

	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}

	public int getFaultReason() {
		return faultReason;
	}

	public void setFaultReason(int faultReason) {
		this.faultReason = faultReason;
	}


	public Itinerary[] getPaxItinerary() {
		return paxItinerary;
	}

	public void setPaxItinerary(Itinerary[] paxItinerary) {
		this.paxItinerary = paxItinerary;
	}

	public Itinerary[] getBagItinerary() {
		return bagItinerary;
	}

	public void setBagItinerary(Itinerary[] bagItinerary) {
		this.bagItinerary = bagItinerary;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ClaimCheck getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(ClaimCheck claimCheck) {
		this.claimCheck = claimCheck;
	}

	public void setPax(Passenger[] pax) {
  	this.pax = pax;
  }

	public Passenger[] getPax() {
  	return pax;
  }

	public Agent getAgent() {
  	return agent;
  }

	public void setAgent(Agent agent) {
  	this.agent = agent;
  }

	public String getPnrLocator() {
  	return pnrLocator;
  }

	public void setPnrLocator(String pnrLocator) {
  	this.pnrLocator = pnrLocator;
  }

	public String getAirlineCode() {
  	return airlineCode;
  }

	public void setAirlineCode(String airlineCode) {
  	this.airlineCode = airlineCode;
  }

	public String getFurtherInfo() {
		return furtherInfo;
	}

	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}

	public String[] getRoutes() {
		return routes;
	}

	public void setRoutes(String[] routes) {
		this.routes = routes;
	}

	public String[] getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String[] messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String[] getMatchInfo() {
		return matchInfo;
	}

	public void setMatchInfo(String[] matchInfo) {
		this.matchInfo = matchInfo;
	}
}
