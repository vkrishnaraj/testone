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
public class LFSalvage implements Serializable {
	
	private static final long serialVersionUID = 5806495189281178779L;

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station location;
	
	@OneToMany(mappedBy = "salvage", fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<LFFound> items;
	
	private Date createdDate;
	private Date closedDate;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Agent getAgent() {
		return agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Set<LFFound> getItems() {
		return items;
	}
	
	public void setItems(Set<LFFound> items) {
		this.items = items;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getClosedDate() {
		return closedDate;
	}
	
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	
	public String getDisCreatedDate(String dateFormat) {
		return DateUtils.formatDate(createdDate, dateFormat, agent.getCurrenttimezone(), null);
	}

	public String getDisClosedDate(String dateFormat) {
		if (closedDate != null) {
			return DateUtils.formatDate(closedDate, dateFormat, agent.getCurrenttimezone(), null);
		}
		return "N/A";
	}
	
	public int getStatusId() {
		return status.getStatus_ID();
	}
	
	public void setStatusId(int statusId) {
		status.setStatus_ID(statusId);
	}

	public Station getLocation() {
		return location;
	}

	public void setLocation(Station location) {
		this.location = location;
	}
	
}
