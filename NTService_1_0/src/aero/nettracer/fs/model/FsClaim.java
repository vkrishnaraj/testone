package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.Blacklist;

import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="subclass_type", discriminatorType = DiscriminatorType.STRING, length=16)
@Proxy(lazy = false)
public class FsClaim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected long id;
	protected long swapId;
	protected String airlineClaimId;
	protected String airline;
	protected int claimType;
	protected Date claimDate;
	protected Date travelDate;
	protected double amountClaimed;
	protected String amountClaimedCurrency;
	protected double amountPaid;
	protected String amountPaidCurrency;
	protected int fraudStatus; // 0=unknown, 1 = believed fraud, 2=known fraud
	protected boolean denied;
	protected String privateReasonForDenial;
	protected String publicReasonForDenial;
	protected String ntIncidentId;
	protected int claimProrateId;
	protected int statusId;
	protected String claimRemark;
	
	@ManyToOne
	@JoinColumn(name = "file_id")
	protected File file;

	//	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	
	//TODO IMPORTANT - this annotation is needed by NetTracer, however, cannot be part of the claim_model.jar that NTFS uses.  If you are rebuilding the model jar, this annotation needs to be commented out - Loupas
//	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	
	private Set<Person> claimants;

	@OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	
	//TODO IMPORTANT - this annotation is needed by NetTracer, however, cannot be part of the claim_model.jar that NTFS uses.  If you are rebuilding the model jar, this annotation needs to be commented out - Loupas
//	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	
	private Set<Segment> segments;

	@OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	
	//TODO IMPORTANT - this annotation is needed by NetTracer, however, cannot be part of the claim_model.jar that NTFS uses.  If you are rebuilding the model jar, this annotation needs to be commented out - Loupas
//	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	
	private Set<FsReceipt> receipts;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.detection.Blacklist.class, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Blacklist blacklist;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "incident_id")
	private aero.nettracer.fs.model.FsIncident incident;
	
	@Transient
	protected Person claimant = null;
		
	public FsClaim(long id){
		this.id = id;
	}
	
	public FsClaim() {
		// TODO Auto-generated constructor stub
	}

	public aero.nettracer.fs.model.FsIncident getIncident() {
		return incident;
	}

	public void setIncident(aero.nettracer.fs.model.FsIncident incident) {
		this.incident = incident;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getSwapId() {
		return swapId;
	}

	public void setSwapId(long swapId) {
		this.swapId = swapId;
	}

	public String getAirlineClaimId() {
		return airlineClaimId;
	}

	public void setAirlineClaimId(String airlineClaimId) {
		this.airlineClaimId = airlineClaimId;
	}
	
	public String getClaimRemark() {
		return claimRemark;
	}

	public void setClaimRemark(String claimRemark) {
		this.claimRemark =claimRemark;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getClaimType() {
		return claimType;
	}

	public void setClaimType(int claimType) {
		this.claimType = claimType;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public double getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getAmountPaidCurrency() {
	  	return amountPaidCurrency;
	}

	public void setAmountPaidCurrency(String amountPaidCurrency) {
		this.amountPaidCurrency = amountPaidCurrency;
	}
//
	public int getFraudStatus() {
		return fraudStatus;
	}

	public void setFraudStatus(int fraudStatus) {
		this.fraudStatus = fraudStatus;
	}

	public boolean isDenied() {
		return denied;
	}

	public void setDenied(boolean denied) {
		this.denied = denied;
	}

	public String getPrivateReasonForDenial() {
		return privateReasonForDenial;
	}

	public void setPrivateReasonForDenial(String privateReasonForDenial) {
		this.privateReasonForDenial = privateReasonForDenial;
	}

	public String getPublicReasonForDenial() {
		return publicReasonForDenial;
	}

	public void setPublicReasonForDenial(String publicReasonForDenial) {
		this.publicReasonForDenial = publicReasonForDenial;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public Set<Person> getClaimants() {
		return claimants;
	}

	public void setClaimants(Set<Person> claimants) {
		this.claimants = claimants;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	public String getAmountClaimedCurrency() {
	  	return amountClaimedCurrency;
	}

	public void setAmountClaimedCurrency(String amountClaimedCurrency) {
		this.amountClaimedCurrency = amountClaimedCurrency;
	}

	public String getNtIncidentId() {
		return ntIncidentId;
	}

	public void setNtIncidentId(String ntIncidentId) {
		this.ntIncidentId = ntIncidentId;
	}

	public int getClaimProrateId() {
		return claimProrateId;
	}

	public void setClaimProrateId(int claimProrateId) {
		this.claimProrateId = claimProrateId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public Set<FsReceipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<FsReceipt> receipts) {
		this.receipts = receipts;
	}

	@Transient
	public String getDisClaimDate(String dateFormat) {
		return DateUtils.formatDate(getClaimDate(), dateFormat, "", null);
	}
	
	@Transient
	public String getDisClaimantName() {
		String toReturn = "";
		toReturn += getClaimant().getLastName() + ", " + getClaimant().getFirstName();
		if (getClaimant().getMiddleName() != null && !getClaimant().getMiddleName().isEmpty()) {
			toReturn += ", " + getClaimant().getMiddleName();
		}
		return toReturn;
	}
	
	@Transient
	public Person getClaimant() {
		if (claimant == null) {
			claimant = claimants.iterator().next();
		}
		return claimant;
	}
	
}
