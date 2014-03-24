package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * 
 * @author SeanFine
 *
 * Object Class for Category Table
 */
@Entity
@Table(name = "category")
@Proxy(lazy = false)
public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2385321012837530679L;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getCategoryVal() {
		return categoryVal;
	}
	public void setCategoryVal(int categoryVal) {
		this.categoryVal = categoryVal;
	}

	@Id
	private long id;
	
	/**
	 * Description of the Category
	 */
	@Column(length=100)
	String description;

	/**
	 * What object the category pertains to
	 */
	private int type;
	

	/**
	 * Value of Category (if existing prior
	 */
	private int categoryVal;
}
