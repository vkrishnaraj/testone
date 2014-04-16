package aero.nettracer.lfc.control;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import aero.nettracer.lfc.faces.util.FacesUtil;
import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.PhoneBean;
import aero.nettracer.lfc.model.RateBean;
import aero.nettracer.lfc.model.SegmentBean;
import aero.nettracer.lfc.model.ShippingBean;
import aero.nettracer.lfc.remote.RemoteService;
import aero.nettracer.lfc.service.ClientViewService;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

@Component("lostAndFound")
@Qualifier("lostAndFound")
@Scope("view")
public class LostAndFoundController {

	private static Logger logger = Logger.getLogger(LostAndFoundController.class);
	
	private static String URL_SWA = "southwest";
	private static String URL_AA = "american";
	private static String URL_FL = "airtran";
	private static String URL_DEMO = "demo";
	private static String URL_AVS = "avis";
	private static String URL_BGT = "budget";
	private static String URL_DL = "delta";
	private static String US_PHONE_COUNTRYCODE = "+1";
	
	private LostReportBean lostReport = new LostReportBean();
	private boolean update;

	private List<SelectItem> locations;
	private List<SelectItem> categoryList;
	private List<SelectItem> subCategories;
	private List<SelectItem> colors;
	private List<SelectItem> states;
	private List<SelectItem> countries;
	private List<CategoryBean> categories;
	private List<RateBean> rates;
	private List<SelectItem> ratesList;
	private String statePickUp;
	private String stateDropOff;
	private List<SelectItem> locationsPickUp;
	private List<SelectItem> locationsDropOff;
	private AddressBean billingAddress;
	private AddressBean shippingAddress;
	private PhoneBean shippingPhone;
	private String selectedoption;
	private List<SelectItem> ccvendors;

	private String prefAddress;

