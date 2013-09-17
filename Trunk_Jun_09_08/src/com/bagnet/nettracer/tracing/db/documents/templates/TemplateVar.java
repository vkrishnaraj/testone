package com.bagnet.nettracer.tracing.db.documents.templates;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="template_var")
public class TemplateVar {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String displayTag;
	
	@Column(nullable = false)
	private String associatedClass;
	
	@ManyToMany(mappedBy = "variables")
	private Set<Template> templates;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayTag() {
		return displayTag;
	}

	public void setDisplayTag(String displayTag) {
		this.displayTag = displayTag;
	}

	public String getAssociatedClass() {
		return associatedClass;
	}

	public void setAssociatedClass(String associatedClass) {
		this.associatedClass = associatedClass;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) return false;
		TemplateVar that = (TemplateVar) o;
		return this.associatedClass.equals(that.associatedClass) && this.displayTag.equals(that.displayTag);
	}
	
	@Override
	public String toString() {
		if (associatedClass == null || displayTag == null) return "";
		return "{" + this.associatedClass + "." + this.displayTag + "}";
	}

}
