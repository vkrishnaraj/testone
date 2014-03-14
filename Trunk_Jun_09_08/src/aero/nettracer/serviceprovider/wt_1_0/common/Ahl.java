package aero.nettracer.serviceprovider.wt_1_0.common;

import java.util.Calendar;

import com.bagnet.nettracer.tracing.utils.StringUtils;

public class Ahl {
	private String ahlId;
	private String pnrLocator;
	private Calendar createDate;
	private int numberPaxAffected;
	private int numberBagsChecked;
	private String stationCode;
	private Passenger[] pax;
	private Itinerary[] paxItinerary;
	private Itinerary[] bagItinerary;
	private Item[] item;
	private ClaimCheck[] claimCheck;
	private Agent agent;
	private String airlineCode;
	private Expenses[] expenses;
	private String faultReasonDescription;
	private String faultStation;
	private int faultReason;
	private Calendar tracingFinalized;
	private String furtherInfo;
	private String[] additionalRoutes;
	private String[] messageInfo;
	private String[] matchInfo;
	
	public String getFaultReasonDescription() {
		return faultReasonDescription;
	}

	public void setFaultReasonDescription(String faultReasonDescription) {
		this.faultReasonDescription = faultReasonDescription;
	}

	public String getAhlId() {
		return ahlId;
	}

	public void setAhlId(String ahlId) {
		this.ahlId = ahlId;
	}

	public int getNumberPaxAffected() {
		return numberPaxAffected;
	}

	public void setNumberPaxAffected(int numberPaxAffected) {
		this.numberPaxAffected = numberPaxAffected;
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

	public Passenger[] getPax() {
		return pax;
	}

	public void setPax(Passenger[] pax) {
		this.pax = pax;
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

	public Item[] getItem() {
		return item;
	}

	public void setItem(Item[] item) {
		this.item = item;
	}

	public ClaimCheck[] getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(ClaimCheck[] claimCheck) {
		this.claimCheck = claimCheck;
	}

	public Calendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public int getNumberBagsChecked() {
		return numberBagsChecked;
	}

	public void setNumberBagsChecked(int numberBagsChecked) {
		this.numberBagsChecked = numberBagsChecked;
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

	public Expenses[] getExpenses() {
		return expenses;
	}

	public void setExpenses(Expenses[] expenses) {
		this.expenses = expenses;
	}

	public Calendar getTracingFinalized() {
		return tracingFinalized;
	}

	public void setTracingFinalized(Calendar tracingFinalized) {
		this.tracingFinalized = tracingFinalized;
	}

	public String getFurtherInfo() {
		return furtherInfo;
	}

	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}

	public String[] getAdditionalRoutes() {
		return additionalRoutes;
	}

	public void setAdditionalRoutes(String[] additionalRoutes) {
		this.additionalRoutes = additionalRoutes;
	}


	public String[] getReadOnlyMessageInfo() {
		if(messageInfo!=null){
			String[] mi=new String[messageInfo.length];
			int i=0;
			for(String s:messageInfo){
				String m=StringUtils.replaceNewLines(s);
				mi[i]=m;
				i++;
			}
			return mi;
		} else {
			return new String[0];
		}
		
	}
	

	public String[] getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String[] messageInfo) {
		this.messageInfo = messageInfo;
	}

	public String[] getReadOnlyMatchInfo() {
		if(matchInfo!=null){
			String[] mi=new String[matchInfo.length];
			int i=0;
			for(String s:matchInfo){
				String m=StringUtils.replaceNewLines(s);
				mi[i]=m;
				i++;
			}
			return mi;
		} else {
			return new String[0];
		}
	}
	

	public String[] getMatchInfo() {
		return matchInfo;
	}

	public void setMatchInfo(String[] matchInfo) {
		this.matchInfo = matchInfo;
	}
	
	public String getRoutesDetail(){
		StringBuilder routesDetail=new StringBuilder();
		int i=1;
		for(String r:additionalRoutes){
			routesDetail.append(r);
			if(i!=additionalRoutes.length){
				routesDetail.append("-");
			}
			i++;
		}
		
		return routesDetail.toString();
	}

}
