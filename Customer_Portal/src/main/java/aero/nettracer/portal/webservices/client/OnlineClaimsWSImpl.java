package aero.nettracer.portal.webservices.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import aero.nettracer.portal.faces.util.File;
import aero.nettracer.portal.model.Address;
import aero.nettracer.portal.model.Bag;
import aero.nettracer.portal.model.Content;
import aero.nettracer.portal.model.Itinerary;
import aero.nettracer.portal.model.Message;
import aero.nettracer.portal.model.Passenger;
import aero.nettracer.portal.model.PassengerBean;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.AuthPassenger;
import com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.LoadClaim;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.OnlineClaimsServiceStub;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Contents;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Phone;


@Service
public class OnlineClaimsWSImpl implements OnlineClaimsWS {

	private static Logger logger = Logger.getLogger(OnlineClaimsWSImpl.class);

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
		if(passengerBean.getItineraryList().size() == 0){
			for(int i=0;i<1;i++){
				passengerBean.getItineraryList().add(new Itinerary());
			}
		}
		passengerBean.setAddress(addresses);
		passengerBean.setPassengers(passengers);
		
		return getExistingClaimData(claim,passengerBean);
	}	
	
	private PassengerBean getExistingClaimData(Claim claim,PassengerBean passengerBean) 
				throws AxisFault, RemoteException{
        
        passengerBean.setClaimId(claim.getClaimId()); //mandatory field for the web service  
        passengerBean.setStatus(claim.getClaimStatus());              
               
        //File
        setFiles(claim,passengerBean);
        
        //Message
        setMessages(claim,passengerBean);
        
       
       return passengerBean;
									
	}	
	
	public boolean sendToNT(PassengerBean passengerBean, Claim claim) throws AxisFault, RemoteException{
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

		setWSPassengers(passengerBean.getPassengers(), claim);

		setWSFiles(passengerBean.getNewFiles(), passengerBean.getFiles(), claim);

		setWSMessages(passengerBean.getCurrentMessage(), passengerBean.getMessages(), claim);
		
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
				file.setId(wsFile[i].getId());
				file.setPublish(wsFile[i].getPublish());
				file.setStatus(wsFile[i].getStatusId());
				file.setDateUploaded(wsFile[i].getDateUploaded());
				file.setDateViewed(wsFile[i].getDateViewed());
				files.add(file);
				file=null; //GC
			}
			passengerBean.setFiles(files);
			files=null; //GC
		}
		
	}
	
	private void setMessages(Claim claim, PassengerBean passengerBean) {
		List<Message> messages=null;
		Message message=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Message[]  wsMessage=claim.getMessagesArray();
		if(null != wsMessage && wsMessage.length >0){
			messages = new ArrayList<Message> ();
			for (int i = 0; i < wsMessage.length; i++) {
				message = new Message();
				message.setId(wsMessage[i].getId());
				message.setMessage(wsMessage[i].getMessage());
				message.setUsername(wsMessage[i].getUsername());
				message.setDateCreated(wsMessage[i].getDateCreated());
				message.setDateReviewed(wsMessage[i].getDateReviewed());
				message.setPublish(wsMessage[i].getPublish());
				message.setStatusId(wsMessage[i].getStatusId());
				messages.add(message);
				message=null; //GC
			}
			passengerBean.setMessages(messages);
			messages=null; //GC
		}
		
	}
	
	private void setWSPassengers(List<Passenger> passengers, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] wsPassengerArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger wsPassenger=null;
		Passenger passenger=null;

		if(null != passengers && passengers.size() >0){
			wsPassengerArray = new com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[passengers.size()];
			for (int i = 0; i < passengers.size(); i++) {
				passenger=passengers.get(i);
				wsPassenger = com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger.Factory.newInstance();
				wsPassenger.setFirstName(passenger.getFirstName());
				wsPassenger.setLastName(passenger.getLastName());
				wsPassenger.setMiddleInitial(passenger.getMiddleInitial());
				wsPassengerArray[i]=wsPassenger;
				wsPassenger=null; //GC
				passenger=null;
			}
		}
		claim.setPassengerArray(wsPassengerArray);
		wsPassengerArray=null; //GC

	}
	
	private void setWSFiles(List<File> newFiles, List<File> files, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.File[] wsFileArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.File wsFile=null;
		File file=null;
		
		if (newFiles != null && newFiles.size() > 0) {
			for (File newFile : newFiles) {
				if (files == null) {
					files = new ArrayList<File>();
				}
				files.add(newFile);
			}
		}

		if(null != files && files.size() >0){
			wsFileArray = new com.bagnet.nettracer.ws.onlineclaims.xsd.File[files.size()];
			for (int i = 0; i < files.size(); i++) {
				file=files.get(i);
				wsFile = com.bagnet.nettracer.ws.onlineclaims.xsd.File.Factory.newInstance();
				wsFile.setFilename(file.getName());
				wsFile.setPath(file.getPath());
				wsFile.setInterim(file.isInterim());
				wsFile.setId(file.getId());
				wsFile.setPublish(file.isPublish());
				wsFile.setStatusId(file.getStatus());
				wsFile.setDateUploaded(file.getDateUploaded());
				wsFile.setDateViewed(file.getDateViewed());
				wsFileArray[i]=wsFile;
				wsFile=null; //GC
				file=null;
			}
		}
		claim.setFileArray(wsFileArray);
		wsFileArray=null; //GC

	}
	
	private void setWSMessages(String currMessage, List<Message> messages, Claim claim) {
		com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] wsMessageArray=null;
		com.bagnet.nettracer.ws.onlineclaims.xsd.Message wsMessage=null;
		Message message=null;
		
		if (currMessage != null && currMessage.trim().length() > 0) {
			message = new Message();
			message.setMessage(currMessage.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<BR>"));
			message.setDateCreated(Calendar.getInstance());
			message.setPublish(true);
			message.setStatusId(0);
			message.setUsername(Message.portalUser);
			if (messages == null) {
				messages = new ArrayList<Message>();
			}
			messages.add(message);
		}

		if(null != messages && messages.size() >0){
			wsMessageArray = new com.bagnet.nettracer.ws.onlineclaims.xsd.Message[messages.size()];
			for (int i = 0; i < messages.size(); i++) {
				message=messages.get(i);
				wsMessage = com.bagnet.nettracer.ws.onlineclaims.xsd.Message.Factory.newInstance();
				wsMessage.setId(message.getId());
				wsMessage.setMessage(message.getMessage());
				wsMessage.setUsername(message.getUsername());
				wsMessage.setDateCreated(message.getDateCreated());
				wsMessage.setDateReviewed(message.getDateReviewed());
				wsMessage.setPublish(message.isPublish());
				wsMessage.setStatusId(message.getStatusId());
				wsMessageArray[i]=wsMessage;
				wsMessage=null; //GC
				message=null;
			}
		}
		
		claim.setMessagesArray(wsMessageArray);
		wsMessageArray=null; //GC

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