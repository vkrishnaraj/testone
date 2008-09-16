package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Incident;


@Entity
@DiscriminatorValue("CLOSE_AHL")
@Proxy(lazy = false)
public class WtqCloseAhl extends WtqIncidentAction {

	public WtqCloseAhl() {
		super();
	}

	public WtqCloseAhl(Incident incident) {
		super(incident);
	}

}
