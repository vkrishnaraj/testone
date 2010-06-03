package com.bagnet.nettracer.tracing.db.wtq;

import java.util.ArrayList;
import java.util.Collection;

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
@Proxy(lazy = false)
@DiscriminatorValue(value = "QOH_TAG")
public class WtqQoh extends WorldTracerQueue {

	private Collection<WtqOhdTag> ohdTags = new ArrayList<WtqOhdTag>();

	@org.hibernate.annotations.CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "WTQ_OHD_TAG", joinColumns = @JoinColumn(name = "wt_queue_id"))
	@Fetch(FetchMode.SELECT)
	public Collection<WtqOhdTag> getOhdTags() {
		return ohdTags;
	}

	@Override
	@Transient
	public Object[] getExistsParameters() {
		ArrayList<String> ohdIds = new ArrayList<String>();
		
		for (WtqOhdTag tag: ohdTags) {
			ohdIds.add(tag.getOhd().getClaimnum());
		}
		return new Object[] { ohdIds, this.getStatus() };
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqMoh moh where moh.ohdTags.ohd.ohd_ID in (:list) and moh.status = ?";
	}

	public void setOhdTags(Collection<WtqOhdTag> ohdTags) {
		this.ohdTags = ohdTags;
	}

}
