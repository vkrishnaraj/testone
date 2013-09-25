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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
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
	private double totalWeight;
	private String weightMetric;
	private String currency;
	private double totalApprovedPayout;
	
	private Claim_Type claimType;
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

	@ManyToOne
	@JoinColumn(name = "claimType_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	public Claim_Type getClaimType() {
		return claimType;
	}

	public void setClaimType(Claim_Type claimType) {
		this.claimType = claimType;
	}

	@Transient
	public int getClaimTypeId() {
		if(claimType!=null)
			return claimType.getId();
		else 
			return 0;
	}

	public void setClaimTypeId(int claimTypeId) {
		if(claimTypeId!=0){
			Claim_Type type=ClaimBMO.getClaimTypeById(claimTypeId);
			if(type!=null){
				this.claimType=type;
			} else {
				this.claimType=new Claim_Type();
				this.claimType.setId(claimTypeId);
			}
		} else {
			this.claimType=null;
		}
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
	
	@Transient
	public double getTotalClaim(){
		double tc=0;
		if(itemlist!=null && itemlist.size()>0)
			for(Depreciation_Item di:itemlist){
				tc+=di.getAmountClaimed();
			}
		return tc;
	}

	@Transient
	public double getTotalValue(){
		double tv=0;
		if(itemlist!=null && itemlist.size()>0)
			for(Depreciation_Item di:itemlist){
				tv+=di.getClaimValue();
			}
		return tv;
	}
	
}
