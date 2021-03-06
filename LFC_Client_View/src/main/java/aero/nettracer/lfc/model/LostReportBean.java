package aero.nettracer.lfc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LostReportBean implements Serializable{

	private static final long serialVersionUID = 3431305779544517131L;
	
	private String itemDate;
	private int pickUpLocation;
	private int dropOffLocation;
	private String vantiveNumber;
	private String agreementNumber;
	private String mvaNumber;
	private String itemBrand;
	private String itemSerial;
	private long itemCategory;
	private long itemSubCategory;
	private String itemColor;
	private String itemDesc;
	private String itemLongDesc;
	private String firstNameBag;
	private String middleNameBag;
	private String lastNameBag;
	private String whereLost;
	private String company;
	private String subCompany;
	private Date dateLost;
	private String itemModel;
	private String itemSize;
	private String itemCaseColor;
	private long daysFromCreate;
	private List<SegmentBean> segments;

	private ContactBean contact = new ContactBean();
	private PhoneBean lostPhone = new PhoneBean();
	private CCBean cc = new CCBean();
	
	//For Status Page
	private String status;
	private String disposition;
	private String trackingNumber;
	private String reportId;
	
	//Shipping
	private double declaredValue;
	private String shippingPayment;
	private String shippingOption;
	private String shippingTax;

	private String feedback;
	private boolean paid=false;
	
	private boolean shipping=false;

	public String getItemDate() {
		return itemDate;
	}

	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public boolean IsShipping() {
		return shipping;
	}

	public void setShipping(boolean shipping) {
		this. shipping =  shipping;
	}
	
	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}

	public String getVantiveNumber() {
		return vantiveNumber;
	}

	public void setVantiveNumber(String vantiveNumber) {
		this.vantiveNumber = vantiveNumber;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getItemSerial() {
		return itemSerial;
	}

	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}

	public String getItemColor() {
		return itemColor;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public ContactBean getContact() {
		return contact;
	}

	public void setContact(ContactBean contact) {
		this.contact = contact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public void setPickUpLocation(int pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public int getPickUpLocation() {
		return pickUpLocation;
	}

	public void setDropOffLocation(int dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

	public int getDropOffLocation() {
		return dropOffLocation;
	}

	public void setItemCategory(long itemCategory) {
		this.itemCategory = itemCategory;
	}

	public long getItemCategory() {
		return itemCategory;
	}

	public void setItemSubCategory(long itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public long getItemSubCategory() {
		return itemSubCategory;
	}

	public void setWhereLost(String whereLost) {
		this.whereLost = whereLost;
	}

	public String getWhereLost() {
		return whereLost;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDateLost(Date dateLost) {
		this.dateLost = dateLost;
	}

	public Date getDateLost() {
		return dateLost;
	}
	
	public String getItemLongDesc() {
		return itemLongDesc;
	}

	public void setItemLongDesc(String itemLongDesc) {
		this.itemLongDesc = itemLongDesc;
	}

	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemCaseColor() {
		return itemCaseColor;
	}

	public void setItemCaseColor(String itemCaseColor) {
		this.itemCaseColor = itemCaseColor;
	}

	public PhoneBean getLostPhone() {
		return lostPhone;
	}

	public void setLostPhone(PhoneBean lostPhone) {
		this.lostPhone = lostPhone;
	}

	public String getSubCompany() {
		return subCompany;
	}

	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	
	public long getDaysFromCreate() {
		return daysFromCreate;
	}

	public void setDaysFromCreate(long daysFromCreate) {
		this.daysFromCreate = daysFromCreate;
	}

	public List<SegmentBean> getSegments() {
		if (segments == null) {
			segments = new ArrayList<SegmentBean>();
			SegmentBean segment = new SegmentBean();
			segments.add(segment);
		}
		return segments;
	}

	public void setSegments(List<SegmentBean> segments) {
		this.segments = segments;
	}

	public String getShippingPayment() {
		return shippingPayment;
	}

	public void setShippingPayment(String shippingPayment) {
		this.shippingPayment = shippingPayment;
	}

	public String getShippingOption() {
		return shippingOption;
	}

	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}

	public String getFirstNameBag() {
		return firstNameBag;
	}

	public void setFirstNameBag(String firstNameBag) {
		this.firstNameBag = firstNameBag;
	}

	public String getMiddleNameBag() {
		return middleNameBag;
	}

	public void setMiddleNameBag(String middleNameBag) {
		this.middleNameBag = middleNameBag;
	}

	public String getLastNameBag() {
		return lastNameBag;
	}

	public void setLastNameBag(String lastNameBag) {
		this.lastNameBag = lastNameBag;
	}

	public String getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(String shippingTax) {
		this.shippingTax = shippingTax;
	}

	public CCBean getCc() {
		return cc;
	}

	public void setCc(CCBean cc) {
		this.cc = cc;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

}
