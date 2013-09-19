package com.bagnet.nettracer.tracing.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Sean Fine
 * Class representing Claim Depreciation object
 */
@Entity
@Table(name = "claim_depreciation")
@Proxy(lazy = false)
public class Claim_Depreciation {

	private int id;
	private Date dateCalculate; 
	private int claimType_id;
	private double totalWeight;
	private String weightMetric;
	private String currency;
	private double totalApprovedPayout;
	
	private Claim claim;
	
	private List<Depreciation_Item> itemlist=new ArrayList<Depreciation_Item>();

	private String _DATEFORMAT; // current login agent's date format
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(targetEntity =  com.bagnet.nettracer.tracing.db.Claim.class)
	@JoinColumn(name="claim_id")
	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	public Date getDateCalculate() {
		return dateCalculate;
	}

	public void setDateCalculate(Date dateCalculate) {
		this.dateCalculate = dateCalculate;
	}

	public int getClaimType_id() {
		return claimType_id;
	}

	public void setClaimType_id(int claimType_id) {
		this.claimType_id = claimType_id;
	}

	@OneToMany(mappedBy = "claimDepreciation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	public List<Depreciation_Item> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<Depreciation_Item> itemlist) {
		this.itemlist = itemlist;
	}

	@Transient
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	@Transient
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	@Transient
	public void setDispDateCalculate(String s) {
		setDateCalculate(DateUtils.convertToDate(s, _DATEFORMAT, null));
	}

	@Transient
	public String getDispDateCalculate() {
		return DateUtils.formatDate(getDateCalculate(), _DATEFORMAT, null, null);
	}

	public String getWeightMetric() {
		return weightMetric;
	}

	public void setWeightMetric(String weightMetric) {
		this.weightMetric = weightMetric;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getTotalApprovedPayout() {
		return totalApprovedPayout;
	}

	public void setTotalApprovedPayout(double totalApprovedPayout) {
		this.totalApprovedPayout = totalApprovedPayout;
	}
}
