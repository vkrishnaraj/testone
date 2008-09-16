package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.OHD;

@Entity
@DiscriminatorValue(value = "FWD_OHD")
@Proxy(lazy = false)
public class WtqFwdOhd extends WtqFwd {

	public WtqFwdOhd() {
	}

	private OHD ohd;
	
	@ManyToOne(targetEntity = OHD.class)
	@JoinColumn(name = "ohd_id")
	public OHD getOhd() {
		return ohd;
	}
	
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}
}
