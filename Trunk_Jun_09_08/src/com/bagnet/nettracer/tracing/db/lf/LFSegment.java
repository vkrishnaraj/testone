package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Table(name="lfsegment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LFSegment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3566745444509507964L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "origin_station_ID", nullable = true)
	private Station origin;
	
	@ManyToOne
	@JoinColumn(name = "destination_station_ID", nullable = true)
	private Station destination;
	
	private String flightNumber;
	
	@ManyToOne
	@JoinColumn(name = "lost_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public Station getOrigin() {
		return origin;
	}

	public void setOrigin(Station origin) {
		this.origin = origin;
	}

	public Station getDestination() {
		return destination;
	}

	public void setDestination(Station destination) {
		this.destination = destination;
	}
	
	public int getOriginId() {
		if (getOrigin() != null) {
			return getOrigin().getStation_ID();
		}
		return 0;
	}
	
	public void setOriginId(int originId) {
		if (originId != 0) {
			if (getOrigin() == null) {
				setOrigin(new Station());
			}
			getOrigin().setStation_ID(originId);
		} else {
			setOrigin(null);
		}
	}
	
	public int getDestinationId() {
		if (getDestination() != null) {
			return getDestination().getStation_ID();
		}
		return 0;
	}
	
	public void setDestinationId(int destinationId) {
		if (destinationId != 0) {
			if (getDestination() == null) {
				setDestination(new Station());
			}
			getDestination().setStation_ID(destinationId);
		} else {
			setDestination(null);
		}
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}
	
}
