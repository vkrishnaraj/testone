package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident;

@Entity
@DiscriminatorValue("CREATE_BDO")
@Proxy(lazy = false)
public class WtqCreateBdo extends WorldTracerQueue {

	@Override
	@Transient
	public Object[] getExistsParameters() {
		return new Object[] {bdo.getBDO_ID(), this.getStatus()};
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqCreateBdo wcb where wcb.bdo.BDO_ID = ? and wcb.status = ?";
	}
	
	private BDO bdo;

	/**
	 * 
	 * @return
	 */
	@ManyToOne(targetEntity = BDO.class)
	@JoinColumn(name = "bdo_id")
	public BDO getBdo() {
		return bdo;
	}

	public void setBdo(BDO bdo) {
		this.bdo = bdo;
	}
	
	

}
