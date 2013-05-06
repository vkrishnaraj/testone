package aero.nettracer.lf.services;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.types.NonNegativeInteger;

import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.RateBean;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.fedex.ws.addressvalidation.v2.Address;
import com.fedex.ws.addressvalidation.v2.AddressToValidate;
import com.fedex.ws.addressvalidation.v2.AddressValidationAccuracyType;
import com.fedex.ws.addressvalidation.v2.AddressValidationChangeType;
import com.fedex.ws.addressvalidation.v2.AddressValidationOptions;
import com.fedex.ws.addressvalidation.v2.AddressValidationReply;
import com.fedex.ws.addressvalidation.v2.AddressValidationReplyDocument;
import com.fedex.ws.addressvalidation.v2.AddressValidationRequest;
import com.fedex.ws.addressvalidation.v2.AddressValidationRequestDocument;
import com.fedex.ws.addressvalidation.v2.AddressValidationResult;
import com.fedex.ws.addressvalidation.v2.AddressValidationServiceStub;
import com.fedex.ws.addressvalidation.v2.ClientDetail;
import com.fedex.ws.addressvalidation.v2.Notification;
import com.fedex.ws.addressvalidation.v2.NotificationSeverityType;
import com.fedex.ws.addressvalidation.v2.ProposedAddressDetail;
import com.fedex.ws.addressvalidation.v2.TransactionDetail;
import com.fedex.ws.addressvalidation.v2.VersionId;
import com.fedex.ws.addressvalidation.v2.WebAuthenticationCredential;
import com.fedex.ws.addressvalidation.v2.WebAuthenticationDetail;
import com.fedex.ws.rate.v13.Dimensions;
import com.fedex.ws.rate.v13.DropoffType;
import com.fedex.ws.rate.v13.LinearUnits;
import com.fedex.ws.rate.v13.Money;
import com.fedex.ws.rate.v13.NotificationSeverityType.Enum;
import com.fedex.ws.rate.v13.PackageRateDetail;
import com.fedex.ws.rate.v13.PackageSpecialServicesRequested;
import com.fedex.ws.rate.v13.PackagingType;
import com.fedex.ws.rate.v13.Party;
import com.fedex.ws.rate.v13.Payment;
import com.fedex.ws.rate.v13.PaymentType;
import com.fedex.ws.rate.v13.RateReply;
import com.fedex.ws.rate.v13.RateReplyDetail;
import com.fedex.ws.rate.v13.RateRequest;
import com.fedex.ws.rate.v13.RateRequestDocument;
import com.fedex.ws.rate.v13.RateRequestType;
import com.fedex.ws.rate.v13.RateServiceStub;
import com.fedex.ws.rate.v13.RatedPackageDetail;
import com.fedex.ws.rate.v13.RatedShipmentDetail;
import com.fedex.ws.rate.v13.RequestedPackageLineItem;
import com.fedex.ws.rate.v13.RequestedShipment;
import com.fedex.ws.rate.v13.ServiceType;
import com.fedex.ws.rate.v13.ShipmentRateDetail;
import com.fedex.ws.rate.v13.Surcharge;
import com.fedex.ws.rate.v13.Weight;
import com.fedex.ws.rate.v13.WeightUnits;

@Stateless
public class FedexUtils {
//Begin Fedex Logic
	private static final ConcurrentHashMap<String, String> provCache;
	static{
		provCache=new ConcurrentHashMap<String,String>();
		provCache.put("ALBERTA","AB");
		provCache.put("BRITISH COLUMBIA","BC");
		provCache.put("MANITOBA","MB");
		provCache.put("NEW BRUNSWICK","NB");
		provCache.put("NEWFOUNDLAND","NL");
		provCache.put("LABRADOR","NL");
		provCache.put("NOVA SCOTIA","NS");
		provCache.put("NUNAVUT","NU");
		provCache.put("ONTARIO","ON");
		provCache.put("QUEBEC","QC"); //Special characters?
		provCache.put("SASKATCHEWAN","SK");
		provCache.put("YUKON","YT");
		provCache.put("YUKON TERRITORY","YT");
		provCache.put("PRINCE EDWARD ISLAND","PE");
	}
	
