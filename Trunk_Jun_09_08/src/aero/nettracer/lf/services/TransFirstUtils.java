package aero.nettracer.lf.services;

import org.apache.axis2.AxisFault;

import postilion.realtime.merchantframework.wsdl.v1.MerchantWebServicesStub;
import postilion.realtime.merchantframework.xsd.v1.Card;
import postilion.realtime.merchantframework.xsd.v1.Contact;
import postilion.realtime.merchantframework.xsd.v1.Merc;
import postilion.realtime.merchantframework.xsd.v1.Phone;
import postilion.realtime.merchantframework.xsd.v1.SendTranRequestDocument;
import postilion.realtime.merchantframework.xsd.v1.SendTranRequestDocument.SendTranRequest;
import postilion.realtime.merchantframework.xsd.v1.SendTranResponseDocument;
import postilion.realtime.merchantframework.xsd.v1.Ship;
import postilion.realtime.merchantframework.xsd.v1.Tax;
import postilion.realtime.merchantframework.xsd.v1.TranData;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.PhoneBean;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.lf.LFTransaction;
import com.fedex.ws.addressvalidation.v2.NotificationSeverityType;

public class TransFirstUtils {
	
	static LFTransaction sendTransaction(LostReportBean lbean, int tranCode){
		MerchantWebServicesStub stub=null;

		try {
			stub = new MerchantWebServicesStub(PropertyBMO.getValue(PropertyBMO.TRANSFIRST_ADDRESS_ENDPOINT));
		} catch (AxisFault e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		SendTranRequestDocument send=SendTranRequestDocument.Factory.newInstance();
		SendTranRequest request=send.addNewSendTranRequest();
		request.setMerc(createMerc(request));
		request.setTranCode(tranCode); //0 For Authorization, 1 for actual Transaction, 9 for account verification
		Contact c= request.addNewContact();
		c.setFullName(lbean.getContact().getFirstName()+" "+lbean.getContact().getLastName());
		PhoneBean pb=lbean.getContact().getShippingPhone();
		String pNumber;
		if(pb.getNumber()!=null && pb.getNumber().length()>0){
			pNumber=pb.getNumber();
		} else {
			pNumber=pb.getCountry()+pb.getArea()+pb.getExchange()+pb.getLine();
		}
		if(pNumber!=null && pNumber.length()>=10 && pNumber.length()<=12){
			Phone p=c.addNewPhone();
			p.setType(0);
			p.setNr(pNumber);
		}
		
		c.setAddrLn1(lbean.getContact().getBillingaddress().getAddress1());
		c.setAddrLn2(lbean.getContact().getBillingaddress().getAddress2());
		c.setCity(lbean.getContact().getBillingaddress().getCity());
		if(lbean.getContact().getBillingaddress().getCountry().equals("US") || lbean.getContact().getBillingaddress().getCountry().equals("CA")){
			c.setState(postilion.realtime.merchantframework.xsd.v1.Contact.State.Enum.forString(lbean.getContact().getBillingaddress().getState())); //ENUM?
		}
		c.setZipCode(lbean.getContact().getBillingaddress().getPostal());
		c.setCtry(lbean.getContact().getBillingaddress().getCountry());
		c.setEmail(lbean.getContact().getEmailAddress());
		
		Ship s=c.addNewShip();
		s.setFullName(lbean.getContact().getFirstName()+" "+lbean.getContact().getLastName());
		s.setAddrLn1(lbean.getContact().getPrefshipaddress().getAddress1());
		s.setAddrLn2(lbean.getContact().getPrefshipaddress().getAddress2());
		s.setCity(lbean.getContact().getPrefshipaddress().getCity());
		if(lbean.getContact().getPrefshipaddress().getCountry().equals("US") || lbean.getContact().getPrefshipaddress().getCountry().equals("CA")){
			s.setState(postilion.realtime.merchantframework.xsd.v1.Ship.State.Enum.forString(lbean.getContact().getPrefshipaddress().getState())); //Enum?
		}
		s.setZipCode(lbean.getContact().getPrefshipaddress().getPostal()); 
		
		if(pNumber!=null && pNumber.length()>=10 && pNumber.length()<=12){
			s.setPhone(pNumber);
		}
		
		s.setEmail(lbean.getContact().getEmailAddress());
		
		//Logic for tax
		if(tranCode==1){
			String payment="0";
			if (lbean.getShippingPayment()!=null){
				payment=lbean.getShippingPayment().replace("USD", "").replace(" ", "").replace(".", "");
			}
			request.setReqAmt("0"+payment); //Leading zero required
		}
		
		
		Card ca=request.addNewCard();
		ca.setPan(lbean.getCc().getCcnumber()); //Primary Account Number
		ca.setXprDt(lbean.getCc().getCcexp()); //expiration month - YYMM format
		
		try {
//			 Initialize the service
			
//			 This is the call to the web service passing in a RateRequest and returning a RateReply
			SendTranResponseDocument replyDoc=stub.sendTran(send);
			
			System.out.println(replyDoc.getSendTranResponse().toString());
			
			TranData td=replyDoc.getSendTranResponse().getTranData();
			LFTransaction tran=new LFTransaction();
			tran.setRspCode(replyDoc.getSendTranResponse().getRspCode());
			tran.setSwchKey(td.getSwchKey()); //What is this?
			tran.setAmt(td.getAmt());
			tran.setAuthCode(td.getAuth());
			tran.setTransactionDate(td.getDtTm().getTime());
			tran.setStan(td.getStan()); //What the heck is stan?
			tran.setTranNum(td.getTranNr());
//			tran.setShipment(shipment);
			return tran;
		} catch (Exception e) {
		    e.printStackTrace();
		} 
		return null;
	}

	private static Merc createMerc(SendTranRequest request) {
		// TODO Auto-generated method stub
		Merc merc = request.addNewMerc();
		merc.setId(PropertyBMO.getValue(PropertyBMO.TRANSFIRST_USER_ID)); //
		merc.setRegKey(PropertyBMO.getValue(PropertyBMO.TRANSFIRST_REG_KEY));
		merc.setInType(1); //Must be 1 for Merchant Web Service
		merc.setProdType(5); //5 = Credit/Debit Card (Default);
        return merc;
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
	
}