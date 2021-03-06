package aero.nettracer.fs.model.transport.v3;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Transient;

import aero.nettracer.fs.model.detection.Blacklist;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class FsClaim implements aero.nettracer.fs.model.transport.v0.FsClaim, Serializable {
	private static final long serialVersionUID = 7010480417198684212L;
	
	protected long id;
	protected long swapId;
	protected String airlineClaimId;
	protected String airline;
	protected String createagentname;
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
	
	
	protected File file;
	private Set<Person> claimants;
	private Set<Segment> segments;
	private Set<FsReceipt> receipts;
	private Set<FsAttachment> attachments;
	private Blacklist blacklist;
	private FsIncident incident;
	private Set<FsIPAddress> ipAddresses;
	private Set<Phone> phones;
	protected Person claimant = null;
		
	public FsClaim(long id){
		this.id = id;
	}
	
	public FsClaim() {
		// TODO Auto-generated constructor stub
	}

	public FsIncident getIncident() {
		return incident;
	}

	public void setIncident(FsIncident incident) {
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
		this.claimRemark = claimRemark;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getCreateagentname() {
	  	return createagentname;
	}

	public void setCreateagentname(String createagentname) {
		this.createagentname = createagentname;
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
	
	public Set<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<FsAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public Set<FsIPAddress> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(Set<FsIPAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
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

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<Phone> getPhones() {
		return phones;
	}
	
}
