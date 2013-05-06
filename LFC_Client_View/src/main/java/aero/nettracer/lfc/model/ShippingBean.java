package aero.nettracer.lfc.model;

import java.io.Serializable;

public class ShippingBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String hashKey;
	private LostReportBean lost;
	private ContactBean client;
	private PhoneBean shippingPhone;
	private AddressBean shippingAddress;
	private AddressBean billingAddress;
	private String shippingOption;
	private String totalPayment;
	private String transactionNum;
	private String cardType;
	private String credit4num;
	private String datePaid;
	private double declaredValue;
//	private ArrayList<String> transactionLog;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public LostReportBean getLost() {
		return lost;
	}

	public void setLost(LostReportBean lost) {
		this.lost = lost;
	}

	public PhoneBean getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(PhoneBean shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public ContactBean getClient() {
		return client;
	}

	public void setClient(ContactBean client) {
		this.client= client;
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

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getShippingOption() {
		return shippingOption;
	}

	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCredit4num() {
		return credit4num;
	}

	public void setCredit4num(String credit4num) {
		this.credit4num = credit4num;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}

	public String getTransactionNum() {
		return transactionNum;
	}

	public void setTransactionNum(String transactionNum) {
		this.transactionNum = transactionNum;
	}


	
}