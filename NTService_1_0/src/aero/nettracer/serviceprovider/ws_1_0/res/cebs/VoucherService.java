package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.swacorp.services.btws.wsdl.v1.BTWSStub;
import com.swacorp.services.btws.wsdl.v1.CancelLuvVoucherError;
import com.swacorp.services.btws.wsdl.v1.IssueLuvVoucherError;
import com.swacorp.services.btws.v1.CancelLuvVoucherRequestDocument.CancelLuvVoucherRequest;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest.Address;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest.DistributionMethod.Enum;
import com.swacorp.services.btws.v1.IssueLuvVoucherResponseDocument;
import com.swacorp.services.btws.v1.CancelLuvVoucherRequestDocument;
import com.swacorp.services.btws.v1.CancelLuvVoucherResponseDocument;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Voucher;
import aero.nettracer.serviceprovider.ws_1_0.res.VoucherServiceInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.VoucherResponse;

public class VoucherService implements VoucherServiceInterface {

	public static String STATUS_OPEN = "open";
	public static String STATUS_CANCEL = "cancel";
	public static String ISSUE_METHOD_EMAIL = "EMAIL";
	public static String ISSUE_METHOD_HOLD_FOR_PICKUP = "HOLD_FOR_PICKUP";
	public static String ISSUE_METHOD_MAIL = "USPS";
	public static String ISSUE_METHOD_IMMEDIATE = "IMMEDIATE";
	
	private static Logger logger = Logger.getLogger(VoucherService.class);
	
	/**
	 * Defining stub for mocking/junit
	 */
	private BTWSStub btwsStub = null;
	
	/**
	 * Defining document for mocking/junit
	 */
	private IssueLuvVoucherRequestDocument issueRequestDoc = null;
	private CancelLuvVoucherRequestDocument cancelRequestDoc = null;
	

	/**
	 * Maps Voucher to the IssuLuvVoucherRequestDocument.
	 * 
	 * For mocking testing purposes, the IssueLuvVoucherRequestDocument can be set in advance
	 * 
	 * 
	 * @param voucher
	 * @return
	 */
	public IssueLuvVoucherRequestDocument getIssueRequestDoc(Voucher voucher) {
		if(issueRequestDoc == null){
			IssueLuvVoucherRequestDocument doc = IssueLuvVoucherRequestDocument.Factory.newInstance();
			IssueLuvVoucherRequest request = doc.addNewIssueLuvVoucherRequest();
			if(voucher != null){
				/*
				 * We are able to successfully submit a request without a phone number,
				 * however, if this field is null the request will fail.  Populating with a default empty string value
				 * that can be overridden with a actual value
				 */
				request.setCountryCallingCode("");
				
				
				if(voucher.getPassenger() != null){
					if(voucher.getPassenger().getAddresses() != null){
						Address addr = request.addNewAddress();
						if(voucher.getPassenger().getAddresses().getAddress1() != null){
							addr.setAddressLine1(voucher.getPassenger().getAddresses().getAddress1());
						}
						if(voucher.getPassenger().getAddresses().getAddress2() != null){
							addr.setAddressLine2(voucher.getPassenger().getAddresses().getAddress2());
						}
						if(voucher.getPassenger().getAddresses().getCity() != null){
							addr.setCity(voucher.getPassenger().getAddresses().getCity());
						}
						if(voucher.getPassenger().getAddresses().getCountry() != null){
							addr.setCountryCode(voucher.getPassenger().getAddresses().getCountry());
						}
						if(voucher.getPassenger().getAddresses().getEmailAddress() != null){
							addr.setEmail(voucher.getPassenger().getAddresses().getEmailAddress());
						}
						if(voucher.getPassenger().getAddresses().getZip() != null){
							addr.setPostalCode(voucher.getPassenger().getAddresses().getZip());
						}
						if(voucher.getPassenger().getAddresses().getState() != null){
							addr.setState(voucher.getPassenger().getAddresses().getState());
						}
						if(voucher.getPassenger().getAddresses().getProvince() != null){
							addr.setProvince(voucher.getPassenger().getAddresses().getProvince());
						}
						if(voucher.getPassenger().getAddresses().getHomePhone() != null &&
								voucher.getPassenger().getAddresses().getHomePhone().length() > 0){
							mapPhone(voucher.getPassenger().getAddresses().getHomePhone(), request);
						} 
					} 
					
					if(voucher.getPassenger().getFirstname() != null && voucher.getPassenger().getFirstname().length() > 0){
						request.setFirstName(voucher.getPassenger().getFirstname());	
					} 
					if(voucher.getPassenger().getLastname() != null && voucher.getPassenger().getLastname().length() > 0){
						request.setLastName(voucher.getPassenger().getLastname());
					} 
				} 
				if(voucher.getAgentUserName() != null){
					request.setAgentId(voucher.getAgentUserName());
				}
				request.setAmount((float)voucher.getAmount());
				if(voucher.getDistributionMethod() != null && voucher.getDistributionMethod().length() > 0) {
					request.setDistributionMethod(Enum.forString(voucher.getDistributionMethod()));
				} 
				if(voucher.getPnr() != null && voucher.getPnr().length() > 0){
					request.setPNR(voucher.getPnr());
				} 
				if(voucher.getStation() != null && voucher.getStation().length() > 0){
					request.setStationCode(voucher.getStation());
				}
				if(voucher.getNtIncidentId() != null && voucher.getNtIncidentId().length() > 0){
					request.setIncident(voucher.getNtIncidentId());
				} 
			}
			return doc;
		} else {
			//returning mock request for junit test
			return issueRequestDoc;
		}
	}
	
