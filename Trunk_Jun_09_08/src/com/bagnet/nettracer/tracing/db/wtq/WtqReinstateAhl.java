package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("RIT_AHL")
@Proxy(lazy = false)
public class WtqReinstateAhl extends WtqIncidentAction {

}
