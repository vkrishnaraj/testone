package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bagnet.nettracer.tracing.db.GeneralRemark;

@Entity
public class LFRemark implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5769095740895350735L;

	@Id
	@GeneratedValue
	private long id;
	
	private long calltime;
	
	private int outcome;
	
	@Embedded
	private GeneralRemark remark;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "found_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFFound found;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lost_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCalltime() {
		return calltime;
	}

	public void setCalltime(long calltime) {
		this.calltime = calltime;
	}

	public int getOutcome() {
		return outcome;
	}

	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}

	public GeneralRemark getRemark() {
		if (remark == null) {
			remark = new GeneralRemark();
		}
		return remark;
	}

	public void setRemark(GeneralRemark remark) {
		this.remark = remark;
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public LFFound getFound() {
		return found;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}

	public LFLost getLost() {
		return lost;
	}


}
