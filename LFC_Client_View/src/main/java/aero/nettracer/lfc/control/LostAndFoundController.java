package aero.nettracer.lfc.control;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import aero.nettracer.lfc.service.ClientViewService;

@Component("lostAndFound")
@Qualifier("lostAndFound")
@Scope("view")
public class LostAndFoundController {

	private static Logger logger = Logger
			.getLogger(LostAndFoundController.class);
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
	private String ccnumber;
	private String ccname;
	private String ccvendor;
	private String ccexpirationmonth;
	private String ccexpirationyear;
	private String ccsecurity;
	private List<SelectItem> ccvendors;
	
	private String prefAddress;
	
 
	@Autowired
	private ClientViewService clientViewService;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
		lostReport = (LostReportBean) session.getAttribute("lostReport");
		shippingAddress=(AddressBean)session.getAttribute("shippingAddress");
		shippingPhone=lostReport.getContact().getShippingPhone();
		rates=(List<RateBean>)session.getAttribute("rates");
		
		if (getSubCompany() != null && getSubCompany().equals("SWA") ) {
			setSegmentLocationDesc(lostReport.getSegments());
		}
		
		if(lostReport!=null && lostReport.getContact()!=null && lostReport.getContact().getPrefshipaddress()!=null && lostReport.getContact().getPrefshipaddress().getAddress1()!=null){
			session.setAttribute("shippingOptions", (List<SelectItem>)session.getAttribute("shippingOptions")); //To Make method to get FedEx options based on PrefShipAddress
		}
		
