package com.bagnet.nettracer.tracing.db.wtq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public abstract class WtqFwd extends WorldTracerQueue {

	private String handleCopy = null;

	private Set<String> name = new HashSet<String>();

	private Collection<WtqSegment> itinerary = new ArrayList<WtqSegment>();
	
	private String fwdExpediteNum = null;

	private String fwdDestinationAirline;
	private String fwdDestinationStation;

	private String matchingAhl;

	private Set<String> teletypes = new HashSet<String>();

	private String supInfo;

	@Column(length = 1)
	public String getFwdHandleCopy() {
		return handleCopy;
	}

	public void setFwdHandleCopy(String handleCopy) {
		this.handleCopy = handleCopy;
	}

	@org.hibernate.annotations.CollectionOfElements(targetElement = java.lang.String.class, fetch = FetchType.EAGER)
	@JoinTable(name = "wtq_name", joinColumns = @JoinColumn(name = "wt_queue_id"))
	@Column(name = "pax_name", nullable = false, length = 20)
	@Fetch(FetchMode.SELECT)
	public Set<String> getFwdName() {
		return name;
	}

	public void setFwdName(Set<String> name) {
		this.name = name;
	}

	@org.hibernate.annotations.CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "WTQ_SEGMENT", joinColumns = @JoinColumn(name = "segment_id"))
	@org.hibernate.annotations.OrderBy(clause = "departdate asc, departureTime asc")
	@Fetch(FetchMode.SELECT)
	public Collection<WtqSegment> getItinerary() {
		return itinerary;
	}

	public void setItinerary(Collection<WtqSegment> itinerary) {
		this.itinerary = itinerary;
	}

	@Column(length = 20)
	public String getFwdExpediteNum() {
		return fwdExpediteNum;
	}

	public void setFwdExpediteNum(String expediteNum) {
		this.fwdExpediteNum = expediteNum;
	}

	@Column(length = 3)
	public String getFwdDestinationAirline() {
		return fwdDestinationAirline;
	}

	public void setFwdDestinationAirline(String fwdDestinationAirline) {
		this.fwdDestinationAirline = fwdDestinationAirline;
	}

	@Column(length = 4)
	public String getFwdDestinationStation() {
		return fwdDestinationStation;
	}

	public void setFwdDestinationStation(String fwdDestinationStation) {
		this.fwdDestinationStation = fwdDestinationStation;
	}

	@org.hibernate.annotations.CollectionOfElements(targetElement = java.lang.String.class, fetch = FetchType.EAGER)
	@JoinTable(name = "wtq_teletype", joinColumns = @JoinColumn(name = "wt_queue_id"))
	@Column(name = "ttype_address", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Set<String> getTeletypes() {
		return teletypes;
	}

	public void setTeletypes(Set<String> teletypes) {
		this.teletypes = teletypes;
	}

	@Column(length = 60)
	public String getSupInfo() {
		return supInfo;
	}

	public void setSupInfo(String supInfo) {
		this.supInfo = supInfo;
	}

	@Column(length = 20)
	public String getMatchingAhl() {
		return matchingAhl;
	}

	public void setMatchingAhl(String matchingAhl) {
		this.matchingAhl = matchingAhl;
	}

	@Override
	@Transient
	public Object[] getExistsParameters() {
		return new Object[] { this.getFwdExpediteNum(), this.getStatus() };
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqFwd fwd where fwd.fwdExpediteNum = ? and fwd.status = ?";
	}

}