	static AddressBean validateAddressFedex(AddressBean ship){
		AddressValidationServiceStub stub=null;
		try {
			stub = new AddressValidationServiceStub(PropertyBMO.getValue(PropertyBMO.FEDEX_ADDRESS_ENDPOINT));
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		AddressValidationRequestDocument validate=AddressValidationRequestDocument.Factory.newInstance();
		AddressValidationRequest request=validate.addNewAddressValidationRequest();
		request.setClientDetail(createClientDetail(request));
		request.setWebAuthenticationDetail(createWebAuthenticationDetail(request));
		VersionId versionId = request.addNewVersion(); 
		versionId.setServiceId("aval");
		versionId.setMinor(0);
		versionId.setIntermediate(0);
		versionId.setMajor(2);
	    request.setVersion(versionId);
	    TransactionDetail transactionDetail = request.addNewTransactionDetail();
	    transactionDetail.setCustomerTransactionId("java sample - AddressValidationRequest"); //This is a reference field for the customer.  Any value can be used and will be provided in the return.
	    Calendar c = Calendar.getInstance();
	    request.setRequestTimestamp(c);

	    AddressValidationOptions vo = request.addNewOptions();
	    vo.setMaximumNumberOfMatches(5);
	    vo.setStreetAccuracy(AddressValidationAccuracyType.LOOSE);
	    vo.setDirectionalAccuracy(AddressValidationAccuracyType.LOOSE);
	    vo.setCompanyNameAccuracy(AddressValidationAccuracyType.LOOSE);
	    vo.setConvertToUpperCase(new Boolean(true));
	    vo.setRecognizeAlternateCityNames(new Boolean(true));
	    vo.setReturnParsedElements(new Boolean(true));
	    request.setOptions(vo);
	    AddressToValidate av1 = request.addNewAddressesToValidate();
	    Address a1 = av1.addNewAddress();
	    String [] streetlines;
	    if(ship.getAddress2()!=null && ship.getAddress2().length()>0){
	    	streetlines=new String[] {ship.getAddress1(),ship.getAddress2()};
	    } else {
	    	streetlines=new String[] {ship.getAddress1()};
	    }
	    a1.setStreetLinesArray(streetlines);
	    a1.setCity(ship.getCity());
	    a1.setCountryCode(ship.getCountry());
	    if(ship.getCountry().equals("US")){
	    	a1.setStateOrProvinceCode(ship.getState());
	    } else if (ship.getCountry().equals("CA") && provCache.get(ship.getProvince().toUpperCase())!=null){
	    	a1.setStateOrProvinceCode(provCache.get(ship.getProvince().toUpperCase()));
	    }
	    a1.setPostalCode(ship.getPostal());
	    request.setAddressesToValidateArray(new AddressToValidate[] {av1});
	    av1.setAddress(a1);
	    av1.setAddressId("Ship_Address");
	    try {
	    	AddressValidationReplyDocument replyDoc=stub.addressValidation(validate);
			// Initialize the service
			
			// This is the call to the web service
	    	AddressValidationReply reply = replyDoc.getAddressValidationReply();// port.addressValidation(request);
			
			VersionId vid = reply.getVersion();
			boolean insufficientData=false;
			for(com.fedex.ws.addressvalidation.v2.AddressValidationChangeType.Enum changes:reply.getAddressResultsArray(0).getProposedAddressDetailsArray(0).getChangesArray()){
				if(changes.toString().equals("INSUFFICIENT_DATA")){
					insufficientData=true;
				}
			}
			AddressBean proposedAddress=null;
			if(!insufficientData){
				Address pa=reply.getAddressResultsArray(0).getProposedAddressDetailsArray(0).getAddress();
				
				proposedAddress=new AddressBean();
				proposedAddress.setScore(reply.getAddressResultsArray(0).getProposedAddressDetailsArray(0).getScore());
				proposedAddress.setAddress1(pa.getStreetLinesArray(0));
				proposedAddress.setCity(pa.getCity());
				proposedAddress.setCountry(pa.getCountryCode());
				proposedAddress.setPostal(pa.getPostalCode());
				if(pa.getCountryCode().equals("US")){
					proposedAddress.setState(pa.getStateOrProvinceCode());
				} else if (ship.getCountry().equals("CA") && provCache.get(pa.getStateOrProvinceCode().toUpperCase())!=null){
					proposedAddress.setProvince(pa.getStateOrProvinceCode().toUpperCase());
			    }
			}
			if (isResponseOk(reply.getHighestSeverity()))
			{
				AddressValidationResult[] avr = reply.getAddressResultsArray();
				for(int i = 0; i < avr.length; i++)
				{
					System.out.println("Address Id - " + avr[i].getAddressId());
					System.out.println("--- Proposed details ---");
					ProposedAddressDetail[] ad1 = avr[i].getProposedAddressDetailsArray();
					for(int j = 0; j < ad1.length; j++)
					{
						System.out.println("Score - " + ad1[j].getScore());
						System.out.println("Address - " + ad1[j].getAddress().getStreetLinesArray(0));
						System.out.println(ad1[j].getAddress().getStateOrProvinceCode());
						System.out.println(ad1[j].getAddress().getPostalCode());
						System.out.println(ad1[j].getAddress().getCountryCode());
						System.out.println("Changes - " + ad1[j].getScore());
						AddressValidationChangeType.Enum[] avct = ad1[j].getChangesArray();
						for(int k = 0; k < avct.length; k++)
						{
							System.out.println(avct[k].toString());
						}
					}
				}
			}

			printNotifications(reply.getNotificationsArray());
			
			return proposedAddress;
		} catch (Exception e) {
            e.printStackTrace();
		}

		return null;
	}
	private static boolean isResponseOk(com.fedex.ws.addressvalidation.v2.NotificationSeverityType.Enum notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
			notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
			notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
			return true;
		}
 		return false;
	}


