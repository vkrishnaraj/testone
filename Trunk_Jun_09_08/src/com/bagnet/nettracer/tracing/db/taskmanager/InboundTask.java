package com.bagnet.nettracer.tracing.db.taskmanager;

import java.util.Locale;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("INBOUND")
@Proxy(lazy = true)
public class InboundTask extends InboundQueueTask{
	@Transient
	public String getDescription() {
		String description = null;
		if(getLocale() != null){
			description = messages.getMessage(new Locale(getLocale()), "colname.unassignedinbound.inbound");
		}
		if(description != null){
			return description;
		} else {
			return "INBOUND TASK";
		}
	}
}
