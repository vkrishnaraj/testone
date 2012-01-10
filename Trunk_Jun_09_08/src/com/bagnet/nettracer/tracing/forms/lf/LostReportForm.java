package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;

public final class LostReportForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String dateFormat;
	private LFLost lost;
	private List<LFRemark> remarklist;
	
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
	
	public String getDisClosedDate() {
		return lost.getDisClosedDate(dateFormat);
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
		phone.setPerson(lost.getClient());
		lost.getClient().getPhones().add(phone);
	}
	
	public LFItem getItem(int index) {
		if (lost.getItems() != null && index < lost.getItems().size()) {
			return new ArrayList<LFItem>(lost.getItems()).get(index);
		}
		return null;
	}
	
	public String getClosedAgentUsername(){
		if(lost != null && lost.getCloseAgent() != null){
			return lost.getCloseAgent().getUsername();
		} else {
			return "";
		}
	}
	
	public String getDisLossdate(){
		if(lost != null && lost.getLossInfo() != null){
			return lost.getLossInfo().getDisLossdate(getDateFormat());
		} else {
			return null;
		}
	}
	
	public void setDisLossdate(String date){
		if(lost != null && lost.getLossInfo() != null){
			lost.getLossInfo().setDisLossdate(date, getDateFormat());
		}
	}

	public List<LFRemark> getRemarklist() {
		if (remarklist == null) { 
			remarklist = new ArrayList<LFRemark>();
			if (getLost() != null && getLost().getAgentRemarks() != null) {
				remarklist = new ArrayList<LFRemark>(getLost().getAgentRemarks());
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
		for (int i = 0, s = remarklist.size(); i < s; i++) {
			LFRemark lfr = remarklist.get(i);
			lfr.setLost(getLost());
		}
		lost.setAgentRemarks(new LinkedHashSet<LFRemark>(remarklist));		
	}

}