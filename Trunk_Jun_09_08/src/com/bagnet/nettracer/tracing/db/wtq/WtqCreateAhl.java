package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("CREATE_AHL")
@Proxy(lazy = false)
public class WtqCreateAhl extends WtqIncidentAction {
}
