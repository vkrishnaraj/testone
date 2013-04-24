package aero.nettracer.fs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class FsActionAuditMetric {
	
	@Id
	@GeneratedValue
	long id;
	
	String metric;
	long duration;
	int traceElements;
	String remark;
	
	@ManyToOne
	@JoinColumn(name = "action_id")
	private FsActionAudit action;

	public FsActionAuditMetric(String metric, long duration, int traceElements, String remark){
		this.metric = metric;
		this.duration = duration;
		this.traceElements = traceElements;
		this.remark = remark;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getTraceElements() {
		return traceElements;
	}

	public void setTraceElements(int traceElements) {
		this.traceElements = traceElements;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public FsActionAudit getAction() {
		return action;
	}

	public void setAction(FsActionAudit action) {
		this.action = action;
	}
	
}
