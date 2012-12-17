package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Station;

@Entity
@Proxy(lazy = false)
public class SubcompanyStation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "subcompany_ID", nullable = false)
	private Subcompany subcompany;

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station station;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Subcompany getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(Subcompany subcompany) {
		this.subcompany = subcompany;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
}

