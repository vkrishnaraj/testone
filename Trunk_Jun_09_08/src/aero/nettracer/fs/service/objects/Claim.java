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
	private Person[] claimants;
	private Segment[] segments;
	private Receipt[] receipts;
	
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
	
}