	/**
	 * Identifies if the phone number is US, if so, then splits the phone number into
	 * country code, area code, exchange and line number.
	 * 
	 * If not identified as US, set the phone as international.
	 * 
	 * @param phone
	 * @param request
	 */
	protected void mapPhone(String phone, IssueLuvVoucherRequest request){
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		if(phone != null){
			PhoneNumber usPhone = null;
			try {
				usPhone = phoneUtil.parse(phone, "US");
			} catch (NumberParseException e) {
			}
			if(phoneUtil.isValidNumber(usPhone) && usPhone.getCountryCode() == 1){	
				usPhone.getCountryCode();
				request.setCountryCallingCode("" + usPhone.getCountryCode());
				String nationalNumber = "" + usPhone.getNationalNumber();
				if(nationalNumber.length() >= 10){
					request.setAreaCode(nationalNumber.substring(0, 3));
					request.setExchangeNumber(nationalNumber.substring(3,6));
					request.setLineNumber(nationalNumber.substring(6, 10)); 
				}
			} else {
				request.setIntlPhoneNumber(phone);
			}
		}
	}
	
	/**
	 * Set a request document that is used for mock unit testing
	 * 
	 * @param issueRequestDoc
	 */
	public void setIssueRequestDoc(IssueLuvVoucherRequestDocument issueRequestDoc) {
		this.issueRequestDoc = issueRequestDoc;
	}
	

	/**
	 * Maps Voucher to the CancelLuvVoucherRequestDocument
	 * 
	 * @param voucher
	 * @return
	 */
	public CancelLuvVoucherRequestDocument getCancelRequestDoc(Voucher voucher) {
		if(cancelRequestDoc == null){
			CancelLuvVoucherRequestDocument doc = CancelLuvVoucherRequestDocument.Factory.newInstance();
			CancelLuvVoucherRequest request = doc.addNewCancelLuvVoucherRequest();
			if(voucher != null){
				request.setOrderNumber(voucher.getOrderNumber());
				request.setReason(voucher.getRemark());
				request.setAgentId(voucher.getAgentUserName());
				request.setStationCode(voucher.getStation());
			}
			return doc;
		} else {
			//returning mock request for junit test
			return cancelRequestDoc;
		}
	}
	
	/**
	 * Set a request document that is used for mock unit testing
	 * 
	 * @param cancelRequestDoc
	 */
	public void setCancelRequestDoc(CancelLuvVoucherRequestDocument cancelRequestDoc) {
		this.cancelRequestDoc = cancelRequestDoc;
	}
	
	
	protected BTWSStub getBtwsStub() {
		return btwsStub;
	}