	private static com.fedex.ws.addressvalidation.v2.ClientDetail createClientDetail(AddressValidationRequest request) {
		com.fedex.ws.addressvalidation.v2.ClientDetail clientDetail = request.addNewClientDetail();
        String accountNumber = PropertyBMO.getValue(PropertyBMO.FEDEX_ACCOUNT_NUMBER);
        String meterNumber = PropertyBMO.getValue(PropertyBMO.FEDEX_METER_NUMBER);
        //
        // See if the accountNumber and meterNumber properties are set,
        // if set use those values, otherwise default them to "XXX"
        //
        if (accountNumber == null) {
        	accountNumber = "XXX"; // Replace "XXX" with clients account number
        }
        if (meterNumber == null) {
        	meterNumber = "XXX"; // Replace "XXX" with clients meter number
        }
        clientDetail.setAccountNumber(accountNumber);
        clientDetail.setMeterNumber(meterNumber);
        return clientDetail;
	}
	
	private static com.fedex.ws.addressvalidation.v2.WebAuthenticationDetail createWebAuthenticationDetail(AddressValidationRequest request) {
		com.fedex.ws.addressvalidation.v2.WebAuthenticationDetail detail=request.addNewWebAuthenticationDetail();
		com.fedex.ws.addressvalidation.v2.WebAuthenticationCredential wac = detail.addNewUserCredential();
        String key = PropertyBMO.getValue(PropertyBMO.FEDEX_KEY);
        String password = PropertyBMO.getValue(PropertyBMO.FEDEX_PASSWORD);
        
        //
        // See if the key and password properties are set,
        // if set use those values, otherwise default them to "XXX"
        //
        if (key == null) {
        	key = "XXX"; // Replace "XXX" with clients key
        }
        if (password == null) {
        	password = "XXX"; // Replace "XXX" with clients password
        }
        wac.setKey(key);
        wac.setPassword(password);
//        detail.set(wac);
		return detail;
	}
	
