package com.bagnet.nettracer.tracing.forms.lfc;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;

public final class EnterItemsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private LFFound found;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public LFFound getFound() {
		return found;
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public String getDisReceivedDate() {
		return found.getDisReceivedDate();
	}
	
	public void setDisReceivedDate(String date) {
		found.setDisReceivedDate(date, dateFormat);
	}

	public String getDisFoundDate() {
		return found.getDisFoundDate(dateFormat);
	}
	
	public void setDisFoundDate(String date) {
		found.setDisFoundDate(date, dateFormat);
	}


	public String getPrimaryInternationalNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedCountry()!=null && getPhone(LFPhone.PRIMARY).getDecryptedCountry().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedCountry();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()==11){
				return phone.substring(0,1);
			}
		}
		return null;
		
	}

	public String getPrimaryAreaNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedArea()!=null && getPhone(LFPhone.PRIMARY).getDecryptedArea().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedArea();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(1,4);
				} else {
					return phone.substring(0,3);
				}
		}
		return null;
	}
	
	public String getPrimaryExchangeNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedExchange()!=null && getPhone(LFPhone.PRIMARY).getDecryptedExchange().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedExchange();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(4,7);
				} else {
					return phone.substring(3,6);
				}
		}
		return null;
	}
	
	public String getPrimaryLineNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedLine()!=null && getPhone(LFPhone.PRIMARY).getDecryptedLine().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedLine();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(7,11);
				} else {
					return phone.substring(6,10);
				}
		}
		return null;
	}
	
	public String getPrimaryExtension() {
		return getPhone(LFPhone.PRIMARY).getExtension();
	}
	
	public String getPrimaryPhoneNumber() {
		return getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
	}
	
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedPhoneNumber(primaryPhoneNumber);
		setPhone(phone);
	}
	
	public void setPrimaryInternationalNumber(String primaryInternationalNumber) {
		if(primaryInternationalNumber!=null && primaryInternationalNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.PRIMARY);
			phone.setDecryptedCountry(primaryInternationalNumber);
			setPhone(phone);
		}
	}

	public void setPrimaryAreaNumber(String primaryAreaNumber) {
		if(primaryAreaNumber!=null && primaryAreaNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.PRIMARY);
			phone.setDecryptedArea(primaryAreaNumber);
			setPhone(phone);
		}
	}

	public void setPrimaryExchangeNumber(String primaryExchangeNumber) {

		if(primaryExchangeNumber!=null && primaryExchangeNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.PRIMARY);
			phone.setDecryptedExchange(primaryExchangeNumber);
			setPhone(phone);
		}
	}

	public void setPrimaryLineNumber(String primaryLineNumber) {
		if(primaryLineNumber!=null && primaryLineNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.PRIMARY);
			phone.setDecryptedLine(primaryLineNumber);
			setPhone(phone);
		}
	}
	
	public void setPrimaryExtension(String primaryExtension) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setExtension(primaryExtension);
		setPhone(phone);
	}
	

	public String getSecondaryInternationalNumber() {

		if(getPhone(LFPhone.SECONDARY).getDecryptedCountry()!=null && getPhone(LFPhone.SECONDARY).getDecryptedCountry().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedCountry();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()==11){
				return phone.substring(0,1);
			}
		}
		return null;
		
	}

	public String getSecondaryAreaNumber() {

		if(getPhone(LFPhone.SECONDARY).getDecryptedArea()!=null && getPhone(LFPhone.SECONDARY).getDecryptedArea().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedArea();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(1,4);
				} else {
					return phone.substring(0,3);
				}
		}
		return null;
	}
	
	public String getSecondaryExchangeNumber() {
		if(getPhone(LFPhone.SECONDARY).getDecryptedExchange()!=null && getPhone(LFPhone.SECONDARY).getDecryptedExchange().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedExchange();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(4,7);
				} else {
					return phone.substring(3,6);
				}
		}
		return null;
	}
	
	public String getSecondaryLineNumber() {

		if(getPhone(LFPhone.SECONDARY).getDecryptedLine()!=null && getPhone(LFPhone.SECONDARY).getDecryptedLine().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedLine();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				if(phone.length()==11){
					return phone.substring(7,11);
				} else {
					return phone.substring(6,10);
				}
		}
		return null;
	}
	
	public String getSecondaryExtension() {
		return getPhone(LFPhone.SECONDARY).getExtension();
	}
	
	public String getSecondaryPhoneNumber() {
		return getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
	}
	
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedPhoneNumber(secondaryPhoneNumber);
		setPhone(phone);
	}
	
	public void setSecondaryInternationalNumber(String secondaryInternationalNumber) {

		if(secondaryInternationalNumber!=null && secondaryInternationalNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.SECONDARY);
			phone.setDecryptedCountry(secondaryInternationalNumber);
			setPhone(phone);
		}
	}

	public void setSecondaryAreaNumber(String secondaryAreaNumber) {
		if(secondaryAreaNumber!=null && secondaryAreaNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.SECONDARY);
			phone.setDecryptedArea(secondaryAreaNumber);
			setPhone(phone);
		}
	}

	public void setSecondaryExchangeNumber(String secondaryExchangeNumber) {

		if(secondaryExchangeNumber!=null && secondaryExchangeNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.SECONDARY);
			phone.setDecryptedExchange(secondaryExchangeNumber);
			setPhone(phone);
		}
	}

	public void setSecondaryLineNumber(String secondaryLineNumber) {
		if(secondaryLineNumber!=null && secondaryLineNumber.length()>0){
			LFPhone phone = getPhone(LFPhone.SECONDARY);
			phone.setDecryptedLine(secondaryLineNumber);
			setPhone(phone);
		}
	}
	
	public void setSecondaryExtension(String secondaryExtension) {
		if(secondaryExtension!=null && secondaryExtension.length()>0){
			LFPhone phone = getPhone(LFPhone.SECONDARY);
			phone.setExtension(secondaryExtension);
			setPhone(phone);
		}
	}
	
	public int getPrimaryNumberType() {
		return getPhone(LFPhone.PRIMARY).getNumberType();
	}
	
	public void setPrimaryNumberType(int primaryNumberType) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setNumberType(primaryNumberType);;
		setPhone(phone);
	}
	
	public int getSecondaryNumberType() {
		return getPhone(LFPhone.SECONDARY).getNumberType();
	}
	
	public void setSecondaryNumberType(int secondaryNumberType) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setNumberType(secondaryNumberType);
		setPhone(phone);
	}
	
	private LFPhone getPhone(int type) {
		ArrayList<LFPhone> phones = new ArrayList<LFPhone>(found.getClient().getPhones());
		LFPhone candidate = new LFPhone();
		candidate.setPhoneType(type);
		for (LFPhone phone: phones) {
			if (phone.getPhoneType() == type) {
				candidate = phone;
			}
		}
		
		return candidate;
	}
	
	private void setPhone(LFPhone phone) {
		ArrayList<LFPhone> phones = new ArrayList<LFPhone>(found.getClient().getPhones());
		for (LFPhone toRemove: phones) {
			if (phone.getPhoneType() == toRemove.getPhoneType()) {
				phones.remove(toRemove);
				break;
			}
		}
		phone.setPerson(found.getClient());
		found.getClient().getPhones().add(phone);
	}
	
	public LFItem getItem(int index) {
		if (found.getItems() != null && index < found.getItems().size()) {
			return new ArrayList<LFItem>(found.getItems()).get(index);
		}
		return null;
	}
	
	public long getSubCategory() {
		if (found.getItem() != null) {
			return found.getItem().getSubCategory();
		}
		return 0;
	}
	
	public void setSubCategory(long subCategory) {
		if (found.getItem() != null) {
			found.getItem().setSubCategory(subCategory);
		}
	}
	

	public String getDisFoundInternationalNumber() {
		String toReturn = "";
		if(found.getItem().getPhone() != null){
			if(found.getItem().getPhone().getDecryptedCountry()!=null && found.getItem().getPhone().getDecryptedCountry().length()>0){
				toReturn=found.getItem().getPhone().getDecryptedCountry();
			}
		}
		return toReturn;
	}

	public void setDisFoundInternationalNumber(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setDecryptedCountry(phoneNumber);
	}
	
	public String getDisFoundAreaNumber() {
		
		String toReturn = "";
		if(found.getItem().getPhone() != null)
		if(found.getItem().getPhone().getDecryptedArea()!=null && found.getItem().getPhone().getDecryptedArea().length()>0){
			toReturn=found.getItem().getPhone().getDecryptedArea();
		}
		return toReturn;
	}

	public void setDisFoundAreaNumber(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setDecryptedArea(phoneNumber);
	}
	
	public String getDisFoundExchangeNumber() {
		String toReturn = "";
		if(found.getItem().getPhone() != null){
			if(found.getItem().getPhone().getDecryptedExchange()!=null && found.getItem().getPhone().getDecryptedExchange().length()>0){
				toReturn=found.getItem().getPhone().getDecryptedExchange();
			} 
		}
		return toReturn;
	}

	public void setDisFoundExchangeNumber(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setDecryptedExchange(phoneNumber);
	}
	
	public String getDisFoundLineNumber() {
		String toReturn = "";
		if(found.getItem().getPhone() != null){
			if(found.getItem().getPhone().getDecryptedLine()!=null && found.getItem().getPhone().getDecryptedLine().length()>0){
				toReturn=found.getItem().getPhone().getDecryptedLine();
			}
		}
		return toReturn;
	}

	public void setDisFoundLineNumber(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setDecryptedLine(phoneNumber);
	}
	
	public String getDisFoundExtension() {
		String toReturn = "";
		if (found.getItem().getPhone() != null) {
			toReturn=found.getItem().getPhone().getExtension();
		}
		return toReturn;
	}

	public void setDisFoundExtension(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setExtension(phoneNumber);
	}
	
	public String getDisFoundPhoneNumber() {
		String toReturn = "";
		if (found.getItem().getPhone() != null) {
			toReturn = found.getItem().getPhone().getDecryptedPhoneNumber();
		}
		return toReturn;
	}
	
	public void setDisFoundPhoneNumber(String phoneNumber) {
		LFPhone phone = found.getItem().getPhone();
		if (phone == null) {
			phone = new LFPhone();
			phone.setNumberType(LFPhone.MOBILE);
			phone.setItem(found.getItem());
			found.getItem().setPhone(phone);
		}
		phone.setDecryptedPhoneNumber(phoneNumber);
	}

}