package com.bagnet.nettracer.tracing.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Comment;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

public class ExpensePayoutForm extends ActionForm {

	//displayed - bean property copy
	private Date createdate;
	private String paycode;
	private String draft;
	private Date draftreqdate;
	private Date draftpaiddate;
	private double checkamt;
	private double voucheramt;
	private int mileageamt;
	private List<Comment> oldComments;
	private String newComment;	
	private Date approval_date;
	private double incidentalAmountAuth;
	private double incidentalAmountClaimed;
	private Date voucherExpirationDate;
	private double creditCardRefund;
	
	//hidden
	private int expensepayout_ID;
	private int status_id;
	
	
	//displayed - need conversion
	private String incident_ID;
	private String createUser;
	private int expensetype_id;

	private int expenselocation_ID;
	private String createStation;
	private String currency_ID;
	
	
	//action buttons
	private String createExpense;
	private String updateExpense;
	private String approveExpense;
	private String denyExpense;
	private String rependExpense;
	private String payExpense;
	
	//locale info
	private String dateFormat;
	private String tz;
	
	public String getIncident_ID() {
		return incident_ID;
	}
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getDraft() {
		return draft;
	}
	public void setDraft(String draft) {
		this.draft = draft;
	}
	public Date getDraftreqdate() {
		return draftreqdate;
	}
	public void setDraftreqdate(Date draftreqdate) {
		this.draftreqdate = draftreqdate;
	}
	
	public Date getDraftpaiddate() {
		return draftpaiddate;
	}
	
	public String getDispDraftpaiddate() {
		return getDispDate(draftpaiddate);
	}
	
	public void setDispDraftpaiddate(String dpDate) {
		if(dpDate == null || dpDate.trim().length() < 1) {
			this.draftpaiddate = null;
		}
		try {
			SimpleDateFormat sdf;
			if(dateFormat != null) {
				sdf = new SimpleDateFormat(dateFormat);
			}
			else {
				sdf = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			}
			
			if(tz != null) sdf.setTimeZone(TimeZone.getTimeZone(tz));
			this.draftpaiddate = sdf.parse(dpDate);
		} catch (ParseException e) {
			//pass
		}
	}
	
	public String getDispApproval_date() {
		return getDispDate(approval_date);
	}
	
	public void setDispApproval_date(String dpDate) {
		if(dpDate == null || dpDate.trim().length() < 1) {
			this.approval_date = null;
		}
		try {
			SimpleDateFormat sdf;
			if(dateFormat != null) {
				sdf = new SimpleDateFormat(dateFormat);
			}
			else {
				sdf = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			}
			
			if(tz != null) sdf.setTimeZone(TimeZone.getTimeZone(tz));
			this.approval_date = sdf.parse(dpDate);
		} catch (ParseException e) {
			//pass
		}
	}
	
	public String getDispDraftreqdate() {
		return getDispDate(draftreqdate);
	}
	
	public void setDispDraftreqdate(String dpDate) {
		if(dpDate == null || dpDate.trim().length() < 1) {
			this.draftreqdate = null;
		}
		try {
			SimpleDateFormat sdf;
			if(dateFormat != null) {
				sdf = new SimpleDateFormat(dateFormat);
			}
			else {
				sdf = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			}
			
			if(tz != null) sdf.setTimeZone(TimeZone.getTimeZone(tz));
			this.draftreqdate = sdf.parse(dpDate);
		} catch (ParseException e) {
			//pass
		}
	}
	
	public String getDispVoucherExpirationDate() {
		return getDispDate(voucherExpirationDate);
	}
	
	public void setDispVoucherExpirationDate(String dpDate) {
		if(dpDate == null || dpDate.trim().length() < 1) {
			this.voucherExpirationDate = null;
		}
		try {
			SimpleDateFormat sdf;
			if(dateFormat != null) {
				sdf = new SimpleDateFormat(dateFormat);
			}
			else {
				sdf = new SimpleDateFormat(TracingConstants.DB_DATEFORMAT);
			}
			
			if(tz != null) sdf.setTimeZone(TimeZone.getTimeZone(tz));
			this.voucherExpirationDate = sdf.parse(dpDate);
		} catch (ParseException e) {
			//pass
		}
	}
	
	
	
