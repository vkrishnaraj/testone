package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bagnet.nettracer.tracing.utils.DateUtils;

@Embeddable
public class Comment implements Serializable {
	public Comment() {
	}
	
	public Comment(Agent agent) { 
		this.createDate = DateUtils.convertSystemDateToGMTDate(new Date());
		this.agent = agent;
	}
	
	String content;
	Agent agent;
	Date createDate;

	@Column(length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne
	@JoinColumn(name="agent_id")
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object otherObject) {
		// TODO Auto-generated method stub
		if(this == otherObject) return true;
		if(otherObject == null) return false;
		if(!(otherObject instanceof Comment)) return false;
		
		if(agent == null || content == null || createDate == null) return false;
		
		Comment o = (Comment) otherObject;
		return (agent.equals(o.getAgent()) && content.equals(o.getContent()) && createDate.equals(o.getCreateDate()));
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (agent == null ? 0 : agent.hashCode());
		result = 37 * result + (content == null ? 0 : content.hashCode());
		result = 37 * result + (createDate == null ? 0 : createDate.hashCode());
		return result;
	}
}
