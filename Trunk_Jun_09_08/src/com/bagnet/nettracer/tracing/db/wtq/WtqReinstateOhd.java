package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("RIT_OHD")
@Proxy(lazy = false)
public class WtqReinstateOhd extends WtqOhdAction {

}
