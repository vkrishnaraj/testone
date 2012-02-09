package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("TELEX")
@Proxy(lazy = true)
public class GeneralTelexLog extends GeneralLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1922812271147988868L;

}
