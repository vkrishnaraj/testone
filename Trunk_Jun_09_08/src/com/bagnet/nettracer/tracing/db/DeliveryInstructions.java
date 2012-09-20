/*
 * Created on September 14, 2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "delivery_instructions")
@Proxy(lazy = false)
public class DeliveryInstructions implements Serializable {
	private int id;
	private String instructions;
	
	/**
	 * @return Returns the instructionsID.
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="id"
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	/**
	 * @param instructionsID
	 *          The instructionsID to set.
	 */
	public void setId(int id) {
		this.id=id;
	}
	

	/**
	 * @return Returns the instructions.
	 * 
	 */
	public String getInstructions() {
		return instructions;
	}
	
	/**
	 * @return Returns the instructions.
	 * 
	 */
	public void setInstructions(String instructions) {
		this.instructions=instructions;
	}
	
}