package com.bagnet.nettracer.tracing.db;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
/**
 * @author Sean Fine
 * Class representing Depreciation Item object
 */
@Entity
@Table(name = "depreciation_item")
@Proxy(lazy = false)
public class Depreciation_Item {

	private int id;
	private String description;
	private double amountClaimed;
	private Date datePurchase; 
	private Depreciation_Category category;
	private int proofOwnership;
	private boolean notCoveredCoc; 
	private double calcValue;
	private double claimValue;
	
	private Claim_Depreciation claimDepreciation;

	private String _DATEFORMAT; // current login agent's date format
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "claim_depreciation_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Claim_Depreciation getClaimDepreciation() {
		return claimDepreciation;
	}

	public void setClaimDepreciation(Claim_Depreciation claimDepreciation) {
		this.claimDepreciation = claimDepreciation;
	}

	public double getClaimValue() {
		return claimValue;
	}

	public void setClaimValue(double claimValue) {
		this.claimValue = claimValue;
	}

	public double getCalcValue() {
		return calcValue;
	}

	public void setCalcValue(double calcValue) {
		this.calcValue = calcValue;
	}

	public boolean isNotCoveredCoc() {
		return notCoveredCoc;
	}

	public void setNotCoveredCoc(boolean notCoveredCoc) {
		this.notCoveredCoc = notCoveredCoc;
	}

	public int getProofOwnership() {
		return proofOwnership;
	}

	public void setProofOwnership(int proofOwnership) {
		this.proofOwnership = proofOwnership;
	}
	
	@Transient
	public void setProofOwnership(String proofOwnership) {
		int p=0;
		try{
			p=Integer.valueOf(proofOwnership);
		} catch (Exception e){
			
		}
		setProofOwnership(p);
	}

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	@NotFound(action=NotFoundAction.IGNORE)
	@Fetch(FetchMode.SELECT)
	public Depreciation_Category getCategory() {
		return category;
	}

	public void setCategory(Depreciation_Category category) {
		this.category = category;
	}
	
	@Transient
	public int getCategoryId() {
		if(category!=null)
			return category.getId();
		else 
			return 0;
	}

	public void setCategoryId(int categoryId) {
		if(categoryId!=0){
			Depreciation_Category cat=CategoryBMO.getDepreciationCategory(categoryId);
			if(cat!=null){
				this.category=cat;
			} else {
				this.category=new Depreciation_Category();
				this.category.setId(categoryId);
			}
		} else {
			this.category=null;
		}
	}

	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public double getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public void setDispDatePurchase(String s) {
		setDatePurchase(DateUtils.convertToDate(s, _DATEFORMAT, null));
	}

	@Transient
	public String getDispDatePurchase() {
		return DateUtils.formatDate(getDatePurchase(), _DATEFORMAT, null, null);
	}
	
	@Transient
	public String getRefNum() {
		if(getClaimDepreciation().getClaim()!=null &&getClaimDepreciation().getClaim().getIncident()!=null
				&& getClaimDepreciation().getClaim().getIncident().getAirlineIncidentId()!=null && getClaimDepreciation().getClaim().getIncident().getAirlineIncidentId().length()>0){
			return getClaimDepreciation().getClaim().getIncident().getAirlineIncidentId();
		} else {
			return "";
		}
	}
	
	@Transient
	public String getClaimId() {
		if(getClaimDepreciation().getClaim()!=null && getClaimDepreciation().getClaim().getId()!=0){
			return String.valueOf(getClaimDepreciation().getClaim().getId());
		} else {
			return "";
		}
	}
	
	@Transient
	public String getClaimTypeVal() {
		if(getClaimDepreciation().getClaimType()!=null && getClaimDepreciation().getClaimType().getDescription()!=null 
				&& getClaimDepreciation().getClaimType().getDescription().length()>0){
			return getClaimDepreciation().getClaimType().getDescription();
		} else {
			return "";
		}
	}

	@Transient
	public String getDateCalc() {
		if(getClaimDepreciation()!=null && getClaimDepreciation().getDispDateCalculate()!=null 
				&& getClaimDepreciation().getDispDateCalculate().length()>0){
			return getClaimDepreciation().getDispDateCalculate();
		} else {
			return "";
		}
	}

	@Transient
	public String getApprovedTotal() {

		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		if(getClaimDepreciation()!=null){
			return format.format(getClaimDepreciation().getTotalApprovedPayout());
		} else {
			return "";
		}
	}
	
	@Transient
	public String getCategoryName() {
		if(getCategory()!=null && getCategory().getName()!=null && getCategory().getName().length()>0){
			return getCategory().getName();
		} else {
			return "";
		}
		
	}
	
	@Transient
	public String getProofOwn() {
		if(proofOwnership==0)
			return "No Proof";
		else if(proofOwnership==1){
			return "Check";
		} else if(proofOwnership==2){
			return "Photo";
		} else if(proofOwnership==3){
			return "Appraisal";
		} else if(proofOwnership==4){
			return "CC Receipt";
		} else if(proofOwnership==5){
			return "Receipt";
		} else {
			return "";
		}
	}
	

	@Transient
	public String getClaimTotal() {
		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		if(getClaimDepreciation()!=null)
			return format.format(getClaimDepreciation().getTotalClaim());
		else 
			return "0.00";
	}
	
	@Transient
	public String getValueTotal() {
		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		if(getClaimDepreciation()!=null)
			return format.format(getClaimDepreciation().getTotalValue());
		else 
			return "0.00";
	}
	
	@Transient
	public String getDispClaimValue() {
		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		return format.format(getClaimValue());
	}
	
	@Transient
	public String getDispCalcValue() {
		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		return format.format(getCalcValue());
		
	}

	@Transient
	public String getDispAmountClaimed() {
		DecimalFormat format = (DecimalFormat) java.text.NumberFormat
				.getInstance();
		format.applyPattern("##0.00");
		format.setMinimumFractionDigits(2);
		if(getAmountClaimed()!=0)
			return format.format(getAmountClaimed());
		else 
			return "0.00";
	}

	@Transient
	public String getCoc(){
		if(isNotCoveredCoc()){
			return "Not Covered";
		} else {
			return "";
		}
	}

	
}
