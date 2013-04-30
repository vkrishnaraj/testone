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
		if(getPhone(LFPhone.PRIMARY).getCountryNumber()!=null && getPhone(LFPhone.PRIMARY).getCountryNumber().length()>0){
			return getPhone(LFPhone.PRIMARY).getCountryNumber();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()==11){
				return phone.substring(0,1);
			}
		}
		return null;
		
	}

	public String getPrimaryAreaNumber() {
		if(getPhone(LFPhone.PRIMARY).getAreaNumber()!=null && getPhone(LFPhone.PRIMARY).getAreaNumber().length()>0){
			return getPhone(LFPhone.PRIMARY).getAreaNumber();
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
		if(getPhone(LFPhone.PRIMARY).getExchangeNumber()!=null && getPhone(LFPhone.PRIMARY).getExchangeNumber().length()>0){
			return getPhone(LFPhone.PRIMARY).getExchangeNumber();
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
		if(getPhone(LFPhone.PRIMARY).getLineNumber()!=null && getPhone(LFPhone.PRIMARY).getLineNumber().length()>0){
			return getPhone(LFPhone.PRIMARY).getLineNumber();
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
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedCountry(primaryInternationalNumber);
		setPhone(phone);
	}

	public void setPrimaryAreaNumber(String primaryAreaNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedArea(primaryAreaNumber);
		setPhone(phone);
	}

	public void setPrimaryExchangeNumber(String primaryExchangeNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedExchange(primaryExchangeNumber);
		setPhone(phone);
	}

	public void setPrimaryLineNumber(String primaryLineNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedLine(primaryLineNumber);
		setPhone(phone);
	}
	
	public void setPrimaryExtension(String primaryExtension) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setExtension(primaryExtension);
		setPhone(phone);
	}
	

	public String getSecondaryInternationalNumber() {

		if(getPhone(LFPhone.SECONDARY).getCountryNumber()!=null && getPhone(LFPhone.SECONDARY).getCountryNumber().length()>0){
			return getPhone(LFPhone.SECONDARY).getCountryNumber();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()==11){
				return phone.substring(0,1);
			}
		}
		return null;
		
	}

	public String getSecondaryAreaNumber() {

		if(getPhone(LFPhone.SECONDARY).getAreaNumber()!=null && getPhone(LFPhone.SECONDARY).getAreaNumber().length()>0){
			return getPhone(LFPhone.SECONDARY).getAreaNumber();
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
		if(getPhone(LFPhone.SECONDARY).getExchangeNumber()!=null && getPhone(LFPhone.SECONDARY).getExchangeNumber().length()>0){
			return getPhone(LFPhone.SECONDARY).getExchangeNumber();
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

		if(getPhone(LFPhone.SECONDARY).getLineNumber()!=null && getPhone(LFPhone.SECONDARY).getLineNumber().length()>0){
			return getPhone(LFPhone.SECONDARY).getLineNumber();
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
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedCountry(secondaryInternationalNumber);
		setPhone(phone);
	}

	public void setSecondaryAreaNumber(String secondaryAreaNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedArea(secondaryAreaNumber);
		setPhone(phone);
	}

	public void setSecondaryExchangeNumber(String secondaryExchangeNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedExchange(secondaryExchangeNumber);
		setPhone(phone);
	}

	public void setSecondaryLineNumber(String secondaryLineNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedLine(secondaryLineNumber);
		setPhone(phone);
	}
	
	public void setSecondaryExtension(String secondaryExtension) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setExtension(secondaryExtension);
		setPhone(phone);
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
			} else {
				if(found.getItem().getPhone().getDecryptedPhoneNumber().length()==11) {
					toReturn= found.getItem().getPhone().getDecryptedPhoneNumber().substring(0,1);
				}
			}
		}
		return toReturn;
	}

	public String getDisFoundAreaNumber() {
		
		String toReturn = "";
		if(found.getItem().getPhone() != null)
		if(found.getItem().getPhone().getDecryptedArea()!=null && found.getItem().getPhone().getDecryptedArea().length()>0){
			toReturn=found.getItem().getPhone().getDecryptedArea();
		} else {
			if (found.getItem().getPhone().getDecryptedPhoneNumber().length()==11) {
				toReturn = found.getItem().getPhone().getDecryptedPhoneNumber().substring(1,4);
			} else {
				toReturn = found.getItem().getPhone().getDecryptedPhoneNumber().substring(0,3);
			}
		}
		return toReturn;
	}
	
	public String getDisFoundExchangeNumber() {
		String toReturn = "";
		if(found.getItem().getPhone() != null){
		if(found.getItem().getPhone().getExchangeNumber()!=null && found.getItem().getPhone().getExchangeNumber().length()>0){
			toReturn=found.getItem().getPhone().getExchangeNumber();
		} else {
			if (found.getItem().getPhone().getDecryptedPhoneNumber().length()==11) {
				toReturn = found.getItem().getPhone().getDecryptedPhoneNumber().substring(4,7);
			} else {
				toReturn = found.getItem().getPhone().getDecryptedPhoneNumber().substring(3,6);
			}
		}
		}
		return toReturn;
	}
	
	public String getDisFoundLineNumber() {
		String toReturn = "";
		if(found.getItem().getPhone() != null){
			if(found.getItem().getPhone().getLineNumber()!=null && found.getItem().getPhone().getLineNumber().length()>0){
				toReturn=found.getItem().getPhone().getLineNumber();
			} else {
				if (found.getItem().getPhone().getDecryptedPhoneNumber().length()==11) {
					toReturn=found.getItem().getPhone().getDecryptedPhoneNumber().substring(7,11);
				} else {
					toReturn=found.getItem().getPhone().getDecryptedPhoneNumber().substring(6,10);
				}
			}
		}
		return toReturn;
	}
	
	public String getDisFoundExtension() {
		String toReturn = "";
		if (found.getItem().getPhone() != null) {
			toReturn=found.getItem().getPhone().getExtension();
		}
		return toReturn;
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