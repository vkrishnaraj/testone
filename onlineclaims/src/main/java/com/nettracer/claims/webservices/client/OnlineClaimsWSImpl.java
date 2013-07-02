package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident;
import com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.OnlineClaimsServiceStub;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.AuthPassenger;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.LoadClaim;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim;
import com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument;
import com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident;
import com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Contents;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Phone;
import com.nettracer.claims.core.model.Address;
import com.nettracer.claims.core.model.Bag;
import com.nettracer.claims.core.model.Content;
import com.nettracer.claims.core.model.IncidentAddressBean;
import com.nettracer.claims.core.model.IncidentBagBean;
import com.nettracer.claims.core.model.IncidentBean;
import com.nettracer.claims.core.model.IncidentPhoneBean;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.faces.util.File;
import com.nettracer.claims.passenger.controller.PassengerLoginController;


@Service
public class OnlineClaimsWSImpl implements OnlineClaimsWS {

	private static Logger logger = Logger
			.getLogger(OnlineClaimsWSImpl.class);

	@Override
	public boolean authAdminUser(String userName, String password)
			throws AxisFault, RemoteException {
		// Prepare stub with endpoint
		OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);

		// Prepare XML documents for request
		AuthAdminUserDocument request = AuthAdminUserDocument.Factory.newInstance();
		AuthAdminUser subDoc1 = request.addNewAuthAdminUser();
		subDoc1.setUsername(userName);
		subDoc1.setPassword(password);
		// Set System Username & PW
		NtAuth subDoc2 = subDoc1.addNewAuth();
		subDoc2.setUsername(SYSTEM_USERNAME);
		subDoc2.setPassword(SYSTEM_PASSWORD);
		// Perform Web Service Request
		AuthAdminUserResponseDocument response = stub.authAdminUser(request);
		// Process response
		return response.getAuthAdminUserResponse().getReturn();
	}

	@Override
	public PassengerView getPassengerView(String claimNumber, String lastName, String firstName)
			throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
         // Prepare XML documents for request 
         AuthPassengerDocument request = AuthPassengerDocument.Factory.newInstance();
         AuthPassenger subDoc1 = request.addNewAuthPassenger();
         subDoc1.setIncidentId(claimNumber);
         subDoc1.setPassengerLastName(lastName);
         subDoc1.setPassengerFirstName(firstName);
         // Set System Username & PW
         NtAuth subDoc2 = subDoc1.addNewAuth();
         subDoc2.setUsername(SYSTEM_USERNAME);
         subDoc2.setPassword(SYSTEM_PASSWORD);
         // Perform Web Service Request
         AuthPassengerResponseDocument response = stub.authPassenger(request);
         // Process response
		 return response.getAuthPassengerResponse().getReturn();
	}	
	

	@Override
	public Claim getClaim(WSPVAdvancedIncident passengerData,String lastName, String firstName)
			throws AxisFault, RemoteException {
		  OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
	         // Prepare XML documents for request 
			LoadClaimDocument request=LoadClaimDocument.Factory.newInstance();
			LoadClaim  subDoc1 = request.addNewLoadClaim();
			subDoc1.setIncidentId(passengerData.getIncidentID());
			subDoc1.setName(lastName);
			subDoc1.setFName(firstName);
			// Set System Username & PW
	        NtAuth subDoc2 = subDoc1.addNewAuth();
	        subDoc2.setUsername(SYSTEM_USERNAME);
	        subDoc2.setPassword(SYSTEM_PASSWORD);

	        // Perform Web Service Request
	        LoadClaimResponseDocument  response=stub.loadClaim(request);
	        return response.getLoadClaimResponse().getReturn();
	}

	@Override
	public PassengerBean getPassengerData(WSPVAdvancedIncident passengerData,Claim claim) 
	 		throws AxisFault, RemoteException{
		PassengerBean passengerBean = new PassengerBean();
		List<Address> addresses=new ArrayList<Address>();
		List<Passenger> passengers=new ArrayList<Passenger>();
		WSPVPassenger[] passengerDatarray=passengerData.getPassengersArray();
		for (int i = 0; i < passengerDatarray.length; i++) {
			Address address=new Address();
			/*address.setEmailAddress(passengerDatarray[i].getEmail());
			address.setPhoneHome(passengerDatarray[i].getHomephone());
			address.setPhoneMobile(passengerDatarray[i].getMobile());
			address.setPhoneBusiness(passengerDatarray[i].getWorkphone());
			address.setHotel(passengerDatarray[i].getHotel());*/
			
			Passenger passenger=new Passenger();
			passenger.setFirstName(passengerDatarray[i].getFirstname());
			passenger.setLastName(passengerDatarray[i].getLastname());
			passenger.setMiddleInitial(passengerDatarray[i].getMiddlename());

			addresses.add(address);
			passengers.add(passenger);
		}
		if(addresses.size() ==1){
			Address address=new Address();
			addresses.add(address);
		}
		/*WSPVItem[] itemArray=passengerData.getItemsArray();
		for (int i = 0; i < itemArray.length; i++) {
			String wSPVItem1=itemArray[i].getAddress1();
			String wSPVItem2=itemArray[i].getAddress2();
		}*/
		if(passengerBean.getItineraryList().size() == 0){
			for(int i=0;i<1;i++){
				passengerBean.getItineraryList().add(new Itinerary());
			}
		}
		passengerBean.setAddress(addresses);
		passengerBean.setPassengers(passengers);
		
		return /*passengerBean;*/ getExistingClaimData(claim,passengerBean);
	}	
	
	private PassengerBean getExistingClaimData(Claim claim,PassengerBean passengerBean) 
				throws AxisFault, RemoteException{
        
        passengerBean.setClaimId(claim.getClaimId()); //mandatory field for the web service
        
        com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] wsPassengers = claim.getPassengerArray();
        List<Passenger> passengers = new ArrayList<Passenger>();
        if (wsPassengers != null && wsPassengers.length > 0) {
        	for (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger wsPass : wsPassengers) {
        		Passenger pass = new Passenger();
        		pass.setAccept(wsPass.getAccept());
        		pass.setFirstName(wsPass.getFirstName());
        		pass.setMiddleInitial(wsPass.getMiddleInitial());
        		pass.setLastName(wsPass.getLastName());
        		pass.setSalutation(wsPass.getSalutation());
        		passengers.add(pass);
        	}
        } else {
        	passengers.add(new Passenger());
        }
        passengerBean.setPassengers(passengers);
        
        passengerBean.setBusinessName(claim.getBusinessName());
    	passengerBean.setOccupation(claim.getOccupation());
    	passengerBean.setEmailAddress(claim.getEmailAddress());
        com.bagnet.nettracer.ws.onlineclaims.xsd.Address wsPermanentAddress=claim.getPermanentAddress();
        Address permanentAddress=passengerBean.getAddress().get(0);
        if(null != permanentAddress ){
        	if(null !=wsPermanentAddress){
        	permanentAddress.setAddressLine1(wsPermanentAddress.getAddress1());
        	permanentAddress.setAddressLine2(wsPermanentAddress.getAddress2());
        	permanentAddress.setCity(wsPermanentAddress.getCity());
        	permanentAddress.setCountry(wsPermanentAddress.getCountry());
        	permanentAddress.setPostalCode(wsPermanentAddress.getPostalCode());
        	permanentAddress.setStateRegion(wsPermanentAddress.getStateProvince());
        	}
        	
        	
        	Phone[] phone=claim.getPhoneArray();
        	
        	for (int i = 0; i < phone.length; i++) {
        		if(phone[i].getPhoneType().contains("Home")){
        			permanentAddress.setPhoneHome(phone[i].getPhoneNumber());
        		}else if(phone[i].getPhoneType().contains("Mobile") ){
        			permanentAddress.setPhoneMobile(phone[i].getPhoneNumber());
        		}else if(phone[i].getPhoneType().contains("Business") ){
        			permanentAddress.setPhoneBusiness(phone[i].getPhoneNumber());
        		}else if(phone[i].getPhoneType().contains("Fax") ){
        			permanentAddress.setPhoneFax(phone[i].getPhoneNumber());
        		}
			}
        	passengerBean.getAddress().set(0, permanentAddress);
        	
        }
        
        com.bagnet.nettracer.ws.onlineclaims.xsd.Address wsMailingAddress=claim.getMailingAddress();
        Address mailingAddress=passengerBean.getAddress().get(1);
        if(null != mailingAddress && null !=wsMailingAddress){
        	mailingAddress.setAddressLine1(wsMailingAddress.getAddress1());
        	mailingAddress.setAddressLine2(wsMailingAddress.getAddress2());
        	mailingAddress.setCity(wsMailingAddress.getCity());
        	mailingAddress.setCountry(wsMailingAddress.getCountry());
        	mailingAddress.setPostalCode(wsMailingAddress.getPostalCode());
        	mailingAddress.setStateRegion(wsMailingAddress.getStateProvince());
        	passengerBean.getAddress().set(1, mailingAddress);
        }
        
        
        passengerBean.setFrequentFlyerNumber(null != claim.getFrequentFlierNumber() 
        		? claim.getFrequentFlierNumber() :"");
        passengerBean.setSocialSecurityNumber(null != claim.getSocialSecurity() 
        		? claim.getSocialSecurity() :"");
        passengerBean.setBagsTravelWith(claim.getBagsTravelWith());
        passengerBean.setLostBag(claim.getBagsDelayed());
        passengerBean.setBagCheckLocation(null != claim.getCheckedLocation()
        		? claim.getCheckedLocation() :"");
        passengerBean.setNoOfPassenger(claim.getPassengersTravelingWithYou());
        passengerBean.setRecheckBag(intToBool(claim.getHaveToRecheck()));
        passengerBean.setInspectbag(intToBool(claim.getWasBagInspected()));
        passengerBean.setChargeExcessBaggage(intToBool(claim.getChargedForExcessBaggage()));
        passengerBean.setNoOfCheckedBag(0);
        passengerBean.setNoOfmissingBag(claim.getBagsStillMissing());
        passengerBean.setBagClaimCheck(""); //not DifferentClaimCheck
        passengerBean.setDeclarePayExcessValue(intToBool(claim.getDeclaredExcessValue()));
        passengerBean.setDeclaredValue(null != claim.getDeclaredValue() 
        		&& ! claim.getDeclaredValue().equals("?")
        		? Double.parseDouble(claim.getDeclaredValue()) : 0D);
        passengerBean.setClearCustomBag(claim.getBagClearCustoms());
        passengerBean.setBagWeight(claim.getBagWeight());
        passengerBean.setRerouteBag(intToBool(claim.getBaggageReroutedEnRoute()));
        passengerBean.setDifferentClaimCheck(intToBool(claim.getDifferentClaimCheck()));
        passengerBean.setReroutedCityAirline(claim.getReroutedAirlineCity());
        passengerBean.setReason(claim.getReroutedReason());
        passengerBean.setImmediateClaimAttempt(intToBool(claim.getAttemptToClaimAtArrival()));
        passengerBean.setWhenBagSeen(claim.getLastSawBaggage());
        passengerBean.setFileReportCity(claim.getWhereDidYouFileReport());
        passengerBean.setReportAnotherAirline(intToBool(claim.getReportedToAnotherAirline()));
        passengerBean.setDifferentAirlineTicket(intToBool(claim.getTicketWithAnotherAirline()));
        passengerBean.setTicketNumber(claim.getTicketNumber());
        passengerBean.setDeclaredValueCurrency(claim.getDeclaredCurrency());	
        passengerBean.setClaimAmountCurrency(claim.getPaxClaimAmountCurrency());
        passengerBean.setBagCheckDescription(claim.getCheckedLocationDescription());
        
        if (wsPermanentAddress != null && wsPermanentAddress.getAddress1() != null && wsPermanentAddress.getAddress1().length() > 0) {
        	passengerBean.setDelayed(isBitSet(claim.getClaimType(), 0));
        	passengerBean.setPilferage(isBitSet(claim.getClaimType(), 1));
        	passengerBean.setDamaged(isBitSet(claim.getClaimType(), 2));
        	passengerBean.setInterim(isBitSet(claim.getClaimType(), 3));
        }
        
               
        //itinerary
        setItineraries(claim,passengerBean);
        
        //Bag
        setBags(claim,passengerBean);
        
        //File
        setFiles(claim,passengerBean);
       
        //Fraud Question
        passengerBean.setAnotherClaim(claim.getFiledPreviousClaim());
        passengerBean.setWhichAirline(claim.getFiledPreviousAirline());
        if(null != claim.getFiledPrevoiusDate()){
        	Calendar temp = claim.getFiledPrevoiusDate();
        	int timeDiff = figureTimeDifference(temp);
        	temp.add(Calendar.HOUR_OF_DAY, timeDiff);
        	passengerBean.setDateOfClaim(temp.getTime());
        }
        passengerBean.setClaimantName(claim.getFiledPreviousClaimant());
        passengerBean.setTsaInspect(claim.getTsaInspected());
        passengerBean.setBagConfirmNote(claim.getTsaNotePresent());
        passengerBean.setInspectionPlace(claim.getTsaInspectionLocation());
        passengerBean.setAdditionalComments(claim.getComments());
        passengerBean.setClaimAmount(claim.getPaxClaimAmount());
        passengerBean.setClaimDate(claim.getPaxClaimDate());
        passengerBean.setPrivateInsurance(claim.getPrivateInsurance());
        passengerBean.setPrivateInsuranceName(claim.getPrivateInsuranceName());
        passengerBean.setPrivateInsuranceAddr(claim.getPrivateInsuranceAddr());
        passengerBean.setReportedAirline(claim.getReportedAirline());
        passengerBean.setReportedCity(claim.getReportedCity());
        passengerBean.setReportedFileNumber(claim.getReportedFileNumber());
        passengerBean.setRequestForeignCurrency(claim.getRequestForeignCurrency());
        passengerBean.setForeignCurrencyEmail(claim.getForeignCurrencyEmail());
        if(null != claim.getBagReceivedDate()){
        	Calendar temp = claim.getBagReceivedDate();
        	int timeDiff = figureTimeDifference(temp);
        	temp.add(Calendar.HOUR_OF_DAY, timeDiff);
        	passengerBean.setBagReceivedDate(temp.getTime());
        }
        passengerBean.setReasonForTravel(claim.getReasonForTravel());
        passengerBean.setLengthOfStay(claim.getLengthOfStay());
        
        passengerBean.setStatus(claim.getStatus());
        
       
       return passengerBean;
									
	}

	@Override
	public boolean savePassengerInfo(PassengerBean passengerBean,Claim claim) throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		 com.bagnet.nettracer.ws.onlineclaims.xsd.Address wsPermanentAddress=null;
		 com.bagnet.nettracer.ws.onlineclaims.xsd.Address wsMailingAddress=null;
		 Phone[] phoneArray= null;
		 
         // Prepare XML documents for request
		 SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		 SaveClaim subDoc1 = request.addNewSaveClaim();
		 
		 claim.setEmailAddress(passengerBean.getEmailAddress());
		 claim.setOccupation(passengerBean.getOccupation());
		 claim.setBusinessName(passengerBean.getBusinessName());
		 claim.setFrequentFlierNumber(passengerBean.getFrequentFlyerNumber());
		 claim.setSocialSecurity(passengerBean.getSocialSecurityNumber());
		 
		 //Passengers
		 claim.setPassengerArray(null);
		 for (Passenger pass : passengerBean.getPassengers()) {
			 com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger claimPass = claim.addNewPassenger();
			 claimPass.setFirstName(pass.getFirstName());
			 claimPass.setMiddleInitial(pass.getMiddleInitial());
			 claimPass.setLastName(pass.getLastName());
			 claimPass.setSalutation(pass.getSalutation());
			 claimPass.setAccept(pass.getAccept());
		 }
		 
		 //Addresses
		 wsPermanentAddress = com.bagnet.nettracer.ws.onlineclaims.xsd.Address.Factory.newInstance();
		 wsPermanentAddress.setAddress1(passengerBean.getAddress().get(0).getAddressLine1());
		 wsPermanentAddress.setAddress2(passengerBean.getAddress().get(0).getAddressLine2());
		 wsPermanentAddress.setCity(passengerBean.getAddress().get(0).getCity());
		 wsPermanentAddress.setCountry(passengerBean.getAddress().get(0).getCountry());
		 wsPermanentAddress.setPostalCode(passengerBean.getAddress().get(0).getPostalCode());
		 wsPermanentAddress.setStateProvince(passengerBean.getAddress().get(0).getStateRegion());
		 claim.setPermanentAddress(wsPermanentAddress);
		 
		 wsMailingAddress= com.bagnet.nettracer.ws.onlineclaims.xsd.Address.Factory.newInstance();
		 wsMailingAddress.setAddress1(passengerBean.getAddress().get(1).getAddressLine1());
		 wsMailingAddress.setAddress2(passengerBean.getAddress().get(1).getAddressLine2());
		 wsMailingAddress.setCity(passengerBean.getAddress().get(1).getCity());
		 wsMailingAddress.setCountry(passengerBean.getAddress().get(1).getCountry());
		 wsMailingAddress.setPostalCode(passengerBean.getAddress().get(1).getPostalCode());
		 wsMailingAddress.setStateProvince(passengerBean.getAddress().get(1).getStateRegion());
		 claim.setMailingAddress(wsMailingAddress);
		 
		 //Phones
		 Phone phone1=Phone.Factory.newInstance();
		 phone1.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneHome());
		 phone1.setPhoneType("Home");
			 
		 Phone phone2=Phone.Factory.newInstance();
		 phone2.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneMobile());
		 phone2.setPhoneType("Mobile");
			 
		 Phone phone3=Phone.Factory.newInstance();
		 phone3.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneFax());
		 phone3.setPhoneType("Fax");
			 
		 Phone phone4=Phone.Factory.newInstance();
		 phone4.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneBusiness());
		 phone4.setPhoneType("Business");
			 
		 phoneArray=new Phone[]{phone1,phone2,phone3,phone4};
		 
		 claim.setPhoneArray(phoneArray);
		          
         //Bag Tags
         List<Bag> bagTagList = passengerBean.getBagTagList();
         com.bagnet.nettracer.ws.onlineclaims.xsd.Bag wsBag=null;
         Bag bag=null;
         if(null != bagTagList && bagTagList.size() >0){
        	List<Bag> cleanBagList = new ArrayList<Bag>();
			for (int i=0; i<bagTagList.size();i++) {
				bag=bagTagList.get(i);
				if (null != bag.getBagTagNumber()) {
					cleanBagList.add(bag);
				}
			}
	        if(null != cleanBagList && cleanBagList.size() >0){
				com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[cleanBagList.size()];
					for (int i=0; i<cleanBagList.size();i++) {
						wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
						bag=cleanBagList.get(i);
						wsBag.setTag(bag.getBagTagNumber());
						wsBag.setBagArrive(new Boolean (bag.getBagArrivalStatus()));
						wsBagArray[i]=wsBag;
					}
				claim.setBagArray(wsBagArray);
	        }
         }
         
         //Itineraries
         setWSItineraries(passengerBean.getItineraryList(),claim); // GOOD TO GO	 
         
         //Bag Info
         setWSBags(passengerBean.getBagList(),claim,passengerBean.isInterim()); // GOOD TO GO
         
         //Files Uploaded
         setWSFiles(passengerBean.getFiles(),claim); // GOOD TO GO
		 
 		 int claimType = 0;
		 claimType = encodeBit(claimType, 0, passengerBean.isDelayed());
		 claimType = encodeBit(claimType, 1, passengerBean.isPilferage());
		 claimType = encodeBit(claimType, 2, passengerBean.isDamaged());
		 claimType = encodeBit(claimType, 3, passengerBean.isInterim());
		 claim.setClaimType(claimType);
		 Calendar calendar;
			if(null !=passengerBean.getBagReceivedDate()){
				calendar=Calendar.getInstance();
				calendar.setTime(passengerBean.getBagReceivedDate());
				claim.setBagReceivedDate(calendar);
				calendar=null;//GC
			}
		 
         subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
         subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());
         
         subDoc1.setClaim(claim);
         // Set System Username & PW
         NtAuth subDoc2 = subDoc1.addNewAuth();
         subDoc2.setUsername(SYSTEM_USERNAME);
         subDoc2.setPassword(SYSTEM_PASSWORD);
         // Perform Web Service Request
         SaveClaimResponseDocument response = stub.saveClaim(request);
         return response.getSaveClaimResponse().getReturn();
	}
	
	
	@Override
	public boolean saveFlightInfo(PassengerBean passengerBean,Claim claim) throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		 SaveClaim subDoc1 = null;
		 NtAuth subDoc2 = null;
		 
         // Prepare XML documents for request
		 SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		 if(null != request.getSaveClaim()){
			 subDoc1 = request.getSaveClaim();
		 }else{
			 subDoc1 = request.addNewSaveClaim(); 
		 }
		 subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
		 subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());
		 
		//Flight Information Data
		 claim.setClaimId(passengerBean.getClaimId()); //mandatory field for save through web service
		 claim.setBagsTravelWith(passengerBean.getBagsTravelWith());
         claim.setBagsDelayed(passengerBean.getLostBag());
         claim.setCheckedLocation(null != passengerBean.getBagCheckLocation()
         		? passengerBean.getBagCheckLocation() :"");
         claim.setCheckedLocationDescription(passengerBean.getBagCheckDescription());
         claim.setPassengersTravelingWithYou(passengerBean.getNoOfPassenger());
         claim.setHaveToRecheck(boolToInt(passengerBean.getRecheckBag()));
         claim.setWasBagInspected(boolToInt(passengerBean.getInspectbag()));
         claim.setChargedForExcessBaggage(boolToInt(passengerBean.getChargeExcessBaggage()));
         //claim.setNoOfCheckedBag(0); //To be implemented later
         claim.setBagsStillMissing(passengerBean.getNoOfmissingBag());
         //claim.setBagpassengerBeanCheck(""); //not DifferentpassengerBeanCheck
         claim.setDeclaredExcessValue(boolToInt(passengerBean.getDeclarePayExcessValue()));
         claim.setDeclaredValue(passengerBean.getDeclaredValue()+"");
         claim.setDeclaredCurrency(passengerBean.getDeclaredValueCurrency()); 
         claim.setBagClearCustoms(passengerBean.getClearCustomBag());
         claim.setBagWeight(passengerBean.getBagWeight());              
         claim.setBaggageReroutedEnRoute(boolToInt(passengerBean.getRerouteBag()));
         claim.setDifferentClaimCheck(boolToInt(passengerBean.getDifferentClaimCheck()));
         claim.setReroutedAirlineCity(passengerBean.getReroutedCityAirline());
         claim.setReroutedReason(passengerBean.getReason());
         claim.setAttemptToClaimAtArrival(boolToInt(passengerBean.getImmediateClaimAttempt()));
         claim.setLastSawBaggage(passengerBean.getWhenBagSeen());
         claim.setWhereDidYouFileReport(passengerBean.getFileReportCity());
         claim.setReportedToAnotherAirline(boolToInt(passengerBean.getReportAnotherAirline()));
         claim.setTicketWithAnotherAirline(boolToInt(passengerBean.getDifferentAirlineTicket()));
         claim.setTicketNumber(passengerBean.getTicketNumber());
         claim.setReportedAirline(passengerBean.getReportedAirline());
         claim.setReportedCity(passengerBean.getReportedCity());
         claim.setReportedFileNumber(passengerBean.getReportedFileNumber());
         claim.setReasonForTravel(passengerBean.getReasonForTravel());
         claim.setLengthOfStay(passengerBean.getLengthOfStay());
         
         //Bag Tags
         setBagTags(passengerBean.getBagTagList(),claim);
         
         //itinerary
         setWSItineraries(passengerBean.getItineraryList(),claim);
		 
         subDoc1.setClaim(claim);
         if(null != subDoc1.getAuth()){
        	 subDoc2 = subDoc1.getAuth();
         }else{
        	 // Set System Username & PW
        	 subDoc2 = subDoc1.addNewAuth();
        	  subDoc2.setUsername(SYSTEM_USERNAME);
        	  subDoc2.setPassword(SYSTEM_PASSWORD);
         }
         
         // Perform Web Service Request
         SaveClaimResponseDocument response = stub.saveClaim(request);
         return response.getSaveClaimResponse().getReturn();
	}
	
	@Override
	public boolean saveBagInfo(PassengerBean passengerBean,Claim claim) throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		 SaveClaim subDoc1 = null;
		 NtAuth subDoc2 = null;
		 
         // Prepare XML documents for request
		 SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		 if(null != request.getSaveClaim()){
			 subDoc1 = request.getSaveClaim();
		 }else{
			 subDoc1 = request.addNewSaveClaim(); 
		 }
		 subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
		 subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());
		 
		 setWSBags(passengerBean.getBagList(),claim,passengerBean.isInterim());
		 
		 subDoc1.setClaim(claim);
         if(null != subDoc1.getAuth()){
        	 subDoc2 = subDoc1.getAuth();
         }else{
        	 // Set System Username & PW
        	 subDoc2 = subDoc1.addNewAuth();
        	  subDoc2.setUsername(SYSTEM_USERNAME);
        	  subDoc2.setPassword(SYSTEM_PASSWORD);
         }
         
         // Perform Web Service Request
         SaveClaimResponseDocument response = stub.saveClaim(request);
         return response.getSaveClaimResponse().getReturn();
	}
	
	
	public boolean saveFileInfo(PassengerBean passengerBean, Claim claim) throws AxisFault, RemoteException{
		OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		SaveClaim subDoc1 = null;
		NtAuth subDoc2 = null;
		 
		// Prepare XML documents for request
		SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		if(null != request.getSaveClaim()){
			subDoc1 = request.getSaveClaim();
		}else{
			subDoc1 = request.addNewSaveClaim(); 
		}
		subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
		subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());

		setWSFiles(passengerBean.getFiles(), claim);
		
		subDoc1.setClaim(claim);
		if(null != subDoc1.getAuth()){
			subDoc2 = subDoc1.getAuth();
		}else{
			// Set System Username & PW
			subDoc2 = subDoc1.addNewAuth();
			subDoc2.setUsername(SYSTEM_USERNAME);
			subDoc2.setPassword(SYSTEM_PASSWORD);
		}

		// Perform Web Service Request
		SaveClaimResponseDocument response = stub.saveClaim(request);
		return response.getSaveClaimResponse().getReturn();

	}
	
	
	public boolean saveFraudQuestion(PassengerBean passengerBean,
			Claim claim)throws AxisFault, RemoteException{
		OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		Calendar calendar=null;
		SaveClaim subDoc1 = null;
		NtAuth subDoc2 = null;
		 
		// Prepare XML documents for request
		SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		if(null != request.getSaveClaim()){
			subDoc1 = request.getSaveClaim();
		}else{
			subDoc1 = request.addNewSaveClaim(); 
		}
		subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
		subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());

		claim.setFiledPreviousClaim(passengerBean.getAnotherClaim());
        claim.setFiledPreviousAirline(passengerBean.getWhichAirline());
        if(null != passengerBean.getDateOfClaim()){
       	 calendar=Calendar.getInstance();
       	 calendar.setTime(passengerBean.getDateOfClaim());
       	 //calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
       	 claim.setFiledPrevoiusDate(calendar);
       	 calendar=null; //GC
        }
        claim.setFiledPreviousClaimant(passengerBean.getClaimantName());
        claim.setTsaInspected(passengerBean.getTsaInspect());
        claim.setTsaNotePresent(passengerBean.getBagConfirmNote());
        claim.setTsaInspectionLocation(passengerBean.getInspectionPlace());
        claim.setComments(passengerBean.getAdditionalComments());
        claim.setPrivateInsurance(passengerBean.getPrivateInsurance());
        claim.setPrivateInsuranceName(passengerBean.getPrivateInsuranceName());
        claim.setPrivateInsuranceAddr(passengerBean.getPrivateInsuranceAddr());
        claim.setRequestForeignCurrency(passengerBean.getRequestForeignCurrency());
        claim.setForeignCurrencyEmail(passengerBean.getForeignCurrencyEmail());
		 
		subDoc1.setClaim(claim);
		if(null != subDoc1.getAuth()){
			subDoc2 = subDoc1.getAuth();
		}else{
			// Set System Username & PW
			subDoc2 = subDoc1.addNewAuth();
			subDoc2.setUsername(SYSTEM_USERNAME);
			subDoc2.setPassword(SYSTEM_PASSWORD);
		}

		// Perform Web Service Request
		SaveClaimResponseDocument response = stub.saveClaim(request);
		return response.getSaveClaimResponse().getReturn();

	}
	
	public boolean saveFinalCLaim(PassengerBean passengerBean, Claim claim)
			throws AxisFault, RemoteException{
		OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
		SaveClaim subDoc1 = null;
		NtAuth subDoc2 = null;
		 
		// Prepare XML documents for request
		SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		if(null != request.getSaveClaim()){
			subDoc1 = request.getSaveClaim();
		}else{
			subDoc1 = request.addNewSaveClaim(); 
		}
		subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
		subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());

		 claim.setPassengerArray(null);
		 for (Passenger pass : passengerBean.getPassengers()) {
			 com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger claimPass = claim.addNewPassenger();
			 claimPass.setFirstName(pass.getFirstName());
			 claimPass.setMiddleInitial(pass.getMiddleInitial());
			 claimPass.setLastName(pass.getLastName());
			 claimPass.setSalutation(pass.getSalutation());
			 claimPass.setAccept(pass.getAccept());
		 }
		 claim.setPaxClaimAmount(passengerBean.getClaimAmount());
		 claim.setPaxClaimAmountCurrency(passengerBean.getClaimAmountCurrency());
		 claim.setPaxClaimDate(passengerBean.getClaimDate());
		 claim.setPaxIpAddress(((HttpServletRequest) FacesContext.getCurrentInstance()
				 .getExternalContext().getRequest()).getRemoteAddr());
			
		subDoc1.setClaim(claim);
		if(null != subDoc1.getAuth()){
			subDoc2 = subDoc1.getAuth();
		}else{
			// Set System Username & PW
			subDoc2 = subDoc1.addNewAuth();
			subDoc2.setUsername(SYSTEM_USERNAME);
			subDoc2.setPassword(SYSTEM_PASSWORD);
		}

		// Perform Web Service Request
		SaveClaimResponseDocument response = stub.saveClaim(request);
		return response.getSaveClaimResponse().getReturn();

	}
	
	/**
	 * Flight Information Page: Ticket-info section: -- Setting the bag tag nos. and arriva status
	 * 	with the webservices.
	 * @param bagTagList
	 * @param claim
	 */

	private void setBagTags(List<Bag> bagTagList, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArrayTemp = claim.getBagArray();
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag wsBag=null;
		Bag bag=null;
		if(null != bagTagList && bagTagList.size() >0){
        	List<Bag> cleanBagList = new ArrayList<Bag>();
			for (int i=0; i<bagTagList.size();i++) {
				bag=bagTagList.get(i);
				if (null != bag.getBagTagNumber()) {
					cleanBagList.add(bag);
				}
			}
	        if(null != cleanBagList && cleanBagList.size() >0){
				com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[cleanBagList.size()];
				if (wsBagArrayTemp == null || wsBagArrayTemp.length == 0) {
					for (int i=0; i<cleanBagList.size();i++) {
						wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
						bag=cleanBagList.get(i);
						wsBag.setTag(bag.getBagTagNumber());
						wsBag.setBagArrive(new Boolean (bag.getBagArrivalStatus()));
						wsBagArray[i]=wsBag;
					}
				} else {
					for (int i=0; i<cleanBagList.size();i++) {
						bag=cleanBagList.get(i);
						boolean bagArrStat = Boolean.parseBoolean(bag.getBagArrivalStatus());
						boolean notAdded = true;
						if (!bagArrStat) {
							for (int j = 0; j < wsBagArrayTemp.length; j++) {
								wsBag = wsBagArrayTemp[j];
								if (wsBag != null && !wsBag.getBagArrive() && wsBag.getTag() != null && wsBag.getTag().equals(bag.getBagTagNumber())) {
									notAdded = false;
									wsBagArrayTemp[j] = null;
									break;
								}
							}					
						}
						if (notAdded) {
							wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
						}
						wsBag.setTag(bag.getBagTagNumber());
						wsBag.setBagArrive(new Boolean (bag.getBagArrivalStatus()));
						wsBagArray[i]=wsBag;
					}
				}
				claim.setBagArray(wsBagArray);
	        }
		}

	}

	private void setItineraries(Claim claim, PassengerBean passengerBean) {
		List<Itinerary> itineraryList=null;
		Itinerary itinerary =null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] wsItinerary =claim.getItineraryArray();
		if(null != wsItinerary && wsItinerary.length >0){
			itineraryList=new ArrayList<Itinerary>();
			for (int i = 0; i < wsItinerary.length; i++) {
				itinerary = new Itinerary();
				itinerary.setDepartureCity(wsItinerary[i].getDepartureCity());
				itinerary.setArrivalCity(wsItinerary[i].getArrivalCity());
				itinerary.setAirline(wsItinerary[i].getAirline());
				itinerary.setFlightNum(wsItinerary[i].getFlightNum());
				if(null != wsItinerary[i].getDate()){
					Calendar temp = wsItinerary[i].getDate();
		        	int timeDiff = figureTimeDifference(temp);
		        	temp.add(Calendar.HOUR_OF_DAY, timeDiff);
					itinerary.setJourneyDate(temp.getTime());
				}
				itineraryList.add(itinerary); //Garbage collection
				itinerary=null;
			}
			passengerBean.setItineraryList(itineraryList);
			itineraryList=null; //Garbage collection
		}

	}
	
	/**
	 * Populating the existing Bag Information from webservice
	 * @param claim
	 * @param passengerBean
	 */
	private void setBags(Claim claim, PassengerBean passengerBean) {
		List<Bag> bagList=null;
		List<Bag> bagTagList = null;
		Bag bag=null;
		List<Content> contentList=null;
		Content content=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBag=claim.getBagArray();
		if(null != wsBag && wsBag.length >0){
			bagList=new ArrayList<Bag>();
			bagTagList = new ArrayList<Bag>();
			for (int i = 0; i < wsBag.length; i++) {
				bag= new Bag();
				bag.setBagTagNumber(wsBag[i].getTag()); //clarification pending for bagtag number
				bag.setNameonBag(wsBag[i].getNameOnBag());
				bag.setBrandOftheBag(wsBag[i].getBrand());
				bag.setExternalMarkings(wsBag[i].getExternalMarkings());
				if(null != wsBag[i].getPurchaseDate()){
					Calendar temp = wsBag[i].getPurchaseDate();
		        	int timeDiff = figureTimeDifference(temp);
		        	temp.add(Calendar.HOUR_OF_DAY, timeDiff);
					bag.setBagPurchaseDate(temp.getTime());
				}
				bag.setBagColor(wsBag[i].getBagColor());
				bag.setBagType(wsBag[i].getBagType());
				bag.setHardSided(wsBag[i].getHardSided());
				bag.setSoftSided(wsBag[i].getSoftSided());
				bag.setLocks(wsBag[i].getLocks());
				bag.setWheelsRollers(wsBag[i].getWheels());
				bag.setZippers(wsBag[i].getZippers());
				bag.setPullStrap(wsBag[i].getPullStrap());
				bag.setFeet(wsBag[i].getFeet());
				bag.setRetractableHandel(wsBag[i].getRetractibleHandle());
				bag.setNameTag(wsBag[i].getNameTag());
				bag.setTrim(wsBag[i].getTrim());
				bag.setPockets(wsBag[i].getPockets());
				bag.setRibbonsPersonalMarkings(wsBag[i].getRibbonsOrMarkings());
				bag.setBagPrice(wsBag[i].getBagValue());
				bag.setBagCurrency(wsBag[i].getBagCurrency());
				bag.setBagOwner(wsBag[i].getBagOwner());
				bag.setLeather(wsBag[i].getLeather());
				bag.setMetal(wsBag[i].getMetal());
				bag.setTrimDescription(wsBag[i].getTrimDescription());
				
				//ticket section
				bag.setBagArrivalStatus(wsBag[i].getBagArrive()+"");

				Contents[] wsContents =wsBag[i].getContentsArray();
				if(null !=wsContents && wsContents.length >0){
					contentList= new ArrayList<Content>();
					for (int j = 0; j < wsContents.length; j++) {
						content=new Content();
						content.setQuantity(wsContents[j].getQuantity());
						content.setMale(wsContents[j].getMale());
						content.setArticle(wsContents[j].getArticle());
						content.setColor(wsContents[j].getColor());
						content.setSize(wsContents[j].getSize());
						content.setBrandOrDescription(wsContents[j].getBrand());
						content.setStorePurchased(wsContents[j].getPurchasedAt());
						content.setPurchasedDate(wsContents[j].getPurchasedDate());
						content.setPrice(wsContents[j].getPrice());
						content.setPriceString(wsContents[j].getPrice() + "");
						content.setCurrency(wsContents[j].getCurrency());
						content.setContentOwner(wsContents[j].getContentOwner());
						contentList.add(content);
						content=null; //GC
					}
					bag.setContentList(contentList);
					contentList=null; //GC

				}
				// ONLY ADD BAG IF THEY ARE FILING A PILFERAGE OR DAMAGE
				// IF THOSE ARE FALSE THEN ADD INTERIM BAG IF THEY ARE FILING INTERIM
				// OTHERWISE JUST ADD BAGS MARKED AS DELAYED
				if (passengerBean.isPilferage() 
						|| passengerBean.isDamaged() 
						|| (passengerBean.isInterim() && "INTERIM".equals(bag.getBagTagNumber())) 
						|| !wsBag[i].getBagArrive()) {
					bagList.add(bag);
				}
				if (!"INTERIM".equals(bag.getBagTagNumber())) {
					Bag tempBag = new Bag();
					tempBag.setBagArrivalStatus(wsBag[i].getBagArrive()+"");
					tempBag.setBagTagNumber(wsBag[i].getTag());
					bagTagList.add(tempBag);
				}
				bag=null; //GC
			}
			passengerBean.setBagList(bagList);
			passengerBean.setBagTagList(bagTagList); //For ticket-info
		}

	}
	
	private void setFiles(Claim claim, PassengerBean passengerBean) {
		List<File> files=null;
		File file=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.File[]  wsFile=claim.getFileArray();
		if(null != wsFile && wsFile.length >0){
			files = new ArrayList<File> ();
			for (int i = 0; i < wsFile.length; i++) {
				file = new File();
				file.setName(wsFile[i].getFilename());
				file.setPath(wsFile[i].getPath());
				file.setInterim(wsFile[i].getInterim());
				files.add(file);
				file=null; //GC
			}
			passengerBean.setFiles(files);
			files=null; //GC
		}
		
	}
	
	private void setWSItineraries(List<Itinerary> itineraries, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] wsItineraries= null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary wsItinerary=null;
		Calendar calendar=null;
		if(null != itineraries && itineraries.size() >0){
			wsItineraries=new com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[itineraries.size()];
			for (int i=0; i<itineraries.size();i++) {
				wsItinerary = com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary.Factory.newInstance();
				Itinerary itinerary=itineraries.get(i);
				if(null != itinerary){
					wsItinerary.setDepartureCity(itinerary.getDepartureCity());
					wsItinerary.setArrivalCity(itinerary.getArrivalCity());
					wsItinerary.setAirline(itinerary.getAirline());
					wsItinerary.setFlightNum(itinerary.getFlightNum());
					if(null != itinerary.getJourneyDate()){
						calendar=Calendar.getInstance();
						calendar.setTime(itinerary.getJourneyDate());
						//calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
						wsItinerary.setDate(calendar);
						calendar=null;//GC
					}
					wsItineraries[i]=wsItinerary;
					wsItinerary=null;//GC
				}
			}
			claim.setItineraryArray(wsItineraries);
		}
		

	}
	
	private void setWSBags(List<Bag> bagList, Claim claim, boolean interim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArray= null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag wsBag=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[] wsContentArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Contents wsContent=null;
		Bag bag=null;
		Content content=null;
		Calendar calendar=null;
		if(null != bagList && bagList.size() >0){
			wsBagArray=claim.getBagArray();
			int offset = 0;
			if (interim && !"INTERIM".equals(wsBagArray[wsBagArray.length - 1].getTag())) {
				offset = 1;
			}
			com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] newWsBagArray =
					new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[wsBagArray.length + offset];
			int bagIndex = 0;
			for (int i=0; i<bagList.size();i++) {
				wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
				bag=bagList.get(i);
				if(null != bag && null != bag.getBagTagNumber()){
					wsBag.setTag(bag.getBagTagNumber());
					if (!"INTERIM".equals(bag.getBagTagNumber())) {
						wsBag.setNameOnBag(bag.getNameonBag());
						wsBag.setBrand(bag.getBrandOftheBag());
						wsBag.setExternalMarkings(bag.getExternalMarkings());
						if(null !=bag.getBagPurchaseDate()){
							calendar=Calendar.getInstance();
							calendar.setTime(bag.getBagPurchaseDate());
							//calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
							wsBag.setPurchaseDate(calendar);
							calendar=null;//GC
						}
	
						wsBag.setBagColor(bag.getBagColor());
						wsBag.setBagType(bag.getBagType());
						wsBag.setHardSided(bag.getHardSided());
						wsBag.setSoftSided(bag.getSoftSided());
						wsBag.setLocks(bag.getLocks());
						wsBag.setWheels(bag.getWheelsRollers());
						wsBag.setZippers(bag.getZippers());
						wsBag.setPullStrap(bag.getPullStrap());
						wsBag.setFeet(bag.getFeet());
						wsBag.setRetractibleHandle(bag.getRetractableHandel());
						wsBag.setNameTag(bag.getNameTag());
						wsBag.setTrim(bag.getTrim());
						wsBag.setPockets(bag.getPockets());
						wsBag.setRibbonsOrMarkings(bag.getRibbonsPersonalMarkings());
						wsBag.setBagValue(bag.getBagPrice());
						wsBag.setBagCurrency(bag.getBagCurrency());
						wsBag.setBagOwner(bag.getBagOwner());
						wsBag.setLeather(bag.isLeather());
						wsBag.setMetal(bag.isMetal());
						wsBag.setTrimDescription(bag.getTrimDescription());
					}
					//ticket section
					wsBag.setBagArrive(new Boolean(bag.getBagArrivalStatus()));

					List<Content> contents =bag.getContentList();
					if(null !=contents && contents.size() >0){
						wsContentArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[contents.size()];
						for (int j = 0; j < contents.size(); j++) {
							wsContent = com.bagnet.nettracer.ws.onlineclaims.xsd.Contents.Factory.newInstance();
							content=contents.get(j);
							if(null != content){
								wsContent.setQuantity(content.getQuantity());
								wsContent.setMale(content.getMale());
								wsContent.setArticle(content.getArticle());
								wsContent.setColor(content.getColor());
								wsContent.setSize(content.getSize());
								wsContent.setBrand(content.getBrandOrDescription());
								wsContent.setPurchasedAt(content.getStorePurchased());
								wsContent.setPurchasedDate(content.getPurchasedDate());
								wsContent.setPrice(content.getPrice());
								wsContent.setCurrency(content.getCurrency());
								wsContent.setContentOwner(content.getContentOwner());
								wsContentArray[j]=wsContent;
								wsContent=null; //GC
								content=null;
							}
						}
						wsBag.setContentsArray(wsContentArray);
						wsContentArray=null; //GC
					}
					for (int j = 0; j < wsBagArray.length; j++) {
						if (wsBagArray[j] != null && wsBagArray[j].getTag().equals(wsBag.getTag())) {
							newWsBagArray[bagIndex]=wsBag;
							bagIndex++;
							wsBagArray[j] = null;
							break;
						}
					}
					if (wsBag.getTag().equals("INTERIM")) {
						newWsBagArray[newWsBagArray.length - 1]=wsBag;
					}
					wsBag=null;//GC
					bag=null;
				}
			}
			for (int i = 0; i < wsBagArray.length; i++) {
				if (wsBagArray[i] != null) {
					newWsBagArray[bagIndex]=wsBagArray[i];
					bagIndex++;
				}
			}
			if (interim && newWsBagArray[newWsBagArray.length - 1] == null) {
				wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
				wsBag.setBagArrive(true);
				wsBag.setTag("INTERIM");
				newWsBagArray[newWsBagArray.length - 1] = wsBag;
				wsBag=null;//GC
			}
			claim.setBagArray(newWsBagArray);
		}
		
	}
	
	/**
	 * File Upload: Set all the File information to the webservice before saving.
	 * @param files
	 * @param claim
	 */
	
	private void setWSFiles(List<File> files, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.File[] wsFileArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.File wsFile=null;
		File file=null;

		if(null != files && files.size() >0){
			wsFileArray = new com.bagnet.nettracer.ws.onlineclaims.xsd.File[files.size()];
			for (int i = 0; i < files.size(); i++) {
				file=files.get(i);
				wsFile = com.bagnet.nettracer.ws.onlineclaims.xsd.File.Factory.newInstance();
				wsFile.setFilename(file.getName());
				wsFile.setPath(file.getPath());
				wsFile.setInterim(file.isInterim());
				wsFileArray[i]=wsFile;
				wsFile=null; //GC
				file=null;
			}
		}
		claim.setFileArray(wsFileArray);
		wsFileArray=null; //GC

	}

	@Override
	public Incident getIncident(String pnr, String lastName, String firstName)
			throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
         // Prepare XML documents for request 
         AuthIncidentDocument request = AuthIncidentDocument.Factory.newInstance();
         AuthIncident subDoc1 = request.addNewAuthIncident();
         subDoc1.setPnr(pnr);
         subDoc1.setLastName(lastName);
         subDoc1.setFirstName(firstName);
         // Set System Username & PW
         NtAuth subDoc2 = subDoc1.addNewAuth();
         subDoc2.setUsername(SYSTEM_USERNAME);
         subDoc2.setPassword(SYSTEM_PASSWORD);
         // Perform Web Service Request
         AuthIncidentResponseDocument response = stub.authIncident(request);
         // Process response
		 return response.getAuthIncidentResponse().getReturn();
	}
	
	@Override
	public String saveIncident(IncidentBean bean, Incident incident)
			throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
         // Prepare XML documents for request 
         SaveNewIncidentDocument request = SaveNewIncidentDocument.Factory.newInstance();
         SaveNewIncident subDoc1 = request.addNewSaveNewIncident();
         subDoc1.setPnr(incident.getPnr());
         subDoc1.setLastName(incident.getLastName());
         subDoc1.setFirstName(incident.getFirstName());
         incident = extractInputFromIncidentBean(bean, incident);
         subDoc1.setIncident(incident);
         // Set System Username & PW
         NtAuth subDoc2 = subDoc1.addNewAuth();
         subDoc2.setUsername(SYSTEM_USERNAME);
         subDoc2.setPassword(SYSTEM_PASSWORD);
         // Perform Web Service Request
         SaveNewIncidentResponseDocument response = stub.saveNewIncident(request);
         // Process response
		 return response.getSaveNewIncidentResponse().getReturn();
		
	}
	
	private Incident extractInputFromIncidentBean(IncidentBean bean, Incident incident) {
		// Step 1: Claimchecks (Nothing can change)
		
		// Step 2: Bag Info
		for (IncidentBagBean bagBean : bean.getBag()) {
			IncidentBag bag = incident.addNewBag();
			bag.setType(Integer.parseInt(bagBean.getType()));
			bag.setColor(bagBean.getColor());
		}
		
		// Step 3: Personal Info
		IncidentAddress wsAddr = incident.getDeliveryAddress();
		if (wsAddr == null) {
			wsAddr = incident.addNewDeliveryAddress();
		}
		IncidentAddressBean addr = bean.getDeliveryAddress();
		wsAddr.setAddress1(addr.getAddress1());
		wsAddr.setAddress2(addr.getAddress2());
		wsAddr.setCity(addr.getCity());
		wsAddr.setState(addr.getState());
		wsAddr.setProvince(addr.getProvince());
		wsAddr.setPostalCode(addr.getPostalCode());
		wsAddr.setCountry(addr.getCountry());
		incident.setDeliveryAddress(wsAddr);
		incident.setEmail(bean.getEmail());
		incident.setPhoneArray(null);
		for (IncidentPhoneBean phBean : bean.getPhone()) {
			IncidentPhone phone = incident.addNewPhone();
			phone.setNumber(phBean.getNumber());
			phone.setType(phBean.getType());
			phone.setOther(phBean.getOther());
		}
		incident.setDeliverWithoutSignature(bean.isDeliverWithoutSignature());
		incident.setDeliveryType(bean.getDeliveryType());
		
		// Step 4: Verification (Nothing can change)		
		
		return incident;
	}
	
	@Override
	public IncidentBean getIncidentData(Incident incident) {
		IncidentBean toReturn = new IncidentBean();
		
		// Initial Information
		toReturn.setFirstName(incident.getFirstName());
		toReturn.setLastName(incident.getLastName());
		toReturn.setPnr(incident.getPnr());
		
		// Claims and bags
		List<String> claims = new ArrayList<String>();
		List<IncidentBagBean> bags = new ArrayList<IncidentBagBean>();
		for (String claim : incident.getClaimCheckArray()) {
			claims.add(claim);
			bags.add(new IncidentBagBean());
		}
		toReturn.setClaimCheck(claims);
		toReturn.setBag(bags);
		
		//Address Information
		IncidentAddressBean addrBean = new IncidentAddressBean();
		IncidentAddress wsAddr = incident.getDeliveryAddress();
		if (wsAddr != null) {
			addrBean.setAddress1(wsAddr.getAddress1());
			addrBean.setAddress2(wsAddr.getAddress2());
			addrBean.setCity(wsAddr.getCity());
			addrBean.setState(wsAddr.getState());
			addrBean.setProvince(wsAddr.getProvince());
			addrBean.setPostalCode(wsAddr.getPostalCode());
			addrBean.setCountry(wsAddr.getCountry());
		}
		toReturn.setDeliveryAddress(addrBean);
		toReturn.setEmail(incident.getEmail());
		List<IncidentPhoneBean> phBeans = new ArrayList<IncidentPhoneBean>();
		if (incident.getPhoneArray() == null) {
			phBeans.add(new IncidentPhoneBean());
		} else {
			for (IncidentPhone phone : incident.getPhoneArray()) {
				IncidentPhoneBean phBean = new IncidentPhoneBean();
				phBean.setNumber(phone.getNumber());
				phBean.setType(phone.getType());
				phBean.setOther(phone.getOther());
				phBeans.add(phBean);
			}
		}
		toReturn.setPhone(phBeans);
		
		return toReturn;
	}

	private int figureTimeDifference(Calendar wsTime) {
		int wsHourOfDay = wsTime.get(Calendar.HOUR_OF_DAY);  
		int wsDayOfMonth = wsTime.get(Calendar.DAY_OF_MONTH);  
		
		Calendar server = Calendar.getInstance();
		server.setTime(wsTime.getTime());
		
		int localHourOfDay = server.get(Calendar.HOUR_OF_DAY);  
		int localDayOfMonth = server.get(Calendar.DAY_OF_MONTH);  
	 
		// Difference between Web Service Provided and Server  
		int hourDifference = wsHourOfDay - localHourOfDay;  
		int dayDifference = wsDayOfMonth - localDayOfMonth;  
		if (dayDifference != 0) {  
			hourDifference = hourDifference + 24;  
		}  
		logger.fatal("HOUR DIFFERENCE = " + hourDifference);  
		return hourDifference;
	}
	
	private Boolean intToBool(int toConvert) {
		if (toConvert == 2) {
			return false;
		} else if (toConvert == 1) {
			return true;
		} else {
			return null;
		}
	}
	
	private int boolToInt (Boolean toConvert) {
		if (toConvert == null) {
			return 0;
		} else {
			if (toConvert) {
				return 1;
			} else {
				return 2;
			}
		}
	}
	
	/** Checks whether desired bit is 1 or 0. Index starts at 0 for first bit position.*/
	public static boolean isBitSet(int value, int index) {
		int mask = 1<<index;
		return (mask&value) > 0;
	}

	/** Encodes desired bit to 1 or 0. Index starts at 0 for first bit position. Boolean determines 1 or 0.*/
	public static int encodeBit(int value, int index, boolean bit) {
		int submask = 1<<index;
		if (bit) {
			return value|submask;
		}
		int valIndex = Integer.numberOfTrailingZeros(Integer.highestOneBit(value));
		double power = Math.max(index, valIndex) + 1;
		int alter = (int) (Math.pow(2D, power) - 1D);
		int mask = submask^alter;
		return value&mask;
	}

}