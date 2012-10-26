package aero.nettracer.fs.service.objects;

import java.util.Date;

public class Claim {

	private String airline;
	private String airlineClaimId;
	private int claimType;
	private Date claimDate;
	private Date travelDate;
	private double amountClaimed;
	private String amountClaimedCurrency;
	private double amountPaid;
	private String amountPaidCurrency;
	private Person[] claimants;
	private Segment[] segments;
	private Receipt[] receipts;
	private IpAddress [] ipaddress;
	private String remark;
	private Phone[] phones;
	
	public String getAirlineClaimId() {
		return airlineClaimId;
	}
	public void setAirlineClaimId(String airlineClaimId) {
		this.airlineClaimId = airlineClaimId;
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
	public Date getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
	public double getAmountClaimed() {
		return amountClaimed;
	}
	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	public String getAmountClaimedCurrency() {
		return amountClaimedCurrency;
	}
	public void setAmountClaimedCurrency(String amountClaimedCurrency) {
		this.amountClaimedCurrency = amountClaimedCurrency;
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
	public Person[] getClaimants() {
		return claimants;
	}
	public void setClaimants(Person[] claimants) {
		this.claimants = claimants;
	}
	public Segment[] getSegments() {
		return segments;
	}
	public void setSegments(Segment[] segments) {
		this.segments = segments;
	}
	public Receipt[] getReceipts() {
		return receipts;
	}
	public void setReceipts(Receipt[] receipts) {
		this.receipts = receipts;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setPhones(Phone[] phones) {
		this.phones = phones;
	}
	public Phone[] getPhones() {
		return phones;
	}
	public void setIpaddress(IpAddress [] ipaddress) {
		this.ipaddress = ipaddress;
	}
	public IpAddress [] getIpaddress() {
		return ipaddress;
	}
	
}
