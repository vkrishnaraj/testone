/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.StringUtils;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "ForwardNotice")
@Proxy(lazy = false)
public class ForwardNotice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final int OPEN_STATUS = 0;

	@Transient
	public static final int CLOSED_STATUS = 1;
	
	private static final String MSG_KEY = "FORWARD_NOTICE_KEY_";

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "ohd_log_id")
	private OHD_Log forward;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@Basic
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OHD_Log getForward() {
		return forward;
	}

	public void setForward(OHD_Log forward) {
		this.forward = forward;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKey() {
		return MSG_KEY + getStatus();
	}
	
	public String getDispItinerary() {
		ArrayList<String> list = new ArrayList<String>();
		String pre = null;
		String post = null;
		for (OHD_Log_Itinerary itin: (Set<OHD_Log_Itinerary>)forward.getItinerary()) {
			
			if (itin.getLegto().equalsIgnoreCase(station.getStationcode())) {
				pre = "<strong>";
				post = "</strong>";
			} else {
				pre = "";
				post = "";
			}
			
			String b = StringUtils.join(" ", pre, itin.getAirline() + itin.getFlightnum(), itin.getLegfrom() + " to " + itin.getLegto() + "<br />",
					"Departs:", itin.getDisdepartdate(), itin.getDisschdeparttime(),
					"Arrives:", itin.getDisarrivedate(), itin.getDisscharrivetime(), post);		
			list.add(b.toString());
		}
		return StringUtils.join(list, "<br /><br />");
	}

}