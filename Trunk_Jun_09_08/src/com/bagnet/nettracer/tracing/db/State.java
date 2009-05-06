/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="State"
 */
public class State implements Serializable {
	private String State_ID;
	private String state;

	/**
	 * @return Returns the state.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *          The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return Returns the state_ID.
	 * 
	 * @hibernate.id generator-class="assigned" type="string" length="2"
	 *               column="State_ID"
	 */
	public String getState_ID() {
		return State_ID;
	}

	/**
	 * @param state_ID
	 *          The state_ID to set.
	 */
	public void setState_ID(String state_ID) {
		State_ID = state_ID;
	}
}