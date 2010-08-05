package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.dr.Dispute;

@Entity
@DiscriminatorValue("DISPUTE")
@Proxy(lazy = true)
public class DisputeResolutionTask extends GeneralTask{
	
	Dispute dispute;
	
	@OneToOne(targetEntity = Dispute.class)
	@JoinColumn(name = "dispute_res_id")
	@Transient
	public Dispute getDispute() {
		return dispute;
	}

	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}
}
