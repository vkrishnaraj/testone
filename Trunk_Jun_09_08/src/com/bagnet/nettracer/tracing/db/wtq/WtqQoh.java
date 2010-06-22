package com.bagnet.nettracer.tracing.db.wtq;

import java.util.ArrayList;
import java.util.Collection;

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

import com.bagnet.nettracer.tracing.db.OHD;

@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value = "QOH_TAG")
public class WtqQoh extends WorldTracerQueue {

	private Collection<OHD> ohdTags = new ArrayList<OHD>();

	@org.hibernate.annotations.CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "WTQ_OHD_TAG", joinColumns = @JoinColumn(name = "wt_queue_id"))
	@Column(name = "ohd_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Collection<OHD> getOhdTags() {
		return ohdTags;
	}

	@Override
	@Transient
	public Object[] getExistsParameters() {
		ArrayList<String> ohdIds = new ArrayList<String>();
		
		for (OHD tag: ohdTags) {
			ohdIds.add(tag.getClaimnum());
		}
		return new Object[] { ohdIds, this.getStatus() };
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqMoh moh where moh.ohdTags.ohd.ohd_ID in (:list) and moh.status = ?";
	}

	public void setOhdTags(Collection<OHD> ohdTags) {
		this.ohdTags = ohdTags;
	}

}
