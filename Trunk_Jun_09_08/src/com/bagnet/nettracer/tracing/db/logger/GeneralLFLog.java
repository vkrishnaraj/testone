package com.bagnet.nettracer.tracing.db.logger;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("LF")
@Proxy(lazy = true)
public class GeneralLFLog extends GeneralLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6646060859028887990L;

}
