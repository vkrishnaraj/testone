package com.bagnet.nettracer.tracing.db.taskmanager;

import java.util.Locale;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("DAMAGED")
@Proxy(lazy = true)
public class DamagedTask extends InboundQueueTask{
	@Transient
	public String getDescription() {
		String description = null;
		if(getLocale() != null){
			description = messages.getMessage(new Locale(getLocale()), "colname.unassignedinbound.damaged");
		}
		if(description != null){
			return description;
		} else {
			return "DAMAGED TASK";
		}
	}
}
