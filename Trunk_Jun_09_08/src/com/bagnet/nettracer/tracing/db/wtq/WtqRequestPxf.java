package com.bagnet.nettracer.tracing.db.wtq;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.WT_PXF;

@Entity
@DiscriminatorValue("REQUEST_PXF")
@Proxy(lazy = false)

public class WtqRequestPxf extends WtqIncidentAction {
	public WtqRequestPxf() {
		// 
		super();
	}
	
	private WT_PXF pxf;
	
	@OneToOne(cascade = CascadeType.ALL)
	public WT_PXF getPxf() {
		return pxf;
	}

	public void setPxf(WT_PXF pxf) {
		this.pxf = pxf;
	}

	private String furtherInfo;
	
	private Set<String> teletypes;

	
	@Column(length=3000)
	public String getFurtherInfo() {
		return furtherInfo;
	}

	public void setFurtherInfo(String furtherInfo) {
		this.furtherInfo = furtherInfo;
	}
	
	@org.hibernate.annotations.CollectionOfElements(targetElement = java.lang.String.class, fetch=FetchType.EAGER)
	@JoinTable(name = "wtq_teletype", joinColumns=@JoinColumn(name="wt_queue_id"))
	@Column(name = "ttype_address", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Set<String> getTeletypes() {
		return teletypes;
	}

	public void setTeletypes(Set<String> teletypes) {
		this.teletypes = teletypes;
	}
	
	@Override
	@Transient
	public Object[] getExistsParameters() {
		return new Object[] {this.getPxf(), this.getStatus()};
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqRequestPxf wtqrpxf where wtqrpxf.pxf = ? and status = ?";
	}
	
}
