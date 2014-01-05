package aero.nettracer.portal.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import aero.nettracer.portal.faces.util.File;
import aero.nettracer.portal.utils.ClaimsProperties;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem;

public class PassengerBean {
	
	
	//Data binding For Passenger Info Page
	private String passengerInfoDescriptiveText;
	private String requiredFieldMessage;
	private String passengerInfoHelp;
	private String permanentAddress;
	private String emailAddress;
	private String occupation;
	private String businessName;
	private List<Address> address=new ArrayList<Address>();
	private List<Passenger> passengers=new ArrayList<Passenger>();
	private String personalInformation;
	private String frequentFlyerNumber;
	private String socialSecurityNumber;
	private String passengerInfoSave;
	private String passengerInfoCancel;
	private String passengerInfoForward;
	private String incidentID;
	private Integer bagsTravelWith;
	private Integer lostBag;
	private String bagCheckLocation;
	private Integer noOfPassenger;
	private Boolean recheckBag;
	private Boolean inspectbag;
	private Boolean chargeExcessBaggage;
	private Integer noOfCheckedBag;
	private Integer noOfmissingBag;
	private String bagClaimCheck;
	private Boolean declarePayExcessValue;
	private Double declaredValue;
	private String placeBagChecked;
	private int clearCustomBag;
	private String bagWeight;
	private Boolean rerouteBag;
	private Boolean differentClaimCheck;
	private String reroutedCityAirline;
	private String reason;
	private Boolean immediateClaimAttempt;
	private String whenBagSeen;
	private String fileReportCity;
	private Boolean reportAnotherAirline;
	private Boolean differentAirlineTicket;
	private String ticketNumber;
	private List<Bag> bagTagList = new ArrayList<Bag>() ; //for datatable for bag tag number in page2:ticket-info.xhtml
	private String aboutYourItenerary;
	private List<Itinerary> itineraryList= new ArrayList<Itinerary>();
	private Long claimId;
	private String claimAmount;
	private String claimDate;
	private String declaredValueCurrency;
	private String claimAmountCurrency;
	private String bagCheckDescription;
	private String reportedAirline;
	private String reportedCity;
	private String reportedFileNumber;
	private int privateInsurance;
	private String privateInsuranceName;
	private String privateInsuranceAddr;
	private boolean delayed;
	private boolean pilferage;
	private boolean damaged;
	private boolean interim;
	
	//For About Your Flight Page
	//private List<Bag> bagList;
	
	//For about Your Bag Page
	private List<Bag> bagList;
	
	//File Upload
	private List<File> files = new ArrayList<File>();
	
	//Messages
	private List<Message> messages = new ArrayList<Message>();
	private String currentMessage;
	
	//For Fraud Question
	private int anotherClaim;
	private String whichAirline;
	private Date dateOfClaim;
	private String claimantName;
	private int tsaInspect;
	private int bagConfirmNote;
	private String inspectionPlace;
	private String additionalComments;
	
	//For Submit CLaim Page
	private String status;
	private WSPVAdvancedIncident passengerData;
	private PaxViewItem[] pvItems;
	private Boolean onlineAvailable;
	private Boolean claimsAvailable;
	private Boolean completeClaim;
	private int requestForeignCurrency;
	private String foreignCurrencyEmail;
	private Date bagReceivedDate;
	private String reasonForTravel;
	private String lengthOfStay;

	public String getReasonForTravel() {
		return reasonForTravel;
	}

	public void setReasonForTravel(String reasonForTravel) {
		this.reasonForTravel = reasonForTravel;
	}

	public String getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(String lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}
	
