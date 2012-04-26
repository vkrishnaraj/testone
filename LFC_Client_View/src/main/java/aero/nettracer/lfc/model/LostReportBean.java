package aero.nettracer.lfc.model;

import java.io.Serializable;
import java.util.Date;

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
	private String whereLost;
	private String company;
	private String subCompany;
	private ContactBean contact = new ContactBean();
	private Date dateLost;
	private String itemModel;
	private String itemSize;
	private String itemCaseColor;
	private PhoneBean lostPhone = new PhoneBean();
	private long daysFromCreate;
	
	//For Status Page
	private String status;
	private String disposition;
	private String trackingNumber;
	private String reportId;

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

}
