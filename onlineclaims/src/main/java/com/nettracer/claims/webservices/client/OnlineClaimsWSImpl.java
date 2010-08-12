package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Service;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument;
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
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Contents;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Phone;
import com.nettracer.claims.core.model.Address;
import com.nettracer.claims.core.model.Bag;
import com.nettracer.claims.core.model.Content;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.faces.util.File;


@Service
public class OnlineClaimsWSImpl implements OnlineClaimsWS {

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
	public PassengerView getPassengerView(String claimNumber, String lastName)
			throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
         // Prepare XML documents for request 
         AuthPassengerDocument request = AuthPassengerDocument.Factory.newInstance();
         AuthPassenger subDoc1 = request.addNewAuthPassenger();
         subDoc1.setIncidentId(claimNumber);
         subDoc1.setPassengerLastName(lastName);
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
	public Claim getClaim(WSPVAdvancedIncident passengerData,String lastName)
			throws AxisFault, RemoteException {
		  OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
	         // Prepare XML documents for request 
			LoadClaimDocument request=LoadClaimDocument.Factory.newInstance();
			LoadClaim  subDoc1 = request.addNewLoadClaim();
			subDoc1.setIncidentId(passengerData.getIncidentID());
			subDoc1.setName(lastName);
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
			passengerBean.setPassengerInfolastName(passenger.getLastName());

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
			for(int i=0;i<5;i++){
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
        passengerBean.setMiddleInitial(claim.getMiddleInitial());
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
        passengerBean.setRecheckBag(claim.getHaveToRecheck());
        passengerBean.setInspectbag(claim.getWasBagInspected());
        passengerBean.setChargeExcessBaggage(claim.getChargedForExcessBaggage());
        passengerBean.setNoOfCheckedBag(0);
        passengerBean.setNoOfmissingBag(claim.getBagsStillMissing());
        passengerBean.setBagClaimCheck(""); //not DifferentClaimCheck
        passengerBean.setDeclarePayExcessValue(claim.getDeclaredExcessValue());
        passengerBean.setDeclaredValue(null != claim.getDeclaredValue() 
        		&& ! claim.getDeclaredValue().equals("?")
        		? Double.parseDouble(claim.getDeclaredValue()) : 0D);
        passengerBean.setClearCustomBag(claim.getBagClearCustoms());
        passengerBean.setBagWeight(0D);
        passengerBean.setRerouteBag(claim.getBaggageReroutedEnRoute());
        passengerBean.setDifferentClaimCheck(claim.getDifferentClaimCheck());
        passengerBean.setReroutedCityAirline(claim.getReroutedAirlineCity());
        passengerBean.setReason(claim.getReroutedReason());
        passengerBean.setImmediateClaimAttempt(claim.getAttemptToClaimAtArrival());
        passengerBean.setWhenBagSeen(claim.getLastSawBaggage());
        passengerBean.setFileReportCity(claim.getWhereDidYouFileReport());
        passengerBean.setReportAnotherAirline(claim.getReportedToAnotherAirline());
        passengerBean.setDifferentAirlineTicket(claim.getTicketWithAnotherAirline());
        passengerBean.setTicketNumber("");
        
               
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
        	passengerBean.setDateOfClaim(claim.getFiledPrevoiusDate().getTime());
        }
        passengerBean.setClaimantName(claim.getFiledPreviousClaimant());
        passengerBean.setTsaInspect(claim.getTsaInspected());
        passengerBean.setBagConfirmNote(claim.getTsaNotePresent());
        passengerBean.setInspectionPlace(claim.getTsaInspectionLocation());
        passengerBean.setAdditionalComments(claim.getComments());
        passengerBean.setTypeAccept(claim.getAccept());
        
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
		 //claim.setClaimId(passengerBean.getClaimId()); //mandatory field for save through web service
		 claim.setLastName(passengerBean.getPassengers().get(0).getLastName());
		 claim.setFirstName(passengerBean.getPassengers().get(0).getFirstName());
		 claim.setMiddleInitial(passengerBean.getMiddleInitial());
		 claim.setEmailAddress(passengerBean.getEmailAddress());
		 claim.setOccupation(passengerBean.getOccupation());
		 claim.setBusinessName(passengerBean.getBusinessName());
		 
		 if(null != claim.getPermanentAddress()){
			 wsPermanentAddress=claim.getPermanentAddress();
		 }else{
			 wsPermanentAddress= com.bagnet.nettracer.ws.onlineclaims.xsd.Address.Factory.newInstance();
		 }
		
		 wsPermanentAddress.setAddress1(passengerBean.getAddress().get(0).getAddressLine1());
		 wsPermanentAddress.setAddress2(passengerBean.getAddress().get(0).getAddressLine2());
		 wsPermanentAddress.setCity(passengerBean.getAddress().get(0).getCity());
		 wsPermanentAddress.setCountry(passengerBean.getAddress().get(0).getCountry());
		 wsPermanentAddress.setPostalCode(passengerBean.getAddress().get(0).getPostalCode());
		 wsPermanentAddress.setStateProvince(passengerBean.getAddress().get(0).getStateRegion());
		 claim.setPermanentAddress(wsPermanentAddress);
		 
		 if(null != claim.getMailingAddress()){
			 wsMailingAddress=claim.getMailingAddress();
		 }else{
			 wsMailingAddress= com.bagnet.nettracer.ws.onlineclaims.xsd.Address.Factory.newInstance();
		 }
		 
		 wsMailingAddress.setAddress1(passengerBean.getAddress().get(1).getAddressLine1());
		 wsMailingAddress.setAddress2(passengerBean.getAddress().get(1).getAddressLine2());
		 wsMailingAddress.setCity(passengerBean.getAddress().get(1).getCity());
		 wsMailingAddress.setCountry(passengerBean.getAddress().get(1).getCountry());
		 wsMailingAddress.setPostalCode(passengerBean.getAddress().get(1).getPostalCode());
		 wsMailingAddress.setStateProvince(passengerBean.getAddress().get(1).getStateRegion());
		 claim.setMailingAddress(wsMailingAddress);
		 
		 if(null != claim.getPhoneArray() && claim.getPhoneArray().length > 0){
			 phoneArray= claim.getPhoneArray();
			for (int i = 0; i < phoneArray.length; i++) {
				 Phone phone=phoneArray[i];
				 if(phone.getPhoneType().equals("Home")){
					 phone.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneHome());
				 }else if(phone.getPhoneType().equals("Mobile")){
					 phone.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneMobile());
				 }else if(phone.getPhoneType().equals("Fax")){
					 phone.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneFax());
				 }else if(phone.getPhoneType().equals("Business")){
					 phone.setPhoneNumber(passengerBean.getAddress().get(0).getPhoneBusiness());
				 }
				 //phoneArray[i]=phone;
			}
			 
		 }else{
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
		 }
		 
		 claim.setPhoneArray(phoneArray);
		 claim.setFrequentFlierNumber(passengerBean.getFrequentFlyerNumber());
		 claim.setSocialSecurity(passengerBean.getSocialSecurityNumber());
		 
         subDoc1.setIncidentId(passengerBean.getIncidentID()); //claim number is the incident Id
         subDoc1.setName(passengerBean.getPassengers().get(0).getLastName());
         
         //setWSFiles(passengerBean.getFiles(),claim);
         
        /* 
         //Bag
         setWSBags(passengerBean.getBagList(),claim);
         
         //File
         setWSFiles(passengerBean.getFiles(),claim);
         
         //Fraud Question
         claim.setFiledPreviousClaim(passengerBean.getAnotherClaim());
         claim.setFiledPreviousAirline(passengerBean.getWhichAirline());
         if(null != passengerBean.getDateOfClaim()){
        	 calendar=Calendar.getInstance();
        	 calendar.setTime(passengerBean.getDateOfClaim());
        	 claim.setFiledPrevoiusDate(calendar);
        	 calendar=null; //GC
         }
         claim.setFiledPreviousClaimant(passengerBean.getClaimantName());
         claim.setTsaInspected(passengerBean.getTsaInspect());
         claim.setTsaNotePresent(passengerBean.getBagConfirmNote());
         claim.setTsaInspectionLocation(passengerBean.getInspectionPlace());
         claim.setComments(passengerBean.getAdditionalComments());
         //submit claim
         claim.setAccept(passengerBean.getTypeAccept());*/
         
         
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
         claim.setPassengersTravelingWithYou(passengerBean.getNoOfPassenger());
         claim.setHaveToRecheck(passengerBean.getRecheckBag());
         claim.setWasBagInspected(passengerBean.getInspectbag());
         claim.setChargedForExcessBaggage(passengerBean.getChargeExcessBaggage());
         //claim.setNoOfCheckedBag(0); //To be implemented later
         claim.setBagsStillMissing(passengerBean.getNoOfmissingBag());
         //claim.setBagpassengerBeanCheck(""); //not DifferentpassengerBeanCheck
         claim.setDeclaredExcessValue(passengerBean.getDeclarePayExcessValue());
         claim.setDeclaredValue(passengerBean.getDeclaredValue()+"");
         claim.setBagClearCustoms(passengerBean.getClearCustomBag());
         //claim.setBagWeight(0D);
         claim.setBaggageReroutedEnRoute(passengerBean.getRerouteBag());
         claim.setDifferentClaimCheck(passengerBean.getDifferentClaimCheck());
         claim.setReroutedAirlineCity(passengerBean.getReroutedCityAirline());
         claim.setReroutedReason(passengerBean.getReason());
         claim.setAttemptToClaimAtArrival(passengerBean.getImmediateClaimAttempt());
         claim.setLastSawBaggage(passengerBean.getWhenBagSeen());
         claim.setWhereDidYouFileReport(passengerBean.getFileReportCity());
         claim.setReportedToAnotherAirline(passengerBean.getReportAnotherAirline());
         claim.setTicketWithAnotherAirline(passengerBean.getDifferentAirlineTicket());
         //claim.setTicketNumber(""); //To be implemented later
         
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
		 
		 setWSBags(passengerBean.getBagList(),claim);
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
       	 claim.setFiledPrevoiusDate(calendar);
       	 calendar=null; //GC
        }
        claim.setFiledPreviousClaimant(passengerBean.getClaimantName());
        claim.setTsaInspected(passengerBean.getTsaInspect());
        claim.setTsaNotePresent(passengerBean.getBagConfirmNote());
        claim.setTsaInspectionLocation(passengerBean.getInspectionPlace());
        claim.setComments(passengerBean.getAdditionalComments());
        
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

		 claim.setAccept(passengerBean.getTypeAccept());
        
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
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArray= null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag wsBag=null;
		Bag bag=null;
		if(null != bagTagList && bagTagList.size() >0){
			if(null != claim.getBagArray() && claim.getBagArray().length >0){ // for esisting bags
				wsBagArray = claim.getBagArray();
				for (int i = 0; i < wsBagArray.length; i++) {
					for (int j=0; j<bagTagList.size();j++) {
						if(i==j){
							wsBag=wsBagArray[i];
							bag=bagTagList.get(j);
							wsBag.setTag(bag.getBagTagNumber());
							wsBag.setBagArrive(new Boolean (bag.getBagArrivalStatus()));
							break;
						}
					}
				}
			}else{ // for new bag
				wsBagArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[bagTagList.size()];
				for (int i=0; i<bagTagList.size();i++) {
					wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
					bag=bagTagList.get(i);
					wsBag.setTag(bag.getBagTagNumber());
					wsBag.setBagArrive(new Boolean (bag.getBagArrivalStatus()));
					wsBagArray[i]=wsBag;
				}
			}
			claim.setBagArray(wsBagArray);
			
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
					itinerary.setJourneyDate(wsItinerary[i].getDate().getTime());
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
		Bag bag=null;
		List<Content> contentList=null;
		Content content=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBag=claim.getBagArray();
		if(null != wsBag && wsBag.length >0){
			bagList=new ArrayList<Bag>();
			for (int i = 0; i < wsBag.length; i++) {
				bag= new Bag();
				bag.setBagTagNumber(wsBag[i].getTag()); //clarification pending for bagtag number
				bag.setNameonBag(wsBag[i].getNameOnBag());
				bag.setBrandOftheBag(wsBag[i].getBrand());
				bag.setExternalMarkings(wsBag[i].getExternalMarkings());
				if(null != wsBag[i].getPurchaseDate()){
					bag.setBagPurchaseDate(wsBag[i].getPurchaseDate().getTime());
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
				
				//ticket section
				bag.setBagArrivalStatus(wsBag[i].getBagArrive()+"");

				Contents[] wsContents =wsBag[i].getContentsArray();
				if(null !=wsContents && wsContents.length >0){
					contentList= new ArrayList<Content>();
					for (int j = 0; j < wsContents.length; j++) {
						content=new Content();
						content.setMale(wsContents[j].getMale());
						content.setArticle(wsContents[j].getArticle());
						content.setColor(wsContents[j].getColor());
						content.setSize(wsContents[j].getSize());
						content.setBrandOrDescription(wsContents[j].getBrand());
						content.setStorePurchased(wsContents[j].getPurchasedAt());
						content.setPurchasedDate(wsContents[j].getPurchasedDate());
						content.setPrice(wsContents[j].getPrice());
						content.setCurrency(wsContents[j].getCurrency());
						contentList.add(content);
						content=null; //GC
					}
					bag.setContentList(contentList);
					contentList=null; //GC

				}

				bagList.add(bag);
				bag=null; //GC
			}
			passengerBean.setBagList(bagList);
			passengerBean.setBagTagList(bagList); //For ticket-info
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
				file.setId(wsFile[i].getId());
				file.setName(wsFile[i].getFilename());
				file.setPath(wsFile[i].getPath());
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
			if(null != claim.getItineraryArray() && claim.getItineraryArray().length >0){ //For existing webservice Itinerary data
				wsItineraries = claim.getItineraryArray();
				for (int i = 0; i < wsItineraries.length; i++) {
					wsItinerary = wsItineraries[i];
					for (int j=0; j<itineraries.size();j++) {
						if(i==j){
							Itinerary itinerary=itineraries.get(j);
							if(null != itinerary){
								wsItinerary.setDepartureCity(itinerary.getDepartureCity());
								wsItinerary.setArrivalCity(itinerary.getArrivalCity());
								wsItinerary.setAirline(itinerary.getAirline());
								wsItinerary.setFlightNum(itinerary.getFlightNum());
								if(null != itinerary.getJourneyDate()){
									calendar=Calendar.getInstance();
									calendar.setTime(itinerary.getJourneyDate() );
									wsItinerary.setDate(calendar);
									calendar=null;//GC
								}
								wsItineraries[i]=wsItinerary;
								wsItinerary=null;//GC
								break;
							}
							
						}
					}
				}
			}else{ //For new Itinerary data that has to be persisted
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
							wsItinerary.setDate(calendar);
							calendar=null;//GC
						}
						wsItineraries[i]=wsItinerary;
						wsItinerary=null;//GC
					}
				}
			}
			
			
			claim.setItineraryArray(wsItineraries);
		}
		

	}
	
	private void setWSBags(List<Bag> bagList, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] wsBagArray= null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Bag wsBag=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[] wsContentArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Contents wsContent=null;
		Bag bag=null;
		Content content=null;
		Calendar calendar=null;
		if(null != bagList && bagList.size() >0){
			if(null != claim.getBagArray() && claim.getBagArray().length >0){
				wsBagArray = claim.getBagArray();
				for (int i = 0; i < wsBagArray.length; i++) {
					for (int j=0; j<bagList.size();j++) {
						wsBag = wsBagArray[i];
						bag=bagList.get(j);
						if(wsBag.getTag().equalsIgnoreCase(bag.getBagTagNumber())){
							if(null != bag){
								wsBag.setTag(bag.getBagTagNumber());
								wsBag.setNameOnBag(bag.getNameonBag());
								wsBag.setBrand(bag.getBrandOftheBag());
								wsBag.setExternalMarkings(bag.getExternalMarkings());
								if(null !=bag.getBagPurchaseDate()){
									calendar=Calendar.getInstance();
									calendar.setTime(bag.getBagPurchaseDate());
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
								//ticket section
								wsBag.setBagArrive(new Boolean(bag.getBagArrivalStatus()));

								List<Content> contents =bag.getContentList();
								if(null !=contents && contents.size() >0){
									wsContentArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[contents.size()];
									for (int k = 0; k < contents.size(); k++) {
										wsContent = com.bagnet.nettracer.ws.onlineclaims.xsd.Contents.Factory.newInstance();
										content=contents.get(k);
										if(null != content){
											wsContent.setMale(content.getMale());
											wsContent.setArticle(content.getArticle());
											wsContent.setColor(content.getColor());
											wsContent.setSize(content.getSize());
											wsContent.setBrand(content.getBrandOrDescription());
											wsContent.setPurchasedAt(content.getStorePurchased());
											wsContent.setPurchasedDate(content.getPurchasedDate());
											wsContent.setPrice(content.getPrice());
											wsContent.setCurrency(content.getCurrency());
											wsContentArray[j]=wsContent;
											wsContent=null; //GC
											content=null;
										}
									}
									wsBag.setContentsArray(wsContentArray);
									wsContentArray=null; //GC
								}
								wsBagArray[i]=wsBag;
								wsBag=null;//GC
								bag=null;
							}
							break;
						}
					}
				}
			}else{
				wsBagArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[bagList.size()];
				for (int i=0; i<bagList.size();i++) {
					wsBag = com.bagnet.nettracer.ws.onlineclaims.xsd.Bag.Factory.newInstance();
					bag=bagList.get(i);
					if(null != bag){
						wsBag.setTag(bag.getBagTagNumber());
						wsBag.setNameOnBag(bag.getNameonBag());
						wsBag.setBrand(bag.getBrandOftheBag());
						wsBag.setExternalMarkings(bag.getExternalMarkings());
						if(null !=bag.getBagPurchaseDate()){
							calendar=Calendar.getInstance();
							calendar.setTime(bag.getBagPurchaseDate());
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
						//ticket section
						wsBag.setBagArrive(new Boolean(bag.getBagArrivalStatus()));

						List<Content> contents =bag.getContentList();
						if(null !=contents && contents.size() >0){
							wsContentArray=new com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[contents.size()];
							for (int j = 0; j < contents.size(); j++) {
								wsContent = com.bagnet.nettracer.ws.onlineclaims.xsd.Contents.Factory.newInstance();
								content=contents.get(j);
								if(null != content){
									wsContent.setMale(content.getMale());
									wsContent.setArticle(content.getArticle());
									wsContent.setColor(content.getColor());
									wsContent.setSize(content.getSize());
									wsContent.setBrand(content.getBrandOrDescription());
									wsContent.setPurchasedAt(content.getStorePurchased());
									wsContent.setPurchasedDate(content.getPurchasedDate());
									wsContent.setPrice(content.getPrice());
									wsContent.setCurrency(content.getCurrency());
									wsContentArray[j]=wsContent;
									wsContent=null; //GC
									content=null;
								}
							}
							wsBag.setContentsArray(wsContentArray);
							wsContentArray=null; //GC
						}
						wsBagArray[i]=wsBag;
						wsBag=null;//GC
						bag=null;
					}
				}
			}
			claim.setBagArray(wsBagArray);
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
			if(null != claim.getFileArray() && claim.getFileArray().length > 0){
				wsFileArray = claim.getFileArray();
				for (int i = 0; i < wsFileArray.length; i++) {
					for (int j = 0; j < files.size(); j++) {
						if(i==j){
							file=files.get(j);
							wsFile = wsFileArray[i];
							wsFile.setId(file.getId());
							wsFile.setFilename(file.getName());
							wsFile.setPath(file.getPath());
							wsFileArray[i]=wsFile;
						}
					}
				}
			}else{
				wsFileArray = new com.bagnet.nettracer.ws.onlineclaims.xsd.File[files.size()];
			}
			for (int i = 0; i < files.size(); i++) {
				file=files.get(i);
				wsFile = com.bagnet.nettracer.ws.onlineclaims.xsd.File.Factory.newInstance();
				wsFile.setId(file.getId());
				wsFile.setFilename(file.getName());
				wsFile.setPath(file.getPath());
				wsFileArray[i]=wsFile;
				wsFile=null; //GC
				file=null;
			}
			claim.setFileArray(wsFileArray);
			wsFileArray=null; //GC
		}

	}


}