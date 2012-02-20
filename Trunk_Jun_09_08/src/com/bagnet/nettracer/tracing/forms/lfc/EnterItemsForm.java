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
		return found.getDisReceivedDate(dateFormat);
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
	
	public String getPrimaryPhoneNumber() {
		return getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
	}
	
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setDecryptedPhoneNumber(primaryPhoneNumber);
		setPhone(phone);
	}
	
	public String getSecondaryPhoneNumber() {
		return getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
	}
	
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setDecryptedPhoneNumber(secondaryPhoneNumber);
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