	protected void setBtwsStub(BTWSStub btwsStub) {
		this.btwsStub = btwsStub;
	}
	
	/* (non-Javadoc)
	 * @see aero.nettracer.serviceprovider.ws_1_0.res.VoucherServiceInterface#submitVoucher(aero.nettracer.serviceprovider.common.db.User, aero.nettracer.serviceprovider.ws_1_0.common.xsd.Voucher)
	 * 
	 * SWA provides two voucher methods, issue and cancel.  Depending on the status of the voucher, this service will sumbmit either a issue or cancel request
	 */
	@Override
	public VoucherResponse submitVoucher(User user, Voucher voucher) {
		if(voucher != null && voucher.getStatus() != null){
			if(voucher.getStatus().equals(STATUS_OPEN)){
				return issueVoucher(user,voucher);
			} else if (voucher.getStatus().equals(STATUS_CANCEL)){
				return cancelVoucher(user,voucher);
			} 
		}
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
		ret.addNewError().setDescription("Voucher status must be either " + STATUS_OPEN + " or " + STATUS_CANCEL);
		ret.setSuccess(false);
		return ret;
	}
		
	
	/**
	 * Submits an issueVoucher and maps the response
	 * 
	 * @param user
	 * @param voucher
	 * @return
	 */
	private VoucherResponse issueVoucher(User user, Voucher voucher){
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
		IssueLuvVoucherRequestDocument doc= getIssueRequestDoc(voucher);
		ret.setSuccess(true);
		
		try {
			BTWSStub stub = ConnectionUtil.getStub(btwsStub, user);
			logger.info(doc);
			doc.validate();
			IssueLuvVoucherResponseDocument response = stub.issueLuvVoucher(doc);
			logger.info(response);
			
			if(response != null && response.getIssueLuvVoucherResponse() != null){
				//map response for return to ntcore
				ret.setIssueDate(response.getIssueLuvVoucherResponse().getTransactionDate());
				ret.setCardNumber(response.getIssueLuvVoucherResponse().getCardNumber());
				ret.setMessage(response.getIssueLuvVoucherResponse().getMessage());
				ret.setSecurityCode(response.getIssueLuvVoucherResponse().getSecurityCode());
				if(response.getIssueLuvVoucherResponse().getReturnCode() != null){
					ret.setReturnCode(response.getIssueLuvVoucherResponse().getReturnCode().intValue());
				}
				ret.setOrderNumber(response.getIssueLuvVoucherResponse().getOrderNumber());
			} else {
				ret.setSuccess(false);
				ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.REMOTE_EXCEPTION + ": " + e.getMessage());
		} catch (IssueLuvVoucherError e) {
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION + ": " + e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION + ": " + e.getMessage());
		}
		return ret;
	}
	
	/**
	 * Submits a cancel voucher request and maps the response
	 * 
	 * @param user
	 * @param voucher
	 * @return
	 */
	private VoucherResponse cancelVoucher(User user, Voucher voucher){
		CancelLuvVoucherRequestDocument doc= getCancelRequestDoc(voucher);
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
		ret.setSuccess(true);
		try {
			BTWSStub stub = ConnectionUtil.getStub(btwsStub, user);
			
			logger.info(doc);
			CancelLuvVoucherResponseDocument response = stub.cancelLuvVoucher(doc);
			logger.info(response);
			
			if(response != null && response.getCancelLuvVoucherResponse() != null){
				//map response for return to ntcore
				ret.setCancelDate(response.getCancelLuvVoucherResponse().getCancelDate());
				ret.setStatus(response.getCancelLuvVoucherResponse().getCancelStatus());
			} else {
				ret.setSuccess(false);
				ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.REMOTE_EXCEPTION + ": " + e.getMessage());
		} catch (CancelLuvVoucherError e) {
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION + ": " + e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION + ": " + e.getMessage());
		}
		return ret;
	}
	
}
