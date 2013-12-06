package aero.nettracer.serviceprovider.ws_1_0.response;

import java.util.Calendar;

public class VoucherResponse extends GenericResponse {
	private String status;
	private String voucherId;
	private String securityCode;
	private String message;
	private Calendar issueDate;
	private Calendar cancelDate;
	private int returnCode;
	private String orderNumber;
	private String cardNumber;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Calendar getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Calendar issueDate) {
		this.issueDate = issueDate;
	}
	public Calendar getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Calendar cancelDate) {
		this.cancelDate = cancelDate;
	}
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
