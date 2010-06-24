package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Service;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.OnlineClaimsServiceStub;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.AuthPassenger;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Phone;
import com.nettracer.claims.core.model.Address;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;


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
	public PassengerBean getPassengerData(WSPVAdvancedIncident passengerData) {
		WSPVPassenger[] passengerDatarray=passengerData.getPassengersArray();
		PassengerBean passengerBean = new PassengerBean();
		passengerBean.setIncidentID(passengerData.getIncidentID());
		List<Address> addresses=new ArrayList<Address>();
		List<Passenger> passengers=new ArrayList<Passenger>();
		for (int i = 0; i < passengerDatarray.length; i++) {
			Address address=new Address();
			address.setEmailAddress(passengerDatarray[i].getEmail());
			address.setPhone1(passengerDatarray[i].getHomephone());
			address.setPhone2(passengerDatarray[i].getMobile());
			address.setPhone4(passengerDatarray[i].getWorkphone());
			address.setHotel(passengerDatarray[i].getHotel());
			
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
		WSPVItem[] itemArray=passengerData.getItemsArray();
		for (int i = 0; i < itemArray.length; i++) {
			
		}
		if(passengerBean.getItineraryList().size() == 0){
			for(int i=0;i<5;i++){
				passengerBean.getItineraryList().add(new Itinerary());
			}
		}
		passengerBean.setAddress(addresses);
		passengerBean.setPassengers(passengers);
		return passengerBean;
	}

	@Override
	public boolean savePassengerInfo(PassengerBean passengerBean) throws AxisFault, RemoteException {
		 OnlineClaimsServiceStub stub = new OnlineClaimsServiceStub(ENDPOINT);
         // Prepare XML documents for request
		 SaveClaimDocument request = SaveClaimDocument.Factory.newInstance();
		 SaveClaim subDoc1 = request.addNewSaveClaim();
		 Claim claim= Claim.Factory.newInstance();
		 claim.setLastName(passengerBean.getPassengers().get(0).getLastName());
		 claim.setFirstName(passengerBean.getPassengers().get(0).getFirstName());
		 claim.setMiddleInitial(passengerBean.getPassengers().get(0).getMiddleInitial());
		 claim.setEmailAddress(passengerBean.getAddress().get(0).getEmailAddress());
		 
		 Phone phone1=Phone.Factory.newInstance();
		 phone1.setPhoneNumber(passengerBean.getAddress().get(0).getPhone1());
		 phone1.setPhoneType("Home");
		 
		 Phone phone2=Phone.Factory.newInstance();
		 phone1.setPhoneNumber(passengerBean.getAddress().get(0).getPhone2());
		 phone1.setPhoneType("Mobile");
		 
		 Phone phone3=Phone.Factory.newInstance();
		 phone1.setPhoneNumber(passengerBean.getAddress().get(0).getPhone3());
		 phone1.setPhoneType("Fax");
		 
		 Phone phone4=Phone.Factory.newInstance();
		 phone1.setPhoneNumber(passengerBean.getAddress().get(0).getPhone4());
		 phone1.setPhoneType("Business");
		 
		 Phone[] phoneArray={phone1,phone2,phone3,phone4};
		 
		 claim.setPhoneArray(phoneArray);
		 claim.setFrequentFlierNumber(passengerBean.getFrequentFlyerNumber());
		 claim.setSocialSecurity(passengerBean.getSocialSecurityNumber());
		 
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
	
	

}
