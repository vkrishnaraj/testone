/**
 * 
 */
package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Utpal
 * 
 */
@Entity
@Table(name="dropdown")
public class DropDown  implements  Serializable {
	
	private static final long serialVersionUID = 7734561465258610842L;

	@Id
	private Long id;
	
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
}
