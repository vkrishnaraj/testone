package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("SUS_OHD")
@Proxy(lazy = false)
public class WtqSuspendOhd extends WtqOhdAction {

}
