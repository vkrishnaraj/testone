package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="state")
public class StateCode implements Serializable{

	private static final long serialVersionUID = -2568718205752172533L;

	@Id
	@Column(name="state_id")
	private String id;
	
	private String state ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