	@Autowired
	private ClientViewService clientViewService;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		lostReport = (LostReportBean) session.getAttribute("lostReport");
		String subcompany = getSubcompanyFromURL();
		if (subcompany != null) {
			if (lostReport == null || !subcompany.equals(lostReport.getSubCompany()) || onLandingPage()) {
				lostReport = createReportFromContext(session, subcompany);
			}
			if (TracingConstants.LF_LF_COMPANY_ID.equals(getCompany())) {
				populatePhoneCountry(lostReport);
			}
			shippingAddress = (AddressBean) session.getAttribute("shippingAddress");
			selectedoption = (String) session.getAttribute("selectedOption");
			rates = (List<RateBean>) session.getAttribute("rates");
	
			session.setAttribute("shippingOptions",
					(List<SelectItem>) session.getAttribute("shippingOptions")); 
	
			if (getSubCompany() != null
					&& !(getSubCompany().equals("AVS")
							|| getSubCompany().equals("BGT") || getSubCompany()
							.equals("ABG"))) {
				setSegmentLocationDesc(lostReport.getSegments());
			}
	
			if (lostReport != null && lostReport.getContact() != null) {
				shippingPhone = lostReport.getContact().getShippingPhone();
				if (shippingAddress == null
						|| (shippingAddress != null && shippingAddress
								.getAddress1() == null)) {
					shippingAddress = new AddressBean();
					if (lostReport.getContact().getPrefshipaddress() != null
							&& lostReport.getContact().getPrefshipaddress()
									.getAddress1() != null) {
						populateShippingAddress(lostReport.getContact()
								.getPrefshipaddress());
					} else {
						populateShippingAddress(lostReport.getContact()
								.getAddress());
					}
				}
				if (selectedoption == null
						&& lostReport.getShippingOption() != null) {
					selectedoption = lostReport.getShippingOption().replace(" ",
							"_");
				}
				if (shippingPhone == null) {
					shippingPhone = new PhoneBean();
					shippingPhone.setNumber(lostReport.getContact()
							.getPrimaryPhone().getNumber());
					shippingPhone.setCountry(lostReport.getContact()
							.getPrimaryPhone().getCountry());
					shippingPhone.setArea(lostReport.getContact().getPrimaryPhone()
							.getArea());
					shippingPhone.setExchange(lostReport.getContact()
							.getPrimaryPhone().getExchange());
					shippingPhone.setLine(lostReport.getContact().getPrimaryPhone()
							.getLine());
					shippingPhone.setExtension(lostReport.getContact()
							.getPrimaryPhone().getExtension());
				}
	
				session.setAttribute("shippingAddress", shippingAddress);
				session.setAttribute("shippingPhone", shippingPhone);
				session.setAttribute("selectedOption", selectedoption);
				session.setAttribute("rates", rates);
				session.setAttribute("optionselected", false);
			}
			update = (session.getAttribute("edit") != null && session.getAttribute(
					"edit").equals(true));
		}
	}

	public boolean isEditting() {
		return update;
	}

	public LostReportBean getLostReport() {
		return lostReport;
	}

	public void setLostReport(LostReportBean lostReport) {
		this.lostReport = lostReport;
	}

	public void populateShippingAddress(AddressBean address) {
		shippingAddress.setAddress1(address.getAddress1());
		shippingAddress.setAddress2(address.getAddress2());
		shippingAddress.setCity(address.getCity());
		shippingAddress.setCountry(address.getCountry());
		shippingAddress.setProvince(address.getProvince());
		shippingAddress.setPostal(address.getPostal());
		shippingAddress.setState(address.getState());
	}

	public String createReport() {
		if (validate()) {
			stripPhoneCountry(lostReport);
			long id = clientViewService.create(lostReport);
			if (id != -1) {
				lostReport.setReportId(id + "");
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(false);
				session.setAttribute("lostReport", lostReport);
				return "success?faces-redirect=true";
			}
			FacesUtil.addError("Server Communication Error.");
		}
		return null;
	}

	public String editReport() {
		if (validate()) {
			long id = clientViewService.create(lostReport);
			if (id != -1) {
				lostReport.setReportId(id + "");
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(false);
				session.setAttribute("lostReport", lostReport);
				return "editsuccess?faces-redirect=true";
			}
			FacesUtil.addError("Server Communication Error.");
		}
		return null;
	}

	public String confirmShipping() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (prefAddress != null) {
			if (prefAddress.equals("Current")) {
				lostReport.getContact().setPrefshipaddress(shippingAddress);
			} else if (prefAddress.equals("Proposed")) {
				lostReport.getContact().setPrefshipaddress(
						(AddressBean) session.getAttribute("proposedAddress"));
			} else {
				FacesUtil.addError("Please select a preferred address.");
			}
		} else {
			lostReport.getContact().setPrefshipaddress(shippingAddress);
		}
		lostReport.getContact().setShippingPhone(shippingPhone);
		if (validateShipAddress()) { // checkAddressViaFedex

			rates = clientViewService.getRatesForAddress(lostReport);
			List<SelectItem> shippingOptions = getRatesList();
			if (shippingOptions != null && shippingOptions.size() != 0) {
				long id = clientViewService.create(lostReport);
				// List<SelectItem> shippingOptions=ratesToSelectItem(rates); //
				// new ArrayList<SelectItem>();//=getOptionsOnAddress();
				// create Shipping Address for contact and mark it as the
				// preferred Shipping Address and then get Shipping options and
				// rates
				lostReport.setReportId(id + "");
				session.setAttribute("lostReport", lostReport);
				session.setAttribute("shippingAddress", lostReport.getContact()
						.getPrefshipaddress());
				session.setAttribute("shippingPhone", lostReport.getContact()
						.getShippingPhone());
				session.setAttribute("shippingOptions", shippingOptions);
				session.setAttribute("rates", rates);
				return "shippingrates?faces-redirect=true";
			} else {
				FacesUtil
						.addError("We were unable to verify that FedEx is able to ship to your location. Please confirm your address is correct. If this issue persists, please contact us at 1+(256)-999-0980");
				return null;
			}
			// FacesUtil.addError("Server Communication Error.");
		} else if (session.getAttribute("proposedAddress") != null && prefAddress==null) {
			return "shippingconfirm?faces-redirect=true";
		}

		return null;
	}

	public String confirmCCInfo() {
		// Check If shipping information is the same.
		if (validateCCInfo()) { // checkCCInf
			// Record successful authorization code. Email Customer
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			return "shipconfirmation?faces-redirect=true";
		}
		// Record invalid CC transaction
		FacesUtil
				.addError("Credit Card information is invalid. Please double check your credit card information and resubmit.");

		return null;
	}

	public String confirmPayment() {
		// Check If shipping information is the same.
		ShippingBean shipbean = clientViewService.createAndShip(lostReport);
		if (shipbean != null) {
			// Record successful authorization code. Email Customer
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.setAttribute("shipbean", shipbean);
			if (shipbean.getLost().isPaid()) {
				FacesUtil
						.addInfo("A payment has already been processed to return this item.  No further action is required at this time.");
			} else {
				lostReport.setPaid(true);
				shipbean.getLost().setPaid(true);
				return "shippingsuccess?faces-redirect=true";
			}
		} else {
			FacesUtil.addError("Server Communication Error.");
		}
		return null;
	}

	public String sendFeedback() {
		long id = clientViewService.create(lostReport);
		// List<SelectItem> shippingOptions=ratesToSelectItem(rates); // new
		// ArrayList<SelectItem>();//=getOptionsOnAddress();
		// create Shipping Address for contact and mark it as the preferred
		// Shipping Address and then get Shipping options and rates
		if (id != -1) {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			lostReport.setReportId(id + "");
			session.setAttribute("lostReport", lostReport);
			return null;
		}
		FacesUtil.addError("Server Communication Error.");
		return null;

	}

	public String shipSelectOption() {

		if (!selectedoption.equals("0")) {
			RateBean rate = getRate();
			lostReport.setShippingOption(rate.getRateType());
			lostReport.setShippingPayment(rate.getRateAmount());
			long id = clientViewService.create(lostReport); // Save the selected
															// Shipping Option
															// and Rate
			if (id != -1) {
				// create Shipping Address for contact and mark it as the
				// preferred Shipping Address and then get Shipping options and
				// rates

				lostReport.setReportId(id + "");
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(false);
				session.setAttribute("lostReport", lostReport);
				session.setAttribute("optionselected", true);
				session.setAttribute("selectedOption", selectedoption);
				return "shippingpayment?faces-redirect=true";
			}
			FacesUtil.addError("Server Communication Error.");
		}
		FacesUtil.addError("Please select a shipping option.");
		return null;
	}

	public String backToLanding() {
		return "landing?faces-redirect=true";
	}

	public String backToLogin() {
		return "login?faces-redirect=true";
	}

	public String backToShippingLogin() {
		return "shippinglogin?faces-redirect=true";
	}

	public String backToShippingConfirm() {
		return "shippingconfirm?faces-redirect=true";
	}

	public String backToShippingRates() {
		return "shippingrates?faces-redirect=true";
	}

	public String backToCCInfo() {
		return "shippingpayment?faces-redirect=true";
	}

	public String backToBagcheck() {
		return "bagunchecked?faces-redirect=true";
	}

	public String goToFormPage() {
		return "lostform?faces-redirect=true";
	}

	public String goToStatus() {
		return "status?faces-redirect=true";
	}

	private boolean validate() {
		boolean isValid = true;
		isValid = validateName();
		isValid = validateAddress() && isValid;
		if (getSubCompany() != null && getCompany() != null && TracingConstants.LF_LF_COMPANY_ID.equals(getCompany())) {
			if (getSubCompany().equals("DEM")) {
				isValid = validateLF_DEM() && isValid;
			} else {
				isValid = validateLF_LF() && isValid;
			}
		} else {
			isValid = validateAB() && isValid;
		}
		return isValid;
	}

	private boolean validateSameShipping() {
		boolean isValid = true;
		if (!shippingAddress.getAddress1().equals(
				lostReport.getContact().getPrefshipaddress().getAddress1())) {
			isValid = false;
		}
		if (shippingAddress.getAddress2() != null
				&& !shippingAddress.getAddress2().equals(
						lostReport.getContact().getPrefshipaddress()
								.getAddress2())) {
			isValid = false;
		}
		if (!shippingAddress.getCity().equals(
				lostReport.getContact().getPrefshipaddress().getCity())) {
			isValid = false;
		}
		if (!shippingAddress.getCountry().equals(
				lostReport.getContact().getPrefshipaddress().getCountry())) {
			isValid = false;
		} else {
			if (shippingAddress.getCountry().equals("US")
					&& !shippingAddress.getState().equals(
							lostReport.getContact().getPrefshipaddress()
									.getState())) {
				isValid = false;
			}
			if (shippingAddress.getCountry().equals("CA")
					&& !shippingAddress.getProvince().equals(
							lostReport.getContact().getPrefshipaddress()
									.getProvince())) {
				isValid = false;
			}
		}
		if (!shippingAddress.getCountry().equals(
				lostReport.getContact().getPrefshipaddress().getCountry())) {
			isValid = false;
		}
		if (!shippingAddress.getPostal().equals(
				lostReport.getContact().getPrefshipaddress().getPostal())) {
			isValid = false;
		}
		if (!shippingAddress.getAddress2().equals(
				lostReport.getContact().getPrefshipaddress().getAddress2())) {
			isValid = false;
		}

		return isValid;
	}

	private boolean validateName() {
		boolean isValid = true;
		if (lostReport.getContact().getFirstName() == null // VALIDATE: FIRST
															// NAME
				|| lostReport.getContact().getFirstName().trim().length() == 0) {
			FacesUtil.addError("ERROR: First Name is required.");
			isValid = false;
		}
		if (lostReport.getContact().getLastName() == null // VALIDATE: LAST NAME
				|| lostReport.getContact().getLastName().trim().length() == 0) {
			FacesUtil.addError("ERROR: Last Name is required.");
			isValid = false;
		}
		return isValid;
	}

	private boolean validateAddress() {
		boolean isValid = true;
		if (lostReport.getContact().getAddress().getAddress1() == null // VALIDATE:
																		// ADDRESS
																		// 1
				|| lostReport.getContact().getAddress().getAddress1().trim()
						.length() == 0) {
			FacesUtil.addError("ERROR: Address is required.");
			isValid = false;
		}

		if (lostReport.getContact().getAddress().getAddress1() != null
				&& lostReport.getContact().getAddress().getAddress1()
						.matches("[PO.]*\\s?B(ox)?.*\\d+")) {
			FacesUtil.addError("ERROR: PO Box Addresses are invalid.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCity() == null // VALIDATE:
																	// CITY
				|| lostReport.getContact().getAddress().getCity().trim()
						.length() == 0) {
			FacesUtil.addError("ERROR: City is required.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCountry() == null // VALIDATE:
																		// COUNTRY
				|| lostReport.getContact().getAddress().getCountry().trim()
						.length() == 0) {
			FacesUtil.addError("ERROR: Country is required.");
			isValid = false;
		} else if (lostReport.getContact().getAddress().getCountry()
				.equals("US")) {
			if (lostReport.getContact().getAddress().getState() == null // VALIDATE:
																		// STATE
					|| lostReport.getContact().getAddress().getState().trim()
							.length() == 0) {
				FacesUtil.addError("ERROR: State is required.");
				isValid = false;
			}
			if (lostReport.getContact().getAddress().getPostal() == null // VALIDATE:
																			// ZIP
																			// CODE
					|| lostReport.getContact().getAddress().getPostal().trim()
							.length() == 0) {
				FacesUtil.addError("ERROR: Zip Code is required.");
				isValid = false;
			}
		} else {
			if (lostReport.getContact().getAddress().getProvince() == null // VALIDATE:
																			// PROVINCE
					|| lostReport.getContact().getAddress().getProvince()
							.trim().length() == 0) {
				FacesUtil.addError("ERROR: Province is required.");
				isValid = false;
			}
		}
		return isValid;
	}

	private boolean validateShipAddress() {
		boolean isValid = true;
		boolean addressvalid = true;
		if (prefAddress != null && !validateSameShipping()) {
			FacesUtil
					.addError("ERROR: Shipping Address information has been modified. Resubmitting to FedEx");

		}
		if (lostReport.getContact().getShippingName() == null
				|| lostReport.getContact().getShippingName().trim().length() == 0) {
			FacesUtil.addError("ERROR: Shipping Name is required.");
			isValid = false;
		}
		if (shippingAddress.getAddress1() == null // VALIDATE: ADDRESS 1
				|| shippingAddress.getAddress1().trim().length() == 0) {
			FacesUtil.addError("ERROR: Address is required.");
			addressvalid = false;
			isValid = false;
		}
		if (shippingAddress.getAddress1() != null
				&& shippingAddress.getAddress1().matches(
						"[PO.]*\\s?B(ox)?.*\\d+")) {
			FacesUtil.addError("ERROR: PO Box Addresses are invalid.");
			addressvalid = false;
			isValid = false;
		}
		if (shippingAddress.getCity() == null // VALIDATE: CITY
				|| shippingAddress.getCity().trim().length() == 0) {
			FacesUtil.addError("ERROR: City is required.");
			addressvalid = false;
			isValid = false;
		}
		if (shippingAddress.getCountry() == null // VALIDATE: COUNTRY
				|| shippingAddress.getCountry().trim().length() == 0) {
			FacesUtil.addError("ERROR: Country is required.");
			addressvalid = false;
			isValid = false;
		} else if (shippingAddress.getCountry().equals("US")) {
			if (shippingAddress.getState() == null // VALIDATE: STATE
					|| shippingAddress.getState().trim().length() == 0) {
				FacesUtil.addError("ERROR: State is required.");
				addressvalid = false;
				isValid = false;
			}
			if (shippingAddress.getPostal() == null // VALIDATE: ZIP CODE
					|| shippingAddress.getPostal().trim().length() == 0) {
				FacesUtil.addError("ERROR: Zip Code is required.");
				addressvalid = false;
				isValid = false;
			}
		} else {
			if (shippingAddress.getProvince() == null // VALIDATE: PROVINCE
					|| shippingAddress.getProvince().trim().length() == 0) {
				FacesUtil.addError("ERROR: Province is required.");
				addressvalid = false;
				isValid = false;
			}
		}
		if (lostReport.getDeclaredValue() == 0) {
			FacesUtil
					.addError("ERROR: Please provide a declared value for your item.");
			isValid = false;
		}
		if (isValid
				&& (shippingAddress.getCountry().equals("US") || shippingAddress
						.getCountry().equals("CA"))
				&& (prefAddress == null || (prefAddress != null && prefAddress
						.length() == 0))) {
			AddressBean validAddress = clientViewService
					.validateAddressFedex(lostReport);
			if (validAddress != null
					&& !validAddress.getScore().equals(BigInteger.valueOf(100))) {
				// Set property to show list of potentially valid options???
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(false);
				session.setAttribute("proposedAddress", validAddress);
				isValid = false;
			} else if (validAddress == null
					|| (validAddress != null && !validAddress.getScore()
							.equals(BigInteger.valueOf(100)))) {
				FacesUtil
						.addError("ERROR: FedEx does not recognize the address you provided as valid. Please check your address and confirm it's accurate before submitting again.");
				addressvalid = false;
				isValid = false;
			}
		}

		if (!addressvalid) {
			FacesUtil
					.addError("The shipping address was not valid. Please confirm your address is correct.");
		}

		return isValid;
	}

	private boolean validateCCInfo() {
		boolean isValid = true;
		if (lostReport.getCc().getCcnumber() == null
				|| lostReport.getCc().getCcnumber().length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Number is required.");
			isValid = false;
		}
		if (lostReport.getCc().getCcname() == null
				|| lostReport.getCc().getCcname().length() == 0) {
			FacesUtil.addError("ERROR: Name on Credit Card is required.");
			isValid = false;
		}
		if (lostReport.getCc().getCcvendor() == null
				|| lostReport.getCc().getCcvendor().length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Vendor is required.");
			isValid = false;
		}
		if (lostReport.getCc().getCcexpirationmonth() == null
				|| lostReport.getCc().getCcexpirationmonth().length() == 0
				|| lostReport.getCc().getCcexpirationyear() == null
				|| lostReport.getCc().getCcexpirationyear().length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Expiration is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getAddress1() == null // VALIDATE:
																				// ADDRESS
																				// 1
				|| lostReport.getContact().getBillingaddress().getAddress1()
						.trim().length() == 0) {
			FacesUtil.addError("ERROR: Billing Address is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getCity() == null // VALIDATE:
																			// CITY
				|| lostReport.getContact().getBillingaddress().getCity().trim()
						.length() == 0) {
			FacesUtil.addError("ERROR: Billing City is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getCountry() == null // VALIDATE:
																				// COUNTRY
				|| lostReport.getContact().getBillingaddress().getCountry()
						.trim().length() == 0) {
			FacesUtil.addError("ERROR: Billing Country is required.");
			isValid = false;
		} else if (lostReport.getContact().getBillingaddress().getCountry()
				.equals("US")) {
			if (lostReport.getContact().getBillingaddress().getState() == null // VALIDATE:
																				// STATE
					|| lostReport.getContact().getBillingaddress().getState()
							.trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing State is required.");
				isValid = false;
			}
			if (lostReport.getContact().getBillingaddress().getPostal() == null // VALIDATE:
																				// ZIP
																				// CODE
					|| lostReport.getContact().getBillingaddress().getPostal()
							.trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing Zip Code is required.");
				isValid = false;
			}
		} else {
			if (lostReport.getContact().getBillingaddress().getProvince() == null // VALIDATE:
																					// PROVINCE
					|| lostReport.getContact().getBillingaddress()
							.getProvince().trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing Province is required.");
				isValid = false;
			}
		}

		if (isValid && !clientViewService.authorizeCC(lostReport)) {
			FacesUtil
					.addError("ERROR: Credit Card is not valid. Please reenter correct credit card information.");
			isValid = false;
		}

		return isValid;
	}
	
	private boolean validateABContact() {
		boolean isValid = true;
		boolean hasContactPhoneOrEmail = false;
		if (lostReport.getContact().getPrimaryPhone().getNumber() != null								// VALIDATE: PHONE AND EMAIL
				&& lostReport.getContact().getPrimaryPhone().getNumber().trim().length() > 0) {
			hasContactPhoneOrEmail = true;
		}
		if (lostReport.getContact().getSecondaryPhone().getNumber() != null
				&& lostReport.getContact().getSecondaryPhone().getNumber().trim().length() > 0) {
			hasContactPhoneOrEmail = true;
		}
		if (lostReport.getContact().getEmailAddress() != null
				&& lostReport.getContact().getEmailAddress().trim().length() > 0) {
			hasContactPhoneOrEmail = true;
			if (lostReport.getContact().getConfirmEmail() == null										// VALIDATE: CONFIRM EMAIL
					|| !lostReport.getContact().getConfirmEmail().equals(lostReport.getContact().getEmailAddress())) {
				FacesUtil.addError("ERROR: Email Address and Confirm Email Address must match.");
				isValid = false;
			}
		}
		if (!hasContactPhoneOrEmail) {
			FacesUtil.addError("ERROR: Contact Information must contain at least one Phone Number or Email Address.");
			isValid = false;
		}
		return isValid;
	}

	private boolean validateLFContact() {
		boolean isValid = true;
		boolean hasContactPhoneOrEmail = false;
		if (lostReport.getContact().getAddress().getCountry() != null
				&& lostReport.getContact().getAddress().getCountry()
						.equals("US")) {
			if ((lostReport.getContact().getPrimaryPhone().getArea() != null && lostReport
					.getContact().getPrimaryPhone().getArea()
					.replaceAll("[^\\d.]", "").trim().length() > 0)
					&& (lostReport.getContact().getPrimaryPhone().getExchange() != null && lostReport
							.getContact().getPrimaryPhone().getExchange()
							.replaceAll("[^\\d.]", "").trim().length() > 0)
					&& (lostReport.getContact().getPrimaryPhone().getLine() != null && lostReport
							.getContact().getPrimaryPhone().getLine()
							.replaceAll("[^\\d.]", "").trim().length() > 0)) {
				hasContactPhoneOrEmail = true;
			} else if(!((lostReport.getContact().getPrimaryPhone().getCountry() == null || lostReport.getContact().getPrimaryPhone().getCountry().trim().length()==0 || US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getPrimaryPhone().getCountry().trim())) &&
					(lostReport.getContact().getPrimaryPhone().getArea() == null || lostReport.getContact().getPrimaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getPrimaryPhone().getExchange() == null || lostReport.getContact().getPrimaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getPrimaryPhone().getLine() == null || lostReport.getContact().getPrimaryPhone().getLine().trim().length()==0))){
				isValid = false;
				FacesUtil.addError("ERROR: Primary US Phone Number Must contain a Area-Exchange-Line number.");
			}
		} else {
			if ((lostReport.getContact().getPrimaryPhone().getCountry() != null && lostReport
					.getContact().getPrimaryPhone().getCountry()
					.replaceAll("[^\\d.]", "").trim().length() > 0 && 
					!US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getPrimaryPhone().getCountry()))
					&& (lostReport.getContact().getPrimaryPhone().getLine() != null && lostReport
							.getContact().getPrimaryPhone().getLine()
							.replaceAll("[^\\d.]", "").trim().length() > 0)) {
				hasContactPhoneOrEmail = true;
			} else if(!((lostReport.getContact().getPrimaryPhone().getCountry() == null || lostReport.getContact().getPrimaryPhone().getCountry().trim().length()==0 || US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getPrimaryPhone().getCountry().trim())) &&
					(lostReport.getContact().getPrimaryPhone().getArea() == null || lostReport.getContact().getPrimaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getPrimaryPhone().getExchange() == null || lostReport.getContact().getPrimaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getPrimaryPhone().getLine() == null || lostReport.getContact().getPrimaryPhone().getLine().trim().length()==0))){
				isValid = false;
				FacesUtil.addError("ERROR: Primary International Phone Numbers Must contain a Country-Line number.");
			}
		}
		if (lostReport.getContact().getAddress().getCountry() != null
				&& lostReport.getContact().getAddress().getCountry()
						.equals("US")) {
			if ((lostReport.getContact().getSecondaryPhone().getArea() != null && lostReport
					.getContact().getSecondaryPhone().getArea()
					.replaceAll("[^\\d.]", "").trim().length() > 0)
					&& (lostReport.getContact().getSecondaryPhone()
							.getExchange() != null && lostReport.getContact()
							.getSecondaryPhone().getExchange()
							.replaceAll("[^\\d.]", "").trim().length() > 0)
					&& (lostReport.getContact().getSecondaryPhone().getLine() != null && lostReport
							.getContact().getSecondaryPhone().getLine()
							.replaceAll("[^\\d.]", "").trim().length() > 0)) {
				hasContactPhoneOrEmail = true;
			} else if(!((lostReport.getContact().getSecondaryPhone().getCountry() == null || lostReport.getContact().getSecondaryPhone().getCountry().trim().length()==0 || US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getSecondaryPhone().getCountry().trim())) &&
						(lostReport.getContact().getSecondaryPhone().getArea() == null || lostReport.getContact().getSecondaryPhone().getArea().trim().length()==0) && 
						(lostReport.getContact().getSecondaryPhone().getExchange() == null || lostReport.getContact().getSecondaryPhone().getExchange().trim().length()==0) &&
						(lostReport.getContact().getSecondaryPhone().getLine() == null || lostReport.getContact().getSecondaryPhone().getLine().trim().length()==0))){
					isValid = false;
				FacesUtil.addError("ERROR: Secondary US Phone Number Must contain a Area-Exchange-Line number.");
				
			}
		} else {
			if ((lostReport.getContact().getSecondaryPhone().getCountry() != null && lostReport
					.getContact().getSecondaryPhone().getCountry()
					.replaceAll("[^\\d.]", "").trim().length() > 0 && 
					!US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getSecondaryPhone().getCountry()))
					&& (lostReport.getContact().getSecondaryPhone().getLine() != null && lostReport
							.getContact().getSecondaryPhone().getLine()
							.replaceAll("[^\\d.]", "").trim().length() > 0)) {
				hasContactPhoneOrEmail = true;
			} else if(!((lostReport.getContact().getSecondaryPhone().getCountry() == null || lostReport.getContact().getSecondaryPhone().getCountry().trim().length()==0 || US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getSecondaryPhone().getCountry().trim())) &&
					(lostReport.getContact().getSecondaryPhone().getArea() == null || lostReport.getContact().getSecondaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getSecondaryPhone().getExchange() == null || lostReport.getContact().getSecondaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getSecondaryPhone().getLine() == null || lostReport.getContact().getSecondaryPhone().getLine().trim().length()==0))){
				isValid = false;
				FacesUtil.addError("ERROR: Secondary International Phone Number Must contain a Country-Line number.");
			}
		}

		if (lostReport.getContact().getEmailAddress() != null
				&& lostReport.getContact().getEmailAddress().trim().length() > 0) {
			hasContactPhoneOrEmail = true;
			if (lostReport.getContact().getConfirmEmail() == null // VALIDATE:
																	// CONFIRM
																	// EMAIL
					|| !lostReport.getContact().getConfirmEmail()
							.equals(lostReport.getContact().getEmailAddress())) {
				FacesUtil
						.addError("ERROR: Email Address and Confirm Email Address must match.");
				isValid = false;
			}
		}
		if (!hasContactPhoneOrEmail) {
			FacesUtil
					.addError("ERROR: Contact Information must contain at least one valid Phone Number or Email Address.");
			isValid = false;
		}
		return isValid;
	}

	private boolean validateSegments() {
		boolean isValid = true;
		if (lostReport.getSegments().size() == 0) { // VALIDATE: SEGMENT(S)
													// PROVIDED
			FacesUtil
					.addError("ERROR: At least one segment must be provided in the \"About Your Trip\" section.");
			isValid = false;
		} else {
			for (int i = 0; i < lostReport.getSegments().size(); i++) {
				SegmentBean seg = lostReport.getSegments().get(i);
				int segNum = i + 1;
				if (seg.getArrivalLocation() == 0) { // VALIDATE: SEGMENT -
														// ARRIVAL LOCATION
					FacesUtil
							.addError("ERROR: Arrival Airport required for Segment #"
									+ segNum
									+ " in the \"About Your Trip\" section.");
					isValid = false;
				}
				if (seg.getDepartureLocation() == 0) { // VALIDATE: SEGMENT -
														// DEPARTURE LOCATION
					FacesUtil
							.addError("ERROR: Departure Airport required for Segment #"
									+ segNum
									+ " in the \"About Your Trip\" section.");
					isValid = false;
				}
			}
		}
		return isValid;
	}

	private boolean validateAB() {
		boolean isValid = true;
		isValid = validateABContact();
		if (lostReport.getDateLost() == null) { // VALIDATE: DATE LOST
			FacesUtil.addError("ERROR: Rental Date is required.");
			isValid = false;
		}
		if (lostReport.getPickUpLocation() < 1) { // VALIDATE: PICK UP
			FacesUtil.addError("ERROR: Rental Location is required.");
			isValid = false;
		}
		if (lostReport.getDropOffLocation() < 1) { // VALIDATE: DROP OFF
			FacesUtil.addError("ERROR: Drop Off Rental Location is required.");
			isValid = false;
		}
		return isValid;
	}

	private boolean validateLF_LF() {
		boolean isValid = true;
		isValid = validateSegments();
		isValid = validateLFContact() && isValid;
		if (lostReport.getDateLost() == null) { // VALIDATE: DATE LOST
			FacesUtil.addError("ERROR: Date Lost is required.");
			isValid = false;
		}
		if (lostReport.getItemColor() != null
				&& lostReport.getItemColor().trim().length() == 0) { // VALIDATE:
																		// ITEM
																		// COLOR
			FacesUtil.addError("ERROR: Item Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCaseColor() != null
				&& lostReport.getItemCaseColor().trim().length() == 0) { // VALIDATE:
																			// ITEM
																			// CASE
																			// COLOR
			FacesUtil.addError("ERROR: Item Case Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCategory() < 1) { // VALIDATE: CATEGORY
			FacesUtil.addError("ERROR: Item Category is required.");
			isValid = false;
		} else if (lostReport.getItemSubCategory() == 0) { // VALIDATE:
															// SUBCATEGORY
			FacesUtil
					.addError("ERROR: Item Subcategory is required for Category \""
							+ getCategoryDesc() + "\".");
			isValid = false;
		}
		if (lostReport.getItemCategory() == 7) { // VALIDATE: LOST PHONE NUM
			if (lostReport.getContact().getAddress().getCountry() != null
					&& lostReport.getContact().getAddress().getCountry()
							.equals("US")) {
				if (!((lostReport.getLostPhone().getArea() != null && lostReport
						.getLostPhone().getArea().replaceAll("[^\\d.]", "")
						.trim().length() > 0)
						&& (lostReport.getLostPhone().getExchange() != null && lostReport
								.getLostPhone().getExchange()
								.replaceAll("[^\\d.]", "").trim().length() > 0) && (lostReport
						.getLostPhone().getLine() != null && lostReport
						.getLostPhone().getLine().replaceAll("[^\\d.]", "")
						.trim().length() > 0))) {
					FacesUtil
							.addError("ERROR: Valid Area-Exchange-Line number of lost phone is required for Category \"Cellphone\".");
					isValid = false;
				}
			} else {
				if (!((lostReport.getLostPhone().getCountry() != null && lostReport
						.getLostPhone().getCountry().replaceAll("[^\\d.]", "")
						.trim().length() > 0 && !US_PHONE_COUNTRYCODE.equals(lostReport
						.getLostPhone().getCountry().trim())) && (lostReport.getLostPhone()
						.getLine() != null && lostReport.getLostPhone()
						.getLine().replaceAll("[^\\d.]", "").trim().length() > 0))) {

					FacesUtil
							.addError("ERROR: Valid Country-Line number of lost phone is required for Category \"Cellphone\".");
					isValid = false;
				}
			}

		}
		return isValid;
	}

	private boolean validateLF_DEM() {
		boolean isValid = true;
		/** TODO: Add Custom Validation for the Demo site. */
		return isValid;
	}

	public List<SelectItem> getLocations() {
		if (locations == null || locations.isEmpty()) {
			if (getSubCompany() != null) {
				locations = clientViewService.getLocations(getSubCompany());
			} else {
				locations = new ArrayList<SelectItem>();
			}
		}
		return locations;
	}

	public void setLocations(List<SelectItem> locations) {
		this.locations = locations;
	}

	public List<SelectItem> getLocationsPickUp() {
		if (getSubCompany() != null) {
			locationsPickUp = clientViewService.getLocationsByState(
					getSubCompany(), getStatePickUp());
		}
		return locationsPickUp;
	}

	public void setLocationsPickUp(List<SelectItem> locationsPickUp) {
		this.locationsPickUp = locationsPickUp;
	}

	public List<SelectItem> getLocationsDropOff() {
		if (getSubCompany() != null) {
			locationsDropOff = clientViewService.getLocationsByState(
					getSubCompany(), getStateDropOff());
		}
		return locationsDropOff;
	}

	public void setLocationsDropOff(List<SelectItem> locationsDropOff) {
		this.locationsDropOff = locationsDropOff;
	}

	private void populateCategories() {
		if (categories == null && getCompany() != null) {
			categories = clientViewService.getCategories(getCompany());
		}
	}

	public List<SelectItem> getCategoryList() {
		if (categoryList == null || categories == null) {
			categoryList = new ArrayList<SelectItem>();
			populateCategories();
			if (categories != null) {
				for (CategoryBean cat : categories) {
					if (cat != null) {
						categoryList.add(new SelectItem(cat.getId() + "", cat
								.getDescription()));
					}
				}
			}
		}
		return categoryList;
	}

	public void setCategoryList(List<SelectItem> categoryList) {
		this.categoryList = categoryList;
	}

	public List<SelectItem> getCcvendors() {
		if (ccvendors == null) {
			List<SelectItem> vendors = new ArrayList<SelectItem>();
			vendors.add(new SelectItem("Visa", "Visa"));
			vendors.add(new SelectItem("AmEx", "American Express"));
			vendors.add(new SelectItem("Disc", "Discover"));
			vendors.add(new SelectItem("MC", "Mastercard"));
			ccvendors = vendors;
		}
		return ccvendors;
	}

	public void setCcvendors(List<SelectItem> ccvendors) {
		this.ccvendors = ccvendors;
	}

	public List<SelectItem> getSubCategories() {
		populateCategories();
		subCategories = new ArrayList<SelectItem>();
		if (categories != null) {
			for (CategoryBean cat : categories) {
				if (cat != null && cat.getId() == lostReport.getItemCategory()
						&& cat.getSubcategories() != null) {
					for (KeyValueBean key : cat.getSubcategories()) {
						if (key != null) {
							subCategories.add(new SelectItem(key.getKey(), key
									.getValue()));
						}
					}
				}
			}
		}
		if (subCategories.size() > 0) {
			subCategories.add(0, new SelectItem("0", "Please Select"));
		} else {
			subCategories.add(new SelectItem("-1", "N/A"));
		}
		return subCategories;
	}

	public void setSubCategories(List<SelectItem> subCategories) {
		this.subCategories = subCategories;
	}

	public List<SelectItem> getColors() {
		if (colors == null) {
			colors = clientViewService.getColors();
		}
		return colors;
	}

	public void setColors(List<SelectItem> colors) {
		this.colors = colors;
	}

	public List<SelectItem> getStates() {
		if (states == null) {
			states = clientViewService.getStates();
		}
		return states;
	}

	public void setStates(List<SelectItem> states) {
		this.states = states;
	}

	public List<SelectItem> getCountries() {
		if (countries == null) {
			countries = clientViewService.getCountries();
		}
		return countries;
	}

	public void setCountries(List<SelectItem> countries) {
		this.countries = countries;
	}

	public ClientViewService getClientViewService() {
		return clientViewService;
	}

	public void setClientViewService(ClientViewService clientViewService) {
		this.clientViewService = clientViewService;
	}

	private String getCompany() {
		if (lostReport != null) {
			return lostReport.getCompany();
		}
		return null;
	}

	private String getSubCompany() {
		if (lostReport != null) {
			return lostReport.getSubCompany();
		}
		return null;
	}

	public String getPickUpLocationDesc() {
		int testIt = lostReport.getPickUpLocation();
		return getLocationDesc(testIt);
	}

	public String getDropOffLocationDesc() {
		int testIt = lostReport.getDropOffLocation();
		return getLocationDesc(testIt);
	}

	public String getDepartureLocationDesc(int i) {
		int testIt = lostReport.getSegments().get(i).getDepartureLocation();
		return getLocationDesc(testIt);
	}

	public String getArrivalLocationDesc(int i) {
		int testIt = lostReport.getSegments().get(i).getArrivalLocation();
		return getLocationDesc(testIt);
	}

	private void setSegmentLocationDesc(List<SegmentBean> segs) {
		if (segs != null && segs.size() > 0) {
			for (SegmentBean seg : segs) {
				seg.setArrivalLocationDesc(getLocationDesc(seg
						.getArrivalLocation()));
				seg.setDepartureLocationDesc(getLocationDesc(seg
						.getDepartureLocation()));
			}
		}
	}

	private String getLocationDesc(int testIt) {
		for (SelectItem key : getLocations()) {
			if (((String) key.getValue()).equals(testIt + "")) {
				return key.getLabel();
			}
		}
		return testIt + "";
	}

	public String getCategoryDesc() {
		long testIt = lostReport.getItemCategory();
		if (testIt == 0) {
			return "Please Select";
		}
		populateCategories();
		for (CategoryBean key : categories) {
			if (key.getId() == testIt) {
				return key.getDescription();
			}
		}
		return testIt + "";
	}

	public String getSubCategoryDesc() {
		long testCat = lostReport.getItemCategory();
		long testIt = lostReport.getItemSubCategory();
		if (testIt == -1) {
			return "N/A";
		} else if (testIt == 0) {
			return "Please Select";
		} else {
			populateCategories();
			if (categories != null) {
				for (CategoryBean cat : categories) {
					if (cat.getId() == testCat) {
						if (cat.getSubcategories() != null) {
							for (KeyValueBean key : cat.getSubcategories()) {
								if (key.getKey().equals(testIt + "")) {
									return key.getValue();
								}
							}
						} else {
							return "None Available";
						}
					}
				}
			}
		}
		return testIt + "";
	}

	public String getColorDesc() {
		String testIt = lostReport.getItemColor();
		for (SelectItem key : getColors()) {
			if (((String) key.getValue()).equals(testIt)) {
				return key.getLabel();
			}
		}
		return testIt;
	}

	public String getCaseColorDesc() {
		String testIt = lostReport.getItemCaseColor();
		for (SelectItem key : getColors()) {
			if (((String) key.getValue()).equals(testIt)) {
				return key.getLabel();
			}
		}
		return testIt;
	}

	public String getCountryDesc() {
		String testIt = lostReport.getContact().getAddress().getCountry();
		for (SelectItem key : getCountries()) {
			if (((String) key.getValue()).equals(testIt)) {
				return key.getLabel();
			}
		}
		return testIt;
	}

	public RateBean getRate() {
		String ratekey = selectedoption;
		if (ratekey == null || (ratekey != null && ratekey.length() == 0)) {
			return null;
		}
		if (rates != null)
			for (RateBean key : rates) {
				if (key.getRateKey().equals(ratekey)) {
					return key;
				}
			}
		return null;
	}

	public String getStatePickUp() {
		return statePickUp;
	}

	public void setStatePickUp(String statePickUp) {
		this.statePickUp = statePickUp;
	}

	public String getPrefAddress() {
		return prefAddress;
	}

	public void setPrefAddress(String prefAddress) {
		this.prefAddress = prefAddress;
	}

	public String getStateDropOff() {
		return stateDropOff;
	}

	public void setSelectedoption(String selectedoption) {
		this.selectedoption = selectedoption;
	}

	public String getSelectedoption() {
		return selectedoption;
	}

	public AddressBean getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressBean billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddressBean getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressBean shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public PhoneBean getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(PhoneBean shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public void setStateDropOff(String stateDropOff) {
		this.stateDropOff = stateDropOff;
	}

	public String addSegment() {
		SegmentBean segment = new SegmentBean();
		lostReport.getSegments().add(segment);
		return null;
	}

	public String deleteSegment() {
		int size = lostReport.getSegments().size();
		if (size > 1) {
			lostReport.getSegments().remove(size - 1);
		}
		return null;
	}

	public List<RateBean> getRates() {
		return rates;
	}

	public void setRates(List<RateBean> rates) {
		this.rates = rates;
	}

	public List<SelectItem> getRatesList() {
		ratesList = new ArrayList<SelectItem>();
		if (rates != null) {
			for (RateBean rate : rates) {
				if (rate != null) {
					ratesList.add(new SelectItem(rate.getRateKey() + "", rate
							.getRateType()
							+ " (Est Delivery Date: "
							+ rate.getEstDeliveryDate()
							+ ") - "
							+ rate.getRateAmount()));
				}
			}
		}
		return ratesList;
	}

	public void setRatesList(List<SelectItem> ratesList) {
		this.ratesList = ratesList;
	}
	
	private LostReportBean createReportFromContext(HttpSession session, String subcompany) {
		LostReportBean bean = new LostReportBean();
		if (!onLandingPage()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			try {
				fc.getExternalContext().redirect("landing.do");
			} catch (IOException e) {
			}
		} else {
			session.setAttribute("edit", false);
			bean.setSubCompany(subcompany);
			String company = TracingConstants.LF_LF_COMPANY_ID;
			if (TracingConstants.LF_AVIS_COMPANY_ID.equals(subcompany) || TracingConstants.LF_BUDGET_COMPANY_ID.equals(subcompany)) {
				company = TracingConstants.LF_AB_COMPANY_ID;
			}
			bean.setCompany(company);
			if (RemoteService.getLists(bean.getSubCompany())) {
				session.setAttribute("lostReport", bean);
			} else {
				FacesUtil.addError("Server Communication Error.");
			}
		}
		return bean;
	}
	
	private boolean onLandingPage() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String path = request.getServletPath();
		return !(path == null || !"/landing".equals(path.substring(path.lastIndexOf("/"), path.lastIndexOf(".do"))));
	}
	
	private String getSubcompanyFromURL() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String path = request.getServletPath();
		String companyFromPath = path.substring(1, path.lastIndexOf("/"));
		if (URL_SWA.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_SWA_COMPANY_ID;
		} else if (URL_FL.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_SWA_COMPANY_ID;
		} else if (URL_AVS.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_AVIS_COMPANY_ID;
		} else if (URL_BGT.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_BUDGET_COMPANY_ID;
		} else if (URL_DEMO.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_DEMO_COMPANY_ID;
		} else if (URL_AA.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_AA_COMPANY_ID;
		} else if (URL_DL.equalsIgnoreCase(companyFromPath)) {
			return TracingConstants.LF_DL_COMPANY_ID;
		}
		return null;
	}
	
	private void populatePhoneCountry(LostReportBean lostReport) {
		if (lostReport.getReportId() == null || lostReport.getReportId().trim().length() == 0) {
			if((lostReport.getContact().getSecondaryPhone().getCountry() == null || lostReport.getContact().getSecondaryPhone().getCountry().trim().length()==0) &&
					(lostReport.getContact().getSecondaryPhone().getArea() == null || lostReport.getContact().getSecondaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getSecondaryPhone().getExchange() == null || lostReport.getContact().getSecondaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getSecondaryPhone().getLine() == null || lostReport.getContact().getSecondaryPhone().getLine().trim().length()==0)) {
				lostReport.getContact().getSecondaryPhone().setCountry(US_PHONE_COUNTRYCODE);
			}
			if((lostReport.getContact().getPrimaryPhone().getCountry() == null || lostReport.getContact().getPrimaryPhone().getCountry().trim().length()==0) &&
					(lostReport.getContact().getPrimaryPhone().getArea() == null || lostReport.getContact().getPrimaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getPrimaryPhone().getExchange() == null || lostReport.getContact().getPrimaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getPrimaryPhone().getLine() == null || lostReport.getContact().getPrimaryPhone().getLine().trim().length()==0)) {
				lostReport.getContact().getPrimaryPhone().setCountry(US_PHONE_COUNTRYCODE);
			}
			if((lostReport.getLostPhone().getCountry() == null || lostReport.getLostPhone().getCountry().trim().length()==0) &&
					(lostReport.getLostPhone().getArea() == null || lostReport.getLostPhone().getArea().trim().length()==0) && 
					(lostReport.getLostPhone().getExchange() == null || lostReport.getLostPhone().getExchange().trim().length()==0) &&
					(lostReport.getLostPhone().getLine() == null || lostReport.getLostPhone().getLine().trim().length()==0)) {
				lostReport.getLostPhone().setCountry(US_PHONE_COUNTRYCODE);
			}
		}
	}
	
	private void stripPhoneCountry(LostReportBean lostReport) {
		if (lostReport.getReportId() == null || lostReport.getReportId().trim().length() == 0) {
			if(US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getSecondaryPhone().getCountry().trim()) &&
					(lostReport.getContact().getSecondaryPhone().getArea() == null || lostReport.getContact().getSecondaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getSecondaryPhone().getExchange() == null || lostReport.getContact().getSecondaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getSecondaryPhone().getLine() == null || lostReport.getContact().getSecondaryPhone().getLine().trim().length()==0)) {
				lostReport.getContact().getSecondaryPhone().setCountry("");
			}
			if(US_PHONE_COUNTRYCODE.equals(lostReport.getContact().getPrimaryPhone().getCountry().trim()) &&
					(lostReport.getContact().getPrimaryPhone().getArea() == null || lostReport.getContact().getPrimaryPhone().getArea().trim().length()==0) && 
					(lostReport.getContact().getPrimaryPhone().getExchange() == null || lostReport.getContact().getPrimaryPhone().getExchange().trim().length()==0) &&
					(lostReport.getContact().getPrimaryPhone().getLine() == null || lostReport.getContact().getPrimaryPhone().getLine().trim().length()==0)) {
				lostReport.getContact().getPrimaryPhone().setCountry("");
			}
			if(US_PHONE_COUNTRYCODE.equals(lostReport.getLostPhone().getCountry().trim()) &&
					(lostReport.getLostPhone().getArea() == null || lostReport.getLostPhone().getArea().trim().length()==0) && 
					(lostReport.getLostPhone().getExchange() == null || lostReport.getLostPhone().getExchange().trim().length()==0) &&
					(lostReport.getLostPhone().getLine() == null || lostReport.getLostPhone().getLine().trim().length()==0)) {
				lostReport.getLostPhone().setCountry("");
			}
		}
	}

}