	private static void printNotifications(com.fedex.ws.addressvalidation.v2.Notification[] notifications) {
		System.out.println("Notifications:");
		if (notifications == null || notifications.length == 0) {
			System.out.println("  No notifications returned");
		}
		for (int i=0; i < notifications.length; i++){
			Notification n = notifications[i];
			System.out.print("  Notification no. " + i + ": ");
			if (n == null) {
				System.out.println("null");
				continue;
			} else {
				System.out.println("");
			}
			com.fedex.ws.addressvalidation.v2.NotificationSeverityType.Enum nst = n.getSeverity();

			System.out.println("    Severity: " + (nst == null ? "null" : nst.toString()));
			System.out.println("    Code: " + n.getCode());
			System.out.println("    Message: " + n.getMessage());
			System.out.println("    Source: " + n.getSource());
		}
	}
	
	private static void printNotifications(com.fedex.ws.rate.v13.Notification[] notifications) {
		System.out.println("Notifications:");
		if (notifications == null || notifications.length == 0) {
			System.out.println("  No notifications returned");
		}
		for (int i=0; i < notifications.length; i++){
			com.fedex.ws.rate.v13.Notification n = notifications[i];
			System.out.print("  Notification no. " + i + ": ");
			if (n == null) {
				System.out.println("null");
				continue;
			} else {
				System.out.println("");
			}
			com.fedex.ws.rate.v13.NotificationSeverityType.Enum nst = n.getSeverity();

			System.out.println("    Severity: " + (nst == null ? "null" : nst.toString()));
			System.out.println("    Code: " + n.getCode());
			System.out.println("    Message: " + n.getMessage());
			System.out.println("    Source: " + n.getSource());
		}
	}
	
