package com.bagnet.nettracer.tracing.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

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
	private String bdo_id;
	private String bdo_ref;
	private String distributemethod;
	private String cancelreason;

	private String paymentType;
	
	//hidden
	private int expensepayout_ID;
	private int status_id;
	private int printcount;
	private String ordernum;
	private String slvnum;
	private String seccode;
	private String wssubmit;
	private int cancelcount;
	private String errormsg;

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
	private String updateRemarkOnly;
	private String dispDraftpaiddate;
	private String dispApproval_date;
	private String dispDraftreqdate;
	private String dispVoucherExpirationDate;
	private String toremark;

	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getCancelreason() {
		return cancelreason;
	}
	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	public int getCancelcount() {
		return cancelcount;
	}
	public void setCancelcount(int cancelcount) {
		this.cancelcount = cancelcount;
	}
	public String getWssubmit() {
		return wssubmit;
	}
	public void setWssubmit(String wssubmit) {
		this.wssubmit = wssubmit;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getSlvnum() {
		return slvnum;
	}
	public void setSlvnum(String slvnum) {
		this.slvnum = slvnum;
	}
	public String getSeccode() {
		return seccode;
	}
	public void setSeccode(String seccode) {
		this.seccode = seccode;
	}	
	public int getPrintcount() {
		return printcount;
	}
	public void setPrintcount(int printcount) {
		this.printcount = printcount;
	}
	public String getToremark() {
		return toremark;
	}
	public void setToremark(String toremark) {
		this.toremark = toremark;
	}
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
		this.dispDraftpaiddate = dpDate;
	}
	
	public String getDispApproval_date() {
		return getDispDate(approval_date);
	}
	
	public void setDispApproval_date(String dpDate) {
		this.dispApproval_date = dpDate;
	}
	
	public String getDispDraftreqdate() {
		return getDispDate(draftreqdate);
	}
	
	public void setDispDraftreqdate(String dpDate) {
		this.dispDraftreqdate = dpDate;
		
	}
	
	public String getDispVoucherExpirationDate() {
		return getDispDate(voucherExpirationDate);
	}
	
	public void setDispVoucherExpirationDate(String dpDate) {
		this.dispVoucherExpirationDate = dpDate;
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
			SimpleDateFormat tmp = (dateFormat == null ? new SimpleDateFormat(TracingConstants.DB_DATEFORMAT) : new SimpleDateFormat(dateFormat));
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
	public String getUpdateRemarkOnly() {
		return updateRemarkOnly;
	}
	public void setUpdateRemarkOnly(String updateRemarkOnly) {
		this.updateRemarkOnly = updateRemarkOnly;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getBdo_id() {
		return bdo_id;
	}
	public void setBdo_id(String bdo_id) {
		this.bdo_id = bdo_id;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ActionErrors errs = new ActionErrors();
		
		if (dateFormat == null) {
			Agent user = (Agent) request.getSession().getAttribute("user");
			this.dateFormat = user.getDateformat().getFormat();
			this.tz = user.getCurrenttimezone();
		}

		try {
			this.approval_date = parseUserDate(dispApproval_date);
		} catch (ParseException e) {
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.date"));
		}
		
		try {
			this.draftpaiddate = parseUserDate(dispDraftpaiddate);
		} catch (ParseException e) {
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.date"));
		}
		
		try {
			this.draftreqdate = parseUserDate(dispDraftreqdate);
		} catch (ParseException e) {
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.date"));
		}
		
		try {
			this.voucherExpirationDate = parseUserDate(dispVoucherExpirationDate);
		} catch (ParseException e) {
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("invalid.date"));
		}
		
		if(errs.isEmpty()) {
			return null;
		}
		
		return errs;
	}
	
	private Date parseUserDate(String dateStr) throws ParseException {
		if(dateStr == null || dateStr.trim().length() < 1) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.parse(dateStr);
	}
	
	public String getBdo_ref() {
		return bdo_ref;
	}
	
	public void setBdo_ref(String bdo_ref) {
		this.bdo_ref = bdo_ref;
	}
	public void setDraftpaiddate(Date draftpaiddate) {
		this.draftpaiddate = draftpaiddate;
	}
	
	public String getDistributemethod() {
		return distributemethod;
	}
	public void setDistributemethod(String distributemethod) {
		this.distributemethod = distributemethod;
	}
	
}
