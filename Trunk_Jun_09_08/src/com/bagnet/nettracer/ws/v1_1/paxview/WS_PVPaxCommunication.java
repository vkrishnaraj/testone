package com.bagnet.nettracer.ws.v1_1.paxview;

public class WS_PVPaxCommunication {

	private String acknowledged_agent;
	private String acknowledged_airline;

	private String acknowledged_timestamp;

	private String agent;

	private String comment;
	private String create_timestamp;
	private String status;

	public String getAcknowledged_agent() {
		return acknowledged_agent;
	}

	public String getAcknowledged_airline() {
		return acknowledged_airline;
	}

	public String getAcknowledged_timestamp() {
		return acknowledged_timestamp;
	}

	public String getAgent() {
		return agent;
	}

	public String getComment() {
		return comment;
	}

	public String getCreate_timestamp() {
		return create_timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setAcknowledged_agent(String acknowledged_agent) {
		this.acknowledged_agent = acknowledged_agent;
	}

	public void setAcknowledged_airline(String acknowledged_airline) {
		this.acknowledged_airline = acknowledged_airline;
	}

	public void setAcknowledged_timestamp(String acknowledged_timestamp) {
		this.acknowledged_timestamp = acknowledged_timestamp;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreate_timestamp(String create_timestamp) {
		this.create_timestamp = create_timestamp;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
