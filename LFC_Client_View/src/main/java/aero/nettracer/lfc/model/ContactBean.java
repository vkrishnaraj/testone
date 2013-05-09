package aero.nettracer.lfc.model;

import java.io.Serializable;

public class ContactBean implements Serializable{

	private static final long serialVersionUID = -7301854773110494378L;
	
	private String lastName;
	private String firstName;
	private String shippingName;
	private String middleInitial;
	private String emailAddress;
	private String confirmEmail;
	private PhoneBean primaryPhone = new PhoneBean();
	private PhoneBean secondaryPhone = new PhoneBean();
	private PhoneBean shippingPhone = new PhoneBean();
	private AddressBean address = new AddressBean();
	private AddressBean prefshipaddress = new AddressBean();
	private AddressBean billingaddress = new AddressBean();
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public PhoneBean getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(PhoneBean primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public PhoneBean getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(PhoneBean secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}

	public AddressBean getPrefshipaddress() {
		return prefshipaddress;
	}

	public void setPrefshipaddress(AddressBean prefshipaddress) {
		this.prefshipaddress = prefshipaddress;
	}

	public AddressBean getBillingaddress() {
		return billingaddress;
	}

	public void setBillingaddress(AddressBean billingaddress) {
		this.billingaddress = billingaddress;
	}

	public PhoneBean getShippingPhone() {
		return shippingPhone;
	}

	public void setShippingPhone(PhoneBean shippingPhone) {
		this.shippingPhone = shippingPhone;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

}
