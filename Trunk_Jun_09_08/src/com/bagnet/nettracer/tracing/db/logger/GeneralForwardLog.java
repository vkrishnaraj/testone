package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("FORWARD")
@Proxy(lazy = true)
public class GeneralForwardLog extends GeneralLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6763011705182650093L;

}
