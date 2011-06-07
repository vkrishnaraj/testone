package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;

public final class LostReportForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String dateFormat;
	private LFLost lost;
	private ArrayList<LabelValueBean> colors;
	private ArrayList<LFCategory> categories;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}

	public String getDisOpenDate() {
		return lost.getDisOpenDate(dateFormat);
	}
	
	public String getPrimaryPhoneNumber() {
		return getPhone(LFPhone.PRIMARY).getPhoneNumber();
	}
	
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.PRIMARY);
		phone.setPhoneNumber(primaryPhoneNumber);
		setPhone(phone);
	}
	
	public String getSecondaryPhoneNumber() {
		return getPhone(LFPhone.SECONDARY).getPhoneNumber();
	}
	
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		LFPhone phone = getPhone(LFPhone.SECONDARY);
		phone.setPhoneNumber(secondaryPhoneNumber);
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
		ArrayList<LFPhone> phones = new ArrayList<LFPhone>(lost.getClient().getPhones());
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
		ArrayList<LFPhone> phones = new ArrayList<LFPhone>(lost.getClient().getPhones());
		for (LFPhone toRemove: phones) {
			if (phone.getPhoneType() == toRemove.getPhoneType()) {
				phones.remove(toRemove);
				break;
			}
		}
		lost.getClient().getPhones().add(phone);
	}
	
	public LFItem getItem(int index) {
		if (lost.getItems() != null && index < lost.getItems().size()) {
			return new ArrayList<LFItem>(lost.getItems()).get(index);
		}
		return null;
	}

	public ArrayList<LabelValueBean> getColors() {
		return colors;
	}

	public void setColors(ArrayList<LabelValueBean> colors) {
		this.colors = colors;
	}

	public ArrayList<LFCategory> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<LFCategory> categories) {
		this.categories = categories;
	}
	
}