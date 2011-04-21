package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsFlightInfoPage {

	// Flight Information
	public static final String FLY_BAGSTRAVEL = "How many bags did you travel with";
	public static final String FLY_BAGSLOST = "How many bags were lost/delayed";
	public static final String FLY_WHERECHECK = "Where did you check your bag";
	public static final String FLY_NUMPAX = "Number of Passengers travelling with you";
	public static final String FLY_RECHECK = "Did you have to recheck your baggage at any point in your trip";
	public static final String FLY_CUSTOMS = "Was your bag inspected by customs/border patrol";
	public static final String FLY_DIFFAIRLINE = "Is your ticket with a different airline";
	public static final String FLY_BAGTAGNUMS = "Bag Tag Numbers";
	public static final String FLY_BAGARRIVE = "Did this Bag Arrive";
	public static final String FLY_TICKETNUM = "Ticket Number";
	public static final String FLY_ABOUTFLY = "About Your Flight";
	public static final String FLY_ABOUTTICKET = "About Your Ticket";
	public static final String FLY_ABOUTITIN = "About your Itinerary";
	public static final String FLY_FROMTOAIR = "From/To Airports";
	public static final String FLY_FLYNUM = "Airline/Flight Number ";
	public static final String FLY_DATE = "Date";
	public static final String FLY_CHARGEDEXCESS = "Were you charged for excess baggage";
	public static final String FLY_BAGSCHECKED = "No of bags checked";
	public static final String FLY_BAGSMISS = "No of bags missing";
	public static final String FLY_CLAIMCHECK = "Bag Claim Check ";
	public static final String FLY_PAYEXCESS = "Did you declare and pay for excess value";
	public static final String FLY_DECLARED = "Value Declared";
	public static final String FLY_WHERECHECKBAG = "Where did you check your baggage";
	public static final String FLY_CLEARCUSTOMS = "Did your bag clear customs";
	public static final String FLY_WEIGHT = "Estimated Weight";
	public static final String FLY_REROUTED = "Was your baggage rerouted or rechecked enroute";
	public static final String FLY_DIFFCLAIMCHECK = "If Yes,were you given a different claim check";
	public static final String FLY_CITYREROUTE = "City/Airline rerouted by";
	public static final String FLY_REASON = "Reason";
	public static final String FLY_CLAIMIMMED = "Did you attempt to claim immediately upon arrival";
	public static final String FLY_LASTSEE = "Where/when did you last see your baggage";
	public static final String FLY_WHICHCITY = "At which US Airways city did you file your report";
	public static final String FLY_REPORTANOTHER = "Was mishandling reported to another airline";
	public static final String FLY_DESC = "flightDescriptiveText";
	public static final String FLY_REQMESS = "flightRequiredFieldMessage";
	public static final String FLY_HELP = "flightHelp";
	public static final String FLY_PREV = "Previous";
	public static final String FLY_ADDNEWSEG = "Add New Segment";

	// Labels For Flight Info Page
	private LabelText totalTravelBag;
	private LabelText noOfLostBag;
	private LabelText bagCheckedLocation;
	private LabelText noOfPassenger;
	private LabelText recheckBag;
	private LabelText inspectbag;
	private LabelText differentAirlineTicket;
	private LabelText bagTagNumbers;
	private LabelText bagArrival;
	private LabelText ticketNumber;
	private LabelText aboutYourFlight;
	private LabelText aboutYourTicket;
	private LabelText aboutYourItenerary;
	private LabelText fromToAirports;
	private LabelText airlineFlightNo;
	private LabelText flightDate;
	private LabelText chargeExcessBaggage;
	private LabelText bagNumber;
	private LabelText missingBag;
	private LabelText bagClaimCheck;
	private LabelText declarePayExcessValue;
	private LabelText declaredValue;
	private LabelText placeBagChecked;
	private LabelText clearCustomBag;
	private LabelText bagWeight;
	private LabelText rerouteBag;
	private LabelText differentClaimCheck;
	private LabelText reroutedCityAirline;
	private LabelText reason;
	private LabelText immediateClaimAttempt;
	private LabelText whenBagSeen;
	private LabelText fileReportCity;
	private LabelText reportAnotherAirline;
	private LabelText descriptiveText;
	private LabelText requiredFieldMessage;
	private LabelText help;
	private LabelText previous;
	private LabelText addNewSegment;

	public LabelText getTotalTravelBag() {
		return totalTravelBag;
	}

	public void setTotalTravelBag(LabelText totalTravelBag) {
		this.totalTravelBag = totalTravelBag;
	}

	public LabelText getNoOfLostBag() {
		return noOfLostBag;
	}

	public void setNoOfLostBag(LabelText noOfLostBag) {
		this.noOfLostBag = noOfLostBag;
	}

	public LabelText getBagCheckedLocation() {
		return bagCheckedLocation;
	}

	public void setBagCheckedLocation(LabelText bagCheckedLocation) {
		this.bagCheckedLocation = bagCheckedLocation;
	}

	public LabelText getNoOfPassenger() {
		return noOfPassenger;
	}

	public void setNoOfPassenger(LabelText noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}

	public LabelText getRecheckBag() {
		return recheckBag;
	}

	public void setRecheckBag(LabelText recheckBag) {
		this.recheckBag = recheckBag;
	}

	public LabelText getInspectbag() {
		return inspectbag;
	}

	public void setInspectbag(LabelText inspectbag) {
		this.inspectbag = inspectbag;
	}

	public LabelText getDifferentAirlineTicket() {
		return differentAirlineTicket;
	}

	public void setDifferentAirlineTicket(LabelText differentAirlineTicket) {
		this.differentAirlineTicket = differentAirlineTicket;
	}

	public LabelText getBagTagNumbers() {
		return bagTagNumbers;
	}

	public void setBagTagNumbers(LabelText bagTagNumbers) {
		this.bagTagNumbers = bagTagNumbers;
	}

	public LabelText getBagArrival() {
		return bagArrival;
	}

	public void setBagArrival(LabelText bagArrival) {
		this.bagArrival = bagArrival;
	}

	public LabelText getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(LabelText ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public LabelText getAboutYourFlight() {
		return aboutYourFlight;
	}

	public void setAboutYourFlight(LabelText aboutYourFlight) {
		this.aboutYourFlight = aboutYourFlight;
	}

	public LabelText getAboutYourTicket() {
		return aboutYourTicket;
	}

	public void setAboutYourTicket(LabelText aboutYourTicket) {
		this.aboutYourTicket = aboutYourTicket;
	}

	public LabelText getAboutYourItenerary() {
		return aboutYourItenerary;
	}

	public void setAboutYourItenerary(LabelText aboutYourItenerary) {
		this.aboutYourItenerary = aboutYourItenerary;
	}

	public LabelText getFromToAirports() {
		return fromToAirports;
	}

	public void setFromToAirports(LabelText fromToAirports) {
		this.fromToAirports = fromToAirports;
	}

	public LabelText getAirlineFlightNo() {
		return airlineFlightNo;
	}

	public void setAirlineFlightNo(LabelText airlineFlightNo) {
		this.airlineFlightNo = airlineFlightNo;
	}

	public LabelText getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LabelText flightDate) {
		this.flightDate = flightDate;
	}

	public LabelText getChargeExcessBaggage() {
		return chargeExcessBaggage;
	}

	public void setChargeExcessBaggage(LabelText chargeExcessBaggage) {
		this.chargeExcessBaggage = chargeExcessBaggage;
	}

	public LabelText getBagNumber() {
		return bagNumber;
	}

	public void setBagNumber(LabelText bagNumber) {
		this.bagNumber = bagNumber;
	}

	public LabelText getMissingBag() {
		return missingBag;
	}

	public void setMissingBag(LabelText missingBag) {
		this.missingBag = missingBag;
	}

	public LabelText getBagClaimCheck() {
		return bagClaimCheck;
	}

	public void setBagClaimCheck(LabelText bagClaimCheck) {
		this.bagClaimCheck = bagClaimCheck;
	}

	public LabelText getDeclarePayExcessValue() {
		return declarePayExcessValue;
	}

	public void setDeclarePayExcessValue(LabelText declarePayExcessValue) {
		this.declarePayExcessValue = declarePayExcessValue;
	}

	public LabelText getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(LabelText declaredValue) {
		this.declaredValue = declaredValue;
	}

	public LabelText getPlaceBagChecked() {
		return placeBagChecked;
	}

	public void setPlaceBagChecked(LabelText placeBagChecked) {
		this.placeBagChecked = placeBagChecked;
	}

	public LabelText getClearCustomBag() {
		return clearCustomBag;
	}

	public void setClearCustomBag(LabelText clearCustomBag) {
		this.clearCustomBag = clearCustomBag;
	}

	public LabelText getBagWeight() {
		return bagWeight;
	}

	public void setBagWeight(LabelText bagWeight) {
		this.bagWeight = bagWeight;
	}

	public LabelText getRerouteBag() {
		return rerouteBag;
	}

	public void setRerouteBag(LabelText rerouteBag) {
		this.rerouteBag = rerouteBag;
	}

	public LabelText getDifferentClaimCheck() {
		return differentClaimCheck;
	}

	public void setDifferentClaimCheck(LabelText differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}

	public LabelText getReroutedCityAirline() {
		return reroutedCityAirline;
	}

	public void setReroutedCityAirline(LabelText reroutedCityAirline) {
		this.reroutedCityAirline = reroutedCityAirline;
	}

	public LabelText getReason() {
		return reason;
	}

	public void setReason(LabelText reason) {
		this.reason = reason;
	}

	public LabelText getImmediateClaimAttempt() {
		return immediateClaimAttempt;
	}

	public void setImmediateClaimAttempt(LabelText immediateClaimAttempt) {
		this.immediateClaimAttempt = immediateClaimAttempt;
	}

	public LabelText getWhenBagSeen() {
		return whenBagSeen;
	}

	public void setWhenBagSeen(LabelText whenBagSeen) {
		this.whenBagSeen = whenBagSeen;
	}

	public LabelText getFileReportCity() {
		return fileReportCity;
	}

	public void setFileReportCity(LabelText fileReportCity) {
		this.fileReportCity = fileReportCity;
	}

	public LabelText getReportAnotherAirline() {
		return reportAnotherAirline;
	}

	public void setReportAnotherAirline(LabelText reportAnotherAirline) {
		this.reportAnotherAirline = reportAnotherAirline;
	}

	public LabelText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(LabelText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}

	public LabelText getRequiredFieldMessage() {
		return requiredFieldMessage;
	}

	public void setRequiredFieldMessage(LabelText requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}

	public LabelText getHelp() {
		return help;
	}

	public void setHelp(LabelText help) {
		this.help = help;
	}

	public LabelText getPrevious() {
		return previous;
	}

	public void setPrevious(LabelText previous) {
		this.previous = previous;
	}

	public LabelText getAddNewSegment() {
		return addNewSegment;
	}

	public void setAddNewSegment(LabelText addNewSegment) {
		this.addNewSegment = addNewSegment;
	}
	
	public LabelsFlightInfoPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(FLY_ABOUTFLY)) {
				setAboutYourFlight(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_ABOUTITIN)) {
				setAboutYourItenerary(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_ABOUTTICKET)) {
				setAboutYourTicket(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_ADDNEWSEG)) {
				setAddNewSegment(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGARRIVE)) {
				setBagArrival(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGSCHECKED)) {
				setBagNumber(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGSLOST)) {
				setNoOfLostBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGSMISS)) {
				setMissingBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGSTRAVEL)) {
				setTotalTravelBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_BAGTAGNUMS)) {
				setBagTagNumbers(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CHARGEDEXCESS)) {
				setChargeExcessBaggage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CITYREROUTE)) {
				setReroutedCityAirline(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CLAIMCHECK)) {
				setBagClaimCheck(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CLAIMIMMED)) {
				setImmediateClaimAttempt(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CLEARCUSTOMS)) {
				setClearCustomBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_CUSTOMS)) {
				setInspectbag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_DATE)) {
				setFlightDate(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_DECLARED)) {
				setDeclaredValue(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_DESC)) {
				setDescriptiveText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_DIFFAIRLINE)) {
				setDifferentAirlineTicket(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_DIFFCLAIMCHECK)) {
				setDifferentClaimCheck(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_FLYNUM)) {
				setAirlineFlightNo(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_FROMTOAIR)) {
				setFromToAirports(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_LASTSEE)) {
				setWhenBagSeen(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_NUMPAX)) {
				setNoOfPassenger(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_PAYEXCESS)) {
				setDeclarePayExcessValue(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_PREV)) {
				setPrevious(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_REASON)) {
				setReason(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_RECHECK)) {
				setRecheckBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_REPORTANOTHER)) {
				setReportAnotherAirline(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_REQMESS)) {
				setRequiredFieldMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_REROUTED)) {
				setRerouteBag(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_TICKETNUM)) {
				setTicketNumber(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_WEIGHT)) {
				setBagWeight(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_WHERECHECK)) {
				setBagCheckedLocation(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_WHERECHECKBAG)) {
				setPlaceBagChecked(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(FLY_WHICHCITY)) {
				setFileReportCity(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
