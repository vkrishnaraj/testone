/**
 * 
 */
package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Utpal
 * 
 */
@Entity
@Table(name="label")
public class Label  implements Serializable{

	private static final long serialVersionUID = -7224068351200422596L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String label;
	
	
	private Long delayedState;
	
	
	private Long damagedState;
	
	
	private Long pilferedState;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getDelayedState() {
		return delayedState;
	}

	public void setDelayedState(Long delayedState) {
		this.delayedState = delayedState;
	}

	public Long getDamagedState() {
		return damagedState;
	}

	public void setDamagedState(Long damagedState) {
		this.damagedState = damagedState;
	}

	public Long getPilferedState() {
		return pilferedState;
	}

	public void setPilferedState(Long pilferedState) {
		this.pilferedState = pilferedState;
	}

	

	

		

	
	
}
