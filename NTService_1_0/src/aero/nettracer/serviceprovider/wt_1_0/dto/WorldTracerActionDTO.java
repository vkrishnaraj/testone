package aero.nettracer.serviceprovider.wt_1_0.dto;

import aero.nettracer.serviceprovider.common.db.User;

public class WorldTracerActionDTO {
	private WorldTracerActionType type;
	private User user;
	private Object payload;
	
	
	public WorldTracerActionType getType() {
		return type;
	}

	public void setType(WorldTracerActionType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
