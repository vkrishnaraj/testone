package com.bagnet.nettracer.tracing.db.wtq;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("REQUEST_OHD")
@Proxy(lazy = false)
public class WtqRequestOhd extends WtqIncidentAction {

	public WtqRequestOhd() {
		// TODO Auto-generated constructor stub
	}

	private String wt_ohd;

	private String furtherInfo;
	
	private Set<String> teletypes;

	@Override
	@Transient
	public Object[] getExistsParameters() {
		return new Object[] {this.getWt_ohd(), this.getStatus()};
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqRequestOhd roh where roh.wt_ohd = ? and status = ?";
	}

	@Column(length=15)
	public String getWt_ohd() {
		return wt_ohd;
	}

	public void setWt_ohd(String wt_ohd) {
		this.wt_ohd = wt_ohd;
	}

	@Column(length=60)
	public String getFurtherInfo() {
		return furtherInfo;
	}

	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}

	@org.hibernate.annotations.CollectionOfElements(targetElement = java.lang.String.class, fetch=FetchType.EAGER)
	@JoinTable(name = "wtq_teletype", joinColumns=@JoinColumn(name="wt_queue_id"))
	@Column(name = "ttype_address", nullable = false)
	@Fetch(FetchMode.SUBSELECT)
	public Set<String> getTeletypes() {
		return teletypes;
	}

	public void setTeletypes(Set<String> teletypes) {
		this.teletypes = teletypes;
	}
		
		

}
