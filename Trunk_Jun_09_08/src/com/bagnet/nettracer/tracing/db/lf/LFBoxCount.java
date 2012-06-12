package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Proxy(lazy = false)
public class LFBoxCount implements Serializable {
	
	private static final long serialVersionUID = 5806495189281178779L;

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station source;
	
	@JoinColumn(name = "boxCount", nullable = false)
	private int boxCount;
	
	@ManyToOne
	@JoinColumn(name = "container_ID", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFBoxContainer container;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Station getSource() {
		return source;
	}
	
	public void setSource(Station source) {
		this.source =  source;
	}
	
	public int getSourceId() {
		return source.getStation_ID();
	}

	public String getSourceName() {
		return source.getStationcode();
	}
	
	public int getBoxCount() {
		return boxCount;
	}
	
	public void setBoxCount(int boxCount) {
		this.boxCount=boxCount;
	}
	
	public LFBoxContainer getContainer() {
		return container;
	}
	
	public void setContainer (LFBoxContainer container) {
		this.container=container;
	}

	
}
