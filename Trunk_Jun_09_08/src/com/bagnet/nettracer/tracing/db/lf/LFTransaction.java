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
public class LFTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3504907139984489526L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private int id;
	private String amt;
	private String swchKey;
	private String authCode;
	private Date transactionDate;
	private String stan;
	private String tranNum;
	private String rspCode;
	
	@OneToOne
	@JoinColumn(name = "shipment_id", nullable = false)
	private LFShipping shipment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LFShipping getShipment() {
		return shipment;
	}
	public void setShipment(LFShipping shipment) {
		this.shipment = shipment;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getSwchKey() {
		return swchKey;
	}
	public void setSwchKey(String swchKey) {
		this.swchKey = swchKey;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getTranNum() {
		return tranNum;
	}
	public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	
}
