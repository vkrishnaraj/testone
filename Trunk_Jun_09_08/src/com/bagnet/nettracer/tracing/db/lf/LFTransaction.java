package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

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
	private String transactionInfo;
	@OneToOne
	@JoinColumn(name = "shipment_id", nullable = false)
	private LFShipping shipment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTransactionInfo() {
		return transactionInfo;
	}
	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
	
	public LFShipping getShipment() {
		return shipment;
	}
	public void setShipment(LFShipping shipment) {
		this.shipment = shipment;
	}
	
}
