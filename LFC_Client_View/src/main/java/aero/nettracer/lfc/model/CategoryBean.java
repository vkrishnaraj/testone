package aero.nettracer.lfc.model;

import java.io.Serializable;
import java.util.Set;

public class CategoryBean implements Serializable{
	
	private static final long serialVersionUID = 5231388957407338204L;
	
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
	public Set<KeyValueBean> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<KeyValueBean> subcategories) {
		this.subcategories = subcategories;
	}
	
	private long id;
	String description;
	Set <KeyValueBean> subcategories;

}