	public String getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}
	public String getPassengerInfoDescriptiveText() {
		return passengerInfoDescriptiveText;
	}
	public void setPassengerInfoDescriptiveText(String passengerInfoDescriptiveText) {
		this.passengerInfoDescriptiveText = passengerInfoDescriptiveText;
	}
	public String getRequiredFieldMessage() {
		return requiredFieldMessage;
	}
	public void setRequiredFieldMessage(String requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}
	public String getPassengerInfoHelp() {
		return passengerInfoHelp;
	}
	public void setPassengerInfoHelp(String passengerInfoHelp) {
		this.passengerInfoHelp = passengerInfoHelp;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public String getPersonalInformation() {
		return personalInformation;
	}
	public void setPersonalInformation(String personalInformation) {
		this.personalInformation = personalInformation;
	}
	public String getFrequentFlyerNumber() {
		return frequentFlyerNumber;
	}
	public void setFrequentFlyerNumber(String frequentFlyerNumber) {
		this.frequentFlyerNumber = frequentFlyerNumber;
	}
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	public String getPassengerInfoSave() {
		return passengerInfoSave;
	}
	public void setPassengerInfoSave(String passengerInfoSave) {
		this.passengerInfoSave = passengerInfoSave;
	}
	public String getPassengerInfoCancel() {
		return passengerInfoCancel;
	}
	public void setPassengerInfoCancel(String passengerInfoCancel) {
		this.passengerInfoCancel = passengerInfoCancel;
	}
	public String getPassengerInfoForward() {
		return passengerInfoForward;
	}
	public void setPassengerInfoForward(String passengerInfoForward) {
		this.passengerInfoForward = passengerInfoForward;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	public String getIncidentID() {
		return incidentID;
	}
	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}
	public Integer getBagsTravelWith() {
		return bagsTravelWith;
	}
	public void setBagsTravelWith(Integer bagsTravelWith) {
		this.bagsTravelWith = bagsTravelWith;
	}
	public Integer getLostBag() {
		return lostBag;
	}
	public void setLostBag(Integer lostBag) {
		this.lostBag = lostBag;
	}
	public String getBagCheckLocation() {
		return bagCheckLocation;
	}
	public void setBagCheckLocation(String bagCheckLocation) {
		this.bagCheckLocation = bagCheckLocation;
	}
	public Integer getNoOfPassenger() {
		return noOfPassenger;
	}
	public void setNoOfPassenger(Integer noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}
	public Boolean getRecheckBag() {
		return recheckBag;
	}
	public void setRecheckBag(Boolean recheckBag) {
		this.recheckBag = recheckBag;
	}
	public Boolean getInspectbag() {
		return inspectbag;
	}
	public void setInspectbag(Boolean inspectbag) {
		this.inspectbag = inspectbag;
	}
	public Boolean getChargeExcessBaggage() {
		return chargeExcessBaggage;
	}
	public void setChargeExcessBaggage(Boolean chargeExcessBaggage) {
		this.chargeExcessBaggage = chargeExcessBaggage;
	}
	public Integer getNoOfCheckedBag() {
		return noOfCheckedBag;
	}
	public void setNoOfCheckedBag(Integer noOfCheckedBag) {
		this.noOfCheckedBag = noOfCheckedBag;
	}
	public Integer getNoOfmissingBag() {
		return noOfmissingBag;
	}
	public void setNoOfmissingBag(Integer noOfmissingBag) {
		this.noOfmissingBag = noOfmissingBag;
	}
	public String getBagClaimCheck() {
		return bagClaimCheck;
	}
	public void setBagClaimCheck(String bagClaimCheck) {
		this.bagClaimCheck = bagClaimCheck;
	}
	public Boolean getDeclarePayExcessValue() {
		return declarePayExcessValue;
	}
	public void setDeclarePayExcessValue(Boolean declarePayExcessValue) {
		this.declarePayExcessValue = declarePayExcessValue;
	}
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getPlaceBagChecked() {
		return placeBagChecked;
	}
	public void setPlaceBagChecked(String placeBagChecked) {
		this.placeBagChecked = placeBagChecked;
	}
	public int getClearCustomBag() {
		return clearCustomBag;
	}
	public void setClearCustomBag(int clearCustomBag) {
		this.clearCustomBag = clearCustomBag;
	}
	public String getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}
	public Boolean getRerouteBag() {
		return rerouteBag;
	}
	public void setRerouteBag(Boolean rerouteBag) {
		this.rerouteBag = rerouteBag;
	}
	public Boolean getDifferentClaimCheck() {
		return differentClaimCheck;
	}
	public void setDifferentClaimCheck(Boolean differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}
	public String getReroutedCityAirline() {
		return reroutedCityAirline;
	}
	public void setReroutedCityAirline(String reroutedCityAirline) {
		this.reroutedCityAirline = reroutedCityAirline;
	}
	public Boolean getImmediateClaimAttempt() {
		return immediateClaimAttempt;
	}
	public void setImmediateClaimAttempt(Boolean immediateClaimAttempt) {
		this.immediateClaimAttempt = immediateClaimAttempt;
	}
	public String getWhenBagSeen() {
		return whenBagSeen;
	}
	public void setWhenBagSeen(String whenBagSeen) {
		this.whenBagSeen = whenBagSeen;
	}
	public String getFileReportCity() {
		return fileReportCity;
	}
	public void setFileReportCity(String fileReportCity) {
		this.fileReportCity = fileReportCity;
	}
	public Boolean getReportAnotherAirline() {
		return reportAnotherAirline;
	}
	public void setReportAnotherAirline(Boolean reportAnotherAirline) {
		this.reportAnotherAirline = reportAnotherAirline;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<Bag> getBagTagList() {
		return  bagTagList;
	}
	public void setBagTagList(List<Bag> bagTagList) {
		this.bagTagList = bagTagList;
	}
	public String getAboutYourItenerary() {
		return aboutYourItenerary;
	}
	public void setAboutYourItenerary(String aboutYourItenerary) {
		this.aboutYourItenerary = aboutYourItenerary;
	}
	public List<Itinerary> getItineraryList() {
		return itineraryList;
	}
	public void setItineraryList(List<Itinerary> itineraryList) {
		this.itineraryList = itineraryList;
	}
	public List<Bag> getBagList() {
		return bagList;
	}
	public void setBagList(List<Bag> bagList) {
		this.bagList = bagList;
	}
	public Boolean getDifferentAirlineTicket() {
		return differentAirlineTicket;
	}
	public void setDifferentAirlineTicket(Boolean differentAirlineTicket) {
		this.differentAirlineTicket = differentAirlineTicket;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public int getAnotherClaim() {
		return anotherClaim;
	}
	public void setAnotherClaim(int anotherClaim) {
		this.anotherClaim = anotherClaim;
	}
	public String getWhichAirline() {
		return whichAirline;
	}
	public void setWhichAirline(String whichAirline) {
		this.whichAirline = whichAirline;
	}
	public Date getDateOfClaim() {
		return dateOfClaim;
	}
	public void setDateOfClaim(Date dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}
	public String getClaimantName() {
		return claimantName;
	}
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	public int getTsaInspect() {
		return tsaInspect;
	}
	public void setTsaInspect(int tsaInspect) {
		this.tsaInspect = tsaInspect;
	}
	public int getBagConfirmNote() {
		return bagConfirmNote;
	}
	public void setBagConfirmNote(int bagConfirmNote) {
		this.bagConfirmNote = bagConfirmNote;
	}
	public String getInspectionPlace() {
		return inspectionPlace;
	}
	public void setInspectionPlace(String inspectionPlace) {
		this.inspectionPlace = inspectionPlace;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}	
	public String getDeclaredValueCurrency() {
		return declaredValueCurrency;
	}
	public void setDeclaredValueCurrency(String declaredValueCurrency) {
		this.declaredValueCurrency = declaredValueCurrency;
	}
	public String getClaimAmountCurrency() {
		return claimAmountCurrency;
	}
	public void setClaimAmountCurrency(String claimAmountCurrency) {
		this.claimAmountCurrency = claimAmountCurrency;
	}
	public String getBagCheckDescription() {
		return bagCheckDescription;
	}
	public void setBagCheckDescription(String bagCheckDescription) {
		this.bagCheckDescription = bagCheckDescription;
	}
	public boolean isDelayed() {
		return delayed;
	}
	public void setDelayed(boolean delayed) {
		this.delayed = delayed;
	}
	public boolean isDamaged() {
		return damaged;
	}
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	public boolean isInterim() {
		return interim;
	}
	public void setInterim(boolean interim) {
		this.interim = interim;
	}
	public boolean isPilferage() {
		return pilferage;
	}
	public void setPilferage(boolean pilferage) {
		this.pilferage = pilferage;
	}
	public int getRequestForeignCurrency() {
		return requestForeignCurrency;
	}
	public void setRequestForeignCurrency(int requestForeignCurrency) {
		this.requestForeignCurrency = requestForeignCurrency;
	}
	public String getForeignCurrencyEmail() {
		return foreignCurrencyEmail;
	}
	public void setForeignCurrencyEmail(String foreignCurrencyEmail) {
		this.foreignCurrencyEmail = foreignCurrencyEmail;
	}
	public Date getBagReceivedDate() {
		return bagReceivedDate;
	}
	public void setBagReceivedDate(Date bagReceivedDate) {
		this.bagReceivedDate = bagReceivedDate;
	}
	public void setPassengerData(WSPVAdvancedIncident passengerData) {
		this.passengerData = passengerData; 
	}
	public WSPVAdvancedIncident getPassengerData() {
		return passengerData;
	}	
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getCurrentMessage() {
		return currentMessage;
	}

	public void setCurrentMessage(String currentMessage) {
		this.currentMessage = currentMessage;
	}

	public boolean isOnlineAvailable() {
		if (onlineAvailable == null) {
			if (isClaimsAvailableProperty() && (getStatus() == null 
					|| (!isStatusComplete(getStatus()) && !getStatus().equals("SECOND_CLAIM")))) {
				Calendar fortyFiveDays = Calendar.getInstance();
				fortyFiveDays.add(Calendar.DATE, -45);
				if (!passengerData.getCreatedate().before(fortyFiveDays)) {
					if (passengerData.getItemType() == 1) {
						onlineAvailable = true;
					} else if (passengerData.getItemType() != 1
							&& passengerData.getIncidentStatus().equalsIgnoreCase(
									"open")) {
						onlineAvailable = true;
					}
				}
			}
			if (onlineAvailable == null) {
				onlineAvailable = false;
			}
		}
		return onlineAvailable;
	}
	
	private boolean isStatusComplete(String status) {
		return (status.equals("SUBMITTED") || status.equals("REVIEW") || status.equals("ONRECORD"));
	}
	
	public boolean isCompleteClaim() {
		if (completeClaim == null) {
			if (isClaimsAvailableProperty() && getStatus() != null && isStatusComplete(getStatus())) {
				completeClaim = true;
			} else {
				completeClaim = false;
			}
		}
		return completeClaim;
	}
	
	private boolean isLostDelay() {
		if (passengerData != null) {
			return (passengerData.getItemType() == 1);
		}
		return true;
	}
	
	private boolean isMissing() {
		if (passengerData != null) {
			return (passengerData.getItemType() == 2);
		}
		return false;
	}
	
	private boolean isDamage() {
		if (passengerData != null) {
			return (passengerData.getItemType() == 3);
		}
		return false;
	}
	
	private boolean isShowClaimMessage() {
		return ((isOnlineAvailable() || isClaimsAvailable()) && !isSecondClaim());
	}
	
	public boolean isShowClaimMessageLD() {
		return (isShowClaimMessage() && isLostDelay());
	}
	
	public boolean isShowClaimMessageMiss() {
		return (isShowClaimMessage() && isMissing());
	}
	
	public boolean isShowClaimMessageDam() {
		return (isShowClaimMessage() && isDamage());
	}
	
	public boolean isScansAvailable() {
		return ClaimsProperties.isTrue(ClaimsProperties.SCANS_AVAILABLE);
	}
	
	public boolean isClaimsAvailable() {
		if (isOnlineAvailable() || isCompleteClaim()) {
			return false;
		} else {
			return isClaimsAvailableProperty();
		}
	}
	
	public boolean isSecondClaim() {
		if (isClaimsAvailableProperty() && getStatus() != null && getStatus().equals("SECOND_CLAIM")) {
			return true;
		}
		return false;
	}
	
	private boolean isClaimsAvailableProperty() {
		if (claimsAvailable == null) {
			claimsAvailable = ClaimsProperties.isTrue(ClaimsProperties.CLAIMS_AVAILABLE);
		}
		return claimsAvailable;
	}
	
	public PaxViewItem[] getPvItems() {
		if (pvItems == null && passengerData != null && passengerData.getItemsArray() != null) {
			WSPVItem[] items = passengerData.getItemsArray();
			pvItems = new PaxViewItem[items.length];
			for (int i = 0; i < items.length; i++) {
				PaxViewItem pvItem = new PaxViewItem();
				pvItem.setAddress1(items[i].getAddress1());
				pvItem.setAddress2(items[i].getAddress2());
				pvItem.setBagstatus(items[i].getBagstatus());
				pvItem.setCity(items[i].getCity());
				pvItem.setClaimchecknum(items[i].getClaimchecknum());
				pvItem.setDeliveryStatus(items[i].getDeliveryStatus());
				pvItem.setLastDeliveryUpdate(items[i].getLastDeliveryUpdate());
				pvItem.setStateID(items[i].getStateID());
				pvItem.setZip(items[i].getZip());
				pvItem.setIncidentStatus(passengerData.getIncidentStatus());
				pvItems[i] = pvItem;
			}
		}
		return pvItems;
	}
	
	public void setPvItems(PaxViewItem[] pvItems) {
		this.pvItems = pvItems;
	}
	public String getReportedAirline() {
		return reportedAirline;
	}
	public void setReportedAirline(String reportedAirline) {
		this.reportedAirline = reportedAirline;
	}
	public String getReportedCity() {
		return reportedCity;
	}
	public void setReportedCity(String reportedCity) {
		this.reportedCity = reportedCity;
	}
	public String getReportedFileNumber() {
		return reportedFileNumber;
	}
	public void setReportedFileNumber(String reportedFileNumber) {
		this.reportedFileNumber = reportedFileNumber;
	}
	public int getPrivateInsurance() {
		return privateInsurance;
	}
	public void setPrivateInsurance(int privateInsurance) {
		this.privateInsurance = privateInsurance;
	}
	public String getPrivateInsuranceName() {
		return privateInsuranceName;
	}
	public void setPrivateInsuranceName(String privateInsuranceName) {
		this.privateInsuranceName = privateInsuranceName;
	}
	public String getPrivateInsuranceAddr() {
		return privateInsuranceAddr;
	}
	public void setPrivateInsuranceAddr(String privateInsuranceAddr) {
		this.privateInsuranceAddr = privateInsuranceAddr;
	}

	public String getRecheckBagDisp() {
		if (recheckBag != null) {
			return (recheckBag ? "Yes" : "No");
		}
		return "No";
	}

	public String getInspectbagDisp() {
		if (inspectbag != null) {
			return (inspectbag ? "Yes" : "No");
		}
		return "No";
	}
	public String getChargeExcessBaggageDisp() {
		if (chargeExcessBaggage != null) {
			return (chargeExcessBaggage ? "Yes" : "No");
		}
		return "No";
	}
	public String getDeclarePayExcessValueDisp() {
		if (declarePayExcessValue != null) {
			return (declarePayExcessValue ? "Yes" : "No");
		}
		return "No";
	}
	public String getClearCustomBagDisp() {
		if (clearCustomBag != 0) {
			if (clearCustomBag == 3) {
				return "N/A";
			}
			return (clearCustomBag == 1 ? "Yes" : "No");
		}
		return "No";
	}
	public String getRerouteBagDisp() {
		if (rerouteBag != null) {
			return (rerouteBag ? "Yes" : "No");
		}
		return "No";
	}
	public String getDifferentClaimCheckDisp() {
		if (differentClaimCheck != null) {
			return (differentClaimCheck ? "Yes" : "No");
		}
		return "No";
	}
	public String getImmediateClaimAttemptDisp() {
		if (immediateClaimAttempt != null) {
			return (immediateClaimAttempt ? "Yes" : "No");
		}
		return "No";
	}
	public String getReportAnotherAirlineDisp() {
		if (reportAnotherAirline != null) {
			return (reportAnotherAirline ? "Yes" : "No");
		}
		return "No";
	}
	public String getDifferentAirlineTicketDisp() {
		if (differentAirlineTicket != null) {
			return (differentAirlineTicket ? "Yes" : "No");
		}
		return "No";
	}
	public String getAnotherClaimDisp() {
		if (anotherClaim != 0) {
			return (anotherClaim == 1 ? "Yes" : "No");
		}
		return "N/A";
	}
	public String getTsaInspectDisp() {
		if (tsaInspect != 0) {
			return (tsaInspect == 1 ? "Yes" : "No");
		}
		return "N/A";
	}
	public String getBagConfirmNoteDisp() {
		if (bagConfirmNote != 0) {
			return (bagConfirmNote == 1 ? "Yes" : "No");
		}
		return "N/A";
	}
	public String getPrivateInsuranceDisp() {
		if (privateInsurance != 0) {
			return (privateInsurance == 1 ? "Yes" : "No");
		}
		return "N/A";
	}
	public String getRequestForeignCurrencyDisp() {
		if (requestForeignCurrency != 0) {
			return (requestForeignCurrency == 1 ? "Yes" : "No");
		}
		return "N/A";
	}
	
	public boolean isShowClaimStatus() {
		if (getStatus() != null) {
			return !getStatus().equals("");
		}
		return false;
	}
}