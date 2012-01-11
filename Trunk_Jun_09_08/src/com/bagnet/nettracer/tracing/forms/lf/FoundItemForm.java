package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;

public final class FoundItemForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private LFFound found;
	private List<LFRemark> remarklist;
	
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

	public String getDisFoundDate() {
		return found.getDisFoundDate(dateFormat);
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

	public List<LFRemark> getRemarklist() {
		if (remarklist == null) { 
			remarklist = new ArrayList<LFRemark>();
			if (getFound() != null && getFound().getAgentRemarks() != null) {
				remarklist = new ArrayList<LFRemark>(getFound().getAgentRemarks());
			}
		}
		return remarklist;
	}

	public LFRemark getRemark(int index) {
		if (index < 0) index = 0;
		LFRemark r = null;
		while (this.remarklist.size() <= index) {
			r = new LFRemark();
			r.getRemark().setType(TracingConstants.REMARK_REGULAR);
			this.remarklist.add(r);
		}
		return (LFRemark) this.remarklist.get(index);
	}

	public void setRemarklist(List<LFRemark> remarklist) {
		this.remarklist = remarklist;
	}
	
	public void populateRemarks() {
		List<LFRemark> newRemarks = new ArrayList<LFRemark>();
		for (int i = 0, s = remarklist.size(); i < s; i++) {
			LFRemark lfr = remarklist.get(i);
			if (lfr.getRemark().getRemarktext() != null && !lfr.getRemark().getRemarktext().trim().equals("")) {
				lfr.setFound(getFound());
				if (lfr.getOutcome() != 0) {
					lfr.getRemark().setType(TracingConstants.REMARK_CALL);
				}
				newRemarks.add(lfr);
			}
		}
		found.setAgentRemarks(new LinkedHashSet<LFRemark>(newRemarks));
		setRemarklist(newRemarks);
	}

}