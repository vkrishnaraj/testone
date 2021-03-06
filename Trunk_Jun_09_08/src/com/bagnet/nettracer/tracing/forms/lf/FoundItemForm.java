package com.bagnet.nettracer.tracing.forms.lf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

public final class FoundItemForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private LFFound found;
	private List<LFMatchHistory> traceResults;
	private List<LFMatchHistory> rejectedResults;
	
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
	
	public String getDisDeliveredDate() {
		return found.getDisDeliveredDate(dateFormat);
	}

	public String getDisFoundDate() {
		return found.getDisFoundDate(dateFormat);
	}

	public String getPrimaryInternationalNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedCountry()!=null && getPhone(LFPhone.PRIMARY).getDecryptedCountry().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedCountry();
		}
		return null;
	}

	public String getPrimaryAreaNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedArea()!=null && getPhone(LFPhone.PRIMARY).getDecryptedArea().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedArea();
		}
		return null;
	}
	
	public String getPrimaryExchangeNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedExchange()!=null && getPhone(LFPhone.PRIMARY).getDecryptedExchange().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedExchange();
		}
		return null;
	}
	
	public String getPrimaryLineNumber() {
		if(getPhone(LFPhone.PRIMARY).getDecryptedLine()!=null && getPhone(LFPhone.PRIMARY).getDecryptedLine().length()>0){
			return getPhone(LFPhone.PRIMARY).getDecryptedLine();
		} else {
			String phone=getPhone(LFPhone.PRIMARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				return phone;
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
		if(getPhone(LFPhone.SECONDARY).getDecryptedCountry()!=null && getPhone(LFPhone.SECONDARY).getDecryptedCountry().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedCountry();
		}
		return null;
	}

	public String getSecondaryAreaNumber() {

		if(getPhone(LFPhone.SECONDARY).getDecryptedArea()!=null && getPhone(LFPhone.SECONDARY).getDecryptedArea().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedArea();
		}
		return null;
	}
	
	public String getSecondaryExchangeNumber() {
		if(getPhone(LFPhone.SECONDARY).getDecryptedExchange()!=null && getPhone(LFPhone.SECONDARY).getDecryptedExchange().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedExchange();
		}
		return null;
	}
	
	public String getSecondaryLineNumber() {
		if(getPhone(LFPhone.SECONDARY).getDecryptedLine()!=null && getPhone(LFPhone.SECONDARY).getDecryptedLine().length()>0){
			return getPhone(LFPhone.SECONDARY).getDecryptedLine();
		} else {
			String phone=getPhone(LFPhone.SECONDARY).getDecryptedPhoneNumber();
			if(phone!=null && phone.length()>0)
				return phone;
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

	public LFRemark getRemark(int index) {
		if (index < 0) index = 0;
		LFRemark r = null;
		while (getFound().getAgentRemarks().size() <= index) {
			r = new LFRemark();
			r.getRemark().setType(TracingConstants.REMARK_REGULAR);
			getFound().getAgentRemarks().add(r);
		}
		return (LFRemark) getFound().getAgentRemarks().toArray(new LFRemark[0])[index];
	}

	public List<LFMatchHistory> getTraceResults() {
		return traceResults;
	}

	public void setTraceResults(List<LFMatchHistory> traceResults) {
		this.traceResults = traceResults;
	}
	
	public boolean getDisplaySummary() {
		return  (traceResults != null && !traceResults.isEmpty()) || (rejectedResults != null && !rejectedResults.isEmpty()) || found.hasContactInfo();
	}
	
	public boolean getHasContactInfo() {
		return found.hasContactInfo();
	}

	public List<LFMatchHistory> getRejectedResults() {
		return rejectedResults;
	}

	public void setRejectedResults(List<LFMatchHistory> rejectedResults) {
		this.rejectedResults = rejectedResults;
	}
	
	public LFItem getFoundItem() {
		if (found != null) {
			return found.getItem();
		}
		return null;
	}
	
	public void setFoundItem(LFItem foundItem) {
		if (found != null) {
			found.setItem(foundItem);
		}
	}
	
	public String getDispCheckAmount() {
		String toReturn = "";
		try {
			DecimalFormat df = new DecimalFormat("#0.00");
			if (found.getCheckAmount() > 0) {
				toReturn = df.format(found.getCheckAmount());
			}
		} catch (NumberFormatException nfe) {
			System.err.println(nfe.getStackTrace());
		}
		return toReturn;
	}
	
	public void setDispCheckAmount(String dispCheckAmount) {
		try {
			if (dispCheckAmount == null || dispCheckAmount.isEmpty()) {
				found.setCheckAmount(0);
			} else {
				double checkAmount = Double.valueOf(dispCheckAmount);
				found.setCheckAmount(checkAmount);
			}
		} catch (NumberFormatException nfe) {
			System.err.println(nfe.getStackTrace());
		}
	}
	
}