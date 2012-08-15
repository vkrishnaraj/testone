package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("SCANQRY")
@Proxy(lazy = true)
public class GeneralScanQueryLog extends GeneralLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6763011705182650093L;

}
