package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("PCN")
@Proxy(lazy = true)
public class GeneralPcnLog extends GeneralLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 522713347090003550L;

}