		if(lostReport!=null && lostReport.getContact()!=null){
			if(shippingAddress==null || (shippingAddress!=null && shippingAddress.getAddress1()==null)){
				shippingAddress=new AddressBean();
				if(lostReport.getContact().getPrefshipaddress().getAddress1()!=null){
					populateShippingAddress(lostReport.getContact().getPrefshipaddress());
				} else {
					populateShippingAddress(lostReport.getContact().getAddress());
				}
			}
			if(shippingPhone==null){
				shippingPhone=new PhoneBean();
				shippingPhone.setNumber(lostReport.getContact().getPrimaryPhone().getNumber());
				shippingPhone.setExtension(lostReport.getContact().getPrimaryPhone().getExtension());
			}
			
			session.setAttribute("shippingAddress", shippingAddress);
			session.setAttribute("shippingPhone", shippingPhone);
			session.setAttribute("rates", rates);
			session.setAttribute("optionselected", false);
		}
		update = (session.getAttribute("edit")!=null && session.getAttribute("edit").equals(true));
		if (getCompany() == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
            	fc.getExternalContext().redirect("landing.do");
            } catch (IOException e) {
            }
		}
	}
	
	public boolean isEditting()
	{
		return update;
	}

	public LostReportBean getLostReport() {
		return lostReport;
	}

	public void setLostReport(LostReportBean lostReport) {
		this.lostReport = lostReport;
	}
	public void populateShippingAddress(AddressBean address){
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
			long id = clientViewService.create(lostReport);
			if (id != -1) {
				lostReport.setReportId(id + "");
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
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
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
				session.setAttribute("lostReport", lostReport);
				return "editsuccess?faces-redirect=true";
			}
			FacesUtil.addError("Server Communication Error.");
		}
		return null;
	}
	
	public String confirmShipping() {

		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
		if(prefAddress!=null){
			if(prefAddress.equals("Current")){
				lostReport.getContact().setPrefshipaddress(shippingAddress);
			} else if (prefAddress.equals("Proposed")){
				lostReport.getContact().setPrefshipaddress((AddressBean)session.getAttribute("proposedAddress"));
			}
		} else {
			lostReport.getContact().setPrefshipaddress(shippingAddress);
		}
		lostReport.getContact().setShippingPhone(shippingPhone);
		if (validateShipAddress()) { //checkAddressViaFedex
			
			rates=clientViewService.getRatesForAddress(lostReport);
			List<SelectItem> shippingOptions=getRatesList();
			if (shippingOptions != null) {
				long id = clientViewService.create(lostReport);
				//List<SelectItem> shippingOptions=ratesToSelectItem(rates); // new ArrayList<SelectItem>();//=getOptionsOnAddress();
				//create Shipping Address for contact and mark it as the preferred Shipping Address and then get Shipping options and rates
				lostReport.setReportId(id + "");
				session.setAttribute("lostReport", lostReport);
				session.setAttribute("shippingAddress", lostReport.getContact().getPrefshipaddress());
				session.setAttribute("shippingPhone", lostReport.getContact().getShippingPhone());
				session.setAttribute("shippingOptions", shippingOptions);
				session.setAttribute("rates", rates);
				return "shippingrates?faces-redirect=true";
			} 
			FacesUtil.addError("Server Communication Error.");
		} else if(session.getAttribute("proposedAddress")!=null){
			return "shippingconfirm?faces-redirect=true";
		}
		FacesUtil.addError("Shipping address was not valid. Please confirm.");
		return null;
	}
	
	public String confirmPayment() {
		//Check If shipping information is the same.
		if(validateSameShipping()){
			if (validateCCInfo()) { //checkCCInf
				lostReport.setStatus("Ready for Shipping"); //Is this right?
				ShippingBean shipbean=clientViewService.createAndShip(lostReport);
				if (clientViewService.sendConfirmationEmail(lostReport) && shipbean!=null) {
					//Record successful authorization code. Email Customer
					HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
					session.setAttribute("shipbean", shipbean);
					return "shippingsuccess?faces-redirect=true";
				}
				FacesUtil.addError("Server Communication Error.");
			}
		//Record invalid CC transaction
			FacesUtil.addError("Credit Card information is invalid. Please enter double check your credit card information.");
		} else {
			FacesUtil.addError("Shipping Information has been changed. Please resubmit Shipping Information to determine proper shipping options and pricing.");
			return "shippingconfirm?faces-redirect=true";
		}
		return null;
	}
	
	public String shipSelectOption() {
		
		if (!selectedoption.equals("0")) {
			RateBean rate=getRate();
			lostReport.setShippingOption(rate.getRateType());
			lostReport.setShippingPayment(rate.getRateAmount());
			long id = clientViewService.create(lostReport); //Save the selected Shipping Option and Rate
			if (id != -1) {
				//create Shipping Address for contact and mark it as the preferred Shipping Address and then get Shipping options and rates
				
				lostReport.setReportId(id + "");
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
				session.setAttribute("lostReport", lostReport);
				session.setAttribute("optionselected", true);
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
	
	public String backToShippingPayment() {
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
		isValid = validateContact() && isValid;
		if (getSubCompany() != null && getSubCompany().equals("SWA") ) {
			isValid = validateLF_SWA() && isValid;
		}else if (getSubCompany().equals("DEM")){
			isValid = validateLF_DEM() && isValid;
		} else {
			isValid = validateAB() && isValid;
		}
		return isValid;
	}
	
	private boolean validateSameShipping() {
		boolean isValid=true;
		if(!shippingAddress.getAddress1().equals(lostReport.getContact().getPrefshipaddress().getAddress1())){
			isValid=false;
		}
		if(!shippingAddress.getAddress2().equals(lostReport.getContact().getPrefshipaddress().getAddress2())){
			isValid=false;
		}
		if(!shippingAddress.getCity().equals(lostReport.getContact().getPrefshipaddress().getCity())){
			isValid=false;
		}
		if(!shippingAddress.getState().equals(lostReport.getContact().getPrefshipaddress().getState())){
			isValid=false;
		}
		if(!shippingAddress.getProvince().equals(lostReport.getContact().getPrefshipaddress().getProvince())){
			isValid=false;
		}
		if(!shippingAddress.getCountry().equals(lostReport.getContact().getPrefshipaddress().getCountry())){
			isValid=false;
		}
		if(!shippingAddress.getPostal().equals(lostReport.getContact().getPrefshipaddress().getPostal())){
			isValid=false;
		}
		if(!shippingAddress.getAddress2().equals(lostReport.getContact().getPrefshipaddress().getAddress2())){
			isValid=false;
		}
		
		return isValid;
	}
	
	
	private boolean validateFedex() {
		boolean isValid = true;
		if (lostReport.getContact().getFirstName() == null												// VALIDATE: FIRST NAME
				|| lostReport.getContact().getFirstName().trim().length() == 0) {
			FacesUtil.addError("ERROR: First Name is required.");
			isValid = false;
		}
		if (lostReport.getContact().getLastName() == null												// VALIDATE: LAST NAME
				|| lostReport.getContact().getLastName().trim().length() == 0) {
			FacesUtil.addError("ERROR: Last Name is required.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateName() {
		boolean isValid = true;
		if (lostReport.getContact().getFirstName() == null												// VALIDATE: FIRST NAME
				|| lostReport.getContact().getFirstName().trim().length() == 0) {
			FacesUtil.addError("ERROR: First Name is required.");
			isValid = false;
		}
		if (lostReport.getContact().getLastName() == null												// VALIDATE: LAST NAME
				|| lostReport.getContact().getLastName().trim().length() == 0) {
			FacesUtil.addError("ERROR: Last Name is required.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateAddress() {
		boolean isValid = true;
		if (lostReport.getContact().getAddress().getAddress1() == null									// VALIDATE: ADDRESS 1
				|| lostReport.getContact().getAddress().getAddress1().trim().length() == 0) {
			FacesUtil.addError("ERROR: Address is required.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCity() == null										// VALIDATE: CITY
				|| lostReport.getContact().getAddress().getCity().trim().length() == 0) {
			FacesUtil.addError("ERROR: City is required.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCountry() == null									// VALIDATE: COUNTRY
				|| lostReport.getContact().getAddress().getCountry().trim().length() == 0) {
			FacesUtil.addError("ERROR: Country is required.");
			isValid = false;
		} else if (lostReport.getContact().getAddress().getCountry().equals("US")){
			if (lostReport.getContact().getAddress().getState() == null									// VALIDATE: STATE
					|| lostReport.getContact().getAddress().getState().trim().length() == 0) {
				FacesUtil.addError("ERROR: State is required.");
				isValid = false;
			}
			if (lostReport.getContact().getAddress().getPostal() == null								// VALIDATE: ZIP CODE
					|| lostReport.getContact().getAddress().getPostal().trim().length() == 0) {
				FacesUtil.addError("ERROR: Zip Code is required.");
				isValid = false;
			}			
		} else {
			if (lostReport.getContact().getAddress().getProvince() == null								// VALIDATE: PROVINCE
					|| lostReport.getContact().getAddress().getProvince().trim().length() == 0) {
				FacesUtil.addError("ERROR: Province is required.");
				isValid = false;
			}			
		}
		return isValid;
	}
	
	private boolean validateShipAddress() {
		boolean isValid = true;
		if(prefAddress!=null && !validateSameShipping()){
			FacesUtil.addError("ERROR: Shipping Address information has been modified. Resubmitting to Fedex");
			
		}
		if (shippingAddress.getAddress1() == null									// VALIDATE: ADDRESS 1
				|| shippingAddress.getAddress1().trim().length() == 0) {
			FacesUtil.addError("ERROR: Address is required.");
			isValid = false;
		}
		if (shippingAddress.getCity() == null										// VALIDATE: CITY
				|| shippingAddress.getCity().trim().length() == 0) {
			FacesUtil.addError("ERROR: City is required.");
			isValid = false;
		}
		if (shippingAddress.getCountry() == null									// VALIDATE: COUNTRY
				|| shippingAddress.getCountry().trim().length() == 0) {
			FacesUtil.addError("ERROR: Country is required.");
			isValid = false;
		} else if (shippingAddress.getCountry().equals("US")){
			if (shippingAddress.getState() == null									// VALIDATE: STATE
					|| shippingAddress.getState().trim().length() == 0) {
				FacesUtil.addError("ERROR: State is required.");
				isValid = false;
			}
			if (shippingAddress.getPostal() == null								// VALIDATE: ZIP CODE
					|| shippingAddress.getPostal().trim().length() == 0) {
				FacesUtil.addError("ERROR: Zip Code is required.");
				isValid = false;
			}			
		} else {
			if (shippingAddress.getProvince() == null								// VALIDATE: PROVINCE
					|| shippingAddress.getProvince().trim().length() == 0) {
				FacesUtil.addError("ERROR: Province is required.");
				isValid = false;
			}			
		}
		if(isValid && (prefAddress==null || (prefAddress!=null && prefAddress.length()==0))){
			AddressBean validAddress=clientViewService.validateAddressFedex(lostReport);
			if(validAddress!=null && !validAddress.getScore().equals(BigInteger.valueOf(100))){
				//Set property to show list of potentially valid options???
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
						.getExternalContext().getSession(false);
				session.setAttribute("proposedAddress", validAddress);
				isValid=false;
			} else if(validAddress==null || (validAddress!=null && !validAddress.getScore().equals(BigInteger.valueOf(100)))){
				FacesUtil.addError("ERROR: Address not valid by FedEx. Please check your address and confirm it's accurate before submitting again.");
				isValid = false;
			}
		}
//		if(isValid && !clientViewService.validateAddressFedex(lostReport)){
//			FacesUtil.addError("ERROR: Address not valid by FedEx.");
//			isValid=false;
//		}
		
		return isValid;
	}
	
	private boolean validateCCInfo() {
		boolean isValid = true;
		if (ccnumber== null || ccnumber.length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Number is required.");
			isValid = false;
		}
		if (ccname == null || ccname.length() == 0) {
			FacesUtil.addError("ERROR: Name on Credit Card is required.");
			isValid = false;
		}
		if (ccvendor == null || ccvendor.length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Vendor is required.");
			isValid = false;
		}
		if (ccexpirationmonth==null || ccexpirationmonth.length() == 0 || ccexpirationyear== null || ccexpirationyear.length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Expiration is required.");
			isValid = false;
		}
		if (ccsecurity==null || ccsecurity.length() == 0) {
			FacesUtil.addError("ERROR: Credit Card Security Code is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getAddress1() == null									// VALIDATE: ADDRESS 1
				|| lostReport.getContact().getBillingaddress().getAddress1().trim().length() == 0) {
			FacesUtil.addError("ERROR: Billing Address is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getCity() == null										// VALIDATE: CITY
				|| lostReport.getContact().getBillingaddress().getCity().trim().length() == 0) {
			FacesUtil.addError("ERROR: Billing City is required.");
			isValid = false;
		}
		if (lostReport.getContact().getBillingaddress().getCountry() == null									// VALIDATE: COUNTRY
				|| lostReport.getContact().getBillingaddress().getCountry().trim().length() == 0) {
			FacesUtil.addError("ERROR: Billing Country is required.");
			isValid = false;
		} else if (lostReport.getContact().getBillingaddress().getCountry().equals("US")){
			if (lostReport.getContact().getBillingaddress().getState() == null									// VALIDATE: STATE
					|| lostReport.getContact().getBillingaddress().getState().trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing State is required.");
				isValid = false;
			}
			if (lostReport.getContact().getBillingaddress().getPostal() == null								// VALIDATE: ZIP CODE
					|| lostReport.getContact().getBillingaddress().getPostal().trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing Zip Code is required.");
				isValid = false;
			}			
		} else {
			if (lostReport.getContact().getBillingaddress().getProvince() == null								// VALIDATE: PROVINCE
					|| lostReport.getContact().getBillingaddress().getProvince().trim().length() == 0) {
				FacesUtil.addError("ERROR: Billing Province is required.");
				isValid = false;
			}
		}
		if(isValid && !ccnumber.equals("3214697890")){ //TODO: Implement call to Credit Card validation service
			FacesUtil.addError("ERROR: Credit Card is not valid. Please reenter correct credit card information.");
			isValid=false;
		}
		
		return isValid;
	}
	
	private boolean validateContact() {
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
	
	private boolean validateSegments() {
		boolean isValid = true;
		if (lostReport.getSegments().size() == 0) {													// VALIDATE: SEGMENT(S) PROVIDED
			FacesUtil.addError("ERROR: At least one segment must be provided in the \"About Your Trip\" section.");
			isValid = false;
		} else {
			for (int i = 0; i < lostReport.getSegments().size(); i++) {
				SegmentBean seg = lostReport.getSegments().get(i);
				int segNum = i + 1;
				if (seg.getArrivalLocation() == 0) {												// VALIDATE: SEGMENT - ARRIVAL LOCATION
					FacesUtil.addError("ERROR: Arrival Airport required for Segment #" + segNum + " in the \"About Your Trip\" section.");
					isValid = false;
				}
				if (seg.getDepartureLocation() == 0) {												// VALIDATE: SEGMENT - DEPARTURE LOCATION
					FacesUtil.addError("ERROR: Departure Airport required for Segment #" + segNum + " in the \"About Your Trip\" section.");
					isValid = false;
				}
			}
		}
		return isValid;
	}
	
	private boolean validateAB() {
		boolean isValid = true;
		if (lostReport.getDateLost() == null) {													    // VALIDATE: DATE LOST
			FacesUtil.addError("ERROR: Rental Date is required.");
			isValid = false;
		}
		if (lostReport.getPickUpLocation() < 1) {													// VALIDATE: PICK UP
			FacesUtil.addError("ERROR: Rental Location is required.");
			isValid = false;
		}
		if (lostReport.getDropOffLocation() < 1) {													// VALIDATE: DROP OFF
			FacesUtil.addError("ERROR: Drop Off Rental Location is required.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateLF_SWA() {
		boolean isValid = true;
		isValid = validateSegments();
		if (lostReport.getDateLost() == null) {													    // VALIDATE: DATE LOST
			FacesUtil.addError("ERROR: Date Lost is required.");
			isValid = false;
		}
		if (lostReport.getItemColor() != null && lostReport.getItemColor().trim().length() == 0) {	// VALIDATE: ITEM COLOR
			FacesUtil.addError("ERROR: Item Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCaseColor() != null 
				&& lostReport.getItemCaseColor().trim().length() == 0) {							// VALIDATE: ITEM CASE COLOR
			FacesUtil.addError("ERROR: Item Case Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCategory() < 1) {													 	// VALIDATE: CATEGORY
			FacesUtil.addError("ERROR: Item Category is required.");
			isValid = false;
		} else if (lostReport.getItemSubCategory() == 0) {											// VALIDATE: SUBCATEGORY
			FacesUtil.addError("ERROR: Item Subcategory is required for Category \"" + getCategoryDesc() + "\".");
			isValid = false;
		}
		if (lostReport.getItemCategory() == 7) {								 					// VALIDATE: LOST PHONE NUM
			if (lostReport.getLostPhone() == null 
					|| lostReport.getLostPhone().getNumber() == null 
					|| lostReport.getLostPhone().getNumber().trim().length() == 0) {
				FacesUtil.addError("ERROR: Phone number of lost phone is required for Category \"Cellphone\".");
				isValid = false;
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
			locationsPickUp = clientViewService.getLocationsByState(getSubCompany(), getStatePickUp());
		}
		return locationsPickUp;
	}

	public void setLocationsPickUp(List<SelectItem> locationsPickUp) {
		this.locationsPickUp = locationsPickUp;
	}
	
	public List<SelectItem> getLocationsDropOff() {
		if (getSubCompany() != null) {
			locationsDropOff = clientViewService.getLocationsByState(getSubCompany(), getStateDropOff());
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
						categoryList.add(new SelectItem(cat.getId() + "", cat.getDescription()));
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
		if(ccvendors==null){
			List<SelectItem> vendors=new ArrayList<SelectItem>();
			vendors.add(new SelectItem("VI", "Visa"));
			vendors.add(new SelectItem("AE", "American Express"));
			vendors.add(new SelectItem("DS", "Discover"));
			vendors.add(new SelectItem("MC", "Mastercard"));
			ccvendors=vendors;
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
				if (cat != null && cat.getId() == lostReport.getItemCategory() && cat.getSubcategories() != null) {
					for (KeyValueBean key : cat.getSubcategories()) {
						if (key != null) {
							subCategories.add(new SelectItem(key.getKey(), key.getValue()));
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
				seg.setArrivalLocationDesc(getLocationDesc(seg.getArrivalLocation()));
				seg.setDepartureLocationDesc(getLocationDesc(seg.getDepartureLocation()));
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
								if ( key.getKey().equals(testIt + "")) {
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
		String ratekey=selectedoption;
		if (ratekey==null || (ratekey!=null && ratekey.length()==0)) {
			return null;
		}
		if(rates!=null)
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

	public void setCcnumber(String ccnumber) {
		this.ccnumber = ccnumber;
	}

	public String getCcnumber() {
		return ccnumber;
	}

	public void setCcname(String ccname) {
		this.ccname = ccname;
	}

	public String getCcname() {
		return ccname;
	}

	public void setCcvendor(String ccvendor) {
		this.ccvendor = ccvendor;
	}

	public String getCcvendor() {
		return ccvendor;
	}

	public void setCcsecurity(String ccsecurity) {
		this.ccsecurity = ccsecurity;
	}

	public String getCcsecurity() {
		return ccsecurity;
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
		this.shippingPhone= shippingPhone;
	}

	public void setCcexpirationmonth(String ccexpirationmonth) {
		this.ccexpirationmonth = ccexpirationmonth;
	}
	
	public String getCcexpirationmonth() {
		return ccexpirationmonth;
	}

	public void setCcexpirationyear(String ccexpirationyear) {
		this.ccexpirationyear = ccexpirationyear;
	}
	
	public String getCcexpirationyear() {
		return ccexpirationyear;
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
			for (RateBean rate: rates) {
				if (rate != null) {
					ratesList.add(new SelectItem(rate.getRateKey()+ "", rate.getRateType()+" - "+rate.getRateAmount()));
				}
			}
		}
		return ratesList;
	}

	public void setRatesList(List<SelectItem> ratesList) {
		this.ratesList = ratesList;
	}
	
}