	static ArrayList<RateBean> getRates(AddressBean ship, double declaredValue, float weight){
		boolean getAllRatesFlag = true; 
		RateServiceStub stub=null;
		try {
			stub = new RateServiceStub(PropertyBMO.getValue(PropertyBMO.FEDEX_RATE_ENDPOINT)); //UPDATE
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RateRequestDocument rates = RateRequestDocument.Factory.newInstance();
		RateRequest request= rates.addNewRateRequest();
		
		request.setClientDetail(createClientDetail(request));
	    request.setWebAuthenticationDetail(createWebAuthenticationDetail(request));
	    request.setReturnTransitAndCommit(true);
	    //
	    com.fedex.ws.rate.v13.TransactionDetail transactionDetail = request.addNewTransactionDetail();
	    transactionDetail.setCustomerTransactionId("java sample - Rate Request"); // The client will get the same value back in the response
	    request.setTransactionDetail(transactionDetail);
	
	    //
	    com.fedex.ws.rate.v13.VersionId versionId = request.addNewVersion();
	    versionId.setServiceId("crs");
	    versionId.setMajor(13);
	    versionId.setIntermediate(0);
	    versionId.setMinor(0);
	    request.setVersion(versionId);
	    
	    //
	    RequestedShipment requestedShipment = request.addNewRequestedShipment();
	    
	    requestedShipment.setShipTimestamp(Calendar.getInstance());
	    requestedShipment.setDropoffType(DropoffType.REGULAR_PICKUP);
	    if (! getAllRatesFlag) {
	    	requestedShipment.setServiceType(ServiceType.PRIORITY_OVERNIGHT);
	    	requestedShipment.setPackagingType(PackagingType.YOUR_PACKAGING);
	    }
	    
	    
	    Party shipper = requestedShipment.addNewShipper();
	    com.fedex.ws.rate.v13.Address shipperAddress = shipper.addNewAddress(); // Origin information
	    
	    shipperAddress.setStreetLinesArray(new String[] {"3509 S Broad Street","Suite 202"});
	    shipperAddress.setCity("SCOTTSBORO");
	    shipperAddress.setStateOrProvinceCode("AL");
	    shipperAddress.setPostalCode("35769");
	    shipperAddress.setCountryCode("US");
	    shipper.setAddress(shipperAddress);
	    requestedShipment.setShipper(shipper);
	
	    //
	    Party recipient = requestedShipment.addNewRecipient();
	    com.fedex.ws.rate.v13.Address recipientAddress = recipient.addNewAddress(); // Destination information
	    if(ship.getAddress2()!=null && ship.getAddress2().length()>0){
	    	recipientAddress.setStreetLinesArray(new String[] {ship.getAddress1(),ship.getAddress2()});
	    } else {
	    	recipientAddress.setStreetLinesArray(new String[] {ship.getAddress1()});
	    }
	    recipientAddress.setCity(ship.getCity());
	    if(ship.getCountry().equals("US")){
	    	recipientAddress.setStateOrProvinceCode(ship.getState());
	    } else if (ship.getCountry().equals("CA") && provCache.get(ship.getProvince().toUpperCase())!=null){
	    	recipientAddress.setStateOrProvinceCode(provCache.get(ship.getProvince().toUpperCase()));
	    }
	    recipientAddress.setPostalCode(ship.getPostal());
	    recipientAddress.setCity(ship.getCity());
	    recipientAddress.setCountryCode(ship.getCountry());
	    recipient.setAddress(recipientAddress);
	    requestedShipment.setRecipient(recipient);
	
	    //
	    Payment shippingChargesPayment = requestedShipment.addNewShippingChargesPayment();
	    shippingChargesPayment.setPaymentType(PaymentType.SENDER);
	    requestedShipment.setShippingChargesPayment(shippingChargesPayment);
	
	    RequestedPackageLineItem rp = requestedShipment.addNewRequestedPackageLineItems();
	    rp.setGroupPackageCount(new NonNegativeInteger("1"));
	    Weight w=rp.addNewWeight();
	    w.setUnits(WeightUnits.LB); 
	    w.setValue(new BigDecimal(weight)); 
	    rp.setWeight(w);
	    Money m=rp.addNewInsuredValue();
	    m.setCurrency("USD"); 
	    BigDecimal insuredValue=new BigDecimal("0.00");
	    DecimalFormat format = (DecimalFormat) java.text.NumberFormat.getInstance();
	    format.applyPattern("##0.00");
	    format.setMinimumFractionDigits(2);
	    if(declaredValue>=150){
			insuredValue = BigDecimal.valueOf(Double.valueOf(format.format((declaredValue/100)*.85)));
		}
	    m.setAmount(insuredValue); // Right?
	    rp.setInsuredValue(m);
	    //
	    Dimensions d=rp.addNewDimensions();
	    d.setHeight(new NonNegativeInteger("1"));
	    d.setLength(new NonNegativeInteger("1"));
	    d.setWidth(new NonNegativeInteger("1"));
	    d.setUnits(LinearUnits.IN);
	    rp.setDimensions(d);
	    PackageSpecialServicesRequested pssr = rp.addNewSpecialServicesRequested();
	    rp.setSpecialServicesRequested(pssr);
	    requestedShipment.setRequestedPackageLineItemsArray(new RequestedPackageLineItem[] {rp});
	
	    
	    requestedShipment.setPackageCount(new NonNegativeInteger("1"));
	    requestedShipment.setRateRequestTypesArray(new RateRequestType.Enum[] {RateRequestType.ACCOUNT});
	    request.setRequestedShipment(requestedShipment);
	    
	    //
		try {
//			 Initialize the service
			
//			 This is the call to the web service passing in a RateRequest and returning a RateReply
			com.fedex.ws.rate.v13.RateReplyDocument replyDoc=stub.getRates(rates);
			if (isResponseOk2(replyDoc.getRateReply().getHighestSeverity())) {
				writeServiceOutput(replyDoc.getRateReply());
			} 
			printNotifications(replyDoc.getRateReply().getNotificationsArray());
			ArrayList<RateBean> ratesList=new ArrayList<RateBean>();
			BigDecimal shippingCost;
			//Logic for if SWA, add 1. If AA, add 5
			shippingCost=new BigDecimal(1);
			if(replyDoc.getRateReply().getRateReplyDetailsArray().length==0){
				return null;
			}
			for(RateReplyDetail r:replyDoc.getRateReply().getRateReplyDetailsArray()){
				RateBean rbean=new RateBean();
				r.getDeliveryDayOfWeek();
				rbean.setRateKey(r.getCommitDetailsArray(0).getServiceType().toString());
				rbean.setRateAmount(format.format((r.getRatedShipmentDetailsArray(0).getShipmentRateDetail().getTotalNetCharge().getAmount().add(shippingCost)).doubleValue())+" "+r.getRatedShipmentDetailsArray(0).getShipmentRateDetail().getTotalNetCharge().getCurrency());//Update after demo
				rbean.setEstDeliveryDate(DateUtils.formatDate(r.getDeliveryTimestamp().getTime(), TracingConstants.DISPLAY_DATETIMEFORMAT, null, null));
				rbean.setRateType(r.getCommitDetailsArray(0).getServiceType().toString().replace("_", " "));
				if(r.getRatedShipmentDetailsArray(0).getShipmentRateDetail().getTaxesArray().length>0){
					rbean.setRateTax(r.getRatedShipmentDetailsArray(0).getShipmentRateDetail().getTaxesArray(0).toString());
				}
				ratesList.add(rbean);
			}
			System.out.println("MADE IT!");
			return ratesList;
		} catch (Exception e) {
		    e.printStackTrace();
		} 
		return null;
	}
	
	public static void writeServiceOutput(RateReply reply) {
		RateReplyDetail[] rrds = reply.getRateReplyDetailsArray();
		for (int i = 0; i < rrds.length; i++) {
			RateReplyDetail rrd = rrds[i];
			//print("\nService type", rrd.getServiceType());
			print("Packaging type", rrd.getPackagingType());
			//print("Delivery DOW", rrd.getDeliveryDayOfWeek());
			int month = rrd.getDeliveryTimestamp().get(Calendar.MONTH)+1;
			int date = rrd.getDeliveryTimestamp().get(Calendar.DAY_OF_MONTH);
			int year = rrd.getDeliveryTimestamp().get(Calendar.YEAR);
			String delDate = new String(month + "/" + date + "/" + year);
			print("Delivery date", delDate);
			print("Calendar DOW", rrd.getDeliveryTimestamp().get(Calendar.DAY_OF_WEEK));
	
			RatedShipmentDetail[] rsds = rrd.getRatedShipmentDetailsArray();
			for (int j = 0; j < rsds.length; j++) {
				print("RatedShipmentDetail " + j, "");
				RatedShipmentDetail rsd = rsds[j];
				ShipmentRateDetail srd = rsd.getShipmentRateDetail();
				print("  Rate type", srd.getRateType());
				printWeight("  Total Billing weight", srd.getTotalBillingWeight());
				printMoney("  Total surcharges", srd.getTotalSurcharges());
				printMoney("  Total net charge", srd.getTotalNetCharge());
	
				RatedPackageDetail[] rpds = rsd.getRatedPackagesArray();
				if (rpds != null && rpds.length > 0) {
					print("  RatedPackageDetails", "");
					for (int k = 0; k < rpds.length; k++) {
						print("  RatedPackageDetail " + i, "");
						RatedPackageDetail rpd = rpds[k];
						PackageRateDetail prd = rpd.getPackageRateDetail();
						if (prd != null) {
							printWeight("    Billing weight", prd.getBillingWeight());
							printMoney("    Base charge", prd.getBaseCharge());
							Surcharge[] surcharges = prd.getSurchargesArray();
							if (surcharges != null && surcharges.length > 0) {
								for (int m = 0; m < surcharges.length; m++) {
									Surcharge surcharge = surcharges[m];
									printMoney("    " + surcharge.getDescription() + " surcharge", surcharge.getAmount());
								}
							}
						}
					}
				}
			}
			System.out.println("");
		}
	}
	
	private static com.fedex.ws.rate.v13.ClientDetail createClientDetail(RateRequest request) {
	    com.fedex.ws.rate.v13.ClientDetail clientDetail = request.addNewClientDetail();
	    String accountNumber = PropertyBMO.getValue(PropertyBMO.FEDEX_ACCOUNT_NUMBER);
	    String meterNumber = PropertyBMO.getValue(PropertyBMO.FEDEX_METER_NUMBER);
	    
	    //
	    // See if the accountNumber and meterNumber properties are set,
	    // if set use those values, otherwise default them to "XXX"
	    //
	    if (accountNumber == null) {
	    	accountNumber = "XXX"; // Replace "XXX" with clients account number
	    }
	    if (meterNumber == null) {
	    	meterNumber = "XXX"; // Replace "XXX" with clients meter number
	    }
	    clientDetail.setAccountNumber(accountNumber);
	    clientDetail.setMeterNumber(meterNumber);
	    return clientDetail;
	}
	
	private static com.fedex.ws.rate.v13.WebAuthenticationDetail createWebAuthenticationDetail(RateRequest request) {

	    com.fedex.ws.rate.v13.WebAuthenticationDetail detail=request.addNewWebAuthenticationDetail();
	    com.fedex.ws.rate.v13.WebAuthenticationCredential wac = detail.addNewUserCredential();
	    String key = PropertyBMO.getValue(PropertyBMO.FEDEX_KEY);
	    String password = PropertyBMO.getValue(PropertyBMO.FEDEX_PASSWORD);
	    
	    //
	    // See if the key and password properties are set,
	    // if set use those values, otherwise default them to "XXX"
	    //
	    if (key == null) {
	    	key = "XXX"; // Replace "XXX" with clients key
	    }
	    if (password == null) {
	    	password = "XXX"; // Replace "XXX" with clients password
	    }
	    wac.setKey(key);
	    wac.setPassword(password);
		return detail;
	}
	
	private static boolean isResponseOk2(com.fedex.ws.rate.v13.NotificationSeverityType.Enum notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
			notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
			notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
			return true;
		}
			return false;
	}
	
	private static void print(String msg, Object obj) {
		if (msg == null || obj == null) {
			return;
		}
		System.out.println(msg + ": " + obj.toString());
	}
	
	private static void printMoney(String msg, Money money) {
		if (msg == null || money == null) {
			return;
		}
		System.out.println(msg + ": " + money.getAmount() + " " + money.getCurrency());
	}
	
	private static void printWeight(String msg, Weight weight) {
		if (msg == null || weight == null) {
			return;
		}
		System.out.println(msg + ": " + weight.getValue() + " " + weight.getUnits());
	}
	
//	private static void updateEndPoint(RateServiceLocator serviceLocator) {
//		String endPoint = System.getProperty("endPoint");
//		if (endPoint != null) {
//			serviceLocator.setRateServicePortEndpointAddress(endPoint);
//		}
//	}
//	
//	private static void updateEndPoint2(AddressValidationServiceLocator serviceLocator) {
//		String endPoint = System.getProperty("endPoint");
//		if (endPoint != null) {
//			serviceLocator.setAddressValidationServicePortEndpointAddress(endPoint);
//		}
//	}


}