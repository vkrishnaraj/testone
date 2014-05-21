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
	
	private boolean containsHtml;
	
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
	
	public boolean getContainsHtml() {
		return containsHtml;
	}

	public void setContainsHtml(boolean containsHtml) {
		this.containsHtml = containsHtml;
	}
	
	public boolean variableValueCanContainHtml() {
		return getContainsHtml();
	}
	
	@Override
	public String toString() {
		if (associatedClass == null || displayTag == null) return "";
		return "{" + this.associatedClass + "." + this.displayTag + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associatedClass == null) ? 0 : associatedClass.hashCode());
		result = prime * result + (containsHtml ? 1231 : 1237);
		result = prime * result + ((displayTag == null) ? 0 : displayTag.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((templates == null) ? 0 : templates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateVar other = (TemplateVar) obj;
		if (associatedClass == null) {
			if (other.associatedClass != null)
				return false;
		} else if (!associatedClass.equals(other.associatedClass))
			return false;
		if (containsHtml != other.containsHtml)
			return false;
		if (displayTag == null) {
			if (other.displayTag != null)
				return false;
		} else if (!displayTag.equals(other.displayTag))
			return false;
		if (id != other.id)
			return false;
		if (templates == null) {
			if (other.templates != null)
				return false;
		} else if (!templates.equals(other.templates))
			return false;
		return true;
	}

}
