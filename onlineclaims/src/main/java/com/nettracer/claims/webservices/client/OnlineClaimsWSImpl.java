package com.nettracer.claims.webservices.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Service;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.OnlineClaimsServiceStub;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.AuthPassenger;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.nettracer.claims.core.model.Address;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;


@Service
public class OnlineClaimsWSImpl implements OnlineClaimsWS {

	@Override
	public boolean authAdminUser(String userName, String password)
			throws AxisFault, RemoteException {
		// TODO Auto-generated method stub
		return false;
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
		List<Address> addresses=new ArrayList<Address>();
		List<Passenger> passengers=new ArrayList<Passenger>();
		for (int i = 0; i < passengerDatarray.length; i++) {
			Address address=new Address();
			address.setEmailAddress(passengerDatarray[i].getEmail());
			address.setPhone1(passengerDatarray[i].getHomephone());
			address.setPhone2(passengerDatarray[i].getMobile());
			address.setPhone4(passengerDatarray[i].getWorkphone());
			address.setHotel(passengerDatarray[i].getHotel());
			addresses.add(address);
		}
		WSPVItem[] itemArray=passengerData.getItemsArray();
		for (int i = 0; i < itemArray.length; i++) {
			Passenger passenger=new Passenger();
			passenger.setFirstName(passengerDatarray[i].getFirstname());
			passenger.setLastName(passengerDatarray[i].getLastname());
			passenger.setMiddleInitial(passengerDatarray[i].getMiddlename());
			passengers.add(passenger);
		}
		passengerBean.setAddress(addresses);
		passengerBean.setPassengers(passengers);
		return passengerBean;
	}
	
	

}
