package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class LFShipping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7635407911286747875L;
	@Id
	@GeneratedValue
	private int id;
	private String hashKey;
	
	@OneToOne
	@JoinColumn(name = "lost_id", nullable = false)
	private LFLost lost;
	
	@OneToOne
	@JoinColumn(name = "client_id", nullable = false)
	private LFPerson client;
	
	@OneToOne
	@JoinColumn(name = "phone_id", nullable = true)
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	private LFPhone shippingPhone;

	@OneToOne
	@JoinColumn(name = "shippingaddress_id", nullable = false)
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	private LFAddress shippingAddress;

	@OneToOne
	@JoinColumn(name = "billingaddress_id", nullable = true)
	@Cascade(value = org.hibernate.annotations.CascadeType.ALL)
	private LFAddress billingAddress;
	private String shippingOption;
	private String shippingName;
	private String totalPayment;
	private String authorizationCode;
	private double declaredValue;
//	private ArrayList<String> transactionLog;
	
	@OneToOne
	@JoinColumn(name = "transaction_id", nullable = false)
	private LFTransaction transaction;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	@OneToOne
	@JoinColumn(name = "lost_id", nullable = false)
	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}
	
	@OneToOne
	@JoinColumn(name = "phone_id", nullable = true)
	public LFPhone getShippingPhone() {
		return shippingPhone;
	}
	
	public void setShippingPhone(LFPhone shippingPhone) {
		this.shippingPhone = shippingPhone;
	}


	@OneToOne
	@JoinColumn(name = "client_id", nullable = false)
	public LFPerson getClient() {
		return client;
	}

	public void setClient(LFPerson client) {
		this.client= client;
	}

	@OneToOne
	@JoinColumn(name = "billingaddress_id", nullable = true)
	public LFAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(LFAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public LFAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(LFAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingOption() {
		return shippingOption;
	}

	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public LFTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(LFTransaction transaction) {
		this.transaction = transaction;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	
}


