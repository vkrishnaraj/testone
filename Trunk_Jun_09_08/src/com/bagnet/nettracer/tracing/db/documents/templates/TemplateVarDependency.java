package com.bagnet.nettracer.tracing.db.documents.templates;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "template_var_dependency")
public class TemplateVarDependency {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String associatedClass;

	@Column(nullable = false)
	private String dependentClass;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAssociatedClass() {
		return associatedClass;
	}

	public void setAssociatedClass(String associatedClass) {
		this.associatedClass = associatedClass;
	}

	public String getDependentClass() {
		return dependentClass;
	}

	public void setDependentClass(String dependentClass) {
		this.dependentClass = dependentClass;
	}
	
	public boolean satisfied(List<String> varClassNames) {
		return varClassNames.contains(dependentClass);
	}

}
