package com.bagnet.nettracer.tracing.db.documents.templates;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bagnet.nettracer.tracing.enums.TemplateType;

@Entity
@Table(name = "template_type")
public class TemplateTypeMapping {

	@Id
	@GeneratedValue
	private long id;
	
	private int ordinal;
	
	private String defaultName;
	
	@ManyToMany(mappedBy = "types")
	private Set<Template> templates;
	
	public TemplateTypeMapping() { }
	
	public TemplateTypeMapping(TemplateType type) {
		this.ordinal = type.ordinal();
		this.defaultName = type.getDefaultName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Set<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(Set<Template> templates) {
		this.templates = templates;
	}
	
}
