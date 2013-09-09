package com.bagnet.nettracer.tracing.db.templates;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="document_template_var")
public class DocumentTemplateVar {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String displayTag;
	
	@Column(nullable = false)
	private String associatedClass;
	
	@Column(nullable = false)
	private String classVar;

	@ManyToMany(mappedBy = "variables")
	private Set<DocumentTemplate> templates;
	
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

	public String getClassVar() {
		return classVar;
	}

	public void setClassVar(String classVar) {
		this.classVar = classVar;
	}

}