	public double getCheckamt() {
		return checkamt;
	}
	public void setCheckamt(double checkamt) {
		this.checkamt = checkamt;
	}
	public double getVoucheramt() {
		return voucheramt;
	}
	public void setVoucheramt(double voucheramt) {
		this.voucheramt = voucheramt;
	}
	public int getMileageamt() {
		return mileageamt;
	}
	public void setMileageamt(int mileageamt) {
		this.mileageamt = mileageamt;
	}
	public List<Comment> getOldComments() {
		return oldComments;
	}
	public void setOldComments(List<Comment> oldComments) {
		this.oldComments = oldComments;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	public Date getApproval_date() {
		return approval_date;
	}
	public void setApproval_date(Date approval_date) {
		this.approval_date = approval_date;
	}
	public double getIncidentalAmountAuth() {
		return incidentalAmountAuth;
	}
	public void setIncidentalAmountAuth(double incidentalAmountAuth) {
		this.incidentalAmountAuth = incidentalAmountAuth;
	}
	public double getIncidentalAmountClaimed() {
		return incidentalAmountClaimed;
	}
	public void setIncidentalAmountClaimed(double incidentalAmountClaimed) {
		this.incidentalAmountClaimed = incidentalAmountClaimed;
	}
	public Date getVoucherExpirationDate() {
		return voucherExpirationDate;
	}
	public void setVoucherExpirationDate(Date voucherExpirationDate) {
		this.voucherExpirationDate = voucherExpirationDate;
	}
	public double getCreditCardRefund() {
		return creditCardRefund;
	}
	public void setCreditCardRefund(double creditCardRefund) {
		this.creditCardRefund = creditCardRefund;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public int getExpensetype_id() {
		return expensetype_id;
	}
	public void setExpensetype_id(int expensetype_id) {
		this.expensetype_id = expensetype_id;
	}
	public int getExpenselocation_ID() {
		return expenselocation_ID;
	}
	public void setExpenselocation_ID(int expenselocation) {
		this.expenselocation_ID = expenselocation;
	}
	public String getCreateStation() {
		return createStation;
	}
	public void setCreateStation(String createStation) {
		this.createStation = createStation;
	}
	public String getCurrency_ID() {
		return currency_ID;
	}
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}
	public int getExpensepayout_ID() {
		return expensepayout_ID;
	}
	public void setExpensepayout_ID(int expensepayout_ID) {
		this.expensepayout_ID = expensepayout_ID;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getCreateExpense() {
		return createExpense;
	}
	public void setCreateExpense(String createExpense) {
		this.createExpense = createExpense;
	}
	public String getUpdateExpense() {
		return updateExpense;
	}
	public void setUpdateExpense(String updateExpense) {
		this.updateExpense = updateExpense;
	}
	public String getApproveExpense() {
		return approveExpense;
	}
	public void setApproveExpense(String approveExpense) {
		this.approveExpense = approveExpense;
	}
	public String getDenyExpense() {
		return denyExpense;
	}
	public void setDenyExpense(String denyExpense) {
		this.denyExpense = denyExpense;
	}
	public String getRependExpense() {
		return rependExpense;
	}
	public void setRependExpense(String rependExpense) {
		this.rependExpense = rependExpense;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getTz() {
		return tz;
	}
	public void setTz(String tz) {
		this.tz = tz;
	}
	
	private String getDispDate(Date date) {
		if(date == null ) {
			return "";
		}
		else {
			SimpleDateFormat tmp = dateFormat == null ? new SimpleDateFormat(TracingConstants.DB_DATEFORMAT) : new SimpleDateFormat(dateFormat);
			if(tz == null) {
				tmp.setTimeZone(TimeZone.getDefault());
			}
			else {
				tmp.setTimeZone(TimeZone.getTimeZone(tz));
			}
			return tmp.format(date);
		}
	}
	public String getPayExpense() {
		// TODO Auto-generated method stub
		return payExpense;
	}
	
	public void setPayExpense(String payexpense) {
		this.payExpense = payexpense;
	}
}
