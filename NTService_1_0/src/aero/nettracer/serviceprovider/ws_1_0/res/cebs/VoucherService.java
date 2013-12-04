package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
	 * @param voucher
	 * @return
	 */
	public IssueLuvVoucherRequestDocument getIssueRequestDoc(Voucher voucher, List<String> errors) {
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
							mapPhone(voucher.getPassenger().getAddresses().getHomePhone(), request);//consider check other phones
						} 
//						else {
//							if(voucher.getDistributionMethod() != null && voucher.getDistributionMethod().equals(ISSUE_METHOD_MAIL)){
//								errors.add("Home phone is required");
//							}
//						}
					} 
//					else {
//						errors.add("Address is required");
//					}
					
					if(voucher.getPassenger().getFirstname() != null && voucher.getPassenger().getFirstname().length() > 0){
						request.setFirstName(voucher.getPassenger().getFirstname());	
					} 
//					else {
//						errors.add("First name is required");
//					}
					if(voucher.getPassenger().getLastname() != null && voucher.getPassenger().getLastname().length() > 0){
						request.setLastName(voucher.getPassenger().getLastname());
					} 
//					else {
//						errors.add("Last name is required");
//					}
				} 
//				else {
//					errors.add("Passenger is required");
//				}
				if(voucher.getAgentUserName() != null){
					request.setAgentId(voucher.getAgentUserName());
				}
				request.setAmount((float)voucher.getAmount());
				if(voucher.getRemark() != null && voucher.getRemark().length() > 0){
					request.setCustomMessage(voucher.getRemark());
				}
				if(voucher.getDepartment() != null && voucher.getDepartment().length() > 0){
					request.setDepartment(voucher.getDepartment());
				}
//				if(voucher.getDistributionMethod() != null && voucher.getDistributionMethod().length() > 0 &&
//						(voucher.getDistributionMethod().equals(ISSUE_METHOD_EMAIL)||
//						 voucher.getDistributionMethod().equals(ISSUE_METHOD_HOLD_FOR_PICKUP) ||
//						 voucher.getDistributionMethod().equals(ISSUE_METHOD_IMMEDIATE) ||
//						 voucher.getDistributionMethod().equals(ISSUE_METHOD_MAIL))){
				if(voucher.getDistributionMethod() != null && voucher.getDistributionMethod().length() > 0) {
					request.setDistributionMethod(voucher.getDistributionMethod());
				} 
//				else {
//					errors.add("Distribution method must be one of the following: "
//							+ ISSUE_METHOD_EMAIL + ", " + ISSUE_METHOD_HOLD_FOR_PICKUP + ", "
//							+ ISSUE_METHOD_IMMEDIATE + ", " + ISSUE_METHOD_MAIL);
//				}
				if(voucher.getPnr() != null && voucher.getPnr().length() > 0){
					request.setPNR(voucher.getPnr());
				} 
//				else {
//					errors.add("PNR is required");
//				}
				if(voucher.getStation() != null && voucher.getStation().length() > 0){
					request.setStationCode(voucher.getStation());
				}
				if(voucher.getNtIncidentId() != null && voucher.getNtIncidentId().length() > 0){
					request.setIncident(voucher.getNtIncidentId());
				} 
//				else {
//					errors.add("NtIncidentId is required");
//				}
			}
			return doc;
		} else {
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
	 * @param voucher
	 * @return
	 */
	public CancelLuvVoucherRequestDocument getCancelRequestDoc(Voucher voucher) {
		if(cancelRequestDoc == null){
			CancelLuvVoucherRequestDocument doc = CancelLuvVoucherRequestDocument.Factory.newInstance();
			CancelLuvVoucherRequest request = doc.addNewCancelLuvVoucherRequest();
			if(voucher != null){
				request.setOrderNumber(voucher.getVoucherId());
				request.setReason(voucher.getRemark());
				voucher.getVoucherId();
			}
			return doc;
		} else {
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
	
	@Override
	public VoucherResponse submitVoucher(User user, Voucher voucher) {
		if(voucher != null && voucher.getStatus() != null){
			if(voucher.getStatus().equals(STATUS_OPEN)){
				return issueVoucher(user,voucher);
			} else if (voucher.getStatus().equals(STATUS_OPEN)){
				return cancelVoucher(user,voucher);
			} 
		}
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
		ret.addNewError().setDescription("Voucher status must be either " + STATUS_OPEN + " or " + STATUS_CANCEL);
		return ret;
	}
		
	
	private VoucherResponse issueVoucher(User user, Voucher voucher){
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
		ArrayList<String> errors = new ArrayList<String>();
		IssueLuvVoucherRequestDocument doc= getIssueRequestDoc(voucher, errors);
		
		if(errors != null && errors.size() > 0){
			for(String error:errors){
				System.out.println(error);
				ret.addNewError().setDescription(error);
			}
			return ret;
		}
		
		try {
			BTWSStub stub = ConnectionUtil.getStub(btwsStub, user);
			logger.info(doc);
			doc.validate();
			IssueLuvVoucherResponseDocument response = stub.issueLuvVoucher(doc);
			logger.info(response);
			
			if(response != null && response.getIssueLuvVoucherResponse() != null){
				//map response for return to ntcore
				ret.setIssueDate(response.getIssueLuvVoucherResponse().getTransactionDate());
				ret.setVoucherId(response.getIssueLuvVoucherResponse().getCardNumber());
				ret.setMessage(response.getIssueLuvVoucherResponse().getMessage());
				ret.setSecurityCode(response.getIssueLuvVoucherResponse().getSecurityCode());
				if(response.getIssueLuvVoucherResponse().getReturnCode() != null){
					ret.setReturnCode(response.getIssueLuvVoucherResponse().getReturnCode().intValue());
				}
				ret.setOrderNumber(response.getIssueLuvVoucherResponse().getOrderNumber());
			} else {
				ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.REMOTE_EXCEPTION);
		} catch (IssueLuvVoucherError e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);//TODO provide more meaningful error message
		} catch (Exception e){
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return ret;
	}
	
	private VoucherResponse cancelVoucher(User user, Voucher voucher){
		CancelLuvVoucherRequestDocument doc= getCancelRequestDoc(voucher);
		VoucherResponse ret = VoucherResponse.Factory.newInstance();
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
				ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.REMOTE_EXCEPTION);
		} catch (CancelLuvVoucherError e) {
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.VOUCHER_DATA_EXCEPTION);//TODO provide more meaningful error message
		} catch (Exception e){
			e.printStackTrace();
			ret.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return ret;
	}
	
}
