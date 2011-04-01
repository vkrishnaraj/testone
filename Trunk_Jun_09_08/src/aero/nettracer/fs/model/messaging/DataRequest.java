package aero.nettracer.fs.model.messaging;

import aero.nettracer.fs.model.detection.MatchHistory;

public class DataRequest {
	private long id;
	private MatchHistory match;
	private String request;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchHistory getMatch() {
		return match;
	}

	public void setMatch(MatchHistory match) {
		this.match = match;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

}
