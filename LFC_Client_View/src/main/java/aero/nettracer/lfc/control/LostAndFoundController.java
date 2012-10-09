package aero.nettracer.lfc.control;

import java.io.IOException;
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
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.SegmentBean;
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
	private String statePickUp;
	private String stateDropOff;
	private List<SelectItem> locationsPickUp;
	private List<SelectItem> locationsDropOff;
	
 
	@Autowired
	private ClientViewService clientViewService;
	
	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
		lostReport = (LostReportBean) session.getAttribute("lostReport");
		if (getSubCompany() != null && getSubCompany().equals("SWA") ) {
			setSegmentLocationDesc(lostReport.getSegments());
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
	
	public String backToLanding() {
		return "landing?faces-redirect=true";
	}
	
	public String backToLogin() {
		return "login?faces-redirect=true";
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
	
	private boolean validateName() {
		boolean isValid = true;
		if (lostReport.getContact().getFirstName() == null												// VALIDATE: FIRST NAME
				|| lostReport.getContact().getFirstName().trim().length() == 0) {
			FacesUtil.addError("First Name is required.");
			isValid = false;
		}
		if (lostReport.getContact().getLastName() == null												// VALIDATE: LAST NAME
				|| lostReport.getContact().getLastName().trim().length() == 0) {
			FacesUtil.addError("Last Name is required.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateAddress() {
		boolean isValid = true;
		if (lostReport.getContact().getAddress().getAddress1() == null									// VALIDATE: ADDRESS 1
				|| lostReport.getContact().getAddress().getAddress1().trim().length() == 0) {
			FacesUtil.addError("Address is required.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCity() == null										// VALIDATE: CITY
				|| lostReport.getContact().getAddress().getCity().trim().length() == 0) {
			FacesUtil.addError("City is required.");
			isValid = false;
		}
		if (lostReport.getContact().getAddress().getCountry() == null									// VALIDATE: COUNTRY
				|| lostReport.getContact().getAddress().getCountry().trim().length() == 0) {
			FacesUtil.addError("Country is required.");
			isValid = false;
		} else if (lostReport.getContact().getAddress().getCountry().equals("US")){
			if (lostReport.getContact().getAddress().getState() == null									// VALIDATE: STATE
					|| lostReport.getContact().getAddress().getState().trim().length() == 0) {
				FacesUtil.addError("State is required.");
				isValid = false;
			}
			if (lostReport.getContact().getAddress().getPostal() == null								// VALIDATE: ZIP CODE
					|| lostReport.getContact().getAddress().getPostal().trim().length() == 0) {
				FacesUtil.addError("Zip Code is required.");
				isValid = false;
			}			
		} else {
			if (lostReport.getContact().getAddress().getProvince() == null								// VALIDATE: PROVINCE
					|| lostReport.getContact().getAddress().getProvince().trim().length() == 0) {
				FacesUtil.addError("Province is required.");
				isValid = false;
			}			
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
				FacesUtil.addError("Email Address and Confirm Email Address must match.");
				isValid = false;
			}
		}
		if (!hasContactPhoneOrEmail) {
			FacesUtil.addError("Contact Information must contain at least one Phone Number or Email Address.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateSegments() {
		boolean isValid = true;
		if (lostReport.getSegments().size() == 0) {													// VALIDATE: SEGMENT(S) PROVIDED
			FacesUtil.addError("At least one segment must be provided in the \"About Your Trip\" section.");
			isValid = false;
		} else {
			for (int i = 0; i < lostReport.getSegments().size(); i++) {
				SegmentBean seg = lostReport.getSegments().get(i);
				int segNum = i + 1;
				if (seg.getArrivalLocation() == 0) {												// VALIDATE: SEGMENT - ARRIVAL LOCATION
					FacesUtil.addError("Arrival Airport required for Segment #" + segNum + " in the \"About Your Trip\" section.");
					isValid = false;
				}
				if (seg.getDepartureLocation() == 0) {												// VALIDATE: SEGMENT - DEPARTURE LOCATION
					FacesUtil.addError("Departure Airport required for Segment #" + segNum + " in the \"About Your Trip\" section.");
					isValid = false;
				}
			}
		}
		return isValid;
	}
	
	private boolean validateAB() {
		boolean isValid = true;
		if (lostReport.getDateLost() == null) {													    // VALIDATE: DATE LOST
			FacesUtil.addError("Rental Date is required.");
			isValid = false;
		}
		if (lostReport.getPickUpLocation() < 1) {													// VALIDATE: PICK UP
			FacesUtil.addError("Rental Location is required.");
			isValid = false;
		}
		if (lostReport.getDropOffLocation() < 1) {													// VALIDATE: DROP OFF
			FacesUtil.addError("Drop Off Rental Location is required.");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validateLF_SWA() {
		boolean isValid = true;
		isValid = validateSegments();
		if (lostReport.getDateLost() == null) {													    // VALIDATE: DATE LOST
			FacesUtil.addError("Date Lost is required.");
			isValid = false;
		}
		if (lostReport.getItemColor() != null && lostReport.getItemColor().trim().length() == 0) {	// VALIDATE: ITEM COLOR
			FacesUtil.addError("Item Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCaseColor() != null 
				&& lostReport.getItemCaseColor().trim().length() == 0) {							// VALIDATE: ITEM CASE COLOR
			FacesUtil.addError("Item Case Color is required.");
			isValid = false;
		}
		if (lostReport.getItemCategory() < 1) {													 	// VALIDATE: CATEGORY
			FacesUtil.addError("Item Category is required.");
			isValid = false;
		} else if (lostReport.getItemSubCategory() == 0) {											// VALIDATE: SUBCATEGORY
			FacesUtil.addError("Item Subcategory is required for Category \"" + getCategoryDesc() + "\".");
			isValid = false;
		}
		if (lostReport.getItemCategory() == 7) {								 					// VALIDATE: LOST PHONE NUM
			if (lostReport.getLostPhone() == null 
					|| lostReport.getLostPhone().getNumber() == null 
					|| lostReport.getLostPhone().getNumber().trim().length() == 0) {
				FacesUtil.addError("Lost Phone Phone Number is required for Category \"Cellphone\".");
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

	public String getStatePickUp() {
		return statePickUp;
	}

	public void setStatePickUp(String statePickUp) {
		this.statePickUp = statePickUp;
	}

	public String getStateDropOff() {
		return stateDropOff;
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
	
}
