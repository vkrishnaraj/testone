package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("ERASE_AF")
@Proxy(lazy = false)
public class WtqEraseActionFile extends WorldTracerQueue {
	
	private String af_id;

	@Column(length = 15)
	public String getAf_id() {
		return af_id;
	}

	public void setAf_id(String af_id) {
		this.af_id = af_id;
	}

	@Override
	@Transient
	public Object[] getExistsParameters() {
		// TODO Auto-generated method stub
		return new Object[] {af_id, WtqStatus.PENDING};
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqEraseActionFile where af_id = ? and status = ?";
	}
}
