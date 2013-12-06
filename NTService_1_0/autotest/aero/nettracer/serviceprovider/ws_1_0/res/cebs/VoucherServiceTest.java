package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.swacorp.services.btws.v1.CancelLuvVoucherRequestDocument;
import com.swacorp.services.btws.v1.CancelLuvVoucherRequestDocument.CancelLuvVoucherRequest;
import com.swacorp.services.btws.v1.CancelLuvVoucherResponseDocument;
import com.swacorp.services.btws.v1.CancelLuvVoucherResponseDocument.CancelLuvVoucherResponse;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest.DistributionMethod.Enum;
import com.swacorp.services.btws.v1.IssueLuvVoucherResponseDocument;
import com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest;
import com.swacorp.services.btws.v1.IssueLuvVoucherResponseDocument.IssueLuvVoucherResponse;
import com.swacorp.services.btws.wsdl.v1.BTWSStub;

import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Voucher;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.VoucherResponse;

public class VoucherServiceTest {
	BTWSStub stub;
	
	private String username = "southwest_test";
	private String password = "WNpass1!";
	
	private IssueLuvVoucherRequestDocument ai;
	private CancelLuvVoucherRequestDocument bi;
	
	
	/**
	 * Initializes all mock requests/responses
	 */
	public VoucherServiceTest() {
		try {
			//Create mock stub
			stub = mock(BTWSStub.class);
			
			//Create first mock response
			ai = IssueLuvVoucherRequestDocument.Factory.newInstance();
			IssueLuvVoucherRequest ai2 = ai.addNewIssueLuvVoucherRequest();
			ai2.setAgentId("ntagent");
			ai2.setAmount(new Float(100.00));
			ai2.setCountryCallingCode("1");
			ai2.setAreaCode("770");
			ai2.setExchangeNumber("319");
			ai2.setLineNumber("8085");
			ai2.setDistributionMethod(Enum.forString("IMMEDIATE"));
			ai2.setFirstName("Bill");
			ai2.setLastName("Nettracer");
			ai2.setStationCode("ATL");
			com.swacorp.services.btws.v1.IssueLuvVoucherRequestDocument.IssueLuvVoucherRequest.Address addrA = ai2.addNewAddress();
			addrA.setAddressLine1("2675 Paces Ferry Rd");
			addrA.setAddressLine2("Suite 240");
			addrA.setCity("Atlanta");
			addrA.setState("GA");
			addrA.setPostalCode("30339");
			addrA.setCountryCode("US");
			when(stub.issueLuvVoucher(ai)).thenReturn(getResponseOne());
			
			//Create second mock response
			bi = CancelLuvVoucherRequestDocument.Factory.newInstance();
			CancelLuvVoucherRequest bi2 = bi.addNewCancelLuvVoucherRequest();
			bi2.setOrderNumber("123456");
			bi2.setReason("Cancel");
			when(stub.cancelLuvVoucher(bi)).thenReturn(getResponseTwo());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mock successful voucher issue
	 * 
	 * @return
	 */
	private IssueLuvVoucherResponseDocument getResponseOne() {
		IssueLuvVoucherResponseDocument doc = IssueLuvVoucherResponseDocument.Factory.newInstance();
		IssueLuvVoucherResponse response = doc.addNewIssueLuvVoucherResponse();
		response.setOrderNumber("123456");
		response.setReturnCode(BigInteger.valueOf(123456));
		response.setSecurityCode("asdf123456");
		response.setCardNumber("1234123412341234");
		response.setTransactionDate(new GregorianCalendar());
		return doc;
	}
	
	/**
	 * Mock successful cancel voucher
	 * 
	 * @return
	 */
	private CancelLuvVoucherResponseDocument getResponseTwo(){
		CancelLuvVoucherResponseDocument doc = CancelLuvVoucherResponseDocument.Factory.newInstance();
		CancelLuvVoucherResponse response = doc.addNewCancelLuvVoucherResponse();
		response.setCancelDate(new GregorianCalendar());
		response.setCancelStatus("cancel");
		return doc;
	}
	
	/**
	 * Tests issuing of voucher
	 */
	@Test
	public void issueVoucherSuccessTest(){
		VoucherService service = new VoucherService();
		service.setBtwsStub(stub);
		service.setIssueRequestDoc(ai);
		
		VoucherResponse response = service.submitVoucher(null, createIssueVoucher());
		
		assertTrue(response.getOrderNumber().equals("123456"));
		assertTrue(response.getReturnCode() == 123456);
		assertTrue(response.getSecurityCode().equals("asdf123456"));
		assertTrue(response.getCardNumber().equals("1234123412341234"));
		assertTrue(response.getSuccess() == true);
	}
	
	/**
	 * Tests canceling of voucher
	 */
	@Test
	public void cancelVoucherSuccessTest(){
		VoucherService service = new VoucherService();
		service.setBtwsStub(stub);
		service.setCancelRequestDoc(bi);
		VoucherResponse response = service.submitVoucher(null, createCancelVoucher("123456"));
		assertTrue(response.getStatus().equals("cancel"));
		assertTrue(response.getSuccess() == true);
	}
	
	/**
	 * The voucher service requires a status of either open or cancel.  Test for error message.
	 */
	@Test
	public void statusTest(){
		VoucherService service = new VoucherService();
		Voucher voucher = createIssueVoucher();
		voucher.setStatus("");
		VoucherResponse response = service.submitVoucher(null, voucher);
		assertTrue(response.getError().getDescription().equals("Voucher status must be either open or cancel"));
	}
	
	/**
	 * Phone numbers for SWA must be split into country, area, exchange, line
	 * Test parsing of phone number
	 */
	@Test
	public void testPhoneParse(){
		VoucherService vs = new VoucherService();
		IssueLuvVoucherRequestDocument doc = IssueLuvVoucherRequestDocument.Factory.newInstance();
		IssueLuvVoucherRequest request = doc.addNewIssueLuvVoucherRequest();
		
		String phone = "770-319-8085";
		vs.mapPhone(phone, request);
		assertTrue("1".equals(request.getCountryCallingCode()));
		assertTrue("770".equals(request.getAreaCode()));
		assertTrue("319".equals(request.getExchangeNumber()));
		assertTrue("8085".equals(request.getLineNumber()));

		doc = IssueLuvVoucherRequestDocument.Factory.newInstance();
		request = doc.addNewIssueLuvVoucherRequest();
		phone = "(770)319-8085";
		vs.mapPhone(phone, request);
		assertTrue("1".equals(request.getCountryCallingCode()));
		assertTrue("770".equals(request.getAreaCode()));
		assertTrue("319".equals(request.getExchangeNumber()));
		assertTrue("8085".equals(request.getLineNumber()));
		
		doc = IssueLuvVoucherRequestDocument.Factory.newInstance();
		request = doc.addNewIssueLuvVoucherRequest();
		phone = "011-81-3-9999-9999";
		vs.mapPhone(phone, request);
		assertTrue(request.getCountryCallingCode()==null);
		assertTrue(request.getAreaCode()==null);
		assertTrue(request.getExchangeNumber()==null);
		assertTrue(request.getLineNumber()==null);
		assertTrue(phone.equals(request.getIntlPhoneNumber()));
	}
	

	/**
	 * This is used to test against SWA service the creation and cancellation of a voucher
	 * Run independently for verification
	 */
	public void issueCancelVoucherSWATest(){
		VoucherService service = new VoucherService();
		User user = null;
		try {
			user = ServiceUtilities.getAndAuthorizeUser(username,
					password, PermissionType.GET_PREPOP_DATA);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		VoucherResponse response = service.submitVoucher(user, createIssueVoucher());
		service.submitVoucher(user, createCancelVoucher(response.getOrderNumber()));
	}
	
	
	private Voucher createIssueVoucher(){
		Voucher voucher = Voucher.Factory.newInstance();
		Passenger pax = voucher.addNewPassenger();
		Address addr = pax.addNewAddresses();
		pax.setFirstname("Bill");
		pax.setLastname("Clay");
		addr.setAddress1("2675 Paces Ferry Rd");
		addr.setAddress2("Suite 240");
		addr.setCity("Atlanta");
		addr.setState("GA");
		addr.setZip("30339");
		addr.setHomePhone("770-319-8085");
		addr.setCountry("US");
		addr.setEmailAddress("test@nettracer.aero");
		
		voucher.setAgentUserName("ntagent");
		voucher.setAmount(150.00);
		voucher.setDistributionMethod("IMMEDIATE");
		voucher.setPnr("nttest");
		voucher.setStation("ATL");
		voucher.setStatus("open");
		voucher.setNtIncidentId("ATLWN123456789");
		
		return voucher;
	}
	
	private Voucher createCancelVoucher(String orderNumber){
		Voucher voucher = Voucher.Factory.newInstance();
		voucher.setStatus("cancel");
		voucher.setOrderNumber(orderNumber);
		voucher.setRemark("test cancel");
		voucher.setAgentUserName("ntadmin");
		voucher.setStation("ATL");
		return voucher;
	}
	
}
