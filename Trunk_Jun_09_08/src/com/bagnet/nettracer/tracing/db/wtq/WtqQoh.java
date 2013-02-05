package com.bagnet.nettracer.tracing.db.wtq;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
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

	@ElementCollection(fetch = FetchType.EAGER)
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
			ohdIds.add(tag.getOHD_ID());
		}
		return new Object[] { ohdIds };
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqQoh qoh join qoh.ohdTags ohd where ohd.OHD_ID in (:list) ";
	}

	public void setOhdTags(Collection<OHD> ohdTags) {
		this.ohdTags = ohdTags;
	}

}
